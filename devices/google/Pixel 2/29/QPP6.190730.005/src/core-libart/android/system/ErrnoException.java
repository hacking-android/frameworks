/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import android.system.OsConstants;
import java.io.IOException;
import java.net.SocketException;
import libcore.io.Libcore;
import libcore.io.Os;

public final class ErrnoException
extends Exception {
    public final int errno;
    private final String functionName;

    public ErrnoException(String string, int n) {
        this.functionName = string;
        this.errno = n;
    }

    public ErrnoException(String string, int n, Throwable throwable) {
        super(throwable);
        this.functionName = string;
        this.errno = n;
    }

    @Override
    public String getMessage() {
        CharSequence charSequence;
        CharSequence charSequence2 = charSequence = OsConstants.errnoName(this.errno);
        if (charSequence == null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("errno ");
            ((StringBuilder)charSequence2).append(this.errno);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        String string = Libcore.os.strerror(this.errno);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.functionName);
        ((StringBuilder)charSequence).append(" failed: ");
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(" (");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append(")");
        return ((StringBuilder)charSequence).toString();
    }

    public IOException rethrowAsIOException() throws IOException {
        IOException iOException = new IOException(this.getMessage());
        iOException.initCause(this);
        throw iOException;
    }

    public SocketException rethrowAsSocketException() throws SocketException {
        throw new SocketException(this.getMessage(), (Throwable)this);
    }
}

