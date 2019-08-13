/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentUris
 *  android.content.UriMatcher
 *  android.net.Uri
 *  android.provider.Telephony
 *  android.provider.Telephony$Mms
 */
package com.google.android.mms.util;

import android.content.ContentUris;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.Telephony;
import com.google.android.mms.util.AbstractCache;
import com.google.android.mms.util.PduCacheEntry;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public final class PduCache
extends AbstractCache<Uri, PduCacheEntry> {
    private static final boolean DEBUG = false;
    private static final boolean LOCAL_LOGV = false;
    private static final HashMap<Integer, Integer> MATCH_TO_MSGBOX_ID_MAP;
    private static final int MMS_ALL = 0;
    private static final int MMS_ALL_ID = 1;
    private static final int MMS_CONVERSATION = 10;
    private static final int MMS_CONVERSATION_ID = 11;
    private static final int MMS_DRAFTS = 6;
    private static final int MMS_DRAFTS_ID = 7;
    private static final int MMS_INBOX = 2;
    private static final int MMS_INBOX_ID = 3;
    private static final int MMS_OUTBOX = 8;
    private static final int MMS_OUTBOX_ID = 9;
    private static final int MMS_SENT = 4;
    private static final int MMS_SENT_ID = 5;
    private static final String TAG = "PduCache";
    private static final UriMatcher URI_MATCHER;
    private static PduCache sInstance;
    private final HashMap<Integer, HashSet<Uri>> mMessageBoxes = new HashMap();
    private final HashMap<Long, HashSet<Uri>> mThreads = new HashMap();
    private final HashSet<Uri> mUpdating = new HashSet();

    static {
        URI_MATCHER = new UriMatcher(-1);
        URI_MATCHER.addURI("mms", null, 0);
        URI_MATCHER.addURI("mms", "#", 1);
        UriMatcher uriMatcher = URI_MATCHER;
        Integer n = 2;
        uriMatcher.addURI("mms", "inbox", 2);
        URI_MATCHER.addURI("mms", "inbox/#", 3);
        uriMatcher = URI_MATCHER;
        Integer n2 = 4;
        uriMatcher.addURI("mms", "sent", 4);
        URI_MATCHER.addURI("mms", "sent/#", 5);
        URI_MATCHER.addURI("mms", "drafts", 6);
        URI_MATCHER.addURI("mms", "drafts/#", 7);
        URI_MATCHER.addURI("mms", "outbox", 8);
        URI_MATCHER.addURI("mms", "outbox/#", 9);
        URI_MATCHER.addURI("mms-sms", "conversations", 10);
        URI_MATCHER.addURI("mms-sms", "conversations/#", 11);
        MATCH_TO_MSGBOX_ID_MAP = new HashMap();
        MATCH_TO_MSGBOX_ID_MAP.put(n, 1);
        MATCH_TO_MSGBOX_ID_MAP.put(n2, n);
        MATCH_TO_MSGBOX_ID_MAP.put(6, 3);
        MATCH_TO_MSGBOX_ID_MAP.put(8, n2);
    }

    private PduCache() {
    }

    public static final PduCache getInstance() {
        synchronized (PduCache.class) {
            PduCache pduCache;
            if (sInstance == null) {
                sInstance = pduCache = new PduCache();
            }
            pduCache = sInstance;
            return pduCache;
        }
    }

    private Uri normalizeKey(Uri object) {
        block1 : {
            int n = URI_MATCHER.match(object);
            if (n == 1) break block1;
            if (n != 3 && n != 5 && n != 7 && n != 9) {
                return null;
            }
            object = object.getLastPathSegment();
            object = Uri.withAppendedPath((Uri)Telephony.Mms.CONTENT_URI, (String)object);
        }
        return object;
    }

    private void purgeByMessageBox(Integer serializable) {
        if (serializable != null && (serializable = this.mMessageBoxes.remove(serializable)) != null) {
            Iterator iterator = ((HashSet)serializable).iterator();
            while (iterator.hasNext()) {
                serializable = (Uri)iterator.next();
                this.mUpdating.remove(serializable);
                PduCacheEntry pduCacheEntry = (PduCacheEntry)super.purge(serializable);
                if (pduCacheEntry == null) continue;
                this.removeFromThreads((Uri)serializable, pduCacheEntry);
            }
        }
    }

    private void purgeByThreadId(long l) {
        Object object = this.mThreads.remove(l);
        if (object != null) {
            Iterator<Uri> iterator = ((HashSet)object).iterator();
            while (iterator.hasNext()) {
                Uri uri = iterator.next();
                this.mUpdating.remove((Object)uri);
                object = (PduCacheEntry)super.purge(uri);
                if (object == null) continue;
                this.removeFromMessageBoxes(uri, (PduCacheEntry)object);
            }
        }
    }

    private PduCacheEntry purgeSingleEntry(Uri uri) {
        this.mUpdating.remove((Object)uri);
        PduCacheEntry pduCacheEntry = (PduCacheEntry)super.purge(uri);
        if (pduCacheEntry != null) {
            this.removeFromThreads(uri, pduCacheEntry);
            this.removeFromMessageBoxes(uri, pduCacheEntry);
            return pduCacheEntry;
        }
        return null;
    }

    private void removeFromMessageBoxes(Uri uri, PduCacheEntry object) {
        if ((object = this.mThreads.get(((PduCacheEntry)object).getMessageBox())) != null) {
            ((HashSet)object).remove((Object)uri);
        }
    }

    private void removeFromThreads(Uri uri, PduCacheEntry object) {
        if ((object = this.mThreads.get(((PduCacheEntry)object).getThreadId())) != null) {
            ((HashSet)object).remove((Object)uri);
        }
    }

    public boolean isUpdating(Uri uri) {
        synchronized (this) {
            boolean bl = this.mUpdating.contains((Object)uri);
            return bl;
        }
    }

    @Override
    public PduCacheEntry purge(Uri object) {
        synchronized (this) {
            int n = URI_MATCHER.match(object);
            switch (n) {
                default: {
                    return null;
                }
                case 11: {
                    this.purgeByThreadId(ContentUris.parseId((Uri)object));
                    return null;
                }
                case 3: 
                case 5: 
                case 7: 
                case 9: {
                    object = object.getLastPathSegment();
                    object = this.purgeSingleEntry(Uri.withAppendedPath((Uri)Telephony.Mms.CONTENT_URI, (String)object));
                    return object;
                }
                case 2: 
                case 4: 
                case 6: 
                case 8: {
                    this.purgeByMessageBox(MATCH_TO_MSGBOX_ID_MAP.get(n));
                    return null;
                }
                case 1: {
                    object = this.purgeSingleEntry((Uri)object);
                    return object;
                }
                case 0: 
                case 10: 
            }
            this.purgeAll();
            return null;
        }
    }

    @Override
    public void purgeAll() {
        synchronized (this) {
            super.purgeAll();
            this.mMessageBoxes.clear();
            this.mThreads.clear();
            this.mUpdating.clear();
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean put(Uri uri, PduCacheEntry pduCacheEntry) {
        synchronized (this) {
            void var2_2;
            boolean bl;
            int n = var2_2.getMessageBox();
            HashSet<Uri> hashSet = this.mMessageBoxes.get(n);
            HashSet<Uri> hashSet2 = hashSet;
            if (hashSet == null) {
                hashSet2 = new HashSet<Uri>();
                this.mMessageBoxes.put(n, hashSet2);
            }
            long l = var2_2.getThreadId();
            Uri uri2 = this.mThreads.get(l);
            hashSet = uri2;
            if (uri2 == null) {
                hashSet = new HashSet<Uri>();
                this.mThreads.put(l, hashSet);
            }
            if (bl = super.put(uri2 = this.normalizeKey(uri), var2_2)) {
                hashSet2.add(uri2);
                hashSet.add(uri2);
            }
            this.setUpdating(uri, false);
            return bl;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setUpdating(Uri uri, boolean bl) {
        synchronized (this) {
            void var2_2;
            if (var2_2 != false) {
                this.mUpdating.add(uri);
            } else {
                this.mUpdating.remove((Object)uri);
            }
            return;
        }
    }
}

