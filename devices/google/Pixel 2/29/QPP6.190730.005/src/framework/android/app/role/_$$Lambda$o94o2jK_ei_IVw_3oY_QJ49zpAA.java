/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.role.-$
 *  android.app.role.-$$Lambda
 *  android.app.role.-$$Lambda$o94o2jK_ei-IVw-3oY_QJ49zpAA
 */
package android.app.role;

import android.app.role.-$;
import android.app.role.OnRoleHoldersChangedListener;
import android.os.UserHandle;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$o94o2jK_ei_IVw_3oY_QJ49zpAA
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.o94o2jK_ei-IVw-3oY_QJ49zpAA INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$o94o2jK_ei_IVw_3oY_QJ49zpAA();
    }

    private /* synthetic */ _$$Lambda$o94o2jK_ei_IVw_3oY_QJ49zpAA() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((OnRoleHoldersChangedListener)object).onRoleHoldersChanged((String)object2, (UserHandle)object3);
    }
}

