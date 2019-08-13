/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.usb;

import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import com.android.internal.util.dump.DualDumpOutputStream;

public class DumpUtils {
    public static void writeAccessory(DualDumpOutputStream dualDumpOutputStream, String string2, long l, UsbAccessory usbAccessory) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("manufacturer", 1138166333441L, usbAccessory.getManufacturer());
        dualDumpOutputStream.write("model", 1138166333442L, usbAccessory.getModel());
        com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "description", 1138166333443L, usbAccessory.getManufacturer());
        dualDumpOutputStream.write("version", 1138166333444L, usbAccessory.getVersion());
        com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "uri", 1138166333445L, usbAccessory.getUri());
        dualDumpOutputStream.write("serial", 1138166333446L, usbAccessory.getSerial());
        dualDumpOutputStream.end(l);
    }

    private static void writeConfiguration(DualDumpOutputStream dualDumpOutputStream, String string2, long l, UsbConfiguration usbConfiguration) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("id", 1120986464257L, usbConfiguration.getId());
        dualDumpOutputStream.write("name", 1138166333442L, usbConfiguration.getName());
        dualDumpOutputStream.write("attributes", 1155346202627L, usbConfiguration.getAttributes());
        dualDumpOutputStream.write("max_power", 1120986464260L, usbConfiguration.getMaxPower());
        int n = usbConfiguration.getInterfaceCount();
        for (int i = 0; i < n; ++i) {
            DumpUtils.writeInterface(dualDumpOutputStream, "interfaces", 2246267895813L, usbConfiguration.getInterface(i));
        }
        dualDumpOutputStream.end(l);
    }

    private static void writeContaminantPresenceStatus(DualDumpOutputStream dualDumpOutputStream, String string2, long l, int n) {
        if (dualDumpOutputStream.isProto()) {
            dualDumpOutputStream.write(string2, l, n);
        } else {
            dualDumpOutputStream.write(string2, l, UsbPort.contaminantPresenceStatusToString(n));
        }
    }

    private static void writeDataRole(DualDumpOutputStream dualDumpOutputStream, String string2, long l, int n) {
        if (dualDumpOutputStream.isProto()) {
            dualDumpOutputStream.write(string2, l, n);
        } else {
            dualDumpOutputStream.write(string2, l, UsbPort.dataRoleToString(n));
        }
    }

    public static void writeDevice(DualDumpOutputStream dualDumpOutputStream, String string2, long l, UsbDevice usbDevice) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("name", 1138166333441L, usbDevice.getDeviceName());
        dualDumpOutputStream.write("vendor_id", 1120986464258L, usbDevice.getVendorId());
        dualDumpOutputStream.write("product_id", 1120986464259L, usbDevice.getProductId());
        dualDumpOutputStream.write("class", 1120986464260L, usbDevice.getDeviceClass());
        dualDumpOutputStream.write("subclass", 1120986464261L, usbDevice.getDeviceSubclass());
        dualDumpOutputStream.write("protocol", 1120986464262L, usbDevice.getDeviceProtocol());
        dualDumpOutputStream.write("manufacturer_name", 1138166333447L, usbDevice.getManufacturerName());
        dualDumpOutputStream.write("product_name", 1138166333448L, usbDevice.getProductName());
        dualDumpOutputStream.write("version", 1138166333449L, usbDevice.getVersion());
        dualDumpOutputStream.write("serial_number", 1138166333450L, usbDevice.getSerialNumber());
        int n = usbDevice.getConfigurationCount();
        for (int i = 0; i < n; ++i) {
            DumpUtils.writeConfiguration(dualDumpOutputStream, "configurations", 2246267895819L, usbDevice.getConfiguration(i));
        }
        dualDumpOutputStream.end(l);
    }

    private static void writeEndpoint(DualDumpOutputStream dualDumpOutputStream, String string2, long l, UsbEndpoint usbEndpoint) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("endpoint_number", 1120986464257L, usbEndpoint.getEndpointNumber());
        dualDumpOutputStream.write("direction", 1159641169922L, usbEndpoint.getDirection());
        dualDumpOutputStream.write("address", 1120986464259L, usbEndpoint.getAddress());
        dualDumpOutputStream.write("type", 1159641169924L, usbEndpoint.getType());
        dualDumpOutputStream.write("attributes", 1155346202629L, usbEndpoint.getAttributes());
        dualDumpOutputStream.write("max_packet_size", 1120986464262L, usbEndpoint.getMaxPacketSize());
        dualDumpOutputStream.write("interval", 1120986464263L, usbEndpoint.getInterval());
        dualDumpOutputStream.end(l);
    }

    private static void writeInterface(DualDumpOutputStream dualDumpOutputStream, String string2, long l, UsbInterface usbInterface) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("id", 1120986464257L, usbInterface.getId());
        dualDumpOutputStream.write("alternate_settings", 1120986464258L, usbInterface.getAlternateSetting());
        dualDumpOutputStream.write("name", 1138166333443L, usbInterface.getName());
        dualDumpOutputStream.write("class", 1120986464260L, usbInterface.getInterfaceClass());
        dualDumpOutputStream.write("subclass", 1120986464261L, usbInterface.getInterfaceSubclass());
        dualDumpOutputStream.write("protocol", 1120986464262L, usbInterface.getInterfaceProtocol());
        int n = usbInterface.getEndpointCount();
        for (int i = 0; i < n; ++i) {
            DumpUtils.writeEndpoint(dualDumpOutputStream, "endpoints", 2246267895815L, usbInterface.getEndpoint(i));
        }
        dualDumpOutputStream.end(l);
    }

    public static void writePort(DualDumpOutputStream dualDumpOutputStream, String string2, long l, UsbPort usbPort) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("id", 1138166333441L, usbPort.getId());
        int n = usbPort.getSupportedModes();
        if (dualDumpOutputStream.isProto()) {
            if (n == 0) {
                dualDumpOutputStream.write("supported_modes", 2259152797698L, 0);
            } else {
                if ((n & 3) == 3) {
                    dualDumpOutputStream.write("supported_modes", 2259152797698L, 3);
                } else if ((n & 2) == 2) {
                    dualDumpOutputStream.write("supported_modes", 2259152797698L, 2);
                } else if ((n & 1) == 1) {
                    dualDumpOutputStream.write("supported_modes", 2259152797698L, 1);
                }
                if ((n & 4) == 4) {
                    dualDumpOutputStream.write("supported_modes", 2259152797698L, 4);
                }
                if ((n & 8) == 8) {
                    dualDumpOutputStream.write("supported_modes", 2259152797698L, 8);
                }
            }
        } else {
            dualDumpOutputStream.write("supported_modes", 2259152797698L, UsbPort.modeToString(n));
        }
        dualDumpOutputStream.end(l);
    }

    public static void writePortStatus(DualDumpOutputStream dualDumpOutputStream, String string2, long l, UsbPortStatus usbPortStatus) {
        int n;
        long l2 = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("connected", 1133871366145L, usbPortStatus.isConnected());
        if (dualDumpOutputStream.isProto()) {
            dualDumpOutputStream.write("current_mode", 1159641169922L, usbPortStatus.getCurrentMode());
        } else {
            dualDumpOutputStream.write("current_mode", 1159641169922L, UsbPort.modeToString(usbPortStatus.getCurrentMode()));
        }
        DumpUtils.writePowerRole(dualDumpOutputStream, "power_role", 1159641169923L, usbPortStatus.getCurrentPowerRole());
        DumpUtils.writeDataRole(dualDumpOutputStream, "data_role", 1159641169924L, usbPortStatus.getCurrentDataRole());
        for (int i = usbPortStatus.getSupportedRoleCombinations(); i != 0; i &= 1 << n) {
            n = Integer.numberOfTrailingZeros(i);
            int n2 = n / 3;
            l = dualDumpOutputStream.start("role_combinations", 2246267895813L);
            DumpUtils.writePowerRole(dualDumpOutputStream, "power_role", 1159641169921L, n2 + 0);
            DumpUtils.writeDataRole(dualDumpOutputStream, "data_role", 1159641169922L, n % 3);
            dualDumpOutputStream.end(l);
        }
        DumpUtils.writeContaminantPresenceStatus(dualDumpOutputStream, "contaminant_presence_status", 1159641169926L, usbPortStatus.getContaminantDetectionStatus());
        dualDumpOutputStream.end(l2);
    }

    private static void writePowerRole(DualDumpOutputStream dualDumpOutputStream, String string2, long l, int n) {
        if (dualDumpOutputStream.isProto()) {
            dualDumpOutputStream.write(string2, l, n);
        } else {
            dualDumpOutputStream.write(string2, l, UsbPort.powerRoleToString(n));
        }
    }
}

