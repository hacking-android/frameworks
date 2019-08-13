/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Registrant;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RegistrantList {
    ArrayList registrants = new ArrayList();

    private void internalNotifyRegistrants(Object object, Throwable throwable) {
        synchronized (this) {
            int n = this.registrants.size();
            for (int i = 0; i < n; ++i) {
                ((Registrant)this.registrants.get(i)).internalNotifyRegistrant(object, throwable);
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public void add(Handler handler, int n, Object object) {
        synchronized (this) {
            Registrant registrant = new Registrant(handler, n, object);
            this.add(registrant);
            return;
        }
    }

    @UnsupportedAppUsage
    public void add(Registrant registrant) {
        synchronized (this) {
            this.removeCleared();
            this.registrants.add(registrant);
            return;
        }
    }

    @UnsupportedAppUsage
    public void addUnique(Handler handler, int n, Object object) {
        synchronized (this) {
            this.remove(handler);
            Registrant registrant = new Registrant(handler, n, object);
            this.add(registrant);
            return;
        }
    }

    public Object get(int n) {
        synchronized (this) {
            Object e = this.registrants.get(n);
            return e;
        }
    }

    public void notifyException(Throwable throwable) {
        this.internalNotifyRegistrants(null, throwable);
    }

    @UnsupportedAppUsage
    public void notifyRegistrants() {
        this.internalNotifyRegistrants(null, null);
    }

    @UnsupportedAppUsage
    public void notifyRegistrants(AsyncResult asyncResult) {
        this.internalNotifyRegistrants(asyncResult.result, asyncResult.exception);
    }

    @UnsupportedAppUsage
    public void notifyResult(Object object) {
        this.internalNotifyRegistrants(object, null);
    }

    @UnsupportedAppUsage
    public void remove(Handler handler) {
        synchronized (this) {
            int n = this.registrants.size();
            for (int i = 0; i < n; ++i) {
                Registrant registrant = (Registrant)this.registrants.get(i);
                Handler handler2 = registrant.getHandler();
                if (handler2 != null && handler2 != handler) continue;
                registrant.clear();
            }
            this.removeCleared();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void removeCleared() {
        synchronized (this) {
            int n = this.registrants.size() - 1;
            while (n >= 0) {
                if (((Registrant)this.registrants.get((int)n)).refH == null) {
                    this.registrants.remove(n);
                }
                --n;
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public int size() {
        synchronized (this) {
            int n = this.registrants.size();
            return n;
        }
    }
}

