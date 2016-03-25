(ns pancettone.home.views
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [pancettone.ticket.views :refer [ticket-comp]]))

(defn visible? [ticket date from to]
  (and (or (= "" date) (= date (:on ticket)))
       (or (= "" from) (= from (:from ticket)))
       (or (= "" to) (= to (:to ticket)))))

(defn home-panel []
  (let [cities (re-frame/subscribe [:cities])
        tickets (re-frame/subscribe [:tickets])
        form (re-frame/subscribe [:search-form])
        date (reaction (:date @form))
        from (reaction (:from @form))
        to (reaction (:to @form))]
    (fn []
      [:div
       [:form {:style {:margin-bottom 20} :class-name "pure-form widget"}
        [:h3 "Find a ticket"]
        [:span "I am looking for a train on the "]
        [:input {:type "date"
                 :value @date
                 :min (-> (js/Date.) .toISOString (.slice 0 10))
                 :on-change #(re-frame/dispatch [:change-search :date (-> % .-target .-value)])}]
        [:span " from "]
        [:select {:default-value @from
                  :on-change #(re-frame/dispatch [:change-search :from (-> % .-target .-value)])}
         (doall (for [[val name] @cities :when (not= name @to)]
            [:option {:key val :value val} name]))]
        [:span " to "]
        [:select {:default-value @from
                  :on-change #(re-frame/dispatch [:change-search :to (-> % .-target .-value)])}
         (doall (for [[val name] @cities :when (not= name @from)]
            [:option {:key val :value val} name]))]]
       (if (= 0 (count @tickets))
         [:div "Loading..."]
         [:div (doall (for [ticket-ref @tickets
                            :let [id (first ticket-ref)
                                  ticket (second ticket-ref)]
                            :when (visible? ticket @date @from @to)]
                        [:div {:key id} [ticket-comp ticket]]))])])))
