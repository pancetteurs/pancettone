(ns pancettone.ticket.views
  (:require [pancettone.common.ui :as ui]
            [pancettone.common.helpers :refer [price-to-str]]))

(def style {:item {:background-color (:bg-widget ui/colors)
                   :border-radius 3
                   :margin-bottom 2
                   :padding 20}})

(defn ticket-comp [ticket]
  (fn []
   [:div {:style (:item style)}
    [:span "Ticket from " (:from ticket)]
    [:span " to " (:to ticket)]
    [:span " on " (:on ticket)]
    [:span " for " (price-to-str (:price ticket))]]))
