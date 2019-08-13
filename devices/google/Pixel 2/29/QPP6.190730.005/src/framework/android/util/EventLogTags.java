/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import java.io.BufferedReader;
import java.io.IOException;

@Deprecated
public class EventLogTags {
    public EventLogTags() throws IOException {
    }

    public EventLogTags(BufferedReader bufferedReader) throws IOException {
    }

    public Description get(int n) {
        return null;
    }

    public Description get(String string2) {
        return null;
    }

    public static class Description {
        public final String mName;
        public final int mTag;

        Description(int n, String string2) {
            this.mTag = n;
            this.mName = string2;
        }
    }

}

