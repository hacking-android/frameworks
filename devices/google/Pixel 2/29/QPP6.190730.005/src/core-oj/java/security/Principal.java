/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.util.Set;
import javax.security.auth.Subject;

public interface Principal {
    public boolean equals(Object var1);

    public String getName();

    public int hashCode();

    default public boolean implies(Subject subject) {
        if (subject == null) {
            return false;
        }
        return subject.getPrincipals().contains(this);
    }

    public String toString();
}

