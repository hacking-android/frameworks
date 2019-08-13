/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.DeadObjectException;
import android.os.DeadSystemException;
import android.util.AndroidException;

public class RemoteException
extends AndroidException {
    public RemoteException() {
    }

    public RemoteException(String string2) {
        super(string2);
    }

    public RemoteException(String string2, Throwable throwable, boolean bl, boolean bl2) {
        super(string2, throwable, bl, bl2);
    }

    public RuntimeException rethrowAsRuntimeException() {
        throw new RuntimeException(this);
    }

    @UnsupportedAppUsage
    public RuntimeException rethrowFromSystemServer() {
        if (this instanceof DeadObjectException) {
            throw new RuntimeException(new DeadSystemException());
        }
        throw new RuntimeException(this);
    }
}

