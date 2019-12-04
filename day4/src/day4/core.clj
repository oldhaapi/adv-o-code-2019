(ns day4.core
  (:gen-class))

(def def_rng_min 359282)
(def def_rng_max 820401)

(defn digits-increase
  "Return true if the digits do not decrease"
  [pwstr]
  (let [orig_int (Integer/parseInt pwstr)
        sorted-digit-int (sort (seq pwstr))
        new_int (Integer/parseInt (apply str sorted-digit-int))]
    (>= new_int orig_int)))

(def rxpair #"00+|11+|22+|33+|44+|55+|66+|77+|88+|99+")

(defn just-a-pair-of-digits
  "111122 is good, 123444 is not"
  [pwstr]
  (let [pairs (re-seq rxpair pwstr)]
    (and (> (count pairs) 0)
         (not (zero? (count (filter #(= (count %) 2) pairs)))))))

(defn find_pwds [pwint]
  "Return true if pwint matches the criteria"
  (let [pwstr (str pwint)]
    (and (= 6 (count (seq pwstr)))
         (digits-increase pwstr)
         (just-a-pair-of-digits pwstr)
         )))

(defn -main
  "Given a range of integers, find a number such that:
  It is a six-digit number.
  The value is within the range given in your puzzle input.
  Two adjacent digits are the same (like 22 in 122345).
  Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).

  Compute how many different six-digit passwords meet the above criteria.
  "
  [& args]
  (let [rng_min (if (nil? (first args)) def_rng_min (Integer/parseInt (first args)))
        rng_max (if (nil? (second args)) def_rng_max (Integer/parseInt (second args)))
        ppwds (filter find_pwds (range rng_min rng_max))]
    (println "finding possible password digits in the range ["rng_min","rng_max"]" )
    (println "possible password count:" (count ppwds))
    (println "First six ints are:" (take 6 ppwds))))
