/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class AbsSavedState
implements Parcelable {
    public static final Parcelable.Creator<AbsSavedState> CREATOR;
    public static final AbsSavedState EMPTY_STATE;
    private final Parcelable mSuperState;

    static {
        EMPTY_STATE = new AbsSavedState(){};
        CREATOR = new Parcelable.ClassLoaderCreator<AbsSavedState>(){

            @Override
            public AbsSavedState createFromParcel(Parcel parcel) {
                return this.createFromParcel(parcel, null);
            }

            @Override
            public AbsSavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                if (parcel.readParcelable(classLoader) == null) {
                    return EMPTY_STATE;
                }
                throw new IllegalStateException("superState must be null");
            }

            public AbsSavedState[] newArray(int n) {
                return new AbsSavedState[n];
            }
        };
    }

    private AbsSavedState() {
        this.mSuperState = null;
    }

    protected AbsSavedState(Parcel parcel) {
        this(parcel, null);
    }

    protected AbsSavedState(Parcel object, ClassLoader classLoader) {
        object = ((Parcel)object).readParcelable(classLoader);
        if (object == null) {
            object = EMPTY_STATE;
        }
        this.mSuperState = object;
    }

    protected AbsSavedState(Parcelable parcelable) {
        if (parcelable != null) {
            if (parcelable == EMPTY_STATE) {
                parcelable = null;
            }
            this.mSuperState = parcelable;
            return;
        }
        throw new IllegalArgumentException("superState must not be null");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final Parcelable getSuperState() {
        return this.mSuperState;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mSuperState, n);
    }

}

