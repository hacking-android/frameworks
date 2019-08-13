/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.jar.Manifest;
import sun.nio.ByteBuffered;

public abstract class Resource {
    private InputStream cis;

    private InputStream cachedInputStream() throws IOException {
        synchronized (this) {
            if (this.cis == null) {
                this.cis = this.getInputStream();
            }
            InputStream inputStream = this.cis;
            return inputStream;
        }
    }

    public ByteBuffer getByteBuffer() throws IOException {
        InputStream inputStream = this.cachedInputStream();
        if (inputStream instanceof ByteBuffered) {
            return ((ByteBuffered)((Object)inputStream)).getByteBuffer();
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public byte[] getBytes() throws IOException {
        var1_1 = this.cachedInputStream();
        var2_2 = Thread.interrupted();
        do {
            var3_14 = this.getContentLength();
            var4_15 = var2_2;
            try {
                var5_16 = new byte[]{};
                var6_17 = var3_14;
                if (var3_14 == -1) {
                    var6_17 = Integer.MAX_VALUE;
                }
                var3_14 = 0;
                do lbl-1000: // 2 sources:
                {
                    var7_18 = var2_2;
                    var8_5 = var5_16;
                    if (var3_14 >= var6_17) break;
                    var4_15 = var2_2;
                    if (var3_14 >= var5_16.length) {
                        var4_15 = var2_2;
                        var9_19 = Math.min(var6_17 - var3_14, var5_16.length + 1024);
                        var10_20 = var5_16;
                        var11_25 = var9_19;
                        var4_15 = var2_2;
                        if (var5_16.length < var3_14 + var9_19) {
                            var4_15 = var2_2;
                            var10_20 = Arrays.copyOf(var5_16, var3_14 + var9_19);
                            var11_25 = var9_19;
                        }
                        break block21;
                    }
                    var4_15 = var2_2;
                    var11_25 = var5_16.length;
                    break;
                } while (true);
            }
            catch (Throwable var8_12) {
                try {
                    var1_1.close();
                }
                catch (IOException var10_23) {
                }
                catch (InterruptedIOException var10_24) {
                    var4_15 = true;
                }
                if (var4_15) {
                    Thread.currentThread().interrupt();
                }
                throw var8_12;
            }
            {
                block22 : {
                    block21 : {
                        var11_25 -= var3_14;
                        var10_20 = var5_16;
                    }
                    var9_19 = 0;
                    var4_15 = var2_2;
                    try {
                        var11_25 = var1_1.read(var10_20, var3_14, var11_25);
                    }
                    catch (InterruptedIOException var8_6) {
                        var4_15 = var2_2;
                        Thread.interrupted();
                        var2_2 = true;
                        var11_25 = var9_19;
                    }
                    if (var11_25 >= 0) break block22;
                    if (var6_17 == Integer.MAX_VALUE) {
                        var7_18 = var2_2;
                        var8_8 = var10_20;
                        var4_15 = var2_2;
                        if (var10_20.length == var3_14) break;
                        var4_15 = var2_2;
                        var8_9 = Arrays.copyOf(var10_20, var3_14);
                        var7_18 = var2_2;
                        break;
                    }
                    var4_15 = var2_2;
                    var4_15 = var2_2;
                    var8_10 = new EOFException("Detect premature EOF");
                    var4_15 = var2_2;
                    throw var8_10;
                }
                var3_14 += var11_25;
                var5_16 = var10_20;
                ** while (true)
            }
            try {
                var1_1.close();
            }
            catch (IOException var10_21) {
            }
            catch (InterruptedIOException var10_22) {
                var7_18 = true;
            }
            if (var7_18) {
                Thread.currentThread().interrupt();
            }
            return var8_11;
            catch (InterruptedIOException var8_3) {
                Thread.interrupted();
                var2_2 = true;
                continue;
            }
            break;
        } while (true);
    }

    public Certificate[] getCertificates() {
        return null;
    }

    public CodeSigner[] getCodeSigners() {
        return null;
    }

    public abstract URL getCodeSourceURL();

    public abstract int getContentLength() throws IOException;

    public abstract InputStream getInputStream() throws IOException;

    public Manifest getManifest() throws IOException {
        return null;
    }

    public abstract String getName();

    public abstract URL getURL();
}

