(ns pancettone.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
  :active-panel
  (fn [db _]
    (reaction (:active-panel @db))))

(re-frame/register-sub
 :cities
 (fn [db [_ anywhere?]]
  (reaction
   (let [cities (:cities @db)
         new-cities (map #(vec [% %]) cities)]
     (if anywhere?
       (into [["" "Anywhere"]] new-cities)
       new-cities)))))

(re-frame/register-sub
 :tickets
 (fn [db]
   (reaction (:tickets @db))))

(re-frame/register-sub
 :user
 (fn [db]
   (reaction (:user @db))))

(re-frame/register-sub
 :root
 (fn [db]
   (reaction (:root @db))))

(re-frame/register-sub
 :search-form
 (fn [db]
   (reaction (:search-form @db))))
