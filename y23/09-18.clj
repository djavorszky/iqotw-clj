; First, define the "partial sum" function (see wikipedia: https://en.wikipedia.org/wiki/1_%2B_2_%2B_3_%2B_4_%2B_%E2%8B%AF)
; This tells us how many blocks there are in a given "level".
; #    => partial-sum says 1
; ##   => partial-sum says 3
; ###  => partial-sum says 6
; #### => partial-sum says 10
(defn partial-sum [n]
  (/ (* n (+ n 1)) 2))
  
; Then loop over levels starting from one and check to see
; if the number that was passed in is less than the partial sum
; of the given level.
; If it is less or equal, then we increase the level and try again
; If it is greater than the number, we return the previous level
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