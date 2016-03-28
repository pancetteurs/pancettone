(ns pancettone.components.input-number
  (:require [reagent.core :as reagent :refer [atom]]
            [pancettone.common.ui :as ui]))

(def style {:input {:background-color (:bg ui/colors)
                    :border "none"
                    :height 38
                    :font-weight 600
                    :font-size 25
                    :text-decoration "underline"
                    :line-height "38px"
                    :width 30}})

(defn on-change [e value min max digits]
  (let [new-value (-> e .-target .-value)
        pattern (re-pattern (str "^\\d{0," digits "}$"))
        valid? (and (>= new-value min) (<= new-value max) (re-matches pattern new-value))]
    (when valid?
      (reset! value new-value))))

(defn get-full-number [n digits]
  (let [length (-> (str n) .-length)
        diff (- digits length)]
    (if (< length digits)
      (str (apply str (repeat diff "0")) n)
      n)))

(defn on-blur [value min on-change digits]
  (let [new-value (get-full-number @value digits)]
    (on-change new-value)
    (reset! value (get-full-number @value digits))))

(defn input-number-component [props]
  (let [value (atom (get-full-number (:min props) (:digits props)))]
    (fn []
      [:input {:type "text"
               :style (:input style)
               :value @value
               :on-blur #(on-blur value (:min props) (:on-change props) (:digits props))
               :on-change #(on-change % value (:min props) (:max props) (:digits props))}])))
