/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.NFRuleSet;
import android.icu.text.RBNFPostProcessor;
import android.icu.text.RuleBasedNumberFormat;

final class RBNFChinesePostProcessor
implements RBNFPostProcessor {
    private static final String[] rulesetNames = new String[]{"%traditional", "%simplified", "%accounting", "%time"};
    private int format;
    private boolean longForm;

    RBNFChinesePostProcessor() {
    }

    @Override
    public void init(RuleBasedNumberFormat ruleBasedNumberFormat, String string) {
    }

    @Override
    public void process(StringBuilder stringBuilder, NFRuleSet object) {
        int n;
        String[] arrstring;
        int n2;
        object = ((NFRuleSet)object).getName();
        for (n2 = 0; n2 < (arrstring = rulesetNames).length; ++n2) {
            if (!arrstring[n2].equals(object)) continue;
            this.format = n2;
            boolean bl = n2 == 1 || n2 == 3;
            this.longForm = bl;
            break;
        }
        if (this.longForm) {
            n2 = stringBuilder.indexOf("*");
            while (n2 != -1) {
                stringBuilder.delete(n2, n2 + 1);
                n2 = stringBuilder.indexOf("*", n2);
            }
            return;
        }
        object = new String[][]{{"\u842c", "\u5104", "\u5146", "\u3007"}, {"\u4e07", "\u4ebf", "\u5146", "\u3007"}, {"\u842c", "\u5104", "\u5146", "\u96f6"}};
        arrstring = object[this.format];
        for (n2 = 0; n2 < arrstring.length - 1; ++n2) {
            n = stringBuilder.indexOf(arrstring[n2]);
            if (n == -1) continue;
            stringBuilder.insert(arrstring[n2].length() + n, '|');
        }
        n2 = n = stringBuilder.indexOf("\u9ede");
        if (n == -1) {
            n2 = stringBuilder.length();
        }
        int n3 = 0;
        n = -1;
        object = object[this.format][3];
        int n4 = n2;
        while (n4 >= 0) {
            int n5 = stringBuilder.lastIndexOf("|", n4);
            int n6 = stringBuilder.lastIndexOf((String)object, n4);
            n2 = 0;
            if (n6 > n5) {
                n2 = n6 > 0 && stringBuilder.charAt(n6 - 1) != '*' ? 2 : 1;
            }
            n4 = n5 - 1;
            switch (n3 * 3 + n2) {
                default: {
                    throw new IllegalStateException();
                }
                case 8: {
                    n = -1;
                    break;
                }
                case 7: {
                    stringBuilder.delete(n6 - 1, ((String)object).length() + n6);
                    n = -1;
                    n2 = 0;
                    break;
                }
                case 6: {
                    n = -1;
                    break;
                }
                case 5: {
                    stringBuilder.delete(n - 1, ((String)object).length() + n);
                    n = -1;
                    break;
                }
                case 4: {
                    stringBuilder.delete(n6 - 1, ((String)object).length() + n6);
                    n = -1;
                    n2 = 0;
                    break;
                }
                case 3: {
                    n = -1;
                    break;
                }
                case 2: {
                    n = -1;
                    break;
                }
                case 1: {
                    n = n6;
                    break;
                }
                case 0: {
                    n = -1;
                }
            }
            n3 = n2;
        }
        n2 = stringBuilder.length();
        while (--n2 >= 0) {
            n = stringBuilder.charAt(n2);
            if (n != 42 && n != 124) continue;
            stringBuilder.delete(n2, n2 + 1);
        }
    }
}

