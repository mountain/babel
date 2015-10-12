package babel;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class F2 {

    private static final int BASE = 2;
    private static final BigInteger base = BigInteger.valueOf(BASE);
    private static final Map<Integer, BigInteger> totals = new HashMap<Integer, BigInteger>();
    private static final Map<Integer, BigInteger> orders = new HashMap<Integer, BigInteger>();

    {
        int p = 1;
        BigInteger total = BigInteger.valueOf(0);
        for (int i = 0; i < 3; i++) {
            p = p * BASE;
            BigInteger order = base.pow(p);
            total = total.add(order);
            orders.put(i, order);
            totals.put(i, total);
        }
    }

    private int arity;
    private BigInteger encode;

    public F2(BigInteger raw) {
        BigInteger last = BigInteger.valueOf(0);
        for (int i = 0; i < 10; i++) {
            BigInteger total = totals.get(i);
            if (total.compareTo(raw) > 0) {
                this.arity = i;
                this.encode = raw.subtract(last);
                break;
            }
            last = total;
        }
    }

    public int calculate(int... params) {
        int pos = 0;
        for(int i = 0; i < this.arity; i++) {
            pos = params[i] + pos * BASE;
        }
        BigInteger rem = this.encode.divide(base.pow(pos)).mod(base);
        return rem.intValue();
    }

}
