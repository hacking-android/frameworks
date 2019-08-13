/*
 * Decompiled with CFR 0.145.
 */
package jdk.net;

import java.net.SocketOption;
import jdk.net.SocketFlow;

public final class ExtendedSocketOptions {
    public static final SocketOption<SocketFlow> SO_FLOW_SLA = new ExtSocketOption<SocketFlow>("SO_FLOW_SLA", SocketFlow.class);

    private ExtendedSocketOptions() {
    }

    private static class ExtSocketOption<T>
    implements SocketOption<T> {
        private final String name;
        private final Class<T> type;

        ExtSocketOption(String string, Class<T> class_) {
            this.name = string;
            this.type = class_;
        }

        @Override
        public String name() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        @Override
        public Class<T> type() {
            return this.type;
        }
    }

}

