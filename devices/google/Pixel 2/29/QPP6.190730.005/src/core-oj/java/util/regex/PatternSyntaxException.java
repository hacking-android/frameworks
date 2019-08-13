/*
 * Decompiled with CFR 0.145.
 */
package java.util.regex;

import java.security.AccessController;
import sun.security.action.GetPropertyAction;

public class PatternSyntaxException
extends IllegalArgumentException {
    private static final String nl = AccessController.doPrivileged(new GetPropertyAction("line.separator"));
    private static final long serialVersionUID = -3864639126226059218L;
    private final String desc;
    private final int index;
    private final String pattern;

    public PatternSyntaxException(String string, String string2, int n) {
        this.desc = string;
        this.pattern = string2;
        this.index = n;
    }

    public String getDescription() {
        return this.desc;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String getMessage() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.desc);
        if (this.index >= 0) {
            stringBuffer.append(" near index ");
            stringBuffer.append(this.index);
        }
        stringBuffer.append(nl);
        stringBuffer.append(this.pattern);
        if (this.index >= 0) {
            stringBuffer.append(nl);
            for (int i = 0; i < this.index; ++i) {
                stringBuffer.append(' ');
            }
            stringBuffer.append('^');
        }
        return stringBuffer.toString();
    }

    public String getPattern() {
        return this.pattern;
    }
}

