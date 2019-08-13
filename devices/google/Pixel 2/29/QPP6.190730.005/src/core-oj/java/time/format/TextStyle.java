/*
 * Decompiled with CFR 0.145.
 */
package java.time.format;

public enum TextStyle {
    FULL(2, 0),
    FULL_STANDALONE(32770, 0),
    SHORT(1, 1),
    SHORT_STANDALONE(32769, 1),
    NARROW(4, 1),
    NARROW_STANDALONE(32772, 1);
    
    private final int calendarStyle;
    private final int zoneNameStyleIndex;

    private TextStyle(int n2, int n3) {
        this.calendarStyle = n2;
        this.zoneNameStyleIndex = n3;
    }

    public TextStyle asNormal() {
        return TextStyle.values()[this.ordinal() & -2];
    }

    public TextStyle asStandalone() {
        return TextStyle.values()[this.ordinal() | 1];
    }

    public boolean isStandalone() {
        int n = this.ordinal();
        boolean bl = true;
        if ((n & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    int toCalendarStyle() {
        return this.calendarStyle;
    }

    int zoneNameStyleIndex() {
        return this.zoneNameStyleIndex;
    }
}

