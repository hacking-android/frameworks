/*
 * Decompiled with CFR 0.145.
 */
package android.app.timedetector;

import android.app.timedetector.ITimeDetectorService;
import android.app.timedetector.TimeSignal;
import android.os.RemoteException;
import android.os.ServiceManager;

public final class TimeDetector {
    private static final boolean DEBUG = false;
    private static final String TAG = "timedetector.TimeDetector";
    private final ITimeDetectorService mITimeDetectorService = ITimeDetectorService.Stub.asInterface(ServiceManager.getServiceOrThrow("time_detector"));

    public void suggestTime(TimeSignal timeSignal) {
        try {
            this.mITimeDetectorService.suggestTime(timeSignal);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

