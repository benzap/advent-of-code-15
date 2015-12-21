(ns advent-of-code-15.advent-5
  (:require [clojure.string :as s]))

(def letter-input (slurp "./resources/advent_5.in"))

(defn contains-three-vowels? [sx]
  (->> (re-seq #"a|e|i|o|u" sx)
       count
       (<= 3)))

(defn one-letter-repeating? [sx]
  (->> (re-seq #"([a-z])\1+" sx)
       empty?
       not))

(defn contains-naughty-substring? [sx letters]
  (-> (.indexOf sx letters) (>= 0)))

(def naughty-patterns
  ["ab"
   "cd"
   "pq"
   "xy"
   ])

(defn contains-naughty-patterns? [sx]
  (some (partial contains-naughty-substring? sx) naughty-patterns))

(defn is-string-nice? [sx]
  (every? true?
   [(contains-three-vowels? sx)
    (one-letter-repeating? sx)
    (not (contains-naughty-patterns? sx))
    ]))

(defn count-nice-letters [input]
  (->> input
       s/split-lines
       (map is-string-nice?)
       (filter true?)
       count
       ))

;; First Solution
(count-nice-letters letter-input)

;; New Rules

(defn get-letter-pairs [sx]
  (let [piece-meal
        (concat (partition 2 sx) 
                (->> (rest sx) (partition 2)))]
    (filter #(= (count %) 2) piece-meal)))

(defn has-same-letter-pairs? [sx]
  (->> (get-letter-pairs sx)
       (mapv #(apply str %))
       (apply distinct?)
       not
   ))

(defn letters-with-overlap? [sx]
  (->> (re-seq #"([a-z]).\1" sx)
       empty?
       not))

(defn convert-3overlap->2overlap [sx]
  (s/replace sx #"([a-z])\1\1+" "$1$1"))

(convert-3overlap->2overlap "tessst")

(defn is-string-still-nice? [sx]
  (let [sx (convert-3overlap->2overlap sx)]
    (every? true?
            [(has-same-letter-pairs? sx)
             (letters-with-overlap? sx)
             ])))

(defn count-more-nice-letters [input]
  (->> input
       s/split-lines
       (map is-string-still-nice?)
       (filter true?)
       count
       ))

;; Second Solution
(count-more-nice-letters letter-input)
