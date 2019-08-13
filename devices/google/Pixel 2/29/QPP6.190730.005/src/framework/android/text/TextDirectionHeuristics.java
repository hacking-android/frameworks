/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.text.TextDirectionHeuristic;
import android.text.TextUtils;
import java.nio.CharBuffer;
import java.util.Locale;

public class TextDirectionHeuristics {
    public static final TextDirectionHeuristic ANYRTL_LTR;
    public static final TextDirectionHeuristic FIRSTSTRONG_LTR;
    public static final TextDirectionHeuristic FIRSTSTRONG_RTL;
    public static final TextDirectionHeuristic LOCALE;
    public static final TextDirectionHeuristic LTR;
    public static final TextDirectionHeuristic RTL;
    private static final int STATE_FALSE = 1;
    private static final int STATE_TRUE = 0;
    private static final int STATE_UNKNOWN = 2;

    static {
        LTR = new TextDirectionHeuristicInternal(null, false);
        RTL = new TextDirectionHeuristicInternal(null, true);
        FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, false);
        FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, true);
        ANYRTL_LTR = new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false);
        LOCALE = TextDirectionHeuristicLocale.INSTANCE;
    }

    private static int isRtlCodePoint(int n) {
        byte by = Character.getDirectionality(n);
        if (by != -1) {
            if (by != 0) {
                if (by != 1 && by != 2) {
                    return 2;
                }
                return 0;
            }
            return 1;
        }
        if (1424 <= n && n <= 2303 || 64285 <= n && n <= 64975 || 65008 <= n && n <= 65023 || 65136 <= n && n <= 65279 || 67584 <= n && n <= 69631 || 124928 <= n && n <= 126975) {
            return 0;
        }
        if (!(8293 <= n && n <= 8297 || 65520 <= n && n <= 65528 || 917504 <= n && n <= 921599 || 64976 <= n && n <= 65007 || (n & 65534) == 65534 || 8352 <= n && n <= 8399 || 55296 <= n && n <= 57343)) {
            return 1;
        }
        return 2;
    }

    private static class AnyStrong
    implements TextDirectionAlgorithm {
        public static final AnyStrong INSTANCE_LTR;
        public static final AnyStrong INSTANCE_RTL;
        private final boolean mLookForRtl;

        static {
            INSTANCE_RTL = new AnyStrong(true);
            INSTANCE_LTR = new AnyStrong(false);
        }

        private AnyStrong(boolean bl) {
            this.mLookForRtl = bl;
        }

        @Override
        public int checkRtl(CharSequence charSequence, int n, int n2) {
            int n3;
            int n4 = 0;
            int n5 = 0;
            for (int i = n; i < n + n2; i += Character.charCount((int)n3)) {
                int n6;
                int n7;
                n3 = Character.codePointAt(charSequence, i);
                if (8294 <= n3 && n3 <= 8296) {
                    n7 = n5 + 1;
                    n6 = n4;
                } else if (n3 == 8297) {
                    n6 = n4;
                    n7 = n5;
                    if (n5 > 0) {
                        n7 = n5 - 1;
                        n6 = n4;
                    }
                } else {
                    n6 = n4;
                    n7 = n5;
                    if (n5 == 0) {
                        n6 = TextDirectionHeuristics.isRtlCodePoint(n3);
                        if (n6 != 0) {
                            if (n6 != 1) {
                                n6 = n4;
                                n7 = n5;
                            } else {
                                if (!this.mLookForRtl) {
                                    return 1;
                                }
                                n6 = 1;
                                n7 = n5;
                            }
                        } else {
                            if (this.mLookForRtl) {
                                return 0;
                            }
                            n6 = 1;
                            n7 = n5;
                        }
                    }
                }
                n4 = n6;
                n5 = n7;
            }
            if (n4 != 0) {
                return (int)this.mLookForRtl;
            }
            return 2;
        }
    }

    private static class FirstStrong
    implements TextDirectionAlgorithm {
        public static final FirstStrong INSTANCE = new FirstStrong();

        private FirstStrong() {
        }

        @Override
        public int checkRtl(CharSequence charSequence, int n, int n2) {
            int n3;
            int n4 = 2;
            int n5 = 0;
            for (int i = n; i < n + n2 && n4 == 2; i += Character.charCount((int)n3)) {
                int n6;
                int n7;
                n3 = Character.codePointAt(charSequence, i);
                if (8294 <= n3 && n3 <= 8296) {
                    n7 = n5 + 1;
                    n6 = n4;
                } else if (n3 == 8297) {
                    n6 = n4;
                    n7 = n5;
                    if (n5 > 0) {
                        n7 = n5 - 1;
                        n6 = n4;
                    }
                } else {
                    n6 = n4;
                    n7 = n5;
                    if (n5 == 0) {
                        n6 = TextDirectionHeuristics.isRtlCodePoint(n3);
                        n7 = n5;
                    }
                }
                n4 = n6;
                n5 = n7;
            }
            return n4;
        }
    }

    private static interface TextDirectionAlgorithm {
        public int checkRtl(CharSequence var1, int var2, int var3);
    }

    private static abstract class TextDirectionHeuristicImpl
    implements TextDirectionHeuristic {
        private final TextDirectionAlgorithm mAlgorithm;

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            this.mAlgorithm = textDirectionAlgorithm;
        }

        private boolean doCheck(CharSequence charSequence, int n, int n2) {
            if ((n = this.mAlgorithm.checkRtl(charSequence, n, n2)) != 0) {
                if (n != 1) {
                    return this.defaultIsRtl();
                }
                return false;
            }
            return true;
        }

        protected abstract boolean defaultIsRtl();

        @Override
        public boolean isRtl(CharSequence charSequence, int n, int n2) {
            if (charSequence != null && n >= 0 && n2 >= 0 && charSequence.length() - n2 >= n) {
                if (this.mAlgorithm == null) {
                    return this.defaultIsRtl();
                }
                return this.doCheck(charSequence, n, n2);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isRtl(char[] arrc, int n, int n2) {
            return this.isRtl(CharBuffer.wrap(arrc), n, n2);
        }
    }

    private static class TextDirectionHeuristicInternal
    extends TextDirectionHeuristicImpl {
        private final boolean mDefaultIsRtl;

        private TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean bl) {
            super(textDirectionAlgorithm);
            this.mDefaultIsRtl = bl;
        }

        @Override
        protected boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    private static class TextDirectionHeuristicLocale
    extends TextDirectionHeuristicImpl {
        public static final TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicLocale();

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        @Override
        protected boolean defaultIsRtl() {
            int n = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            return bl;
        }
    }

}

