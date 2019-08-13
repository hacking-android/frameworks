/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.Serializable;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXReason;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import javax.security.auth.x500.X500Principal;
import sun.security.util.Debug;

class PKIXMasterCertPathValidator {
    private static final Debug debug = Debug.getInstance("certpath");

    PKIXMasterCertPathValidator() {
    }

    static void validate(CertPath object, List<X509Certificate> object2, List<PKIXCertPathChecker> list) throws CertPathValidatorException {
        int n = object2.size();
        Object object3 = debug;
        if (object3 != null) {
            ((Debug)object3).println("--------------------------------------------------------------");
            debug.println("Executing PKIX certification path validation algorithm.");
        }
        for (int i = 0; i < n; ++i) {
            Object object4;
            Object object5;
            Serializable serializable = object2.get(i);
            Object object6 = debug;
            if (object6 != null) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Checking cert");
                ((StringBuilder)object3).append(i + 1);
                ((StringBuilder)object3).append(" - Subject: ");
                ((StringBuilder)object3).append(((X509Certificate)serializable).getSubjectX500Principal());
                ((Debug)object6).println(((StringBuilder)object3).toString());
            }
            if ((object3 = serializable.getCriticalExtensionOIDs()) == null) {
                object3 = Collections.emptySet();
            }
            if (debug != null && !object3.isEmpty()) {
                object6 = new StringJoiner(", ", "{", "}");
                object4 = object3.iterator();
                while (object4.hasNext()) {
                    ((StringJoiner)object6).add((String)object4.next());
                }
                object4 = debug;
                object5 = new StringBuilder();
                ((StringBuilder)object5).append("Set of critical extensions: ");
                ((StringBuilder)object5).append(((StringJoiner)object6).toString());
                ((Debug)object4).println(((StringBuilder)object5).toString());
            }
            for (int j = 0; j < list.size(); ++j) {
                object5 = list.get(j);
                object4 = debug;
                if (object4 != null) {
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append("-Using checker");
                    ((StringBuilder)object6).append(j + 1);
                    ((StringBuilder)object6).append(" ... [");
                    ((StringBuilder)object6).append(object5.getClass().getName());
                    ((StringBuilder)object6).append("]");
                    ((Debug)object4).println(((StringBuilder)object6).toString());
                }
                if (i == 0) {
                    ((PKIXCertPathChecker)object5).init(false);
                }
                try {
                    ((PKIXCertPathChecker)object5).check((Certificate)serializable, (Collection<String>)object3);
                    if (debug == null) continue;
                    object6 = debug;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("-checker");
                    ((StringBuilder)object4).append(j + 1);
                    ((StringBuilder)object4).append(" validation succeeded");
                    ((Debug)object6).println(((StringBuilder)object4).toString());
                    continue;
                }
                catch (CertPathValidatorException certPathValidatorException) {
                    object3 = certPathValidatorException.getMessage();
                    object2 = certPathValidatorException.getCause() != null ? certPathValidatorException.getCause() : certPathValidatorException;
                    throw new CertPathValidatorException((String)object3, (Throwable)object2, (CertPath)object, n - (i + 1), certPathValidatorException.getReason());
                }
            }
            if (object3.isEmpty()) {
                object3 = debug;
                if (object3 == null) continue;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("\ncert");
                ((StringBuilder)serializable).append(i + 1);
                ((StringBuilder)serializable).append(" validation succeeded.\n");
                ((Debug)object3).println(((StringBuilder)serializable).toString());
                continue;
            }
            throw new CertPathValidatorException("unrecognized critical extension(s)", null, (CertPath)object, n - (i + 1), PKIXReason.UNRECOGNIZED_CRIT_EXT);
        }
        object = debug;
        if (object != null) {
            ((Debug)object).println("Cert path validation succeeded. (PKIX validation algorithm)");
            debug.println("--------------------------------------------------------------");
        }
    }
}

