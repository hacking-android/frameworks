/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;

public class FaceDetector {
    private static boolean sInitialized = false;
    private byte[] mBWBuffer;
    private long mDCR;
    private long mFD;
    private int mHeight;
    private int mMaxFaces;
    private long mSDK;
    private int mWidth;

    static {
        try {
            System.loadLibrary("FFTEm");
            FaceDetector.nativeClassInit();
            sInitialized = true;
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            Log.d("FFTEm", "face detection library not found!");
        }
    }

    public FaceDetector(int n, int n2, int n3) {
        if (!sInitialized) {
            return;
        }
        this.fft_initialize(n, n2, n3);
        this.mWidth = n;
        this.mHeight = n2;
        this.mMaxFaces = n3;
        this.mBWBuffer = new byte[n * n2];
    }

    private native void fft_destroy();

    private native int fft_detect(Bitmap var1);

    private native void fft_get_face(Face var1, int var2);

    private native int fft_initialize(int var1, int var2, int var3);

    private static native void nativeClassInit();

    protected void finalize() throws Throwable {
        this.fft_destroy();
    }

    public int findFaces(Bitmap bitmap, Face[] arrface) {
        if (!sInitialized) {
            return 0;
        }
        if (bitmap.getWidth() == this.mWidth && bitmap.getHeight() == this.mHeight) {
            if (arrface.length >= this.mMaxFaces) {
                int n;
                int n2 = n = this.fft_detect(bitmap);
                if (n >= this.mMaxFaces) {
                    n2 = this.mMaxFaces;
                }
                for (n = 0; n < n2; ++n) {
                    if (arrface[n] == null) {
                        arrface[n] = new Face();
                    }
                    this.fft_get_face(arrface[n], n);
                }
                return n2;
            }
            throw new IllegalArgumentException("faces[] smaller than maxFaces");
        }
        throw new IllegalArgumentException("bitmap size doesn't match initialization");
    }

    public class Face {
        public static final float CONFIDENCE_THRESHOLD = 0.4f;
        public static final int EULER_X = 0;
        public static final int EULER_Y = 1;
        public static final int EULER_Z = 2;
        private float mConfidence;
        private float mEyesDist;
        private float mMidPointX;
        private float mMidPointY;
        private float mPoseEulerX;
        private float mPoseEulerY;
        private float mPoseEulerZ;

        private Face() {
        }

        public float confidence() {
            return this.mConfidence;
        }

        public float eyesDistance() {
            return this.mEyesDist;
        }

        public void getMidPoint(PointF pointF) {
            pointF.set(this.mMidPointX, this.mMidPointY);
        }

        public float pose(int n) {
            if (n == 0) {
                return this.mPoseEulerX;
            }
            if (n == 1) {
                return this.mPoseEulerY;
            }
            if (n == 2) {
                return this.mPoseEulerZ;
            }
            throw new IllegalArgumentException();
        }
    }

}

