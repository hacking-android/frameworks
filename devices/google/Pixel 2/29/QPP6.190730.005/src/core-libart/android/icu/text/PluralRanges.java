/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.StandardPlural;
import android.icu.util.Freezable;
import android.icu.util.Output;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

@Deprecated
public final class PluralRanges
implements Freezable<PluralRanges>,
Comparable<PluralRanges> {
    private boolean[] explicit = new boolean[StandardPlural.COUNT];
    private volatile boolean isFrozen;
    private Matrix matrix = new Matrix();

    @Deprecated
    public void add(StandardPlural standardPlural5, StandardPlural standardPlural22, StandardPlural standardPlural3) {
        if (!this.isFrozen) {
            this.explicit[standardPlural3.ordinal()] = true;
            if (standardPlural5 == null) {
                for (StandardPlural standardPlural4 : StandardPlural.values()) {
                    if (standardPlural22 == null) {
                        for (StandardPlural standardPlural5 : StandardPlural.values()) {
                            this.matrix.setIfNew(standardPlural4, standardPlural5, standardPlural3);
                        }
                        continue;
                    }
                    this.explicit[standardPlural22.ordinal()] = true;
                    this.matrix.setIfNew(standardPlural4, standardPlural22, standardPlural3);
                }
            } else if (standardPlural22 == null) {
                this.explicit[standardPlural5.ordinal()] = true;
                for (StandardPlural standardPlural22 : StandardPlural.values()) {
                    this.matrix.setIfNew(standardPlural5, standardPlural22, standardPlural3);
                }
            } else {
                this.explicit[standardPlural5.ordinal()] = true;
                this.explicit[standardPlural22.ordinal()] = true;
                this.matrix.setIfNew(standardPlural5, standardPlural22, standardPlural3);
            }
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public PluralRanges cloneAsThawed() {
        PluralRanges pluralRanges = new PluralRanges();
        pluralRanges.explicit = (boolean[])this.explicit.clone();
        pluralRanges.matrix = this.matrix.clone();
        return pluralRanges;
    }

    @Deprecated
    @Override
    public int compareTo(PluralRanges pluralRanges) {
        return this.matrix.compareTo(pluralRanges.matrix);
    }

    @Deprecated
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof PluralRanges)) {
            return false;
        }
        object = (PluralRanges)object;
        if (!this.matrix.equals(((PluralRanges)object).matrix) || !Arrays.equals(this.explicit, ((PluralRanges)object).explicit)) {
            bl = false;
        }
        return bl;
    }

    @Deprecated
    @Override
    public PluralRanges freeze() {
        this.isFrozen = true;
        return this;
    }

    @Deprecated
    public StandardPlural get(StandardPlural standardPlural, StandardPlural standardPlural2) {
        block0 : {
            if ((standardPlural = this.matrix.get(standardPlural, standardPlural2)) != null) break block0;
            standardPlural = standardPlural2;
        }
        return standardPlural;
    }

    @Deprecated
    public int hashCode() {
        return this.matrix.hashCode();
    }

    @Deprecated
    public boolean isExplicit(StandardPlural standardPlural, StandardPlural standardPlural2) {
        boolean bl = this.matrix.get(standardPlural, standardPlural2) != null;
        return bl;
    }

    @Deprecated
    public boolean isExplicitlySet(StandardPlural standardPlural) {
        return this.explicit[standardPlural.ordinal()];
    }

    @Deprecated
    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Deprecated
    public String toString() {
        return this.matrix.toString();
    }

    private static final class Matrix
    implements Comparable<Matrix>,
    Cloneable {
        private byte[] data = new byte[StandardPlural.COUNT * StandardPlural.COUNT];

        Matrix() {
            byte[] arrby;
            for (int i = 0; i < (arrby = this.data).length; ++i) {
                arrby[i] = (byte)-1;
            }
        }

        public Matrix clone() {
            Matrix matrix = new Matrix();
            matrix.data = (byte[])this.data.clone();
            return matrix;
        }

        @Override
        public int compareTo(Matrix matrix) {
            byte[] arrby;
            for (int i = 0; i < (arrby = this.data).length; ++i) {
                int n = arrby[i] - matrix.data[i];
                if (n == 0) continue;
                return n;
            }
            return 0;
        }

        StandardPlural endSame(StandardPlural standardPlural) {
            StandardPlural standardPlural2 = null;
            Iterator<StandardPlural> iterator = StandardPlural.VALUES.iterator();
            while (iterator.hasNext()) {
                StandardPlural standardPlural3 = this.get(iterator.next(), standardPlural);
                if (standardPlural3 == null) continue;
                if (standardPlural2 == null) {
                    standardPlural2 = standardPlural3;
                    continue;
                }
                if (standardPlural2 == standardPlural3) continue;
                return null;
            }
            return standardPlural2;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Matrix;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            if (this.compareTo((Matrix)object) == 0) {
                bl2 = true;
            }
            return bl2;
        }

        StandardPlural get(StandardPlural standardPlural, StandardPlural standardPlural2) {
            byte by = this.data[standardPlural.ordinal() * StandardPlural.COUNT + standardPlural2.ordinal()];
            standardPlural = by < 0 ? null : StandardPlural.VALUES.get(by);
            return standardPlural;
        }

        public int hashCode() {
            byte[] arrby;
            int n = 0;
            for (int i = 0; i < (arrby = this.data).length; ++i) {
                n = n * 37 + arrby[i];
            }
            return n;
        }

        void set(StandardPlural standardPlural, StandardPlural standardPlural2, StandardPlural standardPlural3) {
            byte[] arrby = this.data;
            int n = standardPlural.ordinal();
            int n2 = StandardPlural.COUNT;
            int n3 = standardPlural2.ordinal();
            int n4 = standardPlural3 == null ? -1 : (int)((byte)standardPlural3.ordinal());
            arrby[n * n2 + n3] = (byte)n4;
        }

        void setIfNew(StandardPlural standardPlural, StandardPlural standardPlural2, StandardPlural object) {
            int n = this.data[standardPlural.ordinal() * StandardPlural.COUNT + standardPlural2.ordinal()];
            if (n < 0) {
                byte[] arrby = this.data;
                int n2 = standardPlural.ordinal();
                int n3 = StandardPlural.COUNT;
                int n4 = standardPlural2.ordinal();
                n = object == null ? -1 : (int)((byte)((Enum)object).ordinal());
                arrby[n2 * n3 + n4] = (byte)n;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Previously set value for <");
            ((StringBuilder)object).append((Object)standardPlural);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append((Object)standardPlural2);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append((Object)StandardPlural.VALUES.get(n));
            ((StringBuilder)object).append(">");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        StandardPlural startSame(StandardPlural standardPlural, EnumSet<StandardPlural> enumSet, Output<Boolean> output) {
            output.value = false;
            StandardPlural standardPlural2 = null;
            for (StandardPlural standardPlural3 : StandardPlural.VALUES) {
                StandardPlural standardPlural4 = this.get(standardPlural, standardPlural3);
                if (standardPlural4 == null) continue;
                if (standardPlural2 == null) {
                    standardPlural2 = standardPlural4;
                    continue;
                }
                if (standardPlural2 != standardPlural4) {
                    return null;
                }
                if (enumSet.contains((Object)standardPlural3)) continue;
                output.value = true;
            }
            return standardPlural2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (StandardPlural standardPlural : StandardPlural.values()) {
                for (StandardPlural standardPlural2 : StandardPlural.values()) {
                    StandardPlural standardPlural3 = this.get(standardPlural, standardPlural2);
                    if (standardPlural3 == null) continue;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append((Object)standardPlural);
                    stringBuilder2.append(" & ");
                    stringBuilder2.append((Object)standardPlural2);
                    stringBuilder2.append(" \u2192 ");
                    stringBuilder2.append((Object)standardPlural3);
                    stringBuilder2.append(";\n");
                    stringBuilder.append(stringBuilder2.toString());
                }
            }
            return stringBuilder.toString();
        }
    }

}

