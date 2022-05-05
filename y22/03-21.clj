(defn to-min
  "Convert 'hh:mm' format to minutes-since-midnight"
  [hhmm]
  (+ (* 60 (. Integer parseInt (subs hhmm 0 2)))
     (. Integer parseInt (subs hhmm 3))))


(defn smallest-interval
  "Given a list of times in a 24-hour period, return the smallest interval between two times in the list."
  [times]
  (->> times
       (map to-min)
       (sort)
       (partition 2 1)
       (map #(- (second %) (first %)))
       (apply min)))

(comment

  (to-min "01:00")
  (to-min "01:10")

  (smallest-interval ["01:34" "02:10" "03:30" "00:35"])
  (smallest-interval ["01:00" "08:15" "11:30" "13:45" "14:10" "20:05"]))