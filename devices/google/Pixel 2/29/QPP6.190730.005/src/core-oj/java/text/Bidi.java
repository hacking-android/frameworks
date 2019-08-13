/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.Bidi
 */
package java.text;

import java.text.AttributedCharacterIterator;

public final class Bidi {
    public static final int DIRECTION_DEFAULT_LEFT_TO_RIGHT = -2;
    public static final int DIRECTION_DEFAULT_RIGHT_TO_LEFT = -1;
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int DIRECTION_RIGHT_TO_LEFT = 1;
    private final android.icu.text.Bidi bidiBase;

    private Bidi(android.icu.text.Bidi bidi) {
        this.bidiBase = bidi;
    }

    public Bidi(String string, int n) {
        if (string != null) {
            this.bidiBase = new android.icu.text.Bidi(string.toCharArray(), 0, null, 0, string.length(), Bidi.translateConstToIcu(n));
            return;
        }
        throw new IllegalArgumentException("paragraph is null");
    }

    public Bidi(AttributedCharacterIterator attributedCharacterIterator) {
        if (attributedCharacterIterator != null) {
            this.bidiBase = new android.icu.text.Bidi(attributedCharacterIterator);
            return;
        }
        throw new IllegalArgumentException("paragraph is null");
    }

    public Bidi(char[] object, int n, byte[] object2, int n2, int n3, int n4) {
        if (object != null) {
            if (n3 >= 0) {
                if (n >= 0 && n3 <= ((char[])object).length - n) {
                    if (object2 != null && (n2 < 0 || n3 > ((Object)object2).length - n2)) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("bad range: ");
                        ((StringBuilder)object2).append(n2);
                        ((StringBuilder)object2).append(" length: ");
                        ((StringBuilder)object2).append(n3);
                        ((StringBuilder)object2).append(" for embeddings of length: ");
                        ((StringBuilder)object2).append(((Object)object).length);
                        throw new IllegalArgumentException(((StringBuilder)object2).toString());
                    }
                    this.bidiBase = new android.icu.text.Bidi((char[])object, n, (byte[])object2, n2, n3, Bidi.translateConstToIcu(n4));
                    return;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("bad range: ");
                ((StringBuilder)object2).append(n);
                ((StringBuilder)object2).append(" length: ");
                ((StringBuilder)object2).append(n3);
                ((StringBuilder)object2).append(" for text of length: ");
                ((StringBuilder)object2).append(((Object)object).length);
                throw new IllegalArgumentException(((StringBuilder)object2).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("bad length: ");
            ((StringBuilder)object).append(n3);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("text is null");
    }

    public static void reorderVisually(byte[] object, int n, Object[] object2, int n2, int n3) {
        if (n >= 0 && ((byte[])object).length > n) {
            if (n2 >= 0 && ((Object)object2).length > n2) {
                if (n3 >= 0 && ((Object)object2).length >= n2 + n3) {
                    android.icu.text.Bidi.reorderVisually((byte[])object, (int)n, (Object[])object2, (int)n2, (int)n3);
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Value count ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" is out of range 0 to ");
                ((StringBuilder)object).append(((Object)object2).length - n2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Value objectStart ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" is out of range 0 to ");
            ((StringBuilder)object).append(((Object)object2).length - 1);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Value levelStart ");
        ((StringBuilder)object2).append(n);
        ((StringBuilder)object2).append(" is out of range 0 to ");
        ((StringBuilder)object2).append(((Object)object).length - 1);
        throw new IllegalArgumentException(((StringBuilder)object2).toString());
    }

    public static boolean requiresBidi(char[] object, int n, int n2) {
        if (n >= 0 && n <= n2 && n2 <= ((char[])object).length) {
            return android.icu.text.Bidi.requiresBidi((char[])object, (int)n, (int)n2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Value start ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" is out of range 0 to ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static int translateConstToIcu(int n) {
        if (n != -2) {
            if (n != -1) {
                if (n != 0) {
                    return n == 1;
                }
                return 0;
            }
            return 127;
        }
        return 126;
    }

    public boolean baseIsLeftToRight() {
        return this.bidiBase.baseIsLeftToRight();
    }

    public Bidi createLineBidi(int n, int n2) {
        if (n >= 0 && n2 >= 0 && n <= n2 && n2 <= this.getLength()) {
            if (n == n2) {
                n = Bidi.translateConstToIcu(0);
                return new Bidi(new android.icu.text.Bidi(new char[0], 0, new byte[0], 0, 0, n));
            }
            return new Bidi(this.bidiBase.createLineBidi(n, n2));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid ranges (start=");
        stringBuilder.append(n);
        stringBuilder.append(", limit=");
        stringBuilder.append(n2);
        stringBuilder.append(", length=");
        stringBuilder.append(this.getLength());
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int getBaseLevel() {
        return this.bidiBase.getParaLevel();
    }

    public int getLength() {
        return this.bidiBase.getLength();
    }

    public int getLevelAt(int n) {
        try {
            n = this.bidiBase.getLevelAt(n);
            return n;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return this.getBaseLevel();
        }
    }

    public int getRunCount() {
        int n;
        block0 : {
            n = this.bidiBase.countRuns();
            if (n != 0) break block0;
            n = 1;
        }
        return n;
    }

    public int getRunLevel(int n) {
        if (n == this.getRunCount()) {
            return this.getBaseLevel();
        }
        n = this.bidiBase.countRuns() == 0 ? this.bidiBase.getBaseLevel() : this.bidiBase.getRunLevel(n);
        return n;
    }

    public int getRunLimit(int n) {
        if (n == this.getRunCount()) {
            return this.getBaseLevel();
        }
        n = this.bidiBase.countRuns() == 0 ? this.bidiBase.getLength() : this.bidiBase.getRunLimit(n);
        return n;
    }

    public int getRunStart(int n) {
        if (n == this.getRunCount()) {
            return this.getBaseLevel();
        }
        n = this.bidiBase.countRuns() == 0 ? 0 : this.bidiBase.getRunStart(n);
        return n;
    }

    public boolean isLeftToRight() {
        return this.bidiBase.isLeftToRight();
    }

    public boolean isMixed() {
        return this.bidiBase.isMixed();
    }

    public boolean isRightToLeft() {
        return this.bidiBase.isRightToLeft();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[direction: ");
        stringBuilder.append(this.bidiBase.getDirection());
        stringBuilder.append(" baseLevel: ");
        stringBuilder.append(this.bidiBase.getBaseLevel());
        stringBuilder.append(" length: ");
        stringBuilder.append(this.bidiBase.getLength());
        stringBuilder.append(" runs: ");
        stringBuilder.append(this.bidiBase.getRunCount());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

