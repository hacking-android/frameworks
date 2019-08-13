/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.ContentCaptureHelper;
import android.view.contentcapture.ViewNode;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public final class ContentCaptureEvent
implements Parcelable {
    public static final Parcelable.Creator<ContentCaptureEvent> CREATOR;
    private static final String TAG;
    public static final int TYPE_CONTEXT_UPDATED = 6;
    public static final int TYPE_SESSION_FINISHED = -2;
    public static final int TYPE_SESSION_PAUSED = 8;
    public static final int TYPE_SESSION_RESUMED = 7;
    public static final int TYPE_SESSION_STARTED = -1;
    public static final int TYPE_VIEW_APPEARED = 1;
    public static final int TYPE_VIEW_DISAPPEARED = 2;
    public static final int TYPE_VIEW_TEXT_CHANGED = 3;
    public static final int TYPE_VIEW_TREE_APPEARED = 5;
    public static final int TYPE_VIEW_TREE_APPEARING = 4;
    private ContentCaptureContext mClientContext;
    private final long mEventTime;
    private AutofillId mId;
    private ArrayList<AutofillId> mIds;
    private ViewNode mNode;
    private int mParentSessionId = 0;
    private final int mSessionId;
    private CharSequence mText;
    private final int mType;

    static {
        TAG = ContentCaptureEvent.class.getSimpleName();
        CREATOR = new Parcelable.Creator<ContentCaptureEvent>(){

            @Override
            public ContentCaptureEvent createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                int n2 = parcel.readInt();
                ContentCaptureEvent contentCaptureEvent = new ContentCaptureEvent(n, n2, parcel.readLong());
                Object object = (AutofillId)parcel.readParcelable(null);
                if (object != null) {
                    contentCaptureEvent.setAutofillId((AutofillId)object);
                }
                if ((object = parcel.createTypedArrayList(AutofillId.CREATOR)) != null) {
                    contentCaptureEvent.setAutofillIds((ArrayList<AutofillId>)object);
                }
                if ((object = ViewNode.readFromParcel(parcel)) != null) {
                    contentCaptureEvent.setViewNode((ViewNode)object);
                }
                contentCaptureEvent.setText(parcel.readCharSequence());
                if (n2 == -1 || n2 == -2) {
                    contentCaptureEvent.setParentSessionId(parcel.readInt());
                }
                if (n2 == -1 || n2 == 6) {
                    contentCaptureEvent.setClientContext((ContentCaptureContext)parcel.readParcelable(null));
                }
                return contentCaptureEvent;
            }

            public ContentCaptureEvent[] newArray(int n) {
                return new ContentCaptureEvent[n];
            }
        };
    }

    public ContentCaptureEvent(int n, int n2) {
        this(n, n2, System.currentTimeMillis());
    }

    public ContentCaptureEvent(int n, int n2, long l) {
        this.mSessionId = n;
        this.mType = n2;
        this.mEventTime = l;
    }

    public static String getTypeAsString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("UKNOWN_TYPE: ");
                stringBuilder.append(n);
                return stringBuilder.toString();
            }
            case 8: {
                return "SESSION_PAUSED";
            }
            case 7: {
                return "SESSION_RESUMED";
            }
            case 6: {
                return "CONTEXT_UPDATED";
            }
            case 5: {
                return "VIEW_TREE_APPEARED";
            }
            case 4: {
                return "VIEW_TREE_APPEARING";
            }
            case 3: {
                return "VIEW_TEXT_CHANGED";
            }
            case 2: {
                return "VIEW_DISAPPEARED";
            }
            case 1: {
                return "VIEW_APPEARED";
            }
            case -1: {
                return "SESSION_STARTED";
            }
            case -2: 
        }
        return "SESSION_FINISHED";
    }

    public ContentCaptureEvent addAutofillId(AutofillId autofillId) {
        Preconditions.checkNotNull(autofillId);
        if (this.mIds == null) {
            this.mIds = new ArrayList();
            Object object = this.mId;
            if (object == null) {
                object = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("addAutofillId(");
                stringBuilder.append(autofillId);
                stringBuilder.append(") called without an initial id");
                Log.w((String)object, stringBuilder.toString());
            } else {
                this.mIds.add((AutofillId)object);
                this.mId = null;
            }
        }
        this.mIds.add(autofillId);
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print("type=");
        printWriter.print(ContentCaptureEvent.getTypeAsString(this.mType));
        printWriter.print(", time=");
        printWriter.print(this.mEventTime);
        if (this.mId != null) {
            printWriter.print(", id=");
            printWriter.print(this.mId);
        }
        if (this.mIds != null) {
            printWriter.print(", ids=");
            printWriter.print(this.mIds);
        }
        if (this.mNode != null) {
            printWriter.print(", mNode.id=");
            printWriter.print(this.mNode.getAutofillId());
        }
        if (this.mSessionId != 0) {
            printWriter.print(", sessionId=");
            printWriter.print(this.mSessionId);
        }
        if (this.mParentSessionId != 0) {
            printWriter.print(", parentSessionId=");
            printWriter.print(this.mParentSessionId);
        }
        if (this.mText != null) {
            printWriter.print(", text=");
            printWriter.println(ContentCaptureHelper.getSanitizedString(this.mText));
        }
        if (this.mClientContext != null) {
            printWriter.print(", context=");
            this.mClientContext.dump(printWriter);
            printWriter.println();
        }
    }

    public ContentCaptureContext getContentCaptureContext() {
        return this.mClientContext;
    }

    public long getEventTime() {
        return this.mEventTime;
    }

    public AutofillId getId() {
        return this.mId;
    }

    public List<AutofillId> getIds() {
        return this.mIds;
    }

    public int getParentSessionId() {
        return this.mParentSessionId;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public int getType() {
        return this.mType;
    }

    public ViewNode getViewNode() {
        return this.mNode;
    }

    public void mergeEvent(ContentCaptureEvent object) {
        Preconditions.checkNotNull(object);
        int n = ((ContentCaptureEvent)object).getType();
        if (this.mType != n) {
            String string2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("mergeEvent(");
            ((StringBuilder)object).append(ContentCaptureEvent.getTypeAsString(n));
            ((StringBuilder)object).append(") cannot be merged with different eventType=");
            ((StringBuilder)object).append(ContentCaptureEvent.getTypeAsString(this.mType));
            Log.e(string2, ((StringBuilder)object).toString());
            return;
        }
        if (n == 2) {
            Object object2 = ((ContentCaptureEvent)object).getIds();
            Object object3 = ((ContentCaptureEvent)object).getId();
            if (object2 != null) {
                if (object3 != null) {
                    object3 = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("got TYPE_VIEW_DISAPPEARED event with both id and ids: ");
                    stringBuilder.append(object);
                    Log.w((String)object3, stringBuilder.toString());
                }
                for (n = 0; n < object2.size(); ++n) {
                    this.addAutofillId((AutofillId)object2.get(n));
                }
                return;
            }
            if (object3 != null) {
                this.addAutofillId((AutofillId)object3);
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("mergeEvent(): got TYPE_VIEW_DISAPPEARED event with neither id or ids: ");
            ((StringBuilder)object2).append(object);
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        if (n == 3) {
            this.setText(((ContentCaptureEvent)object).getText());
        } else {
            String string3 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("mergeEvent(");
            ((StringBuilder)object).append(ContentCaptureEvent.getTypeAsString(n));
            ((StringBuilder)object).append(") does not support this event type.");
            Log.e(string3, ((StringBuilder)object).toString());
        }
    }

    public ContentCaptureEvent setAutofillId(AutofillId autofillId) {
        this.mId = Preconditions.checkNotNull(autofillId);
        return this;
    }

    public ContentCaptureEvent setAutofillIds(ArrayList<AutofillId> arrayList) {
        this.mIds = Preconditions.checkNotNull(arrayList);
        return this;
    }

    public ContentCaptureEvent setClientContext(ContentCaptureContext contentCaptureContext) {
        this.mClientContext = contentCaptureContext;
        return this;
    }

    public ContentCaptureEvent setParentSessionId(int n) {
        this.mParentSessionId = n;
        return this;
    }

    public ContentCaptureEvent setText(CharSequence charSequence) {
        this.mText = charSequence;
        return this;
    }

    public ContentCaptureEvent setViewNode(ViewNode viewNode) {
        this.mNode = Preconditions.checkNotNull(viewNode);
        return this;
    }

    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder("ContentCaptureEvent[type=").append(ContentCaptureEvent.getTypeAsString(this.mType));
        stringBuilder.append(", session=");
        stringBuilder.append(this.mSessionId);
        if (this.mType == -1 && this.mParentSessionId != 0) {
            stringBuilder.append(", parent=");
            stringBuilder.append(this.mParentSessionId);
        }
        if (this.mId != null) {
            stringBuilder.append(", id=");
            stringBuilder.append(this.mId);
        }
        if (this.mIds != null) {
            stringBuilder.append(", ids=");
            stringBuilder.append(this.mIds);
        }
        if ((object = this.mNode) != null) {
            object = ((ViewNode)object).getClassName();
            if (this.mNode != null) {
                stringBuilder.append(", class=");
                stringBuilder.append((String)object);
            }
            stringBuilder.append(", id=");
            stringBuilder.append(this.mNode.getAutofillId());
        }
        if (this.mText != null) {
            stringBuilder.append(", text=");
            stringBuilder.append(ContentCaptureHelper.getSanitizedString(this.mText));
        }
        if (this.mClientContext != null) {
            stringBuilder.append(", context=");
            stringBuilder.append(this.mClientContext);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSessionId);
        parcel.writeInt(this.mType);
        parcel.writeLong(this.mEventTime);
        parcel.writeParcelable(this.mId, n);
        parcel.writeTypedList(this.mIds);
        ViewNode.writeToParcel(parcel, this.mNode, n);
        parcel.writeCharSequence(this.mText);
        int n2 = this.mType;
        if (n2 == -1 || n2 == -2) {
            parcel.writeInt(this.mParentSessionId);
        }
        if ((n2 = this.mType) == -1 || n2 == 6) {
            parcel.writeParcelable(this.mClientContext, n);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EventType {
    }

}

