(ns pancettone.create.views
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [pancettone.common.ui :as ui]
            [pancettone.components.input-number :refer [input-number-component]]
            [pancettone.components.date-picker :refer [date-picker-component]]
            [pancettone.components.select :refer [select-component]]))

(def style {:form {:margin-bottom 25
                   :text-align "center"
                   :padding 20
                   :font-size 30}
            :input-number {:background-color (:bg ui/colors)
                           :border "none"
                           :height 38
                           :font-weight 600
                           :font-size 25
                           :text-decoration "underline"
                           :line-height "38px"
                           :width 30}})

(defn create-panel []
  (let [cities (re-frame/subscribe [:cities false])]
    (fn []
      [:div
       [:div {:style (:form style)}
        [:span "I want to sell a ticket from"]
        [select-component {:options @cities
                           :default-value "London"
                           :on-change #(js/console.log %)}]
        [:span " to "]
        [select-component {:options @cities
                           :default-value "Paris"
                           :on-change #(js/console.log %)}]
        [:span " on the "]
        [date-picker-component {:on-change #(js/console.log %)}]
        [:span " at "]
        [input-number-component {:min 0
                                 :max 24
                                 :digits 2
                                 :on-change #(js/console.log %)}]
        [:span "h "]
        [input-number-component {:min 0
                                 :digits 2
                                 :on-change #(js/console.log %)
                                 :max 59}]]])))
