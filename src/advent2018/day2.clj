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

(defn code->indexed-chars [code]
  (->> code
       (map-indexed vector)
       set))

(defn advent-4 []
  (let [codes (->> (read-lines "input/input2.txt")
                   (map #(vector % (-> % frequencies vals set)))
                   (filter #(let [s (second %)] (or (contains? s 2) (contains? s 3))))
                   (map first))]
    (first
     ;; NOTE: Will for will generate the answer twice AxB and BxA
     (for [code1 codes
           code2 codes
           :let [code1-set (code->indexed-chars code1)
                 code2-set (code->indexed-chars code2)
                 intersection (clojure.set/intersection code1-set code2-set)]
           :when (= (count code1) (count code2) (-> intersection count inc))]
       (->> (map vector code1 code2)
            (filter (fn [[a b]] (= a b)))
            (map first)
            (clojure.string/join ""))))))

#_(advent-4)
