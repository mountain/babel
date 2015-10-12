(ns babel.tlang)

(defn e
  ([x]
     (condp = (type x)
       java.lang.Long
       []
       babel.B
       (case (.getArity x)
         0 [(x)]
         1 [(x 0)]
         2 [(x 0 0)]
         3 [(x 0 0 0)]
         4 [(x 0 0 0 0)])
       clojure.lang.AFn
       (x)
       clojure.lang.PersistentList
       x
       clojure.lang.PersistentVector
       x
       []))
  ([x a]
     (condp = (type x)
       java.lang.Long
       (vec (map #(if (> % 0) 1 0) a))
       babel.B
       (case (.getArity x)
         0 [(x)]
         1 [(x a)]
         2 [(x a 0)]
         3 [(x a 0 0)]
         4 [(x a 0 0 0)])
       clojure.lang.AFn
       (x a)
       clojure.lang.PersistentList
       [(if (> (reduce * (map #(reduce * (apply e (conj [%] a))) x)) 0) 1 0)]
       clojure.lang.PersistentVector
       [(if (> (reduce + (map #(reduce + (apply e (conj [%] a))) x)) 0) 1 0)]
       [a]))
  ([x a b]
   (condp = (type x)
     java.lang.Long
     [(if (> a 0) 1 0) (if (> b 0) 1 0)]
     babel.B
     (case (.getArity x)
       0 [(x)]
       1 [(x a)]
       2 [(x a b)]
       3 [(x a b 0)]
       4 [(x a b 0 0)])
     clojure.lang.AFn
     (x a b)
     clojure.lang.PersistentList
     [(if (> (reduce * (map #(reduce * (apply e (conj [%] [a b]))) x)) 0) 1 0)]
     clojure.lang.PersistentVector
     [(if (> (reduce + (map #(reduce + (apply e (conj [%] [a b]))) x)) 0) 1 0)]
     [a b]))
  ([x a b c]
   (condp = (type x)
     java.lang.Long
     [(if (> a 0) 1 0) (if (> b 0) 1 0) (if (> c 0) 1 0)]
     babel.B
     (case (.getArity x)
       0 [(x)]
       1 [(x a)]
       2 [(x a b)]
       3 [(x a b c)]
       4 [(x a b c 0)])
     clojure.lang.AFn
     (x a b c)
     clojure.lang.PersistentList
     [(if (> (reduce * (map #(reduce * (apply e (conj [%] [a b c]))) x)) 0) 1 0)]
     clojure.lang.PersistentVector
     [(if (> (reduce + (map #(reduce + (apply e (conj [%] [a b c]))) x)) 0) 1 0)]
     [a b c]))
  ([x a b c d]
   (condp = (type x)
     java.lang.Long
     [(if (> a 0) 1 0) (if (> b 0) 1 0) (if (> c 0) 1 0) (if (> d 0) 1 0)]
     babel.B
     (case (.getArity x)
       0 [(x)]
       1 [(x a)]
       2 [(x a b)]
       3 [(x a b c)]
       4 [(x a b c d)])
     clojure.lang.AFn
     (x a b c d)
     clojure.lang.PersistentList
     [(if (> (reduce * (map #(reduce * (apply e (conj [%] [a b c d]))) x)) 0) 1 0)]
     clojure.lang.PersistentVector
     [(if (> (reduce + (map #(reduce + (apply e (conj [%] [a b c d]))) x)) 0) 1 0)]
     [a b c d])))

(defn evaluate [x]
  (case (.getArity x)
    0 (fn ([] (e x)) ([a] (e x)) ([a b] (e x)) ([a b c] (e x)) ([a b c d] (e x)))
    1 (fn ([] (e x)) ([a] (e x a)) ([a b] (e x a)) ([a b c] (e x a)) ([a b c d] (e x a)))
    2 (fn ([] (e x)) ([a] (e x a)) ([a b] (e x a b)) ([a b c] (e x a b)) ([a b c d] (e x a b)))
    3 (fn ([] (e x)) ([a] (e x a)) ([a b] (e x a b)) ([a b c] (e x a b c)) ([a b c d] (e x a b c)))
    4 (fn ([] (e x)) ([a] (e x a)) ([a b] (e x a b)) ([a b c] (e x a b c)) ([a b c d] (e x a b c d)))))

(defn any [a b]
  (list a b))

(defn all [a b]
  (vector a b))

(defn ap1 [f a]
  (e f a))

(defn ap2 [f a b]
  (e f a b))

(defn ap3 [f a b c]
  (e f a b c))

(defn ap4 [f a b c d]
  (e f a b c d))
