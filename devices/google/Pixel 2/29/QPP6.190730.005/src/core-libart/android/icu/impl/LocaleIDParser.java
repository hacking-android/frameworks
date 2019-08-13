/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.LocaleIDs;
import android.icu.impl.locale.AsciiUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class LocaleIDParser {
    private static final char COMMA = ',';
    private static final char DONE = '\uffff';
    private static final char DOT = '.';
    private static final char HYPHEN = '-';
    private static final char ITEM_SEPARATOR = ';';
    private static final char KEYWORD_ASSIGN = '=';
    private static final char KEYWORD_SEPARATOR = '@';
    private static final char UNDERSCORE = '_';
    String baseName;
    private StringBuilder buffer;
    private boolean canonicalize;
    private boolean hadCountry;
    private char[] id;
    private int index;
    Map<String, String> keywords;

    public LocaleIDParser(String string) {
        this(string, false);
    }

    public LocaleIDParser(String string, boolean bl) {
        this.id = string.toCharArray();
        this.index = 0;
        this.buffer = new StringBuilder(this.id.length + 5);
        this.canonicalize = bl;
    }

    private void addSeparator() {
        this.append('_');
    }

    private void append(char c) {
        this.buffer.append(c);
    }

    private void append(String string) {
        this.buffer.append(string);
    }

    private boolean atTerminator() {
        int n = this.index;
        char[] arrc = this.id;
        boolean bl = n >= arrc.length || this.isTerminator(arrc[n]);
        return bl;
    }

    private Comparator<String> getKeyComparator() {
        return new Comparator<String>(){

            @Override
            public int compare(String string, String string2) {
                return string.compareTo(string2);
            }
        };
    }

    private String getKeyword() {
        int n = this.index;
        while (!LocaleIDParser.isDoneOrKeywordAssign(this.next())) {
        }
        --this.index;
        return AsciiUtil.toLowerString(new String(this.id, n, this.index - n).trim());
    }

    private String getString(int n) {
        return this.buffer.substring(n);
    }

    private String getValue() {
        int n = this.index;
        while (!LocaleIDParser.isDoneOrItemSeparator(this.next())) {
        }
        --this.index;
        return new String(this.id, n, this.index - n).trim();
    }

    private boolean haveExperimentalLanguagePrefix() {
        char[] arrc = this.id;
        if (arrc.length > 2) {
            boolean bl = true;
            char c = arrc[1];
            if (c == '-' || c == '_') {
                c = this.id[0];
                boolean bl2 = bl;
                if (c != 'x') {
                    bl2 = bl;
                    if (c != 'X') {
                        bl2 = bl;
                        if (c != 'i') {
                            bl2 = c == 'I' ? bl : false;
                        }
                    }
                }
                return bl2;
            }
        }
        return false;
    }

    private boolean haveKeywordAssign() {
        char[] arrc;
        for (int i = this.index; i < (arrc = this.id).length; ++i) {
            if (arrc[i] != '=') continue;
            return true;
        }
        return false;
    }

    private static boolean isDoneOrItemSeparator(char c) {
        boolean bl = c == '\uffff' || c == ';';
        return bl;
    }

    private static boolean isDoneOrKeywordAssign(char c) {
        boolean bl = c == '\uffff' || c == '=';
        return bl;
    }

    private boolean isTerminator(char c) {
        boolean bl = c == '@' || c == '\uffff' || c == '.';
        return bl;
    }

    private boolean isTerminatorOrIDSeparator(char c) {
        boolean bl = c == '_' || c == '-' || this.isTerminator(c);
        return bl;
    }

    private char next() {
        int n = this.index;
        char[] arrc = this.id;
        if (n == arrc.length) {
            this.index = n + 1;
            return '\uffff';
        }
        this.index = n + 1;
        return arrc[n];
    }

    private int parseCountry() {
        if (!this.atTerminator()) {
            char c;
            int n;
            int n2 = this.index++;
            int n3 = this.buffer.length();
            int n4 = 1;
            while (!this.isTerminatorOrIDSeparator(c = this.next())) {
                int n5 = n3;
                n = n4;
                if (n4 != 0) {
                    this.hadCountry = true;
                    this.addSeparator();
                    n5 = n3 + 1;
                    n = 0;
                }
                this.append(AsciiUtil.toUpper(c));
                n3 = n5;
                n4 = n;
            }
            --this.index;
            n4 = this.buffer.length() - n3;
            if (n4 == 0) {
                n = n3;
            } else if (n4 >= 2 && n4 <= 3) {
                n = n3;
                if (n4 == 3) {
                    String string = LocaleIDs.threeToTwoLetterRegion(this.getString(n3));
                    n = n3;
                    if (string != null) {
                        this.set(n3, string);
                        n = n3;
                    }
                }
            } else {
                this.index = n2;
                n = n3 - 1;
                StringBuilder stringBuilder = this.buffer;
                stringBuilder.delete(n, stringBuilder.length());
                this.hadCountry = false;
            }
            return n;
        }
        return this.buffer.length();
    }

    private int parseKeywords() {
        int n = this.buffer.length();
        Map<String, String> map = this.getKeywordMap();
        int n2 = n;
        if (!map.isEmpty()) {
            char c = '\u0001';
            for (Map.Entry entry : map.entrySet()) {
                char c2 = c != '\u0000' ? (c = '@') : (c = ';');
                this.append(c2);
                c = '\u0000';
                this.append((String)entry.getKey());
                this.append('=');
                this.append((String)entry.getValue());
            }
            n2 = n;
            if (c == '\u0000') {
                n2 = n + 1;
            }
        }
        return n2;
    }

    private int parseLanguage() {
        String string;
        char c;
        int n = this.buffer.length();
        if (this.haveExperimentalLanguagePrefix()) {
            this.append(AsciiUtil.toLower(this.id[0]));
            this.append('-');
            this.index = 2;
        }
        while (!this.isTerminatorOrIDSeparator(c = this.next())) {
            this.append(AsciiUtil.toLower(c));
        }
        --this.index;
        if (this.buffer.length() - n == 3 && (string = LocaleIDs.threeToTwoLetterLanguage(this.getString(0))) != null) {
            this.set(0, string);
        }
        return 0;
    }

    private int parseScript() {
        if (!this.atTerminator()) {
            char c;
            int n = this.index++;
            int n2 = this.buffer.length();
            int n3 = 1;
            while (!this.isTerminatorOrIDSeparator(c = this.next()) && AsciiUtil.isAlpha(c)) {
                if (n3 != 0) {
                    this.addSeparator();
                    this.append(AsciiUtil.toUpper(c));
                    n3 = 0;
                    continue;
                }
                this.append(AsciiUtil.toLower(c));
            }
            --this.index;
            if (this.index - n != 5) {
                this.index = n;
                StringBuilder stringBuilder = this.buffer;
                stringBuilder.delete(n2, stringBuilder.length());
                n3 = n2;
            } else {
                n3 = n2 + 1;
            }
            return n3;
        }
        return this.buffer.length();
    }

    private int parseVariant() {
        int n;
        int n2 = this.buffer.length();
        int n3 = 1;
        int n4 = 1;
        boolean bl = false;
        boolean bl2 = true;
        while ((n = this.next()) != 65535) {
            int n5;
            boolean bl3;
            int n6;
            block12 : {
                block11 : {
                    if (n == 46) {
                        n3 = 0;
                        bl = true;
                        continue;
                    }
                    if (n == 64) {
                        if (this.haveKeywordAssign()) break;
                        bl = false;
                        n3 = 0;
                        n4 = 1;
                        continue;
                    }
                    if (n3 != 0) {
                        n3 = n6 = 0;
                        if (n == 95) continue;
                        n3 = n6;
                        if (n == 45) continue;
                        --this.index;
                        n3 = n6;
                        continue;
                    }
                    if (bl) continue;
                    n6 = n2;
                    n5 = n4;
                    bl3 = bl2;
                    if (n4 != 0) {
                        int n7 = 0;
                        n4 = n2;
                        if (bl2) {
                            n4 = n2;
                            if (!this.hadCountry) {
                                this.addSeparator();
                                n4 = n2 + '\u0001';
                            }
                        }
                        this.addSeparator();
                        n6 = n4;
                        n5 = n7;
                        bl3 = bl2;
                        if (bl2) {
                            n6 = n4 + 1;
                            bl3 = false;
                            n5 = n7;
                        }
                    }
                    if ((n2 = (int)AsciiUtil.toUpper((char)n)) == 45) break block11;
                    n = n2;
                    if (n2 != 44) break block12;
                }
                n = n2 = 95;
            }
            this.append((char)n);
            n2 = n6;
            n4 = n5;
            bl2 = bl3;
        }
        --this.index;
        return n2;
    }

    private void reset() {
        this.index = 0;
        this.buffer = new StringBuilder(this.id.length + 5);
    }

    private void set(int n, String string) {
        StringBuilder stringBuilder = this.buffer;
        stringBuilder.delete(n, stringBuilder.length());
        this.buffer.insert(n, string);
    }

    private void setKeywordValue(String string, String map, boolean bl) {
        block14 : {
            block13 : {
                block12 : {
                    if (string != null) break block12;
                    if (bl) {
                        this.keywords = Collections.emptyMap();
                    }
                    break block13;
                }
                String string2 = AsciiUtil.toLowerString(string.trim());
                if (string2.length() == 0) break block14;
                string = map;
                if (map != null && (string = ((String)((Object)map)).trim()).length() == 0) {
                    throw new IllegalArgumentException("value must not be empty");
                }
                map = this.getKeywordMap();
                if (map.isEmpty()) {
                    if (string != null) {
                        this.keywords = new TreeMap<String, String>(this.getKeyComparator());
                        this.keywords.put(string2, string.trim());
                    }
                } else if (bl || !map.containsKey(string2)) {
                    if (string != null) {
                        map.put(string2, string);
                    } else {
                        map.remove(string2);
                        if (map.isEmpty()) {
                            this.keywords = Collections.emptyMap();
                        }
                    }
                }
            }
            return;
        }
        throw new IllegalArgumentException("keyword must not be empty");
    }

    private boolean setToKeywordStart() {
        char[] arrc;
        for (int i = this.index; i < (arrc = this.id).length; ++i) {
            if (arrc[i] != '@') continue;
            if (this.canonicalize) {
                int n;
                for (i = n = i + 1; i < (arrc = this.id).length; ++i) {
                    if (arrc[i] != '=') continue;
                    this.index = n;
                    return true;
                }
                break;
            }
            if (++i >= arrc.length) break;
            this.index = i;
            return true;
        }
        return false;
    }

    private void skipCountry() {
        if (!this.atTerminator()) {
            char[] arrc = this.id;
            int n = this.index;
            if (arrc[n] == '_' || arrc[n] == '-') {
                ++this.index;
            }
            int n2 = this.index;
            this.skipUntilTerminatorOrIDSeparator();
            n = this.index - n2;
            if (n < 2 || n > 3) {
                this.index = n2;
            }
        }
    }

    private void skipLanguage() {
        if (this.haveExperimentalLanguagePrefix()) {
            this.index = 2;
        }
        this.skipUntilTerminatorOrIDSeparator();
    }

    private void skipScript() {
        if (!this.atTerminator()) {
            char c;
            int n = this.index++;
            while (!this.isTerminatorOrIDSeparator(c = this.next()) && AsciiUtil.isAlpha(c)) {
            }
            --this.index;
            if (this.index - n != 5) {
                this.index = n;
            }
        }
    }

    private void skipUntilTerminatorOrIDSeparator() {
        while (!this.isTerminatorOrIDSeparator(this.next())) {
        }
        --this.index;
    }

    public void defaultKeywordValue(String string, String string2) {
        this.setKeywordValue(string, string2, false);
    }

    public String getBaseName() {
        String string = this.baseName;
        if (string != null) {
            return string;
        }
        this.parseBaseName();
        return this.getString(0);
    }

    public String getCountry() {
        this.reset();
        this.skipLanguage();
        this.skipScript();
        return this.getString(this.parseCountry());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Map<String, String> getKeywordMap() {
        block5 : {
            if (this.keywords != null) return this.keywords;
            var1_1 = null;
            var2_2 = null;
            if (!this.setToKeywordStart()) break block5;
            var1_1 = var2_2;
            while ((var3_3 = this.getKeyword()).length() != 0) {
                block7 : {
                    block9 : {
                        block8 : {
                            block6 : {
                                var4_4 = this.next();
                                if (var4_4 == '=') break block6;
                                var2_2 = var1_1;
                                if (var4_4 == '\uffff') {
                                    break;
                                }
                                break block7;
                            }
                            var5_5 = this.getValue();
                            if (var5_5.length() != 0) break block8;
                            var2_2 = var1_1;
                            break block7;
                        }
                        if (var1_1 != null) break block9;
                        var2_2 = new TreeMap<String, V>(this.getKeyComparator());
                        ** GOTO lbl-1000
                    }
                    var2_2 = var1_1;
                    if (var1_1.containsKey(var3_3)) {
                        var2_2 = var1_1;
                    } else lbl-1000: // 2 sources:
                    {
                        var2_2.put(var3_3, var5_5);
                    }
                }
                var1_1 = var2_2;
                if (this.next() == ';') continue;
                var1_1 = var2_2;
                break;
            }
        }
        if (var1_1 == null) {
            var1_1 = Collections.emptyMap();
        }
        this.keywords = var1_1;
        return this.keywords;
    }

    public String getKeywordValue(String string) {
        Map<String, String> map = this.getKeywordMap();
        string = map.isEmpty() ? null : map.get(AsciiUtil.toLowerString(string.trim()));
        return string;
    }

    public Iterator<String> getKeywords() {
        Map<String, String> map = this.getKeywordMap();
        map = map.isEmpty() ? null : map.keySet().iterator();
        return map;
    }

    public String getLanguage() {
        this.reset();
        return this.getString(this.parseLanguage());
    }

    public String[] getLanguageScriptCountryVariant() {
        this.reset();
        return new String[]{this.getString(this.parseLanguage()), this.getString(this.parseScript()), this.getString(this.parseCountry()), this.getString(this.parseVariant())};
    }

    public String getName() {
        this.parseBaseName();
        this.parseKeywords();
        return this.getString(0);
    }

    public String getScript() {
        this.reset();
        this.skipLanguage();
        return this.getString(this.parseScript());
    }

    public String getVariant() {
        this.reset();
        this.skipLanguage();
        this.skipScript();
        this.skipCountry();
        return this.getString(this.parseVariant());
    }

    public void parseBaseName() {
        String string = this.baseName;
        if (string != null) {
            this.set(0, string);
        } else {
            this.reset();
            this.parseLanguage();
            this.parseScript();
            this.parseCountry();
            this.parseVariant();
            int n = this.buffer.length();
            if (n > 0 && this.buffer.charAt(n - 1) == '_') {
                this.buffer.deleteCharAt(n - 1);
            }
        }
    }

    public void setBaseName(String string) {
        this.baseName = string;
    }

    public void setKeywordValue(String string, String string2) {
        this.setKeywordValue(string, string2, true);
    }

}

