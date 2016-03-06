(ns pancettone.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [pancettone.handlers]
              [pancettone.subs]
              [pancettone.routes :as routes]
              [pancettone.views :as views]
              [pancettone.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init [] 
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
