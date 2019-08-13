/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.CertStoreParameters;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class CollectionCertStoreParameters
implements CertStoreParameters {
    private Collection<?> coll;

    public CollectionCertStoreParameters() {
        this.coll = Collections.EMPTY_SET;
    }

    public CollectionCertStoreParameters(Collection<?> collection) {
        if (collection != null) {
            this.coll = collection;
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
        }
    }

    public Collection<?> getCollection() {
        return this.coll;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CollectionCertStoreParameters: [\n");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  collection: ");
        stringBuilder.append(this.coll);
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

