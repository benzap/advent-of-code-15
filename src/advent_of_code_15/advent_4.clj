(ns advent-of-code-15.advent-4
  (:require [digest]))

;;(digest/md5 "foo")

(def input "bgvyzdsv")

(defn has-leading-zeroes? 
  [s & {:keys [n]
        :or {n 5}}]
  (->> s (take n) (every? #(= \0 %))))

(defn get-num-for->
  [s & {:keys [num-leading]
        :or {num-leading 5}}]
  (loop [i 0]
    (let [secret (-> s (str i) (digest/md5))]
      (if-not (has-leading-zeroes? secret :n num-leading)
        (recur (inc i))
        i
        ))))

;; First Solution
;; (get-num-for-> input)
;; 254575

;; Second Solution
;; (get-num-for-> input :num-leading 6)
;; 1038736
