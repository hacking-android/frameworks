/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import android.system.OsConstants;
import java.net.UnknownHostException;
import libcore.io.Libcore;
import libcore.io.Os;

public final class GaiException
extends RuntimeException {
    public final int error;
    private final String functionName;

    public GaiException(String string, int n) {
        this.functionName = string;
        this.error = n;
    }

    public GaiException(String string, int n, Throwable throwable) {
        super(throwable);
        this.functionName = string;
        this.error = n;
    }

    @Override
    public String getMessage() {
        CharSequence charSequence;
        CharSequence charSequence2 = charSequence = OsConstants.gaiName(this.error);
        if (charSequence == null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("GAI_ error ");
            ((StringBuilder)charSequence2).append(this.error);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        String string = Libcore.os.gai_strerror(this.error);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.functionName);
        ((StringBuilder)charSequence).append(" failed: ");
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(" (");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append(")");
        return ((StringBuilder)charSequence).toString();
    }

    public UnknownHostException rethrowAsUnknownHostException() throws UnknownHostException {
        throw this.rethrowAsUnknownHostException(this.getMessage());
    }

    public UnknownHostException rethrowAsUnknownHostException(String object) throws UnknownHostException {
        object = new UnknownHostException((String)object);
        ((Throwable)object).initCause(this);
        throw object;
    }
}

