/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.WordIterator;
import android.text.style.SpellCheckSpan;
import android.text.style.SuggestionSpan;
import android.util.Log;
import android.util.LruCache;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SpellCheckerSubtype;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.TextView;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.util.Locale;

public class SpellChecker
implements SpellCheckerSession.SpellCheckerSessionListener {
    public static final int AVERAGE_WORD_LENGTH = 7;
    private static final boolean DBG = false;
    public static final int MAX_NUMBER_OF_WORDS = 50;
    private static final int MIN_SENTENCE_LENGTH = 50;
    private static final int SPELL_PAUSE_DURATION = 400;
    private static final int SUGGESTION_SPAN_CACHE_SIZE = 10;
    private static final String TAG = SpellChecker.class.getSimpleName();
    private static final int USE_SPAN_RANGE = -1;
    public static final int WORD_ITERATOR_INTERVAL = 350;
    final int mCookie;
    private Locale mCurrentLocale;
    private int[] mIds;
    private boolean mIsSentenceSpellCheckSupported;
    private int mLength;
    private int mSpanSequenceCounter = 0;
    private SpellCheckSpan[] mSpellCheckSpans;
    SpellCheckerSession mSpellCheckerSession;
    private SpellParser[] mSpellParsers = new SpellParser[0];
    private Runnable mSpellRunnable;
    private final LruCache<Long, SuggestionSpan> mSuggestionSpanCache = new LruCache(10);
    private TextServicesManager mTextServicesManager;
    private final TextView mTextView;
    private WordIterator mWordIterator;

    public SpellChecker(TextView textView) {
        this.mTextView = textView;
        this.mIds = ArrayUtils.newUnpaddedIntArray(1);
        this.mSpellCheckSpans = new SpellCheckSpan[this.mIds.length];
        this.setLocale(this.mTextView.getSpellCheckerLocale());
        this.mCookie = this.hashCode();
    }

    private void addSpellCheckSpan(Editable arrn, int n, int n2) {
        int n3 = this.nextSpellCheckSpanIndex();
        SpellCheckSpan spellCheckSpan = this.mSpellCheckSpans[n3];
        arrn.setSpan(spellCheckSpan, n, n2, 33);
        spellCheckSpan.setSpellCheckInProgress(false);
        arrn = this.mIds;
        n = this.mSpanSequenceCounter;
        this.mSpanSequenceCounter = n + 1;
        arrn[n3] = n;
    }

    private void createMisspelledSuggestionSpan(Editable editable, SuggestionsInfo object, SpellCheckSpan object2, int n, int n2) {
        int n3 = editable.getSpanStart(object2);
        int n4 = editable.getSpanEnd(object2);
        if (n3 >= 0 && n4 > n3) {
            if (n != -1 && n2 != -1) {
                n = n3 + n;
                n2 = n + n2;
            } else {
                n = n3;
                n2 = n4;
            }
            n3 = object.getSuggestionsCount();
            if (n3 > 0) {
                object2 = new String[n3];
                for (n4 = 0; n4 < n3; ++n4) {
                    object2[n4] = object.getSuggestionAt(n4);
                }
                object = object2;
            } else {
                object = ArrayUtils.emptyArray(String.class);
            }
            SuggestionSpan suggestionSpan = new SuggestionSpan(this.mTextView.getContext(), (String[])object, 3);
            if (this.mIsSentenceSpellCheckSupported) {
                object = TextUtils.packRangeInLong(n, n2);
                object2 = this.mSuggestionSpanCache.get((Long)object);
                if (object2 != null) {
                    editable.removeSpan(object2);
                }
                this.mSuggestionSpanCache.put((Long)object, suggestionSpan);
            }
            editable.setSpan(suggestionSpan, n, n2, 33);
            this.mTextView.invalidateRegion(n, n2, false);
            return;
        }
    }

    public static boolean haveWordBoundariesChanged(Editable editable, int n, int n2, int n3, int n4) {
        boolean bl = n4 != n && n3 != n2 ? true : (n4 == n && n < editable.length() ? Character.isLetterOrDigit(Character.codePointAt(editable, n)) : (n3 == n2 && n2 > 0 ? Character.isLetterOrDigit(Character.codePointBefore(editable, n2)) : false));
        return bl;
    }

    private boolean isSessionActive() {
        boolean bl = this.mSpellCheckerSession != null;
        return bl;
    }

    private int nextSpellCheckSpanIndex() {
        int n;
        for (int i = 0; i < (n = this.mLength); ++i) {
            if (this.mIds[i] >= 0) continue;
            return i;
        }
        this.mIds = GrowingArrayUtils.append(this.mIds, n, 0);
        this.mSpellCheckSpans = GrowingArrayUtils.append(this.mSpellCheckSpans, this.mLength, new SpellCheckSpan());
        ++this.mLength;
        return this.mLength - 1;
    }

    private SpellCheckSpan onGetSuggestionsInternal(SuggestionsInfo object, int n, int n2) {
        if (object != null && ((SuggestionsInfo)object).getCookie() == this.mCookie) {
            Editable editable = (Editable)this.mTextView.getText();
            int n3 = ((SuggestionsInfo)object).getSequence();
            for (int i = 0; i < this.mLength; ++i) {
                if (n3 != this.mIds[i]) continue;
                int n4 = ((SuggestionsInfo)object).getSuggestionsAttributes();
                int n5 = 0;
                n3 = (n4 & 1) > 0 ? 1 : 0;
                if ((n4 & 2) > 0) {
                    n5 = 1;
                }
                SpellCheckSpan spellCheckSpan = this.mSpellCheckSpans[i];
                if (n3 == 0 && n5 != 0) {
                    this.createMisspelledSuggestionSpan(editable, (SuggestionsInfo)object, spellCheckSpan, n, n2);
                } else if (this.mIsSentenceSpellCheckSupported) {
                    SuggestionSpan suggestionSpan;
                    i = editable.getSpanStart(spellCheckSpan);
                    n3 = editable.getSpanEnd(spellCheckSpan);
                    if (n != -1 && n2 != -1) {
                        n5 = i + n;
                        n = n5 + n2;
                        n2 = n5;
                    } else {
                        n2 = i;
                        n = n3;
                    }
                    if (i >= 0 && n3 > i && n > n2 && (suggestionSpan = this.mSuggestionSpanCache.get((Long)(object = Long.valueOf(TextUtils.packRangeInLong(n2, n))))) != null) {
                        editable.removeSpan(suggestionSpan);
                        this.mSuggestionSpanCache.remove((Long)object);
                    }
                }
                return spellCheckSpan;
            }
            return null;
        }
        return null;
    }

    private void scheduleNewSpellCheck() {
        Runnable runnable = this.mSpellRunnable;
        if (runnable == null) {
            this.mSpellRunnable = new Runnable(){

                @Override
                public void run() {
                    int n = SpellChecker.this.mSpellParsers.length;
                    for (int i = 0; i < n; ++i) {
                        SpellParser spellParser = SpellChecker.this.mSpellParsers[i];
                        if (spellParser.isFinished()) continue;
                        spellParser.parse();
                        break;
                    }
                }
            };
        } else {
            this.mTextView.removeCallbacks(runnable);
        }
        this.mTextView.postDelayed(this.mSpellRunnable, 400L);
    }

    private void setLocale(Locale locale) {
        this.mCurrentLocale = locale;
        this.resetSession();
        if (locale != null) {
            this.mWordIterator = new WordIterator(locale);
        }
        this.mTextView.onLocaleChanged();
    }

    private void spellCheck() {
        if (this.mSpellCheckerSession == null) {
            return;
        }
        TextInfo[] arrtextInfo = (TextInfo[])this.mTextView.getText();
        int n = Selection.getSelectionStart((CharSequence)arrtextInfo);
        int n2 = Selection.getSelectionEnd((CharSequence)arrtextInfo);
        TextInfo[] arrtextInfo2 = new TextInfo[this.mLength];
        int n3 = 0;
        int n4 = 0;
        do {
            int n5;
            block14 : {
                SpellCheckSpan spellCheckSpan;
                int n6;
                int n7;
                boolean bl;
                block17 : {
                    block19 : {
                        boolean bl2;
                        block18 : {
                            block16 : {
                                block15 : {
                                    n5 = this.mLength;
                                    bl2 = false;
                                    bl = false;
                                    if (n4 >= n5) break;
                                    spellCheckSpan = this.mSpellCheckSpans[n4];
                                    n5 = n3;
                                    if (this.mIds[n4] < 0) break block14;
                                    if (!spellCheckSpan.isSpellCheckInProgress()) break block15;
                                    n5 = n3;
                                    break block14;
                                }
                                n6 = arrtextInfo.getSpanStart(spellCheckSpan);
                                n7 = arrtextInfo.getSpanEnd(spellCheckSpan);
                                if (n != n7 + 1 || !WordIterator.isMidWordPunctuation(this.mCurrentLocale, Character.codePointBefore((CharSequence)arrtextInfo, n7 + 1))) break block16;
                                bl = false;
                                break block17;
                            }
                            if (!this.mIsSentenceSpellCheckSupported) break block18;
                            if (n2 <= n6 || n > n7) {
                                bl = true;
                            }
                            break block17;
                        }
                        if (n2 < n6) break block19;
                        bl = bl2;
                        if (n <= n7) break block17;
                    }
                    bl = true;
                }
                n5 = n3;
                if (n6 >= 0) {
                    n5 = n3;
                    if (n7 > n6) {
                        n5 = n3;
                        if (bl) {
                            spellCheckSpan.setSpellCheckInProgress(true);
                            arrtextInfo2[n3] = new TextInfo((CharSequence)arrtextInfo, n6, n7, this.mCookie, this.mIds[n4]);
                            n5 = n3 + 1;
                        }
                    }
                }
            }
            ++n4;
            n3 = n5;
        } while (true);
        if (n3 > 0) {
            arrtextInfo = arrtextInfo2;
            if (n3 < arrtextInfo2.length) {
                arrtextInfo = new TextInfo[n3];
                System.arraycopy(arrtextInfo2, 0, arrtextInfo, 0, n3);
            }
            if (this.mIsSentenceSpellCheckSupported) {
                this.mSpellCheckerSession.getSentenceSuggestions(arrtextInfo, 5);
            } else {
                this.mSpellCheckerSession.getSuggestions(arrtextInfo, 5, false);
            }
        }
    }

    public void closeSession() {
        Object object = this.mSpellCheckerSession;
        if (object != null) {
            ((SpellCheckerSession)object).close();
        }
        int n = this.mSpellParsers.length;
        for (int i = 0; i < n; ++i) {
            this.mSpellParsers[i].stop();
        }
        object = this.mSpellRunnable;
        if (object != null) {
            this.mTextView.removeCallbacks((Runnable)object);
        }
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] arrsentenceSuggestionsInfo) {
        Editable editable = (Editable)this.mTextView.getText();
        for (int i = 0; i < arrsentenceSuggestionsInfo.length; ++i) {
            SentenceSuggestionsInfo sentenceSuggestionsInfo = arrsentenceSuggestionsInfo[i];
            if (sentenceSuggestionsInfo == null) continue;
            SuggestionsInfo suggestionsInfo = null;
            for (int j = 0; j < sentenceSuggestionsInfo.getSuggestionsCount(); ++j) {
                Parcelable parcelable = sentenceSuggestionsInfo.getSuggestionsInfoAt(j);
                if (parcelable == null) {
                    parcelable = suggestionsInfo;
                } else {
                    SpellCheckSpan spellCheckSpan = this.onGetSuggestionsInternal((SuggestionsInfo)parcelable, sentenceSuggestionsInfo.getOffsetAt(j), sentenceSuggestionsInfo.getLengthAt(j));
                    parcelable = suggestionsInfo;
                    if (suggestionsInfo == null) {
                        parcelable = suggestionsInfo;
                        if (spellCheckSpan != null) {
                            parcelable = spellCheckSpan;
                        }
                    }
                }
                suggestionsInfo = parcelable;
            }
            if (suggestionsInfo == null) continue;
            editable.removeSpan(suggestionsInfo);
        }
        this.scheduleNewSpellCheck();
    }

    @Override
    public void onGetSuggestions(SuggestionsInfo[] arrsuggestionsInfo) {
        Editable editable = (Editable)this.mTextView.getText();
        for (int i = 0; i < arrsuggestionsInfo.length; ++i) {
            SpellCheckSpan spellCheckSpan = this.onGetSuggestionsInternal(arrsuggestionsInfo[i], -1, -1);
            if (spellCheckSpan == null) continue;
            editable.removeSpan(spellCheckSpan);
        }
        this.scheduleNewSpellCheck();
    }

    public void onSelectionChanged() {
        this.spellCheck();
    }

    public void onSpellCheckSpanRemoved(SpellCheckSpan spellCheckSpan) {
        for (int i = 0; i < this.mLength; ++i) {
            if (this.mSpellCheckSpans[i] != spellCheckSpan) continue;
            this.mIds[i] = -1;
            return;
        }
    }

    void resetSession() {
        this.closeSession();
        this.mTextServicesManager = this.mTextView.getTextServicesManagerForUser();
        if (this.mCurrentLocale != null && this.mTextServicesManager != null && this.mTextView.length() != 0 && this.mTextServicesManager.isSpellCheckerEnabled() && this.mTextServicesManager.getCurrentSpellCheckerSubtype(true) != null) {
            this.mSpellCheckerSession = this.mTextServicesManager.newSpellCheckerSession(null, this.mCurrentLocale, this, false);
            this.mIsSentenceSpellCheckSupported = true;
        } else {
            this.mSpellCheckerSession = null;
        }
        for (int i = 0; i < this.mLength; ++i) {
            this.mIds[i] = -1;
        }
        this.mLength = 0;
        TextView textView = this.mTextView;
        textView.removeMisspelledSpans((Editable)textView.getText());
        this.mSuggestionSpanCache.evictAll();
    }

    public void spellCheck(int n, int n2) {
        int n3;
        Locale locale;
        int n4;
        Object object = this.mTextView.getSpellCheckerLocale();
        boolean bl = this.isSessionActive();
        if (object != null && (locale = this.mCurrentLocale) != null && locale.equals(object)) {
            object = this.mTextServicesManager;
            boolean bl2 = object != null && ((TextServicesManager)object).isSpellCheckerEnabled();
            n4 = n;
            n3 = n2;
            if (bl != bl2) {
                this.resetSession();
                n4 = n;
                n3 = n2;
            }
        } else {
            this.setLocale((Locale)object);
            n4 = 0;
            n3 = this.mTextView.getText().length();
        }
        if (!bl) {
            return;
        }
        n2 = this.mSpellParsers.length;
        for (n = 0; n < n2; ++n) {
            object = this.mSpellParsers[n];
            if (!((SpellParser)object).isFinished()) continue;
            ((SpellParser)object).parse(n4, n3);
            return;
        }
        object = new SpellParser[n2 + 1];
        System.arraycopy(this.mSpellParsers, 0, object, 0, n2);
        this.mSpellParsers = object;
        this.mSpellParsers[n2] = object = new SpellParser();
        ((SpellParser)object).parse(n4, n3);
    }

    private class SpellParser {
        private Object mRange = new Object();

        private SpellParser() {
        }

        private void removeRangeSpan(Editable editable) {
            editable.removeSpan(this.mRange);
        }

        private <T> void removeSpansAt(Editable editable, int n, T[] arrT) {
            for (T t : arrT) {
                if (editable.getSpanStart(t) > n || editable.getSpanEnd(t) < n) continue;
                editable.removeSpan(t);
            }
        }

        private void setRangeSpan(Editable editable, int n, int n2) {
            editable.setSpan(this.mRange, n, n2, 33);
        }

        public boolean isFinished() {
            boolean bl = ((Editable)SpellChecker.this.mTextView.getText()).getSpanStart(this.mRange) < 0;
            return bl;
        }

        public void parse() {
            int n;
            int n2;
            Editable editable = (Editable)SpellChecker.this.mTextView.getText();
            int n3 = SpellChecker.this.mIsSentenceSpellCheckSupported ? Math.max(0, editable.getSpanStart(this.mRange) - 50) : editable.getSpanStart(this.mRange);
            int n4 = editable.getSpanEnd(this.mRange);
            int n5 = Math.min(n4, n3 + 350);
            SpellChecker.this.mWordIterator.setCharSequence(editable, n3, n5);
            int n6 = SpellChecker.this.mWordIterator.preceding(n3);
            if (n6 == -1) {
                n2 = n = SpellChecker.this.mWordIterator.following(n3);
                if (n != -1) {
                    n6 = SpellChecker.this.mWordIterator.getBeginning(n);
                    n2 = n;
                }
            } else {
                n2 = SpellChecker.this.mWordIterator.getEnd(n6);
            }
            if (n2 == -1) {
                this.removeRangeSpan(editable);
                return;
            }
            Object object = editable.getSpans(n3 - 1, n4 + 1, SpellCheckSpan.class);
            Object object2 = editable.getSpans(n3 - 1, n4 + 1, SuggestionSpan.class);
            int n7 = 0;
            int n8 = 0;
            n = 0;
            if (SpellChecker.this.mIsSentenceSpellCheckSupported) {
                block40 : {
                    if (n5 < n4) {
                        n = 1;
                    }
                    n7 = (n8 = SpellChecker.this.mWordIterator.preceding(n5)) != -1 ? 1 : 0;
                    int n9 = n8;
                    int n10 = n7;
                    if (n7 != 0) {
                        n7 = SpellChecker.this.mWordIterator.getEnd(n8);
                        n9 = n7 != -1 ? 1 : 0;
                        n10 = n9;
                        n9 = n7;
                    }
                    if (n10 == 0) {
                        this.removeRangeSpan(editable);
                        return;
                    }
                    n7 = n6;
                    int n11 = 1;
                    n10 = n9;
                    n8 = 0;
                    n9 = n2;
                    n2 = n10;
                    for (n10 = n8; n10 < SpellChecker.this.mLength; ++n10) {
                        int n12;
                        object = SpellChecker.this.mSpellCheckSpans[n10];
                        if (SpellChecker.this.mIds[n10] >= 0) {
                            if (((SpellCheckSpan)object).isSpellCheckInProgress()) {
                                n8 = n7;
                                n12 = n2;
                            } else {
                                int n13 = editable.getSpanStart(object);
                                int n14 = editable.getSpanEnd(object);
                                n8 = n7;
                                n12 = n2;
                                if (n14 >= n7) {
                                    if (n2 < n13) {
                                        n8 = n7;
                                        n12 = n2;
                                    } else {
                                        if (n13 <= n7 && n2 <= n14) {
                                            n6 = 0;
                                            break block40;
                                        }
                                        editable.removeSpan(object);
                                        n8 = Math.min(n13, n7);
                                        n12 = Math.max(n14, n2);
                                    }
                                }
                            }
                        } else {
                            n12 = n2;
                            n8 = n7;
                        }
                        n7 = n8;
                        n2 = n12;
                    }
                    n6 = n11;
                }
                if (n2 >= n3) {
                    if (n2 <= n7) {
                        object2 = TAG;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Trying to spellcheck invalid region, from ");
                        ((StringBuilder)object).append(n3);
                        ((StringBuilder)object).append(" to ");
                        ((StringBuilder)object).append(n4);
                        Log.w((String)object2, ((StringBuilder)object).toString());
                    } else if (n6 != 0) {
                        SpellChecker.this.addSpellCheckSpan(editable, n7, n2);
                    }
                }
            } else {
                int n15 = n2;
                do {
                    n2 = n6;
                    n = n8;
                    if (n6 > n4) break;
                    n = n7;
                    if (n15 >= n3) {
                        n = n7;
                        if (n15 > n6) {
                            int n16;
                            if (n7 >= 50) {
                                n = 1;
                                n2 = n6;
                                break;
                            }
                            if (n6 < n3 && n15 > n3) {
                                this.removeSpansAt(editable, n3, (T[])object);
                                this.removeSpansAt(editable, n3, (T[])object2);
                            }
                            if (n6 < n4 && n15 > n4) {
                                this.removeSpansAt(editable, n4, (T[])object);
                                this.removeSpansAt(editable, n4, (T[])object2);
                            }
                            n2 = n16 = 1;
                            if (n15 == n3) {
                                n = 0;
                                do {
                                    n2 = n16;
                                    if (n >= ((SpellCheckSpan[])object).length) break;
                                    if (editable.getSpanEnd(object[n]) == n3) {
                                        n2 = 0;
                                        break;
                                    }
                                    ++n;
                                } while (true);
                            }
                            n = n2;
                            if (n6 == n4) {
                                n16 = 0;
                                do {
                                    n = n2;
                                    if (n16 >= ((SpellCheckSpan[])object).length) break;
                                    if (editable.getSpanStart(object[n16]) == n4) {
                                        n = 0;
                                        break;
                                    }
                                    ++n16;
                                } while (true);
                            }
                            if (n != 0) {
                                SpellChecker.this.addSpellCheckSpan(editable, n6, n15);
                            }
                            n = n7 + 1;
                        }
                    }
                    n2 = SpellChecker.this.mWordIterator.following(n15);
                    if (n5 < n4 && (n2 == -1 || n2 >= n5)) {
                        n5 = Math.min(n4, n15 + 350);
                        SpellChecker.this.mWordIterator.setCharSequence(editable, n15, n5);
                        n2 = SpellChecker.this.mWordIterator.following(n15);
                    }
                    if (n2 == -1) {
                        n2 = n6;
                        n = n8;
                        break;
                    }
                    n6 = SpellChecker.this.mWordIterator.getBeginning(n2);
                    if (n6 == -1) {
                        n2 = n6;
                        n = n8;
                        break;
                    }
                    n15 = n2;
                    n7 = n;
                } while (true);
            }
            if (n != 0 && n2 != -1 && n2 <= n4) {
                this.setRangeSpan(editable, n2, n4);
            } else {
                this.removeRangeSpan(editable);
            }
            SpellChecker.this.spellCheck();
        }

        public void parse(int n, int n2) {
            int n3 = SpellChecker.this.mTextView.length();
            if (n2 > n3) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Parse invalid region, from ");
                stringBuilder.append(n);
                stringBuilder.append(" to ");
                stringBuilder.append(n2);
                Log.w(string2, stringBuilder.toString());
                n2 = n3;
            }
            if (n2 > n) {
                this.setRangeSpan((Editable)SpellChecker.this.mTextView.getText(), n, n2);
                this.parse();
            }
        }

        public void stop() {
            this.removeRangeSpan((Editable)SpellChecker.this.mTextView.getText());
        }
    }

}

