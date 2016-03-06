(ns pancettone.config)

(def debug?
  ^boolean js/goog.DEBUG)

(when debug?
  (enable-console-print!))

(def firebase-app-name "blazing-fire-3944")
(def firebase-url (str "https://" firebase-app-name ".firebaseio.com"))
