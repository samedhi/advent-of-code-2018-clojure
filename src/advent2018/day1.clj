(ns advent2018.day1)

(defn read-lines [filename]
  (->  filename slurp (clojure.string/split #"\n")))

(defn advent-1
  ([] (advent-1 0))
  ([initial]
   (->> (read-lines "input/input1.txt")
        (map read-string)
        (apply + initial))))

#_(advent-1)

(defn advent-2
  ([] (advent-2 0))
  ([initial]
   (->> (read-lines "input/input2.txt")
        (map read-string)
        (reductions + initial)
        (reductions (fn [acc v] [(conj acc v)]) #{}))))

#_(advent-2)
