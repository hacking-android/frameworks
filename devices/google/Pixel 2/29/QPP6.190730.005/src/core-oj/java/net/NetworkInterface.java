/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.OsConstants
 *  android.system.StructIfaddrs
 *  libcore.io.Libcore
 *  libcore.io.Os
 */
package java.net;

import android.system.ErrnoException;
import android.system.OsConstants;
import android.system.StructIfaddrs;
import java.net.DefaultInterface;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetPermission;
import java.net.SocketException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import libcore.io.Libcore;
import libcore.io.Os;

public final class NetworkInterface {
    private static final int defaultIndex;
    private static final NetworkInterface defaultInterface;
    private InetAddress[] addrs;
    private InterfaceAddress[] bindings;
    private List<NetworkInterface> childs;
    private String displayName;
    private byte[] hardwareAddr;
    private int index;
    private String name;
    private NetworkInterface parent = null;
    private boolean virtual = false;

    static {
        defaultInterface = DefaultInterface.getDefault();
        NetworkInterface networkInterface = defaultInterface;
        defaultIndex = networkInterface != null ? networkInterface.getIndex() : 0;
    }

    NetworkInterface() {
    }

    NetworkInterface(String string, int n, InetAddress[] arrinetAddress) {
        this.name = string;
        this.index = n;
        this.addrs = arrinetAddress;
    }

    static /* synthetic */ InetAddress[] access$000(NetworkInterface networkInterface) {
        return networkInterface.addrs;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static NetworkInterface[] getAll() throws SocketException {
        block8 : {
            var0 = new HashMap<K, V>();
            ** try [egrp 0[TRYBLOCK] [0 : 8->17)] { 
lbl3: // 2 sources:
            for (StructIfaddrs var4_4 : Libcore.os.getifaddrs()) {
                var5_7 = var4_4.ifa_name;
                var6_8 = (List)var0.get(var5_7);
                var7_9 = var6_8;
                if (var6_8 == null) {
                    var7_9 = new ArrayList<StructIfaddrs>();
                    var0.put(var5_7, var7_9);
                }
                var7_9.add(var4_4);
            }
            var7_9 = new HashMap<K, V>(var0.size());
            var0 = var0.entrySet().iterator();
            break block8;
lbl17: // 1 sources:
            catch (ErrnoException var7_10) {
                throw var7_10.rethrowAsSocketException();
            }
        }
        while (var0.hasNext()) {
            var8_12 = (Map.Entry)var0.next();
            var5_7 = (String)var8_12.getKey();
            var3_3 = Libcore.os.if_nametoindex((String)var8_12.getKey());
            if (var3_3 == 0) continue;
            var6_8 = new NetworkInterface(var5_7, var3_3, null);
            var6_8.displayName = var5_7;
            var1_1 = new ArrayList<E>();
            var4_6 = new ArrayList<InterfaceAddress>();
            for (StructIfaddrs var8_13 : (List)var8_12.getValue()) {
                if (var8_13.ifa_addr != null) {
                    var1_1.add(var8_13.ifa_addr);
                    var4_6.add(new InterfaceAddress(var8_13.ifa_addr, (Inet4Address)var8_13.ifa_broadaddr, var8_13.ifa_netmask));
                }
                if (var8_13.hwaddr == null) continue;
                var6_8.hardwareAddr = var8_13.hwaddr;
            }
            var6_8.addrs = var1_1.toArray(new InetAddress[var1_1.size()]);
            var6_8.bindings = var4_6.toArray(new InterfaceAddress[var4_6.size()]);
            var6_8.childs = new ArrayList<NetworkInterface>(0);
            var7_9.put(var5_7, var6_8);
        }
        var6_8 = var7_9.entrySet().iterator();
        while (var6_8.hasNext() != false) {
            var0 = (NetworkInterface)((Map.Entry)var6_8.next()).getValue();
            var1_1 = var0.getName();
            var3_3 = var1_1.indexOf(58);
            if (var3_3 == -1) continue;
            var1_1 = (NetworkInterface)var7_9.get(var1_1.substring(0, var3_3));
            var0.virtual = true;
            var0.parent = var1_1;
            var1_1.childs.add((NetworkInterface)var0);
        }
        return var7_9.values().toArray(new NetworkInterface[var7_9.size()]);
    }

    public static NetworkInterface getByIndex(int n) throws SocketException {
        if (n >= 0) {
            for (NetworkInterface networkInterface : NetworkInterface.getAll()) {
                if (networkInterface.getIndex() != n) continue;
                return networkInterface;
            }
            return null;
        }
        throw new IllegalArgumentException("Interface index can't be negative");
    }

    public static NetworkInterface getByInetAddress(InetAddress inetAddress) throws SocketException {
        if (inetAddress != null) {
            if (!(inetAddress instanceof Inet4Address) && !(inetAddress instanceof Inet6Address)) {
                throw new IllegalArgumentException("invalid address type");
            }
            for (NetworkInterface networkInterface : NetworkInterface.getAll()) {
                Iterator<InetAddress> iterator = Collections.list(networkInterface.getInetAddresses()).iterator();
                while (iterator.hasNext()) {
                    if (!iterator.next().equals(inetAddress)) continue;
                    return networkInterface;
                }
            }
            return null;
        }
        throw new NullPointerException();
    }

    public static NetworkInterface getByName(String string) throws SocketException {
        if (string != null) {
            for (NetworkInterface networkInterface : NetworkInterface.getAll()) {
                if (!networkInterface.getName().equals(string)) continue;
                return networkInterface;
            }
            return null;
        }
        throw new NullPointerException();
    }

    static NetworkInterface getDefault() {
        return defaultInterface;
    }

    /*
     * Exception decompiling
     */
    private int getFlags() throws SocketException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static Enumeration<NetworkInterface> getNetworkInterfaces() throws SocketException {
        NetworkInterface[] arrnetworkInterface = NetworkInterface.getAll();
        if (arrnetworkInterface.length == 0) {
            return null;
        }
        return Collections.enumeration(Arrays.asList(arrnetworkInterface));
    }

    public boolean equals(Object arrinetAddress) {
        boolean bl = arrinetAddress instanceof NetworkInterface;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        arrinetAddress = (NetworkInterface)arrinetAddress;
        InetAddress[] arrinetAddress2 = this.name;
        if (arrinetAddress2 != null ? !arrinetAddress2.equals(arrinetAddress.name) : arrinetAddress.name != null) {
            return false;
        }
        arrinetAddress2 = this.addrs;
        if (arrinetAddress2 == null) {
            if (arrinetAddress.addrs == null) {
                bl2 = true;
            }
            return bl2;
        }
        InetAddress[] arrinetAddress3 = arrinetAddress.addrs;
        if (arrinetAddress3 == null) {
            return false;
        }
        if (arrinetAddress2.length != arrinetAddress3.length) {
            return false;
        }
        arrinetAddress = arrinetAddress.addrs;
        int n = arrinetAddress.length;
        for (int i = 0; i < n; ++i) {
            boolean bl3;
            boolean bl4 = false;
            int n2 = 0;
            do {
                bl3 = bl4;
                if (n2 >= n) break;
                if (this.addrs[i].equals(arrinetAddress[n2])) {
                    bl3 = true;
                    break;
                }
                ++n2;
            } while (true);
            if (bl3) continue;
            return false;
        }
        return true;
    }

    public String getDisplayName() {
        String string = "".equals(this.displayName) ? null : this.displayName;
        return string;
    }

    public byte[] getHardwareAddress() throws SocketException {
        NetworkInterface networkInterface = NetworkInterface.getByName(this.name);
        if (networkInterface != null) {
            return networkInterface.hardwareAddr;
        }
        throw new SocketException("NetworkInterface doesn't exist anymore");
    }

    public int getIndex() {
        return this.index;
    }

    public Enumeration<InetAddress> getInetAddresses() {
        return new 1checkedAddresses(this);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public List<InterfaceAddress> getInterfaceAddresses() {
        var1_1 = new ArrayList<InterfaceAddress>(1);
        if (this.bindings == null) return var1_1;
        var2_2 = System.getSecurityManager();
        var3_3 = 0;
        while (var3_3 < (var4_4 = this.bindings).length) {
            if (var2_2 == null) ** GOTO lbl9
            try {
                var2_2.checkConnect(var4_4[var3_3].getAddress().getHostAddress(), -1);
lbl9: // 2 sources:
                var1_1.add(this.bindings[var3_3]);
            }
            catch (SecurityException var4_5) {
                // empty catch block
            }
            ++var3_3;
        }
        return var1_1;
    }

    /*
     * Exception decompiling
     */
    public int getMTU() throws SocketException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public String getName() {
        return this.name;
    }

    public NetworkInterface getParent() {
        return this.parent;
    }

    public Enumeration<NetworkInterface> getSubInterfaces() {
        return Collections.enumeration(this.childs);
    }

    public int hashCode() {
        String string = this.name;
        int n = string == null ? 0 : string.hashCode();
        return n;
    }

    public boolean isLoopback() throws SocketException {
        boolean bl = (this.getFlags() & OsConstants.IFF_LOOPBACK) != 0;
        return bl;
    }

    public boolean isPointToPoint() throws SocketException {
        boolean bl = (this.getFlags() & OsConstants.IFF_POINTOPOINT) != 0;
        return bl;
    }

    public boolean isUp() throws SocketException {
        int n = OsConstants.IFF_UP | OsConstants.IFF_RUNNING;
        boolean bl = (this.getFlags() & n) == n;
        return bl;
    }

    public boolean isVirtual() {
        return this.virtual;
    }

    public boolean supportsMulticast() throws SocketException {
        boolean bl = (this.getFlags() & OsConstants.IFF_MULTICAST) != 0;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name:");
        String string = this.name;
        CharSequence charSequence = string;
        if (string == null) {
            charSequence = "null";
        }
        stringBuilder.append((String)charSequence);
        string = stringBuilder.toString();
        charSequence = string;
        if (this.displayName != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" (");
            ((StringBuilder)charSequence).append(this.displayName);
            ((StringBuilder)charSequence).append(")");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    /*
     * Exception performing whole class analysis.
     */
    class 1checkedAddresses
    implements Enumeration<InetAddress> {
        private int count;
        private int i;
        private InetAddress[] local_addrs;
        final /* synthetic */ NetworkInterface this$0;

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        1checkedAddresses(NetworkInterface var1_1) {
            this.this$0 = var1_1;
            super();
            this.i = 0;
            this.count = 0;
            this.local_addrs = new InetAddress[NetworkInterface.access$000(var1_1).length];
            var2_2 = 1;
            var3_3 = System.getSecurityManager();
            var4_4 = var2_2;
            if (var3_3 != null) {
                try {
                    var5_5 = new NetPermission("getNetworkInformation");
                    var3_3.checkPermission((Permission)var5_5);
                    var4_4 = var2_2;
                }
                catch (SecurityException var5_6) {
                    var4_4 = 0;
                }
            }
            var2_2 = 0;
            while (var2_2 < NetworkInterface.access$000(var1_1).length) {
                if (var3_3 == null || var4_4 != 0) ** GOTO lbl22
                try {
                    var3_3.checkConnect(NetworkInterface.access$000(var1_1)[var2_2].getHostAddress(), -1);
lbl22: // 2 sources:
                    var5_5 = this.local_addrs;
                    var6_8 = this.count;
                    this.count = var6_8 + 1;
                    var5_5[var6_8] = NetworkInterface.access$000(var1_1)[var2_2];
                }
                catch (SecurityException var5_7) {
                    // empty catch block
                }
                ++var2_2;
            }
        }

        @Override
        public boolean hasMoreElements() {
            boolean bl = this.i < this.count;
            return bl;
        }

        @Override
        public InetAddress nextElement() {
            int n = this.i;
            if (n < this.count) {
                InetAddress[] arrinetAddress = this.local_addrs;
                this.i = n + 1;
                return arrinetAddress[n];
            }
            throw new NoSuchElementException();
        }
    }

}

