/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public final class UsageEvents
implements Parcelable {
    public static final Parcelable.Creator<UsageEvents> CREATOR = new Parcelable.Creator<UsageEvents>(){

        @Override
        public UsageEvents createFromParcel(Parcel parcel) {
            return new UsageEvents(parcel);
        }

        public UsageEvents[] newArray(int n) {
            return new UsageEvents[n];
        }
    };
    public static final String INSTANT_APP_CLASS_NAME = "android.instant_class";
    public static final String INSTANT_APP_PACKAGE_NAME = "android.instant_app";
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final int mEventCount;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private List<Event> mEventsToWrite = null;
    private final boolean mIncludeTaskRoots;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mIndex = 0;
    @UnsupportedAppUsage
    private Parcel mParcel = null;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String[] mStringPool;

    UsageEvents() {
        this.mEventCount = 0;
        this.mIncludeTaskRoots = true;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public UsageEvents(Parcel parcel) {
        byte[] arrby = parcel.readBlob();
        parcel = Parcel.obtain();
        parcel.unmarshall(arrby, 0, arrby.length);
        parcel.setDataPosition(0);
        this.mEventCount = parcel.readInt();
        this.mIndex = parcel.readInt();
        if (this.mEventCount > 0) {
            this.mStringPool = parcel.createStringArray();
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            this.mParcel = Parcel.obtain();
            this.mParcel.setDataPosition(0);
            this.mParcel.appendFrom(parcel, parcel.dataPosition(), n);
            parcel = this.mParcel;
            parcel.setDataSize(parcel.dataPosition());
            this.mParcel.setDataPosition(n2);
        }
        this.mIncludeTaskRoots = true;
    }

    public UsageEvents(List<Event> list, String[] arrstring) {
        this(list, arrstring, false);
    }

    public UsageEvents(List<Event> list, String[] arrstring, boolean bl) {
        this.mStringPool = arrstring;
        this.mEventCount = list.size();
        this.mEventsToWrite = list;
        this.mIncludeTaskRoots = bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int findStringIndex(String string2) {
        int n = Arrays.binarySearch(this.mStringPool, string2);
        if (n >= 0) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("String '");
        stringBuilder.append(string2);
        stringBuilder.append("' is not in the string pool");
        throw new IllegalStateException(stringBuilder.toString());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private void readEventFromParcel(Parcel parcel, Event event) {
        int n = parcel.readInt();
        event.mPackage = n >= 0 ? this.mStringPool[n] : null;
        n = parcel.readInt();
        event.mClass = n >= 0 ? this.mStringPool[n] : null;
        event.mInstanceId = parcel.readInt();
        n = parcel.readInt();
        event.mTaskRootPackage = n >= 0 ? this.mStringPool[n] : null;
        n = parcel.readInt();
        event.mTaskRootClass = n >= 0 ? this.mStringPool[n] : null;
        event.mEventType = parcel.readInt();
        event.mTimeStamp = parcel.readLong();
        event.mConfiguration = null;
        event.mShortcutId = null;
        event.mAction = null;
        event.mContentType = null;
        event.mContentAnnotations = null;
        event.mNotificationChannelId = null;
        n = event.mEventType;
        if (n != 5) {
            if (n != 8) {
                if (n != 9) {
                    if (n != 11) {
                        if (n == 12) {
                            event.mNotificationChannelId = parcel.readString();
                        }
                    } else {
                        event.mBucketAndReason = parcel.readInt();
                    }
                } else {
                    event.mAction = parcel.readString();
                    event.mContentType = parcel.readString();
                    event.mContentAnnotations = parcel.createStringArray();
                }
            } else {
                event.mShortcutId = parcel.readString();
            }
        } else {
            event.mConfiguration = Configuration.CREATOR.createFromParcel(parcel);
        }
        event.mFlags = parcel.readInt();
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private void writeEventToParcel(Event event, Parcel parcel, int n) {
        int n2 = event.mPackage != null ? this.findStringIndex(event.mPackage) : -1;
        int n3 = event.mClass != null ? this.findStringIndex(event.mClass) : -1;
        int n4 = this.mIncludeTaskRoots && event.mTaskRootPackage != null ? this.findStringIndex(event.mTaskRootPackage) : -1;
        int n5 = this.mIncludeTaskRoots && event.mTaskRootClass != null ? this.findStringIndex(event.mTaskRootClass) : -1;
        parcel.writeInt(n2);
        parcel.writeInt(n3);
        parcel.writeInt(event.mInstanceId);
        parcel.writeInt(n4);
        parcel.writeInt(n5);
        parcel.writeInt(event.mEventType);
        parcel.writeLong(event.mTimeStamp);
        n2 = event.mEventType;
        if (n2 != 5) {
            if (n2 != 8) {
                if (n2 != 9) {
                    if (n2 != 11) {
                        if (n2 == 12) {
                            parcel.writeString(event.mNotificationChannelId);
                        }
                    } else {
                        parcel.writeInt(event.mBucketAndReason);
                    }
                } else {
                    parcel.writeString(event.mAction);
                    parcel.writeString(event.mContentType);
                    parcel.writeStringArray(event.mContentAnnotations);
                }
            } else {
                parcel.writeString(event.mShortcutId);
            }
        } else {
            event.mConfiguration.writeToParcel(parcel, n);
        }
        parcel.writeInt(event.mFlags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean getNextEvent(Event event) {
        if (this.mIndex >= this.mEventCount) {
            return false;
        }
        this.readEventFromParcel(this.mParcel, event);
        ++this.mIndex;
        if (this.mIndex >= this.mEventCount) {
            this.mParcel.recycle();
            this.mParcel = null;
        }
        return true;
    }

    public boolean hasNextEvent() {
        boolean bl = this.mIndex < this.mEventCount;
        return bl;
    }

    public void resetToStart() {
        this.mIndex = 0;
        Parcel parcel = this.mParcel;
        if (parcel != null) {
            parcel.setDataPosition(0);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Parcel parcel2 = Parcel.obtain();
        parcel2.writeInt(this.mEventCount);
        parcel2.writeInt(this.mIndex);
        if (this.mEventCount > 0) {
            parcel2.writeStringArray(this.mStringPool);
            if (this.mEventsToWrite != null) {
                Parcel parcel3 = Parcel.obtain();
                parcel3.setDataPosition(0);
                int n2 = 0;
                do {
                    if (n2 >= this.mEventCount) break;
                    this.writeEventToParcel(this.mEventsToWrite.get(n2), parcel3, n);
                    ++n2;
                } while (true);
                try {
                    n = parcel3.dataPosition();
                    parcel2.writeInt(n);
                    parcel2.writeInt(0);
                    parcel2.appendFrom(parcel3, 0, n);
                }
                catch (Throwable throwable) {
                    throw throwable;
                }
                finally {
                    parcel3.recycle();
                }
            } else {
                Parcel parcel4 = this.mParcel;
                if (parcel4 != null) {
                    parcel2.writeInt(parcel4.dataSize());
                    parcel2.writeInt(this.mParcel.dataPosition());
                    parcel4 = this.mParcel;
                    parcel2.appendFrom(parcel4, 0, parcel4.dataSize());
                } else {
                    throw new IllegalStateException("Either mParcel or mEventsToWrite must not be null");
                }
            }
        }
        parcel.writeBlob(parcel2.marshall());
    }

    public static final class Event {
        public static final int ACTIVITY_DESTROYED = 24;
        public static final int ACTIVITY_PAUSED = 2;
        public static final int ACTIVITY_RESUMED = 1;
        public static final int ACTIVITY_STOPPED = 23;
        public static final int CHOOSER_ACTION = 9;
        public static final int CONFIGURATION_CHANGE = 5;
        public static final int CONTINUE_PREVIOUS_DAY = 4;
        public static final int CONTINUING_FOREGROUND_SERVICE = 21;
        public static final String DEVICE_EVENT_PACKAGE_NAME = "android";
        public static final int DEVICE_SHUTDOWN = 26;
        public static final int DEVICE_STARTUP = 27;
        public static final int END_OF_DAY = 3;
        public static final int FLAG_IS_PACKAGE_INSTANT_APP = 1;
        public static final int FLUSH_TO_DISK = 25;
        public static final int FOREGROUND_SERVICE_START = 19;
        public static final int FOREGROUND_SERVICE_STOP = 20;
        public static final int KEYGUARD_HIDDEN = 18;
        public static final int KEYGUARD_SHOWN = 17;
        public static final int MAX_EVENT_TYPE = 27;
        @Deprecated
        public static final int MOVE_TO_BACKGROUND = 2;
        @Deprecated
        public static final int MOVE_TO_FOREGROUND = 1;
        public static final int NONE = 0;
        @SystemApi
        public static final int NOTIFICATION_INTERRUPTION = 12;
        @SystemApi
        public static final int NOTIFICATION_SEEN = 10;
        public static final int ROLLOVER_FOREGROUND_SERVICE = 22;
        public static final int SCREEN_INTERACTIVE = 15;
        public static final int SCREEN_NON_INTERACTIVE = 16;
        public static final int SHORTCUT_INVOCATION = 8;
        @SystemApi
        public static final int SLICE_PINNED = 14;
        @SystemApi
        public static final int SLICE_PINNED_PRIV = 13;
        public static final int STANDBY_BUCKET_CHANGED = 11;
        @SystemApi
        public static final int SYSTEM_INTERACTION = 6;
        public static final int USER_INTERACTION = 7;
        public static final int VALID_FLAG_BITS = 1;
        public String mAction;
        public int mBucketAndReason;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public String mClass;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public Configuration mConfiguration;
        public String[] mContentAnnotations;
        public String mContentType;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public int mEventType;
        public int mFlags;
        public int mInstanceId;
        public String mNotificationChannelId;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public String mPackage;
        public String mShortcutId;
        public String mTaskRootClass;
        public String mTaskRootPackage;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public long mTimeStamp;

        public Event() {
        }

        public Event(int n, long l) {
            this.mEventType = n;
            this.mTimeStamp = l;
        }

        public Event(Event event) {
            this.mPackage = event.mPackage;
            this.mClass = event.mClass;
            this.mInstanceId = event.mInstanceId;
            this.mTaskRootPackage = event.mTaskRootPackage;
            this.mTaskRootClass = event.mTaskRootClass;
            this.mTimeStamp = event.mTimeStamp;
            this.mEventType = event.mEventType;
            this.mConfiguration = event.mConfiguration;
            this.mShortcutId = event.mShortcutId;
            this.mAction = event.mAction;
            this.mContentType = event.mContentType;
            this.mContentAnnotations = event.mContentAnnotations;
            this.mFlags = event.mFlags;
            this.mBucketAndReason = event.mBucketAndReason;
            this.mNotificationChannelId = event.mNotificationChannelId;
        }

        public int getAppStandbyBucket() {
            return (this.mBucketAndReason & -65536) >>> 16;
        }

        public String getClassName() {
            return this.mClass;
        }

        public Configuration getConfiguration() {
            return this.mConfiguration;
        }

        public int getEventType() {
            return this.mEventType;
        }

        @SystemApi
        public int getInstanceId() {
            return this.mInstanceId;
        }

        @SystemApi
        public String getNotificationChannelId() {
            return this.mNotificationChannelId;
        }

        public Event getObfuscatedIfInstantApp() {
            if (!this.isInstantApp()) {
                return this;
            }
            Event event = new Event(this);
            event.mPackage = UsageEvents.INSTANT_APP_PACKAGE_NAME;
            event.mClass = UsageEvents.INSTANT_APP_CLASS_NAME;
            return event;
        }

        public String getPackageName() {
            return this.mPackage;
        }

        public String getShortcutId() {
            return this.mShortcutId;
        }

        public int getStandbyBucket() {
            return (this.mBucketAndReason & -65536) >>> 16;
        }

        public int getStandbyReason() {
            return this.mBucketAndReason & 65535;
        }

        @SystemApi
        public String getTaskRootClassName() {
            return this.mTaskRootClass;
        }

        @SystemApi
        public String getTaskRootPackageName() {
            return this.mTaskRootPackage;
        }

        public long getTimeStamp() {
            return this.mTimeStamp;
        }

        @SystemApi
        public boolean isInstantApp() {
            int n = this.mFlags;
            boolean bl = true;
            if ((n & 1) != 1) {
                bl = false;
            }
            return bl;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface EventFlags {
        }

    }

}

