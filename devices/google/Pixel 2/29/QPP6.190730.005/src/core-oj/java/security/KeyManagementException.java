/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.KeyException;

public class KeyManagementException
extends KeyException {
    private static final long serialVersionUID = 947674216157062695L;

    public KeyManagementException() {
    }

    public KeyManagementException(String string) {
        super(string);
    }

    public KeyManagementException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public KeyManagementException(Throwable throwable) {
        super(throwable);
    }
}

