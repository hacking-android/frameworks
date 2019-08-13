/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.InterfaceDisabledException;
import android.net.lowpan.JoinFailedAtAuthException;
import android.net.lowpan.JoinFailedAtScanException;
import android.net.lowpan.JoinFailedException;
import android.net.lowpan.LowpanRuntimeException;
import android.net.lowpan.NetworkAlreadyExistsException;
import android.net.lowpan.OperationCanceledException;
import android.net.lowpan.WrongStateException;
import android.os.ServiceSpecificException;
import android.util.AndroidException;

public class LowpanException
extends AndroidException {
    public LowpanException() {
    }

    public LowpanException(Exception exception) {
        super(exception);
    }

    public LowpanException(String string2) {
        super(string2);
    }

    public LowpanException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    static LowpanException rethrowFromServiceSpecificException(ServiceSpecificException serviceSpecificException) throws LowpanException {
        int n = serviceSpecificException.errorCode;
        if (n != 2) {
            if (n != 3) {
                if (n != 4) {
                    if (n != 7) {
                        switch (n) {
                            default: {
                                throw new LowpanRuntimeException(serviceSpecificException);
                            }
                            case 15: {
                                throw new NetworkAlreadyExistsException(serviceSpecificException);
                            }
                            case 14: {
                                throw new JoinFailedAtAuthException(serviceSpecificException);
                            }
                            case 13: {
                                throw new JoinFailedAtScanException(serviceSpecificException);
                            }
                            case 12: {
                                throw new JoinFailedException(serviceSpecificException);
                            }
                            case 11: {
                                String string2 = serviceSpecificException.getMessage() != null ? serviceSpecificException.getMessage() : "Feature not supported";
                                throw new LowpanException(string2, serviceSpecificException);
                            }
                            case 10: 
                        }
                        throw new OperationCanceledException(serviceSpecificException);
                    }
                    String string3 = serviceSpecificException.getMessage() != null ? serviceSpecificException.getMessage() : "NCP problem";
                    throw new LowpanRuntimeException(string3, serviceSpecificException);
                }
                throw new WrongStateException(serviceSpecificException);
            }
            throw new InterfaceDisabledException(serviceSpecificException);
        }
        String string4 = serviceSpecificException.getMessage() != null ? serviceSpecificException.getMessage() : "Invalid argument";
        throw new LowpanRuntimeException(string4, serviceSpecificException);
    }
}

