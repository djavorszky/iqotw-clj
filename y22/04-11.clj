; Given an integer n representing the number of sides of a 
; regular polygon, return the measure of each interior angle. 
; Bonus points: implement some of the other functions listed 
; in the linked Wikipedia page! 

(defn interior-angle
  "Returns the interior angle of a regular polygon with n points"
  [n]
  (if
   (< n 1) 0
   (/ (* 180 (- n 2)) n)))
  
(defn diagonals
  "Returns the number of diagonals within a regular polygon"
  [n]
  (if
   (< n 3) 0
   (* (/ n 2) (- n 3))))

(comment

  (interior-angle 3)
  (interior-angle 8)
  (interior-angle -2)
  
  (diagonals 3)
  (diagonals 4)
  (diagonals 5)
  (diagonals 6)

  (/ 3 2)
  (* 3 (- 3 3))
  )