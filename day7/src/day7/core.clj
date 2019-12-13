(ns day7.core
  (:gen-class))

(def ga-tape [3,8,1001,8,10,8,105,1,0,0,21,38,55,68,93,118,199,280,361,442,99999,3,9,1002,9,2,9,101,5,9,9,102,4,9,9,4,9,99,3,9,101,3,9,9,1002,9,3,9,1001,9,4,9,4,9,99,3,9,101,4,9,9,102,3,9,9,4,9,99,3,9,102,2,9,9,101,4,9,9,102,2,9,9,1001,9,4,9,102,4,9,9,4,9,99,3,9,1002,9,2,9,1001,9,2,9,1002,9,5,9,1001,9,2,9,1002,9,4,9,4,9,99,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,99])

;
;def ga-tape [3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
;              27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5])


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
(def RELMODE 2)

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
  (cond
    (= imm? IMMMODE) x
    (= imm? RELMODE) (nth tape (+ @relbase x))
    (= imm? POSMODE) (nth tape x)
    :else (println "\nBAD MODE:" imm?)))

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
         :PRN [(fn [tape val] (println "\n  OUTPUT:" val) val) 1]
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
    (println " ->" val)
    val))

;(def tape (long-array 10000000))       ; Ten MILLION longs
(defn mktape [n src]
  (let [sz (count src)]
    (loop [i 0 v (transient [])]
      (if (< i n)
        (recur (inc i)
               (conj! v (if (< i sz)
                          (nth src i)   ; Use bigint if necessary
                          0)))          ; Use bigint if necessary
        v))))

(defn intcode [src-tape inp-seq]
  (let [input-func (in-func inp-seq)
        nxtcode (tape-input-func input-func)
        tapelen (count src-tape)
        tape (mktape 4000 src-tape)
        _ (println "\nProgram size is:" tapelen)]
    (reset! relbase 0)
    (loop [out-val 0]
      (let [code-ex (nxtcode tape)
            code (first code-ex)
            opkw (opint-to-opkw code)]
        (if (or (nil? code) (= :HLT opkw))
          out-val
          (let [res (doop tape code-ex)]
            (recur res)))))))

;; Link up Amplifiers A..E and try all possible phase settings on each
;; Phase settings are in (range 5) 0..4 and each setting number is used
;; just once.

(defn phase-settings-s
  "Return a lazy-seq from (range 43211) without any duplicate digits
   Each return is a seq of ascii numerals in 0..4"
  [bot top]
  (let [fpat (if (>= top 50000) #"[0-4]" #"[5-9]")]
    (map #(map str (format "%05d" %))
         (filter #(nil? (re-find fpat (format "%d" %)))
                 (filter #(= 5 (count (dedupe (sort (seq (format "%05d" %))))))
                         (range bot top))))))

(defn phase-settings
  [bot top]
  (for [aseq (phase-settings-s bot top)
        :let [ps (map read-string aseq)]]
    ps)
  )

(defn -main
  "Day 7 Amplifier phase/signal computation using the intcode computer"
  [& args]
  (let []
    (loop [maxthrust 0
           ampEthrust 0
           signal 0
           ps-seq (phase-settings 50000 100000)
           numsignals 0]
      (if (nil? ps-seq)
        (do
          (println "Maxthrust:" maxthrust)
          (println "Signal:" signal)
          (println "Counted" numsignals "signals"))
        (let [[ps & remaining] ps-seq
              [p1 p2 p3 p4 p5] ps
              t1 (intcode ga-tape [p1 ampEthrust])
              t2 (intcode ga-tape [p2 t1])
              t3 (intcode ga-tape [p3 t2])
              t4 (intcode ga-tape [p4 t3])
              thrust (intcode ga-tape [p5 t4])
              newthrust (if (> thrust maxthrust) thrust maxthrust)
              newsig (if (> thrust maxthrust) ps signal)]
          (recur newthrust
                 thrust
                 newsig
                 remaining
                 (inc numsignals)))))))
