/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

public class SkeletonSyntaxException
extends IllegalArgumentException {
    private static final long serialVersionUID = 7733971331648360554L;

    public SkeletonSyntaxException(String string, CharSequence charSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Syntax error in skeleton string: ");
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append((Object)charSequence);
        super(stringBuilder.toString());
    }

    public SkeletonSyntaxException(String string, CharSequence charSequence, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Syntax error in skeleton string: ");
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append((Object)charSequence);
        super(stringBuilder.toString(), throwable);
    }
}

