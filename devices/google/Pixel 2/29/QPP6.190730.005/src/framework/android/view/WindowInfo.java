/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Rect;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pools;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WindowInfo
implements Parcelable {
    public static final Parcelable.Creator<WindowInfo> CREATOR;
    private static final int MAX_POOL_SIZE = 10;
    private static final Pools.SynchronizedPool<WindowInfo> sPool;
    public long accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    public IBinder activityToken;
    public final Rect boundsInScreen = new Rect();
    public List<IBinder> childTokens;
    public boolean focused;
    public boolean hasFlagWatchOutsideTouch;
    public boolean inPictureInPicture;
    public int layer;
    public IBinder parentToken;
    public CharSequence title;
    public IBinder token;
    public int type;

    static {
        sPool = new Pools.SynchronizedPool(10);
        CREATOR = new Parcelable.Creator<WindowInfo>(){

            @Override
            public WindowInfo createFromParcel(Parcel parcel) {
                WindowInfo windowInfo = WindowInfo.obtain();
                windowInfo.initFromParcel(parcel);
                return windowInfo;
            }

            public WindowInfo[] newArray(int n) {
                return new WindowInfo[n];
            }
        };
    }

    private WindowInfo() {
    }

    private void clear() {
        this.type = 0;
        this.layer = 0;
        this.token = null;
        this.parentToken = null;
        this.activityToken = null;
        this.focused = false;
        this.boundsInScreen.setEmpty();
        List<IBinder> list = this.childTokens;
        if (list != null) {
            list.clear();
        }
        this.inPictureInPicture = false;
        this.hasFlagWatchOutsideTouch = false;
    }

    private void initFromParcel(Parcel parcel) {
        this.type = parcel.readInt();
        this.layer = parcel.readInt();
        this.token = parcel.readStrongBinder();
        this.parentToken = parcel.readStrongBinder();
        this.activityToken = parcel.readStrongBinder();
        int n = parcel.readInt();
        boolean bl = false;
        boolean bl2 = n == 1;
        this.focused = bl2;
        this.boundsInScreen.readFromParcel(parcel);
        this.title = parcel.readCharSequence();
        this.accessibilityIdOfAnchor = parcel.readLong();
        bl2 = parcel.readInt() == 1;
        this.inPictureInPicture = bl2;
        bl2 = parcel.readInt() == 1;
        this.hasFlagWatchOutsideTouch = bl2;
        if (parcel.readInt() == 1) {
            bl = true;
        }
        if (bl) {
            if (this.childTokens == null) {
                this.childTokens = new ArrayList<IBinder>();
            }
            parcel.readBinderList(this.childTokens);
        }
    }

    public static WindowInfo obtain() {
        WindowInfo windowInfo;
        WindowInfo windowInfo2 = windowInfo = sPool.acquire();
        if (windowInfo == null) {
            windowInfo2 = new WindowInfo();
        }
        return windowInfo2;
    }

    public static WindowInfo obtain(WindowInfo windowInfo) {
        WindowInfo windowInfo2 = WindowInfo.obtain();
        windowInfo2.type = windowInfo.type;
        windowInfo2.layer = windowInfo.layer;
        windowInfo2.token = windowInfo.token;
        windowInfo2.parentToken = windowInfo.parentToken;
        windowInfo2.activityToken = windowInfo.activityToken;
        windowInfo2.focused = windowInfo.focused;
        windowInfo2.boundsInScreen.set(windowInfo.boundsInScreen);
        windowInfo2.title = windowInfo.title;
        windowInfo2.accessibilityIdOfAnchor = windowInfo.accessibilityIdOfAnchor;
        windowInfo2.inPictureInPicture = windowInfo.inPictureInPicture;
        windowInfo2.hasFlagWatchOutsideTouch = windowInfo.hasFlagWatchOutsideTouch;
        List<IBinder> list = windowInfo.childTokens;
        if (list != null && !list.isEmpty()) {
            list = windowInfo2.childTokens;
            if (list == null) {
                windowInfo2.childTokens = new ArrayList<IBinder>(windowInfo.childTokens);
            } else {
                list.addAll(windowInfo.childTokens);
            }
        }
        return windowInfo2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void recycle() {
        this.clear();
        sPool.release(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WindowInfo[");
        stringBuilder.append("title=");
        stringBuilder.append(this.title);
        stringBuilder.append(", type=");
        stringBuilder.append(this.type);
        stringBuilder.append(", layer=");
        stringBuilder.append(this.layer);
        stringBuilder.append(", token=");
        stringBuilder.append(this.token);
        stringBuilder.append(", bounds=");
        stringBuilder.append(this.boundsInScreen);
        stringBuilder.append(", parent=");
        stringBuilder.append(this.parentToken);
        stringBuilder.append(", focused=");
        stringBuilder.append(this.focused);
        stringBuilder.append(", children=");
        stringBuilder.append(this.childTokens);
        stringBuilder.append(", accessibility anchor=");
        stringBuilder.append(this.accessibilityIdOfAnchor);
        stringBuilder.append(", pictureInPicture=");
        stringBuilder.append(this.inPictureInPicture);
        stringBuilder.append(", watchOutsideTouch=");
        stringBuilder.append(this.hasFlagWatchOutsideTouch);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.type);
        parcel.writeInt(this.layer);
        parcel.writeStrongBinder(this.token);
        parcel.writeStrongBinder(this.parentToken);
        parcel.writeStrongBinder(this.activityToken);
        parcel.writeInt((int)this.focused);
        this.boundsInScreen.writeToParcel(parcel, n);
        parcel.writeCharSequence(this.title);
        parcel.writeLong(this.accessibilityIdOfAnchor);
        parcel.writeInt((int)this.inPictureInPicture);
        parcel.writeInt((int)this.hasFlagWatchOutsideTouch);
        List<IBinder> list = this.childTokens;
        if (list != null && !list.isEmpty()) {
            parcel.writeInt(1);
            parcel.writeBinderList(this.childTokens);
        } else {
            parcel.writeInt(0);
        }
    }

}

