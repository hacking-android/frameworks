/*
 * Decompiled with CFR 0.145.
 */
package junit.framework;

public class ComparisonCompactor {
    private static final String DELTA_END = "]";
    private static final String DELTA_START = "[";
    private static final String ELLIPSIS = "...";
    private String fActual;
    private int fContextLength;
    private String fExpected;
    private int fPrefix;
    private int fSuffix;

    public ComparisonCompactor(int n, String string, String string2) {
        this.fContextLength = n;
        this.fExpected = string;
        this.fActual = string2;
    }

    private boolean areStringsEqual() {
        return this.fExpected.equals(this.fActual);
    }

    private String compactString(String charSequence) {
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append(DELTA_START);
        ((StringBuilder)charSequence2).append(((String)charSequence).substring(this.fPrefix, ((String)charSequence).length() - this.fSuffix + 1));
        ((StringBuilder)charSequence2).append(DELTA_END);
        charSequence2 = ((StringBuilder)charSequence2).toString();
        charSequence = charSequence2;
        if (this.fPrefix > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.computeCommonPrefix());
            ((StringBuilder)charSequence).append((String)charSequence2);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (this.fSuffix > 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(this.computeCommonSuffix());
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        return charSequence2;
    }

    private String computeCommonPrefix() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.fPrefix > this.fContextLength ? ELLIPSIS : "";
        stringBuilder.append(string);
        stringBuilder.append(this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix));
        return stringBuilder.toString();
    }

    private String computeCommonSuffix() {
        int n = Math.min(this.fExpected.length() - this.fSuffix + 1 + this.fContextLength, this.fExpected.length());
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.fExpected;
        stringBuilder.append(string.substring(string.length() - this.fSuffix + 1, n));
        string = this.fExpected.length() - this.fSuffix + 1 < this.fExpected.length() - this.fContextLength ? ELLIPSIS : "";
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private void findCommonPrefix() {
        int n;
        this.fPrefix = 0;
        int n2 = Math.min(this.fExpected.length(), this.fActual.length());
        while ((n = ++this.fPrefix) < n2 && this.fExpected.charAt(n) == this.fActual.charAt(this.fPrefix)) {
        }
    }

    private void findCommonSuffix() {
        int n;
        int n2 = this.fExpected.length() - 1;
        for (int i = this.fActual.length() - 1; i >= (n = this.fPrefix) && n2 >= n && this.fExpected.charAt(n2) == this.fActual.charAt(i); --i, --n2) {
        }
        this.fSuffix = this.fExpected.length() - n2;
    }

    private static String format(String charSequence, Object object, Object object2) {
        String string = "";
        CharSequence charSequence2 = string;
        if (charSequence != null) {
            charSequence2 = string;
            if (((String)charSequence).length() > 0) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(" ");
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("expected:<");
        ((StringBuilder)charSequence).append(object);
        ((StringBuilder)charSequence).append("> but was:<");
        ((StringBuilder)charSequence).append(object2);
        ((StringBuilder)charSequence).append(">");
        return ((StringBuilder)charSequence).toString();
    }

    public String compact(String string) {
        if (this.fExpected != null && this.fActual != null && !this.areStringsEqual()) {
            this.findCommonPrefix();
            this.findCommonSuffix();
            return ComparisonCompactor.format(string, this.compactString(this.fExpected), this.compactString(this.fActual));
        }
        return ComparisonCompactor.format(string, this.fExpected, this.fActual);
    }
}

