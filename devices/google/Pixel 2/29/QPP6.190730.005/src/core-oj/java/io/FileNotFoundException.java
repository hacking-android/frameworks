/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;

public class FileNotFoundException
extends IOException {
    private static final long serialVersionUID = -897856973823710492L;

    public FileNotFoundException() {
    }

    public FileNotFoundException(String string) {
        super(string);
    }

    private FileNotFoundException(String charSequence, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        if (string == null) {
            charSequence = "";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" (");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(")");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        super(stringBuilder.toString());
    }
}

