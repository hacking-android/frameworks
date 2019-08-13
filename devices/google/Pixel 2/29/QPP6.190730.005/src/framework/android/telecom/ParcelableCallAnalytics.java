/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SystemApi
public class ParcelableCallAnalytics
implements Parcelable {
    public static final int CALLTYPE_INCOMING = 1;
    public static final int CALLTYPE_OUTGOING = 2;
    public static final int CALLTYPE_UNKNOWN = 0;
    public static final int CALL_SOURCE_EMERGENCY_DIALPAD = 1;
    public static final int CALL_SOURCE_EMERGENCY_SHORTCUT = 2;
    public static final int CALL_SOURCE_UNSPECIFIED = 0;
    public static final int CDMA_PHONE = 1;
    public static final Parcelable.Creator<ParcelableCallAnalytics> CREATOR = new Parcelable.Creator<ParcelableCallAnalytics>(){

        @Override
        public ParcelableCallAnalytics createFromParcel(Parcel parcel) {
            return new ParcelableCallAnalytics(parcel);
        }

        public ParcelableCallAnalytics[] newArray(int n) {
            return new ParcelableCallAnalytics[n];
        }
    };
    public static final int GSM_PHONE = 2;
    public static final int IMS_PHONE = 4;
    public static final long MILLIS_IN_1_SECOND = 1000L;
    public static final long MILLIS_IN_5_MINUTES = 300000L;
    public static final int SIP_PHONE = 8;
    public static final int STILL_CONNECTED = -1;
    public static final int THIRD_PARTY_PHONE = 16;
    private final List<AnalyticsEvent> analyticsEvents;
    private final long callDurationMillis;
    private int callSource = 0;
    private final int callTechnologies;
    private final int callTerminationCode;
    private final int callType;
    private final String connectionService;
    private final List<EventTiming> eventTimings;
    private final boolean isAdditionalCall;
    private final boolean isCreatedFromExistingConnection;
    private final boolean isEmergencyCall;
    private final boolean isInterrupted;
    private boolean isVideoCall = false;
    private final long startTimeMillis;
    private List<VideoEvent> videoEvents;

    public ParcelableCallAnalytics(long l, long l2, int n, boolean bl, boolean bl2, int n2, int n3, boolean bl3, String string2, boolean bl4, List<AnalyticsEvent> list, List<EventTiming> list2) {
        this.startTimeMillis = l;
        this.callDurationMillis = l2;
        this.callType = n;
        this.isAdditionalCall = bl;
        this.isInterrupted = bl2;
        this.callTechnologies = n2;
        this.callTerminationCode = n3;
        this.isEmergencyCall = bl3;
        this.connectionService = string2;
        this.isCreatedFromExistingConnection = bl4;
        this.analyticsEvents = list;
        this.eventTimings = list2;
    }

    public ParcelableCallAnalytics(Parcel parcel) {
        this.startTimeMillis = parcel.readLong();
        this.callDurationMillis = parcel.readLong();
        this.callType = parcel.readInt();
        this.isAdditionalCall = ParcelableCallAnalytics.readByteAsBoolean(parcel);
        this.isInterrupted = ParcelableCallAnalytics.readByteAsBoolean(parcel);
        this.callTechnologies = parcel.readInt();
        this.callTerminationCode = parcel.readInt();
        this.isEmergencyCall = ParcelableCallAnalytics.readByteAsBoolean(parcel);
        this.connectionService = parcel.readString();
        this.isCreatedFromExistingConnection = ParcelableCallAnalytics.readByteAsBoolean(parcel);
        this.analyticsEvents = new ArrayList<AnalyticsEvent>();
        parcel.readTypedList(this.analyticsEvents, AnalyticsEvent.CREATOR);
        this.eventTimings = new ArrayList<EventTiming>();
        parcel.readTypedList(this.eventTimings, EventTiming.CREATOR);
        this.isVideoCall = ParcelableCallAnalytics.readByteAsBoolean(parcel);
        this.videoEvents = new LinkedList<VideoEvent>();
        parcel.readTypedList(this.videoEvents, VideoEvent.CREATOR);
        this.callSource = parcel.readInt();
    }

    private static boolean readByteAsBoolean(Parcel parcel) {
        byte by = parcel.readByte();
        boolean bl = true;
        if (by != 1) {
            bl = false;
        }
        return bl;
    }

    private static void writeBooleanAsByte(Parcel parcel, boolean bl) {
        parcel.writeByte((byte)(bl ? 1 : 0));
    }

    public List<AnalyticsEvent> analyticsEvents() {
        return this.analyticsEvents;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getCallDurationMillis() {
        return this.callDurationMillis;
    }

    public int getCallSource() {
        return this.callSource;
    }

    public int getCallTechnologies() {
        return this.callTechnologies;
    }

    public int getCallTerminationCode() {
        return this.callTerminationCode;
    }

    public int getCallType() {
        return this.callType;
    }

    public String getConnectionService() {
        return this.connectionService;
    }

    public List<EventTiming> getEventTimings() {
        return this.eventTimings;
    }

    public long getStartTimeMillis() {
        return this.startTimeMillis;
    }

    public List<VideoEvent> getVideoEvents() {
        return this.videoEvents;
    }

    public boolean isAdditionalCall() {
        return this.isAdditionalCall;
    }

    public boolean isCreatedFromExistingConnection() {
        return this.isCreatedFromExistingConnection;
    }

    public boolean isEmergencyCall() {
        return this.isEmergencyCall;
    }

    public boolean isInterrupted() {
        return this.isInterrupted;
    }

    public boolean isVideoCall() {
        return this.isVideoCall;
    }

    public void setCallSource(int n) {
        this.callSource = n;
    }

    public void setIsVideoCall(boolean bl) {
        this.isVideoCall = bl;
    }

    public void setVideoEvents(List<VideoEvent> list) {
        this.videoEvents = list;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.startTimeMillis);
        parcel.writeLong(this.callDurationMillis);
        parcel.writeInt(this.callType);
        ParcelableCallAnalytics.writeBooleanAsByte(parcel, this.isAdditionalCall);
        ParcelableCallAnalytics.writeBooleanAsByte(parcel, this.isInterrupted);
        parcel.writeInt(this.callTechnologies);
        parcel.writeInt(this.callTerminationCode);
        ParcelableCallAnalytics.writeBooleanAsByte(parcel, this.isEmergencyCall);
        parcel.writeString(this.connectionService);
        ParcelableCallAnalytics.writeBooleanAsByte(parcel, this.isCreatedFromExistingConnection);
        parcel.writeTypedList(this.analyticsEvents);
        parcel.writeTypedList(this.eventTimings);
        ParcelableCallAnalytics.writeBooleanAsByte(parcel, this.isVideoCall);
        parcel.writeTypedList(this.videoEvents);
        parcel.writeInt(this.callSource);
    }

    public static final class AnalyticsEvent
    implements Parcelable {
        public static final int AUDIO_ROUTE_BT = 204;
        public static final int AUDIO_ROUTE_EARPIECE = 205;
        public static final int AUDIO_ROUTE_HEADSET = 206;
        public static final int AUDIO_ROUTE_SPEAKER = 207;
        public static final int BIND_CS = 5;
        public static final int BLOCK_CHECK_FINISHED = 105;
        public static final int BLOCK_CHECK_INITIATED = 104;
        public static final int CONFERENCE_WITH = 300;
        public static final Parcelable.Creator<AnalyticsEvent> CREATOR = new Parcelable.Creator<AnalyticsEvent>(){

            @Override
            public AnalyticsEvent createFromParcel(Parcel parcel) {
                return new AnalyticsEvent(parcel);
            }

            public AnalyticsEvent[] newArray(int n) {
                return new AnalyticsEvent[n];
            }
        };
        public static final int CS_BOUND = 6;
        public static final int DIRECT_TO_VM_FINISHED = 103;
        public static final int DIRECT_TO_VM_INITIATED = 102;
        public static final int FILTERING_COMPLETED = 107;
        public static final int FILTERING_INITIATED = 106;
        public static final int FILTERING_TIMED_OUT = 108;
        public static final int MUTE = 202;
        public static final int REMOTELY_HELD = 402;
        public static final int REMOTELY_UNHELD = 403;
        public static final int REQUEST_ACCEPT = 7;
        public static final int REQUEST_HOLD = 400;
        public static final int REQUEST_PULL = 500;
        public static final int REQUEST_REJECT = 8;
        public static final int REQUEST_UNHOLD = 401;
        public static final int SCREENING_COMPLETED = 101;
        public static final int SCREENING_SENT = 100;
        public static final int SET_ACTIVE = 1;
        public static final int SET_DIALING = 4;
        public static final int SET_DISCONNECTED = 2;
        public static final int SET_HOLD = 404;
        public static final int SET_PARENT = 302;
        public static final int SET_SELECT_PHONE_ACCOUNT = 0;
        public static final int SILENCE = 201;
        public static final int SKIP_RINGING = 200;
        public static final int SPLIT_CONFERENCE = 301;
        public static final int START_CONNECTION = 3;
        public static final int SWAP = 405;
        public static final int UNMUTE = 203;
        private int mEventName;
        private long mTimeSinceLastEvent;

        public AnalyticsEvent(int n, long l) {
            this.mEventName = n;
            this.mTimeSinceLastEvent = l;
        }

        AnalyticsEvent(Parcel parcel) {
            this.mEventName = parcel.readInt();
            this.mTimeSinceLastEvent = parcel.readLong();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getEventName() {
            return this.mEventName;
        }

        public long getTimeSinceLastEvent() {
            return this.mTimeSinceLastEvent;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mEventName);
            parcel.writeLong(this.mTimeSinceLastEvent);
        }

    }

    public static final class EventTiming
    implements Parcelable {
        public static final int ACCEPT_TIMING = 0;
        public static final int BIND_CS_TIMING = 6;
        public static final int BLOCK_CHECK_FINISHED_TIMING = 9;
        public static final Parcelable.Creator<EventTiming> CREATOR = new Parcelable.Creator<EventTiming>(){

            @Override
            public EventTiming createFromParcel(Parcel parcel) {
                return new EventTiming(parcel);
            }

            public EventTiming[] newArray(int n) {
                return new EventTiming[n];
            }
        };
        public static final int DIRECT_TO_VM_FINISHED_TIMING = 8;
        public static final int DISCONNECT_TIMING = 2;
        public static final int FILTERING_COMPLETED_TIMING = 10;
        public static final int FILTERING_TIMED_OUT_TIMING = 11;
        public static final int HOLD_TIMING = 3;
        public static final int INVALID = 999999;
        public static final int OUTGOING_TIME_TO_DIALING_TIMING = 5;
        public static final int REJECT_TIMING = 1;
        public static final int SCREENING_COMPLETED_TIMING = 7;
        public static final int START_CONNECTION_TO_REQUEST_DISCONNECT_TIMING = 12;
        public static final int UNHOLD_TIMING = 4;
        private int mName;
        private long mTime;

        public EventTiming(int n, long l) {
            this.mName = n;
            this.mTime = l;
        }

        private EventTiming(Parcel parcel) {
            this.mName = parcel.readInt();
            this.mTime = parcel.readLong();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getName() {
            return this.mName;
        }

        public long getTime() {
            return this.mTime;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mName);
            parcel.writeLong(this.mTime);
        }

    }

    public static final class VideoEvent
    implements Parcelable {
        public static final Parcelable.Creator<VideoEvent> CREATOR = new Parcelable.Creator<VideoEvent>(){

            @Override
            public VideoEvent createFromParcel(Parcel parcel) {
                return new VideoEvent(parcel);
            }

            public VideoEvent[] newArray(int n) {
                return new VideoEvent[n];
            }
        };
        public static final int RECEIVE_REMOTE_SESSION_MODIFY_REQUEST = 2;
        public static final int RECEIVE_REMOTE_SESSION_MODIFY_RESPONSE = 3;
        public static final int SEND_LOCAL_SESSION_MODIFY_REQUEST = 0;
        public static final int SEND_LOCAL_SESSION_MODIFY_RESPONSE = 1;
        private int mEventName;
        private long mTimeSinceLastEvent;
        private int mVideoState;

        public VideoEvent(int n, long l, int n2) {
            this.mEventName = n;
            this.mTimeSinceLastEvent = l;
            this.mVideoState = n2;
        }

        VideoEvent(Parcel parcel) {
            this.mEventName = parcel.readInt();
            this.mTimeSinceLastEvent = parcel.readLong();
            this.mVideoState = parcel.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getEventName() {
            return this.mEventName;
        }

        public long getTimeSinceLastEvent() {
            return this.mTimeSinceLastEvent;
        }

        public int getVideoState() {
            return this.mVideoState;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mEventName);
            parcel.writeLong(this.mTimeSinceLastEvent);
            parcel.writeInt(this.mVideoState);
        }

    }

}

