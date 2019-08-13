/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

class OptionKey {
    private int level;
    private int name;

    OptionKey(int n, int n2) {
        this.level = n;
        this.name = n2;
    }

    int level() {
        return this.level;
    }

    int name() {
        return this.name;
    }
}

