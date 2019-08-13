/*
 * Decompiled with CFR 0.145.
 */
package javax.security.auth;

import javax.security.auth.DestroyFailedException;

public interface Destroyable {
    default public void destroy() throws DestroyFailedException {
        throw new DestroyFailedException();
    }

    default public boolean isDestroyed() {
        return false;
    }
}

