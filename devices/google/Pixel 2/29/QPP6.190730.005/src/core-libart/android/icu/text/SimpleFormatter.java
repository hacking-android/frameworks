/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.SimpleFormatterImpl;

public final class SimpleFormatter {
    private final String compiledPattern;

    private SimpleFormatter(String string) {
        this.compiledPattern = string;
    }

    public static SimpleFormatter compile(CharSequence charSequence) {
        return SimpleFormatter.compileMinMaxArguments(charSequence, 0, Integer.MAX_VALUE);
    }

    public static SimpleFormatter compileMinMaxArguments(CharSequence charSequence, int n, int n2) {
        return new SimpleFormatter(SimpleFormatterImpl.compileToStringMinMaxArguments(charSequence, new StringBuilder(), n, n2));
    }

    public String format(CharSequence ... arrcharSequence) {
        return SimpleFormatterImpl.formatCompiledPattern(this.compiledPattern, arrcharSequence);
    }

    public StringBuilder formatAndAppend(StringBuilder stringBuilder, int[] arrn, CharSequence ... arrcharSequence) {
        return SimpleFormatterImpl.formatAndAppend(this.compiledPattern, stringBuilder, arrn, arrcharSequence);
    }

    public StringBuilder formatAndReplace(StringBuilder stringBuilder, int[] arrn, CharSequence ... arrcharSequence) {
        return SimpleFormatterImpl.formatAndReplace(this.compiledPattern, stringBuilder, arrn, arrcharSequence);
    }

    public int getArgumentLimit() {
        return SimpleFormatterImpl.getArgumentLimit(this.compiledPattern);
    }

    public String getTextWithNoArguments() {
        return SimpleFormatterImpl.getTextWithNoArguments(this.compiledPattern);
    }

    public String toString() {
        CharSequence[] arrcharSequence = new String[this.getArgumentLimit()];
        for (int i = 0; i < arrcharSequence.length; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            stringBuilder.append(i);
            stringBuilder.append('}');
            arrcharSequence[i] = stringBuilder.toString();
        }
        return this.formatAndAppend(new StringBuilder(), null, arrcharSequence).toString();
    }
}

