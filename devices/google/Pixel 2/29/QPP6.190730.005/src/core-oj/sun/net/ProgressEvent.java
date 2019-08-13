/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.net.URL;
import java.util.EventObject;
import sun.net.ProgressSource;

public class ProgressEvent
extends EventObject {
    private String contentType;
    private long expected;
    private String method;
    private long progress;
    private ProgressSource.State state;
    private URL url;

    public ProgressEvent(ProgressSource progressSource, URL uRL, String string, String string2, ProgressSource.State state, long l, long l2) {
        super(progressSource);
        this.url = uRL;
        this.method = string;
        this.contentType = string2;
        this.progress = l;
        this.expected = l2;
        this.state = state;
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

    public ProgressSource.State getState() {
        return this.state;
    }

    public URL getURL() {
        return this.url;
    }

    @Override
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
}

