(ns day5.core
  (:gen-class))

;;; Puzzle input tape
(def ga-tape [3,225,1,225,6,6,1100,1,238,225,104,0,1002,92,42,224,1001,224,-3444,224,4,224,102,8,223,223,101,4,224,224,1,224,223,223,1102,24,81,225,1101,89,36,224,101,-125,224,224,4,224,102,8,223,223,101,5,224,224,1,224,223,223,2,118,191,224,101,-880,224,224,4,224,1002,223,8,223,1001,224,7,224,1,224,223,223,1102,68,94,225,1101,85,91,225,1102,91,82,225,1102,85,77,224,101,-6545,224,224,4,224,1002,223,8,223,101,7,224,224,1,223,224,223,1101,84,20,225,102,41,36,224,101,-3321,224,224,4,224,1002,223,8,223,101,7,224,224,1,223,224,223,1,188,88,224,101,-183,224,224,4,224,1002,223,8,223,1001,224,7,224,1,224,223,223,1001,84,43,224,1001,224,-137,224,4,224,102,8,223,223,101,4,224,224,1,224,223,223,1102,71,92,225,1101,44,50,225,1102,29,47,225,101,7,195,224,101,-36,224,224,4,224,102,8,223,223,101,6,224,224,1,223,224,223,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,107,677,677,224,1002,223,2,223,1006,224,329,1001,223,1,223,1108,226,677,224,102,2,223,223,1006,224,344,101,1,223,223,1107,226,226,224,1002,223,2,223,1006,224,359,101,1,223,223,8,677,226,224,1002,223,2,223,1006,224,374,1001,223,1,223,1107,677,226,224,102,2,223,223,1005,224,389,1001,223,1,223,1008,677,677,224,1002,223,2,223,1006,224,404,1001,223,1,223,108,677,677,224,102,2,223,223,1005,224,419,1001,223,1,223,1107,226,677,224,102,2,223,223,1006,224,434,101,1,223,223,1008,226,226,224,1002,223,2,223,1006,224,449,1001,223,1,223,107,226,226,224,102,2,223,223,1006,224,464,1001,223,1,223,1007,677,226,224,1002,223,2,223,1006,224,479,1001,223,1,223,1108,226,226,224,102,2,223,223,1006,224,494,1001,223,1,223,8,226,226,224,1002,223,2,223,1005,224,509,1001,223,1,223,7,226,677,224,102,2,223,223,1005,224,524,101,1,223,223,1008,677,226,224,102,2,223,223,1005,224,539,101,1,223,223,107,226,677,224,1002,223,2,223,1006,224,554,1001,223,1,223,1108,677,226,224,102,2,223,223,1005,224,569,101,1,223,223,108,226,226,224,1002,223,2,223,1005,224,584,1001,223,1,223,7,677,226,224,1002,223,2,223,1005,224,599,1001,223,1,223,108,226,677,224,1002,223,2,223,1006,224,614,101,1,223,223,1007,677,677,224,1002,223,2,223,1006,224,629,101,1,223,223,7,677,677,224,102,2,223,223,1005,224,644,101,1,223,223,1007,226,226,224,1002,223,2,223,1006,224,659,1001,223,1,223,8,226,677,224,102,2,223,223,1005,224,674,1001,223,1,223,4,223,99,226])

                                        ; Opcodes
(def ADD 1)
(def MUL 2)
(def INP  3)
(def PRN 4)
(def BRT 5)
(def BRF 6)
(def LT  7)
(def EQL 8)
(def HLT 99)
(defn opint-to-opkw [op-with-modes]
  (let [op (mod op-with-modes 100)]
    (cond
      (= op ADD) :ADD
      (= op MUL) :MUL
      (= op INP) :INP
      (= op PRN) :PRN
      (= op BRT) :BRT
      (= op BRF) :BRF
      (= op LT)  :LT
      (= op EQL) :EQL
      (= op HLT) :HLT)))

(def MAXOPINTSZ 4
  ;; opcode + number of parameters
  )
(def op-map
  {;kw  [func params]
   :ADD [  +    3]
   :MUL [  *    3]
   :INP [(fn [tape pos]
           (aset tape pos (Integer/parseInt (read-line)))) 1]
   :PRN [(fn [tape pos] (println "OUTPUT:" (aget tape pos))) 1]
   :BRT [nil    2]
   :BRF [nil    2]
   :LT  [(fn [x y] (if (< x y) 1 0))    3]
   :EQL [(fn [x y] (if (= x y) 1 0))    3]
   :HLT [ nil   0]
   }
  )

                                        ; Modes
(def POSMODE 0)
(def IMMMODE 1)

(defn imm1 [code]
  "true if value of 100th's digit is non-zero"
  (not (zero? (quot (mod code 1000) 100))))

(defn imm2 [code]
  (not (zero? (quot (mod code 10000) 1000))))

(defn imm3 [code]
  (not (zero? (quot (mod code 100000) 10000))))

(defn imm [code]
  "Return sequence of immediate mode bits from the code"
  (sequence [(imm1 code) (imm2 code) (imm3 code)]))

(defn iorp
  "Return immediate value or position value from tape"
  [tape imm? x]
  (if imm?
    x
    (aget tape x)))

(defn tape-input-func
  "Return a function that returns the next code from the tape"
  []
  (let [ip (atom 0)]
    (fn [tape]
      (let [_ (println "IP is" @ip)
            code (aget tape @ip)
            opkw (opint-to-opkw code)
            _ (println "OP:" opkw)
            opmap (opkw op-map)
            oplen (second opmap)
            params (take oplen (nthrest tape (+ 1 @ip)))
            immbits (imm code)]
        (swap! ip + (+ 1 oplen))   ; skip the opcode and params
        ;; Handle branching right here
        (if (and (= opkw :BRT) (not (zero? (iorp tape (first immbits) (first params)))))
          (reset! ip (iorp tape (second immbits) (second params))))
        (if (and (= opkw :BRF) (zero? (iorp tape (first immbits) (first params))))
          (reset! ip (iorp tape (second immbits) (second params))))
        [code opmap params immbits]))))

(defn doop
  "Doobie-doobie doo whop doo whop"
  [tape [code [f oplen] params immbits]]
  (let [opkw (opint-to-opkw code)]
    (cond
      ;; HLT
      (= opkw :HLT) nil
      ;; PRN or LD, no immediate mode
      (or (= opkw :PRN) (= opkw :INP)) (f tape (first params))
      ;; No two-param ops
      ;; + or *, get two imm or pos values, store in 3rd pos
      (or (= opkw :ADD)
          (= opkw :MUL)
          (= opkw :LT)
          (= opkw :EQL))
      (let [p1 (iorp tape (first immbits) (first params))
            p2 (iorp tape (second immbits) (second params))
            pos (first (nthrest params 2))
            _ (println f)
            _ (println "p1:" p1 "p2:" p2 "pos:" pos)]
        (aset tape pos (f p1 p2))))))

(defn intcode [src-tape]
  (let [tape (int-array src-tape)
        nxtcode (tape-input-func)]
    (loop [code-ex (nxtcode tape)]
      (let [code (first code-ex)
            opkw (opint-to-opkw code)
            _ (println "code: " code "opkw:" opkw)]
        (if (or (nil? code) (= :HLT opkw))
          (println "\nDone!")
          (do
            (doop tape code-ex)
            (recur (nxtcode tape))))))))

(defn -main
  "New intcode diagnostics, values 1 and 5"
  [& args]
  (intcode ga-tape))
