(ns advent2018.day2)

(defn read-lines [filename]
  (->  filename slurp (clojure.string/split #"\n")))

(defn advent-3 []
  (->> (read-lines "input/input2.txt")
       (map frequencies)
       (map vals)
       (map set)
       (reduce (fn [[i j] vs]
                 [(if (contains? vs 2) (inc i) i)
                  (if (contains? vs 3) (inc j) j)])
               [0 0])
       (apply *)))

#_(advent-3)
