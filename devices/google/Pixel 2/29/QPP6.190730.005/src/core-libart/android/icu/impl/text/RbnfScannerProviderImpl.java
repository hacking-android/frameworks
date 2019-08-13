/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.text;

import android.icu.impl.ICUDebug;
import android.icu.text.CollationElementIterator;
import android.icu.text.Collator;
import android.icu.text.RbnfLenientScanner;
import android.icu.text.RbnfLenientScannerProvider;
import android.icu.text.RuleBasedCollator;
import android.icu.util.ULocale;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Deprecated
public class RbnfScannerProviderImpl
implements RbnfLenientScannerProvider {
    private static final boolean DEBUG = ICUDebug.enabled("rbnf");
    private Map<String, RbnfLenientScanner> cache = new HashMap<String, RbnfLenientScanner>();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    protected RbnfLenientScanner createScanner(ULocale object, String string) {
        try {
            RuleBasedCollator ruleBasedCollator = (RuleBasedCollator)Collator.getInstance(((ULocale)object).toLocale());
            object = ruleBasedCollator;
            if (string != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append(ruleBasedCollator.getRules());
                ((StringBuilder)object).append(string);
                string = ((StringBuilder)object).toString();
                object = new RuleBasedCollator(string);
            }
            ((RuleBasedCollator)object).setDecomposition(17);
            return new RbnfLenientScannerImpl((RuleBasedCollator)object);
        }
        catch (Exception exception) {
            if (DEBUG) {
                exception.printStackTrace();
                System.out.println("++++");
            }
            object = null;
        }
        return new RbnfLenientScannerImpl((RuleBasedCollator)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @Override
    public RbnfLenientScanner get(ULocale object, String object2) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(((ULocale)object).toString());
        charSequence.append("/");
        charSequence.append((String)object2);
        charSequence = charSequence.toString();
        Map<String, RbnfLenientScanner> map = this.cache;
        synchronized (map) {
            RbnfLenientScanner rbnfLenientScanner = this.cache.get(charSequence);
            if (rbnfLenientScanner != null) {
                return rbnfLenientScanner;
            }
        }
        object2 = this.createScanner((ULocale)object, (String)object2);
        object = this.cache;
        synchronized (object) {
            this.cache.put((String)charSequence, (RbnfLenientScanner)object2);
            return object2;
        }
    }

    private static class RbnfLenientScannerImpl
    implements RbnfLenientScanner {
        private final RuleBasedCollator collator;

        private RbnfLenientScannerImpl(RuleBasedCollator ruleBasedCollator) {
            this.collator = ruleBasedCollator;
        }

        @Override
        public boolean allIgnorable(String object) {
            object = this.collator.getCollationElementIterator((String)object);
            int n = ((CollationElementIterator)object).next();
            while (n != -1 && CollationElementIterator.primaryOrder(n) == 0) {
                n = ((CollationElementIterator)object).next();
            }
            boolean bl = n == -1;
            return bl;
        }

        @Override
        public int[] findText(String string, String string2, int n) {
            int n2 = n;
            n = 0;
            while (n2 < string.length() && n == 0) {
                n = this.prefixLength(string.substring(n2), string2);
                if (n != 0) {
                    return new int[]{n2, n};
                }
                ++n2;
            }
            return new int[]{-1, 0};
        }

        public int[] findText2(String object, String object2, int n) {
            object = this.collator.getCollationElementIterator((String)object);
            object2 = this.collator.getCollationElementIterator((String)object2);
            int n2 = -1;
            ((CollationElementIterator)object).setOffset(n);
            n = ((CollationElementIterator)object).next();
            int n3 = ((CollationElementIterator)object2).next();
            while (n3 != -1) {
                int n4;
                do {
                    n4 = n3;
                    if (n == -1) break;
                    n4 = n3;
                    if (CollationElementIterator.primaryOrder(n) != 0) break;
                    n = ((CollationElementIterator)object).next();
                } while (true);
                while (n4 != -1 && CollationElementIterator.primaryOrder(n4) == 0) {
                    n4 = ((CollationElementIterator)object2).next();
                }
                if (n == -1) {
                    return new int[]{-1, 0};
                }
                if (n4 == -1) break;
                if (CollationElementIterator.primaryOrder(n) == CollationElementIterator.primaryOrder(n4)) {
                    n2 = ((CollationElementIterator)object).getOffset();
                    n = ((CollationElementIterator)object).next();
                    n3 = ((CollationElementIterator)object2).next();
                    continue;
                }
                if (n2 != -1) {
                    n2 = -1;
                    ((CollationElementIterator)object2).reset();
                    n3 = n4;
                    continue;
                }
                n = ((CollationElementIterator)object).next();
                n3 = n4;
            }
            return new int[]{n2, ((CollationElementIterator)object).getOffset() - n2};
        }

        @Override
        public int prefixLength(String object, String object2) {
            int n;
            object = this.collator.getCollationElementIterator((String)object);
            object2 = this.collator.getCollationElementIterator((String)object2);
            int n2 = ((CollationElementIterator)object).next();
            int n3 = ((CollationElementIterator)object2).next();
            do {
                n = n2;
                if (n3 == -1) break;
                do {
                    n = n3;
                    if (CollationElementIterator.primaryOrder(n2) != 0) break;
                    n = n3;
                    if (n2 == -1) break;
                    n2 = ((CollationElementIterator)object).next();
                } while (true);
                while (CollationElementIterator.primaryOrder(n) == 0 && n != -1) {
                    n = ((CollationElementIterator)object2).next();
                }
                if (n == -1) {
                    n = n2;
                    break;
                }
                if (n2 == -1) {
                    return 0;
                }
                if (CollationElementIterator.primaryOrder(n2) != CollationElementIterator.primaryOrder(n)) {
                    return 0;
                }
                n2 = ((CollationElementIterator)object).next();
                n3 = ((CollationElementIterator)object2).next();
            } while (true);
            n2 = n3 = ((CollationElementIterator)object).getOffset();
            if (n != -1) {
                n2 = n3 - 1;
            }
            return n2;
        }
    }

}

