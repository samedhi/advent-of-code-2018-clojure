(ns advent2018.day4)

(defn read-lines [filename]
  (->  filename slurp (clojure.string/split #"\n")))

(defn line->m [s]
  (let [[_ yyyy m dd hh mm action id] (re-find #"\[(\d+)-(\d+)-(\d+) (\d+):(\d+)\] (\S+) (\S+)" s)]
    (into {;; :year (-> yyyy Integer/parseInt)
           ;; :month (-> m Integer/parseInt)
           ;; :day (-> dd Integer/parseInt)
           :hour (-> hh Integer/parseInt)
           :minute (-> mm Integer/parseInt)
           :action (keyword action)}
          (when (= action "Guard") {:id (Integer/parseInt (subs id 1)) :action :begin}))))

#_(line->m "[1518-10-14 00:05] falls asleep")
#_(line->m "[1518-06-15 00:53] wakes up")
#_(line->m "[1518-09-01 23:56] Guard #1019 begins shift")

(defn process []
  (->> (read-lines "input/input4.txt")
       sort
       (map line->m)
       (reduce
        (fn [vs {:keys [action] :as m}]
          (if (= action :begin)
            (conj vs [m])
            (conj (pop vs)
                  (-> vs peek (conj m)))))
        [])))

#_(->> (process) (drop 0) first)

(def shifts (process))

(defn heaviest-napper [shifts]
  (->> shifts
       (reduce
        (fn [m [{:keys [id]} & ms]]
          (->> (partition 2 ms)
               (map (fn [[m1 m2]] (- (:minute m2) (:minute m1))))
               (apply +)
               (update-in m [id] (fnil + 0))))
        {})
       (sort-by second)
       last))

#_(heaviest-napper shifts)

(defn shift->nap-by-minute [[{:keys [id]} & ms]]
  (reduce
   (fn [m [m1 m2]]
     (->> (range (:minute m1) (:minute m2))
          (map #(vector % 1))
          (apply merge m)))
   {}
   (partition 2 ms)))

#_(-> shifts first shift->nap-by-minute)

(defn advent-7 []
  (let [shifts (process)
        [id] (heaviest-napper shifts)]
    (->> shifts
         (filter #(-> % first :id (= id)))
         (map shift->nap-by-minute)
         (apply merge-with +)
         (sort-by second)
         last
         first
         (* id))))

#_(advent-7)


;; Ugh, this code is not good...
(defn advent-8 []
  (let [shifts (process)
        ids (->> shifts (map first) (map :id) set)
        bests (for [id ids
                    :let [[minute c] (->> shifts
                                           (filter #(-> % first :id (= id)))
                                           (map shift->nap-by-minute)
                                           (apply merge-with +)
                                           (sort-by second)
                                           last)]]
                {:id id :minute minute :count c})
        {:keys [id minute]} (->> bests
                                 (sort-by :count)
                                 last)]
    (* minute id)))

#_(advent-8)
