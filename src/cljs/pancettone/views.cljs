(ns pancettone.views
    (:require [re-frame.core :as re-frame]
              [pancettone.navbar.views :refer [navbar-comp]]
              [pancettone.home.views :refer [home-panel]]
              [pancettone.tickets.views :refer [tickets-panel]]
              [pancettone.common.ui :as ui]))

(def style {:page {:padding 20
                   :max-width (:viewport-max ui/layout)
                   :margin-left "auto"
                   :margin-right "auto"}})

;; main
(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :tickets-panel [] [tickets-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [:div
       [navbar-comp]
       [:div {:style (:page style)} (panels @active-panel)]])))
