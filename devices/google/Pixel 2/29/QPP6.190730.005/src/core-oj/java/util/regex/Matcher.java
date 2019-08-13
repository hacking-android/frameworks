/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  libcore.util.NativeAllocationRegistry
 */
package java.util.regex;

import dalvik.annotation.optimization.ReachabilitySensitive;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import libcore.util.NativeAllocationRegistry;

public final class Matcher
implements MatchResult {
    private static final NativeAllocationRegistry registry = NativeAllocationRegistry.createMalloced((ClassLoader)Matcher.class.getClassLoader(), (long)Matcher.getNativeFinalizer());
    @ReachabilitySensitive
    private long address;
    boolean anchoringBounds = true;
    int appendPos = 0;
    int from;
    int[] groups;
    private boolean matchFound;
    private Runnable nativeFinalizer;
    private CharSequence originalInput;
    @ReachabilitySensitive
    private Pattern parentPattern;
    String text;
    int to;
    boolean transparentBounds = false;

    Matcher(Pattern pattern, CharSequence charSequence) {
        this.usePattern(pattern);
        this.reset(charSequence);
    }

    private void appendEvaluated(StringBuffer stringBuffer, String string) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        int n = -1;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\\' && !bl) {
                bl = true;
                continue;
            }
            if (c == '$' && !bl) {
                bl2 = true;
                continue;
            }
            if (c >= '0' && c <= '9' && bl2) {
                stringBuffer.append(this.group(c - 48));
                bl2 = false;
                continue;
            }
            if (c == '{' && bl2) {
                bl3 = true;
                n = i;
                continue;
            }
            if (c == '}' && bl2 && bl3) {
                stringBuffer.append(this.group(string.substring(n + 1, i)));
                bl2 = false;
                bl3 = false;
                continue;
            }
            if (c != '}' && bl2 && bl3) continue;
            stringBuffer.append(c);
            bl2 = false;
            bl = false;
            bl3 = false;
        }
        if (!bl) {
            if (!bl2) {
                if (!bl3) {
                    return;
                }
                throw new IllegalArgumentException("Missing ending brace '}' from replacement string");
            }
            throw new IllegalArgumentException("Illegal group reference: group index is missing");
        }
        throw new IllegalArgumentException("character to be escaped is missing");
    }

    private void ensureMatch() {
        if (this.matchFound) {
            return;
        }
        throw new IllegalStateException("No successful match so far");
    }

    private static native boolean findImpl(long var0, int var2, int[] var3);

    private static native boolean findNextImpl(long var0, int[] var2);

    private int getMatchedGroupIndex(String string) {
        this.ensureMatch();
        int n = Matcher.getMatchedGroupIndex0(this.parentPattern.address, string);
        if (n >= 0) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No capturing group in the pattern with the name ");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static native int getMatchedGroupIndex0(long var0, String var2);

    private static native long getNativeFinalizer();

    private static native int groupCountImpl(long var0);

    private static native boolean hitEndImpl(long var0);

    private static native boolean lookingAtImpl(long var0, int[] var2);

    private static native boolean matchesImpl(long var0, int[] var2);

    private static native long openImpl(long var0);

    public static String quoteReplacement(String string) {
        if (string.indexOf(92) == -1 && string.indexOf(36) == -1) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\\' || c == '$') {
                stringBuilder.append('\\');
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static native boolean requireEndImpl(long var0);

    private Matcher reset(CharSequence charSequence, int n, int n2) {
        if (charSequence != null) {
            if (n >= 0 && n2 >= 0 && n <= charSequence.length() && n2 <= charSequence.length() && n <= n2) {
                this.originalInput = charSequence;
                this.text = charSequence.toString();
                this.from = n;
                this.to = n2;
                this.resetForInput();
                this.matchFound = false;
                this.appendPos = 0;
                return this;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("input == null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void resetForInput() {
        synchronized (this) {
            Matcher.setInputImpl(this.address, this.text, this.from, this.to);
            Matcher.useAnchoringBoundsImpl(this.address, this.anchoringBounds);
            Matcher.useTransparentBoundsImpl(this.address, this.transparentBounds);
            return;
        }
    }

    private static native void setInputImpl(long var0, String var2, int var3, int var4);

    private static native void useAnchoringBoundsImpl(long var0, boolean var2);

    private static native void useTransparentBoundsImpl(long var0, boolean var2);

    public Matcher appendReplacement(StringBuffer stringBuffer, String string) {
        stringBuffer.append(this.text.substring(this.appendPos, this.start()));
        this.appendEvaluated(stringBuffer, string);
        this.appendPos = this.end();
        return this;
    }

    public StringBuffer appendTail(StringBuffer stringBuffer) {
        int n = this.appendPos;
        int n2 = this.to;
        if (n < n2) {
            stringBuffer.append(this.text.substring(n, n2));
        }
        return stringBuffer;
    }

    @Override
    public int end() {
        return this.end(0);
    }

    @Override
    public int end(int n) {
        this.ensureMatch();
        if (n >= 0 && n <= this.groupCount()) {
            return this.groups[n * 2 + 1];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No group ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int end(String string) {
        return this.groups[this.getMatchedGroupIndex(string) * 2 + 1];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean find() {
        synchronized (this) {
            this.matchFound = Matcher.findNextImpl(this.address, this.groups);
            return this.matchFound;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean find(int n) {
        int n2 = this.getTextLength();
        if (n >= 0 && n <= n2) {
            this.reset();
            synchronized (this) {
                this.matchFound = Matcher.findImpl(this.address, n, this.groups);
                return this.matchFound;
            }
        }
        throw new IndexOutOfBoundsException("Illegal start index");
    }

    CharSequence getSubSequence(int n, int n2) {
        return this.text.subSequence(n, n2);
    }

    int getTextLength() {
        return this.text.length();
    }

    @Override
    public String group() {
        return this.group(0);
    }

    @Override
    public String group(int n) {
        this.ensureMatch();
        if (n >= 0 && n <= this.groupCount()) {
            int[] arrn = this.groups;
            if (arrn[n * 2] != -1 && arrn[n * 2 + 1] != -1) {
                return this.getSubSequence(arrn[n * 2], arrn[n * 2 + 1]).toString();
            }
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No group ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public String group(String arrn) {
        int n = this.getMatchedGroupIndex((String)arrn);
        arrn = this.groups;
        if (arrn[n * 2] != -1 && arrn[n * 2 + 1] != -1) {
            return this.getSubSequence(arrn[n * 2], arrn[n * 2 + 1]).toString();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int groupCount() {
        synchronized (this) {
            return Matcher.groupCountImpl(this.address);
        }
    }

    public boolean hasAnchoringBounds() {
        return this.anchoringBounds;
    }

    public boolean hasTransparentBounds() {
        return this.transparentBounds;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean hitEnd() {
        synchronized (this) {
            return Matcher.hitEndImpl(this.address);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean lookingAt() {
        synchronized (this) {
            this.matchFound = Matcher.lookingAtImpl(this.address, this.groups);
            return this.matchFound;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean matches() {
        synchronized (this) {
            this.matchFound = Matcher.matchesImpl(this.address, this.groups);
            return this.matchFound;
        }
    }

    public Pattern pattern() {
        return this.parentPattern;
    }

    public Matcher region(int n, int n2) {
        return this.reset(this.originalInput, n, n2);
    }

    public int regionEnd() {
        return this.to;
    }

    public int regionStart() {
        return this.from;
    }

    public String replaceAll(String string) {
        this.reset();
        if (this.find()) {
            StringBuffer stringBuffer = new StringBuffer();
            do {
                this.appendReplacement(stringBuffer, string);
            } while (this.find());
            this.appendTail(stringBuffer);
            return stringBuffer.toString();
        }
        return this.text.toString();
    }

    public String replaceFirst(String string) {
        if (string != null) {
            this.reset();
            if (!this.find()) {
                return this.text.toString();
            }
            StringBuffer stringBuffer = new StringBuffer();
            this.appendReplacement(stringBuffer, string);
            this.appendTail(stringBuffer);
            return stringBuffer.toString();
        }
        throw new NullPointerException("replacement");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean requireEnd() {
        synchronized (this) {
            return Matcher.requireEndImpl(this.address);
        }
    }

    public Matcher reset() {
        CharSequence charSequence = this.originalInput;
        return this.reset(charSequence, 0, charSequence.length());
    }

    public Matcher reset(CharSequence charSequence) {
        return this.reset(charSequence, 0, charSequence.length());
    }

    @Override
    public int start() {
        return this.start(0);
    }

    @Override
    public int start(int n) {
        this.ensureMatch();
        if (n >= 0 && n <= this.groupCount()) {
            return this.groups[n * 2];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No group ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int start(String string) {
        return this.groups[this.getMatchedGroupIndex(string) * 2];
    }

    public MatchResult toMatchResult() {
        this.ensureMatch();
        return new OffsetBasedMatchResult(this.text, this.groups);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("java.util.regex.Matcher");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[pattern=");
        stringBuilder2.append(this.pattern());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" region=");
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.regionStart());
        stringBuilder2.append(",");
        stringBuilder2.append(this.regionEnd());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" lastmatch=");
        if (this.matchFound && this.group() != null) {
            stringBuilder.append(this.group());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Matcher useAnchoringBounds(boolean bl) {
        synchronized (this) {
            this.anchoringBounds = bl;
            Matcher.useAnchoringBoundsImpl(this.address, bl);
            return this;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Matcher usePattern(Pattern pattern) {
        if (pattern == null) throw new IllegalArgumentException("Pattern cannot be null");
        this.parentPattern = pattern;
        // MONITORENTER : this
        if (this.nativeFinalizer != null) {
            this.nativeFinalizer.run();
            this.address = 0L;
            this.nativeFinalizer = null;
        }
        this.address = Matcher.openImpl(this.parentPattern.address);
        this.nativeFinalizer = registry.registerNativeAllocation((Object)this, this.address);
        // MONITOREXIT : this
        if (this.text != null) {
            this.resetForInput();
        }
        this.groups = new int[(this.groupCount() + 1) * 2];
        this.matchFound = false;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Matcher useTransparentBounds(boolean bl) {
        synchronized (this) {
            this.transparentBounds = bl;
            Matcher.useTransparentBoundsImpl(this.address, bl);
            return this;
        }
    }

    static final class OffsetBasedMatchResult
    implements MatchResult {
        private final String input;
        private final int[] offsets;

        OffsetBasedMatchResult(String string, int[] arrn) {
            this.input = string;
            this.offsets = (int[])arrn.clone();
        }

        @Override
        public int end() {
            return this.end(0);
        }

        @Override
        public int end(int n) {
            return this.offsets[n * 2 + 1];
        }

        @Override
        public String group() {
            return this.group(0);
        }

        @Override
        public String group(int n) {
            int n2 = this.start(n);
            n = this.end(n);
            if (n2 != -1 && n != -1) {
                return this.input.substring(n2, n);
            }
            return null;
        }

        @Override
        public int groupCount() {
            return this.offsets.length / 2 - 1;
        }

        @Override
        public int start() {
            return this.start(0);
        }

        @Override
        public int start(int n) {
            return this.offsets[n * 2];
        }
    }

}

