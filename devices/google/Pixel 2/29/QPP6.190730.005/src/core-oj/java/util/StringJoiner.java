/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Objects;

public final class StringJoiner {
    private final String delimiter;
    private String emptyValue;
    private final String prefix;
    private final String suffix;
    private StringBuilder value;

    public StringJoiner(CharSequence charSequence) {
        this(charSequence, "", "");
    }

    public StringJoiner(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        Objects.requireNonNull(charSequence2, "The prefix must not be null");
        Objects.requireNonNull(charSequence, "The delimiter must not be null");
        Objects.requireNonNull(charSequence3, "The suffix must not be null");
        this.prefix = charSequence2.toString();
        this.delimiter = charSequence.toString();
        this.suffix = charSequence3.toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.prefix);
        ((StringBuilder)charSequence).append(this.suffix);
        this.emptyValue = ((StringBuilder)charSequence).toString();
    }

    private StringBuilder prepareBuilder() {
        StringBuilder stringBuilder = this.value;
        if (stringBuilder != null) {
            stringBuilder.append(this.delimiter);
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.prefix);
            this.value = stringBuilder;
        }
        return this.value;
    }

    public StringJoiner add(CharSequence charSequence) {
        this.prepareBuilder().append(charSequence);
        return this;
    }

    public int length() {
        StringBuilder stringBuilder = this.value;
        int n = stringBuilder != null ? stringBuilder.length() + this.suffix.length() : this.emptyValue.length();
        return n;
    }

    public StringJoiner merge(StringJoiner stringJoiner) {
        Objects.requireNonNull(stringJoiner);
        StringBuilder stringBuilder = stringJoiner.value;
        if (stringBuilder != null) {
            int n = stringBuilder.length();
            this.prepareBuilder().append(stringJoiner.value, stringJoiner.prefix.length(), n);
        }
        return this;
    }

    public StringJoiner setEmptyValue(CharSequence charSequence) {
        this.emptyValue = Objects.requireNonNull(charSequence, "The empty value must not be null").toString();
        return this;
    }

    public String toString() {
        if (this.value == null) {
            return this.emptyValue;
        }
        if (this.suffix.equals("")) {
            return this.value.toString();
        }
        int n = this.value.length();
        CharSequence charSequence = this.value;
        charSequence.append(this.suffix);
        charSequence = charSequence.toString();
        this.value.setLength(n);
        return charSequence;
    }
}

