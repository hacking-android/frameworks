/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.media.tv;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiUtils;
import android.media.tv.TvInputHardwareInfo;
import android.media.tv.TvInputManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class TvInputInfo
implements Parcelable {
    public static final Parcelable.Creator<TvInputInfo> CREATOR = new Parcelable.Creator<TvInputInfo>(){

        @Override
        public TvInputInfo createFromParcel(Parcel parcel) {
            return new TvInputInfo(parcel);
        }

        public TvInputInfo[] newArray(int n) {
            return new TvInputInfo[n];
        }
    };
    private static final boolean DEBUG = false;
    public static final String EXTRA_INPUT_ID = "android.media.tv.extra.INPUT_ID";
    private static final String TAG = "TvInputInfo";
    public static final int TYPE_COMPONENT = 1004;
    public static final int TYPE_COMPOSITE = 1001;
    public static final int TYPE_DISPLAY_PORT = 1008;
    public static final int TYPE_DVI = 1006;
    public static final int TYPE_HDMI = 1007;
    public static final int TYPE_OTHER = 1000;
    public static final int TYPE_SCART = 1003;
    public static final int TYPE_SVIDEO = 1002;
    public static final int TYPE_TUNER = 0;
    public static final int TYPE_VGA = 1005;
    private final boolean mCanRecord;
    private final Bundle mExtras;
    private final int mHdmiConnectionRelativePosition;
    private final HdmiDeviceInfo mHdmiDeviceInfo;
    private final Icon mIcon;
    private final Icon mIconDisconnected;
    private final Icon mIconStandby;
    private Uri mIconUri;
    private final String mId;
    private final boolean mIsConnectedToHdmiSwitch;
    private final boolean mIsHardwareInput;
    private final CharSequence mLabel;
    private final int mLabelResId;
    private final String mParentId;
    private final ResolveInfo mService;
    private final String mSetupActivity;
    private final int mTunerCount;
    private final int mType;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    private TvInputInfo(ResolveInfo resolveInfo, String string2, int n, boolean bl, CharSequence charSequence, int n2, Icon icon, Icon icon2, Icon icon3, String string3, boolean bl2, int n3, HdmiDeviceInfo hdmiDeviceInfo, boolean bl3, int n4, String string4, Bundle bundle) {
        this.mService = resolveInfo;
        this.mId = string2;
        this.mType = n;
        this.mIsHardwareInput = bl;
        this.mLabel = charSequence;
        this.mLabelResId = n2;
        this.mIcon = icon;
        this.mIconStandby = icon2;
        this.mIconDisconnected = icon3;
        this.mSetupActivity = string3;
        this.mCanRecord = bl2;
        this.mTunerCount = n3;
        this.mHdmiDeviceInfo = hdmiDeviceInfo;
        this.mIsConnectedToHdmiSwitch = bl3;
        this.mHdmiConnectionRelativePosition = n4;
        this.mParentId = string4;
        this.mExtras = bundle;
    }

    private TvInputInfo(Parcel parcel) {
        this.mService = ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mType = parcel.readInt();
        byte by = parcel.readByte();
        boolean bl = false;
        boolean bl2 = by == 1;
        this.mIsHardwareInput = bl2;
        this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIconUri = (Uri)parcel.readParcelable(null);
        this.mLabelResId = parcel.readInt();
        this.mIcon = (Icon)parcel.readParcelable(null);
        this.mIconStandby = (Icon)parcel.readParcelable(null);
        this.mIconDisconnected = (Icon)parcel.readParcelable(null);
        this.mSetupActivity = parcel.readString();
        bl2 = parcel.readByte() == 1;
        this.mCanRecord = bl2;
        this.mTunerCount = parcel.readInt();
        this.mHdmiDeviceInfo = (HdmiDeviceInfo)parcel.readParcelable(null);
        bl2 = bl;
        if (parcel.readByte() == 1) {
            bl2 = true;
        }
        this.mIsConnectedToHdmiSwitch = bl2;
        this.mHdmiConnectionRelativePosition = parcel.readInt();
        this.mParentId = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context context, ResolveInfo resolveInfo, HdmiDeviceInfo hdmiDeviceInfo, String string2, int n, Icon icon) throws XmlPullParserException, IOException {
        return new Builder(context, resolveInfo).setHdmiDeviceInfo(hdmiDeviceInfo).setParentId(string2).setLabel(n).setIcon(icon).build();
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context object, ResolveInfo resolveInfo, HdmiDeviceInfo hdmiDeviceInfo, String string2, String string3, Uri uri) throws XmlPullParserException, IOException {
        object = new Builder((Context)object, resolveInfo).setHdmiDeviceInfo(hdmiDeviceInfo).setParentId(string2).setLabel(string3).build();
        ((TvInputInfo)object).mIconUri = uri;
        return object;
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context context, ResolveInfo resolveInfo, TvInputHardwareInfo tvInputHardwareInfo, int n, Icon icon) throws XmlPullParserException, IOException {
        return new Builder(context, resolveInfo).setTvInputHardwareInfo(tvInputHardwareInfo).setLabel(n).setIcon(icon).build();
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context object, ResolveInfo resolveInfo, TvInputHardwareInfo tvInputHardwareInfo, String string2, Uri uri) throws XmlPullParserException, IOException {
        object = new Builder((Context)object, resolveInfo).setTvInputHardwareInfo(tvInputHardwareInfo).setLabel(string2).build();
        ((TvInputInfo)object).mIconUri = uri;
        return object;
    }

    private Drawable loadServiceIcon(Context context) {
        if (this.mService.serviceInfo.icon == 0 && this.mService.serviceInfo.applicationInfo.icon == 0) {
            return null;
        }
        return this.mService.serviceInfo.loadIcon(context.getPackageManager());
    }

    public boolean canRecord() {
        return this.mCanRecord;
    }

    @Deprecated
    public Intent createSettingsIntent() {
        return null;
    }

    public Intent createSetupIntent() {
        if (!TextUtils.isEmpty(this.mSetupActivity)) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName(this.mService.serviceInfo.packageName, this.mSetupActivity);
            intent.putExtra(EXTRA_INPUT_ID, this.getId());
            return intent;
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof TvInputInfo)) {
            return false;
        }
        object = (TvInputInfo)object;
        if (!(Objects.equals(this.mService, ((TvInputInfo)object).mService) && TextUtils.equals(this.mId, ((TvInputInfo)object).mId) && this.mType == ((TvInputInfo)object).mType && this.mIsHardwareInput == ((TvInputInfo)object).mIsHardwareInput && TextUtils.equals(this.mLabel, ((TvInputInfo)object).mLabel) && Objects.equals(this.mIconUri, ((TvInputInfo)object).mIconUri) && this.mLabelResId == ((TvInputInfo)object).mLabelResId && Objects.equals(this.mIcon, ((TvInputInfo)object).mIcon) && Objects.equals(this.mIconStandby, ((TvInputInfo)object).mIconStandby) && Objects.equals(this.mIconDisconnected, ((TvInputInfo)object).mIconDisconnected) && TextUtils.equals(this.mSetupActivity, ((TvInputInfo)object).mSetupActivity) && this.mCanRecord == ((TvInputInfo)object).mCanRecord && this.mTunerCount == ((TvInputInfo)object).mTunerCount && Objects.equals(this.mHdmiDeviceInfo, ((TvInputInfo)object).mHdmiDeviceInfo) && this.mIsConnectedToHdmiSwitch == ((TvInputInfo)object).mIsConnectedToHdmiSwitch && this.mHdmiConnectionRelativePosition == ((TvInputInfo)object).mHdmiConnectionRelativePosition && TextUtils.equals(this.mParentId, ((TvInputInfo)object).mParentId) && Objects.equals(this.mExtras, ((TvInputInfo)object).mExtras))) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int getHdmiConnectionRelativePosition() {
        return this.mHdmiConnectionRelativePosition;
    }

    @SystemApi
    public HdmiDeviceInfo getHdmiDeviceInfo() {
        if (this.mType == 1007) {
            return this.mHdmiDeviceInfo;
        }
        return null;
    }

    public String getId() {
        return this.mId;
    }

    public String getParentId() {
        return this.mParentId;
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public int getTunerCount() {
        return this.mTunerCount;
    }

    public int getType() {
        return this.mType;
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    @SystemApi
    public boolean isConnectedToHdmiSwitch() {
        return this.mIsConnectedToHdmiSwitch;
    }

    @SystemApi
    public boolean isHardwareInput() {
        return this.mIsHardwareInput;
    }

    public boolean isHidden(Context context) {
        return TvInputSettings.isHidden(context, this.mId, UserHandle.myUserId());
    }

    public boolean isPassthroughInput() {
        boolean bl = this.mType != 0;
        return bl;
    }

    public CharSequence loadCustomLabel(Context context) {
        return TvInputSettings.getCustomLabel(context, this.mId, UserHandle.myUserId());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Drawable loadIcon(Context context) {
        object = this.mIcon;
        if (object != null) {
            return object.loadDrawable(context);
        }
        if (this.mIconUri == null) return this.loadServiceIcon(context);
        object = context.getContentResolver().openInputStream(this.mIconUri);
        object2 = Drawable.createFromStream((InputStream)object, null);
        if (object2 == null) ** GOTO lbl14
        if (object == null) return object2;
        TvInputInfo.$closeResource(null, (AutoCloseable)object);
        return object2;
lbl14: // 1 sources:
        if (object == null) return this.loadServiceIcon(context);
        TvInputInfo.$closeResource(null, (AutoCloseable)object);
        return this.loadServiceIcon(context);
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (object == null) throw throwable2;
                try {
                    TvInputInfo.$closeResource(throwable, (AutoCloseable)object);
                    throw throwable2;
                }
                catch (IOException iOException) {
                    object2 = new StringBuilder();
                    object2.append("Loading the default icon due to a failure on loading ");
                    object2.append(this.mIconUri);
                    Log.w("TvInputInfo", object2.toString(), iOException);
                }
            }
        }
        return this.loadServiceIcon(context);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SystemApi
    public Drawable loadIcon(Context object, int n) {
        if (n == 0) {
            return this.loadIcon((Context)object);
        }
        if (n == 1) {
            Icon icon = this.mIconStandby;
            if (icon == null) return null;
            return icon.loadDrawable((Context)object);
        }
        if (n == 2) {
            Icon icon = this.mIconDisconnected;
            if (icon == null) return null;
            return icon.loadDrawable((Context)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown state: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public CharSequence loadLabel(Context context) {
        if (this.mLabelResId != 0) {
            return context.getPackageManager().getText(this.mService.serviceInfo.packageName, this.mLabelResId, null);
        }
        if (!TextUtils.isEmpty(this.mLabel)) {
            return this.mLabel;
        }
        return this.mService.loadLabel(context.getPackageManager());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TvInputInfo{id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(", pkg=");
        stringBuilder.append(this.mService.serviceInfo.packageName);
        stringBuilder.append(", service=");
        stringBuilder.append(this.mService.serviceInfo.name);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mService.writeToParcel(parcel, n);
        parcel.writeString(this.mId);
        parcel.writeInt(this.mType);
        parcel.writeByte((byte)this.mIsHardwareInput);
        TextUtils.writeToParcel(this.mLabel, parcel, n);
        parcel.writeParcelable(this.mIconUri, n);
        parcel.writeInt(this.mLabelResId);
        parcel.writeParcelable(this.mIcon, n);
        parcel.writeParcelable(this.mIconStandby, n);
        parcel.writeParcelable(this.mIconDisconnected, n);
        parcel.writeString(this.mSetupActivity);
        parcel.writeByte((byte)this.mCanRecord);
        parcel.writeInt(this.mTunerCount);
        parcel.writeParcelable(this.mHdmiDeviceInfo, n);
        parcel.writeByte((byte)this.mIsConnectedToHdmiSwitch);
        parcel.writeInt(this.mHdmiConnectionRelativePosition);
        parcel.writeString(this.mParentId);
        parcel.writeBundle(this.mExtras);
    }

    public static final class Builder {
        private static final String DELIMITER_INFO_IN_ID = "/";
        private static final int LENGTH_HDMI_DEVICE_ID = 2;
        private static final int LENGTH_HDMI_PHYSICAL_ADDRESS = 4;
        private static final String PREFIX_HARDWARE_DEVICE = "HW";
        private static final String PREFIX_HDMI_DEVICE = "HDMI";
        private static final String XML_START_TAG_NAME = "tv-input";
        private static final SparseIntArray sHardwareTypeToTvInputType = new SparseIntArray();
        private Boolean mCanRecord;
        private final Context mContext;
        private Bundle mExtras;
        private HdmiDeviceInfo mHdmiDeviceInfo;
        private Icon mIcon;
        private Icon mIconDisconnected;
        private Icon mIconStandby;
        private CharSequence mLabel;
        private int mLabelResId;
        private String mParentId;
        private final ResolveInfo mResolveInfo;
        private String mSetupActivity;
        private Integer mTunerCount;
        private TvInputHardwareInfo mTvInputHardwareInfo;

        static {
            sHardwareTypeToTvInputType.put(1, 1000);
            sHardwareTypeToTvInputType.put(2, 0);
            sHardwareTypeToTvInputType.put(3, 1001);
            sHardwareTypeToTvInputType.put(4, 1002);
            sHardwareTypeToTvInputType.put(5, 1003);
            sHardwareTypeToTvInputType.put(6, 1004);
            sHardwareTypeToTvInputType.put(7, 1005);
            sHardwareTypeToTvInputType.put(8, 1006);
            sHardwareTypeToTvInputType.put(9, 1007);
            sHardwareTypeToTvInputType.put(10, 1008);
        }

        public Builder(Context context, ComponentName parcelable) {
            if (context != null) {
                parcelable = new Intent("android.media.tv.TvInputService").setComponent((ComponentName)parcelable);
                this.mResolveInfo = context.getPackageManager().resolveService((Intent)parcelable, 132);
                if (this.mResolveInfo != null) {
                    this.mContext = context;
                    return;
                }
                throw new IllegalArgumentException("Invalid component. Can't find the service.");
            }
            throw new IllegalArgumentException("context cannot be null.");
        }

        public Builder(Context context, ResolveInfo resolveInfo) {
            if (context != null) {
                if (resolveInfo != null) {
                    this.mContext = context;
                    this.mResolveInfo = resolveInfo;
                    return;
                }
                throw new IllegalArgumentException("resolveInfo cannot be null");
            }
            throw new IllegalArgumentException("context cannot be null");
        }

        private static String generateInputId(ComponentName componentName) {
            return componentName.flattenToShortString();
        }

        private static String generateInputId(ComponentName componentName, HdmiDeviceInfo hdmiDeviceInfo) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(componentName.flattenToShortString());
            stringBuilder.append(String.format(Locale.ENGLISH, "/HDMI%04X%02X", hdmiDeviceInfo.getPhysicalAddress(), hdmiDeviceInfo.getId()));
            return stringBuilder.toString();
        }

        private static String generateInputId(ComponentName componentName, TvInputHardwareInfo tvInputHardwareInfo) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(componentName.flattenToShortString());
            stringBuilder.append(DELIMITER_INFO_IN_ID);
            stringBuilder.append(PREFIX_HARDWARE_DEVICE);
            stringBuilder.append(tvInputHardwareInfo.getDeviceId());
            return stringBuilder.toString();
        }

        private static int getRelativePosition(Context object, HdmiDeviceInfo hdmiDeviceInfo) {
            if ((object = (HdmiControlManager)((Context)object).getSystemService("hdmi_control")) == null) {
                return 0;
            }
            return HdmiUtils.getHdmiAddressRelativePosition(hdmiDeviceInfo.getPhysicalAddress(), ((HdmiControlManager)object).getPhysicalAddress());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void parseServiceMetadata(int n) {
            ServiceInfo serviceInfo;
            Throwable throwable2;
            Object object;
            block17 : {
                Object object2;
                block15 : {
                    Object object3;
                    block16 : {
                        serviceInfo = this.mResolveInfo.serviceInfo;
                        object2 = this.mContext.getPackageManager();
                        object = serviceInfo.loadXmlMetaData((PackageManager)object2, "android.media.tv.input");
                        if (object == null) break block15;
                        try {
                            int n2;
                            object2 = ((PackageManager)object2).getResourcesForApplication(serviceInfo.applicationInfo);
                            object3 = Xml.asAttributeSet((XmlPullParser)object);
                            while ((n2 = object.next()) != 1 && n2 != 2) {
                            }
                            if (!XML_START_TAG_NAME.equals(object.getName())) break block16;
                            object2 = ((Resources)object2).obtainAttributes((AttributeSet)object3, R.styleable.TvInputService);
                            this.mSetupActivity = ((TypedArray)object2).getString(1);
                            if (this.mCanRecord == null) {
                                this.mCanRecord = ((TypedArray)object2).getBoolean(2, false);
                            }
                            if (this.mTunerCount == null && n == 0) {
                                this.mTunerCount = ((TypedArray)object2).getInt(3, 1);
                            }
                            ((TypedArray)object2).recycle();
                        }
                        catch (Throwable throwable2) {}
                        object.close();
                        return;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Meta-data does not start with tv-input tag for ");
                    ((StringBuilder)object2).append(serviceInfo.name);
                    object3 = new IllegalStateException(((StringBuilder)object2).toString());
                    throw object3;
                    break block17;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("No android.media.tv.input meta-data found for ");
                stringBuilder.append(serviceInfo.name);
                object2 = new IllegalStateException(stringBuilder.toString());
                throw object2;
            }
            try {
                throw throwable2;
            }
            catch (Throwable throwable3) {
                if (object == null) throw throwable3;
                try {
                    object.close();
                    throw throwable3;
                }
                catch (Throwable throwable4) {
                    try {
                        throwable2.addSuppressed(throwable4);
                        throw throwable3;
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("No resources found for ");
                        ((StringBuilder)object).append(serviceInfo.packageName);
                        throw new IllegalStateException(((StringBuilder)object).toString(), nameNotFoundException);
                    }
                    catch (IOException | XmlPullParserException throwable5) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Failed reading meta-data for ");
                        ((StringBuilder)object).append(serviceInfo.packageName);
                        throw new IllegalStateException(((StringBuilder)object).toString(), throwable5);
                    }
                }
            }
        }

        public TvInputInfo build() {
            int n;
            boolean bl;
            Object object = new ComponentName(this.mResolveInfo.serviceInfo.packageName, this.mResolveInfo.serviceInfo.name);
            boolean bl2 = false;
            boolean bl3 = false;
            int n2 = 0;
            Parcelable parcelable = this.mHdmiDeviceInfo;
            int n3 = 0;
            if (parcelable != null) {
                object = Builder.generateInputId((ComponentName)object, parcelable);
                n = 1007;
                bl = true;
                n2 = Builder.getRelativePosition(this.mContext, this.mHdmiDeviceInfo);
                bl2 = true;
                if (n2 == 1) {
                    bl2 = false;
                }
                bl3 = bl2;
                bl2 = bl;
            } else {
                parcelable = this.mTvInputHardwareInfo;
                if (parcelable != null) {
                    object = Builder.generateInputId((ComponentName)object, (TvInputHardwareInfo)parcelable);
                    n = sHardwareTypeToTvInputType.get(this.mTvInputHardwareInfo.getType(), 0);
                    bl2 = true;
                } else {
                    object = Builder.generateInputId((ComponentName)object);
                    n = 0;
                }
            }
            this.parseServiceMetadata(n);
            parcelable = this.mResolveInfo;
            CharSequence charSequence = this.mLabel;
            int n4 = this.mLabelResId;
            Icon icon = this.mIcon;
            Icon icon2 = this.mIconStandby;
            Icon icon3 = this.mIconDisconnected;
            String string2 = this.mSetupActivity;
            Comparable<Boolean> comparable = this.mCanRecord;
            bl = comparable == null ? false : (Boolean)comparable;
            comparable = this.mTunerCount;
            if (comparable != null) {
                n3 = (Integer)comparable;
            }
            return new TvInputInfo((ResolveInfo)parcelable, (String)object, n, bl2, charSequence, n4, icon, icon2, icon3, string2, bl, n3, this.mHdmiDeviceInfo, bl3, n2, this.mParentId, this.mExtras);
        }

        public Builder setCanRecord(boolean bl) {
            this.mCanRecord = bl;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        @SystemApi
        public Builder setHdmiDeviceInfo(HdmiDeviceInfo hdmiDeviceInfo) {
            if (this.mTvInputHardwareInfo != null) {
                Log.w(TvInputInfo.TAG, "TvInputHardwareInfo will not be used to build this TvInputInfo");
                this.mTvInputHardwareInfo = null;
            }
            this.mHdmiDeviceInfo = hdmiDeviceInfo;
            return this;
        }

        @SystemApi
        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        @SystemApi
        public Builder setIcon(Icon object, int n) {
            block5 : {
                block3 : {
                    block4 : {
                        block2 : {
                            if (n != 0) break block2;
                            this.mIcon = object;
                            break block3;
                        }
                        if (n != 1) break block4;
                        this.mIconStandby = object;
                        break block3;
                    }
                    if (n != 2) break block5;
                    this.mIconDisconnected = object;
                }
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown state: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @SystemApi
        public Builder setLabel(int n) {
            if (this.mLabel == null) {
                this.mLabelResId = n;
                return this;
            }
            throw new IllegalStateException("Label text is already set.");
        }

        @SystemApi
        public Builder setLabel(CharSequence charSequence) {
            if (this.mLabelResId == 0) {
                this.mLabel = charSequence;
                return this;
            }
            throw new IllegalStateException("Resource ID for label is already set.");
        }

        @SystemApi
        public Builder setParentId(String string2) {
            this.mParentId = string2;
            return this;
        }

        public Builder setTunerCount(int n) {
            this.mTunerCount = n;
            return this;
        }

        @SystemApi
        public Builder setTvInputHardwareInfo(TvInputHardwareInfo tvInputHardwareInfo) {
            if (this.mHdmiDeviceInfo != null) {
                Log.w(TvInputInfo.TAG, "mHdmiDeviceInfo will not be used to build this TvInputInfo");
                this.mHdmiDeviceInfo = null;
            }
            this.mTvInputHardwareInfo = tvInputHardwareInfo;
            return this;
        }
    }

    @SystemApi
    public static final class TvInputSettings {
        private static final String CUSTOM_NAME_SEPARATOR = ",";
        private static final String TV_INPUT_SEPARATOR = ":";

        private TvInputSettings() {
        }

        private static void ensureValidField(String string2) {
            if (!TextUtils.isEmpty(string2)) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" should not empty ");
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        private static String getCustomLabel(Context context, String string2, int n) {
            return TvInputSettings.getCustomLabels(context, n).get(string2);
        }

        @SystemApi
        public static Map<String, String> getCustomLabels(Context object, int n) {
            String[] arrstring = Settings.Secure.getStringForUser(((Context)object).getContentResolver(), "tv_input_custom_labels", n);
            object = new HashMap();
            if (TextUtils.isEmpty((CharSequence)arrstring)) {
                return object;
            }
            String[] arrstring2 = arrstring.split(TV_INPUT_SEPARATOR);
            int n2 = arrstring2.length;
            for (n = 0; n < n2; ++n) {
                arrstring = arrstring2[n].split(CUSTOM_NAME_SEPARATOR);
                object.put(Uri.decode(arrstring[0]), Uri.decode(arrstring[1]));
            }
            return object;
        }

        @SystemApi
        public static Set<String> getHiddenTvInputIds(Context object, int n) {
            String[] arrstring = Settings.Secure.getStringForUser(((Context)object).getContentResolver(), "tv_input_hidden_inputs", n);
            object = new HashSet();
            if (TextUtils.isEmpty((CharSequence)arrstring)) {
                return object;
            }
            arrstring = arrstring.split(TV_INPUT_SEPARATOR);
            int n2 = arrstring.length;
            for (n = 0; n < n2; ++n) {
                object.add(Uri.decode(arrstring[n]));
            }
            return object;
        }

        private static boolean isHidden(Context context, String string2, int n) {
            return TvInputSettings.getHiddenTvInputIds(context, n).contains(string2);
        }

        @SystemApi
        public static void putCustomLabels(Context object, Map<String, String> object2, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl = true;
            for (Map.Entry<String, String> entry : object2.entrySet()) {
                TvInputSettings.ensureValidField(entry.getKey());
                TvInputSettings.ensureValidField(entry.getValue());
                if (bl) {
                    bl = false;
                } else {
                    stringBuilder.append(TV_INPUT_SEPARATOR);
                }
                stringBuilder.append(Uri.encode(entry.getKey()));
                stringBuilder.append(CUSTOM_NAME_SEPARATOR);
                stringBuilder.append(Uri.encode(entry.getValue()));
            }
            Settings.Secure.putStringForUser(((Context)object).getContentResolver(), "tv_input_custom_labels", stringBuilder.toString(), n);
            object = (TvInputManager)((Context)object).getSystemService("tv_input");
            object2 = object2.keySet().iterator();
            while (object2.hasNext()) {
                TvInputInfo tvInputInfo = ((TvInputManager)object).getTvInputInfo((String)object2.next());
                if (tvInputInfo == null) continue;
                ((TvInputManager)object).updateTvInputInfo(tvInputInfo);
            }
        }

        @SystemApi
        public static void putHiddenTvInputs(Context object, Set<String> object2, int n) {
            Object object3;
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl = true;
            Iterator<String> iterator = object2.iterator();
            while (iterator.hasNext()) {
                object3 = iterator.next();
                TvInputSettings.ensureValidField((String)object3);
                if (bl) {
                    bl = false;
                } else {
                    stringBuilder.append(TV_INPUT_SEPARATOR);
                }
                stringBuilder.append(Uri.encode((String)object3));
            }
            Settings.Secure.putStringForUser(((Context)object).getContentResolver(), "tv_input_hidden_inputs", stringBuilder.toString(), n);
            object = (TvInputManager)((Context)object).getSystemService("tv_input");
            object2 = object2.iterator();
            while (object2.hasNext()) {
                object3 = ((TvInputManager)object).getTvInputInfo((String)object2.next());
                if (object3 == null) continue;
                ((TvInputManager)object).updateTvInputInfo((TvInputInfo)object3);
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Type {
    }

}

