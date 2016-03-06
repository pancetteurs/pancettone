(ns pancettone.home.views
  (:require [re-frame.core :as re-frame]
            [pancettone.ticket.views :refer [ticket-comp]]))

(defn home-panel []
  (let [tickets (re-frame/subscribe [:tickets])]
    (fn []
      [:div
       (if (= 0 (count @tickets))
         [:div "Loading..."]
         [:div (for [ticket-ref @tickets
                     :let [id (first ticket-ref)
                           ticket (second ticket-ref)]]
                 [:div {:key id} [ticket-comp ticket]])])])))
