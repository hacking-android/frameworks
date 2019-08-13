/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.security.BasicPermission;

public final class LinkPermission
extends BasicPermission {
    static final long serialVersionUID = -1441492453772213220L;

    public LinkPermission(String string) {
        super(string);
        this.checkName(string);
    }

    public LinkPermission(String charSequence, String string) {
        super((String)charSequence);
        this.checkName((String)charSequence);
        if (string != null && string.length() > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("actions: ");
            ((StringBuilder)charSequence).append(string);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
    }

    private void checkName(String string) {
        if (!string.equals("hard") && !string.equals("symbolic")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("name: ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }
}

