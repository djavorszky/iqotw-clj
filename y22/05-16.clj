(defn maximized-array
  "Given two integer arrays of size n, return a new array of 
   size n such that n consists of only unique elements and the
   sum of all its elements is maximum."
  [arr1 arr2]

  ;; Get the N largest numbers from concatenated arr2 and arr1,
  ;; and turn it into a set.
  (let [max-sum (->> (concat arr2 arr1)
                     (distinct)
                     (sort >)
                     (take (count arr1))
                     (into #{}))]

    ;; Iterate through arr2 and arr1 again, but this time keep
    ;; only the numbers that are also in max-sum. Apply distinct
    ;; on it again, and take the first N numbers.
    ;; This way we get to keep the original order from the arrays.
    (->> (concat arr2 arr1)
         (filter #(contains? max-sum %))
         (distinct)
         (take (count arr1)))))

(comment
  ;;; Testing bunch of stuff ;;;

  (maximized-array [1 2 3 4 2] [5 1 2 4 9])

  (def arr1 [7, 4, 10, 0, 1])
  (def arr2 [9, 7, 2, 3, 6])

  (maximized-array arr1 arr2)

  (concat arr1 arr2)

  ;;; Benchmarking ;;;
  (use '[criterium.core])

  (bench (doall (maximized-array arr1 arr2))))