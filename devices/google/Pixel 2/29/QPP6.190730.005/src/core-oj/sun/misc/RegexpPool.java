/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.PrintStream;
import sun.misc.REException;
import sun.misc.RegexpNode;
import sun.misc.RegexpTarget;

public class RegexpPool {
    private static final int BIG = Integer.MAX_VALUE;
    private int lastDepth = Integer.MAX_VALUE;
    private RegexpNode prefixMachine = new RegexpNode();
    private RegexpNode suffixMachine = new RegexpNode();

    private void add(String string, Object object, boolean bl) throws REException {
        RegexpNode regexpNode;
        int n = string.length();
        if (string.charAt(0) == '*') {
            RegexpNode regexpNode2 = this.suffixMachine;
            do {
                regexpNode = regexpNode2;
                if (n > 1) {
                    regexpNode2 = regexpNode2.add(string.charAt(--n));
                    continue;
                }
                break;
            } while (true);
        } else {
            boolean bl2 = false;
            if (string.charAt(n - 1) == '*') {
                --n;
            } else {
                bl2 = true;
            }
            RegexpNode regexpNode3 = this.prefixMachine;
            for (int i = 0; i < n; ++i) {
                regexpNode3 = regexpNode3.add(string.charAt(i));
            }
            regexpNode3.exact = bl2;
            regexpNode = regexpNode3;
        }
        if (regexpNode.result != null && !bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" is a duplicate");
            throw new REException(((StringBuilder)object).toString());
        }
        regexpNode.re = string;
        regexpNode.result = object;
    }

    private Object matchAfter(String string, int n) {
        int n2;
        Object object;
        Object object2;
        int n3;
        Object object3 = object2 = this.prefixMachine;
        int n4 = 0;
        int n5 = 0;
        int n6 = string.length();
        if (n6 <= 0) {
            return null;
        }
        int n7 = 0;
        do {
            block10 : {
                block11 : {
                    object = object3;
                    n2 = n4;
                    n3 = n5;
                    if (object2 == null) break;
                    object = object3;
                    n2 = n4;
                    n3 = n5;
                    if (((RegexpNode)object2).result == null) break block10;
                    object = object3;
                    n2 = n4;
                    n3 = n5;
                    if (((RegexpNode)object2).depth >= n) break block10;
                    if (!((RegexpNode)object2).exact) break block11;
                    object = object3;
                    n2 = n4;
                    n3 = n5;
                    if (n7 != n6) break block10;
                }
                this.lastDepth = ((RegexpNode)object2).depth;
                object = object2;
                n2 = n7;
                n3 = n6;
            }
            if (n7 >= n6) break;
            object2 = ((RegexpNode)object2).find(string.charAt(n7));
            ++n7;
            object3 = object;
            n4 = n2;
            n5 = n3;
        } while (true);
        n7 = n6;
        for (object2 = this.suffixMachine; (n4 = n7 - 1) >= 0 && object2 != null; object2 = object2.find((char)string.charAt((int)n4))) {
            object3 = object;
            n5 = n2;
            n7 = n3;
            if (((RegexpNode)object2).result != null) {
                object3 = object;
                n5 = n2;
                n7 = n3;
                if (((RegexpNode)object2).depth < n) {
                    this.lastDepth = ((RegexpNode)object2).depth;
                    object3 = object2;
                    n5 = 0;
                    n7 = n4 + 1;
                }
            }
            object = object3;
            n2 = n5;
            n3 = n7;
            n7 = n4;
        }
        object2 = object3 = ((RegexpNode)object).result;
        if (object3 != null) {
            object2 = object3;
            if (object3 instanceof RegexpTarget) {
                object2 = ((RegexpTarget)object3).found(string.substring(n2, n3));
            }
        }
        return object2;
    }

    public void add(String string, Object object) throws REException {
        this.add(string, object, false);
    }

    public Object delete(String string) {
        Object object;
        RegexpNode regexpNode;
        int n;
        int n2;
        Object object2;
        int n3;
        Object var2_2;
        block18 : {
            block17 : {
                var2_2 = null;
                object = object2 = this.prefixMachine;
                n = string.length() - 1;
                n2 = 1;
                if (!string.startsWith("*")) break block17;
                n3 = n;
                if (string.endsWith("*")) break block18;
            }
            n3 = n + 1;
        }
        if (n3 <= 0) {
            return null;
        }
        n = 0;
        do {
            block19 : {
                block20 : {
                    regexpNode = object;
                    if (object2 == null) break;
                    regexpNode = object;
                    if (((RegexpNode)object2).result == null) break block19;
                    regexpNode = object;
                    if (((RegexpNode)object2).depth >= Integer.MAX_VALUE) break block19;
                    if (!((RegexpNode)object2).exact) break block20;
                    regexpNode = object;
                    if (n != n3) break block19;
                }
                regexpNode = object2;
            }
            if (n >= n3) break;
            object2 = ((RegexpNode)object2).find(string.charAt(n));
            ++n;
            object = regexpNode;
        } while (true);
        n = n3;
        n3 = n2;
        for (object2 = this.suffixMachine; (n2 = n - 1) >= 0 && object2 != null; object2 = object2.find((char)string.charAt((int)n2))) {
            object = regexpNode;
            n = n3;
            if (((RegexpNode)object2).result != null) {
                object = regexpNode;
                n = n3;
                if (((RegexpNode)object2).depth < Integer.MAX_VALUE) {
                    n = 0;
                    object = object2;
                }
            }
            regexpNode = object;
            n3 = n;
            n = n2;
        }
        if (n3 != 0) {
            object2 = var2_2;
            if (string.equals(regexpNode.re)) {
                object2 = regexpNode.result;
                regexpNode.result = null;
            }
        } else {
            object2 = var2_2;
            if (string.equals(regexpNode.re)) {
                object2 = regexpNode.result;
                regexpNode.result = null;
            }
        }
        return object2;
    }

    public Object match(String string) {
        return this.matchAfter(string, Integer.MAX_VALUE);
    }

    public Object matchNext(String string) {
        return this.matchAfter(string, this.lastDepth);
    }

    public void print(PrintStream printStream) {
        printStream.print("Regexp pool:\n");
        if (this.suffixMachine.firstchild != null) {
            printStream.print(" Suffix machine: ");
            this.suffixMachine.firstchild.print(printStream);
            printStream.print("\n");
        }
        if (this.prefixMachine.firstchild != null) {
            printStream.print(" Prefix machine: ");
            this.prefixMachine.firstchild.print(printStream);
            printStream.print("\n");
        }
    }

    public void replace(String string, Object object) {
        try {
            this.add(string, object, true);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void reset() {
        this.lastDepth = Integer.MAX_VALUE;
    }
}

