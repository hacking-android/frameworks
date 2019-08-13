/*
 * Decompiled with CFR 0.145.
 */
package android.media;

public class MediaCasStateException
extends IllegalStateException {
    private final String mDiagnosticInfo;
    private final int mErrorCode;

    private MediaCasStateException(int n, String string2, String string3) {
        super(string2);
        this.mErrorCode = n;
        this.mDiagnosticInfo = string3;
    }

    static void throwExceptionIfNeeded(int n) {
        MediaCasStateException.throwExceptionIfNeeded(n, null);
    }

    static void throwExceptionIfNeeded(int n, String string2) {
        if (n == 0) {
            return;
        }
        if (n != 6) {
            String string3;
            switch (n) {
                default: {
                    string3 = "Unknown CAS state exception";
                    break;
                }
                case 14: {
                    string3 = "General CAS error";
                    break;
                }
                case 13: {
                    string3 = "Decrypt error";
                    break;
                }
                case 12: {
                    string3 = "Not initialized";
                    break;
                }
                case 10: {
                    string3 = "Tamper detected";
                    break;
                }
                case 9: {
                    string3 = "Insufficient output protection";
                    break;
                }
                case 5: {
                    string3 = "Invalid CAS state";
                    break;
                }
                case 4: {
                    string3 = "Unsupported scheme or data format";
                    break;
                }
                case 3: {
                    string3 = "Session not opened";
                    break;
                }
                case 2: {
                    string3 = "License expired";
                    break;
                }
                case 1: {
                    string3 = "No license";
                }
            }
            throw new MediaCasStateException(n, string2, String.format("%s (err=%d)", string3, n));
        }
        throw new IllegalArgumentException();
    }

    public String getDiagnosticInfo() {
        return this.mDiagnosticInfo;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }
}

