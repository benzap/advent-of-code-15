(ns advent-of-code-15.advent-2
  (:require [clojure.string :as s]))

(def package-input (slurp "./resources/advent_2.in"))

(defn parse-package-dimensions [package-string]
  (let [[_ l w h] (re-find #"(\d+)x(\d+)x(\d+)\s*" package-string)]
  (->> [l w h]
      (mapv #(Integer/parseInt %1))
      )))

;;(parse-package-dimensions "2x5x3")

(defn calculate-surface-area [dimensions]
  (let [[l w h] dimensions]
    (-> 0
        (+ (* 2 l w))
        (+ (* 2 w h))
        (+ (* 2 h l))
        )))

(defn get-smallest-side [dimensions]
  (let [[l w h] dimensions]
    (min (* l w) (* w h) (* h l))
    ))

(defn calculate-wrapping-paper [dimensions]
  (let [surface-area (calculate-surface-area dimensions)
        little-extra (get-smallest-side dimensions)
        ]
    (+ surface-area little-extra)
    ))

;;(calculate-wrapping-paper [2 2 2])

(defn get-total-wrapping-paper [input]
  (->> input
       s/split-lines
       (map parse-package-dimensions)
       (map calculate-wrapping-paper)
       (apply +)
       ))

;; First Solution
;; (get-total-wrapping-paper package-input)

(defn calculate-side-distance [dimensions]
  (->> dimensions
       sort
       (take 2)
       (map #(* 2 %))
       (apply +)
  ))

(defn calculate-wrap-distance [dimensions]
  (apply * dimensions))

(defn calculate-ribbon [dimensions]
  (+ (calculate-side-distance dimensions)
     (calculate-wrap-distance dimensions)))

(defn get-total-ribbon [input]
  (->> input
       s/split-lines
       (map parse-package-dimensions)
       (map calculate-ribbon)
       (apply +)
       ))

;; Second Solution
(get-total-ribbon package-input)
