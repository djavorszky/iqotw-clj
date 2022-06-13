;;; This week's question:
;;;
;;;  Create a loooong teeeext generator that takes in a string and an integer n, 
;;;  and multiplies the vowels in the string by n.
;;;

(defn vowel?
  "Returns true if c is a vowel, false otherwise"
  [c] (contains? #{\a \e \i \o \u} c))

(defn long-text 
  "Repeats each vowel in text n times"
  [text n]
  (->> text
       (map #(if (vowel? %)
               (apply str (repeat n %)) %))
       (apply str)))

(comment
  ;;; Testing bunch of stuff ;;;

  (long-text "hello" 5)

  ;;; Actual tests ;;;

  (require '[clojure.test :as t])

  (t/run-tests)

  (t/deftest question-tests

    (t/testing "long-test"
      (t/is (= (long-text "hello" 5) "heeeeellooooo"))
      (t/is (= (long-text "lol" 10) "looooooooool"))
      (t/is (= (long-text "rhythym" 5) "rhythym")))

    (t/testing "vowel?"
      (t/is (every? true? (->> "aeiou" (map vowel?))))
      (t/is (every? false? (->> "bcdfghjklmnpqrstvwxyz" (map vowel?))))))


  ;;; Benchmarking ;;;
  (require '[criterium.core :as b])

  (b/bench (long-text "This is a public service announcement" 30))
  )
