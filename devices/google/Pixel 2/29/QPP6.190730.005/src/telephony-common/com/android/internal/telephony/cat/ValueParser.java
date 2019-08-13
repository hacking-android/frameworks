/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  com.android.internal.telephony.GsmAlphabet
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.cat.CatLog;
import com.android.internal.telephony.cat.CommandDetails;
import com.android.internal.telephony.cat.ComprehensionTlv;
import com.android.internal.telephony.cat.DeviceIdentities;
import com.android.internal.telephony.cat.Duration;
import com.android.internal.telephony.cat.FontSize;
import com.android.internal.telephony.cat.IconId;
import com.android.internal.telephony.cat.Item;
import com.android.internal.telephony.cat.ItemsIconId;
import com.android.internal.telephony.cat.ResultCode;
import com.android.internal.telephony.cat.ResultException;
import com.android.internal.telephony.cat.TextAlignment;
import com.android.internal.telephony.cat.TextAttribute;
import com.android.internal.telephony.cat.TextColor;
import com.android.internal.telephony.uicc.IccUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

abstract class ValueParser {
    ValueParser() {
    }

    @UnsupportedAppUsage
    static String retrieveAlphaId(ComprehensionTlv object) throws ResultException {
        boolean bl;
        byte[] arrby = null;
        if (object != null) {
            arrby = ((ComprehensionTlv)object).getRawValue();
            int n = ((ComprehensionTlv)object).getValueIndex();
            int n2 = ((ComprehensionTlv)object).getLength();
            if (n2 != 0) {
                try {
                    object = IccUtils.adnStringFieldToString((byte[])arrby, (int)n, (int)n2);
                    return object;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Alpha Id length=");
            ((StringBuilder)object).append(n2);
            CatLog.d("ValueParser", ((StringBuilder)object).toString());
            return null;
        }
        object = Resources.getSystem();
        try {
            bl = object.getBoolean(17891526);
        }
        catch (Resources.NotFoundException notFoundException) {
            bl = false;
        }
        object = bl ? arrby : "Default Message";
        return object;
    }

    static CommandDetails retrieveCommandDetails(ComprehensionTlv comprehensionTlv) throws ResultException {
        CommandDetails commandDetails = new CommandDetails();
        byte[] arrby = comprehensionTlv.getRawValue();
        int n = comprehensionTlv.getValueIndex();
        try {
            commandDetails.compRequired = comprehensionTlv.isComprehensionRequired();
            commandDetails.commandNumber = arrby[n] & 255;
            commandDetails.typeOfCommand = arrby[n + 1] & 255;
            commandDetails.commandQualifier = arrby[n + 2] & 255;
            return commandDetails;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
        }
    }

    static DeviceIdentities retrieveDeviceIdentities(ComprehensionTlv comprehensionTlv) throws ResultException {
        DeviceIdentities deviceIdentities = new DeviceIdentities();
        byte[] arrby = comprehensionTlv.getRawValue();
        int n = comprehensionTlv.getValueIndex();
        try {
            deviceIdentities.sourceId = arrby[n] & 255;
            deviceIdentities.destinationId = arrby[n + 1] & 255;
            return deviceIdentities;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ResultException(ResultCode.REQUIRED_VALUES_MISSING);
        }
    }

    static Duration retrieveDuration(ComprehensionTlv object) throws ResultException {
        byte[] arrby = Duration.TimeUnit.SECOND;
        arrby = object.getRawValue();
        int n = object.getValueIndex();
        try {
            object = Duration.TimeUnit.values()[arrby[n] & 255];
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
        }
        n = arrby[n + 1];
        return new Duration(n & 255, (Duration.TimeUnit)((Object)object));
    }

    static IconId retrieveIconId(ComprehensionTlv comprehensionTlv) throws ResultException {
        int n;
        IconId iconId = new IconId();
        byte[] arrby = comprehensionTlv.getRawValue();
        boolean bl = (arrby[n = comprehensionTlv.getValueIndex()] & 255) == 0;
        try {
            iconId.selfExplanatory = bl;
            iconId.recordNumber = arrby[n + 1] & 255;
            return iconId;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
        }
    }

    static Item retrieveItem(ComprehensionTlv object) throws ResultException {
        Object var1_2 = null;
        byte[] arrby = ((ComprehensionTlv)object).getRawValue();
        int n = ((ComprehensionTlv)object).getValueIndex();
        int n2 = ((ComprehensionTlv)object).getLength();
        object = var1_2;
        if (n2 != 0) {
            try {
                object = new Item(arrby[n] & 255, IccUtils.adnStringFieldToString((byte[])arrby, (int)(n + 1), (int)(n2 - 1)));
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
            }
        }
        return object;
    }

    static int retrieveItemId(ComprehensionTlv comprehensionTlv) throws ResultException {
        byte[] arrby = comprehensionTlv.getRawValue();
        int n = comprehensionTlv.getValueIndex();
        n = arrby[n];
        return n & 255;
    }

    static ItemsIconId retrieveItemsIconId(ComprehensionTlv arrn) throws ResultException {
        CatLog.d("ValueParser", "retrieveItemsIconId:");
        ItemsIconId itemsIconId = new ItemsIconId();
        byte[] arrby = arrn.getRawValue();
        int n = arrn.getValueIndex();
        int n2 = arrn.getLength();
        boolean bl = true;
        int n3 = n2 - 1;
        itemsIconId.recordNumbers = new int[n3];
        n2 = n + 1;
        if ((arrby[n] & 255) != 0) {
            bl = false;
        }
        try {
            itemsIconId.selfExplanatory = bl;
            n = 0;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
        }
        while (n < n3) {
            arrn = itemsIconId.recordNumbers;
            arrn[n] = arrby[n2];
            ++n;
            ++n2;
        }
        return itemsIconId;
    }

    @UnsupportedAppUsage
    static List<TextAttribute> retrieveTextAttribute(ComprehensionTlv object) throws ResultException {
        ArrayList<TextAttribute> arrayList = new ArrayList<TextAttribute>();
        byte[] arrby = object.getRawValue();
        int n = object.getValueIndex();
        int n2 = object.getLength();
        if (n2 != 0) {
            int n3 = n2 / 4;
            n2 = 0;
            while (n2 < n3) {
                int n4;
                byte by;
                TextAlignment textAlignment;
                byte by2;
                byte by3;
                block7 : {
                    by = arrby[n];
                    by2 = arrby[n + 1];
                    n4 = arrby[n + 2] & 255;
                    by3 = arrby[n + 3];
                    try {
                        textAlignment = TextAlignment.fromInt(n4 & 3);
                        object = FontSize.fromInt(n4 >> 2 & 3);
                        if (object != null) break block7;
                    }
                    catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
                    }
                    object = FontSize.NORMAL;
                }
                boolean bl = false;
                boolean bl2 = (n4 & 16) != 0;
                boolean bl3 = (n4 & 32) != 0;
                boolean bl4 = (n4 & 64) != 0;
                if ((n4 & 128) != 0) {
                    bl = true;
                }
                TextColor textColor = TextColor.fromInt(by3 & 255);
                TextAttribute textAttribute = new TextAttribute(by & 255, by2 & 255, textAlignment, (FontSize)((Object)object), bl2, bl3, bl4, bl, textColor);
                arrayList.add(textAttribute);
                ++n2;
                n += 4;
            }
            return arrayList;
        }
        return null;
    }

    @UnsupportedAppUsage
    static String retrieveTextString(ComprehensionTlv object) throws ResultException {
        block14 : {
            block13 : {
                byte[] arrby = object.getRawValue();
                int n = object.getValueIndex();
                int n2 = object.getLength();
                if (n2 == 0) {
                    return null;
                }
                --n2;
                byte by = (byte)(arrby[n] & 12);
                if (by == 0) {
                    object = GsmAlphabet.gsm7BitPackedToString((byte[])arrby, (int)(n + 1), (int)(n2 * 8 / 7));
                    break block13;
                }
                if (by == 4) {
                    object = GsmAlphabet.gsm8BitUnpackedToString((byte[])arrby, (int)(n + 1), (int)n2);
                    break block13;
                }
                if (by != 8) break block14;
                object = new String(arrby, n + 1, n2, "UTF-16");
            }
            return object;
        }
        try {
            object = new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
            throw object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
        }
    }
}

