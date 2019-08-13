/*
 * Decompiled with CFR 0.145.
 */
package java.util.jar;

import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;

public class Manifest
implements Cloneable {
    private Attributes attr = new Attributes();
    private Map<String, Attributes> entries = new HashMap<String, Attributes>();

    public Manifest() {
    }

    public Manifest(InputStream inputStream) throws IOException {
        this.read(inputStream);
    }

    public Manifest(Manifest manifest) {
        this.attr.putAll(manifest.getMainAttributes());
        this.entries.putAll(manifest.getEntries());
    }

    static void make72Safe(StringBuffer stringBuffer) {
        int n = stringBuffer.length();
        if (n > 72) {
            int n2 = 70;
            while (n2 < n - 2) {
                stringBuffer.insert(n2, "\r\n ");
                n2 += 72;
                n += 3;
            }
        }
    }

    private String parseName(byte[] object, int n) {
        if (this.toLower(object[0]) == 110 && this.toLower(object[1]) == 97 && this.toLower(object[2]) == 109 && this.toLower(object[3]) == 101 && object[4] == 58 && object[5] == 32) {
            try {
                object = new String((byte[])object, 6, n - 6, "UTF8");
                return object;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return null;
    }

    private int toLower(int n) {
        block0 : {
            if (n < 65 || n > 90) break block0;
            n = n - 65 + 97;
        }
        return n;
    }

    public void clear() {
        this.attr.clear();
        this.entries.clear();
    }

    public Object clone() {
        return new Manifest(this);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Manifest;
        boolean bl2 = false;
        if (bl) {
            if (this.attr.equals(((Manifest)(object = (Manifest)object)).getMainAttributes()) && this.entries.equals(((Manifest)object).getEntries())) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public Attributes getAttributes(String string) {
        return this.getEntries().get(string);
    }

    public Map<String, Attributes> getEntries() {
        return this.entries;
    }

    public Attributes getMainAttributes() {
        return this.attr;
    }

    public int hashCode() {
        return this.attr.hashCode() + this.entries.hashCode();
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void read(InputStream object) throws IOException {
        int n;
        FastInputStream fastInputStream = new FastInputStream((InputStream)object);
        byte[] arrby = new byte[512];
        this.attr.read(fastInputStream, arrby);
        int n2 = 0;
        int n3 = 0;
        int n4 = 2;
        Object var7_12 = null;
        boolean bl = true;
        Object var1_2 = null;
        while ((n = fastInputStream.readLine(arrby)) != -1) {
            Object object2;
            void var7_13;
            void var7_17;
            int n5 = n - 1;
            if (arrby[n5] != 10) throw new IOException("manifest line too long");
            n = n5;
            if (n5 > 0) {
                n = n5;
                if (arrby[n5 - 1] == 13) {
                    n = n5 - 1;
                }
            }
            if (n == 0 && bl) continue;
            bl = false;
            if (var7_13 == null) {
                object2 = this.parseName(arrby, n);
                if (object2 == null) throw new IOException("invalid manifest format");
                byte[] arrby2 = object2;
                if (fastInputStream.peek() == 32) {
                    byte[] arrby3 = new byte[n - 6];
                    System.arraycopy(arrby, 6, arrby3, 0, n - 6);
                    Object object3 = object2;
                    continue;
                }
            } else {
                void var1_3;
                object2 = new byte[((void)var1_3).length + n - 1];
                System.arraycopy((byte[])var1_3, 0, (byte[])object2, 0, ((void)var1_3).length);
                System.arraycopy(arrby, 1, (byte[])object2, ((void)var1_3).length, n - 1);
                if (fastInputStream.peek() == 32) {
                    Object object4 = object2;
                    continue;
                }
                String string = new String((byte[])object2, 0, ((Object)object2).length, "UTF8");
                Object var1_6 = null;
            }
            Attributes attributes = this.getAttributes((String)var7_17);
            object2 = attributes;
            if (attributes == null) {
                object2 = new Attributes(n4);
                this.entries.put((String)var7_17, (Attributes)object2);
            }
            ((Attributes)object2).read(fastInputStream, arrby);
            n4 = Math.max(2, (n3 += ((Attributes)object2).size()) / ++n2);
            Object var7_18 = null;
            bl = true;
        }
    }

    public void write(OutputStream object) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)object);
        this.attr.writeMain(dataOutputStream);
        for (Map.Entry<String, Attributes> entry : this.entries.entrySet()) {
            StringBuffer stringBuffer = new StringBuffer("Name: ");
            String string = entry.getKey();
            object = string;
            if (string != null) {
                object = string.getBytes("UTF8");
                object = new String((byte[])object, 0, 0, ((byte[])object).length);
            }
            stringBuffer.append((String)object);
            stringBuffer.append("\r\n");
            Manifest.make72Safe(stringBuffer);
            dataOutputStream.writeBytes(stringBuffer.toString());
            entry.getValue().write(dataOutputStream);
        }
        dataOutputStream.flush();
    }

    static class FastInputStream
    extends FilterInputStream {
        private byte[] buf;
        private int count = 0;
        private int pos = 0;

        FastInputStream(InputStream inputStream) {
            this(inputStream, 8192);
        }

        FastInputStream(InputStream inputStream, int n) {
            super(inputStream);
            this.buf = new byte[n];
        }

        private void fill() throws IOException {
            this.pos = 0;
            this.count = 0;
            InputStream inputStream = this.in;
            byte[] arrby = this.buf;
            int n = inputStream.read(arrby, 0, arrby.length);
            if (n > 0) {
                this.count = n;
            }
        }

        @Override
        public int available() throws IOException {
            return this.count - this.pos + this.in.available();
        }

        @Override
        public void close() throws IOException {
            if (this.in != null) {
                this.in.close();
                this.in = null;
                this.buf = null;
            }
        }

        public byte peek() throws IOException {
            int n;
            if (this.pos == this.count) {
                this.fill();
            }
            if ((n = this.pos) == this.count) {
                return -1;
            }
            return this.buf[n];
        }

        @Override
        public int read() throws IOException {
            if (this.pos >= this.count) {
                this.fill();
                if (this.pos >= this.count) {
                    return -1;
                }
            }
            byte[] arrby = this.buf;
            int n = this.pos;
            this.pos = n + 1;
            return Byte.toUnsignedInt(arrby[n]);
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            int n3;
            int n4 = n3 = this.count - this.pos;
            if (n3 <= 0) {
                if (n2 >= this.buf.length) {
                    return this.in.read(arrby, n, n2);
                }
                this.fill();
                n4 = n3 = this.count - this.pos;
                if (n3 <= 0) {
                    return -1;
                }
            }
            n3 = n2;
            if (n2 > n4) {
                n3 = n4;
            }
            System.arraycopy(this.buf, this.pos, arrby, n, n3);
            this.pos += n3;
            return n3;
        }

        public int readLine(byte[] arrby) throws IOException {
            return this.readLine(arrby, 0, arrby.length);
        }

        public int readLine(byte[] arrby, int n, int n2) throws IOException {
            int n3;
            block5 : {
                byte[] arrby2 = this.buf;
                n3 = 0;
                int n4 = n;
                n = n3;
                do {
                    int n5;
                    int n6;
                    int n7;
                    n3 = n;
                    if (n >= n2) break block5;
                    n3 = n7 = this.count - this.pos;
                    if (n7 <= 0) {
                        this.fill();
                        n3 = n7 = this.count - this.pos;
                        if (n7 <= 0) {
                            return -1;
                        }
                    }
                    n7 = n5 = n2 - n;
                    if (n5 > n3) {
                        n7 = n3;
                    }
                    n3 = n6 = this.pos;
                    do {
                        n3 = n5 = n3;
                        if (n5 >= n6 + n7) break;
                        n3 = n5 + 1;
                    } while (arrby2[n5] != 10);
                    n5 = this.pos;
                    n7 = n3 - n5;
                    System.arraycopy(arrby2, n5, arrby, n4, n7);
                    n4 += n7;
                    n += n7;
                    this.pos = n3;
                } while (arrby2[n3 - 1] != 10);
                n3 = n;
            }
            return n3;
        }

        @Override
        public long skip(long l) throws IOException {
            if (l <= 0L) {
                return 0L;
            }
            long l2 = this.count - this.pos;
            if (l2 <= 0L) {
                return this.in.skip(l);
            }
            long l3 = l;
            if (l > l2) {
                l3 = l2;
            }
            this.pos = (int)((long)this.pos + l3);
            return l3;
        }
    }

}

