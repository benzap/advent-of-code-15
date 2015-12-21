(ns advent-of-code-15.advent-1)

(def level-input (slurp "./resources/advent_1.in"))

(defn parse-bracket [val]
  (case val
    \( 1
    \) -1))

(defn get-floor [input]
  (->> input
   seq
   (map parse-bracket)
   (apply +)
   ))

;; First Solution
;; (get-floor level-input)

(defn get-first-basement-position [input]
  (loop [level 0
         position 0
         input-vec (into [] input)]
    (let [level (+ level (parse-bracket (first input-vec)))
          position (inc position)]
      (if (< level 0)
        position
        (recur level position (rest input-vec))
        ))))

;; Second Solution
;; (get-first-basement-position level-input)
