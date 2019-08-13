/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUBinary;
import android.icu.impl.Utility;
import android.icu.lang.UCharacter;
import android.icu.lang.UScript;
import android.icu.text.Normalizer2;
import android.icu.text.UnicodeSet;
import android.icu.util.ULocale;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpoofChecker {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ALL_CHECKS = -1;
    @Deprecated
    public static final int ANY_CASE = 8;
    static final UnicodeSet ASCII;
    public static final int CHAR_LIMIT = 64;
    public static final int CONFUSABLE = 7;
    public static final int HIDDEN_OVERLAY = 256;
    public static final UnicodeSet INCLUSION;
    public static final int INVISIBLE = 32;
    public static final int MIXED_NUMBERS = 128;
    public static final int MIXED_SCRIPT_CONFUSABLE = 2;
    public static final UnicodeSet RECOMMENDED;
    public static final int RESTRICTION_LEVEL = 16;
    @Deprecated
    public static final int SINGLE_SCRIPT = 16;
    public static final int SINGLE_SCRIPT_CONFUSABLE = 1;
    public static final int WHOLE_SCRIPT_CONFUSABLE = 4;
    private static Normalizer2 nfdNormalizer;
    private UnicodeSet fAllowedCharsSet;
    private Set<ULocale> fAllowedLocales;
    private int fChecks;
    private RestrictionLevel fRestrictionLevel;
    private SpoofData fSpoofData;

    static {
        INCLUSION = new UnicodeSet("['\\-.\\:\\u00B7\\u0375\\u058A\\u05F3\\u05F4\\u06FD\\u06FE\\u0F0B\\u200C\\u200D\\u2010\\u2019\\u2027\\u30A0\\u30FB]").freeze();
        RECOMMENDED = new UnicodeSet("[0-9A-Z_a-z\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u0131\\u0134-\\u013E\\u0141-\\u0148\\u014A-\\u017E\\u018F\\u01A0\\u01A1\\u01AF\\u01B0\\u01CD-\\u01DC\\u01DE-\\u01E3\\u01E6-\\u01F0\\u01F4\\u01F5\\u01F8-\\u021B\\u021E\\u021F\\u0226-\\u0233\\u0259\\u02BB\\u02BC\\u02EC\\u0300-\\u0304\\u0306-\\u030C\\u030F-\\u0311\\u0313\\u0314\\u031B\\u0323-\\u0328\\u032D\\u032E\\u0330\\u0331\\u0335\\u0338\\u0339\\u0342\\u0345\\u037B-\\u037D\\u0386\\u0388-\\u038A\\u038C\\u038E-\\u03A1\\u03A3-\\u03CE\\u03FC-\\u045F\\u048A-\\u0529\\u052E\\u052F\\u0531-\\u0556\\u0559\\u0560-\\u0586\\u0588\\u05B4\\u05D0-\\u05EA\\u05EF-\\u05F2\\u0620-\\u063F\\u0641-\\u0655\\u0660-\\u0669\\u0670-\\u0672\\u0674\\u0679-\\u068D\\u068F-\\u06D3\\u06D5\\u06E5\\u06E6\\u06EE-\\u06FC\\u06FF\\u0750-\\u07B1\\u08A0-\\u08AC\\u08B2\\u08B6-\\u08BD\\u0901-\\u094D\\u094F\\u0950\\u0956\\u0957\\u0960-\\u0963\\u0966-\\u096F\\u0971-\\u0977\\u0979-\\u097F\\u0981-\\u0983\\u0985-\\u098C\\u098F\\u0990\\u0993-\\u09A8\\u09AA-\\u09B0\\u09B2\\u09B6-\\u09B9\\u09BC-\\u09C4\\u09C7\\u09C8\\u09CB-\\u09CE\\u09D7\\u09E0-\\u09E3\\u09E6-\\u09F1\\u09FC\\u09FE\\u0A01-\\u0A03\\u0A05-\\u0A0A\\u0A0F\\u0A10\\u0A13-\\u0A28\\u0A2A-\\u0A30\\u0A32\\u0A35\\u0A38\\u0A39\\u0A3C\\u0A3E-\\u0A42\\u0A47\\u0A48\\u0A4B-\\u0A4D\\u0A5C\\u0A66-\\u0A74\\u0A81-\\u0A83\\u0A85-\\u0A8D\\u0A8F-\\u0A91\\u0A93-\\u0AA8\\u0AAA-\\u0AB0\\u0AB2\\u0AB3\\u0AB5-\\u0AB9\\u0ABC-\\u0AC5\\u0AC7-\\u0AC9\\u0ACB-\\u0ACD\\u0AD0\\u0AE0-\\u0AE3\\u0AE6-\\u0AEF\\u0AFA-\\u0AFF\\u0B01-\\u0B03\\u0B05-\\u0B0C\\u0B0F\\u0B10\\u0B13-\\u0B28\\u0B2A-\\u0B30\\u0B32\\u0B33\\u0B35-\\u0B39\\u0B3C-\\u0B43\\u0B47\\u0B48\\u0B4B-\\u0B4D\\u0B56\\u0B57\\u0B5F-\\u0B61\\u0B66-\\u0B6F\\u0B71\\u0B82\\u0B83\\u0B85-\\u0B8A\\u0B8E-\\u0B90\\u0B92-\\u0B95\\u0B99\\u0B9A\\u0B9C\\u0B9E\\u0B9F\\u0BA3\\u0BA4\\u0BA8-\\u0BAA\\u0BAE-\\u0BB9\\u0BBE-\\u0BC2\\u0BC6-\\u0BC8\\u0BCA-\\u0BCD\\u0BD0\\u0BD7\\u0BE6-\\u0BEF\\u0C01-\\u0C0C\\u0C0E-\\u0C10\\u0C12-\\u0C28\\u0C2A-\\u0C33\\u0C35-\\u0C39\\u0C3D-\\u0C44\\u0C46-\\u0C48\\u0C4A-\\u0C4D\\u0C55\\u0C56\\u0C60\\u0C61\\u0C66-\\u0C6F\\u0C80\\u0C82\\u0C83\\u0C85-\\u0C8C\\u0C8E-\\u0C90\\u0C92-\\u0CA8\\u0CAA-\\u0CB3\\u0CB5-\\u0CB9\\u0CBC-\\u0CC4\\u0CC6-\\u0CC8\\u0CCA-\\u0CCD\\u0CD5\\u0CD6\\u0CE0-\\u0CE3\\u0CE6-\\u0CEF\\u0CF1\\u0CF2\\u0D00\\u0D02\\u0D03\\u0D05-\\u0D0C\\u0D0E-\\u0D10\\u0D12-\\u0D43\\u0D46-\\u0D48\\u0D4A-\\u0D4E\\u0D54-\\u0D57\\u0D60\\u0D61\\u0D66-\\u0D6F\\u0D7A-\\u0D7F\\u0D82\\u0D83\\u0D85-\\u0D8E\\u0D91-\\u0D96\\u0D9A-\\u0DA5\\u0DA7-\\u0DB1\\u0DB3-\\u0DBB\\u0DBD\\u0DC0-\\u0DC6\\u0DCA\\u0DCF-\\u0DD4\\u0DD6\\u0DD8-\\u0DDE\\u0DF2\\u0E01-\\u0E32\\u0E34-\\u0E3A\\u0E40-\\u0E4E\\u0E50-\\u0E59\\u0E81\\u0E82\\u0E84\\u0E87\\u0E88\\u0E8A\\u0E8D\\u0E94-\\u0E97\\u0E99-\\u0E9F\\u0EA1-\\u0EA3\\u0EA5\\u0EA7\\u0EAA\\u0EAB\\u0EAD-\\u0EB2\\u0EB4-\\u0EB9\\u0EBB-\\u0EBD\\u0EC0-\\u0EC4\\u0EC6\\u0EC8-\\u0ECD\\u0ED0-\\u0ED9\\u0EDE\\u0EDF\\u0F00\\u0F20-\\u0F29\\u0F35\\u0F37\\u0F3E-\\u0F42\\u0F44-\\u0F47\\u0F49-\\u0F4C\\u0F4E-\\u0F51\\u0F53-\\u0F56\\u0F58-\\u0F5B\\u0F5D-\\u0F68\\u0F6A-\\u0F6C\\u0F71\\u0F72\\u0F74\\u0F7A-\\u0F80\\u0F82-\\u0F84\\u0F86-\\u0F92\\u0F94-\\u0F97\\u0F99-\\u0F9C\\u0F9E-\\u0FA1\\u0FA3-\\u0FA6\\u0FA8-\\u0FAB\\u0FAD-\\u0FB8\\u0FBA-\\u0FBC\\u0FC6\\u1000-\\u1049\\u1050-\\u109D\\u10C7\\u10CD\\u10D0-\\u10F0\\u10F7-\\u10FA\\u10FD-\\u10FF\\u1200-\\u1248\\u124A-\\u124D\\u1250-\\u1256\\u1258\\u125A-\\u125D\\u1260-\\u1288\\u128A-\\u128D\\u1290-\\u12B0\\u12B2-\\u12B5\\u12B8-\\u12BE\\u12C0\\u12C2-\\u12C5\\u12C8-\\u12D6\\u12D8-\\u1310\\u1312-\\u1315\\u1318-\\u135A\\u135D-\\u135F\\u1380-\\u138F\\u1780-\\u17A2\\u17A5-\\u17A7\\u17A9-\\u17B3\\u17B6-\\u17CA\\u17D2\\u17D7\\u17DC\\u17E0-\\u17E9\\u1C80-\\u1C88\\u1C90-\\u1CBA\\u1CBD-\\u1CBF\\u1E00-\\u1E99\\u1E9E\\u1EA0-\\u1EF9\\u1F00-\\u1F15\\u1F18-\\u1F1D\\u1F20-\\u1F45\\u1F48-\\u1F4D\\u1F50-\\u1F57\\u1F59\\u1F5B\\u1F5D\\u1F5F-\\u1F70\\u1F72\\u1F74\\u1F76\\u1F78\\u1F7A\\u1F7C\\u1F80-\\u1FB4\\u1FB6-\\u1FBA\\u1FBC\\u1FC2-\\u1FC4\\u1FC6-\\u1FC8\\u1FCA\\u1FCC\\u1FD0-\\u1FD2\\u1FD6-\\u1FDA\\u1FE0-\\u1FE2\\u1FE4-\\u1FEA\\u1FEC\\u1FF2-\\u1FF4\\u1FF6-\\u1FF8\\u1FFA\\u1FFC\\u2D27\\u2D2D\\u2D80-\\u2D96\\u2DA0-\\u2DA6\\u2DA8-\\u2DAE\\u2DB0-\\u2DB6\\u2DB8-\\u2DBE\\u2DC0-\\u2DC6\\u2DC8-\\u2DCE\\u2DD0-\\u2DD6\\u2DD8-\\u2DDE\\u3005-\\u3007\\u3041-\\u3096\\u3099\\u309A\\u309D\\u309E\\u30A1-\\u30FA\\u30FC-\\u30FE\\u3105-\\u312F\\u31A0-\\u31BA\\u3400-\\u4DB5\\u4E00-\\u9FEF\\uA660\\uA661\\uA674-\\uA67B\\uA67F\\uA69F\\uA717-\\uA71F\\uA788\\uA78D\\uA78E\\uA790-\\uA793\\uA7A0-\\uA7AA\\uA7AE\\uA7AF\\uA7B8\\uA7B9\\uA7FA\\uA9E7-\\uA9FE\\uAA60-\\uAA76\\uAA7A-\\uAA7F\\uAB01-\\uAB06\\uAB09-\\uAB0E\\uAB11-\\uAB16\\uAB20-\\uAB26\\uAB28-\\uAB2E\\uAC00-\\uD7A3\\uFA0E\\uFA0F\\uFA11\\uFA13\\uFA14\\uFA1F\\uFA21\\uFA23\\uFA24\\uFA27-\\uFA29\\U0001133B\\U0001B002-\\U0001B11E\\U00020000-\\U0002A6D6\\U0002A700-\\U0002B734\\U0002B740-\\U0002B81D\\U0002B820-\\U0002CEA1\\U0002CEB0-\\U0002EBE0]").freeze();
        ASCII = new UnicodeSet(0, 127).freeze();
        nfdNormalizer = Normalizer2.getNFDInstance();
    }

    private SpoofChecker() {
    }

    private static void getAugmentedScriptSet(int n, ScriptSet scriptSet) {
        scriptSet.clear();
        UScript.getScriptExtensions(n, scriptSet);
        if (scriptSet.get(17)) {
            scriptSet.set(172);
            scriptSet.set(105);
            scriptSet.set(119);
        }
        if (scriptSet.get(20)) {
            scriptSet.set(105);
        }
        if (scriptSet.get(22)) {
            scriptSet.set(105);
        }
        if (scriptSet.get(18)) {
            scriptSet.set(119);
        }
        if (scriptSet.get(5)) {
            scriptSet.set(172);
        }
        if (scriptSet.get(0) || scriptSet.get(1)) {
            scriptSet.setAll();
        }
    }

    private void getNumerics(String string, UnicodeSet unicodeSet) {
        int n;
        unicodeSet.clear();
        for (int i = 0; i < string.length(); i += Character.charCount((int)n)) {
            n = Character.codePointAt(string, i);
            if (UCharacter.getType(n) != 9) continue;
            unicodeSet.add(n - UCharacter.getNumericValue(n));
        }
    }

    private void getResolvedScriptSet(CharSequence charSequence, ScriptSet scriptSet) {
        this.getResolvedScriptSetWithout(charSequence, 185, scriptSet);
    }

    private void getResolvedScriptSetWithout(CharSequence charSequence, int n, ScriptSet scriptSet) {
        int n2;
        scriptSet.setAll();
        ScriptSet scriptSet2 = new ScriptSet();
        for (int i = 0; i < charSequence.length(); i += Character.charCount((int)n2)) {
            n2 = Character.codePointAt(charSequence, i);
            SpoofChecker.getAugmentedScriptSet(n2, scriptSet2);
            if (n != 185 && scriptSet2.get(n)) continue;
            scriptSet.and(scriptSet2);
        }
    }

    private RestrictionLevel getRestrictionLevel(String string) {
        if (!this.fAllowedCharsSet.containsAll(string)) {
            return RestrictionLevel.UNRESTRICTIVE;
        }
        if (ASCII.containsAll(string)) {
            return RestrictionLevel.ASCII;
        }
        ScriptSet scriptSet = new ScriptSet();
        this.getResolvedScriptSet(string, scriptSet);
        if (!scriptSet.isEmpty()) {
            return RestrictionLevel.SINGLE_SCRIPT_RESTRICTIVE;
        }
        scriptSet = new ScriptSet();
        this.getResolvedScriptSetWithout(string, 25, scriptSet);
        if (!(scriptSet.get(172) || scriptSet.get(105) || scriptSet.get(119))) {
            if (!(scriptSet.isEmpty() || scriptSet.get(8) || scriptSet.get(14) || scriptSet.get(6))) {
                return RestrictionLevel.MODERATELY_RESTRICTIVE;
            }
            return RestrictionLevel.MINIMALLY_RESTRICTIVE;
        }
        return RestrictionLevel.HIGHLY_RESTRICTIVE;
    }

    public int areConfusable(String object, String string) {
        if ((this.fChecks & 7) != 0) {
            int n;
            if (!this.getSkeleton((CharSequence)object).equals(this.getSkeleton(string))) {
                return 0;
            }
            ScriptSet scriptSet = new ScriptSet();
            this.getResolvedScriptSet((CharSequence)object, scriptSet);
            object = new ScriptSet();
            this.getResolvedScriptSet(string, (ScriptSet)object);
            if (scriptSet.intersects((BitSet)object)) {
                n = false | true;
            } else {
                int n2;
                n = n2 = 0 | 2;
                if (!scriptSet.isEmpty()) {
                    n = n2;
                    if (!((BitSet)object).isEmpty()) {
                        n = n2 | 4;
                    }
                }
            }
            return n & this.fChecks;
        }
        throw new IllegalArgumentException("No confusable checks are enabled.");
    }

    public boolean equals(Object object) {
        if (!(object instanceof SpoofChecker)) {
            return false;
        }
        object = (SpoofChecker)object;
        Iterable<ULocale> iterable = this.fSpoofData;
        Iterable<ULocale> iterable2 = ((SpoofChecker)object).fSpoofData;
        if (iterable != iterable2 && iterable != null && !((SpoofData)((Object)iterable)).equals(iterable2)) {
            return false;
        }
        if (this.fChecks != ((SpoofChecker)object).fChecks) {
            return false;
        }
        iterable = this.fAllowedLocales;
        iterable2 = ((SpoofChecker)object).fAllowedLocales;
        if (iterable != iterable2 && iterable != null && !iterable.equals(iterable2)) {
            return false;
        }
        iterable = this.fAllowedCharsSet;
        iterable2 = ((SpoofChecker)object).fAllowedCharsSet;
        if (iterable != iterable2 && iterable != null && !((UnicodeSet)iterable).equals(iterable2)) {
            return false;
        }
        return this.fRestrictionLevel == ((SpoofChecker)object).fRestrictionLevel;
    }

    public boolean failsChecks(String string) {
        return this.failsChecks(string, null);
    }

    public boolean failsChecks(String object, CheckResult checkResult) {
        int n;
        Object object2;
        boolean bl;
        int n2;
        int n3;
        int n4;
        block19 : {
            n4 = ((String)object).length();
            n2 = 0;
            n3 = 0;
            bl = false;
            if (checkResult != null) {
                checkResult.position = 0;
                checkResult.numerics = null;
                checkResult.restrictionLevel = null;
            }
            if ((this.fChecks & 16) != 0) {
                object2 = this.getRestrictionLevel((String)object);
                if (object2.compareTo(this.fRestrictionLevel) > 0) {
                    n3 = 0 | 16;
                }
                n2 = n3;
                if (checkResult != null) {
                    checkResult.restrictionLevel = object2;
                    n2 = n3;
                }
            }
            n = n2;
            if ((this.fChecks & 128) != 0) {
                object2 = new UnicodeSet();
                this.getNumerics((String)object, (UnicodeSet)object2);
                n3 = n2;
                if (((UnicodeSet)object2).size() > 1) {
                    n3 = n2 | 128;
                }
                n = n3;
                if (checkResult != null) {
                    checkResult.numerics = object2;
                    n = n3;
                }
            }
            n3 = n;
            if ((this.fChecks & 256) != 0) {
                n3 = n;
                if (this.findHiddenOverlay((String)object) != -1) {
                    n3 = n | 256;
                }
            }
            n2 = n3;
            if ((this.fChecks & 64) != 0) {
                n = 0;
                do {
                    n2 = n3;
                    if (n >= n4) break block19;
                    n2 = Character.codePointAt((CharSequence)object, n);
                    n = Character.offsetByCodePoints((CharSequence)object, n, 1);
                } while (this.fAllowedCharsSet.contains(n2));
                n2 = n3 | 64;
            }
        }
        int n5 = n2;
        if ((this.fChecks & 32) != 0) {
            object2 = nfdNormalizer.normalize((CharSequence)object);
            n3 = 0;
            n = 0;
            object = new UnicodeSet();
            int n6 = 0;
            do {
                n5 = n2;
                if (n6 >= n4) break;
                int n7 = Character.codePointAt((CharSequence)object2, n6);
                n5 = Character.offsetByCodePoints((CharSequence)object2, n6, 1);
                if (Character.getType(n7) != 6) {
                    n3 = n7 = 0;
                    n6 = n5;
                    if (n == 0) continue;
                    ((UnicodeSet)object).clear();
                    n = 0;
                    n3 = n7;
                    n6 = n5;
                    continue;
                }
                if (n3 == 0) {
                    n3 = n7;
                    n6 = n5;
                    continue;
                }
                n6 = n;
                if (n == 0) {
                    ((UnicodeSet)object).add(n3);
                    n6 = 1;
                }
                if (((UnicodeSet)object).contains(n7)) {
                    n5 = n2 | 32;
                    break;
                }
                ((UnicodeSet)object).add(n7);
                n = n6;
                n6 = n5;
            } while (true);
        }
        if (checkResult != null) {
            checkResult.checks = n5;
        }
        if (n5 != 0) {
            bl = true;
        }
        return bl;
    }

    int findHiddenOverlay(String string) {
        int n;
        boolean bl = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i += UCharacter.charCount((int)n)) {
            n = string.codePointAt(i);
            if (bl && n == 775) {
                return i;
            }
            int n2 = UCharacter.getCombiningClass(n);
            if (n2 != 0 && n2 != 230) continue;
            bl = this.isIllegalCombiningDotLeadCharacter(n, stringBuilder);
        }
        return -1;
    }

    public UnicodeSet getAllowedChars() {
        return this.fAllowedCharsSet;
    }

    public Set<Locale> getAllowedJavaLocales() {
        HashSet<Locale> hashSet = new HashSet<Locale>(this.fAllowedLocales.size());
        Iterator<ULocale> iterator = this.fAllowedLocales.iterator();
        while (iterator.hasNext()) {
            hashSet.add(iterator.next().toLocale());
        }
        return hashSet;
    }

    public Set<ULocale> getAllowedLocales() {
        return Collections.unmodifiableSet(this.fAllowedLocales);
    }

    public int getChecks() {
        return this.fChecks;
    }

    @Deprecated
    public RestrictionLevel getRestrictionLevel() {
        return this.fRestrictionLevel;
    }

    @Deprecated
    public String getSkeleton(int n, String string) {
        return this.getSkeleton(string);
    }

    public String getSkeleton(CharSequence charSequence) {
        int n;
        String string = nfdNormalizer.normalize(charSequence);
        int n2 = string.length();
        charSequence = new StringBuilder();
        for (int i = 0; i < n2; i += Character.charCount((int)n)) {
            n = Character.codePointAt(string, i);
            this.fSpoofData.confusableLookup(n, (StringBuilder)charSequence);
        }
        charSequence = ((StringBuilder)charSequence).toString();
        return nfdNormalizer.normalize(charSequence);
    }

    public int hashCode() {
        return this.fChecks ^ this.fSpoofData.hashCode() ^ this.fAllowedLocales.hashCode() ^ this.fAllowedCharsSet.hashCode() ^ this.fRestrictionLevel.ordinal();
    }

    boolean isIllegalCombiningDotLeadCharacter(int n, StringBuilder stringBuilder) {
        if (this.isIllegalCombiningDotLeadCharacterNoLookup(n)) {
            return true;
        }
        stringBuilder.setLength(0);
        this.fSpoofData.confusableLookup(n, stringBuilder);
        int n2 = UCharacter.codePointBefore(stringBuilder, stringBuilder.length());
        return n2 != n && this.isIllegalCombiningDotLeadCharacterNoLookup(n2);
    }

    boolean isIllegalCombiningDotLeadCharacterNoLookup(int n) {
        boolean bl = n == 105 || n == 106 || n == 305 || n == 567 || n == 108 || UCharacter.hasBinaryProperty(n, 27);
        return bl;
    }

    public static class Builder {
        final UnicodeSet fAllowedCharsSet = new UnicodeSet(0, 1114111);
        final Set<ULocale> fAllowedLocales = new LinkedHashSet<ULocale>();
        int fChecks;
        private RestrictionLevel fRestrictionLevel;
        SpoofData fSpoofData;

        public Builder() {
            this.fChecks = -1;
            this.fSpoofData = null;
            this.fRestrictionLevel = RestrictionLevel.HIGHLY_RESTRICTIVE;
        }

        public Builder(SpoofChecker spoofChecker) {
            this.fChecks = spoofChecker.fChecks;
            this.fSpoofData = spoofChecker.fSpoofData;
            this.fAllowedCharsSet.set(spoofChecker.fAllowedCharsSet);
            this.fAllowedLocales.addAll(spoofChecker.fAllowedLocales);
            this.fRestrictionLevel = spoofChecker.fRestrictionLevel;
        }

        private void addScriptChars(ULocale arrn, UnicodeSet unicodeSet) {
            if ((arrn = UScript.getCode((ULocale)arrn)) != null) {
                UnicodeSet unicodeSet2 = new UnicodeSet();
                for (int i = 0; i < arrn.length; ++i) {
                    unicodeSet2.applyIntPropertyValue(4106, arrn[i]);
                    unicodeSet.addAll(unicodeSet2);
                }
            }
        }

        public SpoofChecker build() {
            if (this.fSpoofData == null) {
                this.fSpoofData = SpoofData.getDefault();
            }
            SpoofChecker spoofChecker = new SpoofChecker();
            spoofChecker.fChecks = this.fChecks;
            spoofChecker.fSpoofData = this.fSpoofData;
            spoofChecker.fAllowedCharsSet = (UnicodeSet)this.fAllowedCharsSet.clone();
            spoofChecker.fAllowedCharsSet.freeze();
            spoofChecker.fAllowedLocales = new HashSet<ULocale>(this.fAllowedLocales);
            spoofChecker.fRestrictionLevel = this.fRestrictionLevel;
            return spoofChecker;
        }

        public Builder setAllowedChars(UnicodeSet unicodeSet) {
            this.fAllowedCharsSet.set(unicodeSet);
            this.fAllowedLocales.clear();
            this.fChecks |= 64;
            return this;
        }

        public Builder setAllowedJavaLocales(Set<Locale> object) {
            HashSet<ULocale> hashSet = new HashSet<ULocale>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                hashSet.add(ULocale.forLocale((Locale)object.next()));
            }
            return this.setAllowedLocales(hashSet);
        }

        public Builder setAllowedLocales(Set<ULocale> set) {
            this.fAllowedCharsSet.clear();
            Object object = set.iterator();
            while (object.hasNext()) {
                this.addScriptChars(object.next(), this.fAllowedCharsSet);
            }
            this.fAllowedLocales.clear();
            if (set.size() == 0) {
                this.fAllowedCharsSet.add(0, 1114111);
                this.fChecks &= -65;
                return this;
            }
            object = new UnicodeSet();
            ((UnicodeSet)object).applyIntPropertyValue(4106, 0);
            this.fAllowedCharsSet.addAll((UnicodeSet)object);
            ((UnicodeSet)object).applyIntPropertyValue(4106, 1);
            this.fAllowedCharsSet.addAll((UnicodeSet)object);
            this.fAllowedLocales.clear();
            this.fAllowedLocales.addAll(set);
            this.fChecks |= 64;
            return this;
        }

        public Builder setChecks(int n) {
            if ((n & 0) == 0) {
                this.fChecks = n & -1;
                return this;
            }
            throw new IllegalArgumentException("Bad Spoof Checks value.");
        }

        public Builder setData(Reader reader) throws ParseException, IOException {
            this.fSpoofData = new SpoofData();
            ConfusabledataBuilder.buildConfusableData(reader, this.fSpoofData);
            return this;
        }

        @Deprecated
        public Builder setData(Reader reader, Reader reader2) throws ParseException, IOException {
            this.setData(reader);
            return this;
        }

        public Builder setRestrictionLevel(RestrictionLevel restrictionLevel) {
            this.fRestrictionLevel = restrictionLevel;
            this.fChecks |= 144;
            return this;
        }

        private static class ConfusabledataBuilder {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            private UnicodeSet fKeySet = new UnicodeSet();
            private ArrayList<Integer> fKeyVec = new ArrayList();
            private int fLineNum;
            private Pattern fParseHexNum;
            private Pattern fParseLine;
            private StringBuffer fStringTable;
            private Hashtable<Integer, SPUString> fTable = new Hashtable();
            private ArrayList<Integer> fValueVec = new ArrayList();
            private SPUStringPool stringPool = new SPUStringPool();

            ConfusabledataBuilder() {
            }

            public static void buildConfusableData(Reader reader, SpoofData spoofData) throws IOException, ParseException {
                new ConfusabledataBuilder().build(reader, spoofData);
            }

            void build(Reader iterator, SpoofData object) throws ParseException, IOException {
                Object object2 = new StringBuffer();
                iterator = new LineNumberReader((Reader)((Object)iterator));
                do {
                    CharSequence charSequence;
                    if ((charSequence = ((LineNumberReader)((Object)iterator)).readLine()) == null) {
                        int n3;
                        int n222;
                        int n;
                        this.fParseLine = Pattern.compile("(?m)^[ \\t]*([0-9A-Fa-f]+)[ \\t]+;[ \\t]*([0-9A-Fa-f]+(?:[ \\t]+[0-9A-Fa-f]+)*)[ \\t]*;\\s*(?:(SL)|(SA)|(ML)|(MA))[ \\t]*(?:#.*?)?$|^([ \\t]*(?:#.*?)?)$|^(.*?)$");
                        this.fParseHexNum = Pattern.compile("\\s*([0-9A-F]+)");
                        if (((StringBuffer)object2).charAt(0) == '\ufeff') {
                            ((StringBuffer)object2).setCharAt(0, ' ');
                        }
                        iterator = this.fParseLine.matcher((CharSequence)object2);
                        while (((Matcher)((Object)iterator)).find()) {
                            ++this.fLineNum;
                            if (((Matcher)((Object)iterator)).start(7) >= 0) continue;
                            if (((Matcher)((Object)iterator)).start(8) < 0) {
                                n3 = Integer.parseInt(((Matcher)((Object)iterator)).group(1), 16);
                                if (n3 <= 1114111) {
                                    object2 = this.fParseHexNum.matcher(((Matcher)((Object)iterator)).group(2));
                                    charSequence = new StringBuilder();
                                    while (((Matcher)object2).find()) {
                                        n222 = Integer.parseInt(((Matcher)object2).group(1), 16);
                                        if (n222 <= 1114111) {
                                            ((StringBuilder)charSequence).appendCodePoint(n222);
                                            continue;
                                        }
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("Confusables, line ");
                                        ((StringBuilder)object).append(this.fLineNum);
                                        ((StringBuilder)object).append(": Bad code point: ");
                                        ((StringBuilder)object).append(Integer.toString(n222, 16));
                                        throw new ParseException(((StringBuilder)object).toString(), ((Matcher)((Object)iterator)).start(2));
                                    }
                                    object2 = this.stringPool.addString(((StringBuilder)charSequence).toString());
                                    this.fTable.put(n3, (SPUString)object2);
                                    this.fKeySet.add(n3);
                                    continue;
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Confusables, line ");
                                ((StringBuilder)object).append(this.fLineNum);
                                ((StringBuilder)object).append(": Bad code point: ");
                                ((StringBuilder)object).append(((Matcher)((Object)iterator)).group(1));
                                throw new ParseException(((StringBuilder)object).toString(), ((Matcher)((Object)iterator)).start(1));
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Confusables, line ");
                            ((StringBuilder)object).append(this.fLineNum);
                            ((StringBuilder)object).append(": Unrecognized Line: ");
                            ((StringBuilder)object).append(((Matcher)((Object)iterator)).group(8));
                            throw new ParseException(((StringBuilder)object).toString(), ((Matcher)((Object)iterator)).start(8));
                        }
                        this.stringPool.sort();
                        this.fStringTable = new StringBuffer();
                        n222 = this.stringPool.size();
                        for (n3 = 0; n3 < n222; ++n3) {
                            iterator = this.stringPool.getByIndex(n3);
                            n = ((SPUString)iterator).fStr.length();
                            int n4 = this.fStringTable.length();
                            if (n == 1) {
                                ((SPUString)iterator).fCharOrStrTableIndex = ((SPUString)iterator).fStr.charAt(0);
                                continue;
                            }
                            ((SPUString)iterator).fCharOrStrTableIndex = n4;
                            this.fStringTable.append(((SPUString)iterator).fStr);
                        }
                        object2 = this.fKeySet.iterator();
                        while (object2.hasNext()) {
                            n3 = ((String)object2.next()).codePointAt(0);
                            iterator = this.fTable.get(n3);
                            if (((SPUString)iterator).fStr.length() <= 256) {
                                n3 = ConfusableDataUtils.codePointAndLengthToKey(n3, ((SPUString)iterator).fStr.length());
                                n222 = ((SPUString)iterator).fCharOrStrTableIndex;
                                this.fKeyVec.add(n3);
                                this.fValueVec.add(n222);
                                continue;
                            }
                            throw new IllegalArgumentException("Confusable prototypes cannot be longer than 256 entries.");
                        }
                        n222 = this.fKeyVec.size();
                        ((SpoofData)object).fCFUKeys = new int[n222];
                        for (n3 = 0; n3 < n222; ++n3) {
                            n = this.fKeyVec.get(n3);
                            ConfusableDataUtils.keyToCodePoint(n);
                            object.fCFUKeys[n3] = n;
                        }
                        n3 = this.fValueVec.size();
                        ((SpoofData)object).fCFUValues = new short[n3];
                        n3 = 0;
                        for (int n222 : this.fValueVec) {
                            object.fCFUValues[n3] = (short)n222;
                            ++n3;
                        }
                        ((SpoofData)object).fCFUStrings = this.fStringTable.toString();
                        return;
                    }
                    ((StringBuffer)object2).append((String)charSequence);
                    ((StringBuffer)object2).append('\n');
                } while (true);
            }

            private static class SPUString {
                int fCharOrStrTableIndex;
                String fStr;

                SPUString(String string) {
                    this.fStr = string;
                    this.fCharOrStrTableIndex = 0;
                }
            }

            private static class SPUStringComparator
            implements Comparator<SPUString> {
                static final SPUStringComparator INSTANCE = new SPUStringComparator();

                private SPUStringComparator() {
                }

                @Override
                public int compare(SPUString sPUString, SPUString sPUString2) {
                    int n;
                    int n2 = sPUString.fStr.length();
                    if (n2 < (n = sPUString2.fStr.length())) {
                        return -1;
                    }
                    if (n2 > n) {
                        return 1;
                    }
                    return sPUString.fStr.compareTo(sPUString2.fStr);
                }
            }

            private static class SPUStringPool {
                private Hashtable<String, SPUString> fHash = new Hashtable();
                private Vector<SPUString> fVec = new Vector();

                public SPUString addString(String string) {
                    SPUString sPUString;
                    SPUString sPUString2 = sPUString = this.fHash.get(string);
                    if (sPUString == null) {
                        sPUString2 = new SPUString(string);
                        this.fHash.put(string, sPUString2);
                        this.fVec.addElement(sPUString2);
                    }
                    return sPUString2;
                }

                public SPUString getByIndex(int n) {
                    return this.fVec.elementAt(n);
                }

                public int size() {
                    return this.fVec.size();
                }

                public void sort() {
                    Collections.sort(this.fVec, SPUStringComparator.INSTANCE);
                }
            }

        }

    }

    public static class CheckResult {
        public int checks = 0;
        public UnicodeSet numerics;
        @Deprecated
        public int position = 0;
        public RestrictionLevel restrictionLevel;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("checks:");
            int n = this.checks;
            if (n == 0) {
                stringBuilder.append(" none");
            } else if (n == -1) {
                stringBuilder.append(" all");
            } else {
                if ((n & 1) != 0) {
                    stringBuilder.append(" SINGLE_SCRIPT_CONFUSABLE");
                }
                if ((this.checks & 2) != 0) {
                    stringBuilder.append(" MIXED_SCRIPT_CONFUSABLE");
                }
                if ((this.checks & 4) != 0) {
                    stringBuilder.append(" WHOLE_SCRIPT_CONFUSABLE");
                }
                if ((this.checks & 8) != 0) {
                    stringBuilder.append(" ANY_CASE");
                }
                if ((this.checks & 16) != 0) {
                    stringBuilder.append(" RESTRICTION_LEVEL");
                }
                if ((this.checks & 32) != 0) {
                    stringBuilder.append(" INVISIBLE");
                }
                if ((this.checks & 64) != 0) {
                    stringBuilder.append(" CHAR_LIMIT");
                }
                if ((this.checks & 128) != 0) {
                    stringBuilder.append(" MIXED_NUMBERS");
                }
            }
            stringBuilder.append(", numerics: ");
            stringBuilder.append(this.numerics.toPattern(false));
            stringBuilder.append(", position: ");
            stringBuilder.append(this.position);
            stringBuilder.append(", restrictionLevel: ");
            stringBuilder.append((Object)this.restrictionLevel);
            return stringBuilder.toString();
        }
    }

    private static final class ConfusableDataUtils {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public static final int FORMAT_VERSION = 2;

        private ConfusableDataUtils() {
        }

        public static final int codePointAndLengthToKey(int n, int n2) {
            return n2 - 1 << 24 | n;
        }

        public static final int keyToCodePoint(int n) {
            return 16777215 & n;
        }

        public static final int keyToLength(int n) {
            return ((-16777216 & n) >> 24) + 1;
        }
    }

    public static enum RestrictionLevel {
        ASCII,
        SINGLE_SCRIPT_RESTRICTIVE,
        HIGHLY_RESTRICTIVE,
        MODERATELY_RESTRICTIVE,
        MINIMALLY_RESTRICTIVE,
        UNRESTRICTIVE;
        
    }

    static class ScriptSet
    extends BitSet {
        private static final long serialVersionUID = 1L;

        @UnsupportedAppUsage
        ScriptSet() {
        }

        @UnsupportedAppUsage
        public void and(int n) {
            this.clear(0, n);
            this.clear(n + 1, 185);
        }

        public void appendStringTo(StringBuilder stringBuilder) {
            stringBuilder.append("{ ");
            if (this.isEmpty()) {
                stringBuilder.append("- ");
            } else if (this.isFull()) {
                stringBuilder.append("* ");
            } else {
                for (int i = 0; i < 185; ++i) {
                    if (!this.get(i)) continue;
                    stringBuilder.append(UScript.getShortName(i));
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("}");
        }

        @UnsupportedAppUsage
        public boolean isFull() {
            boolean bl = this.cardinality() == 185;
            return bl;
        }

        @UnsupportedAppUsage
        public void setAll() {
            this.set(0, 185);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<ScriptSet ");
            this.appendStringTo(stringBuilder);
            stringBuilder.append(">");
            return stringBuilder.toString();
        }
    }

    private static class SpoofData {
        private static final int DATA_FORMAT = 1130788128;
        private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
        int[] fCFUKeys;
        String fCFUStrings;
        short[] fCFUValues;

        private SpoofData() {
        }

        private SpoofData(ByteBuffer byteBuffer) throws IOException {
            ICUBinary.readHeader(byteBuffer, 1130788128, IS_ACCEPTABLE);
            byteBuffer.mark();
            this.readData(byteBuffer);
        }

        public static SpoofData getDefault() {
            if (DefaultData.EXCEPTION == null) {
                return DefaultData.INSTANCE;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not load default confusables data: ");
            stringBuilder.append(DefaultData.EXCEPTION.getMessage());
            throw new MissingResourceException(stringBuilder.toString(), "SpoofChecker", "");
        }

        private void readData(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.getInt() == 944111087) {
                byteBuffer.getInt();
                byteBuffer.getInt();
                int n = byteBuffer.getInt();
                int n2 = byteBuffer.getInt();
                int n3 = byteBuffer.getInt();
                int n4 = byteBuffer.getInt();
                int n5 = byteBuffer.getInt();
                int n6 = byteBuffer.getInt();
                byteBuffer.reset();
                ICUBinary.skipBytes(byteBuffer, n);
                this.fCFUKeys = ICUBinary.getInts(byteBuffer, n2, 0);
                byteBuffer.reset();
                ICUBinary.skipBytes(byteBuffer, n3);
                this.fCFUValues = ICUBinary.getShorts(byteBuffer, n4, 0);
                byteBuffer.reset();
                ICUBinary.skipBytes(byteBuffer, n5);
                this.fCFUStrings = ICUBinary.getString(byteBuffer, n6, 0);
                return;
            }
            throw new IllegalArgumentException("Bad Spoof Check Data.");
        }

        public void appendValueTo(int n, StringBuilder stringBuilder) {
            int n2 = ConfusableDataUtils.keyToLength(this.fCFUKeys[n]);
            n = this.fCFUValues[n];
            if (n2 == 1) {
                stringBuilder.append((char)n);
            } else {
                stringBuilder.append(this.fCFUStrings, n, n + n2);
            }
        }

        public int codePointAt(int n) {
            return ConfusableDataUtils.keyToCodePoint(this.fCFUKeys[n]);
        }

        public void confusableLookup(int n, StringBuilder stringBuilder) {
            int n2;
            int n3;
            int n4 = 0;
            int n5 = this.length();
            do {
                if (this.codePointAt(n3 = (n4 + n5) / 2) > n) {
                    n2 = n3;
                    n3 = n4;
                } else {
                    if (this.codePointAt(n3) >= n) break;
                    n2 = n5;
                }
                n4 = n3;
                n5 = n2;
            } while (n2 - n3 > 1);
            if (this.codePointAt(n3) != n) {
                stringBuilder.appendCodePoint(n);
                return;
            }
            this.appendValueTo(n3, stringBuilder);
        }

        public boolean equals(Object object) {
            if (!(object instanceof SpoofData)) {
                return false;
            }
            SpoofData spoofData = (SpoofData)object;
            if (!Arrays.equals(this.fCFUKeys, spoofData.fCFUKeys)) {
                return false;
            }
            if (!Arrays.equals(this.fCFUValues, spoofData.fCFUValues)) {
                return false;
            }
            return Utility.sameObjects(this.fCFUStrings, spoofData.fCFUStrings) || (object = this.fCFUStrings) == null || ((String)object).equals(spoofData.fCFUStrings);
        }

        public int hashCode() {
            return Arrays.hashCode(this.fCFUKeys) ^ Arrays.hashCode(this.fCFUValues) ^ this.fCFUStrings.hashCode();
        }

        public int length() {
            return this.fCFUKeys.length;
        }

        private static final class DefaultData {
            private static IOException EXCEPTION;
            private static SpoofData INSTANCE;

            static {
                INSTANCE = null;
                EXCEPTION = null;
                try {
                    SpoofData spoofData;
                    INSTANCE = spoofData = new SpoofData(ICUBinary.getRequiredData("confusables.cfu"));
                }
                catch (IOException iOException) {
                    EXCEPTION = iOException;
                }
            }

            private DefaultData() {
            }
        }

        private static final class IsAcceptable
        implements ICUBinary.Authenticate {
            private IsAcceptable() {
            }

            @Override
            public boolean isDataVersionAcceptable(byte[] arrby) {
                boolean bl = false;
                if (arrby[0] == 2 || arrby[1] != 0 || arrby[2] != 0 || arrby[3] != 0) {
                    bl = true;
                }
                return bl;
            }
        }

    }

}

