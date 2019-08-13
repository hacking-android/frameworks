/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.io.IOException;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.UserPrincipal;

public abstract class UserPrincipalLookupService {
    protected UserPrincipalLookupService() {
    }

    public abstract GroupPrincipal lookupPrincipalByGroupName(String var1) throws IOException;

    public abstract UserPrincipal lookupPrincipalByName(String var1) throws IOException;
}

