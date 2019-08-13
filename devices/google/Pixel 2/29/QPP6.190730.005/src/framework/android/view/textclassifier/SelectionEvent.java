/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextSelection;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.Objects;

public final class SelectionEvent
implements Parcelable {
    public static final int ACTION_ABANDON = 107;
    public static final int ACTION_COPY = 101;
    public static final int ACTION_CUT = 103;
    public static final int ACTION_DRAG = 106;
    public static final int ACTION_OTHER = 108;
    public static final int ACTION_OVERTYPE = 100;
    public static final int ACTION_PASTE = 102;
    public static final int ACTION_RESET = 201;
    public static final int ACTION_SELECT_ALL = 200;
    public static final int ACTION_SHARE = 104;
    public static final int ACTION_SMART_SHARE = 105;
    public static final Parcelable.Creator<SelectionEvent> CREATOR = new Parcelable.Creator<SelectionEvent>(){

        @Override
        public SelectionEvent createFromParcel(Parcel parcel) {
            return new SelectionEvent(parcel);
        }

        public SelectionEvent[] newArray(int n) {
            return new SelectionEvent[n];
        }
    };
    public static final int EVENT_AUTO_SELECTION = 5;
    public static final int EVENT_SELECTION_MODIFIED = 2;
    public static final int EVENT_SELECTION_STARTED = 1;
    public static final int EVENT_SMART_SELECTION_MULTI = 4;
    public static final int EVENT_SMART_SELECTION_SINGLE = 3;
    public static final int INVOCATION_LINK = 2;
    public static final int INVOCATION_MANUAL = 1;
    public static final int INVOCATION_UNKNOWN = 0;
    static final String NO_SIGNATURE = "";
    private final int mAbsoluteEnd;
    private final int mAbsoluteStart;
    private long mDurationSincePreviousEvent;
    private long mDurationSinceSessionStart;
    private int mEnd;
    private final String mEntityType;
    private int mEventIndex;
    private long mEventTime;
    private int mEventType;
    private int mInvocationMethod;
    private String mPackageName = "";
    private String mResultId;
    private TextClassificationSessionId mSessionId;
    private int mSmartEnd;
    private int mSmartStart;
    private int mStart;
    private String mWidgetType = "unknown";
    private String mWidgetVersion;

    SelectionEvent(int n, int n2, int n3, String string2, int n4, String string3) {
        boolean bl = n2 >= n;
        Preconditions.checkArgument(bl, "end cannot be less than start");
        this.mAbsoluteStart = n;
        this.mAbsoluteEnd = n2;
        this.mEventType = n3;
        this.mEntityType = Preconditions.checkNotNull(string2);
        this.mResultId = string3;
        this.mInvocationMethod = n4;
    }

    private SelectionEvent(Parcel parcel) {
        this.mAbsoluteStart = parcel.readInt();
        this.mAbsoluteEnd = parcel.readInt();
        this.mEventType = parcel.readInt();
        this.mEntityType = parcel.readString();
        int n = parcel.readInt();
        Object var3_3 = null;
        Object object = n > 0 ? parcel.readString() : null;
        this.mWidgetVersion = object;
        this.mPackageName = parcel.readString();
        this.mWidgetType = parcel.readString();
        this.mInvocationMethod = parcel.readInt();
        this.mResultId = parcel.readString();
        this.mEventTime = parcel.readLong();
        this.mDurationSinceSessionStart = parcel.readLong();
        this.mDurationSincePreviousEvent = parcel.readLong();
        this.mEventIndex = parcel.readInt();
        object = parcel.readInt() > 0 ? TextClassificationSessionId.CREATOR.createFromParcel(parcel) : var3_3;
        this.mSessionId = object;
        this.mStart = parcel.readInt();
        this.mEnd = parcel.readInt();
        this.mSmartStart = parcel.readInt();
        this.mSmartEnd = parcel.readInt();
    }

    private static void checkActionType(int n) throws IllegalArgumentException {
        if (n != 200 && n != 201) {
            switch (n) {
                default: {
                    throw new IllegalArgumentException(String.format(Locale.US, "%d is not an eventType", n));
                }
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 106: 
                case 107: 
                case 108: 
            }
        }
    }

    public static SelectionEvent createSelectionActionEvent(int n, int n2, int n3) {
        boolean bl = n2 >= n;
        Preconditions.checkArgument(bl, "end cannot be less than start");
        SelectionEvent.checkActionType(n3);
        return new SelectionEvent(n, n2, n3, NO_SIGNATURE, 0, NO_SIGNATURE);
    }

    public static SelectionEvent createSelectionActionEvent(int n, int n2, int n3, TextClassification textClassification) {
        boolean bl = n2 >= n;
        Preconditions.checkArgument(bl, "end cannot be less than start");
        Preconditions.checkNotNull(textClassification);
        SelectionEvent.checkActionType(n3);
        String string2 = textClassification.getEntityCount() > 0 ? textClassification.getEntity(0) : NO_SIGNATURE;
        return new SelectionEvent(n, n2, n3, string2, 0, textClassification.getId());
    }

    public static SelectionEvent createSelectionModifiedEvent(int n, int n2) {
        boolean bl = n2 >= n;
        Preconditions.checkArgument(bl, "end cannot be less than start");
        return new SelectionEvent(n, n2, 2, NO_SIGNATURE, 0, NO_SIGNATURE);
    }

    public static SelectionEvent createSelectionModifiedEvent(int n, int n2, TextClassification textClassification) {
        boolean bl = n2 >= n;
        Preconditions.checkArgument(bl, "end cannot be less than start");
        Preconditions.checkNotNull(textClassification);
        String string2 = textClassification.getEntityCount() > 0 ? textClassification.getEntity(0) : NO_SIGNATURE;
        return new SelectionEvent(n, n2, 2, string2, 0, textClassification.getId());
    }

    public static SelectionEvent createSelectionModifiedEvent(int n, int n2, TextSelection textSelection) {
        boolean bl = n2 >= n;
        Preconditions.checkArgument(bl, "end cannot be less than start");
        Preconditions.checkNotNull(textSelection);
        String string2 = textSelection.getEntityCount() > 0 ? textSelection.getEntity(0) : NO_SIGNATURE;
        return new SelectionEvent(n, n2, 5, string2, 0, textSelection.getId());
    }

    public static SelectionEvent createSelectionStartedEvent(int n, int n2) {
        return new SelectionEvent(n2, n2 + 1, 1, NO_SIGNATURE, n, NO_SIGNATURE);
    }

    public static boolean isTerminal(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 100: 
            case 101: 
            case 102: 
            case 103: 
            case 104: 
            case 105: 
            case 106: 
            case 107: 
            case 108: 
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof SelectionEvent)) {
            return false;
        }
        object = (SelectionEvent)object;
        if (!(this.mAbsoluteStart == ((SelectionEvent)object).mAbsoluteStart && this.mAbsoluteEnd == ((SelectionEvent)object).mAbsoluteEnd && this.mEventType == ((SelectionEvent)object).mEventType && Objects.equals(this.mEntityType, ((SelectionEvent)object).mEntityType) && Objects.equals(this.mWidgetVersion, ((SelectionEvent)object).mWidgetVersion) && Objects.equals(this.mPackageName, ((SelectionEvent)object).mPackageName) && Objects.equals(this.mWidgetType, ((SelectionEvent)object).mWidgetType) && this.mInvocationMethod == ((SelectionEvent)object).mInvocationMethod && Objects.equals(this.mResultId, ((SelectionEvent)object).mResultId) && this.mEventTime == ((SelectionEvent)object).mEventTime && this.mDurationSinceSessionStart == ((SelectionEvent)object).mDurationSinceSessionStart && this.mDurationSincePreviousEvent == ((SelectionEvent)object).mDurationSincePreviousEvent && this.mEventIndex == ((SelectionEvent)object).mEventIndex && Objects.equals(this.mSessionId, ((SelectionEvent)object).mSessionId) && this.mStart == ((SelectionEvent)object).mStart && this.mEnd == ((SelectionEvent)object).mEnd && this.mSmartStart == ((SelectionEvent)object).mSmartStart && this.mSmartEnd == ((SelectionEvent)object).mSmartEnd)) {
            bl = false;
        }
        return bl;
    }

    int getAbsoluteEnd() {
        return this.mAbsoluteEnd;
    }

    int getAbsoluteStart() {
        return this.mAbsoluteStart;
    }

    public long getDurationSincePreviousEvent() {
        return this.mDurationSincePreviousEvent;
    }

    public long getDurationSinceSessionStart() {
        return this.mDurationSinceSessionStart;
    }

    public int getEnd() {
        return this.mEnd;
    }

    public String getEntityType() {
        return this.mEntityType;
    }

    public int getEventIndex() {
        return this.mEventIndex;
    }

    public long getEventTime() {
        return this.mEventTime;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public int getInvocationMethod() {
        return this.mInvocationMethod;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getResultId() {
        return this.mResultId;
    }

    public TextClassificationSessionId getSessionId() {
        return this.mSessionId;
    }

    public int getSmartEnd() {
        return this.mSmartEnd;
    }

    public int getSmartStart() {
        return this.mSmartStart;
    }

    public int getStart() {
        return this.mStart;
    }

    public String getWidgetType() {
        return this.mWidgetType;
    }

    public String getWidgetVersion() {
        return this.mWidgetVersion;
    }

    public int hashCode() {
        return Objects.hash(this.mAbsoluteStart, this.mAbsoluteEnd, this.mEventType, this.mEntityType, this.mWidgetVersion, this.mPackageName, this.mWidgetType, this.mInvocationMethod, this.mResultId, this.mEventTime, this.mDurationSinceSessionStart, this.mDurationSincePreviousEvent, this.mEventIndex, this.mSessionId, this.mStart, this.mEnd, this.mSmartStart, this.mSmartEnd);
    }

    boolean isTerminal() {
        return SelectionEvent.isTerminal(this.mEventType);
    }

    SelectionEvent setDurationSincePreviousEvent(long l) {
        this.mDurationSincePreviousEvent = l;
        return this;
    }

    SelectionEvent setDurationSinceSessionStart(long l) {
        this.mDurationSinceSessionStart = l;
        return this;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public SelectionEvent setEnd(int n) {
        this.mEnd = n;
        return this;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public SelectionEvent setEventIndex(int n) {
        this.mEventIndex = n;
        return this;
    }

    SelectionEvent setEventTime(long l) {
        this.mEventTime = l;
        return this;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void setEventType(int n) {
        this.mEventType = n;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void setInvocationMethod(int n) {
        this.mInvocationMethod = n;
    }

    SelectionEvent setResultId(String string2) {
        this.mResultId = string2;
        return this;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public SelectionEvent setSessionId(TextClassificationSessionId textClassificationSessionId) {
        this.mSessionId = textClassificationSessionId;
        return this;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public SelectionEvent setSmartEnd(int n) {
        this.mSmartEnd = n;
        return this;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public SelectionEvent setSmartStart(int n) {
        this.mSmartStart = n;
        return this;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public SelectionEvent setStart(int n) {
        this.mStart = n;
        return this;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void setTextClassificationSessionContext(TextClassificationContext textClassificationContext) {
        this.mPackageName = textClassificationContext.getPackageName();
        this.mWidgetType = textClassificationContext.getWidgetType();
        this.mWidgetVersion = textClassificationContext.getWidgetVersion();
    }

    public String toString() {
        return String.format(Locale.US, "SelectionEvent {absoluteStart=%d, absoluteEnd=%d, eventType=%d, entityType=%s, widgetVersion=%s, packageName=%s, widgetType=%s, invocationMethod=%s, resultId=%s, eventTime=%d, durationSinceSessionStart=%d, durationSincePreviousEvent=%d, eventIndex=%d,sessionId=%s, start=%d, end=%d, smartStart=%d, smartEnd=%d}", this.mAbsoluteStart, this.mAbsoluteEnd, this.mEventType, this.mEntityType, this.mWidgetVersion, this.mPackageName, this.mWidgetType, this.mInvocationMethod, this.mResultId, this.mEventTime, this.mDurationSinceSessionStart, this.mDurationSincePreviousEvent, this.mEventIndex, this.mSessionId, this.mStart, this.mEnd, this.mSmartStart, this.mSmartEnd);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mAbsoluteStart);
        parcel.writeInt(this.mAbsoluteEnd);
        parcel.writeInt(this.mEventType);
        parcel.writeString(this.mEntityType);
        Object object = this.mWidgetVersion;
        int n2 = 1;
        int n3 = object != null ? 1 : 0;
        parcel.writeInt(n3);
        object = this.mWidgetVersion;
        if (object != null) {
            parcel.writeString((String)object);
        }
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mWidgetType);
        parcel.writeInt(this.mInvocationMethod);
        parcel.writeString(this.mResultId);
        parcel.writeLong(this.mEventTime);
        parcel.writeLong(this.mDurationSinceSessionStart);
        parcel.writeLong(this.mDurationSincePreviousEvent);
        parcel.writeInt(this.mEventIndex);
        n3 = this.mSessionId != null ? n2 : 0;
        parcel.writeInt(n3);
        object = this.mSessionId;
        if (object != null) {
            ((TextClassificationSessionId)object).writeToParcel(parcel, n);
        }
        parcel.writeInt(this.mStart);
        parcel.writeInt(this.mEnd);
        parcel.writeInt(this.mSmartStart);
        parcel.writeInt(this.mSmartEnd);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ActionType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EventType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InvocationMethod {
    }

}

