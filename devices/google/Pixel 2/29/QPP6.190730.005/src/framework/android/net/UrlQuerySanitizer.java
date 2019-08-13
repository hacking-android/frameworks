/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlQuerySanitizer {
    private static final Pattern plusOrPercent;
    private static final ValueSanitizer sAllButNulAndAngleBracketsLegal;
    private static final ValueSanitizer sAllButNulLegal;
    private static final ValueSanitizer sAllButWhitespaceLegal;
    private static final ValueSanitizer sAllIllegal;
    private static final ValueSanitizer sAmpAndSpaceLegal;
    private static final ValueSanitizer sAmpLegal;
    private static final ValueSanitizer sSpaceLegal;
    private static final ValueSanitizer sURLLegal;
    private static final ValueSanitizer sUrlAndSpaceLegal;
    private boolean mAllowUnregisteredParamaters;
    private final HashMap<String, String> mEntries = new HashMap();
    private final ArrayList<ParameterValuePair> mEntriesList = new ArrayList();
    private boolean mPreferFirstRepeatedParameter;
    private final HashMap<String, ValueSanitizer> mSanitizers = new HashMap();
    private ValueSanitizer mUnregisteredParameterValueSanitizer = UrlQuerySanitizer.getAllIllegal();

    static {
        sAllIllegal = new IllegalCharacterValueSanitizer(0);
        sAllButNulLegal = new IllegalCharacterValueSanitizer(1535);
        sAllButWhitespaceLegal = new IllegalCharacterValueSanitizer(1532);
        sURLLegal = new IllegalCharacterValueSanitizer(404);
        sUrlAndSpaceLegal = new IllegalCharacterValueSanitizer(405);
        sAmpLegal = new IllegalCharacterValueSanitizer(128);
        sAmpAndSpaceLegal = new IllegalCharacterValueSanitizer(129);
        sSpaceLegal = new IllegalCharacterValueSanitizer(1);
        sAllButNulAndAngleBracketsLegal = new IllegalCharacterValueSanitizer(1439);
        plusOrPercent = Pattern.compile("[+%]");
    }

    public UrlQuerySanitizer() {
    }

    public UrlQuerySanitizer(String string2) {
        this.setAllowUnregisteredParamaters(true);
        this.parseUrl(string2);
    }

    public static final ValueSanitizer getAllButNulAndAngleBracketsLegal() {
        return sAllButNulAndAngleBracketsLegal;
    }

    public static final ValueSanitizer getAllButNulLegal() {
        return sAllButNulLegal;
    }

    public static final ValueSanitizer getAllButWhitespaceLegal() {
        return sAllButWhitespaceLegal;
    }

    public static final ValueSanitizer getAllIllegal() {
        return sAllIllegal;
    }

    public static final ValueSanitizer getAmpAndSpaceLegal() {
        return sAmpAndSpaceLegal;
    }

    public static final ValueSanitizer getAmpLegal() {
        return sAmpLegal;
    }

    public static final ValueSanitizer getSpaceLegal() {
        return sSpaceLegal;
    }

    public static final ValueSanitizer getUrlAndSpaceLegal() {
        return sUrlAndSpaceLegal;
    }

    public static final ValueSanitizer getUrlLegal() {
        return sURLLegal;
    }

    protected void addSanitizedEntry(String string2, String string3) {
        this.mEntriesList.add(new ParameterValuePair(string2, string3));
        if (this.mPreferFirstRepeatedParameter && this.mEntries.containsKey(string2)) {
            return;
        }
        this.mEntries.put(string2, string3);
    }

    protected void clear() {
        this.mEntries.clear();
        this.mEntriesList.clear();
    }

    protected int decodeHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 65 + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        return -1;
    }

    public boolean getAllowUnregisteredParamaters() {
        return this.mAllowUnregisteredParamaters;
    }

    public ValueSanitizer getEffectiveValueSanitizer(String object) {
        ValueSanitizer valueSanitizer = this.getValueSanitizer((String)object);
        object = valueSanitizer;
        if (valueSanitizer == null) {
            object = valueSanitizer;
            if (this.mAllowUnregisteredParamaters) {
                object = this.getUnregisteredParameterValueSanitizer();
            }
        }
        return object;
    }

    public List<ParameterValuePair> getParameterList() {
        return this.mEntriesList;
    }

    public Set<String> getParameterSet() {
        return this.mEntries.keySet();
    }

    public boolean getPreferFirstRepeatedParameter() {
        return this.mPreferFirstRepeatedParameter;
    }

    public ValueSanitizer getUnregisteredParameterValueSanitizer() {
        return this.mUnregisteredParameterValueSanitizer;
    }

    public String getValue(String string2) {
        return this.mEntries.get(string2);
    }

    public ValueSanitizer getValueSanitizer(String string2) {
        return this.mSanitizers.get(string2);
    }

    public boolean hasParameter(String string2) {
        return this.mEntries.containsKey(string2);
    }

    protected boolean isHexDigit(char c) {
        boolean bl = this.decodeHexDigit(c) >= 0;
        return bl;
    }

    protected void parseEntry(String string2, String string3) {
        ValueSanitizer valueSanitizer = this.getEffectiveValueSanitizer(string2 = this.unescape(string2));
        if (valueSanitizer == null) {
            return;
        }
        this.addSanitizedEntry(string2, valueSanitizer.sanitize(this.unescape(string3)));
    }

    public void parseQuery(String object) {
        this.clear();
        object = new StringTokenizer((String)object, "&");
        while (((StringTokenizer)object).hasMoreElements()) {
            String string2 = ((StringTokenizer)object).nextToken();
            if (string2.length() <= 0) continue;
            int n = string2.indexOf(61);
            if (n < 0) {
                this.parseEntry(string2, "");
                continue;
            }
            this.parseEntry(string2.substring(0, n), string2.substring(n + 1));
        }
    }

    public void parseUrl(String string2) {
        int n = string2.indexOf(63);
        string2 = n >= 0 ? string2.substring(n + 1) : "";
        this.parseQuery(string2);
    }

    public void registerParameter(String string2, ValueSanitizer valueSanitizer) {
        if (valueSanitizer == null) {
            this.mSanitizers.remove(string2);
        }
        this.mSanitizers.put(string2, valueSanitizer);
    }

    public void registerParameters(String[] arrstring, ValueSanitizer valueSanitizer) {
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            this.mSanitizers.put(arrstring[i], valueSanitizer);
        }
    }

    public void setAllowUnregisteredParamaters(boolean bl) {
        this.mAllowUnregisteredParamaters = bl;
    }

    public void setPreferFirstRepeatedParameter(boolean bl) {
        this.mPreferFirstRepeatedParameter = bl;
    }

    public void setUnregisteredParameterValueSanitizer(ValueSanitizer valueSanitizer) {
        this.mUnregisteredParameterValueSanitizer = valueSanitizer;
    }

    public String unescape(String string2) {
        Object object = plusOrPercent.matcher(string2);
        if (!((Matcher)object).find()) {
            return string2;
        }
        int n = ((Matcher)object).start();
        int n2 = string2.length();
        object = new StringBuilder(n2);
        ((StringBuilder)object).append(string2.substring(0, n));
        while (n < n2) {
            char c;
            int n3;
            char c2 = string2.charAt(n);
            if (c2 == '+') {
                c2 = ' ';
                n3 = n;
                c = c2;
            } else {
                n3 = n;
                c = c2;
                if (c2 == '%') {
                    n3 = n;
                    c = c2;
                    if (n + 2 < n2) {
                        char c3 = string2.charAt(n + 1);
                        char c4 = string2.charAt(n + 2);
                        n3 = n;
                        c = c2;
                        if (this.isHexDigit(c3)) {
                            n3 = n;
                            c = c2;
                            if (this.isHexDigit(c4)) {
                                c2 = (char)(this.decodeHexDigit(c3) * 16 + this.decodeHexDigit(c4));
                                n3 = n + 2;
                                c = c2;
                            }
                        }
                    }
                }
            }
            ((StringBuilder)object).append(c);
            n = n3 + 1;
        }
        return ((StringBuilder)object).toString();
    }

    public static class IllegalCharacterValueSanitizer
    implements ValueSanitizer {
        public static final int ALL_BUT_NUL_AND_ANGLE_BRACKETS_LEGAL = 1439;
        public static final int ALL_BUT_NUL_LEGAL = 1535;
        public static final int ALL_BUT_WHITESPACE_LEGAL = 1532;
        public static final int ALL_ILLEGAL = 0;
        public static final int ALL_OK = 2047;
        public static final int ALL_WHITESPACE_OK = 3;
        public static final int AMP_AND_SPACE_LEGAL = 129;
        public static final int AMP_LEGAL = 128;
        public static final int AMP_OK = 128;
        public static final int DQUOTE_OK = 8;
        public static final int GT_OK = 64;
        private static final String JAVASCRIPT_PREFIX = "javascript:";
        public static final int LT_OK = 32;
        private static final int MIN_SCRIPT_PREFIX_LENGTH = Math.min("javascript:".length(), "vbscript:".length());
        public static final int NON_7_BIT_ASCII_OK = 4;
        public static final int NUL_OK = 512;
        public static final int OTHER_WHITESPACE_OK = 2;
        public static final int PCT_OK = 256;
        public static final int SCRIPT_URL_OK = 1024;
        public static final int SPACE_LEGAL = 1;
        public static final int SPACE_OK = 1;
        public static final int SQUOTE_OK = 16;
        public static final int URL_AND_SPACE_LEGAL = 405;
        public static final int URL_LEGAL = 404;
        private static final String VBSCRIPT_PREFIX = "vbscript:";
        private int mFlags;

        public IllegalCharacterValueSanitizer(int n) {
            this.mFlags = n;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean characterIsLegal(char c) {
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            boolean bl5 = false;
            boolean bl6 = false;
            boolean bl7 = false;
            boolean bl8 = false;
            boolean bl9 = false;
            boolean bl10 = false;
            if (c != '\u0000') {
                if (c != ' ') {
                    if (c != '\"') {
                        if (c != '<') {
                            if (c != '>') {
                                switch (c) {
                                    default: {
                                        switch (c) {
                                            default: {
                                                if (c >= ' ') {
                                                    if (c < '') return true;
                                                }
                                                bl = bl10;
                                                if (c < '') return bl;
                                                bl = bl10;
                                                if ((this.mFlags & 4) == 0) return bl;
                                                return true;
                                            }
                                            case '\'': {
                                                if ((this.mFlags & 16) == 0) return bl;
                                                return true;
                                            }
                                            case '&': {
                                                bl = bl2;
                                                if ((this.mFlags & 128) == 0) return bl;
                                                return true;
                                            }
                                            case '%': 
                                        }
                                        bl = bl3;
                                        if ((this.mFlags & 256) == 0) return bl;
                                        return true;
                                    }
                                    case '\t': 
                                    case '\n': 
                                    case '\u000b': 
                                    case '\f': 
                                    case '\r': 
                                }
                                bl = bl4;
                                if ((this.mFlags & 2) == 0) return bl;
                                return true;
                            }
                            bl = bl5;
                            if ((this.mFlags & 64) == 0) return bl;
                            return true;
                        }
                        bl = bl6;
                        if ((32 & this.mFlags) == 0) return bl;
                        return true;
                    }
                    bl = bl7;
                    if ((this.mFlags & 8) == 0) return bl;
                    return true;
                }
                bl = bl8;
                if ((this.mFlags & 1) == 0) return bl;
                return true;
            }
            bl = bl9;
            if ((this.mFlags & 512) == 0) return bl;
            return true;
        }

        private boolean isWhitespace(char c) {
            if (c != ' ') {
                switch (c) {
                    default: {
                        return false;
                    }
                    case '\t': 
                    case '\n': 
                    case '\u000b': 
                    case '\f': 
                    case '\r': 
                }
            }
            return true;
        }

        private String trimWhitespace(String string2) {
            int n;
            int n2;
            int n3 = 0;
            int n4 = n2 = string2.length() - 1;
            do {
                n = n4;
                if (n3 > n4) break;
                n = n4;
                if (!this.isWhitespace(string2.charAt(n3))) break;
                ++n3;
            } while (true);
            while (n >= n3 && this.isWhitespace(string2.charAt(n))) {
                --n;
            }
            if (n3 == 0 && n == n2) {
                return string2;
            }
            return string2.substring(n3, n + 1);
        }

        @Override
        public String sanitize(String charSequence) {
            String string2;
            if (charSequence == null) {
                return null;
            }
            int n = ((String)charSequence).length();
            if ((this.mFlags & 1024) != 0 && n >= MIN_SCRIPT_PREFIX_LENGTH && ((string2 = ((String)charSequence).toLowerCase(Locale.ROOT)).startsWith(JAVASCRIPT_PREFIX) || string2.startsWith(VBSCRIPT_PREFIX))) {
                return "";
            }
            string2 = charSequence;
            if ((this.mFlags & 3) == 0) {
                string2 = this.trimWhitespace((String)charSequence);
                n = string2.length();
            }
            charSequence = new StringBuilder(n);
            for (int i = 0; i < n; ++i) {
                int n2;
                int n3 = n2 = string2.charAt(i);
                if (!this.characterIsLegal((char)n2)) {
                    int n4;
                    n3 = (this.mFlags & 1) != 0 ? (n4 = 32) : (n4 = 95);
                }
                ((StringBuilder)charSequence).append((char)n3);
            }
            return ((StringBuilder)charSequence).toString();
        }
    }

    public class ParameterValuePair {
        public String mParameter;
        public String mValue;

        public ParameterValuePair(String string2, String string3) {
            this.mParameter = string2;
            this.mValue = string3;
        }
    }

    public static interface ValueSanitizer {
        public String sanitize(String var1);
    }

}

