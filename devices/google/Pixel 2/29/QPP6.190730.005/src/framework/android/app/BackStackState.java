/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.BackStackRecord;
import android.app.Fragment;
import android.app.FragmentManagerImpl;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;

final class BackStackState
implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator<BackStackState>(){

        @Override
        public BackStackState createFromParcel(Parcel parcel) {
            return new BackStackState(parcel);
        }

        public BackStackState[] newArray(int n) {
            return new BackStackState[n];
        }
    };
    final int mBreadCrumbShortTitleRes;
    final CharSequence mBreadCrumbShortTitleText;
    final int mBreadCrumbTitleRes;
    final CharSequence mBreadCrumbTitleText;
    final int mIndex;
    final String mName;
    final int[] mOps;
    final boolean mReorderingAllowed;
    final ArrayList<String> mSharedElementSourceNames;
    final ArrayList<String> mSharedElementTargetNames;
    final int mTransition;
    final int mTransitionStyle;

    public BackStackState(FragmentManagerImpl object, BackStackRecord backStackRecord) {
        int n = backStackRecord.mOps.size();
        this.mOps = new int[n * 6];
        if (backStackRecord.mAddToBackStack) {
            int n2 = 0;
            int n3 = 0;
            while (n3 < n) {
                object = backStackRecord.mOps.get(n3);
                int[] arrn = this.mOps;
                int n4 = n2 + 1;
                arrn[n2] = ((BackStackRecord.Op)object).cmd;
                arrn = this.mOps;
                int n5 = n4 + 1;
                n2 = ((BackStackRecord.Op)object).fragment != null ? object.fragment.mIndex : -1;
                arrn[n4] = n2;
                arrn = this.mOps;
                n2 = n5 + 1;
                arrn[n5] = ((BackStackRecord.Op)object).enterAnim;
                arrn = this.mOps;
                n5 = n2 + 1;
                arrn[n2] = ((BackStackRecord.Op)object).exitAnim;
                arrn = this.mOps;
                n2 = n5 + 1;
                arrn[n5] = ((BackStackRecord.Op)object).popEnterAnim;
                this.mOps[n2] = ((BackStackRecord.Op)object).popExitAnim;
                ++n3;
                ++n2;
            }
            this.mTransition = backStackRecord.mTransition;
            this.mTransitionStyle = backStackRecord.mTransitionStyle;
            this.mName = backStackRecord.mName;
            this.mIndex = backStackRecord.mIndex;
            this.mBreadCrumbTitleRes = backStackRecord.mBreadCrumbTitleRes;
            this.mBreadCrumbTitleText = backStackRecord.mBreadCrumbTitleText;
            this.mBreadCrumbShortTitleRes = backStackRecord.mBreadCrumbShortTitleRes;
            this.mBreadCrumbShortTitleText = backStackRecord.mBreadCrumbShortTitleText;
            this.mSharedElementSourceNames = backStackRecord.mSharedElementSourceNames;
            this.mSharedElementTargetNames = backStackRecord.mSharedElementTargetNames;
            this.mReorderingAllowed = backStackRecord.mReorderingAllowed;
            return;
        }
        throw new IllegalStateException("Not on back stack");
    }

    public BackStackState(Parcel parcel) {
        this.mOps = parcel.createIntArray();
        this.mTransition = parcel.readInt();
        this.mTransitionStyle = parcel.readInt();
        this.mName = parcel.readString();
        this.mIndex = parcel.readInt();
        this.mBreadCrumbTitleRes = parcel.readInt();
        this.mBreadCrumbTitleText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mBreadCrumbShortTitleRes = parcel.readInt();
        this.mBreadCrumbShortTitleText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSharedElementSourceNames = parcel.createStringArrayList();
        this.mSharedElementTargetNames = parcel.createStringArrayList();
        boolean bl = parcel.readInt() != 0;
        this.mReorderingAllowed = bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public BackStackRecord instantiate(FragmentManagerImpl fragmentManagerImpl) {
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
        int n = 0;
        for (int i = 0; i < this.mOps.length; ++i) {
            BackStackRecord.Op op = new BackStackRecord.Op();
            Object object = this.mOps;
            Object object2 = i + 1;
            op.cmd = object[i];
            if (FragmentManagerImpl.DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Instantiate ");
                ((StringBuilder)object).append(backStackRecord);
                ((StringBuilder)object).append(" op #");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" base fragment #");
                ((StringBuilder)object).append(this.mOps[object2]);
                Log.v("FragmentManager", ((StringBuilder)object).toString());
            }
            object = this.mOps;
            i = object2 + 1;
            op.fragment = (object2 = (Object)object[object2]) >= 0 ? fragmentManagerImpl.mActive.get((int)object2) : null;
            object = this.mOps;
            object2 = i + 1;
            op.enterAnim = (int)object[i];
            int n2 = object2 + 1;
            op.exitAnim = (int)object[object2];
            i = n2 + 1;
            op.popEnterAnim = (int)object[n2];
            op.popExitAnim = (int)object[i];
            backStackRecord.mEnterAnim = op.enterAnim;
            backStackRecord.mExitAnim = op.exitAnim;
            backStackRecord.mPopEnterAnim = op.popEnterAnim;
            backStackRecord.mPopExitAnim = op.popExitAnim;
            backStackRecord.addOp(op);
            ++n;
        }
        backStackRecord.mTransition = this.mTransition;
        backStackRecord.mTransitionStyle = this.mTransitionStyle;
        backStackRecord.mName = this.mName;
        backStackRecord.mIndex = this.mIndex;
        backStackRecord.mAddToBackStack = true;
        backStackRecord.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
        backStackRecord.mBreadCrumbTitleText = this.mBreadCrumbTitleText;
        backStackRecord.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
        backStackRecord.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText;
        backStackRecord.mSharedElementSourceNames = this.mSharedElementSourceNames;
        backStackRecord.mSharedElementTargetNames = this.mSharedElementTargetNames;
        backStackRecord.mReorderingAllowed = this.mReorderingAllowed;
        backStackRecord.bumpBackStackNesting(1);
        return backStackRecord;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeIntArray(this.mOps);
        parcel.writeInt(this.mTransition);
        parcel.writeInt(this.mTransitionStyle);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mIndex);
        parcel.writeInt(this.mBreadCrumbTitleRes);
        TextUtils.writeToParcel(this.mBreadCrumbTitleText, parcel, 0);
        parcel.writeInt(this.mBreadCrumbShortTitleRes);
        TextUtils.writeToParcel(this.mBreadCrumbShortTitleText, parcel, 0);
        parcel.writeStringList(this.mSharedElementSourceNames);
        parcel.writeStringList(this.mSharedElementTargetNames);
        parcel.writeInt((int)this.mReorderingAllowed);
    }

}

