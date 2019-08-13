/*
 * Decompiled with CFR 0.145.
 */
package android.net.sip;

public class SipErrorCode {
    public static final int CLIENT_ERROR = -4;
    public static final int CROSS_DOMAIN_AUTHENTICATION = -11;
    public static final int DATA_CONNECTION_LOST = -10;
    public static final int INVALID_CREDENTIALS = -8;
    public static final int INVALID_REMOTE_URI = -6;
    public static final int IN_PROGRESS = -9;
    public static final int NO_ERROR = 0;
    public static final int PEER_NOT_REACHABLE = -7;
    public static final int SERVER_ERROR = -2;
    public static final int SERVER_UNREACHABLE = -12;
    public static final int SOCKET_ERROR = -1;
    public static final int TIME_OUT = -5;
    public static final int TRANSACTION_TERMINTED = -3;

    private SipErrorCode() {
    }

    public static String toString(int n) {
        switch (n) {
            default: {
                return "UNKNOWN";
            }
            case 0: {
                return "NO_ERROR";
            }
            case -1: {
                return "SOCKET_ERROR";
            }
            case -2: {
                return "SERVER_ERROR";
            }
            case -3: {
                return "TRANSACTION_TERMINTED";
            }
            case -4: {
                return "CLIENT_ERROR";
            }
            case -5: {
                return "TIME_OUT";
            }
            case -6: {
                return "INVALID_REMOTE_URI";
            }
            case -7: {
                return "PEER_NOT_REACHABLE";
            }
            case -8: {
                return "INVALID_CREDENTIALS";
            }
            case -9: {
                return "IN_PROGRESS";
            }
            case -10: {
                return "DATA_CONNECTION_LOST";
            }
            case -11: {
                return "CROSS_DOMAIN_AUTHENTICATION";
            }
            case -12: 
        }
        return "SERVER_UNREACHABLE";
    }
}

