/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public final class SessionConfiguration
implements Parcelable {
    public static final Parcelable.Creator<SessionConfiguration> CREATOR = new Parcelable.Creator<SessionConfiguration>(){

        @Override
        public SessionConfiguration createFromParcel(Parcel object) {
            try {
                object = new SessionConfiguration((Parcel)object);
                return object;
            }
            catch (Exception exception) {
                Log.e(SessionConfiguration.TAG, "Exception creating SessionConfiguration from parcel", exception);
                return null;
            }
        }

        public SessionConfiguration[] newArray(int n) {
            return new SessionConfiguration[n];
        }
    };
    public static final int SESSION_HIGH_SPEED = 1;
    public static final int SESSION_REGULAR = 0;
    public static final int SESSION_VENDOR_START = 32768;
    private static final String TAG = "SessionConfiguration";
    private Executor mExecutor = null;
    private InputConfiguration mInputConfig = null;
    private List<OutputConfiguration> mOutputConfigurations;
    private CaptureRequest mSessionParameters = null;
    private int mSessionType;
    private CameraCaptureSession.StateCallback mStateCallback;

    public SessionConfiguration(int n, List<OutputConfiguration> list, Executor executor, CameraCaptureSession.StateCallback stateCallback) {
        this.mSessionType = n;
        this.mOutputConfigurations = Collections.unmodifiableList(new ArrayList<OutputConfiguration>(list));
        this.mStateCallback = stateCallback;
        this.mExecutor = executor;
    }

    private SessionConfiguration(Parcel parcel) {
        int n = parcel.readInt();
        int n2 = parcel.readInt();
        int n3 = parcel.readInt();
        int n4 = parcel.readInt();
        ArrayList<OutputConfiguration> arrayList = new ArrayList<OutputConfiguration>();
        parcel.readTypedList(arrayList, OutputConfiguration.CREATOR);
        if (n2 > 0 && n3 > 0 && n4 != -1) {
            this.mInputConfig = new InputConfiguration(n2, n3, n4);
        }
        this.mSessionType = n;
        this.mOutputConfigurations = arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof SessionConfiguration) {
            object = (SessionConfiguration)object;
            if (this.mInputConfig == ((SessionConfiguration)object).mInputConfig && this.mSessionType == ((SessionConfiguration)object).mSessionType && this.mOutputConfigurations.size() == ((SessionConfiguration)object).mOutputConfigurations.size()) {
                for (int i = 0; i < this.mOutputConfigurations.size(); ++i) {
                    if (this.mOutputConfigurations.get(i).equals(((SessionConfiguration)object).mOutputConfigurations.get(i))) continue;
                    return false;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    public InputConfiguration getInputConfiguration() {
        return this.mInputConfig;
    }

    public List<OutputConfiguration> getOutputConfigurations() {
        return this.mOutputConfigurations;
    }

    public CaptureRequest getSessionParameters() {
        return this.mSessionParameters;
    }

    public int getSessionType() {
        return this.mSessionType;
    }

    public CameraCaptureSession.StateCallback getStateCallback() {
        return this.mStateCallback;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(new int[]{this.mOutputConfigurations.hashCode(), this.mInputConfig.hashCode(), this.mSessionType});
    }

    public void setInputConfiguration(InputConfiguration inputConfiguration) {
        if (this.mSessionType != 1) {
            this.mInputConfig = inputConfiguration;
            return;
        }
        throw new UnsupportedOperationException("Method not supported for high speed session types");
    }

    public void setSessionParameters(CaptureRequest captureRequest) {
        this.mSessionParameters = captureRequest;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (parcel != null) {
            parcel.writeInt(this.mSessionType);
            InputConfiguration inputConfiguration = this.mInputConfig;
            if (inputConfiguration != null) {
                parcel.writeInt(inputConfiguration.getWidth());
                parcel.writeInt(this.mInputConfig.getHeight());
                parcel.writeInt(this.mInputConfig.getFormat());
            } else {
                parcel.writeInt(0);
                parcel.writeInt(0);
                parcel.writeInt(-1);
            }
            parcel.writeTypedList(this.mOutputConfigurations);
            return;
        }
        throw new IllegalArgumentException("dest must not be null");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SessionMode {
    }

}

