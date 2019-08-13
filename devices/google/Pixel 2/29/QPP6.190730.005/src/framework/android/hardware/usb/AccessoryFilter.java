/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.hardware.usb;

import android.hardware.usb.UsbAccessory;
import com.android.internal.util.dump.DualDumpOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class AccessoryFilter {
    public final String mManufacturer;
    public final String mModel;
    public final String mVersion;

    public AccessoryFilter(UsbAccessory usbAccessory) {
        this.mManufacturer = usbAccessory.getManufacturer();
        this.mModel = usbAccessory.getModel();
        this.mVersion = usbAccessory.getVersion();
    }

    public AccessoryFilter(String string2, String string3, String string4) {
        this.mManufacturer = string2;
        this.mModel = string3;
        this.mVersion = string4;
    }

    public static AccessoryFilter read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String string2 = null;
        String string3 = null;
        String string4 = null;
        int n = xmlPullParser.getAttributeCount();
        for (int i = 0; i < n; ++i) {
            String string5;
            String string6;
            String string7 = xmlPullParser.getAttributeName(i);
            String string8 = xmlPullParser.getAttributeValue(i);
            if ("manufacturer".equals(string7)) {
                string6 = string8;
                string5 = string3;
            } else if ("model".equals(string7)) {
                string6 = string2;
                string5 = string8;
            } else {
                string6 = string2;
                string5 = string3;
                if ("version".equals(string7)) {
                    string4 = string8;
                    string5 = string3;
                    string6 = string2;
                }
            }
            string2 = string6;
            string3 = string5;
        }
        return new AccessoryFilter(string2, string3, string4);
    }

    public boolean contains(AccessoryFilter accessoryFilter) {
        String string2 = this.mManufacturer;
        boolean bl = false;
        if (string2 != null && !Objects.equals(accessoryFilter.mManufacturer, string2)) {
            return false;
        }
        string2 = this.mModel;
        if (string2 != null && !Objects.equals(accessoryFilter.mModel, string2)) {
            return false;
        }
        string2 = this.mVersion;
        if (string2 == null || Objects.equals(accessoryFilter.mVersion, string2)) {
            bl = true;
        }
        return bl;
    }

    public void dump(DualDumpOutputStream dualDumpOutputStream, String string2, long l) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("manufacturer", 1138166333441L, this.mManufacturer);
        dualDumpOutputStream.write("model", 1138166333442L, this.mModel);
        dualDumpOutputStream.write("version", 1138166333443L, this.mVersion);
        dualDumpOutputStream.end(l);
    }

    public boolean equals(Object object) {
        String string2 = this.mManufacturer;
        boolean bl = false;
        boolean bl2 = false;
        if (string2 != null && this.mModel != null && this.mVersion != null) {
            if (object instanceof AccessoryFilter) {
                object = (AccessoryFilter)object;
                if (string2.equals(((AccessoryFilter)object).mManufacturer) && this.mModel.equals(((AccessoryFilter)object).mModel) && this.mVersion.equals(((AccessoryFilter)object).mVersion)) {
                    bl2 = true;
                }
                return bl2;
            }
            if (object instanceof UsbAccessory) {
                bl2 = string2.equals(((UsbAccessory)(object = (UsbAccessory)object)).getManufacturer()) && this.mModel.equals(((UsbAccessory)object).getModel()) && this.mVersion.equals(((UsbAccessory)object).getVersion()) ? true : bl;
                return bl2;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        String string2 = this.mManufacturer;
        int n = 0;
        int n2 = string2 == null ? 0 : string2.hashCode();
        string2 = this.mModel;
        int n3 = string2 == null ? 0 : string2.hashCode();
        string2 = this.mVersion;
        if (string2 != null) {
            n = string2.hashCode();
        }
        return n2 ^ n3 ^ n;
    }

    public boolean matches(UsbAccessory usbAccessory) {
        String string2 = this.mManufacturer;
        boolean bl = false;
        if (string2 != null && !usbAccessory.getManufacturer().equals(this.mManufacturer)) {
            return false;
        }
        if (this.mModel != null && !usbAccessory.getModel().equals(this.mModel)) {
            return false;
        }
        if (this.mVersion == null || usbAccessory.getVersion().equals(this.mVersion)) {
            bl = true;
        }
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AccessoryFilter[mManufacturer=\"");
        stringBuilder.append(this.mManufacturer);
        stringBuilder.append("\", mModel=\"");
        stringBuilder.append(this.mModel);
        stringBuilder.append("\", mVersion=\"");
        stringBuilder.append(this.mVersion);
        stringBuilder.append("\"]");
        return stringBuilder.toString();
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(null, "usb-accessory");
        String string2 = this.mManufacturer;
        if (string2 != null) {
            xmlSerializer.attribute(null, "manufacturer", string2);
        }
        if ((string2 = this.mModel) != null) {
            xmlSerializer.attribute(null, "model", string2);
        }
        if ((string2 = this.mVersion) != null) {
            xmlSerializer.attribute(null, "version", string2);
        }
        xmlSerializer.endTag(null, "usb-accessory");
    }
}

