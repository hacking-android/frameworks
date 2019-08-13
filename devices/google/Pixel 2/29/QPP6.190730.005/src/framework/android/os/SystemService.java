/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.SystemClock;
import android.os.SystemProperties;
import com.google.android.collect.Maps;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class SystemService {
    private static Object sPropertyLock;
    private static HashMap<String, State> sStates;

    static {
        sStates = Maps.newHashMap();
        sPropertyLock = new Object();
        SystemProperties.addChangeCallback(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object = sPropertyLock;
                synchronized (object) {
                    sPropertyLock.notifyAll();
                    return;
                }
            }
        });
    }

    public static State getState(String object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("init.svc.");
        stringBuilder.append((String)object);
        object = SystemProperties.get(stringBuilder.toString());
        object = sStates.get(object);
        if (object != null) {
            return object;
        }
        return State.STOPPED;
    }

    public static boolean isRunning(String string2) {
        return State.RUNNING.equals((Object)SystemService.getState(string2));
    }

    public static boolean isStopped(String string2) {
        return State.STOPPED.equals((Object)SystemService.getState(string2));
    }

    public static void restart(String string2) {
        SystemProperties.set("ctl.restart", string2);
    }

    @UnsupportedAppUsage
    public static void start(String string2) {
        SystemProperties.set("ctl.start", string2);
    }

    @UnsupportedAppUsage
    public static void stop(String string2) {
        SystemProperties.set("ctl.stop", string2);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void waitForAnyStopped(String ... arrstring) {
        do {
            Object object = sPropertyLock;
            synchronized (object) {
                for (String string2 : arrstring) {
                    if (!State.STOPPED.equals((Object)SystemService.getState(string2))) continue;
                    return;
                }
                try {
                    sPropertyLock.wait();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void waitForState(String string2, State state, long l) throws TimeoutException {
        long l2 = SystemClock.elapsedRealtime();
        do {
            Object object = sPropertyLock;
            synchronized (object) {
                State state2 = SystemService.getState(string2);
                if (state.equals((Object)state2)) {
                    return;
                }
                long l3 = SystemClock.elapsedRealtime();
                if (l3 >= l2 + l) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Service ");
                    stringBuilder.append(string2);
                    stringBuilder.append(" currently ");
                    stringBuilder.append((Object)state2);
                    stringBuilder.append("; waited ");
                    stringBuilder.append(l);
                    stringBuilder.append("ms for ");
                    stringBuilder.append((Object)state);
                    TimeoutException timeoutException = new TimeoutException(stringBuilder.toString());
                    throw timeoutException;
                }
                try {
                    sPropertyLock.wait(l);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
        } while (true);
    }

    public static enum State {
        RUNNING("running"),
        STOPPING("stopping"),
        STOPPED("stopped"),
        RESTARTING("restarting");
        

        private State(String string3) {
            sStates.put(string3, this);
        }
    }

}

