/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.IOException;

public interface Appendable {
    public Appendable append(char var1) throws IOException;

    public Appendable append(CharSequence var1) throws IOException;

    public Appendable append(CharSequence var1, int var2, int var3) throws IOException;
}

