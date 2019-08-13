/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.number.FormattedNumber;
import android.icu.number.LocalizedNumberFormatter;
import android.icu.number.NumberFormatter;
import android.icu.util.ULocale;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class LocalizedNumberFormatterAsFormat
extends Format {
    private static final long serialVersionUID = 1L;
    private final transient LocalizedNumberFormatter formatter;
    private final transient ULocale locale;

    public LocalizedNumberFormatterAsFormat(LocalizedNumberFormatter localizedNumberFormatter, ULocale uLocale) {
        this.formatter = localizedNumberFormatter;
        this.locale = uLocale;
    }

    private Object writeReplace() throws ObjectStreamException {
        Proxy proxy = new Proxy();
        proxy.languageTag = this.locale.toLanguageTag();
        proxy.skeleton = this.formatter.toSkeleton();
        return proxy;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof LocalizedNumberFormatterAsFormat)) {
            return false;
        }
        return this.formatter.equals(((LocalizedNumberFormatterAsFormat)object).getNumberFormatter());
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (object instanceof Number) {
            object = this.formatter.format((Number)object);
            fieldPosition.setBeginIndex(0);
            fieldPosition.setEndIndex(0);
            if (((FormattedNumber)object).nextFieldPosition(fieldPosition) && stringBuffer.length() != 0) {
                fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + stringBuffer.length());
                fieldPosition.setEndIndex(fieldPosition.getEndIndex() + stringBuffer.length());
            }
            ((FormattedNumber)object).appendTo(stringBuffer);
            return stringBuffer;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        if (object instanceof Number) {
            return this.formatter.format((Number)object).toCharacterIterator();
        }
        throw new IllegalArgumentException();
    }

    public LocalizedNumberFormatter getNumberFormatter() {
        return this.formatter;
    }

    public int hashCode() {
        return this.formatter.hashCode();
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    static class Proxy
    implements Externalizable {
        private static final long serialVersionUID = 1L;
        String languageTag;
        String skeleton;

        private Object readResolve() throws ObjectStreamException {
            return NumberFormatter.forSkeleton(this.skeleton).locale(ULocale.forLanguageTag(this.languageTag)).toFormat();
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            objectInput.readByte();
            this.languageTag = objectInput.readUTF();
            this.skeleton = objectInput.readUTF();
        }

        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(0);
            objectOutput.writeUTF(this.languageTag);
            objectOutput.writeUTF(this.skeleton);
        }
    }

}

