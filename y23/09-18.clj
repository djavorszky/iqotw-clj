(defn partial-sum [n]
  (/ (* n (+ n 1)) 2))
  

(defn build-staircases 
  "You have n equal-sized blocks and you want to build a staircase with them.
   Return the number of steps you can fully build."
  [n]
  (loop [level 1]
    (if 
     (> (partial-sum level) n) (dec level)
      (recur (inc level)))))

(comment
  (build-staircases 6) ; => 3
  (build-staircases 9) ; => 3
  (build-staircases 10) ; => 4 
  )