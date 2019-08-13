/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.text.ParseException;
import java.text.PatternEntry;
import java.util.ArrayList;

final class MergeCollation {
    private final byte BITARRAYMASK = (byte)(true ? 1 : 0);
    private final int BYTEMASK;
    private final int BYTEPOWER;
    private transient StringBuffer excess = new StringBuffer();
    private transient PatternEntry lastEntry = null;
    ArrayList<PatternEntry> patterns = new ArrayList();
    private transient PatternEntry saveEntry = null;
    private transient byte[] statusArray = new byte[8192];

    public MergeCollation(String string) throws ParseException {
        byte[] arrby;
        this.BYTEPOWER = 3;
        this.BYTEMASK = 7;
        for (int i = 0; i < (arrby = this.statusArray).length; ++i) {
            arrby[i] = (byte)(false ? 1 : 0);
        }
        this.setPattern(string);
    }

    private final int findLastEntry(PatternEntry patternEntry, StringBuffer abstractStringBuilder) throws ParseException {
        int n;
        if (patternEntry == null) {
            return 0;
        }
        if (patternEntry.strength != -2) {
            int n2 = -1;
            if (patternEntry.chars.length() == 1) {
                char c = patternEntry.chars.charAt(0);
                c = this.statusArray[c >> 3];
                if ((1 << (patternEntry.chars.charAt(0) & 7) & c) != 0) {
                    n2 = this.patterns.lastIndexOf(patternEntry);
                }
            } else {
                n2 = this.patterns.lastIndexOf(patternEntry);
            }
            if (n2 != -1) {
                return n2 + 1;
            }
            abstractStringBuilder = new StringBuilder();
            ((StringBuilder)abstractStringBuilder).append("couldn't find last entry: ");
            ((StringBuilder)abstractStringBuilder).append(patternEntry);
            throw new ParseException(((StringBuilder)abstractStringBuilder).toString(), n2);
        }
        for (n = this.patterns.size() - 1; n >= 0; --n) {
            PatternEntry patternEntry2 = this.patterns.get(n);
            if (!patternEntry2.chars.regionMatches(0, patternEntry.chars, 0, patternEntry2.chars.length())) continue;
            ((StringBuffer)abstractStringBuilder).append(patternEntry.chars.substring(patternEntry2.chars.length(), patternEntry.chars.length()));
            break;
        }
        if (n != -1) {
            return n + 1;
        }
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append("couldn't find: ");
        ((StringBuilder)abstractStringBuilder).append(patternEntry);
        throw new ParseException(((StringBuilder)abstractStringBuilder).toString(), n);
    }

    private final PatternEntry findLastWithNoExtension(int n) {
        while (--n >= 0) {
            PatternEntry patternEntry = this.patterns.get(n);
            if (patternEntry.extension.length() != 0) continue;
            return patternEntry;
        }
        return null;
    }

    private final void fixEntry(PatternEntry patternEntry) throws ParseException {
        if (this.lastEntry != null && patternEntry.chars.equals(this.lastEntry.chars) && patternEntry.extension.equals(this.lastEntry.extension)) {
            if (patternEntry.strength != 3 && patternEntry.strength != -2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The entries ");
                stringBuilder.append(this.lastEntry);
                stringBuilder.append(" and ");
                stringBuilder.append(patternEntry);
                stringBuilder.append(" are adjacent in the rules, but have conflicting strengths: A character can't be unequal to itself.");
                throw new ParseException(stringBuilder.toString(), -1);
            }
            return;
        }
        int n = 1;
        int n2 = 1;
        if (patternEntry.strength != -2) {
            int n3;
            n = -1;
            if (patternEntry.chars.length() == 1) {
                char c = patternEntry.chars.charAt(0);
                n3 = c >> 3;
                byte by = this.statusArray[n3];
                c = (char)(1 << (c & 7));
                if (by != 0 && (by & c) != 0) {
                    n = this.patterns.lastIndexOf(patternEntry);
                } else {
                    this.statusArray[n3] = (byte)(by | c);
                }
            } else {
                n = this.patterns.lastIndexOf(patternEntry);
            }
            if (n != -1) {
                this.patterns.remove(n);
            }
            this.excess.setLength(0);
            n3 = this.findLastEntry(this.lastEntry, this.excess);
            n = n2;
            if (this.excess.length() != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((Object)this.excess);
                stringBuilder.append(patternEntry.extension);
                patternEntry.extension = stringBuilder.toString();
                n = n2;
                if (n3 != this.patterns.size()) {
                    this.lastEntry = this.saveEntry;
                    n = 0;
                }
            }
            if (n3 == this.patterns.size()) {
                this.patterns.add(patternEntry);
                this.saveEntry = patternEntry;
            } else {
                this.patterns.add(n3, patternEntry);
            }
        }
        if (n != 0) {
            this.lastEntry = patternEntry;
        }
    }

    public void addPattern(String object) throws ParseException {
        if (object == null) {
            return;
        }
        PatternEntry.Parser parser = new PatternEntry.Parser((String)object);
        object = parser.next();
        while (object != null) {
            this.fixEntry((PatternEntry)object);
            object = parser.next();
        }
    }

    public String emitPattern() {
        return this.emitPattern(true);
    }

    public String emitPattern(boolean bl) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.patterns.size(); ++i) {
            PatternEntry patternEntry = this.patterns.get(i);
            if (patternEntry == null) continue;
            patternEntry.addToBuffer(stringBuffer, true, bl, null);
        }
        return stringBuffer.toString();
    }

    public int getCount() {
        return this.patterns.size();
    }

    public PatternEntry getItemAt(int n) {
        return this.patterns.get(n);
    }

    public String getPattern() {
        return this.getPattern(true);
    }

    public String getPattern(boolean bl) {
        int n;
        Object object;
        StringBuffer stringBuffer = new StringBuffer();
        Object object2 = null;
        for (n = 0; n < this.patterns.size(); ++n) {
            PatternEntry patternEntry = this.patterns.get(n);
            if (patternEntry.extension.length() != 0) {
                object = object2;
                if (object2 == null) {
                    object = new ArrayList<PatternEntry>();
                }
                ((ArrayList)object).add(patternEntry);
                object2 = object;
                continue;
            }
            object = object2;
            if (object2 != null) {
                object = this.findLastWithNoExtension(n - 1);
                for (int i = object2.size() - 1; i >= 0; --i) {
                    ((PatternEntry)((ArrayList)object2).get(i)).addToBuffer(stringBuffer, false, bl, (PatternEntry)object);
                }
                object = null;
            }
            patternEntry.addToBuffer(stringBuffer, false, bl, null);
            object2 = object;
        }
        if (object2 != null) {
            object = this.findLastWithNoExtension(n - 1);
            for (n = object2.size() - 1; n >= 0; --n) {
                ((PatternEntry)((ArrayList)object2).get(n)).addToBuffer(stringBuffer, false, bl, (PatternEntry)object);
            }
        }
        return stringBuffer.toString();
    }

    public void setPattern(String string) throws ParseException {
        this.patterns.clear();
        this.addPattern(string);
    }
}

