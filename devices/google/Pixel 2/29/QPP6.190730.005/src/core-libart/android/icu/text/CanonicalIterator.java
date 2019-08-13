/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Norm2AllModes;
import android.icu.impl.Normalizer2Impl;
import android.icu.impl.Utility;
import android.icu.lang.UCharacter;
import android.icu.text.Normalizer;
import android.icu.text.Normalizer2;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import android.icu.text.UnicodeSetIterator;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class CanonicalIterator {
    private static boolean PROGRESS = false;
    private static final Set<String> SET_WITH_NULL_STRING;
    private static boolean SKIP_ZEROS;
    private transient StringBuilder buffer = new StringBuilder();
    private int[] current;
    private boolean done;
    private final Normalizer2Impl nfcImpl;
    private final Normalizer2 nfd;
    private String[][] pieces;
    private String source;

    static {
        SKIP_ZEROS = true;
        SET_WITH_NULL_STRING = new HashSet<String>();
        SET_WITH_NULL_STRING.add("");
    }

    public CanonicalIterator(String string) {
        Norm2AllModes norm2AllModes = Norm2AllModes.getNFCInstance();
        this.nfd = norm2AllModes.decomp;
        this.nfcImpl = norm2AllModes.impl.ensureCanonIterData();
        this.setSource(string);
    }

    private Set<String> extract(int n, String string, int n2, StringBuffer abstractStringBuilder) {
        int n3;
        Object object;
        Object object2;
        if (PROGRESS) {
            object2 = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append(" extract: ");
            ((StringBuilder)object).append(Utility.hex(UTF16.valueOf(n)));
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(Utility.hex(string.substring(n2)));
            ((PrintStream)object2).println(((StringBuilder)object).toString());
        }
        object = object2 = this.nfcImpl.getDecomposition(n);
        if (object2 == null) {
            object = UTF16.valueOf(n);
        }
        int n4 = 0;
        int n5 = UTF16.charAt((String)object, 0);
        int n6 = 0 + UTF16.getCharCount(n5);
        ((StringBuffer)abstractStringBuilder).setLength(0);
        int n7 = n2;
        do {
            Appendable appendable;
            n3 = n4;
            if (n7 >= string.length()) break;
            n3 = UTF16.charAt(string, n7);
            if (n3 == n5) {
                if (PROGRESS) {
                    appendable = System.out;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("  matches: ");
                    ((StringBuilder)object2).append(Utility.hex(UTF16.valueOf(n3)));
                    ((PrintStream)appendable).println(((StringBuilder)object2).toString());
                }
                if (n6 == ((String)object).length()) {
                    ((StringBuffer)abstractStringBuilder).append(string.substring(UTF16.getCharCount(n3) + n7));
                    n3 = 1;
                    break;
                }
                n5 = UTF16.charAt((String)object, n6);
                n6 += UTF16.getCharCount(n5);
            } else {
                if (PROGRESS) {
                    object2 = System.out;
                    appendable = new StringBuilder();
                    ((StringBuilder)appendable).append("  buffer: ");
                    ((StringBuilder)appendable).append(Utility.hex(UTF16.valueOf(n3)));
                    ((PrintStream)object2).println(((StringBuilder)appendable).toString());
                }
                UTF16.append((StringBuffer)abstractStringBuilder, n3);
            }
            n7 += UTF16.getCharCount(n3);
        } while (true);
        if (n3 == 0) {
            return null;
        }
        if (PROGRESS) {
            System.out.println("Matches");
        }
        if (((StringBuffer)abstractStringBuilder).length() == 0) {
            return SET_WITH_NULL_STRING;
        }
        object = ((StringBuffer)abstractStringBuilder).toString();
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append(UTF16.valueOf(n));
        ((StringBuilder)abstractStringBuilder).append((String)object);
        if (Normalizer.compare(((StringBuilder)abstractStringBuilder).toString(), string.substring(n2), 0) != 0) {
            return null;
        }
        return this.getEquivalents2((String)object);
    }

    private String[] getEquivalents(String arrstring) {
        HashSet<String> hashSet = new HashSet<String>();
        Object object = this.getEquivalents2((String)arrstring);
        HashSet<String> hashSet2 = new HashSet<String>();
        object = object.iterator();
        while (object.hasNext()) {
            String string = (String)object.next();
            hashSet2.clear();
            CanonicalIterator.permute(string, SKIP_ZEROS, hashSet2);
            for (String string2 : hashSet2) {
                PrintStream printStream;
                StringBuilder stringBuilder;
                if (Normalizer.compare(string2, (String)arrstring, 0) == 0) {
                    if (PROGRESS) {
                        printStream = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Adding Permutation: ");
                        stringBuilder.append(Utility.hex(string2));
                        printStream.println(stringBuilder.toString());
                    }
                    hashSet.add(string2);
                    continue;
                }
                if (!PROGRESS) continue;
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("-Skipping Permutation: ");
                stringBuilder.append(Utility.hex(string2));
                printStream.println(stringBuilder.toString());
            }
        }
        arrstring = new String[hashSet.size()];
        hashSet.toArray(arrstring);
        return arrstring;
    }

    private Set<String> getEquivalents2(String string) {
        int n;
        AbstractStringBuilder abstractStringBuilder;
        Object object;
        HashSet<String> hashSet = new HashSet<String>();
        if (PROGRESS) {
            object = System.out;
            abstractStringBuilder = new StringBuilder();
            ((StringBuilder)abstractStringBuilder).append("Adding: ");
            ((StringBuilder)abstractStringBuilder).append(Utility.hex(string));
            ((PrintStream)object).println(((StringBuilder)abstractStringBuilder).toString());
        }
        hashSet.add(string);
        abstractStringBuilder = new StringBuffer();
        UnicodeSet unicodeSet = new UnicodeSet();
        for (int i = 0; i < string.length(); i += Character.charCount((int)n)) {
            n = string.codePointAt(i);
            if (!this.nfcImpl.getCanonStartSet(n, unicodeSet)) continue;
            object = new UnicodeSetIterator(unicodeSet);
            while (((UnicodeSetIterator)object).next()) {
                int n2 = ((UnicodeSetIterator)object).codepoint;
                Set<String> set = this.extract(n2, string, i, (StringBuffer)abstractStringBuilder);
                if (set == null) continue;
                CharSequence charSequence = string.substring(0, i);
                CharSequence charSequence2 = new StringBuilder();
                charSequence2.append((String)charSequence);
                charSequence2.append(UTF16.valueOf(n2));
                charSequence2 = charSequence2.toString();
                for (String string2 : set) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append(string2);
                    hashSet.add(((StringBuilder)charSequence).toString());
                }
            }
        }
        return hashSet;
    }

    @Deprecated
    public static void permute(String string, boolean bl, Set<String> set) {
        int n;
        if (string.length() <= 2 && UTF16.countCodePoint(string) <= 1) {
            set.add(string);
            return;
        }
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < string.length(); i += UTF16.getCharCount((int)n)) {
            n = UTF16.charAt(string, i);
            if (bl && i != 0 && UCharacter.getCombiningClass(n) == 0) continue;
            hashSet.clear();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string.substring(0, i));
            stringBuilder.append(string.substring(UTF16.getCharCount(n) + i));
            CanonicalIterator.permute(stringBuilder.toString(), bl, hashSet);
            String string2 = UTF16.valueOf(string, i);
            for (String string3 : hashSet) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(string2);
                stringBuilder2.append(string3);
                set.add(stringBuilder2.toString());
            }
        }
    }

    public String getSource() {
        return this.source;
    }

    public String next() {
        int n;
        Object object;
        if (this.done) {
            return null;
        }
        this.buffer.setLength(0);
        for (n = 0; n < ((String[][])(object = this.pieces)).length; ++n) {
            this.buffer.append(object[n][this.current[n]]);
        }
        object = this.buffer.toString();
        n = this.current.length - 1;
        do {
            block8 : {
                block7 : {
                    block6 : {
                        if (n >= 0) break block6;
                        this.done = true;
                        break block7;
                    }
                    int[] arrn = this.current;
                    arrn[n] = arrn[n] + 1;
                    if (arrn[n] >= this.pieces[n].length) break block8;
                }
                return object;
            }
            arrn[n] = 0;
            --n;
        } while (true);
    }

    public void reset() {
        int[] arrn;
        this.done = false;
        for (int i = 0; i < (arrn = this.current).length; ++i) {
            arrn[i] = 0;
        }
    }

    public void setSource(String object) {
        int n;
        int n2;
        this.source = this.nfd.normalize((CharSequence)object);
        this.done = false;
        if (((String)object).length() == 0) {
            this.pieces = new String[1][];
            this.current = new int[1];
            this.pieces[0] = new String[]{""};
            return;
        }
        object = new ArrayList();
        int n3 = 0;
        for (n2 = UTF16.findOffsetFromCodePoint((String)this.source, (int)1); n2 < this.source.length(); n2 += Character.charCount((int)n)) {
            n = this.source.codePointAt(n2);
            int n4 = n3;
            if (this.nfcImpl.isCanonSegmentStarter(n)) {
                object.add(this.source.substring(n3, n2));
                n4 = n2;
            }
            n3 = n4;
        }
        object.add(this.source.substring(n3, n2));
        this.pieces = new String[object.size()][];
        this.current = new int[object.size()];
        for (n2 = 0; n2 < this.pieces.length; ++n2) {
            if (PROGRESS) {
                System.out.println("SEGMENT");
            }
            this.pieces[n2] = this.getEquivalents((String)object.get(n2));
        }
    }
}

