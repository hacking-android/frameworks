/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import java.util.GregorianCalendar;

public class GeomagneticField {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long BASE_TIME;
    private static final float[][] DELTA_G;
    private static final float[][] DELTA_H;
    private static final float EARTH_REFERENCE_RADIUS_KM = 6371.2f;
    private static final float EARTH_SEMI_MAJOR_AXIS_KM = 6378.137f;
    private static final float EARTH_SEMI_MINOR_AXIS_KM = 6356.7524f;
    private static final float[][] G_COEFF;
    private static final float[][] H_COEFF;
    private static final float[][] SCHMIDT_QUASI_NORM_FACTORS;
    private float mGcLatitudeRad;
    private float mGcLongitudeRad;
    private float mGcRadiusKm;
    private float mX;
    private float mY;
    private float mZ;

    static {
        float[] arrf = new float[]{0.0f};
        float[] arrf2 = new float[]{-29438.5f, -1501.1f};
        float[] arrf3 = new float[]{-2445.3f, 3012.5f, 1676.6f};
        float[] arrf4 = new float[]{1351.1f, -2352.3f, 1225.6f, 581.9f};
        float[] arrf5 = new float[]{907.2f, 813.7f, 120.3f, -335.0f, 70.3f};
        float[] arrf6 = new float[]{-232.6f, 360.1f, 192.4f, -141.0f, -157.4f, 4.3f};
        float[] arrf7 = new float[]{69.5f, 67.4f, 72.8f, -129.8f, -29.0f, 13.2f, -70.9f};
        float[] arrf8 = new float[]{81.6f, -76.1f, -6.8f, 51.9f, 15.0f, 9.3f, -2.8f, 6.7f};
        float[] arrf9 = new float[]{3.1f, -1.5f, -2.3f, 2.1f, -0.9f, 0.6f, -0.7f, 0.2f, 1.7f, -0.2f, 0.4f, 3.5f};
        float[] arrf10 = new float[]{-2.0f, -0.3f, 0.4f, 1.3f, -0.9f, 0.9f, 0.1f, 0.5f, -0.4f, -0.4f, 0.2f, -0.9f, 0.0f};
        G_COEFF = new float[][]{arrf, arrf2, arrf3, arrf4, arrf5, arrf6, arrf7, arrf8, {24.0f, 8.6f, -16.9f, -3.2f, -20.6f, 13.3f, 11.7f, -16.0f, -2.0f}, {5.4f, 8.8f, 3.1f, -3.1f, 0.6f, -13.3f, -0.1f, 8.7f, -9.1f, -10.5f}, {-1.9f, -6.5f, 0.2f, 0.6f, -0.6f, 1.7f, -0.7f, 2.1f, 2.3f, -1.8f, -3.6f}, arrf9, arrf10};
        arrf = new float[]{0.0f};
        arrf2 = new float[]{0.0f, 4796.2f};
        arrf3 = new float[]{0.0f, -2845.6f, -642.0f};
        arrf4 = new float[]{0.0f, -115.3f, 245.0f, -538.3f};
        arrf5 = new float[]{0.0f, 283.4f, -188.6f, 180.9f, -329.5f};
        arrf6 = new float[]{0.0f, 47.4f, 196.9f, -119.4f, 16.1f, 100.1f};
        arrf7 = new float[]{0.0f, -20.7f, 33.2f, 58.8f, -66.5f, 7.3f, 62.5f};
        arrf8 = new float[]{0.0f, -54.1f, -19.4f, 5.6f, 24.4f, 3.3f, -27.5f, -2.3f};
        arrf9 = new float[]{0.0f, 10.2f, -18.1f, 13.2f, -14.6f, 16.2f, 5.7f, -9.1f, 2.2f};
        arrf10 = new float[]{0.0f, 3.3f, -0.3f, 4.6f, 4.4f, -7.9f, -0.6f, -4.1f, -2.8f, -1.1f, -8.7f};
        float[] arrf11 = new float[]{0.0f, -0.1f, 2.1f, -0.7f, -1.1f, 0.7f, -0.2f, -2.1f, -1.5f, -2.5f, -2.0f, -2.3f};
        float[] arrf12 = new float[]{0.0f, -1.0f, 0.5f, 1.8f, -2.2f, 0.3f, 0.7f, -0.1f, 0.3f, 0.2f, -0.9f, -0.2f, 0.7f};
        H_COEFF = new float[][]{arrf, arrf2, arrf3, arrf4, arrf5, arrf6, arrf7, arrf8, arrf9, {0.0f, -21.6f, 10.8f, 11.7f, -6.8f, -6.9f, 7.8f, 1.0f, -3.9f, 8.5f}, arrf10, arrf11, arrf12};
        arrf = new float[]{0.0f};
        arrf2 = new float[]{10.7f, 17.9f};
        arrf3 = new float[]{3.1f, -6.2f, -0.4f, -10.4f};
        arrf4 = new float[]{-0.4f, 0.8f, -9.2f, 4.0f, -4.2f};
        arrf5 = new float[]{-0.5f, -0.2f, -0.6f, 2.4f, -1.1f, 0.3f, 1.5f};
        arrf6 = new float[]{0.2f, -0.2f, -0.4f, 1.3f, 0.2f, -0.4f, -0.9f, 0.3f};
        arrf7 = new float[]{0.0f, -0.1f, -0.1f, 0.4f, -0.5f, -0.2f, 0.1f, 0.0f, -0.2f, -0.1f};
        arrf8 = new float[]{0.1f, 0.0f, 0.0f, 0.1f, -0.1f, 0.0f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        DELTA_G = new float[][]{arrf, arrf2, {-8.6f, -3.3f, 2.4f}, arrf3, arrf4, {-0.2f, 0.1f, -1.4f, 0.0f, 1.3f, 3.8f}, arrf5, arrf6, {0.0f, 0.1f, -0.5f, 0.5f, -0.2f, 0.4f, 0.2f, -0.4f, 0.3f}, arrf7, {0.0f, 0.0f, -0.1f, 0.3f, -0.1f, -0.1f, -0.1f, 0.0f, -0.2f, -0.1f, -0.2f}, {0.0f, 0.0f, -0.1f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.1f, -0.1f}, arrf8};
        arrf = new float[]{0.0f, -27.1f, -13.3f};
        arrf2 = new float[]{0.0f, 0.0f, -2.2f, -0.7f, 0.1f, 1.0f, 1.3f};
        arrf3 = new float[]{0.0f, -0.2f, -0.1f, -0.2f, 0.1f, 0.1f, 0.0f, -0.2f, 0.4f, 0.3f};
        arrf4 = new float[]{0.0f, 0.0f, 0.1f, 0.0f, 0.1f, 0.0f, 0.0f, 0.1f, 0.0f, -0.1f, 0.0f, -0.1f};
        DELTA_H = new float[][]{{0.0f}, {0.0f, -26.8f}, arrf, {0.0f, 8.4f, -0.4f, 2.3f}, {0.0f, -0.6f, 5.3f, 3.0f, -5.3f}, {0.0f, 0.4f, 1.6f, -1.1f, 3.3f, 0.1f}, arrf2, {0.0f, 0.7f, 0.5f, -0.2f, -0.1f, -0.7f, 0.1f, 0.1f}, {0.0f, -0.3f, 0.3f, 0.3f, 0.6f, -0.1f, -0.2f, 0.3f, 0.0f}, arrf3, {0.0f, 0.1f, -0.1f, 0.0f, 0.0f, -0.2f, 0.1f, -0.1f, -0.2f, 0.1f, -0.1f}, arrf4, {0.0f, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}};
        BASE_TIME = new GregorianCalendar(2015, 1, 1).getTimeInMillis();
        SCHMIDT_QUASI_NORM_FACTORS = GeomagneticField.computeSchmidtQuasiNormFactors(G_COEFF.length);
    }

    public GeomagneticField(float f, float f2, float f3, long l) {
        int n;
        int n2;
        int n3 = G_COEFF.length;
        float f4 = Math.min(89.99999f, Math.max(-89.99999f, f));
        this.computeGeocentricCoordinates(f4, f2, f3);
        LegendreTable legendreTable = new LegendreTable(n3 - 1, (float)(1.5707963267948966 - (double)this.mGcLatitudeRad));
        float[] arrf = new float[n3 + 2];
        arrf[0] = 1.0f;
        arrf[1] = 6371.2f / this.mGcRadiusKm;
        for (n = 2; n < arrf.length; ++n) {
            arrf[n] = arrf[n - 1] * arrf[1];
        }
        float[] arrf2 = new float[n3];
        float[] arrf3 = new float[n3];
        arrf2[0] = 0.0f;
        arrf3[0] = 1.0f;
        arrf2[1] = (float)Math.sin(this.mGcLongitudeRad);
        arrf3[1] = (float)Math.cos(this.mGcLongitudeRad);
        for (n = 2; n < n3; ++n) {
            n2 = n >> 1;
            arrf2[n] = arrf2[n - n2] * arrf3[n2] + arrf3[n - n2] * arrf2[n2];
            arrf3[n] = arrf3[n - n2] * arrf3[n2] - arrf2[n - n2] * arrf2[n2];
        }
        float f5 = 1.0f / (float)Math.cos(this.mGcLatitudeRad);
        float f6 = (float)(l - BASE_TIME) / 3.1536001E10f;
        f3 = 0.0f;
        f2 = 0.0f;
        f = 0.0f;
        for (n = 1; n < n3; ++n) {
            for (n2 = 0; n2 <= n; ++n2) {
                float f7 = G_COEFF[n][n2] + DELTA_G[n][n2] * f6;
                float f8 = H_COEFF[n][n2] + DELTA_H[n][n2] * f6;
                f3 += arrf[n + 2] * (arrf3[n2] * f7 + arrf2[n2] * f8) * legendreTable.mPDeriv[n][n2] * SCHMIDT_QUASI_NORM_FACTORS[n][n2];
                f2 += arrf[n + 2] * (float)n2 * (arrf2[n2] * f7 - arrf3[n2] * f8) * legendreTable.mP[n][n2] * SCHMIDT_QUASI_NORM_FACTORS[n][n2] * f5;
                f -= (float)(n + 1) * arrf[n + 2] * (arrf3[n2] * f7 + arrf2[n2] * f8) * legendreTable.mP[n][n2] * SCHMIDT_QUASI_NORM_FACTORS[n][n2];
            }
        }
        double d = Math.toRadians(f4) - (double)this.mGcLatitudeRad;
        this.mX = (float)((double)f3 * Math.cos(d) + (double)f * Math.sin(d));
        this.mY = f2;
        this.mZ = (float)((double)(-f3) * Math.sin(d) + (double)f * Math.cos(d));
    }

    private void computeGeocentricCoordinates(float f, float f2, float f3) {
        double d = Math.toRadians(f);
        float f4 = (float)Math.cos(d);
        float f5 = (float)Math.sin(d);
        float f6 = f5 / f4;
        f = (float)Math.sqrt(4.0680636E7f * f4 * f4 + 4.04083E7f * f5 * f5);
        this.mGcLatitudeRad = (float)Math.atan((f * (f3 /= 1000.0f) + 4.04083E7f) * f6 / (f * f3 + 4.0680636E7f));
        this.mGcLongitudeRad = (float)Math.toRadians(f2);
        this.mGcRadiusKm = (float)Math.sqrt(f3 * f3 + 2.0f * f3 * (float)Math.sqrt(4.0680636E7f * f4 * f4 + 4.04083E7f * f5 * f5) + (4.0680636E7f * 4.0680636E7f * f4 * f4 + 4.04083E7f * 4.04083E7f * f5 * f5) / (4.0680636E7f * f4 * f4 + 4.04083E7f * f5 * f5));
    }

    private static float[][] computeSchmidtQuasiNormFactors(int n) {
        float[][] arrarrf = new float[n + 1][];
        arrarrf[0] = new float[]{1.0f};
        for (int i = 1; i <= n; ++i) {
            arrarrf[i] = new float[i + 1];
            arrarrf[i][0] = arrarrf[i - 1][0] * (float)(i * 2 - 1) / (float)i;
            for (int j = 1; j <= i; ++j) {
                float[] arrf = arrarrf[i];
                float f = arrarrf[i][j - 1];
                int n2 = j == 1 ? 2 : 1;
                arrf[j] = f * (float)Math.sqrt((float)((i - j + 1) * n2) / (float)(i + j));
            }
        }
        return arrarrf;
    }

    public float getDeclination() {
        return (float)Math.toDegrees(Math.atan2(this.mY, this.mX));
    }

    public float getFieldStrength() {
        float f = this.mX;
        float f2 = this.mY;
        float f3 = this.mZ;
        return (float)Math.sqrt(f * f + f2 * f2 + f3 * f3);
    }

    public float getHorizontalStrength() {
        return (float)Math.hypot(this.mX, this.mY);
    }

    public float getInclination() {
        return (float)Math.toDegrees(Math.atan2(this.mZ, this.getHorizontalStrength()));
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getZ() {
        return this.mZ;
    }

    private static class LegendreTable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public final float[][] mP;
        public final float[][] mPDeriv;

        public LegendreTable(int n, float f) {
            float f2 = (float)Math.cos(f);
            f = (float)Math.sin(f);
            this.mP = new float[n + 1][];
            this.mPDeriv = new float[n + 1][];
            this.mP[0] = new float[]{1.0f};
            this.mPDeriv[0] = new float[]{0.0f};
            for (int i = 1; i <= n; ++i) {
                this.mP[i] = new float[i + 1];
                this.mPDeriv[i] = new float[i + 1];
                for (int j = 0; j <= i; ++j) {
                    float[][] arrf;
                    float[][] arrf2;
                    if (i == j) {
                        arrf = this.mP;
                        arrf[i][j] = arrf[i - 1][j - 1] * f;
                        arrf2 = this.mPDeriv;
                        arrf2[i][j] = arrf[i - 1][j - 1] * f2 + arrf2[i - 1][j - 1] * f;
                        continue;
                    }
                    if (i != 1 && j != i - 1) {
                        float f3 = (float)((i - 1) * (i - 1) - j * j) / (float)((i * 2 - 1) * (i * 2 - 3));
                        arrf2 = this.mP;
                        arrf2[i][j] = arrf2[i - 1][j] * f2 - arrf2[i - 2][j] * f3;
                        arrf = this.mPDeriv;
                        arrf[i][j] = -f * arrf2[i - 1][j] + arrf[i - 1][j] * f2 - arrf[i - 2][j] * f3;
                        continue;
                    }
                    arrf2 = this.mP;
                    arrf2[i][j] = arrf2[i - 1][j] * f2;
                    arrf = this.mPDeriv;
                    arrf[i][j] = -f * arrf2[i - 1][j] + arrf[i - 1][j] * f2;
                }
            }
        }
    }

}

