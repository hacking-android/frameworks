/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.app.RemoteAction;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ConversationAction
implements Parcelable {
    public static final Parcelable.Creator<ConversationAction> CREATOR = new Parcelable.Creator<ConversationAction>(){

        @Override
        public ConversationAction createFromParcel(Parcel parcel) {
            return new ConversationAction(parcel);
        }

        public ConversationAction[] newArray(int n) {
            return new ConversationAction[n];
        }
    };
    public static final String TYPE_ADD_CONTACT = "add_contact";
    public static final String TYPE_CALL_PHONE = "call_phone";
    public static final String TYPE_COPY = "copy";
    public static final String TYPE_CREATE_REMINDER = "create_reminder";
    public static final String TYPE_OPEN_URL = "open_url";
    public static final String TYPE_SEND_EMAIL = "send_email";
    public static final String TYPE_SEND_SMS = "send_sms";
    public static final String TYPE_SHARE_LOCATION = "share_location";
    public static final String TYPE_TEXT_REPLY = "text_reply";
    public static final String TYPE_TRACK_FLIGHT = "track_flight";
    public static final String TYPE_VIEW_CALENDAR = "view_calendar";
    public static final String TYPE_VIEW_MAP = "view_map";
    private final RemoteAction mAction;
    private final Bundle mExtras;
    private final float mScore;
    private final CharSequence mTextReply;
    private final String mType;

    private ConversationAction(Parcel parcel) {
        this.mType = parcel.readString();
        this.mAction = (RemoteAction)parcel.readParcelable(null);
        this.mTextReply = parcel.readCharSequence();
        this.mScore = parcel.readFloat();
        this.mExtras = parcel.readBundle();
    }

    private ConversationAction(String string2, RemoteAction remoteAction, CharSequence charSequence, float f, Bundle bundle) {
        this.mType = Preconditions.checkNotNull(string2);
        this.mAction = remoteAction;
        this.mTextReply = charSequence;
        this.mScore = f;
        this.mExtras = Preconditions.checkNotNull(bundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public RemoteAction getAction() {
        return this.mAction;
    }

    public float getConfidenceScore() {
        return this.mScore;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public CharSequence getTextReply() {
        return this.mTextReply;
    }

    public String getType() {
        return this.mType;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mType);
        parcel.writeParcelable(this.mAction, n);
        parcel.writeCharSequence(this.mTextReply);
        parcel.writeFloat(this.mScore);
        parcel.writeBundle(this.mExtras);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ActionType {
    }

    public static final class Builder {
        private RemoteAction mAction;
        private Bundle mExtras;
        private float mScore;
        private CharSequence mTextReply;
        private String mType;

        public Builder(String string2) {
            this.mType = Preconditions.checkNotNull(string2);
        }

        public ConversationAction build() {
            Bundle bundle;
            String string2 = this.mType;
            RemoteAction remoteAction = this.mAction;
            CharSequence charSequence = this.mTextReply;
            float f = this.mScore;
            Bundle bundle2 = bundle = this.mExtras;
            if (bundle == null) {
                bundle2 = Bundle.EMPTY;
            }
            return new ConversationAction(string2, remoteAction, charSequence, f, bundle2);
        }

        public Builder setAction(RemoteAction remoteAction) {
            this.mAction = remoteAction;
            return this;
        }

        public Builder setConfidenceScore(float f) {
            this.mScore = f;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setTextReply(CharSequence charSequence) {
            this.mTextReply = charSequence;
            return this;
        }
    }

}

