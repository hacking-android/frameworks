/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.app.Person;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannedString;
import android.view.textclassifier.ConversationAction;
import android.view.textclassifier.TextClassifier;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ConversationActions
implements Parcelable {
    public static final Parcelable.Creator<ConversationActions> CREATOR = new Parcelable.Creator<ConversationActions>(){

        @Override
        public ConversationActions createFromParcel(Parcel parcel) {
            return new ConversationActions(parcel);
        }

        public ConversationActions[] newArray(int n) {
            return new ConversationActions[n];
        }
    };
    private final List<ConversationAction> mConversationActions;
    private final String mId;

    private ConversationActions(Parcel parcel) {
        this.mConversationActions = Collections.unmodifiableList(parcel.createTypedArrayList(ConversationAction.CREATOR));
        this.mId = parcel.readString();
    }

    public ConversationActions(List<ConversationAction> list, String string2) {
        this.mConversationActions = Collections.unmodifiableList(Preconditions.checkNotNull(list));
        this.mId = string2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<ConversationAction> getConversationActions() {
        return this.mConversationActions;
    }

    public String getId() {
        return this.mId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedList(this.mConversationActions);
        parcel.writeString(this.mId);
    }

    public static final class Message
    implements Parcelable {
        public static final Parcelable.Creator<Message> CREATOR;
        public static final Person PERSON_USER_OTHERS;
        public static final Person PERSON_USER_SELF;
        private final Person mAuthor;
        private final Bundle mExtras;
        private final ZonedDateTime mReferenceTime;
        private final CharSequence mText;

        static {
            PERSON_USER_SELF = new Person.Builder().setKey("text-classifier-conversation-actions-user-self").build();
            PERSON_USER_OTHERS = new Person.Builder().setKey("text-classifier-conversation-actions-user-others").build();
            CREATOR = new Parcelable.Creator<Message>(){

                @Override
                public Message createFromParcel(Parcel parcel) {
                    return new Message(parcel);
                }

                public Message[] newArray(int n) {
                    return new Message[n];
                }
            };
        }

        private Message(Person person, ZonedDateTime zonedDateTime, CharSequence charSequence, Bundle bundle) {
            this.mAuthor = person;
            this.mReferenceTime = zonedDateTime;
            this.mText = charSequence;
            this.mExtras = Preconditions.checkNotNull(bundle);
        }

        private Message(Parcel parcel) {
            ZonedDateTime zonedDateTime = null;
            this.mAuthor = (Person)parcel.readParcelable(null);
            if (parcel.readInt() != 0) {
                zonedDateTime = ZonedDateTime.parse(parcel.readString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
            }
            this.mReferenceTime = zonedDateTime;
            this.mText = parcel.readCharSequence();
            this.mExtras = parcel.readBundle();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public Person getAuthor() {
            return this.mAuthor;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public ZonedDateTime getReferenceTime() {
            return this.mReferenceTime;
        }

        public CharSequence getText() {
            return this.mText;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeParcelable(this.mAuthor, n);
            n = this.mReferenceTime != null ? 1 : 0;
            parcel.writeInt(n);
            ZonedDateTime zonedDateTime = this.mReferenceTime;
            if (zonedDateTime != null) {
                parcel.writeString(zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
            }
            parcel.writeCharSequence(this.mText);
            parcel.writeBundle(this.mExtras);
        }

        public static final class Builder {
            private Person mAuthor;
            private Bundle mExtras;
            private ZonedDateTime mReferenceTime;
            private CharSequence mText;

            public Builder(Person person) {
                this.mAuthor = Preconditions.checkNotNull(person);
            }

            public Message build() {
                Bundle bundle;
                Person person = this.mAuthor;
                ZonedDateTime zonedDateTime = this.mReferenceTime;
                CharSequence charSequence = this.mText;
                charSequence = charSequence == null ? null : new SpannedString(charSequence);
                Bundle bundle2 = bundle = this.mExtras;
                if (bundle == null) {
                    bundle2 = Bundle.EMPTY;
                }
                return new Message(person, zonedDateTime, charSequence, bundle2);
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public Builder setReferenceTime(ZonedDateTime zonedDateTime) {
                this.mReferenceTime = zonedDateTime;
                return this;
            }

            public Builder setText(CharSequence charSequence) {
                this.mText = charSequence;
                return this;
            }
        }

    }

    public static final class Request
    implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>(){

            @Override
            public Request createFromParcel(Parcel parcel) {
                return Request.readFromParcel(parcel);
            }

            public Request[] newArray(int n) {
                return new Request[n];
            }
        };
        public static final String HINT_FOR_IN_APP = "in_app";
        public static final String HINT_FOR_NOTIFICATION = "notification";
        private String mCallingPackageName;
        private final List<Message> mConversation;
        private Bundle mExtras;
        private final List<String> mHints;
        private final int mMaxSuggestions;
        private final TextClassifier.EntityConfig mTypeConfig;

        private Request(List<Message> list, TextClassifier.EntityConfig entityConfig, int n, List<String> list2, Bundle bundle) {
            this.mConversation = Preconditions.checkNotNull(list);
            this.mTypeConfig = Preconditions.checkNotNull(entityConfig);
            this.mMaxSuggestions = n;
            this.mHints = list2;
            this.mExtras = bundle;
        }

        private static Request readFromParcel(Parcel object) {
            ArrayList<Message> arrayList = new ArrayList<Message>();
            ((Parcel)object).readParcelableList(arrayList, null);
            TextClassifier.EntityConfig entityConfig = (TextClassifier.EntityConfig)((Parcel)object).readParcelable(null);
            int n = ((Parcel)object).readInt();
            ArrayList<String> arrayList2 = new ArrayList<String>();
            ((Parcel)object).readStringList(arrayList2);
            String string2 = ((Parcel)object).readString();
            object = new Request(arrayList, entityConfig, n, arrayList2, ((Parcel)object).readBundle());
            ((Request)object).setCallingPackageName(string2);
            return object;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getCallingPackageName() {
            return this.mCallingPackageName;
        }

        public List<Message> getConversation() {
            return this.mConversation;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public List<String> getHints() {
            return this.mHints;
        }

        public int getMaxSuggestions() {
            return this.mMaxSuggestions;
        }

        public TextClassifier.EntityConfig getTypeConfig() {
            return this.mTypeConfig;
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public void setCallingPackageName(String string2) {
            this.mCallingPackageName = string2;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeParcelableList(this.mConversation, n);
            parcel.writeParcelable(this.mTypeConfig, n);
            parcel.writeInt(this.mMaxSuggestions);
            parcel.writeStringList(this.mHints);
            parcel.writeString(this.mCallingPackageName);
            parcel.writeBundle(this.mExtras);
        }

        public static final class Builder {
            private List<Message> mConversation;
            private Bundle mExtras;
            private List<String> mHints;
            private int mMaxSuggestions = -1;
            private TextClassifier.EntityConfig mTypeConfig;

            public Builder(List<Message> list) {
                this.mConversation = Preconditions.checkNotNull(list);
            }

            public Request build() {
                Bundle bundle;
                List<Message> list = Collections.unmodifiableList(this.mConversation);
                TextClassifier.EntityConfig entityConfig = this.mTypeConfig;
                if (entityConfig == null) {
                    entityConfig = new TextClassifier.EntityConfig.Builder().build();
                }
                int n = this.mMaxSuggestions;
                List<String> list2 = this.mHints;
                list2 = list2 == null ? Collections.emptyList() : Collections.unmodifiableList(list2);
                Bundle bundle2 = bundle = this.mExtras;
                if (bundle == null) {
                    bundle2 = Bundle.EMPTY;
                }
                return new Request(list, entityConfig, n, list2, bundle2);
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public Builder setHints(List<String> list) {
                this.mHints = list;
                return this;
            }

            public Builder setMaxSuggestions(int n) {
                this.mMaxSuggestions = Preconditions.checkArgumentNonnegative(n);
                return this;
            }

            public Builder setTypeConfig(TextClassifier.EntityConfig entityConfig) {
                this.mTypeConfig = entityConfig;
                return this;
            }
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Hint {
        }

    }

}

