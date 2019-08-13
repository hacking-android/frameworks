/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.security.cert.CertPathBuilderException;
import sun.security.provider.certpath.AdjacencyList;

public class SunCertPathBuilderException
extends CertPathBuilderException {
    private static final long serialVersionUID = -7814288414129264709L;
    private transient AdjacencyList adjList;

    public SunCertPathBuilderException() {
    }

    public SunCertPathBuilderException(String string) {
        super(string);
    }

    public SunCertPathBuilderException(String string, Throwable throwable) {
        super(string, throwable);
    }

    SunCertPathBuilderException(String string, Throwable throwable, AdjacencyList adjacencyList) {
        this(string, throwable);
        this.adjList = adjacencyList;
    }

    SunCertPathBuilderException(String string, AdjacencyList adjacencyList) {
        this(string);
        this.adjList = adjacencyList;
    }

    public SunCertPathBuilderException(Throwable throwable) {
        super(throwable);
    }

    public AdjacencyList getAdjacencyList() {
        return this.adjList;
    }
}

