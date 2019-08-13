/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.security.cert.PolicyNode;
import java.security.cert.PolicyQualifierInfo;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class PolicyNodeImpl
implements PolicyNode {
    private static final String ANY_POLICY = "2.5.29.32.0";
    private boolean isImmutable = false;
    private HashSet<PolicyNodeImpl> mChildren;
    private boolean mCriticalityIndicator;
    private int mDepth;
    private HashSet<String> mExpectedPolicySet;
    private boolean mOriginalExpectedPolicySet;
    private PolicyNodeImpl mParent;
    private HashSet<PolicyQualifierInfo> mQualifierSet;
    private String mValidPolicy;

    PolicyNodeImpl(PolicyNodeImpl policyNodeImpl, String string, Set<PolicyQualifierInfo> set, boolean bl, Set<String> set2, boolean bl2) {
        this.mParent = policyNodeImpl;
        this.mChildren = new HashSet();
        this.mValidPolicy = string != null ? string : "";
        this.mQualifierSet = set != null ? new HashSet<PolicyQualifierInfo>(set) : new HashSet();
        this.mCriticalityIndicator = bl;
        this.mExpectedPolicySet = set2 != null ? new HashSet<String>(set2) : new HashSet();
        this.mOriginalExpectedPolicySet = bl2 ^ true;
        policyNodeImpl = this.mParent;
        if (policyNodeImpl != null) {
            this.mDepth = policyNodeImpl.getDepth() + 1;
            this.mParent.addChild(this);
        } else {
            this.mDepth = 0;
        }
    }

    PolicyNodeImpl(PolicyNodeImpl policyNodeImpl, PolicyNodeImpl policyNodeImpl2) {
        this(policyNodeImpl, policyNodeImpl2.mValidPolicy, policyNodeImpl2.mQualifierSet, policyNodeImpl2.mCriticalityIndicator, policyNodeImpl2.mExpectedPolicySet, false);
    }

    private void addChild(PolicyNodeImpl policyNodeImpl) {
        if (!this.isImmutable) {
            this.mChildren.add(policyNodeImpl);
            return;
        }
        throw new IllegalStateException("PolicyNode is immutable");
    }

    private PolicyNodeImpl copyTree(PolicyNodeImpl policyNodeImpl) {
        policyNodeImpl = new PolicyNodeImpl(policyNodeImpl, this);
        Iterator<PolicyNodeImpl> iterator = this.mChildren.iterator();
        while (iterator.hasNext()) {
            iterator.next().copyTree(policyNodeImpl);
        }
        return policyNodeImpl;
    }

    private void getPolicyNodes(int n, Set<PolicyNodeImpl> set) {
        if (this.mDepth == n) {
            set.add(this);
        } else {
            Iterator<PolicyNodeImpl> iterator = this.mChildren.iterator();
            while (iterator.hasNext()) {
                iterator.next().getPolicyNodes(n, set);
            }
        }
    }

    private Set<PolicyNodeImpl> getPolicyNodesExpectedHelper(int n, String string, boolean bl) {
        HashSet<PolicyNodeImpl> hashSet = new HashSet<PolicyNodeImpl>();
        if (this.mDepth < n) {
            Iterator<PolicyNodeImpl> iterator = this.mChildren.iterator();
            while (iterator.hasNext()) {
                hashSet.addAll(iterator.next().getPolicyNodesExpectedHelper(n, string, bl));
            }
        } else if (bl) {
            if (this.mExpectedPolicySet.contains(ANY_POLICY)) {
                hashSet.add(this);
            }
        } else if (this.mExpectedPolicySet.contains(string)) {
            hashSet.add(this);
        }
        return hashSet;
    }

    private static String policyToString(String string) {
        if (string.equals(ANY_POLICY)) {
            return "anyPolicy";
        }
        return string;
    }

    void addExpectedPolicy(String string) {
        if (!this.isImmutable) {
            if (this.mOriginalExpectedPolicySet) {
                this.mExpectedPolicySet.clear();
                this.mOriginalExpectedPolicySet = false;
            }
            this.mExpectedPolicySet.add(string);
            return;
        }
        throw new IllegalStateException("PolicyNode is immutable");
    }

    String asString() {
        if (this.mParent == null) {
            return "anyPolicy  ROOT\n";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.getDepth();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append("  ");
        }
        stringBuilder.append(PolicyNodeImpl.policyToString(this.getValidPolicy()));
        stringBuilder.append("  CRIT: ");
        stringBuilder.append(this.isCritical());
        stringBuilder.append("  EP: ");
        Iterator<String> iterator = this.getExpectedPolicies().iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(PolicyNodeImpl.policyToString(iterator.next()));
            stringBuilder.append(" ");
        }
        stringBuilder.append(" (");
        stringBuilder.append(this.getDepth());
        stringBuilder.append(")\n");
        return stringBuilder.toString();
    }

    PolicyNodeImpl copyTree() {
        return this.copyTree(null);
    }

    void deleteChild(PolicyNode policyNode) {
        if (!this.isImmutable) {
            this.mChildren.remove(policyNode);
            return;
        }
        throw new IllegalStateException("PolicyNode is immutable");
    }

    public Iterator<PolicyNodeImpl> getChildren() {
        return Collections.unmodifiableSet(this.mChildren).iterator();
    }

    @Override
    public int getDepth() {
        return this.mDepth;
    }

    @Override
    public Set<String> getExpectedPolicies() {
        return Collections.unmodifiableSet(this.mExpectedPolicySet);
    }

    @Override
    public PolicyNode getParent() {
        return this.mParent;
    }

    Set<PolicyNodeImpl> getPolicyNodes(int n) {
        HashSet<PolicyNodeImpl> hashSet = new HashSet<PolicyNodeImpl>();
        this.getPolicyNodes(n, hashSet);
        return hashSet;
    }

    Set<PolicyNodeImpl> getPolicyNodesExpected(int n, String string, boolean bl) {
        if (string.equals(ANY_POLICY)) {
            return this.getPolicyNodes(n);
        }
        return this.getPolicyNodesExpectedHelper(n, string, bl);
    }

    Set<PolicyNodeImpl> getPolicyNodesValid(int n, String string) {
        HashSet<PolicyNodeImpl> hashSet = new HashSet<PolicyNodeImpl>();
        if (this.mDepth < n) {
            Iterator<PolicyNodeImpl> iterator = this.mChildren.iterator();
            while (iterator.hasNext()) {
                hashSet.addAll(iterator.next().getPolicyNodesValid(n, string));
            }
        } else if (this.mValidPolicy.equals(string)) {
            hashSet.add(this);
        }
        return hashSet;
    }

    public Set<PolicyQualifierInfo> getPolicyQualifiers() {
        return Collections.unmodifiableSet(this.mQualifierSet);
    }

    @Override
    public String getValidPolicy() {
        return this.mValidPolicy;
    }

    @Override
    public boolean isCritical() {
        return this.mCriticalityIndicator;
    }

    boolean isImmutable() {
        return this.isImmutable;
    }

    void prune(int n) {
        if (!this.isImmutable) {
            if (this.mChildren.size() == 0) {
                return;
            }
            Iterator<PolicyNodeImpl> iterator = this.mChildren.iterator();
            while (iterator.hasNext()) {
                PolicyNodeImpl policyNodeImpl = iterator.next();
                policyNodeImpl.prune(n);
                if (policyNodeImpl.mChildren.size() != 0 || n <= this.mDepth + 1) continue;
                iterator.remove();
            }
            return;
        }
        throw new IllegalStateException("PolicyNode is immutable");
    }

    void setImmutable() {
        if (this.isImmutable) {
            return;
        }
        Iterator<PolicyNodeImpl> iterator = this.mChildren.iterator();
        while (iterator.hasNext()) {
            iterator.next().setImmutable();
        }
        this.isImmutable = true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.asString());
        Iterator<PolicyNodeImpl> iterator = this.mChildren.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
        }
        return stringBuilder.toString();
    }
}

