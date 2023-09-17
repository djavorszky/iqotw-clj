
(defn sort-and-drop-zeros [arr]
  (->> arr
       sort
       (drop-while zero?)))
  
(defn seperate-and-sort
  "Given an array of integers, sort them into two separate sorted arrays of even and odd numbers. If you see a zero, skip it."
  [arr]
  (loop
   [[cur & rest] (sort-and-drop-zeros arr)
    evens []
    odds []]
    (cond
      (nil? cur) [evens odds]
      (even? cur) (recur rest (conj evens cur) odds)
      :else (recur rest evens (conj odds cur)))))
  
; Alternative solution
(defn sep-sort 
  "Alternative solution - group by whether number is even into a map and get the values"
  [arr]
  (->> arr
       sort-and-drop-zeros
       (group-by even?)
       vals))
  
  
  
  (comment
  
    (def large-arr [12 5 1 56 3 5 2 1 6 7  2 1 5 93 6 92 0 3 1 50 0 2 3 58 1 1 2 3 4 56 7 92 235  67 2 15 153 26])
    (def arr [4,3,2,1,5,7,8,9])
  
  
    (seperate-and-sort [4 3 2 1 5 7 8 9]) ; [[2 4 8] [1 3 5 7 9]]
    (seperate-and-sort [1 1 1 1]) ; [[] [1 1 1 1]]
  
    (time (dotimes [_x 1000000] (seperate-and-sort large-arr))) ; ~3.3 seconds
    (time (dotimes [_x 1000000] (sep-sort large-arr))) ; ~4.9 seconds
    (time (dotimes [_x 1000000] (seperate-and-sort arr))) ; ~469 ms
    (time (dotimes [_x 1000000] (sep-sort arr))) ; ~837 ms
  
  
    (->> arr
         sort
         (drop-while zero?)
         (group-by even?)
         vals))
  

  