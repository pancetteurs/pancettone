(ns pancettone.navbar.views
  (:require [pancettone.common.ui :as ui]
            [re-frame.core :as re-frame]))

(def style {:header-container {:background-color (:bg-negative ui/colors)}
            :container {:height 60 :display "flex"
                        :color (:text-negative ui/colors)
                        :align-items "stretch"
                        :flex-direction "row"
                        :flex-wrap "wrap"
                        :justify-content "flex-start"
                        :max-width (:viewport-max ui/layout)
                        :min-width (:viewport-min ui/layout)
                        :margin-left "auto"
                        :margin-right "auto"}
            :item-right {:margin-left "auto"}
            :item {:padding 15 :cursor "pointer"}
            :item-active {:background-color (:bg ui/colors) :color (:bg-negative ui/colors)}})

(defn navbar-comp [active-panel user]
  (let [is-logged-in (not (nil? user))]
    [:div {:style (:header-container style)}
     [:div {:style (:container style)}
      [:div {:style (merge
                     (:item style)
                     (if (= active-panel :home-panel)
                       (:item-active style)))} "Home"]
      [:div {:on-click #(re-frame/dispatch [(if is-logged-in :logout :login-request)])
             :style (merge
                     (:item style)
                     (:item-right style))} (if is-logged-in "Logout" "Login")]]]))
