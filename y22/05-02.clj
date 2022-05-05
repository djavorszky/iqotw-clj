(use '[clojure.string :as str])

(defn simple-sac
  "Simple autocomplete. Returns all items from words for which
   's' is a partial match"
  [words s]
  (filter #(str/includes? % s) words))

(defn faster-sac
  "Simple autocomplete. Returns all items from words for which
   's' is a partial match. Does it faster because transducer."
  [words s]
  (transduce (comp (filter #(str/includes? % s))) conj words))

(comment
  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
      ;; Testing the simple autocomplete
      ;;
      ;; Going to read in all the words from Ubuntu's wbritish
      ;; package
  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

  (def words
    (->> (slurp "/usr/share/dict/words")
         (str/split-lines)
         (map lower-case)))

  (simple-sac words "berry")
  (faster-sac words "berry")

  ;; Benchmarking
  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

  (use '[criterium.core])

  (bench (doall (simple-sac words "berry")))

  (bench (faster-sac words "berry"))
  )

