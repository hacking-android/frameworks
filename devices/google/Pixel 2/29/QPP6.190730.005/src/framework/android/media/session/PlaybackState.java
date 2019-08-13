/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PlaybackState
implements Parcelable {
    public static final long ACTION_FAST_FORWARD = 64L;
    public static final long ACTION_PAUSE = 2L;
    public static final long ACTION_PLAY = 4L;
    public static final long ACTION_PLAY_FROM_MEDIA_ID = 1024L;
    public static final long ACTION_PLAY_FROM_SEARCH = 2048L;
    public static final long ACTION_PLAY_FROM_URI = 8192L;
    public static final long ACTION_PLAY_PAUSE = 512L;
    public static final long ACTION_PREPARE = 16384L;
    public static final long ACTION_PREPARE_FROM_MEDIA_ID = 32768L;
    public static final long ACTION_PREPARE_FROM_SEARCH = 65536L;
    public static final long ACTION_PREPARE_FROM_URI = 131072L;
    public static final long ACTION_REWIND = 8L;
    public static final long ACTION_SEEK_TO = 256L;
    public static final long ACTION_SET_RATING = 128L;
    public static final long ACTION_SKIP_TO_NEXT = 32L;
    public static final long ACTION_SKIP_TO_PREVIOUS = 16L;
    public static final long ACTION_SKIP_TO_QUEUE_ITEM = 4096L;
    public static final long ACTION_STOP = 1L;
    public static final Parcelable.Creator<PlaybackState> CREATOR = new Parcelable.Creator<PlaybackState>(){

        @Override
        public PlaybackState createFromParcel(Parcel parcel) {
            return new PlaybackState(parcel);
        }

        public PlaybackState[] newArray(int n) {
            return new PlaybackState[n];
        }
    };
    public static final long PLAYBACK_POSITION_UNKNOWN = -1L;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_CONNECTING = 8;
    public static final int STATE_ERROR = 7;
    public static final int STATE_FAST_FORWARDING = 4;
    public static final int STATE_NONE = 0;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_REWINDING = 5;
    public static final int STATE_SKIPPING_TO_NEXT = 10;
    public static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    public static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    public static final int STATE_STOPPED = 1;
    private static final String TAG = "PlaybackState";
    private final long mActions;
    private final long mActiveItemId;
    private final long mBufferedPosition;
    private List<CustomAction> mCustomActions;
    private final CharSequence mErrorMessage;
    private final Bundle mExtras;
    private final long mPosition;
    private final float mSpeed;
    private final int mState;
    private final long mUpdateTime;

    private PlaybackState(int n, long l, long l2, float f, long l3, long l4, List<CustomAction> list, long l5, CharSequence charSequence, Bundle bundle) {
        this.mState = n;
        this.mPosition = l;
        this.mSpeed = f;
        this.mUpdateTime = l2;
        this.mBufferedPosition = l3;
        this.mActions = l4;
        this.mCustomActions = new ArrayList<CustomAction>(list);
        this.mActiveItemId = l5;
        this.mErrorMessage = charSequence;
        this.mExtras = bundle;
    }

    private PlaybackState(Parcel parcel) {
        this.mState = parcel.readInt();
        this.mPosition = parcel.readLong();
        this.mSpeed = parcel.readFloat();
        this.mUpdateTime = parcel.readLong();
        this.mBufferedPosition = parcel.readLong();
        this.mActions = parcel.readLong();
        this.mCustomActions = parcel.createTypedArrayList(CustomAction.CREATOR);
        this.mActiveItemId = parcel.readLong();
        this.mErrorMessage = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mExtras = parcel.readBundle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getActions() {
        return this.mActions;
    }

    public long getActiveQueueItemId() {
        return this.mActiveItemId;
    }

    public long getBufferedPosition() {
        return this.mBufferedPosition;
    }

    public List<CustomAction> getCustomActions() {
        return this.mCustomActions;
    }

    public CharSequence getErrorMessage() {
        return this.mErrorMessage;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public long getLastPositionUpdateTime() {
        return this.mUpdateTime;
    }

    public float getPlaybackSpeed() {
        return this.mSpeed;
    }

    public long getPosition() {
        return this.mPosition;
    }

    public int getState() {
        return this.mState;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("PlaybackState {");
        stringBuilder.append("state=");
        stringBuilder.append(this.mState);
        stringBuilder.append(", position=");
        stringBuilder.append(this.mPosition);
        stringBuilder.append(", buffered position=");
        stringBuilder.append(this.mBufferedPosition);
        stringBuilder.append(", speed=");
        stringBuilder.append(this.mSpeed);
        stringBuilder.append(", updated=");
        stringBuilder.append(this.mUpdateTime);
        stringBuilder.append(", actions=");
        stringBuilder.append(this.mActions);
        stringBuilder.append(", custom actions=");
        stringBuilder.append(this.mCustomActions);
        stringBuilder.append(", active item id=");
        stringBuilder.append(this.mActiveItemId);
        stringBuilder.append(", error=");
        stringBuilder.append(this.mErrorMessage);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mPosition);
        parcel.writeFloat(this.mSpeed);
        parcel.writeLong(this.mUpdateTime);
        parcel.writeLong(this.mBufferedPosition);
        parcel.writeLong(this.mActions);
        parcel.writeTypedList(this.mCustomActions);
        parcel.writeLong(this.mActiveItemId);
        TextUtils.writeToParcel(this.mErrorMessage, parcel, 0);
        parcel.writeBundle(this.mExtras);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Actions {
    }

    public static final class Builder {
        private long mActions;
        private long mActiveItemId = -1L;
        private long mBufferedPosition;
        private final List<CustomAction> mCustomActions = new ArrayList<CustomAction>();
        private CharSequence mErrorMessage;
        private Bundle mExtras;
        private long mPosition;
        private float mSpeed;
        private int mState;
        private long mUpdateTime;

        public Builder() {
        }

        public Builder(PlaybackState playbackState) {
            if (playbackState == null) {
                return;
            }
            this.mState = playbackState.mState;
            this.mPosition = playbackState.mPosition;
            this.mBufferedPosition = playbackState.mBufferedPosition;
            this.mSpeed = playbackState.mSpeed;
            this.mActions = playbackState.mActions;
            if (playbackState.mCustomActions != null) {
                this.mCustomActions.addAll(playbackState.mCustomActions);
            }
            this.mErrorMessage = playbackState.mErrorMessage;
            this.mUpdateTime = playbackState.mUpdateTime;
            this.mActiveItemId = playbackState.mActiveItemId;
            this.mExtras = playbackState.mExtras;
        }

        public Builder addCustomAction(CustomAction customAction) {
            if (customAction != null) {
                this.mCustomActions.add(customAction);
                return this;
            }
            throw new IllegalArgumentException("You may not add a null CustomAction to PlaybackState.");
        }

        public Builder addCustomAction(String string2, String string3, int n) {
            return this.addCustomAction(new CustomAction(string2, string3, n, null));
        }

        public PlaybackState build() {
            return new PlaybackState(this.mState, this.mPosition, this.mUpdateTime, this.mSpeed, this.mBufferedPosition, this.mActions, this.mCustomActions, this.mActiveItemId, this.mErrorMessage, this.mExtras);
        }

        public Builder setActions(long l) {
            this.mActions = l;
            return this;
        }

        public Builder setActiveQueueItemId(long l) {
            this.mActiveItemId = l;
            return this;
        }

        public Builder setBufferedPosition(long l) {
            this.mBufferedPosition = l;
            return this;
        }

        public Builder setErrorMessage(CharSequence charSequence) {
            this.mErrorMessage = charSequence;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setState(int n, long l, float f) {
            return this.setState(n, l, f, SystemClock.elapsedRealtime());
        }

        public Builder setState(int n, long l, float f, long l2) {
            this.mState = n;
            this.mPosition = l;
            this.mUpdateTime = l2;
            this.mSpeed = f;
            return this;
        }
    }

    public static final class CustomAction
    implements Parcelable {
        public static final Parcelable.Creator<CustomAction> CREATOR = new Parcelable.Creator<CustomAction>(){

            @Override
            public CustomAction createFromParcel(Parcel parcel) {
                return new CustomAction(parcel);
            }

            public CustomAction[] newArray(int n) {
                return new CustomAction[n];
            }
        };
        private final String mAction;
        private final Bundle mExtras;
        private final int mIcon;
        private final CharSequence mName;

        private CustomAction(Parcel parcel) {
            this.mAction = parcel.readString();
            this.mName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mIcon = parcel.readInt();
            this.mExtras = parcel.readBundle();
        }

        private CustomAction(String string2, CharSequence charSequence, int n, Bundle bundle) {
            this.mAction = string2;
            this.mName = charSequence;
            this.mIcon = n;
            this.mExtras = bundle;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getAction() {
            return this.mAction;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public int getIcon() {
            return this.mIcon;
        }

        public CharSequence getName() {
            return this.mName;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Action:mName='");
            stringBuilder.append((Object)this.mName);
            stringBuilder.append(", mIcon=");
            stringBuilder.append(this.mIcon);
            stringBuilder.append(", mExtras=");
            stringBuilder.append(this.mExtras);
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mAction);
            TextUtils.writeToParcel(this.mName, parcel, n);
            parcel.writeInt(this.mIcon);
            parcel.writeBundle(this.mExtras);
        }

        public static final class Builder {
            private final String mAction;
            private Bundle mExtras;
            private final int mIcon;
            private final CharSequence mName;

            public Builder(String string2, CharSequence charSequence, int n) {
                if (!TextUtils.isEmpty(string2)) {
                    if (!TextUtils.isEmpty(charSequence)) {
                        if (n != 0) {
                            this.mAction = string2;
                            this.mName = charSequence;
                            this.mIcon = n;
                            return;
                        }
                        throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
                    }
                    throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
                }
                throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
            }

            public CustomAction build() {
                return new CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras);
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface State {
    }

}

