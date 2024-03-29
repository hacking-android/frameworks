/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.io;

import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileSystem {
    public static final FileSystem SYSTEM = new FileSystem(){

        @Override
        public Sink appendingSink(File file) throws FileNotFoundException {
            try {
                Sink sink = Okio.appendingSink(file);
                return sink;
            }
            catch (FileNotFoundException fileNotFoundException) {
                file.getParentFile().mkdirs();
                return Okio.appendingSink(file);
            }
        }

        @Override
        public void delete(File file) throws IOException {
            if (!file.delete() && file.exists()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("failed to delete ");
                stringBuilder.append(file);
                throw new IOException(stringBuilder.toString());
            }
        }

        @Override
        public void deleteContents(File object) throws IOException {
            Object object2 = ((File)object).listFiles();
            if (object2 != null) {
                int n = ((File[])object2).length;
                for (int i = 0; i < n; ++i) {
                    object = object2[i];
                    if (((File)object).isDirectory()) {
                        this.deleteContents((File)object);
                    }
                    if (((File)object).delete()) {
                        continue;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("failed to delete ");
                    ((StringBuilder)object2).append(object);
                    throw new IOException(((StringBuilder)object2).toString());
                }
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("not a readable directory: ");
            ((StringBuilder)object2).append(object);
            throw new IOException(((StringBuilder)object2).toString());
        }

        @Override
        public boolean exists(File file) throws IOException {
            return file.exists();
        }

        @Override
        public void rename(File file, File file2) throws IOException {
            this.delete(file2);
            if (file.renameTo(file2)) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("failed to rename ");
            stringBuilder.append(file);
            stringBuilder.append(" to ");
            stringBuilder.append(file2);
            throw new IOException(stringBuilder.toString());
        }

        @Override
        public Sink sink(File file) throws FileNotFoundException {
            try {
                Sink sink = Okio.sink(file);
                return sink;
            }
            catch (FileNotFoundException fileNotFoundException) {
                file.getParentFile().mkdirs();
                return Okio.sink(file);
            }
        }

        @Override
        public long size(File file) {
            return file.length();
        }

        @Override
        public Source source(File file) throws FileNotFoundException {
            return Okio.source(file);
        }
    };

    public Sink appendingSink(File var1) throws FileNotFoundException;

    public void delete(File var1) throws IOException;

    public void deleteContents(File var1) throws IOException;

    public boolean exists(File var1) throws IOException;

    public void rename(File var1, File var2) throws IOException;

    public Sink sink(File var1) throws FileNotFoundException;

    public long size(File var1);

    public Source source(File var1) throws FileNotFoundException;

}

