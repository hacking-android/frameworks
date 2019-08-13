/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

public class BackgroundFallback {
    private Drawable mBackgroundFallback;

    private boolean isOpaque(Drawable drawable2) {
        boolean bl = drawable2 != null && drawable2.getOpacity() == -1;
        return bl;
    }

    private boolean viewsCoverEntireWidth(View view, View view2, int n) {
        boolean bl = view.getLeft() <= 0 && view.getRight() >= view2.getLeft() && view2.getRight() >= n;
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void draw(ViewGroup var1_1, ViewGroup var2_2, Canvas var3_3, View var4_4, View var5_5, View var6_6) {
        block35 : {
            block36 : {
                if (!this.hasFallback()) {
                    return;
                }
                var7_7 = var1_1.getWidth();
                var8_8 = var1_1.getHeight();
                var9_9 = var2_2.getLeft();
                var10_10 = var2_2.getTop();
                var11_11 = var7_7;
                var12_12 = var8_8;
                var13_13 = 0;
                var14_14 = 0;
                var15_15 = var2_2.getChildCount();
                for (var16_16 = 0; var16_16 < var15_15; ++var16_16) {
                    block34 : {
                        block33 : {
                            var1_1 = var2_2.getChildAt(var16_16);
                            var17_17 = var1_1.getBackground();
                            if (var1_1 != var4_4) break block33;
                            if (var17_17 != null || !(var1_1 instanceof ViewGroup) || ((ViewGroup)var1_1).getChildCount() != 0) ** GOTO lbl-1000
                            var18_18 = var11_11;
                            var19_19 = var12_12;
                            var20_20 = var13_13;
                            var21_21 = var14_14;
                            break block34;
                        }
                        var18_18 = var11_11;
                        var19_19 = var12_12;
                        var20_20 = var13_13;
                        var21_21 = var14_14;
                        if (var1_1.getVisibility() == 0) {
                            ** if (this.isOpaque((Drawable)var17_17)) goto lbl-1000
lbl-1000: // 1 sources:
                            {
                                var18_18 = var11_11;
                                var19_19 = var12_12;
                                var20_20 = var13_13;
                                var21_21 = var14_14;
                                ** GOTO lbl39
                            }
                        }
                        break block34;
lbl-1000: // 2 sources:
                        {
                            var18_18 = Math.min(var11_11, var1_1.getLeft() + var9_9);
                            var19_19 = Math.min(var12_12, var1_1.getTop() + var10_10);
                            var20_20 = Math.max(var13_13, var1_1.getRight() + var9_9);
                            var21_21 = Math.max(var14_14, var1_1.getBottom() + var10_10);
                        }
                    }
                    var11_11 = var18_18;
                    var12_12 = var19_19;
                    var13_13 = var20_20;
                    var14_14 = var21_21;
                }
                var21_21 = 1;
                var15_15 = var14_14;
                var14_14 = var12_12;
                var12_12 = var11_11;
                for (var16_16 = 0; var16_16 < 2; ++var16_16) {
                    var1_1 = var16_16 == 0 ? var5_5 : var6_6;
                    if (var1_1 != null && var1_1.getVisibility() == 0 && var1_1.getAlpha() == 1.0f && this.isOpaque(var1_1.getBackground())) {
                        var11_11 = var12_12;
                        if (var1_1.getTop() <= 0) {
                            var11_11 = var12_12;
                            if (var1_1.getBottom() >= var8_8) {
                                var11_11 = var12_12;
                                if (var1_1.getLeft() <= 0) {
                                    var11_11 = var12_12;
                                    if (var1_1.getRight() >= var12_12) {
                                        var11_11 = 0;
                                    }
                                }
                            }
                        }
                        var12_12 = var13_13;
                        if (var1_1.getTop() <= 0) {
                            var12_12 = var13_13;
                            if (var1_1.getBottom() >= var8_8) {
                                var12_12 = var13_13;
                                if (var1_1.getLeft() <= var13_13) {
                                    var12_12 = var13_13;
                                    if (var1_1.getRight() >= var7_7) {
                                        var12_12 = var7_7;
                                    }
                                }
                            }
                        }
                        var13_13 = var14_14;
                        if (var1_1.getTop() <= 0) {
                            var13_13 = var14_14;
                            if (var1_1.getBottom() >= var14_14) {
                                var13_13 = var14_14;
                                if (var1_1.getLeft() <= 0) {
                                    var13_13 = var14_14;
                                    if (var1_1.getRight() >= var7_7) {
                                        var13_13 = 0;
                                    }
                                }
                            }
                        }
                        var14_14 = var15_15;
                        if (var1_1.getTop() <= var15_15) {
                            var14_14 = var15_15;
                            if (var1_1.getBottom() >= var8_8) {
                                var14_14 = var15_15;
                                if (var1_1.getLeft() <= 0) {
                                    var14_14 = var15_15;
                                    if (var1_1.getRight() >= var7_7) {
                                        var14_14 = var8_8;
                                    }
                                }
                            }
                        }
                        var15_15 = var1_1.getTop() <= 0 && var1_1.getBottom() >= var13_13 ? 1 : 0;
                        var19_19 = var21_21 & var15_15;
                        var21_21 = var11_11;
                        var20_20 = var13_13;
                        var13_13 = var12_12;
                        var15_15 = var14_14;
                        var11_11 = var19_19;
                    } else {
                        var11_11 = 0;
                        var20_20 = var14_14;
                        var21_21 = var12_12;
                    }
                    var12_12 = var21_21;
                    var14_14 = var20_20;
                    var21_21 = var11_11;
                }
                var11_11 = var14_14;
                if (var21_21 == 0) break block35;
                if (this.viewsCoverEntireWidth(var5_5, var6_6, var7_7)) break block36;
                var11_11 = var14_14;
                if (!this.viewsCoverEntireWidth(var6_6, var5_5, var7_7)) break block35;
            }
            var11_11 = 0;
        }
        if (var12_12 >= var13_13) return;
        if (var11_11 >= var15_15) {
            return;
        }
        if (var11_11 > 0) {
            this.mBackgroundFallback.setBounds(0, 0, var7_7, var11_11);
            this.mBackgroundFallback.draw(var3_3);
        }
        if (var12_12 > 0) {
            this.mBackgroundFallback.setBounds(0, var11_11, var12_12, var8_8);
            this.mBackgroundFallback.draw(var3_3);
        }
        if (var13_13 < var7_7) {
            this.mBackgroundFallback.setBounds(var13_13, var11_11, var7_7, var8_8);
            this.mBackgroundFallback.draw(var3_3);
        }
        if (var15_15 >= var8_8) return;
        this.mBackgroundFallback.setBounds(var12_12, var15_15, var13_13, var8_8);
        this.mBackgroundFallback.draw(var3_3);
    }

    public Drawable getDrawable() {
        return this.mBackgroundFallback;
    }

    public boolean hasFallback() {
        boolean bl = this.mBackgroundFallback != null;
        return bl;
    }

    public void setDrawable(Drawable drawable2) {
        this.mBackgroundFallback = drawable2;
    }
}

