/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Fragment;
import android.app.FragmentContainer;
import android.app.FragmentHostCallback;
import android.app.FragmentManagerImpl;
import android.app.FragmentManagerNonConfig;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

final class FragmentState
implements Parcelable {
    public static final Parcelable.Creator<FragmentState> CREATOR = new Parcelable.Creator<FragmentState>(){

        @Override
        public FragmentState createFromParcel(Parcel parcel) {
            return new FragmentState(parcel);
        }

        public FragmentState[] newArray(int n) {
            return new FragmentState[n];
        }
    };
    final Bundle mArguments;
    final String mClassName;
    final int mContainerId;
    final boolean mDetached;
    final int mFragmentId;
    final boolean mFromLayout;
    final boolean mHidden;
    final int mIndex;
    Fragment mInstance;
    final boolean mRetainInstance;
    Bundle mSavedFragmentState;
    final String mTag;

    FragmentState(Fragment fragment) {
        this.mClassName = fragment.getClass().getName();
        this.mIndex = fragment.mIndex;
        this.mFromLayout = fragment.mFromLayout;
        this.mFragmentId = fragment.mFragmentId;
        this.mContainerId = fragment.mContainerId;
        this.mTag = fragment.mTag;
        this.mRetainInstance = fragment.mRetainInstance;
        this.mDetached = fragment.mDetached;
        this.mArguments = fragment.mArguments;
        this.mHidden = fragment.mHidden;
    }

    FragmentState(Parcel parcel) {
        this.mClassName = parcel.readString();
        this.mIndex = parcel.readInt();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.mFromLayout = bl2;
        this.mFragmentId = parcel.readInt();
        this.mContainerId = parcel.readInt();
        this.mTag = parcel.readString();
        bl2 = parcel.readInt() != 0;
        this.mRetainInstance = bl2;
        bl2 = parcel.readInt() != 0;
        this.mDetached = bl2;
        this.mArguments = parcel.readBundle();
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.mHidden = bl2;
        this.mSavedFragmentState = parcel.readBundle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Fragment instantiate(FragmentHostCallback object, FragmentContainer object2, Fragment fragment, FragmentManagerNonConfig fragmentManagerNonConfig) {
        if (this.mInstance == null) {
            Context context = ((FragmentHostCallback)object).getContext();
            Bundle bundle = this.mArguments;
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            this.mInstance = object2 != null ? ((FragmentContainer)object2).instantiate(context, this.mClassName, this.mArguments) : Fragment.instantiate(context, this.mClassName, this.mArguments);
            object2 = this.mSavedFragmentState;
            if (object2 != null) {
                ((Bundle)object2).setClassLoader(context.getClassLoader());
                this.mInstance.mSavedFragmentState = this.mSavedFragmentState;
            }
            this.mInstance.setIndex(this.mIndex, fragment);
            object2 = this.mInstance;
            ((Fragment)object2).mFromLayout = this.mFromLayout;
            ((Fragment)object2).mRestored = true;
            ((Fragment)object2).mFragmentId = this.mFragmentId;
            ((Fragment)object2).mContainerId = this.mContainerId;
            ((Fragment)object2).mTag = this.mTag;
            ((Fragment)object2).mRetainInstance = this.mRetainInstance;
            ((Fragment)object2).mDetached = this.mDetached;
            ((Fragment)object2).mHidden = this.mHidden;
            ((Fragment)object2).mFragmentManager = ((FragmentHostCallback)object).mFragmentManager;
            if (FragmentManagerImpl.DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Instantiated fragment ");
                ((StringBuilder)object).append(this.mInstance);
                Log.v("FragmentManager", ((StringBuilder)object).toString());
            }
        }
        object = this.mInstance;
        ((Fragment)object).mChildNonConfig = fragmentManagerNonConfig;
        return object;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mClassName);
        parcel.writeInt(this.mIndex);
        parcel.writeInt((int)this.mFromLayout);
        parcel.writeInt(this.mFragmentId);
        parcel.writeInt(this.mContainerId);
        parcel.writeString(this.mTag);
        parcel.writeInt((int)this.mRetainInstance);
        parcel.writeInt((int)this.mDetached);
        parcel.writeBundle(this.mArguments);
        parcel.writeInt((int)this.mHidden);
        parcel.writeBundle(this.mSavedFragmentState);
    }

}

