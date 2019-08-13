/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.content.Context;
import android.webkit.Plugin;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class PluginList {
    private ArrayList<Plugin> mPlugins = new ArrayList();

    @Deprecated
    public void addPlugin(Plugin plugin) {
        synchronized (this) {
            if (!this.mPlugins.contains(plugin)) {
                this.mPlugins.add(plugin);
            }
            return;
        }
    }

    @Deprecated
    public void clear() {
        synchronized (this) {
            this.mPlugins.clear();
            return;
        }
    }

    @Deprecated
    public List getList() {
        synchronized (this) {
            ArrayList<Plugin> arrayList = this.mPlugins;
            return arrayList;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Deprecated
    public void pluginClicked(Context context, int n) {
        // MONITORENTER : this
        ArrayList<Plugin> arrayList = this.mPlugins;
        try {
            void var2_4;
            arrayList.get((int)var2_4).dispatchClickEvent(context);
            return;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
        // MONITOREXIT : this
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void removePlugin(Plugin plugin) {
        synchronized (this) {
            int n = this.mPlugins.indexOf(plugin);
            if (n != -1) {
                this.mPlugins.remove(n);
            }
            return;
        }
    }
}

