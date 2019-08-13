/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

import android.util.apk.DataDigester;
import java.io.IOException;
import java.security.DigestException;

interface DataSource {
    public void feedIntoDataDigester(DataDigester var1, long var2, int var4) throws IOException, DigestException;

    public long size();
}

