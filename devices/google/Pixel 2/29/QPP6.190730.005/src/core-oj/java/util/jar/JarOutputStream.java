/*
 * Decompiled with CFR 0.145.
 */
package java.util.jar;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class JarOutputStream
extends ZipOutputStream {
    private static final int JAR_MAGIC = 51966;
    private boolean firstEntry = true;

    public JarOutputStream(OutputStream outputStream) throws IOException {
        super(outputStream);
    }

    public JarOutputStream(OutputStream outputStream, Manifest manifest) throws IOException {
        super(outputStream);
        if (manifest != null) {
            this.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
            manifest.write(new BufferedOutputStream(this));
            this.closeEntry();
            return;
        }
        throw new NullPointerException("man");
    }

    private static int get16(byte[] arrby, int n) {
        return Byte.toUnsignedInt(arrby[n]) | Byte.toUnsignedInt(arrby[n + 1]) << 8;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean hasMagic(byte[] arrby) {
        try {
            int n2;
            for (int n = 0; n < arrby.length; n += n2 + 4) {
                if (JarOutputStream.get16(arrby, n) == 51966) {
                    return true;
                }
                n2 = JarOutputStream.get16(arrby, n + 2);
            }
            return false;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            // empty catch block
        }
        return false;
    }

    private static void set16(byte[] arrby, int n, int n2) {
        arrby[n + 0] = (byte)n2;
        arrby[n + 1] = (byte)(n2 >> 8);
    }

    @Override
    public void putNextEntry(ZipEntry zipEntry) throws IOException {
        if (this.firstEntry) {
            byte[] arrby = zipEntry.getExtra();
            if (arrby == null || !JarOutputStream.hasMagic(arrby)) {
                byte[] arrby2;
                if (arrby == null) {
                    arrby2 = new byte[4];
                } else {
                    arrby2 = new byte[arrby.length + 4];
                    System.arraycopy(arrby, 0, arrby2, 4, arrby.length);
                }
                JarOutputStream.set16(arrby2, 0, 51966);
                JarOutputStream.set16(arrby2, 2, 0);
                zipEntry.setExtra(arrby2);
            }
            this.firstEntry = false;
        }
        super.putNextEntry(zipEntry);
    }
}

