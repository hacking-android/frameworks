/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.impl.UCharacterNameReader;
import android.icu.impl.UCharacterUtility;
import android.icu.impl.Utility;
import android.icu.lang.UCharacter;
import android.icu.text.UnicodeSet;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.MissingResourceException;

public final class UCharacterName {
    static final int EXTENDED_CATEGORY_ = 33;
    private static final String FILE_NAME_ = "unames.icu";
    private static final int GROUP_MASK_ = 31;
    private static final int GROUP_SHIFT_ = 5;
    public static final UCharacterName INSTANCE;
    private static final int LEAD_SURROGATE_ = 31;
    public static final int LINES_PER_GROUP_ = 32;
    private static final int NON_CHARACTER_ = 30;
    private static final int OFFSET_HIGH_OFFSET_ = 1;
    private static final int OFFSET_LOW_OFFSET_ = 2;
    private static final int SINGLE_NIBBLE_MAX_ = 11;
    private static final int TRAIL_SURROGATE_ = 32;
    private static final String[] TYPE_NAMES_;
    private static final String UNKNOWN_TYPE_NAME_ = "unknown";
    private int[] m_ISOCommentSet_ = new int[8];
    private AlgorithmName[] m_algorithm_;
    public int m_groupcount_ = 0;
    private char[] m_groupinfo_;
    private char[] m_grouplengths_ = new char[33];
    private char[] m_groupoffsets_ = new char[33];
    int m_groupsize_ = 0;
    private byte[] m_groupstring_;
    private int m_maxISOCommentLength_;
    private int m_maxNameLength_;
    private int[] m_nameSet_ = new int[8];
    private byte[] m_tokenstring_;
    private char[] m_tokentable_;
    private int[] m_utilIntBuffer_ = new int[2];
    private StringBuffer m_utilStringBuffer_ = new StringBuffer();

    static {
        try {
            UCharacterName uCharacterName;
            INSTANCE = uCharacterName = new UCharacterName();
        }
        catch (IOException iOException) {
            throw new MissingResourceException("Could not construct UCharacterName. Missing unames.icu", "", "");
        }
        TYPE_NAMES_ = new String[]{"unassigned", "uppercase letter", "lowercase letter", "titlecase letter", "modifier letter", "other letter", "non spacing mark", "enclosing mark", "combining spacing mark", "decimal digit number", "letter number", "other number", "space separator", "line separator", "paragraph separator", "control", "format", "private use area", "surrogate", "dash punctuation", "start punctuation", "end punctuation", "connector punctuation", "other punctuation", "math symbol", "currency symbol", "modifier symbol", "other symbol", "initial punctuation", "final punctuation", "noncharacter", "lead surrogate", "trail surrogate"};
    }

    private UCharacterName() throws IOException {
        new UCharacterNameReader(ICUBinary.getRequiredData(FILE_NAME_)).read(this);
    }

    private static int add(int[] arrn, String string) {
        int n = string.length();
        for (int i = n - 1; i >= 0; --i) {
            UCharacterName.add(arrn, string.charAt(i));
        }
        return n;
    }

    private static int add(int[] arrn, StringBuffer stringBuffer) {
        int n = stringBuffer.length();
        for (int i = n - 1; i >= 0; --i) {
            UCharacterName.add(arrn, stringBuffer.charAt(i));
        }
        return n;
    }

    private static void add(int[] arrn, char c) {
        int n = c >>> 5;
        arrn[n] = arrn[n] | 1 << (c & 31);
    }

    private int addAlgorithmName(int n) {
        for (int i = this.m_algorithm_.length - 1; i >= 0; --i) {
            int n2 = this.m_algorithm_[i].add(this.m_nameSet_, n);
            int n3 = n;
            if (n2 > n) {
                n3 = n2;
            }
            n = n3;
        }
        return n;
    }

    private int addExtendedName(int n) {
        int n2 = n;
        for (int i = UCharacterName.TYPE_NAMES_.length - 1; i >= 0; --i) {
            int n3 = UCharacterName.add(this.m_nameSet_, TYPE_NAMES_[i]) + 9;
            n = n2;
            if (n3 > n2) {
                n = n3;
            }
            n2 = n;
        }
        return n2;
    }

    private void addGroupName(int n) {
        int n2 = 0;
        char[] arrc = new char[34];
        char[] arrc2 = new char[34];
        byte[] arrby = new byte[this.m_tokentable_.length];
        for (int i = 0; i < this.m_groupcount_; ++i) {
            int n3 = this.getGroupLengths(i, arrc, arrc2);
            int n4 = n;
            for (int j = 0; j < 32; ++j) {
                int n5 = arrc[j] + n3;
                int n6 = arrc2[j];
                if (n6 == 0) {
                    n6 = n2;
                    n = n4;
                } else {
                    int[] arrn = this.addGroupName(n5, n6, arrby, this.m_nameSet_);
                    n = n4;
                    if (arrn[0] > n4) {
                        n = arrn[0];
                    }
                    int n7 = n5 + arrn[1];
                    if (arrn[1] >= n6) {
                        n6 = n2;
                    } else {
                        n6 -= arrn[1];
                        arrn = this.addGroupName(n7, n6, arrby, this.m_nameSet_);
                        n4 = n;
                        if (arrn[0] > n) {
                            n4 = arrn[0];
                        }
                        int n8 = arrn[1];
                        if (arrn[1] >= n6) {
                            n6 = n2;
                            n = n4;
                        } else {
                            n5 = n6 - arrn[1];
                            n6 = n2;
                            n = n4;
                            if (this.addGroupName(n7 + n8, n5, arrby, this.m_ISOCommentSet_)[1] > n2) {
                                n6 = n5;
                                n = n4;
                            }
                        }
                    }
                }
                n2 = n6;
                n4 = n;
            }
            n = n4;
        }
        this.m_maxISOCommentLength_ = n2;
        this.m_maxNameLength_ = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int[] addGroupName(int n, int n2, byte[] arrby, int[] arrn) {
        int n3;
        int n4 = 0;
        int n5 = 0;
        do {
            n3 = n5;
            if (n5 >= n2) break;
            byte[] arrby2 = this.m_groupstring_;
            int n6 = arrby2[n + n5] & 255;
            n3 = n5 + 1;
            if (n6 == 59) break;
            Object object = this.m_tokentable_;
            if (n6 >= ((char[])object).length) {
                UCharacterName.add(arrn, (char)n6);
                ++n4;
                n5 = n3;
                continue;
            }
            int n7 = object[n6 & 255];
            n5 = n3;
            int n8 = n6;
            int n9 = n7;
            if (n7 == 65534) {
                n7 = (char)(arrby2[n + n3] & 255 | n6 << 8);
                n9 = object[n7];
                n5 = n3 + 1;
                n8 = n7;
            }
            if (n9 == 65535) {
                UCharacterName.add(arrn, (char)n8);
                ++n4;
                continue;
            }
            n3 = n7 = arrby[n8];
            if (n7 == 0) {
                object = this.m_utilStringBuffer_;
                synchronized (object) {
                    this.m_utilStringBuffer_.setLength(0);
                    UCharacterUtility.getNullTermByteSubString(this.m_utilStringBuffer_, this.m_tokenstring_, n9);
                    n3 = (byte)UCharacterName.add(arrn, this.m_utilStringBuffer_);
                }
                arrby[n8] = (byte)n3;
            }
            n4 += n3;
        } while (true);
        arrby = this.m_utilIntBuffer_;
        arrby[0] = n4;
        arrby[1] = n3;
        return arrby;
    }

    private static boolean contains(int[] arrn, char c) {
        int n = arrn[c >>> 5];
        boolean bl = true;
        if ((n & 1 << (c & 31)) == 0) {
            bl = false;
        }
        return bl;
    }

    private void convert(int[] arrn, UnicodeSet unicodeSet) {
        char c;
        unicodeSet.clear();
        if (!this.initNameSetsLengths()) {
            return;
        }
        char c2 = c = '\u00ff';
        while (c2 > '\u0000') {
            if (UCharacterName.contains(arrn, c2)) {
                unicodeSet.add(c2);
            }
            c2 = c = (char)(c2 - '\u0001');
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getAlgName(int n, int n2) {
        if (n2 != 0) {
            if (n2 != 2) return null;
        }
        StringBuffer stringBuffer = this.m_utilStringBuffer_;
        synchronized (stringBuffer) {
            this.m_utilStringBuffer_.setLength(0);
            n2 = this.m_algorithm_.length - 1;
            while (n2 >= 0) {
                if (this.m_algorithm_[n2].contains(n)) {
                    this.m_algorithm_[n2].appendName(n, this.m_utilStringBuffer_);
                    return this.m_utilStringBuffer_.toString();
                }
                --n2;
            }
            return null;
        }
    }

    public static int getCodepointMSB(int n) {
        return n >> 5;
    }

    private static int getExtendedChar(String string, int n) {
        if (string.charAt(0) == '<') {
            int n2;
            if (n == 2 && string.charAt(n = string.length() - 1) == '>' && (n2 = string.lastIndexOf(45)) >= 0) {
                int n3 = n2 + 1;
                try {
                    n2 = Integer.parseInt(string.substring(n3, n), 16);
                }
                catch (NumberFormatException numberFormatException) {
                    return -1;
                }
                string = string.substring(1, n3 - 1);
                n3 = TYPE_NAMES_.length;
                for (n = 0; n < n3; ++n) {
                    if (string.compareTo(TYPE_NAMES_[n]) != 0) continue;
                    if (UCharacterName.getType(n2) == n) {
                        return n2;
                    }
                    break;
                }
            }
            return -1;
        }
        return -2;
    }

    private int getGroupChar(int n, char[] arrc, String string, int n2) {
        int n3 = string.length();
        for (int i = 0; i <= 32; ++i) {
            int n4;
            int n5;
            int n6 = 0;
            int n7 = arrc[i];
            int n8 = n;
            int n9 = n7;
            if (n2 != 0) {
                n5 = 2;
                n8 = n;
                n9 = n7;
                if (n2 != 2) {
                    if (n2 == 4) {
                        n9 = n7;
                    } else {
                        n5 = n2;
                        n9 = n7;
                    }
                    do {
                        n8 = n + UCharacterUtility.skipByteSubString(this.m_groupstring_, n, n9, (byte)59);
                        n7 = n9 - (n8 - n);
                        n4 = n5 - 1;
                        n = n8;
                        n9 = n7;
                        n5 = n4;
                    } while (n4 > 0);
                    n9 = n7;
                }
            }
            n5 = 0;
            n = n6;
            while (n5 < n9 && n != -1 && n < n3) {
                byte[] arrby = this.m_groupstring_;
                byte by = arrby[n8 + n5];
                n7 = n5 + 1;
                char[] arrc2 = this.m_tokentable_;
                if (by >= arrc2.length) {
                    if (string.charAt(n) != (by & 255)) {
                        n = -1;
                        n5 = n7;
                        continue;
                    }
                    ++n;
                    n5 = n7;
                    continue;
                }
                n4 = arrc2[by & 255];
                n5 = n7;
                n6 = n4;
                if (n4 == 65534) {
                    n6 = arrc2[arrby[n8 + n7] & 255 | by << 8];
                    n5 = n7 + 1;
                }
                if (n6 == 65535) {
                    if (string.charAt(n) != (by & 255)) {
                        n = -1;
                        continue;
                    }
                    ++n;
                    continue;
                }
                n = UCharacterUtility.compareNullTermByteSubString(string, this.m_tokenstring_, n, n6);
            }
            if (n3 == n && (n5 == n9 || this.m_groupstring_[n8 + n5] == 59)) {
                return i;
            }
            n = n8 + n9;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int getGroupChar(String string, int n) {
        synchronized (this) {
            int n2 = 0;
            while (n2 < this.m_groupcount_) {
                char c;
                int n3 = this.getGroupChar(this.getGroupLengths(n2, this.m_groupoffsets_, this.m_grouplengths_), this.m_grouplengths_, string, c);
                if (n3 != -1) {
                    c = this.m_groupinfo_[this.m_groupsize_ * n2];
                    return c << 5 | n3;
                }
                ++n2;
            }
            return -1;
        }
    }

    public static int getGroupLimit(int n) {
        return (n << 5) + 32;
    }

    public static int getGroupMin(int n) {
        return n << 5;
    }

    public static int getGroupMinFromCodepoint(int n) {
        return n & -32;
    }

    public static int getGroupOffset(int n) {
        return n & 31;
    }

    private static int getType(int n) {
        int n2;
        if (UCharacterUtility.isNonCharacter(n)) {
            return 30;
        }
        int n3 = n2 = UCharacter.getType(n);
        if (n2 == 18) {
            n3 = n <= 56319 ? 31 : 32;
        }
        return n3;
    }

    private boolean initNameSetsLengths() {
        if (this.m_maxNameLength_ > 0) {
            return true;
        }
        for (int i = "0123456789ABCDEF<>-".length() - 1; i >= 0; --i) {
            UCharacterName.add(this.m_nameSet_, "0123456789ABCDEF<>-".charAt(i));
        }
        this.m_maxNameLength_ = this.addAlgorithmName(0);
        this.m_maxNameLength_ = this.addExtendedName(this.m_maxNameLength_);
        this.addGroupName(this.m_maxNameLength_);
        return true;
    }

    public int getAlgorithmEnd(int n) {
        return this.m_algorithm_[n].m_rangeend_;
    }

    public int getAlgorithmLength() {
        return this.m_algorithm_.length;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getAlgorithmName(int n, int n2) {
        StringBuffer stringBuffer = this.m_utilStringBuffer_;
        synchronized (stringBuffer) {
            this.m_utilStringBuffer_.setLength(0);
            this.m_algorithm_[n].appendName(n2, this.m_utilStringBuffer_);
            return this.m_utilStringBuffer_.toString();
        }
    }

    public int getAlgorithmStart(int n) {
        return this.m_algorithm_[n].m_rangestart_;
    }

    public int getCharFromName(int n, String arralgorithmName) {
        if (n < 4 && arralgorithmName != null && arralgorithmName.length() != 0) {
            int n2 = UCharacterName.getExtendedChar(arralgorithmName.toLowerCase(Locale.ENGLISH), n);
            if (n2 >= -1) {
                return n2;
            }
            String string = arralgorithmName.toUpperCase(Locale.ENGLISH);
            if (n == 0 || n == 2) {
                n2 = 0;
                arralgorithmName = this.m_algorithm_;
                if (arralgorithmName != null) {
                    n2 = arralgorithmName.length;
                }
                --n2;
                while (n2 >= 0) {
                    int n3 = this.m_algorithm_[n2].getChar(string);
                    if (n3 >= 0) {
                        return n3;
                    }
                    --n2;
                }
            }
            if (n == 2) {
                n = n2 = this.getGroupChar(string, 0);
                if (n2 == -1) {
                    n = this.getGroupChar(string, 3);
                }
            } else {
                n = this.getGroupChar(string, n);
            }
            return n;
        }
        return -1;
    }

    public void getCharNameCharacters(UnicodeSet unicodeSet) {
        this.convert(this.m_nameSet_, unicodeSet);
    }

    public String getExtendedName(int n) {
        String string;
        String string2 = string = this.getName(n, 0);
        if (string == null) {
            string2 = this.getExtendedOr10Name(n);
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getExtendedOr10Name(int n) {
        Object object = null;
        if (false) return object;
        int n2 = UCharacterName.getType(n);
        object = n2 >= ((String[])(object = TYPE_NAMES_)).length ? UNKNOWN_TYPE_NAME_ : object[n2];
        StringBuffer stringBuffer = this.m_utilStringBuffer_;
        synchronized (stringBuffer) {
            this.m_utilStringBuffer_.setLength(0);
            this.m_utilStringBuffer_.append('<');
            this.m_utilStringBuffer_.append((String)object);
            this.m_utilStringBuffer_.append('-');
            object = Integer.toHexString(n).toUpperCase(Locale.ENGLISH);
            n = 4 - ((String)object).length();
            do {
                if (n <= 0) {
                    this.m_utilStringBuffer_.append((String)object);
                    this.m_utilStringBuffer_.append('>');
                    return this.m_utilStringBuffer_.toString();
                }
                this.m_utilStringBuffer_.append('0');
                --n;
            } while (true);
        }
    }

    public int getGroup(int n) {
        int n2 = this.m_groupcount_;
        int n3 = UCharacterName.getCodepointMSB(n);
        n = 0;
        while (n < n2 - 1) {
            int n4 = n + n2 >> 1;
            if (n3 < this.getGroupMSB(n4)) {
                n2 = n4;
                continue;
            }
            n = n4;
        }
        return n;
    }

    public int getGroupLengths(int n, char[] arrc, char[] arrc2) {
        int n2 = 65535;
        char[] arrc3 = this.m_groupinfo_;
        int n3 = UCharacterUtility.toInt(arrc3[(n *= this.m_groupsize_) + 1], arrc3[n + 2]);
        arrc[0] = (char)(false ? 1 : 0);
        int n4 = 0;
        n = n2;
        while (n4 < 32) {
            byte by = this.m_groupstring_[n3];
            for (n2 = 4; n2 >= 0; n2 -= 4) {
                byte by2 = (byte)(by >> n2 & 15);
                if (n == 65535 && by2 > 11) {
                    n = (char)(by2 - 12 << 4);
                    continue;
                }
                arrc2[n4] = n != 65535 ? (char)((char)((n | by2) + 12)) : (char)((char)by2);
                if (n4 < 32) {
                    arrc[n4 + 1] = (char)(arrc[n4] + arrc2[n4]);
                }
                n = 65535;
                ++n4;
            }
            ++n3;
        }
        return n3;
    }

    public int getGroupMSB(int n) {
        if (n >= this.m_groupcount_) {
            return -1;
        }
        return this.m_groupinfo_[this.m_groupsize_ * n];
    }

    public String getGroupName(int n, int n2) {
        synchronized (this) {
            block4 : {
                int n3 = UCharacterName.getCodepointMSB(n);
                int n4 = this.getGroup(n);
                if (n3 != this.m_groupinfo_[this.m_groupsize_ * n4]) break block4;
                n4 = this.getGroupLengths(n4, this.m_groupoffsets_, this.m_grouplengths_);
                n &= 31;
                String string = this.getGroupName(this.m_groupoffsets_[n] + n4, this.m_grouplengths_[n], n2);
                return string;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getGroupName(int n, int n2, int n3) {
        int n4;
        char[] arrc;
        int n5;
        int n6 = n;
        int n7 = n2;
        if (n3 != 0) {
            n6 = n;
            n7 = n2;
            if (n3 != 2) {
                arrc = this.m_tokentable_;
                if (59 < arrc.length && arrc[59] != '\uffff') {
                    n7 = 0;
                    n6 = n;
                } else {
                    n7 = n3 == 4 ? 2 : n3;
                    do {
                        n6 = n + UCharacterUtility.skipByteSubString(this.m_groupstring_, n, n2, (byte)59);
                        n5 = n2 - (n6 - n);
                        n7 = n4 = n7 - 1;
                        n = n6;
                        n2 = n5;
                    } while (n4 > 0);
                    n7 = n5;
                }
            }
        }
        arrc = this.m_utilStringBuffer_;
        synchronized (arrc) {
            this.m_utilStringBuffer_.setLength(0);
            n = 0;
            while (n < n7) {
                byte by = this.m_groupstring_[n6 + n];
                n5 = n + 1;
                if (by >= this.m_tokentable_.length) {
                    if (by == 59) break;
                    this.m_utilStringBuffer_.append(by);
                    n = n5;
                    continue;
                }
                n4 = this.m_tokentable_[by & 255];
                n = n5;
                n2 = n4;
                if (n4 == 65534) {
                    n2 = this.m_tokentable_[by << 8 | this.m_groupstring_[n6 + n5] & 255];
                    n = n5 + 1;
                }
                if (n2 == 65535) {
                    if (by == 59) {
                        if (this.m_utilStringBuffer_.length() != 0 || n3 != 2) break;
                        continue;
                    }
                    this.m_utilStringBuffer_.append((char)(by & 255));
                    continue;
                }
                UCharacterUtility.getNullTermByteSubString(this.m_utilStringBuffer_, this.m_tokenstring_, n2);
            }
            if (this.m_utilStringBuffer_.length() <= 0) return null;
            return this.m_utilStringBuffer_.toString();
        }
    }

    public void getISOCommentCharacters(UnicodeSet unicodeSet) {
        this.convert(this.m_ISOCommentSet_, unicodeSet);
    }

    public int getMaxCharNameLength() {
        if (this.initNameSetsLengths()) {
            return this.m_maxNameLength_;
        }
        return 0;
    }

    public int getMaxISOCommentLength() {
        if (this.initNameSetsLengths()) {
            return this.m_maxISOCommentLength_;
        }
        return 0;
    }

    public String getName(int n, int n2) {
        block2 : {
            String string;
            block4 : {
                block3 : {
                    if (n < 0 || n > 1114111 || n2 > 4) break block2;
                    String string2 = this.getAlgName(n, n2);
                    if (string2 == null) break block3;
                    string = string2;
                    if (string2.length() != 0) break block4;
                }
                string = n2 == 2 ? this.getExtendedName(n) : this.getGroupName(n, n2);
            }
            return string;
        }
        return null;
    }

    boolean setAlgorithm(AlgorithmName[] arralgorithmName) {
        if (arralgorithmName != null && arralgorithmName.length != 0) {
            this.m_algorithm_ = arralgorithmName;
            return true;
        }
        return false;
    }

    boolean setGroup(char[] arrc, byte[] arrby) {
        if (arrc != null && arrby != null && arrc.length > 0 && arrby.length > 0) {
            this.m_groupinfo_ = arrc;
            this.m_groupstring_ = arrby;
            return true;
        }
        return false;
    }

    boolean setGroupCountSize(int n, int n2) {
        if (n > 0 && n2 > 0) {
            this.m_groupcount_ = n;
            this.m_groupsize_ = n2;
            return true;
        }
        return false;
    }

    boolean setToken(char[] arrc, byte[] arrby) {
        if (arrc != null && arrby != null && arrc.length > 0 && arrby.length > 0) {
            this.m_tokentable_ = arrc;
            this.m_tokenstring_ = arrby;
            return true;
        }
        return false;
    }

    static final class AlgorithmName {
        static final int TYPE_0_ = 0;
        static final int TYPE_1_ = 1;
        private char[] m_factor_;
        private byte[] m_factorstring_;
        private String m_prefix_;
        private int m_rangeend_;
        private int m_rangestart_;
        private byte m_type_;
        private int[] m_utilIntBuffer_ = new int[256];
        private StringBuffer m_utilStringBuffer_ = new StringBuffer();
        private byte m_variant_;

        AlgorithmName() {
        }

        private boolean compareFactorString(int[] arrn, int n, String string, int n2) {
            int n3 = this.m_factor_.length;
            if (arrn != null && n == n3) {
                int n4 = 0;
                n = n2;
                int n5 = n3 - 1;
                for (n2 = 0; n2 <= n5; ++n2) {
                    char c = this.m_factor_[n2];
                    n3 = UCharacterUtility.compareNullTermByteSubString(string, this.m_factorstring_, n, n4 = UCharacterUtility.skipNullTermByteSubString(this.m_factorstring_, n4, arrn[n2]));
                    if (n3 < 0) {
                        return false;
                    }
                    n = n4;
                    if (n2 != n5) {
                        n = UCharacterUtility.skipNullTermByteSubString(this.m_factorstring_, n4, c - arrn[n2]);
                    }
                    n4 = n;
                    n = n3;
                }
                return n == string.length();
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private String getFactorString(int[] object, int n) {
            int n2 = this.m_factor_.length;
            if (object == null) return null;
            if (n != n2) {
                return null;
            }
            StringBuffer stringBuffer = this.m_utilStringBuffer_;
            synchronized (stringBuffer) {
                this.m_utilStringBuffer_.setLength(0);
                n = 0;
                int n3 = n2 - 1;
                n2 = 0;
                while (n2 <= n3) {
                    int n4;
                    char c = this.m_factor_[n2];
                    n = UCharacterUtility.skipNullTermByteSubString(this.m_factorstring_, n, object[n2]);
                    n = n4 = UCharacterUtility.getNullTermByteSubString(this.m_utilStringBuffer_, this.m_factorstring_, n);
                    if (n2 != n3) {
                        n = UCharacterUtility.skipNullTermByteSubString(this.m_factorstring_, n4, c - object[n2] - 1);
                    }
                    ++n2;
                }
                return this.m_utilStringBuffer_.toString();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        int add(int[] arrn, int n) {
            int n2;
            block9 : {
                int n3;
                block8 : {
                    block7 : {
                        n3 = UCharacterName.add(arrn, this.m_prefix_);
                        n2 = this.m_type_;
                        if (n2 == 0) break block7;
                        if (n2 == 1) break block8;
                        n2 = n3;
                        break block9;
                    }
                    n2 = n3 + this.m_variant_;
                    break block9;
                }
                int n4 = this.m_variant_ - 1;
                do {
                    n2 = n3;
                    if (n4 <= 0) break;
                    int n5 = 0;
                    int n6 = 0;
                    for (n2 = this.m_factor_[n4]; n2 > 0; --n2) {
                        int n7;
                        StringBuffer stringBuffer = this.m_utilStringBuffer_;
                        synchronized (stringBuffer) {
                            this.m_utilStringBuffer_.setLength(0);
                            n7 = UCharacterUtility.getNullTermByteSubString(this.m_utilStringBuffer_, this.m_factorstring_, n6);
                            UCharacterName.add(arrn, this.m_utilStringBuffer_);
                            n6 = n5;
                            if (this.m_utilStringBuffer_.length() > n5) {
                                n6 = this.m_utilStringBuffer_.length();
                            }
                        }
                        n5 = n6;
                        n6 = n7;
                    }
                    n3 += n5;
                    --n4;
                } while (true);
            }
            if (n2 > n) {
                return n2;
            }
            return n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void appendName(int n, StringBuffer stringBuffer) {
            stringBuffer.append(this.m_prefix_);
            int n2 = this.m_type_;
            if (n2 == 0) {
                stringBuffer.append(Utility.hex(n, this.m_variant_));
                return;
            }
            if (n2 != 1) {
                return;
            }
            n2 = n - this.m_rangestart_;
            int[] arrn = this.m_utilIntBuffer_;
            int[] arrn2 = this.m_utilIntBuffer_;
            synchronized (arrn2) {
                n = this.m_variant_ - 1;
                do {
                    if (n <= 0) {
                        arrn[0] = n2;
                        stringBuffer.append(this.getFactorString(arrn, this.m_variant_));
                        return;
                    }
                    int n3 = this.m_factor_[n] & 255;
                    arrn[n] = n2 % n3;
                    n2 /= n3;
                    --n;
                } while (true);
            }
        }

        boolean contains(int n) {
            boolean bl = this.m_rangestart_ <= n && n <= this.m_rangeend_;
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        int getChar(String string) {
            int n = this.m_prefix_.length();
            if (string.length() < n) return -1;
            if (!this.m_prefix_.equals(string.substring(0, n))) {
                return -1;
            }
            int n2 = this.m_type_;
            if (n2 != 0) {
                if (n2 != 1) {
                    return -1;
                }
            } else {
                try {
                    int n3 = Integer.parseInt(string.substring(n), 16);
                    if (this.m_rangestart_ > n3) return -1;
                    n2 = this.m_rangeend_;
                    if (n3 > n2) return -1;
                    return n3;
                }
                catch (NumberFormatException numberFormatException) {
                    return -1;
                }
            }
            n2 = this.m_rangestart_;
            while (n2 <= this.m_rangeend_) {
                int n4 = n2 - this.m_rangestart_;
                int[] arrn = this.m_utilIntBuffer_;
                int[] arrn2 = this.m_utilIntBuffer_;
                synchronized (arrn2) {
                    int n5;
                    for (int i = this.m_variant_ - 1; i > 0; n4 /= n5, --i) {
                        n5 = this.m_factor_[i] & 255;
                        arrn[i] = n4 % n5;
                    }
                    arrn[0] = n4;
                    if (this.compareFactorString(arrn, this.m_variant_, string, n)) {
                        return n2;
                    }
                }
                ++n2;
            }
            return -1;
        }

        boolean setFactor(char[] arrc) {
            if (arrc.length == this.m_variant_) {
                this.m_factor_ = arrc;
                return true;
            }
            return false;
        }

        boolean setFactorString(byte[] arrby) {
            this.m_factorstring_ = arrby;
            return true;
        }

        boolean setInfo(int n, int n2, byte by, byte by2) {
            if (n >= 0 && n <= n2 && n2 <= 1114111 && (by == 0 || by == 1)) {
                this.m_rangestart_ = n;
                this.m_rangeend_ = n2;
                this.m_type_ = by;
                this.m_variant_ = by2;
                return true;
            }
            return false;
        }

        boolean setPrefix(String string) {
            if (string != null && string.length() > 0) {
                this.m_prefix_ = string;
                return true;
            }
            return false;
        }
    }

}

