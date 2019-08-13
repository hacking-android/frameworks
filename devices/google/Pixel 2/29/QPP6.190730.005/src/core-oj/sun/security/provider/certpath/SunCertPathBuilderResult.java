/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import sun.security.provider.certpath.AdjacencyList;
import sun.security.util.Debug;

public class SunCertPathBuilderResult
extends PKIXCertPathBuilderResult {
    private static final Debug debug = Debug.getInstance("certpath");
    private AdjacencyList adjList;

    SunCertPathBuilderResult(CertPath certPath, TrustAnchor trustAnchor, PolicyNode policyNode, PublicKey publicKey, AdjacencyList adjacencyList) {
        super(certPath, trustAnchor, policyNode, publicKey);
        this.adjList = adjacencyList;
    }

    public AdjacencyList getAdjacencyList() {
        return this.adjList;
    }
}

