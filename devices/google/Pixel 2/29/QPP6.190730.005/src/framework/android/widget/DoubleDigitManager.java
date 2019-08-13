/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.os.Handler;

class DoubleDigitManager {
    private Integer intermediateDigit;
    private final CallBack mCallBack;
    private final long timeoutInMillis;

    public DoubleDigitManager(long l, CallBack callBack) {
        this.timeoutInMillis = l;
        this.mCallBack = callBack;
    }

    public void reportDigit(int n) {
        Integer n2 = this.intermediateDigit;
        if (n2 == null) {
            this.intermediateDigit = n;
            new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {
                    if (DoubleDigitManager.this.intermediateDigit != null) {
                        DoubleDigitManager.this.mCallBack.singleDigitFinal(DoubleDigitManager.this.intermediateDigit);
                        DoubleDigitManager.this.intermediateDigit = null;
                    }
                }
            }, this.timeoutInMillis);
            if (!this.mCallBack.singleDigitIntermediate(n)) {
                this.intermediateDigit = null;
                this.mCallBack.singleDigitFinal(n);
            }
        } else if (this.mCallBack.twoDigitsFinal(n2, n)) {
            this.intermediateDigit = null;
        }
    }

    static interface CallBack {
        public void singleDigitFinal(int var1);

        public boolean singleDigitIntermediate(int var1);

        public boolean twoDigitsFinal(int var1, int var2);
    }

}

