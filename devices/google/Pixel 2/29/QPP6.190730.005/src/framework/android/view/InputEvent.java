/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class InputEvent
implements Parcelable {
    public static final Parcelable.Creator<InputEvent> CREATOR;
    protected static final int PARCEL_TOKEN_KEY_EVENT = 2;
    protected static final int PARCEL_TOKEN_MOTION_EVENT = 1;
    private static final boolean TRACK_RECYCLED_LOCATION = false;
    private static final AtomicInteger mNextSeq;
    protected boolean mRecycled;
    private RuntimeException mRecycledLocation;
    protected int mSeq = mNextSeq.getAndIncrement();

    static {
        mNextSeq = new AtomicInteger();
        CREATOR = new Parcelable.Creator<InputEvent>(){

            @Override
            public InputEvent createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                if (n == 2) {
                    return KeyEvent.createFromParcelBody(parcel);
                }
                if (n == 1) {
                    return MotionEvent.createFromParcelBody(parcel);
                }
                throw new IllegalStateException("Unexpected input event type token in parcel.");
            }

            public InputEvent[] newArray(int n) {
                return new InputEvent[n];
            }
        };
    }

    InputEvent() {
    }

    public abstract void cancel();

    public abstract InputEvent copy();

    @Override
    public int describeContents() {
        return 0;
    }

    public final InputDevice getDevice() {
        return InputDevice.getDevice(this.getDeviceId());
    }

    public abstract int getDeviceId();

    public abstract int getDisplayId();

    public abstract long getEventTime();

    public abstract long getEventTimeNano();

    @UnsupportedAppUsage
    public int getSequenceNumber() {
        return this.mSeq;
    }

    public abstract int getSource();

    public boolean isFromSource(int n) {
        boolean bl = (this.getSource() & n) == n;
        return bl;
    }

    public abstract boolean isTainted();

    protected void prepareForReuse() {
        this.mRecycled = false;
        this.mRecycledLocation = null;
        this.mSeq = mNextSeq.getAndIncrement();
    }

    public void recycle() {
        if (!this.mRecycled) {
            this.mRecycled = true;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.toString());
        stringBuilder.append(" recycled twice!");
        throw new RuntimeException(stringBuilder.toString());
    }

    public void recycleIfNeededAfterDispatch() {
        this.recycle();
    }

    public abstract void setDisplayId(int var1);

    public abstract void setSource(int var1);

    public abstract void setTainted(boolean var1);

}

