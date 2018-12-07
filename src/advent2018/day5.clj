(ns advent2018.day5)

(defn read-polymer []
  (clojure.string/trim (slurp "input/input5.txt")))

#_(read-polymer)

(def s (read-polymer))

(defn combine? [x y]
  (assert (number? x) (str "x -> " x))
  (assert (number? y) (str "y -> " y))
  (-> (- x y) Math/abs (= 32)))

(defn remove-combinations [vs]
  (loop [[[a b] :as xs] (iterate rest vs)
         acc []]
    (cond
      (nil? a) acc
      (nil? b) (conj acc a)
      (combine? a b) (recur (-> xs rest rest) acc)
      :else (recur (rest xs) (conj acc a)))))

(defn collapse [vs]
  (loop [old-vs vs]
    (let [new-vs (remove-combinations old-vs)]
      (if (= old-vs new-vs)
        (->> new-vs (map char) (clojure.string/join ""))
        (recur new-vs)))))

(defn advent-9 []
  (->> (read-polymer)
       collapse
       count))

#_(advent-9)

(defn advent-10 []
  (let [vs (->> (read-polymer) (map int))]
    (->> (for [i (range 26)
               :let [j (+ i 65)
                     k (+ j 32)
                     vs (remove #{j k} vs)]]
           (-> vs collapse count))
         sort
         first)))

#_(advent-10)

