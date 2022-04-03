; Interview question taken from: 
; https://igotanoffer.com/blogs/tech/google-machine-learning-engineer-interview
; 
; We can rotate digits by 180 degrees to form new digits. When 0, 1, 6, 8, 9 are rotated 180 degrees,
; they become 0, 1, 9, 8, 6 respectively. When 2, 3, 4, 5, and 7 are rotated 180 degrees, they become invalid.
; A confusing number is a number that when rotated 180 degrees becomes a different number with each digit valid.
; (Note that the rotated number can be greater than the original number.) Given a positive integer N, 
; return the number of confusing numbers between 1 and N inclusive.

(def valid-nums #{\0 \1 \6 \8 \9})

(defn valid? [num] (.contains valid-nums num))

(defn confusing? [num] 
  "Returns true if a number is confusing; A confusing number 
   is a number that when rotated 180 degrees becomes a different 
   number with each digit valid."
  (cond
    ; If there's any invalid digits, then the number can't be confusing
    (some false? (map valid? num)) false
    ; It's only confusing if rotated number is different
    (some true? (map #(or (= \6 %) (= \9 %)) num)) true
    :else false
    )
)

(defn rotated-digits [until]
  "Returns the number of numbers that can be rotated between 0 and `until` (inclusive)."
  (->> (range 1 (+ 1 until) 2)
       (map str)
       (filter confusing?)
       (count)))

(comment
  (seq (str 123))

  (.contains valid-nums \1)

  (some (map invalid? "123456789"))

  (invalid? \1)

    (some true? (map #(or (= \6 %) (= \9 %)) "111"))

  (confusing? "1069")

  (time (rotated-digits 1456020))

  (->> (range 1 (+ 1 91) 2)
       (map str)
       (filter confusing?)
       (count)
       )

  )