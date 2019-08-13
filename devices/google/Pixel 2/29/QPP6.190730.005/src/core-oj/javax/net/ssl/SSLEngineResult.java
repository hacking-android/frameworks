/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

public class SSLEngineResult {
    private final int bytesConsumed;
    private final int bytesProduced;
    private final HandshakeStatus handshakeStatus;
    private final Status status;

    public SSLEngineResult(Status status, HandshakeStatus handshakeStatus, int n, int n2) {
        if (status != null && handshakeStatus != null && n >= 0 && n2 >= 0) {
            this.status = status;
            this.handshakeStatus = handshakeStatus;
            this.bytesConsumed = n;
            this.bytesProduced = n2;
            return;
        }
        throw new IllegalArgumentException("Invalid Parameter(s)");
    }

    public final int bytesConsumed() {
        return this.bytesConsumed;
    }

    public final int bytesProduced() {
        return this.bytesProduced;
    }

    public final HandshakeStatus getHandshakeStatus() {
        return this.handshakeStatus;
    }

    public final Status getStatus() {
        return this.status;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Status = ");
        stringBuilder.append((Object)this.status);
        stringBuilder.append(" HandshakeStatus = ");
        stringBuilder.append((Object)this.handshakeStatus);
        stringBuilder.append("\nbytesConsumed = ");
        stringBuilder.append(this.bytesConsumed);
        stringBuilder.append(" bytesProduced = ");
        stringBuilder.append(this.bytesProduced);
        return stringBuilder.toString();
    }

    public static enum HandshakeStatus {
        NOT_HANDSHAKING,
        FINISHED,
        NEED_TASK,
        NEED_WRAP,
        NEED_UNWRAP;
        
    }

    public static enum Status {
        BUFFER_UNDERFLOW,
        BUFFER_OVERFLOW,
        OK,
        CLOSED;
        
    }

}

