/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import java.text.FieldPosition;
import java.text.Format;

@Deprecated
public class UFieldPosition
extends FieldPosition {
    private int countVisibleFractionDigits = -1;
    private long fractionDigits = 0L;

    @Deprecated
    public UFieldPosition() {
        super(-1);
    }

    @Deprecated
    public UFieldPosition(int n) {
        super(n);
    }

    @Deprecated
    public UFieldPosition(Format.Field field) {
        super(field);
    }

    @Deprecated
    public UFieldPosition(Format.Field field, int n) {
        super(field, n);
    }

    @Deprecated
    public int getCountVisibleFractionDigits() {
        return this.countVisibleFractionDigits;
    }

    @Deprecated
    public long getFractionDigits() {
        return this.fractionDigits;
    }

    @Deprecated
    public void setFractionDigits(int n, long l) {
        this.countVisibleFractionDigits = n;
        this.fractionDigits = l;
    }
}

