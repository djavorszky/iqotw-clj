;; Interview question of the week:
;; Given a string that represents items as asterisk (*) and compartment walls 
;; as pipes (|), a start index, and an end index, return the number of items 
;; in a closed compartment"
;; ---
;; Solution is simple:
;; 1. Guard against edge cases (when start >= end, or end > length of string)
;; 2. Take the substring of the input
;; 3. Drop all * characters at the start
;; 4. Reverse the string
;; 5. Drop all * characters at the new start
;; 6. Filter out everything _except_ the * characters
;; 7. Return the length of the resulting string


(defn *? [c]
  "Returns true if the passed in `c` is the litera asterisk (`*`), false otherwise"
  (= c \*))

(defn contained-items [string start end]
  "Given a string that represents items as asterisk (*) and compartment walls 
   as pipes (|), a start index, and an end index, return the number of items 
   in a closed compartment"

  (if (or (>= start end) (> end (count string))) 0
      (->> (subs string start end)
           (drop-while *?)
           (reverse)
           (drop-while *?)
           (filter *?)
           (count))))


(use 'clojure.test)

(deftest interview-test
  (testing "happy paths"
    (is (= (contained-items "|**|*|*" 0 5) 2))
    (is (= (contained-items "|**|*|*" 0 6) 3))
    (is (= (contained-items "|**|*|*" 1 7) 1))
    (is (= (contained-items "**||*|**" 0 6) 1)))

  (testing "edge cases"
    (is (= (contained-items "" 0 2) 0))
    (is (= (contained-items "**" 0 2) 0))
    (is (= (contained-items "|" 0 1) 0))
    (is (= (contained-items "|**|" 1 0) 0))))


(comment
  (run-tests)
  (contained-items "|**|*|*" 0 5))