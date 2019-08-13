/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Fragment;
import java.util.List;

@Deprecated
public class FragmentManagerNonConfig {
    private final List<FragmentManagerNonConfig> mChildNonConfigs;
    private final List<Fragment> mFragments;

    FragmentManagerNonConfig(List<Fragment> list, List<FragmentManagerNonConfig> list2) {
        this.mFragments = list;
        this.mChildNonConfigs = list2;
    }

    List<FragmentManagerNonConfig> getChildNonConfigs() {
        return this.mChildNonConfigs;
    }

    List<Fragment> getFragments() {
        return this.mFragments;
    }
}

