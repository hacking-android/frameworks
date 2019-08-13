/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.PolicyQualifierInfo;
import java.util.Iterator;
import java.util.Set;

public interface PolicyNode {
    public Iterator<? extends PolicyNode> getChildren();

    public int getDepth();

    public Set<String> getExpectedPolicies();

    public PolicyNode getParent();

    public Set<? extends PolicyQualifierInfo> getPolicyQualifiers();

    public String getValidPolicy();

    public boolean isCritical();
}

