/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.hardware.usb;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.util.Slog;
import com.android.internal.util.dump.DualDumpOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class DeviceFilter {
    private static final String TAG = DeviceFilter.class.getSimpleName();
    public final int mClass;
    public final String mManufacturerName;
    public final int mProductId;
    public final String mProductName;
    public final int mProtocol;
    public final String mSerialNumber;
    public final int mSubclass;
    public final int mVendorId;

    public DeviceFilter(int n, int n2, int n3, int n4, int n5, String string2, String string3, String string4) {
        this.mVendorId = n;
        this.mProductId = n2;
        this.mClass = n3;
        this.mSubclass = n4;
        this.mProtocol = n5;
        this.mManufacturerName = string2;
        this.mProductName = string3;
        this.mSerialNumber = string4;
    }

    public DeviceFilter(UsbDevice usbDevice) {
        this.mVendorId = usbDevice.getVendorId();
        this.mProductId = usbDevice.getProductId();
        this.mClass = usbDevice.getDeviceClass();
        this.mSubclass = usbDevice.getDeviceSubclass();
        this.mProtocol = usbDevice.getDeviceProtocol();
        this.mManufacturerName = usbDevice.getManufacturerName();
        this.mProductName = usbDevice.getProductName();
        this.mSerialNumber = usbDevice.getSerialNumber();
    }

    private boolean matches(int n, int n2, int n3) {
        int n4 = this.mClass;
        boolean bl = !(n4 != -1 && n != n4 || (n = this.mSubclass) != -1 && n2 != n || (n = this.mProtocol) != -1 && n3 != n);
        return bl;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static DeviceFilter read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n = xmlPullParser.getAttributeCount();
        int n2 = 0;
        Object object = null;
        Object object2 = null;
        Object object3 = null;
        int n3 = -1;
        int n4 = -1;
        int n5 = -1;
        int n6 = -1;
        int n7 = -1;
        do {
            Object object6;
            Object object5;
            int n9;
            int n8;
            Object object4;
            int n11;
            int n10;
            block20 : {
                object4 = xmlPullParser;
                if (n2 >= n) return new DeviceFilter(n7, n6, n5, n4, n3, (String)object3, (String)object2, (String)object);
                String string2 = object4.getAttributeName(n2);
                object4 = object4.getAttributeValue(n2);
                if ("manufacturer-name".equals(string2)) {
                    n8 = n7;
                    n10 = n6;
                    n9 = n5;
                    n11 = n4;
                    object5 = object2;
                    object6 = object;
                } else if ("product-name".equals(string2)) {
                    object5 = object4;
                    n8 = n7;
                    n10 = n6;
                    n9 = n5;
                    n11 = n4;
                    object4 = object3;
                    object6 = object;
                } else if ("serial-number".equals(string2)) {
                    object6 = object4;
                    n8 = n7;
                    n10 = n6;
                    n9 = n5;
                    n11 = n4;
                    object4 = object3;
                    object5 = object2;
                } else {
                    int n12;
                    if (object4 != null && ((String)object4).length() > 2 && ((String)object4).charAt(0) == '0' && (((String)object4).charAt(1) == 'x' || ((String)object4).charAt(1) == 'X')) {
                        object4 = ((String)object4).substring(2);
                        n12 = 16;
                    } else {
                        n12 = 10;
                    }
                    n12 = Integer.parseInt((String)object4, n12);
                    if ("vendor-id".equals(string2)) {
                        n8 = n12;
                        n10 = n6;
                        n9 = n5;
                        n11 = n4;
                        object4 = object3;
                        object5 = object2;
                        object6 = object;
                    } else if ("product-id".equals(string2)) {
                        n8 = n7;
                        n10 = n12;
                        n9 = n5;
                        n11 = n4;
                        object4 = object3;
                        object5 = object2;
                        object6 = object;
                    } else if ("class".equals(string2)) {
                        n8 = n7;
                        n10 = n6;
                        n9 = n12;
                        n11 = n4;
                        object4 = object3;
                        object5 = object2;
                        object6 = object;
                    } else if ("subclass".equals(string2)) {
                        n8 = n7;
                        n10 = n6;
                        n9 = n5;
                        n11 = n12;
                        object4 = object3;
                        object5 = object2;
                        object6 = object;
                    } else {
                        n8 = n7;
                        n10 = n6;
                        n9 = n5;
                        n11 = n4;
                        object4 = object3;
                        object5 = object2;
                        object6 = object;
                        if ("protocol".equals(string2)) {
                            n8 = n7;
                            n10 = n6;
                            n9 = n5;
                            n11 = n4;
                            n3 = n12;
                            object4 = object3;
                            object5 = object2;
                            object6 = object;
                        }
                    }
                }
                break block20;
                catch (NumberFormatException numberFormatException) {
                    object6 = TAG;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("invalid number for field ");
                    ((StringBuilder)object4).append(string2);
                    Slog.e((String)object6, ((StringBuilder)object4).toString(), numberFormatException);
                    object6 = object;
                    object5 = object2;
                    object4 = object3;
                    n11 = n4;
                    n9 = n5;
                    n10 = n6;
                    n8 = n7;
                }
            }
            ++n2;
            n7 = n8;
            n6 = n10;
            n5 = n9;
            n4 = n11;
            object3 = object4;
            object2 = object5;
            object = object6;
        } while (true);
    }

    public boolean contains(DeviceFilter deviceFilter) {
        int n = this.mVendorId;
        if (n != -1 && deviceFilter.mVendorId != n) {
            return false;
        }
        n = this.mProductId;
        if (n != -1 && deviceFilter.mProductId != n) {
            return false;
        }
        String string2 = this.mManufacturerName;
        if (string2 != null && !Objects.equals(string2, deviceFilter.mManufacturerName)) {
            return false;
        }
        string2 = this.mProductName;
        if (string2 != null && !Objects.equals(string2, deviceFilter.mProductName)) {
            return false;
        }
        string2 = this.mSerialNumber;
        if (string2 != null && !Objects.equals(string2, deviceFilter.mSerialNumber)) {
            return false;
        }
        return this.matches(deviceFilter.mClass, deviceFilter.mSubclass, deviceFilter.mProtocol);
    }

    public void dump(DualDumpOutputStream dualDumpOutputStream, String string2, long l) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("vendor_id", 1120986464257L, this.mVendorId);
        dualDumpOutputStream.write("product_id", 1120986464258L, this.mProductId);
        dualDumpOutputStream.write("class", 1120986464259L, this.mClass);
        dualDumpOutputStream.write("subclass", 1120986464260L, this.mSubclass);
        dualDumpOutputStream.write("protocol", 1120986464261L, this.mProtocol);
        dualDumpOutputStream.write("manufacturer_name", 1138166333446L, this.mManufacturerName);
        dualDumpOutputStream.write("product_name", 1138166333447L, this.mProductName);
        dualDumpOutputStream.write("serial_number", 1138166333448L, this.mSerialNumber);
        dualDumpOutputStream.end(l);
    }

    public boolean equals(Object object) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5 = this.mVendorId;
        if (n5 != -1 && (n2 = this.mProductId) != -1 && (n4 = this.mClass) != -1 && (n = this.mSubclass) != -1 && (n3 = this.mProtocol) != -1) {
            if (object instanceof DeviceFilter) {
                object = (DeviceFilter)object;
                if (((DeviceFilter)object).mVendorId == n5 && ((DeviceFilter)object).mProductId == n2 && ((DeviceFilter)object).mClass == n4 && ((DeviceFilter)object).mSubclass == n && ((DeviceFilter)object).mProtocol == n3) {
                    String string2;
                    if (((DeviceFilter)object).mManufacturerName != null && this.mManufacturerName == null || ((DeviceFilter)object).mManufacturerName == null && this.mManufacturerName != null || ((DeviceFilter)object).mProductName != null && this.mProductName == null || ((DeviceFilter)object).mProductName == null && this.mProductName != null || ((DeviceFilter)object).mSerialNumber != null && this.mSerialNumber == null || ((DeviceFilter)object).mSerialNumber == null && this.mSerialNumber != null) {
                        return false;
                    }
                    String string3 = ((DeviceFilter)object).mManufacturerName;
                    return !(string3 != null && (string2 = this.mManufacturerName) != null && !string2.equals(string3) || (string2 = ((DeviceFilter)object).mProductName) != null && (string3 = this.mProductName) != null && !string3.equals(string2)) && ((string3 = ((DeviceFilter)object).mSerialNumber) == null || (object = this.mSerialNumber) == null || ((String)object).equals(string3));
                }
                return false;
            }
            if (object instanceof UsbDevice) {
                if (((UsbDevice)(object = (UsbDevice)object)).getVendorId() == this.mVendorId && ((UsbDevice)object).getProductId() == this.mProductId && ((UsbDevice)object).getDeviceClass() == this.mClass && ((UsbDevice)object).getDeviceSubclass() == this.mSubclass && ((UsbDevice)object).getDeviceProtocol() == this.mProtocol) {
                    if (this.mManufacturerName != null && ((UsbDevice)object).getManufacturerName() == null || this.mManufacturerName == null && ((UsbDevice)object).getManufacturerName() != null || this.mProductName != null && ((UsbDevice)object).getProductName() == null || this.mProductName == null && ((UsbDevice)object).getProductName() != null || this.mSerialNumber != null && ((UsbDevice)object).getSerialNumber() == null || this.mSerialNumber == null && ((UsbDevice)object).getSerialNumber() != null) {
                        return false;
                    }
                    return !(((UsbDevice)object).getManufacturerName() != null && !this.mManufacturerName.equals(((UsbDevice)object).getManufacturerName()) || ((UsbDevice)object).getProductName() != null && !this.mProductName.equals(((UsbDevice)object).getProductName())) && (((UsbDevice)object).getSerialNumber() == null || this.mSerialNumber.equals(((UsbDevice)object).getSerialNumber()));
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        return (this.mVendorId << 16 | this.mProductId) ^ (this.mClass << 16 | this.mSubclass << 8 | this.mProtocol);
    }

    public boolean matches(UsbDevice usbDevice) {
        if (this.mVendorId != -1 && usbDevice.getVendorId() != this.mVendorId) {
            return false;
        }
        if (this.mProductId != -1 && usbDevice.getProductId() != this.mProductId) {
            return false;
        }
        if (this.mManufacturerName != null && usbDevice.getManufacturerName() == null) {
            return false;
        }
        if (this.mProductName != null && usbDevice.getProductName() == null) {
            return false;
        }
        if (this.mSerialNumber != null && usbDevice.getSerialNumber() == null) {
            return false;
        }
        if (this.mManufacturerName != null && usbDevice.getManufacturerName() != null && !this.mManufacturerName.equals(usbDevice.getManufacturerName())) {
            return false;
        }
        if (this.mProductName != null && usbDevice.getProductName() != null && !this.mProductName.equals(usbDevice.getProductName())) {
            return false;
        }
        if (this.mSerialNumber != null && usbDevice.getSerialNumber() != null && !this.mSerialNumber.equals(usbDevice.getSerialNumber())) {
            return false;
        }
        if (this.matches(usbDevice.getDeviceClass(), usbDevice.getDeviceSubclass(), usbDevice.getDeviceProtocol())) {
            return true;
        }
        int n = usbDevice.getInterfaceCount();
        for (int i = 0; i < n; ++i) {
            UsbInterface usbInterface = usbDevice.getInterface(i);
            if (!this.matches(usbInterface.getInterfaceClass(), usbInterface.getInterfaceSubclass(), usbInterface.getInterfaceProtocol())) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DeviceFilter[mVendorId=");
        stringBuilder.append(this.mVendorId);
        stringBuilder.append(",mProductId=");
        stringBuilder.append(this.mProductId);
        stringBuilder.append(",mClass=");
        stringBuilder.append(this.mClass);
        stringBuilder.append(",mSubclass=");
        stringBuilder.append(this.mSubclass);
        stringBuilder.append(",mProtocol=");
        stringBuilder.append(this.mProtocol);
        stringBuilder.append(",mManufacturerName=");
        stringBuilder.append(this.mManufacturerName);
        stringBuilder.append(",mProductName=");
        stringBuilder.append(this.mProductName);
        stringBuilder.append(",mSerialNumber=");
        stringBuilder.append(this.mSerialNumber);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        String string2;
        xmlSerializer.startTag(null, "usb-device");
        int n = this.mVendorId;
        if (n != -1) {
            xmlSerializer.attribute(null, "vendor-id", Integer.toString(n));
        }
        if ((n = this.mProductId) != -1) {
            xmlSerializer.attribute(null, "product-id", Integer.toString(n));
        }
        if ((n = this.mClass) != -1) {
            xmlSerializer.attribute(null, "class", Integer.toString(n));
        }
        if ((n = this.mSubclass) != -1) {
            xmlSerializer.attribute(null, "subclass", Integer.toString(n));
        }
        if ((n = this.mProtocol) != -1) {
            xmlSerializer.attribute(null, "protocol", Integer.toString(n));
        }
        if ((string2 = this.mManufacturerName) != null) {
            xmlSerializer.attribute(null, "manufacturer-name", string2);
        }
        if ((string2 = this.mProductName) != null) {
            xmlSerializer.attribute(null, "product-name", string2);
        }
        if ((string2 = this.mSerialNumber) != null) {
            xmlSerializer.attribute(null, "serial-number", string2);
        }
        xmlSerializer.endTag(null, "usb-device");
    }
}

