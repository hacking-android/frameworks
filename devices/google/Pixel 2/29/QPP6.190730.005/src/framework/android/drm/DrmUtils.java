/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DrmUtils {
    public static ExtendedMetadataParser getExtendedMetadataParser(byte[] arrby) {
        return new ExtendedMetadataParser(arrby);
    }

    private static void quietlyDispose(Closeable closeable) {
        block2 : {
            if (closeable == null) break block2;
            try {
                closeable.close();
            }
            catch (IOException iOException) {}
        }
    }

    static byte[] readBytes(File arrby) throws IOException {
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        block4 : {
            fileInputStream = new FileInputStream((File)arrby);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            arrby = null;
            int n = bufferedInputStream.available();
            if (n <= 0) break block4;
            arrby = new byte[n];
            bufferedInputStream.read(arrby);
        }
        return arrby;
        finally {
            DrmUtils.quietlyDispose(bufferedInputStream);
            DrmUtils.quietlyDispose(fileInputStream);
        }
    }

    static byte[] readBytes(String string2) throws IOException {
        return DrmUtils.readBytes(new File(string2));
    }

    static void removeFile(String string2) throws IOException {
        new File(string2).delete();
    }

    static void writeToFile(String object, byte[] arrby) throws IOException {
        Object object2 = null;
        if (object != null && arrby != null) {
            Object object3 = object2;
            object3 = object2;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream((String)object);
                object3 = object = fileOutputStream;
            }
            catch (Throwable throwable) {
                DrmUtils.quietlyDispose(object3);
                throw throwable;
            }
            ((FileOutputStream)object).write(arrby);
            DrmUtils.quietlyDispose((Closeable)object);
        }
    }

    public static class ExtendedMetadataParser {
        HashMap<String, String> mMap = new HashMap();

        private ExtendedMetadataParser(byte[] arrby) {
            int n = 0;
            while (n < arrby.length) {
                String string2;
                int n2 = this.readByte(arrby, n);
                int n3 = n + 1;
                n = this.readByte(arrby, n3);
                String string3 = this.readMultipleBytes(arrby, n2, ++n3);
                n2 = n3 + n2;
                String string4 = string2 = this.readMultipleBytes(arrby, n, n2);
                if (string2.equals(" ")) {
                    string4 = "";
                }
                n = n2 + n;
                this.mMap.put(string3, string4);
            }
        }

        private int readByte(byte[] arrby, int n) {
            return arrby[n];
        }

        private String readMultipleBytes(byte[] arrby, int n, int n2) {
            byte[] arrby2 = new byte[n];
            int n3 = n2;
            int n4 = 0;
            while (n3 < n2 + n) {
                arrby2[n4] = arrby[n3];
                ++n3;
                ++n4;
            }
            return new String(arrby2);
        }

        public String get(String string2) {
            return this.mMap.get(string2);
        }

        public Iterator<String> iterator() {
            return this.mMap.values().iterator();
        }

        public Iterator<String> keyIterator() {
            return this.mMap.keySet().iterator();
        }
    }

}

