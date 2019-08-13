/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import java.io.DataInputStream;
import java.io.IOException;

public class GesturePoint {
    public final long timestamp;
    public final float x;
    public final float y;

    public GesturePoint(float f, float f2, long l) {
        this.x = f;
        this.y = f2;
        this.timestamp = l;
    }

    static GesturePoint deserialize(DataInputStream dataInputStream) throws IOException {
        return new GesturePoint(dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readLong());
    }

    public Object clone() {
        return new GesturePoint(this.x, this.y, this.timestamp);
    }
}

