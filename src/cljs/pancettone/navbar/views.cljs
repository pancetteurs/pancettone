(ns pancettone.navbar.views
  (:require-macros [reagent.ratom :refer [reaction]])
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

(defn navbar-comp []
  (let [user (re-frame/subscribe [:user])
        root (re-frame/subscribe [:root])
        active-panel (re-frame/subscribe [:active-panel])
        is-logged-in (reaction (not (nil? @user)))]
    (fn []
      [:div {:style (:header-container style)}
       [:div {:style (:container style)}
        [:div {:style (merge
                       (:item style)
                       (if (= @active-panel :home-panel)
                         (:item-active style)))} "Home"]
        [:div {:on-click #(if @is-logged-in
                            (re-frame/dispatch [:logout])
                            (.authWithOAuthPopup @root "facebook"))
               :style (merge
                       (:item style)
                       (:item-right style))} (if @is-logged-in "Logout" "Login")]]])))
