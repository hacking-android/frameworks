/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LocalLog {
    private final ArrayList<String> mLines = new ArrayList(20);
    private final int mMaxLines;
    private final String mTag;

    public LocalLog(String string2) {
        this.mMaxLines = 20;
        this.mTag = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean dump(PrintWriter printWriter, String string2, String string3) {
        ArrayList<String> arrayList = this.mLines;
        synchronized (arrayList) {
            if (this.mLines.size() <= 0) {
                return false;
            }
            if (string2 != null) {
                printWriter.println(string2);
            }
            int n = 0;
            while (n < this.mLines.size()) {
                if (string3 != null) {
                    printWriter.print(string3);
                }
                printWriter.println(this.mLines.get(n));
                ++n;
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void w(String string2) {
        ArrayList<String> arrayList = this.mLines;
        synchronized (arrayList) {
            Slog.w(this.mTag, string2);
            if (this.mLines.size() >= 20) {
                this.mLines.remove(0);
            }
            this.mLines.add(string2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        ArrayList<String> arrayList = this.mLines;
        synchronized (arrayList) {
            int n = 0;
            do {
                if (n >= this.mLines.size()) {
                    // MONITOREXIT [3, 4, 5] lbl7 : MonitorExitStatement: MONITOREXIT : var4_3
                    protoOutputStream.end(l);
                    return;
                }
                protoOutputStream.write(2237677961217L, this.mLines.get(n));
                ++n;
            } while (true);
        }
    }
}

