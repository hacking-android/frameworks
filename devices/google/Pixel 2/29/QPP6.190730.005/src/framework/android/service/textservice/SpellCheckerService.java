/*
 * Decompiled with CFR 0.145.
 */
package android.service.textservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import android.text.method.WordIterator;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import com.android.internal.textservice.ISpellCheckerService;
import com.android.internal.textservice.ISpellCheckerServiceCallback;
import com.android.internal.textservice.ISpellCheckerSession;
import com.android.internal.textservice.ISpellCheckerSessionListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

public abstract class SpellCheckerService
extends Service {
    private static final boolean DBG = false;
    public static final String SERVICE_INTERFACE = "android.service.textservice.SpellCheckerService";
    private static final String TAG = SpellCheckerService.class.getSimpleName();
    private final SpellCheckerServiceBinder mBinder = new SpellCheckerServiceBinder(this);

    public abstract Session createSession();

    @Override
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    private static class InternalISpellCheckerSession
    extends ISpellCheckerSession.Stub {
        private final Bundle mBundle;
        private ISpellCheckerSessionListener mListener;
        private final String mLocale;
        private final Session mSession;

        public InternalISpellCheckerSession(String string2, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, Session session) {
            this.mListener = iSpellCheckerSessionListener;
            this.mSession = session;
            this.mLocale = string2;
            this.mBundle = bundle;
            session.setInternalISpellCheckerSession(this);
        }

        public Bundle getBundle() {
            return this.mBundle;
        }

        public String getLocale() {
            return this.mLocale;
        }

        @Override
        public void onCancel() {
            int n = Process.getThreadPriority(Process.myTid());
            try {
                Process.setThreadPriority(10);
                this.mSession.onCancel();
                return;
            }
            finally {
                Process.setThreadPriority(n);
            }
        }

        @Override
        public void onClose() {
            int n = Process.getThreadPriority(Process.myTid());
            try {
                Process.setThreadPriority(10);
                this.mSession.onClose();
                return;
            }
            finally {
                Process.setThreadPriority(n);
                this.mListener = null;
            }
        }

        @Override
        public void onGetSentenceSuggestionsMultiple(TextInfo[] arrtextInfo, int n) {
            try {
                this.mListener.onGetSentenceSuggestions(this.mSession.onGetSentenceSuggestionsMultiple(arrtextInfo, n));
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        @Override
        public void onGetSuggestionsMultiple(TextInfo[] arrtextInfo, int n, boolean bl) {
            int n2 = Process.getThreadPriority(Process.myTid());
            try {
                Process.setThreadPriority(10);
                this.mListener.onGetSuggestions(this.mSession.onGetSuggestionsMultiple(arrtextInfo, n, bl));
            }
            catch (Throwable throwable) {
                Process.setThreadPriority(n2);
                throw throwable;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            Process.setThreadPriority(n2);
        }
    }

    private static class SentenceLevelAdapter {
        public static final SentenceSuggestionsInfo[] EMPTY_SENTENCE_SUGGESTIONS_INFOS = new SentenceSuggestionsInfo[0];
        private static final SuggestionsInfo EMPTY_SUGGESTIONS_INFO = new SuggestionsInfo(0, null);
        private final WordIterator mWordIterator;

        public SentenceLevelAdapter(Locale locale) {
            this.mWordIterator = new WordIterator(locale);
        }

        private SentenceTextInfoParams getSplitWords(TextInfo textInfo) {
            WordIterator wordIterator = this.mWordIterator;
            String string2 = textInfo.getText();
            int n = textInfo.getCookie();
            int n2 = string2.length();
            ArrayList<SentenceWordItem> arrayList = new ArrayList<SentenceWordItem>();
            wordIterator.setCharSequence(string2, 0, string2.length());
            int n3 = wordIterator.following(0);
            int n4 = wordIterator.getBeginning(n3);
            while (n4 <= n2 && n3 != -1 && n4 != -1) {
                if (n3 >= 0 && n3 > n4) {
                    CharSequence charSequence = string2.subSequence(n4, n3);
                    arrayList.add(new SentenceWordItem(new TextInfo(charSequence, 0, charSequence.length(), n, charSequence.hashCode()), n4, n3));
                }
                if ((n3 = wordIterator.following(n3)) == -1) break;
                n4 = wordIterator.getBeginning(n3);
            }
            return new SentenceTextInfoParams(textInfo, arrayList);
        }

        public static SentenceSuggestionsInfo reconstructSuggestions(SentenceTextInfoParams sentenceTextInfoParams, SuggestionsInfo[] arrsuggestionsInfo) {
            if (arrsuggestionsInfo != null && arrsuggestionsInfo.length != 0) {
                if (sentenceTextInfoParams == null) {
                    return null;
                }
                int n = sentenceTextInfoParams.mOriginalTextInfo.getCookie();
                int n2 = sentenceTextInfoParams.mOriginalTextInfo.getSequence();
                int n3 = sentenceTextInfoParams.mSize;
                int[] arrn = new int[n3];
                int[] arrn2 = new int[n3];
                SuggestionsInfo[] arrsuggestionsInfo2 = new SuggestionsInfo[n3];
                for (int i = 0; i < n3; ++i) {
                    SuggestionsInfo suggestionsInfo;
                    SentenceWordItem sentenceWordItem = sentenceTextInfoParams.mItems.get(i);
                    SuggestionsInfo suggestionsInfo2 = null;
                    int n4 = 0;
                    do {
                        suggestionsInfo = suggestionsInfo2;
                        if (n4 >= arrsuggestionsInfo.length) break;
                        suggestionsInfo = arrsuggestionsInfo[n4];
                        if (suggestionsInfo != null && suggestionsInfo.getSequence() == sentenceWordItem.mTextInfo.getSequence()) {
                            suggestionsInfo.setCookieAndSequence(n, n2);
                            break;
                        }
                        ++n4;
                    } while (true);
                    arrn[i] = sentenceWordItem.mStart;
                    arrn2[i] = sentenceWordItem.mLength;
                    if (suggestionsInfo == null) {
                        suggestionsInfo = EMPTY_SUGGESTIONS_INFO;
                    }
                    arrsuggestionsInfo2[i] = suggestionsInfo;
                }
                return new SentenceSuggestionsInfo(arrsuggestionsInfo2, arrn, arrn2);
            }
            return null;
        }

        public static class SentenceTextInfoParams {
            final ArrayList<SentenceWordItem> mItems;
            final TextInfo mOriginalTextInfo;
            final int mSize;

            public SentenceTextInfoParams(TextInfo textInfo, ArrayList<SentenceWordItem> arrayList) {
                this.mOriginalTextInfo = textInfo;
                this.mItems = arrayList;
                this.mSize = arrayList.size();
            }
        }

        public static class SentenceWordItem {
            public final int mLength;
            public final int mStart;
            public final TextInfo mTextInfo;

            public SentenceWordItem(TextInfo textInfo, int n, int n2) {
                this.mTextInfo = textInfo;
                this.mStart = n;
                this.mLength = n2 - n;
            }
        }

    }

    public static abstract class Session {
        private InternalISpellCheckerSession mInternalSession;
        private volatile SentenceLevelAdapter mSentenceLevelAdapter;

        public Bundle getBundle() {
            return this.mInternalSession.getBundle();
        }

        public String getLocale() {
            return this.mInternalSession.getLocale();
        }

        public void onCancel() {
        }

        public void onClose() {
        }

        public abstract void onCreate();

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public SentenceSuggestionsInfo[] onGetSentenceSuggestionsMultiple(TextInfo[] arrtextInfo, int n) {
            Cloneable cloneable;
            TextInfo[] arrtextInfo2;
            Object object;
            if (arrtextInfo == null) return SentenceLevelAdapter.EMPTY_SENTENCE_SUGGESTIONS_INFOS;
            if (arrtextInfo.length == 0) {
                return SentenceLevelAdapter.EMPTY_SENTENCE_SUGGESTIONS_INFOS;
            }
            if (this.mSentenceLevelAdapter == null) {
                synchronized (this) {
                    if (this.mSentenceLevelAdapter == null && !TextUtils.isEmpty((CharSequence)(object = this.getLocale()))) {
                        cloneable = new Locale((String)object);
                        arrtextInfo2 = new SentenceLevelAdapter((Locale)cloneable);
                        this.mSentenceLevelAdapter = arrtextInfo2;
                    }
                }
            }
            if (this.mSentenceLevelAdapter == null) {
                return SentenceLevelAdapter.EMPTY_SENTENCE_SUGGESTIONS_INFOS;
            }
            int n2 = arrtextInfo.length;
            SentenceSuggestionsInfo[] arrsentenceSuggestionsInfo = new SentenceSuggestionsInfo[n2];
            int n3 = 0;
            while (n3 < n2) {
                object = this.mSentenceLevelAdapter.getSplitWords(arrtextInfo[n3]);
                cloneable = ((SentenceLevelAdapter.SentenceTextInfoParams)object).mItems;
                int n4 = ((ArrayList)cloneable).size();
                arrtextInfo2 = new TextInfo[n4];
                for (int i = 0; i < n4; ++i) {
                    arrtextInfo2[i] = ((SentenceLevelAdapter.SentenceWordItem)cloneable.get((int)i)).mTextInfo;
                }
                arrsentenceSuggestionsInfo[n3] = SentenceLevelAdapter.reconstructSuggestions((SentenceLevelAdapter.SentenceTextInfoParams)object, this.onGetSuggestionsMultiple(arrtextInfo2, n, true));
                ++n3;
            }
            return arrsentenceSuggestionsInfo;
        }

        public abstract SuggestionsInfo onGetSuggestions(TextInfo var1, int var2);

        public SuggestionsInfo[] onGetSuggestionsMultiple(TextInfo[] arrtextInfo, int n, boolean bl) {
            int n2 = arrtextInfo.length;
            SuggestionsInfo[] arrsuggestionsInfo = new SuggestionsInfo[n2];
            for (int i = 0; i < n2; ++i) {
                arrsuggestionsInfo[i] = this.onGetSuggestions(arrtextInfo[i], n);
                arrsuggestionsInfo[i].setCookieAndSequence(arrtextInfo[i].getCookie(), arrtextInfo[i].getSequence());
            }
            return arrsuggestionsInfo;
        }

        public final void setInternalISpellCheckerSession(InternalISpellCheckerSession internalISpellCheckerSession) {
            this.mInternalSession = internalISpellCheckerSession;
        }
    }

    private static class SpellCheckerServiceBinder
    extends ISpellCheckerService.Stub {
        private final WeakReference<SpellCheckerService> mInternalServiceRef;

        public SpellCheckerServiceBinder(SpellCheckerService spellCheckerService) {
            this.mInternalServiceRef = new WeakReference<SpellCheckerService>(spellCheckerService);
        }

        @Override
        public void getISpellCheckerSession(String object, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, ISpellCheckerServiceCallback iSpellCheckerServiceCallback) {
            Object object2 = (SpellCheckerService)this.mInternalServiceRef.get();
            if (object2 == null) {
                object = null;
            } else {
                object2 = ((SpellCheckerService)object2).createSession();
                object = new InternalISpellCheckerSession((String)object, iSpellCheckerSessionListener, bundle, (Session)object2);
                ((Session)object2).onCreate();
            }
            try {
                iSpellCheckerServiceCallback.onSessionCreated((ISpellCheckerSession)object);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

}

