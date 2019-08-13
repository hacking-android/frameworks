/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.Extension;
import java.util.Collections;
import java.util.List;
import sun.misc.CharacterEncoder;
import sun.misc.HexDumpEncoder;
import sun.security.provider.certpath.CertId;
import sun.security.provider.certpath.OCSP;
import sun.security.util.Debug;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

class OCSPRequest {
    private static final Debug debug = Debug.getInstance("certpath");
    private static final boolean dump;
    private final List<CertId> certIds;
    private final List<Extension> extensions;
    private byte[] nonce;

    static {
        boolean bl = debug != null && Debug.isOn("ocsp");
        dump = bl;
    }

    OCSPRequest(List<CertId> list) {
        this.certIds = list;
        this.extensions = Collections.emptyList();
    }

    OCSPRequest(List<CertId> list, List<Extension> list2) {
        this.certIds = list;
        this.extensions = list2;
    }

    OCSPRequest(CertId certId) {
        this(Collections.singletonList(certId));
    }

    byte[] encodeBytes() throws IOException {
        Object object;
        Object object2 = new DerOutputStream();
        Object object3 = new DerOutputStream();
        for (CertId certId : this.certIds) {
            object = new DerOutputStream();
            certId.encode((DerOutputStream)object);
            ((DerOutputStream)object3).write((byte)48, (DerOutputStream)object);
        }
        ((DerOutputStream)object2).write((byte)48, (DerOutputStream)object3);
        if (!this.extensions.isEmpty()) {
            object = new DerOutputStream();
            for (Extension extension : this.extensions) {
                extension.encode((OutputStream)object);
                if (!extension.getId().equals(OCSP.NONCE_EXTENSION_OID.toString())) continue;
                this.nonce = extension.getValue();
            }
            DerOutputStream derOutputStream = new DerOutputStream();
            derOutputStream.write((byte)48, (DerOutputStream)object);
            ((DerOutputStream)object2).write(DerValue.createTag((byte)-128, true, (byte)2), derOutputStream);
        }
        object = new DerOutputStream();
        ((DerOutputStream)object).write((byte)48, (DerOutputStream)object2);
        object2 = new DerOutputStream();
        ((DerOutputStream)object2).write((byte)48, (DerOutputStream)object);
        byte[] arrby = ((ByteArrayOutputStream)object2).toByteArray();
        if (dump) {
            object3 = new HexDumpEncoder();
            object = debug;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("OCSPRequest bytes...\n\n");
            ((StringBuilder)object2).append(((CharacterEncoder)object3).encode(arrby));
            ((StringBuilder)object2).append("\n");
            ((Debug)object).println(((StringBuilder)object2).toString());
        }
        return arrby;
    }

    List<CertId> getCertIds() {
        return this.certIds;
    }

    byte[] getNonce() {
        return this.nonce;
    }
}

