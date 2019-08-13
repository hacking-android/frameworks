/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.-$
 *  android.hardware.radio.-$$Lambda
 *  android.hardware.radio.-$$Lambda$RadioManager
 *  android.hardware.radio.-$$Lambda$RadioManager$cfMLnpQqL72UMrjmCGbrhAOHHgg
 *  android.hardware.radio.-$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw
 */
package android.hardware.radio;

import android.annotation.SystemApi;
import android.content.Context;
import android.hardware.radio.-$;
import android.hardware.radio.Announcement;
import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ICloseHandle;
import android.hardware.radio.IRadioService;
import android.hardware.radio.ITuner;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioMetadata;
import android.hardware.radio.RadioTuner;
import android.hardware.radio.TunerAdapter;
import android.hardware.radio.TunerCallbackAdapter;
import android.hardware.radio.Utils;
import android.hardware.radio._$$Lambda$RadioManager$1$yOwq8CG0kiZcgKFclFSIrjag008;
import android.hardware.radio._$$Lambda$RadioManager$cfMLnpQqL72UMrjmCGbrhAOHHgg;
import android.hardware.radio._$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SystemApi
public class RadioManager {
    public static final int BAND_AM = 0;
    public static final int BAND_AM_HD = 3;
    public static final int BAND_FM = 1;
    public static final int BAND_FM_HD = 2;
    public static final int BAND_INVALID = -1;
    public static final int CLASS_AM_FM = 0;
    public static final int CLASS_DT = 2;
    public static final int CLASS_SAT = 1;
    public static final int CONFIG_DAB_DAB_LINKING = 6;
    public static final int CONFIG_DAB_DAB_SOFT_LINKING = 8;
    public static final int CONFIG_DAB_FM_LINKING = 7;
    public static final int CONFIG_DAB_FM_SOFT_LINKING = 9;
    public static final int CONFIG_FORCE_ANALOG = 2;
    public static final int CONFIG_FORCE_DIGITAL = 3;
    public static final int CONFIG_FORCE_MONO = 1;
    public static final int CONFIG_RDS_AF = 4;
    public static final int CONFIG_RDS_REG = 5;
    public static final int REGION_ITU_1 = 0;
    public static final int REGION_ITU_2 = 1;
    public static final int REGION_JAPAN = 3;
    public static final int REGION_KOREA = 4;
    public static final int REGION_OIRT = 2;
    public static final int STATUS_BAD_VALUE = -22;
    public static final int STATUS_DEAD_OBJECT = -32;
    public static final int STATUS_ERROR = Integer.MIN_VALUE;
    public static final int STATUS_INVALID_OPERATION = -38;
    public static final int STATUS_NO_INIT = -19;
    public static final int STATUS_OK = 0;
    public static final int STATUS_PERMISSION_DENIED = -1;
    public static final int STATUS_TIMED_OUT = -110;
    private static final String TAG = "BroadcastRadio.manager";
    private final Map<Announcement.OnListUpdatedListener, ICloseHandle> mAnnouncementListeners = new HashMap<Announcement.OnListUpdatedListener, ICloseHandle>();
    private final Context mContext;
    private final IRadioService mService;

    public RadioManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mService = IRadioService.Stub.asInterface(ServiceManager.getServiceOrThrow("broadcastradio"));
    }

    static /* synthetic */ void lambda$addAnnouncementListener$0(Runnable runnable) {
        runnable.run();
    }

    private native int nativeListModules(List<ModuleProperties> var1);

    public void addAnnouncementListener(Set<Integer> set, Announcement.OnListUpdatedListener onListUpdatedListener) {
        this.addAnnouncementListener((Executor)_$$Lambda$RadioManager$cfMLnpQqL72UMrjmCGbrhAOHHgg.INSTANCE, set, onListUpdatedListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addAnnouncementListener(Executor object, Set<Integer> object2, Announcement.OnListUpdatedListener onListUpdatedListener) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(onListUpdatedListener);
        object2 = object2.stream().mapToInt(_$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray();
        IAnnouncementListener.Stub stub = new IAnnouncementListener.Stub((Executor)object, onListUpdatedListener){
            final /* synthetic */ Executor val$executor;
            final /* synthetic */ Announcement.OnListUpdatedListener val$listener;
            {
                this.val$executor = executor;
                this.val$listener = onListUpdatedListener;
            }

            static /* synthetic */ void lambda$onListUpdated$0(Announcement.OnListUpdatedListener onListUpdatedListener, List list) {
                onListUpdatedListener.onListUpdated(list);
            }

            @Override
            public void onListUpdated(List<Announcement> list) {
                this.val$executor.execute(new _$$Lambda$RadioManager$1$yOwq8CG0kiZcgKFclFSIrjag008(this.val$listener, list));
            }
        };
        Map<Announcement.OnListUpdatedListener, ICloseHandle> map = this.mAnnouncementListeners;
        synchronized (map) {
            object = null;
            try {
                try {
                    object = object2 = this.mService.addAnnouncementListener((int[])object2, stub);
                }
                catch (RemoteException remoteException) {
                    remoteException.rethrowFromSystemServer();
                }
                Objects.requireNonNull(object);
                object = this.mAnnouncementListeners.put(onListUpdatedListener, (ICloseHandle)object);
                if (object != null) {
                    Utils.close((ICloseHandle)object);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    public int listModules(List<ModuleProperties> list) {
        List<ModuleProperties> list2;
        block3 : {
            if (list == null) {
                Log.e("BroadcastRadio.manager", "the output list must not be empty");
                return -22;
            }
            Log.d("BroadcastRadio.manager", "Listing available tuners...");
            try {
                list2 = this.mService.listModules();
                if (list2 != null) break block3;
            }
            catch (RemoteException remoteException) {
                Log.e("BroadcastRadio.manager", "Failed listing available tuners", remoteException);
                return -32;
            }
            Log.e("BroadcastRadio.manager", "Returned list was a null");
            return Integer.MIN_VALUE;
        }
        list.addAll(list2);
        return 0;
    }

    public RadioTuner openTuner(int n, BandConfig bandConfig, boolean bl, RadioTuner.Callback object, Handler object2) {
        if (object != null) {
            block3 : {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Opening tuner ");
                stringBuilder.append(n);
                stringBuilder.append("...");
                Log.d("BroadcastRadio.manager", stringBuilder.toString());
                object = new TunerCallbackAdapter((RadioTuner.Callback)object, (Handler)object2);
                try {
                    object2 = this.mService.openTuner(n, bandConfig, bl, (ITunerCallback)object);
                    if (object2 != null) break block3;
                }
                catch (RemoteException | IllegalArgumentException | IllegalStateException exception) {
                    Log.e("BroadcastRadio.manager", "Failed to open tuner", exception);
                    return null;
                }
                Log.e("BroadcastRadio.manager", "Failed to open tuner");
                return null;
            }
            n = bandConfig != null ? bandConfig.getType() : -1;
            return new TunerAdapter((ITuner)object2, (TunerCallbackAdapter)object, n);
        }
        throw new IllegalArgumentException("callback must not be empty");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeAnnouncementListener(Announcement.OnListUpdatedListener object) {
        Objects.requireNonNull(object);
        Map<Announcement.OnListUpdatedListener, ICloseHandle> map = this.mAnnouncementListeners;
        synchronized (map) {
            object = this.mAnnouncementListeners.remove(object);
            if (object != null) {
                Utils.close((ICloseHandle)object);
            }
            return;
        }
    }

    public static class AmBandConfig
    extends BandConfig {
        public static final Parcelable.Creator<AmBandConfig> CREATOR = new Parcelable.Creator<AmBandConfig>(){

            @Override
            public AmBandConfig createFromParcel(Parcel parcel) {
                return new AmBandConfig(parcel);
            }

            public AmBandConfig[] newArray(int n) {
                return new AmBandConfig[n];
            }
        };
        private final boolean mStereo;

        AmBandConfig(int n, int n2, int n3, int n4, int n5, boolean bl) {
            super(n, n2, n3, n4, n5);
            this.mStereo = bl;
        }

        public AmBandConfig(AmBandDescriptor amBandDescriptor) {
            super(amBandDescriptor);
            this.mStereo = amBandDescriptor.isStereoSupported();
        }

        private AmBandConfig(Parcel parcel) {
            super(parcel);
            byte by = parcel.readByte();
            boolean bl = true;
            if (by != 1) {
                bl = false;
            }
            this.mStereo = bl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            if (!(object instanceof AmBandConfig)) {
                return false;
            }
            return this.mStereo == ((AmBandConfig)(object = (AmBandConfig)object)).getStereo();
        }

        public boolean getStereo() {
            return this.mStereo;
        }

        @Override
        public int hashCode() {
            return super.hashCode() * 31 + this.mStereo;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AmBandConfig [");
            stringBuilder.append(super.toString());
            stringBuilder.append(", mStereo=");
            stringBuilder.append(this.mStereo);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeByte((byte)(this.mStereo ? 1 : 0));
        }

        public static class Builder {
            private final BandDescriptor mDescriptor;
            private boolean mStereo;

            public Builder(AmBandConfig amBandConfig) {
                this.mDescriptor = new BandDescriptor(amBandConfig.getRegion(), amBandConfig.getType(), amBandConfig.getLowerLimit(), amBandConfig.getUpperLimit(), amBandConfig.getSpacing());
                this.mStereo = amBandConfig.getStereo();
            }

            public Builder(AmBandDescriptor amBandDescriptor) {
                this.mDescriptor = new BandDescriptor(amBandDescriptor.getRegion(), amBandDescriptor.getType(), amBandDescriptor.getLowerLimit(), amBandDescriptor.getUpperLimit(), amBandDescriptor.getSpacing());
                this.mStereo = amBandDescriptor.isStereoSupported();
            }

            public AmBandConfig build() {
                return new AmBandConfig(this.mDescriptor.getRegion(), this.mDescriptor.getType(), this.mDescriptor.getLowerLimit(), this.mDescriptor.getUpperLimit(), this.mDescriptor.getSpacing(), this.mStereo);
            }

            public Builder setStereo(boolean bl) {
                this.mStereo = bl;
                return this;
            }
        }

    }

    public static class AmBandDescriptor
    extends BandDescriptor {
        public static final Parcelable.Creator<AmBandDescriptor> CREATOR = new Parcelable.Creator<AmBandDescriptor>(){

            @Override
            public AmBandDescriptor createFromParcel(Parcel parcel) {
                return new AmBandDescriptor(parcel);
            }

            public AmBandDescriptor[] newArray(int n) {
                return new AmBandDescriptor[n];
            }
        };
        private final boolean mStereo;

        public AmBandDescriptor(int n, int n2, int n3, int n4, int n5, boolean bl) {
            super(n, n2, n3, n4, n5);
            this.mStereo = bl;
        }

        private AmBandDescriptor(Parcel parcel) {
            super(parcel);
            byte by = parcel.readByte();
            boolean bl = true;
            if (by != 1) {
                bl = false;
            }
            this.mStereo = bl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            if (!(object instanceof AmBandDescriptor)) {
                return false;
            }
            return this.mStereo == ((AmBandDescriptor)(object = (AmBandDescriptor)object)).isStereoSupported();
        }

        @Override
        public int hashCode() {
            return super.hashCode() * 31 + this.mStereo;
        }

        public boolean isStereoSupported() {
            return this.mStereo;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AmBandDescriptor [ ");
            stringBuilder.append(super.toString());
            stringBuilder.append(" mStereo=");
            stringBuilder.append(this.mStereo);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeByte((byte)(this.mStereo ? 1 : 0));
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Band {
    }

    public static class BandConfig
    implements Parcelable {
        public static final Parcelable.Creator<BandConfig> CREATOR = new Parcelable.Creator<BandConfig>(){

            @Override
            public BandConfig createFromParcel(Parcel object) {
                int n = BandDescriptor.lookupTypeFromParcel((Parcel)object);
                if (n != 0) {
                    if (n != 1 && n != 2) {
                        if (n != 3) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unsupported band: ");
                            ((StringBuilder)object).append(n);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                    } else {
                        return new FmBandConfig((Parcel)object);
                    }
                }
                return new AmBandConfig((Parcel)object);
            }

            public BandConfig[] newArray(int n) {
                return new BandConfig[n];
            }
        };
        final BandDescriptor mDescriptor;

        BandConfig(int n, int n2, int n3, int n4, int n5) {
            this.mDescriptor = new BandDescriptor(n, n2, n3, n4, n5);
        }

        BandConfig(BandDescriptor bandDescriptor) {
            this.mDescriptor = Objects.requireNonNull(bandDescriptor);
        }

        private BandConfig(Parcel parcel) {
            this.mDescriptor = new BandDescriptor(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl;
            if (this == object) {
                return true;
            }
            if (!(object instanceof BandConfig)) {
                return false;
            }
            boolean bl2 = this.mDescriptor == null;
            if (bl2 != (bl = (object = ((BandConfig)object).getDescriptor()) == null)) {
                return false;
            }
            BandDescriptor bandDescriptor = this.mDescriptor;
            return bandDescriptor == null || bandDescriptor.equals(object);
        }

        BandDescriptor getDescriptor() {
            return this.mDescriptor;
        }

        public int getLowerLimit() {
            return this.mDescriptor.getLowerLimit();
        }

        public int getRegion() {
            return this.mDescriptor.getRegion();
        }

        public int getSpacing() {
            return this.mDescriptor.getSpacing();
        }

        public int getType() {
            return this.mDescriptor.getType();
        }

        public int getUpperLimit() {
            return this.mDescriptor.getUpperLimit();
        }

        public int hashCode() {
            return 1 * 31 + this.mDescriptor.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("BandConfig [ ");
            stringBuilder.append(this.mDescriptor.toString());
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            this.mDescriptor.writeToParcel(parcel, n);
        }

    }

    public static class BandDescriptor
    implements Parcelable {
        public static final Parcelable.Creator<BandDescriptor> CREATOR = new Parcelable.Creator<BandDescriptor>(){

            @Override
            public BandDescriptor createFromParcel(Parcel object) {
                int n = BandDescriptor.lookupTypeFromParcel((Parcel)object);
                if (n != 0) {
                    if (n != 1 && n != 2) {
                        if (n != 3) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unsupported band: ");
                            ((StringBuilder)object).append(n);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                    } else {
                        return new FmBandDescriptor((Parcel)object);
                    }
                }
                return new AmBandDescriptor((Parcel)object);
            }

            public BandDescriptor[] newArray(int n) {
                return new BandDescriptor[n];
            }
        };
        private final int mLowerLimit;
        private final int mRegion;
        private final int mSpacing;
        private final int mType;
        private final int mUpperLimit;

        BandDescriptor(int n, int n2, int n3, int n4, int n5) {
            if (n2 != 0 && n2 != 1 && n2 != 2 && n2 != 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported band: ");
                stringBuilder.append(n2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mRegion = n;
            this.mType = n2;
            this.mLowerLimit = n3;
            this.mUpperLimit = n4;
            this.mSpacing = n5;
        }

        private BandDescriptor(Parcel parcel) {
            this.mRegion = parcel.readInt();
            this.mType = parcel.readInt();
            this.mLowerLimit = parcel.readInt();
            this.mUpperLimit = parcel.readInt();
            this.mSpacing = parcel.readInt();
        }

        private static int lookupTypeFromParcel(Parcel parcel) {
            int n = parcel.dataPosition();
            parcel.readInt();
            int n2 = parcel.readInt();
            parcel.setDataPosition(n);
            return n2;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof BandDescriptor)) {
                return false;
            }
            if (this.mRegion != ((BandDescriptor)(object = (BandDescriptor)object)).getRegion()) {
                return false;
            }
            if (this.mType != ((BandDescriptor)object).getType()) {
                return false;
            }
            if (this.mLowerLimit != ((BandDescriptor)object).getLowerLimit()) {
                return false;
            }
            if (this.mUpperLimit != ((BandDescriptor)object).getUpperLimit()) {
                return false;
            }
            return this.mSpacing == ((BandDescriptor)object).getSpacing();
        }

        public int getLowerLimit() {
            return this.mLowerLimit;
        }

        public int getRegion() {
            return this.mRegion;
        }

        public int getSpacing() {
            return this.mSpacing;
        }

        public int getType() {
            return this.mType;
        }

        public int getUpperLimit() {
            return this.mUpperLimit;
        }

        public int hashCode() {
            return ((((1 * 31 + this.mRegion) * 31 + this.mType) * 31 + this.mLowerLimit) * 31 + this.mUpperLimit) * 31 + this.mSpacing;
        }

        public boolean isAmBand() {
            int n = this.mType;
            boolean bl = n == 0 || n == 3;
            return bl;
        }

        public boolean isFmBand() {
            boolean bl;
            int n = this.mType;
            boolean bl2 = bl = true;
            if (n != 1) {
                bl2 = n == 2 ? bl : false;
            }
            return bl2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("BandDescriptor [mRegion=");
            stringBuilder.append(this.mRegion);
            stringBuilder.append(", mType=");
            stringBuilder.append(this.mType);
            stringBuilder.append(", mLowerLimit=");
            stringBuilder.append(this.mLowerLimit);
            stringBuilder.append(", mUpperLimit=");
            stringBuilder.append(this.mUpperLimit);
            stringBuilder.append(", mSpacing=");
            stringBuilder.append(this.mSpacing);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mRegion);
            parcel.writeInt(this.mType);
            parcel.writeInt(this.mLowerLimit);
            parcel.writeInt(this.mUpperLimit);
            parcel.writeInt(this.mSpacing);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ConfigFlag {
    }

    public static class FmBandConfig
    extends BandConfig {
        public static final Parcelable.Creator<FmBandConfig> CREATOR = new Parcelable.Creator<FmBandConfig>(){

            @Override
            public FmBandConfig createFromParcel(Parcel parcel) {
                return new FmBandConfig(parcel);
            }

            public FmBandConfig[] newArray(int n) {
                return new FmBandConfig[n];
            }
        };
        private final boolean mAf;
        private final boolean mEa;
        private final boolean mRds;
        private final boolean mStereo;
        private final boolean mTa;

        FmBandConfig(int n, int n2, int n3, int n4, int n5, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
            super(n, n2, n3, n4, n5);
            this.mStereo = bl;
            this.mRds = bl2;
            this.mTa = bl3;
            this.mAf = bl4;
            this.mEa = bl5;
        }

        public FmBandConfig(FmBandDescriptor fmBandDescriptor) {
            super(fmBandDescriptor);
            this.mStereo = fmBandDescriptor.isStereoSupported();
            this.mRds = fmBandDescriptor.isRdsSupported();
            this.mTa = fmBandDescriptor.isTaSupported();
            this.mAf = fmBandDescriptor.isAfSupported();
            this.mEa = fmBandDescriptor.isEaSupported();
        }

        private FmBandConfig(Parcel parcel) {
            super(parcel);
            byte by = parcel.readByte();
            boolean bl = false;
            boolean bl2 = by == 1;
            this.mStereo = bl2;
            bl2 = parcel.readByte() == 1;
            this.mRds = bl2;
            bl2 = parcel.readByte() == 1;
            this.mTa = bl2;
            bl2 = parcel.readByte() == 1;
            this.mAf = bl2;
            bl2 = bl;
            if (parcel.readByte() == 1) {
                bl2 = true;
            }
            this.mEa = bl2;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            if (!(object instanceof FmBandConfig)) {
                return false;
            }
            object = (FmBandConfig)object;
            if (this.mStereo != ((FmBandConfig)object).mStereo) {
                return false;
            }
            if (this.mRds != ((FmBandConfig)object).mRds) {
                return false;
            }
            if (this.mTa != ((FmBandConfig)object).mTa) {
                return false;
            }
            if (this.mAf != ((FmBandConfig)object).mAf) {
                return false;
            }
            return this.mEa == ((FmBandConfig)object).mEa;
        }

        public boolean getAf() {
            return this.mAf;
        }

        public boolean getEa() {
            return this.mEa;
        }

        public boolean getRds() {
            return this.mRds;
        }

        public boolean getStereo() {
            return this.mStereo;
        }

        public boolean getTa() {
            return this.mTa;
        }

        @Override
        public int hashCode() {
            return ((((super.hashCode() * 31 + this.mStereo) * 31 + this.mRds) * 31 + this.mTa) * 31 + this.mAf) * 31 + this.mEa;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FmBandConfig [");
            stringBuilder.append(super.toString());
            stringBuilder.append(", mStereo=");
            stringBuilder.append(this.mStereo);
            stringBuilder.append(", mRds=");
            stringBuilder.append(this.mRds);
            stringBuilder.append(", mTa=");
            stringBuilder.append(this.mTa);
            stringBuilder.append(", mAf=");
            stringBuilder.append(this.mAf);
            stringBuilder.append(", mEa =");
            stringBuilder.append(this.mEa);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeByte((byte)(this.mStereo ? 1 : 0));
            parcel.writeByte((byte)(this.mRds ? 1 : 0));
            parcel.writeByte((byte)(this.mTa ? 1 : 0));
            parcel.writeByte((byte)(this.mAf ? 1 : 0));
            parcel.writeByte((byte)(this.mEa ? 1 : 0));
        }

        public static class Builder {
            private boolean mAf;
            private final BandDescriptor mDescriptor;
            private boolean mEa;
            private boolean mRds;
            private boolean mStereo;
            private boolean mTa;

            public Builder(FmBandConfig fmBandConfig) {
                this.mDescriptor = new BandDescriptor(fmBandConfig.getRegion(), fmBandConfig.getType(), fmBandConfig.getLowerLimit(), fmBandConfig.getUpperLimit(), fmBandConfig.getSpacing());
                this.mStereo = fmBandConfig.getStereo();
                this.mRds = fmBandConfig.getRds();
                this.mTa = fmBandConfig.getTa();
                this.mAf = fmBandConfig.getAf();
                this.mEa = fmBandConfig.getEa();
            }

            public Builder(FmBandDescriptor fmBandDescriptor) {
                this.mDescriptor = new BandDescriptor(fmBandDescriptor.getRegion(), fmBandDescriptor.getType(), fmBandDescriptor.getLowerLimit(), fmBandDescriptor.getUpperLimit(), fmBandDescriptor.getSpacing());
                this.mStereo = fmBandDescriptor.isStereoSupported();
                this.mRds = fmBandDescriptor.isRdsSupported();
                this.mTa = fmBandDescriptor.isTaSupported();
                this.mAf = fmBandDescriptor.isAfSupported();
                this.mEa = fmBandDescriptor.isEaSupported();
            }

            public FmBandConfig build() {
                return new FmBandConfig(this.mDescriptor.getRegion(), this.mDescriptor.getType(), this.mDescriptor.getLowerLimit(), this.mDescriptor.getUpperLimit(), this.mDescriptor.getSpacing(), this.mStereo, this.mRds, this.mTa, this.mAf, this.mEa);
            }

            public Builder setAf(boolean bl) {
                this.mAf = bl;
                return this;
            }

            public Builder setEa(boolean bl) {
                this.mEa = bl;
                return this;
            }

            public Builder setRds(boolean bl) {
                this.mRds = bl;
                return this;
            }

            public Builder setStereo(boolean bl) {
                this.mStereo = bl;
                return this;
            }

            public Builder setTa(boolean bl) {
                this.mTa = bl;
                return this;
            }
        }

    }

    public static class FmBandDescriptor
    extends BandDescriptor {
        public static final Parcelable.Creator<FmBandDescriptor> CREATOR = new Parcelable.Creator<FmBandDescriptor>(){

            @Override
            public FmBandDescriptor createFromParcel(Parcel parcel) {
                return new FmBandDescriptor(parcel);
            }

            public FmBandDescriptor[] newArray(int n) {
                return new FmBandDescriptor[n];
            }
        };
        private final boolean mAf;
        private final boolean mEa;
        private final boolean mRds;
        private final boolean mStereo;
        private final boolean mTa;

        public FmBandDescriptor(int n, int n2, int n3, int n4, int n5, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
            super(n, n2, n3, n4, n5);
            this.mStereo = bl;
            this.mRds = bl2;
            this.mTa = bl3;
            this.mAf = bl4;
            this.mEa = bl5;
        }

        private FmBandDescriptor(Parcel parcel) {
            super(parcel);
            byte by = parcel.readByte();
            boolean bl = false;
            boolean bl2 = by == 1;
            this.mStereo = bl2;
            bl2 = parcel.readByte() == 1;
            this.mRds = bl2;
            bl2 = parcel.readByte() == 1;
            this.mTa = bl2;
            bl2 = parcel.readByte() == 1;
            this.mAf = bl2;
            bl2 = bl;
            if (parcel.readByte() == 1) {
                bl2 = true;
            }
            this.mEa = bl2;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            if (!(object instanceof FmBandDescriptor)) {
                return false;
            }
            if (this.mStereo != ((FmBandDescriptor)(object = (FmBandDescriptor)object)).isStereoSupported()) {
                return false;
            }
            if (this.mRds != ((FmBandDescriptor)object).isRdsSupported()) {
                return false;
            }
            if (this.mTa != ((FmBandDescriptor)object).isTaSupported()) {
                return false;
            }
            if (this.mAf != ((FmBandDescriptor)object).isAfSupported()) {
                return false;
            }
            return this.mEa == ((FmBandDescriptor)object).isEaSupported();
        }

        @Override
        public int hashCode() {
            return ((((super.hashCode() * 31 + this.mStereo) * 31 + this.mRds) * 31 + this.mTa) * 31 + this.mAf) * 31 + this.mEa;
        }

        public boolean isAfSupported() {
            return this.mAf;
        }

        public boolean isEaSupported() {
            return this.mEa;
        }

        public boolean isRdsSupported() {
            return this.mRds;
        }

        public boolean isStereoSupported() {
            return this.mStereo;
        }

        public boolean isTaSupported() {
            return this.mTa;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FmBandDescriptor [ ");
            stringBuilder.append(super.toString());
            stringBuilder.append(" mStereo=");
            stringBuilder.append(this.mStereo);
            stringBuilder.append(", mRds=");
            stringBuilder.append(this.mRds);
            stringBuilder.append(", mTa=");
            stringBuilder.append(this.mTa);
            stringBuilder.append(", mAf=");
            stringBuilder.append(this.mAf);
            stringBuilder.append(", mEa =");
            stringBuilder.append(this.mEa);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeByte((byte)(this.mStereo ? 1 : 0));
            parcel.writeByte((byte)(this.mRds ? 1 : 0));
            parcel.writeByte((byte)(this.mTa ? 1 : 0));
            parcel.writeByte((byte)(this.mAf ? 1 : 0));
            parcel.writeByte((byte)(this.mEa ? 1 : 0));
        }

    }

    public static class ModuleProperties
    implements Parcelable {
        public static final Parcelable.Creator<ModuleProperties> CREATOR = new Parcelable.Creator<ModuleProperties>(){

            @Override
            public ModuleProperties createFromParcel(Parcel parcel) {
                return new ModuleProperties(parcel);
            }

            public ModuleProperties[] newArray(int n) {
                return new ModuleProperties[n];
            }
        };
        private final BandDescriptor[] mBands;
        private final int mClassId;
        private final Map<String, Integer> mDabFrequencyTable;
        private final int mId;
        private final String mImplementor;
        private final boolean mIsBgScanSupported;
        private final boolean mIsCaptureSupported;
        private final boolean mIsInitializationRequired;
        private final int mNumAudioSources;
        private final int mNumTuners;
        private final String mProduct;
        private final String mSerial;
        private final String mServiceName;
        private final Set<Integer> mSupportedIdentifierTypes;
        private final Set<Integer> mSupportedProgramTypes;
        private final Map<String, String> mVendorInfo;
        private final String mVersion;

        /*
         * WARNING - void declaration
         */
        public ModuleProperties(int n, String map4, int n2, String object, String string2, String string3, String string4, int n3, int n4, boolean bl, boolean bl2, BandDescriptor[] arrbandDescriptor, boolean bl3, int[] arrn, int[] arrn2, Map<String, Integer> map2, Map<String, String> map3) {
            void var15_22;
            void var5_12;
            void var8_15;
            void var16_23;
            void var10_17;
            void var14_21;
            Iterator iterator;
            void var2_9;
            void var6_13;
            void var12_19;
            void var7_14;
            void var13_20;
            void var11_18;
            void var17_24;
            void var3_10;
            void var2_4;
            void var9_16;
            this.mId = n;
            if (TextUtils.isEmpty(map4)) {
                String string5 = "default";
            }
            this.mServiceName = var2_4;
            this.mClassId = var3_10;
            this.mImplementor = iterator;
            this.mProduct = var5_12;
            this.mVersion = var6_13;
            this.mSerial = var7_14;
            this.mNumTuners = var8_15;
            this.mNumAudioSources = var9_16;
            this.mIsInitializationRequired = var10_17;
            this.mIsCaptureSupported = var11_18;
            this.mBands = var12_19;
            this.mIsBgScanSupported = var13_20;
            this.mSupportedProgramTypes = ModuleProperties.arrayToSet((int[])var14_21);
            this.mSupportedIdentifierTypes = ModuleProperties.arrayToSet((int[])var15_22);
            if (var16_23 != null) {
                for (Map.Entry entry : var16_23.entrySet()) {
                    Objects.requireNonNull((String)entry.getKey());
                    Objects.requireNonNull((Integer)entry.getValue());
                }
            }
            this.mDabFrequencyTable = var16_23;
            if (var17_24 == null) {
                HashMap hashMap = new HashMap();
            } else {
                void var2_8 = var17_24;
            }
            this.mVendorInfo = var2_9;
        }

        private ModuleProperties(Parcel parcel) {
            this.mId = parcel.readInt();
            Parcelable[] arrparcelable = parcel.readString();
            if (TextUtils.isEmpty((CharSequence)arrparcelable)) {
                arrparcelable = "default";
            }
            this.mServiceName = arrparcelable;
            this.mClassId = parcel.readInt();
            this.mImplementor = parcel.readString();
            this.mProduct = parcel.readString();
            this.mVersion = parcel.readString();
            this.mSerial = parcel.readString();
            this.mNumTuners = parcel.readInt();
            this.mNumAudioSources = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = false;
            boolean bl2 = n == 1;
            this.mIsInitializationRequired = bl2;
            bl2 = parcel.readInt() == 1;
            this.mIsCaptureSupported = bl2;
            arrparcelable = parcel.readParcelableArray(BandDescriptor.class.getClassLoader());
            this.mBands = new BandDescriptor[arrparcelable.length];
            for (n = 0; n < arrparcelable.length; ++n) {
                this.mBands[n] = (BandDescriptor)arrparcelable[n];
            }
            bl2 = bl;
            if (parcel.readInt() == 1) {
                bl2 = true;
            }
            this.mIsBgScanSupported = bl2;
            this.mSupportedProgramTypes = ModuleProperties.arrayToSet(parcel.createIntArray());
            this.mSupportedIdentifierTypes = ModuleProperties.arrayToSet(parcel.createIntArray());
            this.mDabFrequencyTable = Utils.readStringIntMap(parcel);
            this.mVendorInfo = Utils.readStringMap(parcel);
        }

        private static Set<Integer> arrayToSet(int[] arrn) {
            return Arrays.stream(arrn).boxed().collect(Collectors.toSet());
        }

        private static int[] setToArray(Set<Integer> set) {
            return set.stream().mapToInt(_$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ModuleProperties)) {
                return false;
            }
            if (this.mId != ((ModuleProperties)(object = (ModuleProperties)object)).getId()) {
                return false;
            }
            if (!TextUtils.equals(this.mServiceName, ((ModuleProperties)object).mServiceName)) {
                return false;
            }
            if (this.mClassId != ((ModuleProperties)object).mClassId) {
                return false;
            }
            if (!Objects.equals(this.mImplementor, ((ModuleProperties)object).mImplementor)) {
                return false;
            }
            if (!Objects.equals(this.mProduct, ((ModuleProperties)object).mProduct)) {
                return false;
            }
            if (!Objects.equals(this.mVersion, ((ModuleProperties)object).mVersion)) {
                return false;
            }
            if (!Objects.equals(this.mSerial, ((ModuleProperties)object).mSerial)) {
                return false;
            }
            if (this.mNumTuners != ((ModuleProperties)object).mNumTuners) {
                return false;
            }
            if (this.mNumAudioSources != ((ModuleProperties)object).mNumAudioSources) {
                return false;
            }
            if (this.mIsInitializationRequired != ((ModuleProperties)object).mIsInitializationRequired) {
                return false;
            }
            if (this.mIsCaptureSupported != ((ModuleProperties)object).mIsCaptureSupported) {
                return false;
            }
            if (!Objects.equals(this.mBands, ((ModuleProperties)object).mBands)) {
                return false;
            }
            if (this.mIsBgScanSupported != ((ModuleProperties)object).mIsBgScanSupported) {
                return false;
            }
            if (!Objects.equals(this.mDabFrequencyTable, ((ModuleProperties)object).mDabFrequencyTable)) {
                return false;
            }
            return Objects.equals(this.mVendorInfo, ((ModuleProperties)object).mVendorInfo);
        }

        public BandDescriptor[] getBands() {
            return this.mBands;
        }

        public int getClassId() {
            return this.mClassId;
        }

        public Map<String, Integer> getDabFrequencyTable() {
            return this.mDabFrequencyTable;
        }

        public int getId() {
            return this.mId;
        }

        public String getImplementor() {
            return this.mImplementor;
        }

        public int getNumAudioSources() {
            return this.mNumAudioSources;
        }

        public int getNumTuners() {
            return this.mNumTuners;
        }

        public String getProduct() {
            return this.mProduct;
        }

        public String getSerial() {
            return this.mSerial;
        }

        public String getServiceName() {
            return this.mServiceName;
        }

        public Map<String, String> getVendorInfo() {
            return this.mVendorInfo;
        }

        public String getVersion() {
            return this.mVersion;
        }

        public int hashCode() {
            return Objects.hash(this.mId, this.mServiceName, this.mClassId, this.mImplementor, this.mProduct, this.mVersion, this.mSerial, this.mNumTuners, this.mNumAudioSources, this.mIsInitializationRequired, this.mIsCaptureSupported, this.mBands, this.mIsBgScanSupported, this.mDabFrequencyTable, this.mVendorInfo);
        }

        public boolean isBackgroundScanningSupported() {
            return this.mIsBgScanSupported;
        }

        public boolean isCaptureSupported() {
            return this.mIsCaptureSupported;
        }

        public boolean isInitializationRequired() {
            return this.mIsInitializationRequired;
        }

        public boolean isProgramIdentifierSupported(int n) {
            return this.mSupportedIdentifierTypes.contains(n);
        }

        public boolean isProgramTypeSupported(int n) {
            return this.mSupportedProgramTypes.contains(n);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ModuleProperties [mId=");
            stringBuilder.append(this.mId);
            stringBuilder.append(", mServiceName=");
            stringBuilder.append(this.mServiceName);
            stringBuilder.append(", mClassId=");
            stringBuilder.append(this.mClassId);
            stringBuilder.append(", mImplementor=");
            stringBuilder.append(this.mImplementor);
            stringBuilder.append(", mProduct=");
            stringBuilder.append(this.mProduct);
            stringBuilder.append(", mVersion=");
            stringBuilder.append(this.mVersion);
            stringBuilder.append(", mSerial=");
            stringBuilder.append(this.mSerial);
            stringBuilder.append(", mNumTuners=");
            stringBuilder.append(this.mNumTuners);
            stringBuilder.append(", mNumAudioSources=");
            stringBuilder.append(this.mNumAudioSources);
            stringBuilder.append(", mIsInitializationRequired=");
            stringBuilder.append(this.mIsInitializationRequired);
            stringBuilder.append(", mIsCaptureSupported=");
            stringBuilder.append(this.mIsCaptureSupported);
            stringBuilder.append(", mIsBgScanSupported=");
            stringBuilder.append(this.mIsBgScanSupported);
            stringBuilder.append(", mBands=");
            stringBuilder.append(Arrays.toString(this.mBands));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mId);
            parcel.writeString(this.mServiceName);
            parcel.writeInt(this.mClassId);
            parcel.writeString(this.mImplementor);
            parcel.writeString(this.mProduct);
            parcel.writeString(this.mVersion);
            parcel.writeString(this.mSerial);
            parcel.writeInt(this.mNumTuners);
            parcel.writeInt(this.mNumAudioSources);
            parcel.writeInt((int)this.mIsInitializationRequired);
            parcel.writeInt((int)this.mIsCaptureSupported);
            parcel.writeParcelableArray((Parcelable[])this.mBands, n);
            parcel.writeInt((int)this.mIsBgScanSupported);
            parcel.writeIntArray(ModuleProperties.setToArray(this.mSupportedProgramTypes));
            parcel.writeIntArray(ModuleProperties.setToArray(this.mSupportedIdentifierTypes));
            Utils.writeStringIntMap(parcel, this.mDabFrequencyTable);
            Utils.writeStringMap(parcel, this.mVendorInfo);
        }

    }

    public static class ProgramInfo
    implements Parcelable {
        public static final Parcelable.Creator<ProgramInfo> CREATOR = new Parcelable.Creator<ProgramInfo>(){

            @Override
            public ProgramInfo createFromParcel(Parcel parcel) {
                return new ProgramInfo(parcel);
            }

            public ProgramInfo[] newArray(int n) {
                return new ProgramInfo[n];
            }
        };
        private static final int FLAG_LIVE = 1;
        private static final int FLAG_MUTED = 2;
        private static final int FLAG_STEREO = 32;
        private static final int FLAG_TRAFFIC_ANNOUNCEMENT = 8;
        private static final int FLAG_TRAFFIC_PROGRAM = 4;
        private static final int FLAG_TUNED = 16;
        private final int mInfoFlags;
        private final ProgramSelector.Identifier mLogicallyTunedTo;
        private final RadioMetadata mMetadata;
        private final ProgramSelector.Identifier mPhysicallyTunedTo;
        private final Collection<ProgramSelector.Identifier> mRelatedContent;
        private final ProgramSelector mSelector;
        private final int mSignalQuality;
        private final Map<String, String> mVendorInfo;

        public ProgramInfo(ProgramSelector map, ProgramSelector.Identifier identifier, ProgramSelector.Identifier identifier2, Collection<ProgramSelector.Identifier> collection, int n, int n2, RadioMetadata radioMetadata, Map<String, String> map2) {
            this.mSelector = Objects.requireNonNull(map);
            this.mLogicallyTunedTo = identifier;
            this.mPhysicallyTunedTo = identifier2;
            if (collection == null) {
                this.mRelatedContent = Collections.emptyList();
            } else {
                Preconditions.checkCollectionElementsNotNull(collection, "relatedContent");
                this.mRelatedContent = collection;
            }
            this.mInfoFlags = n;
            this.mSignalQuality = n2;
            this.mMetadata = radioMetadata;
            map = map2 == null ? new HashMap<String, String>() : map2;
            this.mVendorInfo = map;
        }

        private ProgramInfo(Parcel parcel) {
            this.mSelector = Objects.requireNonNull(parcel.readTypedObject(ProgramSelector.CREATOR));
            this.mLogicallyTunedTo = parcel.readTypedObject(ProgramSelector.Identifier.CREATOR);
            this.mPhysicallyTunedTo = parcel.readTypedObject(ProgramSelector.Identifier.CREATOR);
            this.mRelatedContent = parcel.createTypedArrayList(ProgramSelector.Identifier.CREATOR);
            this.mInfoFlags = parcel.readInt();
            this.mSignalQuality = parcel.readInt();
            this.mMetadata = parcel.readTypedObject(RadioMetadata.CREATOR);
            this.mVendorInfo = Utils.readStringMap(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ProgramInfo)) {
                return false;
            }
            object = (ProgramInfo)object;
            if (!Objects.equals(this.mSelector, ((ProgramInfo)object).mSelector)) {
                return false;
            }
            if (!Objects.equals(this.mLogicallyTunedTo, ((ProgramInfo)object).mLogicallyTunedTo)) {
                return false;
            }
            if (!Objects.equals(this.mPhysicallyTunedTo, ((ProgramInfo)object).mPhysicallyTunedTo)) {
                return false;
            }
            if (!Objects.equals(this.mRelatedContent, ((ProgramInfo)object).mRelatedContent)) {
                return false;
            }
            if (this.mInfoFlags != ((ProgramInfo)object).mInfoFlags) {
                return false;
            }
            if (this.mSignalQuality != ((ProgramInfo)object).mSignalQuality) {
                return false;
            }
            if (!Objects.equals(this.mMetadata, ((ProgramInfo)object).mMetadata)) {
                return false;
            }
            return Objects.equals(this.mVendorInfo, ((ProgramInfo)object).mVendorInfo);
        }

        @Deprecated
        public int getChannel() {
            long l;
            try {
                l = this.mSelector.getFirstId(1);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.w(RadioManager.TAG, "Not an AM/FM program");
                return 0;
            }
            return (int)l;
        }

        public ProgramSelector.Identifier getLogicallyTunedTo() {
            return this.mLogicallyTunedTo;
        }

        public RadioMetadata getMetadata() {
            return this.mMetadata;
        }

        public ProgramSelector.Identifier getPhysicallyTunedTo() {
            return this.mPhysicallyTunedTo;
        }

        public Collection<ProgramSelector.Identifier> getRelatedContent() {
            return this.mRelatedContent;
        }

        public ProgramSelector getSelector() {
            return this.mSelector;
        }

        public int getSignalStrength() {
            return this.mSignalQuality;
        }

        @Deprecated
        public int getSubChannel() {
            long l;
            try {
                l = this.mSelector.getFirstId(4);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return 0;
            }
            return (int)l + 1;
        }

        public Map<String, String> getVendorInfo() {
            return this.mVendorInfo;
        }

        public int hashCode() {
            return Objects.hash(this.mSelector, this.mLogicallyTunedTo, this.mPhysicallyTunedTo, this.mRelatedContent, this.mInfoFlags, this.mSignalQuality, this.mMetadata, this.mVendorInfo);
        }

        @Deprecated
        public boolean isDigital() {
            ProgramSelector.Identifier identifier;
            ProgramSelector.Identifier identifier2 = identifier = this.mLogicallyTunedTo;
            if (identifier == null) {
                identifier2 = this.mSelector.getPrimaryId();
            }
            int n = identifier2.getType();
            boolean bl = true;
            if (n == 1 || n == 2) {
                bl = false;
            }
            return bl;
        }

        public boolean isLive() {
            int n = this.mInfoFlags;
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            return bl;
        }

        public boolean isMuted() {
            boolean bl = (this.mInfoFlags & 2) != 0;
            return bl;
        }

        public boolean isStereo() {
            boolean bl = (this.mInfoFlags & 32) != 0;
            return bl;
        }

        public boolean isTrafficAnnouncementActive() {
            boolean bl = (this.mInfoFlags & 8) != 0;
            return bl;
        }

        public boolean isTrafficProgram() {
            boolean bl = (this.mInfoFlags & 4) != 0;
            return bl;
        }

        public boolean isTuned() {
            boolean bl = (this.mInfoFlags & 16) != 0;
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ProgramInfo [selector=");
            stringBuilder.append(this.mSelector);
            stringBuilder.append(", logicallyTunedTo=");
            stringBuilder.append(Objects.toString(this.mLogicallyTunedTo));
            stringBuilder.append(", physicallyTunedTo=");
            stringBuilder.append(Objects.toString(this.mPhysicallyTunedTo));
            stringBuilder.append(", relatedContent=");
            stringBuilder.append(this.mRelatedContent.size());
            stringBuilder.append(", infoFlags=");
            stringBuilder.append(this.mInfoFlags);
            stringBuilder.append(", mSignalQuality=");
            stringBuilder.append(this.mSignalQuality);
            stringBuilder.append(", mMetadata=");
            stringBuilder.append(Objects.toString(this.mMetadata));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeTypedObject(this.mSelector, n);
            parcel.writeTypedObject(this.mLogicallyTunedTo, n);
            parcel.writeTypedObject(this.mPhysicallyTunedTo, n);
            Utils.writeTypedCollection(parcel, this.mRelatedContent);
            parcel.writeInt(this.mInfoFlags);
            parcel.writeInt(this.mSignalQuality);
            parcel.writeTypedObject(this.mMetadata, n);
            Utils.writeStringMap(parcel, this.mVendorInfo);
        }

    }

}

