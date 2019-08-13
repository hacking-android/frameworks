/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.Int64Ref
 *  libcore.io.IoUtils
 */
package com.android.internal.content;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.AbstractCursor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.FileObserver;
import android.os.FileUtils;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
import android.provider.MediaStore;
import android.provider.MetadataReader;
import android.system.Int64Ref;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.content._$$Lambda$FileSystemProvider$y9rjeYFpkvVjwD2Whw_ujCM_C7Y;
import com.android.internal.util.ArrayUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import libcore.io.IoUtils;

public abstract class FileSystemProvider
extends DocumentsProvider {
    private static final boolean LOG_INOTIFY = false;
    protected static final String SUPPORTED_QUERY_ARGS = FileSystemProvider.joinNewline("android:query-arg-display-name", "android:query-arg-file-size-over", "android:query-arg-last-modified-after", "android:query-arg-mime-types");
    private static final String TAG = "FileSystemProvider";
    private String[] mDefaultProjection;
    private Handler mHandler;
    @GuardedBy(value={"mObservers"})
    private final ArrayMap<File, DirectoryObserver> mObservers = new ArrayMap();

    private String getDocumentType(String string2, File file) throws FileNotFoundException {
        if (file.isDirectory()) {
            return "vnd.android.document/directory";
        }
        int n = string2.lastIndexOf(46);
        if (n >= 0) {
            string2 = string2.substring(n + 1).toLowerCase();
            string2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(string2);
            if (string2 != null) {
                return string2;
            }
        }
        return "application/octet-stream";
    }

    private static String joinNewline(String ... arrstring) {
        return TextUtils.join((CharSequence)"\n", arrstring);
    }

    private boolean matchSearchQueryArguments(File file, Bundle bundle) {
        String string2;
        if (file == null) {
            return false;
        }
        String string3 = file.getName();
        if (file.isDirectory()) {
            string2 = "vnd.android.document/directory";
        } else {
            int n = string3.lastIndexOf(46);
            if (n < 0) {
                return false;
            }
            string2 = string3.substring(n + 1);
            string2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(string2);
        }
        return DocumentsContract.matchSearchQueryArguments(bundle, string3, string2, file.lastModified(), file.length());
    }

    private void moveInMediaStore(File file, File file2) {
        if (file != null) {
            MediaStore.scanFile(this.getContext(), file);
        }
        if (file2 != null) {
            MediaStore.scanFile(this.getContext(), file2);
        }
    }

    private void removeFromMediaStore(File object, boolean bl) throws FileNotFoundException {
        if (object != null) {
            long l = Binder.clearCallingIdentity();
            try {
                ContentResolver contentResolver = this.getContext().getContentResolver();
                Uri uri = MediaStore.Files.getContentUri("external");
                if (bl) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(((File)object).getAbsolutePath());
                    stringBuilder.append("/");
                    String string2 = stringBuilder.toString();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(string2);
                    stringBuilder.append("%");
                    contentResolver.delete(uri, "_data LIKE ?1 AND lower(substr(_data,1,?2))=lower(?3)", new String[]{stringBuilder.toString(), Integer.toString(string2.length()), string2});
                }
                object = ((File)object).getAbsolutePath();
                contentResolver.delete(uri, "_data LIKE ?1 AND lower(_data)=lower(?2)", new String[]{object, object});
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }
    }

    private String[] resolveProjection(String[] arrstring) {
        block0 : {
            if (arrstring != null) break block0;
            arrstring = this.mDefaultProjection;
        }
        return arrstring;
    }

    private void scanFile(File file) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(file));
        this.getContext().sendBroadcast(intent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void startObserving(File file, Uri uri, DirectoryCursor directoryCursor) {
        ArrayMap<File, DirectoryObserver> arrayMap = this.mObservers;
        synchronized (arrayMap) {
            DirectoryObserver directoryObserver;
            DirectoryObserver directoryObserver2 = directoryObserver = this.mObservers.get(file);
            if (directoryObserver == null) {
                directoryObserver2 = new DirectoryObserver(file, this.getContext().getContentResolver(), uri);
                directoryObserver2.startWatching();
                this.mObservers.put(file, directoryObserver2);
            }
            directoryObserver2.mCursors.add(directoryCursor);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void stopObserving(File file, DirectoryCursor directoryCursor) {
        ArrayMap<File, DirectoryObserver> arrayMap = this.mObservers;
        synchronized (arrayMap) {
            DirectoryObserver directoryObserver = this.mObservers.get(file);
            if (directoryObserver == null) {
                return;
            }
            directoryObserver.mCursors.remove(directoryCursor);
            if (directoryObserver.mCursors.size() == 0) {
                this.mObservers.remove(file);
                directoryObserver.stopWatching();
            }
            return;
        }
    }

    protected abstract Uri buildNotificationUri(String var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String createDocument(String object, String charSequence, String object2) throws FileNotFoundException {
        block7 : {
            object2 = FileUtils.buildValidFatFilename((String)object2);
            if (!((File)(object = this.getFileForDocId((String)object))).isDirectory()) {
                throw new IllegalArgumentException("Parent document isn't a directory");
            }
            object2 = FileUtils.buildUniqueFile((File)object, (String)charSequence, (String)object2);
            if ("vnd.android.document/directory".equals(charSequence)) {
                if (!((File)object2).mkdir()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Failed to mkdir ");
                    ((StringBuilder)object).append(object2);
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                object = this.getDocIdForFile((File)object2);
                this.onDocIdChanged((String)object);
            } else {
                if (!((File)object2).createNewFile()) break block7;
                object = this.getDocIdForFile((File)object2);
                this.onDocIdChanged((String)object);
            }
            MediaStore.scanFile(this.getContext(), (File)object2);
            return object;
        }
        try {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Failed to touch ");
            ((StringBuilder)charSequence).append(object2);
            object = new IllegalStateException(((StringBuilder)charSequence).toString());
            throw object;
        }
        catch (IOException iOException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Failed to touch ");
            ((StringBuilder)charSequence).append(object2);
            ((StringBuilder)charSequence).append(": ");
            ((StringBuilder)charSequence).append(iOException);
            throw new IllegalStateException(((StringBuilder)charSequence).toString());
        }
    }

    @Override
    public void deleteDocument(String charSequence) throws FileNotFoundException {
        File file = this.getFileForDocId((String)charSequence);
        File file2 = this.getFileForDocId((String)charSequence, true);
        boolean bl = file.isDirectory();
        if (bl) {
            FileUtils.deleteContents(file);
        }
        if (file.delete()) {
            this.onDocIdChanged((String)charSequence);
            this.removeFromMediaStore(file2, bl);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Failed to delete ");
        ((StringBuilder)charSequence).append(file);
        throw new IllegalStateException(((StringBuilder)charSequence).toString());
    }

    protected final List<String> findDocumentPath(File serializable, File file) throws FileNotFoundException {
        if (file.exists()) {
            if (FileUtils.contains((File)serializable, file)) {
                LinkedList<String> linkedList = new LinkedList<String>();
                while (file != null && FileUtils.contains((File)serializable, file)) {
                    linkedList.addFirst(this.getDocIdForFile(file));
                    file = file.getParentFile();
                }
                return linkedList;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(file);
            stringBuilder.append(" is not found under ");
            stringBuilder.append(serializable);
            throw new FileNotFoundException(stringBuilder.toString());
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(file);
        ((StringBuilder)serializable).append(" is not found.");
        throw new FileNotFoundException(((StringBuilder)serializable).toString());
    }

    protected abstract String getDocIdForFile(File var1) throws FileNotFoundException;

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Bundle getDocumentMetadata(String object) throws FileNotFoundException {
        Throwable throwable2222;
        FileInputStream fileInputStream;
        File file = this.getFileForDocId((String)object);
        if (!file.exists()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't find the file for documentId: ");
            stringBuilder.append((String)object);
            throw new FileNotFoundException(stringBuilder.toString());
        }
        String string2 = this.getDocumentType((String)object);
        if ("vnd.android.document/directory".equals(string2)) {
            Object object2;
            final Int64Ref int64Ref = new Int64Ref(0L);
            object = new Int64Ref(0L);
            try {
                object2 = FileSystems.getDefault().getPath(file.getAbsolutePath(), new String[0]);
                FileVisitor<Path> fileVisitor = new FileVisitor<Path>((Int64Ref)object){
                    final /* synthetic */ Int64Ref val$treeSize;
                    {
                        this.val$treeSize = int64Ref2;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path path, IOException iOException) {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) {
                        path = int64Ref;
                        ++((Int64Ref)path).value;
                        path = this.val$treeSize;
                        ((Int64Ref)path).value += basicFileAttributes.size();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path path, IOException iOException) {
                        return FileVisitResult.CONTINUE;
                    }
                };
                Files.walkFileTree((Path)object2, (FileVisitor<? super Path>)fileVisitor);
                object2 = new Bundle();
            }
            catch (IOException iOException) {
                Log.e("FileSystemProvider", "An error occurred retrieving the metadata", iOException);
                return null;
            }
            ((BaseBundle)object2).putLong("android:metadataTreeCount", int64Ref.value);
            ((BaseBundle)object2).putLong("android:metadataTreeSize", ((Int64Ref)object).value);
            return object2;
        }
        if (!file.isFile()) {
            Log.w("FileSystemProvider", "Can't stream non-regular file. Returning empty metadata.");
            return null;
        }
        if (!file.canRead()) {
            Log.w("FileSystemProvider", "Can't stream non-readable file. Returning empty metadata.");
            return null;
        }
        if (!MetadataReader.isSupportedMimeType(string2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported type ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(". Returning empty metadata.");
            Log.w("FileSystemProvider", ((StringBuilder)object).toString());
            return null;
        }
        FileInputStream fileInputStream2 = null;
        FileInputStream fileInputStream3 = null;
        object = fileInputStream3;
        FileInputStream fileInputStream4 = fileInputStream2;
        object = fileInputStream3;
        fileInputStream4 = fileInputStream2;
        Bundle bundle = new Bundle();
        object = fileInputStream3;
        fileInputStream4 = fileInputStream2;
        object = fileInputStream3;
        fileInputStream4 = fileInputStream2;
        fileInputStream3 = fileInputStream = new FileInputStream(file.getAbsolutePath());
        object = fileInputStream3;
        fileInputStream4 = fileInputStream3;
        MetadataReader.getMetadata(bundle, fileInputStream3, string2, null);
        IoUtils.closeQuietly((AutoCloseable)fileInputStream3);
        return bundle;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            object = fileInputStream4;
            {
                Log.e("FileSystemProvider", "An error occurred retrieving the metadata", iOException);
            }
            IoUtils.closeQuietly(fileInputStream4);
            return null;
        }
        IoUtils.closeQuietly((AutoCloseable)object);
        throw throwable2222;
    }

    @Override
    public String getDocumentType(String string2) throws FileNotFoundException {
        return this.getDocumentType(string2, this.getFileForDocId(string2));
    }

    protected final File getFileForDocId(String string2) throws FileNotFoundException {
        return this.getFileForDocId(string2, false);
    }

    protected abstract File getFileForDocId(String var1, boolean var2) throws FileNotFoundException;

    protected MatrixCursor.RowBuilder includeFile(MatrixCursor object, String string2, File file) throws FileNotFoundException {
        long l;
        int n;
        String[] arrstring = ((MatrixCursor)object).getColumnNames();
        object = ((MatrixCursor)object).newRow();
        if (string2 == null) {
            string2 = this.getDocIdForFile(file);
        } else {
            file = this.getFileForDocId(string2);
        }
        String string3 = this.getDocumentType(string2, file);
        ((MatrixCursor.RowBuilder)object).add("document_id", (Object)string2);
        ((MatrixCursor.RowBuilder)object).add("mime_type", (Object)string3);
        int n2 = ArrayUtils.indexOf(arrstring, "flags");
        if (n2 != -1) {
            n = 0;
            if (file.canWrite()) {
                n = string3.equals("vnd.android.document/directory") ? 0 | 8 | 4 | 64 | 256 : 0 | 2 | 4 | 64 | 256;
            }
            int n3 = n;
            if (string3.startsWith("image/")) {
                n3 = n | 1;
            }
            n = n3;
            if (this.typeSupportsMetadata(string3)) {
                n = n3 | 16384;
            }
            ((MatrixCursor.RowBuilder)object).add(n2, (Object)n);
        }
        if ((n = ArrayUtils.indexOf(arrstring, "_display_name")) != -1) {
            ((MatrixCursor.RowBuilder)object).add(n, (Object)file.getName());
        }
        if ((n = ArrayUtils.indexOf(arrstring, "last_modified")) != -1 && (l = file.lastModified()) > 31536000000L) {
            ((MatrixCursor.RowBuilder)object).add(n, (Object)l);
        }
        if ((n = ArrayUtils.indexOf(arrstring, "_size")) != -1) {
            ((MatrixCursor.RowBuilder)object).add(n, (Object)file.length());
        }
        return object;
    }

    @Override
    public boolean isChildDocument(String string2, String string3) {
        try {
            boolean bl = FileUtils.contains(this.getFileForDocId(string2).getCanonicalFile(), this.getFileForDocId(string3).getCanonicalFile());
            return bl;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to determine if ");
            stringBuilder.append(string3);
            stringBuilder.append(" is child of ");
            stringBuilder.append(string2);
            stringBuilder.append(": ");
            stringBuilder.append(iOException);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public /* synthetic */ void lambda$openDocument$0$FileSystemProvider(String string2, File file, IOException iOException) {
        this.onDocIdChanged(string2);
        this.scanFile(file);
    }

    @Override
    public String moveDocument(String charSequence, String object, String object2) throws FileNotFoundException {
        File file = this.getFileForDocId((String)charSequence);
        object = new File(this.getFileForDocId((String)object2), file.getName());
        object2 = this.getFileForDocId((String)charSequence, true);
        if (!((File)object).exists()) {
            if (file.renameTo((File)object)) {
                object = this.getDocIdForFile((File)object);
                this.onDocIdChanged((String)charSequence);
                this.onDocIdChanged((String)object);
                this.moveInMediaStore((File)object2, this.getFileForDocId((String)object, true));
                return object;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Failed to move to ");
            ((StringBuilder)charSequence).append(object);
            throw new IllegalStateException(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Already exists ");
        ((StringBuilder)charSequence).append(object);
        throw new IllegalStateException(((StringBuilder)charSequence).toString());
    }

    protected void onCreate(String[] arrstring) {
        this.mHandler = new Handler();
        this.mDefaultProjection = arrstring;
    }

    @Override
    public boolean onCreate() {
        throw new UnsupportedOperationException("Subclass should override this and call onCreate(defaultDocumentProjection)");
    }

    protected void onDocIdChanged(String string2) {
    }

    @Override
    public ParcelFileDescriptor openDocument(String object, String object2, CancellationSignal object3) throws FileNotFoundException {
        object3 = this.getFileForDocId((String)object);
        File file = this.getFileForDocId((String)object, true);
        int n = ParcelFileDescriptor.parseMode((String)object2);
        if (n != 268435456 && file != null) {
            try {
                Handler handler = this.mHandler;
                object2 = new _$$Lambda$FileSystemProvider$y9rjeYFpkvVjwD2Whw_ujCM_C7Y(this, (String)object, file);
                object = ParcelFileDescriptor.open((File)object3, n, handler, (ParcelFileDescriptor.OnCloseListener)object2);
                return object;
            }
            catch (IOException iOException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Failed to open for writing: ");
                ((StringBuilder)object2).append(iOException);
                throw new FileNotFoundException(((StringBuilder)object2).toString());
            }
        }
        return ParcelFileDescriptor.open((File)object3, n);
    }

    @Override
    public AssetFileDescriptor openDocumentThumbnail(String string2, Point point, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return DocumentsContract.openImageThumbnail(this.getFileForDocId(string2));
    }

    @Override
    public Cursor queryChildDocuments(String arrfile, String[] object, String object2) throws FileNotFoundException {
        object2 = this.getFileForDocId((String)arrfile);
        object = new DirectoryCursor(this.resolveProjection((String[])object), (String)arrfile, (File)object2);
        if (((File)object2).isDirectory()) {
            arrfile = FileUtils.listFilesOrEmpty((File)object2);
            int n = arrfile.length;
            for (int i = 0; i < n; ++i) {
                this.includeFile((MatrixCursor)object, null, arrfile[i]);
            }
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("parentDocumentId '");
            ((StringBuilder)object2).append((String)arrfile);
            ((StringBuilder)object2).append("' is not Directory");
            Log.w("FileSystemProvider", ((StringBuilder)object2).toString());
        }
        return object;
    }

    @Override
    public Cursor queryDocument(String string2, String[] object) throws FileNotFoundException {
        object = new MatrixCursor(this.resolveProjection((String[])object));
        this.includeFile((MatrixCursor)object, string2, null);
        return object;
    }

    protected final Cursor querySearchDocuments(File object, String[] object2, Set<String> arrstring, Bundle bundle) throws FileNotFoundException {
        object2 = new MatrixCursor(this.resolveProjection((String[])object2));
        LinkedList<File> linkedList = new LinkedList<File>();
        linkedList.add((File)object);
        while (!linkedList.isEmpty() && ((MatrixCursor)object2).getCount() < 24) {
            object = (File)linkedList.removeFirst();
            if (((File)object).isDirectory()) {
                File[] arrfile = ((File)object).listFiles();
                int n = arrfile.length;
                for (int i = 0; i < n; ++i) {
                    linkedList.add(arrfile[i]);
                }
            }
            if (arrstring.contains(((File)object).getAbsolutePath()) || !this.matchSearchQueryArguments((File)object, bundle)) continue;
            this.includeFile((MatrixCursor)object2, null, (File)object);
        }
        arrstring = DocumentsContract.getHandledQueryArguments(bundle);
        if (arrstring.length > 0) {
            object = new Bundle();
            ((BaseBundle)object).putStringArray("android.content.extra.HONORED_ARGS", arrstring);
            ((AbstractCursor)object2).setExtras((Bundle)object);
        }
        return object2;
    }

    @Override
    public String renameDocument(String charSequence, String object) throws FileNotFoundException {
        Object object2 = FileUtils.buildValidFatFilename((String)object);
        Object object3 = this.getFileForDocId((String)charSequence);
        object = this.getFileForDocId((String)charSequence, true);
        object2 = FileUtils.buildUniqueFile(((File)object3).getParentFile(), (String)object2);
        if (((File)object3).renameTo((File)object2)) {
            object3 = this.getDocIdForFile((File)object2);
            this.onDocIdChanged((String)charSequence);
            this.onDocIdChanged((String)object3);
            this.moveInMediaStore((File)object, this.getFileForDocId((String)object3, true));
            if (!TextUtils.equals(charSequence, (CharSequence)object3)) {
                return object3;
            }
            return null;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Failed to rename to ");
        ((StringBuilder)charSequence).append(object2);
        throw new IllegalStateException(((StringBuilder)charSequence).toString());
    }

    protected boolean typeSupportsMetadata(String string2) {
        boolean bl = MetadataReader.isSupportedMimeType(string2) || "vnd.android.document/directory".equals(string2);
        return bl;
    }

    private class DirectoryCursor
    extends MatrixCursor {
        private final File mFile;

        public DirectoryCursor(String[] object, String string2, File file) {
            super((String[])object);
            object = FileSystemProvider.this.buildNotificationUri(string2);
            this.setNotificationUris(FileSystemProvider.this.getContext().getContentResolver(), Arrays.asList(object), FileSystemProvider.this.getContext().getContentResolver().getUserId(), false);
            this.mFile = file;
            FileSystemProvider.this.startObserving(this.mFile, (Uri)object, this);
        }

        @Override
        public void close() {
            super.close();
            FileSystemProvider.this.stopObserving(this.mFile, this);
        }

        public void notifyChanged() {
            this.onChange(false);
        }
    }

    private static class DirectoryObserver
    extends FileObserver {
        private static final int NOTIFY_EVENTS = 4044;
        private final CopyOnWriteArrayList<DirectoryCursor> mCursors;
        private final File mFile;
        private final Uri mNotifyUri;
        private final ContentResolver mResolver;

        DirectoryObserver(File file, ContentResolver contentResolver, Uri uri) {
            super(file.getAbsolutePath(), 4044);
            this.mFile = file;
            this.mResolver = contentResolver;
            this.mNotifyUri = uri;
            this.mCursors = new CopyOnWriteArrayList();
        }

        @Override
        public void onEvent(int n, String object) {
            if ((n & 4044) != 0) {
                object = this.mCursors.iterator();
                while (object.hasNext()) {
                    ((DirectoryCursor)object.next()).notifyChanged();
                }
                this.mResolver.notifyChange(this.mNotifyUri, null, false);
            }
        }

        public String toString() {
            String string2 = this.mFile.getAbsolutePath();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DirectoryObserver{file=");
            stringBuilder.append(string2);
            stringBuilder.append(", ref=");
            stringBuilder.append(this.mCursors.size());
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

