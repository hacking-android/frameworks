/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import sun.nio.fs.Util;

class UnixMountEntry {
    private long dev;
    private byte[] dir;
    private byte[] fstype;
    private volatile String fstypeAsString;
    private byte[] name;
    private volatile String optionsAsString;
    private byte[] opts;

    UnixMountEntry() {
    }

    long dev() {
        return this.dev;
    }

    byte[] dir() {
        return this.dir;
    }

    String fstype() {
        if (this.fstypeAsString == null) {
            this.fstypeAsString = Util.toString(this.fstype);
        }
        return this.fstypeAsString;
    }

    boolean hasOption(String string) {
        if (this.optionsAsString == null) {
            this.optionsAsString = Util.toString(this.opts);
        }
        String[] arrstring = Util.split(this.optionsAsString, ',');
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!arrstring[i].equals(string)) continue;
            return true;
        }
        return false;
    }

    boolean isIgnored() {
        return this.hasOption("ignore");
    }

    boolean isReadOnly() {
        return this.hasOption("ro");
    }

    String name() {
        return Util.toString(this.name);
    }
}

