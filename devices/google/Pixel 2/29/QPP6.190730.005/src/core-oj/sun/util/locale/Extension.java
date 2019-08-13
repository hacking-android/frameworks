/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

class Extension {
    private String id;
    private final char key;
    private String value;

    protected Extension(char c) {
        this.key = c;
    }

    Extension(char c, String string) {
        this.key = c;
        this.setValue(string);
    }

    public String getID() {
        return this.id;
    }

    public char getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    protected void setValue(String string) {
        this.value = string;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.key);
        stringBuilder.append("-");
        stringBuilder.append(string);
        this.id = stringBuilder.toString();
    }

    public String toString() {
        return this.getID();
    }
}

