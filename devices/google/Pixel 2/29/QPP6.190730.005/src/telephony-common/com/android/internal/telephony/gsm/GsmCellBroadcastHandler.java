/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.CellLocation
 *  android.telephony.SmsCbLocation
 *  android.telephony.SmsCbMessage
 *  android.telephony.TelephonyManager
 *  android.telephony.gsm.GsmCellLocation
 *  com.android.internal.telephony.gsm.GsmSmsCbMessage
 *  com.android.internal.telephony.gsm.SmsCbHeader
 */
package com.android.internal.telephony.gsm;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.telephony.CellLocation;
import android.telephony.SmsCbLocation;
import android.telephony.SmsCbMessage;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import com.android.internal.telephony.CellBroadcastHandler;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.gsm.GsmSmsCbMessage;
import com.android.internal.telephony.gsm.SmsCbHeader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class GsmCellBroadcastHandler
extends CellBroadcastHandler {
    private static final boolean VDBG = false;
    private final HashMap<SmsCbConcatInfo, byte[][]> mSmsCbPageMap = new HashMap(4);

    protected GsmCellBroadcastHandler(Context context, Phone phone) {
        super("GsmCellBroadcastHandler", context, phone);
        phone.mCi.setOnNewGsmBroadcastSms(this.getHandler(), 1, null);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SmsCbMessage handleGsmBroadcastSms(AsyncResult asyncResult) {
        try {
            int n;
            SmsCbHeader smsCbHeader;
            Object object;
            int n2;
            int n3;
            String string;
            byte[] arrby;
            byte[][] arrarrby;
            block13 : {
                block11 : {
                    block12 : {
                        arrby = (byte[])asyncResult.result;
                        smsCbHeader = new SmsCbHeader(arrby);
                        string = TelephonyManager.from((Context)this.mContext).getNetworkOperatorForPhone(this.mPhone.getPhoneId());
                        n = -1;
                        n3 = -1;
                        asyncResult = this.mPhone.getCellLocation();
                        if (asyncResult instanceof GsmCellLocation) {
                            asyncResult = (GsmCellLocation)asyncResult;
                            n = asyncResult.getLac();
                            n3 = asyncResult.getCid();
                        }
                        if ((n2 = smsCbHeader.getGeographicalScope()) == 0) break block11;
                        if (n2 == 2) break block12;
                        if (n2 == 3) break block11;
                        asyncResult = new SmsCbLocation(string);
                        break block13;
                    }
                    asyncResult = new SmsCbLocation(string, n, -1);
                    break block13;
                }
                asyncResult = new SmsCbLocation(string, n, n3);
            }
            int n4 = smsCbHeader.getNumberOfPages();
            n2 = 0;
            if (n4 <= 1) {
                arrarrby = new byte[][]{arrby};
            } else {
                SmsCbConcatInfo smsCbConcatInfo = new SmsCbConcatInfo(smsCbHeader, (SmsCbLocation)asyncResult);
                object = this.mSmsCbPageMap.get(smsCbConcatInfo);
                arrarrby = object;
                if (object == null) {
                    arrarrby = new byte[n4][];
                    this.mSmsCbPageMap.put(smsCbConcatInfo, arrarrby);
                }
                arrarrby[smsCbHeader.getPageIndex() - 1] = arrby;
                n4 = arrarrby.length;
                while (n2 < n4) {
                    if (arrarrby[n2] == null) {
                        this.log("still missing pdu");
                        return null;
                    }
                    ++n2;
                }
                this.mSmsCbPageMap.remove(smsCbConcatInfo);
            }
            object = this.mSmsCbPageMap.keySet().iterator();
            do {
                if (!object.hasNext()) {
                    return GsmSmsCbMessage.createSmsCbMessage((Context)this.mContext, (SmsCbHeader)smsCbHeader, (SmsCbLocation)asyncResult, (byte[][])arrarrby);
                }
                if (((SmsCbConcatInfo)object.next()).matchesLocation(string, n, n3)) continue;
                object.remove();
            } while (true);
        }
        catch (RuntimeException runtimeException) {
            this.loge("Error in decoding SMS CB pdu", runtimeException);
            return null;
        }
    }

    public static GsmCellBroadcastHandler makeGsmCellBroadcastHandler(Context object, Phone phone) {
        object = new GsmCellBroadcastHandler((Context)object, phone);
        object.start();
        return object;
    }

    @Override
    protected boolean handleSmsMessage(Message message) {
        SmsCbMessage smsCbMessage;
        if (message.obj instanceof AsyncResult && (smsCbMessage = this.handleGsmBroadcastSms((AsyncResult)message.obj)) != null) {
            this.handleBroadcastSms(smsCbMessage);
            return true;
        }
        return super.handleSmsMessage(message);
    }

    @Override
    protected void onQuitting() {
        this.mPhone.mCi.unSetOnNewGsmBroadcastSms(this.getHandler());
        super.onQuitting();
    }

    private static final class SmsCbConcatInfo {
        private final SmsCbHeader mHeader;
        private final SmsCbLocation mLocation;

        SmsCbConcatInfo(SmsCbHeader smsCbHeader, SmsCbLocation smsCbLocation) {
            this.mHeader = smsCbHeader;
            this.mLocation = smsCbLocation;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof SmsCbConcatInfo;
            boolean bl2 = false;
            if (bl) {
                object = (SmsCbConcatInfo)object;
                if (this.mHeader.getSerialNumber() == ((SmsCbConcatInfo)object).mHeader.getSerialNumber() && this.mLocation.equals((Object)((SmsCbConcatInfo)object).mLocation)) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }

        public int hashCode() {
            return this.mHeader.getSerialNumber() * 31 + this.mLocation.hashCode();
        }

        public boolean matchesLocation(String string, int n, int n2) {
            return this.mLocation.isInLocationArea(string, n, n2);
        }
    }

}

