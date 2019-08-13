/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.text.Spannable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

public interface MovementMethod {
    public boolean canSelectArbitrarily();

    public void initialize(TextView var1, Spannable var2);

    public boolean onGenericMotionEvent(TextView var1, Spannable var2, MotionEvent var3);

    public boolean onKeyDown(TextView var1, Spannable var2, int var3, KeyEvent var4);

    public boolean onKeyOther(TextView var1, Spannable var2, KeyEvent var3);

    public boolean onKeyUp(TextView var1, Spannable var2, int var3, KeyEvent var4);

    public void onTakeFocus(TextView var1, Spannable var2, int var3);

    public boolean onTouchEvent(TextView var1, Spannable var2, MotionEvent var3);

    public boolean onTrackballEvent(TextView var1, Spannable var2, MotionEvent var3);
}

