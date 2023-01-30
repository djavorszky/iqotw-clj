(defn generate-arrays
  "Generates a list of arrays, where each element in the array goes from 1 to the index of the array."
  [n]
  (map #(range 1 (inc %)) (range 1 (inc n))))


; Example:
; (generate-arrays 4)
; => ((1) (1 2) (1 2 3) (1 2 3 4))
