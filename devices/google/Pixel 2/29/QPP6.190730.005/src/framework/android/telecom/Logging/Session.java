/*
 * Decompiled with CFR 0.145.
 */
package android.telecom.Logging;

import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Log;
import android.text.TextUtils;
import com.android.internal.annotations.VisibleForTesting;
import java.util.AbstractList;
import java.util.ArrayList;

public class Session {
    public static final String CONTINUE_SUBSESSION = "CONTINUE_SUBSESSION";
    public static final String CREATE_SUBSESSION = "CREATE_SUBSESSION";
    public static final String END_SESSION = "END_SESSION";
    public static final String END_SUBSESSION = "END_SUBSESSION";
    public static final String EXTERNAL_INDICATOR = "E-";
    public static final String SESSION_SEPARATION_CHAR_CHILD = "_";
    public static final String START_EXTERNAL_SESSION = "START_EXTERNAL_SESSION";
    public static final String START_SESSION = "START_SESSION";
    public static final String SUBSESSION_SEPARATION_CHAR = "->";
    public static final String TRUNCATE_STRING = "...";
    public static final int UNDEFINED = -1;
    private int mChildCounter = 0;
    private ArrayList<Session> mChildSessions;
    private long mExecutionEndTimeMs = -1L;
    private long mExecutionStartTimeMs;
    private String mFullMethodPathCache;
    private boolean mIsCompleted = false;
    private boolean mIsExternal = false;
    private boolean mIsStartedFromActiveSession = false;
    private String mOwnerInfo;
    private Session mParentSession;
    private String mSessionId;
    private String mShortMethodName;

    public Session(String string2, String string3, long l, boolean bl, String string4) {
        this.setSessionId(string2);
        this.setShortMethodName(string3);
        this.mExecutionStartTimeMs = l;
        this.mParentSession = null;
        this.mChildSessions = new ArrayList(5);
        this.mIsStartedFromActiveSession = bl;
        this.mOwnerInfo = string4;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void getFullMethodPath(StringBuilder stringBuilder, boolean bl) {
        synchronized (this) {
            void var2_2;
            if (!TextUtils.isEmpty(this.mFullMethodPathCache) && var2_2 == false) {
                stringBuilder.append(this.mFullMethodPathCache);
                return;
            }
            Session session = this.getParentSession();
            boolean bl2 = false;
            if (session != null) {
                bl2 = !this.mShortMethodName.equals(session.mShortMethodName);
                session.getFullMethodPath(stringBuilder, (boolean)var2_2);
                stringBuilder.append(SUBSESSION_SEPARATION_CHAR);
            }
            if (this.isExternal()) {
                if (var2_2 != false) {
                    stringBuilder.append(TRUNCATE_STRING);
                } else {
                    stringBuilder.append("(");
                    stringBuilder.append(this.mShortMethodName);
                    stringBuilder.append(")");
                }
            } else {
                stringBuilder.append(this.mShortMethodName);
            }
            if (bl2 && var2_2 == false) {
                this.mFullMethodPathCache = stringBuilder.toString();
            }
            return;
        }
    }

    private String getFullSessionId() {
        Session session = this.mParentSession;
        if (session == null) {
            return this.mSessionId;
        }
        if (Log.VERBOSE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(session.getFullSessionId());
            stringBuilder.append(SESSION_SEPARATION_CHAR_CHILD);
            stringBuilder.append(this.mSessionId);
            return stringBuilder.toString();
        }
        return session.getFullSessionId();
    }

    private boolean isSessionExternal() {
        if (this.getParentSession() == null) {
            return this.isExternal();
        }
        return this.getParentSession().isSessionExternal();
    }

    private void printSessionTree(int n, StringBuilder stringBuilder) {
        stringBuilder.append(this.toString());
        for (Session session : this.mChildSessions) {
            stringBuilder.append("\n");
            for (int i = 0; i <= n; ++i) {
                stringBuilder.append("\t");
            }
            session.printSessionTree(n + 1, stringBuilder);
        }
    }

    public void addChild(Session session) {
        if (session != null) {
            this.mChildSessions.add(session);
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (Session)object;
            if (this.mExecutionStartTimeMs != ((Session)object).mExecutionStartTimeMs) {
                return false;
            }
            if (this.mExecutionEndTimeMs != ((Session)object).mExecutionEndTimeMs) {
                return false;
            }
            if (this.mIsCompleted != ((Session)object).mIsCompleted) {
                return false;
            }
            if (this.mChildCounter != ((Session)object).mChildCounter) {
                return false;
            }
            if (this.mIsStartedFromActiveSession != ((Session)object).mIsStartedFromActiveSession) {
                return false;
            }
            Object object2 = this.mSessionId;
            if (object2 != null ? !((String)object2).equals(((Session)object).mSessionId) : ((Session)object).mSessionId != null) {
                return false;
            }
            object2 = this.mShortMethodName;
            if (object2 != null ? !((String)object2).equals(((Session)object).mShortMethodName) : ((Session)object).mShortMethodName != null) {
                return false;
            }
            object2 = this.mParentSession;
            if (object2 != null ? !((Session)object2).equals(((Session)object).mParentSession) : ((Session)object).mParentSession != null) {
                return false;
            }
            object2 = this.mChildSessions;
            if (object2 != null ? !((AbstractList)object2).equals(((Session)object).mChildSessions) : ((Session)object).mChildSessions != null) {
                return false;
            }
            object2 = this.mOwnerInfo;
            if (object2 != null) {
                bl = ((String)object2).equals(((Session)object).mOwnerInfo);
            } else if (((Session)object).mOwnerInfo != null) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public ArrayList<Session> getChildSessions() {
        return this.mChildSessions;
    }

    public long getExecutionStartTimeMilliseconds() {
        return this.mExecutionStartTimeMs;
    }

    public String getFullMethodPath(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        this.getFullMethodPath(stringBuilder, bl);
        return stringBuilder.toString();
    }

    public Info getInfo() {
        return Info.getInfo(this);
    }

    public long getLocalExecutionTime() {
        long l = this.mExecutionEndTimeMs;
        if (l == -1L) {
            return -1L;
        }
        return l - this.mExecutionStartTimeMs;
    }

    public String getNextChildId() {
        synchronized (this) {
            int n = this.mChildCounter;
            this.mChildCounter = n + 1;
            return String.valueOf(n);
        }
    }

    public Session getParentSession() {
        return this.mParentSession;
    }

    @VisibleForTesting
    public String getSessionId() {
        return this.mSessionId;
    }

    public String getShortMethodName() {
        return this.mShortMethodName;
    }

    public int hashCode() {
        Object object = this.mSessionId;
        int n = 0;
        int n2 = object != null ? ((String)object).hashCode() : 0;
        object = this.mShortMethodName;
        int n3 = object != null ? ((String)object).hashCode() : 0;
        long l = this.mExecutionStartTimeMs;
        int n4 = (int)(l ^ l >>> 32);
        l = this.mExecutionEndTimeMs;
        int n5 = (int)(l ^ l >>> 32);
        object = this.mParentSession;
        int n6 = object != null ? ((Session)object).hashCode() : 0;
        object = this.mChildSessions;
        int n7 = object != null ? ((AbstractList)object).hashCode() : 0;
        int n8 = this.mIsCompleted;
        int n9 = this.mChildCounter;
        int n10 = this.mIsStartedFromActiveSession;
        object = this.mOwnerInfo;
        if (object != null) {
            n = ((String)object).hashCode();
        }
        return ((((((((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n;
    }

    public boolean isExternal() {
        return this.mIsExternal;
    }

    public boolean isSessionCompleted() {
        return this.mIsCompleted;
    }

    public boolean isStartedFromActiveSession() {
        return this.mIsStartedFromActiveSession;
    }

    public void markSessionCompleted(long l) {
        this.mExecutionEndTimeMs = l;
        this.mIsCompleted = true;
    }

    public String printFullSessionTree() {
        Session session = this;
        while (session.getParentSession() != null) {
            session = session.getParentSession();
        }
        return session.printSessionTree();
    }

    public String printSessionTree() {
        StringBuilder stringBuilder = new StringBuilder();
        this.printSessionTree(0, stringBuilder);
        return stringBuilder.toString();
    }

    public void removeChild(Session session) {
        if (session != null) {
            this.mChildSessions.remove(session);
        }
    }

    public void setExecutionStartTimeMs(long l) {
        this.mExecutionStartTimeMs = l;
    }

    public void setIsExternal(boolean bl) {
        this.mIsExternal = bl;
    }

    public void setParentSession(Session session) {
        this.mParentSession = session;
    }

    public void setSessionId(String string2) {
        if (string2 == null) {
            this.mSessionId = "?";
        }
        this.mSessionId = string2;
    }

    public void setShortMethodName(String string2) {
        String string3 = string2;
        if (string2 == null) {
            string3 = "";
        }
        this.mShortMethodName = string3;
    }

    public String toString() {
        Object object = this.mParentSession;
        if (object != null && this.mIsStartedFromActiveSession) {
            return ((Session)object).toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.getFullMethodPath(false));
        CharSequence charSequence = this.mOwnerInfo;
        if (charSequence != null && !((String)charSequence).isEmpty()) {
            ((StringBuilder)object).append("(InCall package: ");
            ((StringBuilder)object).append(this.mOwnerInfo);
            ((StringBuilder)object).append(")");
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(((StringBuilder)object).toString());
        ((StringBuilder)charSequence).append("@");
        ((StringBuilder)charSequence).append(this.getFullSessionId());
        return ((StringBuilder)charSequence).toString();
    }

    public static class Info
    implements Parcelable {
        public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>(){

            @Override
            public Info createFromParcel(Parcel parcel) {
                return new Info(parcel.readString(), parcel.readString());
            }

            public Info[] newArray(int n) {
                return new Info[n];
            }
        };
        public final String methodPath;
        public final String sessionId;

        private Info(String string2, String string3) {
            this.sessionId = string2;
            this.methodPath = string3;
        }

        public static Info getInfo(Session session) {
            String string2 = session.getFullSessionId();
            boolean bl = !Log.DEBUG && session.isSessionExternal();
            return new Info(string2, session.getFullMethodPath(bl));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.sessionId);
            parcel.writeString(this.methodPath);
        }

    }

}

