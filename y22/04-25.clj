
(defn merge-overlaps [ranges]
  "Given an array of intervals, merge the overlapping intervals, and return an array of the resulting intervals."
  (->> ranges
       (sort-by first)
       (reduce #(if (overlaps? (peek %1) %2)
                  (set-last %1 [(first (peek %1)) (second %2)])
                  (conj %1 %2)) [])))

(defn set-last [coll x]
  "Replaces the last item in the coll with x"
  (conj (pop coll) x))

(defn overlaps? [c1 c2]
  "Returns true if the two ranges overlap, false otherwise. Also returns false if either c1 or c2 is nil"
  (true? (and c1 c2 (>= (second c1) (first c2)))))


  (use 'clojure.test)

  (deftest test-overlaps?
    (testing "yep"
      (is (true? (overlaps? [1 2] [2 3])))
      (is (true? (overlaps? [1 3] [2 6])))
      (is (true? (overlaps? [1 5] [2 3])))
      (is (true? (overlaps? [1 2] [2 3]))))

    (testing "nope"
      (is (false? (overlaps? [1 2] [3 4])))
      (is (false? (overlaps? nil [3 4])))
    (is (false? (overlaps? [1 2] nil)))))


(deftest test-merge-overlaps
  (testing "overlaps"

    (is (= (merge-overlaps [[1 4] [2 6] [8 10] [15 20]]) [[1 6] [8 10] [15 20]]))
    (is (= (merge-overlaps [[1 8] [2 4] [4 6] [15 20]]) [[1 6] [15 20]]))
    (is (= (merge-overlaps [[1 8] [8 10] [10 11] [15 20]]) [[1 11] [15 20]]))
    (is (= (merge-overlaps [[1 2] [2 7]]) [[1 7]])))

  (testing "out-of-order"

    (is (= (merge-overlaps [[2 6] [8 10] [15 20] [1 4]]) [[1 6] [8 10] [15 20]]))
    (is (= (merge-overlaps [[1 2] [2 7]]) [[1 7]])))

  (testing "non-overlaps"
    (is (= (merge-overlaps [[1 2] [3 4]]) [[1 2] [3 4]])))
)

(comment

  (run-tests)

)