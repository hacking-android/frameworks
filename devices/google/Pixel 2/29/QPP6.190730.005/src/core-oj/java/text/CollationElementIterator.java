/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.CollationElementIterator
 */
package java.text;

import java.text.CharacterIterator;

public final class CollationElementIterator {
    public static final int NULLORDER = -1;
    private android.icu.text.CollationElementIterator icuIterator;

    CollationElementIterator(android.icu.text.CollationElementIterator collationElementIterator) {
        this.icuIterator = collationElementIterator;
    }

    public static final int primaryOrder(int n) {
        return android.icu.text.CollationElementIterator.primaryOrder((int)n);
    }

    public static final short secondaryOrder(int n) {
        return (short)android.icu.text.CollationElementIterator.secondaryOrder((int)n);
    }

    public static final short tertiaryOrder(int n) {
        return (short)android.icu.text.CollationElementIterator.tertiaryOrder((int)n);
    }

    public int getMaxExpansion(int n) {
        return this.icuIterator.getMaxExpansion(n);
    }

    public int getOffset() {
        return this.icuIterator.getOffset();
    }

    public int next() {
        return this.icuIterator.next();
    }

    public int previous() {
        return this.icuIterator.previous();
    }

    public void reset() {
        this.icuIterator.reset();
    }

    public void setOffset(int n) {
        this.icuIterator.setOffset(n);
    }

    public void setText(String string) {
        this.icuIterator.setText(string);
    }

    public void setText(CharacterIterator characterIterator) {
        this.icuIterator.setText(characterIterator);
    }
}

