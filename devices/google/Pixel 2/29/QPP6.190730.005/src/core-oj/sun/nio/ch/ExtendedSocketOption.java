/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.net.SocketOption;

class ExtendedSocketOption {
    static final SocketOption<Boolean> SO_OOBINLINE = new SocketOption<Boolean>(){

        @Override
        public String name() {
            return "SO_OOBINLINE";
        }

        public String toString() {
            return this.name();
        }

        @Override
        public Class<Boolean> type() {
            return Boolean.class;
        }
    };

    private ExtendedSocketOption() {
    }

}

