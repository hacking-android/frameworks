/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PKIXPolicyNode
implements PolicyNode {
    protected List children;
    protected boolean critical;
    protected int depth;
    protected Set expectedPolicies;
    protected PolicyNode parent;
    protected Set policyQualifiers;
    protected String validPolicy;

    public PKIXPolicyNode(List list, int n, Set set, PolicyNode policyNode, Set set2, String string, boolean bl) {
        this.children = list;
        this.depth = n;
        this.expectedPolicies = set;
        this.parent = policyNode;
        this.policyQualifiers = set2;
        this.validPolicy = string;
        this.critical = bl;
    }

    public void addChild(PKIXPolicyNode pKIXPolicyNode) {
        this.children.add(pKIXPolicyNode);
        pKIXPolicyNode.setParent(this);
    }

    public Object clone() {
        return this.copy();
    }

    public PKIXPolicyNode copy() {
        Object object = new HashSet<String>();
        Object object2 = this.expectedPolicies.iterator();
        while (object2.hasNext()) {
            object.add(new String((String)object2.next()));
        }
        Object object3 = new HashSet<String>();
        object2 = this.policyQualifiers.iterator();
        while (object2.hasNext()) {
            object3.add(new String((String)object2.next()));
        }
        object3 = new PKIXPolicyNode(new ArrayList(), this.depth, (Set)object, null, (Set)object3, new String(this.validPolicy), this.critical);
        object = this.children.iterator();
        while (object.hasNext()) {
            object2 = ((PKIXPolicyNode)object.next()).copy();
            ((PKIXPolicyNode)object2).setParent((PKIXPolicyNode)object3);
            ((PKIXPolicyNode)object3).addChild((PKIXPolicyNode)object2);
        }
        return object3;
    }

    public Iterator getChildren() {
        return this.children.iterator();
    }

    @Override
    public int getDepth() {
        return this.depth;
    }

    public Set getExpectedPolicies() {
        return this.expectedPolicies;
    }

    @Override
    public PolicyNode getParent() {
        return this.parent;
    }

    public Set getPolicyQualifiers() {
        return this.policyQualifiers;
    }

    @Override
    public String getValidPolicy() {
        return this.validPolicy;
    }

    public boolean hasChildren() {
        return this.children.isEmpty() ^ true;
    }

    @Override
    public boolean isCritical() {
        return this.critical;
    }

    public void removeChild(PKIXPolicyNode pKIXPolicyNode) {
        this.children.remove(pKIXPolicyNode);
    }

    public void setCritical(boolean bl) {
        this.critical = bl;
    }

    public void setExpectedPolicies(Set set) {
        this.expectedPolicies = set;
    }

    public void setParent(PKIXPolicyNode pKIXPolicyNode) {
        this.parent = pKIXPolicyNode;
    }

    public String toString() {
        return this.toString("");
    }

    public String toString(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(string);
        stringBuffer.append(this.validPolicy);
        stringBuffer.append(" {\n");
        for (int i = 0; i < this.children.size(); ++i) {
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode)this.children.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("    ");
            stringBuffer.append(pKIXPolicyNode.toString(stringBuilder.toString()));
        }
        stringBuffer.append(string);
        stringBuffer.append("}\n");
        return stringBuffer.toString();
    }
}

