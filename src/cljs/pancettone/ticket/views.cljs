(ns pancettone.ticket.views
  (:require [pancettone.common.ui :as ui]
            [pancettone.common.helpers :refer [price-to-str]]))

(def style {:item {:margin-bottom 2}})

(defn ticket-comp [ticket]
  (fn []
   [:div {:style (:item style) :class-name "widget"}
    [:span "🚆 "]
    [:span (:from ticket)]
    [:span " ➔ " (:to ticket)]
    [:span {:class-name "label"} " on the "]
    [:span (:on ticket)]
    [:span {:class-name "label"} " at "]
    [:span (:at ticket)]
    [:span {:class-name "label"} " for "]
    [:span (price-to-str (:price ticket))]]))
