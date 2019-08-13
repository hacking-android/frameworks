/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p;

import android.annotation.UnsupportedAppUsage;
import android.net.wifi.p2p.WifiP2pDevice;

public class WifiP2pProvDiscEvent {
    public static final int ENTER_PIN = 3;
    public static final int PBC_REQ = 1;
    public static final int PBC_RSP = 2;
    public static final int SHOW_PIN = 4;
    private static final String TAG = "WifiP2pProvDiscEvent";
    @UnsupportedAppUsage
    public WifiP2pDevice device;
    @UnsupportedAppUsage
    public int event;
    @UnsupportedAppUsage
    public String pin;

    @UnsupportedAppUsage
    public WifiP2pProvDiscEvent() {
        this.device = new WifiP2pDevice();
    }

    public WifiP2pProvDiscEvent(String string2) throws IllegalArgumentException {
        Object object;
        block4 : {
            block9 : {
                block6 : {
                    block8 : {
                        block7 : {
                            block5 : {
                                object = string2.split(" ");
                                if (((String[])object).length < 2) break block4;
                                if (!object[0].endsWith("PBC-REQ")) break block5;
                                this.event = 1;
                                break block6;
                            }
                            if (!((String)object[0]).endsWith("PBC-RESP")) break block7;
                            this.event = 2;
                            break block6;
                        }
                        if (!((String)object[0]).endsWith("ENTER-PIN")) break block8;
                        this.event = 3;
                        break block6;
                    }
                    if (!((String)object[0]).endsWith("SHOW-PIN")) break block9;
                    this.event = 4;
                }
                this.device = new WifiP2pDevice();
                this.device.deviceAddress = object[1];
                if (this.event == 4) {
                    this.pin = object[2];
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Malformed event ");
            ((StringBuilder)object).append(string2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Malformed event ");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.device);
        stringBuffer.append("\n event: ");
        stringBuffer.append(this.event);
        stringBuffer.append("\n pin: ");
        stringBuffer.append(this.pin);
        return stringBuffer.toString();
    }
}

