(ns day6.core
  (:gen-class))

(defn read-orbits
  "Read a)b strings from specified file"
  [fname]
  (println "opening:" fname)
  (with-open [rdr (clojure.java.io/reader fname)]
    (map #(seq (clojure.string/split % #"\)")) (doall (line-seq rdr)))))

(defn -main
  "Day6 - count nodes in nested orbital vectors"
  [& args]
  (let [infile (if (nil? args) "orbits.txt" (first args))
        orbits-seq-orig (read-orbits infile)
        orb-com (filter #(= "COM" (first %)) orbits-seq-orig)
        orb-seq (concat orb-com (filter #(not= "COM" (first %)) orbits-seq-orig))
        orb-tree (tree-seq seq? identity orb-seq)]
    (println "orb-com:" orb-com)
    (println "Input" (count orb-seq) "orbits")
    (if (not (nil? args)) (println "orb-seq:\n" orb-seq))
    (println (count orb-tree))
    (-> orb-tree distinct count println)
    (if (not (nil? args)) (distinct orb-tree))))
