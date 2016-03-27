(ns pancettone.components.date-picker
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs-time.core :as time]
            [cljs-time.format :as time-format]
            [pancettone.common.ui :as ui]
            [cljs-time.periodic :as time-period]))

(def style {:day {:height 40
                  :width 40
                  :line-height "40px"
                  :text-align "center"
                  :border (str "1px solid " (:bg ui/colors))
                  :background-color "white"
                  :cursor "pointer"}
            :month {:text-align "center"}
            :arrow-left {:float "left"
                         :cursor "pointer"}
            :arrow-right {:float "right"
                          :cursor "pointer"}
            :day-selected {:background-color (:bg ui/colors)
                           :color "black"}
            :day-disabled {:color (:bg ui/colors)}
            :header {:text-align "center"
                     :height 40
                     :line-height "40px"
                     :color (:text-negative-active ui/colors)
                     :border "1px solid transparent"
                     :border-bottom (str "1px solid" (:bg ui/colors))}
            :picker {:background-color "white"
                     :position "absolute"
                     :padding 15
                     :width 287
                     :border (str "1px solid " (:bg ui/colors))
                     :top 60}
            :container {:position "relative"
                        :font-size 18
                        :display "inline-block"}
            :label {:cursor "pointer"
                    :padding "5px 10px"
                    :font-weight 600
                    :font-size 25
                    :text-decoration "underline"}})

(defn time-range
  "Return a lazy sequence of DateTime's from start to end, incremented
  by 'step' units of time."
  [start end step]
  (let [inf-range (time-period/periodic-seq start step)
        below-end? (fn [t] (time/within? (time/interval start end)
                                         t))]
    (take-while below-end? inf-range)))

(def months-label ["January"
                   "February"
                   "March"
                   "April"
                   "May"
                   "June"
                   "July"
                   "August"
                   "September"
                   "October"
                   "November"
                   "December"])

(def days-label ["M"
                 "T"
                 "W"
                 "T"
                 "F"
                 "S"
                 "S"])

(defn previous-month [filter-date]
  (let [new-month (dec (:month @filter-date))]
    (if (= 0 new-month)
      (swap! filter-date assoc :month 12 :year (dec (:year @filter-date)))
      (swap! filter-date assoc :month (dec (:month @filter-date))))))

(defn next-month [filter-date]
  (let [new-month (inc (:month @filter-date))]
    (if (> new-month 12)
      (swap! filter-date assoc :month 1 :year (inc (:year @filter-date)))
      (swap! filter-date assoc :month (inc (:month @filter-date))))))

(defn get-days-range [filter-date]
  (let [day (time/date-time (:year filter-date)
                            (:month filter-date))
        first-day-of-the-month (time/first-day-of-the-month day)
        last-day-of-the-month (time/last-day-of-the-month day)
        diff1 (- (time/day-of-week first-day-of-the-month) 1)
        start-date (time/minus first-day-of-the-month (time/days diff1))
        diff2 (- 8 (time/day-of-week last-day-of-the-month))
        end-date (time/plus last-day-of-the-month (time/days diff2))]
    (time-range start-date end-date (time/days 1))))

(defn select-date [date on-change selected-date is-open]
  (reset! selected-date date)
  (reset! is-open false)
  (on-change (time-format/unparse (time-format/formatters :date) date)))

(defn date-picker-component [props]
  (let [selected-date (atom nil)
        is-open (atom false)
        filter-date (atom {:month (time/month (time/today))
                           :year (time/year (time/today))})
        placeholder "dd-MM-YYYY"]
    (fn []
      [:div {:style (:container style)}
       [:div {:on-blur #(reset! is-open false)
              :tab-index "-1"
              :on-click #(reset! is-open (not @is-open))
              :style (:label style)}
        (if (not (nil? @selected-date))
          (time-format/unparse (time-format/formatters :date) @selected-date)
          placeholder)]
       (when @is-open
         [:div {:style (:picker style)
                :on-mouse-down #(.preventDefault %)}
          [:table
           [:thead
            [:tr {:style (:month style)}
             [:td {:col-span "7"}
              [:span {:style (:arrow-left style)
                      :on-mouse-down #(previous-month filter-date)} "<"]
              (str (get months-label (dec (:month @filter-date))) " " (:year @filter-date))
              [:span {:style (:arrow-right style)
                      :on-mouse-down #(next-month filter-date)} ">"]]]
            [:tr
             (for [i (range 7)]
               [:th {:style (:header style)} (get days-label i)])]]
           [:tbody
            (doall
              (for [week (partition 7 (get-days-range @filter-date))]
                [:tr
                 (doall
                   (for [day week :let [is-selected (and (not (nil? @selected-date)) (time/equal? day @selected-date))
                                        is-disabled (not= (:month @filter-date) (time/month day))]]
                     [:td {:style (merge (:day style)
                                         (when (and is-selected (not is-disabled))
                                           (:day-selected style))
                                         (when is-disabled
                                           (:day-disabled style)))
                           :on-mouse-down (when (not is-disabled)
                                            #(select-date day (:on-change props) selected-date is-open))
                           :key day}
                      (when (not is-disabled)
                        (str (time/day day)))]))]))]]])])))
