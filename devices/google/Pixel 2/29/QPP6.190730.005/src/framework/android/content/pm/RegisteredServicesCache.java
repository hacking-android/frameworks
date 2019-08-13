/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.RegisteredServicesCacheListener;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.pm.XmlSerializerAndParser;
import android.content.pm._$$Lambda$RegisteredServicesCache$lDXmLhKoG7lZpIyDOuPYOrjzDYY;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.AtomicFile;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastXmlSerializer;
import com.google.android.collect.Maps;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public abstract class RegisteredServicesCache<V> {
    private static final boolean DEBUG = false;
    protected static final String REGISTERED_SERVICES_DIR = "registered_services";
    private static final String TAG = "PackageManager";
    private final String mAttributesName;
    public final Context mContext;
    private final BroadcastReceiver mExternalReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            RegisteredServicesCache.this.handlePackageEvent(intent, 0);
        }
    };
    private Handler mHandler;
    private final String mInterfaceName;
    private RegisteredServicesCacheListener<V> mListener;
    private final String mMetaDataName;
    private final BroadcastReceiver mPackageReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            int n = intent.getIntExtra("android.intent.extra.UID", -1);
            if (n != -1) {
                RegisteredServicesCache.this.handlePackageEvent(intent, UserHandle.getUserId(n));
            }
        }
    };
    private final XmlSerializerAndParser<V> mSerializerAndParser;
    protected final Object mServicesLock = new Object();
    private final BroadcastReceiver mUserRemovedReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            int n = intent.getIntExtra("android.intent.extra.user_handle", -1);
            RegisteredServicesCache.this.onUserRemoved(n);
        }
    };
    @GuardedBy(value={"mServicesLock"})
    private final SparseArray<UserServices<V>> mUserServices = new SparseArray(2);

    @UnsupportedAppUsage
    public RegisteredServicesCache(Context object, String object2, String string2, String string3, XmlSerializerAndParser<V> xmlSerializerAndParser) {
        this.mContext = object;
        this.mInterfaceName = object2;
        this.mMetaDataName = string2;
        this.mAttributesName = string3;
        this.mSerializerAndParser = xmlSerializerAndParser;
        this.migrateIfNecessaryLocked();
        object = new IntentFilter();
        ((IntentFilter)object).addAction("android.intent.action.PACKAGE_ADDED");
        ((IntentFilter)object).addAction("android.intent.action.PACKAGE_CHANGED");
        ((IntentFilter)object).addAction("android.intent.action.PACKAGE_REMOVED");
        ((IntentFilter)object).addDataScheme("package");
        this.mContext.registerReceiverAsUser(this.mPackageReceiver, UserHandle.ALL, (IntentFilter)object, null, null);
        object = new IntentFilter();
        ((IntentFilter)object).addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        ((IntentFilter)object).addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        this.mContext.registerReceiver(this.mExternalReceiver, (IntentFilter)object);
        object2 = new IntentFilter();
        ((IntentFilter)object).addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiver(this.mUserRemovedReceiver, (IntentFilter)object2);
    }

    private boolean containsType(ArrayList<ServiceInfo<V>> arrayList, V v) {
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            if (!arrayList.get((int)i).type.equals(v)) continue;
            return true;
        }
        return false;
    }

    private boolean containsTypeAndUid(ArrayList<ServiceInfo<V>> arrayList, V v, int n) {
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            ServiceInfo<V> serviceInfo = arrayList.get(i);
            if (!serviceInfo.type.equals(v) || serviceInfo.uid != n) continue;
            return true;
        }
        return false;
    }

    private boolean containsUid(int[] arrn, int n) {
        boolean bl = arrn == null || ArrayUtils.contains(arrn, n);
        return bl;
    }

    private AtomicFile createFileForUser(int n) {
        File file = this.getUserSystemDirectory(n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("registered_services/");
        stringBuilder.append(this.mInterfaceName);
        stringBuilder.append(".xml");
        return new AtomicFile(new File(file, stringBuilder.toString()));
    }

    @GuardedBy(value={"mServicesLock"})
    private UserServices<V> findOrCreateUserLocked(int n) {
        return this.findOrCreateUserLocked(n, true);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @GuardedBy(value={"mServicesLock"})
    private UserServices<V> findOrCreateUserLocked(int var1_1, boolean var2_2) {
        var4_4 = var3_3 = this.mUserServices.get(var1_1);
        if (var3_3 != null) return var4_4;
        var5_6 = new UserServices<V>();
        this.mUserServices.put(var1_1, var5_6);
        var4_4 = var5_6;
        if (var2_2 == false) return var4_4;
        var4_4 = var5_6;
        if (this.mSerializerAndParser == null) return var4_4;
        var6_7 = this.getUser(var1_1);
        var4_4 = var5_6;
        if (var6_7 == null) return var4_4;
        var7_8 = this.createFileForUser(var6_7.id);
        var4_4 = var5_6;
        if (var7_8.getBaseFile().exists() == false) return var4_4;
        var4_4 = null;
        var3_3 = null;
        var7_8 = var7_8.openRead();
        var3_3 = var7_8;
        var4_4 = var7_8;
        this.readPersistentServicesLocked((InputStream)var7_8);
        var4_4 = var7_8;
lbl23: // 2 sources:
        do {
            IoUtils.closeQuietly((AutoCloseable)var4_4);
            return var5_6;
            break;
        } while (true);
        {
            catch (Throwable var4_5) {
            }
            catch (Exception var8_9) {}
            var3_3 = var4_4;
            {
                var3_3 = var4_4;
                var7_8 = new StringBuilder();
                var3_3 = var4_4;
                var7_8.append("Error reading persistent services for user ");
                var3_3 = var4_4;
                var7_8.append(var6_7.id);
                var3_3 = var4_4;
                Log.w("PackageManager", var7_8.toString(), var8_9);
                ** continue;
            }
        }
        IoUtils.closeQuietly(var3_3);
        throw var4_5;
    }

    /*
     * Exception decompiling
     */
    private void generateServicesMap(int[] var1_1, int var2_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [8[WHILELOOP]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void handlePackageEvent(Intent arrn, int n) {
        String string2 = arrn.getAction();
        int n2 = !"android.intent.action.PACKAGE_REMOVED".equals(string2) && !"android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(string2) ? 0 : 1;
        boolean bl = arrn.getBooleanExtra("android.intent.extra.REPLACING", false);
        if (n2 == 0 || !bl) {
            Object var6_6 = null;
            if (!"android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(string2) && !"android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(string2)) {
                n2 = arrn.getIntExtra("android.intent.extra.UID", -1);
                arrn = var6_6;
                if (n2 > 0) {
                    arrn = new int[]{n2};
                }
            } else {
                arrn = arrn.getIntArrayExtra("android.intent.extra.changed_uid_list");
            }
            this.generateServicesMap(arrn, n);
        }
    }

    static /* synthetic */ void lambda$notifyListener$0(RegisteredServicesCacheListener registeredServicesCacheListener, Object object, int n, boolean bl) {
        try {
            registeredServicesCacheListener.onServiceChanged(object, n, bl);
        }
        catch (Throwable throwable) {
            Slog.wtf(TAG, "Exception from onServiceChanged", throwable);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void migrateIfNecessaryLocked() {
        if (this.mSerializerAndParser == null) {
            return;
        }
        var1_1 = new File(new File(this.getDataDirectory(), "system"), "registered_services");
        var2_4 = new StringBuilder();
        var2_4.append(this.mInterfaceName);
        var2_4.append(".xml");
        var3_5 = new AtomicFile(new File((File)var1_1, var2_4.toString()));
        if (var3_5.getBaseFile().exists() == false) return;
        var2_4 = new StringBuilder();
        var2_4.append(this.mInterfaceName);
        var2_4.append(".xml.migrated");
        var4_7 = new File((File)var1_1, var2_4.toString());
        if (var4_7.exists() != false) return;
        var1_1 = null;
        var2_4 = null;
        var3_5 = var3_5.openRead();
        var2_4 = var3_5;
        var1_1 = var3_5;
        this.mUserServices.clear();
        var2_4 = var3_5;
        var1_1 = var3_5;
        this.readPersistentServicesLocked((InputStream)var3_5);
        var1_1 = var3_5;
lbl29: // 2 sources:
        do {
            IoUtils.closeQuietly((AutoCloseable)var1_1);
            try {
                for (Object var3_5 : this.getUsers()) {
                    var1_1 = this.mUserServices.get(var3_5.id);
                    if (var1_1 == null) continue;
                    this.writePersistentServicesLocked(var1_1, var3_5.id);
                }
                var4_7.createNewFile();
            }
            catch (Exception var1_3) {
                Log.w("PackageManager", "Migration failed", var1_3);
            }
            this.mUserServices.clear();
            return;
            break;
        } while (true);
        {
            catch (Throwable var1_2) {
            }
            catch (Exception var3_6) {}
            var2_4 = var1_1;
            {
                Log.w("PackageManager", "Error reading persistent services, starting from scratch", var3_6);
                ** continue;
            }
        }
        IoUtils.closeQuietly((AutoCloseable)var2_4);
        throw var1_2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void notifyListener(V v, int n, boolean bl) {
        // MONITORENTER : this
        RegisteredServicesCacheListener<V> registeredServicesCacheListener = this.mListener;
        Handler handler = this.mHandler;
        // MONITOREXIT : this
        if (registeredServicesCacheListener == null) {
            return;
        }
        handler.post(new _$$Lambda$RegisteredServicesCache$lDXmLhKoG7lZpIyDOuPYOrjzDYY(registeredServicesCacheListener, v, n, bl));
    }

    private void readPersistentServicesLocked(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        int n = xmlPullParser.getEventType();
        while (n != 2 && n != 1) {
            n = xmlPullParser.next();
        }
        if ("services".equals(xmlPullParser.getName())) {
            int n2;
            n = xmlPullParser.next();
            do {
                if (n == 2 && xmlPullParser.getDepth() == 2 && "service".equals(xmlPullParser.getName())) {
                    inputStream = this.mSerializerAndParser.createFromXml(xmlPullParser);
                    if (inputStream == null) break;
                    n = Integer.parseInt(xmlPullParser.getAttributeValue(null, "uid"));
                    this.findOrCreateUserLocked((int)UserHandle.getUserId((int)n), (boolean)false).persistentServices.put((Integer)((Object)inputStream), n);
                }
                n = n2 = xmlPullParser.next();
            } while (n2 != 1);
        }
    }

    private void writePersistentServicesLocked(UserServices<V> object, int n) {
        block20 : {
            FileOutputStream fileOutputStream;
            if (this.mSerializerAndParser == null) {
                return;
            }
            AtomicFile atomicFile = this.createFileForUser(n);
            FileOutputStream fileOutputStream2 = null;
            fileOutputStream2 = fileOutputStream = atomicFile.startWrite();
            fileOutputStream2 = fileOutputStream;
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fileOutputStream2 = fileOutputStream;
            fastXmlSerializer.setOutput((OutputStream)fileOutputStream, StandardCharsets.UTF_8.name());
            fileOutputStream2 = fileOutputStream;
            fastXmlSerializer.startDocument(null, Boolean.valueOf(true));
            fileOutputStream2 = fileOutputStream;
            fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            fileOutputStream2 = fileOutputStream;
            fastXmlSerializer.startTag(null, "services");
            fileOutputStream2 = fileOutputStream;
            object = ((UserServices)object).persistentServices.entrySet().iterator();
            do {
                fileOutputStream2 = fileOutputStream;
                if (!object.hasNext()) break;
                fileOutputStream2 = fileOutputStream;
                Map.Entry entry = (Map.Entry)object.next();
                fileOutputStream2 = fileOutputStream;
                fastXmlSerializer.startTag(null, "service");
                fileOutputStream2 = fileOutputStream;
                fastXmlSerializer.attribute(null, "uid", Integer.toString((Integer)entry.getValue()));
                fileOutputStream2 = fileOutputStream;
                this.mSerializerAndParser.writeAsXml(entry.getKey(), fastXmlSerializer);
                fileOutputStream2 = fileOutputStream;
                fastXmlSerializer.endTag(null, "service");
                continue;
                break;
            } while (true);
            fileOutputStream2 = fileOutputStream;
            fastXmlSerializer.endTag(null, "services");
            fileOutputStream2 = fileOutputStream;
            fastXmlSerializer.endDocument();
            fileOutputStream2 = fileOutputStream;
            try {
                atomicFile.finishWrite(fileOutputStream);
            }
            catch (IOException iOException) {
                Log.w(TAG, "Error writing accounts", iOException);
                if (fileOutputStream2 == null) break block20;
                atomicFile.failWrite(fileOutputStream2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] object2, int n) {
        object = this.mServicesLock;
        synchronized (object) {
            UserServices<V> userServices = this.findOrCreateUserLocked(n);
            if (userServices.services != null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("RegisteredServicesCache: ");
                ((StringBuilder)object2).append(userServices.services.size());
                ((StringBuilder)object2).append(" services");
                printWriter.println(((StringBuilder)object2).toString());
                for (ServiceInfo serviceInfo : userServices.services.values()) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("  ");
                    ((StringBuilder)object2).append(serviceInfo);
                    printWriter.println(((StringBuilder)object2).toString());
                }
            } else {
                printWriter.println("RegisteredServicesCache: services not loaded");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Collection<ServiceInfo<V>> getAllServices(int n) {
        Object object = this.mServicesLock;
        synchronized (object) {
            UserServices<V> userServices = this.findOrCreateUserLocked(n);
            if (userServices.services == null) {
                this.generateServicesMap(null, n);
            }
            Collection collection = new Collection(userServices.services.values());
            return Collections.unmodifiableCollection(collection);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getBindInstantServiceAllowed(int n) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIND_INSTANT_SERVICE", "getBindInstantServiceAllowed");
        Object object = this.mServicesLock;
        synchronized (object) {
            return this.findOrCreateUserLocked((int)n).mBindInstantServiceAllowed;
        }
    }

    @VisibleForTesting
    protected File getDataDirectory() {
        return Environment.getDataDirectory();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public RegisteredServicesCacheListener<V> getListener() {
        synchronized (this) {
            return this.mListener;
        }
    }

    @VisibleForTesting
    protected Map<V, Integer> getPersistentServices(int n) {
        return this.findOrCreateUserLocked((int)n).persistentServices;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ServiceInfo<V> getServiceInfo(V object, int n) {
        Object object2 = this.mServicesLock;
        synchronized (object2) {
            UserServices<V> userServices = this.findOrCreateUserLocked(n);
            if (userServices.services != null) return userServices.services.get(object);
            this.generateServicesMap(null, n);
            return userServices.services.get(object);
        }
    }

    @VisibleForTesting
    protected UserInfo getUser(int n) {
        return UserManager.get(this.mContext).getUserInfo(n);
    }

    @VisibleForTesting
    protected File getUserSystemDirectory(int n) {
        return Environment.getUserSystemDirectory(n);
    }

    @VisibleForTesting
    protected List<UserInfo> getUsers() {
        return UserManager.get(this.mContext).getUsers(true);
    }

    @VisibleForTesting
    protected boolean inSystemImage(int n) {
        String[] arrstring = this.mContext.getPackageManager().getPackagesForUid(n);
        if (arrstring != null) {
            for (String string2 : arrstring) {
                try {
                    int n2 = this.mContext.getPackageManager().getPackageInfo((String)string2, (int)0).applicationInfo.flags;
                    if ((n2 & 1) == 0) continue;
                    return true;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    return false;
                }
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void invalidateCache(int n) {
        Object object = this.mServicesLock;
        synchronized (object) {
            this.findOrCreateUserLocked((int)n).services = null;
            this.onServicesChangedLocked(n);
            return;
        }
    }

    protected void onServicesChangedLocked(int n) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    protected void onUserRemoved(int n) {
        Object object = this.mServicesLock;
        synchronized (object) {
            this.mUserServices.remove(n);
            return;
        }
    }

    public abstract V parseServiceAttributes(Resources var1, String var2, AttributeSet var3);

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    protected ServiceInfo<V> parseServiceInfo(ResolveInfo var1_1) throws XmlPullParserException, IOException {
        var2_5 = var1_1.serviceInfo;
        var3_6 /* !! */  = new ComponentName(var2_5.packageName, var2_5.name);
        var4_7 /* !! */  = this.mContext.getPackageManager();
        var5_8 = null;
        var6_9 = null;
        try {
            var7_10 = var2_5.loadXmlMetaData(var4_7 /* !! */ , this.mMetaDataName);
            if (var7_10 == null) ** GOTO lbl58
            var6_9 = var7_10;
            var5_8 = var7_10;
            var8_11 = Xml.asAttributeSet((XmlPullParser)var7_10);
            do {
                var6_9 = var7_10;
                var5_8 = var7_10;
            } while ((var9_12 = var7_10.next()) != 1 && var9_12 != 2);
            var6_9 = var7_10;
            var5_8 = var7_10;
            var10_13 = var7_10.getName();
            var6_9 = var7_10;
            var5_8 = var7_10;
            if (this.mAttributesName.equals(var10_13)) {
                var6_9 = var7_10;
                var5_8 = var7_10;
                if ((var4_7 /* !! */  = this.parseServiceAttributes(var4_7 /* !! */ .getResourcesForApplication(var2_5.applicationInfo), var2_5.packageName, var8_11)) == null) {
                    var7_10.close();
                    return null;
                }
                var6_9 = var7_10;
                var5_8 = var7_10;
                var1_1 = new ServiceInfo<PackageManager>(var4_7 /* !! */ , var1_1.serviceInfo, var3_6 /* !! */ );
                var7_10.close();
                return var1_1;
            }
            var6_9 = var7_10;
            var5_8 = var7_10;
            var6_9 = var7_10;
            var5_8 = var7_10;
            var6_9 = var7_10;
            var5_8 = var7_10;
            var1_1 = new ServiceInfo<PackageManager>();
            var6_9 = var7_10;
            var5_8 = var7_10;
            var1_1.append("Meta-data does not start with ");
            var6_9 = var7_10;
            var5_8 = var7_10;
            var1_1.append(this.mAttributesName);
            var6_9 = var7_10;
            var5_8 = var7_10;
            var1_1.append(" tag");
            var6_9 = var7_10;
            var5_8 = var7_10;
            var3_6 /* !! */  = new XmlPullParserException(var1_1.toString());
            var6_9 = var7_10;
            var5_8 = var7_10;
            throw var3_6 /* !! */ ;
lbl58: // 1 sources:
            var6_9 = var7_10;
            var5_8 = var7_10;
            var6_9 = var7_10;
            var5_8 = var7_10;
            var6_9 = var7_10;
            var5_8 = var7_10;
            var1_1 = new ServiceInfo<PackageManager>();
            var6_9 = var7_10;
            var5_8 = var7_10;
            var1_1.append("No ");
            var6_9 = var7_10;
            var5_8 = var7_10;
            var1_1.append(this.mMetaDataName);
            var6_9 = var7_10;
            var5_8 = var7_10;
            var1_1.append(" meta-data");
            var6_9 = var7_10;
            var5_8 = var7_10;
            var3_6 /* !! */  = new XmlPullParserException(var1_1.toString());
            var6_9 = var7_10;
            var5_8 = var7_10;
            throw var3_6 /* !! */ ;
        }
        catch (Throwable var1_2) {
        }
        catch (PackageManager.NameNotFoundException var1_3) {
            var6_9 = var5_8;
            var6_9 = var5_8;
            var6_9 = var5_8;
            var7_10 = new StringBuilder();
            var6_9 = var5_8;
            var7_10.append("Unable to load resources for pacakge ");
            var6_9 = var5_8;
            var7_10.append(var2_5.packageName);
            var6_9 = var5_8;
            var1_4 = new XmlPullParserException(var7_10.toString());
            var6_9 = var5_8;
            throw var1_4;
        }
        if (var6_9 == null) throw var1_2;
        var6_9.close();
        throw var1_2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    protected List<ResolveInfo> queryIntentServices(int n) {
        PackageManager packageManager = this.mContext.getPackageManager();
        int n2 = 786560;
        Object object = this.mServicesLock;
        synchronized (object) {
            if (this.findOrCreateUserLocked((int)n).mBindInstantServiceAllowed) {
                n2 = 786560 | 8388608;
            }
            return packageManager.queryIntentServicesAsUser(new Intent(this.mInterfaceName), n2, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setBindInstantServiceAllowed(int n, boolean bl) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIND_INSTANT_SERVICE", "setBindInstantServiceAllowed");
        Object object = this.mServicesLock;
        synchronized (object) {
            this.findOrCreateUserLocked((int)n).mBindInstantServiceAllowed = bl;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setListener(RegisteredServicesCacheListener<V> registeredServicesCacheListener, Handler handler) {
        Handler handler2 = handler;
        if (handler == null) {
            handler2 = new Handler(this.mContext.getMainLooper());
        }
        synchronized (this) {
            this.mHandler = handler2;
            this.mListener = registeredServicesCacheListener;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateServices(int n) {
        Object object;
        Object object2;
        Object object3 = this.mServicesLock;
        synchronized (object3) {
            object2 = this.findOrCreateUserLocked(n);
            if (((UserServices)object2).services == null) {
                return;
            }
            object = new ArrayList(((UserServices)object2).services.values());
        }
        object3 = null;
        Iterator<E> iterator = object.iterator();
        while (iterator.hasNext()) {
            block13 : {
                ServiceInfo serviceInfo;
                block12 : {
                    serviceInfo = (ServiceInfo)iterator.next();
                    long l = serviceInfo.componentInfo.applicationInfo.versionCode;
                    object2 = serviceInfo.componentInfo.packageName;
                    object = null;
                    try {
                        object = object2 = this.mContext.getPackageManager().getApplicationInfoAsUser((String)object2, 0, n);
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        // empty catch block
                    }
                    if (object == null) break block12;
                    object2 = object3;
                    if ((long)((ApplicationInfo)object).versionCode == l) break block13;
                }
                object = object3;
                if (object3 == null) {
                    object = new IntArray();
                }
                ((IntArray)object).add(serviceInfo.uid);
                object2 = object;
            }
            object3 = object2;
        }
        if (object3 != null && ((IntArray)object3).size() > 0) {
            this.generateServicesMap(((IntArray)object3).toArray(), n);
        }
    }

    public static class ServiceInfo<V> {
        public final ComponentInfo componentInfo;
        @UnsupportedAppUsage
        public final ComponentName componentName;
        @UnsupportedAppUsage
        public final V type;
        @UnsupportedAppUsage
        public final int uid;

        public ServiceInfo(V v, ComponentInfo componentInfo, ComponentName componentName) {
            this.type = v;
            this.componentInfo = componentInfo;
            this.componentName = componentName;
            int n = componentInfo != null ? componentInfo.applicationInfo.uid : -1;
            this.uid = n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ServiceInfo: ");
            stringBuilder.append(this.type);
            stringBuilder.append(", ");
            stringBuilder.append(this.componentName);
            stringBuilder.append(", uid ");
            stringBuilder.append(this.uid);
            return stringBuilder.toString();
        }
    }

    private static class UserServices<V> {
        @GuardedBy(value={"mServicesLock"})
        boolean mBindInstantServiceAllowed = false;
        @GuardedBy(value={"mServicesLock"})
        boolean mPersistentServicesFileDidNotExist = true;
        @GuardedBy(value={"mServicesLock"})
        final Map<V, Integer> persistentServices = Maps.newHashMap();
        @GuardedBy(value={"mServicesLock"})
        Map<V, ServiceInfo<V>> services = null;

        private UserServices() {
        }
    }

}

