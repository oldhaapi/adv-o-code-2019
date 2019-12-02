(ns intcode.core
  (:gen-class))

(def ga-tape [1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,6,1,19,1,19,5,23,2,10,23,27,2,27,13,31,1,10,31,35,1,35,9,39,2,39,13,43,1,43,5,47,1,47,6,51,2,6,51,55,1,5,55,59,2,9,59,63,2,6,63,67,1,13,67,71,1,9,71,75,2,13,75,79,1,79,10,83,2,83,9,87,1,5,87,91,2,91,6,95,2,13,95,99,1,99,5,103,1,103,2,107,1,107,10,0,99,2,0,14,0])

(defn intcode [src-tape & [op1 op2]]
  "Process intcode tape input.
   Opcodes are 1 (+) 2 (*) and 99 (EOT)
   Consume 4 positions for ops + and *
   Return value of tape pos 0 at end of program
   Original state op1 and op2 are values for pos 1 and 2, if specified"
  (let [tape (int-array src-tape)]
    (if-not (nil? op1) (aset tape 1 op1))
    (if-not (nil? op2) (aset tape 2 op2))
    (loop [ip 0
           result (aget tape 0)]
      (let [[opcode & rest] (nthrest tape ip)]
        (if (= opcode 99)
          result
          (do
            (let [[p1 p2 p3] rest
                  v1 (aget tape p1)
                  v2 (aget tape p2)]
              (cond
                (= opcode 1) (aset tape p3 (+ v1 v2))
                (= opcode 2) (aset tape p3 (* v1 v2))
                :else (println "unknown opcode:" opcode)))
            (recur (+ ip 4)
                   (aget tape 0))))))))

(def goal 19690720)

(defn -main
  "Advent of code: Intcode computer
   Find initial noun/verb that produce intcode value of 19690720
   Nouns/verbs come from the range 0..99 inclusive"
  [& args]
  (loop [nouns (range 100)]
    (let [noun (first nouns)
          verb (first (filter #(= goal (intcode ga-tape noun %)) (range 100)))]
      (if (nil? verb)
        (recur (rest nouns))
        (println (+ (* 100 noun) verb))))))
