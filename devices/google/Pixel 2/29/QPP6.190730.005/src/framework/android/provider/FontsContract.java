/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.provider.-$
 *  android.provider.-$$Lambda
 *  android.provider.-$$Lambda$FontsContract
 *  android.provider.-$$Lambda$FontsContract$3FDNQd-WsglsyDhif-aHVbzkfrA
 */
package android.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.provider.-$;
import android.provider.BaseColumns;
import android.provider.FontRequest;
import android.provider._$$Lambda$FontsContract$3FDNQd_WsglsyDhif_aHVbzkfrA;
import android.provider._$$Lambda$FontsContract$DV4gvjPxJzdQvcfoIJqGrzFtTQs;
import android.provider._$$Lambda$FontsContract$FCawscMFN_8Qxcb2EdA5gdE_O2k;
import android.provider._$$Lambda$FontsContract$LJ3jfZobcxq5xTMmb88GlM1r9Jk;
import android.provider._$$Lambda$FontsContract$Qvl9aVA7txTF3tFcFbbKD_nWpuM;
import android.provider._$$Lambda$FontsContract$YhiTIVckhFBdgNR2V1bGY3Q1Nqg;
import android.provider._$$Lambda$FontsContract$bLFahJqnd9gkPbDqB_OCiChzm_E;
import android.provider._$$Lambda$FontsContract$dFs2m4XF5xdir4W3T_ncUQAVX8k;
import android.provider._$$Lambda$FontsContract$gJeQYFM3pOm_NcWmWnWDAEk3vlM;
import android.provider._$$Lambda$FontsContract$p_tsXYYYpEH0_EJSp2uPrJ33dkU;
import android.provider._$$Lambda$FontsContract$rqfIZKvP1frnI9vP1hVA8jQN_RE;
import android.provider._$$Lambda$FontsContract$rqmVfWYeZ5NL5MtBx5LOdhNAOP4;
import android.provider._$$Lambda$FontsContract$rvEOORTXb3mMYTLkoH9nlHQr9Iw;
import android.provider._$$Lambda$FontsContract$xDMhIK5JxjXFDIXBeQbZ_hdXTBc;
import android.util.Log;
import android.util.LruCache;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FontsContract {
    private static final long SYNC_FONT_FETCH_TIMEOUT_MS = 500L;
    private static final String TAG = "FontsContract";
    private static final int THREAD_RENEWAL_THRESHOLD_MS = 10000;
    private static final Comparator<byte[]> sByteArrayComparator;
    private static volatile Context sContext;
    @GuardedBy(value={"sLock"})
    private static Handler sHandler;
    @GuardedBy(value={"sLock"})
    private static Set<String> sInQueueSet;
    private static final Object sLock;
    private static final Runnable sReplaceDispatcherThreadRunnable;
    @GuardedBy(value={"sLock"})
    private static HandlerThread sThread;
    private static final LruCache<String, Typeface> sTypefaceCache;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        sLock = new Object();
        sTypefaceCache = new LruCache(16);
        sReplaceDispatcherThreadRunnable = new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object = sLock;
                synchronized (object) {
                    if (sThread != null) {
                        sThread.quitSafely();
                        sThread = null;
                        sHandler = null;
                    }
                    return;
                }
            }
        };
        sByteArrayComparator = _$$Lambda$FontsContract$3FDNQd_WsglsyDhif_aHVbzkfrA.INSTANCE;
    }

    private FontsContract() {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Typeface buildTypeface(Context object, CancellationSignal object2, FontInfo[] object3) {
        int n;
        int n2;
        if (((Context)object).isRestricted()) {
            return null;
        }
        Map<Uri, ByteBuffer> map = FontsContract.prepareFontData((Context)object, (FontInfo[])object3, (CancellationSignal)object2);
        if (map.isEmpty()) {
            return null;
        }
        int n3 = ((Object)object3).length;
        object = null;
        for (n2 = 0; n2 < n3; ++n2) {
            object2 = object3[n2];
            ByteBuffer byteBuffer = map.get(((FontInfo)object2).getUri());
            if (byteBuffer == null) continue;
            try {
                Object object4 = new Font.Builder(byteBuffer);
                object4 = ((Font.Builder)object4).setWeight(((FontInfo)object2).getWeight());
                n = ((FontInfo)object2).isItalic() ? 1 : 0;
                object4 = ((Font.Builder)object4).setSlant(n).setTtcIndex(((FontInfo)object2).getTtcIndex()).setFontVariationSettings(((FontInfo)object2).getAxes()).build();
                if (object == null) {
                    object = object2 = new FontFamily.Builder((Font)object4);
                    continue;
                }
                ((FontFamily.Builder)object).addFont((Font)object4);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            continue;
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        if (object == null) {
            return null;
        }
        object3 = ((FontFamily.Builder)object).build();
        map = new FontStyle(400, 0);
        object = ((FontFamily)object3).getFont(0);
        n = ((FontStyle)((Object)map)).getMatchScore(((Font)object).getStyle());
        n2 = 1;
        while (n2 < ((FontFamily)object3).getSize()) {
            object2 = ((FontFamily)object3).getFont(n2);
            int n4 = ((FontStyle)((Object)map)).getMatchScore(((Font)object2).getStyle());
            n3 = n;
            if (n4 < n) {
                object = object2;
                n3 = n4;
            }
            ++n2;
            n = n3;
        }
        return new Typeface.CustomFallbackBuilder((FontFamily)object3).setStyle(((Font)object).getStyle()).build();
    }

    private static List<byte[]> convertToByteArrayList(Signature[] arrsignature) {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        for (int i = 0; i < arrsignature.length; ++i) {
            arrayList.add(arrsignature[i].toByteArray());
        }
        return arrayList;
    }

    private static boolean equalsByteArrayList(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (Arrays.equals(list.get(i), list2.get(i))) continue;
            return false;
        }
        return true;
    }

    public static FontFamilyResult fetchFonts(Context object, CancellationSignal cancellationSignal, FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        if (((Context)object).isRestricted()) {
            return new FontFamilyResult(3, null);
        }
        ProviderInfo providerInfo = FontsContract.getProvider(((Context)object).getPackageManager(), fontRequest);
        if (providerInfo == null) {
            return new FontFamilyResult(1, null);
        }
        try {
            object = new FontFamilyResult(0, FontsContract.getFontFromProvider((Context)object, fontRequest, providerInfo.authority, cancellationSignal));
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return new FontFamilyResult(2, null);
        }
    }

    @VisibleForTesting
    public static FontInfo[] getFontFromProvider(Context arrayList, FontRequest arrayList2, String arrfontVariationAxis, CancellationSignal object) {
        Cursor cursor;
        block32 : {
            int n;
            Object object2 = new ArrayList<Object>();
            Uri uri = new Uri.Builder().scheme("content").authority((String)arrfontVariationAxis).build();
            Uri uri2 = new Uri.Builder().scheme("content").authority((String)arrfontVariationAxis).appendPath("file").build();
            arrayList = ((Context)((Object)arrayList)).getContentResolver();
            arrayList2 = ((FontRequest)((Object)arrayList2)).getQuery();
            cursor = ((ContentResolver)((Object)arrayList)).query(uri, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{arrayList2}, null, (CancellationSignal)object);
            arrayList = object2;
            if (cursor == null) break block32;
            arrayList2 = object2;
            arrayList = object2;
            if (cursor.getCount() <= 0) break block32;
            arrayList2 = object2;
            int n2 = cursor.getColumnIndex("result_code");
            arrayList2 = object2;
            arrayList2 = object2;
            arrayList2 = arrayList = new ArrayList<Object>();
            int n3 = cursor.getColumnIndexOrThrow("_id");
            arrayList2 = arrayList;
            int n4 = cursor.getColumnIndex("file_id");
            arrayList2 = arrayList;
            int n5 = cursor.getColumnIndex("font_ttc_index");
            arrayList2 = arrayList;
            int n6 = cursor.getColumnIndex("font_variation_settings");
            arrayList2 = arrayList;
            int n7 = cursor.getColumnIndex("font_weight");
            arrayList2 = arrayList;
            try {
                n = cursor.getColumnIndex("font_italic");
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    FontsContract.$closeResource(throwable, cursor);
                    throw throwable2;
                }
            }
            do {
                arrayList2 = arrayList;
                if (!cursor.moveToNext()) break block32;
                if (n2 == -1) break block33;
                arrayList2 = arrayList;
                break;
            } while (true);
            {
                int n8;
                boolean bl;
                int n9;
                int n10;
                block33 : {
                    n9 = cursor.getInt(n2);
                }
                n9 = 0;
                if (n5 != -1) {
                    arrayList2 = arrayList;
                    n10 = cursor.getInt(n5);
                }
                n10 = 0;
                if (n6 != -1) {
                    arrayList2 = arrayList;
                    arrfontVariationAxis = cursor.getString(n6);
                }
                arrfontVariationAxis = null;
                if (n4 == -1) {
                    arrayList2 = arrayList;
                    object = ContentUris.withAppendedId(uri, cursor.getLong(n3));
                }
                arrayList2 = arrayList;
                object = ContentUris.withAppendedId(uri2, cursor.getLong(n4));
                if (n7 != -1 && n != -1) {
                    arrayList2 = arrayList;
                    n8 = cursor.getInt(n7);
                    arrayList2 = arrayList;
                    if (cursor.getInt(n) == 1) {
                        bl = true;
                    }
                    bl = false;
                }
                n8 = 400;
                bl = false;
                arrayList2 = arrayList;
                arrfontVariationAxis = FontVariationAxis.fromFontVariationSettings((String)arrfontVariationAxis);
                arrayList2 = arrayList;
                arrayList2 = arrayList;
                object2 = new FontInfo((Uri)object, n10, arrfontVariationAxis, n8, bl, n9);
                arrayList2 = arrayList;
                arrayList.add(object2);
                continue;
            }
        }
        if (cursor != null) {
            FontsContract.$closeResource(null, cursor);
        }
        return arrayList.toArray(new FontInfo[0]);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Typeface getFontSync(FontRequest object) {
        CharSequence charSequence = ((FontRequest)object).getIdentifier();
        Object object2 = sTypefaceCache.get((String)charSequence);
        if (object2 != null) {
            return object2;
        }
        object2 = sLock;
        synchronized (object2) {
            long l;
            AtomicBoolean atomicBoolean;
            Object object3;
            long l2;
            AtomicReference atomicReference;
            Condition condition;
            AtomicBoolean atomicBoolean2;
            block15 : {
                if (sHandler == null) {
                    sThread = object3 = new HandlerThread("fonts", 10);
                    sThread.start();
                    sHandler = object3 = new Handler(sThread.getLooper());
                }
                object3 = new ReentrantLock();
                condition = object3.newCondition();
                atomicReference = new AtomicReference();
                atomicBoolean = new AtomicBoolean(true);
                atomicBoolean2 = new AtomicBoolean(false);
                Handler handler = sHandler;
                _$$Lambda$FontsContract$rqfIZKvP1frnI9vP1hVA8jQN_RE _$$Lambda$FontsContract$rqfIZKvP1frnI9vP1hVA8jQN_RE = new _$$Lambda$FontsContract$rqfIZKvP1frnI9vP1hVA8jQN_RE((FontRequest)object, (String)charSequence, atomicReference, (Lock)object3, atomicBoolean2, atomicBoolean, condition);
                handler.post(_$$Lambda$FontsContract$rqfIZKvP1frnI9vP1hVA8jQN_RE);
                sHandler.removeCallbacks(sReplaceDispatcherThreadRunnable);
                sHandler.postDelayed(sReplaceDispatcherThreadRunnable, 10000L);
                l2 = TimeUnit.MILLISECONDS.toNanos(500L);
                object3.lock();
                if (atomicBoolean.get()) break block15;
                object = (Typeface)atomicReference.get();
                object3.unlock();
                return object;
            }
            do {
                block16 : {
                    try {
                        l = condition.awaitNanos(l2);
                    }
                    catch (InterruptedException interruptedException) {
                        l = l2;
                    }
                    if (atomicBoolean.get()) break block16;
                    object = (Typeface)atomicReference.get();
                    object3.unlock();
                    return object;
                }
                l2 = l;
            } while (l > 0L);
            try {
                atomicBoolean2.set(true);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Remote font fetch timed out: ");
                ((StringBuilder)charSequence).append(((FontRequest)object).getProviderAuthority());
                ((StringBuilder)charSequence).append("/");
                ((StringBuilder)charSequence).append(((FontRequest)object).getQuery());
                Log.w(TAG, ((StringBuilder)charSequence).toString());
                return null;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                object3.unlock();
            }
        }
    }

    @VisibleForTesting
    public static ProviderInfo getProvider(PackageManager list, FontRequest object) throws PackageManager.NameNotFoundException {
        Object object2 = ((FontRequest)object).getProviderAuthority();
        ProviderInfo providerInfo = ((PackageManager)((Object)list)).resolveContentProvider((String)object2, 0);
        if (providerInfo != null) {
            if (providerInfo.packageName.equals(((FontRequest)object).getProviderPackage())) {
                if (providerInfo.applicationInfo.isSystemApp()) {
                    return providerInfo;
                }
                list = FontsContract.convertToByteArrayList(list.getPackageInfo((String)providerInfo.packageName, (int)64).signatures);
                Collections.sort(list, sByteArrayComparator);
                object = ((FontRequest)object).getCertificates();
                for (int i = 0; i < object.size(); ++i) {
                    object2 = new ArrayList((Collection)object.get(i));
                    Collections.sort(object2, sByteArrayComparator);
                    if (!FontsContract.equalsByteArrayList(list, (List<byte[]>)object2)) continue;
                    return providerInfo;
                }
                return null;
            }
            list = new StringBuilder();
            ((StringBuilder)((Object)list)).append("Found content provider ");
            ((StringBuilder)((Object)list)).append((String)object2);
            ((StringBuilder)((Object)list)).append(", but package was not ");
            ((StringBuilder)((Object)list)).append(((FontRequest)object).getProviderPackage());
            throw new PackageManager.NameNotFoundException(((StringBuilder)((Object)list)).toString());
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("No package found for authority: ");
        ((StringBuilder)((Object)list)).append((String)object2);
        throw new PackageManager.NameNotFoundException(((StringBuilder)((Object)list)).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static /* synthetic */ void lambda$getFontSync$0(FontRequest object, String string2, AtomicReference atomicReference, Lock lock, AtomicBoolean atomicBoolean, AtomicBoolean atomicBoolean2, Condition condition) {
        try {
            object = FontsContract.fetchFonts(sContext, null, (FontRequest)object);
            if (((FontFamilyResult)object).getStatusCode() == 0) {
                if ((object = FontsContract.buildTypeface(sContext, null, ((FontFamilyResult)object).getFonts())) != null) {
                    sTypefaceCache.put(string2, (Typeface)object);
                }
                atomicReference.set(object);
            }
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            // empty catch block
        }
        lock.lock();
        try {
            if (atomicBoolean.get()) return;
            atomicBoolean2.set(false);
            condition.signal();
            return;
        }
        finally {
            lock.unlock();
        }
    }

    static /* synthetic */ void lambda$requestFonts$1(FontRequestCallback fontRequestCallback, Typeface typeface) {
        fontRequestCallback.onTypefaceRetrieved(typeface);
    }

    static /* synthetic */ void lambda$requestFonts$10(FontRequestCallback fontRequestCallback) {
        fontRequestCallback.onTypefaceRequestFailed(-3);
    }

    static /* synthetic */ void lambda$requestFonts$11(FontRequestCallback fontRequestCallback, Typeface typeface) {
        fontRequestCallback.onTypefaceRetrieved(typeface);
    }

    static /* synthetic */ void lambda$requestFonts$12(Context object, CancellationSignal cancellationSignal, FontRequest fontRequest, Handler handler, FontRequestCallback fontRequestCallback) {
        FontInfo[] arrfontInfo;
        Object object2;
        block10 : {
            try {
                object2 = FontsContract.fetchFonts((Context)object, cancellationSignal, fontRequest);
                arrfontInfo = sTypefaceCache.get(fontRequest.getIdentifier());
                if (arrfontInfo == null) break block10;
                handler.post(new _$$Lambda$FontsContract$xDMhIK5JxjXFDIXBeQbZ_hdXTBc(fontRequestCallback, (Typeface)arrfontInfo));
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                handler.post(new _$$Lambda$FontsContract$bLFahJqnd9gkPbDqB_OCiChzm_E(fontRequestCallback));
                return;
            }
            return;
        }
        if (((FontFamilyResult)object2).getStatusCode() != 0) {
            int n = ((FontFamilyResult)object2).getStatusCode();
            if (n != 1) {
                if (n != 2) {
                    handler.post(new _$$Lambda$FontsContract$DV4gvjPxJzdQvcfoIJqGrzFtTQs(fontRequestCallback));
                    return;
                }
                handler.post(new _$$Lambda$FontsContract$FCawscMFN_8Qxcb2EdA5gdE_O2k(fontRequestCallback));
                return;
            }
            handler.post(new _$$Lambda$FontsContract$YhiTIVckhFBdgNR2V1bGY3Q1Nqg(fontRequestCallback));
            return;
        }
        arrfontInfo = ((FontFamilyResult)object2).getFonts();
        if (arrfontInfo != null && arrfontInfo.length != 0) {
            int n = arrfontInfo.length;
            for (int i = 0; i < n; ++i) {
                object2 = arrfontInfo[i];
                if (((FontInfo)object2).getResultCode() == 0) continue;
                i = ((FontInfo)object2).getResultCode();
                if (i < 0) {
                    handler.post(new _$$Lambda$FontsContract$Qvl9aVA7txTF3tFcFbbKD_nWpuM(fontRequestCallback));
                } else {
                    handler.post(new _$$Lambda$FontsContract$rvEOORTXb3mMYTLkoH9nlHQr9Iw(fontRequestCallback, i));
                }
                return;
            }
            if ((object = FontsContract.buildTypeface((Context)object, cancellationSignal, arrfontInfo)) == null) {
                handler.post(new _$$Lambda$FontsContract$rqmVfWYeZ5NL5MtBx5LOdhNAOP4(fontRequestCallback));
                return;
            }
            sTypefaceCache.put(fontRequest.getIdentifier(), (Typeface)object);
            handler.post(new _$$Lambda$FontsContract$gJeQYFM3pOm_NcWmWnWDAEk3vlM(fontRequestCallback, (Typeface)object));
            return;
        }
        handler.post(new _$$Lambda$FontsContract$LJ3jfZobcxq5xTMmb88GlM1r9Jk(fontRequestCallback));
        return;
    }

    static /* synthetic */ void lambda$requestFonts$2(FontRequestCallback fontRequestCallback) {
        fontRequestCallback.onTypefaceRequestFailed(-1);
    }

    static /* synthetic */ void lambda$requestFonts$3(FontRequestCallback fontRequestCallback, Typeface typeface) {
        fontRequestCallback.onTypefaceRetrieved(typeface);
    }

    static /* synthetic */ void lambda$requestFonts$4(FontRequestCallback fontRequestCallback) {
        fontRequestCallback.onTypefaceRequestFailed(-2);
    }

    static /* synthetic */ void lambda$requestFonts$5(FontRequestCallback fontRequestCallback) {
        fontRequestCallback.onTypefaceRequestFailed(-3);
    }

    static /* synthetic */ void lambda$requestFonts$6(FontRequestCallback fontRequestCallback) {
        fontRequestCallback.onTypefaceRequestFailed(-3);
    }

    static /* synthetic */ void lambda$requestFonts$7(FontRequestCallback fontRequestCallback) {
        fontRequestCallback.onTypefaceRequestFailed(1);
    }

    static /* synthetic */ void lambda$requestFonts$8(FontRequestCallback fontRequestCallback) {
        fontRequestCallback.onTypefaceRequestFailed(-3);
    }

    static /* synthetic */ void lambda$requestFonts$9(FontRequestCallback fontRequestCallback, int n) {
        fontRequestCallback.onTypefaceRequestFailed(n);
    }

    static /* synthetic */ int lambda$static$13(byte[] arrby, byte[] arrby2) {
        if (arrby.length != arrby2.length) {
            return arrby.length - arrby2.length;
        }
        for (int i = 0; i < arrby.length; ++i) {
            if (arrby[i] == arrby2[i]) continue;
            return arrby[i] - arrby2[i];
        }
        return 0;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Map<Uri, ByteBuffer> prepareFontData(Context object2, FontInfo[] arrfontInfo, CancellationSignal cancellationSignal) {
        void var1_18;
        HashMap<Uri, void> hashMap = new HashMap<Uri, void>();
        ContentResolver contentResolver = ((Context)object2).getContentResolver();
        int n = ((void)var1_18).length;
        int n2 = 0;
        while (n2 < n) {
            block19 : {
                Uri uri;
                void var0_15;
                block18 : {
                    ParcelFileDescriptor parcelFileDescriptor;
                    MappedByteBuffer mappedByteBuffer;
                    void var0_12;
                    block17 : {
                        void var2_19;
                        void throwable3 = var1_18[n2];
                        if (throwable3.getResultCode() != 0 || hashMap.containsKey(uri = throwable3.getUri())) break block19;
                        MappedByteBuffer mappedByteBuffer2 = null;
                        Object iOException = null;
                        mappedByteBuffer = null;
                        MappedByteBuffer mappedByteBuffer3 = null;
                        MappedByteBuffer mappedByteBuffer4 = null;
                        parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r", (CancellationSignal)var2_19);
                        if (parcelFileDescriptor == null) break block17;
                        mappedByteBuffer = mappedByteBuffer4;
                        mappedByteBuffer3 = mappedByteBuffer2;
                        mappedByteBuffer = mappedByteBuffer4;
                        mappedByteBuffer3 = mappedByteBuffer2;
                        FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                        FileChannel iOException2 = fileInputStream.getChannel();
                        long l = iOException2.size();
                        mappedByteBuffer = iOException = iOException2.map(FileChannel.MapMode.READ_ONLY, 0L, l);
                        mappedByteBuffer3 = iOException;
                        FontsContract.$closeResource(null, fileInputStream);
                        catch (Throwable throwable) {
                            try {
                                throw throwable;
                            }
                            catch (Throwable throwable2) {
                                mappedByteBuffer = mappedByteBuffer4;
                                mappedByteBuffer3 = mappedByteBuffer2;
                                try {
                                    FontsContract.$closeResource(throwable, fileInputStream);
                                    mappedByteBuffer = mappedByteBuffer4;
                                    mappedByteBuffer3 = mappedByteBuffer2;
                                    throw throwable2;
                                }
                                catch (Throwable throwable4) {
                                    try {
                                        throw throwable4;
                                    }
                                    catch (Throwable throwable5) {
                                        try {
                                            FontsContract.$closeResource(throwable4, parcelFileDescriptor);
                                            throw throwable5;
                                        }
                                        catch (IOException iOException3) {
                                            MappedByteBuffer mappedByteBuffer5 = mappedByteBuffer;
                                            break block18;
                                        }
                                    }
                                }
                                catch (IOException iOException4) {
                                    MappedByteBuffer mappedByteBuffer6 = mappedByteBuffer3;
                                }
                            }
                        }
                    }
                    if (parcelFileDescriptor == null) break block18;
                    mappedByteBuffer = var0_12;
                    try {
                        FontsContract.$closeResource(null, parcelFileDescriptor);
                    }
                    catch (IOException iOException) {
                        MappedByteBuffer mappedByteBuffer7 = mappedByteBuffer;
                    }
                }
                hashMap.put(uri, var0_15);
            }
            ++n2;
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static void requestFonts(Context context, FontRequest fontRequest, Handler handler, CancellationSignal cancellationSignal, FontRequestCallback fontRequestCallback) {
        Handler handler2 = new Handler();
        Typeface typeface = sTypefaceCache.get(fontRequest.getIdentifier());
        if (typeface != null) {
            handler2.post(new _$$Lambda$FontsContract$p_tsXYYYpEH0_EJSp2uPrJ33dkU(fontRequestCallback, typeface));
            return;
        }
        handler.post(new _$$Lambda$FontsContract$dFs2m4XF5xdir4W3T_ncUQAVX8k(context, cancellationSignal, fontRequest, handler2, fontRequestCallback));
    }

    public static void setApplicationContextForResources(Context context) {
        sContext = context.getApplicationContext();
    }

    public static final class Columns
    implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";

        private Columns() {
        }
    }

    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_REJECTED = 3;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        public FontFamilyResult(int n, FontInfo[] arrfontInfo) {
            this.mStatusCode = n;
            this.mFonts = arrfontInfo;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        static @interface FontResultStatus {
        }

    }

    public static class FontInfo {
        private final FontVariationAxis[] mAxes;
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        public FontInfo(Uri uri, int n, FontVariationAxis[] arrfontVariationAxis, int n2, boolean bl, int n3) {
            this.mUri = Preconditions.checkNotNull(uri);
            this.mTtcIndex = n;
            this.mAxes = arrfontVariationAxis;
            this.mWeight = n2;
            this.mItalic = bl;
            this.mResultCode = n3;
        }

        public FontVariationAxis[] getAxes() {
            return this.mAxes;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }
    }

    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;

        public void onTypefaceRequestFailed(int n) {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        static @interface FontRequestFailReason {
        }

    }

}

