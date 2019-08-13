/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternScanner {
    private Pattern mIgnorePattern;
    private String mInput;
    private int mLineNo = 0;
    private int mOffset = 0;
    private int mStartOfLine = 0;

    public PatternScanner(String string2) {
        this.mInput = string2;
    }

    public PatternScanner(String string2, Pattern pattern) {
        this.mInput = string2;
        this.mIgnorePattern = pattern;
        this.skip(this.mIgnorePattern);
    }

    public boolean atEnd() {
        boolean bl = this.mOffset >= this.mInput.length();
        return bl;
    }

    public String eat(Pattern object, String string2) {
        if ((object = this.tryEat((Pattern)object)) != null) {
            return object;
        }
        throw new RuntimeException(this.unexpectedTokenMessage(string2));
    }

    public int lineNo() {
        return this.mLineNo;
    }

    public boolean peek(Pattern object) {
        Pattern pattern = this.mIgnorePattern;
        if (pattern != null) {
            this.skip(pattern);
        }
        object = ((Pattern)object).matcher(this.mInput);
        ((Matcher)object).region(this.mOffset, this.mInput.length());
        return ((Matcher)object).lookingAt();
    }

    public void skip(Pattern object) {
        object = ((Pattern)object).matcher(this.mInput);
        ((Matcher)object).region(this.mOffset, this.mInput.length());
        if (((Matcher)object).lookingAt()) {
            this.updateLineCount(this.mOffset, ((Matcher)object).end());
            this.mOffset = ((Matcher)object).end();
        }
    }

    public String tryEat(Pattern object) {
        Object object2 = this.mIgnorePattern;
        if (object2 != null) {
            this.skip((Pattern)object2);
        }
        object2 = ((Pattern)object).matcher(this.mInput);
        ((Matcher)object2).region(this.mOffset, this.mInput.length());
        object = null;
        if (((Matcher)object2).lookingAt()) {
            this.updateLineCount(this.mOffset, ((Matcher)object2).end());
            this.mOffset = ((Matcher)object2).end();
            object = this.mInput.substring(((Matcher)object2).start(), ((Matcher)object2).end());
        }
        if (object != null && (object2 = this.mIgnorePattern) != null) {
            this.skip((Pattern)object2);
        }
        return object;
    }

    public String unexpectedTokenMessage(String string2) {
        String string3 = this.mInput.substring(this.mStartOfLine, this.mOffset);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected token on line ");
        stringBuilder.append(this.mLineNo + 1);
        stringBuilder.append(" after '");
        stringBuilder.append(string3);
        stringBuilder.append("' <- Expected ");
        stringBuilder.append(string2);
        stringBuilder.append("!");
        return stringBuilder.toString();
    }

    public void updateLineCount(int n, int n2) {
        while (n < n2) {
            if (this.mInput.charAt(n) == '\n') {
                ++this.mLineNo;
                this.mStartOfLine = n + 1;
            }
            ++n;
        }
    }
}

