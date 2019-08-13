/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.net.NetworkInterface;
import java.net.ProtocolFamily;
import java.net.SocketOption;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.util.HashMap;
import java.util.Map;
import sun.nio.ch.ExtendedSocketOption;
import sun.nio.ch.Net;
import sun.nio.ch.OptionKey;

class SocketOptionRegistry {
    private SocketOptionRegistry() {
    }

    public static OptionKey findOption(SocketOption<?> object, ProtocolFamily protocolFamily) {
        object = new RegistryKey((SocketOption<?>)object, protocolFamily);
        return LazyInitialization.options.get(object);
    }

    private static class LazyInitialization {
        static final Map<RegistryKey, OptionKey> options = LazyInitialization.options();

        private LazyInitialization() {
        }

        private static Map<RegistryKey, OptionKey> options() {
            HashMap<RegistryKey, OptionKey> hashMap = new HashMap<RegistryKey, OptionKey>();
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_BROADCAST, Net.UNSPEC), new OptionKey(1, 6));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_KEEPALIVE, Net.UNSPEC), new OptionKey(1, 9));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_LINGER, Net.UNSPEC), new OptionKey(1, 13));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_SNDBUF, Net.UNSPEC), new OptionKey(1, 7));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_RCVBUF, Net.UNSPEC), new OptionKey(1, 8));
            hashMap.put(new RegistryKey(StandardSocketOptions.SO_REUSEADDR, Net.UNSPEC), new OptionKey(1, 2));
            hashMap.put(new RegistryKey(StandardSocketOptions.TCP_NODELAY, Net.UNSPEC), new OptionKey(6, 1));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_TOS, StandardProtocolFamily.INET), new OptionKey(0, 1));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_IF, StandardProtocolFamily.INET), new OptionKey(0, 32));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_TTL, StandardProtocolFamily.INET), new OptionKey(0, 33));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_LOOP, StandardProtocolFamily.INET), new OptionKey(0, 34));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_TOS, StandardProtocolFamily.INET6), new OptionKey(41, 67));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_IF, StandardProtocolFamily.INET6), new OptionKey(41, 17));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_TTL, StandardProtocolFamily.INET6), new OptionKey(41, 18));
            hashMap.put(new RegistryKey(StandardSocketOptions.IP_MULTICAST_LOOP, StandardProtocolFamily.INET6), new OptionKey(41, 19));
            hashMap.put(new RegistryKey(ExtendedSocketOption.SO_OOBINLINE, Net.UNSPEC), new OptionKey(1, 10));
            return hashMap;
        }
    }

    private static class RegistryKey {
        private final ProtocolFamily family;
        private final SocketOption<?> name;

        RegistryKey(SocketOption<?> socketOption, ProtocolFamily protocolFamily) {
            this.name = socketOption;
            this.family = protocolFamily;
        }

        public boolean equals(Object object) {
            if (object == null) {
                return false;
            }
            if (!(object instanceof RegistryKey)) {
                return false;
            }
            object = (RegistryKey)object;
            if (this.name != ((RegistryKey)object).name) {
                return false;
            }
            return this.family == ((RegistryKey)object).family;
        }

        public int hashCode() {
            return this.name.hashCode() + this.family.hashCode();
        }
    }

}

