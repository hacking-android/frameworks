/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.util.DebugUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.ContentCaptureHelper;
import android.view.contentcapture.ContentCaptureSessionId;
import android.view.contentcapture.MainContentCaptureSession;
import android.view.contentcapture.ViewNode;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Random;

public abstract class ContentCaptureSession
implements AutoCloseable {
    public static final int FLUSH_REASON_FULL = 1;
    public static final int FLUSH_REASON_IDLE_TIMEOUT = 5;
    public static final int FLUSH_REASON_SESSION_FINISHED = 4;
    public static final int FLUSH_REASON_SESSION_STARTED = 3;
    public static final int FLUSH_REASON_TEXT_CHANGE_TIMEOUT = 6;
    public static final int FLUSH_REASON_VIEW_ROOT_ENTERED = 2;
    private static final int INITIAL_CHILDREN_CAPACITY = 5;
    public static final int NO_SESSION_ID = 0;
    public static final int STATE_ACTIVE = 2;
    public static final int STATE_BY_APP = 64;
    public static final int STATE_DISABLED = 4;
    public static final int STATE_DUPLICATED_ID = 8;
    public static final int STATE_FLAG_SECURE = 32;
    public static final int STATE_INTERNAL_ERROR = 256;
    public static final int STATE_NOT_WHITELISTED = 512;
    public static final int STATE_NO_RESPONSE = 128;
    public static final int STATE_NO_SERVICE = 16;
    public static final int STATE_SERVICE_DIED = 1024;
    public static final int STATE_SERVICE_RESURRECTED = 4096;
    public static final int STATE_SERVICE_UPDATING = 2048;
    public static final int STATE_WAITING_FOR_SERVER = 1;
    private static final String TAG = ContentCaptureSession.class.getSimpleName();
    public static final int UNKNOWN_STATE = 0;
    private static final Random sIdGenerator = new Random();
    @GuardedBy(value={"mLock"})
    private ArrayList<ContentCaptureSession> mChildren;
    private ContentCaptureContext mClientContext;
    private ContentCaptureSessionId mContentCaptureSessionId;
    @GuardedBy(value={"mLock"})
    private boolean mDestroyed;
    protected final int mId;
    private final Object mLock = new Object();
    private int mState;

    protected ContentCaptureSession() {
        this(ContentCaptureSession.getRandomSessionId());
    }

    @VisibleForTesting
    public ContentCaptureSession(int n) {
        boolean bl = false;
        this.mState = 0;
        if (n != 0) {
            bl = true;
        }
        Preconditions.checkArgument(bl);
        this.mId = n;
    }

    ContentCaptureSession(ContentCaptureContext contentCaptureContext) {
        this();
        this.mClientContext = Preconditions.checkNotNull(contentCaptureContext);
    }

    public static String getFlushReasonAsString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("UNKOWN-");
                stringBuilder.append(n);
                return stringBuilder.toString();
            }
            case 6: {
                return "TEXT_CHANGE";
            }
            case 5: {
                return "IDLE";
            }
            case 4: {
                return "FINISHED";
            }
            case 3: {
                return "STARTED";
            }
            case 2: {
                return "VIEW_ROOT";
            }
            case 1: 
        }
        return "FULL";
    }

    private static int getRandomSessionId() {
        int n;
        while ((n = sIdGenerator.nextInt()) == 0) {
        }
        return n;
    }

    protected static String getStateAsString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" (");
        String string2 = n == 0 ? "UNKNOWN" : DebugUtils.flagsToString(ContentCaptureSession.class, "STATE_", n);
        stringBuilder.append(string2);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void close() {
        this.destroy();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final ContentCaptureSession createContentCaptureSession(ContentCaptureContext object) {
        Serializable serializable;
        ContentCaptureSession contentCaptureSession = this.newChild((ContentCaptureContext)object);
        if (ContentCaptureHelper.sDebug) {
            String string2 = TAG;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("createContentCaptureSession(");
            ((StringBuilder)serializable).append(object);
            ((StringBuilder)serializable).append(": parent=");
            ((StringBuilder)serializable).append(this.mId);
            ((StringBuilder)serializable).append(", child=");
            ((StringBuilder)serializable).append(contentCaptureSession.mId);
            Log.d(string2, ((StringBuilder)serializable).toString());
        }
        object = this.mLock;
        synchronized (object) {
            if (this.mChildren == null) {
                serializable = new ArrayList(5);
                this.mChildren = serializable;
            }
            this.mChildren.add(contentCaptureSession);
            return contentCaptureSession;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void destroy() {
        Object object = this.mLock;
        synchronized (object) {
            CharSequence charSequence;
            Object object2;
            if (this.mDestroyed) {
                if (!ContentCaptureHelper.sDebug) return;
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("destroy(");
                stringBuilder.append(this.mId);
                stringBuilder.append("): already destroyed");
                Log.d(string2, stringBuilder.toString());
                return;
            }
            this.mDestroyed = true;
            if (ContentCaptureHelper.sVerbose) {
                charSequence = TAG;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("destroy(): state=");
                ((StringBuilder)object2).append(ContentCaptureSession.getStateAsString(this.mState));
                ((StringBuilder)object2).append(", mId=");
                ((StringBuilder)object2).append(this.mId);
                Log.v((String)charSequence, ((StringBuilder)object2).toString());
            }
            if (this.mChildren != null) {
                int n = this.mChildren.size();
                if (ContentCaptureHelper.sVerbose) {
                    object2 = TAG;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Destroying ");
                    ((StringBuilder)charSequence).append(n);
                    ((StringBuilder)charSequence).append(" children first");
                    Log.v((String)object2, ((StringBuilder)charSequence).toString());
                }
                for (int i = 0; i < n; ++i) {
                    object2 = this.mChildren.get(i);
                    try {
                        ((ContentCaptureSession)object2).destroy();
                        continue;
                    }
                    catch (Exception exception) {
                        String string3 = TAG;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("exception destroying child session #");
                        ((StringBuilder)object2).append(i);
                        ((StringBuilder)object2).append(": ");
                        ((StringBuilder)object2).append(exception);
                        Log.w(string3, ((StringBuilder)object2).toString());
                    }
                }
            }
        }
        try {
            this.flush(4);
            return;
        }
        finally {
            this.onDestroy();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dump(String string2, PrintWriter printWriter) {
        printWriter.print(string2);
        printWriter.print("id: ");
        printWriter.println(this.mId);
        if (this.mClientContext != null) {
            printWriter.print(string2);
            this.mClientContext.dump(printWriter);
            printWriter.println();
        }
        Object object = this.mLock;
        synchronized (object) {
            printWriter.print(string2);
            printWriter.print("destroyed: ");
            printWriter.println(this.mDestroyed);
            if (this.mChildren != null && !this.mChildren.isEmpty()) {
                CharSequence charSequence = new StringBuilder();
                charSequence.append(string2);
                charSequence.append("  ");
                charSequence = charSequence.toString();
                int n = this.mChildren.size();
                printWriter.print(string2);
                printWriter.print("number children: ");
                printWriter.println(n);
                for (int i = 0; i < n; ++i) {
                    ContentCaptureSession contentCaptureSession = this.mChildren.get(i);
                    printWriter.print(string2);
                    printWriter.print(i);
                    printWriter.println(": ");
                    contentCaptureSession.dump((String)charSequence, printWriter);
                }
            }
            return;
        }
    }

    abstract void flush(int var1);

    public final ContentCaptureContext getContentCaptureContext() {
        return this.mClientContext;
    }

    public final ContentCaptureSessionId getContentCaptureSessionId() {
        if (this.mContentCaptureSessionId == null) {
            this.mContentCaptureSessionId = new ContentCaptureSessionId(this.mId);
        }
        return this.mContentCaptureSessionId;
    }

    public int getId() {
        return this.mId;
    }

    abstract MainContentCaptureSession getMainCaptureSession();

    abstract void internalNotifyViewAppeared(ViewNode.ViewStructureImpl var1);

    abstract void internalNotifyViewDisappeared(AutofillId var1);

    abstract void internalNotifyViewTextChanged(AutofillId var1, CharSequence var2);

    public abstract void internalNotifyViewTreeEvent(boolean var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean isContentCaptureEnabled() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mDestroyed) return false;
            return true;
        }
    }

    public AutofillId newAutofillId(AutofillId autofillId, long l) {
        Preconditions.checkNotNull(autofillId);
        Preconditions.checkArgument(autofillId.isNonVirtual(), "hostId cannot be virtual: %s", autofillId);
        return new AutofillId(autofillId, l, this.mId);
    }

    abstract ContentCaptureSession newChild(ContentCaptureContext var1);

    public final ViewStructure newViewStructure(View view) {
        return new ViewNode.ViewStructureImpl(view);
    }

    public final ViewStructure newVirtualViewStructure(AutofillId autofillId, long l) {
        return new ViewNode.ViewStructureImpl(autofillId, l, this.mId);
    }

    public final void notifyViewAppeared(ViewStructure viewStructure) {
        Preconditions.checkNotNull(viewStructure);
        if (!this.isContentCaptureEnabled()) {
            return;
        }
        if (viewStructure instanceof ViewNode.ViewStructureImpl) {
            this.internalNotifyViewAppeared((ViewNode.ViewStructureImpl)viewStructure);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid node class: ");
        stringBuilder.append(viewStructure.getClass());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final void notifyViewDisappeared(AutofillId autofillId) {
        Preconditions.checkNotNull(autofillId);
        if (!this.isContentCaptureEnabled()) {
            return;
        }
        this.internalNotifyViewDisappeared(autofillId);
    }

    public final void notifyViewTextChanged(AutofillId autofillId, CharSequence charSequence) {
        Preconditions.checkNotNull(autofillId);
        if (!this.isContentCaptureEnabled()) {
            return;
        }
        this.internalNotifyViewTextChanged(autofillId, charSequence);
    }

    public final void notifyViewsDisappeared(AutofillId autofillId, long[] arrl) {
        boolean bl = autofillId.isNonVirtual();
        Preconditions.checkArgument(bl, "hostId cannot be virtual: %s", autofillId);
        Preconditions.checkArgument(ArrayUtils.isEmpty(arrl) ^ true, "virtual ids cannot be empty");
        if (!this.isContentCaptureEnabled()) {
            return;
        }
        int n = arrl.length;
        for (int i = 0; i < n; ++i) {
            this.internalNotifyViewDisappeared(new AutofillId(autofillId, arrl[i], this.mId));
        }
    }

    abstract void onDestroy();

    public final void setContentCaptureContext(ContentCaptureContext contentCaptureContext) {
        this.mClientContext = contentCaptureContext;
        this.updateContentCaptureContext(contentCaptureContext);
    }

    public String toString() {
        return Integer.toString(this.mId);
    }

    abstract void updateContentCaptureContext(ContentCaptureContext var1);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FlushReason {
    }

}

