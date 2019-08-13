/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal;

import com.android.okhttp.internal.FaultHidingSink;
import com.android.okhttp.internal.Platform;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.io.FileSystem;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DiskLruCache
implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final long ANY_SEQUENCE_NUMBER = -1L;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,120}");
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final Sink NULL_SINK = new Sink(){

        @Override
        public void close() throws IOException {
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public Timeout timeout() {
            return Timeout.NONE;
        }

        @Override
        public void write(Buffer buffer, long l) throws IOException {
            buffer.skip(l);
        }
    };
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final int appVersion;
    private final Runnable cleanupRunnable = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            DiskLruCache diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache) {
                boolean bl = !DiskLruCache.this.initialized;
                if (bl | DiskLruCache.this.closed) {
                    return;
                }
                try {
                    DiskLruCache.this.trimToSize();
                    if (DiskLruCache.this.journalRebuildRequired()) {
                        DiskLruCache.this.rebuildJournal();
                        DiskLruCache.this.redundantOpCount = 0;
                    }
                    return;
                }
                catch (IOException iOException) {
                    RuntimeException runtimeException = new RuntimeException(iOException);
                    throw runtimeException;
                }
            }
        }
    };
    private boolean closed;
    private final File directory;
    private final Executor executor;
    private final FileSystem fileSystem;
    private boolean hasJournalErrors;
    private boolean initialized;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    private BufferedSink journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, 0.75f, true);
    private long maxSize;
    private long nextSequenceNumber = 0L;
    private int redundantOpCount;
    private long size = 0L;
    private final int valueCount;

    DiskLruCache(FileSystem fileSystem, File file, int n, int n2, long l, Executor executor) {
        this.fileSystem = fileSystem;
        this.directory = file;
        this.appVersion = n;
        this.journalFile = new File(file, JOURNAL_FILE);
        this.journalFileTmp = new File(file, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(file, JOURNAL_FILE_BACKUP);
        this.valueCount = n2;
        this.maxSize = l;
        this.executor = executor;
    }

    private void checkNotClosed() {
        synchronized (this) {
            block4 : {
                boolean bl = this.isClosed();
                if (bl) break block4;
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("cache is closed");
            throw illegalStateException;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void completeEdit(Editor object, boolean bl) throws IOException {
        synchronized (this) {
            void var2_2;
            long l;
            int n;
            Object object2 = ((Editor)object).entry;
            if (((Entry)object2).currentEditor != object) {
                object = new IllegalStateException();
                throw object;
            }
            int n2 = 0;
            if (var2_2 != false && !((Entry)object2).readable) {
                for (n = 0; n < this.valueCount; ++n) {
                    if (!((Editor)object).written[n]) {
                        ((Editor)object).abort();
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Newly created entry didn't create value for index ");
                        ((StringBuilder)object).append(n);
                        object2 = new IllegalStateException(((StringBuilder)object).toString());
                        throw object2;
                    }
                    if (this.fileSystem.exists(((Entry)object2).dirtyFiles[n])) continue;
                    ((Editor)object).abort();
                    return;
                }
            }
            for (n = n2; n < this.valueCount; ++n) {
                object = ((Entry)object2).dirtyFiles[n];
                if (var2_2 != false) {
                    long l2;
                    if (!this.fileSystem.exists((File)object)) continue;
                    File file = ((Entry)object2).cleanFiles[n];
                    this.fileSystem.rename((File)object, file);
                    l = ((Entry)object2).lengths[n];
                    Entry.access$1200((Entry)object2)[n] = l2 = this.fileSystem.size(file);
                    this.size = this.size - l + l2;
                    continue;
                }
                this.fileSystem.delete((File)object);
            }
            ++this.redundantOpCount;
            ((Entry)object2).currentEditor = null;
            if ((((Entry)object2).readable | var2_2) != 0) {
                ((Entry)object2).readable = true;
                this.journalWriter.writeUtf8(CLEAN).writeByte(32);
                this.journalWriter.writeUtf8(((Entry)object2).key);
                ((Entry)object2).writeLengths(this.journalWriter);
                this.journalWriter.writeByte(10);
                if (var2_2 != false) {
                    l = this.nextSequenceNumber;
                    this.nextSequenceNumber = 1L + l;
                    ((Entry)object2).sequenceNumber = l;
                }
            } else {
                this.lruEntries.remove(((Entry)object2).key);
                this.journalWriter.writeUtf8(REMOVE).writeByte(32);
                this.journalWriter.writeUtf8(((Entry)object2).key);
                this.journalWriter.writeByte(10);
            }
            this.journalWriter.flush();
            if (this.size > this.maxSize || this.journalRebuildRequired()) {
                this.executor.execute(this.cleanupRunnable);
            }
            return;
        }
    }

    public static DiskLruCache create(FileSystem fileSystem, File file, int n, int n2, long l) {
        if (l > 0L) {
            if (n2 > 0) {
                return new DiskLruCache(fileSystem, file, n, n2, l, new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory("OkHttp DiskLruCache", true)));
            }
            throw new IllegalArgumentException("valueCount <= 0");
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Editor edit(String object, long l) throws IOException {
        synchronized (this) {
            Object object2;
            long l2;
            void var2_2;
            this.initialize();
            this.checkNotClosed();
            this.validateKey((String)object);
            Entry entry = this.lruEntries.get(object);
            if (var2_2 != -1L && (entry == null || (l2 = entry.sequenceNumber) != var2_2)) {
                return null;
            }
            if (entry != null && (object2 = entry.currentEditor) != null) {
                return null;
            }
            this.journalWriter.writeUtf8(DIRTY).writeByte(32).writeUtf8((String)object).writeByte(10);
            this.journalWriter.flush();
            boolean bl = this.hasJournalErrors;
            if (bl) {
                return null;
            }
            object2 = entry;
            if (entry == null) {
                object2 = new Entry((String)object);
                this.lruEntries.put((String)object, (Entry)object2);
            }
            object = new Editor((Entry)object2);
            ((Entry)object2).currentEditor = (Editor)object;
            return object;
        }
    }

    private boolean journalRebuildRequired() {
        int n = this.redundantOpCount;
        boolean bl = n >= 2000 && n >= this.lruEntries.size();
        return bl;
    }

    private BufferedSink newJournalWriter() throws FileNotFoundException {
        return Okio.buffer(new FaultHidingSink(this.fileSystem.appendingSink(this.journalFile)){
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override
            protected void onException(IOException iOException) {
                DiskLruCache.this.hasJournalErrors = true;
            }
        });
    }

    private void processJournal() throws IOException {
        this.fileSystem.delete(this.journalFileTmp);
        Iterator<Entry> iterator = this.lruEntries.values().iterator();
        while (iterator.hasNext()) {
            int n;
            Entry entry = iterator.next();
            if (entry.currentEditor == null) {
                for (n = 0; n < this.valueCount; ++n) {
                    this.size += entry.lengths[n];
                }
                continue;
            }
            entry.currentEditor = null;
            for (n = 0; n < this.valueCount; ++n) {
                this.fileSystem.delete(entry.cleanFiles[n]);
                this.fileSystem.delete(entry.dirtyFiles[n]);
            }
            iterator.remove();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void readJournal() throws IOException {
        boolean bl;
        BufferedSource bufferedSource = Okio.buffer(this.fileSystem.source(this.journalFile));
        String string2 = bufferedSource.readUtf8LineStrict();
        String string = bufferedSource.readUtf8LineStrict();
        Object object = bufferedSource.readUtf8LineStrict();
        String string4 = bufferedSource.readUtf8LineStrict();
        String string3 = bufferedSource.readUtf8LineStrict();
        if (!(MAGIC.equals(string2) && VERSION_1.equals(string) && Integer.toString(this.appVersion).equals(object) && Integer.toString(this.valueCount).equals(string4) && (bl = "".equals(string3)))) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected journal header: [");
            stringBuilder.append(string2);
            stringBuilder.append(", ");
            stringBuilder.append(string);
            stringBuilder.append(", ");
            stringBuilder.append(string4);
            stringBuilder.append(", ");
            stringBuilder.append(string3);
            stringBuilder.append("]");
            object = new IOException(stringBuilder.toString());
            throw object;
        }
        int n = 0;
        {
            catch (Throwable throwable) {
                throw throwable;
            }
            try {
                do {
                    this.readJournalLine(bufferedSource.readUtf8LineStrict());
                    ++n;
                } while (true);
            }
            catch (EOFException eOFException) {}
            this.redundantOpCount = n - this.lruEntries.size();
            if (!bufferedSource.exhausted()) {
                this.rebuildJournal();
                return;
            }
            this.journalWriter = this.newJournalWriter();
            return;
        }
    }

    private void readJournalLine(String arrstring) throws IOException {
        block9 : {
            CharSequence charSequence;
            block13 : {
                block11 : {
                    int n;
                    int n2;
                    block12 : {
                        Object object;
                        block10 : {
                            n2 = arrstring.indexOf(32);
                            if (n2 == -1) break block9;
                            int n3 = n2 + 1;
                            n = arrstring.indexOf(32, n3);
                            if (n == -1) {
                                object = arrstring.substring(n3);
                                charSequence = object;
                                if (n2 == REMOVE.length()) {
                                    charSequence = object;
                                    if (arrstring.startsWith(REMOVE)) {
                                        this.lruEntries.remove(object);
                                        return;
                                    }
                                }
                            } else {
                                charSequence = arrstring.substring(n3, n);
                            }
                            Entry entry = this.lruEntries.get(charSequence);
                            object = entry;
                            if (entry == null) {
                                object = new Entry((String)charSequence);
                                this.lruEntries.put((String)charSequence, (Entry)object);
                            }
                            if (n == -1 || n2 != CLEAN.length() || !arrstring.startsWith(CLEAN)) break block10;
                            arrstring = arrstring.substring(n + 1).split(" ");
                            ((Entry)object).readable = true;
                            ((Entry)object).currentEditor = null;
                            ((Entry)object).setLengths(arrstring);
                            break block11;
                        }
                        if (n != -1 || n2 != DIRTY.length() || !arrstring.startsWith(DIRTY)) break block12;
                        ((Entry)object).currentEditor = new Editor((Entry)object);
                        break block11;
                    }
                    if (n != -1 || n2 != READ.length() || !arrstring.startsWith(READ)) break block13;
                }
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("unexpected journal line: ");
            ((StringBuilder)charSequence).append((String)arrstring);
            throw new IOException(((StringBuilder)charSequence).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected journal line: ");
        stringBuilder.append((String)arrstring);
        throw new IOException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void rebuildJournal() throws IOException {
        synchronized (this) {
            if (this.journalWriter != null) {
                this.journalWriter.close();
            }
            BufferedSink bufferedSink = Okio.buffer(this.fileSystem.sink(this.journalFileTmp));
            bufferedSink.writeUtf8(MAGIC).writeByte(10);
            bufferedSink.writeUtf8(VERSION_1).writeByte(10);
            bufferedSink.writeDecimalLong(this.appVersion).writeByte(10);
            bufferedSink.writeDecimalLong(this.valueCount).writeByte(10);
            bufferedSink.writeByte(10);
            for (Entry entry : this.lruEntries.values()) {
                if (entry.currentEditor != null) {
                    bufferedSink.writeUtf8(DIRTY).writeByte(32);
                    bufferedSink.writeUtf8(entry.key);
                    bufferedSink.writeByte(10);
                    continue;
                }
                bufferedSink.writeUtf8(CLEAN).writeByte(32);
                bufferedSink.writeUtf8(entry.key);
                entry.writeLengths(bufferedSink);
                bufferedSink.writeByte(10);
            }
            if (this.fileSystem.exists(this.journalFile)) {
                this.fileSystem.rename(this.journalFile, this.journalFileBackup);
            }
            this.fileSystem.rename(this.journalFileTmp, this.journalFile);
            this.fileSystem.delete(this.journalFileBackup);
            this.journalWriter = this.newJournalWriter();
            this.hasJournalErrors = false;
            return;
            finally {
                bufferedSink.close();
            }
        }
    }

    private boolean removeEntry(Entry entry) throws IOException {
        if (entry.currentEditor != null) {
            entry.currentEditor.detach();
        }
        for (int i = 0; i < this.valueCount; ++i) {
            this.fileSystem.delete(entry.cleanFiles[i]);
            this.size -= entry.lengths[i];
            Entry.access$1200((Entry)entry)[i] = 0L;
        }
        ++this.redundantOpCount;
        this.journalWriter.writeUtf8(REMOVE).writeByte(32).writeUtf8(entry.key).writeByte(10);
        this.lruEntries.remove(entry.key);
        if (this.journalRebuildRequired()) {
            this.executor.execute(this.cleanupRunnable);
        }
        return true;
    }

    private void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            this.removeEntry(this.lruEntries.values().iterator().next());
        }
    }

    private void validateKey(String string) {
        if (LEGAL_KEY_PATTERN.matcher(string).matches()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("keys must match regex [a-z0-9_-]{1,120}: \"");
        stringBuilder.append(string);
        stringBuilder.append("\"");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        synchronized (this) {
            if (this.initialized && !this.closed) {
                Entry[] arrentry = this.lruEntries.values().toArray(new Entry[this.lruEntries.size()]);
                int n = arrentry.length;
                int n2 = 0;
                do {
                    if (n2 >= n) {
                        this.trimToSize();
                        this.journalWriter.close();
                        this.journalWriter = null;
                        this.closed = true;
                        return;
                    }
                    Entry entry = arrentry[n2];
                    if (entry.currentEditor != null) {
                        entry.currentEditor.abort();
                    }
                    ++n2;
                } while (true);
            }
            this.closed = true;
            return;
        }
    }

    public void delete() throws IOException {
        this.close();
        this.fileSystem.deleteContents(this.directory);
    }

    public Editor edit(String string) throws IOException {
        return this.edit(string, -1L);
    }

    public void evictAll() throws IOException {
        synchronized (this) {
            this.initialize();
            Entry[] arrentry = this.lruEntries.values().toArray(new Entry[this.lruEntries.size()]);
            int n = arrentry.length;
            for (int i = 0; i < n; ++i) {
                this.removeEntry(arrentry[i]);
            }
            return;
        }
    }

    public void flush() throws IOException {
        synchronized (this) {
            block4 : {
                boolean bl = this.initialized;
                if (bl) break block4;
                return;
            }
            this.checkNotClosed();
            this.trimToSize();
            this.journalWriter.flush();
            return;
        }
    }

    public Snapshot get(String string) throws IOException {
        synchronized (this) {
            block6 : {
                Object object;
                block7 : {
                    this.initialize();
                    this.checkNotClosed();
                    this.validateKey(string);
                    object = this.lruEntries.get(string);
                    if (object == null) break block6;
                    if (!((Entry)object).readable) break block6;
                    if ((object = ((Entry)object).snapshot()) != null) break block7;
                    return null;
                }
                ++this.redundantOpCount;
                this.journalWriter.writeUtf8(READ).writeByte(32).writeUtf8(string).writeByte(10);
                if (this.journalRebuildRequired()) {
                    this.executor.execute(this.cleanupRunnable);
                }
                return object;
            }
            return null;
        }
    }

    public File getDirectory() {
        return this.directory;
    }

    public long getMaxSize() {
        synchronized (this) {
            long l = this.maxSize;
            return l;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void initialize() throws IOException {
        synchronized (this) {
            boolean bl = this.initialized;
            if (bl) {
                return;
            }
            if (this.fileSystem.exists(this.journalFileBackup)) {
                if (this.fileSystem.exists(this.journalFile)) {
                    this.fileSystem.delete(this.journalFileBackup);
                } else {
                    this.fileSystem.rename(this.journalFileBackup, this.journalFile);
                }
            }
            if (bl = this.fileSystem.exists(this.journalFile)) {
                try {
                    this.readJournal();
                    this.processJournal();
                    this.initialized = true;
                    return;
                }
                catch (IOException iOException) {
                    Platform platform = Platform.get();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DiskLruCache ");
                    stringBuilder.append(this.directory);
                    stringBuilder.append(" is corrupt: ");
                    stringBuilder.append(iOException.getMessage());
                    stringBuilder.append(", removing");
                    platform.logW(stringBuilder.toString());
                    this.delete();
                    this.closed = false;
                }
            }
            this.rebuildJournal();
            this.initialized = true;
            return;
        }
    }

    public boolean isClosed() {
        synchronized (this) {
            boolean bl = this.closed;
            return bl;
        }
    }

    public boolean remove(String object) throws IOException {
        synchronized (this) {
            block4 : {
                this.initialize();
                this.checkNotClosed();
                this.validateKey((String)object);
                object = this.lruEntries.get(object);
                if (object != null) break block4;
                return false;
            }
            boolean bl = this.removeEntry((Entry)object);
            return bl;
        }
    }

    public void setMaxSize(long l) {
        synchronized (this) {
            this.maxSize = l;
            if (this.initialized) {
                this.executor.execute(this.cleanupRunnable);
            }
            return;
        }
    }

    public long size() throws IOException {
        synchronized (this) {
            this.initialize();
            long l = this.size;
            return l;
        }
    }

    public Iterator<Snapshot> snapshots() throws IOException {
        synchronized (this) {
            this.initialize();
            Iterator<Snapshot> iterator = new Iterator<Snapshot>(){
                final Iterator<Entry> delegate;
                Snapshot nextSnapshot;
                Snapshot removeSnapshot;
                {
                    this.delegate = new ArrayList(DiskLruCache.this.lruEntries.values()).iterator();
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public boolean hasNext() {
                    if (this.nextSnapshot != null) {
                        return true;
                    }
                    DiskLruCache diskLruCache = DiskLruCache.this;
                    synchronized (diskLruCache) {
                        Snapshot snapshot;
                        if (DiskLruCache.this.closed) {
                            return false;
                        }
                        do {
                            if (this.delegate.hasNext()) continue;
                            return false;
                        } while ((snapshot = this.delegate.next().snapshot()) == null);
                        this.nextSnapshot = snapshot;
                        return true;
                    }
                }

                @Override
                public Snapshot next() {
                    if (this.hasNext()) {
                        this.removeSnapshot = this.nextSnapshot;
                        this.nextSnapshot = null;
                        return this.removeSnapshot;
                    }
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    Snapshot snapshot = this.removeSnapshot;
                    if (snapshot != null) {
                        try {
                            DiskLruCache.this.remove(snapshot.key);
                        }
                        catch (Throwable throwable) {
                            this.removeSnapshot = null;
                            throw throwable;
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                        this.removeSnapshot = null;
                        return;
                    }
                    throw new IllegalStateException("remove() before next()");
                }
            };
            return iterator;
        }
    }

    public final class Editor {
        private boolean done;
        private final Entry entry;
        private final boolean[] written;

        private Editor(Entry entry) {
            this.entry = entry;
            DiskLruCache.this = entry.readable ? null : new boolean[((DiskLruCache)DiskLruCache.this).valueCount];
            this.written = DiskLruCache.this;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void abort() throws IOException {
            DiskLruCache diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache) {
                if (this.done) {
                    IllegalStateException illegalStateException = new IllegalStateException();
                    throw illegalStateException;
                }
                if (this.entry.currentEditor == this) {
                    DiskLruCache.this.completeEdit(this, false);
                }
                this.done = true;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void abortUnlessCommitted() {
            DiskLruCache diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache) {
                Editor editor;
                if (!this.done && (editor = this.entry.currentEditor) == this) {
                    try {
                        DiskLruCache.this.completeEdit(this, false);
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void commit() throws IOException {
            DiskLruCache diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache) {
                if (this.done) {
                    IllegalStateException illegalStateException = new IllegalStateException();
                    throw illegalStateException;
                }
                if (this.entry.currentEditor == this) {
                    DiskLruCache.this.completeEdit(this, true);
                }
                this.done = true;
                return;
            }
        }

        void detach() {
            if (this.entry.currentEditor == this) {
                for (int i = 0; i < DiskLruCache.this.valueCount; ++i) {
                    try {
                        DiskLruCache.this.fileSystem.delete(this.entry.dirtyFiles[i]);
                        continue;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                this.entry.currentEditor = null;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Sink newSink(int n) throws IOException {
            DiskLruCache diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache) {
                Sink sink;
                if (this.done) {
                    IllegalStateException illegalStateException = new IllegalStateException();
                    throw illegalStateException;
                }
                if (this.entry.currentEditor != this) {
                    return NULL_SINK;
                }
                if (!this.entry.readable) {
                    this.written[n] = true;
                }
                FaultHidingSink faultHidingSink = this.entry.dirtyFiles[n];
                try {
                    sink = DiskLruCache.this.fileSystem.sink((File)((Object)faultHidingSink));
                }
                catch (FileNotFoundException fileNotFoundException) {
                    return NULL_SINK;
                }
                return new FaultHidingSink(sink){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    protected void onException(IOException iOException) {
                        DiskLruCache diskLruCache = DiskLruCache.this;
                        synchronized (diskLruCache) {
                            Editor.this.detach();
                            return;
                        }
                    }
                };
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Source newSource(int n) throws IOException {
            DiskLruCache diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache) {
                if (this.done) {
                    IllegalStateException illegalStateException = new IllegalStateException();
                    throw illegalStateException;
                }
                if (!this.entry.readable) return null;
                Editor editor = this.entry.currentEditor;
                if (editor == this) {
                    try {
                        return DiskLruCache.this.fileSystem.source(this.entry.cleanFiles[n]);
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        return null;
                    }
                }
                return null;
            }
        }

    }

    private final class Entry {
        private final File[] cleanFiles;
        private Editor currentEditor;
        private final File[] dirtyFiles;
        private final String key;
        private final long[] lengths;
        private boolean readable;
        private long sequenceNumber;

        private Entry(String charSequence) {
            this.key = charSequence;
            this.lengths = new long[DiskLruCache.this.valueCount];
            this.cleanFiles = new File[DiskLruCache.this.valueCount];
            this.dirtyFiles = new File[DiskLruCache.this.valueCount];
            charSequence = new StringBuilder((String)charSequence).append('.');
            int n = ((StringBuilder)charSequence).length();
            for (int i = 0; i < DiskLruCache.this.valueCount; ++i) {
                ((StringBuilder)charSequence).append(i);
                this.cleanFiles[i] = new File(DiskLruCache.this.directory, ((StringBuilder)charSequence).toString());
                ((StringBuilder)charSequence).append(".tmp");
                this.dirtyFiles[i] = new File(DiskLruCache.this.directory, ((StringBuilder)charSequence).toString());
                ((StringBuilder)charSequence).setLength(n);
            }
        }

        private IOException invalidLengths(String[] arrstring) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected journal line: ");
            stringBuilder.append(Arrays.toString(arrstring));
            throw new IOException(stringBuilder.toString());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void setLengths(String[] arrstring) throws IOException {
            if (arrstring.length != DiskLruCache.this.valueCount) {
                throw this.invalidLengths(arrstring);
            }
            int n = 0;
            try {
                do {
                    if (n >= arrstring.length) {
                        return;
                    }
                    this.lengths[n] = Long.parseLong(arrstring[n]);
                    ++n;
                } while (true);
            }
            catch (NumberFormatException numberFormatException) {
                throw this.invalidLengths(arrstring);
            }
        }

        Snapshot snapshot() {
            if (Thread.holdsLock(DiskLruCache.this)) {
                Source[] arrsource = new Source[DiskLruCache.this.valueCount];
                Object object = (long[])this.lengths.clone();
                int n = 0;
                do {
                    if (n >= DiskLruCache.this.valueCount) break;
                    arrsource[n] = DiskLruCache.this.fileSystem.source(this.cleanFiles[n]);
                    ++n;
                    continue;
                    break;
                } while (true);
                try {
                    object = new Snapshot(this.key, this.sequenceNumber, arrsource, (long[])object);
                    return object;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    for (n = 0; n < DiskLruCache.this.valueCount && arrsource[n] != null; ++n) {
                        Util.closeQuietly(arrsource[n]);
                    }
                    return null;
                }
            }
            throw new AssertionError();
        }

        void writeLengths(BufferedSink bufferedSink) throws IOException {
            for (long l : this.lengths) {
                bufferedSink.writeByte(32).writeDecimalLong(l);
            }
        }
    }

    public final class Snapshot
    implements Closeable {
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;
        private final Source[] sources;

        private Snapshot(String string, long l, Source[] arrsource, long[] arrl) {
            this.key = string;
            this.sequenceNumber = l;
            this.sources = arrsource;
            this.lengths = arrl;
        }

        @Override
        public void close() {
            Source[] arrsource = this.sources;
            int n = arrsource.length;
            for (int i = 0; i < n; ++i) {
                Util.closeQuietly(arrsource[i]);
            }
        }

        public Editor edit() throws IOException {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public long getLength(int n) {
            return this.lengths[n];
        }

        public Source getSource(int n) {
            return this.sources[n];
        }

        public String key() {
            return this.key;
        }
    }

}

