(ns pancettone.tickets.views
  (:require [re-frame.core :as re-frame]
            [pancettone.ticket.views :refer [ticket-comp]]))

(defn tickets-panel []
  (let [user (re-frame/subscribe [:user])
        tickets (re-frame/subscribe [:tickets])]
    (fn []
      [:div
       [:h3 "Tickets You Created"]
       (doall (for [ticket-ref @tickets
                    :let [id (first ticket-ref)
                          ticket (second ticket-ref)]
                    :when (= (.-uid @user) (:owner ticket))]
                ^{:key id}
                [ticket-comp ticket]))])))
