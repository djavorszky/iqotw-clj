(ns utils)


(defn idx-to-point
  "Given a one-dimensional array, returns a [row col] vector pointing
   to a location that the index would have were the array 2 dimensional
   with `cols` width."
  [cols idx]
  [(-> (/ idx cols) Math/floor int)
   (mod idx cols)])

(defn point-to-idx
  "Given a 2 dimensional array with `cols` width, and a [col row] vector, 
   calculates the index of the same point were the 2d array flattened to 
   one dimension"
  [cols [i j]]
  (+ (* i cols) j))



(comment
  ;;; Testing bunch of stuff ;;;

  (point-to-idx 2 [2 5])

  (idx-to-point 2 5)

  ;;; Actual tests ;;;

  (use 'clojure.test)

  (run-tests)

  (deftest test-utils

    (testing "point-to-idx"
      (is (= (point-to-idx 1 [1 1]) 2))
      (is (= (point-to-idx 2 [0 1]) 1))
      (is (= (point-to-idx 2 [1 0]) 2))
      (is (= (point-to-idx 2 [1 1]) 3)))


    (testing "idx-to-point"
      (is (= (idx-to-point 5 10) [2 0]))
      (is (= (idx-to-point 5 0) [0 0]))
      (is (= (idx-to-point 5 1) [0 1]))
      (is (= (idx-to-point 5 6) [1 1]))
      (is (= (idx-to-point 3 4) [1 1]))))


  ;;; Benchmarking ;;;
  (require '[criterium.core :as b])

  (b/bench (point-to-idx 300 [150 241]))

  (b/bench (idx-to-point 300 16943)))