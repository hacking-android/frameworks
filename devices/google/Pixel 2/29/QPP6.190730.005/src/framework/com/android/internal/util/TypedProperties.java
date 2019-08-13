/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypedProperties
extends HashMap<String, Object> {
    static final String NULL_STRING = new String("<TypedProperties:NULL_STRING>");
    public static final int STRING_NOT_SET = -1;
    public static final int STRING_NULL = 0;
    public static final int STRING_SET = 1;
    public static final int STRING_TYPE_MISMATCH = -2;
    static final int TYPE_BOOLEAN = 90;
    static final int TYPE_BYTE = 329;
    static final int TYPE_DOUBLE = 2118;
    static final int TYPE_ERROR = -1;
    static final int TYPE_FLOAT = 1094;
    static final int TYPE_INT = 1097;
    static final int TYPE_LONG = 2121;
    static final int TYPE_SHORT = 585;
    static final int TYPE_STRING = 29516;
    static final int TYPE_UNSET = 120;

    static StreamTokenizer initTokenizer(Reader object) {
        object = new StreamTokenizer((Reader)object);
        ((StreamTokenizer)object).resetSyntax();
        ((StreamTokenizer)object).wordChars(48, 57);
        ((StreamTokenizer)object).wordChars(65, 90);
        ((StreamTokenizer)object).wordChars(97, 122);
        ((StreamTokenizer)object).wordChars(95, 95);
        ((StreamTokenizer)object).wordChars(36, 36);
        ((StreamTokenizer)object).wordChars(46, 46);
        ((StreamTokenizer)object).wordChars(45, 45);
        ((StreamTokenizer)object).wordChars(43, 43);
        ((StreamTokenizer)object).ordinaryChar(61);
        ((StreamTokenizer)object).whitespaceChars(32, 32);
        ((StreamTokenizer)object).whitespaceChars(9, 9);
        ((StreamTokenizer)object).whitespaceChars(10, 10);
        ((StreamTokenizer)object).whitespaceChars(13, 13);
        ((StreamTokenizer)object).quoteChar(34);
        ((StreamTokenizer)object).slashStarComments(true);
        ((StreamTokenizer)object).slashSlashComments(true);
        return object;
    }

    static int interpretType(String string2) {
        if ("unset".equals(string2)) {
            return 120;
        }
        if ("boolean".equals(string2)) {
            return 90;
        }
        if ("byte".equals(string2)) {
            return 329;
        }
        if ("short".equals(string2)) {
            return 585;
        }
        if ("int".equals(string2)) {
            return 1097;
        }
        if ("long".equals(string2)) {
            return 2121;
        }
        if ("float".equals(string2)) {
            return 1094;
        }
        if ("double".equals(string2)) {
            return 2118;
        }
        if ("String".equals(string2)) {
            return 29516;
        }
        return -1;
    }

    static void parse(Reader object, Map<String, Object> map) throws ParseException, IOException {
        StreamTokenizer streamTokenizer;
        block6 : {
            block7 : {
                block8 : {
                    block9 : {
                        block10 : {
                            streamTokenizer = TypedProperties.initTokenizer((Reader)object);
                            Pattern pattern = Pattern.compile("([a-zA-Z_$][0-9a-zA-Z_$]*\\.)*[a-zA-Z_$][0-9a-zA-Z_$]*");
                            do {
                                int n;
                                if ((n = streamTokenizer.nextToken()) == -1) {
                                    return;
                                }
                                if (n != -3) break block6;
                                n = TypedProperties.interpretType(streamTokenizer.sval);
                                if (n == -1) break block7;
                                streamTokenizer.sval = null;
                                if (n == 120 && streamTokenizer.nextToken() != 40) {
                                    throw new ParseException(streamTokenizer, "'('");
                                }
                                if (streamTokenizer.nextToken() != -3) break block8;
                                String string2 = streamTokenizer.sval;
                                if (!pattern.matcher(string2).matches()) break block9;
                                streamTokenizer.sval = null;
                                if (n == 120) {
                                    if (streamTokenizer.nextToken() == 41) {
                                        map.remove(string2);
                                        continue;
                                    }
                                    throw new ParseException(streamTokenizer, "')'");
                                }
                                if (streamTokenizer.nextToken() != 61) break block10;
                                object = TypedProperties.parseValue(streamTokenizer, n);
                                Object object2 = map.remove(string2);
                                if (object2 != null && object.getClass() != object2.getClass()) {
                                    throw new ParseException(streamTokenizer, "(property previously declared as a different type)");
                                }
                                map.put(string2, object);
                            } while (streamTokenizer.nextToken() == 59);
                            throw new ParseException(streamTokenizer, "';'");
                        }
                        throw new ParseException(streamTokenizer, "'='");
                    }
                    throw new ParseException(streamTokenizer, "valid property name");
                }
                throw new ParseException(streamTokenizer, "property name");
            }
            throw new ParseException(streamTokenizer, "valid type name");
        }
        throw new ParseException(streamTokenizer, "type name");
    }

    static Object parseValue(StreamTokenizer object, int n) throws IOException {
        int n2 = ((StreamTokenizer)object).nextToken();
        if (n == 90) {
            if (n2 == -3) {
                if ("true".equals(((StreamTokenizer)object).sval)) {
                    return Boolean.TRUE;
                }
                if ("false".equals(((StreamTokenizer)object).sval)) {
                    return Boolean.FALSE;
                }
                throw new ParseException((StreamTokenizer)object, "boolean constant");
            }
            throw new ParseException((StreamTokenizer)object, "boolean constant");
        }
        if ((n & 255) == 73) {
            if (n2 == -3) {
                long l;
                block19 : {
                    block20 : {
                        block21 : {
                            block22 : {
                                block23 : {
                                    try {
                                        l = Long.decode(((StreamTokenizer)object).sval);
                                        n = n >> 8 & 255;
                                        if (n == 1) break block19;
                                        if (n == 2) break block20;
                                        if (n == 4) break block21;
                                        if (n != 8) break block22;
                                        if (l < Long.MIN_VALUE || l > Long.MAX_VALUE) break block23;
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        throw new ParseException((StreamTokenizer)object, "integer constant");
                                    }
                                    return new Long(l);
                                }
                                throw new ParseException((StreamTokenizer)object, "64-bit integer constant");
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Internal error; unexpected integer type width ");
                            ((StringBuilder)object).append(n);
                            throw new IllegalStateException(((StringBuilder)object).toString());
                        }
                        if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) {
                            return new Integer((int)l);
                        }
                        throw new ParseException((StreamTokenizer)object, "32-bit integer constant");
                    }
                    if (l >= -32768L && l <= 32767L) {
                        return new Short((short)l);
                    }
                    throw new ParseException((StreamTokenizer)object, "16-bit integer constant");
                }
                if (l >= -128L && l <= 127L) {
                    return new Byte((byte)l);
                }
                throw new ParseException((StreamTokenizer)object, "8-bit integer constant");
            }
            throw new ParseException((StreamTokenizer)object, "integer constant");
        }
        if ((n & 255) == 70) {
            if (n2 == -3) {
                double d;
                block24 : {
                    try {
                        d = Double.parseDouble(((StreamTokenizer)object).sval);
                        if ((n >> 8 & 255) != 4) break block24;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw new ParseException((StreamTokenizer)object, "float constant");
                    }
                    double d2 = Math.abs(d);
                    if (d2 != 0.0 && !Double.isInfinite(d) && !Double.isNaN(d) && (d2 < 1.401298464324817E-45 || d2 > 3.4028234663852886E38)) {
                        throw new ParseException((StreamTokenizer)object, "32-bit float constant");
                    }
                    return new Float((float)d);
                }
                return new Double(d);
            }
            throw new ParseException((StreamTokenizer)object, "float constant");
        }
        if (n == 29516) {
            if (n2 == 34) {
                return ((StreamTokenizer)object).sval;
            }
            if (n2 == -3 && "null".equals(((StreamTokenizer)object).sval)) {
                return NULL_STRING;
            }
            throw new ParseException((StreamTokenizer)object, "double-quoted string or 'null'");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Internal error; unknown type ");
        ((StringBuilder)object).append(n);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    @Override
    public Object get(Object object) {
        if ((object = super.get(object)) == NULL_STRING) {
            return null;
        }
        return object;
    }

    public boolean getBoolean(String string2) {
        return this.getBoolean(string2, false);
    }

    public boolean getBoolean(String string2, boolean bl) {
        Object v = super.get(string2);
        if (v == null) {
            return bl;
        }
        if (v instanceof Boolean) {
            return (Boolean)v;
        }
        throw new TypeException(string2, v, "boolean");
    }

    public byte getByte(String string2) {
        return this.getByte(string2, (byte)0);
    }

    public byte getByte(String string2, byte by) {
        Object v = super.get(string2);
        if (v == null) {
            return by;
        }
        if (v instanceof Byte) {
            return (Byte)v;
        }
        throw new TypeException(string2, v, "byte");
    }

    public double getDouble(String string2) {
        return this.getDouble(string2, 0.0);
    }

    public double getDouble(String string2, double d) {
        Object v = super.get(string2);
        if (v == null) {
            return d;
        }
        if (v instanceof Double) {
            return (Double)v;
        }
        throw new TypeException(string2, v, "double");
    }

    public float getFloat(String string2) {
        return this.getFloat(string2, 0.0f);
    }

    public float getFloat(String string2, float f) {
        Object v = super.get(string2);
        if (v == null) {
            return f;
        }
        if (v instanceof Float) {
            return ((Float)v).floatValue();
        }
        throw new TypeException(string2, v, "float");
    }

    public int getInt(String string2) {
        return this.getInt(string2, 0);
    }

    public int getInt(String string2, int n) {
        Object v = super.get(string2);
        if (v == null) {
            return n;
        }
        if (v instanceof Integer) {
            return (Integer)v;
        }
        throw new TypeException(string2, v, "int");
    }

    public long getLong(String string2) {
        return this.getLong(string2, 0L);
    }

    public long getLong(String string2, long l) {
        Object v = super.get(string2);
        if (v == null) {
            return l;
        }
        if (v instanceof Long) {
            return (Long)v;
        }
        throw new TypeException(string2, v, "long");
    }

    public short getShort(String string2) {
        return this.getShort(string2, (short)0);
    }

    public short getShort(String string2, short s) {
        Object v = super.get(string2);
        if (v == null) {
            return s;
        }
        if (v instanceof Short) {
            return (Short)v;
        }
        throw new TypeException(string2, v, "short");
    }

    public String getString(String string2) {
        return this.getString(string2, "");
    }

    public String getString(String string2, String string3) {
        Object v = super.get(string2);
        if (v == null) {
            return string3;
        }
        if (v == NULL_STRING) {
            return null;
        }
        if (v instanceof String) {
            return (String)v;
        }
        throw new TypeException(string2, v, "string");
    }

    public int getStringInfo(String string2) {
        if ((string2 = super.get(string2)) == null) {
            return -1;
        }
        if (string2 == NULL_STRING) {
            return 0;
        }
        if (string2 instanceof String) {
            return 1;
        }
        return -2;
    }

    public void load(Reader reader) throws IOException {
        TypedProperties.parse(reader, this);
    }

    public static class ParseException
    extends IllegalArgumentException {
        ParseException(StreamTokenizer streamTokenizer, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expected ");
            stringBuilder.append(string2);
            stringBuilder.append(", saw ");
            stringBuilder.append(streamTokenizer.toString());
            super(stringBuilder.toString());
        }
    }

    public static class TypeException
    extends IllegalArgumentException {
        TypeException(String string2, Object object, String string3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" has type ");
            stringBuilder.append(object.getClass().getName());
            stringBuilder.append(", not ");
            stringBuilder.append(string3);
            super(stringBuilder.toString());
        }
    }

}

