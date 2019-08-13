/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ClassLoaderUtil;
import android.icu.impl.Normalizer2Impl;
import android.icu.impl.coll.BOCSU;
import android.icu.impl.coll.CollationCompare;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationFastLatin;
import android.icu.impl.coll.CollationIterator;
import android.icu.impl.coll.CollationKeys;
import android.icu.impl.coll.CollationLoader;
import android.icu.impl.coll.CollationRoot;
import android.icu.impl.coll.CollationSettings;
import android.icu.impl.coll.CollationTailoring;
import android.icu.impl.coll.ContractionsAndExpansions;
import android.icu.impl.coll.FCDUTF16CollationIterator;
import android.icu.impl.coll.SharedObject;
import android.icu.impl.coll.TailoredSet;
import android.icu.impl.coll.UTF16CollationIterator;
import android.icu.text.CollationElementIterator;
import android.icu.text.CollationKey;
import android.icu.text.Collator;
import android.icu.text.RawCollationKey;
import android.icu.text.UCharacterIterator;
import android.icu.text.UnicodeSet;
import android.icu.text.UnicodeSetIterator;
import android.icu.util.ULocale;
import android.icu.util.VersionInfo;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.CharacterIterator;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class RuleBasedCollator
extends Collator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean actualLocaleIsSameAsValid;
    private CollationBuffer collationBuffer;
    CollationData data;
    private Lock frozenLock;
    SharedObject.Reference<CollationSettings> settings;
    CollationTailoring tailoring;
    private ULocale validLocale;

    RuleBasedCollator(CollationTailoring collationTailoring, ULocale uLocale) {
        this.data = collationTailoring.data;
        this.settings = collationTailoring.settings.clone();
        this.tailoring = collationTailoring;
        this.validLocale = uLocale;
        this.actualLocaleIsSameAsValid = false;
    }

    public RuleBasedCollator(String string) throws Exception {
        if (string != null) {
            this.validLocale = ULocale.ROOT;
            this.internalBuildTailoring(string);
            return;
        }
        throw new IllegalArgumentException("Collation rules can not be null");
    }

    private void adoptTailoring(CollationTailoring collationTailoring) {
        this.data = collationTailoring.data;
        this.settings = collationTailoring.settings.clone();
        this.tailoring = collationTailoring;
        this.validLocale = collationTailoring.actualLocale;
        this.actualLocaleIsSameAsValid = false;
    }

    private void checkNotFrozen() {
        if (!this.isFrozen()) {
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify frozen RuleBasedCollator");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static final int compareNFDIter(Normalizer2Impl var0, NFDIterator var1_1, NFDIterator var2_2) {
        do lbl-1000: // 3 sources:
        {
            block1 : {
                if ((var3_3 = var1_1.nextCodePoint()) != (var4_4 = var2_2.nextCodePoint())) break block1;
                if (var3_3 >= 0) ** GOTO lbl-1000
                return 0;
            }
            if ((var3_3 = var3_3 < 0 ? -2 : (var3_3 == 65534 ? -1 : var1_1.nextDecomposedCodePoint(var0, var3_3))) >= (var4_4 = var4_4 < 0 ? -2 : (var4_4 == 65534 ? -1 : var2_2.nextDecomposedCodePoint(var0, var4_4)))) continue;
            return -1;
        } while (var3_3 <= var4_4);
        return 1;
    }

    private final CollationBuffer getCollationBuffer() {
        if (this.isFrozen()) {
            this.frozenLock.lock();
        } else if (this.collationBuffer == null) {
            this.collationBuffer = new CollationBuffer(this.data);
        }
        return this.collationBuffer;
    }

    private CollationKey getCollationKey(String string, CollationBuffer collationBuffer) {
        collationBuffer.rawCollationKey = this.getRawCollationKey(string, collationBuffer.rawCollationKey, collationBuffer);
        return new CollationKey(string, collationBuffer.rawCollationKey);
    }

    private final CollationSettings getDefaultSettings() {
        return this.tailoring.settings.readOnly();
    }

    private final CollationSettings getOwnedSettings() {
        return this.settings.copyOnWrite();
    }

    private RawCollationKey getRawCollationKey(CharSequence charSequence, RawCollationKey object, CollationBuffer collationBuffer) {
        Object object2;
        if (object == null) {
            object2 = new RawCollationKey(this.simpleKeyLengthEstimate(charSequence));
        } else {
            object2 = object;
            if (((RawCollationKey)object).bytes == null) {
                ((RawCollationKey)object).bytes = new byte[this.simpleKeyLengthEstimate(charSequence)];
                object2 = object;
            }
        }
        object = new CollationKeyByteSink((RawCollationKey)object2);
        this.writeSortKey(charSequence, (CollationKeyByteSink)object, collationBuffer);
        ((RawCollationKey)object2).size = ((CollationKeys.SortKeyByteSink)object).NumberOfBytesAppended();
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void initMaxExpansions() {
        CollationTailoring collationTailoring = this.tailoring;
        synchronized (collationTailoring) {
            if (this.tailoring.maxExpansions == null) {
                this.tailoring.maxExpansions = CollationElementIterator.computeMaxExpansions(this.tailoring.data);
            }
            return;
        }
    }

    private final void internalBuildTailoring(String object) throws Exception {
        CollationTailoring collationTailoring = CollationRoot.getRoot();
        Object object2 = ClassLoaderUtil.getClassLoader(this.getClass());
        try {
            object2 = ((ClassLoader)object2).loadClass("android.icu.impl.coll.CollationBuilder");
            collationTailoring = ((Class)object2).getConstructor(CollationTailoring.class).newInstance(collationTailoring);
            object = (CollationTailoring)((Class)object2).getMethod("parseAndBuild", String.class).invoke(collationTailoring, object);
            ((CollationTailoring)object).actualLocale = null;
            this.adoptTailoring((CollationTailoring)object);
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw (Exception)invocationTargetException.getTargetException();
        }
    }

    private void internalSetVariableTop(long l) {
        if (l != this.settings.readOnly().variableTop) {
            int n = this.data.getGroupForPrimary(l);
            if (n >= 4096 && 4099 >= n) {
                l = this.data.getLastPrimaryForGroup(n);
                if (l != this.settings.readOnly().variableTop) {
                    CollationSettings collationSettings = this.getOwnedSettings();
                    collationSettings.setMaxVariable(n - 4096, this.getDefaultSettings().options);
                    collationSettings.variableTop = l;
                    this.setFastLatinOptions(collationSettings);
                }
            } else {
                throw new IllegalArgumentException("The variable top must be a primary weight in the space/punctuation/symbols/currency symbols range");
            }
        }
    }

    private final void releaseCollationBuffer(CollationBuffer collationBuffer) {
        if (this.isFrozen()) {
            this.frozenLock.unlock();
        }
    }

    private void setFastLatinOptions(CollationSettings collationSettings) {
        collationSettings.fastLatinOptions = CollationFastLatin.getOptions(this.data, collationSettings, collationSettings.fastLatinPrimaries);
    }

    private int simpleKeyLengthEstimate(CharSequence charSequence) {
        return charSequence.length() * 2 + 10;
    }

    private void writeIdenticalLevel(CharSequence charSequence, CollationKeyByteSink collationKeyByteSink) {
        int n = this.data.nfcImpl.decompose(charSequence, 0, charSequence.length(), null);
        collationKeyByteSink.Append(1);
        CollationKeyByteSink.access$100((CollationKeyByteSink)collationKeyByteSink).size = collationKeyByteSink.NumberOfBytesAppended();
        int n2 = 0;
        if (n != 0) {
            n2 = BOCSU.writeIdenticalLevelRun(0, charSequence, 0, n, collationKeyByteSink.key_);
        }
        if (n < charSequence.length()) {
            int n3 = charSequence.length();
            StringBuilder stringBuilder = new StringBuilder();
            this.data.nfcImpl.decompose(charSequence, n, charSequence.length(), stringBuilder, n3 - n);
            BOCSU.writeIdenticalLevelRun(n2, stringBuilder, 0, stringBuilder.length(), collationKeyByteSink.key_);
        }
        collationKeyByteSink.setBufferAndAppended(CollationKeyByteSink.access$100((CollationKeyByteSink)collationKeyByteSink).bytes, CollationKeyByteSink.access$100((CollationKeyByteSink)collationKeyByteSink).size);
    }

    private void writeSortKey(CharSequence charSequence, CollationKeyByteSink collationKeyByteSink, CollationBuffer collationBuffer) {
        boolean bl = this.settings.readOnly().isNumeric();
        if (this.settings.readOnly().dontCheckFCD()) {
            collationBuffer.leftUTF16CollIter.setText(bl, charSequence, 0);
            CollationKeys.writeSortKeyUpToQuaternary(collationBuffer.leftUTF16CollIter, this.data.compressibleBytes, this.settings.readOnly(), collationKeyByteSink, 1, CollationKeys.SIMPLE_LEVEL_FALLBACK, true);
        } else {
            collationBuffer.leftFCDUTF16Iter.setText(bl, charSequence, 0);
            CollationKeys.writeSortKeyUpToQuaternary(collationBuffer.leftFCDUTF16Iter, this.data.compressibleBytes, this.settings.readOnly(), collationKeyByteSink, 1, CollationKeys.SIMPLE_LEVEL_FALLBACK, true);
        }
        if (this.settings.readOnly().getStrength() == 15) {
            this.writeIdenticalLevel(charSequence, collationKeyByteSink);
        }
        collationKeyByteSink.Append(0);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    @Override
    public RuleBasedCollator cloneAsThawed() {
        try {
            RuleBasedCollator ruleBasedCollator = (RuleBasedCollator)super.clone();
            ruleBasedCollator.settings = this.settings.clone();
            ruleBasedCollator.collationBuffer = null;
            ruleBasedCollator.frozenLock = null;
            return ruleBasedCollator;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public int compare(String string, String string2) {
        return this.doCompare(string, string2);
    }

    @Deprecated
    @Override
    protected int doCompare(CharSequence charSequence, CharSequence charSequence2) {
        boolean bl;
        int n;
        CollationSettings collationSettings;
        int n2;
        CollationBuffer collationBuffer;
        CollationBuffer collationBuffer2;
        block31 : {
            block33 : {
                block32 : {
                    if (charSequence == charSequence2) {
                        return 0;
                    }
                    n2 = 0;
                    do {
                        if (n2 == charSequence.length()) {
                            if (n2 != charSequence2.length()) break;
                            return 0;
                        }
                        if (n2 == charSequence2.length() || charSequence.charAt(n2) != charSequence2.charAt(n2)) break;
                        ++n2;
                    } while (true);
                    collationSettings = this.settings.readOnly();
                    bl = collationSettings.isNumeric();
                    n = n2;
                    if (n2 <= 0) break block31;
                    if (n2 == charSequence.length()) break block32;
                    n = n2;
                    if (this.data.isUnsafeBackward(charSequence.charAt(n2), bl)) break block33;
                }
                n = n2;
                if (n2 == charSequence2.length()) break block31;
                n = n2;
                if (!this.data.isUnsafeBackward(charSequence2.charAt(n2), bl)) break block31;
                n = n2;
            }
            do {
                n = n2 = n - 1;
                if (n2 <= 0) break;
                n = n2;
                if (!this.data.isUnsafeBackward(charSequence.charAt(n2), bl)) break;
                n = n2;
            } while (true);
        }
        n2 = !((n2 = collationSettings.fastLatinOptions) < 0 || n != charSequence.length() && charSequence.charAt(n) > '\u017f' || n != charSequence2.length() && charSequence2.charAt(n) > '\u017f') ? CollationFastLatin.compareUTF16(this.data.fastLatinTable, collationSettings.fastLatinPrimaries, n2, charSequence, charSequence2, n) : -2;
        int n3 = n2;
        if (n2 == -2) {
            block29 : {
                collationBuffer = null;
                try {
                    collationBuffer = collationBuffer2 = this.getCollationBuffer();
                }
                catch (Throwable throwable) {
                    this.releaseCollationBuffer(collationBuffer);
                    throw throwable;
                }
                if (!collationSettings.dontCheckFCD()) break block29;
                collationBuffer = collationBuffer2;
                collationBuffer2.leftUTF16CollIter.setText(bl, charSequence, n);
                collationBuffer = collationBuffer2;
                collationBuffer2.rightUTF16CollIter.setText(bl, charSequence2, n);
                collationBuffer = collationBuffer2;
                n2 = CollationCompare.compareUpToQuaternary(collationBuffer2.leftUTF16CollIter, collationBuffer2.rightUTF16CollIter, collationSettings);
            }
            collationBuffer = collationBuffer2;
            collationBuffer2.leftFCDUTF16Iter.setText(bl, charSequence, n);
            collationBuffer = collationBuffer2;
            collationBuffer2.rightFCDUTF16Iter.setText(bl, charSequence2, n);
            collationBuffer = collationBuffer2;
            n2 = CollationCompare.compareUpToQuaternary(collationBuffer2.leftFCDUTF16Iter, collationBuffer2.rightFCDUTF16Iter, collationSettings);
            this.releaseCollationBuffer(collationBuffer2);
            n3 = n2;
        }
        if (n3 == 0 && collationSettings.getStrength() >= 15) {
            Normalizer2Impl normalizer2Impl;
            block30 : {
                collationBuffer = null;
                try {
                    collationBuffer = collationBuffer2 = this.getCollationBuffer();
                }
                catch (Throwable throwable) {
                    this.releaseCollationBuffer(collationBuffer);
                    throw throwable;
                }
                normalizer2Impl = this.data.nfcImpl;
                collationBuffer = collationBuffer2;
                if (!collationSettings.dontCheckFCD()) break block30;
                collationBuffer = collationBuffer2;
                collationBuffer2.leftUTF16NFDIter.setText(charSequence, n);
                collationBuffer = collationBuffer2;
                collationBuffer2.rightUTF16NFDIter.setText(charSequence2, n);
                collationBuffer = collationBuffer2;
                n2 = RuleBasedCollator.compareNFDIter(normalizer2Impl, collationBuffer2.leftUTF16NFDIter, collationBuffer2.rightUTF16NFDIter);
                this.releaseCollationBuffer(collationBuffer2);
                return n2;
            }
            collationBuffer = collationBuffer2;
            collationBuffer2.leftFCDUTF16NFDIter.setText(normalizer2Impl, charSequence, n);
            collationBuffer = collationBuffer2;
            collationBuffer2.rightFCDUTF16NFDIter.setText(normalizer2Impl, charSequence2, n);
            collationBuffer = collationBuffer2;
            n2 = RuleBasedCollator.compareNFDIter(normalizer2Impl, collationBuffer2.leftFCDUTF16NFDIter, collationBuffer2.rightFCDUTF16NFDIter);
            this.releaseCollationBuffer(collationBuffer2);
            return n2;
        }
        return n3;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl;
        if (this == object) {
            return true;
        }
        if (!super.equals(object)) {
            return false;
        }
        object = (RuleBasedCollator)object;
        if (!this.settings.readOnly().equals(((RuleBasedCollator)object).settings.readOnly())) {
            return false;
        }
        Object object2 = this.data;
        if (object2 == ((RuleBasedCollator)object).data) {
            return true;
        }
        boolean bl2 = ((CollationData)object2).base == null;
        if (bl2 != (bl = object.data.base == null)) {
            return false;
        }
        String string = this.tailoring.getRules();
        object2 = ((RuleBasedCollator)object).tailoring.getRules();
        if ((bl2 || string.length() != 0) && (bl || ((String)object2).length() != 0) && string.equals(object2)) {
            return true;
        }
        return this.getTailoredSet().equals(((RuleBasedCollator)object).getTailoredSet());
    }

    @Override
    public Collator freeze() {
        if (!this.isFrozen()) {
            this.frozenLock = new ReentrantLock();
            if (this.collationBuffer == null) {
                this.collationBuffer = new CollationBuffer(this.data);
            }
        }
        return this;
    }

    public CollationElementIterator getCollationElementIterator(UCharacterIterator uCharacterIterator) {
        this.initMaxExpansions();
        return new CollationElementIterator(uCharacterIterator, this);
    }

    public CollationElementIterator getCollationElementIterator(String string) {
        this.initMaxExpansions();
        return new CollationElementIterator(string, this);
    }

    public CollationElementIterator getCollationElementIterator(CharacterIterator characterIterator) {
        this.initMaxExpansions();
        return new CollationElementIterator((CharacterIterator)characterIterator.clone(), this);
    }

    @Override
    public CollationKey getCollationKey(String object) {
        CollationBuffer collationBuffer;
        if (object == null) {
            return null;
        }
        CollationBuffer collationBuffer2 = null;
        try {
            collationBuffer2 = collationBuffer = this.getCollationBuffer();
        }
        catch (Throwable throwable) {
            this.releaseCollationBuffer(collationBuffer2);
            throw throwable;
        }
        object = this.getCollationKey((String)object, collationBuffer);
        this.releaseCollationBuffer(collationBuffer);
        return object;
    }

    public void getContractionsAndExpansions(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, boolean bl) throws Exception {
        if (unicodeSet != null) {
            unicodeSet.clear();
        }
        if (unicodeSet2 != null) {
            unicodeSet2.clear();
        }
        new ContractionsAndExpansions(unicodeSet, unicodeSet2, null, bl).forData(this.data);
    }

    @Override
    public int getDecomposition() {
        int n = (this.settings.readOnly().options & 1) != 0 ? 17 : 16;
        return n;
    }

    @UnsupportedAppUsage
    @Override
    public ULocale getLocale(ULocale.Type object) {
        if (object == ULocale.ACTUAL_LOCALE) {
            object = this.actualLocaleIsSameAsValid ? this.validLocale : this.tailoring.actualLocale;
            return object;
        }
        if (object == ULocale.VALID_LOCALE) {
            return this.validLocale;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unknown ULocale.Type ");
        stringBuilder.append(object);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int getMaxVariable() {
        return this.settings.readOnly().getMaxVariable() + 4096;
    }

    public boolean getNumericCollation() {
        boolean bl = (this.settings.readOnly().options & 2) != 0;
        return bl;
    }

    @Override
    public RawCollationKey getRawCollationKey(String object, RawCollationKey rawCollationKey) {
        CollationBuffer collationBuffer;
        if (object == null) {
            return null;
        }
        CollationBuffer collationBuffer2 = null;
        try {
            collationBuffer2 = collationBuffer = this.getCollationBuffer();
        }
        catch (Throwable throwable) {
            this.releaseCollationBuffer(collationBuffer2);
            throw throwable;
        }
        object = this.getRawCollationKey((CharSequence)object, rawCollationKey, collationBuffer);
        this.releaseCollationBuffer(collationBuffer);
        return object;
    }

    @Override
    public int[] getReorderCodes() {
        return (int[])this.settings.readOnly().reorderCodes.clone();
    }

    public String getRules() {
        return this.tailoring.getRules();
    }

    public String getRules(boolean bl) {
        if (!bl) {
            return this.tailoring.getRules();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CollationLoader.getRootRules());
        stringBuilder.append(this.tailoring.getRules());
        return stringBuilder.toString();
    }

    @Override
    public int getStrength() {
        return this.settings.readOnly().getStrength();
    }

    @Override
    public UnicodeSet getTailoredSet() {
        UnicodeSet unicodeSet = new UnicodeSet();
        if (this.data.base != null) {
            new TailoredSet(unicodeSet).forData(this.data);
        }
        return unicodeSet;
    }

    @Override
    public VersionInfo getUCAVersion() {
        VersionInfo versionInfo = this.getVersion();
        return VersionInfo.getInstance(versionInfo.getMinor() >> 3, versionInfo.getMinor() & 7, versionInfo.getMilli() >> 6, 0);
    }

    @Override
    public int getVariableTop() {
        return (int)this.settings.readOnly().variableTop;
    }

    @Override
    public VersionInfo getVersion() {
        int n = this.tailoring.version;
        int n2 = VersionInfo.UCOL_RUNTIME_VERSION.getMajor();
        return VersionInfo.getInstance((n >>> 24) + (n2 << 4) + (n2 >> 4), n >> 16 & 255, n >> 8 & 255, n & 255);
    }

    @Override
    public int hashCode() {
        int n = this.settings.readOnly().hashCode();
        if (this.data.base == null) {
            return n;
        }
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(this.getTailoredSet());
        while (unicodeSetIterator.next() && unicodeSetIterator.codepoint != UnicodeSetIterator.IS_STRING) {
            n ^= this.data.getCE32(unicodeSetIterator.codepoint);
        }
        return n;
    }

    @Deprecated
    void internalAddContractions(int n, UnicodeSet unicodeSet) {
        new ContractionsAndExpansions(unicodeSet, null, null, false).forCodePoint(this.data, n);
    }

    @Deprecated
    public long[] internalGetCEs(CharSequence object) {
        CollationBuffer collationBuffer;
        CollationBuffer collationBuffer2;
        block12 : {
            boolean bl;
            block11 : {
                collationBuffer2 = null;
                try {
                    collationBuffer2 = collationBuffer = this.getCollationBuffer();
                }
                catch (Throwable throwable) {
                    this.releaseCollationBuffer(collationBuffer2);
                    throw throwable;
                }
                bl = this.settings.readOnly().isNumeric();
                collationBuffer2 = collationBuffer;
                if (!this.settings.readOnly().dontCheckFCD()) break block11;
                collationBuffer2 = collationBuffer;
                collationBuffer.leftUTF16CollIter.setText(bl, (CharSequence)object, 0);
                collationBuffer2 = collationBuffer;
                object = collationBuffer.leftUTF16CollIter;
                break block12;
            }
            collationBuffer2 = collationBuffer;
            collationBuffer.leftFCDUTF16Iter.setText(bl, (CharSequence)object, 0);
            collationBuffer2 = collationBuffer;
            object = collationBuffer.leftFCDUTF16Iter;
        }
        collationBuffer2 = collationBuffer;
        int n = ((CollationIterator)object).fetchCEs() - 1;
        collationBuffer2 = collationBuffer;
        long[] arrl = new long[n];
        collationBuffer2 = collationBuffer;
        System.arraycopy(((CollationIterator)object).getCEs(), 0, arrl, 0, n);
        this.releaseCollationBuffer(collationBuffer);
        return arrl;
    }

    public boolean isAlternateHandlingShifted() {
        return this.settings.readOnly().getAlternateHandling();
    }

    public boolean isCaseLevel() {
        boolean bl = (this.settings.readOnly().options & 1024) != 0;
        return bl;
    }

    public boolean isFrenchCollation() {
        boolean bl = (this.settings.readOnly().options & 2048) != 0;
        return bl;
    }

    @Override
    public boolean isFrozen() {
        boolean bl = this.frozenLock != null;
        return bl;
    }

    @Deprecated
    public boolean isHiraganaQuaternary() {
        return false;
    }

    public boolean isLowerCaseFirst() {
        boolean bl = this.settings.readOnly().getCaseFirst() == 512;
        return bl;
    }

    final boolean isUnsafe(int n) {
        return this.data.isUnsafeBackward(n, this.settings.readOnly().isNumeric());
    }

    public boolean isUpperCaseFirst() {
        boolean bl = this.settings.readOnly().getCaseFirst() == 768;
        return bl;
    }

    public void setAlternateHandlingDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setAlternateHandlingDefault(collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setAlternateHandlingShifted(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.isAlternateHandlingShifted()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setAlternateHandlingShifted(bl);
        this.setFastLatinOptions(collationSettings);
    }

    public final void setCaseFirstDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setCaseFirstDefault(collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setCaseLevel(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.isCaseLevel()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setFlag(1024, bl);
        this.setFastLatinOptions(collationSettings);
    }

    public void setCaseLevelDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setFlagDefault(1024, collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void setDecomposition(int n) {
        boolean bl;
        this.checkNotFrozen();
        if (n != 16) {
            if (n != 17) throw new IllegalArgumentException("Wrong decomposition mode.");
            bl = true;
        } else {
            bl = false;
        }
        if (bl == this.settings.readOnly().getFlag(1)) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setFlag(1, bl);
        this.setFastLatinOptions(collationSettings);
    }

    public void setDecompositionDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setFlagDefault(1, collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setFrenchCollation(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.isFrenchCollation()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setFlag(2048, bl);
        this.setFastLatinOptions(collationSettings);
    }

    public void setFrenchCollationDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setFlagDefault(2048, collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    @Deprecated
    public void setHiraganaQuaternary(boolean bl) {
        this.checkNotFrozen();
    }

    @Deprecated
    public void setHiraganaQuaternaryDefault() {
        this.checkNotFrozen();
    }

    @Override
    void setLocale(ULocale uLocale, ULocale uLocale2) {
        this.actualLocaleIsSameAsValid = !Objects.equals(uLocale2, this.tailoring.actualLocale);
        this.validLocale = uLocale;
    }

    public void setLowerCaseFirst(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.isLowerCaseFirst()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        int n = bl ? 512 : 0;
        collationSettings.setCaseFirst(n);
        this.setFastLatinOptions(collationSettings);
    }

    @Override
    public RuleBasedCollator setMaxVariable(int n) {
        block8 : {
            int n2;
            block7 : {
                block6 : {
                    if (n != -1) break block6;
                    n2 = -1;
                    break block7;
                }
                if (4096 > n || n > 4099) break block8;
                n2 = n - 4096;
            }
            if (n2 == this.settings.readOnly().getMaxVariable()) {
                return this;
            }
            CollationSettings collationSettings = this.getDefaultSettings();
            if (this.settings.readOnly() == collationSettings && n2 < 0) {
                return this;
            }
            CollationSettings collationSettings2 = this.getOwnedSettings();
            int n3 = n;
            if (n == -1) {
                n3 = collationSettings.getMaxVariable() + 4096;
            }
            long l = this.data.getLastPrimaryForGroup(n3);
            collationSettings2.setMaxVariable(n2, collationSettings.options);
            collationSettings2.variableTop = l;
            this.setFastLatinOptions(collationSettings2);
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("illegal max variable group ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setNumericCollation(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.getNumericCollation()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setFlag(2, bl);
        this.setFastLatinOptions(collationSettings);
    }

    public void setNumericCollationDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setFlagDefault(2, collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    @Override
    public void setReorderCodes(int ... object) {
        this.checkNotFrozen();
        int n = object != null ? ((int[])object).length : 0;
        int n2 = n;
        if (n == 1) {
            n2 = n;
            if (object[0] == 103) {
                n2 = 0;
            }
        }
        if (n2 == 0 ? this.settings.readOnly().reorderCodes.length == 0 : Arrays.equals((int[])object, this.settings.readOnly().reorderCodes)) {
            return;
        }
        CollationSettings collationSettings = this.getDefaultSettings();
        if (n2 == 1 && object[0] == -1) {
            if (this.settings.readOnly() != collationSettings) {
                object = this.getOwnedSettings();
                ((CollationSettings)object).copyReorderingFrom(collationSettings);
                this.setFastLatinOptions((CollationSettings)object);
            }
            return;
        }
        collationSettings = this.getOwnedSettings();
        if (n2 == 0) {
            collationSettings.resetReordering();
        } else {
            collationSettings.setReordering(this.data, (int[])object.clone());
        }
        this.setFastLatinOptions(collationSettings);
    }

    @Override
    public void setStrength(int n) {
        this.checkNotFrozen();
        if (n == this.getStrength()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setStrength(n);
        this.setFastLatinOptions(collationSettings);
    }

    public void setStrengthDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setStrengthDefault(collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setUpperCaseFirst(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.isUpperCaseFirst()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        int n = bl ? 768 : 0;
        collationSettings.setCaseFirst(n);
        this.setFastLatinOptions(collationSettings);
    }

    @Deprecated
    @Override
    public int setVariableTop(String object) {
        this.checkNotFrozen();
        if (object != null && ((String)object).length() != 0) {
            long l;
            long l2;
            boolean bl = this.settings.readOnly().isNumeric();
            if (this.settings.readOnly().dontCheckFCD()) {
                object = new UTF16CollationIterator(this.data, bl, (CharSequence)object, 0);
                l2 = ((CollationIterator)object).nextCE();
                l = ((CollationIterator)object).nextCE();
            } else {
                object = new FCDUTF16CollationIterator(this.data, bl, (CharSequence)object, 0);
                l2 = ((CollationIterator)object).nextCE();
                l = ((CollationIterator)object).nextCE();
            }
            if (l2 != 0x101000100L && l == 0x101000100L) {
                this.internalSetVariableTop(l2 >>> 32);
                return (int)this.settings.readOnly().variableTop;
            }
            throw new IllegalArgumentException("Variable top argument string must map to exactly one collation element");
        }
        throw new IllegalArgumentException("Variable top argument string can not be null or zero in length.");
    }

    @Deprecated
    @Override
    public void setVariableTop(int n) {
        this.checkNotFrozen();
        this.internalSetVariableTop((long)n & 0xFFFFFFFFL);
    }

    private static final class CollationBuffer {
        FCDUTF16CollationIterator leftFCDUTF16Iter;
        FCDUTF16NFDIterator leftFCDUTF16NFDIter;
        UTF16CollationIterator leftUTF16CollIter;
        UTF16NFDIterator leftUTF16NFDIter;
        RawCollationKey rawCollationKey;
        FCDUTF16CollationIterator rightFCDUTF16Iter;
        FCDUTF16NFDIterator rightFCDUTF16NFDIter;
        UTF16CollationIterator rightUTF16CollIter;
        UTF16NFDIterator rightUTF16NFDIter;

        private CollationBuffer(CollationData collationData) {
            this.leftUTF16CollIter = new UTF16CollationIterator(collationData);
            this.rightUTF16CollIter = new UTF16CollationIterator(collationData);
            this.leftFCDUTF16Iter = new FCDUTF16CollationIterator(collationData);
            this.rightFCDUTF16Iter = new FCDUTF16CollationIterator(collationData);
            this.leftUTF16NFDIter = new UTF16NFDIterator();
            this.rightUTF16NFDIter = new UTF16NFDIterator();
            this.leftFCDUTF16NFDIter = new FCDUTF16NFDIterator();
            this.rightFCDUTF16NFDIter = new FCDUTF16NFDIterator();
        }
    }

    private static final class CollationKeyByteSink
    extends CollationKeys.SortKeyByteSink {
        private RawCollationKey key_;

        CollationKeyByteSink(RawCollationKey rawCollationKey) {
            super(rawCollationKey.bytes);
            this.key_ = rawCollationKey;
        }

        @Override
        protected void AppendBeyondCapacity(byte[] arrby, int n, int n2, int n3) {
            if (this.Resize(n2, n3)) {
                System.arraycopy((byte[])arrby, (int)n, (byte[])this.buffer_, (int)n3, (int)n2);
            }
        }

        @Override
        protected boolean Resize(int n, int n2) {
            int n3 = this.buffer_.length * 2;
            int n4 = n * 2 + n2;
            n = n3;
            if (n3 < n4) {
                n = n4;
            }
            n3 = n;
            if (n < 200) {
                n3 = 200;
            }
            byte[] arrby = new byte[n3];
            System.arraycopy((byte[])this.buffer_, (int)0, (byte[])arrby, (int)0, (int)n2);
            this.key_.bytes = arrby;
            this.buffer_ = arrby;
            return true;
        }
    }

    private static final class FCDUTF16NFDIterator
    extends UTF16NFDIterator {
        private StringBuilder str;

        FCDUTF16NFDIterator() {
        }

        void setText(Normalizer2Impl normalizer2Impl, CharSequence charSequence, int n) {
            this.reset();
            int n2 = normalizer2Impl.makeFCD(charSequence, n, charSequence.length(), null);
            if (n2 == charSequence.length()) {
                this.s = charSequence;
                this.pos = n;
            } else {
                Appendable appendable = this.str;
                if (appendable == null) {
                    this.str = new StringBuilder();
                } else {
                    appendable.setLength(0);
                }
                this.str.append(charSequence, n, n2);
                appendable = new Normalizer2Impl.ReorderingBuffer(normalizer2Impl, this.str, charSequence.length() - n);
                normalizer2Impl.makeFCD(charSequence, n2, charSequence.length(), (Normalizer2Impl.ReorderingBuffer)appendable);
                this.s = this.str;
                this.pos = 0;
            }
        }
    }

    private static abstract class NFDIterator {
        private String decomp;
        private int index;

        NFDIterator() {
        }

        final int nextCodePoint() {
            int n = this.index;
            if (n >= 0) {
                if (n == this.decomp.length()) {
                    this.index = -1;
                } else {
                    n = Character.codePointAt(this.decomp, this.index);
                    this.index += Character.charCount(n);
                    return n;
                }
            }
            return this.nextRawCodePoint();
        }

        final int nextDecomposedCodePoint(Normalizer2Impl object, int n) {
            if (this.index >= 0) {
                return n;
            }
            this.decomp = ((Normalizer2Impl)object).getDecomposition(n);
            object = this.decomp;
            if (object == null) {
                return n;
            }
            n = Character.codePointAt((CharSequence)object, 0);
            this.index = Character.charCount(n);
            return n;
        }

        protected abstract int nextRawCodePoint();

        final void reset() {
            this.index = -1;
        }
    }

    private static class UTF16NFDIterator
    extends NFDIterator {
        protected int pos;
        protected CharSequence s;

        UTF16NFDIterator() {
        }

        @Override
        protected int nextRawCodePoint() {
            if (this.pos == this.s.length()) {
                return -1;
            }
            int n = Character.codePointAt(this.s, this.pos);
            this.pos += Character.charCount(n);
            return n;
        }

        void setText(CharSequence charSequence, int n) {
            this.reset();
            this.s = charSequence;
            this.pos = n;
        }
    }

}

