/*
 * Decompiled with CFR 0.145.
 */
package android.app.assist;

import android.annotation.SystemApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PooledStringReader;
import android.os.PooledStringWriter;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewStructure;
import android.view.WindowManagerGlobal;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssistStructure
implements Parcelable {
    public static final Parcelable.Creator<AssistStructure> CREATOR = new Parcelable.Creator<AssistStructure>(){

        @Override
        public AssistStructure createFromParcel(Parcel parcel) {
            return new AssistStructure(parcel);
        }

        public AssistStructure[] newArray(int n) {
            return new AssistStructure[n];
        }
    };
    private static final boolean DEBUG_PARCEL = false;
    private static final boolean DEBUG_PARCEL_CHILDREN = false;
    private static final boolean DEBUG_PARCEL_TREE = false;
    private static final String DESCRIPTOR = "android.app.AssistStructure";
    private static final String TAG = "AssistStructure";
    private static final int TRANSACTION_XFER = 2;
    private static final int VALIDATE_VIEW_TOKEN = 572662306;
    private static final int VALIDATE_WINDOW_TOKEN = 286331153;
    private long mAcquisitionEndTime;
    private long mAcquisitionStartTime;
    private ComponentName mActivityComponent;
    private int mAutofillFlags;
    private int mFlags;
    private boolean mHaveData;
    private boolean mIsHomeActivity;
    private final ArrayList<ViewNodeBuilder> mPendingAsyncChildren = new ArrayList();
    private IBinder mReceiveChannel;
    private boolean mSanitizeOnWrite;
    private SendChannel mSendChannel;
    private int mTaskId;
    private Rect mTmpRect = new Rect();
    private final ArrayList<WindowNode> mWindowNodes = new ArrayList();

    public AssistStructure() {
        this.mSanitizeOnWrite = false;
        this.mHaveData = true;
        this.mFlags = 0;
    }

    public AssistStructure(Activity object, boolean bl, int n) {
        this.mSanitizeOnWrite = false;
        this.mHaveData = true;
        this.mFlags = n;
        ArrayList<ViewRootImpl> arrayList = WindowManagerGlobal.getInstance().getRootViews(((Activity)object).getActivityToken());
        for (int i = 0; i < arrayList.size(); ++i) {
            object = arrayList.get(i);
            if (((ViewRootImpl)object).getView() == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Skipping window with dettached view: ");
                stringBuilder.append((Object)((ViewRootImpl)object).getTitle());
                Log.w(TAG, stringBuilder.toString());
                continue;
            }
            this.mWindowNodes.add(new WindowNode(this, (ViewRootImpl)object, bl, n));
        }
    }

    public AssistStructure(Parcel parcel) {
        boolean bl = false;
        this.mSanitizeOnWrite = false;
        this.mTaskId = parcel.readInt();
        this.mActivityComponent = ComponentName.readFromParcel(parcel);
        if (parcel.readInt() == 1) {
            bl = true;
        }
        this.mIsHomeActivity = bl;
        this.mReceiveChannel = parcel.readStrongBinder();
    }

    public void clearSendChannel() {
        SendChannel sendChannel = this.mSendChannel;
        if (sendChannel != null) {
            sendChannel.mAssistStructure = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    void dump(String string2, ViewNode viewNode, boolean bl) {
        float f;
        Object object;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append("View [");
        ((StringBuilder)object2).append(viewNode.getLeft());
        ((StringBuilder)object2).append(",");
        ((StringBuilder)object2).append(viewNode.getTop());
        ((StringBuilder)object2).append(" ");
        ((StringBuilder)object2).append(viewNode.getWidth());
        ((StringBuilder)object2).append("x");
        ((StringBuilder)object2).append(viewNode.getHeight());
        ((StringBuilder)object2).append("] ");
        ((StringBuilder)object2).append(viewNode.getClassName());
        Log.i(TAG, ((StringBuilder)object2).toString());
        int n = viewNode.getId();
        if (n != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  ID: #");
            ((StringBuilder)object).append(Integer.toHexString(n));
            String string3 = viewNode.getIdEntry();
            if (string3 != null) {
                object2 = viewNode.getIdType();
                String string4 = viewNode.getIdPackage();
                ((StringBuilder)object).append(" ");
                ((StringBuilder)object).append(string4);
                ((StringBuilder)object).append(":");
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(string3);
            }
            Log.i(TAG, ((StringBuilder)object).toString());
        }
        n = viewNode.getScrollX();
        int n2 = viewNode.getScrollY();
        if (n != 0 || n2 != 0) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Scroll: ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(",");
            ((StringBuilder)object2).append(n2);
            Log.i(TAG, ((StringBuilder)object2).toString());
        }
        if ((object2 = viewNode.getTransformation()) != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  Transformation: ");
            ((StringBuilder)object).append(object2);
            Log.i(TAG, ((StringBuilder)object).toString());
        }
        if ((f = viewNode.getElevation()) != 0.0f) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Elevation: ");
            ((StringBuilder)object2).append(f);
            Log.i(TAG, ((StringBuilder)object2).toString());
        }
        if (viewNode.getAlpha() != 0.0f) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Alpha: ");
            ((StringBuilder)object2).append(f);
            Log.i(TAG, ((StringBuilder)object2).toString());
        }
        if ((object2 = viewNode.getContentDescription()) != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  Content description: ");
            ((StringBuilder)object).append(object2);
            Log.i(TAG, ((StringBuilder)object).toString());
        }
        if ((object = viewNode.getText()) != null) {
            if (!viewNode.isSanitized() && !bl) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("REDACTED[");
                ((StringBuilder)object2).append(object.length());
                ((StringBuilder)object2).append(" chars]");
                object2 = ((StringBuilder)object2).toString();
            } else {
                object2 = object.toString();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  Text (sel ");
            ((StringBuilder)object).append(viewNode.getTextSelectionStart());
            ((StringBuilder)object).append("-");
            ((StringBuilder)object).append(viewNode.getTextSelectionEnd());
            ((StringBuilder)object).append("): ");
            ((StringBuilder)object).append((String)object2);
            Log.i(TAG, ((StringBuilder)object).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Text size: ");
            ((StringBuilder)object2).append(viewNode.getTextSize());
            ((StringBuilder)object2).append(" , style: #");
            ((StringBuilder)object2).append(viewNode.getTextStyle());
            Log.i(TAG, ((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Text color fg: #");
            ((StringBuilder)object2).append(Integer.toHexString(viewNode.getTextColor()));
            ((StringBuilder)object2).append(", bg: #");
            ((StringBuilder)object2).append(Integer.toHexString(viewNode.getTextBackgroundColor()));
            Log.i(TAG, ((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Input type: ");
            ((StringBuilder)object2).append(viewNode.getInputType());
            Log.i(TAG, ((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Resource id: ");
            ((StringBuilder)object2).append(viewNode.getTextIdEntry());
            Log.i(TAG, ((StringBuilder)object2).toString());
        }
        if ((object = viewNode.getWebDomain()) != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Web domain: ");
            ((StringBuilder)object2).append((String)object);
            Log.i(TAG, ((StringBuilder)object2).toString());
        }
        if ((object2 = viewNode.getHtmlInfo()) != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  HtmlInfo: tag=");
            ((StringBuilder)object).append(((ViewStructure.HtmlInfo)object2).getTag());
            ((StringBuilder)object).append(", attr=");
            ((StringBuilder)object).append(((ViewStructure.HtmlInfo)object2).getAttributes());
            Log.i(TAG, ((StringBuilder)object).toString());
        }
        if ((object = viewNode.getLocaleList()) != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  LocaleList: ");
            ((StringBuilder)object2).append(object);
            Log.i(TAG, ((StringBuilder)object2).toString());
        }
        if ((object2 = viewNode.getHint()) != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  Hint: ");
            ((StringBuilder)object).append((String)object2);
            Log.i(TAG, ((StringBuilder)object).toString());
        }
        if ((object2 = viewNode.getExtras()) != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  Extras: ");
            ((StringBuilder)object).append(object2);
            Log.i(TAG, ((StringBuilder)object).toString());
        }
        if (viewNode.isAssistBlocked()) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  BLOCKED");
            Log.i(TAG, ((StringBuilder)object2).toString());
        }
        if ((object = viewNode.getAutofillId()) == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(" NO autofill ID");
            Log.i(TAG, ((StringBuilder)object2).toString());
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Autofill info: id= ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(", type=");
            ((StringBuilder)object2).append(viewNode.getAutofillType());
            ((StringBuilder)object2).append(", options=");
            ((StringBuilder)object2).append(Arrays.toString(viewNode.getAutofillOptions()));
            ((StringBuilder)object2).append(", hints=");
            ((StringBuilder)object2).append(Arrays.toString(viewNode.getAutofillHints()));
            ((StringBuilder)object2).append(", value=");
            ((StringBuilder)object2).append(viewNode.getAutofillValue());
            ((StringBuilder)object2).append(", sanitized=");
            ((StringBuilder)object2).append(viewNode.isSanitized());
            ((StringBuilder)object2).append(", important=");
            ((StringBuilder)object2).append(viewNode.getImportantForAutofill());
            Log.i(TAG, ((StringBuilder)object2).toString());
        }
        n2 = viewNode.getChildCount();
        if (n2 > 0) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Children:");
            Log.i(TAG, ((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("    ");
            string2 = ((StringBuilder)object2).toString();
            for (n = 0; n < n2; ++n) {
                this.dump(string2, viewNode.getChildAt(n), bl);
            }
        }
    }

    public void dump(boolean bl) {
        if (this.mActivityComponent == null) {
            Log.i(TAG, "dump(): calling ensureData() first");
            this.ensureData();
        }
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Task id: ");
        ((StringBuilder)object).append(this.mTaskId);
        Log.i(TAG, ((StringBuilder)object).toString());
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("Activity: ");
        object = this.mActivityComponent;
        object = object != null ? ((ComponentName)object).flattenToShortString() : null;
        ((StringBuilder)object2).append((String)object);
        Log.i(TAG, ((StringBuilder)object2).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("Sanitize on write: ");
        ((StringBuilder)object).append(this.mSanitizeOnWrite);
        Log.i(TAG, ((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("Flags: ");
        ((StringBuilder)object).append(this.mFlags);
        Log.i(TAG, ((StringBuilder)object).toString());
        int n = this.getWindowNodeCount();
        for (int i = 0; i < n; ++i) {
            object2 = this.getWindowNodeAt(i);
            object = new StringBuilder();
            ((StringBuilder)object).append("Window #");
            ((StringBuilder)object).append(i);
            ((StringBuilder)object).append(" [");
            ((StringBuilder)object).append(((WindowNode)object2).getLeft());
            ((StringBuilder)object).append(",");
            ((StringBuilder)object).append(((WindowNode)object2).getTop());
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(((WindowNode)object2).getWidth());
            ((StringBuilder)object).append("x");
            ((StringBuilder)object).append(((WindowNode)object2).getHeight());
            ((StringBuilder)object).append("] ");
            ((StringBuilder)object).append((Object)((WindowNode)object2).getTitle());
            Log.i(TAG, ((StringBuilder)object).toString());
            this.dump("  ", ((WindowNode)object2).getRootViewNode(), bl);
        }
    }

    public void ensureData() {
        if (this.mHaveData) {
            return;
        }
        this.mHaveData = true;
        new ParcelTransferReader(this.mReceiveChannel).go();
    }

    public void ensureDataForAutofill() {
        if (this.mHaveData) {
            return;
        }
        this.mHaveData = true;
        Binder.allowBlocking(this.mReceiveChannel);
        try {
            ParcelTransferReader parcelTransferReader = new ParcelTransferReader(this.mReceiveChannel);
            parcelTransferReader.go();
            return;
        }
        finally {
            Binder.defaultBlocking(this.mReceiveChannel);
        }
    }

    public long getAcquisitionEndTime() {
        this.ensureData();
        return this.mAcquisitionEndTime;
    }

    public long getAcquisitionStartTime() {
        this.ensureData();
        return this.mAcquisitionStartTime;
    }

    public ComponentName getActivityComponent() {
        return this.mActivityComponent;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public WindowNode getWindowNodeAt(int n) {
        this.ensureData();
        return this.mWindowNodes.get(n);
    }

    public int getWindowNodeCount() {
        this.ensureData();
        return this.mWindowNodes.size();
    }

    public boolean isHomeActivity() {
        return this.mIsHomeActivity;
    }

    public void sanitizeForParceling(boolean bl) {
        this.mSanitizeOnWrite = bl;
    }

    public void setAcquisitionEndTime(long l) {
        this.mAcquisitionEndTime = l;
    }

    public void setAcquisitionStartTime(long l) {
        this.mAcquisitionStartTime = l;
    }

    public void setActivityComponent(ComponentName componentName) {
        this.mActivityComponent = componentName;
    }

    public void setHomeActivity(boolean bl) {
        this.mIsHomeActivity = bl;
    }

    public void setTaskId(int n) {
        this.mTaskId = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean waitForReady() {
        boolean bl = false;
        synchronized (this) {
            long l;
            long l2 = SystemClock.uptimeMillis() + 5000L;
            while (this.mPendingAsyncChildren.size() > 0 && (l = SystemClock.uptimeMillis()) < l2) {
                try {
                    this.wait(l2 - l);
                }
                catch (InterruptedException interruptedException) {}
            }
            if (this.mPendingAsyncChildren.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Skipping assist structure, waiting too long for async children (have ");
                stringBuilder.append(this.mPendingAsyncChildren.size());
                stringBuilder.append(" remaining");
                Log.w(TAG, stringBuilder.toString());
                return false;
            }
        }
        if (bl) return false;
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mTaskId);
        ComponentName.writeToParcel(this.mActivityComponent, parcel);
        parcel.writeInt((int)this.mIsHomeActivity);
        if (this.mHaveData) {
            if (this.mSendChannel == null) {
                this.mSendChannel = new SendChannel(this);
            }
            parcel.writeStrongBinder(this.mSendChannel);
        } else {
            parcel.writeStrongBinder(this.mReceiveChannel);
        }
    }

    public static class AutofillOverlay {
        public boolean focused;
        public AutofillValue value;
    }

    private static final class HtmlInfoNode
    extends ViewStructure.HtmlInfo
    implements Parcelable {
        public static final Parcelable.Creator<HtmlInfoNode> CREATOR = new Parcelable.Creator<HtmlInfoNode>(){

            @Override
            public HtmlInfoNode createFromParcel(Parcel object) {
                HtmlInfoNodeBuilder htmlInfoNodeBuilder = new HtmlInfoNodeBuilder(((Parcel)object).readString());
                String[] arrstring = ((Parcel)object).readStringArray();
                String[] arrstring2 = ((Parcel)object).readStringArray();
                if (arrstring != null && arrstring2 != null) {
                    if (arrstring.length != arrstring2.length) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("HtmlInfo attributes mismatch: names=");
                        ((StringBuilder)object).append(arrstring.length);
                        ((StringBuilder)object).append(", values=");
                        ((StringBuilder)object).append(arrstring2.length);
                        Log.w(AssistStructure.TAG, ((StringBuilder)object).toString());
                    } else {
                        for (int i = 0; i < arrstring.length; ++i) {
                            htmlInfoNodeBuilder.addAttribute(arrstring[i], arrstring2[i]);
                        }
                    }
                }
                return htmlInfoNodeBuilder.build();
            }

            public HtmlInfoNode[] newArray(int n) {
                return new HtmlInfoNode[n];
            }
        };
        private ArrayList<Pair<String, String>> mAttributes;
        private final String[] mNames;
        private final String mTag;
        private final String[] mValues;

        private HtmlInfoNode(HtmlInfoNodeBuilder htmlInfoNodeBuilder) {
            this.mTag = htmlInfoNodeBuilder.mTag;
            if (htmlInfoNodeBuilder.mNames == null) {
                this.mNames = null;
                this.mValues = null;
            } else {
                this.mNames = new String[htmlInfoNodeBuilder.mNames.size()];
                this.mValues = new String[htmlInfoNodeBuilder.mValues.size()];
                htmlInfoNodeBuilder.mNames.toArray(this.mNames);
                htmlInfoNodeBuilder.mValues.toArray(this.mValues);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public List<Pair<String, String>> getAttributes() {
            Object object;
            if (this.mAttributes == null && (object = this.mNames) != null) {
                this.mAttributes = new ArrayList(((String[])object).length);
                for (int i = 0; i < ((String[])(object = this.mNames)).length; ++i) {
                    object = new Pair<String, String>(object[i], this.mValues[i]);
                    this.mAttributes.add(i, (Pair<String, String>)object);
                }
            }
            return this.mAttributes;
        }

        @Override
        public String getTag() {
            return this.mTag;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mTag);
            parcel.writeStringArray(this.mNames);
            parcel.writeStringArray(this.mValues);
        }

    }

    private static final class HtmlInfoNodeBuilder
    extends ViewStructure.HtmlInfo.Builder {
        private ArrayList<String> mNames;
        private final String mTag;
        private ArrayList<String> mValues;

        HtmlInfoNodeBuilder(String string2) {
            this.mTag = string2;
        }

        @Override
        public ViewStructure.HtmlInfo.Builder addAttribute(String string2, String string3) {
            if (this.mNames == null) {
                this.mNames = new ArrayList();
                this.mValues = new ArrayList();
            }
            this.mNames.add(string2);
            this.mValues.add(string3);
            return this;
        }

        @Override
        public HtmlInfoNode build() {
            return new HtmlInfoNode(this);
        }
    }

    final class ParcelTransferReader {
        private final IBinder mChannel;
        private Parcel mCurParcel;
        int mNumReadViews;
        int mNumReadWindows;
        PooledStringReader mStringReader;
        final float[] mTmpMatrix = new float[9];
        private IBinder mTransferToken;

        ParcelTransferReader(IBinder iBinder) {
            this.mChannel = iBinder;
        }

        private void fetchData() {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(AssistStructure.DESCRIPTOR);
                parcel.writeStrongBinder(this.mTransferToken);
                if (this.mCurParcel != null) {
                    this.mCurParcel.recycle();
                }
                this.mCurParcel = Parcel.obtain();
                try {
                    this.mChannel.transact(2, parcel, this.mCurParcel, 0);
                    this.mNumReadViews = 0;
                    this.mNumReadWindows = 0;
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.w(AssistStructure.TAG, "Failure reading AssistStructure data", remoteException);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failure reading AssistStructure data: ");
                    stringBuilder.append(remoteException);
                    IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                    throw illegalStateException;
                }
            }
            finally {
                parcel.recycle();
            }
        }

        void go() {
            this.fetchData();
            AssistStructure.this.mFlags = this.mCurParcel.readInt();
            AssistStructure.this.mAutofillFlags = this.mCurParcel.readInt();
            AssistStructure.this.mAcquisitionStartTime = this.mCurParcel.readLong();
            AssistStructure.this.mAcquisitionEndTime = this.mCurParcel.readLong();
            int n = this.mCurParcel.readInt();
            if (n > 0) {
                this.mStringReader = new PooledStringReader(this.mCurParcel);
                for (int i = 0; i < n; ++i) {
                    AssistStructure.this.mWindowNodes.add(new WindowNode(this));
                }
            }
            this.mCurParcel.recycle();
            this.mCurParcel = null;
        }

        Parcel readParcel(int n, int n2) {
            n2 = this.mCurParcel.readInt();
            if (n2 != 0) {
                if (n2 == n) {
                    return this.mCurParcel;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Got token ");
                stringBuilder.append(Integer.toHexString(n2));
                stringBuilder.append(", expected token ");
                stringBuilder.append(Integer.toHexString(n));
                throw new BadParcelableException(stringBuilder.toString());
            }
            this.mTransferToken = this.mCurParcel.readStrongBinder();
            if (this.mTransferToken != null) {
                this.fetchData();
                this.mStringReader = new PooledStringReader(this.mCurParcel);
                this.mCurParcel.readInt();
                return this.mCurParcel;
            }
            throw new IllegalStateException("Reached end of partial data without transfer token");
        }
    }

    static final class ParcelTransferWriter
    extends Binder {
        ViewStackEntry mCurViewStackEntry;
        int mCurViewStackPos;
        int mCurWindow;
        int mNumWindows;
        int mNumWrittenViews;
        int mNumWrittenWindows;
        final boolean mSanitizeOnWrite;
        final float[] mTmpMatrix = new float[9];
        final ArrayList<ViewStackEntry> mViewStack = new ArrayList();
        final boolean mWriteStructure;

        ParcelTransferWriter(AssistStructure assistStructure, Parcel parcel) {
            int n;
            this.mSanitizeOnWrite = assistStructure.mSanitizeOnWrite;
            this.mWriteStructure = assistStructure.waitForReady();
            parcel.writeInt(assistStructure.mFlags);
            parcel.writeInt(assistStructure.mAutofillFlags);
            parcel.writeLong(assistStructure.mAcquisitionStartTime);
            parcel.writeLong(assistStructure.mAcquisitionEndTime);
            this.mNumWindows = assistStructure.mWindowNodes.size();
            if (this.mWriteStructure && (n = this.mNumWindows) > 0) {
                parcel.writeInt(n);
            } else {
                parcel.writeInt(0);
            }
        }

        void pushViewStackEntry(ViewNode viewNode, int n) {
            ViewStackEntry viewStackEntry;
            if (n >= this.mViewStack.size()) {
                viewStackEntry = new ViewStackEntry();
                this.mViewStack.add(viewStackEntry);
            } else {
                viewStackEntry = this.mViewStack.get(n);
            }
            viewStackEntry.node = viewNode;
            viewStackEntry.numChildren = viewNode.getChildCount();
            viewStackEntry.curChild = 0;
            this.mCurViewStackEntry = viewStackEntry;
        }

        boolean writeNextEntryToParcel(AssistStructure object, Parcel parcel, PooledStringWriter pooledStringWriter) {
            int n;
            ViewStackEntry viewStackEntry = this.mCurViewStackEntry;
            if (viewStackEntry != null) {
                if (viewStackEntry.curChild < this.mCurViewStackEntry.numChildren) {
                    object = this.mCurViewStackEntry.node.mChildren[this.mCurViewStackEntry.curChild];
                    viewStackEntry = this.mCurViewStackEntry;
                    ++viewStackEntry.curChild;
                    this.writeView((ViewNode)object, parcel, pooledStringWriter, 1);
                    return true;
                }
                do {
                    int n2;
                    this.mCurViewStackPos = n2 = this.mCurViewStackPos - 1;
                    if (n2 < 0) {
                        this.mCurViewStackEntry = null;
                        break;
                    }
                    this.mCurViewStackEntry = this.mViewStack.get(n2);
                } while (this.mCurViewStackEntry.curChild >= this.mCurViewStackEntry.numChildren);
                return true;
            }
            if ((n = this.mCurWindow++) < this.mNumWindows) {
                object = (WindowNode)((AssistStructure)object).mWindowNodes.get(n);
                parcel.writeInt(286331153);
                ((WindowNode)object).writeSelfToParcel(parcel, pooledStringWriter, this.mTmpMatrix);
                ++this.mNumWrittenWindows;
                object = ((WindowNode)object).mRoot;
                this.mCurViewStackPos = 0;
                this.writeView((ViewNode)object, parcel, pooledStringWriter, 0);
                return true;
            }
            return false;
        }

        void writeToParcel(AssistStructure object, Parcel parcel) {
            int n = parcel.dataPosition();
            this.mNumWrittenWindows = 0;
            this.mNumWrittenViews = 0;
            boolean bl = this.writeToParcelInner((AssistStructure)object, parcel);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Flattened ");
            object = bl ? "partial" : "final";
            stringBuilder.append((String)object);
            stringBuilder.append(" assist data: ");
            stringBuilder.append(parcel.dataPosition() - n);
            stringBuilder.append(" bytes, containing ");
            stringBuilder.append(this.mNumWrittenWindows);
            stringBuilder.append(" windows, ");
            stringBuilder.append(this.mNumWrittenViews);
            stringBuilder.append(" views");
            Log.i(AssistStructure.TAG, stringBuilder.toString());
        }

        boolean writeToParcelInner(AssistStructure assistStructure, Parcel parcel) {
            if (this.mNumWindows == 0) {
                return false;
            }
            PooledStringWriter pooledStringWriter = new PooledStringWriter(parcel);
            while (this.writeNextEntryToParcel(assistStructure, parcel, pooledStringWriter)) {
                if (parcel.dataSize() <= 65536) continue;
                parcel.writeInt(0);
                parcel.writeStrongBinder(this);
                pooledStringWriter.finish();
                return true;
            }
            pooledStringWriter.finish();
            this.mViewStack.clear();
            return false;
        }

        void writeView(ViewNode viewNode, Parcel parcel, PooledStringWriter pooledStringWriter, int n) {
            parcel.writeInt(572662306);
            n = viewNode.writeSelfToParcel(parcel, pooledStringWriter, this.mSanitizeOnWrite, this.mTmpMatrix);
            ++this.mNumWrittenViews;
            if ((1048576 & n) != 0) {
                parcel.writeInt(viewNode.mChildren.length);
                this.mCurViewStackPos = n = this.mCurViewStackPos + 1;
                this.pushViewStackEntry(viewNode, n);
            }
        }
    }

    static final class SendChannel
    extends Binder {
        volatile AssistStructure mAssistStructure;

        SendChannel(AssistStructure assistStructure) {
            this.mAssistStructure = assistStructure;
        }

        @Override
        protected boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n == 2) {
                AssistStructure assistStructure = this.mAssistStructure;
                if (assistStructure == null) {
                    return true;
                }
                ((Parcel)object).enforceInterface(AssistStructure.DESCRIPTOR);
                object = ((Parcel)object).readStrongBinder();
                if (object != null) {
                    if (object instanceof ParcelTransferWriter) {
                        ((ParcelTransferWriter)object).writeToParcel(assistStructure, (Parcel)object2);
                        return true;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Caller supplied bad token type: ");
                    ((StringBuilder)object2).append(object);
                    Log.w(AssistStructure.TAG, ((StringBuilder)object2).toString());
                    return true;
                }
                new ParcelTransferWriter(assistStructure, (Parcel)object2).writeToParcel(assistStructure, (Parcel)object2);
                return true;
            }
            return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
        }
    }

    public static class ViewNode {
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_HINTS = 16;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_OPTIONS = 32;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_SESSION_ID = 2048;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_TYPE = 8;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VALUE = 4;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VIEW_ID = 1;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VIRTUAL_VIEW_ID = 2;
        static final int AUTOFILL_FLAGS_HAS_HTML_INFO = 64;
        static final int AUTOFILL_FLAGS_HAS_MAX_TEXT_EMS = 512;
        static final int AUTOFILL_FLAGS_HAS_MAX_TEXT_LENGTH = 1024;
        static final int AUTOFILL_FLAGS_HAS_MIN_TEXT_EMS = 256;
        static final int AUTOFILL_FLAGS_HAS_TEXT_ID_ENTRY = 128;
        static final int FLAGS_ACCESSIBILITY_FOCUSED = 4096;
        static final int FLAGS_ACTIVATED = 8192;
        static final int FLAGS_ALL_CONTROL = -1048576;
        static final int FLAGS_ASSIST_BLOCKED = 128;
        static final int FLAGS_CHECKABLE = 256;
        static final int FLAGS_CHECKED = 512;
        static final int FLAGS_CLICKABLE = 1024;
        static final int FLAGS_CONTEXT_CLICKABLE = 16384;
        static final int FLAGS_DISABLED = 1;
        static final int FLAGS_FOCUSABLE = 16;
        static final int FLAGS_FOCUSED = 32;
        static final int FLAGS_HAS_ALPHA = 536870912;
        static final int FLAGS_HAS_CHILDREN = 1048576;
        static final int FLAGS_HAS_COMPLEX_TEXT = 8388608;
        static final int FLAGS_HAS_CONTENT_DESCRIPTION = 33554432;
        static final int FLAGS_HAS_ELEVATION = 268435456;
        static final int FLAGS_HAS_EXTRAS = 4194304;
        static final int FLAGS_HAS_ID = 2097152;
        static final int FLAGS_HAS_INPUT_TYPE = 262144;
        static final int FLAGS_HAS_LARGE_COORDS = 67108864;
        static final int FLAGS_HAS_LOCALE_LIST = 65536;
        static final int FLAGS_HAS_MATRIX = 1073741824;
        static final int FLAGS_HAS_SCROLL = 134217728;
        static final int FLAGS_HAS_TEXT = 16777216;
        static final int FLAGS_HAS_URL = 524288;
        static final int FLAGS_LONG_CLICKABLE = 2048;
        static final int FLAGS_OPAQUE = 32768;
        static final int FLAGS_SELECTED = 64;
        static final int FLAGS_VISIBILITY_MASK = 12;
        public static final int TEXT_COLOR_UNDEFINED = 1;
        public static final int TEXT_STYLE_BOLD = 1;
        public static final int TEXT_STYLE_ITALIC = 2;
        public static final int TEXT_STYLE_STRIKE_THRU = 8;
        public static final int TEXT_STYLE_UNDERLINE = 4;
        float mAlpha;
        int mAutofillFlags;
        String[] mAutofillHints;
        AutofillId mAutofillId;
        CharSequence[] mAutofillOptions;
        AutofillOverlay mAutofillOverlay;
        int mAutofillType;
        AutofillValue mAutofillValue;
        ViewNode[] mChildren;
        String mClassName;
        CharSequence mContentDescription;
        float mElevation;
        Bundle mExtras;
        int mFlags;
        int mHeight;
        ViewStructure.HtmlInfo mHtmlInfo;
        int mId = -1;
        String mIdEntry;
        String mIdPackage;
        String mIdType;
        int mImportantForAutofill;
        int mInputType;
        LocaleList mLocaleList;
        Matrix mMatrix;
        int mMaxEms;
        int mMaxLength;
        int mMinEms;
        boolean mSanitized;
        int mScrollX;
        int mScrollY;
        ViewNodeText mText;
        String mTextIdEntry;
        String mWebDomain;
        String mWebScheme;
        int mWidth;
        int mX;
        int mY;

        @SystemApi
        public ViewNode() {
            this.mAutofillType = 0;
            this.mMinEms = -1;
            this.mMaxEms = -1;
            this.mMaxLength = -1;
            this.mAlpha = 1.0f;
        }

        ViewNode(ParcelTransferReader parcelTransferReader, int n) {
            boolean bl;
            boolean bl2 = false;
            this.mAutofillType = 0;
            this.mMinEms = -1;
            this.mMaxEms = -1;
            this.mMaxLength = -1;
            this.mAlpha = 1.0f;
            Parcel parcel = parcelTransferReader.readParcel(572662306, n);
            ++parcelTransferReader.mNumReadViews;
            PooledStringReader pooledStringReader = parcelTransferReader.mStringReader;
            this.mClassName = pooledStringReader.readString();
            int n2 = this.mFlags = parcel.readInt();
            int n3 = this.mAutofillFlags = parcel.readInt();
            if ((2097152 & n2) != 0) {
                this.mId = parcel.readInt();
                if (this.mId != -1) {
                    this.mIdEntry = pooledStringReader.readString();
                    if (this.mIdEntry != null) {
                        this.mIdType = pooledStringReader.readString();
                        this.mIdPackage = pooledStringReader.readString();
                    }
                }
            }
            if (n3 != 0) {
                bl = parcel.readInt() == 1;
                this.mSanitized = bl;
                this.mImportantForAutofill = parcel.readInt();
                if ((n3 & 1) != 0) {
                    int n4 = parcel.readInt();
                    this.mAutofillId = (n3 & 2) != 0 ? new AutofillId(n4, parcel.readInt()) : new AutofillId(n4);
                    if ((n3 & 2048) != 0) {
                        this.mAutofillId.setSessionId(parcel.readInt());
                    }
                }
                if ((n3 & 8) != 0) {
                    this.mAutofillType = parcel.readInt();
                }
                if ((n3 & 16) != 0) {
                    this.mAutofillHints = parcel.readStringArray();
                }
                if ((n3 & 4) != 0) {
                    this.mAutofillValue = (AutofillValue)parcel.readParcelable(null);
                }
                if ((n3 & 32) != 0) {
                    this.mAutofillOptions = parcel.readCharSequenceArray();
                }
                if ((n3 & 64) != 0) {
                    this.mHtmlInfo = (ViewStructure.HtmlInfo)parcel.readParcelable(null);
                }
                if ((n3 & 256) != 0) {
                    this.mMinEms = parcel.readInt();
                }
                if ((n3 & 512) != 0) {
                    this.mMaxEms = parcel.readInt();
                }
                if ((n3 & 1024) != 0) {
                    this.mMaxLength = parcel.readInt();
                }
                if ((n3 & 128) != 0) {
                    this.mTextIdEntry = pooledStringReader.readString();
                }
            }
            if ((67108864 & n2) != 0) {
                this.mX = parcel.readInt();
                this.mY = parcel.readInt();
                this.mWidth = parcel.readInt();
                this.mHeight = parcel.readInt();
            } else {
                n3 = parcel.readInt();
                this.mX = n3 & 32767;
                this.mY = n3 >> 16 & 32767;
                n3 = parcel.readInt();
                this.mWidth = n3 & 32767;
                this.mHeight = n3 >> 16 & 32767;
            }
            if ((134217728 & n2) != 0) {
                this.mScrollX = parcel.readInt();
                this.mScrollY = parcel.readInt();
            }
            if ((1073741824 & n2) != 0) {
                this.mMatrix = new Matrix();
                parcel.readFloatArray(parcelTransferReader.mTmpMatrix);
                this.mMatrix.setValues(parcelTransferReader.mTmpMatrix);
            }
            if ((268435456 & n2) != 0) {
                this.mElevation = parcel.readFloat();
            }
            if ((536870912 & n2) != 0) {
                this.mAlpha = parcel.readFloat();
            }
            if ((33554432 & n2) != 0) {
                this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            }
            if ((16777216 & n2) != 0) {
                bl = bl2;
                if ((8388608 & n2) == 0) {
                    bl = true;
                }
                this.mText = new ViewNodeText(parcel, bl);
            }
            if ((262144 & n2) != 0) {
                this.mInputType = parcel.readInt();
            }
            if ((524288 & n2) != 0) {
                this.mWebScheme = parcel.readString();
                this.mWebDomain = parcel.readString();
            }
            if ((65536 & n2) != 0) {
                this.mLocaleList = (LocaleList)parcel.readParcelable(null);
            }
            if ((4194304 & n2) != 0) {
                this.mExtras = parcel.readBundle();
            }
            if ((1048576 & n2) != 0) {
                n3 = parcel.readInt();
                this.mChildren = new ViewNode[n3];
                for (n2 = 0; n2 < n3; ++n2) {
                    this.mChildren[n2] = new ViewNode(parcelTransferReader, n + 1);
                }
            }
        }

        public float getAlpha() {
            return this.mAlpha;
        }

        public String[] getAutofillHints() {
            return this.mAutofillHints;
        }

        public AutofillId getAutofillId() {
            return this.mAutofillId;
        }

        public CharSequence[] getAutofillOptions() {
            return this.mAutofillOptions;
        }

        public int getAutofillType() {
            return this.mAutofillType;
        }

        public AutofillValue getAutofillValue() {
            return this.mAutofillValue;
        }

        public ViewNode getChildAt(int n) {
            return this.mChildren[n];
        }

        public int getChildCount() {
            ViewNode[] arrviewNode = this.mChildren;
            int n = arrviewNode != null ? arrviewNode.length : 0;
            return n;
        }

        public String getClassName() {
            return this.mClassName;
        }

        public CharSequence getContentDescription() {
            return this.mContentDescription;
        }

        public float getElevation() {
            return this.mElevation;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public String getHint() {
            Object object = this.mText;
            object = object != null ? ((ViewNodeText)object).mHint : null;
            return object;
        }

        public ViewStructure.HtmlInfo getHtmlInfo() {
            return this.mHtmlInfo;
        }

        public int getId() {
            return this.mId;
        }

        public String getIdEntry() {
            return this.mIdEntry;
        }

        public String getIdPackage() {
            return this.mIdPackage;
        }

        public String getIdType() {
            return this.mIdType;
        }

        public int getImportantForAutofill() {
            return this.mImportantForAutofill;
        }

        public int getInputType() {
            return this.mInputType;
        }

        public int getLeft() {
            return this.mX;
        }

        public LocaleList getLocaleList() {
            return this.mLocaleList;
        }

        public int getMaxTextEms() {
            return this.mMaxEms;
        }

        public int getMaxTextLength() {
            return this.mMaxLength;
        }

        public int getMinTextEms() {
            return this.mMinEms;
        }

        public int getScrollX() {
            return this.mScrollX;
        }

        public int getScrollY() {
            return this.mScrollY;
        }

        public CharSequence getText() {
            Object object = this.mText;
            object = object != null ? ((ViewNodeText)object).mText : null;
            return object;
        }

        public int getTextBackgroundColor() {
            ViewNodeText viewNodeText = this.mText;
            int n = viewNodeText != null ? viewNodeText.mTextBackgroundColor : 1;
            return n;
        }

        public int getTextColor() {
            ViewNodeText viewNodeText = this.mText;
            int n = viewNodeText != null ? viewNodeText.mTextColor : 1;
            return n;
        }

        public String getTextIdEntry() {
            return this.mTextIdEntry;
        }

        public int[] getTextLineBaselines() {
            Object object = this.mText;
            object = object != null ? object.mLineBaselines : null;
            return object;
        }

        public int[] getTextLineCharOffsets() {
            Object object = this.mText;
            object = object != null ? object.mLineCharOffsets : null;
            return object;
        }

        public int getTextSelectionEnd() {
            ViewNodeText viewNodeText = this.mText;
            int n = viewNodeText != null ? viewNodeText.mTextSelectionEnd : -1;
            return n;
        }

        public int getTextSelectionStart() {
            ViewNodeText viewNodeText = this.mText;
            int n = viewNodeText != null ? viewNodeText.mTextSelectionStart : -1;
            return n;
        }

        public float getTextSize() {
            ViewNodeText viewNodeText = this.mText;
            float f = viewNodeText != null ? viewNodeText.mTextSize : 0.0f;
            return f;
        }

        public int getTextStyle() {
            ViewNodeText viewNodeText = this.mText;
            int n = viewNodeText != null ? viewNodeText.mTextStyle : 0;
            return n;
        }

        public int getTop() {
            return this.mY;
        }

        public Matrix getTransformation() {
            return this.mMatrix;
        }

        public int getVisibility() {
            return this.mFlags & 12;
        }

        public String getWebDomain() {
            return this.mWebDomain;
        }

        public String getWebScheme() {
            return this.mWebScheme;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public boolean isAccessibilityFocused() {
            boolean bl = (this.mFlags & 4096) != 0;
            return bl;
        }

        public boolean isActivated() {
            boolean bl = (this.mFlags & 8192) != 0;
            return bl;
        }

        public boolean isAssistBlocked() {
            boolean bl = (this.mFlags & 128) != 0;
            return bl;
        }

        public boolean isCheckable() {
            boolean bl = (this.mFlags & 256) != 0;
            return bl;
        }

        public boolean isChecked() {
            boolean bl = (this.mFlags & 512) != 0;
            return bl;
        }

        public boolean isClickable() {
            boolean bl = (this.mFlags & 1024) != 0;
            return bl;
        }

        public boolean isContextClickable() {
            boolean bl = (this.mFlags & 16384) != 0;
            return bl;
        }

        public boolean isEnabled() {
            int n = this.mFlags;
            boolean bl = true;
            if ((n & 1) != 0) {
                bl = false;
            }
            return bl;
        }

        public boolean isFocusable() {
            boolean bl = (this.mFlags & 16) != 0;
            return bl;
        }

        public boolean isFocused() {
            boolean bl = (this.mFlags & 32) != 0;
            return bl;
        }

        public boolean isLongClickable() {
            boolean bl = (this.mFlags & 2048) != 0;
            return bl;
        }

        public boolean isOpaque() {
            boolean bl = (this.mFlags & 32768) != 0;
            return bl;
        }

        public boolean isSanitized() {
            return this.mSanitized;
        }

        public boolean isSelected() {
            boolean bl = (this.mFlags & 64) != 0;
            return bl;
        }

        public void setAutofillOverlay(AutofillOverlay autofillOverlay) {
            this.mAutofillOverlay = autofillOverlay;
        }

        public void setWebDomain(String object) {
            if (object == null) {
                return;
            }
            if ((object = Uri.parse((String)object)) == null) {
                Log.w(AssistStructure.TAG, "Failed to parse web domain");
                return;
            }
            this.mWebScheme = ((Uri)object).getScheme();
            this.mWebDomain = ((Uri)object).getHost();
        }

        public void updateAutofillValue(AutofillValue autofillValue) {
            this.mAutofillValue = autofillValue;
            if (autofillValue.isText()) {
                if (this.mText == null) {
                    this.mText = new ViewNodeText();
                }
                this.mText.mText = autofillValue.getTextValue();
            }
        }

        int writeSelfToParcel(Parcel parcel, PooledStringWriter object, boolean bl, float[] arrf) {
            int n;
            int n2;
            Object object2;
            boolean bl2;
            int n3;
            int n4;
            block70 : {
                block71 : {
                    int n5;
                    block69 : {
                        block68 : {
                            block67 : {
                                block66 : {
                                    block65 : {
                                        block64 : {
                                            bl2 = true;
                                            n3 = this.mFlags & 1048575;
                                            n5 = 0;
                                            n2 = n3;
                                            if (this.mId != -1) {
                                                n2 = n3 | 2097152;
                                            }
                                            if ((this.mX & -32768) != 0 || (this.mY & -32768) != 0) break block64;
                                            n = (this.mWidth & -32768) != 0 ? 1 : 0;
                                            n4 = (this.mHeight & -32768) != 0 ? 1 : 0;
                                            n3 = n2;
                                            if (!(n | n4)) break block65;
                                        }
                                        n3 = n2 | 67108864;
                                    }
                                    if (this.mScrollX != 0) break block66;
                                    n2 = n3;
                                    if (this.mScrollY == 0) break block67;
                                }
                                n2 = n3 | 134217728;
                            }
                            n3 = n2;
                            if (this.mMatrix != null) {
                                n3 = n2 | 1073741824;
                            }
                            n2 = n3;
                            if (this.mElevation != 0.0f) {
                                n2 = n3 | 268435456;
                            }
                            n3 = n2;
                            if (this.mAlpha != 1.0f) {
                                n3 = n2 | 536870912;
                            }
                            n = n3;
                            if (this.mContentDescription != null) {
                                n = n3 | 33554432;
                            }
                            object2 = this.mText;
                            n2 = n;
                            if (object2 != null) {
                                n2 = n3 = n | 16777216;
                                if (!((ViewNodeText)object2).isSimple()) {
                                    n2 = n3 | 8388608;
                                }
                            }
                            n3 = n2;
                            if (this.mInputType != 0) {
                                n3 = n2 | 262144;
                            }
                            if (this.mWebScheme != null) break block68;
                            n2 = n3;
                            if (this.mWebDomain == null) break block69;
                        }
                        n2 = n3 | 524288;
                    }
                    n3 = n2;
                    if (this.mLocaleList != null) {
                        n3 = n2 | 65536;
                    }
                    n2 = n3;
                    if (this.mExtras != null) {
                        n2 = n3 | 4194304;
                    }
                    n3 = n2;
                    if (this.mChildren != null) {
                        n3 = n2 | 1048576;
                    }
                    object2 = this.mAutofillId;
                    n2 = n5;
                    if (object2 != null) {
                        n = n2 = false | true;
                        if (((AutofillId)object2).isVirtualInt()) {
                            n = n2 | 2;
                        }
                        n2 = n;
                        if (this.mAutofillId.hasSession()) {
                            n2 = n | 2048;
                        }
                    }
                    n = n2;
                    if (this.mAutofillValue != null) {
                        n = n2 | 4;
                    }
                    n2 = n;
                    if (this.mAutofillType != 0) {
                        n2 = n | 8;
                    }
                    n4 = n2;
                    if (this.mAutofillHints != null) {
                        n4 = n2 | 16;
                    }
                    n = n4;
                    if (this.mAutofillOptions != null) {
                        n = n4 | 32;
                    }
                    n2 = n;
                    if (this.mHtmlInfo instanceof Parcelable) {
                        n2 = n | 64;
                    }
                    n4 = n2;
                    if (this.mMinEms > -1) {
                        n4 = n2 | 256;
                    }
                    n = n4;
                    if (this.mMaxEms > -1) {
                        n = n4 | 512;
                    }
                    n2 = n;
                    if (this.mMaxLength > -1) {
                        n2 = n | 1024;
                    }
                    n4 = n2;
                    if (this.mTextIdEntry != null) {
                        n4 = n2 | 128;
                    }
                    ((PooledStringWriter)object).writeString(this.mClassName);
                    n2 = n = n3;
                    if (n4 == 0) break block70;
                    if (this.mSanitized) break block71;
                    n2 = n;
                    if (bl) break block70;
                }
                n2 = n3 & -513;
            }
            object2 = this.mAutofillOverlay;
            n = n2;
            if (object2 != null) {
                n = ((AutofillOverlay)object2).focused ? n2 | 32 : n2 & -33;
            }
            parcel.writeInt(n);
            parcel.writeInt(n4);
            if ((2097152 & n3) != 0) {
                parcel.writeInt(this.mId);
                if (this.mId != -1) {
                    ((PooledStringWriter)object).writeString(this.mIdEntry);
                    if (this.mIdEntry != null) {
                        ((PooledStringWriter)object).writeString(this.mIdType);
                        ((PooledStringWriter)object).writeString(this.mIdPackage);
                    }
                }
            }
            if (n4 != 0) {
                parcel.writeInt((int)this.mSanitized);
                parcel.writeInt(this.mImportantForAutofill);
                bl = this.mSanitized || !bl;
                if ((n4 & 1) != 0) {
                    parcel.writeInt(this.mAutofillId.getViewId());
                    if ((n4 & 2) != 0) {
                        parcel.writeInt(this.mAutofillId.getVirtualChildIntId());
                    }
                    if ((n4 & 2048) != 0) {
                        parcel.writeInt(this.mAutofillId.getSessionId());
                    }
                }
                if ((n4 & 8) != 0) {
                    parcel.writeInt(this.mAutofillType);
                }
                if ((n4 & 16) != 0) {
                    parcel.writeStringArray(this.mAutofillHints);
                }
                if ((n4 & 4) != 0) {
                    object2 = bl ? this.mAutofillValue : ((object2 = this.mAutofillOverlay) != null && ((AutofillOverlay)object2).value != null ? this.mAutofillOverlay.value : null);
                    parcel.writeParcelable((Parcelable)object2, 0);
                }
                if ((n4 & 32) != 0) {
                    parcel.writeCharSequenceArray(this.mAutofillOptions);
                }
                if ((n4 & 64) != 0) {
                    parcel.writeParcelable((Parcelable)((Object)this.mHtmlInfo), 0);
                }
                if ((n4 & 256) != 0) {
                    parcel.writeInt(this.mMinEms);
                }
                if ((n4 & 512) != 0) {
                    parcel.writeInt(this.mMaxEms);
                }
                if ((n4 & 1024) != 0) {
                    parcel.writeInt(this.mMaxLength);
                }
                bl2 = bl;
                if ((n4 & 128) != 0) {
                    ((PooledStringWriter)object).writeString(this.mTextIdEntry);
                    bl2 = bl;
                }
            }
            if ((n3 & 67108864) != 0) {
                parcel.writeInt(this.mX);
                parcel.writeInt(this.mY);
                parcel.writeInt(this.mWidth);
                parcel.writeInt(this.mHeight);
            } else {
                parcel.writeInt(this.mY << 16 | this.mX);
                parcel.writeInt(this.mHeight << 16 | this.mWidth);
            }
            if ((n3 & 134217728) != 0) {
                parcel.writeInt(this.mScrollX);
                parcel.writeInt(this.mScrollY);
            }
            if ((n3 & 1073741824) != 0) {
                this.mMatrix.getValues(arrf);
                parcel.writeFloatArray(arrf);
            }
            if ((n3 & 268435456) != 0) {
                parcel.writeFloat(this.mElevation);
            }
            if ((n3 & 536870912) != 0) {
                parcel.writeFloat(this.mAlpha);
            }
            if ((n3 & 33554432) != 0) {
                TextUtils.writeToParcel(this.mContentDescription, parcel, 0);
            }
            if ((n3 & 16777216) != 0) {
                object = this.mText;
                bl = (8388608 & n3) == 0;
                ((ViewNodeText)object).writeToParcel(parcel, bl, bl2);
            }
            if ((n3 & 262144) != 0) {
                parcel.writeInt(this.mInputType);
            }
            if ((524288 & n3) != 0) {
                parcel.writeString(this.mWebScheme);
                parcel.writeString(this.mWebDomain);
            }
            if ((65536 & n3) != 0) {
                parcel.writeParcelable(this.mLocaleList, 0);
            }
            if ((4194304 & n3) != 0) {
                parcel.writeBundle(this.mExtras);
            }
            return n3;
        }
    }

    static class ViewNodeBuilder
    extends ViewStructure {
        final AssistStructure mAssist;
        final boolean mAsync;
        final ViewNode mNode;

        ViewNodeBuilder(AssistStructure assistStructure, ViewNode viewNode, boolean bl) {
            this.mAssist = assistStructure;
            this.mNode = viewNode;
            this.mAsync = bl;
        }

        private final ViewNodeText getNodeText() {
            if (this.mNode.mText != null) {
                return this.mNode.mText;
            }
            this.mNode.mText = new ViewNodeText();
            return this.mNode.mText;
        }

        @Override
        public int addChildCount(int n) {
            if (this.mNode.mChildren == null) {
                this.setChildCount(n);
                return 0;
            }
            int n2 = this.mNode.mChildren.length;
            ViewNode[] arrviewNode = new ViewNode[n2 + n];
            System.arraycopy(this.mNode.mChildren, 0, arrviewNode, 0, n2);
            this.mNode.mChildren = arrviewNode;
            return n2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void asyncCommit() {
            AssistStructure assistStructure = this.mAssist;
            synchronized (assistStructure) {
                if (!this.mAsync) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Child ");
                    stringBuilder.append(this);
                    stringBuilder.append(" was not created with ViewStructure.asyncNewChild");
                    IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                    throw illegalStateException;
                }
                if (this.mAssist.mPendingAsyncChildren.remove(this)) {
                    this.mAssist.notifyAll();
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Child ");
                stringBuilder.append(this);
                stringBuilder.append(" already committed");
                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                throw illegalStateException;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public ViewStructure asyncNewChild(int n) {
            AssistStructure assistStructure = this.mAssist;
            synchronized (assistStructure) {
                ViewNode viewNode;
                this.mNode.mChildren[n] = viewNode = new ViewNode();
                ViewNodeBuilder viewNodeBuilder = new ViewNodeBuilder(this.mAssist, viewNode, true);
                this.mAssist.mPendingAsyncChildren.add(viewNodeBuilder);
                return viewNodeBuilder;
            }
        }

        @Override
        public AutofillId getAutofillId() {
            return this.mNode.mAutofillId;
        }

        @Override
        public int getChildCount() {
            int n = this.mNode.mChildren != null ? this.mNode.mChildren.length : 0;
            return n;
        }

        @Override
        public Bundle getExtras() {
            if (this.mNode.mExtras != null) {
                return this.mNode.mExtras;
            }
            this.mNode.mExtras = new Bundle();
            return this.mNode.mExtras;
        }

        @Override
        public CharSequence getHint() {
            String string2 = this.mNode.mText != null ? this.mNode.mText.mHint : null;
            return string2;
        }

        @Override
        public Rect getTempRect() {
            return this.mAssist.mTmpRect;
        }

        @Override
        public CharSequence getText() {
            CharSequence charSequence = this.mNode.mText != null ? this.mNode.mText.mText : null;
            return charSequence;
        }

        @Override
        public int getTextSelectionEnd() {
            int n = this.mNode.mText != null ? this.mNode.mText.mTextSelectionEnd : -1;
            return n;
        }

        @Override
        public int getTextSelectionStart() {
            int n = this.mNode.mText != null ? this.mNode.mText.mTextSelectionStart : -1;
            return n;
        }

        @Override
        public boolean hasExtras() {
            boolean bl = this.mNode.mExtras != null;
            return bl;
        }

        @Override
        public ViewStructure newChild(int n) {
            ViewNode viewNode;
            this.mNode.mChildren[n] = viewNode = new ViewNode();
            return new ViewNodeBuilder(this.mAssist, viewNode, false);
        }

        @Override
        public ViewStructure.HtmlInfo.Builder newHtmlInfoBuilder(String string2) {
            return new HtmlInfoNodeBuilder(string2);
        }

        @Override
        public void setAccessibilityFocused(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 4096 : 0;
            viewNode.mFlags = n & -4097 | n2;
        }

        @Override
        public void setActivated(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 8192 : 0;
            viewNode.mFlags = n & -8193 | n2;
        }

        @Override
        public void setAlpha(float f) {
            this.mNode.mAlpha = f;
        }

        @Override
        public void setAssistBlocked(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 128 : 0;
            viewNode.mFlags = n & -129 | n2;
        }

        @Override
        public void setAutofillHints(String[] arrstring) {
            this.mNode.mAutofillHints = arrstring;
        }

        @Override
        public void setAutofillId(AutofillId autofillId) {
            this.mNode.mAutofillId = autofillId;
        }

        @Override
        public void setAutofillId(AutofillId autofillId, int n) {
            this.mNode.mAutofillId = new AutofillId(autofillId, n);
        }

        @Override
        public void setAutofillOptions(CharSequence[] arrcharSequence) {
            this.mNode.mAutofillOptions = arrcharSequence;
        }

        @Override
        public void setAutofillType(int n) {
            this.mNode.mAutofillType = n;
        }

        @Override
        public void setAutofillValue(AutofillValue autofillValue) {
            this.mNode.mAutofillValue = autofillValue;
        }

        @Override
        public void setCheckable(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 256 : 0;
            viewNode.mFlags = n & -257 | n2;
        }

        @Override
        public void setChecked(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 512 : 0;
            viewNode.mFlags = n & -513 | n2;
        }

        @Override
        public void setChildCount(int n) {
            this.mNode.mChildren = new ViewNode[n];
        }

        @Override
        public void setClassName(String string2) {
            this.mNode.mClassName = string2;
        }

        @Override
        public void setClickable(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 1024 : 0;
            viewNode.mFlags = n & -1025 | n2;
        }

        @Override
        public void setContentDescription(CharSequence charSequence) {
            this.mNode.mContentDescription = charSequence;
        }

        @Override
        public void setContextClickable(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 16384 : 0;
            viewNode.mFlags = n & -16385 | n2;
        }

        @Override
        public void setDataIsSensitive(boolean bl) {
            this.mNode.mSanitized = bl ^ true;
        }

        @Override
        public void setDimens(int n, int n2, int n3, int n4, int n5, int n6) {
            ViewNode viewNode = this.mNode;
            viewNode.mX = n;
            viewNode.mY = n2;
            viewNode.mScrollX = n3;
            viewNode.mScrollY = n4;
            viewNode.mWidth = n5;
            viewNode.mHeight = n6;
        }

        @Override
        public void setElevation(float f) {
            this.mNode.mElevation = f;
        }

        @Override
        public void setEnabled(boolean bl) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = viewNode.mFlags & -2 | bl ^ true;
        }

        @Override
        public void setFocusable(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 16 : 0;
            viewNode.mFlags = n & -17 | n2;
        }

        @Override
        public void setFocused(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 32 : 0;
            viewNode.mFlags = n & -33 | n2;
        }

        @Override
        public void setHint(CharSequence charSequence) {
            ViewNodeText viewNodeText = this.getNodeText();
            charSequence = charSequence != null ? charSequence.toString() : null;
            viewNodeText.mHint = charSequence;
        }

        @Override
        public void setHtmlInfo(ViewStructure.HtmlInfo htmlInfo) {
            this.mNode.mHtmlInfo = htmlInfo;
        }

        @Override
        public void setId(int n, String string2, String string3, String string4) {
            ViewNode viewNode = this.mNode;
            viewNode.mId = n;
            viewNode.mIdPackage = string2;
            viewNode.mIdType = string3;
            viewNode.mIdEntry = string4;
        }

        @Override
        public void setImportantForAutofill(int n) {
            this.mNode.mImportantForAutofill = n;
        }

        @Override
        public void setInputType(int n) {
            this.mNode.mInputType = n;
        }

        @Override
        public void setLocaleList(LocaleList localeList) {
            this.mNode.mLocaleList = localeList;
        }

        @Override
        public void setLongClickable(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 2048 : 0;
            viewNode.mFlags = n & -2049 | n2;
        }

        @Override
        public void setMaxTextEms(int n) {
            this.mNode.mMaxEms = n;
        }

        @Override
        public void setMaxTextLength(int n) {
            this.mNode.mMaxLength = n;
        }

        @Override
        public void setMinTextEms(int n) {
            this.mNode.mMinEms = n;
        }

        @Override
        public void setOpaque(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 32768 : 0;
            viewNode.mFlags = n & -32769 | n2;
        }

        @Override
        public void setSelected(boolean bl) {
            ViewNode viewNode = this.mNode;
            int n = viewNode.mFlags;
            int n2 = bl ? 64 : 0;
            viewNode.mFlags = n & -65 | n2;
        }

        @Override
        public void setText(CharSequence charSequence) {
            ViewNodeText viewNodeText = this.getNodeText();
            viewNodeText.mText = TextUtils.trimNoCopySpans(charSequence);
            viewNodeText.mTextSelectionEnd = -1;
            viewNodeText.mTextSelectionStart = -1;
        }

        @Override
        public void setText(CharSequence charSequence, int n, int n2) {
            ViewNodeText viewNodeText = this.getNodeText();
            viewNodeText.mText = TextUtils.trimNoCopySpans(charSequence);
            viewNodeText.mTextSelectionStart = n;
            viewNodeText.mTextSelectionEnd = n2;
        }

        @Override
        public void setTextIdEntry(String string2) {
            this.mNode.mTextIdEntry = Preconditions.checkNotNull(string2);
        }

        @Override
        public void setTextLines(int[] arrn, int[] arrn2) {
            ViewNodeText viewNodeText = this.getNodeText();
            viewNodeText.mLineCharOffsets = arrn;
            viewNodeText.mLineBaselines = arrn2;
        }

        @Override
        public void setTextStyle(float f, int n, int n2, int n3) {
            ViewNodeText viewNodeText = this.getNodeText();
            viewNodeText.mTextColor = n;
            viewNodeText.mTextBackgroundColor = n2;
            viewNodeText.mTextSize = f;
            viewNodeText.mTextStyle = n3;
        }

        @Override
        public void setTransformation(Matrix matrix) {
            this.mNode.mMatrix = matrix == null ? null : new Matrix(matrix);
        }

        @Override
        public void setVisibility(int n) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = viewNode.mFlags & -13 | n & 12;
        }

        @Override
        public void setWebDomain(String string2) {
            this.mNode.setWebDomain(string2);
        }
    }

    static final class ViewNodeText {
        String mHint;
        int[] mLineBaselines;
        int[] mLineCharOffsets;
        CharSequence mText;
        int mTextBackgroundColor = 1;
        int mTextColor = 1;
        int mTextSelectionEnd;
        int mTextSelectionStart;
        float mTextSize;
        int mTextStyle;

        ViewNodeText() {
        }

        ViewNodeText(Parcel parcel, boolean bl) {
            this.mText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mTextSize = parcel.readFloat();
            this.mTextStyle = parcel.readInt();
            this.mTextColor = parcel.readInt();
            if (!bl) {
                this.mTextBackgroundColor = parcel.readInt();
                this.mTextSelectionStart = parcel.readInt();
                this.mTextSelectionEnd = parcel.readInt();
                this.mLineCharOffsets = parcel.createIntArray();
                this.mLineBaselines = parcel.createIntArray();
                this.mHint = parcel.readString();
            }
        }

        boolean isSimple() {
            int n = this.mTextBackgroundColor;
            boolean bl = true;
            if (n != 1 || this.mTextSelectionStart != 0 || this.mTextSelectionEnd != 0 || this.mLineCharOffsets != null || this.mLineBaselines != null || this.mHint != null) {
                bl = false;
            }
            return bl;
        }

        void writeToParcel(Parcel parcel, boolean bl, boolean bl2) {
            CharSequence charSequence = bl2 ? this.mText : "";
            TextUtils.writeToParcel(charSequence, parcel, 0);
            parcel.writeFloat(this.mTextSize);
            parcel.writeInt(this.mTextStyle);
            parcel.writeInt(this.mTextColor);
            if (!bl) {
                parcel.writeInt(this.mTextBackgroundColor);
                parcel.writeInt(this.mTextSelectionStart);
                parcel.writeInt(this.mTextSelectionEnd);
                parcel.writeIntArray(this.mLineCharOffsets);
                parcel.writeIntArray(this.mLineBaselines);
                parcel.writeString(this.mHint);
            }
        }
    }

    static final class ViewStackEntry {
        int curChild;
        ViewNode node;
        int numChildren;

        ViewStackEntry() {
        }
    }

    public static class WindowNode {
        final int mDisplayId;
        final int mHeight;
        final ViewNode mRoot;
        final CharSequence mTitle;
        final int mWidth;
        final int mX;
        final int mY;

        WindowNode(ParcelTransferReader parcelTransferReader) {
            Parcel parcel = parcelTransferReader.readParcel(286331153, 0);
            ++parcelTransferReader.mNumReadWindows;
            this.mX = parcel.readInt();
            this.mY = parcel.readInt();
            this.mWidth = parcel.readInt();
            this.mHeight = parcel.readInt();
            this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mDisplayId = parcel.readInt();
            this.mRoot = new ViewNode(parcelTransferReader, 0);
        }

        WindowNode(AssistStructure object, ViewRootImpl viewRootImpl, boolean bl, int n) {
            View view = viewRootImpl.getView();
            Rect rect = new Rect();
            view.getBoundsOnScreen(rect);
            this.mX = rect.left - view.getLeft();
            this.mY = rect.top - view.getTop();
            this.mWidth = rect.width();
            this.mHeight = rect.height();
            this.mTitle = viewRootImpl.getTitle();
            this.mDisplayId = viewRootImpl.getDisplayId();
            this.mRoot = new ViewNode();
            object = new ViewNodeBuilder((AssistStructure)object, this.mRoot, false);
            if ((viewRootImpl.getWindowFlags() & 8192) != 0) {
                if (bl) {
                    view.onProvideAutofillStructure((ViewStructure)object, this.resolveViewAutofillFlags(view.getContext(), n));
                } else {
                    view.onProvideStructure((ViewStructure)object);
                    ((ViewNodeBuilder)object).setAssistBlocked(true);
                    return;
                }
            }
            if (bl) {
                view.dispatchProvideAutofillStructure((ViewStructure)object, this.resolveViewAutofillFlags(view.getContext(), n));
            } else {
                view.dispatchProvideStructure((ViewStructure)object);
            }
        }

        public int getDisplayId() {
            return this.mDisplayId;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getLeft() {
            return this.mX;
        }

        public ViewNode getRootViewNode() {
            return this.mRoot;
        }

        public CharSequence getTitle() {
            return this.mTitle;
        }

        public int getTop() {
            return this.mY;
        }

        public int getWidth() {
            return this.mWidth;
        }

        int resolveViewAutofillFlags(Context context, int n) {
            n = (n & 1) == 0 && !context.isAutofillCompatibilityEnabled() ? 0 : 1;
            return n;
        }

        void writeSelfToParcel(Parcel parcel, PooledStringWriter pooledStringWriter, float[] arrf) {
            parcel.writeInt(this.mX);
            parcel.writeInt(this.mY);
            parcel.writeInt(this.mWidth);
            parcel.writeInt(this.mHeight);
            TextUtils.writeToParcel(this.mTitle, parcel, 0);
            parcel.writeInt(this.mDisplayId);
        }
    }

}

