/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.PatternProps;
import android.icu.text.MessagePattern;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class SelectFormat
extends Format {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = 2993154333257524984L;
    private transient MessagePattern msgPattern;
    private String pattern = null;

    public SelectFormat(String string) {
        this.applyPattern(string);
    }

    static int findSubMessage(MessagePattern messagePattern, int n, String string) {
        int n2;
        int n3 = messagePattern.countParts();
        int n4 = 0;
        int n5 = n;
        do {
            n2 = n5 + 1;
            MessagePattern.Part part = messagePattern.getPart(n5);
            if (part.getType() == MessagePattern.Part.Type.ARG_LIMIT) {
                n = n4;
                break;
            }
            if (messagePattern.partSubstringMatches(part, string)) {
                return n2;
            }
            n = n4;
            if (n4 == 0) {
                n = n4;
                if (messagePattern.partSubstringMatches(part, "other")) {
                    n = n2;
                }
            }
            n2 = messagePattern.getLimitPartIndex(n2) + 1;
            n4 = n;
            n5 = n2;
        } while (n2 < n3);
        return n;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        object = this.pattern;
        if (object != null) {
            this.applyPattern((String)object);
        }
    }

    private void reset() {
        this.pattern = null;
        MessagePattern messagePattern = this.msgPattern;
        if (messagePattern != null) {
            messagePattern.clear();
        }
    }

    public void applyPattern(String string) {
        this.pattern = string;
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        try {
            this.msgPattern.parseSelectStyle(string);
            return;
        }
        catch (RuntimeException runtimeException) {
            this.reset();
            throw runtimeException;
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (SelectFormat)object;
            MessagePattern messagePattern = this.msgPattern;
            if (messagePattern == null) {
                if (((SelectFormat)object).msgPattern != null) {
                    bl = false;
                }
            } else {
                bl = messagePattern.equals(((SelectFormat)object).msgPattern);
            }
            return bl;
        }
        return false;
    }

    public final String format(String object) {
        if (PatternProps.isIdentifier((CharSequence)object)) {
            Object object2 = this.msgPattern;
            if (object2 != null && ((MessagePattern)object2).countParts() != 0) {
                int n = SelectFormat.findSubMessage(this.msgPattern, 0, (String)object);
                if (!this.msgPattern.jdkAposMode()) {
                    int n2 = this.msgPattern.getLimitPartIndex(n);
                    return this.msgPattern.getPatternString().substring(this.msgPattern.getPart(n).getLimit(), this.msgPattern.getPatternIndex(n2));
                }
                object = null;
                int n3 = this.msgPattern.getPart(n).getLimit();
                do {
                    int n4;
                    object2 = this.msgPattern;
                    int n5 = n + 1;
                    MessagePattern.Part part = ((MessagePattern)object2).getPart(n5);
                    MessagePattern.Part.Type type = part.getType();
                    int n6 = part.getIndex();
                    if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                        if (object == null) {
                            return this.pattern.substring(n3, n6);
                        }
                        ((StringBuilder)object).append(this.pattern, n3, n6);
                        return ((StringBuilder)object).toString();
                    }
                    if (type == MessagePattern.Part.Type.SKIP_SYNTAX) {
                        object2 = object;
                        if (object == null) {
                            object2 = new StringBuilder();
                        }
                        ((StringBuilder)object2).append(this.pattern, n3, n6);
                        n4 = part.getLimit();
                        n = n5;
                    } else {
                        object2 = object;
                        n4 = n3;
                        n = n5;
                        if (type == MessagePattern.Part.Type.ARG_START) {
                            object2 = object;
                            if (object == null) {
                                object2 = new StringBuilder();
                            }
                            ((StringBuilder)object2).append(this.pattern, n3, n6);
                            n = this.msgPattern.getLimitPartIndex(n5);
                            n4 = this.msgPattern.getPart(n).getLimit();
                            MessagePattern.appendReducedApostrophes(this.pattern, n6, n4, (StringBuilder)object2);
                        }
                    }
                    object = object2;
                    n3 = n4;
                } while (true);
            }
            throw new IllegalStateException("Invalid format error.");
        }
        throw new IllegalArgumentException("Invalid formatting argument.");
    }

    @Override
    public StringBuffer format(Object object, StringBuffer abstractStringBuilder, FieldPosition fieldPosition) {
        if (object instanceof String) {
            ((StringBuffer)abstractStringBuilder).append(this.format((String)object));
            return abstractStringBuilder;
        }
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append("'");
        ((StringBuilder)abstractStringBuilder).append(object);
        ((StringBuilder)abstractStringBuilder).append("' is not a String");
        throw new IllegalArgumentException(((StringBuilder)abstractStringBuilder).toString());
    }

    public int hashCode() {
        String string = this.pattern;
        if (string != null) {
            return string.hashCode();
        }
        return 0;
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    public String toPattern() {
        return this.pattern;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("pattern='");
        stringBuilder.append(this.pattern);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}

