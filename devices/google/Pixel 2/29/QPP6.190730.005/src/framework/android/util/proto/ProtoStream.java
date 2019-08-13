/*
 * Decompiled with CFR 0.145.
 */
package android.util.proto;

public abstract class ProtoStream {
    public static final long FIELD_COUNT_MASK = 0xF0000000000L;
    public static final long FIELD_COUNT_PACKED = 0x50000000000L;
    public static final long FIELD_COUNT_REPEATED = 0x20000000000L;
    public static final int FIELD_COUNT_SHIFT = 40;
    public static final long FIELD_COUNT_SINGLE = 0x10000000000L;
    public static final long FIELD_COUNT_UNKNOWN = 0L;
    public static final int FIELD_ID_MASK = -8;
    public static final int FIELD_ID_SHIFT = 3;
    public static final long FIELD_TYPE_BOOL = 0x800000000L;
    public static final long FIELD_TYPE_BYTES = 0xC00000000L;
    public static final long FIELD_TYPE_DOUBLE = 0x100000000L;
    public static final long FIELD_TYPE_ENUM = 0xE00000000L;
    public static final long FIELD_TYPE_FIXED32 = 0x700000000L;
    public static final long FIELD_TYPE_FIXED64 = 0x600000000L;
    public static final long FIELD_TYPE_FLOAT = 0x200000000L;
    public static final long FIELD_TYPE_INT32 = 0x500000000L;
    public static final long FIELD_TYPE_INT64 = 0x300000000L;
    public static final long FIELD_TYPE_MASK = 0xFF00000000L;
    public static final long FIELD_TYPE_MESSAGE = 0xB00000000L;
    protected static final String[] FIELD_TYPE_NAMES = new String[]{"Double", "Float", "Int64", "UInt64", "Int32", "Fixed64", "Fixed32", "Bool", "String", "Group", "Message", "Bytes", "UInt32", "Enum", "SFixed32", "SFixed64", "SInt32", "SInt64"};
    public static final long FIELD_TYPE_SFIXED32 = 0xF00000000L;
    public static final long FIELD_TYPE_SFIXED64 = 0x1000000000L;
    public static final int FIELD_TYPE_SHIFT = 32;
    public static final long FIELD_TYPE_SINT32 = 0x1100000000L;
    public static final long FIELD_TYPE_SINT64 = 77309411328L;
    public static final long FIELD_TYPE_STRING = 0x900000000L;
    public static final long FIELD_TYPE_UINT32 = 0xD00000000L;
    public static final long FIELD_TYPE_UINT64 = 0x400000000L;
    public static final long FIELD_TYPE_UNKNOWN = 0L;
    public static final int WIRE_TYPE_END_GROUP = 4;
    public static final int WIRE_TYPE_FIXED32 = 5;
    public static final int WIRE_TYPE_FIXED64 = 1;
    public static final int WIRE_TYPE_LENGTH_DELIMITED = 2;
    public static final int WIRE_TYPE_MASK = 7;
    public static final int WIRE_TYPE_START_GROUP = 3;
    public static final int WIRE_TYPE_VARINT = 0;

    public static int convertObjectIdToOrdinal(int n) {
        return 524287 - n;
    }

    public static int getDepthFromToken(long l) {
        return (int)(l >> 51 & 511L);
    }

    public static String getFieldCountString(long l) {
        if (l == 0x10000000000L) {
            return "";
        }
        if (l == 0x20000000000L) {
            return "Repeated";
        }
        if (l == 0x50000000000L) {
            return "Packed";
        }
        return null;
    }

    public static String getFieldIdString(long l) {
        CharSequence charSequence;
        CharSequence charSequence2;
        long l2 = 0xF0000000000L & l;
        CharSequence charSequence3 = charSequence2 = ProtoStream.getFieldCountString(l2);
        if (charSequence2 == null) {
            charSequence3 = new StringBuilder();
            ((StringBuilder)charSequence3).append("fieldCount=");
            ((StringBuilder)charSequence3).append(l2);
            charSequence3 = ((StringBuilder)charSequence3).toString();
        }
        charSequence2 = charSequence3;
        if (((String)charSequence3).length() > 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence3);
            ((StringBuilder)charSequence2).append(" ");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        l2 = 0xFF00000000L & l;
        charSequence3 = charSequence = ProtoStream.getFieldTypeString(l2);
        if (charSequence == null) {
            charSequence3 = new StringBuilder();
            ((StringBuilder)charSequence3).append("fieldType=");
            ((StringBuilder)charSequence3).append(l2);
            charSequence3 = ((StringBuilder)charSequence3).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append((String)charSequence3);
        ((StringBuilder)charSequence).append(" tag=");
        ((StringBuilder)charSequence).append((int)l);
        ((StringBuilder)charSequence).append(" fieldId=0x");
        ((StringBuilder)charSequence).append(Long.toHexString(l));
        return ((StringBuilder)charSequence).toString();
    }

    public static String getFieldTypeString(long l) {
        String[] arrstring;
        int n = (int)((0xFF00000000L & l) >>> 32) - 1;
        if (n >= 0 && n < (arrstring = FIELD_TYPE_NAMES).length) {
            return arrstring[n];
        }
        return null;
    }

    public static int getObjectIdFromToken(long l) {
        return (int)(l >> 32 & 524287L);
    }

    public static int getOffsetFromToken(long l) {
        return (int)l;
    }

    public static boolean getRepeatedFromToken(long l) {
        boolean bl = (l >> 60 & 1L) != 0L;
        return bl;
    }

    public static int getTagSizeFromToken(long l) {
        return (int)(l >> 61 & 7L);
    }

    public static String getWireTypeString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "Fixed32";
                        }
                        return "End Group";
                    }
                    return "Start Group";
                }
                return "Length Delimited";
            }
            return "Fixed64";
        }
        return "Varint";
    }

    public static long makeFieldId(int n, long l) {
        return (long)n & 0xFFFFFFFFL | l;
    }

    public static long makeToken(int n, boolean bl, int n2, int n3, int n4) {
        long l = n;
        long l2 = bl ? 0x1000000000000000L : 0L;
        return (l & 7L) << 61 | l2 | (511L & (long)n2) << 51 | (524287L & (long)n3) << 32 | 0xFFFFFFFFL & (long)n4;
    }

    public static String token2String(long l) {
        if (l == 0L) {
            return "Token(0)";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Token(val=0x");
        stringBuilder.append(Long.toHexString(l));
        stringBuilder.append(" depth=");
        stringBuilder.append(ProtoStream.getDepthFromToken(l));
        stringBuilder.append(" object=");
        stringBuilder.append(ProtoStream.convertObjectIdToOrdinal(ProtoStream.getObjectIdFromToken(l)));
        stringBuilder.append(" tagSize=");
        stringBuilder.append(ProtoStream.getTagSizeFromToken(l));
        stringBuilder.append(" offset=");
        stringBuilder.append(ProtoStream.getOffsetFromToken(l));
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

