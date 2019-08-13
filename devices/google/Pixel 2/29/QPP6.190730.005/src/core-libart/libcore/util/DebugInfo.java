/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

import java.util.ArrayList;
import java.util.List;

public class DebugInfo {
    private final List<DebugEntry> entries = new ArrayList<DebugEntry>();

    public DebugInfo addStringEntry(String string, int n) {
        this.addStringEntry(string, Integer.toString(n));
        return this;
    }

    public DebugInfo addStringEntry(String string, String string2) {
        this.entries.add(new DebugEntry(string, string2));
        return this;
    }

    public List<DebugEntry> getDebugEntries() {
        return this.entries;
    }

    public DebugEntry getDebugEntry(String string) {
        for (DebugEntry debugEntry : this.getDebugEntries()) {
            if (!string.equals(debugEntry.getKey())) continue;
            return debugEntry;
        }
        return null;
    }

    public static class DebugEntry {
        private final String key;
        private final String stringValue;

        public DebugEntry(String string, String string2) {
            this.key = string;
            this.stringValue = string2;
        }

        public String getKey() {
            return this.key;
        }

        public String getStringValue() {
            return this.stringValue;
        }
    }

}

