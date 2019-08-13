/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.content.Context;
import android.content.res.Resources;
import android.gesture.GestureLibrary;
import android.gesture.GestureStore;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;

public final class GestureLibraries {
    private GestureLibraries() {
    }

    public static GestureLibrary fromFile(File file) {
        return new FileGestureLibrary(file);
    }

    public static GestureLibrary fromFile(String string2) {
        return GestureLibraries.fromFile(new File(string2));
    }

    public static GestureLibrary fromPrivateFile(Context context, String string2) {
        return GestureLibraries.fromFile(context.getFileStreamPath(string2));
    }

    public static GestureLibrary fromRawResource(Context context, int n) {
        return new ResourceGestureLibrary(context, n);
    }

    private static class FileGestureLibrary
    extends GestureLibrary {
        private final File mPath;

        public FileGestureLibrary(File file) {
            this.mPath = file;
        }

        @Override
        public boolean isReadOnly() {
            return this.mPath.canWrite() ^ true;
        }

        @Override
        public boolean load() {
            boolean bl;
            block4 : {
                boolean bl2 = false;
                boolean bl3 = false;
                Serializable serializable = this.mPath;
                bl = bl2;
                if (!((File)serializable).exists()) break block4;
                bl = bl2;
                if (((File)serializable).canRead()) {
                    try {
                        GestureStore gestureStore = this.mStore;
                        FileInputStream fileInputStream = new FileInputStream((File)serializable);
                        gestureStore.load(fileInputStream, true);
                        bl = true;
                    }
                    catch (IOException iOException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Could not load the gesture library from ");
                        stringBuilder.append(this.mPath);
                        Log.d("Gestures", stringBuilder.toString(), iOException);
                        bl = bl2;
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Could not load the gesture library from ");
                        ((StringBuilder)serializable).append(this.mPath);
                        Log.d("Gestures", ((StringBuilder)serializable).toString(), fileNotFoundException);
                        bl = bl3;
                    }
                }
            }
            return bl;
        }

        @Override
        public boolean save() {
            if (!this.mStore.hasChanged()) {
                return true;
            }
            Serializable serializable = this.mPath;
            Object object = ((File)serializable).getParentFile();
            if (!((File)object).exists() && !((File)object).mkdirs()) {
                return false;
            }
            boolean bl = false;
            boolean bl2 = false;
            try {
                ((File)serializable).createNewFile();
                GestureStore gestureStore = this.mStore;
                object = new FileOutputStream((File)serializable);
                gestureStore.save((OutputStream)object, true);
                bl2 = true;
            }
            catch (IOException iOException) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Could not save the gesture library in ");
                ((StringBuilder)serializable).append(this.mPath);
                Log.d("Gestures", ((StringBuilder)serializable).toString(), iOException);
                bl2 = bl;
            }
            catch (FileNotFoundException fileNotFoundException) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Could not save the gesture library in ");
                ((StringBuilder)serializable).append(this.mPath);
                Log.d("Gestures", ((StringBuilder)serializable).toString(), fileNotFoundException);
            }
            return bl2;
        }
    }

    private static class ResourceGestureLibrary
    extends GestureLibrary {
        private final WeakReference<Context> mContext;
        private final int mResourceId;

        public ResourceGestureLibrary(Context context, int n) {
            this.mContext = new WeakReference<Context>(context);
            this.mResourceId = n;
        }

        @Override
        public boolean isReadOnly() {
            return true;
        }

        @Override
        public boolean load() {
            boolean bl = false;
            Context context = (Context)this.mContext.get();
            boolean bl2 = bl;
            if (context != null) {
                Object object = context.getResources().openRawResource(this.mResourceId);
                try {
                    this.mStore.load((InputStream)object, true);
                    bl2 = true;
                }
                catch (IOException iOException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Could not load the gesture library from raw resource ");
                    ((StringBuilder)object).append(context.getResources().getResourceName(this.mResourceId));
                    Log.d("Gestures", ((StringBuilder)object).toString(), iOException);
                    bl2 = bl;
                }
            }
            return bl2;
        }

        @Override
        public boolean save() {
            return false;
        }
    }

}

