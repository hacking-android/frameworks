/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 *  libcore.io.Libcore
 *  libcore.io.Os
 */
package java.net;

import android.system.OsConstants;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import libcore.io.Libcore;
import libcore.io.Os;
import sun.misc.Unsafe;

public final class Inet6Address
extends InetAddress {
    public static final InetAddress ANY = new Inet6Address("::", new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 0);
    private static final long FIELDS_OFFSET;
    static final int INADDRSZ = 16;
    private static final int INT16SZ = 2;
    public static final InetAddress LOOPBACK;
    private static final Unsafe UNSAFE;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = 6880410070516793377L;
    private final transient Inet6AddressHolder holder6;

    static {
        LOOPBACK = new Inet6Address("ip6-localhost", new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, 0);
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("ipaddress", byte[].class), new ObjectStreamField("scope_id", Integer.TYPE), new ObjectStreamField("scope_id_set", Boolean.TYPE), new ObjectStreamField("scope_ifname_set", Boolean.TYPE), new ObjectStreamField("ifname", String.class)};
        try {
            Unsafe unsafe = Unsafe.getUnsafe();
            FIELDS_OFFSET = unsafe.objectFieldOffset(Inet6Address.class.getDeclaredField("holder6"));
            UNSAFE = unsafe;
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    Inet6Address() {
        this.holder.init(null, OsConstants.AF_INET6);
        this.holder6 = new Inet6AddressHolder();
    }

    Inet6Address(String string, byte[] arrby) {
        this.holder6 = new Inet6AddressHolder();
        try {
            this.initif(string, arrby, null);
        }
        catch (UnknownHostException unknownHostException) {}
    }

    Inet6Address(String string, byte[] arrby, int n) {
        this.holder.init(string, OsConstants.AF_INET6);
        this.holder6 = new Inet6AddressHolder();
        this.holder6.init(arrby, n);
    }

    Inet6Address(String string, byte[] arrby, String string2) throws UnknownHostException {
        this.holder6 = new Inet6AddressHolder();
        this.initstr(string, arrby, string2);
    }

    Inet6Address(String string, byte[] arrby, NetworkInterface networkInterface) throws UnknownHostException {
        this.holder6 = new Inet6AddressHolder();
        this.initif(string, arrby, networkInterface);
    }

    private int deriveNumericScope(String string) throws UnknownHostException {
        Object object;
        try {
            object = NetworkInterface.getNetworkInterfaces();
        }
        catch (SocketException socketException) {
            throw new UnknownHostException("could not enumerate local network interfaces");
        }
        while (object.hasMoreElements()) {
            NetworkInterface networkInterface = object.nextElement();
            if (!networkInterface.getName().equals(string)) continue;
            return Inet6Address.deriveNumericScope(this.holder6.ipaddress, networkInterface);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No matching address found for interface : ");
        ((StringBuilder)object).append(string);
        throw new UnknownHostException(((StringBuilder)object).toString());
    }

    private static int deriveNumericScope(byte[] arrby, NetworkInterface object) throws UnknownHostException {
        object = ((NetworkInterface)object).getInetAddresses();
        while (object.hasMoreElements()) {
            InetAddress inetAddress = (InetAddress)object.nextElement();
            if (!(inetAddress instanceof Inet6Address) || !Inet6Address.isDifferentLocalAddressType(arrby, ((Inet6Address)(inetAddress = (Inet6Address)inetAddress)).getAddress())) continue;
            return ((Inet6Address)inetAddress).getScopeId();
        }
        throw new UnknownHostException("no scope_id found");
    }

    public static Inet6Address getByAddress(String string, byte[] arrby, int n) throws UnknownHostException {
        String string2 = string;
        if (string != null) {
            string2 = string;
            if (string.length() > 0) {
                string2 = string;
                if (string.charAt(0) == '[') {
                    string2 = string;
                    if (string.charAt(string.length() - 1) == ']') {
                        string2 = string.substring(1, string.length() - 1);
                    }
                }
            }
        }
        if (arrby != null && arrby.length == 16) {
            return new Inet6Address(string2, arrby, n);
        }
        throw new UnknownHostException("addr is of illegal length");
    }

    public static Inet6Address getByAddress(String string, byte[] arrby, NetworkInterface networkInterface) throws UnknownHostException {
        String string2 = string;
        if (string != null) {
            string2 = string;
            if (string.length() > 0) {
                string2 = string;
                if (string.charAt(0) == '[') {
                    string2 = string;
                    if (string.charAt(string.length() - 1) == ']') {
                        string2 = string.substring(1, string.length() - 1);
                    }
                }
            }
        }
        if (arrby != null && arrby.length == 16) {
            return new Inet6Address(string2, arrby, networkInterface);
        }
        throw new UnknownHostException("addr is of illegal length");
    }

    private void initif(String string, byte[] arrby, NetworkInterface networkInterface) throws UnknownHostException {
        int n = -1;
        this.holder6.init(arrby, networkInterface);
        if (arrby.length == 16) {
            n = OsConstants.AF_INET6;
        }
        this.holder.init(string, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initstr(String object, byte[] object2, String string) throws UnknownHostException {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName(string);
            if (networkInterface != null) {
                this.initif((String)object, (byte[])object2, networkInterface);
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("no such interface ");
            ((StringBuilder)object2).append(string);
            object = new UnknownHostException(((StringBuilder)object2).toString());
            throw object;
        }
        catch (SocketException socketException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SocketException thrown");
            stringBuilder.append(string);
            throw new UnknownHostException(stringBuilder.toString());
        }
    }

    private static boolean isDifferentLocalAddressType(byte[] arrby, byte[] arrby2) {
        if (Inet6Address.isLinkLocalAddress(arrby) && !Inet6Address.isLinkLocalAddress(arrby2)) {
            return false;
        }
        return !Inet6Address.isSiteLocalAddress(arrby) || Inet6Address.isSiteLocalAddress(arrby2);
    }

    static boolean isLinkLocalAddress(byte[] arrby) {
        boolean bl;
        boolean bl2 = bl = false;
        if ((arrby[0] & 255) == 254) {
            bl2 = bl;
            if ((arrby[1] & 192) == 128) {
                bl2 = true;
            }
        }
        return bl2;
    }

    static boolean isSiteLocalAddress(byte[] arrby) {
        boolean bl;
        boolean bl2 = bl = false;
        if ((arrby[0] & 255) == 254) {
            bl2 = bl;
            if ((arrby[1] & 192) == 192) {
                bl2 = true;
            }
        }
        return bl2;
    }

    static String numericToTextFormat(byte[] arrby) {
        StringBuilder stringBuilder = new StringBuilder(39);
        for (int i = 0; i < 8; ++i) {
            stringBuilder.append(Integer.toHexString(arrby[i << 1] << 8 & 65280 | arrby[(i << 1) + 1] & 255));
            if (i >= 7) continue;
            stringBuilder.append(":");
        }
        return stringBuilder.toString();
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        Object var2_3 = null;
        Object object2 = null;
        if (this.getClass().getClassLoader() == Class.class.getClassLoader()) {
            boolean bl;
            int n;
            byte[] arrby;
            boolean bl2;
            block10 : {
                object = ((ObjectInputStream)object).readFields();
                arrby = (byte[])((ObjectInputStream.GetField)object).get("ipaddress", null);
                int n2 = ((ObjectInputStream.GetField)object).get("scope_id", -1);
                boolean bl3 = ((ObjectInputStream.GetField)object).get("scope_id_set", false);
                boolean bl4 = ((ObjectInputStream.GetField)object).get("scope_ifname_set", false);
                String string = (String)((ObjectInputStream.GetField)object).get("ifname", null);
                object = var2_3;
                n = n2;
                bl2 = bl4;
                bl = bl3;
                if (string != null) {
                    object = var2_3;
                    n = n2;
                    bl2 = bl4;
                    bl = bl3;
                    if (!"".equals(string)) {
                        object = object2;
                        bl2 = bl4;
                        object2 = NetworkInterface.getByName(string);
                        if (object2 == null) {
                            bl2 = false;
                            n = 0;
                            bl = false;
                            object = object2;
                            break block10;
                        }
                        bl = true;
                        bl2 = true;
                        object = object2;
                        try {
                            n = Inet6Address.deriveNumericScope(arrby, (NetworkInterface)object2);
                            object = object2;
                            bl2 = bl;
                            bl = bl3;
                        }
                        catch (UnknownHostException unknownHostException) {
                            object = object2;
                            n = n2;
                            bl2 = bl;
                            bl = bl3;
                        }
                        catch (SocketException socketException) {
                            bl = bl3;
                            n = n2;
                        }
                    }
                }
            }
            if (((byte[])(object2 = (byte[])arrby.clone())).length == 16) {
                if (this.holder().getFamily() == OsConstants.AF_INET6) {
                    object = new Inet6AddressHolder((byte[])object2, n, bl, (NetworkInterface)object, bl2);
                    UNSAFE.putObject(this, FIELDS_OFFSET, object);
                    return;
                }
                throw new InvalidObjectException("invalid address family type");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("invalid address length: ");
            ((StringBuilder)object).append(((byte[])object2).length);
            throw new InvalidObjectException(((StringBuilder)object).toString());
        }
        throw new SecurityException("invalid address type");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        synchronized (this) {
            String string = null;
            if (this.holder6.scope_ifname != null) {
                string = this.holder6.scope_ifname.getName();
                this.holder6.scope_ifname_set = true;
            }
            ObjectOutputStream.PutField putField = objectOutputStream.putFields();
            putField.put("ipaddress", this.holder6.ipaddress);
            putField.put("scope_id", this.holder6.scope_id);
            putField.put("scope_id_set", this.holder6.scope_id_set);
            putField.put("scope_ifname_set", this.holder6.scope_ifname_set);
            putField.put("ifname", string);
            objectOutputStream.writeFields();
            return;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Inet6Address) {
            object = (Inet6Address)object;
            return this.holder6.equals(((Inet6Address)object).holder6);
        }
        return false;
    }

    @Override
    public byte[] getAddress() {
        return (byte[])this.holder6.ipaddress.clone();
    }

    @Override
    public String getHostAddress() {
        return Libcore.os.getnameinfo((InetAddress)this, OsConstants.NI_NUMERICHOST);
    }

    public int getScopeId() {
        return this.holder6.scope_id;
    }

    public NetworkInterface getScopedInterface() {
        return this.holder6.scope_ifname;
    }

    @Override
    public int hashCode() {
        return this.holder6.hashCode();
    }

    @Override
    public boolean isAnyLocalAddress() {
        return this.holder6.isAnyLocalAddress();
    }

    public boolean isIPv4CompatibleAddress() {
        return this.holder6.isIPv4CompatibleAddress();
    }

    @Override
    public boolean isLinkLocalAddress() {
        return this.holder6.isLinkLocalAddress();
    }

    @Override
    public boolean isLoopbackAddress() {
        return this.holder6.isLoopbackAddress();
    }

    @Override
    public boolean isMCGlobal() {
        return this.holder6.isMCGlobal();
    }

    @Override
    public boolean isMCLinkLocal() {
        return this.holder6.isMCLinkLocal();
    }

    @Override
    public boolean isMCNodeLocal() {
        return this.holder6.isMCNodeLocal();
    }

    @Override
    public boolean isMCOrgLocal() {
        return this.holder6.isMCOrgLocal();
    }

    @Override
    public boolean isMCSiteLocal() {
        return this.holder6.isMCSiteLocal();
    }

    @Override
    public boolean isMulticastAddress() {
        return this.holder6.isMulticastAddress();
    }

    @Override
    public boolean isSiteLocalAddress() {
        return this.holder6.isSiteLocalAddress();
    }

    private class Inet6AddressHolder {
        byte[] ipaddress;
        int scope_id;
        boolean scope_id_set;
        NetworkInterface scope_ifname;
        boolean scope_ifname_set;

        private Inet6AddressHolder() {
            this.ipaddress = new byte[16];
        }

        private Inet6AddressHolder(byte[] arrby, int n, boolean bl, NetworkInterface networkInterface, boolean bl2) {
            this.ipaddress = arrby;
            this.scope_id = n;
            this.scope_id_set = bl;
            this.scope_ifname_set = bl2;
            this.scope_ifname = networkInterface;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Inet6AddressHolder)) {
                return false;
            }
            object = (Inet6AddressHolder)object;
            return Arrays.equals(this.ipaddress, ((Inet6AddressHolder)object).ipaddress);
        }

        public int hashCode() {
            if (this.ipaddress != null) {
                int n = 0;
                int n2 = 0;
                while (n2 < 16) {
                    int n3 = 0;
                    for (int i = 0; i < 4 && n2 < 16; ++i, ++n2) {
                        n3 = (n3 << 8) + this.ipaddress[n2];
                    }
                    n += n3;
                }
                return n;
            }
            return 0;
        }

        void init(byte[] arrby, int n) {
            this.setAddr(arrby);
            if (n > 0) {
                this.scope_id = n;
                this.scope_id_set = true;
            }
        }

        void init(byte[] arrby, NetworkInterface networkInterface) throws UnknownHostException {
            this.setAddr(arrby);
            if (networkInterface != null) {
                this.scope_id = Inet6Address.deriveNumericScope(this.ipaddress, networkInterface);
                this.scope_id_set = true;
                this.scope_ifname = networkInterface;
                this.scope_ifname_set = true;
            }
        }

        boolean isAnyLocalAddress() {
            byte by = 0;
            for (int i = 0; i < 16; ++i) {
                by = (byte)(this.ipaddress[i] | by);
            }
            boolean bl = by == 0;
            return bl;
        }

        boolean isIPv4CompatibleAddress() {
            byte[] arrby = this.ipaddress;
            return arrby[0] == 0 && arrby[1] == 0 && arrby[2] == 0 && arrby[3] == 0 && arrby[4] == 0 && arrby[5] == 0 && arrby[6] == 0 && arrby[7] == 0 && arrby[8] == 0 && arrby[9] == 0 && arrby[10] == 0 && arrby[11] == 0;
        }

        boolean isLinkLocalAddress() {
            boolean bl;
            byte[] arrby = this.ipaddress;
            boolean bl2 = bl = false;
            if ((arrby[0] & 255) == 254) {
                bl2 = bl;
                if ((arrby[1] & 192) == 128) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        boolean isLoopbackAddress() {
            byte by = 0;
            for (int i = 0; i < 15; ++i) {
                by = (byte)(this.ipaddress[i] | by);
            }
            boolean bl = true;
            if (by != 0 || this.ipaddress[15] != 1) {
                bl = false;
            }
            return bl;
        }

        boolean isMCGlobal() {
            boolean bl;
            byte[] arrby = this.ipaddress;
            boolean bl2 = bl = false;
            if ((arrby[0] & 255) == 255) {
                bl2 = bl;
                if ((arrby[1] & 15) == 14) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        boolean isMCLinkLocal() {
            boolean bl;
            byte[] arrby = this.ipaddress;
            boolean bl2 = bl = false;
            if ((arrby[0] & 255) == 255) {
                bl2 = bl;
                if ((arrby[1] & 15) == 2) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        boolean isMCNodeLocal() {
            boolean bl;
            byte[] arrby = this.ipaddress;
            boolean bl2 = bl = false;
            if ((arrby[0] & 255) == 255) {
                bl2 = bl;
                if ((arrby[1] & 15) == 1) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        boolean isMCOrgLocal() {
            boolean bl;
            byte[] arrby = this.ipaddress;
            boolean bl2 = bl = false;
            if ((arrby[0] & 255) == 255) {
                bl2 = bl;
                if ((arrby[1] & 15) == 8) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        boolean isMCSiteLocal() {
            boolean bl;
            byte[] arrby = this.ipaddress;
            boolean bl2 = bl = false;
            if ((arrby[0] & 255) == 255) {
                bl2 = bl;
                if ((arrby[1] & 15) == 5) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        boolean isMulticastAddress() {
            byte[] arrby = this.ipaddress;
            boolean bl = false;
            if ((arrby[0] & 255) == 255) {
                bl = true;
            }
            return bl;
        }

        boolean isSiteLocalAddress() {
            boolean bl;
            byte[] arrby = this.ipaddress;
            boolean bl2 = bl = false;
            if ((arrby[0] & 255) == 254) {
                bl2 = bl;
                if ((arrby[1] & 192) == 192) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        void setAddr(byte[] arrby) {
            if (arrby.length == 16) {
                System.arraycopy(arrby, 0, this.ipaddress, 0, 16);
            }
        }
    }

}

