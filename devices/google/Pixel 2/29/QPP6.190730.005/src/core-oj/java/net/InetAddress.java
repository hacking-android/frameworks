/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.StructUtsname
 *  libcore.io.Libcore
 *  libcore.io.Os
 *  libcore.net.InetAddressUtils
 */
package java.net;

import android.system.StructUtsname;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.Inet6AddressImpl;
import java.net.InetAddressImpl;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import libcore.io.Libcore;
import libcore.io.Os;
import libcore.net.InetAddressUtils;
import sun.net.spi.nameservice.NameService;
import sun.net.util.IPAddressUtil;

public class InetAddress
implements Serializable {
    private static final ClassLoader BOOT_CLASSLOADER;
    static final int NETID_UNSET = 0;
    static final InetAddressImpl impl;
    private static final NameService nameService;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = 3286316764910316507L;
    private transient String canonicalHostName = null;
    transient InetAddressHolder holder = new InetAddressHolder();

    static {
        impl = new Inet6AddressImpl();
        nameService = new NameService(){

            @Override
            public String getHostByAddr(byte[] arrby) throws UnknownHostException {
                return impl.getHostByAddr(arrby);
            }

            @Override
            public InetAddress[] lookupAllHostAddr(String string, int n) throws UnknownHostException {
                return impl.lookupAllHostAddr(string, n);
            }
        };
        BOOT_CLASSLOADER = Object.class.getClassLoader();
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("hostName", String.class), new ObjectStreamField("address", Integer.TYPE), new ObjectStreamField("family", Integer.TYPE)};
    }

    InetAddress() {
    }

    static InetAddress anyLocalAddress() {
        return impl.anyLocalAddress();
    }

    public static void clearDnsCache() {
        impl.clearAddressCache();
    }

    public static InetAddress[] getAllByName(String string) throws UnknownHostException {
        return (InetAddress[])impl.lookupAllHostAddr(string, 0).clone();
    }

    public static InetAddress[] getAllByNameOnNet(String string, int n) throws UnknownHostException {
        return (InetAddress[])impl.lookupAllHostAddr(string, n).clone();
    }

    public static InetAddress getByAddress(String string, byte[] arrby) throws UnknownHostException {
        return InetAddress.getByAddress(string, arrby, -1);
    }

    private static InetAddress getByAddress(String arrby, byte[] arrby2, int n) throws UnknownHostException {
        Object object = arrby;
        if (arrby != null) {
            object = arrby;
            if (arrby.length() > 0) {
                object = arrby;
                if (arrby.charAt(0) == '[') {
                    object = arrby;
                    if (arrby.charAt(arrby.length() - 1) == ']') {
                        object = arrby.substring(1, arrby.length() - 1);
                    }
                }
            }
        }
        if (arrby2 != null) {
            if (arrby2.length == 4) {
                return new Inet4Address((String)object, arrby2);
            }
            if (arrby2.length == 16) {
                arrby = IPAddressUtil.convertFromIPv4MappedAddress(arrby2);
                if (arrby != null) {
                    return new Inet4Address((String)object, arrby);
                }
                return new Inet6Address((String)object, arrby2, n);
            }
        }
        throw new UnknownHostException("addr is of illegal length");
    }

    public static InetAddress getByAddress(byte[] arrby) throws UnknownHostException {
        return InetAddress.getByAddress(null, arrby);
    }

    public static InetAddress getByName(String string) throws UnknownHostException {
        return impl.lookupAllHostAddr(string, 0)[0];
    }

    public static InetAddress getByNameOnNet(String string, int n) throws UnknownHostException {
        return impl.lookupAllHostAddr(string, n)[0];
    }

    private static String getHostFromNameService(InetAddress object) {
        String string;
        boolean bl;
        block6 : {
            int n;
            InetAddress[] arrinetAddress;
            boolean bl2;
            try {
                string = nameService.getHostByAddr(((InetAddress)object).getAddress());
                arrinetAddress = nameService.lookupAllHostAddr(string, 0);
                bl = false;
                bl2 = false;
                if (arrinetAddress == null) break block6;
                n = 0;
            }
            catch (UnknownHostException unknownHostException) {
                object = ((InetAddress)object).getHostAddress();
            }
            do {
                bl = bl2;
                if (bl2) break;
                bl = bl2;
                if (n >= arrinetAddress.length) break;
                bl2 = ((InetAddress)object).equals(arrinetAddress[n]);
                ++n;
                continue;
                break;
            } while (true);
        }
        if (!bl) {
            string = ((InetAddress)object).getHostAddress();
            return string;
        }
        object = string;
        return object;
    }

    public static InetAddress getLocalHost() throws UnknownHostException {
        String string = Libcore.os.uname().nodename;
        return impl.lookupAllHostAddr(string, 0)[0];
    }

    public static InetAddress getLoopbackAddress() {
        return impl.loopbackAddresses()[0];
    }

    @Deprecated
    public static boolean isNumeric(String string) {
        boolean bl = InetAddressUtils.parseNumericAddressNoThrowStripOptionalBrackets((String)string) != null;
        return bl;
    }

    @Deprecated
    public static InetAddress parseNumericAddress(String string) {
        if (string != null && !string.isEmpty()) {
            Serializable serializable = InetAddressUtils.parseNumericAddressNoThrowStripOptionalBrackets((String)string);
            if (serializable != null) {
                return serializable;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Not a numeric address: ");
            ((StringBuilder)serializable).append(string);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
        return Inet6Address.LOOPBACK;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        if (this.getClass().getClassLoader() == BOOT_CLASSLOADER) {
            object = ((ObjectInputStream)object).readFields();
            this.holder = new InetAddressHolder((String)((ObjectInputStream.GetField)object).get("hostName", null), ((ObjectInputStream.GetField)object).get("address", 0), ((ObjectInputStream.GetField)object).get("family", 0));
            return;
        }
        throw new SecurityException("invalid address type");
    }

    private void readObjectNoData(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        if (this.getClass().getClassLoader() == BOOT_CLASSLOADER) {
            return;
        }
        throw new SecurityException("invalid address type");
    }

    private Object readResolve() throws ObjectStreamException {
        return new Inet4Address(this.holder().getHostName(), this.holder().getAddress());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.getClass().getClassLoader() == BOOT_CLASSLOADER) {
            ObjectOutputStream.PutField putField = objectOutputStream.putFields();
            putField.put("hostName", this.holder().hostName);
            putField.put("address", this.holder().address);
            putField.put("family", this.holder().family);
            objectOutputStream.writeFields();
            objectOutputStream.flush();
            return;
        }
        throw new SecurityException("invalid address type");
    }

    public boolean equals(Object object) {
        return false;
    }

    public byte[] getAddress() {
        return null;
    }

    public String getCanonicalHostName() {
        if (this.canonicalHostName == null) {
            this.canonicalHostName = InetAddress.getHostFromNameService(this);
        }
        return this.canonicalHostName;
    }

    public String getHostAddress() {
        return null;
    }

    public String getHostName() {
        if (this.holder().getHostName() == null) {
            this.holder().hostName = InetAddress.getHostFromNameService(this);
        }
        return this.holder().getHostName();
    }

    public int hashCode() {
        return -1;
    }

    InetAddressHolder holder() {
        return this.holder;
    }

    public boolean isAnyLocalAddress() {
        return false;
    }

    public boolean isLinkLocalAddress() {
        return false;
    }

    public boolean isLoopbackAddress() {
        return false;
    }

    public boolean isMCGlobal() {
        return false;
    }

    public boolean isMCLinkLocal() {
        return false;
    }

    public boolean isMCNodeLocal() {
        return false;
    }

    public boolean isMCOrgLocal() {
        return false;
    }

    public boolean isMCSiteLocal() {
        return false;
    }

    public boolean isMulticastAddress() {
        return false;
    }

    public boolean isReachable(int n) throws IOException {
        return this.isReachable(null, 0, n);
    }

    public boolean isReachable(NetworkInterface networkInterface, int n, int n2) throws IOException {
        if (n >= 0) {
            if (n2 >= 0) {
                return impl.isReachable(this, n2, networkInterface, n);
            }
            throw new IllegalArgumentException("timeout can't be negative");
        }
        throw new IllegalArgumentException("ttl can't be negative");
    }

    public boolean isReachableByICMP(int n) throws IOException {
        return ((Inet6AddressImpl)impl).icmpEcho(this, n, null, 0);
    }

    public boolean isSiteLocalAddress() {
        return false;
    }

    public String toString() {
        String string = this.holder().getHostName();
        StringBuilder stringBuilder = new StringBuilder();
        if (string == null) {
            string = "";
        }
        stringBuilder.append(string);
        stringBuilder.append("/");
        stringBuilder.append(this.getHostAddress());
        return stringBuilder.toString();
    }

    static class InetAddressHolder {
        int address;
        int family;
        String hostName;
        String originalHostName;

        InetAddressHolder() {
        }

        InetAddressHolder(String string, int n, int n2) {
            this.originalHostName = string;
            this.hostName = string;
            this.address = n;
            this.family = n2;
        }

        int getAddress() {
            return this.address;
        }

        int getFamily() {
            return this.family;
        }

        String getHostName() {
            return this.hostName;
        }

        String getOriginalHostName() {
            return this.originalHostName;
        }

        void init(String string, int n) {
            this.originalHostName = string;
            this.hostName = string;
            if (n != -1) {
                this.family = n;
            }
        }
    }

}

