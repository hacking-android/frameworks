/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.content.ClipDescription;
import android.content.ContentProvider;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.inputmethod.IInputContentUriToken;
import java.security.InvalidParameterException;

public final class InputContentInfo
implements Parcelable {
    public static final Parcelable.Creator<InputContentInfo> CREATOR = new Parcelable.Creator<InputContentInfo>(){

        @Override
        public InputContentInfo createFromParcel(Parcel parcel) {
            return new InputContentInfo(parcel);
        }

        public InputContentInfo[] newArray(int n) {
            return new InputContentInfo[n];
        }
    };
    private final Uri mContentUri;
    private final int mContentUriOwnerUserId;
    private final ClipDescription mDescription;
    private final Uri mLinkUri;
    private IInputContentUriToken mUriToken;

    public InputContentInfo(Uri uri, ClipDescription clipDescription) {
        this(uri, clipDescription, null);
    }

    public InputContentInfo(Uri uri, ClipDescription clipDescription, Uri uri2) {
        InputContentInfo.validateInternal(uri, clipDescription, uri2, true);
        this.mContentUri = uri;
        this.mContentUriOwnerUserId = ContentProvider.getUserIdFromUri(this.mContentUri, UserHandle.myUserId());
        this.mDescription = clipDescription;
        this.mLinkUri = uri2;
    }

    private InputContentInfo(Parcel parcel) {
        this.mContentUri = Uri.CREATOR.createFromParcel(parcel);
        this.mContentUriOwnerUserId = parcel.readInt();
        this.mDescription = ClipDescription.CREATOR.createFromParcel(parcel);
        this.mLinkUri = Uri.CREATOR.createFromParcel(parcel);
        this.mUriToken = parcel.readInt() == 1 ? IInputContentUriToken.Stub.asInterface(parcel.readStrongBinder()) : null;
    }

    private static boolean validateInternal(Uri object, ClipDescription clipDescription, Uri uri, boolean bl) {
        if (object == null) {
            if (!bl) {
                return false;
            }
            throw new NullPointerException("contentUri");
        }
        if (clipDescription == null) {
            if (!bl) {
                return false;
            }
            throw new NullPointerException("description");
        }
        if (!"content".equals(((Uri)object).getScheme())) {
            if (!bl) {
                return false;
            }
            throw new InvalidParameterException("contentUri must have content scheme");
        }
        if (uri != null && ((object = uri.getScheme()) == null || !((String)object).equalsIgnoreCase("http") && !((String)object).equalsIgnoreCase("https"))) {
            if (!bl) {
                return false;
            }
            throw new InvalidParameterException("linkUri must have either http or https scheme");
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Uri getContentUri() {
        if (this.mContentUriOwnerUserId != UserHandle.myUserId()) {
            return ContentProvider.maybeAddUserId(this.mContentUri, this.mContentUriOwnerUserId);
        }
        return this.mContentUri;
    }

    public ClipDescription getDescription() {
        return this.mDescription;
    }

    public Uri getLinkUri() {
        return this.mLinkUri;
    }

    public void releasePermission() {
        IInputContentUriToken iInputContentUriToken = this.mUriToken;
        if (iInputContentUriToken == null) {
            return;
        }
        try {
            iInputContentUriToken.release();
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public void requestPermission() {
        IInputContentUriToken iInputContentUriToken = this.mUriToken;
        if (iInputContentUriToken == null) {
            return;
        }
        try {
            iInputContentUriToken.take();
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public void setUriToken(IInputContentUriToken iInputContentUriToken) {
        if (this.mUriToken == null) {
            this.mUriToken = iInputContentUriToken;
            return;
        }
        throw new IllegalStateException("URI token is already set");
    }

    public boolean validate() {
        return InputContentInfo.validateInternal(this.mContentUri, this.mDescription, this.mLinkUri, false);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Uri.writeToParcel(parcel, this.mContentUri);
        parcel.writeInt(this.mContentUriOwnerUserId);
        this.mDescription.writeToParcel(parcel, n);
        Uri.writeToParcel(parcel, this.mLinkUri);
        if (this.mUriToken != null) {
            parcel.writeInt(1);
            parcel.writeStrongBinder(this.mUriToken.asBinder());
        } else {
            parcel.writeInt(0);
        }
    }

}

