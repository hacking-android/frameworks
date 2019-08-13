/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.view.IDragAndDropPermissions;

public class DragEvent
implements Parcelable {
    public static final int ACTION_DRAG_ENDED = 4;
    public static final int ACTION_DRAG_ENTERED = 5;
    public static final int ACTION_DRAG_EXITED = 6;
    public static final int ACTION_DRAG_LOCATION = 2;
    public static final int ACTION_DRAG_STARTED = 1;
    public static final int ACTION_DROP = 3;
    public static final Parcelable.Creator<DragEvent> CREATOR;
    private static final int MAX_RECYCLED = 10;
    private static final boolean TRACK_RECYCLED_LOCATION = false;
    private static final Object gRecyclerLock;
    private static DragEvent gRecyclerTop;
    private static int gRecyclerUsed;
    int mAction;
    @UnsupportedAppUsage
    ClipData mClipData;
    @UnsupportedAppUsage
    ClipDescription mClipDescription;
    IDragAndDropPermissions mDragAndDropPermissions;
    boolean mDragResult;
    boolean mEventHandlerWasCalled;
    Object mLocalState;
    private DragEvent mNext;
    private boolean mRecycled;
    private RuntimeException mRecycledLocation;
    float mX;
    float mY;

    static {
        gRecyclerLock = new Object();
        gRecyclerUsed = 0;
        gRecyclerTop = null;
        CREATOR = new Parcelable.Creator<DragEvent>(){

            @Override
            public DragEvent createFromParcel(Parcel parcel) {
                DragEvent dragEvent = DragEvent.obtain();
                dragEvent.mAction = parcel.readInt();
                dragEvent.mX = parcel.readFloat();
                dragEvent.mY = parcel.readFloat();
                boolean bl = parcel.readInt() != 0;
                dragEvent.mDragResult = bl;
                if (parcel.readInt() != 0) {
                    dragEvent.mClipData = ClipData.CREATOR.createFromParcel(parcel);
                }
                if (parcel.readInt() != 0) {
                    dragEvent.mClipDescription = ClipDescription.CREATOR.createFromParcel(parcel);
                }
                if (parcel.readInt() != 0) {
                    dragEvent.mDragAndDropPermissions = IDragAndDropPermissions.Stub.asInterface(parcel.readStrongBinder());
                }
                return dragEvent;
            }

            public DragEvent[] newArray(int n) {
                return new DragEvent[n];
            }
        };
    }

    private DragEvent() {
    }

    private void init(int n, float f, float f2, ClipDescription clipDescription, ClipData clipData, IDragAndDropPermissions iDragAndDropPermissions, Object object, boolean bl) {
        this.mAction = n;
        this.mX = f;
        this.mY = f2;
        this.mClipDescription = clipDescription;
        this.mClipData = clipData;
        this.mDragAndDropPermissions = iDragAndDropPermissions;
        this.mLocalState = object;
        this.mDragResult = bl;
    }

    static DragEvent obtain() {
        return DragEvent.obtain(0, 0.0f, 0.0f, null, null, null, null, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static DragEvent obtain(int n, float f, float f2, Object object, ClipDescription clipDescription, ClipData clipData, IDragAndDropPermissions iDragAndDropPermissions, boolean bl) {
        DragEvent dragEvent;
        Object object2 = gRecyclerLock;
        synchronized (object2) {
            if (gRecyclerTop == null) {
                DragEvent dragEvent2 = new DragEvent();
                dragEvent2.init(n, f, f2, clipDescription, clipData, iDragAndDropPermissions, object, bl);
                return dragEvent2;
            }
            dragEvent = gRecyclerTop;
            gRecyclerTop = dragEvent.mNext;
            --gRecyclerUsed;
        }
        dragEvent.mRecycledLocation = null;
        dragEvent.mRecycled = false;
        dragEvent.mNext = null;
        dragEvent.init(n, f, f2, clipDescription, clipData, iDragAndDropPermissions, object, bl);
        return dragEvent;
    }

    @UnsupportedAppUsage
    public static DragEvent obtain(DragEvent dragEvent) {
        return DragEvent.obtain(dragEvent.mAction, dragEvent.mX, dragEvent.mY, dragEvent.mLocalState, dragEvent.mClipDescription, dragEvent.mClipData, dragEvent.mDragAndDropPermissions, dragEvent.mDragResult);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAction() {
        return this.mAction;
    }

    public ClipData getClipData() {
        return this.mClipData;
    }

    public ClipDescription getClipDescription() {
        return this.mClipDescription;
    }

    public IDragAndDropPermissions getDragAndDropPermissions() {
        return this.mDragAndDropPermissions;
    }

    public Object getLocalState() {
        return this.mLocalState;
    }

    public boolean getResult() {
        return this.mDragResult;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void recycle() {
        if (this.mRecycled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.toString());
            stringBuilder.append(" recycled twice!");
            throw new RuntimeException(stringBuilder.toString());
        }
        this.mRecycled = true;
        this.mClipData = null;
        this.mClipDescription = null;
        this.mLocalState = null;
        this.mEventHandlerWasCalled = false;
        Object object = gRecyclerLock;
        synchronized (object) {
            if (gRecyclerUsed < 10) {
                ++gRecyclerUsed;
                this.mNext = gRecyclerTop;
                gRecyclerTop = this;
            }
            return;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DragEvent{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" action=");
        stringBuilder.append(this.mAction);
        stringBuilder.append(" @ (");
        stringBuilder.append(this.mX);
        stringBuilder.append(", ");
        stringBuilder.append(this.mY);
        stringBuilder.append(") desc=");
        stringBuilder.append(this.mClipDescription);
        stringBuilder.append(" data=");
        stringBuilder.append(this.mClipData);
        stringBuilder.append(" local=");
        stringBuilder.append(this.mLocalState);
        stringBuilder.append(" result=");
        stringBuilder.append(this.mDragResult);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mAction);
        parcel.writeFloat(this.mX);
        parcel.writeFloat(this.mY);
        parcel.writeInt((int)this.mDragResult);
        if (this.mClipData == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mClipData.writeToParcel(parcel, n);
        }
        if (this.mClipDescription == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mClipDescription.writeToParcel(parcel, n);
        }
        if (this.mDragAndDropPermissions == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeStrongBinder(this.mDragAndDropPermissions.asBinder());
        }
    }

}

