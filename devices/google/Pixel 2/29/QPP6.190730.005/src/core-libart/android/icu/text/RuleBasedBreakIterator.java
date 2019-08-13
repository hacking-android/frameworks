/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CharacterIteration;
import android.icu.impl.ICUBinary;
import android.icu.impl.ICUDebug;
import android.icu.impl.RBBIDataWrapper;
import android.icu.impl.Trie2;
import android.icu.lang.UCharacter;
import android.icu.text.BreakIterator;
import android.icu.text.BurmeseBreakEngine;
import android.icu.text.CjkBreakEngine;
import android.icu.text.DictionaryBreakEngine;
import android.icu.text.KhmerBreakEngine;
import android.icu.text.LanguageBreakEngine;
import android.icu.text.LaoBreakEngine;
import android.icu.text.RBBIRuleBuilder;
import android.icu.text.ThaiBreakEngine;
import android.icu.text.UnhandledBreakEngine;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class RuleBasedBreakIterator
extends BreakIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String RBBI_DEBUG_ARG = "rbbi";
    private static final int RBBI_END = 2;
    private static final int RBBI_RUN = 1;
    private static final int RBBI_START = 0;
    private static final int START_STATE = 1;
    private static final int STOP_STATE = 0;
    private static final boolean TRACE;
    @Deprecated
    public static final String fDebugEnv;
    private static final List<LanguageBreakEngine> gAllBreakEngines;
    private static final UnhandledBreakEngine gUnhandledBreakEngine;
    private static final int kMaxLookaheads = 8;
    private BreakCache fBreakCache = new BreakCache();
    private List<LanguageBreakEngine> fBreakEngines;
    private DictionaryCache fDictionaryCache = new DictionaryCache();
    private int fDictionaryCharCount = 0;
    private boolean fDone;
    private LookAheadResults fLookAheadMatches = new LookAheadResults();
    private int fPosition;
    @Deprecated
    public RBBIDataWrapper fRData;
    private int fRuleStatusIndex;
    private CharacterIterator fText = new StringCharacterIterator("");

    static {
        boolean bl = ICUDebug.enabled(RBBI_DEBUG_ARG) && ICUDebug.value(RBBI_DEBUG_ARG).indexOf("trace") >= 0;
        TRACE = bl;
        gUnhandledBreakEngine = new UnhandledBreakEngine();
        gAllBreakEngines = new ArrayList<LanguageBreakEngine>();
        gAllBreakEngines.add(gUnhandledBreakEngine);
        String string = ICUDebug.enabled(RBBI_DEBUG_ARG) ? ICUDebug.value(RBBI_DEBUG_ARG) : null;
        fDebugEnv = string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RuleBasedBreakIterator() {
        List<LanguageBreakEngine> list = gAllBreakEngines;
        synchronized (list) {
            ArrayList<LanguageBreakEngine> arrayList = new ArrayList<LanguageBreakEngine>(gAllBreakEngines);
            this.fBreakEngines = arrayList;
            return;
        }
    }

    public RuleBasedBreakIterator(String charSequence) {
        this();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            RuleBasedBreakIterator.compileRules((String)charSequence, byteArrayOutputStream);
            this.fRData = RBBIDataWrapper.get(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
            return;
        }
        catch (IOException iOException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("RuleBasedBreakIterator rule compilation internal error: ");
            ((StringBuilder)charSequence).append(iOException.getMessage());
            throw new RuntimeException(((StringBuilder)charSequence).toString());
        }
    }

    private static int CISetIndex32(CharacterIterator characterIterator, int n) {
        if (n <= characterIterator.getBeginIndex()) {
            characterIterator.first();
        } else if (n >= characterIterator.getEndIndex()) {
            characterIterator.setIndex(characterIterator.getEndIndex());
        } else if (Character.isLowSurrogate(characterIterator.setIndex(n)) && !Character.isHighSurrogate(characterIterator.previous())) {
            characterIterator.next();
        }
        return characterIterator.getIndex();
    }

    protected static final void checkOffset(int n, CharacterIterator characterIterator) {
        if (n >= characterIterator.getBeginIndex() && n <= characterIterator.getEndIndex()) {
            return;
        }
        throw new IllegalArgumentException("offset out of bounds");
    }

    public static void compileRules(String string, OutputStream outputStream) throws IOException {
        RBBIRuleBuilder.compileRules(string, outputStream);
    }

    public static RuleBasedBreakIterator getInstanceFromCompiledRules(InputStream inputStream) throws IOException {
        RuleBasedBreakIterator ruleBasedBreakIterator = new RuleBasedBreakIterator();
        ruleBasedBreakIterator.fRData = RBBIDataWrapper.get(ICUBinary.getByteBufferFromInputStreamAndCloseStream(inputStream));
        return ruleBasedBreakIterator;
    }

    @Deprecated
    public static RuleBasedBreakIterator getInstanceFromCompiledRules(ByteBuffer byteBuffer) throws IOException {
        RuleBasedBreakIterator ruleBasedBreakIterator = new RuleBasedBreakIterator();
        ruleBasedBreakIterator.fRData = RBBIDataWrapper.get(byteBuffer);
        return ruleBasedBreakIterator;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private LanguageBreakEngine getLanguageBreakEngine(int n) {
        for (Object object : this.fBreakEngines) {
            if (!object.handles(n)) continue;
            return object;
        }
        List<LanguageBreakEngine> list = gAllBreakEngines;
        synchronized (list) {
            Object object;
            block20 : {
                int n2;
                block22 : {
                    block21 : {
                        for (LanguageBreakEngine languageBreakEngine : gAllBreakEngines) {
                            if (!languageBreakEngine.handles(n)) continue;
                            this.fBreakEngines.add(languageBreakEngine);
                            return languageBreakEngine;
                        }
                        int n3 = UCharacter.getIntPropertyValue(n, 4106);
                        if (n3 == 22) break block21;
                        n2 = n3;
                        if (n3 != 20) break block22;
                    }
                    n2 = 17;
                }
                if (n2 != 17) {
                    if (n2 != 18) {
                        if (n2 != 23) {
                            if (n2 != 24) {
                                if (n2 != 28) {
                                    if (n2 != 38) {
                                        try {
                                            gUnhandledBreakEngine.handleChar(n);
                                            object = gUnhandledBreakEngine;
                                            break block20;
                                        }
                                        catch (IOException iOException) {
                                            return null;
                                        }
                                    }
                                    object = new ThaiBreakEngine();
                                } else {
                                    object = new BurmeseBreakEngine();
                                }
                            } else {
                                object = new LaoBreakEngine();
                            }
                        } else {
                            object = new KhmerBreakEngine();
                        }
                    } else {
                        object = new CjkBreakEngine(true);
                    }
                } else {
                    object = new CjkBreakEngine(false);
                }
            }
            if (object == null) return object;
            if (object == gUnhandledBreakEngine) return object;
            gAllBreakEngines.add((LanguageBreakEngine)object);
            this.fBreakEngines.add((LanguageBreakEngine)object);
            return object;
        }
    }

    private int handleNext() {
        Object object;
        Appendable appendable;
        Appendable appendable2;
        int n;
        if (TRACE) {
            System.out.println("Handle Next   pos      char  state category");
        }
        this.fRuleStatusIndex = 0;
        this.fDictionaryCharCount = 0;
        Object object2 = this.fText;
        Trie2 trie2 = this.fRData.fTrie;
        Object object3 = this.fRData.fFTable.fTable;
        int n2 = this.fPosition;
        object2.setIndex(n2);
        int n3 = n2;
        int n4 = n = object2.current();
        if (n >= 55296) {
            n4 = n = CharacterIteration.nextTrail32((CharacterIterator)object2, n);
            if (n == Integer.MAX_VALUE) {
                this.fDone = true;
                return -1;
            }
        }
        short s = 1;
        int n5 = this.fRData.getRowIndex(1);
        n = 3;
        Object object4 = this.fRData.fFTable.fFlags;
        int n6 = 1;
        if ((object4 & 2) != 0) {
            object4 = 2;
            object = 0;
            n = object4;
            n6 = object;
            if (TRACE) {
                appendable = System.out;
                appendable2 = new StringBuilder();
                ((StringBuilder)appendable2).append("            ");
                ((StringBuilder)appendable2).append(RBBIDataWrapper.intToString(object2.getIndex(), 5));
                ((PrintStream)appendable).print(((StringBuilder)appendable2).toString());
                System.out.print(RBBIDataWrapper.intToHexString(n4, 10));
                appendable = System.out;
                appendable2 = new StringBuilder();
                ((StringBuilder)appendable2).append(RBBIDataWrapper.intToString(1, 7));
                ((StringBuilder)appendable2).append(RBBIDataWrapper.intToString(2, 6));
                ((PrintStream)appendable).println(((StringBuilder)appendable2).toString());
                n6 = object;
                n = object4;
            }
        }
        this.fLookAheadMatches.reset();
        object4 = n6;
        object = n4;
        n6 = n3;
        while (s != 0) {
            if (object == Integer.MAX_VALUE) {
                if (object4 == 2) break;
                n4 = 2;
                n = 1;
                n3 = object;
            } else if (object4 == 1) {
                n4 = n = (int)((short)trie2.get((int)object));
                if ((n & 16384) != 0) {
                    ++this.fDictionaryCharCount;
                    n4 = (short)(n & -16385);
                }
                if (TRACE) {
                    appendable2 = System.out;
                    appendable = new StringBuilder();
                    ((StringBuilder)appendable).append("            ");
                    ((StringBuilder)appendable).append(RBBIDataWrapper.intToString(object2.getIndex(), 5));
                    ((PrintStream)appendable2).print(((StringBuilder)appendable).toString());
                    System.out.print(RBBIDataWrapper.intToHexString(object, 10));
                    appendable = System.out;
                    appendable2 = new StringBuilder();
                    ((StringBuilder)appendable2).append(RBBIDataWrapper.intToString(s, 7));
                    ((StringBuilder)appendable2).append(RBBIDataWrapper.intToString(n4, 6));
                    ((PrintStream)appendable).println(((StringBuilder)appendable2).toString());
                }
                if ((n3 = (int)object2.next()) >= 55296) {
                    n3 = CharacterIteration.nextTrail32((CharacterIterator)object2, n3);
                    n = n4;
                    n4 = object4;
                } else {
                    n = n4;
                    n4 = object4;
                }
            } else {
                n4 = 1;
                n3 = object;
            }
            s = object3[n5 + 4 + n];
            n5 = this.fRData.getRowIndex(s);
            if (object3[n5 + 0] == -1) {
                n6 = object4 = object2.getIndex();
                if (n3 >= 65536) {
                    n6 = object4;
                    if (n3 <= 1114111) {
                        n6 = object4 - 1;
                    }
                }
                this.fRuleStatusIndex = (int)object3[n5 + 2];
            }
            if ((object4 = (Object)object3[n5 + 0]) > 0 && (object4 = this.fLookAheadMatches.getPosition((int)object4)) >= 0) {
                this.fRuleStatusIndex = (int)object3[n5 + 2];
                this.fPosition = object4;
                return object4;
            }
            object = object3[n5 + 1];
            if (object != 0) {
                object4 = object2.getIndex();
                if (n3 >= 65536 && n3 <= 1114111) {
                    --object4;
                }
                this.fLookAheadMatches.setPosition((int)object, (int)object4);
            }
            object4 = n4;
            object = n3;
        }
        n4 = n6;
        if (n6 == n2) {
            if (TRACE) {
                System.out.println("Iterator did not move. Advancing by 1.");
            }
            object2.setIndex(n2);
            CharacterIteration.next32((CharacterIterator)object2);
            n4 = object2.getIndex();
            this.fRuleStatusIndex = 0;
        }
        this.fPosition = n4;
        if (TRACE) {
            object2 = System.out;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("result = ");
            ((StringBuilder)object3).append(n4);
            ((PrintStream)object2).println(((StringBuilder)object3).toString());
        }
        return n4;
    }

    private int handleSafePrevious(int n) {
        Object object = this.fText;
        Trie2 trie2 = this.fRData.fTrie;
        Object object2 = this.fRData.fRTable.fTable;
        RuleBasedBreakIterator.CISetIndex32((CharacterIterator)object, n);
        if (TRACE) {
            System.out.print("Handle Previous   pos   char  state category");
        }
        if (object.getIndex() == object.getBeginIndex()) {
            return -1;
        }
        n = CharacterIteration.previous32((CharacterIterator)object);
        short s = 1;
        int n2 = this.fRData.getRowIndex(1);
        while (n != Integer.MAX_VALUE) {
            short s2 = (short)((short)trie2.get(n) & -16385);
            if (TRACE) {
                Appendable appendable = System.out;
                Appendable appendable2 = new StringBuilder();
                ((StringBuilder)appendable2).append("            ");
                ((StringBuilder)appendable2).append(RBBIDataWrapper.intToString(object.getIndex(), 5));
                ((PrintStream)appendable).print(((StringBuilder)appendable2).toString());
                System.out.print(RBBIDataWrapper.intToHexString(n, 10));
                appendable2 = System.out;
                appendable = new StringBuilder();
                ((StringBuilder)appendable).append(RBBIDataWrapper.intToString(s, 7));
                ((StringBuilder)appendable).append(RBBIDataWrapper.intToString(s2, 6));
                ((PrintStream)appendable2).println(((StringBuilder)appendable).toString());
            }
            s = object2[n2 + 4 + s2];
            n2 = this.fRData.getRowIndex(s);
            if (s == 0) break;
            n = CharacterIteration.previous32((CharacterIterator)object);
        }
        n = object.getIndex();
        if (TRACE) {
            object = System.out;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("result = ");
            ((StringBuilder)object2).append(n);
            ((PrintStream)object).println(((StringBuilder)object2).toString());
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object clone() {
        RuleBasedBreakIterator ruleBasedBreakIterator = (RuleBasedBreakIterator)super.clone();
        Object object = this.fText;
        if (object != null) {
            ruleBasedBreakIterator.fText = (CharacterIterator)object.clone();
        }
        object = gAllBreakEngines;
        synchronized (object) {
            ArrayList<LanguageBreakEngine> arrayList = new ArrayList<LanguageBreakEngine>(gAllBreakEngines);
            ruleBasedBreakIterator.fBreakEngines = arrayList;
        }
        ruleBasedBreakIterator.fLookAheadMatches = new LookAheadResults();
        Objects.requireNonNull(ruleBasedBreakIterator);
        ruleBasedBreakIterator.fBreakCache = ruleBasedBreakIterator.new BreakCache(this.fBreakCache);
        Objects.requireNonNull(ruleBasedBreakIterator);
        ruleBasedBreakIterator.fDictionaryCache = ruleBasedBreakIterator.new DictionaryCache(this.fDictionaryCache);
        return ruleBasedBreakIterator;
    }

    @Override
    public int current() {
        int n = this.fText != null ? this.fPosition : -1;
        return n;
    }

    @Deprecated
    public void dump(PrintStream printStream) {
        PrintStream printStream2 = printStream;
        if (printStream == null) {
            printStream2 = System.out;
        }
        this.fRData.dump(printStream2);
    }

    public boolean equals(Object object) {
        block12 : {
            boolean bl;
            RuleBasedBreakIterator ruleBasedBreakIterator;
            block11 : {
                block10 : {
                    block9 : {
                        bl = false;
                        if (object == null) {
                            return false;
                        }
                        if (this == object) {
                            return true;
                        }
                        try {
                            ruleBasedBreakIterator = (RuleBasedBreakIterator)object;
                            if (this.fRData == ruleBasedBreakIterator.fRData || this.fRData != null && ruleBasedBreakIterator.fRData != null) break block9;
                            return false;
                        }
                        catch (ClassCastException classCastException) {
                            return false;
                        }
                    }
                    if (this.fRData == null || ruleBasedBreakIterator.fRData == null || this.fRData.fRuleSource.equals(ruleBasedBreakIterator.fRData.fRuleSource)) break block10;
                    return false;
                }
                if (this.fText != null || ruleBasedBreakIterator.fText != null) break block11;
                return true;
            }
            if (this.fText == null || (object = ruleBasedBreakIterator.fText) == null) break block12;
            if (!this.fText.equals(object)) break block12;
            int n = this.fPosition;
            int n2 = ruleBasedBreakIterator.fPosition;
            if (n == n2) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int first() {
        CharacterIterator characterIterator = this.fText;
        if (characterIterator == null) {
            return -1;
        }
        characterIterator.first();
        int n = this.fText.getIndex();
        if (!this.fBreakCache.seek(n)) {
            this.fBreakCache.populateNear(n);
        }
        this.fBreakCache.current();
        return this.fPosition;
    }

    @Override
    public int following(int n) {
        if (n < this.fText.getBeginIndex()) {
            return this.first();
        }
        n = RuleBasedBreakIterator.CISetIndex32(this.fText, n);
        this.fBreakCache.following(n);
        n = this.fDone ? -1 : this.fPosition;
        return n;
    }

    @Override
    public int getRuleStatus() {
        int n = this.fRuleStatusIndex;
        int n2 = this.fRData.fStatusTable[this.fRuleStatusIndex];
        return this.fRData.fStatusTable[n + n2];
    }

    @Override
    public int getRuleStatusVec(int[] arrn) {
        int n = this.fRData.fStatusTable[this.fRuleStatusIndex];
        if (arrn != null) {
            int n2 = Math.min(n, arrn.length);
            for (int i = 0; i < n2; ++i) {
                arrn[i] = this.fRData.fStatusTable[this.fRuleStatusIndex + i + 1];
            }
        }
        return n;
    }

    @Override
    public CharacterIterator getText() {
        return this.fText;
    }

    public int hashCode() {
        return this.fRData.fRuleSource.hashCode();
    }

    @Override
    public boolean isBoundary(int n) {
        RuleBasedBreakIterator.checkOffset(n, this.fText);
        int n2 = RuleBasedBreakIterator.CISetIndex32(this.fText, n);
        boolean bl = false;
        if (this.fBreakCache.seek(n2) || this.fBreakCache.populateNear(n2)) {
            bl = this.fBreakCache.current() == n;
        }
        if (!bl) {
            this.next();
        }
        return bl;
    }

    @Override
    public int last() {
        CharacterIterator characterIterator = this.fText;
        if (characterIterator == null) {
            return -1;
        }
        int n = characterIterator.getEndIndex();
        this.isBoundary(n);
        if (this.fPosition != n) {
            // empty if block
        }
        return n;
    }

    @Override
    public int next() {
        this.fBreakCache.next();
        int n = this.fDone ? -1 : this.fPosition;
        return n;
    }

    @Override
    public int next(int n) {
        int n2 = 0;
        int n3 = 0;
        if (n > 0) {
            int n4 = n;
            do {
                n = n3;
                if (n4 > 0) {
                    n = n3;
                    if (n3 != -1) {
                        n3 = this.next();
                        --n4;
                        continue;
                    }
                }
                break;
            } while (true);
        } else if (n < 0) {
            int n5 = n;
            n3 = n2;
            do {
                n = n3;
                if (n5 < 0) {
                    n = n3;
                    if (n3 != -1) {
                        n3 = this.previous();
                        ++n5;
                        continue;
                    }
                }
                break;
            } while (true);
        } else {
            n = this.current();
        }
        return n;
    }

    @Override
    public int preceding(int n) {
        CharacterIterator characterIterator = this.fText;
        if (characterIterator != null && n <= characterIterator.getEndIndex()) {
            if (n < this.fText.getBeginIndex()) {
                return this.first();
            }
            this.fBreakCache.preceding(n);
            n = this.fDone ? -1 : this.fPosition;
            return n;
        }
        return this.last();
    }

    @Override
    public int previous() {
        this.fBreakCache.previous();
        int n = this.fDone ? -1 : this.fPosition;
        return n;
    }

    @Override
    public void setText(CharacterIterator characterIterator) {
        if (characterIterator != null) {
            this.fBreakCache.reset(characterIterator.getBeginIndex(), 0);
        } else {
            this.fBreakCache.reset();
        }
        this.fDictionaryCache.reset();
        this.fText = characterIterator;
        this.first();
    }

    public String toString() {
        String string = "";
        RBBIDataWrapper rBBIDataWrapper = this.fRData;
        if (rBBIDataWrapper != null) {
            string = rBBIDataWrapper.fRuleSource;
        }
        return string;
    }

    class BreakCache {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int CACHE_SIZE = 128;
        static final boolean RetainCachePosition = false;
        static final boolean UpdateCachePosition = true;
        int[] fBoundaries = new int[128];
        int fBufIdx;
        int fEndBufIdx;
        DictionaryBreakEngine.DequeI fSideBuffer = new DictionaryBreakEngine.DequeI();
        int fStartBufIdx;
        short[] fStatuses = new short[128];
        int fTextIdx;

        BreakCache() {
            this.reset();
        }

        BreakCache(BreakCache breakCache) {
            this.fStartBufIdx = breakCache.fStartBufIdx;
            this.fEndBufIdx = breakCache.fEndBufIdx;
            this.fTextIdx = breakCache.fTextIdx;
            this.fBufIdx = breakCache.fBufIdx;
            this.fBoundaries = (int[])breakCache.fBoundaries.clone();
            this.fStatuses = (short[])breakCache.fStatuses.clone();
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
        }

        private final int modChunkSize(int n) {
            return n & 127;
        }

        void addFollowing(int n, int n2, boolean bl) {
            block1 : {
                int n3;
                int n4 = this.modChunkSize(this.fEndBufIdx + 1);
                if (n4 == (n3 = this.fStartBufIdx)) {
                    this.fStartBufIdx = this.modChunkSize(n3 + 6);
                }
                this.fBoundaries[n4] = n;
                this.fStatuses[n4] = (short)n2;
                this.fEndBufIdx = n4;
                if (!bl) break block1;
                this.fBufIdx = n4;
                this.fTextIdx = n;
            }
        }

        boolean addPreceding(int n, int n2, boolean bl) {
            int n3;
            int n4 = this.modChunkSize(this.fStartBufIdx - 1);
            if (n4 == (n3 = this.fEndBufIdx)) {
                if (this.fBufIdx == n3 && !bl) {
                    return false;
                }
                this.fEndBufIdx = this.modChunkSize(this.fEndBufIdx - 1);
            }
            this.fBoundaries[n4] = n;
            this.fStatuses[n4] = (short)n2;
            this.fStartBufIdx = n4;
            if (bl) {
                this.fBufIdx = n4;
                this.fTextIdx = n;
            }
            return true;
        }

        int current() {
            RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
            RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
            RuleBasedBreakIterator.this.fDone = false;
            return this.fTextIdx;
        }

        void dumpCache() {
            System.out.printf("fTextIdx:%d   fBufIdx:%d%n", this.fTextIdx, this.fBufIdx);
            int n = this.fStartBufIdx;
            do {
                System.out.printf("%d  %d%n", n, this.fBoundaries[n]);
                if (n == this.fEndBufIdx) {
                    return;
                }
                n = this.modChunkSize(n + 1);
            } while (true);
        }

        void following(int n) {
            if (n == this.fTextIdx || this.seek(n) || this.populateNear(n)) {
                RuleBasedBreakIterator.this.fDone = false;
                this.next();
            }
        }

        void next() {
            int n = this.fBufIdx;
            if (n == this.fEndBufIdx) {
                RuleBasedBreakIterator.this.fDone = this.populateFollowing() ^ true;
                RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
                RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
            } else {
                this.fBufIdx = this.modChunkSize(n + 1);
                this.fTextIdx = RuleBasedBreakIterator.this.fPosition = this.fBoundaries[this.fBufIdx];
                RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
            }
        }

        boolean populateFollowing() {
            int[] arrn = this.fBoundaries;
            int n = this.fEndBufIdx;
            int n2 = arrn[n];
            short s = this.fStatuses[n];
            if (RuleBasedBreakIterator.this.fDictionaryCache.following(n2)) {
                this.addFollowing(RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)RuleBasedBreakIterator.this).fBoundary, RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)RuleBasedBreakIterator.this).fStatusIndex, true);
                return true;
            }
            RuleBasedBreakIterator.this.fPosition = n2;
            n = RuleBasedBreakIterator.this.handleNext();
            if (n == -1) {
                return false;
            }
            int n3 = RuleBasedBreakIterator.this.fRuleStatusIndex;
            if (RuleBasedBreakIterator.this.fDictionaryCharCount > 0) {
                RuleBasedBreakIterator.this.fDictionaryCache.populateDictionary(n2, n, s, n3);
                if (RuleBasedBreakIterator.this.fDictionaryCache.following(n2)) {
                    this.addFollowing(RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)RuleBasedBreakIterator.this).fBoundary, RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)RuleBasedBreakIterator.this).fStatusIndex, true);
                    return true;
                }
            }
            this.addFollowing(n, n3, true);
            for (n2 = 0; n2 < 6 && (n = RuleBasedBreakIterator.this.handleNext()) != -1 && RuleBasedBreakIterator.this.fDictionaryCharCount <= 0; ++n2) {
                this.addFollowing(n, RuleBasedBreakIterator.this.fRuleStatusIndex, false);
            }
            return true;
        }

        boolean populateNear(int n) {
            int[] arrn;
            int n2;
            block10 : {
                int n3;
                block11 : {
                    block12 : {
                        block13 : {
                            arrn = this.fBoundaries;
                            if (n >= arrn[this.fStartBufIdx] - 15 && n <= arrn[this.fEndBufIdx] + 15) break block10;
                            int n4 = RuleBasedBreakIterator.this.fText.getBeginIndex();
                            n3 = 0;
                            n2 = n4;
                            if (n <= n4 + 20) break block11;
                            n3 = RuleBasedBreakIterator.this.handleSafePrevious(n);
                            n2 = n4;
                            if (n3 <= n4) break block12;
                            RuleBasedBreakIterator.this.fPosition = n3;
                            n4 = RuleBasedBreakIterator.this.handleNext();
                            if (n4 == n3 + 1) break block13;
                            n2 = n4;
                            if (n4 != n3 + 2) break block12;
                            n2 = n4;
                            if (!Character.isHighSurrogate(RuleBasedBreakIterator.this.fText.setIndex(n3))) break block12;
                            n2 = n4;
                            if (!Character.isLowSurrogate(RuleBasedBreakIterator.this.fText.next())) break block12;
                        }
                        n2 = RuleBasedBreakIterator.this.handleNext();
                    }
                    n3 = RuleBasedBreakIterator.this.fRuleStatusIndex;
                }
                this.reset(n2, n3);
            }
            if ((arrn = this.fBoundaries)[this.fEndBufIdx] < n) {
                while ((arrn = this.fBoundaries)[n2 = this.fEndBufIdx] < n) {
                    if (this.populateFollowing()) continue;
                    return false;
                }
                this.fBufIdx = n2;
                this.fTextIdx = arrn[this.fBufIdx];
                while (this.fTextIdx > n) {
                    this.previous();
                }
                return true;
            }
            if (arrn[this.fStartBufIdx] > n) {
                while ((arrn = this.fBoundaries)[n2 = this.fStartBufIdx] > n) {
                    this.populatePreceding();
                }
                this.fBufIdx = n2;
                this.fTextIdx = arrn[this.fBufIdx];
                while ((n2 = this.fTextIdx) < n) {
                    this.next();
                }
                if (n2 > n) {
                    this.previous();
                }
                return true;
            }
            return true;
        }

        boolean populatePreceding() {
            int n;
            int n2;
            int n3 = this.fBoundaries[this.fStartBufIdx];
            int n4 = RuleBasedBreakIterator.this.fText.getBeginIndex();
            if (n3 == n4) {
                return false;
            }
            if (RuleBasedBreakIterator.this.fDictionaryCache.preceding(n3)) {
                this.addPreceding(RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)RuleBasedBreakIterator.this).fBoundary, RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)RuleBasedBreakIterator.this).fStatusIndex, true);
                return true;
            }
            int n5 = n3;
            do {
                block13 : {
                    block15 : {
                        block14 : {
                            if ((n5 = (n5 -= 30) <= n4 ? n4 : RuleBasedBreakIterator.this.handleSafePrevious(n5)) == -1 || n5 == n4) break block13;
                            RuleBasedBreakIterator.this.fPosition = n5;
                            n = RuleBasedBreakIterator.this.handleNext();
                            if (n == n5 + 1) break block14;
                            n2 = n;
                            if (n != n5 + 2) break block15;
                            n2 = n;
                            if (!Character.isHighSurrogate(RuleBasedBreakIterator.this.fText.setIndex(n5))) break block15;
                            n2 = n;
                            if (!Character.isLowSurrogate(RuleBasedBreakIterator.this.fText.next())) break block15;
                        }
                        n2 = RuleBasedBreakIterator.this.handleNext();
                    }
                    n = RuleBasedBreakIterator.this.fRuleStatusIndex;
                    continue;
                }
                n2 = n4;
                n = 0;
            } while (n2 >= n3);
            this.fSideBuffer.removeAllElements();
            this.fSideBuffer.push(n2);
            this.fSideBuffer.push(n);
            int n6 = n;
            n = n2;
            do {
                int n7 = RuleBasedBreakIterator.this.fPosition = n;
                n4 = RuleBasedBreakIterator.this.handleNext();
                int n8 = RuleBasedBreakIterator.this.fRuleStatusIndex;
                if (n4 == -1) break;
                n = 0;
                int n9 = 0;
                n5 = n4;
                n2 = n8;
                if (RuleBasedBreakIterator.this.fDictionaryCharCount != 0) {
                    RuleBasedBreakIterator.this.fDictionaryCache.populateDictionary(n7, n4, n6, n8);
                    n = n9;
                    n6 = n7;
                    n2 = n8;
                    n5 = n4;
                    while (RuleBasedBreakIterator.this.fDictionaryCache.following(n6)) {
                        n5 = RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)RuleBasedBreakIterator.this).fBoundary;
                        n2 = RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)RuleBasedBreakIterator.this).fStatusIndex;
                        n4 = 1;
                        n = 1;
                        if (n5 >= n3) {
                            n = n4;
                            break;
                        }
                        this.fSideBuffer.push(n5);
                        this.fSideBuffer.push(n2);
                        n6 = n5;
                    }
                }
                if (n == 0 && n5 < n3) {
                    this.fSideBuffer.push(n5);
                    this.fSideBuffer.push(n2);
                }
                n = n5;
                n6 = n2;
            } while (n5 < n3);
            boolean bl = false;
            if (!this.fSideBuffer.isEmpty()) {
                n5 = this.fSideBuffer.pop();
                this.addPreceding(this.fSideBuffer.pop(), n5, true);
                bl = true;
            }
            while (!this.fSideBuffer.isEmpty()) {
                n5 = this.fSideBuffer.pop();
                if (this.addPreceding(this.fSideBuffer.pop(), n5, false)) continue;
            }
            return bl;
        }

        void preceding(int n) {
            if (n == this.fTextIdx || this.seek(n) || this.populateNear(n)) {
                if (n == this.fTextIdx) {
                    this.previous();
                } else {
                    this.current();
                }
            }
        }

        void previous() {
            int n = this.fBufIdx;
            int n2 = this.fBufIdx;
            int n3 = this.fStartBufIdx;
            boolean bl = true;
            if (n2 == n3) {
                this.populatePreceding();
            } else {
                this.fBufIdx = this.modChunkSize(n2 - 1);
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
            }
            RuleBasedBreakIterator ruleBasedBreakIterator = RuleBasedBreakIterator.this;
            if (this.fBufIdx != n) {
                bl = false;
            }
            ruleBasedBreakIterator.fDone = bl;
            RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
            RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
        }

        void reset() {
            this.reset(0, 0);
        }

        void reset(int n, int n2) {
            this.fStartBufIdx = 0;
            this.fEndBufIdx = 0;
            this.fTextIdx = n;
            this.fBufIdx = 0;
            this.fBoundaries[0] = n;
            this.fStatuses[0] = (short)n2;
        }

        boolean seek(int n) {
            int n2;
            int[] arrn = this.fBoundaries;
            int n3 = this.fStartBufIdx;
            if (n >= arrn[n3] && n <= arrn[n2 = this.fEndBufIdx]) {
                if (n == arrn[n3]) {
                    this.fBufIdx = n3;
                    this.fTextIdx = arrn[this.fBufIdx];
                    return true;
                }
                if (n == arrn[n2]) {
                    this.fBufIdx = n2;
                    this.fTextIdx = arrn[this.fBufIdx];
                    return true;
                }
                n3 = this.fStartBufIdx;
                n2 = this.fEndBufIdx;
                while (n3 != n2) {
                    int n4 = n3 > n2 ? 128 : 0;
                    if (this.fBoundaries[n4 = this.modChunkSize((n3 + n2 + n4) / 2)] > n) {
                        n2 = n4;
                        continue;
                    }
                    n3 = this.modChunkSize(n4 + 1);
                }
                this.fBufIdx = this.modChunkSize(n2 - 1);
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                return true;
            }
            return false;
        }
    }

    class DictionaryCache {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        int fBoundary;
        DictionaryBreakEngine.DequeI fBreaks;
        int fFirstRuleStatusIndex;
        int fLimit;
        int fOtherRuleStatusIndex;
        int fPositionInCache;
        int fStart;
        int fStatusIndex;

        DictionaryCache() {
            this.fPositionInCache = -1;
            this.fBreaks = new DictionaryBreakEngine.DequeI();
        }

        DictionaryCache(DictionaryCache dictionaryCache) {
            try {
                this.fBreaks = (DictionaryBreakEngine.DequeI)dictionaryCache.fBreaks.clone();
                this.fPositionInCache = dictionaryCache.fPositionInCache;
                this.fStart = dictionaryCache.fStart;
                this.fLimit = dictionaryCache.fLimit;
                this.fFirstRuleStatusIndex = dictionaryCache.fFirstRuleStatusIndex;
                this.fOtherRuleStatusIndex = dictionaryCache.fOtherRuleStatusIndex;
                this.fBoundary = dictionaryCache.fBoundary;
                this.fStatusIndex = dictionaryCache.fStatusIndex;
                return;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new RuntimeException(cloneNotSupportedException);
            }
        }

        boolean following(int n) {
            if (n < this.fLimit && n >= this.fStart) {
                int n2 = this.fPositionInCache;
                if (n2 >= 0 && n2 < this.fBreaks.size() && this.fBreaks.elementAt(this.fPositionInCache) == n) {
                    ++this.fPositionInCache;
                    if (this.fPositionInCache >= this.fBreaks.size()) {
                        this.fPositionInCache = -1;
                        return false;
                    }
                    this.fBoundary = n = this.fBreaks.elementAt(this.fPositionInCache);
                    this.fStatusIndex = this.fOtherRuleStatusIndex;
                    return true;
                }
                this.fPositionInCache = 0;
                while (this.fPositionInCache < this.fBreaks.size()) {
                    n2 = this.fBreaks.elementAt(this.fPositionInCache);
                    if (n2 > n) {
                        this.fBoundary = n2;
                        this.fStatusIndex = this.fOtherRuleStatusIndex;
                        return true;
                    }
                    ++this.fPositionInCache;
                }
                this.fPositionInCache = -1;
                return false;
            }
            this.fPositionInCache = -1;
            return false;
        }

        void populateDictionary(int n, int n2, int n3, int n4) {
            if (n2 - n <= 1) {
                return;
            }
            this.reset();
            this.fFirstRuleStatusIndex = n3;
            this.fOtherRuleStatusIndex = n4;
            int n5 = 0;
            RuleBasedBreakIterator.this.fText.setIndex(n);
            n3 = CharacterIteration.current32(RuleBasedBreakIterator.this.fText);
            n4 = (short)RuleBasedBreakIterator.this.fRData.fTrie.get(n3);
            do {
                int n6;
                if ((n6 = RuleBasedBreakIterator.this.fText.getIndex()) < n2 && (n4 & 16384) == 0) {
                    n3 = CharacterIteration.next32(RuleBasedBreakIterator.this.fText);
                    n4 = (short)RuleBasedBreakIterator.this.fRData.fTrie.get(n3);
                    continue;
                }
                if (n6 >= n2) {
                    if (n5 > 0) {
                        if (n < this.fBreaks.elementAt(0)) {
                            this.fBreaks.offer(n);
                        }
                        if (n2 > this.fBreaks.peek()) {
                            this.fBreaks.push(n2);
                        }
                        this.fPositionInCache = 0;
                        this.fStart = this.fBreaks.elementAt(0);
                        this.fLimit = this.fBreaks.peek();
                    }
                    return;
                }
                LanguageBreakEngine languageBreakEngine = RuleBasedBreakIterator.this.getLanguageBreakEngine(n3);
                n3 = n5;
                if (languageBreakEngine != null) {
                    n3 = n5 + languageBreakEngine.findBreaks(RuleBasedBreakIterator.this.fText, n, n2, this.fBreaks);
                }
                n6 = CharacterIteration.current32(RuleBasedBreakIterator.this.fText);
                n4 = (short)RuleBasedBreakIterator.this.fRData.fTrie.get(n6);
                n5 = n3;
                n3 = n6;
            } while (true);
        }

        boolean preceding(int n) {
            int n2;
            if (n > this.fStart && n <= (n2 = this.fLimit)) {
                if (n == n2) {
                    this.fPositionInCache = this.fBreaks.size() - 1;
                    if (this.fPositionInCache >= 0) {
                        // empty if block
                    }
                }
                if ((n2 = this.fPositionInCache) > 0 && n2 < this.fBreaks.size() && this.fBreaks.elementAt(this.fPositionInCache) == n) {
                    --this.fPositionInCache;
                    this.fBoundary = n = this.fBreaks.elementAt(this.fPositionInCache);
                    n = n == this.fStart ? this.fFirstRuleStatusIndex : this.fOtherRuleStatusIndex;
                    this.fStatusIndex = n;
                    return true;
                }
                if (this.fPositionInCache == 0) {
                    this.fPositionInCache = -1;
                    return false;
                }
                this.fPositionInCache = this.fBreaks.size() - 1;
                while ((n2 = this.fPositionInCache) >= 0) {
                    if ((n2 = this.fBreaks.elementAt(n2)) < n) {
                        this.fBoundary = n2;
                        n = n2 == this.fStart ? this.fFirstRuleStatusIndex : this.fOtherRuleStatusIndex;
                        this.fStatusIndex = n;
                        return true;
                    }
                    --this.fPositionInCache;
                }
                this.fPositionInCache = -1;
                return false;
            }
            this.fPositionInCache = -1;
            return false;
        }

        void reset() {
            this.fPositionInCache = -1;
            this.fStart = 0;
            this.fLimit = 0;
            this.fFirstRuleStatusIndex = 0;
            this.fOtherRuleStatusIndex = 0;
            this.fBreaks.removeAllElements();
        }
    }

    private static class LookAheadResults {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        int[] fKeys = new int[8];
        int[] fPositions = new int[8];
        int fUsedSlotLimit = 0;

        LookAheadResults() {
        }

        int getPosition(int n) {
            for (int i = 0; i < this.fUsedSlotLimit; ++i) {
                if (this.fKeys[i] != n) continue;
                return this.fPositions[i];
            }
            return -1;
        }

        void reset() {
            this.fUsedSlotLimit = 0;
        }

        void setPosition(int n, int n2) {
            int n3;
            for (n3 = 0; n3 < this.fUsedSlotLimit; ++n3) {
                if (this.fKeys[n3] != n) continue;
                this.fPositions[n3] = n2;
                return;
            }
            int n4 = n3;
            if (n3 >= 8) {
                n4 = 7;
            }
            this.fKeys[n4] = n;
            this.fPositions[n4] = n2;
            this.fUsedSlotLimit = n4 + 1;
        }
    }

}

