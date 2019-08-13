/*
 * Decompiled with CFR 0.145.
 */
package java.text;

public abstract class CollationKey
implements Comparable<CollationKey> {
    private final String source;

    protected CollationKey(String string) {
        if (string != null) {
            this.source = string;
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public abstract int compareTo(CollationKey var1);

    public String getSourceString() {
        return this.source;
    }

    public abstract byte[] toByteArray();
}

