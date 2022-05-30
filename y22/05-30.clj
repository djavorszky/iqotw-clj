(use '[clojure.string :as str])


;;; Write a function that determines if all the characters in a given string are unique.
;;; Can you do this without making any new variables? You choose if you want to include capitalization in your consideration for this one, as a fun challenge.

(defn inc-val
  "Increments the value of key in map if present, sets it to 1 otherwise"
  [map key]
  (->> (get map key 0)
       (inc)
       (assoc map key)))

(defn all-unique
  "Returns true if all characters in text are unique, false otherwise"
  [text]
  (->> text
       (reduce inc-val {})
       (vals)
       (every? #(= % 1))))

(defn all-unique-lower-case
  "Returns true if all characters in text are unique in a case-insensitive way, false otherwise"
  [text]
  (all-unique (str/lower-case text)))


(defn all-unique-sort
  "Returns true if all characters in text are unique, false otherwise. Uses sorting and
     windowed iteration to check that no two characters are the same"
  [text]
  (->> text
       (sort)
       (partition 2 1)
       (not-any? #(= (first %) (second %)))))

(defn all-unique-sort-lower-case
  "Returns true if all characters in text are unique in a case-insensitive way, false otherwise.
   Uses sorting and windowed iteration to check that no two characters are the same"
  [text]
  (all-unique-sort (str/lower-case text)))



(comment
  ;;; Testing bunch of stuff ;;;

  (def m {\a 10})

  (inc-val m \b)

  (->> "Cassidy"
       (reduce inc-val {})
       (vals)
       (every? #(= % 1)))

  (all-unique-sort "Cassidy")

  ;;; Actual tests ;;;

  (use 'clojure.test)

  (run-tests)

  (deftest test-helpers

    (testing "All unique"
      (let [passes ["with" "maps" "cat" "dog"]
            fails ["all" "unique" "Cassidy"]]
        (is (every? #(true? (all-unique %)) passes))
        (is (every? #(false? (all-unique %)) fails))

        (is (every? #(true? (all-unique-sort %)) passes))
        (is (every? #(false? (all-unique-sort %)) fails))))

    (testing "All lowercase unique"
      (let [passes ["With" "maps" "cat" "dog"]
            fails ["Bob" "Unique" "CaSsidy"]]
        (is (every? #(true? (all-unique-lower-case %)) passes))
        (is (every? #(false? (all-unique-lower-case %)) fails))
        (is (every? #(true? (all-unique-sort-lower-case %)) passes))
        (is (every? #(false? (all-unique-sort-lower-case %)) fails)))))

  ;;; Benchmarking ;;;
  (use '[criterium.core :as b])

  ;; Benching both versions for comparison. The string contains one duplicate character at the end (\s)

  (b/bench (all-unique "this makeplumys"))
  ;; Result on my laptop:
  ; Evaluation count : 15028980 in 60 samples of 250483 calls.
  ;           Execution time mean : 4.018521 µs
  ;  Execution time std-deviation : 73.741096 ns
  ; Execution time lower quantile : 3.892982 µs ( 2.5%)
  ; Execution time upper quantile : 4.173707 µs (97.5%)
  ;                 Overhead used : 7.142250 ns

  (b/bench (all-unique-sort "this makeplumys"))
  ;; Results:
  ; Evaluation count : 12948840 in 60 samples of 215814 calls.
  ;           Execution time mean : 4.764978 µs
  ;  Execution time std-deviation : 184.845418 ns
  ; Execution time lower quantile : 4.558544 µs ( 2.5%)
  ; Execution time upper quantile : 5.191006 µs (97.5%)
  ;                 Overhead used : 7.142250 ns

  )