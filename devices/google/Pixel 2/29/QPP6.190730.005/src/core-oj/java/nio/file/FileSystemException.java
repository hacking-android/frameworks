/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.IOException;

public class FileSystemException
extends IOException {
    static final long serialVersionUID = -3055425747967319812L;
    private final String file;
    private final String other;

    public FileSystemException(String string) {
        super((String)null);
        this.file = string;
        this.other = null;
    }

    public FileSystemException(String string, String string2, String string3) {
        super(string3);
        this.file = string;
        this.other = string2;
    }

    public String getFile() {
        return this.file;
    }

    @Override
    public String getMessage() {
        if (this.file == null && this.other == null) {
            return this.getReason();
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.file;
        if (string != null) {
            stringBuilder.append(string);
        }
        if (this.other != null) {
            stringBuilder.append(" -> ");
            stringBuilder.append(this.other);
        }
        if (this.getReason() != null) {
            stringBuilder.append(": ");
            stringBuilder.append(this.getReason());
        }
        return stringBuilder.toString();
    }

    public String getOtherFile() {
        return this.other;
    }

    public String getReason() {
        return super.getMessage();
    }
}

