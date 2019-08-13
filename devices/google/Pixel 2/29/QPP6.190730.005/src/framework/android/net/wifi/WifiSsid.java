/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.ByteArrayOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Locale;

public class WifiSsid
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<WifiSsid> CREATOR = new Parcelable.Creator<WifiSsid>(){

        @Override
        public WifiSsid createFromParcel(Parcel parcel) {
            WifiSsid wifiSsid = new WifiSsid();
            int n = parcel.readInt();
            byte[] arrby = new byte[n];
            parcel.readByteArray(arrby);
            wifiSsid.octets.write(arrby, 0, n);
            return wifiSsid;
        }

        public WifiSsid[] newArray(int n) {
            return new WifiSsid[n];
        }
    };
    private static final int HEX_RADIX = 16;
    @UnsupportedAppUsage
    public static final String NONE = "<unknown ssid>";
    private static final String TAG = "WifiSsid";
    @UnsupportedAppUsage
    public final ByteArrayOutputStream octets = new ByteArrayOutputStream(32);

    private WifiSsid() {
    }

    private void convertToBytes(String string2) {
        int n = 0;
        block6 : while (n < string2.length()) {
            int n2 = string2.charAt(n);
            if (n2 != 92) {
                this.octets.write(n2);
                ++n;
                continue;
            }
            if ((n2 = string2.charAt(++n)) != 34) {
                if (n2 != 92) {
                    if (n2 != 101) {
                        if (n2 != 110) {
                            if (n2 != 114) {
                                if (n2 != 116) {
                                    if (n2 != 120) {
                                        int n3;
                                        switch (n2) {
                                            default: {
                                                continue block6;
                                            }
                                            case 48: 
                                            case 49: 
                                            case 50: 
                                            case 51: 
                                            case 52: 
                                            case 53: 
                                            case 54: 
                                            case 55: 
                                        }
                                        int n4 = string2.charAt(n) - 48;
                                        n2 = n3 = n + 1;
                                        n = n4;
                                        if (string2.charAt(n3) >= '0') {
                                            n2 = n3;
                                            n = n4;
                                            if (string2.charAt(n3) <= '7') {
                                                n = n4 * 8 + string2.charAt(n3) - 48;
                                                n2 = n3 + 1;
                                            }
                                        }
                                        n4 = n2;
                                        n3 = n;
                                        if (string2.charAt(n2) >= '0') {
                                            n4 = n2;
                                            n3 = n;
                                            if (string2.charAt(n2) <= '7') {
                                                n3 = n * 8 + string2.charAt(n2) - 48;
                                                n4 = n2 + 1;
                                            }
                                        }
                                        this.octets.write(n3);
                                        n = n4;
                                        continue;
                                    }
                                    n2 = n + 1;
                                    try {
                                        n = Integer.parseInt(string2.substring(n2, n2 + 2), 16);
                                    }
                                    catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                                        n = -1;
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        n = -1;
                                    }
                                    if (n < 0) {
                                        n = Character.digit(string2.charAt(n2), 16);
                                        if (n < 0) {
                                            n = n2;
                                            continue;
                                        }
                                        this.octets.write(n);
                                        n = n2 + 1;
                                        continue;
                                    }
                                    this.octets.write(n);
                                    n = n2 + 2;
                                    continue;
                                }
                                this.octets.write(9);
                                ++n;
                                continue;
                            }
                            this.octets.write(13);
                            ++n;
                            continue;
                        }
                        this.octets.write(10);
                        ++n;
                        continue;
                    }
                    this.octets.write(27);
                    ++n;
                    continue;
                }
                this.octets.write(92);
                ++n;
                continue;
            }
            this.octets.write(34);
            ++n;
        }
    }

    @UnsupportedAppUsage
    public static WifiSsid createFromAsciiEncoded(String string2) {
        WifiSsid wifiSsid = new WifiSsid();
        wifiSsid.convertToBytes(string2);
        return wifiSsid;
    }

    public static WifiSsid createFromByteArray(byte[] arrby) {
        WifiSsid wifiSsid = new WifiSsid();
        if (arrby != null) {
            wifiSsid.octets.write(arrby, 0, arrby.length);
        }
        return wifiSsid;
    }

    public static WifiSsid createFromHex(String string2) {
        WifiSsid wifiSsid;
        String string3;
        block8 : {
            block7 : {
                wifiSsid = new WifiSsid();
                if (string2 == null) {
                    return wifiSsid;
                }
                if (string2.startsWith("0x")) break block7;
                string3 = string2;
                if (!string2.startsWith("0X")) break block8;
            }
            string3 = string2.substring(2);
        }
        for (int i = 0; i < string3.length() - 1; i += 2) {
            int n;
            try {
                n = Integer.parseInt(string3.substring(i, i + 2), 16);
            }
            catch (NumberFormatException numberFormatException) {
                n = 0;
            }
            wifiSsid.octets.write(n);
        }
        return wifiSsid;
    }

    private boolean isArrayAllZeroes(byte[] arrby) {
        for (int i = 0; i < arrby.length; ++i) {
            if (arrby[i] == 0) continue;
            return false;
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof WifiSsid)) {
            return false;
        }
        object = (WifiSsid)object;
        return Arrays.equals(this.octets.toByteArray(), ((WifiSsid)object).octets.toByteArray());
    }

    public String getHexString() {
        String string2 = "0x";
        byte[] arrby = this.getOctets();
        for (int i = 0; i < this.octets.size(); ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(String.format(Locale.US, "%02x", arrby[i]));
            string2 = stringBuilder.toString();
        }
        if (this.octets.size() <= 0) {
            string2 = null;
        }
        return string2;
    }

    @UnsupportedAppUsage
    public byte[] getOctets() {
        return this.octets.toByteArray();
    }

    public int hashCode() {
        return Arrays.hashCode(this.octets.toByteArray());
    }

    public boolean isHidden() {
        return this.isArrayAllZeroes(this.octets.toByteArray());
    }

    public String toString() {
        Object object = this.octets.toByteArray();
        if (this.octets.size() > 0 && !this.isArrayAllZeroes((byte[])object)) {
            CharsetDecoder charsetDecoder = Charset.forName("UTF-8").newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
            CharBuffer charBuffer = CharBuffer.allocate(32);
            object = charsetDecoder.decode(ByteBuffer.wrap((byte[])object), charBuffer, true);
            charBuffer.flip();
            if (((CoderResult)object).isError()) {
                return NONE;
            }
            return charBuffer.toString();
        }
        return "";
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.octets.size());
        parcel.writeByteArray(this.octets.toByteArray());
    }

}

