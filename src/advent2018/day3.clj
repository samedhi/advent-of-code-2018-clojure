(ns advent2018.day3)

(defn read-lines [filename]
  (->  filename
       slurp 
       (clojure.string/split #"\n")))

;; TODO: There is a probably a much more elegant/declarative way to parse this line...
(defn s->claim[s]
  (let [[id _ coords dimensions] (clojure.string/split s #" ")
        [left top] (clojure.string/split coords #",")
        [width height] (clojure.string/split dimensions #"x")]
    {:id (Integer/parseInt (subs id 1))
     :left (Integer/parseInt left)
     :top (Integer/parseInt (subs top 0 (-> top count dec)))
     :width (Integer/parseInt width)
     :height (Integer/parseInt height)}))

#_(s->claim "#1 @ 555,891: 18x12")

(defn claims []
  (->> (read-lines "input/input3.txt")
       (map s->claim)))

(defn add-claim [m {:keys [id left top width height]}]
  (reduce
   (fn [m [x y]]
     (update-in m [[x y]] (fnil conj #{}) id))
   m
   (for [i (range width) 
         j (range height)]
     [(+ left i) (+ top j)])))

;; HO, HO, HO... Sometimes the stupid solution just works... :)
(defn advent-5 []
  (->> (claims)
       (reduce add-claim {})
       vals
       (filter #(< 1 (count %)))
       count))

#_(advent-5)

(defn advent-6 []
  (->> (claims)
       (reduce add-claim {})
       vals
       (filter #(< 1 (count %)))
       (reduce into #{})
       sort
       (map-indexed (fn [i v] [(inc i) v]))
       (drop-while (fn [[i v]] (= i v)))
       ffirst))

#_(advent-6)
