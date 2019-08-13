/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

@Deprecated
public abstract class FragmentContainer {
    public Fragment instantiate(Context context, String string2, Bundle bundle) {
        return Fragment.instantiate(context, string2, bundle);
    }

    public abstract <T extends View> T onFindViewById(int var1);

    public abstract boolean onHasView();
}

