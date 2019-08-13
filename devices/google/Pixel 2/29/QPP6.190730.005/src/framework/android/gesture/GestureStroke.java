/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.gesture.GesturePoint;
import android.gesture.GestureUtils;
import android.gesture.OrientedBoundingBox;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GestureStroke {
    static final float TOUCH_TOLERANCE = 3.0f;
    public final RectF boundingBox;
    public final float length;
    private Path mCachedPath;
    public final float[] points;
    private final long[] timestamps;

    private GestureStroke(RectF rectF, float f, float[] arrf, long[] arrl) {
        this.boundingBox = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        this.length = f;
        this.points = (float[])arrf.clone();
        this.timestamps = (long[])arrl.clone();
    }

    public GestureStroke(ArrayList<GesturePoint> arrayList) {
        int n = arrayList.size();
        float[] arrf = new float[n * 2];
        long[] arrl = new long[n];
        RectF rectF = null;
        float f = 0.0f;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            GesturePoint gesturePoint = arrayList.get(i);
            arrf[i * 2] = gesturePoint.x;
            arrf[i * 2 + 1] = gesturePoint.y;
            arrl[n2] = gesturePoint.timestamp;
            if (rectF == null) {
                rectF = new RectF();
                rectF.top = gesturePoint.y;
                rectF.left = gesturePoint.x;
                rectF.right = gesturePoint.x;
                rectF.bottom = gesturePoint.y;
                f = 0.0f;
            } else {
                f = (float)((double)f + Math.hypot(gesturePoint.x - arrf[(i - 1) * 2], gesturePoint.y - arrf[(i - 1) * 2 + 1]));
                rectF.union(gesturePoint.x, gesturePoint.y);
            }
            ++n2;
        }
        this.timestamps = arrl;
        this.points = arrf;
        this.boundingBox = rectF;
        this.length = f;
    }

    static GestureStroke deserialize(DataInputStream dataInputStream) throws IOException {
        int n = dataInputStream.readInt();
        ArrayList<GesturePoint> arrayList = new ArrayList<GesturePoint>(n);
        for (int i = 0; i < n; ++i) {
            arrayList.add(GesturePoint.deserialize(dataInputStream));
        }
        return new GestureStroke(arrayList);
    }

    private void makePath() {
        float[] arrf = this.points;
        int n = arrf.length;
        Path path = null;
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < n; i += 2) {
            float f3;
            float f4;
            Path path2;
            block4 : {
                float f5;
                float f6;
                block5 : {
                    block3 : {
                        f5 = arrf[i];
                        f6 = arrf[i + 1];
                        if (path != null) break block3;
                        path2 = new Path();
                        path2.moveTo(f5, f6);
                        f4 = f5;
                        f3 = f6;
                        break block4;
                    }
                    f3 = Math.abs(f5 - f);
                    float f7 = Math.abs(f6 - f2);
                    if (f3 >= 3.0f) break block5;
                    path2 = path;
                    f4 = f;
                    f3 = f2;
                    if (!(f7 >= 3.0f)) break block4;
                }
                path.quadTo(f, f2, (f5 + f) / 2.0f, (f6 + f2) / 2.0f);
                f4 = f5;
                f3 = f6;
                path2 = path;
            }
            path = path2;
            f = f4;
            f2 = f3;
        }
        this.mCachedPath = path;
    }

    public void clearPath() {
        Path path = this.mCachedPath;
        if (path != null) {
            path.rewind();
        }
    }

    public Object clone() {
        return new GestureStroke(this.boundingBox, this.length, this.points, this.timestamps);
    }

    public OrientedBoundingBox computeOrientedBoundingBox() {
        return GestureUtils.computeOrientedBoundingBox(this.points);
    }

    void draw(Canvas canvas, Paint paint) {
        if (this.mCachedPath == null) {
            this.makePath();
        }
        canvas.drawPath(this.mCachedPath, paint);
    }

    public Path getPath() {
        if (this.mCachedPath == null) {
            this.makePath();
        }
        return this.mCachedPath;
    }

    void serialize(DataOutputStream dataOutputStream) throws IOException {
        float[] arrf = this.points;
        long[] arrl = this.timestamps;
        int n = this.points.length;
        dataOutputStream.writeInt(n / 2);
        for (int i = 0; i < n; i += 2) {
            dataOutputStream.writeFloat(arrf[i]);
            dataOutputStream.writeFloat(arrf[i + 1]);
            dataOutputStream.writeLong(arrl[i / 2]);
        }
    }

    public Path toPath(float f, float f2, int n) {
        float[] arrf = GestureUtils.temporalSampling(this, n);
        Object object = this.boundingBox;
        GestureUtils.translate(arrf, -((RectF)object).left, -((RectF)object).top);
        f /= ((RectF)object).width();
        f2 /= ((RectF)object).height();
        if (f > f2) {
            f = f2;
        }
        GestureUtils.scale(arrf, f, f);
        float f3 = 0.0f;
        float f4 = 0.0f;
        object = null;
        int n2 = arrf.length;
        for (n = 0; n < n2; n += 2) {
            Object object2;
            block6 : {
                float f5;
                float f6;
                block7 : {
                    block5 : {
                        f5 = arrf[n];
                        f6 = arrf[n + 1];
                        if (object != null) break block5;
                        object2 = new Path();
                        ((Path)object2).moveTo(f5, f6);
                        f2 = f5;
                        f = f6;
                        break block6;
                    }
                    f = Math.abs(f5 - f3);
                    float f7 = Math.abs(f6 - f4);
                    if (f >= 3.0f) break block7;
                    f2 = f3;
                    f = f4;
                    object2 = object;
                    if (!(f7 >= 3.0f)) break block6;
                }
                ((Path)object).quadTo(f3, f4, (f5 + f3) / 2.0f, (f6 + f4) / 2.0f);
                f2 = f5;
                f = f6;
                object2 = object;
            }
            f3 = f2;
            f4 = f;
            object = object2;
        }
        return object;
    }
}

