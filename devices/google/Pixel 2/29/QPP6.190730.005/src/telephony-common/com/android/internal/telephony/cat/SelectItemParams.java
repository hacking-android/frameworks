/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.graphics.Bitmap
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import com.android.internal.telephony.cat.CommandDetails;
import com.android.internal.telephony.cat.CommandParams;
import com.android.internal.telephony.cat.Item;
import com.android.internal.telephony.cat.Menu;
import java.util.List;

class SelectItemParams
extends CommandParams {
    boolean mLoadTitleIcon = false;
    Menu mMenu = null;

    @UnsupportedAppUsage
    SelectItemParams(CommandDetails commandDetails, Menu menu, boolean bl) {
        super(commandDetails);
        this.mMenu = menu;
        this.mLoadTitleIcon = bl;
    }

    @Override
    boolean setIcon(Bitmap bitmap) {
        Menu object2;
        if (bitmap != null && (object2 = this.mMenu) != null) {
            if (this.mLoadTitleIcon && object2.titleIcon == null) {
                this.mMenu.titleIcon = bitmap;
            } else {
                for (Item item : this.mMenu.items) {
                    if (item.icon != null) continue;
                    item.icon = bitmap;
                    break;
                }
            }
            return true;
        }
        return false;
    }
}

