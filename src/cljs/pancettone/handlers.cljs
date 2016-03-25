(ns pancettone.handlers
    (:require [re-frame.core :as re-frame]
              [matchbox.core :as m]
              [pancettone.config :as config]
              [pancettone.db :as db]))

(re-frame/register-handler
  :set-active-panel
  (fn [db [_ active-panel]]
    (assoc db :active-panel active-panel)))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   (let [root (m/connect config/firebase-url)
         child (m/get-in root [:tickets])]
     (.onAuth root #(re-frame/dispatch [:login-success %]))
     (m/listen-to child :value #(re-frame/dispatch [:process-tickets %]))
     (assoc db/default-db :root root))))

(re-frame/register-handler
 :process-tickets
 (fn [db [_ response]]
   (assoc db :tickets (get response 1))))

(re-frame/register-handler
  :login-success
  (fn [db [_ user]]
    (if (nil? user)
      db
      (assoc db :user user))))

(re-frame/register-handler
  :logout
  (fn [db [_ user]]
    (m/unauth (:root db))
    (assoc db :user nil)))

(re-frame/register-handler
 :change-search
 (fn [db [_ field val]]
   (assoc-in db [:search-form field] val)))
