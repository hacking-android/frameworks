/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.util.List;

public class CertPathValidatorException
extends GeneralSecurityException {
    private static final long serialVersionUID = -3083180014971893139L;
    private CertPath certPath;
    private int index;
    private Reason reason;

    public CertPathValidatorException() {
        this(null, null);
    }

    public CertPathValidatorException(String string) {
        this(string, null);
    }

    public CertPathValidatorException(String string, Throwable throwable) {
        this(string, throwable, null, -1);
    }

    public CertPathValidatorException(String string, Throwable throwable, CertPath certPath, int n) {
        this(string, throwable, certPath, n, BasicReason.UNSPECIFIED);
    }

    public CertPathValidatorException(String string, Throwable throwable, CertPath certPath, int n, Reason reason) {
        super(string, throwable);
        this.index = -1;
        this.reason = BasicReason.UNSPECIFIED;
        if (certPath == null && n != -1) {
            throw new IllegalArgumentException();
        }
        if (n >= -1 && (certPath == null || n < certPath.getCertificates().size())) {
            if (reason != null) {
                this.certPath = certPath;
                this.index = n;
                this.reason = reason;
                return;
            }
            throw new NullPointerException("reason can't be null");
        }
        throw new IndexOutOfBoundsException();
    }

    public CertPathValidatorException(Throwable throwable) {
        String string = throwable == null ? null : throwable.toString();
        this(string, throwable);
    }

    private void readObject(ObjectInputStream object) throws ClassNotFoundException, IOException {
        ((ObjectInputStream)object).defaultReadObject();
        if (this.reason == null) {
            this.reason = BasicReason.UNSPECIFIED;
        }
        if (this.certPath == null && this.index != -1) {
            throw new InvalidObjectException("certpath is null and index != -1");
        }
        int n = this.index;
        if (n >= -1 && ((object = this.certPath) == null || n < ((CertPath)object).getCertificates().size())) {
            return;
        }
        throw new InvalidObjectException("index out of range");
    }

    public CertPath getCertPath() {
        return this.certPath;
    }

    public int getIndex() {
        return this.index;
    }

    public Reason getReason() {
        return this.reason;
    }

    public static enum BasicReason implements Reason
    {
        UNSPECIFIED,
        EXPIRED,
        NOT_YET_VALID,
        REVOKED,
        UNDETERMINED_REVOCATION_STATUS,
        INVALID_SIGNATURE,
        ALGORITHM_CONSTRAINED;
        
    }

    public static interface Reason
    extends Serializable {
    }

}

