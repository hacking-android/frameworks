/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Utility;
import android.icu.util.Freezable;
import java.util.Objects;

public class Row<C0, C1, C2, C3, C4>
implements Comparable,
Cloneable,
Freezable<Row<C0, C1, C2, C3, C4>> {
    protected volatile boolean frozen;
    protected Object[] items;

    public static <C0, C1> R2<C0, C1> of(C0 C0, C1 C1) {
        return new R2<C0, C1>(C0, C1);
    }

    public static <C0, C1, C2> R3<C0, C1, C2> of(C0 C0, C1 C1, C2 C2) {
        return new R3<C0, C1, C2>(C0, C1, C2);
    }

    public static <C0, C1, C2, C3> R4<C0, C1, C2, C3> of(C0 C0, C1 C1, C2 C2, C3 C3) {
        return new R4<C0, C1, C2, C3>(C0, C1, C2, C3);
    }

    public static <C0, C1, C2, C3, C4> R5<C0, C1, C2, C3, C4> of(C0 C0, C1 C1, C2 C2, C3 C3, C4 C4) {
        return new R5<C0, C1, C2, C3, C4>(C0, C1, C2, C3, C4);
    }

    public Object clone() {
        if (this.frozen) {
            return this;
        }
        try {
            Row row = (Row)super.clone();
            this.items = (Object[])this.items.clone();
            return row;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public Row<C0, C1, C2, C3, C4> cloneAsThawed() {
        try {
            Row row = (Row)super.clone();
            this.items = (Object[])this.items.clone();
            row.frozen = false;
            return row;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    public int compareTo(Object object) {
        object = (Row)object;
        Object[] arrobject = this.items;
        int n = arrobject.length - ((Row)object).items.length;
        if (n != 0) {
            return n;
        }
        int n2 = arrobject.length;
        n = 0;
        int n3 = 0;
        while (n3 < n2) {
            int n4 = Utility.checkCompare((Comparable)arrobject[n3], (Comparable)((Row)object).items[n]);
            if (n4 != 0) {
                return n4;
            }
            ++n3;
            ++n;
        }
        return 0;
    }

    public boolean equals(Object object) {
        int n;
        int n2;
        Object[] arrobject;
        int n3;
        block7 : {
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (Row)object;
            if (this.items.length == ((Row)object).items.length) break block7;
            return false;
        }
        try {
            arrobject = this.items;
            n2 = arrobject.length;
            n = 0;
            n3 = 0;
        }
        catch (Exception exception) {
            return false;
        }
        while (n3 < n2) {
            block8 : {
                boolean bl = Objects.equals(arrobject[n3], ((Row)object).items[n]);
                if (bl) break block8;
                return false;
            }
            ++n3;
            ++n;
        }
        return true;
    }

    @Override
    public Row<C0, C1, C2, C3, C4> freeze() {
        this.frozen = true;
        return this;
    }

    public C0 get0() {
        return (C0)this.items[0];
    }

    public C1 get1() {
        return (C1)this.items[1];
    }

    public C2 get2() {
        return (C2)this.items[2];
    }

    public C3 get3() {
        return (C3)this.items[3];
    }

    public C4 get4() {
        return (C4)this.items[4];
    }

    public int hashCode() {
        Object[] arrobject = this.items;
        int n = arrobject.length;
        int n2 = arrobject.length;
        for (int i = 0; i < n2; ++i) {
            n = n * 37 + Utility.checkHash(arrobject[i]);
        }
        return n;
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    protected Row<C0, C1, C2, C3, C4> set(int n, Object object) {
        if (!this.frozen) {
            this.items[n] = object;
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify frozen object");
    }

    public Row<C0, C1, C2, C3, C4> set0(C0 C0) {
        return this.set(0, C0);
    }

    public Row<C0, C1, C2, C3, C4> set1(C1 C1) {
        return this.set(1, C1);
    }

    public Row<C0, C1, C2, C3, C4> set2(C2 C2) {
        return this.set(2, C2);
    }

    public Row<C0, C1, C2, C3, C4> set3(C3 C3) {
        return this.set(3, C3);
    }

    public Row<C0, C1, C2, C3, C4> set4(C4 C4) {
        return this.set(4, C4);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        boolean bl = true;
        for (Object object : this.items) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            stringBuilder.append(object);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static class R2<C0, C1>
    extends Row<C0, C1, C1, C1, C1> {
        public R2(C0 C0, C1 C1) {
            this.items = new Object[]{C0, C1};
        }
    }

    public static class R3<C0, C1, C2>
    extends Row<C0, C1, C2, C2, C2> {
        public R3(C0 C0, C1 C1, C2 C2) {
            this.items = new Object[]{C0, C1, C2};
        }
    }

    public static class R4<C0, C1, C2, C3>
    extends Row<C0, C1, C2, C3, C3> {
        public R4(C0 C0, C1 C1, C2 C2, C3 C3) {
            this.items = new Object[]{C0, C1, C2, C3};
        }
    }

    public static class R5<C0, C1, C2, C3, C4>
    extends Row<C0, C1, C2, C3, C4> {
        public R5(C0 C0, C1 C1, C2 C2, C3 C3, C4 C4) {
            this.items = new Object[]{C0, C1, C2, C3, C4};
        }
    }

}

