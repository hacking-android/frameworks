/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.service.autofill;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.metrics.LogMaker;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.io.PrintWriter;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class AutofillServiceInfo {
    private static final String TAG = "AutofillServiceInfo";
    private static final String TAG_AUTOFILL_SERVICE = "autofill-service";
    private static final String TAG_COMPATIBILITY_PACKAGE = "compatibility-package";
    private final ArrayMap<String, Long> mCompatibilityPackages;
    private final ServiceInfo mServiceInfo;
    private final String mSettingsActivity;

    public AutofillServiceInfo(Context context, ComponentName componentName, int n) throws PackageManager.NameNotFoundException {
        this(context, AutofillServiceInfo.getServiceInfoOrThrow(componentName, n));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public AutofillServiceInfo(Context arrayMap, ServiceInfo object) {
        block10 : {
            super();
            if (!"android.permission.BIND_AUTOFILL_SERVICE".equals(object.permission)) {
                if (!"android.permission.BIND_AUTOFILL".equals(object.permission)) {
                    arrayMap = new StringBuilder();
                    arrayMap.append("AutofillService from '");
                    arrayMap.append(object.packageName);
                    arrayMap.append("' does not require permission ");
                    arrayMap.append("android.permission.BIND_AUTOFILL_SERVICE");
                    Log.w("AutofillServiceInfo", arrayMap.toString());
                    throw new SecurityException("Service does not require permission android.permission.BIND_AUTOFILL_SERVICE");
                }
                object2 = new StringBuilder();
                object2.append("AutofillService from '");
                object2.append(object.packageName);
                object2.append("' uses unsupported permission ");
                object2.append("android.permission.BIND_AUTOFILL");
                object2.append(". It works for now, but might not be supported on future releases");
                Log.w("AutofillServiceInfo", object2.toString());
                new MetricsLogger().write(new LogMaker(1289).setPackageName(object.packageName));
            }
            this.mServiceInfo = object;
            xmlResourceParser = object.loadXmlMetaData(arrayMap.getPackageManager(), "android.autofill");
            if (xmlResourceParser == null) {
                this.mSettingsActivity = null;
                this.mCompatibilityPackages = null;
                return;
            }
            arrayMap2 = null;
            var6_8 = null;
            var7_9 = null;
            var8_10 = null;
            object2 = arrayMap2;
            resources = arrayMap.getPackageManager().getResourcesForApplication(object.applicationInfo);
            n = 0;
            while (n != 1 && n != 2) {
                object2 = arrayMap2;
                n = xmlResourceParser.next();
            }
            object2 = arrayMap2;
            if (!"autofill-service".equals(xmlResourceParser.getName())) ** GOTO lbl72
            object2 = arrayMap2;
            object = Xml.asAttributeSet(xmlResourceParser);
            arrayMap = null;
            object = resources.obtainAttributes((AttributeSet)object, R.styleable.AutofillService);
            arrayMap = object;
            object2 = object.getString(0);
            arrayMap = object2;
            object2 = arrayMap;
            {
                catch (Throwable throwable) {
                    if (arrayMap != null) {
                        object2 = arrayMap2;
                        arrayMap.recycle();
                    }
                    object2 = arrayMap2;
                    throw throwable;
                }
            }
            try {
                object.recycle();
                object2 = arrayMap;
                arrayMap2 = this.parseCompatibilityPackages(xmlResourceParser, resources);
                object = arrayMap;
                arrayMap = arrayMap2;
                break block10;
lbl72: // 1 sources:
                object2 = arrayMap2;
                Log.e("AutofillServiceInfo", "Meta-data does not start with autofill-service tag");
                arrayMap = var8_10;
                object = var6_8;
            }
            catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException throwable) {
                Log.e("AutofillServiceInfo", "Error parsing auto fill service meta-data", throwable);
                arrayMap = var7_9;
                object = object2;
            }
        }
        this.mSettingsActivity = object;
        this.mCompatibilityPackages = arrayMap;
    }

    private static ServiceInfo getServiceInfoOrThrow(ComponentName componentName, int n) throws PackageManager.NameNotFoundException {
        try {
            ServiceInfo serviceInfo = AppGlobals.getPackageManager().getServiceInfo(componentName, 128, n);
            if (serviceInfo != null) {
                return serviceInfo;
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw new PackageManager.NameNotFoundException(componentName.toString());
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ArrayMap<String, Long> parseCompatibilityPackages(XmlPullParser xmlPullParser, Resources object) throws IOException, XmlPullParserException {
        int n;
        int n2 = xmlPullParser.getDepth();
        ArrayMap<String, Object> arrayMap = null;
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
            void var2_6;
            Object object2;
            block34 : {
                Object object4;
                Object object3;
                ArrayMap<String, Object> arrayMap2;
                String string2;
                block33 : {
                    block32 : {
                        block31 : {
                            if (n == 3 || n == 4 || !TAG_COMPATIBILITY_PACKAGE.equals(xmlPullParser.getName())) continue;
                            object2 = null;
                            object3 = null;
                            arrayMap2 = Xml.asAttributeSet(xmlPullParser);
                            object4 = R.styleable.AutofillService_CompatibilityPackage;
                            object2 = object3;
                            object2 = object4 = ((Resources)object).obtainAttributes((AttributeSet)((Object)arrayMap2), (int[])object4);
                            {
                                catch (Throwable throwable) {}
                            }
                            string2 = ((TypedArray)object4).getString(0);
                            object2 = object4;
                            boolean bl = TextUtils.isEmpty(string2);
                            if (!bl) break block31;
                            object2 = object4;
                            object2 = object4;
                            object = new StringBuilder();
                            object2 = object4;
                            ((StringBuilder)object).append("Invalid compatibility package:");
                            object2 = object4;
                            ((StringBuilder)object).append(string2);
                            object2 = object4;
                            Log.e(TAG, ((StringBuilder)object).toString());
                            XmlUtils.skipCurrentTag(xmlPullParser);
                            ((TypedArray)object4).recycle();
                            return arrayMap;
                        }
                        object2 = object4;
                        object3 = ((TypedArray)object4).getString(1);
                        if (object3 == null) break block32;
                        object2 = object4;
                        arrayMap2 = Long.parseLong((String)object3);
                        object3 = arrayMap2;
                        object2 = object4;
                        if ((Long)((Object)arrayMap2) >= 0L) break block33;
                        object2 = object4;
                        object2 = object4;
                        object = new StringBuilder();
                        object2 = object4;
                        ((StringBuilder)object).append("Invalid compatibility max version code:");
                        object2 = object4;
                        ((StringBuilder)object).append(arrayMap2);
                        object2 = object4;
                        Log.e(TAG, ((StringBuilder)object).toString());
                        XmlUtils.skipCurrentTag(xmlPullParser);
                        ((TypedArray)object4).recycle();
                        return arrayMap;
                        catch (NumberFormatException numberFormatException) {
                            object2 = object4;
                            object2 = object4;
                            object = new StringBuilder();
                            object2 = object4;
                            ((StringBuilder)object).append("Invalid compatibility max version code:");
                            object2 = object4;
                            ((StringBuilder)object).append((String)object3);
                            object2 = object4;
                            Log.e(TAG, ((StringBuilder)object).toString());
                            XmlUtils.skipCurrentTag(xmlPullParser);
                            ((TypedArray)object4).recycle();
                            return arrayMap;
                        }
                    }
                    object2 = object4;
                    object3 = Long.MAX_VALUE;
                }
                arrayMap2 = arrayMap;
                if (arrayMap == null) {
                    object2 = object4;
                    object2 = object4;
                    arrayMap2 = new ArrayMap<String, Object>();
                }
                object2 = object4;
                arrayMap2.put(string2, object3);
                XmlUtils.skipCurrentTag(xmlPullParser);
                ((TypedArray)object4).recycle();
                arrayMap = arrayMap2;
                continue;
                break block34;
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            XmlUtils.skipCurrentTag(xmlPullParser);
            if (object2 == null) throw var2_6;
            ((TypedArray)object2).recycle();
            throw var2_6;
        }
        return arrayMap;
    }

    public void dump(String string2, PrintWriter printWriter) {
        printWriter.print(string2);
        printWriter.print("Component: ");
        printWriter.println(this.getServiceInfo().getComponentName());
        printWriter.print(string2);
        printWriter.print("Settings: ");
        printWriter.println(this.mSettingsActivity);
        printWriter.print(string2);
        printWriter.print("Compat packages: ");
        printWriter.println(this.mCompatibilityPackages);
    }

    public ArrayMap<String, Long> getCompatibilityPackages() {
        return this.mCompatibilityPackages;
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
        stringBuilder.append(", hasCompatPckgs:");
        ArrayMap<String, Long> arrayMap = this.mCompatibilityPackages;
        boolean bl = arrayMap != null && !arrayMap.isEmpty();
        stringBuilder.append(bl);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

