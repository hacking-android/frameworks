/*
 * Decompiled with CFR 0.145.
 */
package android.text.util;

public class Rfc822Token {
    private String mAddress;
    private String mComment;
    private String mName;

    public Rfc822Token(String string2, String string3, String string4) {
        this.mName = string2;
        this.mAddress = string3;
        this.mComment = string4;
    }

    public static String quoteComment(String string2) {
        int n = string2.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (c == '(' || c == ')' || c == '\\') {
                stringBuilder.append('\\');
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String quoteName(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (c == '\\' || c == '\"') {
                stringBuilder.append('\\');
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String quoteNameIfNecessary(String string2) {
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c == ' ' || c >= '0' && c <= '9') continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('\"');
            stringBuilder.append(Rfc822Token.quoteName(string2));
            stringBuilder.append('\"');
            return stringBuilder.toString();
        }
        return string2;
    }

    private static boolean stringEquals(String string2, String string3) {
        if (string2 == null) {
            boolean bl = string3 == null;
            return bl;
        }
        return string2.equals(string3);
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof Rfc822Token;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (Rfc822Token)object;
            if (!Rfc822Token.stringEquals(this.mName, ((Rfc822Token)object).mName) || !Rfc822Token.stringEquals(this.mAddress, ((Rfc822Token)object).mAddress) || !Rfc822Token.stringEquals(this.mComment, ((Rfc822Token)object).mComment)) break block1;
            bl = true;
        }
        return bl;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public String getComment() {
        return this.mComment;
    }

    public String getName() {
        return this.mName;
    }

    public int hashCode() {
        int n = 17;
        String string2 = this.mName;
        if (string2 != null) {
            n = 17 * 31 + string2.hashCode();
        }
        string2 = this.mAddress;
        int n2 = n;
        if (string2 != null) {
            n2 = n * 31 + string2.hashCode();
        }
        string2 = this.mComment;
        n = n2;
        if (string2 != null) {
            n = n2 * 31 + string2.hashCode();
        }
        return n;
    }

    public void setAddress(String string2) {
        this.mAddress = string2;
    }

    public void setComment(String string2) {
        this.mComment = string2;
    }

    public void setName(String string2) {
        this.mName = string2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = this.mName;
        if (string2 != null && string2.length() != 0) {
            stringBuilder.append(Rfc822Token.quoteNameIfNecessary(this.mName));
            stringBuilder.append(' ');
        }
        if ((string2 = this.mComment) != null && string2.length() != 0) {
            stringBuilder.append('(');
            stringBuilder.append(Rfc822Token.quoteComment(this.mComment));
            stringBuilder.append(") ");
        }
        if ((string2 = this.mAddress) != null && string2.length() != 0) {
            stringBuilder.append('<');
            stringBuilder.append(this.mAddress);
            stringBuilder.append('>');
        }
        return stringBuilder.toString();
    }
}

