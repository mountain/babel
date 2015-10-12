package babel;

import clojure.java.api.Clojure;
import clojure.lang.AFn;
import clojure.lang.IFn;

public class B extends AFn {

    public static class Constructor extends AFn {
        @Override
        public Object invoke(Object o1) {
            if (o1 instanceof Integer) {
                return new B(((Integer) o1).intValue());
            } else if (o1 instanceof Long) {
                return new B(((Long) o1).intValue());
            }
            return null;
        }
    }

    public static class Accessor extends AFn {
        @Override
        public Object invoke(Object o1) {
            B b1 = (B)o1;
            return b1.getArity();
        }
    }

    private static final IFn constructor = new Constructor();
    private static final IFn accessor = new Accessor();

    private static final IFn map = Clojure.var("clojure.core", "map");
    private static final IFn vector = Clojure.var("clojure.core", "vector");
    private static final IFn range = Clojure.var("clojure.core", "range");
    private static final IFn evaluate = Clojure.var("babel.tlang", "evaluate");
    private static final Object indexes = range.invoke(0, 65814);
    private static final Object functions = map.invoke(constructor, indexes);
    private static final Object wappers = map.invoke(evaluate, functions);
    private static final Object arities = map.invoke(accessor, functions);

    public static final Object declares = map.invoke(vector, wappers, arities);
    public static final Object tests = functions;

    private static final int BASE = 2;
    private static final int[] orders = new int[] {2, 4, 16, 256, 65536};
    private static final int[] totals = new int[] {2, 6, 22, 278, 65814};

    private int arity;
    private int encode;

    public B(int idx) {
        if (idx >= 278) {
            this.arity = 4;
            this.encode = idx - 278;
        } else if (idx >= 22) {
            this.arity = 3;
            this.encode = idx - 22;
        } else if (idx >= 6) {
            this.arity = 2;
            this.encode = idx - 6;
        } else if (idx >= 2) {
            this.arity = 1;
            this.encode = idx - 2;
        } else {
            this.arity = 0;
            this.encode = idx;
        }
    }

    public int getArity() {
        return this.arity;
    }

    private int pow(int e) {
        int result = 1;
        for (int i = 0; i < e; i++) {
            result *= 2;
        }
        return result;
    }

    public int calculate(int... params) {
        int pos = 0;
        for(int i = 0; i < this.arity; i++) {
            pos = params[i] + pos * BASE;
        }
        return (this.encode / pow(pos)) % BASE;
    }

    public String toString() {
        return String.format("%d#%d", this.encode, this.arity);
    }

    @Override
    public Object invoke() {
        if(this.arity == 0) {
            return calculate();
        } else {
            return this.throwArity(0);
        }
    }

    @Override
    public Object invoke(Object o1) {
        if(this.arity <= 1) {
            return calculate(((Long)o1).intValue());
        } else {
            return this.throwArity(1);
        }
    }

    @Override
    public Object invoke(Object o1, Object o2) {
        if(this.arity <= 2) {
            return calculate(((Long)o1).intValue(), ((Long)o2).intValue());
        } else {
            return this.throwArity(2);
        }
    }

    @Override
    public Object invoke(Object o1, Object o2, Object o3) {
        if(this.arity <= 3) {
            return calculate(((Long)o1).intValue(), ((Long)o2).intValue(), ((Long)o3).intValue());
        } else {
            return this.throwArity(3);
        }
    }

    @Override
    public Object invoke(Object o1, Object o2, Object o3, Object o4) {
        if(this.arity <= 4) {
            return calculate(((Long)o1).intValue(), ((Long)o2).intValue(), ((Long)o3).intValue(), ((Long)o4).intValue());
        } else {
            return this.throwArity(4);
        }
    }

}
