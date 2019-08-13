/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.RecordingCanvas;
import android.graphics.Rect;

public interface WindowCallbacks {
    public static final int RESIZE_MODE_DOCKED_DIVIDER = 1;
    public static final int RESIZE_MODE_FREEFORM = 0;
    public static final int RESIZE_MODE_INVALID = -1;

    public boolean onContentDrawn(int var1, int var2, int var3, int var4);

    public void onPostDraw(RecordingCanvas var1);

    public void onRequestDraw(boolean var1);

    public void onWindowDragResizeEnd();

    public void onWindowDragResizeStart(Rect var1, boolean var2, Rect var3, Rect var4, int var5);

    public void onWindowSizeIsChanging(Rect var1, boolean var2, Rect var3, Rect var4);
}

