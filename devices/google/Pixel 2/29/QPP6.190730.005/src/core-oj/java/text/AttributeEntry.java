/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.text.AttributedCharacterIterator;
import java.util.Map;

class AttributeEntry
implements Map.Entry<AttributedCharacterIterator.Attribute, Object> {
    private AttributedCharacterIterator.Attribute key;
    private Object value;

    AttributeEntry(AttributedCharacterIterator.Attribute attribute, Object object) {
        this.key = attribute;
        this.value = object;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            Object object2;
            boolean bl2 = object instanceof AttributeEntry;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (AttributeEntry)object;
            if (!((AttributeEntry)object).key.equals(this.key) || !((object2 = this.value) == null ? ((AttributeEntry)object).value == null : ((AttributeEntry)object).value.equals(object2))) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public AttributedCharacterIterator.Attribute getKey() {
        return this.key;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        int n = this.key.hashCode();
        Object object = this.value;
        int n2 = object == null ? 0 : object.hashCode();
        return n ^ n2;
    }

    @Override
    public Object setValue(Object object) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.key.toString());
        stringBuilder.append("=");
        stringBuilder.append(this.value.toString());
        return stringBuilder.toString();
    }
}

