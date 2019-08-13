/*
 * Decompiled with CFR 0.145.
 */
package java.text;

public class ParsePosition {
    int errorIndex = -1;
    int index = 0;

    public ParsePosition(int n) {
        this.index = n;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (!(object instanceof ParsePosition)) {
            return false;
        }
        object = (ParsePosition)object;
        boolean bl2 = bl;
        if (this.index == ((ParsePosition)object).index) {
            bl2 = bl;
            if (this.errorIndex == ((ParsePosition)object).errorIndex) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public int getErrorIndex() {
        return this.errorIndex;
    }

    public int getIndex() {
        return this.index;
    }

    public int hashCode() {
        return this.errorIndex << 16 | this.index;
    }

    public void setErrorIndex(int n) {
        this.errorIndex = n;
    }

    public void setIndex(int n) {
        this.index = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[index=");
        stringBuilder.append(this.index);
        stringBuilder.append(",errorIndex=");
        stringBuilder.append(this.errorIndex);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

