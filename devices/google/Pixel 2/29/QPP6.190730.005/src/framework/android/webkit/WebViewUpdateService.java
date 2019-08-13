/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.RemoteException;
import android.webkit.IWebViewUpdateService;
import android.webkit.WebViewFactory;
import android.webkit.WebViewProviderInfo;

@SystemApi
public final class WebViewUpdateService {
    @UnsupportedAppUsage
    private WebViewUpdateService() {
    }

    public static WebViewProviderInfo[] getAllWebViewPackages() {
        WebViewProviderInfo[] arrwebViewProviderInfo = WebViewUpdateService.getUpdateService();
        if (arrwebViewProviderInfo == null) {
            return new WebViewProviderInfo[0];
        }
        try {
            arrwebViewProviderInfo = arrwebViewProviderInfo.getAllWebViewPackages();
            return arrwebViewProviderInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static String getCurrentWebViewPackageName() {
        Object object = WebViewUpdateService.getUpdateService();
        if (object == null) {
            return null;
        }
        try {
            object = object.getCurrentWebViewPackageName();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private static IWebViewUpdateService getUpdateService() {
        return WebViewFactory.getUpdateService();
    }

    public static WebViewProviderInfo[] getValidWebViewPackages() {
        WebViewProviderInfo[] arrwebViewProviderInfo = WebViewUpdateService.getUpdateService();
        if (arrwebViewProviderInfo == null) {
            return new WebViewProviderInfo[0];
        }
        try {
            arrwebViewProviderInfo = arrwebViewProviderInfo.getValidWebViewPackages();
            return arrwebViewProviderInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

