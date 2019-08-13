/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.ETC1;
import android.opengl.GLES10;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ETC1Util {
    public static ETC1Texture compressTexture(Buffer buffer, int n, int n2, int n3, int n4) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(ETC1.getEncodedDataSize(n, n2)).order(ByteOrder.nativeOrder());
        ETC1.encodeImage(buffer, n, n2, n3, n4, byteBuffer);
        return new ETC1Texture(n, n2, byteBuffer);
    }

    public static ETC1Texture createTexture(InputStream inputStream) throws IOException {
        byte[] arrby = new byte[4096];
        if (inputStream.read(arrby, 0, 16) == 16) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());
            byteBuffer.put(arrby, 0, 16).position(0);
            if (ETC1.isValid(byteBuffer)) {
                int n;
                int n2 = ETC1.getWidth(byteBuffer);
                int n3 = ETC1.getHeight(byteBuffer);
                int n4 = ETC1.getEncodedDataSize(n2, n3);
                byteBuffer = ByteBuffer.allocateDirect(n4).order(ByteOrder.nativeOrder());
                for (int i = 0; i < n4; i += n) {
                    n = Math.min(arrby.length, n4 - i);
                    if (inputStream.read(arrby, 0, n) == n) {
                        byteBuffer.put(arrby, 0, n);
                        continue;
                    }
                    throw new IOException("Unable to read PKM file data.");
                }
                byteBuffer.position(0);
                return new ETC1Texture(n2, n3, byteBuffer);
            }
            throw new IOException("Not a PKM file.");
        }
        throw new IOException("Unable to read PKM file header.");
    }

    public static boolean isETC1Supported() {
        int[] arrn = new int[20];
        GLES10.glGetIntegerv(34466, arrn, 0);
        int n = arrn[0];
        int[] arrn2 = arrn;
        if (n > arrn.length) {
            arrn2 = new int[n];
        }
        GLES10.glGetIntegerv(34467, arrn2, 0);
        for (int i = 0; i < n; ++i) {
            if (arrn2[i] != 36196) continue;
            return true;
        }
        return false;
    }

    public static void loadTexture(int n, int n2, int n3, int n4, int n5, ETC1Texture object) {
        if (n4 == 6407) {
            if (n5 != 33635 && n5 != 5121) {
                throw new IllegalArgumentException("Unsupported fallbackType");
            }
            int n6 = ((ETC1Texture)object).getWidth();
            int n7 = ((ETC1Texture)object).getHeight();
            ByteBuffer byteBuffer = ((ETC1Texture)object).getData();
            if (ETC1Util.isETC1Supported()) {
                GLES10.glCompressedTexImage2D(n, n2, 36196, n6, n7, n3, byteBuffer.remaining(), byteBuffer);
            } else {
                int n8 = n5 != 5121 ? 1 : 0;
                n8 = n8 != 0 ? 2 : 3;
                int n9 = n8 * n6;
                object = ByteBuffer.allocateDirect(n9 * n7).order(ByteOrder.nativeOrder());
                ETC1.decodeImage(byteBuffer, (Buffer)object, n6, n7, n8, n9);
                GLES10.glTexImage2D(n, n2, n4, n6, n7, n3, n4, n5, (Buffer)object);
            }
            return;
        }
        throw new IllegalArgumentException("fallbackFormat must be GL_RGB");
    }

    public static void loadTexture(int n, int n2, int n3, int n4, int n5, InputStream inputStream) throws IOException {
        ETC1Util.loadTexture(n, n2, n3, n4, n5, ETC1Util.createTexture(inputStream));
    }

    public static void writeTexture(ETC1Texture object, OutputStream outputStream) throws IOException {
        int n;
        int n2;
        byte[] arrby;
        int n3;
        ByteBuffer byteBuffer = ((ETC1Texture)object).getData();
        int n4 = byteBuffer.position();
        try {
            n3 = ((ETC1Texture)object).getWidth();
            n = ((ETC1Texture)object).getHeight();
            object = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());
            ETC1.formatHeader((Buffer)object, n3, n);
            arrby = new byte[4096];
            ((ByteBuffer)object).get(arrby, 0, 16);
            outputStream.write(arrby, 0, 16);
            n3 = ETC1.getEncodedDataSize(n3, n);
        }
        catch (Throwable throwable) {
            byteBuffer.position(n4);
            throw throwable;
        }
        for (n = 0; n < n3; n += n2) {
            n2 = Math.min(arrby.length, n3 - n);
            byteBuffer.get(arrby, 0, n2);
            outputStream.write(arrby, 0, n2);
        }
        byteBuffer.position(n4);
        return;
    }

    public static class ETC1Texture {
        private ByteBuffer mData;
        private int mHeight;
        private int mWidth;

        public ETC1Texture(int n, int n2, ByteBuffer byteBuffer) {
            this.mWidth = n;
            this.mHeight = n2;
            this.mData = byteBuffer;
        }

        public ByteBuffer getData() {
            return this.mData;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getWidth() {
            return this.mWidth;
        }
    }

}

