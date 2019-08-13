/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CharTrie;
import android.icu.impl.ICUBinary;
import android.icu.impl.StringPrepDataReader;
import android.icu.impl.Trie;
import android.icu.impl.UBiDiProps;
import android.icu.lang.UCharacter;
import android.icu.text.Normalizer;
import android.icu.text.StringPrepParseException;
import android.icu.text.UCharacterIterator;
import android.icu.text.UTF16;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.VersionInfo;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

public final class StringPrep {
    public static final int ALLOW_UNASSIGNED = 1;
    private static final WeakReference<StringPrep>[] CACHE;
    private static final int CHECK_BIDI_ON = 2;
    public static final int DEFAULT = 0;
    private static final int DELETE = 3;
    private static final int FOUR_UCHARS_MAPPING_INDEX_START = 6;
    private static final int INDEX_MAPPING_DATA_SIZE = 1;
    private static final int INDEX_TOP = 16;
    private static final int MAP = 1;
    private static final int MAX_INDEX_VALUE = 16319;
    private static final int MAX_PROFILE = 13;
    private static final int NORMALIZATION_ON = 1;
    private static final int NORM_CORRECTNS_LAST_UNI_VERSION = 2;
    private static final int ONE_UCHAR_MAPPING_INDEX_START = 3;
    private static final int OPTIONS = 7;
    private static final String[] PROFILE_NAMES;
    private static final int PROHIBITED = 2;
    public static final int RFC3491_NAMEPREP = 0;
    public static final int RFC3530_NFS4_CIS_PREP = 3;
    public static final int RFC3530_NFS4_CS_PREP = 1;
    public static final int RFC3530_NFS4_CS_PREP_CI = 2;
    public static final int RFC3530_NFS4_MIXED_PREP_PREFIX = 4;
    public static final int RFC3530_NFS4_MIXED_PREP_SUFFIX = 5;
    public static final int RFC3722_ISCSI = 6;
    public static final int RFC3920_NODEPREP = 7;
    public static final int RFC3920_RESOURCEPREP = 8;
    public static final int RFC4011_MIB = 9;
    public static final int RFC4013_SASLPREP = 10;
    public static final int RFC4505_TRACE = 11;
    public static final int RFC4518_LDAP = 12;
    public static final int RFC4518_LDAP_CI = 13;
    private static final int THREE_UCHARS_MAPPING_INDEX_START = 5;
    private static final int TWO_UCHARS_MAPPING_INDEX_START = 4;
    private static final int TYPE_LIMIT = 4;
    private static final int TYPE_THRESHOLD = 65520;
    private static final int UNASSIGNED = 0;
    private UBiDiProps bdp;
    private boolean checkBiDi;
    private boolean doNFKC;
    private int[] indexes;
    private char[] mappingData;
    private VersionInfo normCorrVer;
    private CharTrie sprepTrie;
    private VersionInfo sprepUniVer;

    static {
        PROFILE_NAMES = new String[]{"rfc3491", "rfc3530cs", "rfc3530csci", "rfc3491", "rfc3530mixp", "rfc3491", "rfc3722", "rfc3920node", "rfc3920res", "rfc4011", "rfc4013", "rfc4505", "rfc4518", "rfc4518ci"};
        CACHE = new WeakReference[14];
    }

    public StringPrep(InputStream inputStream) throws IOException {
        this(ICUBinary.getByteBufferFromInputStreamAndCloseStream(inputStream));
    }

    private StringPrep(ByteBuffer comparable) throws IOException {
        StringPrepDataReader stringPrepDataReader = new StringPrepDataReader((ByteBuffer)comparable);
        this.indexes = stringPrepDataReader.readIndexes(16);
        this.sprepTrie = new CharTrie((ByteBuffer)comparable, null);
        this.mappingData = stringPrepDataReader.read(this.indexes[1] / 2);
        int n = this.indexes[7];
        boolean bl = false;
        boolean bl2 = (n & 1) > 0;
        this.doNFKC = bl2;
        bl2 = bl;
        if ((this.indexes[7] & 2) > 0) {
            bl2 = true;
        }
        this.checkBiDi = bl2;
        this.sprepUniVer = StringPrep.getVersionInfo(stringPrepDataReader.getUnicodeVersion());
        this.normCorrVer = StringPrep.getVersionInfo(this.indexes[2]);
        comparable = UCharacter.getUnicodeVersion();
        if (((VersionInfo)comparable).compareTo(this.sprepUniVer) < 0 && ((VersionInfo)comparable).compareTo(this.normCorrVer) < 0 && (1 & this.indexes[7]) > 0) {
            throw new IOException("Normalization Correction version not supported");
        }
        if (this.checkBiDi) {
            this.bdp = UBiDiProps.INSTANCE;
        }
    }

    private char getCodePointValue(int n) {
        return this.sprepTrie.getCodePointValue(n);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static StringPrep getInstance(int n) {
        if (n < 0) throw new IllegalArgumentException("Bad profile type");
        if (n > 13) throw new IllegalArgumentException("Bad profile type");
        StringPrep stringPrep = null;
        WeakReference<StringPrep>[] arrweakReference = CACHE;
        synchronized (arrweakReference) {
            Object object = CACHE[n];
            if (object != null) {
                stringPrep = (StringPrep)object.get();
            }
            object = stringPrep;
            if (stringPrep != null) return object;
            object = new StringBuilder();
            object.append(PROFILE_NAMES[n]);
            object.append(".spp");
            object = ICUBinary.getRequiredData(object.toString());
            if (object != null) {
                try {
                    stringPrep = new StringPrep((ByteBuffer)object);
                }
                catch (IOException iOException) {
                    object = new ICUUncheckedIOException(iOException);
                    throw object;
                }
            }
            object = stringPrep;
            if (stringPrep == null) return object;
            object = CACHE;
            WeakReference<StringPrep> weakReference = new WeakReference<StringPrep>(stringPrep);
            object[n] = weakReference;
            return stringPrep;
        }
    }

    private static final void getValues(char c, Values values) {
        values.reset();
        if (c == '\u0000') {
            values.type = 4;
        } else if (c >= '\ufff0') {
            values.type = c - 65520;
        } else {
            values.type = 1;
            if ((c & 2) > 0) {
                values.isIndex = true;
                values.value = c >> 2;
            } else {
                values.isIndex = false;
                values.value = c << 16 >> 16;
                values.value >>= 2;
            }
            if (c >> 2 == 16319) {
                values.type = 3;
                values.isIndex = false;
                values.value = 0;
            }
        }
    }

    private static VersionInfo getVersionInfo(int n) {
        return VersionInfo.getInstance(n >> 24 & 255, n >> 16 & 255, n >> 8 & 255, n & 255);
    }

    private static VersionInfo getVersionInfo(byte[] arrby) {
        if (arrby.length != 4) {
            return null;
        }
        return VersionInfo.getInstance(arrby[0], arrby[1], arrby[2], arrby[3]);
    }

    private StringBuffer map(UCharacterIterator uCharacterIterator, int n) throws StringPrepParseException {
        Values values = new Values();
        StringBuffer stringBuffer = new StringBuffer();
        boolean bl = (n & 1) > 0;
        do {
            int n2;
            n = n2 = uCharacterIterator.nextCodePoint();
            if (n2 == -1) break;
            StringPrep.getValues(this.getCodePointValue(n), values);
            if (values.type == 0 && !bl) {
                throw new StringPrepParseException("An unassigned code point was found in the input", 3, uCharacterIterator.getText(), uCharacterIterator.getIndex());
            }
            if (values.type == 1) {
                if (values.isIndex) {
                    n2 = values.value;
                    int[] arrn = this.indexes;
                    if (n2 >= arrn[3] && n2 < arrn[4]) {
                        n = 1;
                    } else {
                        arrn = this.indexes;
                        if (n2 >= arrn[4] && n2 < arrn[5]) {
                            n = 2;
                        } else {
                            arrn = this.indexes;
                            if (n2 >= arrn[5] && n2 < arrn[6]) {
                                n = 3;
                            } else {
                                n = this.mappingData[n2];
                                ++n2;
                            }
                        }
                    }
                    stringBuffer.append(this.mappingData, n2, n);
                    continue;
                }
                n -= values.value;
            } else if (values.type == 3) continue;
            UTF16.append(stringBuffer, n);
        } while (true);
        return stringBuffer;
    }

    private StringBuffer normalize(StringBuffer stringBuffer) {
        return new StringBuffer(Normalizer.normalize(stringBuffer.toString(), Normalizer.NFKC, 32));
    }

    public String prepare(String string, int n) throws StringPrepParseException {
        return this.prepare(UCharacterIterator.getInstance(string), n).toString();
    }

    public StringBuffer prepare(UCharacterIterator object, int n) throws StringPrepParseException {
        int n2;
        Object object2;
        object = object2 = this.map((UCharacterIterator)object, n);
        if (this.doNFKC) {
            object = this.normalize((StringBuffer)object2);
        }
        object2 = UCharacterIterator.getInstance((StringBuffer)object);
        Values values = new Values();
        int n3 = 23;
        int n4 = 23;
        n = -1;
        int n5 = -1;
        boolean bl = false;
        boolean bl2 = false;
        while ((n2 = ((UCharacterIterator)object2).nextCodePoint()) != -1) {
            StringPrep.getValues(this.getCodePointValue(n2), values);
            if (values.type != 2) {
                if (!this.checkBiDi) continue;
                n2 = this.bdp.getClass(n2);
                int n6 = n4;
                if (n4 == 23) {
                    n6 = n2;
                }
                int n7 = n5;
                boolean bl3 = bl2;
                if (n2 == 0) {
                    bl3 = true;
                    n7 = ((UCharacterIterator)object2).getIndex() - 1;
                }
                if (n2 != 1) {
                    n3 = n2;
                    n4 = n6;
                    n5 = n7;
                    bl2 = bl3;
                    if (n2 != 13) continue;
                }
                bl = true;
                n = ((UCharacterIterator)object2).getIndex() - 1;
                n3 = n2;
                n4 = n6;
                n5 = n7;
                bl2 = bl3;
                continue;
            }
            throw new StringPrepParseException("A prohibited code point was found in the input", 2, ((UCharacterIterator)object2).getText(), values.value);
        }
        if (this.checkBiDi) {
            if (bl2 && bl) {
                object = ((UCharacterIterator)object2).getText();
                if (n <= n5) {
                    n = n5;
                }
                throw new StringPrepParseException("The input does not conform to the rules for BiDi code points.", 4, (String)object, n);
            }
            if (bl && (n4 != 1 && n4 != 13 || n3 != 1 && n3 != 13)) {
                object = ((UCharacterIterator)object2).getText();
                if (n > n5) {
                    n5 = n;
                }
                throw new StringPrepParseException("The input does not conform to the rules for BiDi code points.", 4, (String)object, n5);
            }
        }
        return object;
    }

    private static final class Values {
        boolean isIndex;
        int type;
        int value;

        private Values() {
        }

        public void reset() {
            this.isIndex = false;
            this.value = 0;
            this.type = -1;
        }
    }

}

