/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.android.internal.util.FastPrintWriter;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TransactionTracker {
    private Map<String, Long> mTraces;

    TransactionTracker() {
        this.resetTraces();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void resetTraces() {
        synchronized (this) {
            HashMap<String, Long> hashMap = new HashMap<String, Long>();
            this.mTraces = hashMap;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addTrace(Throwable object) {
        object = Log.getStackTraceString((Throwable)object);
        synchronized (this) {
            if (this.mTraces.containsKey(object)) {
                this.mTraces.put((String)object, this.mTraces.get(object) + 1L);
            } else {
                this.mTraces.put((String)object, 1L);
            }
            return;
        }
    }

    public void clearTraces() {
        this.resetTraces();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeTracesToFile(ParcelFileDescriptor object) {
        if (this.mTraces.isEmpty()) {
            return;
        }
        FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(((ParcelFileDescriptor)object).getFileDescriptor()));
        synchronized (this) {
            Iterator<String> iterator = this.mTraces.keySet().iterator();
            do {
                if (!iterator.hasNext()) {
                    // MONITOREXIT [2, 3, 4] lbl8 : MonitorExitStatement: MONITOREXIT : this
                    ((PrintWriter)fastPrintWriter).flush();
                    return;
                }
                object = iterator.next();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Count: ");
                stringBuilder.append(this.mTraces.get(object));
                fastPrintWriter.println(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("Trace: ");
                stringBuilder.append((String)object);
                fastPrintWriter.println(stringBuilder.toString());
                ((PrintWriter)fastPrintWriter).println();
            } while (true);
        }
    }
}

