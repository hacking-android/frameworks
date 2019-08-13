/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package android.hardware.camera2.legacy;

import android.os.ServiceSpecificException;
import android.system.OsConstants;
import android.util.AndroidException;

public class LegacyExceptionUtils {
    public static final int ALREADY_EXISTS;
    public static final int BAD_VALUE;
    public static final int DEAD_OBJECT;
    public static final int INVALID_OPERATION;
    public static final int NO_ERROR = 0;
    public static final int PERMISSION_DENIED;
    private static final String TAG = "LegacyExceptionUtils";
    public static final int TIMED_OUT;

    static {
        PERMISSION_DENIED = -OsConstants.EPERM;
        ALREADY_EXISTS = -OsConstants.EEXIST;
        BAD_VALUE = -OsConstants.EINVAL;
        DEAD_OBJECT = -OsConstants.ENOSYS;
        INVALID_OPERATION = -OsConstants.EPIPE;
        TIMED_OUT = -OsConstants.ETIMEDOUT;
    }

    private LegacyExceptionUtils() {
        throw new AssertionError();
    }

    public static int throwOnError(int n) throws BufferQueueAbandonedException {
        if (n == 0) {
            return 0;
        }
        if (n != BAD_VALUE) {
            if (n >= 0) {
                return n;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown error ");
            stringBuilder.append(n);
            throw new UnsupportedOperationException(stringBuilder.toString());
        }
        throw new BufferQueueAbandonedException();
    }

    public static void throwOnServiceError(int n) {
        CharSequence charSequence;
        if (n >= 0) {
            return;
        }
        if (n != PERMISSION_DENIED) {
            if (n == ALREADY_EXISTS) {
                return;
            }
            if (n != BAD_VALUE) {
                if (n != DEAD_OBJECT) {
                    if (n != TIMED_OUT) {
                        if (n != -OsConstants.EACCES) {
                            if (n != -OsConstants.EBUSY) {
                                if (n != -OsConstants.EUSERS) {
                                    if (n != -OsConstants.ENODEV) {
                                        if (n != -OsConstants.EOPNOTSUPP) {
                                            if (n == INVALID_OPERATION) {
                                                n = 10;
                                                charSequence = "Illegal state encountered in camera service.";
                                            } else {
                                                int n2 = 10;
                                                charSequence = new StringBuilder();
                                                ((StringBuilder)charSequence).append("Unknown camera device error ");
                                                ((StringBuilder)charSequence).append(n);
                                                charSequence = ((StringBuilder)charSequence).toString();
                                                n = n2;
                                            }
                                        } else {
                                            n = 9;
                                            charSequence = "Deprecated camera HAL does not support this";
                                        }
                                    } else {
                                        n = 4;
                                        charSequence = "Camera device not available";
                                    }
                                } else {
                                    n = 8;
                                    charSequence = "Maximum number of cameras in use";
                                }
                            } else {
                                n = 7;
                                charSequence = "Camera already in use";
                            }
                        } else {
                            n = 6;
                            charSequence = "Camera disabled by policy";
                        }
                    } else {
                        n = 10;
                        charSequence = "Operation timed out in camera service";
                    }
                } else {
                    n = 4;
                    charSequence = "Camera service not available";
                }
            } else {
                n = 3;
                charSequence = "Bad argument passed to camera service";
            }
        } else {
            n = 1;
            charSequence = "Lacking privileges to access camera service";
        }
        throw new ServiceSpecificException(n, (String)charSequence);
    }

    public static class BufferQueueAbandonedException
    extends AndroidException {
        public BufferQueueAbandonedException() {
        }

        public BufferQueueAbandonedException(Exception exception) {
            super(exception);
        }

        public BufferQueueAbandonedException(String string2) {
            super(string2);
        }

        public BufferQueueAbandonedException(String string2, Throwable throwable) {
            super(string2, throwable);
        }
    }

}

