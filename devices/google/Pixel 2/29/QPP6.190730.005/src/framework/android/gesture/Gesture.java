/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.gesture.GestureStroke;
import android.gesture.GestureUtils;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Gesture
implements Parcelable {
    private static final boolean BITMAP_RENDERING_ANTIALIAS = true;
    private static final boolean BITMAP_RENDERING_DITHER = true;
    private static final int BITMAP_RENDERING_WIDTH = 2;
    public static final Parcelable.Creator<Gesture> CREATOR;
    private static final long GESTURE_ID_BASE;
    private static final AtomicInteger sGestureCount;
    private final RectF mBoundingBox = new RectF();
    private long mGestureID = GESTURE_ID_BASE + (long)sGestureCount.incrementAndGet();
    private final ArrayList<GestureStroke> mStrokes = new ArrayList();

    static {
        GESTURE_ID_BASE = System.currentTimeMillis();
        sGestureCount = new AtomicInteger(0);
        CREATOR = new Parcelable.Creator<Gesture>(){

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public Gesture createFromParcel(Parcel var1_1) {
                var2_4 = null;
                var3_5 = var1_1.readLong();
                var5_6 = new DataInputStream(new ByteArrayInputStream(var1_1.createByteArray()));
                var1_1 = Gesture.deserialize(var5_6);
lbl6: // 2 sources:
                do {
                    GestureUtils.closeStream(var5_6);
                    if (var1_1 == null) return var1_1;
                    Gesture.access$002((Gesture)var1_1, var3_5);
                    return var1_1;
                    break;
                } while (true);
                {
                    catch (Throwable var1_2) {
                    }
                    catch (IOException var1_3) {}
                    {
                        Log.e("Gestures", "Error reading Gesture from parcel:", var1_3);
                        var1_1 = var2_4;
                        ** continue;
                    }
                }
                GestureUtils.closeStream(var5_6);
                throw var1_2;
            }

            public Gesture[] newArray(int n) {
                return new Gesture[n];
            }
        };
    }

    static /* synthetic */ long access$002(Gesture gesture, long l) {
        gesture.mGestureID = l;
        return l;
    }

    static Gesture deserialize(DataInputStream dataInputStream) throws IOException {
        Gesture gesture = new Gesture();
        gesture.mGestureID = dataInputStream.readLong();
        int n = dataInputStream.readInt();
        for (int i = 0; i < n; ++i) {
            gesture.addStroke(GestureStroke.deserialize(dataInputStream));
        }
        return gesture;
    }

    public void addStroke(GestureStroke gestureStroke) {
        this.mStrokes.add(gestureStroke);
        this.mBoundingBox.union(gestureStroke.boundingBox);
    }

    public Object clone() {
        Gesture gesture = new Gesture();
        gesture.mBoundingBox.set(this.mBoundingBox.left, this.mBoundingBox.top, this.mBoundingBox.right, this.mBoundingBox.bottom);
        int n = this.mStrokes.size();
        for (int i = 0; i < n; ++i) {
            GestureStroke gestureStroke = this.mStrokes.get(i);
            gesture.mStrokes.add((GestureStroke)gestureStroke.clone());
        }
        return gesture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public RectF getBoundingBox() {
        return this.mBoundingBox;
    }

    public long getID() {
        return this.mGestureID;
    }

    public float getLength() {
        int n = 0;
        ArrayList<GestureStroke> arrayList = this.mStrokes;
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            n = (int)((float)n + arrayList.get((int)i).length);
        }
        return n;
    }

    public ArrayList<GestureStroke> getStrokes() {
        return this.mStrokes;
    }

    public int getStrokesCount() {
        return this.mStrokes.size();
    }

    void serialize(DataOutputStream dataOutputStream) throws IOException {
        ArrayList<GestureStroke> arrayList = this.mStrokes;
        int n = arrayList.size();
        dataOutputStream.writeLong(this.mGestureID);
        dataOutputStream.writeInt(n);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).serialize(dataOutputStream);
        }
    }

    void setID(long l) {
        this.mGestureID = l;
    }

    public Bitmap toBitmap(int n, int n2, int n3, int n4) {
        Bitmap bitmap = Bitmap.createBitmap(n, n2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(n4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2.0f);
        Path path = this.toPath();
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        float f = (float)(n - n3 * 2) / rectF.width();
        float f2 = (float)(n2 - n3 * 2) / rectF.height();
        if (f > f2) {
            f = f2;
        }
        paint.setStrokeWidth(2.0f / f);
        path.offset(-rectF.left + ((float)n - rectF.width() * f) / 2.0f, -rectF.top + ((float)n2 - rectF.height() * f) / 2.0f);
        canvas.translate(n3, n3);
        canvas.scale(f, f);
        canvas.drawPath(path, paint);
        return bitmap;
    }

    public Bitmap toBitmap(int n, int n2, int n3, int n4, int n5) {
        Bitmap bitmap = Bitmap.createBitmap(n, n2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(n3, n3);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(n5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2.0f);
        ArrayList<GestureStroke> arrayList = this.mStrokes;
        int n6 = arrayList.size();
        for (n5 = 0; n5 < n6; ++n5) {
            canvas.drawPath(arrayList.get(n5).toPath(n - n3 * 2, n2 - n3 * 2, n4), paint);
        }
        return bitmap;
    }

    public Path toPath() {
        return this.toPath(null);
    }

    public Path toPath(int n, int n2, int n3, int n4) {
        return this.toPath(null, n, n2, n3, n4);
    }

    public Path toPath(Path object) {
        Path path = object;
        if (object == null) {
            path = new Path();
        }
        object = this.mStrokes;
        int n = ((ArrayList)object).size();
        for (int i = 0; i < n; ++i) {
            path.addPath(((GestureStroke)((ArrayList)object).get(i)).getPath());
        }
        return path;
    }

    public Path toPath(Path object, int n, int n2, int n3, int n4) {
        Path path = object;
        if (object == null) {
            path = new Path();
        }
        object = this.mStrokes;
        int n5 = ((ArrayList)object).size();
        for (int i = 0; i < n5; ++i) {
            path.addPath(((GestureStroke)((ArrayList)object).get(i)).toPath(n - n3 * 2, n2 - n3 * 2, n4));
        }
        return path;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void writeToParcel(Parcel var1_1, int var2_3) {
        var1_1.writeLong(this.mGestureID);
        var2_3 = 0;
        var3_4 = new ByteArrayOutputStream(32768);
        var4_5 = new DataOutputStream(var3_4);
        this.serialize(var4_5);
        var2_3 = 1;
lbl8: // 2 sources:
        do {
            GestureUtils.closeStream(var4_5);
            GestureUtils.closeStream(var3_4);
            if (var2_3 == 0) return;
            var1_1.writeByteArray(var3_4.toByteArray());
            return;
            break;
        } while (true);
        {
            catch (Throwable var1_2) {
            }
            catch (IOException var5_6) {}
            {
                Log.e("Gestures", "Error writing Gesture to parcel:", var5_6);
                ** continue;
            }
        }
        GestureUtils.closeStream(var4_5);
        GestureUtils.closeStream(var3_4);
        throw var1_2;
    }

}

