/*
 * Decompiled with CFR 0.145.
 */
package java.util.jar;

import java.util.zip.ZipException;

public class JarException
extends ZipException {
    private static final long serialVersionUID = 7159778400963954473L;

    public JarException() {
    }

    public JarException(String string) {
        super(string);
    }
}

