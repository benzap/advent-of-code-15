(ns advent-of-code-15.advent-6
  (:require [clojure.string :as s]))

(def light-input (slurp "./resources/advent_6.in"))

;;
;; Parsing
;;

(def regex-toggle #"toggle (\d{1,3}),(\d{1,3}) through (\d{1,3}),(\d{1,3})")
(def regex-turn-on #"turn on (\d{1,3}),(\d{1,3}) through (\d{1,3}),(\d{1,3})")
(def regex-turn-off #"turn off (\d{1,3}),(\d{1,3}) through (\d{1,3}),(\d{1,3})")

(defn parse [regex-pattern sx]
  (when-let [[_ x1 y1 x2 y2] (re-matches regex-pattern sx)]
    [[(Integer/parseInt x1) (Integer/parseInt y1)] 
     [(Integer/parseInt x2) (Integer/parseInt y2)]]
    ))

(defn parse-toggle [sx]
  (parse regex-toggle sx))

(defn parse-turn-on [sx]
  (parse regex-turn-on sx))

(defn parse-turn-off [sx]
  (parse regex-turn-off sx))

;;
;; Breaking up into grid actions
;;

(defn grid-light-fcn [[[x1 y1] [x2 y2]] type]
  (for [x (range x1 (inc x2))
        y (range y1 (inc y2))]
    [x y type]
    ))

(defn grid-light-toggle [sgrid]
  (grid-light-fcn sgrid :toggle))

(defn grid-light-on [sgrid]
  (grid-light-fcn sgrid :on))

(defn grid-light-off [sgrid]
  (grid-light-fcn sgrid :off))

(defn parse-line-into-actions [input-line]
  (cond
    ;; Toggle
    (parse-toggle input-line)
    (grid-light-toggle (parse-toggle input-line))
    ;; On
    (parse-turn-on input-line)
    (grid-light-on (parse-turn-on input-line))
    ;; Off
    (parse-turn-off input-line)
    (grid-light-off (parse-turn-off input-line))
    ))

;;
;; Performing a grid action
;;

(defmulti perform-grid-action
  (fn [grid grid-actions]
    (nth grid-actions 2)
    ))

(defmethod perform-grid-action :toggle
  [grid grid-action]
  (let [[x y _] grid-action
        toggle-fcn
        (fn [val]
          (case val
            :on :off
            :off :on))
        ]
    (update-in grid [[x y]] (fnil toggle-fcn :off))
    ))

(defmethod perform-grid-action :on
  [grid grid-action]
  (let [[x y _] grid-action]
    (assoc grid [x y] :on)))

(defmethod perform-grid-action :off
  [grid grid-action]
  (let [[x y _] grid-action]
    (assoc grid [x y] :off)))

(defn perform-light-dance [input]
  (->> input
       (s/split-lines)
       (mapcat parse-line-into-actions)
       (reduce perform-grid-action {})))

(defn count-lights-on [input]
  (->> input
       (perform-light-dance)
       (filter (fn [[_ v]] (= v :on)))
       count))

;; First Solution
;; (count-lights-on light-input)
