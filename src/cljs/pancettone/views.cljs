(ns pancettone.views
    (:require [re-frame.core :as re-frame]
              [pancettone.navbar.views :refer [navbar-comp]]
              [pancettone.home.views :refer [home-panel]]
              [pancettone.common.ui :as ui]))

(def style {:page {:padding 20
                   :max-width (:viewport-max ui/layout)
                   :min-width (:viewport-min ui/layout)
                   :margin-left "auto"
                   :margin-right "auto"}})

;; main
(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])
        user (re-frame/subscribe [:user])]
    (fn []
      [:div
       [navbar-comp @active-panel @user]
       [:div {:style (:page style)} (panels @active-panel)]])))
