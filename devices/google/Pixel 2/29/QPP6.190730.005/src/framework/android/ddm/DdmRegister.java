/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.harmony.dalvik.ddmc.DdmServer
 */
package android.ddm;

import android.ddm.DdmHandleExit;
import android.ddm.DdmHandleHeap;
import android.ddm.DdmHandleHello;
import android.ddm.DdmHandleNativeHeap;
import android.ddm.DdmHandleProfiling;
import android.ddm.DdmHandleThread;
import android.ddm.DdmHandleViewDebug;
import org.apache.harmony.dalvik.ddmc.DdmServer;

public class DdmRegister {
    private DdmRegister() {
    }

    public static void registerHandlers() {
        DdmHandleHello.register();
        DdmHandleThread.register();
        DdmHandleHeap.register();
        DdmHandleNativeHeap.register();
        DdmHandleProfiling.register();
        DdmHandleExit.register();
        DdmHandleViewDebug.register();
        DdmServer.registrationComplete();
    }
}

