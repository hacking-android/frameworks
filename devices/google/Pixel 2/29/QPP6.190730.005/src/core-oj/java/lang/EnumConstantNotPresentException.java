/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class EnumConstantNotPresentException
extends RuntimeException {
    private static final long serialVersionUID = -6046998521960521108L;
    private String constantName;
    private Class<? extends Enum> enumType;

    public EnumConstantNotPresentException(Class<? extends Enum> class_, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(class_.getName());
        stringBuilder.append(".");
        stringBuilder.append(string);
        super(stringBuilder.toString());
        this.enumType = class_;
        this.constantName = string;
    }

    public String constantName() {
        return this.constantName;
    }

    public Class<? extends Enum> enumType() {
        return this.enumType;
    }
}

