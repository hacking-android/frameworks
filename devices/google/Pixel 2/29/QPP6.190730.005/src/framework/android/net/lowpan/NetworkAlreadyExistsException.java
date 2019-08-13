/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanException;

public class NetworkAlreadyExistsException
extends LowpanException {
    public NetworkAlreadyExistsException() {
    }

    public NetworkAlreadyExistsException(Exception exception) {
        super(exception);
    }

    public NetworkAlreadyExistsException(String string2) {
        super(string2, null);
    }

    public NetworkAlreadyExistsException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

