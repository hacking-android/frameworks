/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class Icon
implements Parcelable {
    public static final Parcelable.Creator<Icon> CREATOR;
    static final BlendMode DEFAULT_BLEND_MODE;
    public static final int MIN_ASHMEM_ICON_SIZE = 131072;
    private static final String TAG = "Icon";
    public static final int TYPE_ADAPTIVE_BITMAP = 5;
    public static final int TYPE_BITMAP = 1;
    public static final int TYPE_DATA = 3;
    public static final int TYPE_RESOURCE = 2;
    public static final int TYPE_URI = 4;
    private static final int VERSION_STREAM_SERIALIZER = 1;
    private BlendMode mBlendMode;
    private int mInt1;
    private int mInt2;
    private Object mObj1;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String mString1;
    private ColorStateList mTintList;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final int mType;

    static {
        DEFAULT_BLEND_MODE = Drawable.DEFAULT_BLEND_MODE;
        CREATOR = new Parcelable.Creator<Icon>(){

            @Override
            public Icon createFromParcel(Parcel parcel) {
                return new Icon(parcel);
            }

            public Icon[] newArray(int n) {
                return new Icon[n];
            }
        };
    }

    private Icon(int n) {
        this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        this.mType = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Icon(Parcel object) {
        block7 : {
            block3 : {
                int n;
                block4 : {
                    byte[] arrby;
                    block5 : {
                        block6 : {
                            this(((Parcel)object).readInt());
                            n = this.mType;
                            if (n == 1) break block3;
                            if (n == 2) break block4;
                            if (n == 3) break block5;
                            if (n == 4) break block6;
                            if (n != 5) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("invalid ");
                                ((StringBuilder)object).append(this.getClass().getSimpleName());
                                ((StringBuilder)object).append(" type in parcel: ");
                                ((StringBuilder)object).append(this.mType);
                                throw new RuntimeException(((StringBuilder)object).toString());
                            }
                            break block3;
                        }
                        this.mString1 = ((Parcel)object).readString();
                        break block7;
                    }
                    n = ((Parcel)object).readInt();
                    if (n != (arrby = ((Parcel)object).readBlob()).length) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("internal unparceling error: blob length (");
                        ((StringBuilder)object).append(arrby.length);
                        ((StringBuilder)object).append(") != expected length (");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(")");
                        throw new RuntimeException(((StringBuilder)object).toString());
                    }
                    this.mInt1 = n;
                    this.mObj1 = arrby;
                    break block7;
                }
                String string2 = ((Parcel)object).readString();
                n = ((Parcel)object).readInt();
                this.mString1 = string2;
                this.mInt1 = n;
                break block7;
            }
            this.mObj1 = Bitmap.CREATOR.createFromParcel((Parcel)object);
        }
        if (((Parcel)object).readInt() == 1) {
            this.mTintList = ColorStateList.CREATOR.createFromParcel((Parcel)object);
        }
        this.mBlendMode = BlendMode.fromValue(((Parcel)object).readInt());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Icon createFromStream(InputStream inputStream) throws IOException {
        if (((DataInputStream)(inputStream = new DataInputStream(inputStream))).readInt() < 1) return null;
        int n = ((DataInputStream)inputStream).readByte();
        if (n == 1) return Icon.createWithBitmap(BitmapFactory.decodeStream(inputStream));
        if (n == 2) return Icon.createWithResource(((DataInputStream)inputStream).readUTF(), ((DataInputStream)inputStream).readInt());
        if (n != 3) {
            if (n == 4) return Icon.createWithContentUri(((DataInputStream)inputStream).readUTF());
            if (n != 5) return null;
            return Icon.createWithAdaptiveBitmap(BitmapFactory.decodeStream(inputStream));
        }
        n = ((DataInputStream)inputStream).readInt();
        byte[] arrby = new byte[n];
        ((DataInputStream)inputStream).read(arrby, 0, n);
        return Icon.createWithData(arrby, 0, n);
    }

    public static Icon createWithAdaptiveBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            Icon icon = new Icon(5);
            icon.setBitmap(bitmap);
            return icon;
        }
        throw new IllegalArgumentException("Bitmap must not be null.");
    }

    public static Icon createWithBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            Icon icon = new Icon(1);
            icon.setBitmap(bitmap);
            return icon;
        }
        throw new IllegalArgumentException("Bitmap must not be null.");
    }

    public static Icon createWithContentUri(Uri uri) {
        if (uri != null) {
            Icon icon = new Icon(4);
            icon.mString1 = uri.toString();
            return icon;
        }
        throw new IllegalArgumentException("Uri must not be null.");
    }

    public static Icon createWithContentUri(String string2) {
        if (string2 != null) {
            Icon icon = new Icon(4);
            icon.mString1 = string2;
            return icon;
        }
        throw new IllegalArgumentException("Uri must not be null.");
    }

    public static Icon createWithData(byte[] arrby, int n, int n2) {
        if (arrby != null) {
            Icon icon = new Icon(3);
            icon.mObj1 = arrby;
            icon.mInt1 = n2;
            icon.mInt2 = n;
            return icon;
        }
        throw new IllegalArgumentException("Data must not be null.");
    }

    public static Icon createWithFilePath(String string2) {
        if (string2 != null) {
            Icon icon = new Icon(4);
            icon.mString1 = string2;
            return icon;
        }
        throw new IllegalArgumentException("Path must not be null.");
    }

    public static Icon createWithResource(Context context, int n) {
        if (context != null) {
            Icon icon = new Icon(2);
            icon.mInt1 = n;
            icon.mString1 = context.getPackageName();
            return icon;
        }
        throw new IllegalArgumentException("Context must not be null.");
    }

    @UnsupportedAppUsage
    public static Icon createWithResource(Resources resources, int n) {
        if (resources != null) {
            Icon icon = new Icon(2);
            icon.mInt1 = n;
            icon.mString1 = resources.getResourcePackageName(n);
            return icon;
        }
        throw new IllegalArgumentException("Resource must not be null.");
    }

    public static Icon createWithResource(String string2, int n) {
        if (string2 != null) {
            Icon icon = new Icon(2);
            icon.mInt1 = n;
            icon.mString1 = string2;
            return icon;
        }
        throw new IllegalArgumentException("Resource package name must not be null.");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Drawable loadDrawableInner(Context object) {
        int n = this.mType;
        if (n == 1) return new BitmapDrawable(((Context)object).getResources(), this.getBitmap());
        if (n != 2) {
            if (n == 3) return new BitmapDrawable(((Context)object).getResources(), BitmapFactory.decodeByteArray(this.getDataBytes(), this.getDataOffset(), this.getDataLength()));
            if (n != 4) {
                if (n == 5) return new AdaptiveIconDrawable(null, new BitmapDrawable(((Context)object).getResources(), this.getBitmap()));
                return null;
            }
            Uri uri = this.getUri();
            Object object2 = uri.getScheme();
            Object object3 = null;
            FileInputStream fileInputStream = null;
            if (!"content".equals(object2) && !"file".equals(object2)) {
                try {
                    object2 = new File(this.mString1);
                    fileInputStream = new FileInputStream((File)object2);
                    object3 = fileInputStream;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unable to load image from path: ");
                    ((StringBuilder)object2).append(uri);
                    Log.w(TAG, ((StringBuilder)object2).toString(), fileNotFoundException);
                }
            } else {
                try {
                    object3 = ((Context)object).getContentResolver().openInputStream(uri);
                }
                catch (Exception exception) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Unable to load image from URI: ");
                    ((StringBuilder)object3).append(uri);
                    Log.w(TAG, ((StringBuilder)object3).toString(), exception);
                    object3 = fileInputStream;
                }
            }
            if (object3 == null) return null;
            return new BitmapDrawable(((Context)object).getResources(), BitmapFactory.decodeStream((InputStream)object3));
        }
        if (this.getResources() == null) {
            Object object4 = this.getResPackage();
            String string2 = object4;
            if (TextUtils.isEmpty((CharSequence)object4)) {
                string2 = ((Context)object).getPackageName();
            }
            if ("android".equals(string2)) {
                this.mObj1 = Resources.getSystem();
            } else {
                ApplicationInfo applicationInfo;
                object4 = ((Context)object).getPackageManager();
                try {
                    applicationInfo = ((PackageManager)object4).getApplicationInfo(string2, 8192);
                    if (applicationInfo == null) return null;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    Log.e(TAG, String.format("Unable to find pkg=%s for icon %s", string2, this), nameNotFoundException);
                    return null;
                }
                this.mObj1 = ((PackageManager)object4).getResourcesForApplication(applicationInfo);
            }
        }
        try {
            return this.getResources().getDrawable(this.getResId(), ((Context)object).getTheme());
        }
        catch (RuntimeException runtimeException) {
            Log.e(TAG, String.format("Unable to load resource 0x%08x from pkg=%s", this.getResId(), this.getResPackage()), runtimeException);
        }
        return null;
    }

    public static Bitmap scaleDownIfNecessary(Bitmap bitmap, int n, int n2) {
        Bitmap bitmap2;
        block3 : {
            int n3;
            int n4;
            block2 : {
                n3 = bitmap.getWidth();
                n4 = bitmap.getHeight();
                if (n3 > n) break block2;
                bitmap2 = bitmap;
                if (n4 <= n2) break block3;
            }
            float f = Math.min((float)n / (float)n3, (float)n2 / (float)n4);
            bitmap2 = Bitmap.createScaledBitmap(bitmap, Math.max(1, (int)((float)n3 * f)), Math.max(1, (int)((float)n4 * f)), true);
        }
        return bitmap2;
    }

    private void setBitmap(Bitmap bitmap) {
        this.mObj1 = bitmap;
    }

    private static final String typeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return "UNKNOWN";
                        }
                        return "BITMAP_MASKABLE";
                    }
                    return "URI";
                }
                return "DATA";
            }
            return "RESOURCE";
        }
        return "BITMAP";
    }

    public void convertToAshmem() {
        int n = this.mType;
        if ((n == 1 || n == 5) && this.getBitmap().isMutable() && this.getBitmap().getAllocationByteCount() >= 131072) {
            this.setBitmap(this.getBitmap().createAshmemBitmap());
        }
    }

    @Override
    public int describeContents() {
        int n;
        block0 : {
            int n2 = this.mType;
            n = 1;
            if (n2 == 1 || n2 == 5 || n2 == 3) break block0;
            n = 0;
        }
        return n;
    }

    @UnsupportedAppUsage
    public Bitmap getBitmap() {
        int n = this.mType;
        if (n != 1 && n != 5) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("called getBitmap() on ");
            stringBuilder.append(this);
            throw new IllegalStateException(stringBuilder.toString());
        }
        return (Bitmap)this.mObj1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public byte[] getDataBytes() {
        if (this.mType == 3) {
            synchronized (this) {
                return (byte[])this.mObj1;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getDataBytes() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public int getDataLength() {
        if (this.mType == 3) {
            synchronized (this) {
                return this.mInt1;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getDataLength() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getDataOffset() {
        if (this.mType == 3) {
            synchronized (this) {
                return this.mInt2;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getDataOffset() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int getResId() {
        if (this.mType == 2) {
            return this.mInt1;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getResId() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public String getResPackage() {
        if (this.mType == 2) {
            return this.mString1;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getResPackage() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public Resources getResources() {
        if (this.mType == 2) {
            return (Resources)this.mObj1;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getResources() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @IconType
    public int getType() {
        return this.mType;
    }

    public Uri getUri() {
        return Uri.parse(this.getUriString());
    }

    public String getUriString() {
        if (this.mType == 4) {
            return this.mString1;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getUriString() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public boolean hasTint() {
        boolean bl = this.mTintList != null || this.mBlendMode != DEFAULT_BLEND_MODE;
        return bl;
    }

    public Drawable loadDrawable(Context object) {
        if ((object = this.loadDrawableInner((Context)object)) != null && (this.mTintList != null || this.mBlendMode != DEFAULT_BLEND_MODE)) {
            ((Drawable)object).mutate();
            ((Drawable)object).setTintList(this.mTintList);
            ((Drawable)object).setTintBlendMode(this.mBlendMode);
        }
        return object;
    }

    public Drawable loadDrawableAsUser(Context context, int n) {
        if (this.mType == 2) {
            Object object = this.getResPackage();
            String string2 = object;
            if (TextUtils.isEmpty((CharSequence)object)) {
                string2 = context.getPackageName();
            }
            if (this.getResources() == null && !this.getResPackage().equals("android")) {
                object = context.getPackageManager();
                try {
                    this.mObj1 = ((PackageManager)object).getResourcesForApplicationAsUser(string2, n);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    Log.e(TAG, String.format("Unable to find pkg=%s user=%d", this.getResPackage(), n), nameNotFoundException);
                }
            }
        }
        return this.loadDrawable(context);
    }

    public void loadDrawableAsync(Context context, OnDrawableLoadedListener onDrawableLoadedListener, Handler handler) {
        new LoadDrawableTask(context, handler, onDrawableLoadedListener).runAsync();
    }

    public void loadDrawableAsync(Context context, Message message) {
        if (message.getTarget() != null) {
            new LoadDrawableTask(context, message).runAsync();
            return;
        }
        throw new IllegalArgumentException("callback message must have a target handler");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean sameAs(Icon var1_1) {
        block5 : {
            var2_2 = true;
            var3_3 = true;
            var4_4 = true;
            if (var1_1 == this) {
                return true;
            }
            if (this.mType != var1_1.getType()) {
                return false;
            }
            var5_5 = this.mType;
            if (var5_5 == 1) break block5;
            if (var5_5 == 2) ** GOTO lbl20
            if (var5_5 != 3) {
                if (var5_5 == 4) return Objects.equals(this.getUriString(), var1_1.getUriString());
                if (var5_5 != 5) {
                    return false;
                }
            } else {
                if (this.getDataLength() != var1_1.getDataLength()) return false;
                if (this.getDataOffset() != var1_1.getDataOffset()) return false;
                if (Arrays.equals(this.getDataBytes(), var1_1.getDataBytes()) == false) return false;
                return var4_4;
lbl20: // 1 sources:
                if (this.getResId() != var1_1.getResId()) return false;
                if (Objects.equals(this.getResPackage(), var1_1.getResPackage()) == false) return false;
                return var2_2;
            }
        }
        if (this.getBitmap() != var1_1.getBitmap()) return false;
        return var3_3;
    }

    public void scaleDownIfNecessary(int n, int n2) {
        int n3 = this.mType;
        if (n3 != 1 && n3 != 5) {
            return;
        }
        this.setBitmap(Icon.scaleDownIfNecessary(this.getBitmap(), n, n2));
    }

    public Icon setTint(int n) {
        return this.setTintList(ColorStateList.valueOf(n));
    }

    public Icon setTintBlendMode(BlendMode blendMode) {
        this.mBlendMode = blendMode;
        return this;
    }

    public Icon setTintList(ColorStateList colorStateList) {
        this.mTintList = colorStateList;
        return this;
    }

    public Icon setTintMode(PorterDuff.Mode mode) {
        this.mBlendMode = BlendMode.fromValue(mode.nativeInt);
        return this;
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder;
        block8 : {
            block4 : {
                block5 : {
                    block6 : {
                        block7 : {
                            stringBuilder = new StringBuilder("Icon(typ=").append(Icon.typeToString(this.mType));
                            n = this.mType;
                            if (n == 1) break block4;
                            if (n == 2) break block5;
                            if (n == 3) break block6;
                            if (n == 4) break block7;
                            if (n == 5) break block4;
                            break block8;
                        }
                        stringBuilder.append(" uri=");
                        stringBuilder.append(this.getUriString());
                        break block8;
                    }
                    stringBuilder.append(" len=");
                    stringBuilder.append(this.getDataLength());
                    if (this.getDataOffset() != 0) {
                        stringBuilder.append(" off=");
                        stringBuilder.append(this.getDataOffset());
                    }
                    break block8;
                }
                stringBuilder.append(" pkg=");
                stringBuilder.append(this.getResPackage());
                stringBuilder.append(" id=");
                stringBuilder.append(String.format("0x%08x", this.getResId()));
                break block8;
            }
            stringBuilder.append(" size=");
            stringBuilder.append(this.getBitmap().getWidth());
            stringBuilder.append("x");
            stringBuilder.append(this.getBitmap().getHeight());
        }
        if (this.mTintList != null) {
            stringBuilder.append(" tint=");
            int[] arrn = this.mTintList.getColors();
            int n2 = arrn.length;
            String string2 = "";
            for (n = 0; n < n2; ++n) {
                stringBuilder.append(String.format("%s0x%08x", string2, arrn[n]));
                string2 = "|";
            }
        }
        if (this.mBlendMode != DEFAULT_BLEND_MODE) {
            stringBuilder.append(" mode=");
            stringBuilder.append((Object)this.mBlendMode);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        block6 : {
            block2 : {
                block3 : {
                    block4 : {
                        block5 : {
                            parcel.writeInt(this.mType);
                            int n2 = this.mType;
                            if (n2 == 1) break block2;
                            if (n2 == 2) break block3;
                            if (n2 == 3) break block4;
                            if (n2 == 4) break block5;
                            if (n2 == 5) break block2;
                            break block6;
                        }
                        parcel.writeString(this.getUriString());
                        break block6;
                    }
                    parcel.writeInt(this.getDataLength());
                    parcel.writeBlob(this.getDataBytes(), this.getDataOffset(), this.getDataLength());
                    break block6;
                }
                parcel.writeString(this.getResPackage());
                parcel.writeInt(this.getResId());
                break block6;
            }
            this.getBitmap();
            this.getBitmap().writeToParcel(parcel, n);
        }
        if (this.mTintList == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mTintList.writeToParcel(parcel, n);
        }
        parcel.writeInt(BlendMode.toValue(this.mBlendMode));
    }

    public void writeToStream(OutputStream outputStream) throws IOException {
        block4 : {
            block0 : {
                block1 : {
                    block2 : {
                        block3 : {
                            outputStream = new DataOutputStream(outputStream);
                            ((DataOutputStream)outputStream).writeInt(1);
                            ((DataOutputStream)outputStream).writeByte(this.mType);
                            int n = this.mType;
                            if (n == 1) break block0;
                            if (n == 2) break block1;
                            if (n == 3) break block2;
                            if (n == 4) break block3;
                            if (n == 5) break block0;
                            break block4;
                        }
                        ((DataOutputStream)outputStream).writeUTF(this.getUriString());
                        break block4;
                    }
                    ((DataOutputStream)outputStream).writeInt(this.getDataLength());
                    ((DataOutputStream)outputStream).write(this.getDataBytes(), this.getDataOffset(), this.getDataLength());
                    break block4;
                }
                ((DataOutputStream)outputStream).writeUTF(this.getResPackage());
                ((DataOutputStream)outputStream).writeInt(this.getResId());
                break block4;
            }
            this.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        }
    }

    public static @interface IconType {
    }

    private class LoadDrawableTask
    implements Runnable {
        final Context mContext;
        final Message mMessage;

        public LoadDrawableTask(Context context, Handler handler, final OnDrawableLoadedListener onDrawableLoadedListener) {
            this.mContext = context;
            this.mMessage = Message.obtain(handler, new Runnable(){

                @Override
                public void run() {
                    onDrawableLoadedListener.onDrawableLoaded((Drawable)LoadDrawableTask.this.mMessage.obj);
                }
            });
        }

        public LoadDrawableTask(Context context, Message message) {
            this.mContext = context;
            this.mMessage = message;
        }

        @Override
        public void run() {
            this.mMessage.obj = Icon.this.loadDrawable(this.mContext);
            this.mMessage.sendToTarget();
        }

        public void runAsync() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }

    }

    public static interface OnDrawableLoadedListener {
        public void onDrawableLoaded(Drawable var1);
    }

}

