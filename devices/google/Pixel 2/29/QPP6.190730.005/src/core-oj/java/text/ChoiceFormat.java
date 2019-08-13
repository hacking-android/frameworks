/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;

public class ChoiceFormat
extends NumberFormat {
    static final long EXPONENT = 9218868437227405312L;
    static final long POSITIVEINFINITY = 9218868437227405312L;
    static final long SIGN = Long.MIN_VALUE;
    private static final long serialVersionUID = 1795184449645032964L;
    private String[] choiceFormats;
    private double[] choiceLimits;

    public ChoiceFormat(String string) {
        this.applyPattern(string);
    }

    public ChoiceFormat(double[] arrd, String[] arrstring) {
        this.setChoices(arrd, arrstring);
    }

    private static double[] doubleArraySize(double[] arrd) {
        int n = arrd.length;
        double[] arrd2 = new double[n * 2];
        System.arraycopy((Object)arrd, 0, (Object)arrd2, 0, n);
        return arrd2;
    }

    private String[] doubleArraySize(String[] arrstring) {
        int n = arrstring.length;
        String[] arrstring2 = new String[n * 2];
        System.arraycopy(arrstring, 0, arrstring2, 0, n);
        return arrstring2;
    }

    public static final double nextDouble(double d) {
        return ChoiceFormat.nextDouble(d, true);
    }

    public static double nextDouble(double d, boolean bl) {
        long l;
        if (Double.isNaN(d)) {
            return d;
        }
        if (d == 0.0) {
            d = Double.longBitsToDouble(1L);
            if (bl) {
                return d;
            }
            return -d;
        }
        long l2 = Double.doubleToLongBits(d);
        long l3 = Long.MAX_VALUE & l2;
        boolean bl2 = l2 > 0L;
        if (bl2 == bl) {
            l = l3;
            if (l3 != 9218868437227405312L) {
                l = l3 + 1L;
            }
        } else {
            l = l3 - 1L;
        }
        return Double.longBitsToDouble(l | Long.MIN_VALUE & l2);
    }

    public static final double previousDouble(double d) {
        return ChoiceFormat.nextDouble(d, false);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.choiceLimits.length == this.choiceFormats.length) {
            return;
        }
        throw new InvalidObjectException("limits and format arrays of different length.");
    }

    public void applyPattern(String arrstring) {
        Object object;
        int n;
        StringBuffer[] arrstringBuffer = new StringBuffer[2];
        for (n = 0; n < arrstringBuffer.length; ++n) {
            arrstringBuffer[n] = new StringBuffer();
        }
        double[] arrd = new double[30];
        Object object2 = new String[30];
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        double d = Double.NaN;
        double d2 = 0.0;
        for (n = 0; n < arrstring.length(); ++n) {
            block25 : {
                block26 : {
                    int n5;
                    block22 : {
                        char c;
                        block20 : {
                            block19 : {
                                block24 : {
                                    block23 : {
                                        block21 : {
                                            c = arrstring.charAt(n);
                                            if (c != '\'') break block21;
                                            if (n + 1 < arrstring.length() && arrstring.charAt(n + 1) == c) {
                                                arrstringBuffer[n3].append(c);
                                                ++n;
                                                n5 = n3;
                                            } else {
                                                n5 = n4 == 0 ? 1 : 0;
                                                n4 = n5;
                                                n5 = n3;
                                            }
                                            break block22;
                                        }
                                        if (n4 == 0) break block23;
                                        arrstringBuffer[n3].append(c);
                                        n5 = n3;
                                        break block22;
                                    }
                                    if (c == '<' || c == '#' || c == '\u2264') break block24;
                                    if (c == '|') {
                                        double[] arrd2 = arrd;
                                        object = object2;
                                        if (n2 == arrd.length) {
                                            arrd2 = ChoiceFormat.doubleArraySize(arrd);
                                            object = this.doubleArraySize((String[])object2);
                                        }
                                        arrd2[n2] = d2;
                                        object[n2] = arrstringBuffer[1].toString();
                                        ++n2;
                                        d = d2;
                                        arrstringBuffer[1].setLength(0);
                                        n5 = 0;
                                        arrd = arrd2;
                                        object2 = object;
                                    } else {
                                        arrstringBuffer[n3].append(c);
                                        n5 = n3;
                                    }
                                    break block22;
                                }
                                if (arrstringBuffer[0].length() == 0) break block25;
                                try {
                                    object = arrstringBuffer[0].toString();
                                    if (!((String)object).equals("\u221e")) break block19;
                                    d2 = Double.POSITIVE_INFINITY;
                                }
                                catch (Exception exception) {
                                    throw new IllegalArgumentException();
                                }
                            }
                            if (!((String)object).equals("-\u221e")) break block20;
                            d2 = Double.NEGATIVE_INFINITY;
                        }
                        d2 = Double.parseDouble(arrstringBuffer[0].toString());
                        double d3 = d2;
                        if (c == '<') {
                            d3 = d2;
                            if (d2 != Double.POSITIVE_INFINITY) {
                                d3 = d2;
                                if (d2 != Double.NEGATIVE_INFINITY) {
                                    d3 = ChoiceFormat.nextDouble(d2);
                                }
                            }
                        }
                        if (d3 <= d) break block26;
                        arrstringBuffer[0].setLength(0);
                        n5 = 1;
                        d2 = d3;
                    }
                    n3 = n5;
                    continue;
                }
                throw new IllegalArgumentException();
            }
            throw new IllegalArgumentException();
        }
        object = arrd;
        n = n2;
        arrstring = object2;
        if (n3 == 1) {
            object = arrd;
            arrstring = object2;
            if (n2 == arrd.length) {
                object = ChoiceFormat.doubleArraySize(arrd);
                arrstring = this.doubleArraySize((String[])object2);
            }
            object[n2] = d2;
            arrstring[n2] = arrstringBuffer[1].toString();
            n = n2 + 1;
        }
        this.choiceLimits = new double[n];
        System.arraycopy(object, 0, (Object)this.choiceLimits, 0, n);
        this.choiceFormats = new String[n];
        System.arraycopy(arrstring, 0, this.choiceFormats, 0, n);
    }

    @Override
    public Object clone() {
        ChoiceFormat choiceFormat = (ChoiceFormat)super.clone();
        choiceFormat.choiceLimits = (double[])this.choiceLimits.clone();
        choiceFormat.choiceFormats = (String[])this.choiceFormats.clone();
        return choiceFormat;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl;
        block3 : {
            bl = false;
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (ChoiceFormat)object;
            if (!Arrays.equals(this.choiceLimits, ((ChoiceFormat)object).choiceLimits) || !Arrays.equals(this.choiceFormats, ((ChoiceFormat)object).choiceFormats)) break block3;
            bl = true;
        }
        return bl;
    }

    @Override
    public StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition arrd) {
        int n;
        int n2;
        for (n2 = 0; n2 < (arrd = this.choiceLimits).length && d >= arrd[n2]; ++n2) {
        }
        n2 = n = n2 - 1;
        if (n < 0) {
            n2 = 0;
        }
        stringBuffer.append(this.choiceFormats[n2]);
        return stringBuffer;
    }

    @Override
    public StringBuffer format(long l, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.format((double)l, stringBuffer, fieldPosition);
    }

    public Object[] getFormats() {
        String[] arrstring = this.choiceFormats;
        return Arrays.copyOf(arrstring, arrstring.length);
    }

    public double[] getLimits() {
        double[] arrd = this.choiceLimits;
        return Arrays.copyOf(arrd, arrd.length);
    }

    @Override
    public int hashCode() {
        int n = this.choiceLimits.length;
        String[] arrstring = this.choiceFormats;
        int n2 = n;
        if (arrstring.length > 0) {
            n2 = n ^ arrstring[arrstring.length - 1].hashCode();
        }
        return n2;
    }

    @Override
    public Number parse(String string, ParsePosition parsePosition) {
        double d;
        int n;
        int n2;
        int n3 = n2 = parsePosition.index;
        double d2 = Double.NaN;
        int n4 = 0;
        do {
            Object object = this.choiceFormats;
            n = n3;
            d = d2;
            if (n4 >= ((String[])object).length) break;
            object = object[n4];
            n = n3;
            d = d2;
            if (string.regionMatches(n2, (String)object, 0, ((String)object).length())) {
                parsePosition.index = ((String)object).length() + n2;
                double d3 = this.choiceLimits[n4];
                n = n3;
                d = d2;
                if (parsePosition.index > n3) {
                    n3 = parsePosition.index;
                    d2 = d3;
                    n = n3;
                    d = d2;
                    if (n3 == string.length()) {
                        n = n3;
                        d = d2;
                        break;
                    }
                }
            }
            ++n4;
            n3 = n;
            d2 = d;
        } while (true);
        parsePosition.index = n;
        if (parsePosition.index == n2) {
            parsePosition.errorIndex = n;
        }
        return new Double(d);
    }

    public void setChoices(double[] arrd, String[] arrstring) {
        if (arrd.length == arrstring.length) {
            this.choiceLimits = Arrays.copyOf(arrd, arrd.length);
            this.choiceFormats = Arrays.copyOf(arrstring, arrstring.length);
            return;
        }
        throw new IllegalArgumentException("Array and limit arrays must be of the same length.");
    }

    public String toPattern() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.choiceLimits.length; ++i) {
            Object object;
            if (i != 0) {
                stringBuffer.append('|');
            }
            double d = ChoiceFormat.previousDouble(this.choiceLimits[i]);
            if (Math.abs(Math.IEEEremainder(this.choiceLimits[i], 1.0)) < Math.abs(Math.IEEEremainder(d, 1.0))) {
                object = new StringBuilder();
                ((StringBuilder)object).append("");
                ((StringBuilder)object).append(this.choiceLimits[i]);
                stringBuffer.append(((StringBuilder)object).toString());
                stringBuffer.append('#');
            } else {
                object = this.choiceLimits;
                if (object[i] == Double.POSITIVE_INFINITY) {
                    stringBuffer.append("\u221e");
                } else if (object[i] == Double.NEGATIVE_INFINITY) {
                    stringBuffer.append("-\u221e");
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("");
                    ((StringBuilder)object).append(d);
                    stringBuffer.append(((StringBuilder)object).toString());
                }
                stringBuffer.append('<');
            }
            object = this.choiceFormats[i];
            boolean bl = ((String)object).indexOf(60) >= 0 || ((String)object).indexOf(35) >= 0 || ((String)object).indexOf(8804) >= 0 || ((String)object).indexOf(124) >= 0;
            if (bl) {
                stringBuffer.append('\'');
            }
            if (((String)object).indexOf(39) < 0) {
                stringBuffer.append((String)object);
            } else {
                for (int j = 0; j < ((String)object).length(); ++j) {
                    char c = ((String)object).charAt(j);
                    stringBuffer.append(c);
                    if (c != '\'') continue;
                    stringBuffer.append(c);
                }
            }
            if (!bl) continue;
            stringBuffer.append('\'');
        }
        return stringBuffer.toString();
    }
}

