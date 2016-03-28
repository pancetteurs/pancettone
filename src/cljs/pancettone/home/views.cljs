(ns pancettone.home.views
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [pancettone.components.date-picker :refer [date-picker-component]]
            [pancettone.components.select :refer [select-component]]
            [pancettone.ticket.views :refer [ticket-comp]]))

(def style {:form {:margin-bottom 25
                   :text-align "center"
                   :padding 20
                   :font-size 30}})

(defn visible? [ticket date from to]
  (and (or (= "" date) (= date (:on ticket)))
       (or (= "" from) (= from (:from ticket)))
       (or (= "" to) (= to (:to ticket)))))

(defn home-panel []
  (let [cities (re-frame/subscribe [:cities true])
        tickets (re-frame/subscribe [:tickets])
        form (re-frame/subscribe [:search-form])
        date (reaction (:date @form))
        from (reaction (:from @form))
        to (reaction (:to @form))]
    (fn []
      [:div
        [:div {:style (:form style)}
         [:span "I am looking for a train on the "]
         [date-picker-component {:on-change #(re-frame/dispatch [:change-search :date %])}]
         [:span " from"]
         [select-component {:options @cities
                            :default-value @from
                            :on-change #(re-frame/dispatch [:change-search :from %])}]
         [:span " to "]
         [select-component {:options @cities
                            :default-value @to
                            :on-change #(re-frame/dispatch [:change-search :to %])}]]
       (if (= 0 (count @tickets))
         [:div "Loading..."]
         [:div (doall (for [ticket-ref @tickets
                            :let [id (first ticket-ref)
                                  ticket (second ticket-ref)]
                            :when (visible? ticket @date @from @to)]
                        [:div {:key id} [ticket-comp ticket]]))])])))
