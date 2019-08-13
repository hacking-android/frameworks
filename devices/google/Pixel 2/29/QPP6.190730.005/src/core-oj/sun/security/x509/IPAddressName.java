/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import sun.misc.HexDumpEncoder;
import sun.security.util.BitArray;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralNameInterface;

public class IPAddressName
implements GeneralNameInterface {
    private static final int MASKSIZE = 16;
    private byte[] address;
    private boolean isIPv4;
    private String name;

    public IPAddressName(String string) throws IOException {
        block2 : {
            block3 : {
                block6 : {
                    block5 : {
                        block4 : {
                            if (string == null || string.length() == 0) break block2;
                            if (string.charAt(string.length() - 1) == '/') break block3;
                            if (string.indexOf(58) < 0) break block4;
                            this.parseIPv6(string);
                            this.isIPv4 = false;
                            break block5;
                        }
                        if (string.indexOf(46) < 0) break block6;
                        this.parseIPv4(string);
                        this.isIPv4 = true;
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid IPAddress: ");
                stringBuilder.append(string);
                throw new IOException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid IPAddress: ");
            stringBuilder.append(string);
            throw new IOException(stringBuilder.toString());
        }
        throw new IOException("IPAddress cannot be null or empty");
    }

    public IPAddressName(DerValue derValue) throws IOException {
        this(derValue.getOctetString());
    }

    public IPAddressName(byte[] arrby) throws IOException {
        if (arrby.length != 4 && arrby.length != 8) {
            if (arrby.length != 16 && arrby.length != 32) {
                throw new IOException("Invalid IPAddressName");
            }
            this.isIPv4 = false;
        } else {
            this.isIPv4 = true;
        }
        this.address = arrby;
    }

    private void parseIPv4(String string) throws IOException {
        int n = string.indexOf(47);
        if (n == -1) {
            this.address = InetAddress.getByName(string).getAddress();
        } else {
            this.address = new byte[8];
            byte[] arrby = InetAddress.getByName(string.substring(n + 1)).getAddress();
            System.arraycopy(InetAddress.getByName(string.substring(0, n)).getAddress(), 0, this.address, 0, 4);
            System.arraycopy(arrby, 0, this.address, 4, 4);
        }
    }

    private void parseIPv6(String arrby) throws IOException {
        int n;
        block5 : {
            block4 : {
                int n2;
                block3 : {
                    n2 = arrby.indexOf(47);
                    if (n2 != -1) break block3;
                    this.address = InetAddress.getByName((String)arrby).getAddress();
                    break block4;
                }
                this.address = new byte[32];
                System.arraycopy(InetAddress.getByName(arrby.substring(0, n2)).getAddress(), 0, this.address, 0, 16);
                n = Integer.parseInt(arrby.substring(n2 + 1));
                if (n >= 0 && n <= 128) {
                    arrby = new BitArray(128);
                    for (n2 = 0; n2 < n; ++n2) {
                        arrby.set(n2, true);
                    }
                    arrby = arrby.toByteArray();
                    for (n2 = 0; n2 < 16; ++n2) {
                        this.address[n2 + 16] = arrby[n2];
                    }
                }
                break block5;
            }
            return;
        }
        arrby = new StringBuilder();
        arrby.append("IPv6Address prefix length (");
        arrby.append(n);
        arrby.append(") in out of valid range [0,128]");
        throw new IOException(arrby.toString());
    }

    @Override
    public int constrains(GeneralNameInterface arrby) throws UnsupportedOperationException {
        int n;
        if (arrby == null) {
            n = -1;
        } else if (arrby.getType() != 7) {
            n = -1;
        } else if (((IPAddressName)arrby).equals(this)) {
            n = 0;
        } else {
            arrby = ((IPAddressName)arrby).address;
            if (arrby.length == 4 && this.address.length == 4) {
                n = 3;
            } else if (arrby.length == 8 && this.address.length == 8 || arrby.length == 32 && this.address.length == 32) {
                boolean bl = true;
                boolean bl2 = true;
                boolean bl3 = false;
                boolean bl4 = false;
                int n2 = this.address.length / 2;
                for (n = 0; n < n2; ++n) {
                    byte by;
                    byte by2;
                    byte[] arrby2 = this.address;
                    if ((byte)(arrby2[n] & arrby2[n + n2]) != arrby2[n]) {
                        bl3 = true;
                    }
                    if ((byte)(arrby[n] & arrby[n + n2]) != arrby[n]) {
                        bl4 = true;
                    }
                    if ((byte)((arrby2 = this.address)[n + n2] & arrby[n + n2]) != arrby2[n + n2] || (by = (byte)(arrby2[n] & arrby2[n + n2])) != (byte)(arrby2[n + n2] & (by2 = arrby[n]))) {
                        bl = false;
                    }
                    if ((byte)((by2 = arrby[n + n2]) & (arrby2 = this.address)[n + n2]) == arrby[n + n2] && (byte)(arrby[n] & arrby[n + n2]) == (byte)(arrby2[n] & arrby[n + n2])) continue;
                    bl2 = false;
                }
                n = !bl3 && !bl4 ? (bl ? 1 : (bl2 ? 2 : 3)) : (bl3 && bl4 ? 0 : (bl3 ? 2 : 1));
            } else if (arrby.length != 8 && arrby.length != 32) {
                byte[] arrby3 = this.address;
                if (arrby3.length != 8 && arrby3.length != 32) {
                    n = 3;
                } else {
                    byte by;
                    int n3 = this.address.length / 2;
                    for (n = 0; n < n3 && ((by = arrby[n]) & (arrby3 = this.address)[n + n3]) == arrby3[n]; ++n) {
                    }
                    n = n == n3 ? 1 : 3;
                }
            } else {
                int n4 = arrby.length / 2;
                for (n = 0; n < n4 && (this.address[n] & arrby[n + n4]) == arrby[n]; ++n) {
                }
                n = n == n4 ? 2 : 3;
            }
        }
        return n;
    }

    @Override
    public void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.putOctetString(this.address);
    }

    public boolean equals(Object arrby) {
        if (this == arrby) {
            return true;
        }
        if (!(arrby instanceof IPAddressName)) {
            return false;
        }
        arrby = ((IPAddressName)arrby).address;
        int n = arrby.length;
        byte[] arrby2 = this.address;
        if (n != arrby2.length) {
            return false;
        }
        if (arrby2.length != 8 && arrby2.length != 32) {
            return Arrays.equals(arrby, arrby2);
        }
        int n2 = this.address.length / 2;
        for (n = 0; n < n2; ++n) {
            arrby2 = this.address;
            byte by = arrby2[n];
            if ((byte)(arrby2[n + n2] & by) == (byte)(arrby[n] & arrby[n + n2])) continue;
            return false;
        }
        for (n = n2; n < (arrby2 = this.address).length; ++n) {
            if (arrby2[n] == arrby[n]) continue;
            return false;
        }
        return true;
    }

    public byte[] getBytes() {
        return (byte[])this.address.clone();
    }

    public String getName() throws IOException {
        Object object = this.name;
        if (object != null) {
            return object;
        }
        if (this.isIPv4) {
            object = new byte[4];
            System.arraycopy(this.address, 0, (byte[])object, 0, 4);
            this.name = InetAddress.getByAddress((byte[])object).getHostAddress();
            Object object2 = this.address;
            if (((byte[])object2).length == 8) {
                object = new byte[4];
                System.arraycopy((byte[])object2, 4, (byte[])object, 0, 4);
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(this.name);
                ((StringBuilder)object2).append("/");
                ((StringBuilder)object2).append(InetAddress.getByAddress((byte[])object).getHostAddress());
                this.name = ((StringBuilder)object2).toString();
            }
        } else {
            object = new byte[16];
            System.arraycopy(this.address, 0, (byte[])object, 0, 16);
            this.name = InetAddress.getByAddress((byte[])object).getHostAddress();
            if (this.address.length == 32) {
                int n;
                object = new byte[16];
                for (n = 16; n < 32; ++n) {
                    object[n - 16] = this.address[n];
                }
                object = new BitArray(128, (byte[])object);
                for (n = 0; n < 128 && ((BitArray)object).get(n); ++n) {
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.name);
                stringBuilder.append("/");
                stringBuilder.append(n);
                this.name = stringBuilder.toString();
                while (n < 128) {
                    if (!((BitArray)object).get(n)) {
                        ++n;
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid IPv6 subdomain - set bit ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" not contiguous");
                    throw new IOException(((StringBuilder)object).toString());
                }
            }
        }
        return this.name;
    }

    @Override
    public int getType() {
        return 7;
    }

    public int hashCode() {
        byte[] arrby;
        int n = 0;
        for (int i = 0; i < (arrby = this.address).length; ++i) {
            n += arrby[i] * i;
        }
        return n;
    }

    @Override
    public int subtreeDepth() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("subtreeDepth() not defined for IPAddressName");
    }

    public String toString() {
        try {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("IPAddress: ");
            charSequence.append(this.getName());
            charSequence = charSequence.toString();
            return charSequence;
        }
        catch (IOException iOException) {
            HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IPAddress: ");
            stringBuilder.append(hexDumpEncoder.encodeBuffer(this.address));
            return stringBuilder.toString();
        }
    }
}

