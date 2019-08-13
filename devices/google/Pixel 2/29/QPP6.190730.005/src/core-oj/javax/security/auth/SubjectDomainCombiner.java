/*
 * Decompiled with CFR 0.145.
 */
package javax.security.auth;

import java.security.DomainCombiner;
import java.security.ProtectionDomain;
import javax.security.auth.Subject;

public class SubjectDomainCombiner
implements DomainCombiner {
    public SubjectDomainCombiner(Subject subject) {
    }

    @Override
    public ProtectionDomain[] combine(ProtectionDomain[] arrprotectionDomain, ProtectionDomain[] arrprotectionDomain2) {
        return null;
    }

    public Subject getSubject() {
        return null;
    }
}

