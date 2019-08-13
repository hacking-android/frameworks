/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

public class InvalidPathException
extends IllegalArgumentException {
    static final long serialVersionUID = 4355821422286746137L;
    private int index;
    private String input;

    public InvalidPathException(String string, String string2) {
        this(string, string2, -1);
    }

    public InvalidPathException(String string, String string2, int n) {
        super(string2);
        if (string != null && string2 != null) {
            if (n >= -1) {
                this.input = string;
                this.index = n;
                return;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    public int getIndex() {
        return this.index;
    }

    public String getInput() {
        return this.input;
    }

    @Override
    public String getMessage() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getReason());
        if (this.index > -1) {
            stringBuffer.append(" at index ");
            stringBuffer.append(this.index);
        }
        stringBuffer.append(": ");
        stringBuffer.append(this.input);
        return stringBuffer.toString();
    }

    public String getReason() {
        return super.getMessage();
    }
}

