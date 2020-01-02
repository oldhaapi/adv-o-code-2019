(ns day6.core
  (:require [clojure.string :as str]))

;; this is from a solution found on reddit

(defn format-input [input]
  (->> (str/split (str/trim input) #"\n")
       (map str/trim)
       (map #(str/split % #"\)"))))

(defn connected-nodes [n edges]
  (->> (filter (fn [[x _]] (= x n)) edges)
       (map second)))

(defn build-graph
  ([edges] (build-graph edges "COM"))
  ([edges start-node]
   (when-let [nodes (connected-nodes start-node edges)]
     [start-node  (mapv (partial build-graph edges) nodes)])))

(def input (slurp "orbits.txt"))

(defn solve-1 []
  (->> input
       format-input
       build-graph
       (tree-seq second second)
       (map flatten)
       (map #(-> % count dec))
       (reduce +)))

(defn length-of-longest-path-containing-a-and-not-b [a b paths]
  (->> (map set paths)
       (filter #(% a))
       (remove #(% b))
       (sort-by >)
       count
       dec))

(defn distance-between [node-a node-b tree-s]
  (let [flat-paths (map flatten tree-s)]
    (+ (length-of-longest-path-containing-a-and-not-b
        node-a node-b flat-paths)
       (length-of-longest-path-containing-a-and-not-b
        node-b node-a flat-paths))))

(defn solve-2 []
  (->> input
       format-input
       build-graph
       (tree-seq second second)
       (distance-between "YOU" "SAN")))


(defn -main []
  (println "Solve-1:" (solve-1))
  (println "Solve-2:" (solve-2)))

