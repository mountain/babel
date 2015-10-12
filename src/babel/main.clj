(ns babel.main
  (:use fungp.core)
  (:use fungp.util)
  (:use clojure.pprint)
  (:use babel.tlang)
  (:gen-class))


(defmethod print-dup babel.B [o w]
  (.write w (.toString o)))

(def functions
  (conj babel.B/declares
        [babel.tlang/any 2] [babel.tlang/all 2]
        [babel.tlang/ap1 2] [babel.tlang/ap2 3] [babel.tlang/ap3 4] [babel.tlang/ap4 5]))

(def terminals
  '[a b c d e f g h i j k l m n o p])

(def numbers
  [])

(def test-input
  [[0 0 0 0]
   [0 0 0 1]
   [0 0 1 0]
   [0 0 1 1]
   [0 1 0 0]
   [0 1 0 1]
   [0 1 1 0]
   [0 1 1 1]
   [1 0 0 0]
   [1 0 0 1]
   [1 0 1 0]
   [1 0 1 1]
   [1 1 0 0]
   [1 1 0 1]
   [1 1 1 0]
   [1 1 1 1]])

(defn make-situations []
  (map (fn [t] (map (fn [args] (apply t args))) test-input) (take 3000 (shuffle babel.B/tests))))

(defn validate [pair]
  (let [[description situation] pair
        ;tmp (println '-----------------')
        ;tmp (println description)
        ;tmp (println '-----------------')
        test-results (map #(apply e (conj [description] %)) test-input)
        eqn (reduce #(and %1 %2) (map #(= %1 %2) test-results situation))]
    (if eqn 1 0)))

(defn build-speaker [tree]
  ;(println '+++++++++++++++++')
  (let [code (list 'fn '[a b c d e f g h i j k l m n o p] tree)
        ;tmp  (println code)
        func (eval code)
        ;tmp  (println '+++++++++++++++++')
        ]
    func))

(defn speak [speaker situation]
  ;(println '=================')
  ;(println situation)
  ;(println '=================')
  (apply speaker situation))

(defn fitness [tree]
  (try
    (let [speaker      (build-speaker tree)
          situations   (make-situations)
          descriptions (map #(speak speaker %) situations)
          validations  (map validate (map vector descriptions situations))
          correctness  (reduce + validations)
          dcomplexity  (reduce + (map #(* (first %) (second %))
                                      (map vector validations (map max-tree-height descriptions))))
          scomplexity  (max-tree-height tree)
          result (- (+ dcomplexity scomplexity) correctness)]
      (println ". " result)
      result)
    (catch Throwable e (println "!") 10000000)))

(defn report
  "Reporting function. Prints out the tree and its score"
  [tree fitness]
  (pprint (list 'fn '[a b c d e f g h i j k l m n o p] tree))
  (print (str "Error:\t" fitness "\n\n"))
  (flush))

(defn -main []
  (try
    (let [options {:iterations 5 :migrations 3 :num-islands 3
                 :population-size 20
                 :max-depth 15
                 :terminals terminals
                 :numbers   numbers
                 :functions functions
                 :fitness   fitness
                 :report    report}
        result (run-genetic-programming options)
        [tree score] (rest result)]
      (do (println "Done!")
        (report tree score)))
    (catch Exception e (.printStackTrace e))))


(-main)