/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class TypeNotPresentException
extends RuntimeException {
    private static final long serialVersionUID = -5101214195716534496L;
    private String typeName;

    public TypeNotPresentException(String string, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type ");
        stringBuilder.append(string);
        stringBuilder.append(" not present");
        super(stringBuilder.toString(), throwable);
        this.typeName = string;
    }

    public String typeName() {
        return this.typeName;
    }
}

