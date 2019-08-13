/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import com.android.internal.content.PackageMonitor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class ActivityChooserModel
extends DataSetObservable {
    private static final String ATTRIBUTE_ACTIVITY = "activity";
    private static final String ATTRIBUTE_TIME = "time";
    private static final String ATTRIBUTE_WEIGHT = "weight";
    private static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
    private static final String TAG_HISTORICAL_RECORD = "historical-record";
    private static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final Map<String, ActivityChooserModel> sDataModelRegistry;
    private static final Object sRegistryLock;
    private final List<ActivityResolveInfo> mActivities = new ArrayList<ActivityResolveInfo>();
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter = new DefaultSorter();
    private boolean mCanReadHistoricalData = true;
    private final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList<HistoricalRecord>();
    private boolean mHistoricalRecordsChanged = true;
    private final String mHistoryFileName;
    private int mHistoryMaxSize = 50;
    private final Object mInstanceLock = new Object();
    private Intent mIntent;
    private final PackageMonitor mPackageMonitor = new DataModelPackageMonitor();
    private boolean mReadShareHistoryCalled = false;
    private boolean mReloadActivities = false;

    static {
        sRegistryLock = new Object();
        sDataModelRegistry = new HashMap<String, ActivityChooserModel>();
    }

    private ActivityChooserModel(Context object, String string2) {
        this.mContext = ((Context)object).getApplicationContext();
        if (!TextUtils.isEmpty(string2) && !string2.endsWith(HISTORY_FILE_EXTENSION)) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(HISTORY_FILE_EXTENSION);
            this.mHistoryFileName = ((StringBuilder)object).toString();
        } else {
            this.mHistoryFileName = string2;
        }
        this.mPackageMonitor.register(this.mContext, null, true);
    }

    private boolean addHisoricalRecord(HistoricalRecord historicalRecord) {
        boolean bl = this.mHistoricalRecords.add(historicalRecord);
        if (bl) {
            this.mHistoricalRecordsChanged = true;
            this.pruneExcessiveHistoricalRecordsIfNeeded();
            this.persistHistoricalDataIfNeeded();
            this.sortActivitiesIfNeeded();
            this.notifyChanged();
        }
        return bl;
    }

    private void ensureConsistentState() {
        boolean bl = this.loadActivitiesIfNeeded();
        boolean bl2 = this.readHistoricalDataIfNeeded();
        this.pruneExcessiveHistoricalRecordsIfNeeded();
        if (bl | bl2) {
            this.sortActivitiesIfNeeded();
            this.notifyChanged();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static ActivityChooserModel get(Context context, String string2) {
        Object object = sRegistryLock;
        synchronized (object) {
            ActivityChooserModel activityChooserModel;
            ActivityChooserModel activityChooserModel2 = activityChooserModel = sDataModelRegistry.get(string2);
            if (activityChooserModel == null) {
                activityChooserModel2 = new ActivityChooserModel(context, string2);
                sDataModelRegistry.put(string2, activityChooserModel2);
            }
            return activityChooserModel2;
        }
    }

    private boolean loadActivitiesIfNeeded() {
        if (this.mReloadActivities && this.mIntent != null) {
            this.mReloadActivities = false;
            this.mActivities.clear();
            List<ResolveInfo> list = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                ResolveInfo resolveInfo = list.get(i);
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (ActivityManager.checkComponentPermission(activityInfo.permission, Process.myUid(), activityInfo.applicationInfo.uid, activityInfo.exported) != 0) continue;
                this.mActivities.add(new ActivityResolveInfo(resolveInfo));
            }
            return true;
        }
        return false;
    }

    private void persistHistoricalDataIfNeeded() {
        if (this.mReadShareHistoryCalled) {
            if (!this.mHistoricalRecordsChanged) {
                return;
            }
            this.mHistoricalRecordsChanged = false;
            if (!TextUtils.isEmpty(this.mHistoryFileName)) {
                new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new ArrayList<HistoricalRecord>(this.mHistoricalRecords), this.mHistoryFileName);
            }
            return;
        }
        throw new IllegalStateException("No preceding call to #readHistoricalData");
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int n = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (n <= 0) {
            return;
        }
        this.mHistoricalRecordsChanged = true;
        for (int i = 0; i < n; ++i) {
            HistoricalRecord historicalRecord = this.mHistoricalRecords.remove(0);
        }
    }

    private boolean readHistoricalDataIfNeeded() {
        if (this.mCanReadHistoricalData && this.mHistoricalRecordsChanged && !TextUtils.isEmpty(this.mHistoryFileName)) {
            this.mCanReadHistoricalData = false;
            this.mReadShareHistoryCalled = true;
            this.readHistoricalDataImpl();
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void readHistoricalDataImpl() {
        Throwable throwable2222;
        int n;
        FileInputStream fileInputStream;
        Object object2;
        Object object;
        block20 : {
            fileInputStream = this.mContext.openFileInput(this.mHistoryFileName);
            object2 = Xml.newPullParser();
            object2.setInput((InputStream)fileInputStream, StandardCharsets.UTF_8.name());
            n = 0;
            while (n != 1 && n != 2) {
                n = object2.next();
            }
            if (!TAG_HISTORICAL_RECORDS.equals(object2.getName())) {
                XmlPullParserException xmlPullParserException = new XmlPullParserException("Share records file does not start with historical-records tag.");
                throw xmlPullParserException;
            }
            break block20;
            catch (FileNotFoundException fileNotFoundException) {
                return;
            }
        }
        Object object3 = this.mHistoricalRecords;
        object3.clear();
        do {
            if ((n = object2.next()) == 1) {
                if (fileInputStream == null) return;
                fileInputStream.close();
                return;
            }
            if (n == 3 || n == 4) continue;
            if (!TAG_HISTORICAL_RECORD.equals(object2.getName())) {
                object = new XmlPullParserException("Share records file not well-formed.");
                throw object;
            }
            String string2 = object2.getAttributeValue(null, ATTRIBUTE_ACTIVITY);
            long l = Long.parseLong(object2.getAttributeValue(null, ATTRIBUTE_TIME));
            float f = Float.parseFloat(object2.getAttributeValue(null, ATTRIBUTE_WEIGHT));
            object = new HistoricalRecord(string2, l, f);
            object3.add(object);
        } while (true);
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {
                object = LOG_TAG;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Error reading historical recrod file: ");
                ((StringBuilder)object3).append(this.mHistoryFileName);
                Log.e(object, ((StringBuilder)object3).toString(), iOException);
                if (fileInputStream == null) return;
                try {
                    fileInputStream.close();
                    return;
                }
                catch (IOException iOException2) {
                    return;
                }
                catch (XmlPullParserException xmlPullParserException) {}
                {
                    object = LOG_TAG;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Error reading historical recrod file: ");
                    ((StringBuilder)object2).append(this.mHistoryFileName);
                    Log.e(object, ((StringBuilder)object2).toString(), xmlPullParserException);
                    if (fileInputStream == null) return;
                }
                fileInputStream.close();
                return;
            }
        }
        if (fileInputStream == null) throw throwable2222;
        try {
            fileInputStream.close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        throw throwable2222;
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter != null && this.mIntent != null && !this.mActivities.isEmpty() && !this.mHistoricalRecords.isEmpty()) {
            this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public Intent chooseActivity(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            Object object2;
            if (this.mIntent == null) {
                return null;
            }
            this.ensureConsistentState();
            Object object3 = this.mActivities.get(n);
            ComponentName componentName = new ComponentName(object3.resolveInfo.activityInfo.packageName, object3.resolveInfo.activityInfo.name);
            object3 = new Intent(this.mIntent);
            ((Intent)object3).setComponent(componentName);
            if (this.mActivityChoserModelPolicy != null && this.mActivityChoserModelPolicy.onChooseActivity(this, (Intent)(object2 = new Intent((Intent)object3)))) {
                return null;
            }
            object2 = new HistoricalRecord(componentName, System.currentTimeMillis(), 1.0f);
            this.addHisoricalRecord((HistoricalRecord)object2);
            return object3;
        }
    }

    protected void finalize() throws Throwable {
        Object.super.finalize();
        this.mPackageMonitor.unregister();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public ResolveInfo getActivity(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            return this.mActivities.get((int)n).resolveInfo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public int getActivityCount() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            return this.mActivities.size();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getActivityIndex(ResolveInfo resolveInfo) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            List<ActivityResolveInfo> list = this.mActivities;
            int n = list.size();
            int n2 = 0;
            while (n2 < n) {
                if (list.get((int)n2).resolveInfo == resolveInfo) {
                    return n2;
                }
                ++n2;
            }
            return -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ResolveInfo getDefaultActivity() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            if (this.mActivities.isEmpty()) return null;
            return this.mActivities.get((int)0).resolveInfo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getHistoryMaxSize() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            return this.mHistoryMaxSize;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getHistorySize() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            return this.mHistoricalRecords.size();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Intent getIntent() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            return this.mIntent;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setActivitySorter(ActivitySorter activitySorter) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (this.mActivitySorter == activitySorter) {
                return;
            }
            this.mActivitySorter = activitySorter;
            if (this.sortActivitiesIfNeeded()) {
                this.notifyChanged();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setDefaultActivity(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            Object object2 = this.mActivities.get(n);
            Comparable<ActivityResolveInfo> comparable = this.mActivities.get(0);
            float f = comparable != null ? comparable.weight - ((ActivityResolveInfo)object2).weight + 5.0f : 1.0f;
            comparable = new ComponentName(object2.resolveInfo.activityInfo.packageName, object2.resolveInfo.activityInfo.name);
            object2 = new HistoricalRecord((ComponentName)comparable, System.currentTimeMillis(), f);
            this.addHisoricalRecord((HistoricalRecord)object2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setHistoryMaxSize(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (this.mHistoryMaxSize == n) {
                return;
            }
            this.mHistoryMaxSize = n;
            this.pruneExcessiveHistoricalRecordsIfNeeded();
            if (this.sortActivitiesIfNeeded()) {
                this.notifyChanged();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setIntent(Intent intent) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (this.mIntent == intent) {
                return;
            }
            this.mIntent = intent;
            this.mReloadActivities = true;
            this.ensureConsistentState();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setOnChooseActivityListener(OnChooseActivityListener onChooseActivityListener) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.mActivityChoserModelPolicy = onChooseActivityListener;
            return;
        }
    }

    public static interface ActivityChooserModelClient {
        public void setActivityChooserModel(ActivityChooserModel var1);
    }

    public final class ActivityResolveInfo
    implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        @Override
        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (ActivityResolveInfo)object;
            return Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo)object).weight);
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append("resolveInfo:");
            stringBuilder.append(this.resolveInfo.toString());
            stringBuilder.append("; weight:");
            stringBuilder.append(new BigDecimal(this.weight));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    public static interface ActivitySorter {
        public void sort(Intent var1, List<ActivityResolveInfo> var2, List<HistoricalRecord> var3);
    }

    private final class DataModelPackageMonitor
    extends PackageMonitor {
        private DataModelPackageMonitor() {
        }

        @Override
        public void onSomePackagesChanged() {
            ActivityChooserModel.this.mReloadActivities = true;
        }
    }

    private final class DefaultSorter
    implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap<ComponentName, ActivityResolveInfo>();

        private DefaultSorter() {
        }

        @Override
        public void sort(Intent object, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            int n;
            Object object2;
            object = this.mPackageNameToActivityMap;
            object.clear();
            int n2 = list.size();
            for (n = 0; n < n2; ++n) {
                object2 = list.get(n);
                ((ActivityResolveInfo)object2).weight = 0.0f;
                object.put(new ComponentName(object2.resolveInfo.activityInfo.packageName, object2.resolveInfo.activityInfo.name), object2);
            }
            n = list2.size();
            float f = 1.0f;
            --n;
            while (n >= 0) {
                object2 = list2.get(n);
                ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo)object.get(((HistoricalRecord)object2).activity);
                float f2 = f;
                if (activityResolveInfo != null) {
                    activityResolveInfo.weight += ((HistoricalRecord)object2).weight * f;
                    f2 = f * 0.95f;
                }
                --n;
                f = f2;
            }
            Collections.sort(list);
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(ComponentName componentName, long l, float f) {
            this.activity = componentName;
            this.time = l;
            this.weight = f;
        }

        public HistoricalRecord(String string2, long l, float f) {
            this(ComponentName.unflattenFromString(string2), l, f);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            HistoricalRecord historicalRecord = (HistoricalRecord)object;
            object = this.activity;
            if (object == null ? historicalRecord.activity != null : !((ComponentName)object).equals(historicalRecord.activity)) {
                return false;
            }
            if (this.time != historicalRecord.time) {
                return false;
            }
            return Float.floatToIntBits(this.weight) == Float.floatToIntBits(historicalRecord.weight);
        }

        public int hashCode() {
            ComponentName componentName = this.activity;
            int n = componentName == null ? 0 : componentName.hashCode();
            long l = this.time;
            return ((1 * 31 + n) * 31 + (int)(l ^ l >>> 32)) * 31 + Float.floatToIntBits(this.weight);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append("; activity:");
            stringBuilder.append(this.activity);
            stringBuilder.append("; time:");
            stringBuilder.append(this.time);
            stringBuilder.append("; weight:");
            stringBuilder.append(new BigDecimal(this.weight));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    public static interface OnChooseActivityListener {
        public boolean onChooseActivity(ActivityChooserModel var1, Intent var2);
    }

    private final class PersistHistoryAsyncTask
    extends AsyncTask<Object, Void, Void> {
        private PersistHistoryAsyncTask() {
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public Void doInBackground(Object ... object) {
            void var1_11;
            Object object2;
            FileOutputStream fileOutputStream;
            block33 : {
                Object object3;
                block32 : {
                    block31 : {
                        block30 : {
                            object2 = (List)object[0];
                            object = (String)object[1];
                            fileOutputStream = ActivityChooserModel.this.mContext.openFileOutput((String)object, 0);
                            XmlSerializer xmlSerializer = Xml.newSerializer();
                            object3 = object2;
                            object3 = object2;
                            object3 = object2;
                            object3 = object2;
                            xmlSerializer.setOutput((OutputStream)fileOutputStream, null);
                            object3 = object2;
                            object3 = object2;
                            object3 = object2;
                            object3 = object2;
                            xmlSerializer.startDocument(StandardCharsets.UTF_8.name(), Boolean.valueOf(true));
                            object3 = object2;
                            object3 = object2;
                            object3 = object2;
                            object3 = object2;
                            xmlSerializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                            object3 = object2;
                            object3 = object2;
                            object3 = object2;
                            object3 = object2;
                            int n = object2.size();
                            object = object2;
                            for (int i = 0; i < n; ++i) {
                                object3 = object;
                                object3 = object;
                                object3 = object;
                                object3 = object;
                                object2 = (HistoricalRecord)object.remove(0);
                                object3 = object;
                                object3 = object;
                                object3 = object;
                                object3 = object;
                                xmlSerializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                                object3 = object;
                                object3 = object;
                                object3 = object;
                                object3 = object;
                                xmlSerializer.attribute(null, ActivityChooserModel.ATTRIBUTE_ACTIVITY, ((HistoricalRecord)object2).activity.flattenToString());
                                xmlSerializer.attribute(null, ActivityChooserModel.ATTRIBUTE_TIME, String.valueOf(((HistoricalRecord)object2).time));
                                xmlSerializer.attribute(null, ActivityChooserModel.ATTRIBUTE_WEIGHT, String.valueOf(((HistoricalRecord)object2).weight));
                                xmlSerializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                            }
                            xmlSerializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                            xmlSerializer.endDocument();
                            ActivityChooserModel.this.mCanReadHistoricalData = true;
                            if (fileOutputStream == null) return null;
                            try {
                                fileOutputStream.close();
                                return null;
                            }
                            catch (IOException iOException) {}
                            catch (IOException iOException) {
                                break block30;
                            }
                            catch (IllegalStateException illegalStateException) {
                                break block31;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                break block32;
                            }
                            catch (Throwable throwable) {
                                break block33;
                            }
                            catch (IOException iOException) {
                                // empty catch block
                            }
                        }
                        object2 = LOG_TAG;
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Error writing historical recrod file: ");
                        ((StringBuilder)object3).append(ActivityChooserModel.this.mHistoryFileName);
                        Log.e((String)object2, ((StringBuilder)object3).toString(), (Throwable)object);
                        ActivityChooserModel.this.mCanReadHistoricalData = true;
                        if (fileOutputStream == null) return null;
                        fileOutputStream.close();
                        return null;
                        catch (IllegalStateException illegalStateException) {
                            // empty catch block
                        }
                    }
                    object3 = LOG_TAG;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Error writing historical recrod file: ");
                    ((StringBuilder)object2).append(ActivityChooserModel.this.mHistoryFileName);
                    Log.e((String)object3, ((StringBuilder)object2).toString(), (Throwable)object);
                    ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (fileOutputStream == null) return null;
                    fileOutputStream.close();
                    return null;
                    catch (IllegalArgumentException illegalArgumentException) {
                        // empty catch block
                    }
                }
                object3 = LOG_TAG;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Error writing historical recrod file: ");
                ((StringBuilder)object2).append(ActivityChooserModel.this.mHistoryFileName);
                Log.e((String)object3, ((StringBuilder)object2).toString(), (Throwable)object);
                ActivityChooserModel.this.mCanReadHistoricalData = true;
                if (fileOutputStream == null) return null;
                fileOutputStream.close();
                return null;
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            ActivityChooserModel.this.mCanReadHistoricalData = true;
            if (fileOutputStream == null) throw var1_11;
            try {
                fileOutputStream.close();
                throw var1_11;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            throw var1_11;
            catch (FileNotFoundException fileNotFoundException) {
                String string2 = LOG_TAG;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Error writing historical recrod file: ");
                ((StringBuilder)object2).append((String)object);
                Log.e(string2, ((StringBuilder)object2).toString(), fileNotFoundException);
                return null;
            }
        }
    }

}

