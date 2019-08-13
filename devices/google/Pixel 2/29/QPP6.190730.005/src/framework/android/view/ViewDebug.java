/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.graphics.Picture;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewOverlay;
import android.view.ViewRootImpl;
import android.view._$$Lambda$ViewDebug$1iDmmthcZt_8LsYI6VndkxasPEs;
import android.view._$$Lambda$ViewDebug$5rTN0pemwbr3I3IL2E_xDBeDTDg;
import android.view._$$Lambda$ViewDebug$flFXZc7_CjFXx7_tYT59WSbUNjI;
import android.view._$$Lambda$ViewDebug$hyDSYptlxuUTTyRIONqWzWWVDB0;
import android.view._$$Lambda$ViewDebug$inOytI2zZEgp1DJv8Cu4GjQVNiE;
import android.view._$$Lambda$ViewDebug$w986pBwzwNi77yEgLa3IWusjPNw;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class ViewDebug {
    private static final int CAPTURE_TIMEOUT = 4000;
    public static final boolean DEBUG_DRAG = false;
    public static final boolean DEBUG_POSITIONING = false;
    private static final String REMOTE_COMMAND_CAPTURE = "CAPTURE";
    private static final String REMOTE_COMMAND_CAPTURE_LAYERS = "CAPTURE_LAYERS";
    private static final String REMOTE_COMMAND_DUMP = "DUMP";
    private static final String REMOTE_COMMAND_DUMP_THEME = "DUMP_THEME";
    private static final String REMOTE_COMMAND_INVALIDATE = "INVALIDATE";
    private static final String REMOTE_COMMAND_OUTPUT_DISPLAYLIST = "OUTPUT_DISPLAYLIST";
    private static final String REMOTE_COMMAND_REQUEST_LAYOUT = "REQUEST_LAYOUT";
    private static final String REMOTE_PROFILE = "PROFILE";
    @Deprecated
    public static final boolean TRACE_HIERARCHY = false;
    @Deprecated
    public static final boolean TRACE_RECYCLER = false;
    private static HashMap<Class<?>, Field[]> mCapturedViewFieldsForClasses;
    private static HashMap<Class<?>, Method[]> mCapturedViewMethodsForClasses;
    private static HashMap<AccessibleObject, ExportedProperty> sAnnotations;
    private static HashMap<Class<?>, Field[]> sFieldsForClasses;
    private static HashMap<Class<?>, Method[]> sMethodsForClasses;

    static {
        mCapturedViewMethodsForClasses = null;
        mCapturedViewFieldsForClasses = null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Object callMethodOnAppropriateTheadBlocking(Method object, Object object2) throws IllegalAccessException, InvocationTargetException, TimeoutException {
        if (!(object2 instanceof View)) {
            return ((Method)object).invoke(object2, null);
        }
        object2 = (View)object2;
        FutureTask<Object> futureTask = new FutureTask<Object>(new Callable<Object>((View)object2){
            final /* synthetic */ View val$view;
            {
                this.val$view = view;
            }

            @Override
            public Object call() throws IllegalAccessException, InvocationTargetException {
                return Method.this.invoke(this.val$view, null);
            }
        });
        object = object2 = ((View)object2).getHandler();
        if (object2 == null) {
            object = new Handler(Looper.getMainLooper());
        }
        ((Handler)object).post(futureTask);
        do {
            try {
                return futureTask.get(4000L, TimeUnit.MILLISECONDS);
            }
            catch (CancellationException cancellationException) {
                throw new RuntimeException("Unexpected cancellation exception", cancellationException);
            }
            catch (InterruptedException interruptedException) {
                continue;
            }
            break;
        } while (true);
        catch (ExecutionException executionException) {
            Throwable throwable = executionException.getCause();
            if (throwable instanceof IllegalAccessException) throw (IllegalAccessException)throwable;
            if (!(throwable instanceof InvocationTargetException)) throw new RuntimeException("Unexpected exception", throwable);
            throw (InvocationTargetException)throwable;
        }
    }

    public static void capture(View object, OutputStream outputStream, View object2) throws IOException {
        Bitmap bitmap = ViewDebug.performViewCapture((View)object2, false);
        object2 = bitmap;
        if (bitmap == null) {
            Log.w("View", "Failed to create capture bitmap!");
            object2 = Bitmap.createBitmap(((View)object).getResources().getDisplayMetrics(), 1, 1, Bitmap.Config.ARGB_8888);
        }
        bitmap = null;
        object = bitmap;
        object = bitmap;
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 32768);
            outputStream = bufferedOutputStream;
            object = outputStream;
        }
        catch (Throwable throwable) {
            if (object != null) {
                ((FilterOutputStream)object).close();
            }
            ((Bitmap)object2).recycle();
            throw throwable;
        }
        ((Bitmap)object2).compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        object = outputStream;
        ((BufferedOutputStream)outputStream).flush();
        ((FilterOutputStream)outputStream).close();
        ((Bitmap)object2).recycle();
    }

    private static void capture(View view, OutputStream outputStream, String string2) throws IOException {
        ViewDebug.capture(view, outputStream, ViewDebug.findView(view, string2));
    }

    public static void captureLayers(View view, DataOutputStream dataOutputStream) throws IOException {
        try {
            Rect rect = new Rect();
            try {
                view.mAttachInfo.mSession.getDisplayFrame(view.mAttachInfo.mWindow, rect);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            dataOutputStream.writeInt(rect.width());
            dataOutputStream.writeInt(rect.height());
            ViewDebug.captureViewLayer(view, dataOutputStream, true);
            dataOutputStream.write(2);
            return;
        }
        finally {
            dataOutputStream.close();
        }
    }

    private static void captureViewLayer(View view, DataOutputStream dataOutputStream, boolean bl) throws IOException {
        Object object;
        int n;
        bl = view.getVisibility() == 0 && bl;
        if ((view.mPrivateFlags & 128) != 128) {
            n = view.getId();
            object = view.getClass().getSimpleName();
            if (n != -1) {
                object = ViewDebug.resolveId(view.getContext(), n).toString();
            }
            dataOutputStream.write(1);
            dataOutputStream.writeUTF((String)object);
            n = bl ? 1 : 0;
            dataOutputStream.writeByte(n);
            object = new int[2];
            view.getLocationInWindow((int[])object);
            dataOutputStream.writeInt(object[0]);
            dataOutputStream.writeInt(object[1]);
            dataOutputStream.flush();
            Bitmap bitmap = ViewDebug.performViewCapture(view, true);
            if (bitmap != null) {
                object = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight() * 2);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)object);
                dataOutputStream.writeInt(((ByteArrayOutputStream)object).size());
                ((ByteArrayOutputStream)object).writeTo(dataOutputStream);
            }
            dataOutputStream.flush();
        }
        if (view instanceof ViewGroup) {
            object = (ViewGroup)view;
            int n2 = ((ViewGroup)object).getChildCount();
            for (n = 0; n < n2; ++n) {
                ViewDebug.captureViewLayer(((ViewGroup)object).getChildAt(n), dataOutputStream, bl);
            }
        }
        if (view.mOverlay != null) {
            ViewDebug.captureViewLayer(view.getOverlay().mOverlayViewGroup, dataOutputStream, bl);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String capturedViewExportFields(Object object, Class<?> arrfield, String string2) {
        if (object == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        arrfield = ViewDebug.capturedViewGetPropertyFields(arrfield);
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            Field field = arrfield[n2];
            try {
                Object object2 = field.get(object);
                stringBuilder.append(string2);
                stringBuilder.append(field.getName());
                stringBuilder.append("=");
                if (object2 != null) {
                    stringBuilder.append(object2.toString().replace("\n", "\\n"));
                } else {
                    stringBuilder.append("null");
                }
                stringBuilder.append(' ');
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
            ++n2;
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String capturedViewExportMethods(Object object, Class<?> arrmethod, String string2) {
        if (object == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        arrmethod = ViewDebug.capturedViewGetPropertyMethods(arrmethod);
        int n = arrmethod.length;
        int n2 = 0;
        while (n2 < n) {
            Method method = arrmethod[n2];
            try {
                Object object2 = method.invoke(object, null);
                Class<?> class_ = method.getReturnType();
                if (method.getAnnotation(CapturedViewProperty.class).retrieveReturn()) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(method.getName());
                    stringBuilder2.append("#");
                    stringBuilder.append(ViewDebug.capturedViewExportMethods(object2, class_, stringBuilder2.toString()));
                } else {
                    stringBuilder.append(string2);
                    stringBuilder.append(method.getName());
                    stringBuilder.append("()=");
                    if (object2 != null) {
                        stringBuilder.append(object2.toString().replace("\n", "\\n"));
                    } else {
                        stringBuilder.append("null");
                    }
                    stringBuilder.append("; ");
                }
            }
            catch (InvocationTargetException invocationTargetException) {
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
            ++n2;
        }
        return stringBuilder.toString();
    }

    private static Field[] capturedViewGetPropertyFields(Class<?> class_) {
        HashMap<Class<?>, Field[]> hashMap;
        Object object;
        if (mCapturedViewFieldsForClasses == null) {
            mCapturedViewFieldsForClasses = new HashMap();
        }
        if ((object = (hashMap = mCapturedViewFieldsForClasses).get(class_)) != null) {
            return object;
        }
        object = new ArrayList();
        for (Field field : class_.getFields()) {
            if (!field.isAnnotationPresent(CapturedViewProperty.class)) continue;
            field.setAccessible(true);
            ((ArrayList)object).add(field);
        }
        object = ((ArrayList)object).toArray(new Field[((ArrayList)object).size()]);
        hashMap.put(class_, (Field[])object);
        return object;
    }

    private static Method[] capturedViewGetPropertyMethods(Class<?> class_) {
        Method[] arrmethod;
        HashMap<Class<?>, Method[]> hashMap;
        if (mCapturedViewMethodsForClasses == null) {
            mCapturedViewMethodsForClasses = new HashMap();
        }
        if ((arrmethod = (hashMap = mCapturedViewMethodsForClasses).get(class_)) != null) {
            return arrmethod;
        }
        ArrayList<Method> arrayList = new ArrayList<Method>();
        for (Method method : class_.getMethods()) {
            if (method.getParameterTypes().length != 0 || !method.isAnnotationPresent(CapturedViewProperty.class) || method.getReturnType() == Void.class) continue;
            method.setAccessible(true);
            arrayList.add(method);
        }
        arrmethod = arrayList.toArray(new Method[arrayList.size()]);
        hashMap.put(class_, arrmethod);
        return arrmethod;
    }

    @UnsupportedAppUsage
    static void dispatchCommand(View view, String string2, String arrstring, OutputStream outputStream) throws IOException {
        view = view.getRootView();
        if (REMOTE_COMMAND_DUMP.equalsIgnoreCase(string2)) {
            ViewDebug.dump(view, false, true, outputStream);
        } else if (REMOTE_COMMAND_DUMP_THEME.equalsIgnoreCase(string2)) {
            ViewDebug.dumpTheme(view, outputStream);
        } else if (REMOTE_COMMAND_CAPTURE_LAYERS.equalsIgnoreCase(string2)) {
            ViewDebug.captureLayers(view, new DataOutputStream(outputStream));
        } else {
            arrstring = arrstring.split(" ");
            if (REMOTE_COMMAND_CAPTURE.equalsIgnoreCase(string2)) {
                ViewDebug.capture(view, outputStream, arrstring[0]);
            } else if (REMOTE_COMMAND_OUTPUT_DISPLAYLIST.equalsIgnoreCase(string2)) {
                ViewDebug.outputDisplayList(view, arrstring[0]);
            } else if (REMOTE_COMMAND_INVALIDATE.equalsIgnoreCase(string2)) {
                ViewDebug.invalidate(view, arrstring[0]);
            } else if (REMOTE_COMMAND_REQUEST_LAYOUT.equalsIgnoreCase(string2)) {
                ViewDebug.requestLayout(view, arrstring[0]);
            } else if (REMOTE_PROFILE.equalsIgnoreCase(string2)) {
                ViewDebug.profile(view, outputStream, arrstring[0]);
            }
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
    @Deprecated
    @UnsupportedAppUsage
    public static void dump(View var0, boolean var1_3, boolean var2_4, OutputStream var3_5) throws IOException {
        var4_6 = null;
        var6_8 /* !! */  = var5_7 = null;
        var7_9 /* !! */  = var4_6;
        var6_8 /* !! */  = var5_7;
        var7_9 /* !! */  = var4_6;
        var6_8 /* !! */  = var5_7;
        var7_9 /* !! */  = var4_6;
        var9_11 = new OutputStreamWriter((OutputStream)var3_5, "utf-8");
        var6_8 /* !! */  = var5_7;
        var7_9 /* !! */  = var4_6;
        var8_10 = new BufferedWriter(var9_11, 32768);
        var3_5 = var8_10;
        var6_8 /* !! */  = var3_5;
        var7_9 /* !! */  = var3_5;
        var0 = var0.getRootView();
        var6_8 /* !! */  = var3_5;
        var7_9 /* !! */  = var3_5;
        if (var0 instanceof ViewGroup) {
            var6_8 /* !! */  = var3_5;
            var7_9 /* !! */  = var3_5;
            var0 = (ViewGroup)var0;
            var6_8 /* !! */  = var3_5;
            var7_9 /* !! */  = var3_5;
            ViewDebug.dumpViewHierarchy(var0.getContext(), (ViewGroup)var0, (BufferedWriter)var3_5, 0, var1_3, var2_4);
        }
        var6_8 /* !! */  = var3_5;
        var7_9 /* !! */  = var3_5;
        var3_5.write("DONE.");
        var6_8 /* !! */  = var3_5;
        var7_9 /* !! */  = var3_5;
        var3_5.newLine();
lbl32: // 2 sources:
        do {
            var3_5.close();
            return;
            break;
        } while (true);
        {
            catch (Throwable var0_1) {
            }
            catch (Exception var0_2) {}
            var6_8 /* !! */  = var7_9 /* !! */ ;
            {
                Log.w("View", "Problem dumping the view:", var0_2);
                if (var7_9 /* !! */  == null) return;
                var3_5 = var7_9 /* !! */ ;
                ** continue;
            }
        }
        if (var6_8 /* !! */  == null) throw var0_1;
        var6_8 /* !! */ .close();
        throw var0_1;
    }

    public static void dumpCapturedView(String string2, Object object) {
        Class<?> class_ = object.getClass();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(class_.getName());
        stringBuilder.append(": ");
        stringBuilder = new StringBuilder(stringBuilder.toString());
        stringBuilder.append(ViewDebug.capturedViewExportFields(object, class_, ""));
        stringBuilder.append(ViewDebug.capturedViewExportMethods(object, class_, ""));
        Log.d(string2, stringBuilder.toString());
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void dumpTheme(View var0, OutputStream var1_3) throws IOException {
        var2_4 = null;
        var3_5 = null;
        var4_6 /* !! */  = var3_5;
        var5_7 /* !! */  = var2_4;
        var4_6 /* !! */  = var3_5;
        var5_7 /* !! */  = var2_4;
        var4_6 /* !! */  = var3_5;
        var5_7 /* !! */  = var2_4;
        var7_9 = new OutputStreamWriter((OutputStream)var1_3, "utf-8");
        var4_6 /* !! */  = var3_5;
        var5_7 /* !! */  = var2_4;
        var6_8 = new BufferedWriter(var7_9, 32768);
        var1_3 = var6_8;
        var4_6 /* !! */  = var1_3;
        var5_7 /* !! */  = var1_3;
        var0 = ViewDebug.getStyleAttributesDump(var0.getContext().getResources(), var0.getContext().getTheme());
        if (var0 != null) {
            var8_10 = 0;
            do {
                var4_6 /* !! */  = var1_3;
                var5_7 /* !! */  = var1_3;
                if (var8_10 >= var0.length) break;
                if (var0[var8_10] != null) {
                    var4_6 /* !! */  = var1_3;
                    var5_7 /* !! */  = var1_3;
                    var4_6 /* !! */  = var1_3;
                    var5_7 /* !! */  = var1_3;
                    var3_5 = new StringBuilder();
                    var4_6 /* !! */  = var1_3;
                    var5_7 /* !! */  = var1_3;
                    var3_5.append(var0[var8_10]);
                    var4_6 /* !! */  = var1_3;
                    var5_7 /* !! */  = var1_3;
                    var3_5.append("\n");
                    var4_6 /* !! */  = var1_3;
                    var5_7 /* !! */  = var1_3;
                    var1_3.write(var3_5.toString());
                    var4_6 /* !! */  = var1_3;
                    var5_7 /* !! */  = var1_3;
                    var4_6 /* !! */  = var1_3;
                    var5_7 /* !! */  = var1_3;
                    var3_5 = new StringBuilder();
                    var4_6 /* !! */  = var1_3;
                    var5_7 /* !! */  = var1_3;
                    var3_5.append(var0[var8_10 + 1]);
                    var4_6 /* !! */  = var1_3;
                    var5_7 /* !! */  = var1_3;
                    var3_5.append("\n");
                    var4_6 /* !! */  = var1_3;
                    var5_7 /* !! */  = var1_3;
                    var1_3.write(var3_5.toString());
                }
                var8_10 += 2;
            } while (true);
        }
        var4_6 /* !! */  = var1_3;
        var5_7 /* !! */  = var1_3;
        var1_3.write("DONE.");
        var4_6 /* !! */  = var1_3;
        var5_7 /* !! */  = var1_3;
        var1_3.newLine();
lbl65: // 2 sources:
        do {
            var1_3.close();
            return;
            break;
        } while (true);
        {
            catch (Throwable var0_1) {
            }
            catch (Exception var0_2) {}
            var4_6 /* !! */  = var5_7 /* !! */ ;
            {
                Log.w("View", "Problem dumping View Theme:", var0_2);
                if (var5_7 /* !! */  == null) return;
                var1_3 = var5_7 /* !! */ ;
                ** continue;
            }
        }
        if (var4_6 /* !! */  == null) throw var0_1;
        var4_6 /* !! */ .close();
        throw var0_1;
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private static boolean dumpView(Context context, View view, BufferedWriter bufferedWriter, int n, boolean bl) {
        for (int i = 0; i < n; ++i) {
            bufferedWriter.write(32);
            continue;
        }
        try {
            String string2;
            String string3 = string2 = view.getClass().getName();
            if (string2.equals("android.view.ViewOverlay$OverlayViewGroup")) {
                string3 = "ViewOverlay";
            }
            bufferedWriter.write(string3);
            bufferedWriter.write(64);
            bufferedWriter.write(Integer.toHexString(view.hashCode()));
            bufferedWriter.write(32);
            if (bl) {
                ViewDebug.dumpViewProperties(context, view, bufferedWriter);
            }
            bufferedWriter.newLine();
            return true;
        }
        catch (IOException iOException) {
            Log.w("View", "Error while dumping hierarchy tree");
            return false;
        }
    }

    private static void dumpViewHierarchy(Context context, ViewGroup viewGroup, BufferedWriter bufferedWriter, int n, boolean bl, boolean bl2) {
        if (!ViewDebug.dumpView(context, viewGroup, bufferedWriter, n, bl2)) {
            return;
        }
        if (bl) {
            return;
        }
        int n2 = viewGroup.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                ViewDebug.dumpViewHierarchy(context, (ViewGroup)view, bufferedWriter, n + 1, bl, bl2);
            } else {
                ViewDebug.dumpView(context, view, bufferedWriter, n + 1, bl2);
            }
            if (view.mOverlay == null) continue;
            ViewDebug.dumpViewHierarchy(context, view.getOverlay().mOverlayViewGroup, bufferedWriter, n + 2, bl, bl2);
        }
        if (viewGroup instanceof HierarchyHandler) {
            ((HierarchyHandler)((Object)viewGroup)).dumpViewHierarchyWithProperties(bufferedWriter, n + 1);
        }
    }

    private static void dumpViewProperties(Context context, Object object, BufferedWriter bufferedWriter) throws IOException {
        ViewDebug.dumpViewProperties(context, object, bufferedWriter, "");
    }

    private static void dumpViewProperties(Context object, Object object2, BufferedWriter bufferedWriter, String string2) throws IOException {
        if (object2 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("=4,null ");
            bufferedWriter.write(((StringBuilder)object).toString());
            return;
        }
        Class<?> class_ = object2.getClass();
        do {
            ViewDebug.exportFields((Context)object, object2, bufferedWriter, class_, string2);
            ViewDebug.exportMethods((Context)object, object2, bufferedWriter, class_, string2);
        } while ((class_ = class_.getSuperclass()) != Object.class);
    }

    public static void dumpv2(final View view, ByteArrayOutputStream object) throws InterruptedException {
        object = new ViewHierarchyEncoder((ByteArrayOutputStream)object);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        view.post(new Runnable(){

            @Override
            public void run() {
                ViewHierarchyEncoder.this.addProperty("window:left", view.mAttachInfo.mWindowLeft);
                ViewHierarchyEncoder.this.addProperty("window:top", view.mAttachInfo.mWindowTop);
                view.encode(ViewHierarchyEncoder.this);
                countDownLatch.countDown();
            }
        });
        countDownLatch.await(2L, TimeUnit.SECONDS);
        ((ViewHierarchyEncoder)object).endStream();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void exportFields(Context var0, Object var1_1, BufferedWriter var2_2, Class<?> var3_3, String var4_5) throws IOException {
        var5_6 = ViewDebug.getExportedPropertyFields(var3_3);
        var6_7 = var5_6.length;
        var7_8 = 0;
        while (var7_8 < var6_7) {
            block20 : {
                block22 : {
                    block21 : {
                        var8_10 = var5_6[var7_8];
                        var9_12 = var8_10.getType();
                        var10_13 = ViewDebug.sAnnotations.get(var8_10);
                        var11_14 = var10_13.category().length();
                        if (var11_14 != 0) {
                            var3_3 = new StringBuilder();
                            var3_3.append(var10_13.category());
                            var3_3.append(":");
                            var12_15 = var3_3.toString();
                        } else {
                            var12_15 = "";
                        }
                        if (var9_12 == Integer.TYPE || var9_12 == Byte.TYPE) ** GOTO lbl62
                        if (var9_12 == int[].class) {
                            var13_16 = (int[])var8_10.get(var1_1);
                            var3_3 = new StringBuilder();
                            var3_3.append(var12_15);
                            var3_3.append(var4_5);
                            var3_3.append(var8_10.getName());
                            var3_3.append('_');
                            ViewDebug.exportUnrolledArray(var0, var2_2, var10_13, (int[])var13_16, var3_3.toString(), "");
                            break block20;
                        }
                        if (var9_12 != String[].class) break block21;
                        var3_3 = (String[])var8_10.get(var1_1);
                        if (var10_13.hasAdjacentMapping() && var3_3 != null) {
                            for (var11_14 = 0; var11_14 < ((Object)var3_3).length; var11_14 += 2) {
                                if (var3_3[var11_14] == null) continue;
                                var13_16 = new StringBuilder();
                                var13_16.append(var12_15);
                                var13_16.append(var4_5);
                                var14_17 = var13_16.toString();
                                var8_11 = var3_3[var11_14];
                                var13_16 = var3_3[var11_14 + 1] == null ? "null" : var3_3[var11_14 + 1];
                                ViewDebug.writeEntry(var2_2, (String)var14_17, (String)var8_11, "", var13_16);
                            }
                        }
                    }
                    if (!var9_12.isPrimitive() && var10_13.deepExport()) {
                        var3_3 = var8_10.get(var1_1);
                        var13_16 = new StringBuilder();
                        var13_16.append(var4_5);
                        var13_16.append(var10_13.prefix());
                        ViewDebug.dumpViewProperties(var0, var3_3, var2_2, var13_16.toString());
                        break block20;
                    }
                    var3_3 = null;
                    ** GOTO lbl107
lbl62: // 1 sources:
                    var3_3 = null;
                    var13_16 = null;
                    if (!var10_13.resolveId() || var0 == null) break block22;
                    var3_3 = ViewDebug.resolveId(var0, var8_10.getInt(var1_1));
                    ** GOTO lbl107
                }
                var14_17 = var10_13.flagMapping();
                if (((FlagToString[])var14_17).length > 0) {
                    var11_14 = var8_10.getInt(var1_1);
                    var15_18 = new StringBuilder();
                    var15_18.append(var12_15);
                    var15_18.append(var4_5);
                    var15_18.append(var8_10.getName());
                    var15_18.append('_');
                    ViewDebug.exportUnrolledFlags(var2_2, (FlagToString[])var14_17, var11_14, var15_18.toString());
                }
                if (((Annotation[])(var14_17 = var10_13.mapping())).length <= 0) ** GOTO lbl94
                var16_19 = var8_10.getInt(var1_1);
                var17_20 = ((Annotation[])var14_17).length;
                var3_3 = var14_17;
                for (var11_14 = 0; var11_14 < var17_20; ++var11_14) {
                    var14_17 = var3_3[var11_14];
                    if (var14_17.from() != var16_19) continue;
                    var13_16 = var14_17.to();
                    break;
                }
                var3_3 = var13_16;
                if (var13_16 != null) ** GOTO lbl94
                try {
                    var3_3 = var16_19;
lbl94: // 3 sources:
                    if (var10_13.formatToHexString()) {
                        var13_16 = var8_10.get(var1_1);
                        if (var9_12 == Integer.TYPE) {
                            var3_3 = ViewDebug.formatIntToHexString((Integer)var13_16);
                        } else {
                            var3_3 = var13_16;
                            if (var9_12 == Byte.TYPE) {
                                var3_3 = new StringBuilder();
                                var3_3.append("0x");
                                var3_3.append(Byte.toHexString((byte)((Byte)var13_16), (boolean)true));
                                var3_3 = var3_3.toString();
                            }
                        }
                    }
lbl107: // 8 sources:
                    var13_16 = var3_3;
                    if (var3_3 == null) {
                        var13_16 = var8_10.get(var1_1);
                    }
                    var3_3 = new StringBuilder();
                    var3_3.append(var12_15);
                    var3_3.append(var4_5);
                    ViewDebug.writeEntry(var2_2, var3_3.toString(), var8_10.getName(), "", var13_16);
                }
                catch (IllegalAccessException var3_4) {
                    // empty catch block
                }
            }
            ++var7_8;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void exportMethods(Context var0, Object var1_1, BufferedWriter var2_2, Class<?> var3_3, String var4_13) throws IOException {
        var5_14 = ViewDebug.getExportedPropertyMethods(var3_3);
        var6_15 = var5_14.length;
        var7_16 = 0;
        while (var7_16 < var6_15) {
            block32 : {
                block30 : {
                    block31 : {
                        block29 : {
                            block28 : {
                                var8_17 = var5_14[var7_16];
                                var3_3 = ViewDebug.callMethodOnAppropriateTheadBlocking((Method)var8_17, var1_1);
                                var9_18 = var8_17.getReturnType();
                                var10_19 = ViewDebug.sAnnotations.get(var8_17);
                                var11_20 = var10_19.category().length();
                                if (var11_20 == 0) break block28;
                                var12_21 = new StringBuilder();
                                var12_21.append(var10_19.category());
                                var12_21.append(":");
                                var13_22 = var12_21.toString();
                                break block29;
                            }
                            var13_22 = "";
                        }
                        var12_21 = Integer.TYPE;
                        if (var9_18 != var12_21) break block30;
                        try {
                            if (!var10_19.resolveId() || var0 == null) break block31;
                            var12_21 = ViewDebug.resolveId(var0, (Integer)var3_3);
                            var14_23 = "()";
                            ** GOTO lbl-1000
                        }
                        catch (TimeoutException var3_4) {
                            break block32;
                        }
                        catch (InvocationTargetException var3_5) {
                            break block32;
                        }
                        catch (IllegalAccessException var3_6) {
                            break block32;
                        }
                    }
                    var14_23 = var10_19.flagMapping();
                    if (((FlagToString[])var14_23).length > 0) {
                        var11_20 = (Integer)var3_3;
                        var12_21 = new StringBuilder();
                        var12_21.append(var13_22);
                        var12_21.append(var4_13);
                        var12_21.append(var8_17.getName());
                        var12_21.append('_');
                        ViewDebug.exportUnrolledFlags(var2_2, (FlagToString[])var14_23, var11_20, var12_21.toString());
                    }
                    if (((IntToString[])(var12_21 = var10_19.mapping())).length > 0) {
                        block33 : {
                            var15_24 = (Integer)var3_3;
                            var16_25 = ((Object)var12_21).length;
                            for (var11_20 = 0; var11_20 < var16_25; ++var11_20) {
                                var14_23 = var12_21[var11_20];
                                if (var14_23.from() != var15_24) continue;
                                var3_3 = var14_23.to();
                                var11_20 = 1;
                                break block33;
                            }
                            var11_20 = 0;
                        }
                        if (var11_20 == 0) {
                            var3_3 = var15_24;
                        }
                    }
                    var14_23 = "()";
                    var12_21 = var3_3;
                    ** GOTO lbl-1000
                }
                if (var9_18 == int[].class) {
                    var3_3 = (int[])var3_3;
                    var12_21 = new StringBuilder();
                    var12_21.append(var13_22);
                    var12_21.append(var4_13);
                    var12_21.append(var8_17.getName());
                    var12_21.append('_');
                    var12_21 = var12_21.toString();
                    ViewDebug.exportUnrolledArray(var0, var2_2, var10_19, (int[])var3_3, (String)var12_21, "()");
                }
                var17_26 = "()";
                if (var9_18 == String[].class) {
                    var3_3 = (String[])var3_3;
                    if (var10_19.hasAdjacentMapping() && var3_3 != null) {
                        for (var11_20 = 0; var11_20 < ((Object)var3_3).length; var11_20 += 2) {
                            if (var3_3[var11_20] == null) continue;
                            var12_21 = new StringBuilder();
                            var12_21.append(var13_22);
                            var12_21.append(var4_13);
                            var8_17 = var12_21.toString();
                            var14_23 = var3_3[var11_20];
                            var12_21 = var3_3[var11_20 + 1] == null ? "null" : var3_3[var11_20 + 1];
                            ViewDebug.writeEntry(var2_2, (String)var8_17, (String)var14_23, var17_26, var12_21);
                        }
                    }
                    break block32;
                }
                var12_21 = var3_3;
                var14_23 = var17_26;
                try {
                    if (var9_18.isPrimitive()) ** GOTO lbl-1000
                    var12_21 = var3_3;
                    var14_23 = var17_26;
                    if (var10_19.deepExport()) {
                        var12_21 = new StringBuilder();
                        var12_21.append(var4_13);
                        var12_21.append(var10_19.prefix());
                        ViewDebug.dumpViewProperties(var0, var3_3, var2_2, var12_21.toString());
                    } else lbl-1000: // 4 sources:
                    {
                        var3_3 = new StringBuilder();
                        var3_3.append(var13_22);
                        var3_3.append(var4_13);
                        ViewDebug.writeEntry(var2_2, var3_3.toString(), var8_17.getName(), (String)var14_23, var12_21);
                    }
                    break block32;
                }
                catch (TimeoutException var3_7) {
                }
                catch (InvocationTargetException var3_8) {
                }
                catch (IllegalAccessException var3_9) {}
                break block32;
                catch (TimeoutException var3_10) {
                    break block32;
                }
                catch (InvocationTargetException var3_11) {
                    break block32;
                }
                catch (IllegalAccessException var3_12) {
                    // empty catch block
                }
            }
            ++var7_16;
        }
    }

    private static void exportUnrolledArray(Context context, BufferedWriter bufferedWriter, ExportedProperty object, int[] arrn, String string2, String string3) throws IOException {
        IntToString[] arrintToString = object.indexMapping();
        int n = arrintToString.length;
        int n2 = 0;
        n = n > 0 ? 1 : 0;
        IntToString[] arrintToString2 = object.mapping();
        boolean bl = arrintToString2.length > 0;
        int n3 = n2;
        if (object.resolveId()) {
            n3 = n2;
            if (context != null) {
                n3 = 1;
            }
        }
        int n4 = arrn.length;
        for (n2 = 0; n2 < n4; ++n2) {
            int n5;
            int n6;
            Object object2 = null;
            int n7 = arrn[n2];
            Object object3 = object = String.valueOf(n2);
            if (n != 0) {
                n6 = arrintToString.length;
                n5 = 0;
                do {
                    object3 = object;
                    if (n5 >= n6) break;
                    object3 = arrintToString[n5];
                    if (object3.from() == n2) {
                        object3 = object3.to();
                        break;
                    }
                    ++n5;
                } while (true);
            }
            object = object2;
            if (bl) {
                n6 = arrintToString2.length;
                n5 = 0;
                do {
                    object = object2;
                    if (n5 >= n6) break;
                    object = arrintToString2[n5];
                    if (object.from() == n7) {
                        object = object.to();
                        break;
                    }
                    ++n5;
                } while (true);
            }
            if (n3 != 0) {
                object2 = object;
                if (object == null) {
                    object2 = (String)ViewDebug.resolveId(context, n7);
                }
            } else {
                object2 = String.valueOf(n7);
            }
            ViewDebug.writeEntry(bufferedWriter, string2, (String)object3, string3, object2);
        }
    }

    private static void exportUnrolledFlags(BufferedWriter bufferedWriter, FlagToString[] arrflagToString, int n, String string2) throws IOException {
        for (FlagToString flagToString : arrflagToString) {
            boolean bl = flagToString.outputIf();
            int n2 = flagToString.mask() & n;
            boolean bl2 = n2 == flagToString.equals();
            if ((!bl2 || !bl) && (bl2 || bl)) continue;
            ViewDebug.writeEntry(bufferedWriter, string2, flagToString.name(), "", ViewDebug.formatIntToHexString(n2));
        }
    }

    public static View findView(View view, String string2) {
        if (string2.indexOf(64) != -1) {
            String[] arrstring = string2.split("@");
            string2 = arrstring[0];
            int n = (int)Long.parseLong(arrstring[1], 16);
            if ((view = view.getRootView()) instanceof ViewGroup) {
                return ViewDebug.findView((ViewGroup)view, string2, n);
            }
            return null;
        }
        int n = view.getResources().getIdentifier(string2, null, null);
        return view.getRootView().findViewById(n);
    }

    private static View findView(ViewGroup viewGroup, String string2, int n) {
        if (ViewDebug.isRequestedView(viewGroup, string2, n)) {
            return viewGroup;
        }
        int n2 = viewGroup.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view;
            View view2 = viewGroup.getChildAt(i);
            if (view2 instanceof ViewGroup) {
                view = ViewDebug.findView((ViewGroup)view2, string2, n);
                if (view != null) {
                    return view;
                }
            } else if (ViewDebug.isRequestedView(view2, string2, n)) {
                return view2;
            }
            if (view2.mOverlay != null && (view = ViewDebug.findView(view2.mOverlay.mOverlayViewGroup, string2, n)) != null) {
                return view;
            }
            if (!(view2 instanceof HierarchyHandler) || (view2 = ((HierarchyHandler)((Object)view2)).findHierarchyView(string2, n)) == null) continue;
            return view2;
        }
        return null;
    }

    public static String flagsToString(Class<?> arrflagToString, String charSequence, int n) {
        if ((arrflagToString = ViewDebug.getFlagMapping(arrflagToString, (String)charSequence)) == null) {
            return Integer.toHexString(n);
        }
        charSequence = new StringBuilder();
        int n2 = arrflagToString.length;
        int n3 = 0;
        do {
            boolean bl = true;
            if (n3 >= n2) break;
            FlagToString flagToString = arrflagToString[n3];
            boolean bl2 = flagToString.outputIf();
            if ((flagToString.mask() & n) != flagToString.equals()) {
                bl = false;
            }
            if (bl && bl2) {
                ((StringBuilder)charSequence).append(flagToString.name());
                ((StringBuilder)charSequence).append(' ');
            }
            ++n3;
        } while (true);
        if (((StringBuilder)charSequence).length() > 0) {
            ((StringBuilder)charSequence).deleteCharAt(((StringBuilder)charSequence).length() - 1);
        }
        return ((StringBuilder)charSequence).toString();
    }

    private static String formatIntToHexString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n).toUpperCase());
        return stringBuilder.toString();
    }

    private static Field[] getExportedPropertyFields(Class<?> class_) {
        Field[] object2;
        HashMap<Class<?>, Field[]> hashMap;
        if (sFieldsForClasses == null) {
            sFieldsForClasses = new HashMap();
        }
        if (sAnnotations == null) {
            sAnnotations = new HashMap(512);
        }
        if ((object2 = (hashMap = sFieldsForClasses).get(class_)) != null) {
            return object2;
        }
        Field[] arrfield = class_.getDeclaredFieldsUnchecked(false);
        ArrayList<Field> arrayList = new ArrayList<Field>();
        for (Field field : arrfield) {
            if (field.getType() == null || !field.isAnnotationPresent(ExportedProperty.class)) continue;
            field.setAccessible(true);
            arrayList.add(field);
            sAnnotations.put(field, field.getAnnotation(ExportedProperty.class));
        }
        try {
            Field[] arrfield2 = arrayList.toArray(new Field[arrayList.size()]);
            hashMap.put(class_, arrfield2);
            return arrfield2;
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            throw new AssertionError(noClassDefFoundError);
        }
    }

    private static Method[] getExportedPropertyMethods(Class<?> class_) {
        HashMap<Class<?>, Method[]> hashMap;
        Object object;
        if (sMethodsForClasses == null) {
            sMethodsForClasses = new HashMap(100);
        }
        if (sAnnotations == null) {
            sAnnotations = new HashMap(512);
        }
        if ((object = (hashMap = sMethodsForClasses).get(class_)) != null) {
            return object;
        }
        Method[] arrmethod = class_.getDeclaredMethodsUnchecked(false);
        object = new ArrayList();
        for (Method method : arrmethod) {
            try {
                method.getReturnType();
                method.getParameterTypes();
            }
            catch (NoClassDefFoundError noClassDefFoundError) {
                // empty catch block
            }
            if (method.getParameterTypes().length != 0 || !method.isAnnotationPresent(ExportedProperty.class) || method.getReturnType() == Void.class) continue;
            method.setAccessible(true);
            ((ArrayList)object).add(method);
            sAnnotations.put(method, method.getAnnotation(ExportedProperty.class));
        }
        object = ((ArrayList)object).toArray(new Method[((ArrayList)object).size()]);
        hashMap.put(class_, (Method[])object);
        return object;
    }

    private static FlagToString[] getFlagMapping(Class<?> arrflagToString, String string2) {
        try {
            arrflagToString = arrflagToString.getDeclaredField(string2).getAnnotation(ExportedProperty.class).flagMapping();
            return arrflagToString;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return null;
        }
    }

    private static IntToString[] getMapping(Class<?> arrintToString, String string2) {
        try {
            arrintToString = arrintToString.getDeclaredField(string2).getAnnotation(ExportedProperty.class).mapping();
            return arrintToString;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return null;
        }
    }

    private static String[] getStyleAttributesDump(Resources resources, Resources.Theme theme) {
        TypedValue typedValue = new TypedValue();
        int n = 0;
        int[] arrn = theme.getAllAttributes();
        String[] arrstring = new String[arrn.length * 2];
        for (int n2 : arrn) {
            int n3;
            String string2;
            block8 : {
                block7 : {
                    n3 = n;
                    arrstring[n] = resources.getResourceName(n2);
                    n3 = n;
                    if (!theme.resolveAttribute(n2, typedValue, true)) break block7;
                    n3 = n;
                    string2 = typedValue.coerceToString().toString();
                    break block8;
                }
                string2 = "null";
            }
            arrstring[n + 1] = string2;
            n3 = n += 2;
            if (typedValue.type != 1) continue;
            n3 = n;
            try {
                arrstring[n - 1] = resources.getResourceName(typedValue.resourceId);
            }
            catch (Resources.NotFoundException notFoundException) {
                n = n3;
            }
        }
        return arrstring;
    }

    @UnsupportedAppUsage
    public static long getViewInstanceCount() {
        return Debug.countInstancesOfClass(View.class);
    }

    @UnsupportedAppUsage
    public static long getViewRootImplCount() {
        return Debug.countInstancesOfClass(ViewRootImpl.class);
    }

    /*
     * WARNING - void declaration
     */
    public static String intToString(Class<?> arrintToString, String object2, int n) {
        void var2_4;
        if ((arrintToString = ViewDebug.getMapping(arrintToString, (String)object2)) == null) {
            return Integer.toString((int)var2_4);
        }
        for (IntToString intToString : arrintToString) {
            if (intToString.from() != var2_4) continue;
            return intToString.to();
        }
        return Integer.toString((int)var2_4);
    }

    private static void invalidate(View view, String string2) {
        if ((view = ViewDebug.findView(view, string2)) != null) {
            view.postInvalidate();
        }
    }

    public static Object invokeViewMethod(final View view, final Method method, final Object[] arrobject) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        view.post(new Runnable(){

            @Override
            public void run() {
                try {
                    AtomicReference.this.set(method.invoke(view, arrobject));
                }
                catch (Exception exception) {
                    atomicReference2.set(exception);
                }
                catch (InvocationTargetException invocationTargetException) {
                    atomicReference2.set(invocationTargetException.getCause());
                }
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
            if (atomicReference2.get() == null) {
                return atomicReference.get();
            }
            throw new RuntimeException((Throwable)atomicReference2.get());
        }
        catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
    }

    private static boolean isRequestedView(View object, String string2, int n) {
        if (object.hashCode() == n) {
            object = object.getClass().getName();
            if (string2.equals("ViewOverlay")) {
                return ((String)object).equals("android.view.ViewOverlay$OverlayViewGroup");
            }
            return string2.equals(object);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static /* synthetic */ void lambda$performViewCapture$5(View view, Bitmap[] arrbitmap, boolean bl, CountDownLatch countDownLatch) {
        try {
            try {
                CanvasProvider canvasProvider = view.isHardwareAccelerated() ? new HardwareCanvasProvider() : new SoftwareCanvasProvider();
                arrbitmap[0] = view.createSnapshot(canvasProvider, bl);
            }
            catch (OutOfMemoryError outOfMemoryError) {
                Log.w("View", "Out of memory for bitmap");
            }
            countDownLatch.countDown();
            return;
        }
        catch (Throwable throwable2) {}
        countDownLatch.countDown();
        throw throwable2;
    }

    static /* synthetic */ void lambda$profileViewDraw$1(View view, RecordingCanvas recordingCanvas) {
        view.draw(recordingCanvas);
    }

    static /* synthetic */ void lambda$profileViewDraw$2(View view, Canvas canvas) {
        view.draw(canvas);
    }

    static /* synthetic */ void lambda$profileViewLayout$0(View view) {
        view.layout(view.mLeft, view.mTop, view.mRight, view.mBottom);
    }

    static /* synthetic */ void lambda$profileViewOperation$3(ViewOperation viewOperation, long[] arrl, CountDownLatch countDownLatch) {
        try {
            viewOperation.pre();
            long l = Debug.threadCpuTimeNanos();
            viewOperation.run();
            arrl[0] = Debug.threadCpuTimeNanos() - l;
            return;
        }
        finally {
            countDownLatch.countDown();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static /* synthetic */ Boolean lambda$startRenderingCommandsCapture$4(Callable object, Picture picture) {
        block2 : {
            try {
                object = (OutputStream)object.call();
                if (object == null) break block2;
                picture.writeToStream((OutputStream)object);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return true;
        }
        return false;
    }

    public static void outputDisplayList(View view, View view2) {
        view.getViewRootImpl().outputDisplayList(view2);
    }

    private static void outputDisplayList(View view, String string2) throws IOException {
        view = ViewDebug.findView(view, string2);
        view.getViewRootImpl().outputDisplayList(view);
    }

    private static Bitmap performViewCapture(View object, boolean bl) {
        if (object != null) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Bitmap[] arrbitmap = new Bitmap[1];
            ((View)object).post(new _$$Lambda$ViewDebug$1iDmmthcZt_8LsYI6VndkxasPEs((View)object, arrbitmap, bl, countDownLatch));
            try {
                countDownLatch.await(4000L, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException interruptedException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not complete the capture of the view ");
                stringBuilder.append(object);
                Log.w("View", stringBuilder.toString());
                Thread.currentThread().interrupt();
            }
            object = arrbitmap[0];
            return object;
        }
        return null;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void profile(View var0, OutputStream var1_2, String var2_4) throws IOException {
        var3_5 = ViewDebug.findView((View)var0, (String)var2_4);
        var4_6 = null;
        var5_7 = null;
        var2_4 = var5_7;
        var0 = var4_6;
        var2_4 = var5_7;
        var0 = var4_6;
        var2_4 = var5_7;
        var0 = var4_6;
        var7_9 = new OutputStreamWriter((OutputStream)var1_2);
        var2_4 = var5_7;
        var0 = var4_6;
        var6_8 = new BufferedWriter(var7_9, 32768);
        var1_2 = var6_8;
        if (var3_5 != null) {
            var2_4 = var1_2;
            var0 = var1_2;
            ViewDebug.profileViewAndChildren(var3_5, (BufferedWriter)var1_2);
        } else {
            var2_4 = var1_2;
            var0 = var1_2;
            var1_2.write("-1 -1 -1");
            var2_4 = var1_2;
            var0 = var1_2;
            var1_2.newLine();
        }
        var2_4 = var1_2;
        var0 = var1_2;
        var1_2.write("DONE.");
        var2_4 = var1_2;
        var0 = var1_2;
        var1_2.newLine();
        var0 = var1_2;
lbl34: // 2 sources:
        do {
            var0.close();
            return;
            break;
        } while (true);
        {
            catch (Throwable var0_1) {
            }
            catch (Exception var1_3) {}
            var2_4 = var0;
            {
                Log.w("View", "Problem profiling the view:", var1_3);
                if (var0 == null) return;
                ** continue;
            }
        }
        if (var2_4 == null) throw var0_1;
        var2_4.close();
        throw var0_1;
    }

    private static void profileViewAndChildren(View view, RenderNode renderNode, BufferedWriter bufferedWriter, boolean bl) throws IOException {
        long l = 0L;
        long l2 = !bl && (view.mPrivateFlags & 2048) == 0 ? 0L : ViewDebug.profileViewMeasure(view);
        long l3 = !bl && (view.mPrivateFlags & 8192) == 0 ? 0L : ViewDebug.profileViewLayout(view);
        if (bl || !view.willNotDraw() || (view.mPrivateFlags & 32) != 0) {
            l = ViewDebug.profileViewDraw(view, renderNode);
        }
        bufferedWriter.write(String.valueOf(l2));
        bufferedWriter.write(32);
        bufferedWriter.write(String.valueOf(l3));
        bufferedWriter.write(32);
        bufferedWriter.write(String.valueOf(l));
        bufferedWriter.newLine();
        if (view instanceof ViewGroup) {
            view = (ViewGroup)view;
            int n = ((ViewGroup)view).getChildCount();
            for (int i = 0; i < n; ++i) {
                ViewDebug.profileViewAndChildren(((ViewGroup)view).getChildAt(i), renderNode, bufferedWriter, false);
            }
        }
    }

    public static void profileViewAndChildren(View view, BufferedWriter bufferedWriter) throws IOException {
        ViewDebug.profileViewAndChildren(view, RenderNode.create("ViewDebug", null), bufferedWriter, true);
    }

    private static long profileViewDraw(View view, RenderNode object) {
        Object object2 = view.getResources().getDisplayMetrics();
        if (object2 == null) {
            return 0L;
        }
        if (view.isHardwareAccelerated()) {
            RecordingCanvas recordingCanvas = ((RenderNode)object).beginRecording(((DisplayMetrics)object2).widthPixels, ((DisplayMetrics)object2).heightPixels);
            try {
                object2 = new _$$Lambda$ViewDebug$flFXZc7_CjFXx7_tYT59WSbUNjI(view, recordingCanvas);
                long l = ViewDebug.profileViewOperation(view, (ViewOperation)object2);
                return l;
            }
            finally {
                ((RenderNode)object).endRecording();
            }
        }
        object2 = Bitmap.createBitmap((DisplayMetrics)object2, ((DisplayMetrics)object2).widthPixels, ((DisplayMetrics)object2).heightPixels, Bitmap.Config.RGB_565);
        object = new Canvas((Bitmap)object2);
        try {
            _$$Lambda$ViewDebug$w986pBwzwNi77yEgLa3IWusjPNw _$$Lambda$ViewDebug$w986pBwzwNi77yEgLa3IWusjPNw = new _$$Lambda$ViewDebug$w986pBwzwNi77yEgLa3IWusjPNw(view, (Canvas)object);
            long l = ViewDebug.profileViewOperation(view, _$$Lambda$ViewDebug$w986pBwzwNi77yEgLa3IWusjPNw);
            return l;
        }
        finally {
            ((Canvas)object).setBitmap(null);
            ((Bitmap)object2).recycle();
        }
    }

    private static long profileViewLayout(View view) {
        return ViewDebug.profileViewOperation(view, new _$$Lambda$ViewDebug$inOytI2zZEgp1DJv8Cu4GjQVNiE(view));
    }

    private static long profileViewMeasure(View view) {
        return ViewDebug.profileViewOperation(view, new ViewOperation(){

            private void forceLayout(View view) {
                view.forceLayout();
                if (view instanceof ViewGroup) {
                    view = (ViewGroup)view;
                    int n = ((ViewGroup)view).getChildCount();
                    for (int i = 0; i < n; ++i) {
                        this.forceLayout(((ViewGroup)view).getChildAt(i));
                    }
                }
            }

            @Override
            public void pre() {
                this.forceLayout(View.this);
            }

            @Override
            public void run() {
                View view = View.this;
                view.measure(view.mOldWidthMeasureSpec, View.this.mOldHeightMeasureSpec);
            }
        });
    }

    private static long profileViewOperation(View view, ViewOperation object) {
        long[] arrl;
        block2 : {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            arrl = new long[1];
            view.post(new _$$Lambda$ViewDebug$5rTN0pemwbr3I3IL2E_xDBeDTDg((ViewOperation)object, arrl, countDownLatch));
            try {
                if (countDownLatch.await(4000L, TimeUnit.MILLISECONDS)) break block2;
                object = new StringBuilder();
                ((StringBuilder)object).append("Could not complete the profiling of the view ");
                ((StringBuilder)object).append(view);
                Log.w("View", ((StringBuilder)object).toString());
                return -1L;
            }
            catch (InterruptedException interruptedException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not complete the profiling of the view ");
                stringBuilder.append(view);
                Log.w("View", stringBuilder.toString());
                Thread.currentThread().interrupt();
                return -1L;
            }
        }
        return arrl[0];
    }

    private static void requestLayout(View view, String object) {
        if ((object = ViewDebug.findView(view, (String)object)) != null) {
            view.post(new Runnable(){

                @Override
                public void run() {
                    View.this.requestLayout();
                }
            });
        }
    }

    static Object resolveId(Context object, int n) {
        object = ((Context)object).getResources();
        if (n >= 0) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((Resources)object).getResourceTypeName(n));
                stringBuilder.append('/');
                stringBuilder.append(((Resources)object).getResourceEntryName(n));
                object = stringBuilder.toString();
            }
            catch (Resources.NotFoundException notFoundException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("id/");
                ((StringBuilder)object).append(ViewDebug.formatIntToHexString(n));
                object = ((StringBuilder)object).toString();
            }
        } else {
            object = "NO_ID";
        }
        return object;
    }

    public static void setLayoutParameter(View object, String string2, int n) throws NoSuchFieldException, IllegalAccessException {
        final ViewGroup.LayoutParams layoutParams = ((View)object).getLayoutParams();
        Field field = layoutParams.getClass().getField(string2);
        if (field.getType() == Integer.TYPE) {
            field.set(layoutParams, n);
            ((View)object).post(new Runnable(){

                @Override
                public void run() {
                    View.this.setLayoutParams(layoutParams);
                }
            });
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Only integer layout parameters can be set. Field ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" is of type ");
        ((StringBuilder)object).append(field.getType().getSimpleName());
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Deprecated
    public static void startHierarchyTracing(String string2, View view) {
    }

    @Deprecated
    public static void startRecyclerTracing(String string2, View view) {
    }

    public static AutoCloseable startRenderingCommandsCapture(View object, Executor executor, Callable<OutputStream> callable) {
        object = ((View)object).mAttachInfo;
        if (object != null) {
            if (((View.AttachInfo)object).mHandler.getLooper() == Looper.myLooper()) {
                object = ((View.AttachInfo)object).mThreadedRenderer;
                if (object != null) {
                    return new PictureCallbackHandler((HardwareRenderer)object, new _$$Lambda$ViewDebug$hyDSYptlxuUTTyRIONqWzWWVDB0(callable), executor);
                }
                return null;
            }
            throw new IllegalStateException("Called on the wrong thread. Must be called on the thread that owns the given View");
        }
        throw new IllegalArgumentException("Given view isn't attached");
    }

    @Deprecated
    public static AutoCloseable startRenderingCommandsCapture(View object, Executor executor, Function<Picture, Boolean> function) {
        object = ((View)object).mAttachInfo;
        if (object != null) {
            if (((View.AttachInfo)object).mHandler.getLooper() == Looper.myLooper()) {
                object = ((View.AttachInfo)object).mThreadedRenderer;
                if (object != null) {
                    return new PictureCallbackHandler((HardwareRenderer)object, function, executor);
                }
                return null;
            }
            throw new IllegalStateException("Called on the wrong thread. Must be called on the thread that owns the given View");
        }
        throw new IllegalArgumentException("Given view isn't attached");
    }

    @Deprecated
    public static void stopHierarchyTracing() {
    }

    @Deprecated
    public static void stopRecyclerTracing() {
    }

    @Deprecated
    public static void trace(View view, HierarchyTraceType hierarchyTraceType) {
    }

    @Deprecated
    public static void trace(View view, RecyclerTraceType recyclerTraceType, int ... arrn) {
    }

    private static void writeEntry(BufferedWriter bufferedWriter, String string2, String string3, String string4, Object object) throws IOException {
        bufferedWriter.write(string2);
        bufferedWriter.write(string3);
        bufferedWriter.write(string4);
        bufferedWriter.write("=");
        ViewDebug.writeValue(bufferedWriter, object);
        bufferedWriter.write(32);
    }

    private static void writeValue(BufferedWriter bufferedWriter, Object object) throws IOException {
        block3 : {
            block2 : {
                if (object == null) break block2;
                try {
                    object = object.toString().replace("\n", "\\n");
                }
                catch (Throwable throwable) {
                    bufferedWriter.write(String.valueOf("[EXCEPTION]".length()));
                    bufferedWriter.write(",");
                    bufferedWriter.write("[EXCEPTION]");
                    throw throwable;
                }
                bufferedWriter.write(String.valueOf(((String)object).length()));
                bufferedWriter.write(",");
                bufferedWriter.write((String)object);
                break block3;
            }
            bufferedWriter.write("4,null");
        }
    }

    public static interface CanvasProvider {
        public Bitmap createBitmap();

        public Canvas getCanvas(View var1, int var2, int var3);
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD, ElementType.METHOD})
    public static @interface CapturedViewProperty {
        public boolean retrieveReturn() default false;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD, ElementType.METHOD})
    public static @interface ExportedProperty {
        public String category() default "";

        public boolean deepExport() default false;

        public FlagToString[] flagMapping() default {};

        public boolean formatToHexString() default false;

        public boolean hasAdjacentMapping() default false;

        public IntToString[] indexMapping() default {};

        public IntToString[] mapping() default {};

        public String prefix() default "";

        public boolean resolveId() default false;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface FlagToString {
        public int equals();

        public int mask();

        public String name();

        public boolean outputIf() default true;
    }

    public static class HardwareCanvasProvider
    implements CanvasProvider {
        private Picture mPicture;

        @Override
        public Bitmap createBitmap() {
            this.mPicture.endRecording();
            return Bitmap.createBitmap(this.mPicture);
        }

        @Override
        public Canvas getCanvas(View view, int n, int n2) {
            this.mPicture = new Picture();
            return this.mPicture.beginRecording(n, n2);
        }
    }

    public static interface HierarchyHandler {
        public void dumpViewHierarchyWithProperties(BufferedWriter var1, int var2);

        public View findHierarchyView(String var1, int var2);
    }

    @Deprecated
    public static enum HierarchyTraceType {
        INVALIDATE,
        INVALIDATE_CHILD,
        INVALIDATE_CHILD_IN_PARENT,
        REQUEST_LAYOUT,
        ON_LAYOUT,
        ON_MEASURE,
        DRAW,
        BUILD_CACHE;
        
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface IntToString {
        public int from();

        public String to();
    }

    private static class PictureCallbackHandler
    implements AutoCloseable,
    HardwareRenderer.PictureCapturedCallback,
    Runnable {
        private final Function<Picture, Boolean> mCallback;
        private final Executor mExecutor;
        private final ReentrantLock mLock = new ReentrantLock(false);
        private final ArrayDeque<Picture> mQueue = new ArrayDeque(3);
        private Thread mRenderThread;
        private final HardwareRenderer mRenderer;
        private boolean mStopListening;

        private PictureCallbackHandler(HardwareRenderer hardwareRenderer, Function<Picture, Boolean> function, Executor executor) {
            this.mRenderer = hardwareRenderer;
            this.mCallback = function;
            this.mExecutor = executor;
            this.mRenderer.setPictureCaptureCallback(this);
        }

        @Override
        public void close() {
            this.mLock.lock();
            this.mStopListening = true;
            this.mLock.unlock();
            this.mRenderer.setPictureCaptureCallback(null);
        }

        @Override
        public void onPictureCaptured(Picture picture) {
            this.mLock.lock();
            if (this.mStopListening) {
                this.mLock.unlock();
                this.mRenderer.setPictureCaptureCallback(null);
                return;
            }
            if (this.mRenderThread == null) {
                this.mRenderThread = Thread.currentThread();
            }
            Picture picture2 = null;
            if (this.mQueue.size() == 3) {
                picture2 = this.mQueue.removeLast();
            }
            this.mQueue.add(picture);
            this.mLock.unlock();
            if (picture2 == null) {
                this.mExecutor.execute(this);
            } else {
                picture2.close();
            }
        }

        @Override
        public void run() {
            this.mLock.lock();
            Picture picture = this.mQueue.poll();
            boolean bl = this.mStopListening;
            this.mLock.unlock();
            if (Thread.currentThread() != this.mRenderThread) {
                if (bl) {
                    picture.close();
                    return;
                }
                if (!this.mCallback.apply(picture).booleanValue()) {
                    this.close();
                }
                return;
            }
            this.close();
            throw new IllegalStateException("ViewDebug#startRenderingCommandsCapture must be given an executor that invokes asynchronously");
        }
    }

    @Deprecated
    public static enum RecyclerTraceType {
        NEW_VIEW,
        BIND_VIEW,
        RECYCLE_FROM_ACTIVE_HEAP,
        RECYCLE_FROM_SCRAP_HEAP,
        MOVE_TO_SCRAP_HEAP,
        MOVE_FROM_ACTIVE_TO_SCRAP_HEAP;
        
    }

    public static class SoftwareCanvasProvider
    implements CanvasProvider {
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private boolean mEnabledHwBitmapsInSwMode;

        @Override
        public Bitmap createBitmap() {
            this.mCanvas.setBitmap(null);
            this.mCanvas.setHwBitmapsInSwModeEnabled(this.mEnabledHwBitmapsInSwMode);
            return this.mBitmap;
        }

        @Override
        public Canvas getCanvas(View view, int n, int n2) {
            this.mBitmap = Bitmap.createBitmap(view.getResources().getDisplayMetrics(), n, n2, Bitmap.Config.ARGB_8888);
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null) {
                bitmap.setDensity(view.getResources().getDisplayMetrics().densityDpi);
                if (view.mAttachInfo != null) {
                    this.mCanvas = view.mAttachInfo.mCanvas;
                }
                if (this.mCanvas == null) {
                    this.mCanvas = new Canvas();
                }
                this.mEnabledHwBitmapsInSwMode = this.mCanvas.isHwBitmapsInSwModeEnabled();
                this.mCanvas.setBitmap(this.mBitmap);
                return this.mCanvas;
            }
            throw new OutOfMemoryError();
        }
    }

    static interface ViewOperation {
        default public void pre() {
        }

        public void run();
    }

}

