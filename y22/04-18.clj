; Given an unsorted array of integers and a number n, 
; find the subarray of length n that has the largest sum.

(defn lsas [arr n]
  (->> (partition n 1 arr)
       (reduce max-key +)))


(comment

  (lsas [0 1 2 3 4 5 1 9 2 5] 3)
  (lsas [3 1 4 1 5 9 2 6] 3)
)