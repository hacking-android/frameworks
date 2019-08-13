/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.service.contentcapture;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.io.PrintWriter;
import org.xmlpull.v1.XmlPullParserException;

public final class ContentCaptureServiceInfo {
    private static final String TAG = ContentCaptureServiceInfo.class.getSimpleName();
    private static final String XML_TAG_SERVICE = "content-capture-service";
    private final ServiceInfo mServiceInfo;
    private final String mSettingsActivity;

    public ContentCaptureServiceInfo(Context context, ComponentName componentName, boolean bl, int n) throws PackageManager.NameNotFoundException {
        this(context, ContentCaptureServiceInfo.getServiceInfoOrThrow(componentName, bl, n));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private ContentCaptureServiceInfo(Context object, ServiceInfo object2) {
        block9 : {
            super();
            if (!"android.permission.BIND_CONTENT_CAPTURE_SERVICE".equals(object2.permission)) {
                object = ContentCaptureServiceInfo.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("ContentCaptureService from '");
                stringBuilder.append(object2.packageName);
                stringBuilder.append("' does not require permission ");
                stringBuilder.append("android.permission.BIND_CONTENT_CAPTURE_SERVICE");
                Slog.w((String)object, stringBuilder.toString());
                throw new SecurityException("Service does not require permission android.permission.BIND_CONTENT_CAPTURE_SERVICE");
            }
            this.mServiceInfo = object2;
            xmlResourceParser = object2.loadXmlMetaData(object.getPackageManager(), "android.content_capture");
            if (xmlResourceParser == null) {
                this.mSettingsActivity = null;
                return;
            }
            string2 = null;
            attributeSet = null;
            object3 = string2;
            object2 = object.getPackageManager().getResourcesForApplication(object2.applicationInfo);
            n = 0;
            while (n != 1 && n != 2) {
                object3 = string2;
                n = xmlResourceParser.next();
            }
            object3 = string2;
            if (!"content-capture-service".equals(xmlResourceParser.getName())) ** GOTO lbl48
            object3 = string2;
            attributeSet = Xml.asAttributeSet(xmlResourceParser);
            object = null;
            object = object2 = object2.obtainAttributes(attributeSet, R.styleable.ContentCaptureService);
            object3 = object = (object3 = object2.getString(0));
            {
                catch (Throwable throwable) {
                    if (object != null) {
                        object3 = string2;
                        object.recycle();
                    }
                    object3 = string2;
                    throw throwable;
                }
            }
            try {
                object2.recycle();
                break block9;
lbl48: // 1 sources:
                object3 = string2;
                Log.e(ContentCaptureServiceInfo.TAG, "Meta-data does not start with content-capture-service tag");
                object = attributeSet;
            }
            catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException throwable) {
                Log.e(ContentCaptureServiceInfo.TAG, "Error parsing auto fill service meta-data", throwable);
                object = object3;
            }
        }
        this.mSettingsActivity = object;
    }

    private static ServiceInfo getServiceInfoOrThrow(ComponentName componentName, boolean bl, int n) throws PackageManager.NameNotFoundException {
        Object object;
        int n2 = 128;
        if (!bl) {
            n2 = 128 | 1048576;
        }
        Object object2 = null;
        try {
            object2 = object = AppGlobals.getPackageManager().getServiceInfo(componentName, n2, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        if (object2 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not get serviceInfo for ");
            object2 = bl ? " (temp)" : "(default system)";
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(componentName.flattenToShortString());
            throw new PackageManager.NameNotFoundException(((StringBuilder)object).toString());
        }
        return object2;
    }

    public void dump(String string2, PrintWriter printWriter) {
        printWriter.print(string2);
        printWriter.print("Component: ");
        printWriter.println(this.getServiceInfo().getComponentName());
        printWriter.print(string2);
        printWriter.print("Settings: ");
        printWriter.println(this.mSettingsActivity);
    }

    public ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public String getSettingsActivity() {
        return this.mSettingsActivity;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("[");
        stringBuilder.append(this.mServiceInfo);
        stringBuilder.append(", settings:");
        stringBuilder.append(this.mSettingsActivity);
        return stringBuilder.toString();
    }
}

