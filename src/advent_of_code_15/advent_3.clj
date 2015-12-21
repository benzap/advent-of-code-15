(ns advent-of-code-15.advent-3)

(def direction-input (slurp "./resources/advent_3.in"))

(defn parse-direction [c]
  (case c
    \^ [0 1]
    \> [1 0]
    \v [0 -1]
    \< [-1 0]
    ))

(defn generate-smegsmog [input]
  (let [position [0 0]
        house-map {position 1}
        ]
    (loop [next-direction (first input)
           next-input (rest input)
           current-position position
           current-map house-map]
      (let [direction (parse-direction next-direction)
            new-position (mapv + current-position direction)
            new-map (update-in current-map [new-position] (fnil inc 0))
            ]
        (if-not (empty? next-input)
          (recur
           (first next-input)
           (rest next-input)
           new-position
           new-map
           )
          new-map
        )))))

;; First Solution
;; (count (generate-smegsmog direction-input))

(let [part-meal (partition 2 direction-input)
      santa-letters (map first part-meal)
      robot-letters (map second part-meal)
      ]
  (def santa-input (apply str santa-letters))
  (def robot-input (apply str robot-letters))
  )

;; Second Solution

(let [santa-map (generate-smegsmog santa-input)
      robot-map (generate-smegsmog robot-input)]
  (count (merge santa-map robot-map))
 )
