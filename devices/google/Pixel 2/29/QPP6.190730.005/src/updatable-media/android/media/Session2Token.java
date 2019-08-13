/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.media.MediaSession2;
import android.media.Session2Link;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;

public final class Session2Token
implements Parcelable {
    public static final Parcelable.Creator<Session2Token> CREATOR = new Parcelable.Creator<Session2Token>(){

        public Session2Token createFromParcel(Parcel parcel) {
            return new Session2Token(parcel);
        }

        public Session2Token[] newArray(int n) {
            return new Session2Token[n];
        }
    };
    private static final String TAG = "Session2Token";
    public static final int TYPE_SESSION = 0;
    public static final int TYPE_SESSION_SERVICE = 1;
    private final ComponentName mComponentName;
    private final Bundle mExtras;
    private final String mPackageName;
    private final String mServiceName;
    private final Session2Link mSessionLink;
    private final int mType;
    private final int mUid;

    Session2Token(int n, int n2, String string, Session2Link session2Link, Bundle bundle) {
        this.mUid = n;
        this.mType = n2;
        this.mPackageName = string;
        this.mServiceName = null;
        this.mComponentName = null;
        this.mSessionLink = session2Link;
        this.mExtras = bundle;
    }

    public Session2Token(Context object, ComponentName componentName) {
        if (object != null) {
            if (componentName != null) {
                object = object.getPackageManager();
                int n = Session2Token.getUid((PackageManager)object, componentName.getPackageName());
                if (!Session2Token.isInterfaceDeclared((PackageManager)object, "android.media.MediaSession2Service", componentName)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((Object)componentName);
                    ((StringBuilder)object).append(" doesn't implement MediaSession2Service.");
                    Log.w((String)TAG, (String)((StringBuilder)object).toString());
                }
                this.mComponentName = componentName;
                this.mPackageName = componentName.getPackageName();
                this.mServiceName = componentName.getClassName();
                this.mUid = n;
                this.mType = 1;
                this.mSessionLink = null;
                this.mExtras = Bundle.EMPTY;
                return;
            }
            throw new IllegalArgumentException("serviceComponent shouldn't be null");
        }
        throw new IllegalArgumentException("context shouldn't be null");
    }

    Session2Token(Parcel parcel) {
        this.mUid = parcel.readInt();
        this.mType = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mServiceName = parcel.readString();
        this.mSessionLink = (Session2Link)parcel.readParcelable(null);
        this.mComponentName = ComponentName.unflattenFromString((String)parcel.readString());
        Bundle bundle = parcel.readBundle();
        if (bundle == null) {
            Log.w((String)TAG, (String)"extras shouldn't be null.");
            parcel = Bundle.EMPTY;
        } else {
            parcel = bundle;
            if (MediaSession2.hasCustomParcelable(bundle)) {
                Log.w((String)TAG, (String)"extras contain custom parcelable. Ignoring.");
                parcel = Bundle.EMPTY;
            }
        }
        this.mExtras = parcel;
    }

    private static int getUid(PackageManager packageManager, String string) {
        try {
            int n = packageManager.getApplicationInfo((String)string, (int)0).uid;
            return n;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot find package ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    private static boolean isInterfaceDeclared(PackageManager object, String string, ComponentName componentName) {
        string = new Intent(string);
        string.setPackage(componentName.getPackageName());
        object = object.queryIntentServices((Intent)string, 128);
        if (object != null) {
            for (int i = 0; i < object.size(); ++i) {
                string = (ResolveInfo)object.get(i);
                if (string == null || ((ResolveInfo)string).serviceInfo == null || !TextUtils.equals((CharSequence)string.serviceInfo.name, (CharSequence)componentName.getClassName())) continue;
                return true;
            }
        }
        return false;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof Session2Token;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (Session2Token)object;
            if (this.mUid != ((Session2Token)object).mUid || !TextUtils.equals((CharSequence)this.mPackageName, (CharSequence)((Session2Token)object).mPackageName) || !TextUtils.equals((CharSequence)this.mServiceName, (CharSequence)((Session2Token)object).mServiceName) || this.mType != ((Session2Token)object).mType || !Objects.equals(this.mSessionLink, ((Session2Token)object).mSessionLink)) break block1;
            bl = true;
        }
        return bl;
    }

    public Bundle getExtras() {
        return new Bundle(this.mExtras);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getServiceName() {
        return this.mServiceName;
    }

    Session2Link getSessionLink() {
        return this.mSessionLink;
    }

    public int getType() {
        return this.mType;
    }

    public int getUid() {
        return this.mUid;
    }

    public int hashCode() {
        return Objects.hash(this.mType, this.mUid, this.mPackageName, this.mServiceName, this.mSessionLink);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Session2Token {pkg=");
        stringBuilder.append(this.mPackageName);
        stringBuilder.append(" type=");
        stringBuilder.append(this.mType);
        stringBuilder.append(" service=");
        stringBuilder.append(this.mServiceName);
        stringBuilder.append(" Session2Link=");
        stringBuilder.append(this.mSessionLink);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mUid);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mServiceName);
        parcel.writeParcelable((Parcelable)this.mSessionLink, n);
        Object object = this.mComponentName;
        object = object == null ? "" : object.flattenToString();
        parcel.writeString((String)object);
        parcel.writeBundle(this.mExtras);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TokenType {
    }

}

