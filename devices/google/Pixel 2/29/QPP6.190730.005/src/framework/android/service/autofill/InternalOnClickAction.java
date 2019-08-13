/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcelable;
import android.service.autofill.OnClickAction;
import android.view.ViewGroup;

public abstract class InternalOnClickAction
implements OnClickAction,
Parcelable {
    public abstract void onClick(ViewGroup var1);
}

