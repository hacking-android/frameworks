/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.servertransaction.ClientTransactionItem;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class ActivityLifecycleItem
extends ClientTransactionItem {
    public static final int ON_CREATE = 1;
    public static final int ON_DESTROY = 6;
    public static final int ON_PAUSE = 4;
    public static final int ON_RESTART = 7;
    public static final int ON_RESUME = 3;
    public static final int ON_START = 2;
    public static final int ON_STOP = 5;
    public static final int PRE_ON_CREATE = 0;
    public static final int UNDEFINED = -1;

    public abstract int getTargetState();

    @Override
    public void recycle() {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LifecycleState {
    }

}

