/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class UEventObserver {
    private static final boolean DEBUG = false;
    private static final String TAG = "UEventObserver";
    private static UEventThread sThread;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static UEventThread getThread() {
        synchronized (UEventObserver.class) {
            UEventThread uEventThread;
            if (sThread != null) return sThread;
            sThread = uEventThread = new UEventThread();
            sThread.start();
            return sThread;
        }
    }

    private static native void nativeAddMatch(String var0);

    private static native void nativeRemoveMatch(String var0);

    private static native void nativeSetup();

    private static native String nativeWaitForNextEvent();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static UEventThread peekThread() {
        synchronized (UEventObserver.class) {
            return sThread;
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.stopObserving();
            return;
        }
        finally {
            super.finalize();
        }
    }

    @UnsupportedAppUsage
    public abstract void onUEvent(UEvent var1);

    @UnsupportedAppUsage
    public final void startObserving(String string2) {
        if (string2 != null && !string2.isEmpty()) {
            UEventObserver.getThread().addObserver(string2, this);
            return;
        }
        throw new IllegalArgumentException("match substring must be non-empty");
    }

    @UnsupportedAppUsage
    public final void stopObserving() {
        UEventThread uEventThread = UEventObserver.peekThread();
        if (uEventThread != null) {
            uEventThread.removeObserver(this);
        }
    }

    public static final class UEvent {
        private final HashMap<String, String> mMap = new HashMap();

        public UEvent(String string2) {
            int n = 0;
            int n2 = string2.length();
            while (n < n2) {
                int n3 = string2.indexOf(61, n);
                int n4 = string2.indexOf(0, n);
                if (n4 < 0) break;
                if (n3 > n && n3 < n4) {
                    this.mMap.put(string2.substring(n, n3), string2.substring(n3 + 1, n4));
                }
                n = n4 + 1;
            }
        }

        @UnsupportedAppUsage
        public String get(String string2) {
            return this.mMap.get(string2);
        }

        @UnsupportedAppUsage
        public String get(String string2, String string3) {
            block0 : {
                if ((string2 = this.mMap.get(string2)) != null) break block0;
                string2 = string3;
            }
            return string2;
        }

        public String toString() {
            return this.mMap.toString();
        }
    }

    private static final class UEventThread
    extends Thread {
        private final ArrayList<Object> mKeysAndObservers = new ArrayList();
        private final ArrayList<UEventObserver> mTempObserversToSignal = new ArrayList();

        public UEventThread() {
            super(UEventObserver.TAG);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        private void sendEvent(String object) {
            int n;
            ArrayList<Object> arrayList = this.mKeysAndObservers;
            // MONITORENTER : arrayList
            int n2 = this.mKeysAndObservers.size();
            for (n = 0; n < n2; n += 2) {
                if (!((String)object).contains((String)this.mKeysAndObservers.get(n))) continue;
                UEventObserver uEventObserver = (UEventObserver)this.mKeysAndObservers.get(n + 1);
                this.mTempObserversToSignal.add(uEventObserver);
            }
            // MONITOREXIT : arrayList
            if (this.mTempObserversToSignal.isEmpty()) return;
            object = new UEvent((String)object);
            n2 = this.mTempObserversToSignal.size();
            n = 0;
            do {
                if (n >= n2) {
                    this.mTempObserversToSignal.clear();
                    return;
                }
                this.mTempObserversToSignal.get(n).onUEvent((UEvent)object);
                ++n;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void addObserver(String string2, UEventObserver uEventObserver) {
            ArrayList<Object> arrayList = this.mKeysAndObservers;
            synchronized (arrayList) {
                this.mKeysAndObservers.add(string2);
                this.mKeysAndObservers.add(uEventObserver);
                UEventObserver.nativeAddMatch(string2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void removeObserver(UEventObserver uEventObserver) {
            ArrayList<Object> arrayList = this.mKeysAndObservers;
            synchronized (arrayList) {
                int n = 0;
                while (n < this.mKeysAndObservers.size()) {
                    if (this.mKeysAndObservers.get(n + 1) == uEventObserver) {
                        this.mKeysAndObservers.remove(n + 1);
                        UEventObserver.nativeRemoveMatch((String)this.mKeysAndObservers.remove(n));
                        continue;
                    }
                    n += 2;
                }
                return;
            }
        }

        @Override
        public void run() {
            UEventObserver.nativeSetup();
            do {
                String string2;
                if ((string2 = UEventObserver.nativeWaitForNextEvent()) == null) {
                    continue;
                }
                this.sendEvent(string2);
            } while (true);
        }
    }

}

