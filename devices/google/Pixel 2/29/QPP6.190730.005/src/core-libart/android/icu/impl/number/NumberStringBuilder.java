/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.text.NumberFormat;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NumberStringBuilder
implements CharSequence {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final NumberStringBuilder EMPTY = new NumberStringBuilder();
    private static final Map<NumberFormat.Field, Character> fieldToDebugChar = new HashMap<NumberFormat.Field, Character>();
    private char[] chars;
    private NumberFormat.Field[] fields;
    private int length;
    private int zero;

    static {
        fieldToDebugChar.put(NumberFormat.Field.SIGN, Character.valueOf('-'));
        fieldToDebugChar.put(NumberFormat.Field.INTEGER, Character.valueOf('i'));
        fieldToDebugChar.put(NumberFormat.Field.FRACTION, Character.valueOf('f'));
        fieldToDebugChar.put(NumberFormat.Field.EXPONENT, Character.valueOf('e'));
        fieldToDebugChar.put(NumberFormat.Field.EXPONENT_SIGN, Character.valueOf('+'));
        fieldToDebugChar.put(NumberFormat.Field.EXPONENT_SYMBOL, Character.valueOf('E'));
        fieldToDebugChar.put(NumberFormat.Field.DECIMAL_SEPARATOR, Character.valueOf('.'));
        fieldToDebugChar.put(NumberFormat.Field.GROUPING_SEPARATOR, Character.valueOf(','));
        fieldToDebugChar.put(NumberFormat.Field.PERCENT, Character.valueOf('%'));
        fieldToDebugChar.put(NumberFormat.Field.PERMILLE, Character.valueOf('\u2030'));
        fieldToDebugChar.put(NumberFormat.Field.CURRENCY, Character.valueOf('$'));
    }

    public NumberStringBuilder() {
        this(40);
    }

    public NumberStringBuilder(int n) {
        this.chars = new char[n];
        this.fields = new NumberFormat.Field[n];
        this.zero = n / 2;
        this.length = 0;
    }

    public NumberStringBuilder(NumberStringBuilder numberStringBuilder) {
        this.copyFrom(numberStringBuilder);
    }

    private int getCapacity() {
        return this.chars.length;
    }

    private int prepareForInsert(int n, int n2) {
        int n3;
        if (n == 0 && (n3 = this.zero) - n2 >= 0) {
            this.zero = n3 - n2;
            this.length += n2;
            return this.zero;
        }
        n3 = this.length;
        if (n == n3 && this.zero + n3 + n2 < this.getCapacity()) {
            this.length += n2;
            return this.zero + this.length - n2;
        }
        return this.prepareForInsertHelper(n, n2);
    }

    private int prepareForInsertHelper(int n, int n2) {
        int n3 = this.getCapacity();
        int n4 = this.zero;
        char[] arrc = this.chars;
        NumberFormat.Field[] arrfield = this.fields;
        int n5 = this.length;
        if (n5 + n2 > n3) {
            n3 = (n5 + n2) * 2;
            n5 = n3 / 2 - (n5 + n2) / 2;
            char[] arrc2 = new char[n3];
            NumberFormat.Field[] arrfield2 = new NumberFormat.Field[n3];
            System.arraycopy(arrc, n4, arrc2, n5, n);
            System.arraycopy(arrc, n4 + n, arrc2, n5 + n + n2, this.length - n);
            System.arraycopy(arrfield, n4, arrfield2, n5, n);
            System.arraycopy(arrfield, n4 + n, arrfield2, n5 + n + n2, this.length - n);
            this.chars = arrc2;
            this.fields = arrfield2;
            this.zero = n5;
            this.length += n2;
        } else {
            n3 = n3 / 2 - (n5 + n2) / 2;
            System.arraycopy(arrc, n4, arrc, n3, n5);
            System.arraycopy(arrc, n3 + n, arrc, n3 + n + n2, this.length - n);
            System.arraycopy(arrfield, n4, arrfield, n3, this.length);
            System.arraycopy(arrfield, n3 + n, arrfield, n3 + n + n2, this.length - n);
            this.zero = n3;
            this.length += n2;
        }
        return this.zero + n;
    }

    private int remove(int n, int n2) {
        int n3 = this.zero + n;
        Object[] arrobject = this.chars;
        System.arraycopy(arrobject, n3 + n2, arrobject, n3, this.length - n - n2);
        arrobject = this.fields;
        System.arraycopy(arrobject, n3 + n2, arrobject, n3, this.length - n - n2);
        this.length -= n2;
        return n3;
    }

    public int append(NumberStringBuilder numberStringBuilder) {
        return this.insert(this.length, numberStringBuilder);
    }

    public int append(CharSequence charSequence, NumberFormat.Field field) {
        return this.insert(this.length, charSequence, field);
    }

    public int append(char[] arrc, NumberFormat.Field[] arrfield) {
        return this.insert(this.length, arrc, arrfield);
    }

    public int appendCodePoint(int n, NumberFormat.Field field) {
        return this.insertCodePoint(this.length, n, field);
    }

    @Override
    public char charAt(int n) {
        return this.chars[this.zero + n];
    }

    public NumberStringBuilder clear() {
        this.zero = this.getCapacity() / 2;
        this.length = 0;
        return this;
    }

    public int codePointAt(int n) {
        char[] arrc = this.chars;
        int n2 = this.zero;
        return Character.codePointAt(arrc, n2 + n, n2 + this.length);
    }

    public int codePointBefore(int n) {
        char[] arrc = this.chars;
        int n2 = this.zero;
        return Character.codePointBefore(arrc, n2 + n, n2);
    }

    public int codePointCount() {
        return Character.codePointCount(this, 0, this.length());
    }

    public boolean contentEquals(NumberStringBuilder numberStringBuilder) {
        if (this.length != numberStringBuilder.length) {
            return false;
        }
        for (int i = 0; i < this.length; ++i) {
            if (this.charAt(i) == numberStringBuilder.charAt(i) && this.fieldAt(i) == numberStringBuilder.fieldAt(i)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public boolean contentEquals(char[] arrc, NumberFormat.Field[] arrfield) {
        int n = arrc.length;
        int n2 = this.length;
        if (n != n2) {
            return false;
        }
        if (arrfield.length != n2) {
            return false;
        }
        for (n = 0; n < this.length; ++n) {
            char[] arrc2 = this.chars;
            n2 = this.zero;
            if (arrc2[n2 + n] != arrc[n]) {
                return false;
            }
            if (this.fields[n2 + n] == arrfield[n]) continue;
            return false;
        }
        return true;
    }

    public void copyFrom(NumberStringBuilder numberStringBuilder) {
        Object[] arrobject = numberStringBuilder.chars;
        this.chars = Arrays.copyOf(arrobject, arrobject.length);
        arrobject = numberStringBuilder.fields;
        this.fields = (NumberFormat.Field[])Arrays.copyOf(arrobject, arrobject.length);
        this.zero = numberStringBuilder.zero;
        this.length = numberStringBuilder.length;
    }

    public boolean equals(Object object) {
        throw new UnsupportedOperationException("Don't call #hashCode() or #equals() on a mutable.");
    }

    public NumberFormat.Field fieldAt(int n) {
        return this.fields[this.zero + n];
    }

    public int getFirstCodePoint() {
        int n = this.length;
        if (n == 0) {
            return -1;
        }
        char[] arrc = this.chars;
        int n2 = this.zero;
        return Character.codePointAt(arrc, n2, n + n2);
    }

    public int getLastCodePoint() {
        int n = this.length;
        if (n == 0) {
            return -1;
        }
        char[] arrc = this.chars;
        int n2 = this.zero;
        return Character.codePointBefore(arrc, n + n2, n2);
    }

    public int hashCode() {
        throw new UnsupportedOperationException("Don't call #hashCode() or #equals() on a mutable.");
    }

    public int insert(int n, NumberStringBuilder numberStringBuilder) {
        if (this != numberStringBuilder) {
            int n2 = numberStringBuilder.length;
            if (n2 == 0) {
                return 0;
            }
            int n3 = this.prepareForInsert(n, n2);
            for (n = 0; n < n2; ++n) {
                this.chars[n3 + n] = numberStringBuilder.charAt(n);
                this.fields[n3 + n] = numberStringBuilder.fieldAt(n);
            }
            return n2;
        }
        throw new IllegalArgumentException("Cannot call insert/append on myself");
    }

    public int insert(int n, CharSequence charSequence, int n2, int n3, NumberFormat.Field field) {
        int n4 = this.prepareForInsert(n, n3 -= n2);
        for (n = 0; n < n3; ++n) {
            this.chars[n4 + n] = charSequence.charAt(n2 + n);
            this.fields[n4 + n] = field;
        }
        return n3;
    }

    public int insert(int n, CharSequence charSequence, NumberFormat.Field field) {
        if (charSequence.length() == 0) {
            return 0;
        }
        if (charSequence.length() == 1) {
            return this.insertCodePoint(n, charSequence.charAt(0), field);
        }
        return this.insert(n, charSequence, 0, charSequence.length(), field);
    }

    public int insert(int n, char[] arrc, NumberFormat.Field[] arrfield) {
        int n2 = arrc.length;
        if (n2 == 0) {
            return 0;
        }
        int n3 = this.prepareForInsert(n, n2);
        for (n = 0; n < n2; ++n) {
            this.chars[n3 + n] = arrc[n];
            NumberFormat.Field[] arrfield2 = this.fields;
            NumberFormat.Field field = arrfield == null ? null : arrfield[n];
            arrfield2[n3 + n] = field;
        }
        return n2;
    }

    public int insertCodePoint(int n, int n2, NumberFormat.Field field) {
        int n3 = Character.charCount(n2);
        n = this.prepareForInsert(n, n3);
        Character.toChars(n2, this.chars, n);
        NumberFormat.Field[] arrfield = this.fields;
        arrfield[n] = field;
        if (n3 == 2) {
            arrfield[n + 1] = field;
        }
        return n3;
    }

    @Override
    public int length() {
        return this.length;
    }

    public boolean nextFieldPosition(FieldPosition object) {
        Format.Field field;
        Format.Field field2 = field = ((FieldPosition)object).getFieldAttribute();
        if (field == null) {
            if (((FieldPosition)object).getField() == 0) {
                field2 = NumberFormat.Field.INTEGER;
            } else if (((FieldPosition)object).getField() == 1) {
                field2 = NumberFormat.Field.FRACTION;
            } else {
                return false;
            }
        }
        if (field2 instanceof NumberFormat.Field) {
            int n;
            int n2;
            field = (NumberFormat.Field)field2;
            boolean bl = false;
            int n3 = -1;
            int n4 = ((FieldPosition)object).getEndIndex();
            for (n4 = this.zero + n4; n4 <= (n2 = this.zero) + (n = this.length); ++n4) {
                field2 = n4 < n2 + n ? this.fields[n4] : null;
                if (bl && field != field2) {
                    if (field == NumberFormat.Field.INTEGER && field2 == NumberFormat.Field.GROUPING_SEPARATOR) continue;
                    ((FieldPosition)object).setEndIndex(n4 - this.zero);
                    break;
                }
                boolean bl2 = bl;
                if (!bl) {
                    bl2 = bl;
                    if (field == field2) {
                        ((FieldPosition)object).setBeginIndex(n4 - this.zero);
                        bl2 = true;
                    }
                }
                if (field2 != NumberFormat.Field.INTEGER) {
                    bl = bl2;
                    if (field2 != NumberFormat.Field.DECIMAL_SEPARATOR) continue;
                }
                n3 = n4 - this.zero + 1;
                bl = bl2;
            }
            if (field == NumberFormat.Field.FRACTION && !bl && n3 != -1) {
                ((FieldPosition)object).setBeginIndex(n3);
                ((FieldPosition)object).setEndIndex(n3);
            }
            return bl;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("You must pass an instance of android.icu.text.NumberFormat.Field as your FieldPosition attribute.  You passed: ");
        ((StringBuilder)object).append(field2.getClass().toString());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public int splice(int n, int n2, CharSequence charSequence, int n3, int n4, NumberFormat.Field field) {
        int n5 = (n4 -= n3) - (n2 - n);
        n = n5 > 0 ? this.prepareForInsert(n, n5) : this.remove(n, -n5);
        for (n2 = 0; n2 < n4; ++n2) {
            this.chars[n + n2] = charSequence.charAt(n3 + n2);
            this.fields[n + n2] = field;
        }
        return n5;
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        if (n >= 0 && n2 <= this.length && n2 >= n) {
            NumberStringBuilder numberStringBuilder = new NumberStringBuilder(this);
            numberStringBuilder.zero = this.zero + n;
            numberStringBuilder.length = n2 - n;
            return numberStringBuilder;
        }
        throw new IndexOutOfBoundsException();
    }

    public char[] toCharArray() {
        char[] arrc = this.chars;
        int n = this.zero;
        return Arrays.copyOfRange(arrc, n, this.length + n);
    }

    public AttributedCharacterIterator toCharacterIterator() {
        int n;
        AttributedString attributedString = new AttributedString(this.toString());
        NumberFormat.Field field = null;
        int n2 = -1;
        for (int i = 0; i < (n = this.length); ++i) {
            NumberFormat.Field field2;
            NumberFormat.Field field3 = this.fields[this.zero + i];
            if (field == NumberFormat.Field.INTEGER && field3 == NumberFormat.Field.GROUPING_SEPARATOR) {
                attributedString.addAttribute(NumberFormat.Field.GROUPING_SEPARATOR, NumberFormat.Field.GROUPING_SEPARATOR, i, i + 1);
                field2 = field;
                n = n2;
            } else {
                field2 = field;
                n = n2;
                if (field != field3) {
                    if (field != null) {
                        attributedString.addAttribute(field, field, n2, i);
                    }
                    field2 = field3;
                    n = i;
                }
            }
            field = field2;
            n2 = n;
        }
        if (field != null) {
            attributedString.addAttribute(field, field, n2, n);
        }
        return attributedString.getIterator();
    }

    public String toDebugString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<NumberStringBuilder [");
        stringBuilder.append(this.toString());
        stringBuilder.append("] [");
        for (int i = this.zero; i < this.zero + this.length; ++i) {
            NumberFormat.Field[] arrfield = this.fields;
            if (arrfield[i] == null) {
                stringBuilder.append('n');
                continue;
            }
            stringBuilder.append(fieldToDebugChar.get(arrfield[i]));
        }
        stringBuilder.append("]>");
        return stringBuilder.toString();
    }

    public NumberFormat.Field[] toFieldArray() {
        NumberFormat.Field[] arrfield = this.fields;
        int n = this.zero;
        return Arrays.copyOfRange(arrfield, n, this.length + n);
    }

    @Override
    public String toString() {
        return new String(this.chars, this.zero, this.length);
    }
}

