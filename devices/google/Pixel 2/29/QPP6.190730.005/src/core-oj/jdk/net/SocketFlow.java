/*
 * Decompiled with CFR 0.145.
 */
package jdk.net;

public class SocketFlow {
    public static final int HIGH_PRIORITY = 2;
    public static final int NORMAL_PRIORITY = 1;
    private static final int UNSET = -1;
    private long bandwidth = -1L;
    private int priority = 1;
    private Status status = Status.NO_STATUS;

    private SocketFlow() {
    }

    public static SocketFlow create() {
        return new SocketFlow();
    }

    public long bandwidth() {
        return this.bandwidth;
    }

    public SocketFlow bandwidth(long l) {
        if (l >= 0L) {
            this.bandwidth = l;
            return this;
        }
        throw new IllegalArgumentException("invalid bandwidth");
    }

    public int priority() {
        return this.priority;
    }

    public SocketFlow priority(int n) {
        if (n != 1 && n != 2) {
            throw new IllegalArgumentException("invalid priority");
        }
        this.priority = n;
        return this;
    }

    public Status status() {
        return this.status;
    }

    public static enum Status {
        NO_STATUS,
        OK,
        NO_PERMISSION,
        NOT_CONNECTED,
        NOT_SUPPORTED,
        ALREADY_CREATED,
        IN_PROGRESS,
        OTHER;
        
    }

}

