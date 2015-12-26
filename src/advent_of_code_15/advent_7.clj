(ns advent-of-code-15.advent-7
  (:require [clojure.string :as s]))

(def logic-input (slurp "./resources/advent_7.in"))

(def regex-assignment #"(\d+) -> ([a-z]+)")
(def regex-and-gate #"([a-z0-9]+) AND ([a-z0-9]+) -> ([a-z]+)")
(def regex-or-gate #"([a-z0-9]+) OR ([a-z0-9]+) -> ([a-z]+)")
(def regex-lshift-gate #"([a-z0-9]+) LSHIFT (\d+) -> ([a-z]+)")
(def regex-rshift-gate #"([a-z0-9]+) RSHIFT (\d+) -> ([a-z]+)")
(def regex-not-gate #"NOT ([a-z0-9]+) -> ([a-z]+)")

(defn parse-assignment [sx]
  (when-let [[_ in out] (re-matches regex-assignment sx)]
    {:type :assignment
     :out out
     :in1 in
     }))

(defn parse-and [sx]
  (when-let [[_ in1 in2 out] (re-matches regex-and-gate sx)]
    {:type :and
     :out out
     :in1 in1
     :in2 in2
     }))

(defn parse-or [sx]
  (when-let [[_ in1 in2 out] (re-matches regex-or-gate sx)]
    {:type :or
     :out out
     :in1 in1
     :in2 in2
     }))

(defn parse-lshift [sx]
  (when-let [[_ in1 in2 out] (re-matches regex-lshift-gate sx)]
    {:type :lshift
     :out out
     :in1 in1
     :in2 in2
     }))

(defn parse-rshift [sx]
  (when-let [[_ in1 in2 out] (re-matches regex-rshift-gate sx)] 
    {:type :rshift
     :out out
     :in1 in1
     :in2 in2
     }))

(defn parse-not [sx]
  (when-let [[_ in out] (re-matches regex-not-gate sx)] 
    {:type :not
     :out out
     :in1 in
     }))
