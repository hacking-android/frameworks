/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.app.assist.AssistStructure;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.SparseIntArray;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import java.util.LinkedList;

public final class FillContext
implements Parcelable {
    public static final Parcelable.Creator<FillContext> CREATOR = new Parcelable.Creator<FillContext>(){

        @Override
        public FillContext createFromParcel(Parcel parcel) {
            return new FillContext(parcel);
        }

        public FillContext[] newArray(int n) {
            return new FillContext[n];
        }
    };
    private final AutofillId mFocusedId;
    private final int mRequestId;
    private final AssistStructure mStructure;
    private ArrayMap<AutofillId, AssistStructure.ViewNode> mViewNodeLookupTable;

    public FillContext(int n, AssistStructure assistStructure, AutofillId autofillId) {
        this.mRequestId = n;
        this.mStructure = assistStructure;
        this.mFocusedId = autofillId;
    }

    private FillContext(Parcel parcel) {
        this(parcel.readInt(), (AssistStructure)parcel.readParcelable(null), (AutofillId)parcel.readParcelable(null));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public AssistStructure.ViewNode[] findViewNodesByAutofillIds(AutofillId[] arrautofillId) {
        int n;
        int n2;
        ArrayMap<AutofillId, AssistStructure.ViewNode> arrayMap;
        LinkedList<AssistStructure.ViewNode> linkedList = new LinkedList<AssistStructure.ViewNode>();
        AssistStructure.ViewNode[] arrviewNode = new AssistStructure.ViewNode[arrautofillId.length];
        SparseIntArray sparseIntArray = new SparseIntArray(arrautofillId.length);
        for (n = 0; n < arrautofillId.length; ++n) {
            arrayMap = this.mViewNodeLookupTable;
            if (arrayMap != null) {
                n2 = arrayMap.indexOfKey(arrautofillId[n]);
                if (n2 >= 0) {
                    arrviewNode[n] = this.mViewNodeLookupTable.valueAt(n2);
                    continue;
                }
                sparseIntArray.put(n, 0);
                continue;
            }
            sparseIntArray.put(n, 0);
        }
        n2 = this.mStructure.getWindowNodeCount();
        for (n = 0; n < n2; ++n) {
            linkedList.add(this.mStructure.getWindowNodeAt(n).getRootViewNode());
        }
        while (sparseIntArray.size() > 0 && !linkedList.isEmpty()) {
            arrayMap = (AssistStructure.ViewNode)linkedList.removeFirst();
            for (n = 0; n < sparseIntArray.size(); ++n) {
                n2 = sparseIntArray.keyAt(n);
                AutofillId autofillId = arrautofillId[n2];
                if (!autofillId.equals(((AssistStructure.ViewNode)((Object)arrayMap)).getAutofillId())) continue;
                arrviewNode[n2] = arrayMap;
                if (this.mViewNodeLookupTable == null) {
                    this.mViewNodeLookupTable = new ArrayMap(arrautofillId.length);
                }
                this.mViewNodeLookupTable.put(autofillId, (AssistStructure.ViewNode)((Object)arrayMap));
                sparseIntArray.removeAt(n);
                break;
            }
            for (n = 0; n < ((AssistStructure.ViewNode)((Object)arrayMap)).getChildCount(); ++n) {
                linkedList.addLast(((AssistStructure.ViewNode)((Object)arrayMap)).getChildAt(n));
            }
        }
        for (n = 0; n < sparseIntArray.size(); ++n) {
            if (this.mViewNodeLookupTable == null) {
                this.mViewNodeLookupTable = new ArrayMap(sparseIntArray.size());
            }
            this.mViewNodeLookupTable.put(arrautofillId[sparseIntArray.keyAt(n)], null);
        }
        return arrviewNode;
    }

    public AutofillId getFocusedId() {
        return this.mFocusedId;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public AssistStructure getStructure() {
        return this.mStructure;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FillContext [reqId=");
        stringBuilder.append(this.mRequestId);
        stringBuilder.append(", focusedId=");
        stringBuilder.append(this.mFocusedId);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRequestId);
        parcel.writeParcelable(this.mStructure, n);
        parcel.writeParcelable(this.mFocusedId, n);
    }

}

