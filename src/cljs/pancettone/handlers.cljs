(ns pancettone.handlers
    (:require [re-frame.core :as re-frame]
              [matchbox.core :as m]
              [pancettone.config :as config]
              [pancettone.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   (let [root (m/connect config/firebase-url)
         child (m/get-in root [:tickets])]
     (m/listen-to child :value #(re-frame/dispatch [:process-tickets %])))
   db/default-db))

(re-frame/register-handler
 :process-tickets
 (fn [db [_ response]]
   (assoc db :tickets (get response 1))))

(re-frame/register-handler
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))
