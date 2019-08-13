/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;

public abstract class Format
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -299282585814624189L;

    protected Format() {
    }

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    AttributedCharacterIterator createAttributedCharacterIterator(String string) {
        return new AttributedString(string).getIterator();
    }

    AttributedCharacterIterator createAttributedCharacterIterator(String object, AttributedCharacterIterator.Attribute attribute, Object object2) {
        object = new AttributedString((String)object);
        ((AttributedString)object).addAttribute(attribute, object2);
        return ((AttributedString)object).getIterator();
    }

    AttributedCharacterIterator createAttributedCharacterIterator(AttributedCharacterIterator object, AttributedCharacterIterator.Attribute attribute, Object object2) {
        object = new AttributedString((AttributedCharacterIterator)object);
        ((AttributedString)object).addAttribute(attribute, object2);
        return ((AttributedString)object).getIterator();
    }

    AttributedCharacterIterator createAttributedCharacterIterator(AttributedCharacterIterator[] arrattributedCharacterIterator) {
        return new AttributedString(arrattributedCharacterIterator).getIterator();
    }

    public final String format(Object object) {
        return this.format(object, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public abstract StringBuffer format(Object var1, StringBuffer var2, FieldPosition var3);

    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        return this.createAttributedCharacterIterator(this.format(object));
    }

    public Object parseObject(String object) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        object = this.parseObject((String)object, parsePosition);
        if (parsePosition.index != 0) {
            return object;
        }
        throw new ParseException("Format.parseObject(String) failed", parsePosition.errorIndex);
    }

    public abstract Object parseObject(String var1, ParsePosition var2);

    public static class Field
    extends AttributedCharacterIterator.Attribute {
        private static final long serialVersionUID = 276966692217360283L;

        protected Field(String string) {
            super(string);
        }
    }

    static interface FieldDelegate {
        public void formatted(int var1, Field var2, Object var3, int var4, int var5, StringBuffer var6);

        public void formatted(Field var1, Object var2, int var3, int var4, StringBuffer var5);
    }

}

