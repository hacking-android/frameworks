/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.io.IOException;

public class UserPrincipalNotFoundException
extends IOException {
    static final long serialVersionUID = -5369283889045833024L;
    private final String name;

    public UserPrincipalNotFoundException(String string) {
        this.name = string;
    }

    public String getName() {
        return this.name;
    }
}

