/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.-$
 *  android.view.-$$Lambda
 *  android.view.-$$Lambda$cZhmLzK8aetUdx4VlP9w5jR7En0
 */
package android.view;

import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseIntArray;
import android.view.-$;
import android.view.DisplayCutout;
import android.view.InsetsSource;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view._$$Lambda$cZhmLzK8aetUdx4VlP9w5jR7En0;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.function.Function;

public class InsetsState
implements Parcelable {
    public static final Parcelable.Creator<InsetsState> CREATOR = new Parcelable.Creator<InsetsState>(){

        @Override
        public InsetsState createFromParcel(Parcel parcel) {
            return new InsetsState(parcel);
        }

        public InsetsState[] newArray(int n) {
            return new InsetsState[n];
        }
    };
    static final int FIRST_TYPE = 0;
    static final int INSET_SIDE_BOTTOM = 3;
    static final int INSET_SIDE_LEFT = 0;
    static final int INSET_SIDE_RIGHT = 2;
    static final int INSET_SIDE_TOP = 1;
    static final int INSET_SIDE_UNKNWON = 4;
    static final int LAST_TYPE = 10;
    public static final int TYPE_BOTTOM_GESTURES = 5;
    public static final int TYPE_BOTTOM_TAPPABLE_ELEMENT = 9;
    public static final int TYPE_IME = 10;
    public static final int TYPE_LEFT_GESTURES = 6;
    public static final int TYPE_NAVIGATION_BAR = 1;
    public static final int TYPE_RIGHT_GESTURES = 7;
    public static final int TYPE_SHELF = 1;
    public static final int TYPE_SIDE_BAR_1 = 1;
    public static final int TYPE_SIDE_BAR_2 = 2;
    public static final int TYPE_SIDE_BAR_3 = 3;
    public static final int TYPE_TOP_BAR = 0;
    public static final int TYPE_TOP_GESTURES = 4;
    public static final int TYPE_TOP_TAPPABLE_ELEMENT = 8;
    private final Rect mDisplayFrame = new Rect();
    private final ArrayMap<Integer, InsetsSource> mSources = new ArrayMap();

    public InsetsState() {
    }

    public InsetsState(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public InsetsState(InsetsState insetsState) {
        this.set(insetsState);
    }

    public InsetsState(InsetsState insetsState, boolean bl) {
        this.set(insetsState, bl);
    }

    public static boolean getDefaultVisibility(int n) {
        if (n != 0 && n != 1 && n != 2 && n != 3) {
            return n != 10;
        }
        return true;
    }

    private int getInsetSide(Insets insets) {
        if (insets.left != 0) {
            return 0;
        }
        if (insets.top != 0) {
            return 1;
        }
        if (insets.right != 0) {
            return 2;
        }
        if (insets.bottom != 0) {
            return 3;
        }
        return 4;
    }

    private void processSource(InsetsSource insetsSource, Rect parcelable, boolean bl, Insets[] arrinsets, SparseIntArray sparseIntArray, boolean[] arrbl) {
        parcelable = insetsSource.calculateInsets((Rect)parcelable, bl);
        int n = InsetsState.toPublicType(insetsSource.getType());
        this.processSourceAsPublicType(insetsSource, arrinsets, sparseIntArray, arrbl, (Insets)parcelable, n);
        if (n == 16) {
            this.processSourceAsPublicType(insetsSource, arrinsets, sparseIntArray, arrbl, (Insets)parcelable, 8);
        }
    }

    private void processSourceAsPublicType(InsetsSource insetsSource, Insets[] arrinsets, SparseIntArray sparseIntArray, boolean[] arrbl, Insets insets, int n) {
        Insets insets2 = arrinsets[n = WindowInsets.Type.indexOf(n)];
        arrinsets[n] = insets2 == null ? insets : Insets.max(insets2, insets);
        if (arrbl != null) {
            arrbl[n] = insetsSource.isVisible();
        }
        if (sparseIntArray != null && !Insets.NONE.equals(insets) && this.getInsetSide(insets) != 4) {
            sparseIntArray.put(insetsSource.getType(), this.getInsetSide(insets));
        }
    }

    public static ArraySet<Integer> toInternalType(int n) {
        ArraySet<Integer> arraySet = new ArraySet<Integer>();
        if ((n & 1) != 0) {
            arraySet.add(0);
        }
        if ((n & 4) != 0) {
            arraySet.add(1);
            arraySet.add(2);
            arraySet.add(3);
        }
        if ((n & 2) != 0) {
            arraySet.add(10);
        }
        return arraySet;
    }

    static int toPublicType(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown type: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 10: {
                return 2;
            }
            case 8: 
            case 9: {
                return 32;
            }
            case 6: 
            case 7: {
                return 8;
            }
            case 4: 
            case 5: {
                return 16;
            }
            case 1: 
            case 2: 
            case 3: {
                return 4;
            }
            case 0: 
        }
        return 1;
    }

    public static String typeToString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("TYPE_UNKNOWN_");
                stringBuilder.append(n);
                return stringBuilder.toString();
            }
            case 9: {
                return "TYPE_BOTTOM_TAPPABLE_ELEMENT";
            }
            case 8: {
                return "TYPE_TOP_TAPPABLE_ELEMENT";
            }
            case 7: {
                return "TYPE_RIGHT_GESTURES";
            }
            case 6: {
                return "TYPE_LEFT_GESTURES";
            }
            case 5: {
                return "TYPE_BOTTOM_GESTURES";
            }
            case 4: {
                return "TYPE_TOP_GESTURES";
            }
            case 3: {
                return "TYPE_SIDE_BAR_3";
            }
            case 2: {
                return "TYPE_SIDE_BAR_2";
            }
            case 1: {
                return "TYPE_SIDE_BAR_1";
            }
            case 0: 
        }
        return "TYPE_TOP_BAR";
    }

    public void addSource(InsetsSource insetsSource) {
        this.mSources.put(insetsSource.getType(), insetsSource);
    }

    public WindowInsets calculateInsets(Rect rect, boolean bl, boolean bl2, DisplayCutout displayCutout, Rect parcelable, Rect rect2, int n, SparseIntArray sparseIntArray) {
        Insets[] arrinsets = new Insets[7];
        Insets[] arrinsets2 = new Insets[7];
        boolean[] arrbl = new boolean[7];
        Rect rect3 = new Rect(rect);
        rect = new Rect(rect);
        if (ViewRootImpl.sNewInsetsMode != 2 && parcelable != null && rect2 != null) {
            WindowInsets.assignCompatInsets(arrinsets, (Rect)parcelable);
            WindowInsets.assignCompatInsets(arrinsets2, rect2);
        }
        for (int i = 0; i <= 10; ++i) {
            parcelable = this.mSources.get(i);
            if (parcelable == null) continue;
            int n2 = ViewRootImpl.sNewInsetsMode;
            boolean bl3 = true;
            n2 = n2 != 2 && (i == 0 || i == 1) ? 1 : 0;
            boolean bl4 = ((InsetsSource)parcelable).getType() == 10 && (n & 16) == 0;
            if (ViewRootImpl.sNewInsetsMode != 0 || (InsetsState.toPublicType(i) & WindowInsets.Type.compatSystemInsets()) == 0) {
                bl3 = false;
            }
            if (n2 == 0 && !bl4 && !bl3) {
                this.processSource((InsetsSource)parcelable, rect3, false, arrinsets, sparseIntArray, arrbl);
                if (((InsetsSource)parcelable).getType() == 10) continue;
                this.processSource((InsetsSource)parcelable, rect, true, arrinsets2, null, null);
                continue;
            }
            arrbl[WindowInsets.Type.indexOf((int)InsetsState.toPublicType((int)i))] = ((InsetsSource)parcelable).isVisible();
        }
        return new WindowInsets(arrinsets, arrinsets2, arrbl, bl, bl2, displayCutout);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(String string2, PrintWriter printWriter) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("InsetsState");
        printWriter.println(((StringBuilder)object).toString());
        for (int i = this.mSources.size() - 1; i >= 0; --i) {
            object = this.mSources.valueAt(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("  ");
            ((InsetsSource)object).dump(stringBuilder.toString(), printWriter);
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            InsetsState insetsState = (InsetsState)object;
            if (!this.mDisplayFrame.equals(insetsState.mDisplayFrame)) {
                return false;
            }
            if (this.mSources.size() != insetsState.mSources.size()) {
                return false;
            }
            for (int i = this.mSources.size() - 1; i >= 0; --i) {
                InsetsSource insetsSource = this.mSources.valueAt(i);
                object = insetsState.mSources.get(insetsSource.getType());
                if (object == null) {
                    return false;
                }
                if (((InsetsSource)object).equals(insetsSource)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public Rect getDisplayFrame() {
        return this.mDisplayFrame;
    }

    public InsetsSource getSource(int n) {
        return this.mSources.computeIfAbsent(n, (Function<Integer, InsetsSource>)_$$Lambda$cZhmLzK8aetUdx4VlP9w5jR7En0.INSTANCE);
    }

    public int getSourcesCount() {
        return this.mSources.size();
    }

    public int hashCode() {
        return Objects.hash(this.mDisplayFrame, this.mSources);
    }

    public void readFromParcel(Parcel parcel) {
        this.mSources.clear();
        this.mDisplayFrame.set((Rect)parcel.readParcelable(null));
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            InsetsSource insetsSource = (InsetsSource)parcel.readParcelable(null);
            this.mSources.put(insetsSource.getType(), insetsSource);
        }
    }

    public void removeSource(int n) {
        this.mSources.remove(n);
    }

    public void set(InsetsState insetsState) {
        this.set(insetsState, false);
    }

    public void set(InsetsState insetsState, boolean bl) {
        this.mDisplayFrame.set(insetsState.mDisplayFrame);
        this.mSources.clear();
        if (bl) {
            for (int i = 0; i < insetsState.mSources.size(); ++i) {
                InsetsSource insetsSource = insetsState.mSources.valueAt(i);
                this.mSources.put(insetsSource.getType(), new InsetsSource(insetsSource));
            }
        } else {
            this.mSources.putAll(insetsState.mSources);
        }
    }

    public void setDisplayFrame(Rect rect) {
        this.mDisplayFrame.set(rect);
    }

    public InsetsSource sourceAt(int n) {
        return this.mSources.valueAt(n);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mDisplayFrame, n);
        parcel.writeInt(this.mSources.size());
        for (int i = 0; i < this.mSources.size(); ++i) {
            parcel.writeParcelable(this.mSources.valueAt(i), n);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InsetSide {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InternalInsetType {
    }

}

