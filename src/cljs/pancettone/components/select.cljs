(ns pancettone.components.select
  (:require [reagent.core :as reagent :refer [atom]]
            [pancettone.common.ui :as ui]))

(def style {:label {:cursor "pointer"
                    :padding "5px 10px"
                    :font-weight 600
                    :font-size 25
                    :text-decoration "underline"}
            :container {:display "inline-block"
                        :position "relative"
                        :font-size 18}
            :option {:padding "5px 15px"
                     :cursor "pointer"
                     :border-bottom (str "1px solid " (:bg ui/colors))}
            :option-hovered {:background-color (:bg-widget-active ui/colors)}
            :dropdown {:background-color "white"
                       :position "absolute"
                       :z-index 100
                       :top 50}})

(defn select-value [val selected-value open? on-change hovered]
  (reset! selected-value val)
  (reset! open? false)
  (reset! hovered nil)
  (on-change val))

(defn select-component [props]
  (let [open? (atom false)
        hovered (atom nil)
        options (into {} (:options props))
        selected-value (atom nil)]
    (fn []
      [:div {:style (:container style)}
       [:span {:style (:label style)
               :on-blur #(reset! open? false)
               :on-click #(reset! open? (not @open?))
               :tab-index "-1"}
        (if (not (nil? @selected-value))
          (get options @selected-value)
          (get options (:default-value props)))]
       (when @open?
         [:div {:style (:dropdown style)
                :on-mouse-down #(.preventDefault %)}
          (doall (for [[val name] (:options props)]
                   [:div {:style (merge
                                  (:option style)
                                  (when (= @hovered val)
                                    (:option-hovered style)))
                          :on-mouse-over #(reset! hovered val)
                          :on-mouse-out #(reset! hovered nil)
                          :on-mouse-down #(select-value val selected-value open? (:on-change props) hovered)
                          :key val} name]))])])))
