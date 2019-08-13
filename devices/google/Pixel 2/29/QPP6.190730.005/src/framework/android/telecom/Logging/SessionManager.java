/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telecom.Logging.-$
 *  android.telecom.Logging.-$$Lambda
 *  android.telecom.Logging.-$$Lambda$L5F_SL2jOCUETYvgdB36aGwY50E
 */
package android.telecom.Logging;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telecom.Log;
import android.telecom.Logging.-$;
import android.telecom.Logging.Session;
import android.telecom.Logging._$$Lambda$L5F_SL2jOCUETYvgdB36aGwY50E;
import android.telecom.Logging._$$Lambda$SessionManager$VyH2gT1EjIvzDy_C9JfTT60CISM;
import android.telecom.Logging._$$Lambda$SessionManager$hhtZwTEbvO_fLNlAvB6Do9_2gW4;
import android.util.Base64;
import com.android.internal.annotations.VisibleForTesting;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final long DEFAULT_SESSION_TIMEOUT_MS = 30000L;
    private static final String LOGGING_TAG = "Logging";
    private static final long SESSION_ID_ROLLOVER_THRESHOLD = 262144L;
    private static final String TIMEOUTS_PREFIX = "telecom.";
    @VisibleForTesting
    public Runnable mCleanStaleSessions = new _$$Lambda$SessionManager$VyH2gT1EjIvzDy_C9JfTT60CISM(this);
    private Context mContext;
    @VisibleForTesting
    public ICurrentThreadId mCurrentThreadId = _$$Lambda$L5F_SL2jOCUETYvgdB36aGwY50E.INSTANCE;
    private Handler mSessionCleanupHandler = new Handler(Looper.getMainLooper());
    private ISessionCleanupTimeoutMs mSessionCleanupTimeoutMs = new _$$Lambda$SessionManager$hhtZwTEbvO_fLNlAvB6Do9_2gW4(this);
    private List<ISessionListener> mSessionListeners = new ArrayList<ISessionListener>();
    @VisibleForTesting
    public ConcurrentHashMap<Integer, Session> mSessionMapper = new ConcurrentHashMap(100);
    private int sCodeEntryCounter = 0;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Session createSubsession(boolean bl) {
        synchronized (this) {
            int n = this.getCallingThreadId();
            Object object = this.mSessionMapper.get(n);
            if (object == null) {
                Log.d(LOGGING_TAG, "Log.createSubsession was called with no session active.", new Object[0]);
                return null;
            }
            Session session = new Session(((Session)object).getNextChildId(), ((Session)object).getShortMethodName(), System.currentTimeMillis(), bl, null);
            ((Session)object).addChild(session);
            session.setParentSession((Session)object);
            if (!bl) {
                object = new StringBuilder();
                ((StringBuilder)object).append("CREATE_SUBSESSION ");
                ((StringBuilder)object).append(session.toString());
                Log.v(LOGGING_TAG, ((StringBuilder)object).toString(), new Object[0]);
            } else {
                Log.v(LOGGING_TAG, "CREATE_SUBSESSION (Invisible subsession)", new Object[0]);
            }
            return session;
        }
    }

    private void endParentSessions(Session session) {
        if (session.isSessionCompleted() && session.getChildSessions().size() == 0) {
            Object object = session.getParentSession();
            if (object != null) {
                session.setParentSession(null);
                ((Session)object).removeChild(session);
                if (((Session)object).isExternal()) {
                    long l = System.currentTimeMillis();
                    long l2 = session.getExecutionStartTimeMilliseconds();
                    this.notifySessionCompleteListeners(session.getShortMethodName(), l - l2);
                }
                this.endParentSessions((Session)object);
            } else {
                long l = System.currentTimeMillis() - session.getExecutionStartTimeMilliseconds();
                object = new StringBuilder();
                ((StringBuilder)object).append("END_SESSION (dur: ");
                ((StringBuilder)object).append(l);
                ((StringBuilder)object).append(" ms): ");
                ((StringBuilder)object).append(session.toString());
                Log.d(LOGGING_TAG, ((StringBuilder)object).toString(), new Object[0]);
                if (!session.isExternal()) {
                    this.notifySessionCompleteListeners(session.getShortMethodName(), l);
                }
            }
            return;
        }
    }

    private String getBase64Encoding(int n) {
        return Base64.encodeToString(Arrays.copyOfRange(ByteBuffer.allocate(4).putInt(n).array(), 2, 4), 3);
    }

    private int getCallingThreadId() {
        return this.mCurrentThreadId.get();
    }

    private long getCleanupTimeout(Context context) {
        return Settings.Secure.getLong(context.getContentResolver(), "telecom.stale_session_cleanup_timeout_millis", 30000L);
    }

    private String getNextSessionID() {
        synchronized (this) {
            int n = this.sCodeEntryCounter;
            this.sCodeEntryCounter = n + 1;
            Integer n2 = n;
            Object object = n2;
            if ((long)n2.intValue() >= 262144L) {
                this.restartSessionCounter();
                n = this.sCodeEntryCounter;
                this.sCodeEntryCounter = n + 1;
                object = n;
            }
            object = this.getBase64Encoding((Integer)object);
            return object;
        }
    }

    private long getSessionCleanupTimeoutMs() {
        return this.mSessionCleanupTimeoutMs.get();
    }

    private void notifySessionCompleteListeners(String string2, long l) {
        Iterator<ISessionListener> iterator = this.mSessionListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().sessionComplete(string2, l);
        }
    }

    private void resetStaleSessionTimer() {
        synchronized (this) {
            this.mSessionCleanupHandler.removeCallbacksAndMessages(null);
            if (this.mCleanStaleSessions != null) {
                this.mSessionCleanupHandler.postDelayed(this.mCleanStaleSessions, this.getSessionCleanupTimeoutMs());
            }
            return;
        }
    }

    private void restartSessionCounter() {
        synchronized (this) {
            this.sCodeEntryCounter = 0;
            return;
        }
    }

    public void cancelSubsession(Session session) {
        synchronized (this) {
            if (session == null) {
                return;
            }
            session.markSessionCompleted(-1L);
            this.endParentSessions(session);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void cleanupStaleSessions(long l) {
        synchronized (this) {
            String string2 = "Stale Sessions Cleaned:\n";
            boolean bl = false;
            long l2 = System.currentTimeMillis();
            Iterator<Map.Entry<Integer, Session>> iterator = this.mSessionMapper.entrySet().iterator();
            while (iterator.hasNext()) {
                Session session = iterator.next().getValue();
                CharSequence charSequence = string2;
                if (l2 - session.getExecutionStartTimeMilliseconds() > l) {
                    iterator.remove();
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append(session.printFullSessionTree());
                    ((StringBuilder)charSequence).append("\n");
                    charSequence = ((StringBuilder)charSequence).toString();
                    bl = true;
                }
                string2 = charSequence;
            }
            if (bl) {
                Log.w(LOGGING_TAG, string2, new Object[0]);
            } else {
                Log.v(LOGGING_TAG, "No stale logging sessions needed to be cleaned...", new Object[0]);
            }
            return;
        }
    }

    public void continueSession(Session object, String string2) {
        synchronized (this) {
            if (object == null) {
                return;
            }
            this.resetStaleSessionTimer();
            ((Session)object).setShortMethodName(string2);
            ((Session)object).setExecutionStartTimeMs(System.currentTimeMillis());
            if (((Session)object).getParentSession() == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Log.continueSession was called with no session active for method ");
                ((StringBuilder)object).append(string2);
                Log.i(LOGGING_TAG, ((StringBuilder)object).toString(), new Object[0]);
                return;
            }
            this.mSessionMapper.put(this.getCallingThreadId(), (Session)object);
            if (!((Session)object).isStartedFromActiveSession()) {
                Log.v(LOGGING_TAG, "CONTINUE_SUBSESSION", new Object[0]);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("CONTINUE_SUBSESSION (Invisible Subsession) with Method ");
                ((StringBuilder)object).append(string2);
                Log.v(LOGGING_TAG, ((StringBuilder)object).toString(), new Object[0]);
            }
            return;
        }
    }

    public Session createSubsession() {
        return this.createSubsession(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void endSession() {
        synchronized (this) {
            Object object;
            int n = this.getCallingThreadId();
            Session session = this.mSessionMapper.get(n);
            if (session == null) {
                Log.w(LOGGING_TAG, "Log.endSession was called with no session active.", new Object[0]);
                return;
            }
            session.markSessionCompleted(System.currentTimeMillis());
            if (!session.isStartedFromActiveSession()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("END_SUBSESSION (dur: ");
                ((StringBuilder)object).append(session.getLocalExecutionTime());
                ((StringBuilder)object).append(" mS)");
                Log.v(LOGGING_TAG, ((StringBuilder)object).toString(), new Object[0]);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("END_SUBSESSION (Invisible Subsession) (dur: ");
                ((StringBuilder)object).append(session.getLocalExecutionTime());
                ((StringBuilder)object).append(" ms)");
                Log.v(LOGGING_TAG, ((StringBuilder)object).toString(), new Object[0]);
            }
            object = session.getParentSession();
            this.mSessionMapper.remove(n);
            this.endParentSessions(session);
            if (object != null && !((Session)object).isSessionCompleted() && session.isStartedFromActiveSession()) {
                this.mSessionMapper.put(n, (Session)object);
            }
            return;
        }
    }

    public Session.Info getExternalSession() {
        synchronized (this) {
            Object object;
            block5 : {
                int n = this.getCallingThreadId();
                object = this.mSessionMapper.get(n);
                if (object != null) break block5;
                Log.d(LOGGING_TAG, "Log.getExternalSession was called with no session active.", new Object[0]);
                return null;
            }
            object = ((Session)object).getInfo();
            return object;
        }
    }

    public String getSessionId() {
        Object object = this.mSessionMapper.get(this.getCallingThreadId());
        object = object != null ? ((Session)object).toString() : "";
        return object;
    }

    public /* synthetic */ void lambda$new$0$SessionManager() {
        this.cleanupStaleSessions(this.getSessionCleanupTimeoutMs());
    }

    public /* synthetic */ long lambda$new$1$SessionManager() {
        Context context = this.mContext;
        if (context == null) {
            return 30000L;
        }
        return this.getCleanupTimeout(context);
    }

    public void registerSessionListener(ISessionListener iSessionListener) {
        synchronized (this) {
            if (iSessionListener != null) {
                this.mSessionListeners.add(iSessionListener);
            }
            return;
        }
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void startExternalSession(Session.Info info, String string2) {
        synchronized (this) {
            if (info == null) {
                return;
            }
            int n = this.getCallingThreadId();
            if (this.mSessionMapper.get(n) != null) {
                Log.w(LOGGING_TAG, "trying to start an external session with a session already active.", new Object[0]);
                return;
            }
            Log.d(LOGGING_TAG, "START_EXTERNAL_SESSION", new Object[0]);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("E-");
            stringBuilder.append(info.sessionId);
            Session session = new Session(stringBuilder.toString(), info.methodPath, System.currentTimeMillis(), false, null);
            session.setIsExternal(true);
            session.markSessionCompleted(-1L);
            this.mSessionMapper.put(n, session);
            this.continueSession(this.createSubsession(), string2);
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startSession(Session.Info info, String string2, String string3) {
        synchronized (this) {
            void var2_2;
            if (info == null) {
                void var3_3;
                this.startSession((String)var2_2, (String)var3_3);
            } else {
                this.startExternalSession(info, (String)var2_2);
            }
            return;
        }
    }

    public void startSession(String string2, String string3) {
        synchronized (this) {
            this.resetStaleSessionTimer();
            int n = this.getCallingThreadId();
            if (this.mSessionMapper.get(n) != null) {
                this.continueSession(this.createSubsession(true), string2);
                return;
            }
            Log.d(LOGGING_TAG, "START_SESSION", new Object[0]);
            Session session = new Session(this.getNextSessionID(), string2, System.currentTimeMillis(), false, string3);
            this.mSessionMapper.put(n, session);
            return;
        }
    }

    public static interface ICurrentThreadId {
        public int get();
    }

    private static interface ISessionCleanupTimeoutMs {
        public long get();
    }

    public static interface ISessionIdQueryHandler {
        public String getSessionId();
    }

    public static interface ISessionListener {
        public void sessionComplete(String var1, long var2);
    }

}

