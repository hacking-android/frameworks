/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.net.URL;
import sun.net.ProgressMonitor;

public class ProgressSource {
    private boolean connected = false;
    private String contentType;
    private long expected = -1L;
    private long lastProgress = 0L;
    private String method;
    private long progress = 0L;
    private ProgressMonitor progressMonitor;
    private State state;
    private int threshold = 8192;
    private URL url;

    public ProgressSource(URL uRL, String string) {
        this(uRL, string, -1L);
    }

    public ProgressSource(URL uRL, String string, long l) {
        this.url = uRL;
        this.method = string;
        this.contentType = "content/unknown";
        this.progress = 0L;
        this.lastProgress = 0L;
        this.expected = l;
        this.state = State.NEW;
        this.progressMonitor = ProgressMonitor.getDefault();
        this.threshold = this.progressMonitor.getProgressUpdateThreshold();
    }

    public void beginTracking() {
        this.progressMonitor.registerSource(this);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void close() {
        this.state = State.DELETE;
    }

    public boolean connected() {
        if (!this.connected) {
            this.connected = true;
            this.state = State.CONNECTED;
            return false;
        }
        return true;
    }

    public void finishTracking() {
        this.progressMonitor.unregisterSource(this);
    }

    public String getContentType() {
        return this.contentType;
    }

    public long getExpected() {
        return this.expected;
    }

    public String getMethod() {
        return this.method;
    }

    public long getProgress() {
        return this.progress;
    }

    public State getState() {
        return this.state;
    }

    public URL getURL() {
        return this.url;
    }

    public void setContentType(String string) {
        this.contentType = string;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[url=");
        stringBuilder.append(this.url);
        stringBuilder.append(", method=");
        stringBuilder.append(this.method);
        stringBuilder.append(", state=");
        stringBuilder.append((Object)this.state);
        stringBuilder.append(", content-type=");
        stringBuilder.append(this.contentType);
        stringBuilder.append(", progress=");
        stringBuilder.append(this.progress);
        stringBuilder.append(", expected=");
        stringBuilder.append(this.expected);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void updateProgress(long l, long l2) {
        this.lastProgress = this.progress;
        this.progress = l;
        this.expected = l2;
        this.state = !this.connected() ? State.CONNECTED : State.UPDATE;
        l = this.lastProgress;
        int n = this.threshold;
        if (l / (long)n != this.progress / (long)n) {
            this.progressMonitor.updateProgress(this);
        }
        if ((l = this.expected) != -1L && (l2 = this.progress) >= l && l2 != 0L) {
            this.close();
        }
    }

    public static enum State {
        NEW,
        CONNECTED,
        UPDATE,
        DELETE;
        
    }

}

