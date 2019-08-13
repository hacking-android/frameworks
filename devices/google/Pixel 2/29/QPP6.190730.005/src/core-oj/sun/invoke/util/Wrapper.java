/*
 * Decompiled with CFR 0.145.
 */
package sun.invoke.util;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public final class Wrapper
extends Enum<Wrapper> {
    private static final /* synthetic */ Wrapper[] $VALUES;
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final /* enum */ Wrapper BOOLEAN;
    public static final /* enum */ Wrapper BYTE;
    public static final /* enum */ Wrapper CHAR;
    public static final /* enum */ Wrapper DOUBLE;
    public static final /* enum */ Wrapper FLOAT;
    private static final Wrapper[] FROM_CHAR;
    private static final Wrapper[] FROM_PRIM;
    private static final Wrapper[] FROM_WRAP;
    public static final /* enum */ Wrapper INT;
    public static final /* enum */ Wrapper LONG;
    public static final /* enum */ Wrapper OBJECT;
    public static final /* enum */ Wrapper SHORT;
    public static final /* enum */ Wrapper VOID;
    private final char basicTypeChar;
    private final Object emptyArray;
    private final int format;
    private final String primitiveSimpleName;
    private final Class<?> primitiveType;
    private final String wrapperSimpleName;
    private final Class<?> wrapperType;
    private final Object zero;

    static {
        Wrapper[] arrwrapper = Boolean.TYPE;
        int n = Format.unsigned(1);
        BOOLEAN = new Wrapper(Boolean.class, (Class<?>)arrwrapper, 'Z', false, new boolean[0], n);
        arrwrapper = Byte.TYPE;
        n = Format.signed(8);
        BYTE = new Wrapper(Byte.class, (Class<?>)arrwrapper, 'B', (byte)0, new byte[0], n);
        arrwrapper = Short.TYPE;
        n = Format.signed(16);
        SHORT = new Wrapper(Short.class, (Class<?>)arrwrapper, 'S', (short)0, new short[0], n);
        arrwrapper = Character.TYPE;
        n = Format.unsigned(16);
        CHAR = new Wrapper(Character.class, (Class<?>)arrwrapper, 'C', Character.valueOf('\u0000'), new char[0], n);
        arrwrapper = Integer.TYPE;
        n = Format.signed(32);
        INT = new Wrapper(Integer.class, (Class<?>)arrwrapper, 'I', 0, new int[0], n);
        arrwrapper = Long.TYPE;
        n = Format.signed(64);
        LONG = new Wrapper(Long.class, (Class<?>)arrwrapper, 'J', 0L, new long[0], n);
        arrwrapper = Float.TYPE;
        n = Format.floating(32);
        FLOAT = new Wrapper(Float.class, (Class<?>)arrwrapper, 'F', Float.valueOf(0.0f), new float[0], n);
        arrwrapper = Double.TYPE;
        n = Format.floating(64);
        DOUBLE = new Wrapper(Double.class, (Class<?>)arrwrapper, 'D', 0.0, new double[0], n);
        n = Format.other(1);
        OBJECT = new Wrapper(Object.class, Object.class, 'L', null, new Object[0], n);
        VOID = new Wrapper(Void.class, Void.TYPE, 'V', null, null, Format.other(0));
        $VALUES = new Wrapper[]{BOOLEAN, BYTE, SHORT, CHAR, INT, LONG, FLOAT, DOUBLE, OBJECT, VOID};
        FROM_PRIM = new Wrapper[16];
        FROM_WRAP = new Wrapper[16];
        FROM_CHAR = new Wrapper[16];
        for (Wrapper wrapper : Wrapper.values()) {
            int n2 = Wrapper.hashPrim(wrapper.primitiveType);
            int n3 = Wrapper.hashWrap(wrapper.wrapperType);
            int n4 = Wrapper.hashChar(wrapper.basicTypeChar);
            Wrapper.FROM_PRIM[n2] = wrapper;
            Wrapper.FROM_WRAP[n3] = wrapper;
            Wrapper.FROM_CHAR[n4] = wrapper;
        }
    }

    private Wrapper(Class<?> class_, Class<?> class_2, char c, Object object, Object object2, int n2) {
        this.wrapperType = class_;
        this.primitiveType = class_2;
        this.basicTypeChar = c;
        this.zero = object;
        this.emptyArray = object2;
        this.format = n2;
        this.wrapperSimpleName = class_.getSimpleName();
        this.primitiveSimpleName = class_2.getSimpleName();
    }

    public static <T> Class<T> asPrimitiveType(Class<T> class_) {
        Wrapper wrapper = Wrapper.findWrapperType(class_);
        if (wrapper != null) {
            return Wrapper.forceType(wrapper.primitiveType(), class_);
        }
        return class_;
    }

    public static <T> Class<T> asWrapperType(Class<T> class_) {
        if (class_.isPrimitive()) {
            return Wrapper.forPrimitiveType(class_).wrapperType(class_);
        }
        return class_;
    }

    public static char basicTypeChar(Class<?> class_) {
        if (!class_.isPrimitive()) {
            return 'L';
        }
        return Wrapper.forPrimitiveType(class_).basicTypeChar();
    }

    private static boolean boolValue(byte by) {
        boolean bl = (byte)(by & 1) != 0;
        return bl;
    }

    private static boolean checkConvertibleFrom() {
        for (Wrapper wrapper : Wrapper.values()) {
            if (wrapper != VOID) {
                // empty if block
            }
            if (wrapper == CHAR || !wrapper.isConvertibleFrom(INT)) {
                // empty if block
            }
            if (wrapper == BOOLEAN || wrapper == VOID || wrapper != OBJECT) {
                // empty if block
            }
            if (wrapper.isSigned()) {
                for (Wrapper wrapper2 : Wrapper.values()) {
                    if (wrapper != wrapper2 && !wrapper2.isFloating() && wrapper2.isSigned() && wrapper.compareTo(wrapper2) >= 0) continue;
                }
            }
            if (!wrapper.isFloating()) continue;
            for (Wrapper wrapper2 : Wrapper.values()) {
                if (wrapper != wrapper2 && !wrapper2.isSigned() && wrapper2.isFloating() && wrapper.compareTo(wrapper2) >= 0) continue;
            }
        }
        return true;
    }

    private <T> T convert(Object object, Class<T> object2, boolean bl) {
        if (this == OBJECT) {
            if (!object2.isInterface()) {
                object2.cast(object);
            }
            return (T)object;
        }
        Class<T> class_ = this.wrapperType((Class<T>)object2);
        if (class_.isInstance(object)) {
            return class_.cast(object);
        }
        if (!bl) {
            Class<?> class_2 = object.getClass();
            object2 = Wrapper.findWrapperType(class_2);
            if (object2 == null || !this.isConvertibleFrom((Wrapper)((Object)object2))) {
                throw Wrapper.newClassCastException(class_, class_2);
            }
        } else if (object == null) {
            return (T)this.zero;
        }
        object = this.wrap(object);
        return (T)object;
    }

    static Wrapper findPrimitiveType(Class<?> class_) {
        Wrapper wrapper = FROM_PRIM[Wrapper.hashPrim(class_)];
        if (wrapper != null && wrapper.primitiveType == class_) {
            return wrapper;
        }
        return null;
    }

    static Wrapper findWrapperType(Class<?> class_) {
        Wrapper wrapper = FROM_WRAP[Wrapper.hashWrap(class_)];
        if (wrapper != null && wrapper.wrapperType == class_) {
            return wrapper;
        }
        return null;
    }

    public static Wrapper forBasicType(char c) {
        Wrapper wrapper = FROM_CHAR[Wrapper.hashChar(c)];
        if (wrapper != null && wrapper.basicTypeChar == c) {
            return wrapper;
        }
        for (Wrapper wrapper2 : Wrapper.values()) {
            if (wrapper.basicTypeChar != c) {
                continue;
            }
            throw new InternalError();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("not basic type char: ");
        stringBuilder.append(c);
        throw Wrapper.newIllegalArgumentException(stringBuilder.toString());
    }

    public static Wrapper forBasicType(Class<?> class_) {
        if (class_.isPrimitive()) {
            return Wrapper.forPrimitiveType(class_);
        }
        return OBJECT;
    }

    public static Wrapper forPrimitiveType(Class<?> class_) {
        Object object = Wrapper.findPrimitiveType(class_);
        if (object != null) {
            return object;
        }
        if (class_.isPrimitive()) {
            throw new InternalError();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("not primitive: ");
        ((StringBuilder)object).append(class_);
        throw Wrapper.newIllegalArgumentException(((StringBuilder)object).toString());
    }

    public static Wrapper forWrapperType(Class<?> class_) {
        Object object = Wrapper.findWrapperType(class_);
        if (object != null) {
            return object;
        }
        object = Wrapper.values();
        int n = ((Wrapper[])object).length;
        for (int i = 0; i < n; ++i) {
            if (object[i].wrapperType != class_) {
                continue;
            }
            throw new InternalError();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("not wrapper: ");
        ((StringBuilder)object).append(class_);
        throw Wrapper.newIllegalArgumentException(((StringBuilder)object).toString());
    }

    static <T> Class<T> forceType(Class<?> class_, Class<T> class_2) {
        boolean bl = class_ == class_2 || class_.isPrimitive() && Wrapper.forPrimitiveType(class_) == Wrapper.findWrapperType(class_2) || class_2.isPrimitive() && Wrapper.forPrimitiveType(class_2) == Wrapper.findWrapperType(class_) || class_ == Object.class && !class_2.isPrimitive();
        if (!bl) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(class_);
            stringBuilder.append(" <= ");
            stringBuilder.append(class_2);
            printStream.println(stringBuilder.toString());
        }
        return class_;
    }

    private static int hashChar(char c) {
        return ((c >> 1) + c) % 16;
    }

    private static int hashPrim(Class<?> object) {
        if (((String)(object = ((Class)object).getName())).length() < 3) {
            return 0;
        }
        return (((String)object).charAt(0) + ((String)object).charAt(2)) % 16;
    }

    private static int hashWrap(Class<?> object) {
        if (((String)(object = ((Class)object).getName())).length() < 13) {
            return 0;
        }
        return (((String)object).charAt(11) * 3 + ((String)object).charAt(12)) % 16;
    }

    public static boolean isPrimitiveType(Class<?> class_) {
        return class_.isPrimitive();
    }

    public static boolean isWrapperType(Class<?> class_) {
        boolean bl = Wrapper.findWrapperType(class_) != null;
        return bl;
    }

    private static ClassCastException newClassCastException(Class<?> class_, Class<?> class_2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(class_);
        stringBuilder.append(" is not compatible with ");
        stringBuilder.append(class_2);
        return new ClassCastException(stringBuilder.toString());
    }

    private static RuntimeException newIllegalArgumentException(String string) {
        return new IllegalArgumentException(string);
    }

    private static RuntimeException newIllegalArgumentException(String string, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(object);
        return Wrapper.newIllegalArgumentException(stringBuilder.toString());
    }

    private static Number numberValue(Object object) {
        if (object instanceof Number) {
            return (Number)object;
        }
        if (object instanceof Character) {
            return (int)((Character)object).charValue();
        }
        if (object instanceof Boolean) {
            return (int)((Boolean)object).booleanValue();
        }
        return (Number)object;
    }

    public static Wrapper valueOf(String string) {
        return Enum.valueOf(Wrapper.class, string);
    }

    public static Wrapper[] values() {
        return (Wrapper[])$VALUES.clone();
    }

    public Class<?> arrayType() {
        return this.emptyArray.getClass();
    }

    public char basicTypeChar() {
        return this.basicTypeChar;
    }

    public int bitWidth() {
        return this.format >> 2 & 1023;
    }

    public <T> T cast(Object object, Class<T> class_) {
        return this.convert(object, class_, true);
    }

    public <T> T convert(Object object, Class<T> class_) {
        return this.convert(object, class_, false);
    }

    public void copyArrayBoxing(Object object, int n, Object[] arrobject, int n2, int n3) {
        if (object.getClass() != this.arrayType()) {
            this.arrayType().cast(object);
        }
        for (int i = 0; i < n3; ++i) {
            Object object2;
            arrobject[i + n2] = object2 = Array.get(object, i + n);
        }
    }

    public void copyArrayUnboxing(Object[] arrobject, int n, Object object, int n2, int n3) {
        if (object.getClass() != this.arrayType()) {
            this.arrayType().cast(object);
        }
        for (int i = 0; i < n3; ++i) {
            Array.set(object, i + n2, this.convert(arrobject[i + n], this.primitiveType));
        }
    }

    public String detailString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.wrapperSimpleName);
        Class<?> class_ = this.wrapperType;
        Class<?> class_2 = this.primitiveType;
        char c = this.basicTypeChar;
        Object object = this.zero;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("0x");
        stringBuilder2.append(Integer.toHexString(this.format));
        stringBuilder.append(Arrays.asList(class_, class_2, Character.valueOf(c), object, stringBuilder2.toString()));
        return stringBuilder.toString();
    }

    public boolean isConvertibleFrom(Wrapper wrapper) {
        if (this == wrapper) {
            return true;
        }
        if (this.compareTo(wrapper) < 0) {
            return false;
        }
        boolean bl = (this.format & wrapper.format & -4096) != 0;
        if (!bl) {
            if (this.isOther()) {
                return true;
            }
            return wrapper.format == 65;
        }
        return true;
    }

    public boolean isDoubleWord() {
        boolean bl = (this.format & 2) != 0;
        return bl;
    }

    public boolean isFloating() {
        boolean bl = this.format >= 4225;
        return bl;
    }

    public boolean isIntegral() {
        boolean bl = this.isNumeric() && this.format < 4225;
        return bl;
    }

    public boolean isNumeric() {
        boolean bl = (this.format & -4) != 0;
        return bl;
    }

    public boolean isOther() {
        boolean bl = (this.format & -4) == 0;
        return bl;
    }

    public boolean isSigned() {
        boolean bl = this.format < 0;
        return bl;
    }

    public boolean isSingleWord() {
        int n = this.format;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isSubwordOrInt() {
        boolean bl = this.isIntegral() && this.isSingleWord();
        return bl;
    }

    public boolean isUnsigned() {
        int n = this.format;
        boolean bl = n >= 5 && n < 4225;
        return bl;
    }

    public Object makeArray(int n) {
        return Array.newInstance(this.primitiveType, n);
    }

    public String primitiveSimpleName() {
        return this.primitiveSimpleName;
    }

    public Class<?> primitiveType() {
        return this.primitiveType;
    }

    public int stackSlots() {
        return this.format >> 0 & 3;
    }

    public Object wrap(int n) {
        char c = this.basicTypeChar;
        if (c == 'L') {
            return n;
        }
        if (c != 'F') {
            if (c != 'L') {
                if (c != 'S') {
                    if (c != 'V') {
                        if (c != 'Z') {
                            if (c != 'I') {
                                if (c != 'J') {
                                    switch (c) {
                                        default: {
                                            throw new InternalError("bad wrapper");
                                        }
                                        case 'D': {
                                            return (double)n;
                                        }
                                        case 'C': {
                                            return Character.valueOf((char)n);
                                        }
                                        case 'B': 
                                    }
                                    return (byte)n;
                                }
                                return (long)n;
                            }
                            return n;
                        }
                        return Wrapper.boolValue((byte)n);
                    }
                    return null;
                }
                return (short)n;
            }
            throw Wrapper.newIllegalArgumentException("cannot wrap to object type");
        }
        return Float.valueOf(n);
    }

    public Object wrap(Object object) {
        char c = this.basicTypeChar;
        if (c != 'L') {
            if (c != 'V') {
                object = Wrapper.numberValue(object);
                c = this.basicTypeChar;
                if (c != 'F') {
                    if (c != 'S') {
                        if (c != 'Z') {
                            if (c != 'I') {
                                if (c != 'J') {
                                    switch (c) {
                                        default: {
                                            throw new InternalError("bad wrapper");
                                        }
                                        case 'D': {
                                            return ((Number)object).doubleValue();
                                        }
                                        case 'C': {
                                            return Character.valueOf((char)((Number)object).intValue());
                                        }
                                        case 'B': 
                                    }
                                    return (byte)((Number)object).intValue();
                                }
                                return ((Number)object).longValue();
                            }
                            return ((Number)object).intValue();
                        }
                        return Wrapper.boolValue(((Number)object).byteValue());
                    }
                    return (short)((Number)object).intValue();
                }
                return Float.valueOf(((Number)object).floatValue());
            }
            return null;
        }
        return object;
    }

    public String wrapperSimpleName() {
        return this.wrapperSimpleName;
    }

    public Class<?> wrapperType() {
        return this.wrapperType;
    }

    public <T> Class<T> wrapperType(Class<T> class_) {
        Class<?> class_2 = this.wrapperType;
        if (class_ == class_2) {
            return class_;
        }
        if (class_ != this.primitiveType && class_2 != Object.class && !class_.isInterface()) {
            throw Wrapper.newClassCastException(class_, this.primitiveType);
        }
        return Wrapper.forceType(this.wrapperType, class_);
    }

    public Object zero() {
        return this.zero;
    }

    public <T> T zero(Class<T> class_) {
        return this.convert(this.zero, class_);
    }

    private static abstract class Format {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int BOOLEAN = 5;
        static final int CHAR = 65;
        static final int FLOAT = 4225;
        static final int FLOATING = 4096;
        static final int INT = -3967;
        static final int KIND_SHIFT = 12;
        static final int NUM_MASK = -4;
        static final int SHORT = -4031;
        static final int SIGNED = -4096;
        static final int SIZE_MASK = 1023;
        static final int SIZE_SHIFT = 2;
        static final int SLOT_MASK = 3;
        static final int SLOT_SHIFT = 0;
        static final int UNSIGNED = 0;
        static final int VOID = 0;

        private Format() {
        }

        static int floating(int n) {
            int n2 = n > 32 ? 2 : 1;
            return Format.format(4096, n, n2);
        }

        static int format(int n, int n2, int n3) {
            return n2 << 2 | n | n3 << 0;
        }

        static int other(int n) {
            return n << 0;
        }

        static int signed(int n) {
            int n2 = n > 32 ? 2 : 1;
            return Format.format(-4096, n, n2);
        }

        static int unsigned(int n) {
            int n2 = n > 32 ? 2 : 1;
            return Format.format(0, n, n2);
        }
    }

}

