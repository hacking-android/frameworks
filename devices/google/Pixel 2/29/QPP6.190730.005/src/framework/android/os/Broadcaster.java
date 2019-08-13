/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Handler;
import android.os.Message;
import java.io.PrintStream;

public class Broadcaster {
    private Registration mReg;

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void broadcast(Message message) {
        synchronized (this) {
            Handler[] arrhandler;
            if (this.mReg == null) {
                return;
            }
            int n = message.what;
            Object[] arrobject = this.mReg;
            Object object = arrobject;
            while (((Registration)object).senderWhat < n) {
                arrhandler = ((Registration)object).next;
                object = arrhandler;
                if (arrhandler != arrobject) continue;
                object = arrhandler;
                break;
            }
            if (((Registration)object).senderWhat == n) {
                arrhandler = ((Registration)object).targets;
                arrobject = ((Registration)object).targetWhats;
                int n2 = arrhandler.length;
                for (n = 0; n < n2; ++n) {
                    Handler handler = arrhandler[n];
                    object = Message.obtain();
                    ((Message)object).copyFrom(message);
                    ((Message)object).what = (int)arrobject[n];
                    handler.sendMessage((Message)object);
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void cancelRequest(int n, Handler handler, int n2) {
        synchronized (this) {
            Handler[] arrhandler;
            Object[] arrobject;
            Handler[] arrhandler2 = arrobject = (arrhandler = this.mReg);
            if (arrobject == null) {
                return;
            }
            while (arrhandler2.senderWhat < n) {
                arrhandler2 = arrobject = arrhandler2.next;
                if (arrobject != arrhandler) continue;
                arrhandler2 = arrobject;
                break;
            }
            if (arrhandler2.senderWhat == n) {
                arrhandler = arrhandler2.targets;
                arrobject = arrhandler2.targetWhats;
                int n3 = arrhandler.length;
                for (n = 0; n < n3; ++n) {
                    if (arrhandler[n] != handler || arrobject[n] != n2) continue;
                    arrhandler2.targets = new Handler[n3 - 1];
                    arrhandler2.targetWhats = new int[n3 - 1];
                    if (n > 0) {
                        System.arraycopy(arrhandler, 0, arrhandler2.targets, 0, n);
                        System.arraycopy(arrobject, 0, arrhandler2.targetWhats, 0, n);
                    }
                    if ((n2 = n3 - n - 1) == 0) break;
                    System.arraycopy(arrhandler, n + 1, arrhandler2.targets, n, n2);
                    System.arraycopy(arrobject, n + 1, arrhandler2.targetWhats, n, n2);
                    break;
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dumpRegistrations() {
        synchronized (this) {
            Registration registration = this.mReg;
            Object object = System.out;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Broadcaster ");
            ((StringBuilder)object2).append(this);
            ((StringBuilder)object2).append(" {");
            ((PrintStream)object).println(((StringBuilder)object2).toString());
            if (registration != null) {
                object2 = registration;
                do {
                    PrintStream printStream = System.out;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("    senderWhat=");
                    ((StringBuilder)object).append(((Registration)object2).senderWhat);
                    printStream.println(((StringBuilder)object).toString());
                    int n = ((Registration)object2).targets.length;
                    for (int i = 0; i < n; ++i) {
                        printStream = System.out;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("        [");
                        ((StringBuilder)object).append(((Registration)object2).targetWhats[i]);
                        ((StringBuilder)object).append("] ");
                        ((StringBuilder)object).append(((Registration)object2).targets[i]);
                        printStream.println(((StringBuilder)object).toString());
                    }
                    object2 = object = ((Registration)object2).next;
                } while (object != registration);
            }
            System.out.println("}");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void request(int n, Handler handler, int n2) {
        synchronized (this) {
            if (this.mReg == null) {
                Registration registration = new Registration();
                registration.senderWhat = n;
                registration.targets = new Handler[1];
                registration.targetWhats = new int[1];
                registration.targets[0] = handler;
                registration.targetWhats[0] = n2;
                this.mReg = registration;
                registration.next = registration;
                registration.prev = registration;
            } else {
                Handler[] arrhandler;
                int[] arrn;
                Object[] arrobject = arrn = this.mReg;
                while (arrobject.senderWhat < n) {
                    arrhandler = arrobject.next;
                    arrobject = arrhandler;
                    if (arrhandler != arrn) continue;
                    arrobject = arrhandler;
                    break;
                }
                if (arrobject.senderWhat != n) {
                    arrhandler = new Registration();
                    arrhandler.senderWhat = n;
                    arrhandler.targets = new Handler[1];
                    arrhandler.targetWhats = new int[1];
                    arrhandler.next = arrobject;
                    arrhandler.prev = arrobject.prev;
                    arrobject.prev.next = arrhandler;
                    arrobject.prev = arrhandler;
                    if (arrobject == this.mReg && arrobject.senderWhat > arrhandler.senderWhat) {
                        this.mReg = arrhandler;
                    }
                    arrobject = arrhandler;
                    n = 0;
                } else {
                    int n3 = arrobject.targets.length;
                    arrhandler = arrobject.targets;
                    arrn = arrobject.targetWhats;
                    for (n = 0; n < n3; ++n) {
                        if (arrhandler[n] != handler || arrn[n] != n2) continue;
                        return;
                    }
                    arrobject.targets = new Handler[n3 + 1];
                    System.arraycopy(arrhandler, 0, arrobject.targets, 0, n3);
                    arrobject.targetWhats = new int[n3 + 1];
                    System.arraycopy(arrn, 0, arrobject.targetWhats, 0, n3);
                    n = n3;
                }
                arrobject.targets[n] = handler;
                arrobject.targetWhats[n] = n2;
            }
            return;
        }
    }

    private class Registration {
        Registration next;
        Registration prev;
        int senderWhat;
        int[] targetWhats;
        Handler[] targets;

        private Registration() {
        }
    }

}

