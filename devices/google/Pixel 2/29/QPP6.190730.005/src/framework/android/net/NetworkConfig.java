/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import java.util.Locale;

public class NetworkConfig {
    public boolean dependencyMet;
    public String name;
    public int priority;
    public int radio;
    public int restoreTime;
    public int type;

    public NetworkConfig(String arrstring) {
        arrstring = arrstring.split(",");
        this.name = arrstring[0].trim().toLowerCase(Locale.ROOT);
        this.type = Integer.parseInt(arrstring[1]);
        this.radio = Integer.parseInt(arrstring[2]);
        this.priority = Integer.parseInt(arrstring[3]);
        this.restoreTime = Integer.parseInt(arrstring[4]);
        this.dependencyMet = Boolean.parseBoolean(arrstring[5]);
    }

    public boolean isDefault() {
        boolean bl = this.type == this.radio;
        return bl;
    }
}

