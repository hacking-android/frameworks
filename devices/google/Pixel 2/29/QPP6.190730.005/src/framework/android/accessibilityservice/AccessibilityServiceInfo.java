/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.accessibilityservice;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityEvent;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

public class AccessibilityServiceInfo
implements Parcelable {
    public static final int CAPABILITY_CAN_CONTROL_MAGNIFICATION = 16;
    public static final int CAPABILITY_CAN_PERFORM_GESTURES = 32;
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_FINGERPRINT_GESTURES = 64;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
    public static final Parcelable.Creator<AccessibilityServiceInfo> CREATOR = new Parcelable.Creator<AccessibilityServiceInfo>(){

        @Override
        public AccessibilityServiceInfo createFromParcel(Parcel parcel) {
            AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
            accessibilityServiceInfo.initFromParcel(parcel);
            return accessibilityServiceInfo;
        }

        public AccessibilityServiceInfo[] newArray(int n) {
            return new AccessibilityServiceInfo[n];
        }
    };
    public static final int DEFAULT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_AUDIBLE = 4;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FEEDBACK_GENERIC = 16;
    public static final int FEEDBACK_HAPTIC = 2;
    public static final int FEEDBACK_SPOKEN = 1;
    public static final int FEEDBACK_VISUAL = 8;
    public static final int FLAG_ENABLE_ACCESSIBILITY_VOLUME = 128;
    public static final int FLAG_FORCE_DIRECT_BOOT_AWARE = 65536;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_ACCESSIBILITY_BUTTON = 256;
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_FINGERPRINT_GESTURES = 512;
    public static final int FLAG_REQUEST_SHORTCUT_WARNING_DIALOG_SPOKEN_FEEDBACK = 1024;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
    public static final int FLAG_RETRIEVE_INTERACTIVE_WINDOWS = 64;
    private static final String TAG_ACCESSIBILITY_SERVICE = "accessibility-service";
    private static SparseArray<CapabilityInfo> sAvailableCapabilityInfos;
    public boolean crashed;
    public int eventTypes;
    public int feedbackType;
    public int flags;
    private int mCapabilities;
    private ComponentName mComponentName;
    private int mDescriptionResId;
    private int mInteractiveUiTimeout;
    private int mNonInteractiveUiTimeout;
    private String mNonLocalizedDescription;
    private String mNonLocalizedSummary;
    private ResolveInfo mResolveInfo;
    private String mSettingsActivityName;
    private int mSummaryResId;
    public long notificationTimeout;
    public String[] packageNames;

    public AccessibilityServiceInfo() {
    }

    /*
     * Exception decompiling
     */
    public AccessibilityServiceInfo(ResolveInfo var1_1, Context var2_3) throws XmlPullParserException, IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[WHILELOOP]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static void appendCapabilities(StringBuilder stringBuilder, int n) {
        stringBuilder.append("capabilities:");
        stringBuilder.append("[");
        while (n != 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            stringBuilder.append(AccessibilityServiceInfo.capabilityToString(n2));
            if ((n &= n2) == 0) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
    }

    private static void appendEventTypes(StringBuilder stringBuilder, int n) {
        stringBuilder.append("eventTypes:");
        stringBuilder.append("[");
        while (n != 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            stringBuilder.append(AccessibilityEvent.eventTypeToString(n2));
            if ((n &= n2) == 0) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
    }

    private static void appendFeedbackTypes(StringBuilder stringBuilder, int n) {
        stringBuilder.append("feedbackTypes:");
        stringBuilder.append("[");
        while (n != 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            stringBuilder.append(AccessibilityServiceInfo.feedbackTypeToString(n2));
            if ((n &= n2) == 0) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
    }

    private static void appendFlags(StringBuilder stringBuilder, int n) {
        stringBuilder.append("flags:");
        stringBuilder.append("[");
        while (n != 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            stringBuilder.append(AccessibilityServiceInfo.flagToString(n2));
            if ((n &= n2) == 0) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
    }

    private static void appendPackageNames(StringBuilder stringBuilder, String[] arrstring) {
        stringBuilder.append("packageNames:");
        stringBuilder.append("[");
        if (arrstring != null) {
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                stringBuilder.append(arrstring[i]);
                if (i >= n - 1) continue;
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
    }

    public static String capabilityToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 32) {
                                if (n != 64) {
                                    return "UNKNOWN";
                                }
                                return "CAPABILITY_CAN_REQUEST_FINGERPRINT_GESTURES";
                            }
                            return "CAPABILITY_CAN_PERFORM_GESTURES";
                        }
                        return "CAPABILITY_CAN_CONTROL_MAGNIFICATION";
                    }
                    return "CAPABILITY_CAN_REQUEST_FILTER_KEY_EVENTS";
                }
                return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
            }
            return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
        }
        return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
    }

    public static String feedbackTypeToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        while (n != 0) {
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            n &= n2;
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 4) {
                        if (n2 != 8) {
                            if (n2 != 16) {
                                if (n2 != 32) continue;
                                if (stringBuilder.length() > 1) {
                                    stringBuilder.append(", ");
                                }
                                stringBuilder.append("FEEDBACK_BRAILLE");
                                continue;
                            }
                            if (stringBuilder.length() > 1) {
                                stringBuilder.append(", ");
                            }
                            stringBuilder.append("FEEDBACK_GENERIC");
                            continue;
                        }
                        if (stringBuilder.length() > 1) {
                            stringBuilder.append(", ");
                        }
                        stringBuilder.append("FEEDBACK_VISUAL");
                        continue;
                    }
                    if (stringBuilder.length() > 1) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append("FEEDBACK_AUDIBLE");
                    continue;
                }
                if (stringBuilder.length() > 1) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append("FEEDBACK_HAPTIC");
                continue;
            }
            if (stringBuilder.length() > 1) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("FEEDBACK_SPOKEN");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static boolean fingerprintAvailable(Context context) {
        boolean bl = context.getPackageManager().hasSystemFeature("android.hardware.fingerprint") && context.getSystemService(FingerprintManager.class).isHardwareDetected();
        return bl;
    }

    public static String flagToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 32) {
                                if (n != 64) {
                                    if (n != 128) {
                                        if (n != 256) {
                                            if (n != 512) {
                                                if (n != 1024) {
                                                    return null;
                                                }
                                                return "FLAG_REQUEST_SHORTCUT_WARNING_DIALOG_SPOKEN_FEEDBACK";
                                            }
                                            return "FLAG_REQUEST_FINGERPRINT_GESTURES";
                                        }
                                        return "FLAG_REQUEST_ACCESSIBILITY_BUTTON";
                                    }
                                    return "FLAG_ENABLE_ACCESSIBILITY_VOLUME";
                                }
                                return "FLAG_RETRIEVE_INTERACTIVE_WINDOWS";
                            }
                            return "FLAG_REQUEST_FILTER_KEY_EVENTS";
                        }
                        return "FLAG_REPORT_VIEW_IDS";
                    }
                    return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
                }
                return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
            }
            return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
        }
        return "DEFAULT";
    }

    private static SparseArray<CapabilityInfo> getCapabilityInfoSparseArray(Context context) {
        if (sAvailableCapabilityInfos == null) {
            sAvailableCapabilityInfos = new SparseArray();
            sAvailableCapabilityInfos.put(1, new CapabilityInfo(1, 17039638, 17039632));
            sAvailableCapabilityInfos.put(2, new CapabilityInfo(2, 17039637, 17039631));
            sAvailableCapabilityInfos.put(8, new CapabilityInfo(8, 17039636, 17039630));
            sAvailableCapabilityInfos.put(16, new CapabilityInfo(16, 17039634, 17039628));
            sAvailableCapabilityInfos.put(32, new CapabilityInfo(32, 17039635, 17039629));
            if (context == null || AccessibilityServiceInfo.fingerprintAvailable(context)) {
                sAvailableCapabilityInfos.put(64, new CapabilityInfo(64, 17039633, 17039627));
            }
        }
        return sAvailableCapabilityInfos;
    }

    private void initFromParcel(Parcel parcel) {
        this.eventTypes = parcel.readInt();
        this.packageNames = parcel.readStringArray();
        this.feedbackType = parcel.readInt();
        this.notificationTimeout = parcel.readLong();
        this.mNonInteractiveUiTimeout = parcel.readInt();
        this.mInteractiveUiTimeout = parcel.readInt();
        this.flags = parcel.readInt();
        boolean bl = parcel.readInt() != 0;
        this.crashed = bl;
        this.mComponentName = (ComponentName)parcel.readParcelable(this.getClass().getClassLoader());
        this.mResolveInfo = (ResolveInfo)parcel.readParcelable(null);
        this.mSettingsActivityName = parcel.readString();
        this.mCapabilities = parcel.readInt();
        this.mSummaryResId = parcel.readInt();
        this.mNonLocalizedSummary = parcel.readString();
        this.mDescriptionResId = parcel.readInt();
        this.mNonLocalizedDescription = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo)object;
        object = this.mComponentName;
        return !(object == null ? accessibilityServiceInfo.mComponentName != null : !((ComponentName)object).equals(accessibilityServiceInfo.mComponentName));
    }

    public boolean getCanRetrieveWindowContent() {
        int n = this.mCapabilities;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public List<CapabilityInfo> getCapabilityInfos() {
        return this.getCapabilityInfos(null);
    }

    public List<CapabilityInfo> getCapabilityInfos(Context object) {
        int n;
        if (this.mCapabilities == 0) {
            return Collections.emptyList();
        }
        ArrayList<CapabilityInfo> arrayList = new ArrayList<CapabilityInfo>();
        object = AccessibilityServiceInfo.getCapabilityInfoSparseArray((Context)object);
        for (int i = this.mCapabilities; i != 0; i &= n) {
            n = 1 << Integer.numberOfTrailingZeros(i);
            CapabilityInfo capabilityInfo = (CapabilityInfo)((SparseArray)object).get(n);
            if (capabilityInfo == null) continue;
            arrayList.add(capabilityInfo);
        }
        return arrayList;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public String getDescription() {
        return this.mNonLocalizedDescription;
    }

    public String getId() {
        return this.mComponentName.flattenToShortString();
    }

    public int getInteractiveUiTimeoutMillis() {
        return this.mInteractiveUiTimeout;
    }

    public int getNonInteractiveUiTimeoutMillis() {
        return this.mNonInteractiveUiTimeout;
    }

    public ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    public String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public int hashCode() {
        ComponentName componentName = this.mComponentName;
        int n = componentName == null ? 0 : componentName.hashCode();
        return n + 31;
    }

    public boolean isDirectBootAware() {
        boolean bl = (this.flags & 65536) != 0 || this.mResolveInfo.serviceInfo.directBootAware;
        return bl;
    }

    public String loadDescription(PackageManager object) {
        if (this.mDescriptionResId == 0) {
            return this.mNonLocalizedDescription;
        }
        ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
        if ((object = ((PackageManager)object).getText(serviceInfo.packageName, this.mDescriptionResId, serviceInfo.applicationInfo)) != null) {
            return object.toString().trim();
        }
        return null;
    }

    public CharSequence loadSummary(PackageManager object) {
        if (this.mSummaryResId == 0) {
            return this.mNonLocalizedSummary;
        }
        ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
        if ((object = ((PackageManager)object).getText(serviceInfo.packageName, this.mSummaryResId, serviceInfo.applicationInfo)) != null) {
            return object.toString().trim();
        }
        return null;
    }

    @UnsupportedAppUsage
    public void setCapabilities(int n) {
        this.mCapabilities = n;
    }

    public void setComponentName(ComponentName componentName) {
        this.mComponentName = componentName;
    }

    public void setInteractiveUiTimeoutMillis(int n) {
        this.mInteractiveUiTimeout = n;
    }

    public void setNonInteractiveUiTimeoutMillis(int n) {
        this.mNonInteractiveUiTimeout = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        AccessibilityServiceInfo.appendEventTypes(stringBuilder, this.eventTypes);
        stringBuilder.append(", ");
        AccessibilityServiceInfo.appendPackageNames(stringBuilder, this.packageNames);
        stringBuilder.append(", ");
        AccessibilityServiceInfo.appendFeedbackTypes(stringBuilder, this.feedbackType);
        stringBuilder.append(", ");
        stringBuilder.append("notificationTimeout: ");
        stringBuilder.append(this.notificationTimeout);
        stringBuilder.append(", ");
        stringBuilder.append("nonInteractiveUiTimeout: ");
        stringBuilder.append(this.mNonInteractiveUiTimeout);
        stringBuilder.append(", ");
        stringBuilder.append("interactiveUiTimeout: ");
        stringBuilder.append(this.mInteractiveUiTimeout);
        stringBuilder.append(", ");
        AccessibilityServiceInfo.appendFlags(stringBuilder, this.flags);
        stringBuilder.append(", ");
        stringBuilder.append("id: ");
        stringBuilder.append(this.getId());
        stringBuilder.append(", ");
        stringBuilder.append("resolveInfo: ");
        stringBuilder.append(this.mResolveInfo);
        stringBuilder.append(", ");
        stringBuilder.append("settingsActivityName: ");
        stringBuilder.append(this.mSettingsActivityName);
        stringBuilder.append(", ");
        stringBuilder.append("summary: ");
        stringBuilder.append(this.mNonLocalizedSummary);
        stringBuilder.append(", ");
        AccessibilityServiceInfo.appendCapabilities(stringBuilder, this.mCapabilities);
        return stringBuilder.toString();
    }

    public void updateDynamicallyConfigurableProperties(AccessibilityServiceInfo accessibilityServiceInfo) {
        this.eventTypes = accessibilityServiceInfo.eventTypes;
        this.packageNames = accessibilityServiceInfo.packageNames;
        this.feedbackType = accessibilityServiceInfo.feedbackType;
        this.notificationTimeout = accessibilityServiceInfo.notificationTimeout;
        this.mNonInteractiveUiTimeout = accessibilityServiceInfo.mNonInteractiveUiTimeout;
        this.mInteractiveUiTimeout = accessibilityServiceInfo.mInteractiveUiTimeout;
        this.flags = accessibilityServiceInfo.flags;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.eventTypes);
        parcel.writeStringArray(this.packageNames);
        parcel.writeInt(this.feedbackType);
        parcel.writeLong(this.notificationTimeout);
        parcel.writeInt(this.mNonInteractiveUiTimeout);
        parcel.writeInt(this.mInteractiveUiTimeout);
        parcel.writeInt(this.flags);
        parcel.writeInt((int)this.crashed);
        parcel.writeParcelable(this.mComponentName, n);
        parcel.writeParcelable(this.mResolveInfo, 0);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeInt(this.mCapabilities);
        parcel.writeInt(this.mSummaryResId);
        parcel.writeString(this.mNonLocalizedSummary);
        parcel.writeInt(this.mDescriptionResId);
        parcel.writeString(this.mNonLocalizedDescription);
    }

    public static final class CapabilityInfo {
        public final int capability;
        public final int descResId;
        public final int titleResId;

        public CapabilityInfo(int n, int n2, int n3) {
            this.capability = n;
            this.titleResId = n2;
            this.descResId = n3;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FeedbackType {
    }

}

