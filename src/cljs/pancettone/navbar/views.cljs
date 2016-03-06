(ns pancettone.navbar.views
  (:require [pancettone.common.ui :as ui]))

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
            :item {:padding 15 :cursor "pointer"}
            :item-active {:background-color (:bg ui/colors) :color (:bg-negative ui/colors)}})

(defn navbar-comp [active-panel]
  [:div {:style (:header-container style)}
   [:div {:style (:container style)}
    [:div {:style (merge
                   (:item style)
                   (if (= active-panel :home-panel)
                     (:item-active style)))} "Home"]]])
