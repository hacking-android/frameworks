/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.LocusId;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.contentcapture.ContentCaptureSessionId;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ContentCaptureContext
implements Parcelable {
    public static final Parcelable.Creator<ContentCaptureContext> CREATOR = new Parcelable.Creator<ContentCaptureContext>(){

        @Override
        public ContentCaptureContext createFromParcel(Parcel parcel) {
            Object object;
            Parcelable parcelable;
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            if (bl) {
                object = (LocusId)parcel.readParcelable(null);
                parcelable = parcel.readBundle();
                object = new Builder((LocusId)object);
                if (parcelable != null) {
                    ((Builder)object).setExtras((Bundle)parcelable);
                }
                parcelable = new ContentCaptureContext((Builder)object);
            } else {
                parcelable = null;
            }
            object = (ComponentName)parcel.readParcelable(null);
            if (object == null) {
                return parcelable;
            }
            return new ContentCaptureContext((ContentCaptureContext)parcelable, (ComponentName)object, parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public ContentCaptureContext[] newArray(int n) {
            return new ContentCaptureContext[n];
        }
    };
    @SystemApi
    public static final int FLAG_DISABLED_BY_APP = 1;
    @SystemApi
    public static final int FLAG_DISABLED_BY_FLAG_SECURE = 2;
    @SystemApi
    public static final int FLAG_RECONNECTED = 4;
    private final ComponentName mComponentName;
    private final int mDisplayId;
    private final Bundle mExtras;
    private final int mFlags;
    private final boolean mHasClientContext;
    private final LocusId mId;
    private int mParentSessionId = 0;
    private final int mTaskId;

    private ContentCaptureContext(Builder builder) {
        this.mHasClientContext = true;
        this.mExtras = builder.mExtras;
        this.mId = builder.mId;
        this.mComponentName = null;
        this.mFlags = 0;
        this.mTaskId = 0;
        this.mDisplayId = -1;
    }

    public ContentCaptureContext(ContentCaptureContext contentCaptureContext, int n) {
        this.mHasClientContext = contentCaptureContext.mHasClientContext;
        this.mExtras = contentCaptureContext.mExtras;
        this.mId = contentCaptureContext.mId;
        this.mComponentName = contentCaptureContext.mComponentName;
        this.mTaskId = contentCaptureContext.mTaskId;
        this.mFlags = contentCaptureContext.mFlags | n;
        this.mDisplayId = contentCaptureContext.mDisplayId;
    }

    public ContentCaptureContext(ContentCaptureContext contentCaptureContext, ComponentName componentName, int n, int n2, int n3) {
        if (contentCaptureContext != null) {
            this.mHasClientContext = true;
            this.mExtras = contentCaptureContext.mExtras;
            this.mId = contentCaptureContext.mId;
        } else {
            this.mHasClientContext = false;
            this.mExtras = null;
            this.mId = null;
        }
        this.mComponentName = Preconditions.checkNotNull(componentName);
        this.mTaskId = n;
        this.mDisplayId = n2;
        this.mFlags = n3;
    }

    public static ContentCaptureContext forLocusId(String string2) {
        return new Builder(new LocusId(string2)).build();
    }

    private boolean fromServer() {
        boolean bl = this.mComponentName != null;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter) {
        if (this.mComponentName != null) {
            printWriter.print("activity=");
            printWriter.print(this.mComponentName.flattenToShortString());
        }
        if (this.mId != null) {
            printWriter.print(", id=");
            this.mId.dump(printWriter);
        }
        printWriter.print(", taskId=");
        printWriter.print(this.mTaskId);
        printWriter.print(", displayId=");
        printWriter.print(this.mDisplayId);
        if (this.mParentSessionId != 0) {
            printWriter.print(", parentId=");
            printWriter.print(this.mParentSessionId);
        }
        if (this.mFlags > 0) {
            printWriter.print(", flags=");
            printWriter.print(this.mFlags);
        }
        if (this.mExtras != null) {
            printWriter.print(", hasExtras");
        }
    }

    @SystemApi
    public ComponentName getActivityComponent() {
        return this.mComponentName;
    }

    @SystemApi
    public int getDisplayId() {
        return this.mDisplayId;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @SystemApi
    public int getFlags() {
        return this.mFlags;
    }

    public LocusId getLocusId() {
        return this.mId;
    }

    @SystemApi
    public ContentCaptureSessionId getParentSessionId() {
        int n = this.mParentSessionId;
        ContentCaptureSessionId contentCaptureSessionId = n == 0 ? null : new ContentCaptureSessionId(n);
        return contentCaptureSessionId;
    }

    @SystemApi
    public int getTaskId() {
        return this.mTaskId;
    }

    public void setParentSessionId(int n) {
        this.mParentSessionId = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Context[");
        if (this.fromServer()) {
            stringBuilder.append("act=");
            stringBuilder.append(ComponentName.flattenToShortString(this.mComponentName));
            stringBuilder.append(", taskId=");
            stringBuilder.append(this.mTaskId);
            stringBuilder.append(", displayId=");
            stringBuilder.append(this.mDisplayId);
            stringBuilder.append(", flags=");
            stringBuilder.append(this.mFlags);
        } else {
            stringBuilder.append("id=");
            stringBuilder.append(this.mId);
            if (this.mExtras != null) {
                stringBuilder.append(", hasExtras");
            }
        }
        if (this.mParentSessionId != 0) {
            stringBuilder.append(", parentId=");
            stringBuilder.append(this.mParentSessionId);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.mHasClientContext);
        if (this.mHasClientContext) {
            parcel.writeParcelable(this.mId, n);
            parcel.writeBundle(this.mExtras);
        }
        parcel.writeParcelable(this.mComponentName, n);
        if (this.fromServer()) {
            parcel.writeInt(this.mTaskId);
            parcel.writeInt(this.mDisplayId);
            parcel.writeInt(this.mFlags);
        }
    }

    public static final class Builder {
        private boolean mDestroyed;
        private Bundle mExtras;
        private final LocusId mId;

        public Builder(LocusId locusId) {
            this.mId = Preconditions.checkNotNull(locusId);
        }

        private void throwIfDestroyed() {
            Preconditions.checkState(this.mDestroyed ^ true, "Already called #build()");
        }

        public ContentCaptureContext build() {
            this.throwIfDestroyed();
            this.mDestroyed = true;
            return new ContentCaptureContext(this);
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = Preconditions.checkNotNull(bundle);
            this.throwIfDestroyed();
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface ContextCreationFlags {
    }

}

