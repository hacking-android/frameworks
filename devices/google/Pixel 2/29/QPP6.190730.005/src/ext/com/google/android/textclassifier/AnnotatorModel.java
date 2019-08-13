/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.textclassifier;

import com.google.android.textclassifier.NamedVariant;
import com.google.android.textclassifier.RemoteActionTemplate;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public final class AnnotatorModel
implements AutoCloseable {
    static final String TYPE_ADDRESS = "address";
    static final String TYPE_DATE = "date";
    static final String TYPE_DATE_TIME = "datetime";
    static final String TYPE_EMAIL = "email";
    static final String TYPE_FLIGHT_NUMBER = "flight";
    static final String TYPE_OTHER = "other";
    static final String TYPE_PHONE = "phone";
    static final String TYPE_UNKNOWN = "";
    static final String TYPE_URL = "url";
    private long annotatorPtr;
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    static {
        System.loadLibrary("textclassifier");
    }

    public AnnotatorModel(int n) {
        this.annotatorPtr = AnnotatorModel.nativeNewAnnotator(n);
        if (this.annotatorPtr != 0L) {
            return;
        }
        throw new IllegalArgumentException("Couldn't initialize TC from file descriptor.");
    }

    public AnnotatorModel(String string) {
        this.annotatorPtr = AnnotatorModel.nativeNewAnnotatorFromPath(string);
        if (this.annotatorPtr != 0L) {
            return;
        }
        throw new IllegalArgumentException("Couldn't initialize TC from given file.");
    }

    public static String getLocales(int n) {
        return AnnotatorModel.nativeGetLocales(n);
    }

    public static String getName(int n) {
        return AnnotatorModel.nativeGetName(n);
    }

    public static int getVersion(int n) {
        return AnnotatorModel.nativeGetVersion(n);
    }

    private native AnnotatedSpan[] nativeAnnotate(long var1, String var3, AnnotationOptions var4);

    private native ClassificationResult[] nativeClassifyText(long var1, String var3, int var4, int var5, ClassificationOptions var6, Object var7, String var8);

    private native void nativeCloseAnnotator(long var1);

    private static native String nativeGetLocales(int var0);

    private static native String nativeGetName(int var0);

    private native long nativeGetNativeModelPtr(long var1);

    private static native int nativeGetVersion(int var0);

    private native boolean nativeInitializeContactEngine(long var1, byte[] var3);

    private native boolean nativeInitializeInstalledAppEngine(long var1, byte[] var3);

    private native boolean nativeInitializeKnowledgeEngine(long var1, byte[] var3);

    private native byte[] nativeLookUpKnowledgeEntity(long var1, String var3);

    private static native long nativeNewAnnotator(int var0);

    private static native long nativeNewAnnotatorFromPath(String var0);

    private native int[] nativeSuggestSelection(long var1, String var3, int var4, int var5, SelectionOptions var6);

    public AnnotatedSpan[] annotate(String string, AnnotationOptions annotationOptions) {
        return this.nativeAnnotate(this.annotatorPtr, string, annotationOptions);
    }

    public ClassificationResult[] classifyText(String string, int n, int n2, ClassificationOptions classificationOptions) {
        return this.classifyText(string, n, n2, classificationOptions, null, null);
    }

    public ClassificationResult[] classifyText(String string, int n, int n2, ClassificationOptions classificationOptions, Object object, String string2) {
        return this.nativeClassifyText(this.annotatorPtr, string, n, n2, classificationOptions, object, string2);
    }

    @Override
    public void close() {
        if (this.isClosed.compareAndSet(false, true)) {
            this.nativeCloseAnnotator(this.annotatorPtr);
            this.annotatorPtr = 0L;
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    long getNativeAnnotator() {
        return this.nativeGetNativeModelPtr(this.annotatorPtr);
    }

    public void initializeContactEngine(byte[] arrby) {
        if (this.nativeInitializeContactEngine(this.annotatorPtr, arrby)) {
            return;
        }
        throw new IllegalArgumentException("Couldn't initialize the contact engine");
    }

    public void initializeInstalledAppEngine(byte[] arrby) {
        if (this.nativeInitializeInstalledAppEngine(this.annotatorPtr, arrby)) {
            return;
        }
        throw new IllegalArgumentException("Couldn't initialize the installed app engine");
    }

    public void initializeKnowledgeEngine(byte[] arrby) {
        if (this.nativeInitializeKnowledgeEngine(this.annotatorPtr, arrby)) {
            return;
        }
        throw new IllegalArgumentException("Couldn't initialize the KG engine");
    }

    public byte[] lookUpKnowledgeEntity(String string) {
        return this.nativeLookUpKnowledgeEntity(this.annotatorPtr, string);
    }

    public int[] suggestSelection(String string, int n, int n2, SelectionOptions selectionOptions) {
        return this.nativeSuggestSelection(this.annotatorPtr, string, n, n2, selectionOptions);
    }

    public static final class AnnotatedSpan {
        private final ClassificationResult[] classification;
        private final int endIndex;
        private final int startIndex;

        AnnotatedSpan(int n, int n2, ClassificationResult[] arrclassificationResult) {
            this.startIndex = n;
            this.endIndex = n2;
            this.classification = arrclassificationResult;
        }

        public ClassificationResult[] getClassification() {
            return this.classification;
        }

        public int getEndIndex() {
            return this.endIndex;
        }

        public int getStartIndex() {
            return this.startIndex;
        }
    }

    public static final class AnnotationOptions {
        private final int annotationUsecase;
        private final String detectedTextLanguageTags;
        private final String[] entityTypes;
        private final boolean isSerializedEntityDataEnabled;
        private final String locales;
        private final long referenceTimeMsUtc;
        private final String referenceTimezone;

        public AnnotationOptions(long l, String string, String string2, String string3) {
            this(l, string, string2, string3, null, AnnotationUsecase.SMART.getValue(), false);
        }

        public AnnotationOptions(long l, String arrstring, String string, String string2, Collection<String> collection, int n, boolean bl) {
            this.referenceTimeMsUtc = l;
            this.referenceTimezone = arrstring;
            this.locales = string;
            this.detectedTextLanguageTags = string2;
            arrstring = new String[]{};
            if (collection != null) {
                arrstring = collection.toArray(arrstring);
            }
            this.entityTypes = arrstring;
            this.annotationUsecase = n;
            this.isSerializedEntityDataEnabled = bl;
        }

        public int getAnnotationUsecase() {
            return this.annotationUsecase;
        }

        public String getDetectedTextLanguageTags() {
            return this.detectedTextLanguageTags;
        }

        public String[] getEntityTypes() {
            return this.entityTypes;
        }

        public String getLocale() {
            return this.locales;
        }

        public long getReferenceTimeMsUtc() {
            return this.referenceTimeMsUtc;
        }

        public String getReferenceTimezone() {
            return this.referenceTimezone;
        }

        public boolean isSerializedEntityDataEnabled() {
            return this.isSerializedEntityDataEnabled;
        }
    }

    public static enum AnnotationUsecase {
        SMART(0),
        RAW(1);
        
        private final int value;

        private AnnotationUsecase(int n2) {
            this.value = n2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static final class ClassificationOptions {
        private final int annotationUsecase;
        private final String detectedTextLanguageTags;
        private final String locales;
        private final long referenceTimeMsUtc;
        private final String referenceTimezone;

        public ClassificationOptions(long l, String string, String string2, String string3) {
            this(l, string, string2, string3, AnnotationUsecase.SMART.getValue());
        }

        public ClassificationOptions(long l, String string, String string2, String string3, int n) {
            this.referenceTimeMsUtc = l;
            this.referenceTimezone = string;
            this.locales = string2;
            this.detectedTextLanguageTags = string3;
            this.annotationUsecase = n;
        }

        public int getAnnotationUsecase() {
            return this.annotationUsecase;
        }

        public String getDetectedTextLanguageTags() {
            return this.detectedTextLanguageTags;
        }

        public String getLocale() {
            return this.locales;
        }

        public long getReferenceTimeMsUtc() {
            return this.referenceTimeMsUtc;
        }

        public String getReferenceTimezone() {
            return this.referenceTimezone;
        }
    }

    public static final class ClassificationResult {
        private final String appName;
        private final String appPackageName;
        private final String collection;
        private final String contactEmailAddress;
        private final String contactGivenName;
        private final String contactId;
        private final String contactName;
        private final String contactNickname;
        private final String contactPhoneNumber;
        private final DatetimeResult datetimeResult;
        private final long durationMs;
        private final NamedVariant[] entityData;
        private final long numericValue;
        private final RemoteActionTemplate[] remoteActionTemplates;
        private final float score;
        private final byte[] serializedEntityData;
        private final byte[] serializedKnowledgeResult;

        public ClassificationResult(String string, float f, DatetimeResult datetimeResult, byte[] arrby, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, NamedVariant[] arrnamedVariant, byte[] arrby2, RemoteActionTemplate[] arrremoteActionTemplate, long l, long l2) {
            this.collection = string;
            this.score = f;
            this.datetimeResult = datetimeResult;
            this.serializedKnowledgeResult = arrby;
            this.contactName = string2;
            this.contactGivenName = string3;
            this.contactNickname = string4;
            this.contactEmailAddress = string5;
            this.contactPhoneNumber = string6;
            this.contactId = string7;
            this.appName = string8;
            this.appPackageName = string9;
            this.entityData = arrnamedVariant;
            this.serializedEntityData = arrby2;
            this.remoteActionTemplates = arrremoteActionTemplate;
            this.durationMs = l;
            this.numericValue = l2;
        }

        public String getAppName() {
            return this.appName;
        }

        public String getAppPackageName() {
            return this.appPackageName;
        }

        public String getCollection() {
            return this.collection;
        }

        public String getContactEmailAddress() {
            return this.contactEmailAddress;
        }

        public String getContactGivenName() {
            return this.contactGivenName;
        }

        public String getContactId() {
            return this.contactId;
        }

        public String getContactName() {
            return this.contactName;
        }

        public String getContactNickname() {
            return this.contactNickname;
        }

        public String getContactPhoneNumber() {
            return this.contactPhoneNumber;
        }

        public DatetimeResult getDatetimeResult() {
            return this.datetimeResult;
        }

        public long getDurationMs() {
            return this.durationMs;
        }

        public NamedVariant[] getEntityData() {
            return this.entityData;
        }

        public long getNumericValue() {
            return this.numericValue;
        }

        public RemoteActionTemplate[] getRemoteActionTemplates() {
            return this.remoteActionTemplates;
        }

        public float getScore() {
            return this.score;
        }

        public byte[] getSerializedEntityData() {
            return this.serializedEntityData;
        }

        public byte[] getSerializedKnowledgeResult() {
            return this.serializedKnowledgeResult;
        }
    }

    public static final class DatetimeResult {
        public static final int GRANULARITY_DAY = 3;
        public static final int GRANULARITY_HOUR = 4;
        public static final int GRANULARITY_MINUTE = 5;
        public static final int GRANULARITY_MONTH = 1;
        public static final int GRANULARITY_SECOND = 6;
        public static final int GRANULARITY_WEEK = 2;
        public static final int GRANULARITY_YEAR = 0;
        private final int granularity;
        private final long timeMsUtc;

        public DatetimeResult(long l, int n) {
            this.timeMsUtc = l;
            this.granularity = n;
        }

        public int getGranularity() {
            return this.granularity;
        }

        public long getTimeMsUtc() {
            return this.timeMsUtc;
        }
    }

    public static final class SelectionOptions {
        private final int annotationUsecase;
        private final String detectedTextLanguageTags;
        private final String locales;

        public SelectionOptions(String string, String string2) {
            this(string, string2, AnnotationUsecase.SMART.getValue());
        }

        public SelectionOptions(String string, String string2, int n) {
            this.locales = string;
            this.detectedTextLanguageTags = string2;
            this.annotationUsecase = n;
        }

        public int getAnnotationUsecase() {
            return this.annotationUsecase;
        }

        public String getDetectedTextLanguageTags() {
            return this.detectedTextLanguageTags;
        }

        public String getLocales() {
            return this.locales;
        }
    }

}

