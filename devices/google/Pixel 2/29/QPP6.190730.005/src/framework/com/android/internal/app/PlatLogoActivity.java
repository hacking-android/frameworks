/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.android.internal.app;

import android.animation.TimeAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import org.json.JSONObject;

public class PlatLogoActivity
extends Activity {
    TimeAnimator anim;
    PBackground bg;
    FrameLayout layout;

    private void launchNextStage() {
        Object object = this.getContentResolver();
        if (Settings.System.getLong((ContentResolver)object, "egg_mode", 0L) == 0L) {
            try {
                Settings.System.putLong((ContentResolver)object, "egg_mode", System.currentTimeMillis());
            }
            catch (RuntimeException runtimeException) {
                Log.e("PlatLogoActivity", "Can't write settings", runtimeException);
            }
        }
        try {
            object = new Intent("android.intent.action.MAIN");
            this.startActivity(((Intent)object).setFlags(268468224).addCategory("com.android.internal.category.PLATLOGO"));
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Log.e("PlatLogoActivity", "No more eggs.");
        }
        this.finish();
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.layout = new FrameLayout(this);
        this.setContentView(this.layout);
        this.bg = new PBackground();
        this.layout.setBackground(this.bg);
        object = this.getContentResolver();
        this.layout.setOnTouchListener(new View.OnTouchListener((ContentResolver)object){
            final String TOUCH_STATS;
            int maxPointers;
            final MotionEvent.PointerCoords pc0 = new MotionEvent.PointerCoords();
            final MotionEvent.PointerCoords pc1 = new MotionEvent.PointerCoords();
            double pressure_max;
            double pressure_min;
            int tapCount;
            final /* synthetic */ ContentResolver val$cr;
            {
                this.val$cr = contentResolver;
                this.TOUCH_STATS = "touch.stats";
            }

            @Override
            public boolean onTouch(View object, MotionEvent motionEvent) {
                block18 : {
                    int n;
                    float f;
                    block17 : {
                        double d;
                        block15 : {
                            block14 : {
                                block16 : {
                                    f = motionEvent.getPressure();
                                    n = motionEvent.getActionMasked();
                                    if (n == 0) break block15;
                                    if (n == 1) break block16;
                                    if (n == 2) break block17;
                                    if (n != 3) break block18;
                                }
                                object = Settings.System.getString(this.val$cr, "touch.stats");
                                if (object != null) break block14;
                                object = "{}";
                            }
                            try {
                                motionEvent = new JSONObject((String)object);
                                if (motionEvent.has("min")) {
                                    this.pressure_min = Math.min(this.pressure_min, motionEvent.getDouble("min"));
                                }
                                if (motionEvent.has("max")) {
                                    this.pressure_max = Math.max(this.pressure_max, motionEvent.getDouble("max"));
                                }
                                motionEvent.put("min", this.pressure_min);
                                motionEvent.put("max", this.pressure_max);
                                Settings.System.putString(this.val$cr, "touch.stats", motionEvent.toString());
                            }
                            catch (Exception exception) {
                                Log.e("PlatLogoActivity", "Can't write touch settings", exception);
                            }
                            if (this.maxPointers == 1) {
                                ++this.tapCount;
                                if (this.tapCount < 7) {
                                    PlatLogoActivity.this.bg.randomizePalette();
                                } else {
                                    PlatLogoActivity.this.launchNextStage();
                                }
                            } else {
                                this.tapCount = 0;
                            }
                            this.maxPointers = 0;
                            break block18;
                        }
                        this.pressure_max = d = (double)f;
                        this.pressure_min = d;
                    }
                    if ((double)f < this.pressure_min) {
                        this.pressure_min = f;
                    }
                    if ((double)f > this.pressure_max) {
                        this.pressure_max = f;
                    }
                    if ((n = motionEvent.getPointerCount()) > this.maxPointers) {
                        this.maxPointers = n;
                    }
                    if (n > 1) {
                        motionEvent.getPointerCoords(0, this.pc0);
                        motionEvent.getPointerCoords(1, this.pc1);
                        PlatLogoActivity.this.bg.setRadius((float)Math.hypot(this.pc0.x - this.pc1.x, this.pc0.y - this.pc1.y) / 2.0f);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        this.bg.randomizePalette();
        this.anim = new TimeAnimator();
        this.anim.setTimeListener(new TimeAnimator.TimeListener(){

            @Override
            public void onTimeUpdate(TimeAnimator timeAnimator, long l, long l2) {
                PlatLogoActivity.this.bg.setOffset((float)l / 60000.0f);
                PlatLogoActivity.this.bg.invalidateSelf();
            }
        });
        this.anim.start();
    }

    @Override
    public void onStop() {
        TimeAnimator timeAnimator = this.anim;
        if (timeAnimator != null) {
            timeAnimator.cancel();
            this.anim = null;
        }
        super.onStop();
    }

    private class PBackground
    extends Drawable {
        private int darkest;
        private float dp;
        private float maxRadius;
        private float offset;
        private int[] palette;
        private float radius;
        private float x;
        private float y;

        public PBackground() {
            this.randomizePalette();
        }

        @Override
        public void draw(Canvas canvas) {
            int[] arrn;
            if (this.dp == 0.0f) {
                this.dp = PlatLogoActivity.this.getResources().getDisplayMetrics().density;
            }
            float f = canvas.getWidth();
            float f2 = canvas.getHeight();
            if (this.radius == 0.0f) {
                this.setPosition(f / 2.0f, f2 / 2.0f);
                this.setRadius(f / 6.0f);
            }
            float f3 = this.radius * 0.667f;
            Paint paint = new Paint();
            paint.setStrokeCap(Paint.Cap.BUTT);
            canvas.translate(this.x, this.y);
            Path path = new Path();
            path.moveTo(-this.radius, f2);
            path.lineTo(-this.radius, 0.0f);
            float f4 = this.radius;
            path.arcTo(-f4, -f4, f4, f4, -180.0f, 270.0f, false);
            f4 = this.radius;
            path.lineTo(-f4, f4);
            f4 = Math.max(canvas.getWidth(), canvas.getHeight());
            paint.setStyle(Paint.Style.FILL);
            f4 *= 1.414f;
            int n = 0;
            while (f4 > this.radius * 2.0f + f3 * 2.0f) {
                arrn = this.palette;
                paint.setColor(arrn[n % arrn.length] | -16777216);
                canvas.drawOval(-f4 / 2.0f, -f4 / 2.0f, f4 / 2.0f, f4 / 2.0f, paint);
                f4 = (float)((double)f4 - (double)f3 * (Math.sin(((float)n / 20.0f + this.offset) * 3.14159f) + 1.100000023841858));
                ++n;
            }
            arrn = this.palette;
            paint.setColor(arrn[(this.darkest + 1) % arrn.length] | -16777216);
            f4 = this.radius;
            canvas.drawOval(-f4, -f4, f4, f4, paint);
            path.reset();
            path.moveTo(-this.radius, f2);
            path.lineTo(-this.radius, 0.0f);
            f4 = this.radius;
            path.arcTo(-f4, -f4, f4, f4, -180.0f, 270.0f, false);
            f4 = this.radius;
            path.lineTo(-f4 + f3, f4);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(f3 * 2.0f);
            paint.setColor(this.palette[this.darkest]);
            canvas.drawPath(path, paint);
            paint.setStrokeWidth(f3);
            paint.setColor(-1);
            canvas.drawPath(path, paint);
        }

        @Override
        public int getOpacity() {
            return 0;
        }

        public float lum(int n) {
            return ((float)Color.red(n) * 299.0f + (float)Color.green(n) * 587.0f + (float)Color.blue(n) * 114.0f) / 1000.0f;
        }

        public void randomizePalette() {
            int n;
            int n2 = (int)(Math.random() * 2.0) + 2;
            Object object = new float[]{(float)Math.random() * 360.0f, 1.0f, 1.0f};
            this.palette = new int[n2];
            this.darkest = 0;
            for (n = 0; n < n2; ++n) {
                this.palette[n] = Color.HSVToColor((float[])object);
                object[0] = (object[0] + 360.0f / (float)n2) % 360.0f;
                if (!(this.lum(this.palette[n]) < this.lum(this.palette[this.darkest]))) continue;
                this.darkest = n;
            }
            object = new StringBuilder();
            Object object2 = this.palette;
            n2 = ((int[])object2).length;
            for (n = 0; n < n2; ++n) {
                ((StringBuilder)object).append(String.format("#%08x ", (int)object2[n]));
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("color palette: ");
            ((StringBuilder)object2).append(object);
            Log.v("PlatLogoActivity", ((StringBuilder)object2).toString());
        }

        @Override
        public void setAlpha(int n) {
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
        }

        public void setOffset(float f) {
            this.offset = f;
        }

        public void setPosition(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        public void setRadius(float f) {
            this.radius = Math.max(this.dp * 48.0f, f);
        }
    }

}

