/*
 * Decompiled with CFR 0.145.
 */
package java.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class ChangeListenerMap<L extends EventListener> {
    private Map<String, L[]> map;

    ChangeListenerMap() {
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void add(String string, L l) {
        synchronized (this) {
            EventListener[] arreventListener;
            void var2_2;
            EventListener[] arreventListener2;
            if (this.map == null) {
                arreventListener2 = new HashMap();
                this.map = arreventListener2;
            }
            int n = (arreventListener = (EventListener[])this.map.get(string)) != null ? arreventListener.length : 0;
            arreventListener2 = this.newArray(n + 1);
            arreventListener2[n] = var2_2;
            if (arreventListener != null) {
                System.arraycopy(arreventListener, 0, arreventListener2, 0, n);
            }
            this.map.put(string, arreventListener2);
            return;
        }
    }

    public abstract L extract(L var1);

    public final L[] get(String object) {
        synchronized (this) {
            object = this.map != null ? (EventListener[])this.map.get(object) : null;
            return object;
        }
    }

    public final Set<Map.Entry<String, L[]>> getEntries() {
        Object object = this.map;
        object = object != null ? object.entrySet() : Collections.emptySet();
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final L[] getListeners() {
        // MONITORENTER : this
        if (this.map == null) {
            var1_1 = this.newArray(0);
            // MONITOREXIT : this
            return var1_1;
        }
        var1_2 = new ArrayList();
        var2_3 /* !! */  = (EventListener[])this.map.get(null);
        if (var2_3 /* !! */  != null) {
            var3_4 = var2_3 /* !! */ .length;
            for (var4_5 = 0; var4_5 < var3_4; ++var4_5) {
                var1_2.add(var2_3 /* !! */ [var4_5]);
            }
        }
        var5_6 = this.map.entrySet().iterator();
        block5 : do lbl-1000: // 3 sources:
        {
            if (!var5_6.hasNext()) {
                var1_2 = var1_2.toArray(this.newArray(var1_2.size()));
                // MONITOREXIT : this
                return var1_2;
            }
            var6_8 = var5_6.next();
            var2_3 /* !! */  = var6_8.getKey();
            if (var2_3 /* !! */  == null) ** GOTO lbl-1000
            var6_9 = (EventListener[])var6_8.getValue();
            var3_4 = var6_9.length;
            var4_5 = 0;
            do {
                if (var4_5 >= var3_4) continue block5;
                var1_2.add(this.newProxy((String)var2_3 /* !! */ , var6_9[var4_5]));
                ++var4_5;
            } while (true);
            break;
        } while (true);
    }

    public final L[] getListeners(String arreventListener) {
        if (arreventListener != null && (arreventListener = this.get((String)arreventListener)) != null) {
            return (EventListener[])arreventListener.clone();
        }
        return this.newArray(0);
    }

    public final boolean hasListeners(String arrL) {
        synchronized (this) {
            boolean bl;
            block7 : {
                block6 : {
                    boolean bl2;
                    block5 : {
                        Map<String, L[]> map = this.map;
                        bl2 = false;
                        if (map != null) break block5;
                        return false;
                    }
                    if ((EventListener[])this.map.get(null) != null) break block6;
                    bl = bl2;
                    if (arrL == null) break block7;
                    arrL = this.map.get(arrL);
                    bl = bl2;
                    if (arrL == null) break block7;
                }
                bl = true;
            }
            return bl;
        }
    }

    protected abstract L[] newArray(int var1);

    protected abstract L newProxy(String var1, L var2);

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void remove(String string, L object) {
        synchronized (this) {
            EventListener[] arreventListener;
            if (this.map != null && (arreventListener = (EventListener[])this.map.get(string)) != null) {
                for (int i = 0; i < arreventListener.length; ++i) {
                    void var2_2;
                    if (!var2_2.equals(arreventListener[i])) continue;
                    int n = arreventListener.length - 1;
                    if (n > 0) {
                        object = this.newArray(n);
                        System.arraycopy(arreventListener, 0, object, 0, i);
                        System.arraycopy(arreventListener, i + 1, object, i, n - i);
                        this.map.put(string, (L[])object);
                        break;
                    }
                    this.map.remove(string);
                    if (!this.map.isEmpty()) break;
                    this.map = null;
                    break;
                }
            }
            return;
        }
    }

    public final void set(String string, L[] object) {
        if (object != null) {
            if (this.map == null) {
                this.map = new HashMap<String, L[]>();
            }
            this.map.put(string, (L[])object);
        } else {
            object = this.map;
            if (object != null) {
                object.remove(string);
                if (this.map.isEmpty()) {
                    this.map = null;
                }
            }
        }
    }
}

