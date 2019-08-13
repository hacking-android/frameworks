/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.speech.tts.ITextToSpeechCallback;
import android.speech.tts.ITextToSpeechService;
import android.speech.tts.TtsEngines;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

public class TextToSpeech {
    public static final String ACTION_TTS_QUEUE_PROCESSING_COMPLETED = "android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED";
    public static final int ERROR = -1;
    public static final int ERROR_INVALID_REQUEST = -8;
    public static final int ERROR_NETWORK = -6;
    public static final int ERROR_NETWORK_TIMEOUT = -7;
    public static final int ERROR_NOT_INSTALLED_YET = -9;
    public static final int ERROR_OUTPUT = -5;
    public static final int ERROR_SERVICE = -4;
    public static final int ERROR_SYNTHESIS = -3;
    public static final int LANG_AVAILABLE = 0;
    public static final int LANG_COUNTRY_AVAILABLE = 1;
    public static final int LANG_COUNTRY_VAR_AVAILABLE = 2;
    public static final int LANG_MISSING_DATA = -1;
    public static final int LANG_NOT_SUPPORTED = -2;
    public static final int QUEUE_ADD = 1;
    static final int QUEUE_DESTROY = 2;
    public static final int QUEUE_FLUSH = 0;
    public static final int STOPPED = -2;
    public static final int SUCCESS = 0;
    private static final String TAG = "TextToSpeech";
    @UnsupportedAppUsage
    private Connection mConnectingServiceConnection;
    private final Context mContext;
    @UnsupportedAppUsage
    private volatile String mCurrentEngine = null;
    private final Map<String, Uri> mEarcons;
    private final TtsEngines mEnginesHelper;
    @UnsupportedAppUsage
    private OnInitListener mInitListener;
    private final Bundle mParams = new Bundle();
    private String mRequestedEngine;
    private Connection mServiceConnection;
    private final Object mStartLock = new Object();
    private final boolean mUseFallback;
    private volatile UtteranceProgressListener mUtteranceProgressListener;
    private final Map<CharSequence, Uri> mUtterances;

    public TextToSpeech(Context context, OnInitListener onInitListener) {
        this(context, onInitListener, null);
    }

    public TextToSpeech(Context context, OnInitListener onInitListener, String string2) {
        this(context, onInitListener, string2, null, true);
    }

    public TextToSpeech(Context context, OnInitListener onInitListener, String string2, String string3, boolean bl) {
        this.mContext = context;
        this.mInitListener = onInitListener;
        this.mRequestedEngine = string2;
        this.mUseFallback = bl;
        this.mEarcons = new HashMap<String, Uri>();
        this.mUtterances = new HashMap<CharSequence, Uri>();
        this.mUtteranceProgressListener = null;
        this.mEnginesHelper = new TtsEngines(this.mContext);
        this.initTts();
    }

    private boolean connectToEngine(String string2) {
        Object object = new Connection();
        Object object2 = new Intent("android.intent.action.TTS_SERVICE");
        ((Intent)object2).setPackage(string2);
        if (!this.mContext.bindService((Intent)object2, (ServiceConnection)object, 1)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to bind to ");
            ((StringBuilder)object).append(string2);
            Log.e(TAG, ((StringBuilder)object).toString());
            return false;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Sucessfully bound to ");
        ((StringBuilder)object2).append(string2);
        Log.i(TAG, ((StringBuilder)object2).toString());
        this.mConnectingServiceConnection = object;
        return true;
    }

    private Bundle convertParamsHashMaptoBundle(HashMap<String, String> object) {
        if (object != null && !((HashMap)((Object)object)).isEmpty()) {
            Bundle bundle = new Bundle();
            this.copyIntParam(bundle, (HashMap<String, String>)((Object)object), "streamType");
            this.copyIntParam(bundle, (HashMap<String, String>)((Object)object), "sessionId");
            this.copyStringParam(bundle, (HashMap<String, String>)((Object)object), "utteranceId");
            this.copyFloatParam(bundle, (HashMap<String, String>)((Object)object), "volume");
            this.copyFloatParam(bundle, (HashMap<String, String>)((Object)object), "pan");
            this.copyStringParam(bundle, (HashMap<String, String>)((Object)object), "networkTts");
            this.copyStringParam(bundle, (HashMap<String, String>)((Object)object), "embeddedTts");
            this.copyIntParam(bundle, (HashMap<String, String>)((Object)object), "networkTimeoutMs");
            this.copyIntParam(bundle, (HashMap<String, String>)((Object)object), "networkRetriesCount");
            if (!TextUtils.isEmpty(this.mCurrentEngine)) {
                for (Map.Entry entry : ((HashMap)((Object)object)).entrySet()) {
                    String string2 = (String)entry.getKey();
                    if (string2 == null || !string2.startsWith(this.mCurrentEngine)) continue;
                    bundle.putString(string2, (String)entry.getValue());
                }
            }
            return bundle;
        }
        return null;
    }

    private void copyFloatParam(Bundle bundle, HashMap<String, String> object, String string2) {
        if (!TextUtils.isEmpty((CharSequence)(object = ((HashMap)object).get(string2)))) {
            try {
                bundle.putFloat(string2, Float.parseFloat((String)object));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
    }

    private void copyIntParam(Bundle bundle, HashMap<String, String> object, String string2) {
        if (!TextUtils.isEmpty((CharSequence)(object = ((HashMap)object).get(string2)))) {
            try {
                bundle.putInt(string2, Integer.parseInt((String)object));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
    }

    private void copyStringParam(Bundle bundle, HashMap<String, String> object, String string2) {
        if ((object = ((HashMap)object).get(string2)) != null) {
            bundle.putString(string2, (String)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dispatchOnInit(int n) {
        Object object = this.mStartLock;
        synchronized (object) {
            if (this.mInitListener != null) {
                this.mInitListener.onInit(n);
                this.mInitListener = null;
            }
            return;
        }
    }

    private IBinder getCallerIdentity() {
        return this.mServiceConnection.getCallerIdentity();
    }

    public static int getMaxSpeechInputLength() {
        return 4000;
    }

    private Bundle getParams(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            Bundle bundle2 = new Bundle(this.mParams);
            bundle2.putAll(bundle);
            TextToSpeech.verifyIntegerBundleParam(bundle2, "streamType");
            TextToSpeech.verifyIntegerBundleParam(bundle2, "sessionId");
            TextToSpeech.verifyStringBundleParam(bundle2, "utteranceId");
            TextToSpeech.verifyFloatBundleParam(bundle2, "volume");
            TextToSpeech.verifyFloatBundleParam(bundle2, "pan");
            TextToSpeech.verifyBooleanBundleParam(bundle2, "networkTts");
            TextToSpeech.verifyBooleanBundleParam(bundle2, "embeddedTts");
            TextToSpeech.verifyIntegerBundleParam(bundle2, "networkTimeoutMs");
            TextToSpeech.verifyIntegerBundleParam(bundle2, "networkRetriesCount");
            return bundle2;
        }
        return this.mParams;
    }

    private Voice getVoice(ITextToSpeechService object, String string2) throws RemoteException {
        if ((object = object.getVoices()) == null) {
            Log.w(TAG, "getVoices returned null");
            return null;
        }
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            object = (Voice)iterator.next();
            if (!((Voice)object).getName().equals(string2)) continue;
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not find voice ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" in voice list");
        Log.w(TAG, ((StringBuilder)object).toString());
        return null;
    }

    private int initTts() {
        CharSequence charSequence = this.mRequestedEngine;
        if (charSequence != null) {
            if (this.mEnginesHelper.isEngineInstalled((String)charSequence)) {
                if (this.connectToEngine(this.mRequestedEngine)) {
                    this.mCurrentEngine = this.mRequestedEngine;
                    return 0;
                }
                if (!this.mUseFallback) {
                    this.mCurrentEngine = null;
                    this.dispatchOnInit(-1);
                    return -1;
                }
            } else if (!this.mUseFallback) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Requested engine not installed: ");
                ((StringBuilder)charSequence).append(this.mRequestedEngine);
                Log.i(TAG, ((StringBuilder)charSequence).toString());
                this.mCurrentEngine = null;
                this.dispatchOnInit(-1);
                return -1;
            }
        }
        if ((charSequence = this.getDefaultEngine()) != null && !((String)charSequence).equals(this.mRequestedEngine) && this.connectToEngine((String)charSequence)) {
            this.mCurrentEngine = charSequence;
            return 0;
        }
        String string2 = this.mEnginesHelper.getHighestRankedEngineName();
        if (string2 != null && !string2.equals(this.mRequestedEngine) && !string2.equals(charSequence) && this.connectToEngine(string2)) {
            this.mCurrentEngine = string2;
            return 0;
        }
        this.mCurrentEngine = null;
        this.dispatchOnInit(-1);
        return -1;
    }

    private Uri makeResourceUri(String string2, int n) {
        return new Uri.Builder().scheme("android.resource").encodedAuthority(string2).appendEncodedPath(String.valueOf(n)).build();
    }

    private <R> R runAction(Action<R> action, R r, String string2) {
        return this.runAction(action, r, string2, true, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private <R> R runAction(Action<R> object, R r, String string2, boolean bl, boolean bl2) {
        Object object2 = this.mStartLock;
        synchronized (object2) {
            if (this.mServiceConnection == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" failed: not bound to TTS engine");
                Log.w(TAG, ((StringBuilder)object).toString());
                return r;
            }
            object = this.mServiceConnection.runAction(object, r, string2, bl, bl2);
            return (R)object;
        }
    }

    private <R> R runActionNoReconnect(Action<R> action, R r, String string2, boolean bl) {
        return this.runAction(action, r, string2, false, bl);
    }

    private static boolean verifyBooleanBundleParam(Bundle object, String string2) {
        if (((BaseBundle)object).containsKey(string2) && !(((BaseBundle)object).get(string2) instanceof Boolean) && !(((BaseBundle)object).get(string2) instanceof String)) {
            ((Bundle)object).remove(string2);
            object = new StringBuilder();
            ((StringBuilder)object).append("Synthesis request paramter ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" containst value  with invalid type. Should be a Boolean or String");
            Log.w(TAG, ((StringBuilder)object).toString());
            return false;
        }
        return true;
    }

    private static boolean verifyFloatBundleParam(Bundle object, String string2) {
        if (((BaseBundle)object).containsKey(string2) && !(((BaseBundle)object).get(string2) instanceof Float) && !(((BaseBundle)object).get(string2) instanceof Double)) {
            ((Bundle)object).remove(string2);
            object = new StringBuilder();
            ((StringBuilder)object).append("Synthesis request paramter ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" containst value  with invalid type. Should be a Float or a Double");
            Log.w(TAG, ((StringBuilder)object).toString());
            return false;
        }
        return true;
    }

    private static boolean verifyIntegerBundleParam(Bundle object, String string2) {
        if (((BaseBundle)object).containsKey(string2) && !(((BaseBundle)object).get(string2) instanceof Integer) && !(((BaseBundle)object).get(string2) instanceof Long)) {
            ((Bundle)object).remove(string2);
            object = new StringBuilder();
            ((StringBuilder)object).append("Synthesis request paramter ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" containst value  with invalid type. Should be an Integer or a Long");
            Log.w(TAG, ((StringBuilder)object).toString());
            return false;
        }
        return true;
    }

    private static boolean verifyStringBundleParam(Bundle object, String string2) {
        if (((BaseBundle)object).containsKey(string2) && !(((BaseBundle)object).get(string2) instanceof String)) {
            ((Bundle)object).remove(string2);
            object = new StringBuilder();
            ((StringBuilder)object).append("Synthesis request paramter ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" containst value  with invalid type. Should be a String");
            Log.w(TAG, ((StringBuilder)object).toString());
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int addEarcon(String string2, File file) {
        Object object = this.mStartLock;
        synchronized (object) {
            this.mEarcons.put(string2, Uri.fromFile(file));
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public int addEarcon(String string2, String string3) {
        Object object = this.mStartLock;
        synchronized (object) {
            this.mEarcons.put(string2, Uri.parse(string3));
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int addEarcon(String string2, String string3, int n) {
        Object object = this.mStartLock;
        synchronized (object) {
            this.mEarcons.put(string2, this.makeResourceUri(string3, n));
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int addSpeech(CharSequence charSequence, File file) {
        Object object = this.mStartLock;
        synchronized (object) {
            this.mUtterances.put(charSequence, Uri.fromFile(file));
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int addSpeech(CharSequence charSequence, String string2, int n) {
        Object object = this.mStartLock;
        synchronized (object) {
            this.mUtterances.put(charSequence, this.makeResourceUri(string2, n));
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int addSpeech(String string2, String string3) {
        Object object = this.mStartLock;
        synchronized (object) {
            this.mUtterances.put(string2, Uri.parse(string3));
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int addSpeech(String string2, String string3, int n) {
        Object object = this.mStartLock;
        synchronized (object) {
            this.mUtterances.put(string2, this.makeResourceUri(string3, n));
            return 0;
        }
    }

    @Deprecated
    public boolean areDefaultsEnforced() {
        return false;
    }

    public Set<Locale> getAvailableLanguages() {
        return this.runAction(new Action<Set<Locale>>(){

            @Override
            public Set<Locale> run(ITextToSpeechService object) throws RemoteException {
                List<Voice> list = object.getVoices();
                if (list == null) {
                    return new HashSet<Locale>();
                }
                object = new HashSet();
                list = list.iterator();
                while (list.hasNext()) {
                    ((HashSet)object).add(((Voice)list.next()).getLocale());
                }
                return object;
            }
        }, null, "getAvailableLanguages");
    }

    @UnsupportedAppUsage
    public String getCurrentEngine() {
        return this.mCurrentEngine;
    }

    public String getDefaultEngine() {
        return this.mEnginesHelper.getDefaultEngine();
    }

    @Deprecated
    public Locale getDefaultLanguage() {
        return this.runAction(new Action<Locale>(){

            @Override
            public Locale run(ITextToSpeechService arrstring) throws RemoteException {
                arrstring = arrstring.getClientDefaultLanguage();
                return new Locale(arrstring[0], arrstring[1], arrstring[2]);
            }
        }, null, "getDefaultLanguage");
    }

    public Voice getDefaultVoice() {
        return this.runAction(new Action<Voice>(){

            @Override
            public Voice run(ITextToSpeechService object) throws RemoteException {
                String[] arrstring = object.getClientDefaultLanguage();
                if (arrstring != null && arrstring.length != 0) {
                    String string2 = arrstring[0];
                    int n = arrstring.length;
                    Object object2 = "";
                    String string3 = n > 1 ? arrstring[1] : "";
                    if (arrstring.length > 2) {
                        object2 = arrstring[2];
                    }
                    if (object.isLanguageAvailable(string2, string3, (String)object2) < 0) {
                        return null;
                    }
                    if (TextUtils.isEmpty(string3 = object.getDefaultVoiceNameFor(string2, string3, (String)object2))) {
                        return null;
                    }
                    if ((object = object.getVoices()) == null) {
                        return null;
                    }
                    object2 = object.iterator();
                    while (object2.hasNext()) {
                        object = (Voice)object2.next();
                        if (!((Voice)object).getName().equals(string3)) continue;
                        return object;
                    }
                    return null;
                }
                Log.e(TextToSpeech.TAG, "service.getClientDefaultLanguage() returned empty array");
                return null;
            }
        }, null, "getDefaultVoice");
    }

    public List<EngineInfo> getEngines() {
        return this.mEnginesHelper.getEngines();
    }

    @Deprecated
    public Set<String> getFeatures(final Locale locale) {
        return this.runAction(new Action<Set<String>>(){

            @Override
            public Set<String> run(ITextToSpeechService object) throws RemoteException {
                block2 : {
                    String[] arrstring;
                    try {
                        arrstring = object.getFeaturesForLanguage(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant());
                        if (arrstring == null) break block2;
                    }
                    catch (MissingResourceException missingResourceException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Couldn't retrieve 3 letter ISO 639-2/T language and/or ISO 3166 country code for locale: ");
                        stringBuilder.append(locale);
                        Log.w(TextToSpeech.TAG, stringBuilder.toString(), missingResourceException);
                        return null;
                    }
                    object = new HashSet();
                    Collections.addAll(object, arrstring);
                    return object;
                }
                return null;
            }
        }, null, "getFeatures");
    }

    @Deprecated
    public Locale getLanguage() {
        return this.runAction(new Action<Locale>(){

            @Override
            public Locale run(ITextToSpeechService iTextToSpeechService) {
                return new Locale(TextToSpeech.this.mParams.getString("language", ""), TextToSpeech.this.mParams.getString("country", ""), TextToSpeech.this.mParams.getString("variant", ""));
            }
        }, null, "getLanguage");
    }

    public Voice getVoice() {
        return this.runAction(new Action<Voice>(){

            @Override
            public Voice run(ITextToSpeechService iTextToSpeechService) throws RemoteException {
                String string2 = TextToSpeech.this.mParams.getString("voiceName", "");
                if (TextUtils.isEmpty(string2)) {
                    return null;
                }
                return TextToSpeech.this.getVoice(iTextToSpeechService, string2);
            }
        }, null, "getVoice");
    }

    public Set<Voice> getVoices() {
        return this.runAction(new Action<Set<Voice>>(){

            @Override
            public Set<Voice> run(ITextToSpeechService object) throws RemoteException {
                HashSet<Voice> hashSet = (object = object.getVoices()) != null ? new HashSet<Voice>((Collection<Voice>)object) : new HashSet<Voice>();
                return hashSet;
            }
        }, null, "getVoices");
    }

    public int isLanguageAvailable(final Locale locale) {
        return this.runAction(new Action<Integer>(){

            @Override
            public Integer run(ITextToSpeechService object) throws RemoteException {
                String string2;
                String string3;
                try {
                    string2 = locale.getISO3Language();
                }
                catch (MissingResourceException missingResourceException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Couldn't retrieve ISO 639-2/T language code for locale: ");
                    ((StringBuilder)object).append(locale);
                    Log.w(TextToSpeech.TAG, ((StringBuilder)object).toString(), missingResourceException);
                    return -2;
                }
                try {
                    string3 = locale.getISO3Country();
                }
                catch (MissingResourceException missingResourceException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Couldn't retrieve ISO 3166 country code for locale: ");
                    ((StringBuilder)object).append(locale);
                    Log.w(TextToSpeech.TAG, ((StringBuilder)object).toString(), missingResourceException);
                    return -2;
                }
                return object.isLanguageAvailable(string2, string3, locale.getVariant());
            }
        }, -2, "isLanguageAvailable");
    }

    public boolean isSpeaking() {
        return this.runAction(new Action<Boolean>(){

            @Override
            public Boolean run(ITextToSpeechService iTextToSpeechService) throws RemoteException {
                return iTextToSpeechService.isSpeaking();
            }
        }, false, "isSpeaking");
    }

    public int playEarcon(final String string2, final int n, final Bundle bundle, final String string3) {
        return this.runAction(new Action<Integer>(){

            @Override
            public Integer run(ITextToSpeechService iTextToSpeechService) throws RemoteException {
                Uri uri = (Uri)TextToSpeech.this.mEarcons.get(string2);
                if (uri == null) {
                    return -1;
                }
                return iTextToSpeechService.playAudio(TextToSpeech.this.getCallerIdentity(), uri, n, TextToSpeech.this.getParams(bundle), string3);
            }
        }, -1, "playEarcon");
    }

    @Deprecated
    public int playEarcon(String string2, int n, HashMap<String, String> object) {
        Bundle bundle = this.convertParamsHashMaptoBundle((HashMap<String, String>)object);
        object = object == null ? null : ((HashMap)object).get("utteranceId");
        return this.playEarcon(string2, n, bundle, (String)object);
    }

    @Deprecated
    public int playSilence(long l, int n, HashMap<String, String> object) {
        object = object == null ? null : ((HashMap)object).get("utteranceId");
        return this.playSilentUtterance(l, n, (String)object);
    }

    public int playSilentUtterance(final long l, final int n, final String string2) {
        return this.runAction(new Action<Integer>(){

            @Override
            public Integer run(ITextToSpeechService iTextToSpeechService) throws RemoteException {
                return iTextToSpeechService.playSilence(TextToSpeech.this.getCallerIdentity(), l, n, string2);
            }
        }, -1, "playSilentUtterance");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int setAudioAttributes(AudioAttributes audioAttributes) {
        if (audioAttributes != null) {
            Object object = this.mStartLock;
            synchronized (object) {
                this.mParams.putParcelable("audioAttributes", audioAttributes);
                return 0;
            }
        }
        return -1;
    }

    @Deprecated
    public int setEngineByPackageName(String string2) {
        this.mRequestedEngine = string2;
        return this.initTts();
    }

    public int setLanguage(final Locale locale) {
        return this.runAction(new Action<Integer>(){

            @Override
            public Integer run(ITextToSpeechService object) throws RemoteException {
                String string2;
                Object object2 = locale;
                Object object3 = -2;
                if (object2 == null) {
                    return object3;
                }
                try {
                    object2 = ((Locale)object2).getISO3Language();
                }
                catch (MissingResourceException missingResourceException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Couldn't retrieve ISO 639-2/T language code for locale: ");
                    ((StringBuilder)object).append(locale);
                    Log.w(TextToSpeech.TAG, ((StringBuilder)object).toString(), missingResourceException);
                    return object3;
                }
                try {
                    string2 = locale.getISO3Country();
                }
                catch (MissingResourceException missingResourceException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Couldn't retrieve ISO 3166 country code for locale: ");
                    ((StringBuilder)object).append(locale);
                    Log.w(TextToSpeech.TAG, ((StringBuilder)object).toString(), missingResourceException);
                    return object3;
                }
                CharSequence charSequence = locale.getVariant();
                int n = object.isLanguageAvailable((String)object2, string2, (String)charSequence);
                if (n >= 0) {
                    String string3 = object.getDefaultVoiceNameFor((String)object2, string2, (String)charSequence);
                    if (TextUtils.isEmpty(string3)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Couldn't find the default voice for ");
                        ((StringBuilder)object).append((String)object2);
                        ((StringBuilder)object).append("-");
                        ((StringBuilder)object).append(string2);
                        ((StringBuilder)object).append("-");
                        ((StringBuilder)object).append((String)charSequence);
                        Log.w(TextToSpeech.TAG, ((StringBuilder)object).toString());
                        return object3;
                    }
                    if (object.loadVoice(TextToSpeech.this.getCallerIdentity(), string3) == -1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("The service claimed ");
                        ((StringBuilder)object).append((String)object2);
                        ((StringBuilder)object).append("-");
                        ((StringBuilder)object).append(string2);
                        ((StringBuilder)object).append("-");
                        ((StringBuilder)object).append((String)charSequence);
                        ((StringBuilder)object).append(" was available with voice name ");
                        ((StringBuilder)object).append(string3);
                        ((StringBuilder)object).append(" but loadVoice returned ERROR");
                        Log.w(TextToSpeech.TAG, ((StringBuilder)object).toString());
                        return object3;
                    }
                    Voice voice = TextToSpeech.this.getVoice((ITextToSpeechService)object, string3);
                    if (voice == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("getDefaultVoiceNameFor returned ");
                        ((StringBuilder)object).append(string3);
                        ((StringBuilder)object).append(" for locale ");
                        ((StringBuilder)object).append((String)object2);
                        ((StringBuilder)object).append("-");
                        ((StringBuilder)object).append(string2);
                        ((StringBuilder)object).append("-");
                        ((StringBuilder)object).append((String)charSequence);
                        ((StringBuilder)object).append(" but getVoice returns null");
                        Log.w(TextToSpeech.TAG, ((StringBuilder)object).toString());
                        return object3;
                    }
                    object = "";
                    try {
                        object = object3 = voice.getLocale().getISO3Language();
                    }
                    catch (MissingResourceException missingResourceException) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Couldn't retrieve ISO 639-2/T language code for locale: ");
                        ((StringBuilder)object2).append(voice.getLocale());
                        Log.w(TextToSpeech.TAG, ((StringBuilder)object2).toString(), missingResourceException);
                    }
                    object3 = "";
                    try {
                        object3 = object2 = voice.getLocale().getISO3Country();
                    }
                    catch (MissingResourceException missingResourceException) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Couldn't retrieve ISO 3166 country code for locale: ");
                        ((StringBuilder)charSequence).append(voice.getLocale());
                        Log.w(TextToSpeech.TAG, ((StringBuilder)charSequence).toString(), missingResourceException);
                    }
                    TextToSpeech.this.mParams.putString("voiceName", string3);
                    TextToSpeech.this.mParams.putString("language", (String)object);
                    TextToSpeech.this.mParams.putString("country", (String)object3);
                    TextToSpeech.this.mParams.putString("variant", voice.getLocale().getVariant());
                }
                return n;
            }
        }, -2, "setLanguage");
    }

    @Deprecated
    public int setOnUtteranceCompletedListener(OnUtteranceCompletedListener onUtteranceCompletedListener) {
        this.mUtteranceProgressListener = UtteranceProgressListener.from(onUtteranceCompletedListener);
        return 0;
    }

    public int setOnUtteranceProgressListener(UtteranceProgressListener utteranceProgressListener) {
        this.mUtteranceProgressListener = utteranceProgressListener;
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int setPitch(float f) {
        int n;
        if (f > 0.0f && (n = (int)(100.0f * f)) > 0) {
            Object object = this.mStartLock;
            synchronized (object) {
                this.mParams.putInt("pitch", n);
                return 0;
            }
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int setSpeechRate(float f) {
        int n;
        if (f > 0.0f && (n = (int)(100.0f * f)) > 0) {
            Object object = this.mStartLock;
            synchronized (object) {
                this.mParams.putInt("rate", n);
                return 0;
            }
        }
        return -1;
    }

    public int setVoice(final Voice voice) {
        return this.runAction(new Action<Integer>(){

            @Override
            public Integer run(ITextToSpeechService object) throws RemoteException {
                int n = object.loadVoice(TextToSpeech.this.getCallerIdentity(), voice.getName());
                if (n == 0) {
                    CharSequence charSequence;
                    TextToSpeech.this.mParams.putString("voiceName", voice.getName());
                    object = "";
                    try {
                        charSequence = voice.getLocale().getISO3Language();
                        object = charSequence;
                    }
                    catch (MissingResourceException missingResourceException) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Couldn't retrieve ISO 639-2/T language code for locale: ");
                        ((StringBuilder)charSequence).append(voice.getLocale());
                        Log.w(TextToSpeech.TAG, ((StringBuilder)charSequence).toString(), missingResourceException);
                    }
                    charSequence = "";
                    try {
                        String string2 = voice.getLocale().getISO3Country();
                        charSequence = string2;
                    }
                    catch (MissingResourceException missingResourceException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Couldn't retrieve ISO 3166 country code for locale: ");
                        stringBuilder.append(voice.getLocale());
                        Log.w(TextToSpeech.TAG, stringBuilder.toString(), missingResourceException);
                    }
                    TextToSpeech.this.mParams.putString("language", (String)object);
                    TextToSpeech.this.mParams.putString("country", (String)charSequence);
                    TextToSpeech.this.mParams.putString("variant", voice.getLocale().getVariant());
                }
                return n;
            }
        }, -2, "setVoice");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void shutdown() {
        Object object = this.mStartLock;
        synchronized (object) {
            if (this.mConnectingServiceConnection != null) {
                this.mContext.unbindService(this.mConnectingServiceConnection);
                this.mConnectingServiceConnection = null;
                return;
            }
        }
        this.runActionNoReconnect(new Action<Void>(){

            @Override
            public Void run(ITextToSpeechService iTextToSpeechService) throws RemoteException {
                iTextToSpeechService.setCallback(TextToSpeech.this.getCallerIdentity(), null);
                iTextToSpeechService.stop(TextToSpeech.this.getCallerIdentity());
                TextToSpeech.this.mServiceConnection.disconnect();
                TextToSpeech.this.mServiceConnection = null;
                TextToSpeech.this.mCurrentEngine = null;
                return null;
            }
        }, null, "shutdown", false);
    }

    public int speak(final CharSequence charSequence, final int n, final Bundle bundle, final String string2) {
        return this.runAction(new Action<Integer>(){

            @Override
            public Integer run(ITextToSpeechService iTextToSpeechService) throws RemoteException {
                Uri uri = (Uri)TextToSpeech.this.mUtterances.get(charSequence);
                if (uri != null) {
                    return iTextToSpeechService.playAudio(TextToSpeech.this.getCallerIdentity(), uri, n, TextToSpeech.this.getParams(bundle), string2);
                }
                return iTextToSpeechService.speak(TextToSpeech.this.getCallerIdentity(), charSequence, n, TextToSpeech.this.getParams(bundle), string2);
            }
        }, -1, "speak");
    }

    @Deprecated
    public int speak(String string2, int n, HashMap<String, String> object) {
        Bundle bundle = this.convertParamsHashMaptoBundle((HashMap<String, String>)object);
        object = object == null ? null : ((HashMap)object).get("utteranceId");
        return this.speak(string2, n, bundle, (String)object);
    }

    public int stop() {
        return this.runAction(new Action<Integer>(){

            @Override
            public Integer run(ITextToSpeechService iTextToSpeechService) throws RemoteException {
                return iTextToSpeechService.stop(TextToSpeech.this.getCallerIdentity());
            }
        }, -1, "stop");
    }

    public int synthesizeToFile(final CharSequence charSequence, final Bundle bundle, final File file, final String string2) {
        return this.runAction(new Action<Integer>(){

            @Override
            public Integer run(ITextToSpeechService object) throws RemoteException {
                int n;
                try {
                    if (file.exists() && !file.canWrite()) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Can't write to ");
                        ((StringBuilder)object).append(file);
                        Log.e(TextToSpeech.TAG, ((StringBuilder)object).toString());
                        return -1;
                    }
                    ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(file, 738197504);
                    n = object.synthesizeToFileDescriptor(TextToSpeech.this.getCallerIdentity(), charSequence, parcelFileDescriptor, TextToSpeech.this.getParams(bundle), string2);
                    parcelFileDescriptor.close();
                }
                catch (IOException iOException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Closing file ");
                    ((StringBuilder)object).append(file);
                    ((StringBuilder)object).append(" failed");
                    Log.e(TextToSpeech.TAG, ((StringBuilder)object).toString(), iOException);
                    return -1;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Opening file ");
                    ((StringBuilder)object).append(file);
                    ((StringBuilder)object).append(" failed");
                    Log.e(TextToSpeech.TAG, ((StringBuilder)object).toString(), fileNotFoundException);
                    return -1;
                }
                return n;
            }
        }, -1, "synthesizeToFile");
    }

    @Deprecated
    public int synthesizeToFile(String string2, HashMap<String, String> hashMap, String string3) {
        return this.synthesizeToFile(string2, this.convertParamsHashMaptoBundle(hashMap), new File(string3), hashMap.get("utteranceId"));
    }

    private static interface Action<R> {
        public R run(ITextToSpeechService var1) throws RemoteException;
    }

    private class Connection
    implements ServiceConnection {
        private final ITextToSpeechCallback.Stub mCallback = new ITextToSpeechCallback.Stub(){

            @Override
            public void onAudioAvailable(String string2, byte[] arrby) {
                UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                if (utteranceProgressListener != null) {
                    utteranceProgressListener.onAudioAvailable(string2, arrby);
                }
            }

            @Override
            public void onBeginSynthesis(String string2, int n, int n2, int n3) {
                UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                if (utteranceProgressListener != null) {
                    utteranceProgressListener.onBeginSynthesis(string2, n, n2, n3);
                }
            }

            @Override
            public void onError(String string2, int n) {
                UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                if (utteranceProgressListener != null) {
                    utteranceProgressListener.onError(string2);
                }
            }

            @Override
            public void onRangeStart(String string2, int n, int n2, int n3) {
                UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                if (utteranceProgressListener != null) {
                    utteranceProgressListener.onRangeStart(string2, n, n2, n3);
                }
            }

            @Override
            public void onStart(String string2) {
                UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                if (utteranceProgressListener != null) {
                    utteranceProgressListener.onStart(string2);
                }
            }

            @Override
            public void onStop(String string2, boolean bl) throws RemoteException {
                UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                if (utteranceProgressListener != null) {
                    utteranceProgressListener.onStop(string2, bl);
                }
            }

            @Override
            public void onSuccess(String string2) {
                UtteranceProgressListener utteranceProgressListener = TextToSpeech.this.mUtteranceProgressListener;
                if (utteranceProgressListener != null) {
                    utteranceProgressListener.onDone(string2);
                }
            }
        };
        private boolean mEstablished;
        private SetupConnectionAsyncTask mOnSetupConnectionAsyncTask;
        private ITextToSpeechService mService;

        private Connection() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private boolean clearServiceConnection() {
            Object object = TextToSpeech.this.mStartLock;
            synchronized (object) {
                boolean bl = false;
                if (this.mOnSetupConnectionAsyncTask != null) {
                    bl = this.mOnSetupConnectionAsyncTask.cancel(false);
                    this.mOnSetupConnectionAsyncTask = null;
                }
                this.mService = null;
                if (TextToSpeech.this.mServiceConnection == this) {
                    TextToSpeech.this.mServiceConnection = null;
                }
                return bl;
            }
        }

        public void disconnect() {
            TextToSpeech.this.mContext.unbindService(this);
            this.clearServiceConnection();
        }

        public IBinder getCallerIdentity() {
            return this.mCallback;
        }

        public boolean isEstablished() {
            boolean bl = this.mService != null && this.mEstablished;
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder object) {
            Object object2 = TextToSpeech.this.mStartLock;
            synchronized (object2) {
                TextToSpeech.this.mConnectingServiceConnection = null;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Connected to ");
                stringBuilder.append(componentName);
                Log.i(TextToSpeech.TAG, stringBuilder.toString());
                if (this.mOnSetupConnectionAsyncTask != null) {
                    this.mOnSetupConnectionAsyncTask.cancel(false);
                }
                this.mService = ITextToSpeechService.Stub.asInterface((IBinder)object);
                TextToSpeech.this.mServiceConnection = this;
                this.mEstablished = false;
                this.mOnSetupConnectionAsyncTask = object = new SetupConnectionAsyncTask(componentName);
                this.mOnSetupConnectionAsyncTask.execute(new Void[0]);
                return;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Asked to disconnect from ");
            stringBuilder.append(componentName);
            Log.i(TextToSpeech.TAG, stringBuilder.toString());
            if (this.clearServiceConnection()) {
                TextToSpeech.this.dispatchOnInit(-1);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public <R> R runAction(Action<R> object, R r, String string2, boolean bl, boolean bl2) {
            Object object2 = TextToSpeech.this.mStartLock;
            synchronized (object2) {
                try {
                    try {
                        if (this.mService == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append(string2);
                            ((StringBuilder)object).append(" failed: not connected to TTS engine");
                            Log.w(TextToSpeech.TAG, ((StringBuilder)object).toString());
                            return r;
                        }
                        if (bl2 && !this.isEstablished()) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append(string2);
                            ((StringBuilder)object).append(" failed: TTS engine connection not fully set up");
                            Log.w(TextToSpeech.TAG, ((StringBuilder)object).toString());
                            return r;
                        }
                        object = object.run(this.mService);
                    }
                    catch (RemoteException remoteException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(string2);
                        stringBuilder.append(" failed");
                        Log.e(TextToSpeech.TAG, stringBuilder.toString(), remoteException);
                        if (bl) {
                            this.disconnect();
                            TextToSpeech.this.initTts();
                        }
                        return r;
                    }
                    return (R)object;
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        private class SetupConnectionAsyncTask
        extends AsyncTask<Void, Void, Integer> {
            private final ComponentName mName;

            public SetupConnectionAsyncTask(ComponentName componentName) {
                this.mName = componentName;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            protected Integer doInBackground(Void ... object) {
                object = TextToSpeech.this.mStartLock;
                synchronized (object) {
                    if (this.isCancelled()) {
                        return null;
                    }
                    try {
                        Object object2;
                        Connection.this.mService.setCallback(Connection.this.getCallerIdentity(), Connection.this.mCallback);
                        if (TextToSpeech.this.mParams.getString("language") == null) {
                            object2 = Connection.this.mService.getClientDefaultLanguage();
                            TextToSpeech.this.mParams.putString("language", object2[0]);
                            TextToSpeech.this.mParams.putString("country", object2[1]);
                            TextToSpeech.this.mParams.putString("variant", object2[2]);
                            object2 = Connection.this.mService.getDefaultVoiceNameFor(object2[0], object2[1], object2[2]);
                            TextToSpeech.this.mParams.putString("voiceName", (String)object2);
                        }
                        object2 = new StringBuilder();
                        object2.append("Set up connection to ");
                        object2.append(this.mName);
                        Log.i(TextToSpeech.TAG, object2.toString());
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TextToSpeech.TAG, "Error connecting to service, setCallback() failed");
                        return -1;
                    }
                    return 0;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void onPostExecute(Integer n) {
                Object object = TextToSpeech.this.mStartLock;
                synchronized (object) {
                    if (Connection.this.mOnSetupConnectionAsyncTask == this) {
                        Connection.this.mOnSetupConnectionAsyncTask = null;
                    }
                    Connection.this.mEstablished = true;
                    TextToSpeech.this.dispatchOnInit(n);
                    return;
                }
            }
        }

    }

    public class Engine {
        public static final String ACTION_CHECK_TTS_DATA = "android.speech.tts.engine.CHECK_TTS_DATA";
        public static final String ACTION_GET_SAMPLE_TEXT = "android.speech.tts.engine.GET_SAMPLE_TEXT";
        public static final String ACTION_INSTALL_TTS_DATA = "android.speech.tts.engine.INSTALL_TTS_DATA";
        public static final String ACTION_TTS_DATA_INSTALLED = "android.speech.tts.engine.TTS_DATA_INSTALLED";
        @Deprecated
        public static final int CHECK_VOICE_DATA_BAD_DATA = -1;
        public static final int CHECK_VOICE_DATA_FAIL = 0;
        @Deprecated
        public static final int CHECK_VOICE_DATA_MISSING_DATA = -2;
        @Deprecated
        public static final int CHECK_VOICE_DATA_MISSING_VOLUME = -3;
        public static final int CHECK_VOICE_DATA_PASS = 1;
        @Deprecated
        public static final String DEFAULT_ENGINE = "com.svox.pico";
        public static final float DEFAULT_PAN = 0.0f;
        public static final int DEFAULT_PITCH = 100;
        public static final int DEFAULT_RATE = 100;
        public static final int DEFAULT_STREAM = 3;
        public static final float DEFAULT_VOLUME = 1.0f;
        public static final String EXTRA_AVAILABLE_VOICES = "availableVoices";
        @Deprecated
        public static final String EXTRA_CHECK_VOICE_DATA_FOR = "checkVoiceDataFor";
        public static final String EXTRA_SAMPLE_TEXT = "sampleText";
        @Deprecated
        public static final String EXTRA_TTS_DATA_INSTALLED = "dataInstalled";
        public static final String EXTRA_UNAVAILABLE_VOICES = "unavailableVoices";
        @Deprecated
        public static final String EXTRA_VOICE_DATA_FILES = "dataFiles";
        @Deprecated
        public static final String EXTRA_VOICE_DATA_FILES_INFO = "dataFilesInfo";
        @Deprecated
        public static final String EXTRA_VOICE_DATA_ROOT_DIRECTORY = "dataRoot";
        public static final String INTENT_ACTION_TTS_SERVICE = "android.intent.action.TTS_SERVICE";
        @Deprecated
        public static final String KEY_FEATURE_EMBEDDED_SYNTHESIS = "embeddedTts";
        public static final String KEY_FEATURE_NETWORK_RETRIES_COUNT = "networkRetriesCount";
        @Deprecated
        public static final String KEY_FEATURE_NETWORK_SYNTHESIS = "networkTts";
        public static final String KEY_FEATURE_NETWORK_TIMEOUT_MS = "networkTimeoutMs";
        public static final String KEY_FEATURE_NOT_INSTALLED = "notInstalled";
        public static final String KEY_PARAM_AUDIO_ATTRIBUTES = "audioAttributes";
        public static final String KEY_PARAM_COUNTRY = "country";
        public static final String KEY_PARAM_ENGINE = "engine";
        public static final String KEY_PARAM_LANGUAGE = "language";
        public static final String KEY_PARAM_PAN = "pan";
        public static final String KEY_PARAM_PITCH = "pitch";
        public static final String KEY_PARAM_RATE = "rate";
        public static final String KEY_PARAM_SESSION_ID = "sessionId";
        public static final String KEY_PARAM_STREAM = "streamType";
        public static final String KEY_PARAM_UTTERANCE_ID = "utteranceId";
        public static final String KEY_PARAM_VARIANT = "variant";
        public static final String KEY_PARAM_VOICE_NAME = "voiceName";
        public static final String KEY_PARAM_VOLUME = "volume";
        public static final String SERVICE_META_DATA = "android.speech.tts";
        public static final int USE_DEFAULTS = 0;
    }

    public static class EngineInfo {
        public int icon;
        public String label;
        public String name;
        public int priority;
        public boolean system;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("EngineInfo{name=");
            stringBuilder.append(this.name);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Error {
    }

    public static interface OnInitListener {
        public void onInit(int var1);
    }

    @Deprecated
    public static interface OnUtteranceCompletedListener {
        public void onUtteranceCompleted(String var1);
    }

}

