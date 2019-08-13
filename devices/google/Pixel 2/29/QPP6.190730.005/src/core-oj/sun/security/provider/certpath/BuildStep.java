/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import sun.security.provider.certpath.Vertex;

public class BuildStep {
    public static final int BACK = 2;
    public static final int FAIL = 4;
    public static final int FOLLOW = 3;
    public static final int POSSIBLE = 1;
    public static final int SUCCEED = 5;
    private X509Certificate cert;
    private int result;
    private Throwable throwable;
    private Vertex vertex;

    public BuildStep(Vertex vertex, int n) {
        vertex = this.vertex = vertex;
        if (vertex != null) {
            this.cert = vertex.getCertificate();
            this.throwable = this.vertex.getThrowable();
        }
        this.result = n;
    }

    public String fullToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.resultToString(this.getResult()));
        stringBuilder.append(this.vertex.toString());
        return stringBuilder.toString();
    }

    public X509Certificate getCertificate() {
        return this.cert;
    }

    public String getIssuerName() {
        return this.getIssuerName(null);
    }

    public String getIssuerName(String string) {
        X509Certificate x509Certificate = this.cert;
        if (x509Certificate != null) {
            string = x509Certificate.getIssuerX500Principal().toString();
        }
        return string;
    }

    public int getResult() {
        return this.result;
    }

    public String getSubjectName() {
        return this.getSubjectName(null);
    }

    public String getSubjectName(String string) {
        X509Certificate x509Certificate = this.cert;
        if (x509Certificate != null) {
            string = x509Certificate.getSubjectX500Principal().toString();
        }
        return string;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public Vertex getVertex() {
        return this.vertex;
    }

    public String resultToString(int n) {
        String string = n != 1 ? (n != 2 ? (n != 3 ? (n != 4 ? (n != 5 ? "Internal error: Invalid step result value.\n" : "Certificate satisfies conditions.\n") : "Certificate backed out since path does not satisfy conditions.\n") : "Certificate satisfies conditions.\n") : "Certificate backed out since path does not satisfy build requirements.\n") : "Certificate to be tried.\n";
        return string;
    }

    public String toString() {
        String string;
        block2 : {
            block0 : {
                block1 : {
                    int n = this.result;
                    if (n == 1) break block0;
                    if (n == 2) break block1;
                    if (n == 3) break block0;
                    if (n == 4) break block1;
                    if (n == 5) break block0;
                    string = "Internal Error: Invalid step result\n";
                    break block2;
                }
                string = this.resultToString(this.result);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(this.vertex.throwableToString());
                string = stringBuilder.toString();
                break block2;
            }
            string = this.resultToString(this.result);
        }
        return string;
    }

    public String verboseToString() {
        String string;
        StringBuilder stringBuilder;
        block2 : {
            block3 : {
                block4 : {
                    string = this.resultToString(this.getResult());
                    int n = this.result;
                    if (n == 1) break block2;
                    if (n == 2) break block3;
                    if (n == 3) break block4;
                    if (n == 4) break block3;
                    if (n != 5) break block2;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(this.vertex.moreToString());
                string = stringBuilder.toString();
                break block2;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(this.vertex.throwableToString());
            string = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("Certificate contains:\n");
        stringBuilder.append(this.vertex.certToString());
        return stringBuilder.toString();
    }
}

