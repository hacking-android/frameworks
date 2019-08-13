/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import sun.net.DefaultProgressMeteringPolicy;
import sun.net.ProgressEvent;
import sun.net.ProgressListener;
import sun.net.ProgressMeteringPolicy;
import sun.net.ProgressSource;

public class ProgressMonitor {
    private static ProgressMeteringPolicy meteringPolicy = new DefaultProgressMeteringPolicy();
    private static ProgressMonitor pm = new ProgressMonitor();
    private ArrayList<ProgressListener> progressListenerList = new ArrayList();
    private ArrayList<ProgressSource> progressSourceList = new ArrayList();

    public static ProgressMonitor getDefault() {
        synchronized (ProgressMonitor.class) {
            ProgressMonitor progressMonitor = pm;
            return progressMonitor;
        }
    }

    public static void setDefault(ProgressMonitor progressMonitor) {
        synchronized (ProgressMonitor.class) {
            if (progressMonitor != null) {
                pm = progressMonitor;
            }
            return;
        }
    }

    public static void setMeteringPolicy(ProgressMeteringPolicy progressMeteringPolicy) {
        synchronized (ProgressMonitor.class) {
            if (progressMeteringPolicy != null) {
                meteringPolicy = progressMeteringPolicy;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addProgressListener(ProgressListener progressListener) {
        ArrayList<ProgressListener> arrayList = this.progressListenerList;
        synchronized (arrayList) {
            this.progressListenerList.add(progressListener);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public ArrayList<ProgressSource> getProgressSources() {
        Iterator<ProgressSource> iterator;
        ArrayList<ProgressSource> arrayList = new ArrayList<ProgressSource>();
        try {
            ArrayList<ProgressSource> arrayList2 = this.progressSourceList;
            // MONITORENTER : arrayList2
            iterator = this.progressSourceList.iterator();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            cloneNotSupportedException.printStackTrace();
        }
        do {
            if (!iterator.hasNext()) {
                // MONITOREXIT : arrayList2
                return arrayList;
            }
            arrayList.add((ProgressSource)iterator.next().clone());
        } while (true);
        return arrayList;
    }

    public int getProgressUpdateThreshold() {
        synchronized (this) {
            int n = meteringPolicy.getProgressUpdateThreshold();
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void registerSource(ProgressSource progressSource) {
        Object object = this.progressSourceList;
        // MONITORENTER : object
        if (this.progressSourceList.contains(progressSource)) {
            // MONITOREXIT : object
            return;
        }
        this.progressSourceList.add(progressSource);
        // MONITOREXIT : object
        if (this.progressListenerList.size() <= 0) return;
        ArrayList<ProgressListener> arrayList = new ArrayList<ProgressListener>();
        object = this.progressListenerList;
        // MONITORENTER : object
        Iterator<ProgressListener> iterator = this.progressListenerList.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        // MONITOREXIT : object
        object = arrayList.iterator();
        while (object.hasNext()) {
            ((ProgressListener)object.next()).progressStart(new ProgressEvent(progressSource, progressSource.getURL(), progressSource.getMethod(), progressSource.getContentType(), progressSource.getState(), progressSource.getProgress(), progressSource.getExpected()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeProgressListener(ProgressListener progressListener) {
        ArrayList<ProgressListener> arrayList = this.progressListenerList;
        synchronized (arrayList) {
            this.progressListenerList.remove(progressListener);
            return;
        }
    }

    public boolean shouldMeterInput(URL uRL, String string) {
        return meteringPolicy.shouldMeterInput(uRL, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void unregisterSource(ProgressSource progressSource) {
        Object object = this.progressSourceList;
        // MONITORENTER : object
        if (!this.progressSourceList.contains(progressSource)) {
            // MONITOREXIT : object
            return;
        }
        progressSource.close();
        this.progressSourceList.remove(progressSource);
        // MONITOREXIT : object
        if (this.progressListenerList.size() <= 0) return;
        ArrayList<ProgressListener> arrayList = new ArrayList<ProgressListener>();
        object = this.progressListenerList;
        // MONITORENTER : object
        Iterator<ProgressListener> iterator = this.progressListenerList.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        // MONITOREXIT : object
        object = arrayList.iterator();
        while (object.hasNext()) {
            ((ProgressListener)object.next()).progressFinish(new ProgressEvent(progressSource, progressSource.getURL(), progressSource.getMethod(), progressSource.getContentType(), progressSource.getState(), progressSource.getProgress(), progressSource.getExpected()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void updateProgress(ProgressSource progressSource) {
        Object object = this.progressSourceList;
        // MONITORENTER : object
        if (!this.progressSourceList.contains(progressSource)) {
            // MONITOREXIT : object
            return;
        }
        // MONITOREXIT : object
        if (this.progressListenerList.size() <= 0) return;
        ArrayList<ProgressListener> arrayList = new ArrayList<ProgressListener>();
        object = this.progressListenerList;
        // MONITORENTER : object
        Iterator<ProgressListener> iterator = this.progressListenerList.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        // MONITOREXIT : object
        object = arrayList.iterator();
        while (object.hasNext()) {
            ((ProgressListener)object.next()).progressUpdate(new ProgressEvent(progressSource, progressSource.getURL(), progressSource.getMethod(), progressSource.getContentType(), progressSource.getState(), progressSource.getProgress(), progressSource.getExpected()));
        }
    }
}

