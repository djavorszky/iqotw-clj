(use '[clojure.string :as str])


;;; Write a function that determines if all the characters in a given string are unique.
;;; Can you do this without making any new variables? You choose if you want to include capitalization in your consideration for this one, as a fun challenge.

;; So I wrote 3 (+ lower-case versions), as well as tests and benchmarks

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
  "Same as all-unique, but case-insensitive"
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
  "Same as all-unique-sort, but case-insensitive"
  [text]
  (all-unique-sort (str/lower-case text)))

(defn all-unique-loop
  "Returns true if all characters in text are unique, false otherwise.
   Does recursion, stops early if a character has already been seen."
  [text]
  
  (loop [seen #{}
         [c & others] text]
    (cond
      (get seen c) false
      (empty? others) true
      :else (recur (conj seen c) others))))

(defn all-unique-loop-lower-case
  "Same as all-unique-loop, but case-insensitive"
  [text]
  (all-unique-loop (str/lower-case text)))



(comment
  ;;; Testing bunch of stuff ;;;

  (def m {\a 10})

  (inc-val m \b)

  (empty? nil)

  (->> "Cassidy"
       (reduce inc-val {})
       (vals)
       (every? #(= % 1)))

  (all-unique-loop "Cassidy")

  ;;; Actual tests ;;;

  (require '[clojure.test :as t])

  (t/run-tests)

  (t/deftest test-helpers

    (t/testing "All unique"
      (let [passes ["with" "maps" "cat" "dog"]]
        (t/is (every? #(true? (all-unique %)) passes))
        (t/is (every? #(true? (all-unique-sort %)) passes))
        (t/is (every? #(true? (all-unique-loop %)) passes)))

      (let [fails  ["all" "unique" "Cassidy"]]
        (t/is (every? #(false? (all-unique %)) fails))
        (t/is (every? #(false? (all-unique-sort %)) fails))
        (t/is (every? #(false? (all-unique-loop %)) fails))))

    (t/testing "All lowercase unique"
      (let [passes ["With" "maps" "cat" "dog"]]
        (t/is (every? #(true? (all-unique-lower-case %)) passes))
        (t/is (every? #(true? (all-unique-sort-lower-case %)) passes))
        (t/is (every? #(true? (all-unique-loop-lower-case %)) passes)))

      (let [fails  ["Bob" "Unique" "CaSsidy"]]
        (t/is (every? #(false? (all-unique-lower-case %)) fails))
        (t/is (every? #(false? (all-unique-sort-lower-case %)) fails))
        (t/is (every? #(false? (all-unique-loop-lower-case %)) fails)))))

  ;;; Benchmarking ;;;
  (require '[criterium.core :as b])

  ;; Benching both versions for comparison.

  (defn make-test-word
    "Returns a random permutation of unique characters"
    []
    (->> (seq "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
         shuffle
         str/join))

  (def test-word "WLPRUtyjVZhHENXFfvrbkJuznBlmdKoqxIeMcwigGYATOCpDaQSs")

  (b/bench (all-unique test-word))
  ; Result: ~14.54 µs

  (b/bench (all-unique-sort test-word))
  ; Result: ~26.27 µs

  (b/bench (all-unique-loop test-word))
  ; Result: ~14.16 µs

  ; Added a singular 'k' to the middle, to check potential gain from early returns
  (def test-word-with-duplicate "WLPRUtyjVZhHENXFfvrbkJuznBlkmdKoqxIeMcwigGYATOCpDaQSs")


  (b/bench (all-unique test-word-with-duplicate))
  ; Result: ~13.05 µs

  (b/bench (all-unique-sort test-word-with-duplicate))
  ; Results: ~19.91 µs

  (b/bench (all-unique-loop test-word-with-duplicate))
  ; result: ~8.12 µs

  )
  