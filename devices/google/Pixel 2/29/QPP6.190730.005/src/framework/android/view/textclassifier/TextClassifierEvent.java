/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.ULocale
 */
package android.view.textclassifier;

import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationSessionId;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public abstract class TextClassifierEvent
implements Parcelable {
    public static final int CATEGORY_CONVERSATION_ACTIONS = 3;
    public static final int CATEGORY_LANGUAGE_DETECTION = 4;
    public static final int CATEGORY_LINKIFY = 2;
    public static final int CATEGORY_SELECTION = 1;
    public static final Parcelable.Creator<TextClassifierEvent> CREATOR = new Parcelable.Creator<TextClassifierEvent>(){

        @Override
        public TextClassifierEvent createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            if (n == 1) {
                return new TextSelectionEvent(parcel);
            }
            if (n == 2) {
                return new TextLinkifyEvent(parcel);
            }
            if (n == 4) {
                return new LanguageDetectionEvent(parcel);
            }
            if (n == 3) {
                return new ConversationActionsEvent(parcel);
            }
            throw new IllegalStateException("Unexpected input event type token in parcel.");
        }

        public TextClassifierEvent[] newArray(int n) {
            return new TextClassifierEvent[n];
        }
    };
    private static final int PARCEL_TOKEN_CONVERSATION_ACTION_EVENT = 3;
    private static final int PARCEL_TOKEN_LANGUAGE_DETECTION_EVENT = 4;
    private static final int PARCEL_TOKEN_TEXT_LINKIFY_EVENT = 2;
    private static final int PARCEL_TOKEN_TEXT_SELECTION_EVENT = 1;
    public static final int TYPE_ACTIONS_GENERATED = 20;
    public static final int TYPE_ACTIONS_SHOWN = 6;
    public static final int TYPE_AUTO_SELECTION = 5;
    public static final int TYPE_COPY_ACTION = 9;
    public static final int TYPE_CUT_ACTION = 11;
    public static final int TYPE_LINK_CLICKED = 7;
    public static final int TYPE_MANUAL_REPLY = 19;
    public static final int TYPE_OTHER_ACTION = 16;
    public static final int TYPE_OVERTYPE = 8;
    public static final int TYPE_PASTE_ACTION = 10;
    public static final int TYPE_SELECTION_DESTROYED = 15;
    public static final int TYPE_SELECTION_DRAG = 14;
    public static final int TYPE_SELECTION_MODIFIED = 2;
    public static final int TYPE_SELECTION_RESET = 18;
    public static final int TYPE_SELECTION_STARTED = 1;
    public static final int TYPE_SELECT_ALL = 17;
    public static final int TYPE_SHARE_ACTION = 12;
    public static final int TYPE_SMART_ACTION = 13;
    public static final int TYPE_SMART_SELECTION_MULTI = 4;
    public static final int TYPE_SMART_SELECTION_SINGLE = 3;
    private final int[] mActionIndices;
    private final String[] mEntityTypes;
    private final int mEventCategory;
    private final TextClassificationContext mEventContext;
    private final int mEventIndex;
    private final int mEventType;
    private final Bundle mExtras;
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public TextClassificationSessionId mHiddenTempSessionId;
    private final ULocale mLocale;
    private final String mModelName;
    private final String mResultId;
    private final float[] mScores;

    private TextClassifierEvent(Parcel parcel) {
        this.mEventCategory = parcel.readInt();
        this.mEventType = parcel.readInt();
        this.mEntityTypes = parcel.readStringArray();
        ULocale uLocale = null;
        this.mEventContext = (TextClassificationContext)parcel.readParcelable(null);
        this.mResultId = parcel.readString();
        this.mEventIndex = parcel.readInt();
        this.mScores = new float[parcel.readInt()];
        parcel.readFloatArray(this.mScores);
        this.mModelName = parcel.readString();
        this.mActionIndices = parcel.createIntArray();
        String string2 = parcel.readString();
        if (string2 != null) {
            uLocale = ULocale.forLanguageTag((String)string2);
        }
        this.mLocale = uLocale;
        this.mExtras = parcel.readBundle();
    }

    private TextClassifierEvent(Builder object) {
        this.mEventCategory = ((Builder)object).mEventCategory;
        this.mEventType = ((Builder)object).mEventType;
        this.mEntityTypes = ((Builder)object).mEntityTypes;
        this.mEventContext = ((Builder)object).mEventContext;
        this.mResultId = ((Builder)object).mResultId;
        this.mEventIndex = ((Builder)object).mEventIndex;
        this.mScores = ((Builder)object).mScores;
        this.mModelName = ((Builder)object).mModelName;
        this.mActionIndices = ((Builder)object).mActionIndices;
        this.mLocale = ((Builder)object).mLocale;
        object = ((Builder)object).mExtras == null ? Bundle.EMPTY : ((Builder)object).mExtras;
        this.mExtras = object;
    }

    private int getParcelToken() {
        if (this instanceof TextSelectionEvent) {
            return 1;
        }
        if (this instanceof TextLinkifyEvent) {
            return 2;
        }
        if (this instanceof LanguageDetectionEvent) {
            return 4;
        }
        if (this instanceof ConversationActionsEvent) {
            return 3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected type: ");
        stringBuilder.append(this.getClass().getSimpleName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int[] getActionIndices() {
        return this.mActionIndices;
    }

    public String[] getEntityTypes() {
        return this.mEntityTypes;
    }

    public int getEventCategory() {
        return this.mEventCategory;
    }

    public TextClassificationContext getEventContext() {
        return this.mEventContext;
    }

    public int getEventIndex() {
        return this.mEventIndex;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public ULocale getLocale() {
        return this.mLocale;
    }

    public String getModelName() {
        return this.mModelName;
    }

    public String getResultId() {
        return this.mResultId;
    }

    public float[] getScores() {
        return this.mScores;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public final SelectionEvent toSelectionEvent() {
        int n = this.getEventCategory();
        if (n != 1) {
            if (n != 2) {
                return null;
            }
            n = 2;
        } else {
            n = 1;
        }
        int n2 = this.getEntityTypes().length;
        String string2 = "";
        Object object = n2 > 0 ? this.getEntityTypes()[0] : "";
        SelectionEvent selectionEvent = new SelectionEvent(0, 0, 0, (String)object, 0, "");
        selectionEvent.setInvocationMethod(n);
        if (this.getEventContext() != null) {
            selectionEvent.setTextClassificationSessionContext(this.getEventContext());
        }
        selectionEvent.setSessionId(this.mHiddenTempSessionId);
        object = this.getResultId();
        if (object == null) {
            object = string2;
        }
        selectionEvent.setResultId((String)object);
        selectionEvent.setEventIndex(this.getEventIndex());
        switch (this.getEventType()) {
            default: {
                n = 0;
                break;
            }
            case 18: {
                n = 201;
                break;
            }
            case 17: {
                n = 200;
                break;
            }
            case 16: {
                n = 108;
                break;
            }
            case 15: {
                n = 107;
                break;
            }
            case 14: {
                n = 106;
                break;
            }
            case 13: {
                n = 105;
                break;
            }
            case 12: {
                n = 104;
                break;
            }
            case 11: {
                n = 103;
                break;
            }
            case 10: {
                n = 102;
                break;
            }
            case 9: {
                n = 101;
                break;
            }
            case 8: {
                n = 100;
                break;
            }
            case 5: {
                n = 5;
                break;
            }
            case 4: {
                n = 4;
                break;
            }
            case 3: {
                n = 3;
                break;
            }
            case 2: {
                n = 2;
                break;
            }
            case 1: {
                n = 1;
            }
        }
        selectionEvent.setEventType(n);
        if (this instanceof TextSelectionEvent) {
            object = (TextSelectionEvent)this;
            selectionEvent.setStart(((TextSelectionEvent)object).getRelativeWordStartIndex());
            selectionEvent.setEnd(((TextSelectionEvent)object).getRelativeWordEndIndex());
            selectionEvent.setSmartStart(((TextSelectionEvent)object).getRelativeSuggestedWordStartIndex());
            selectionEvent.setSmartEnd(((TextSelectionEvent)object).getRelativeSuggestedWordEndIndex());
        }
        return selectionEvent;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("{");
        stringBuilder.append("mEventCategory=");
        stringBuilder.append(this.mEventCategory);
        stringBuilder.append(", mEventTypes=");
        stringBuilder.append(Arrays.toString(this.mEntityTypes));
        stringBuilder.append(", mEventContext=");
        stringBuilder.append(this.mEventContext);
        stringBuilder.append(", mResultId=");
        stringBuilder.append(this.mResultId);
        stringBuilder.append(", mEventIndex=");
        stringBuilder.append(this.mEventIndex);
        stringBuilder.append(", mExtras=");
        stringBuilder.append(this.mExtras);
        stringBuilder.append(", mScores=");
        stringBuilder.append(Arrays.toString(this.mScores));
        stringBuilder.append(", mModelName=");
        stringBuilder.append(this.mModelName);
        stringBuilder.append(", mActionIndices=");
        stringBuilder.append(Arrays.toString(this.mActionIndices));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.getParcelToken());
        parcel.writeInt(this.mEventCategory);
        parcel.writeInt(this.mEventType);
        parcel.writeStringArray(this.mEntityTypes);
        parcel.writeParcelable(this.mEventContext, n);
        parcel.writeString(this.mResultId);
        parcel.writeInt(this.mEventIndex);
        parcel.writeInt(this.mScores.length);
        parcel.writeFloatArray(this.mScores);
        parcel.writeString(this.mModelName);
        parcel.writeIntArray(this.mActionIndices);
        Object object = this.mLocale;
        object = object == null ? null : object.toLanguageTag();
        parcel.writeString((String)object);
        parcel.writeBundle(this.mExtras);
    }

    public static abstract class Builder<T extends Builder<T>> {
        private int[] mActionIndices = new int[0];
        private String[] mEntityTypes = new String[0];
        private final int mEventCategory;
        private TextClassificationContext mEventContext;
        private int mEventIndex;
        private final int mEventType;
        private Bundle mExtras;
        private ULocale mLocale;
        private String mModelName;
        private String mResultId;
        private float[] mScores = new float[0];

        private Builder(int n, int n2) {
            this.mEventCategory = n;
            this.mEventType = n2;
        }

        abstract T self();

        public T setActionIndices(int ... arrn) {
            this.mActionIndices = new int[arrn.length];
            System.arraycopy(arrn, 0, this.mActionIndices, 0, arrn.length);
            return this.self();
        }

        public T setEntityTypes(String ... arrstring) {
            Preconditions.checkNotNull(arrstring);
            this.mEntityTypes = new String[arrstring.length];
            System.arraycopy(arrstring, 0, this.mEntityTypes, 0, arrstring.length);
            return this.self();
        }

        public T setEventContext(TextClassificationContext textClassificationContext) {
            this.mEventContext = textClassificationContext;
            return this.self();
        }

        public T setEventIndex(int n) {
            this.mEventIndex = n;
            return this.self();
        }

        public T setExtras(Bundle bundle) {
            this.mExtras = Preconditions.checkNotNull(bundle);
            return this.self();
        }

        public T setLocale(ULocale uLocale) {
            this.mLocale = uLocale;
            return this.self();
        }

        public T setModelName(String string2) {
            this.mModelName = string2;
            return this.self();
        }

        public T setResultId(String string2) {
            this.mResultId = string2;
            return this.self();
        }

        public T setScores(float ... arrf) {
            Preconditions.checkNotNull(arrf);
            this.mScores = new float[arrf.length];
            System.arraycopy(arrf, 0, this.mScores, 0, arrf.length);
            return this.self();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Category {
    }

    public static final class ConversationActionsEvent
    extends TextClassifierEvent
    implements Parcelable {
        public static final Parcelable.Creator<ConversationActionsEvent> CREATOR = new Parcelable.Creator<ConversationActionsEvent>(){

            @Override
            public ConversationActionsEvent createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new ConversationActionsEvent(parcel);
            }

            public ConversationActionsEvent[] newArray(int n) {
                return new ConversationActionsEvent[n];
            }
        };

        private ConversationActionsEvent(Parcel parcel) {
            super(parcel);
        }

        private ConversationActionsEvent(Builder builder) {
            super(builder);
        }

        public static final class Builder
        extends android.view.textclassifier.TextClassifierEvent$Builder<Builder> {
            public Builder(int n) {
                super(3, n);
            }

            public ConversationActionsEvent build() {
                return new ConversationActionsEvent(this);
            }

            @Override
            Builder self() {
                return this;
            }
        }

    }

    public static final class LanguageDetectionEvent
    extends TextClassifierEvent
    implements Parcelable {
        public static final Parcelable.Creator<LanguageDetectionEvent> CREATOR = new Parcelable.Creator<LanguageDetectionEvent>(){

            @Override
            public LanguageDetectionEvent createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new LanguageDetectionEvent(parcel);
            }

            public LanguageDetectionEvent[] newArray(int n) {
                return new LanguageDetectionEvent[n];
            }
        };

        private LanguageDetectionEvent(Parcel parcel) {
            super(parcel);
        }

        private LanguageDetectionEvent(Builder builder) {
            super(builder);
        }

        public static final class Builder
        extends android.view.textclassifier.TextClassifierEvent$Builder<Builder> {
            public Builder(int n) {
                super(4, n);
            }

            public LanguageDetectionEvent build() {
                return new LanguageDetectionEvent(this);
            }

            @Override
            Builder self() {
                return this;
            }
        }

    }

    public static final class TextLinkifyEvent
    extends TextClassifierEvent
    implements Parcelable {
        public static final Parcelable.Creator<TextLinkifyEvent> CREATOR = new Parcelable.Creator<TextLinkifyEvent>(){

            @Override
            public TextLinkifyEvent createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new TextLinkifyEvent(parcel);
            }

            public TextLinkifyEvent[] newArray(int n) {
                return new TextLinkifyEvent[n];
            }
        };

        private TextLinkifyEvent(Parcel parcel) {
            super(parcel);
        }

        private TextLinkifyEvent(Builder builder) {
            super(builder);
        }

        public static final class Builder
        extends android.view.textclassifier.TextClassifierEvent$Builder<Builder> {
            public Builder(int n) {
                super(2, n);
            }

            public TextLinkifyEvent build() {
                return new TextLinkifyEvent(this);
            }

            @Override
            Builder self() {
                return this;
            }
        }

    }

    public static final class TextSelectionEvent
    extends TextClassifierEvent
    implements Parcelable {
        public static final Parcelable.Creator<TextSelectionEvent> CREATOR = new Parcelable.Creator<TextSelectionEvent>(){

            @Override
            public TextSelectionEvent createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new TextSelectionEvent(parcel);
            }

            public TextSelectionEvent[] newArray(int n) {
                return new TextSelectionEvent[n];
            }
        };
        final int mRelativeSuggestedWordEndIndex;
        final int mRelativeSuggestedWordStartIndex;
        final int mRelativeWordEndIndex;
        final int mRelativeWordStartIndex;

        private TextSelectionEvent(Parcel parcel) {
            super(parcel);
            this.mRelativeWordStartIndex = parcel.readInt();
            this.mRelativeWordEndIndex = parcel.readInt();
            this.mRelativeSuggestedWordStartIndex = parcel.readInt();
            this.mRelativeSuggestedWordEndIndex = parcel.readInt();
        }

        private TextSelectionEvent(Builder builder) {
            super(builder);
            this.mRelativeWordStartIndex = builder.mRelativeWordStartIndex;
            this.mRelativeWordEndIndex = builder.mRelativeWordEndIndex;
            this.mRelativeSuggestedWordStartIndex = builder.mRelativeSuggestedWordStartIndex;
            this.mRelativeSuggestedWordEndIndex = builder.mRelativeSuggestedWordEndIndex;
        }

        public int getRelativeSuggestedWordEndIndex() {
            return this.mRelativeSuggestedWordEndIndex;
        }

        public int getRelativeSuggestedWordStartIndex() {
            return this.mRelativeSuggestedWordStartIndex;
        }

        public int getRelativeWordEndIndex() {
            return this.mRelativeWordEndIndex;
        }

        public int getRelativeWordStartIndex() {
            return this.mRelativeWordStartIndex;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.mRelativeWordStartIndex);
            parcel.writeInt(this.mRelativeWordEndIndex);
            parcel.writeInt(this.mRelativeSuggestedWordStartIndex);
            parcel.writeInt(this.mRelativeSuggestedWordEndIndex);
        }

        public static final class Builder
        extends android.view.textclassifier.TextClassifierEvent$Builder<Builder> {
            int mRelativeSuggestedWordEndIndex;
            int mRelativeSuggestedWordStartIndex;
            int mRelativeWordEndIndex;
            int mRelativeWordStartIndex;

            public Builder(int n) {
                super(1, n);
            }

            public TextSelectionEvent build() {
                return new TextSelectionEvent(this);
            }

            @Override
            Builder self() {
                return this;
            }

            public Builder setRelativeSuggestedWordEndIndex(int n) {
                this.mRelativeSuggestedWordEndIndex = n;
                return this;
            }

            public Builder setRelativeSuggestedWordStartIndex(int n) {
                this.mRelativeSuggestedWordStartIndex = n;
                return this;
            }

            public Builder setRelativeWordEndIndex(int n) {
                this.mRelativeWordEndIndex = n;
                return this;
            }

            public Builder setRelativeWordStartIndex(int n) {
                this.mRelativeWordStartIndex = n;
                return this;
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Type {
    }

}

