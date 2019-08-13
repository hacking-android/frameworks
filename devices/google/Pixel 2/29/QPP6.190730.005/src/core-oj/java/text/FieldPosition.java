/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.text.AttributedCharacterIterator;
import java.text.Format;

public class FieldPosition {
    private Format.Field attribute;
    int beginIndex = 0;
    int endIndex = 0;
    int field = 0;

    public FieldPosition(int n) {
        this.field = n;
    }

    public FieldPosition(Format.Field field) {
        this(field, -1);
    }

    public FieldPosition(Format.Field field, int n) {
        this.attribute = field;
        this.field = n;
    }

    private boolean matchesField(Format.Field field) {
        Format.Field field2 = this.attribute;
        if (field2 != null) {
            return field2.equals(field);
        }
        return false;
    }

    private boolean matchesField(Format.Field field, int n) {
        Format.Field field2 = this.attribute;
        if (field2 != null) {
            return field2.equals(field);
        }
        boolean bl = n == this.field;
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (!(object instanceof FieldPosition)) {
            return false;
        }
        FieldPosition fieldPosition = (FieldPosition)object;
        object = this.attribute;
        if (object == null ? fieldPosition.attribute != null : !((AttributedCharacterIterator.Attribute)object).equals(fieldPosition.attribute)) {
            return false;
        }
        boolean bl2 = bl;
        if (this.beginIndex == fieldPosition.beginIndex) {
            bl2 = bl;
            if (this.endIndex == fieldPosition.endIndex) {
                bl2 = bl;
                if (this.field == fieldPosition.field) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    public int getBeginIndex() {
        return this.beginIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public int getField() {
        return this.field;
    }

    public Format.Field getFieldAttribute() {
        return this.attribute;
    }

    Format.FieldDelegate getFieldDelegate() {
        return new Delegate();
    }

    public int hashCode() {
        return this.field << 24 | this.beginIndex << 16 | this.endIndex;
    }

    public void setBeginIndex(int n) {
        this.beginIndex = n;
    }

    public void setEndIndex(int n) {
        this.endIndex = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[field=");
        stringBuilder.append(this.field);
        stringBuilder.append(",attribute=");
        stringBuilder.append(this.attribute);
        stringBuilder.append(",beginIndex=");
        stringBuilder.append(this.beginIndex);
        stringBuilder.append(",endIndex=");
        stringBuilder.append(this.endIndex);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private class Delegate
    implements Format.FieldDelegate {
        private boolean encounteredField;

        private Delegate() {
        }

        @Override
        public void formatted(int n, Format.Field field, Object object, int n2, int n3, StringBuffer stringBuffer) {
            if (!this.encounteredField && FieldPosition.this.matchesField(field, n)) {
                FieldPosition.this.setBeginIndex(n2);
                FieldPosition.this.setEndIndex(n3);
                boolean bl = n2 != n3;
                this.encounteredField = bl;
            }
        }

        @Override
        public void formatted(Format.Field field, Object object, int n, int n2, StringBuffer stringBuffer) {
            if (!this.encounteredField && FieldPosition.this.matchesField(field)) {
                FieldPosition.this.setBeginIndex(n);
                FieldPosition.this.setEndIndex(n2);
                boolean bl = n != n2;
                this.encounteredField = bl;
            }
        }
    }

}

