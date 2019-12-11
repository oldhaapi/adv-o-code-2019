(ns day9.core
  (:gen-class))

(def sixteenbit-tape [1102,34915192,34915192,7,4,7,99,0])
(def repro-tape [109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99])
(def bignum-tape [104,1125899906842624,99])
(def ga-tape [1102,34463338,34463338,63,1007,63,34463338,63,1005,63,53,1102,3,1,1000,109,988,209,12,9,1000,209,6,209,3,203,0,1008,1000,1,63,1005,63,65,1008,1000,2,63,1005,63,904,1008,1000,0,63,1005,63,58,4,25,104,0,99,4,0,104,0,99,4,17,104,0,99,0,0,1101,0,34,1006,1101,0,689,1022,1102,27,1,1018,1102,1,38,1010,1102,1,31,1012,1101,20,0,1015,1102,1,791,1026,1102,0,1,1020,1101,24,0,1000,1101,0,682,1023,1101,788,0,1027,1101,0,37,1005,1102,21,1,1011,1102,1,28,1002,1101,0,529,1024,1101,39,0,1017,1102,30,1,1013,1101,0,23,1003,1102,524,1,1025,1101,32,0,1007,1102,25,1,1008,1101,29,0,1001,1101,33,0,1016,1101,410,0,1029,1101,419,0,1028,1101,22,0,1014,1102,26,1,1019,1102,1,35,1009,1102,36,1,1004,1102,1,1,1021,109,11,2107,22,-8,63,1005,63,199,4,187,1106,0,203,1001,64,1,64,1002,64,2,64,109,2,21108,40,40,-2,1005,1011,221,4,209,1106,0,225,1001,64,1,64,1002,64,2,64,109,13,21102,41,1,-7,1008,1019,41,63,1005,63,251,4,231,1001,64,1,64,1106,0,251,1002,64,2,64,109,-19,1202,1,1,63,1008,63,26,63,1005,63,271,1105,1,277,4,257,1001,64,1,64,1002,64,2,64,109,7,2101,0,-6,63,1008,63,24,63,1005,63,297,1106,0,303,4,283,1001,64,1,64,1002,64,2,64,109,7,1205,-1,315,1105,1,321,4,309,1001,64,1,64,1002,64,2,64,109,-11,21107,42,41,0,1005,1010,341,1001,64,1,64,1106,0,343,4,327,1002,64,2,64,109,-8,1207,6,24,63,1005,63,363,1001,64,1,64,1106,0,365,4,349,1002,64,2,64,109,11,1206,8,381,1001,64,1,64,1106,0,383,4,371,1002,64,2,64,109,4,1205,4,401,4,389,1001,64,1,64,1105,1,401,1002,64,2,64,109,14,2106,0,-3,4,407,1001,64,1,64,1106,0,419,1002,64,2,64,109,-33,1202,3,1,63,1008,63,29,63,1005,63,445,4,425,1001,64,1,64,1105,1,445,1002,64,2,64,109,-5,2102,1,7,63,1008,63,25,63,1005,63,465,1105,1,471,4,451,1001,64,1,64,1002,64,2,64,109,11,21107,43,44,7,1005,1011,489,4,477,1105,1,493,1001,64,1,64,1002,64,2,64,109,-3,1208,8,35,63,1005,63,511,4,499,1105,1,515,1001,64,1,64,1002,64,2,64,109,25,2105,1,-2,4,521,1106,0,533,1001,64,1,64,1002,64,2,64,109,-8,21108,44,47,-8,1005,1010,549,1106,0,555,4,539,1001,64,1,64,1002,64,2,64,109,-19,1207,7,35,63,1005,63,577,4,561,1001,64,1,64,1106,0,577,1002,64,2,64,109,2,2108,32,0,63,1005,63,597,1001,64,1,64,1106,0,599,4,583,1002,64,2,64,109,13,2101,0,-7,63,1008,63,32,63,1005,63,625,4,605,1001,64,1,64,1105,1,625,1002,64,2,64,109,-13,2107,24,2,63,1005,63,645,1001,64,1,64,1106,0,647,4,631,1002,64,2,64,109,18,21101,45,0,-4,1008,1015,43,63,1005,63,671,1001,64,1,64,1105,1,673,4,653,1002,64,2,64,109,-6,2105,1,10,1001,64,1,64,1105,1,691,4,679,1002,64,2,64,109,1,1208,-6,23,63,1005,63,707,1105,1,713,4,697,1001,64,1,64,1002,64,2,64,109,-2,1206,8,731,4,719,1001,64,1,64,1106,0,731,1002,64,2,64,109,-7,21102,46,1,5,1008,1010,43,63,1005,63,751,1106,0,757,4,737,1001,64,1,64,1002,64,2,64,109,-9,2108,24,4,63,1005,63,779,4,763,1001,64,1,64,1106,0,779,1002,64,2,64,109,38,2106,0,-7,1106,0,797,4,785,1001,64,1,64,1002,64,2,64,109,-27,2102,1,-6,63,1008,63,29,63,1005,63,819,4,803,1105,1,823,1001,64,1,64,1002,64,2,64,109,1,21101,47,0,7,1008,1015,47,63,1005,63,845,4,829,1105,1,849,1001,64,1,64,1002,64,2,64,109,-11,1201,5,0,63,1008,63,31,63,1005,63,869,1106,0,875,4,855,1001,64,1,64,1002,64,2,64,109,5,1201,4,0,63,1008,63,34,63,1005,63,901,4,881,1001,64,1,64,1105,1,901,4,64,99,21102,27,1,1,21101,915,0,0,1105,1,922,21201,1,58905,1,204,1,99,109,3,1207,-2,3,63,1005,63,964,21201,-2,-1,1,21101,0,942,0,1106,0,922,22101,0,1,-1,21201,-2,-3,1,21102,1,957,0,1106,0,922,22201,1,-1,-2,1106,0,968,22102,1,-2,-2,109,-3,2106,0,0])

(def day7-tape [3,8,1001,8,10,8,105,1,0,0,21,38,55,68,93,118,199,280,361,442,99999,3,9,1002,9,2,9,101,5,9,9,102,4,9,9,4,9,99,3,9,101,3,9,9,1002,9,3,9,1001,9,4,9,4,9,99,3,9,101,4,9,9,102,3,9,9,4,9,99,3,9,102,2,9,9,101,4,9,9,102,2,9,9,1001,9,4,9,102,4,9,9,4,9,99,3,9,1002,9,2,9,1001,9,2,9,1002,9,5,9,1001,9,2,9,1002,9,4,9,4,9,99,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,99])

                                        ; Opcodes
(def ADD 1)
(def MUL 2)
(def INP  3)
(def PRN 4)
(def BRT 5)
(def BRF 6)
(def LT  7)
(def EQL 8)
(def RBS 9)
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
      (= op RBS) :RBS                   ;Relative base adjustment
      (= op HLT) :HLT)))

(def MAXOPINTSZ 4
  ;; opcode + number of parameters
  )

(def inp-vec (atom [0 0]))

                                        ; Modes
(def POSMODE 0)
(def IMMMODE 1)

(defn imm1 [code]
  "Value of 100th's digit 0 1 or 2"
  (quot (mod code 1000) 100))

(defn imm2 [code]
  (quot (mod code 10000) 1000))

(defn imm3 [code]
  (quot (mod code 100000) 10000))

(defn imm [code]
  "Return sequence of immediate mode bits from the code"
  (sequence [(imm1 code) (imm2 code) (imm3 code)]))

(defn rel? [immbit]
  (= immbit 2))

(defn in-func
  "Return a function to read from an int seq or read-line from *in*"
  [& args]
  (if (or (nil? args) (not (vector? (first args))))
    #(Integer/parseInt (read-line))
    (let [c (atom 0)
          argseq (seq (first args))]
      (fn []
        (let [val (nth argseq @c)]
          (swap! c inc)
          val)))))

(def relbase (atom 0))

(defn iorp
  "Return immediate value or [relative] position value from tape"
  [tape imm? x]
  (if (= imm? 1)
    x
    (if (= imm? 2)
      (nth tape (+ @relbase x))
      (nth tape x))))

(defn tape-input-func
  "Return a function that returns the next code from the tape"
  [inp-func]
  (let [ip (atom 0)]
    (fn [tape]
      (def op-map
        {;kw  [func params]
         :ADD [  +    3]
         :MUL [  *    3]
         :INP [(fn [tape immbit param]
                 (let [v (inp-func)
                       pos (if (rel? immbit)
                             (+ param @relbase)
                             param)]
                   (assoc! tape pos v)
                   v))           1]
         :PRN [(fn [tape val] (println "\n  OUTPUT:" val)) 1]
         :BRT [nil    2]
         :BRF [nil    2]
         :LT  [(fn [x y] (if (< x y) 1 0))    3]
         :EQL [(fn [x y] (if (= x y) 1 0))    3]
         :RBS [(fn [bogus  x] (swap! relbase + x))   1]
         :HLT [ nil   0]
         }
        )
      (let [code (nth tape @ip)
            opkw (opint-to-opkw code)
            opmap (opkw op-map)
            oplen (second opmap)
            params (loop [i 0 ps []]
                     (if (< i oplen)
                       (recur (inc i)
                              (conj ps (nth tape (+ 1 @ip i))))
                       ps))
            ; params (take oplen (nthrest tape (+ 1 @ip)))
            immbits (imm code)
            _ (print (format "%3s %05d/%d" (int @ip) (int code) (int oplen)) opkw params) ]
        (swap! ip + (+ 1 oplen))   ; skip the opcode and params
        ;; Handle branching right here
        (if (and (= opkw :BRT) (not= 0N (iorp tape (first immbits) (first params))))
          (reset! ip (iorp tape (second immbits) (second params))))
        (if (and (= opkw :BRF) (= 0N (iorp tape (first immbits) (first params))))
          (reset! ip (iorp tape (second immbits) (second params))))
        [code opmap params immbits]))))

(defn doop
  "Doobie-doobie doo whop doo whop"
  [tape [code [f oplen] params immbits]]
  (let [opkw (opint-to-opkw code)
        val (cond
              ;; HLT
              (= opkw :HLT) nil
              (= opkw :INP)
              ;; One param to store it into, could be relative
              (f tape (first immbits) (first params))
              ;; PRN or RBS, one param, tape is not used by PRN
              (or (= opkw :PRN) (= opkw :RBS))
              (let [pos (iorp tape (first immbits) (first params))]
                (f tape pos))
              ;; No two-param ops
              ;; + or *, get two imm or pos values, store in 3rd pos
              (or (= opkw :ADD)
                  (= opkw :MUL)
                  (= opkw :LT)
                  (= opkw :EQL))
              (let [p1 (iorp tape (first immbits) (first params))
                    p2 (iorp tape (second immbits) (second params))
                    posval (nth params 2)
                    pos (if (rel? (nth immbits 2)) (+ @relbase posval) posval)]
                ;(println opkw "into " pos "with" p1 p2)
                (assoc! tape pos (f p1 p2))
                (nth tape pos)))]
    (println " ->" val)))

;(def tape (long-array 10000000))       ; Ten MILLION longs
(defn mktape [n src]
  (let [sz (count src)]
    (loop [i 0 v (transient [])]
      (if (< i n)
        (recur (inc i)
               (conj! v (if (< i sz)
                          (bigint (nth src i))
                          (bigint 0))))
        v))))

(defn intcode [src-tape inp-seq]
  (let [input-func (in-func inp-seq)
        nxtcode (tape-input-func input-func)
        tapelen (count src-tape)
        tape (mktape 40000000 src-tape)
        _ (println "Program size is:" tapelen)]
    (reset! relbase 0)
    (loop [code-ex (nxtcode tape)
           out-val 0]
      (let [code (first code-ex)
            opkw (opint-to-opkw code)]
        (if (or (nil? code) (= :HLT opkw))
          (do
            (println "\nDone!")
            out-val)
          (let [res (doop tape code-ex)]
            (recur (nxtcode tape)
                   res)))))))



(defn -main
  "BOOST signal detection"
  [& args]
  (intcode ga-tape [2] ))
