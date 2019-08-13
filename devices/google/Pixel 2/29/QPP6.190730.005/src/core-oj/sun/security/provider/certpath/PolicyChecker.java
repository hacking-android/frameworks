/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXReason;
import java.security.cert.PolicyNode;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import sun.security.provider.certpath.PolicyNodeImpl;
import sun.security.util.Debug;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.CertificatePolicyId;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.Extension;
import sun.security.x509.InhibitAnyPolicyExtension;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.PolicyConstraintsExtension;
import sun.security.x509.PolicyInformation;
import sun.security.x509.PolicyMappingsExtension;
import sun.security.x509.X509CertImpl;

class PolicyChecker
extends PKIXCertPathChecker {
    static final String ANY_POLICY = "2.5.29.32.0";
    private static final Debug debug = Debug.getInstance("certpath");
    private final boolean anyPolicyInhibited;
    private int certIndex;
    private final int certPathLen;
    private final boolean expPolicyRequired;
    private int explicitPolicy;
    private int inhibitAnyPolicy;
    private final Set<String> initPolicies;
    private final boolean polMappingInhibited;
    private int policyMapping;
    private final boolean rejectPolicyQualifiers;
    private PolicyNodeImpl rootNode;
    private Set<String> supportedExts;

    PolicyChecker(Set<String> set, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, PolicyNodeImpl policyNodeImpl) {
        if (set.isEmpty()) {
            this.initPolicies = new HashSet<String>(1);
            this.initPolicies.add(ANY_POLICY);
        } else {
            this.initPolicies = new HashSet<String>(set);
        }
        this.certPathLen = n;
        this.expPolicyRequired = bl;
        this.polMappingInhibited = bl2;
        this.anyPolicyInhibited = bl3;
        this.rejectPolicyQualifiers = bl4;
        this.rootNode = policyNodeImpl;
    }

    private void checkPolicy(X509Certificate object) throws CertPathValidatorException {
        boolean bl;
        Object object2;
        Object object3 = debug;
        if (object3 != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("PolicyChecker.checkPolicy() ---checking ");
            ((StringBuilder)object2).append("certificate policies");
            ((StringBuilder)object2).append("...");
            ((Debug)object3).println(((StringBuilder)object2).toString());
            object3 = debug;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("PolicyChecker.checkPolicy() certIndex = ");
            ((StringBuilder)object2).append(this.certIndex);
            ((Debug)object3).println(((StringBuilder)object2).toString());
            object2 = debug;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("PolicyChecker.checkPolicy() BEFORE PROCESSING: explicitPolicy = ");
            ((StringBuilder)object3).append(this.explicitPolicy);
            ((Debug)object2).println(((StringBuilder)object3).toString());
            object3 = debug;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("PolicyChecker.checkPolicy() BEFORE PROCESSING: policyMapping = ");
            ((StringBuilder)object2).append(this.policyMapping);
            ((Debug)object3).println(((StringBuilder)object2).toString());
            object2 = debug;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("PolicyChecker.checkPolicy() BEFORE PROCESSING: inhibitAnyPolicy = ");
            ((StringBuilder)object3).append(this.inhibitAnyPolicy);
            ((Debug)object2).println(((StringBuilder)object3).toString());
            object2 = debug;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("PolicyChecker.checkPolicy() BEFORE PROCESSING: policyTree = ");
            ((StringBuilder)object3).append(this.rootNode);
            ((Debug)object2).println(((StringBuilder)object3).toString());
        }
        try {
            object = X509CertImpl.toImpl((X509Certificate)object);
            bl = this.certIndex == this.certPathLen;
        }
        catch (CertificateException certificateException) {
            throw new CertPathValidatorException(certificateException);
        }
        this.rootNode = PolicyChecker.processPolicies(this.certIndex, this.initPolicies, this.explicitPolicy, this.policyMapping, this.inhibitAnyPolicy, this.rejectPolicyQualifiers, this.rootNode, (X509CertImpl)object, bl);
        if (!bl) {
            this.explicitPolicy = PolicyChecker.mergeExplicitPolicy(this.explicitPolicy, (X509CertImpl)object, bl);
            this.policyMapping = PolicyChecker.mergePolicyMapping(this.policyMapping, (X509CertImpl)object);
            this.inhibitAnyPolicy = PolicyChecker.mergeInhibitAnyPolicy(this.inhibitAnyPolicy, (X509CertImpl)object);
        }
        ++this.certIndex;
        object2 = debug;
        if (object2 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("PolicyChecker.checkPolicy() AFTER PROCESSING: explicitPolicy = ");
            ((StringBuilder)object).append(this.explicitPolicy);
            ((Debug)object2).println(((StringBuilder)object).toString());
            object = debug;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("PolicyChecker.checkPolicy() AFTER PROCESSING: policyMapping = ");
            ((StringBuilder)object2).append(this.policyMapping);
            ((Debug)object).println(((StringBuilder)object2).toString());
            object2 = debug;
            object = new StringBuilder();
            ((StringBuilder)object).append("PolicyChecker.checkPolicy() AFTER PROCESSING: inhibitAnyPolicy = ");
            ((StringBuilder)object).append(this.inhibitAnyPolicy);
            ((Debug)object2).println(((StringBuilder)object).toString());
            object2 = debug;
            object = new StringBuilder();
            ((StringBuilder)object).append("PolicyChecker.checkPolicy() AFTER PROCESSING: policyTree = ");
            ((StringBuilder)object).append(this.rootNode);
            ((Debug)object2).println(((StringBuilder)object).toString());
            object = debug;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("PolicyChecker.checkPolicy() ");
            ((StringBuilder)object2).append("certificate policies");
            ((StringBuilder)object2).append(" verified");
            ((Debug)object).println(((StringBuilder)object2).toString());
        }
    }

    static int mergeExplicitPolicy(int n, X509CertImpl object, boolean bl) throws CertPathValidatorException {
        block12 : {
            int n2;
            int n3;
            block11 : {
                block13 : {
                    block10 : {
                        n3 = n;
                        if (n > 0) {
                            n3 = n;
                            if (!X509CertImpl.isSelfIssued((X509Certificate)object)) {
                                n3 = n - 1;
                            }
                        }
                        try {
                            object = ((X509CertImpl)object).getPolicyConstraintsExtension();
                            if (object != null) break block10;
                            return n3;
                        }
                        catch (IOException iOException) {
                            object = debug;
                            if (object != null) {
                                ((Debug)object).println("PolicyChecker.mergeExplicitPolicy unexpected exception");
                                iOException.printStackTrace();
                            }
                            throw new CertPathValidatorException(iOException);
                        }
                    }
                    n2 = ((PolicyConstraintsExtension)object).get("require");
                    if (debug != null) {
                        Debug debug = PolicyChecker.debug;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("PolicyChecker.mergeExplicitPolicy() require Index from cert = ");
                        ((StringBuilder)object).append(n2);
                        debug.println(((StringBuilder)object).toString());
                    }
                    if (bl) break block11;
                    n = n3;
                    if (n2 == -1) break block12;
                    if (n3 == -1) break block13;
                    n = n3;
                    if (n2 >= n3) break block12;
                }
                n = n2;
                break block12;
            }
            n = n3;
            if (n2 == 0) {
                n = n2;
            }
        }
        return n;
    }

    static int mergeInhibitAnyPolicy(int n, X509CertImpl object) throws CertPathValidatorException {
        int n2;
        block9 : {
            n2 = n;
            if (n > 0) {
                n2 = n;
                if (!X509CertImpl.isSelfIssued((X509Certificate)object)) {
                    n2 = n - 1;
                }
            }
            try {
                object = (InhibitAnyPolicyExtension)((X509CertImpl)object).getExtension(PKIXExtensions.InhibitAnyPolicy_Id);
                if (object != null) break block9;
                return n2;
            }
            catch (IOException iOException) {
                Debug debug = PolicyChecker.debug;
                if (debug != null) {
                    debug.println("PolicyChecker.mergeInhibitAnyPolicy unexpected exception");
                    iOException.printStackTrace();
                }
                throw new CertPathValidatorException(iOException);
            }
        }
        int n3 = ((InhibitAnyPolicyExtension)object).get("skip_certs");
        if (debug != null) {
            Debug debug = PolicyChecker.debug;
            object = new StringBuilder();
            ((StringBuilder)object).append("PolicyChecker.mergeInhibitAnyPolicy() skipCerts Index from cert = ");
            ((StringBuilder)object).append(n3);
            debug.println(((StringBuilder)object).toString());
        }
        n = n2;
        if (n3 != -1) {
            n = n2;
            if (n3 < n2) {
                n = n3;
            }
        }
        return n;
    }

    static int mergePolicyMapping(int n, X509CertImpl object) throws CertPathValidatorException {
        block11 : {
            int n2;
            block12 : {
                int n3;
                block10 : {
                    n3 = n;
                    if (n > 0) {
                        n3 = n;
                        if (!X509CertImpl.isSelfIssued((X509Certificate)object)) {
                            n3 = n - 1;
                        }
                    }
                    try {
                        object = ((X509CertImpl)object).getPolicyConstraintsExtension();
                        if (object != null) break block10;
                        return n3;
                    }
                    catch (IOException iOException) {
                        Debug debug = PolicyChecker.debug;
                        if (debug != null) {
                            debug.println("PolicyChecker.mergePolicyMapping unexpected exception");
                            iOException.printStackTrace();
                        }
                        throw new CertPathValidatorException(iOException);
                    }
                }
                n2 = ((PolicyConstraintsExtension)object).get("inhibit");
                if (debug != null) {
                    object = debug;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("PolicyChecker.mergePolicyMapping() inhibit Index from cert = ");
                    stringBuilder.append(n2);
                    ((Debug)object).println(stringBuilder.toString());
                }
                n = n3;
                if (n2 == -1) break block11;
                if (n3 == -1) break block12;
                n = n3;
                if (n2 >= n3) break block11;
            }
            n = n2;
        }
        return n;
    }

    private static boolean processParents(int n, boolean bl, boolean bl2, PolicyNodeImpl object, String string, Set<PolicyQualifierInfo> set, boolean bl3) throws CertPathValidatorException {
        Object object2;
        bl2 = false;
        Debug object32 = debug;
        if (object32 != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("PolicyChecker.processParents(): matchAny = ");
            ((StringBuilder)object2).append(bl3);
            object32.println(((StringBuilder)object2).toString());
        }
        for (PolicyNodeImpl policyNodeImpl : ((PolicyNodeImpl)((Object)object)).getPolicyNodesExpected(n - 1, string, bl3)) {
            Object object3;
            object2 = debug;
            if (object2 != null) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("PolicyChecker.processParents() found parent:\n");
                ((StringBuilder)object3).append(policyNodeImpl.asString());
                ((Debug)object2).println(((StringBuilder)object3).toString());
            }
            policyNodeImpl.getValidPolicy();
            if (string.equals(ANY_POLICY)) {
                block1 : for (String string2 : policyNodeImpl.getExpectedPolicies()) {
                    Object object4 = policyNodeImpl.getChildren();
                    while (object4.hasNext()) {
                        object3 = object4.next().getValidPolicy();
                        if (!string2.equals(object3)) continue;
                        Debug debug = PolicyChecker.debug;
                        if (debug == null) continue block1;
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append((String)object3);
                        ((StringBuilder)object4).append(" in parent's expected policy set already appears in child node");
                        debug.println(((StringBuilder)object4).toString());
                        continue block1;
                    }
                    object3 = new HashSet();
                    object3.add(string2);
                    new PolicyNodeImpl(policyNodeImpl, string2, set, bl, (Set<String>)object3, false);
                }
            } else {
                object2 = new HashSet();
                object2.add(string);
                new PolicyNodeImpl(policyNodeImpl, string, set, bl, (Set<String>)object2, false);
            }
            bl2 = true;
        }
        return bl2;
    }

    static PolicyNodeImpl processPolicies(int n, Set<String> set, int n2, int n3, int n4, boolean bl, PolicyNodeImpl object, X509CertImpl x509CertImpl, boolean bl2) throws CertPathValidatorException {
        CertificatePoliciesExtension certificatePoliciesExtension;
        Object object2;
        Object object3;
        block26 : {
            boolean bl3;
            block25 : {
                Iterator iterator;
                Object object4;
                block24 : {
                    bl3 = false;
                    object3 = new HashSet();
                    object = object == null ? null : ((PolicyNodeImpl)object).copyTree();
                    certificatePoliciesExtension = x509CertImpl.getCertificatePoliciesExtension();
                    if (certificatePoliciesExtension == null || object == null) break block25;
                    bl3 = certificatePoliciesExtension.isCritical();
                    object2 = debug;
                    if (object2 != null) {
                        iterator = new StringBuilder();
                        ((StringBuilder)((Object)iterator)).append("PolicyChecker.processPolicies() policiesCritical = ");
                        ((StringBuilder)((Object)iterator)).append(bl3);
                        ((Debug)object2).println(((StringBuilder)((Object)iterator)).toString());
                    }
                    try {
                        iterator = certificatePoliciesExtension.get("policies");
                        object4 = debug;
                        if (object4 == null) break block24;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("PolicyChecker.processPolicies() rejectPolicyQualifiers = ");
                    }
                    catch (IOException iOException) {
                        throw new CertPathValidatorException("Exception while retrieving policyOIDs", iOException);
                    }
                    ((StringBuilder)object2).append(bl);
                    ((Debug)object4).println(((StringBuilder)object2).toString());
                }
                iterator = iterator.iterator();
                boolean bl4 = false;
                while (iterator.hasNext()) {
                    boolean bl5;
                    object2 = (PolicyInformation)iterator.next();
                    object4 = ((PolicyInformation)object2).getPolicyIdentifier().getIdentifier().toString();
                    if (((String)object4).equals(ANY_POLICY)) {
                        object2 = ((PolicyInformation)object2).getPolicyQualifiers();
                        bl5 = true;
                    } else {
                        Object object5 = debug;
                        if (object5 != null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("PolicyChecker.processPolicies() processing policy: ");
                            stringBuilder.append((String)object4);
                            ((Debug)object5).println(stringBuilder.toString());
                        }
                        if (!(object5 = ((PolicyInformation)object2).getPolicyQualifiers()).isEmpty() && bl && bl3) {
                            throw new CertPathValidatorException("critical policy qualifiers present in certificate", null, null, -1, PKIXReason.INVALID_POLICY);
                        }
                        bl5 = bl4;
                        object2 = object3;
                        if (!PolicyChecker.processParents(n, bl3, bl, (PolicyNodeImpl)object, (String)object4, (Set<PolicyQualifierInfo>)object5, false)) {
                            PolicyChecker.processParents(n, bl3, bl, (PolicyNodeImpl)object, (String)object4, (Set<PolicyQualifierInfo>)object5, true);
                            object2 = object3;
                            bl5 = bl4;
                        }
                    }
                    bl4 = bl5;
                    object3 = object2;
                }
                if (bl4 && (n4 > 0 || !bl2 && X509CertImpl.isSelfIssued(x509CertImpl))) {
                    object2 = debug;
                    if (object2 != null) {
                        ((Debug)object2).println("PolicyChecker.processPolicies() processing policy: 2.5.29.32.0");
                    }
                    PolicyChecker.processParents(n, bl3, bl, (PolicyNodeImpl)object, ANY_POLICY, (Set<PolicyQualifierInfo>)object3, true);
                }
                ((PolicyNodeImpl)object).prune(n);
                object2 = object;
                if (!((PolicyNodeImpl)object).getChildren().hasNext()) {
                    object2 = null;
                }
                bl = bl3;
                object = object2;
                object2 = object3;
                break block26;
            }
            if (certificatePoliciesExtension == null) {
                object = debug;
                if (object != null) {
                    ((Debug)object).println("PolicyChecker.processPolicies() no policies present in cert");
                }
                object = null;
                bl = bl3;
                object2 = object3;
            } else {
                object2 = object3;
                bl = bl3;
            }
        }
        object3 = object;
        if (object != null) {
            object3 = object;
            if (!bl2) {
                object3 = PolicyChecker.processPolicyMappings(x509CertImpl, n, n3, (PolicyNodeImpl)object, bl, (Set<PolicyQualifierInfo>)object2);
            }
        }
        object = object3;
        if (object3 != null) {
            object = object3;
            if (!set.contains(ANY_POLICY)) {
                object = object3;
                if (certificatePoliciesExtension != null) {
                    object = object3 = PolicyChecker.removeInvalidNodes((PolicyNodeImpl)object3, n, set, certificatePoliciesExtension);
                    if (object3 != null) {
                        object = object3;
                        if (bl2) {
                            object = PolicyChecker.rewriteLeafNodes(n, set, (PolicyNodeImpl)object3);
                        }
                    }
                }
            }
        }
        if (bl2) {
            n2 = PolicyChecker.mergeExplicitPolicy(n2, x509CertImpl, bl2);
        }
        if (n2 == 0 && object == null) {
            throw new CertPathValidatorException("non-null policy tree required and policy tree is null", null, null, -1, PKIXReason.INVALID_POLICY);
        }
        return object;
    }

    private static PolicyNodeImpl processPolicyMappings(X509CertImpl object, int n, int n2, PolicyNodeImpl object2, boolean bl, Set<PolicyQualifierInfo> set) throws CertPathValidatorException {
        boolean bl2;
        Object policyNodeImpl = object2;
        if ((object = ((X509CertImpl)object).getPolicyMappingsExtension()) == null) {
            return policyNodeImpl;
        }
        Object object3 = debug;
        if (object3 != null) {
            ((Debug)object3).println("PolicyChecker.processPolicyMappings() inside policyMapping check");
        }
        try {
            object = ((PolicyMappingsExtension)object).get("map");
            bl2 = false;
            object3 = object.iterator();
        }
        catch (IOException iOException) {
            object2 = debug;
            if (object2 != null) {
                ((Debug)object2).println("PolicyChecker.processPolicyMappings() mapping exception");
                iOException.printStackTrace();
            }
            throw new CertPathValidatorException("Exception while checking mapping", iOException);
        }
        while (object3.hasNext()) {
            block17 : {
                block18 : {
                    boolean bl3;
                    block20 : {
                        String string;
                        block21 : {
                            block19 : {
                                Set<PolicyNodeImpl> set2;
                                object = (CertificatePolicyMap)object3.next();
                                string = ((CertificatePolicyMap)object).getIssuerIdentifier().getIdentifier().toString();
                                object = ((CertificatePolicyMap)object).getSubjectIdentifier().getIdentifier().toString();
                                Debug debug = PolicyChecker.debug;
                                if (debug != null) {
                                    set2 = new StringBuilder();
                                    ((StringBuilder)((Object)set2)).append("PolicyChecker.processPolicyMappings() issuerDomain = ");
                                    ((StringBuilder)((Object)set2)).append(string);
                                    debug.println(((StringBuilder)((Object)set2)).toString());
                                    Debug debug2 = PolicyChecker.debug;
                                    set2 = new StringBuilder();
                                    ((StringBuilder)((Object)set2)).append("PolicyChecker.processPolicyMappings() subjectDomain = ");
                                    ((StringBuilder)((Object)set2)).append((String)object);
                                    debug2.println(((StringBuilder)((Object)set2)).toString());
                                }
                                if (string.equals(ANY_POLICY)) break block17;
                                if (((String)object).equals(ANY_POLICY)) break block18;
                                set2 = ((PolicyNodeImpl)policyNodeImpl).getPolicyNodesValid(n, string);
                                if (set2.isEmpty()) break block19;
                                for (PolicyNodeImpl policyNodeImpl2 : set2) {
                                    if (n2 <= 0 && n2 != -1) {
                                        if (n2 != 0) continue;
                                        set2 = (PolicyNodeImpl)policyNodeImpl2.getParent();
                                        Debug debug3 = PolicyChecker.debug;
                                        if (debug3 != null) {
                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("PolicyChecker.processPolicyMappings() before deleting: policy tree = ");
                                            stringBuilder.append(policyNodeImpl);
                                            debug3.println(stringBuilder.toString());
                                        }
                                        ((PolicyNodeImpl)((Object)set2)).deleteChild(policyNodeImpl2);
                                        bl3 = true;
                                        Debug debug4 = PolicyChecker.debug;
                                        bl2 = bl3;
                                        if (debug4 == null) continue;
                                        set2 = new StringBuilder();
                                        ((StringBuilder)((Object)set2)).append("PolicyChecker.processPolicyMappings() after deleting: policy tree = ");
                                        ((StringBuilder)((Object)set2)).append(policyNodeImpl);
                                        debug4.println(((StringBuilder)((Object)set2)).toString());
                                        bl2 = bl3;
                                        continue;
                                    }
                                    policyNodeImpl2.addExpectedPolicy((String)object);
                                }
                                bl3 = bl2;
                                break block20;
                            }
                            if (n2 > 0) break block21;
                            bl3 = bl2;
                            if (n2 != -1) break block20;
                        }
                        for (PolicyNodeImpl policyNodeImpl3 : ((PolicyNodeImpl)policyNodeImpl).getPolicyNodesValid(n, ANY_POLICY)) {
                            PolicyNodeImpl policyNodeImpl2;
                            policyNodeImpl2 = (PolicyNodeImpl)policyNodeImpl3.getParent();
                            HashSet<String> hashSet = new HashSet<String>();
                            hashSet.add((String)object);
                            new PolicyNodeImpl(policyNodeImpl2, string, set, bl, hashSet, true);
                        }
                        bl3 = bl2;
                    }
                    bl2 = bl3;
                    continue;
                }
                throw new CertPathValidatorException("encountered a subjectDomainPolicy of ANY_POLICY", null, null, -1, PKIXReason.INVALID_POLICY);
            }
            throw new CertPathValidatorException("encountered an issuerDomainPolicy of ANY_POLICY", null, null, -1, PKIXReason.INVALID_POLICY);
        }
        object = policyNodeImpl;
        if (bl2) {
            ((PolicyNodeImpl)policyNodeImpl).prune(n);
            object = policyNodeImpl;
            if (!((PolicyNodeImpl)object2).getChildren().hasNext()) {
                object = debug;
                if (object != null) {
                    ((Debug)object).println("setting rootNode to null");
                }
                object = null;
            }
        }
        return object;
    }

    private static PolicyNodeImpl removeInvalidNodes(PolicyNodeImpl policyNodeImpl, int n, Set<String> object, CertificatePoliciesExtension iterator) throws CertPathValidatorException {
        boolean bl;
        try {
            iterator = ((CertificatePoliciesExtension)((Object)iterator)).get("policies");
            bl = false;
            iterator = iterator.iterator();
        }
        catch (IOException iOException) {
            throw new CertPathValidatorException("Exception while retrieving policyOIDs", iOException);
        }
        while (iterator.hasNext()) {
            Object object2;
            Object object3 = (PolicyInformation)iterator.next();
            object3 = ((PolicyInformation)object3).getPolicyIdentifier().getIdentifier().toString();
            Debug debug = PolicyChecker.debug;
            if (debug != null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("PolicyChecker.processPolicies() processing policy second time: ");
                ((StringBuilder)object2).append((String)object3);
                debug.println(((StringBuilder)object2).toString());
            }
            for (PolicyNodeImpl policyNodeImpl2 : policyNodeImpl.getPolicyNodesValid(n, (String)object3)) {
                PolicyNodeImpl policyNodeImpl3 = (PolicyNodeImpl)policyNodeImpl2.getParent();
                boolean bl2 = bl;
                if (policyNodeImpl3.getValidPolicy().equals(ANY_POLICY)) {
                    bl2 = bl;
                    if (!object.contains(object3)) {
                        bl2 = bl;
                        if (!((String)object3).equals(ANY_POLICY)) {
                            object2 = PolicyChecker.debug;
                            if (object2 != null) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("PolicyChecker.processPolicies() before deleting: policy tree = ");
                                stringBuilder.append(policyNodeImpl);
                                ((Debug)object2).println(stringBuilder.toString());
                            }
                            policyNodeImpl3.deleteChild(policyNodeImpl2);
                            bl = true;
                            object2 = PolicyChecker.debug;
                            bl2 = bl;
                            if (object2 != null) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("PolicyChecker.processPolicies() after deleting: policy tree = ");
                                stringBuilder.append(policyNodeImpl);
                                ((Debug)object2).println(stringBuilder.toString());
                                bl2 = bl;
                            }
                        }
                    }
                }
                bl = bl2;
            }
        }
        object = policyNodeImpl;
        if (bl) {
            policyNodeImpl.prune(n);
            object = policyNodeImpl;
            if (!policyNodeImpl.getChildren().hasNext()) {
                object = null;
            }
        }
        return object;
    }

    private static PolicyNodeImpl rewriteLeafNodes(int n, Set<String> object, PolicyNodeImpl object2) {
        Object object3 = object2;
        Set<PolicyNodeImpl> set = ((PolicyNodeImpl)object3).getPolicyNodesValid(n, ANY_POLICY);
        if (set.isEmpty()) {
            return object3;
        }
        Object object4 = set.iterator().next();
        set = (PolicyNodeImpl)((PolicyNodeImpl)object4).getParent();
        ((PolicyNodeImpl)((Object)set)).deleteChild((PolicyNode)object4);
        object = new HashSet<String>((Collection<String>)object);
        Iterator<PolicyNodeImpl> iterator = ((PolicyNodeImpl)object3).getPolicyNodes(n).iterator();
        while (iterator.hasNext()) {
            object.remove(iterator.next().getValidPolicy());
        }
        if (object.isEmpty()) {
            ((PolicyNodeImpl)object3).prune(n);
            object = object3;
            if (!((PolicyNodeImpl)object2).getChildren().hasNext()) {
                object = null;
            }
        } else {
            boolean bl = ((PolicyNodeImpl)object4).isCritical();
            object2 = ((PolicyNodeImpl)object4).getPolicyQualifiers();
            object4 = object.iterator();
            do {
                object = object3;
                if (!object4.hasNext()) break;
                object = (String)object4.next();
                new PolicyNodeImpl((PolicyNodeImpl)((Object)set), (String)object, (Set<PolicyQualifierInfo>)object2, bl, Collections.singleton(object), false);
            } while (true);
        }
        return object;
    }

    @Override
    public void check(Certificate certificate, Collection<String> collection) throws CertPathValidatorException {
        this.checkPolicy((X509Certificate)certificate);
        if (collection != null && !collection.isEmpty()) {
            collection.remove(PKIXExtensions.CertificatePolicies_Id.toString());
            collection.remove(PKIXExtensions.PolicyMappings_Id.toString());
            collection.remove(PKIXExtensions.PolicyConstraints_Id.toString());
            collection.remove(PKIXExtensions.InhibitAnyPolicy_Id.toString());
        }
    }

    PolicyNode getPolicyTree() {
        PolicyNodeImpl policyNodeImpl = this.rootNode;
        if (policyNodeImpl == null) {
            return null;
        }
        policyNodeImpl = policyNodeImpl.copyTree();
        policyNodeImpl.setImmutable();
        return policyNodeImpl;
    }

    @Override
    public Set<String> getSupportedExtensions() {
        if (this.supportedExts == null) {
            this.supportedExts = new HashSet<String>(4);
            this.supportedExts.add(PKIXExtensions.CertificatePolicies_Id.toString());
            this.supportedExts.add(PKIXExtensions.PolicyMappings_Id.toString());
            this.supportedExts.add(PKIXExtensions.PolicyConstraints_Id.toString());
            this.supportedExts.add(PKIXExtensions.InhibitAnyPolicy_Id.toString());
            this.supportedExts = Collections.unmodifiableSet(this.supportedExts);
        }
        return this.supportedExts;
    }

    @Override
    public void init(boolean bl) throws CertPathValidatorException {
        if (!bl) {
            this.certIndex = 1;
            bl = this.expPolicyRequired;
            int n = 0;
            int n2 = bl ? 0 : this.certPathLen + 1;
            this.explicitPolicy = n2;
            n2 = this.polMappingInhibited ? 0 : this.certPathLen + 1;
            this.policyMapping = n2;
            n2 = this.anyPolicyInhibited ? n : this.certPathLen + 1;
            this.inhibitAnyPolicy = n2;
            return;
        }
        throw new CertPathValidatorException("forward checking not supported");
    }

    @Override
    public boolean isForwardCheckingSupported() {
        return false;
    }
}

