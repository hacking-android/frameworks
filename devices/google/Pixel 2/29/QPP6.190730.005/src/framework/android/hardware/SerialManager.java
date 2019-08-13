/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.hardware.ISerialManager;
import android.hardware.SerialPort;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.io.IOException;

public class SerialManager {
    private static final String TAG = "SerialManager";
    private final Context mContext;
    private final ISerialManager mService;

    public SerialManager(Context context, ISerialManager iSerialManager) {
        this.mContext = context;
        this.mService = iSerialManager;
    }

    @UnsupportedAppUsage
    public String[] getSerialPorts() {
        try {
            String[] arrstring = this.mService.getSerialPorts();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public SerialPort openSerialPort(String string2, int n) throws IOException {
        Object object;
        block4 : {
            try {
                object = this.mService.openSerialPort(string2);
                if (object == null) break block4;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            SerialPort serialPort = new SerialPort(string2);
            serialPort.open((ParcelFileDescriptor)object, n);
            return serialPort;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not open serial port ");
        stringBuilder.append(string2);
        object = new IOException(stringBuilder.toString());
        throw object;
    }
}

