
(defn val-to-idx
  "Takes a seq and turns it into a map where value -> idx"
  [v]
  (->> v
       (map-indexed #(hash-map %2 %1))
       (apply conj {})))

(defn intersect
  "Given two arrays A and B, returns the indices at which the two arrays 
  intersect. If the two arrays have no intersection at all, returns nil."
  [a b]
  (let [idx-a (map-indexed list a)
        b-map (val-to-idx b)]
    (loop [arr idx-a]
      (let [[idx a-val] (first arr)
            b-val (get b-map a-val)]
        (cond
          (empty? arr) nil
          (some? b-val) [idx b-val]
          :else (recur (rest arr)))))))



(comment

  (map-indexed list [10 20 30 40])
  ; => ((0 10) (1 20) (2 30) (3 40))

  (val-to-idx [10 20 30 40])
  ; => {10 0, 20 1, 30 2, 40 3}

  (intersect [2 3 4] [1 5 6])
  ; => nil

  (intersect [1 4 5 6] [2 3 4 5 6])
  ; => [1 2]
  )