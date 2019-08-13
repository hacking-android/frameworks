/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.util.AndroidException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CameraAccessException
extends AndroidException {
    public static final int CAMERA_DEPRECATED_HAL = 1000;
    public static final int CAMERA_DISABLED = 1;
    public static final int CAMERA_DISCONNECTED = 2;
    public static final int CAMERA_ERROR = 3;
    public static final int CAMERA_IN_USE = 4;
    public static final int MAX_CAMERAS_IN_USE = 5;
    private static final long serialVersionUID = 5630338637471475675L;
    private final int mReason;

    public CameraAccessException(int n) {
        super(CameraAccessException.getDefaultMessage(n));
        this.mReason = n;
    }

    public CameraAccessException(int n, String string2) {
        super(CameraAccessException.getCombinedMessage(n, string2));
        this.mReason = n;
    }

    public CameraAccessException(int n, String string2, Throwable throwable) {
        super(CameraAccessException.getCombinedMessage(n, string2), throwable);
        this.mReason = n;
    }

    public CameraAccessException(int n, Throwable throwable) {
        super(CameraAccessException.getDefaultMessage(n), throwable);
        this.mReason = n;
    }

    private static String getCombinedMessage(int n, String string2) {
        return String.format("%s (%d): %s", CameraAccessException.getProblemString(n), n, string2);
    }

    public static String getDefaultMessage(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return null;
                        }
                        return "The system-wide limit for number of open cameras has been reached, and more camera devices cannot be opened until previous instances are closed.";
                    }
                    return "The camera device is in use already";
                }
                return "The camera device is currently in the error state; no further calls to it will succeed.";
            }
            return "The camera device is removable and has been disconnected from the Android device, or the camera service has shut down the connection due to a higher-priority access request for the camera device.";
        }
        return "The camera is disabled due to a device policy, and cannot be opened.";
    }

    private static String getProblemString(int n) {
        String string2 = n != 1 ? (n != 2 ? (n != 3 ? (n != 4 ? (n != 5 ? (n != 1000 ? "<UNKNOWN ERROR>" : "CAMERA_DEPRECATED_HAL") : "MAX_CAMERAS_IN_USE") : "CAMERA_IN_USE") : "CAMERA_ERROR") : "CAMERA_DISCONNECTED") : "CAMERA_DISABLED";
        return string2;
    }

    public final int getReason() {
        return this.mReason;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AccessError {
    }

}

