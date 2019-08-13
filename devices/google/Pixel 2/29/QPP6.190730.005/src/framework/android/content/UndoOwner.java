/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.UndoManager;

public class UndoOwner {
    Object mData;
    final UndoManager mManager;
    int mOpCount;
    int mSavedIdx;
    int mStateSeq;
    final String mTag;

    UndoOwner(String string2, UndoManager undoManager) {
        if (string2 != null) {
            if (undoManager != null) {
                this.mTag = string2;
                this.mManager = undoManager;
                return;
            }
            throw new NullPointerException("manager can't be null");
        }
        throw new NullPointerException("tag can't be null");
    }

    public Object getData() {
        return this.mData;
    }

    public String getTag() {
        return this.mTag;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UndoOwner:[mTag=");
        stringBuilder.append(this.mTag);
        stringBuilder.append(" mManager=");
        stringBuilder.append(this.mManager);
        stringBuilder.append(" mData=");
        stringBuilder.append(this.mData);
        stringBuilder.append(" mData=");
        stringBuilder.append(this.mData);
        stringBuilder.append(" mOpCount=");
        stringBuilder.append(this.mOpCount);
        stringBuilder.append(" mStateSeq=");
        stringBuilder.append(this.mStateSeq);
        stringBuilder.append(" mSavedIdx=");
        stringBuilder.append(this.mSavedIdx);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

