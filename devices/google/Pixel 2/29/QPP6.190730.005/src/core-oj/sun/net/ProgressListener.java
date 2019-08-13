/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.util.EventListener;
import sun.net.ProgressEvent;

public interface ProgressListener
extends EventListener {
    public void progressFinish(ProgressEvent var1);

    public void progressStart(ProgressEvent var1);

    public void progressUpdate(ProgressEvent var1);
}

