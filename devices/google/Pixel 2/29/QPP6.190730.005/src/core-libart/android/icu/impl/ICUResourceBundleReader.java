/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.CacheValue;
import android.icu.impl.ICUBinary;
import android.icu.impl.ICUData;
import android.icu.impl.SoftCache;
import android.icu.impl.UResource;
import android.icu.util.ICUException;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.ULocale;
import android.icu.util.UResourceTypeMismatchException;
import android.icu.util.VersionInfo;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public final class ICUResourceBundleReader {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static ReaderCache CACHE;
    private static final int DATA_FORMAT = 1382380354;
    private static final boolean DEBUG = false;
    private static final CharBuffer EMPTY_16_BIT_UNITS;
    private static final Array EMPTY_ARRAY;
    private static final Table EMPTY_TABLE;
    private static final String ICU_RESOURCE_SUFFIX = ".res";
    private static final IsAcceptable IS_ACCEPTABLE;
    static final int LARGE_SIZE = 24;
    private static final ICUResourceBundleReader NULL_READER;
    private static int[] PUBLIC_TYPES;
    private static final int URES_ATT_IS_POOL_BUNDLE = 2;
    private static final int URES_ATT_NO_FALLBACK = 1;
    private static final int URES_ATT_USES_POOL_BUNDLE = 4;
    private static final int URES_INDEX_16BIT_TOP = 6;
    private static final int URES_INDEX_ATTRIBUTES = 5;
    private static final int URES_INDEX_BUNDLE_TOP = 3;
    private static final int URES_INDEX_KEYS_TOP = 1;
    private static final int URES_INDEX_LENGTH = 0;
    private static final int URES_INDEX_MAX_TABLE_LENGTH = 4;
    private static final int URES_INDEX_POOL_CHECKSUM = 7;
    private static final ByteBuffer emptyByteBuffer;
    private static final byte[] emptyBytes;
    private static final char[] emptyChars;
    private static final int[] emptyInts;
    private static final String emptyString = "";
    private CharBuffer b16BitUnits;
    private ByteBuffer bytes;
    private int dataVersion;
    private boolean isPoolBundle;
    private byte[] keyBytes;
    private int localKeyLimit;
    private boolean noFallback;
    private ICUResourceBundleReader poolBundleReader;
    private int poolCheckSum;
    private int poolStringIndex16Limit;
    private int poolStringIndexLimit;
    private ResourceCache resourceCache;
    private int rootRes;
    private boolean usesPoolBundle;

    static {
        IS_ACCEPTABLE = new IsAcceptable();
        EMPTY_16_BIT_UNITS = CharBuffer.wrap("\u0000");
        CACHE = new ReaderCache();
        NULL_READER = new ICUResourceBundleReader();
        emptyBytes = new byte[0];
        emptyByteBuffer = ByteBuffer.allocate(0).asReadOnlyBuffer();
        emptyChars = new char[0];
        emptyInts = new int[0];
        EMPTY_ARRAY = new Array();
        EMPTY_TABLE = new Table();
        PUBLIC_TYPES = new int[]{0, 1, 2, 3, 2, 2, 0, 7, 8, 8, -1, -1, -1, -1, 14, -1};
    }

    private ICUResourceBundleReader() {
    }

    private ICUResourceBundleReader(ByteBuffer object, String string, String string2, ClassLoader classLoader) throws IOException {
        this.init((ByteBuffer)object);
        if (this.usesPoolBundle) {
            this.poolBundleReader = ICUResourceBundleReader.getReader(string, "pool", classLoader);
            object = this.poolBundleReader;
            if (object != null && ((ICUResourceBundleReader)object).isPoolBundle) {
                if (((ICUResourceBundleReader)object).poolCheckSum != this.poolCheckSum) {
                    throw new IllegalStateException("pool.res has a different checksum than this bundle");
                }
            } else {
                throw new IllegalStateException("pool.res is not a pool bundle");
            }
        }
    }

    static int RES_GET_INT(int n) {
        return n << 4 >> 4;
    }

    private static int RES_GET_OFFSET(int n) {
        return 268435455 & n;
    }

    static int RES_GET_TYPE(int n) {
        return n >>> 28;
    }

    static int RES_GET_UINT(int n) {
        return 268435455 & n;
    }

    static boolean URES_IS_ARRAY(int n) {
        boolean bl = n == 8 || n == 9;
        return bl;
    }

    static boolean URES_IS_TABLE(int n) {
        boolean bl = n == 2 || n == 5 || n == 4;
        return bl;
    }

    private int compareKeys(CharSequence charSequence, char c) {
        int n = this.localKeyLimit;
        if (c < n) {
            return ICUBinary.compareKeys(charSequence, this.keyBytes, (int)c);
        }
        return ICUBinary.compareKeys(charSequence, this.poolBundleReader.keyBytes, c - n);
    }

    private int compareKeys32(CharSequence charSequence, int n) {
        if (n >= 0) {
            return ICUBinary.compareKeys(charSequence, this.keyBytes, n);
        }
        return ICUBinary.compareKeys(charSequence, this.poolBundleReader.keyBytes, Integer.MAX_VALUE & n);
    }

    private char[] getChars(int n, int n2) {
        char[] arrc = new char[n2];
        if (n2 <= 16) {
            for (int i = 0; i < n2; ++i) {
                arrc[i] = this.bytes.getChar(n);
                n += 2;
            }
        } else {
            CharBuffer charBuffer = this.bytes.asCharBuffer();
            charBuffer.position(n / 2);
            charBuffer.get(arrc);
        }
        return arrc;
    }

    public static String getFullName(String charSequence, String charSequence2) {
        if (charSequence != null && ((String)charSequence).length() != 0) {
            if (((String)charSequence).indexOf(46) == -1) {
                if (((String)charSequence).charAt(((String)charSequence).length() - 1) != '/') {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((String)charSequence);
                    stringBuilder.append("/");
                    stringBuilder.append((String)charSequence2);
                    stringBuilder.append(ICU_RESOURCE_SUFFIX);
                    return stringBuilder.toString();
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)charSequence);
                stringBuilder.append((String)charSequence2);
                stringBuilder.append(ICU_RESOURCE_SUFFIX);
                return stringBuilder.toString();
            }
            charSequence = ((String)charSequence).replace('.', '/');
            if (((String)charSequence2).length() == 0) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(ICU_RESOURCE_SUFFIX);
                return ((StringBuilder)charSequence2).toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("_");
            stringBuilder.append((String)charSequence2);
            stringBuilder.append(ICU_RESOURCE_SUFFIX);
            return stringBuilder.toString();
        }
        if (((String)charSequence2).length() == 0) {
            return ULocale.getDefault().toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(ICU_RESOURCE_SUFFIX);
        return ((StringBuilder)charSequence).toString();
    }

    private int getIndexesInt(int n) {
        return this.bytes.getInt(n + 1 << 2);
    }

    private int getInt(int n) {
        return this.bytes.getInt(n);
    }

    private int[] getInts(int n, int n2) {
        int[] arrn = new int[n2];
        if (n2 <= 16) {
            int n3 = 0;
            int n4 = n;
            for (n = n3; n < n2; ++n) {
                arrn[n] = this.bytes.getInt(n4);
                n4 += 4;
            }
        } else {
            IntBuffer intBuffer = this.bytes.asIntBuffer();
            intBuffer.position(n / 4);
            intBuffer.get(arrn);
        }
        return arrn;
    }

    private String getKey16String(int n) {
        int n2 = this.localKeyLimit;
        if (n < n2) {
            return ICUResourceBundleReader.makeKeyStringFromBytes(this.keyBytes, n);
        }
        return ICUResourceBundleReader.makeKeyStringFromBytes(this.poolBundleReader.keyBytes, n - n2);
    }

    private String getKey32String(int n) {
        if (n >= 0) {
            return ICUResourceBundleReader.makeKeyStringFromBytes(this.keyBytes, n);
        }
        return ICUResourceBundleReader.makeKeyStringFromBytes(this.poolBundleReader.keyBytes, Integer.MAX_VALUE & n);
    }

    static ICUResourceBundleReader getReader(String object, String string, ClassLoader classLoader) {
        object = new ReaderCacheKey((String)object, string);
        if ((object = (ICUResourceBundleReader)CACHE.getInstance(object, classLoader)) == NULL_READER) {
            return null;
        }
        return object;
    }

    private int getResourceByteOffset(int n) {
        return n << 2;
    }

    private char[] getTable16KeyOffsets(int n) {
        char[] arrc = this.b16BitUnits;
        int n2 = n + 1;
        int n3 = arrc.charAt(n);
        if (n3 > 0) {
            arrc = new char[n3];
            if (n3 <= 16) {
                int n4 = 0;
                n = n2;
                while (n4 < n3) {
                    arrc[n4] = this.b16BitUnits.charAt(n);
                    ++n4;
                    ++n;
                }
            } else {
                CharBuffer charBuffer = this.b16BitUnits.duplicate();
                charBuffer.position(n2);
                charBuffer.get(arrc);
            }
            return arrc;
        }
        return emptyChars;
    }

    private int[] getTable32KeyOffsets(int n) {
        int n2 = this.getInt(n);
        if (n2 > 0) {
            return this.getInts(n + 4, n2);
        }
        return emptyInts;
    }

    private char[] getTableKeyOffsets(int n) {
        char c = this.bytes.getChar(n);
        if (c > '\u0000') {
            return this.getChars(n + 2, c);
        }
        return emptyChars;
    }

    private void init(ByteBuffer byteBuffer) throws IOException {
        this.dataVersion = ICUBinary.readHeader(byteBuffer, 1382380354, IS_ACCEPTABLE);
        int n = byteBuffer.get(16);
        this.bytes = ICUBinary.sliceWithOrder(byteBuffer);
        int n2 = this.bytes.remaining();
        this.rootRes = this.bytes.getInt(0);
        int n3 = this.getIndexesInt(0);
        int n4 = n3 & 255;
        if (n4 > 4) {
            int n5;
            if (n2 >= n4 + 1 << 2 && n2 >= (n5 = this.getIndexesInt(3)) << 2) {
                n2 = n5 - 1;
                if (n >= 3) {
                    this.poolStringIndexLimit = n3 >>> 8;
                }
                if (n4 > 5) {
                    n3 = this.getIndexesInt(5);
                    boolean bl = (n3 & 1) != 0;
                    this.noFallback = bl;
                    bl = (n3 & 2) != 0;
                    this.isPoolBundle = bl;
                    bl = (n3 & 4) != 0;
                    this.usesPoolBundle = bl;
                    this.poolStringIndexLimit |= (61440 & n3) << 12;
                    this.poolStringIndex16Limit = n3 >>> 16;
                }
                n = n4 + 1;
                n3 = this.getIndexesInt(1);
                if (n3 > n) {
                    if (this.isPoolBundle) {
                        this.keyBytes = new byte[n3 - n << 2];
                        this.bytes.position(n << 2);
                    } else {
                        this.localKeyLimit = n3 << 2;
                        this.keyBytes = new byte[this.localKeyLimit];
                    }
                    this.bytes.get(this.keyBytes);
                }
                if (n4 > 6) {
                    n = this.getIndexesInt(6);
                    if (n > n3) {
                        n = (n - n3) * 2;
                        this.bytes.position(n3 << 2);
                        this.b16BitUnits = this.bytes.asCharBuffer();
                        this.b16BitUnits.limit(n);
                        n2 |= n - 1;
                    } else {
                        this.b16BitUnits = EMPTY_16_BIT_UNITS;
                    }
                } else {
                    this.b16BitUnits = EMPTY_16_BIT_UNITS;
                }
                if (n4 > 7) {
                    this.poolCheckSum = this.getIndexesInt(7);
                }
                if (!this.isPoolBundle || this.b16BitUnits.length() > 1) {
                    this.resourceCache = new ResourceCache(n2);
                }
                this.bytes.position(0);
                return;
            }
            throw new ICUException("not enough bytes");
        }
        throw new ICUException("not enough indexes");
    }

    private boolean isNoInheritanceMarker(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        boolean bl = false;
        if (n2 != 0) {
            if (n == n2) {
                n = this.getResourceByteOffset(n2);
                if (this.getInt(n) == 3 && this.bytes.getChar(n + 4) == '\u2205' && this.bytes.getChar(n + 6) == '\u2205' && this.bytes.getChar(n + 8) == '\u2205') {
                    bl = true;
                }
                return bl;
            }
            if (ICUResourceBundleReader.RES_GET_TYPE(n) == 6) {
                n = this.poolStringIndexLimit;
                if (n2 < n) {
                    return this.poolBundleReader.isStringV2NoInheritanceMarker(n2);
                }
                return this.isStringV2NoInheritanceMarker(n2 - n);
            }
        }
        return false;
    }

    private boolean isStringV2NoInheritanceMarker(int n) {
        char c = this.b16BitUnits.charAt(n);
        boolean bl = false;
        boolean bl2 = false;
        if (c == '\u2205') {
            if (this.b16BitUnits.charAt(n + 1) == '\u2205' && this.b16BitUnits.charAt(n + 2) == '\u2205' && this.b16BitUnits.charAt(n + 3) == '\u0000') {
                bl2 = true;
            }
            return bl2;
        }
        if (c == '\udc03') {
            bl2 = this.b16BitUnits.charAt(n + 1) == '\u2205' && this.b16BitUnits.charAt(n + 2) == '\u2205' && this.b16BitUnits.charAt(n + 3) == '\u2205' ? true : bl;
            return bl2;
        }
        return false;
    }

    private static String makeKeyStringFromBytes(byte[] arrby, int n) {
        byte by;
        StringBuilder stringBuilder = new StringBuilder();
        while ((by = arrby[n]) != 0) {
            ++n;
            stringBuilder.append((char)by);
        }
        return stringBuilder.toString();
    }

    private String makeStringFromBytes(int n, int n2) {
        if (n2 <= 16) {
            StringBuilder stringBuilder = new StringBuilder(n2);
            int n3 = 0;
            int n4 = n;
            for (n = n3; n < n2; ++n) {
                stringBuilder.append(this.bytes.getChar(n4));
                n4 += 2;
            }
            return stringBuilder.toString();
        }
        CharBuffer charBuffer = this.bytes.asCharBuffer();
        return charBuffer.subSequence(n /= 2, n + n2).toString();
    }

    private void setKeyFromKey16(int n, UResource.Key key) {
        int n2 = this.localKeyLimit;
        if (n < n2) {
            key.setBytes(this.keyBytes, n);
        } else {
            key.setBytes(this.poolBundleReader.keyBytes, n - n2);
        }
    }

    private void setKeyFromKey32(int n, UResource.Key key) {
        if (n >= 0) {
            key.setBytes(this.keyBytes, n);
        } else {
            key.setBytes(this.poolBundleReader.keyBytes, Integer.MAX_VALUE & n);
        }
    }

    String getAlias(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (ICUResourceBundleReader.RES_GET_TYPE(n) == 3) {
            if (n2 == 0) {
                return emptyString;
            }
            Object object = this.resourceCache.get(n);
            if (object != null) {
                return (String)object;
            }
            n2 = this.getResourceByteOffset(n2);
            int n3 = this.getInt(n2);
            object = this.makeStringFromBytes(n2 + 4, n3);
            return (String)this.resourceCache.putIfAbsent(n, object, n3 * 2);
        }
        return null;
    }

    Array getArray(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_TYPE(n);
        if (!ICUResourceBundleReader.URES_IS_ARRAY(n2)) {
            return null;
        }
        int n3 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (n3 == 0) {
            return EMPTY_ARRAY;
        }
        Object object = this.resourceCache.get(n);
        if (object != null) {
            return (Array)object;
        }
        object = n2 == 8 ? new Array32(this, n3) : new Array16(this, n3);
        return (Array)this.resourceCache.putIfAbsent(n, object, 0);
    }

    ByteBuffer getBinary(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (ICUResourceBundleReader.RES_GET_TYPE(n) == 1) {
            ByteBuffer byteBuffer;
            if (n2 == 0) {
                return emptyByteBuffer.duplicate();
            }
            n = this.getInt(n2 = this.getResourceByteOffset(n2));
            if (n == 0) {
                return emptyByteBuffer.duplicate();
            }
            ByteBuffer byteBuffer2 = this.bytes.duplicate();
            byteBuffer2.position(n2 += 4).limit(n2 + n);
            byteBuffer2 = byteBuffer = ICUBinary.sliceWithOrder(byteBuffer2);
            if (!byteBuffer.isReadOnly()) {
                byteBuffer2 = byteBuffer.asReadOnlyBuffer();
            }
            return byteBuffer2;
        }
        return null;
    }

    byte[] getBinary(int n, byte[] object) {
        block8 : {
            byte[] arrby;
            int n2;
            int n3;
            block10 : {
                block9 : {
                    n3 = ICUResourceBundleReader.RES_GET_OFFSET(n);
                    if (ICUResourceBundleReader.RES_GET_TYPE(n) != 1) break block8;
                    if (n3 == 0) {
                        return emptyBytes;
                    }
                    n = this.getResourceByteOffset(n3);
                    n2 = this.getInt(n);
                    if (n2 == 0) {
                        return emptyBytes;
                    }
                    if (object == null) break block9;
                    arrby = object;
                    if (((byte[])object).length == n2) break block10;
                }
                arrby = new byte[n2];
            }
            n3 = n + 4;
            if (n2 <= 16) {
                n = 0;
                while (n < n2) {
                    arrby[n] = this.bytes.get(n3);
                    ++n;
                    ++n3;
                }
            } else {
                object = this.bytes.duplicate();
                ((Buffer)object).position(n3);
                ((ByteBuffer)object).get(arrby);
            }
            return arrby;
        }
        return null;
    }

    int[] getIntVector(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (ICUResourceBundleReader.RES_GET_TYPE(n) == 14) {
            if (n2 == 0) {
                return emptyInts;
            }
            n = this.getResourceByteOffset(n2);
            return this.getInts(n + 4, this.getInt(n));
        }
        return null;
    }

    boolean getNoFallback() {
        return this.noFallback;
    }

    int getRootResource() {
        return this.rootRes;
    }

    String getString(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (n != n2 && ICUResourceBundleReader.RES_GET_TYPE(n) != 6) {
            return null;
        }
        if (n2 == 0) {
            return emptyString;
        }
        if (n != n2) {
            int n3 = this.poolStringIndexLimit;
            if (n2 < n3) {
                return this.poolBundleReader.getStringV2(n);
            }
            return this.getStringV2(n - n3);
        }
        Object object = this.resourceCache.get(n);
        if (object != null) {
            return (String)object;
        }
        int n4 = this.getResourceByteOffset(n2);
        object = this.makeStringFromBytes(n4 + 4, this.getInt(n4));
        return (String)this.resourceCache.putIfAbsent(n, object, ((String)object).length() * 2);
    }

    String getStringV2(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        Object object = this.resourceCache.get(n);
        if (object != null) {
            return (String)object;
        }
        int n3 = this.b16BitUnits.charAt(n2);
        if ((n3 & -1024) != 56320) {
            char c;
            if (n3 == 0) {
                return emptyString;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((char)n3);
            while ((c = ((CharBuffer)(object = this.b16BitUnits)).charAt(++n2)) != '\u0000') {
                stringBuilder.append(c);
            }
            object = stringBuilder.toString();
        } else {
            if (n3 < 57327) {
                n3 &= 1023;
                ++n2;
            } else if (n3 < 57343) {
                n3 = n3 - 57327 << 16 | this.b16BitUnits.charAt(n2 + 1);
                n2 += 2;
            } else {
                n3 = this.b16BitUnits.charAt(n2 + 1) << 16 | this.b16BitUnits.charAt(n2 + 2);
                n2 += 3;
            }
            object = this.b16BitUnits.subSequence(n2, n2 + n3).toString();
        }
        return (String)this.resourceCache.putIfAbsent(n, object, ((String)object).length() * 2);
    }

    Table getTable(int n) {
        int n2 = ICUResourceBundleReader.RES_GET_TYPE(n);
        if (!ICUResourceBundleReader.URES_IS_TABLE(n2)) {
            return null;
        }
        int n3 = ICUResourceBundleReader.RES_GET_OFFSET(n);
        if (n3 == 0) {
            return EMPTY_TABLE;
        }
        Object object = this.resourceCache.get(n);
        if (object != null) {
            return (Table)object;
        }
        if (n2 == 2) {
            object = new Table1632(this, n3);
            n2 = ((Container)object).getSize() * 2;
        } else if (n2 == 5) {
            object = new Table16(this, n3);
            n2 = ((Container)object).getSize() * 2;
        } else {
            object = new Table32(this, n3);
            n2 = ((Container)object).getSize() * 4;
        }
        return (Table)this.resourceCache.putIfAbsent(n, object, n2);
    }

    boolean getUsesPoolBundle() {
        return this.usesPoolBundle;
    }

    VersionInfo getVersion() {
        return ICUBinary.getVersionInfoFromCompactInt(this.dataVersion);
    }

    static class Array
    extends Container
    implements UResource.Array {
        Array() {
        }

        @Override
        public boolean getValue(int n, UResource.Value value) {
            if (n >= 0 && n < this.size) {
                value = (ReaderValue)value;
                ((ReaderValue)value).res = this.getContainerResource(((ReaderValue)value).reader, n);
                return true;
            }
            return false;
        }
    }

    private static final class Array16
    extends Array {
        Array16(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            this.size = iCUResourceBundleReader.b16BitUnits.charAt(n);
            this.itemsOffset = n + 1;
        }

        @Override
        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return this.getContainer16Resource(iCUResourceBundleReader, n);
        }
    }

    private static final class Array32
    extends Array {
        Array32(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            n = iCUResourceBundleReader.getResourceByteOffset(n);
            this.size = iCUResourceBundleReader.getInt(n);
            this.itemsOffset = n + 4;
        }

        @Override
        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return this.getContainer32Resource(iCUResourceBundleReader, n);
        }
    }

    static class Container {
        protected int itemsOffset;
        protected int size;

        Container() {
        }

        protected int getContainer16Resource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            if (n >= 0 && this.size > n) {
                n = iCUResourceBundleReader.b16BitUnits.charAt(this.itemsOffset + n);
                if (n >= iCUResourceBundleReader.poolStringIndex16Limit) {
                    n = n - iCUResourceBundleReader.poolStringIndex16Limit + iCUResourceBundleReader.poolStringIndexLimit;
                }
                return 1610612736 | n;
            }
            return -1;
        }

        protected int getContainer32Resource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            if (n >= 0 && this.size > n) {
                return iCUResourceBundleReader.getInt(this.itemsOffset + n * 4);
            }
            return -1;
        }

        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return -1;
        }

        int getResource(ICUResourceBundleReader iCUResourceBundleReader, String string) {
            return this.getContainerResource(iCUResourceBundleReader, Integer.parseInt(string));
        }

        public final int getSize() {
            return this.size;
        }
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] arrby) {
            boolean bl;
            block3 : {
                block2 : {
                    boolean bl2 = false;
                    if (arrby[0] == 1 && (arrby[1] & 255) >= 1) break block2;
                    bl = bl2;
                    if (2 > arrby[0]) break block3;
                    bl = bl2;
                    if (arrby[0] > 3) break block3;
                }
                bl = true;
            }
            return bl;
        }
    }

    private static class ReaderCache
    extends SoftCache<ReaderCacheKey, ICUResourceBundleReader, ClassLoader> {
        private ReaderCache() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected ICUResourceBundleReader createInstance(ReaderCacheKey object, ClassLoader object2) {
            String string = ICUResourceBundleReader.getFullName(((ReaderCacheKey)object).baseName, ((ReaderCacheKey)object).localeID);
            try {
                Object object3;
                if (((ReaderCacheKey)object).baseName != null && ((ReaderCacheKey)object).baseName.startsWith("android/icu/impl/data/icudt63b")) {
                    object3 = ICUBinary.getData((ClassLoader)object2, string, string.substring("android/icu/impl/data/icudt63b".length() + 1));
                    if (object3 != null) return new ICUResourceBundleReader((ByteBuffer)object3, ((ReaderCacheKey)object).baseName, ((ReaderCacheKey)object).localeID, (ClassLoader)object2);
                    return NULL_READER;
                }
                object3 = ICUData.getStream((ClassLoader)object2, string);
                if (object3 == null) {
                    return NULL_READER;
                }
                object3 = ICUBinary.getByteBufferFromInputStreamAndCloseStream((InputStream)object3);
                return new ICUResourceBundleReader((ByteBuffer)object3, ((ReaderCacheKey)object).baseName, ((ReaderCacheKey)object).localeID, (ClassLoader)object2);
            }
            catch (IOException iOException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Data file ");
                ((StringBuilder)object2).append(string);
                ((StringBuilder)object2).append(" is corrupt - ");
                ((StringBuilder)object2).append(iOException.getMessage());
                throw new ICUUncheckedIOException(((StringBuilder)object2).toString(), iOException);
            }
        }
    }

    private static class ReaderCacheKey {
        final String baseName;
        final String localeID;

        ReaderCacheKey(String string, String string2) {
            String string3 = ICUResourceBundleReader.emptyString;
            if (string == null) {
                string = ICUResourceBundleReader.emptyString;
            }
            this.baseName = string;
            if (string2 == null) {
                string2 = string3;
            }
            this.localeID = string2;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof ReaderCacheKey)) {
                return false;
            }
            object = (ReaderCacheKey)object;
            if (!this.baseName.equals(((ReaderCacheKey)object).baseName) || !this.localeID.equals(((ReaderCacheKey)object).localeID)) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return this.baseName.hashCode() ^ this.localeID.hashCode();
        }
    }

    static class ReaderValue
    extends UResource.Value {
        ICUResourceBundleReader reader;
        int res;

        ReaderValue() {
        }

        private String[] getStringArray(Array array) {
            String[] arrstring = new String[array.size];
            for (int i = 0; i < array.size; ++i) {
                int n = array.getContainerResource(this.reader, i);
                String string = this.reader.getString(n);
                if (string != null) {
                    arrstring[i] = string;
                    continue;
                }
                throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
            }
            return arrstring;
        }

        @Override
        public String getAliasString() {
            String string = this.reader.getAlias(this.res);
            if (string != null) {
                return string;
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public UResource.Array getArray() {
            Array array = this.reader.getArray(this.res);
            if (array != null) {
                return array;
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public ByteBuffer getBinary() {
            ByteBuffer byteBuffer = this.reader.getBinary(this.res);
            if (byteBuffer != null) {
                return byteBuffer;
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public int getInt() {
            if (ICUResourceBundleReader.RES_GET_TYPE(this.res) == 7) {
                return ICUResourceBundleReader.RES_GET_INT(this.res);
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public int[] getIntVector() {
            int[] arrn = this.reader.getIntVector(this.res);
            if (arrn != null) {
                return arrn;
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public String getString() {
            String string = this.reader.getString(this.res);
            if (string != null) {
                return string;
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public String[] getStringArray() {
            Array array = this.reader.getArray(this.res);
            if (array != null) {
                return this.getStringArray(array);
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public String[] getStringArrayOrStringAsArray() {
            Object object = this.reader.getArray(this.res);
            if (object != null) {
                return this.getStringArray((Array)object);
            }
            object = this.reader.getString(this.res);
            if (object != null) {
                return new String[]{object};
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public String getStringOrFirstOfArray() {
            Object object = this.reader.getString(this.res);
            if (object != null) {
                return object;
            }
            object = this.reader.getArray(this.res);
            if (object != null && ((Array)object).size > 0) {
                int n = ((Container)object).getContainerResource(this.reader, 0);
                object = this.reader.getString(n);
                if (object != null) {
                    return object;
                }
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public UResource.Table getTable() {
            Table table = this.reader.getTable(this.res);
            if (table != null) {
                return table;
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public int getType() {
            return PUBLIC_TYPES[ICUResourceBundleReader.RES_GET_TYPE(this.res)];
        }

        @Override
        public int getUInt() {
            if (ICUResourceBundleReader.RES_GET_TYPE(this.res) == 7) {
                return ICUResourceBundleReader.RES_GET_UINT(this.res);
            }
            throw new UResourceTypeMismatchException(ICUResourceBundleReader.emptyString);
        }

        @Override
        public boolean isNoInheritanceMarker() {
            return this.reader.isNoInheritanceMarker(this.res);
        }
    }

    private static final class ResourceCache {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int NEXT_BITS = 6;
        private static final int ROOT_BITS = 7;
        private static final int SIMPLE_LENGTH = 32;
        private int[] keys = new int[32];
        private int length;
        private int levelBitsList;
        private int maxOffsetBits = 28;
        private Level rootLevel;
        private Object[] values = new Object[32];

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        ResourceCache(int n) {
            while (n <= 134217727) {
                n <<= 1;
                --this.maxOffsetBits;
            }
            n = this.maxOffsetBits + 2;
            if (n <= 7) {
                this.levelBitsList = n;
                return;
            }
            if (n < 10) {
                this.levelBitsList = n - 3 | 48;
                return;
            }
            this.levelBitsList = 7;
            int n2 = n - 7;
            n = 4;
            do {
                if (n2 <= 6) {
                    this.levelBitsList |= n2 << n;
                    return;
                }
                if (n2 < 9) {
                    this.levelBitsList |= (n2 - 3 | 48) << n;
                    return;
                }
                this.levelBitsList = 6 << n | this.levelBitsList;
                n2 -= 6;
                n += 4;
            } while (true);
        }

        private int findSimple(int n) {
            return Arrays.binarySearch(this.keys, 0, this.length, n);
        }

        private int makeKey(int n) {
            int n2 = ICUResourceBundleReader.RES_GET_TYPE(n);
            n2 = n2 == 6 ? 1 : (n2 == 5 ? 3 : (n2 == 9 ? 2 : 0));
            return ICUResourceBundleReader.RES_GET_OFFSET(n) | n2 << this.maxOffsetBits;
        }

        private static final Object putIfCleared(Object[] arrobject, int n, Object object, int n2) {
            Object object2 = arrobject[n];
            if (!(object2 instanceof SoftReference)) {
                return object2;
            }
            if ((object2 = ((SoftReference)object2).get()) != null) {
                return object2;
            }
            object2 = CacheValue.futureInstancesWillBeStrong() ? object : new SoftReference<Object>(object);
            arrobject[n] = object2;
            return object;
        }

        private static boolean storeDirectly(int n) {
            boolean bl = n < 24 || CacheValue.futureInstancesWillBeStrong();
            return bl;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        Object get(int n) {
            synchronized (this) {
                Object object;
                Object object2;
                block7 : {
                    block6 : {
                        if (this.length < 0) break block6;
                        if ((n = this.findSimple(n)) < 0) return null;
                        object = this.values[n];
                        break block7;
                    }
                    object = object2 = this.rootLevel.get(this.makeKey(n));
                    if (object2 != null) break block7;
                    return null;
                }
                object2 = object;
                if (!(object instanceof SoftReference)) return object2;
                return ((SoftReference)object).get();
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        Object putIfAbsent(int n, Object softReference, int n2) {
            synchronized (this) {
                int n3;
                Level level;
                block13 : {
                    SoftReference<Object> softReference2;
                    block15 : {
                        block14 : {
                            block12 : {
                                if (this.length < 0) return this.rootLevel.putIfAbsent(this.makeKey(n), softReference, n2);
                                n3 = this.findSimple(n);
                                if (n3 < 0) break block12;
                                return ResourceCache.putIfCleared(this.values, n3, softReference, n2);
                            }
                            if (this.length >= 32) break block13;
                            if (n3 < this.length) {
                                System.arraycopy(this.keys, n3, this.keys, n3 + 1, this.length - n3);
                                System.arraycopy(this.values, n3, this.values, n3 + 1, this.length - n3);
                            }
                            ++this.length;
                            this.keys[n3] = n;
                            Object[] arrobject = this.values;
                            if (!ResourceCache.storeDirectly(n2)) break block14;
                            softReference2 = softReference;
                            break block15;
                        }
                        softReference2 = new SoftReference<Object>(softReference);
                    }
                    arrobject[n3] = softReference2;
                    return softReference;
                }
                this.rootLevel = level = new Level(this.levelBitsList, 0);
                for (n3 = 0; n3 < 32; ++n3) {
                    this.rootLevel.putIfAbsent(this.makeKey(this.keys[n3]), this.values[n3], 0);
                }
                this.keys = null;
                this.values = null;
                this.length = -1;
                return this.rootLevel.putIfAbsent(this.makeKey(n), softReference, n2);
            }
        }

        private static final class Level {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            int[] keys;
            int levelBitsList;
            int mask;
            int shift;
            Object[] values;

            Level(int n, int n2) {
                this.levelBitsList = n;
                this.shift = n2;
                n = 1 << (n & 15);
                this.mask = n - 1;
                this.keys = new int[n];
                this.values = new Object[n];
            }

            Object get(int n) {
                Level level;
                int n2 = n >> this.shift & this.mask;
                int n3 = this.keys[n2];
                if (n3 == n) {
                    return this.values[n2];
                }
                if (n3 == 0 && (level = (Level)this.values[n2]) != null) {
                    return level.get(n);
                }
                return null;
            }

            Object putIfAbsent(int n, Object object, int n2) {
                Object object2 = this.keys;
                int n3 = this.shift;
                int n4 = n >> n3 & this.mask;
                int n5 = object2[n4];
                if (n5 == n) {
                    return ResourceCache.putIfCleared(this.values, n4, object, n2);
                }
                if (n5 == 0) {
                    Object[] arrobject = this.values;
                    Object object3 = (Level)arrobject[n4];
                    if (object3 != null) {
                        return ((Level)object3).putIfAbsent(n, object, n2);
                    }
                    object2[n4] = n;
                    object3 = ResourceCache.storeDirectly(n2) ? object : new SoftReference<Object>(object);
                    arrobject[n4] = object3;
                    return object;
                }
                int n6 = this.levelBitsList;
                object2 = new Level(n6 >> 4, n3 + (n6 & 15));
                n3 = n5 >> ((Level)object2).shift & ((Level)object2).mask;
                object2.keys[n3] = n5;
                Object[] arrobject = ((Level)object2).values;
                Object[] arrobject2 = this.values;
                arrobject[n3] = arrobject2[n4];
                this.keys[n4] = 0;
                arrobject2[n4] = object2;
                return ((Level)object2).putIfAbsent(n, object, n2);
            }
        }

    }

    static class Table
    extends Container
    implements UResource.Table {
        private static final int URESDATA_ITEM_NOT_FOUND = -1;
        protected int[] key32Offsets;
        protected char[] keyOffsets;

        Table() {
        }

        int findTableItem(ICUResourceBundleReader iCUResourceBundleReader, CharSequence charSequence) {
            int n = 0;
            int n2 = this.size;
            while (n < n2) {
                int n3 = n + n2 >>> 1;
                char[] arrc = this.keyOffsets;
                int n4 = arrc != null ? iCUResourceBundleReader.compareKeys(charSequence, arrc[n3]) : iCUResourceBundleReader.compareKeys32(charSequence, this.key32Offsets[n3]);
                if (n4 < 0) {
                    n2 = n3;
                    continue;
                }
                if (n4 > 0) {
                    n = n3 + 1;
                    continue;
                }
                return n3;
            }
            return -1;
        }

        String getKey(ICUResourceBundleReader object, int n) {
            if (n >= 0 && this.size > n) {
                char[] arrc = this.keyOffsets;
                object = arrc != null ? ((ICUResourceBundleReader)object).getKey16String(arrc[n]) : ((ICUResourceBundleReader)object).getKey32String(this.key32Offsets[n]);
                return object;
            }
            return null;
        }

        @Override
        public boolean getKeyAndValue(int n, UResource.Key key, UResource.Value value) {
            if (n >= 0 && n < this.size) {
                value = (ReaderValue)value;
                if (this.keyOffsets != null) {
                    ((ReaderValue)value).reader.setKeyFromKey16(this.keyOffsets[n], key);
                } else {
                    ((ReaderValue)value).reader.setKeyFromKey32(this.key32Offsets[n], key);
                }
                ((ReaderValue)value).res = this.getContainerResource(((ReaderValue)value).reader, n);
                return true;
            }
            return false;
        }

        @Override
        int getResource(ICUResourceBundleReader iCUResourceBundleReader, String string) {
            return this.getContainerResource(iCUResourceBundleReader, this.findTableItem(iCUResourceBundleReader, string));
        }
    }

    private static final class Table16
    extends Table {
        Table16(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            this.keyOffsets = iCUResourceBundleReader.getTable16KeyOffsets(n);
            this.size = this.keyOffsets.length;
            this.itemsOffset = n + 1 + this.size;
        }

        @Override
        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return this.getContainer16Resource(iCUResourceBundleReader, n);
        }
    }

    private static final class Table1632
    extends Table {
        Table1632(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            n = iCUResourceBundleReader.getResourceByteOffset(n);
            this.keyOffsets = iCUResourceBundleReader.getTableKeyOffsets(n);
            this.size = this.keyOffsets.length;
            this.itemsOffset = (this.size + 2 & -2) * 2 + n;
        }

        @Override
        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return this.getContainer32Resource(iCUResourceBundleReader, n);
        }
    }

    private static final class Table32
    extends Table {
        Table32(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            n = iCUResourceBundleReader.getResourceByteOffset(n);
            this.key32Offsets = iCUResourceBundleReader.getTable32KeyOffsets(n);
            this.size = this.key32Offsets.length;
            this.itemsOffset = (this.size + 1) * 4 + n;
        }

        @Override
        int getContainerResource(ICUResourceBundleReader iCUResourceBundleReader, int n) {
            return this.getContainer32Resource(iCUResourceBundleReader, n);
        }
    }

}

