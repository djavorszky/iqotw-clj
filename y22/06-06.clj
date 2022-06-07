;;;  Given an int array coins and an int amount, return an array of coins 
;;;  that add up to amount (and an empty array if it’s an impossible combination).

(defn coin-combo
  "Returns combination of values from coins collection that add up to amount, 
  or empty list if no such possible combination exists"
  [coins amount]
  (loop [res []
         sum 0
         possibilities (sort > coins)]
    (cond
      (= sum amount) res
      (empty? possibilities) nil
      :else (let [c (first possibilities)
                  new-sum (+ sum c)]
              (if (<= new-sum amount)
                (recur (conj res c) new-sum possibilities)
                (recur res sum (rest possibilities)))))))
(comment
  ;;; Testing bunch of stuff ;;;

  (apply + [1 2 3 4])

  (sort > [1 2 3])

  (coin-combo [1 2 10] 34)

  ;;; Actual tests ;;;

  (require '[clojure.test :as t])

  (t/run-tests)

  (t/deftest coin-combos

    (t/testing "Empty results"
      (t/is (empty? (coin-combo [], 5)))
      (t/is (empty? (coin-combo [10], 5)))
      (t/is (empty? (coin-combo [2, 5], 6))))

    (t/testing "Good results"
      (t/is (= (coin-combo [1 2] 5) [2 2 1]))
      (t/is (= (coin-combo [1 2 3] 5) [3 2]))
      (t/is (= (coin-combo [1 2 5] 10) [5 5]))))

  ;;; Benchmarking ;;;
  (require '[criterium.core :as b])

  (b/bench (coin-combo [3 5 7] 2387))

  )
