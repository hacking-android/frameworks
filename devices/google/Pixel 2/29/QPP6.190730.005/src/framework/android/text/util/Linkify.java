/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.text.util.-$
 *  android.text.util.-$$Lambda
 *  android.text.util.-$$Lambda$Linkify
 *  android.text.util.-$$Lambda$Linkify$7J_-cMhIF2bcttjkxA2jDFP8sKw
 *  com.android.i18n.phonenumbers.PhoneNumberUtil
 *  com.android.i18n.phonenumbers.PhoneNumberUtil$Leniency
 *  libcore.util.EmptyArray
 */
package android.text.util;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.text.util.-$;
import android.text.util.LinkSpec;
import android.text.util._$$Lambda$Linkify$7J__cMhIF2bcttjkxA2jDFP8sKw;
import android.util.EventLog;
import android.util.Log;
import android.util.Patterns;
import android.webkit.WebView;
import android.widget.TextView;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.util.EmptyArray;

public class Linkify {
    public static final int ALL = 15;
    private static final Function<String, URLSpan> DEFAULT_SPAN_FACTORY;
    public static final int EMAIL_ADDRESSES = 2;
    private static final String LOG_TAG = "Linkify";
    @Deprecated
    public static final int MAP_ADDRESSES = 8;
    public static final int PHONE_NUMBERS = 4;
    private static final int PHONE_NUMBER_MINIMUM_DIGITS = 5;
    public static final int WEB_URLS = 1;
    public static final MatchFilter sPhoneNumberMatchFilter;
    public static final TransformFilter sPhoneNumberTransformFilter;
    public static final MatchFilter sUrlMatchFilter;

    static {
        sUrlMatchFilter = new MatchFilter(){

            @Override
            public final boolean acceptMatch(CharSequence charSequence, int n, int n2) {
                if (n == 0) {
                    return true;
                }
                return charSequence.charAt(n - 1) != '@';
            }
        };
        sPhoneNumberMatchFilter = new MatchFilter(){

            @Override
            public final boolean acceptMatch(CharSequence charSequence, int n, int n2) {
                int n3 = 0;
                while (n < n2) {
                    int n4 = n3++;
                    if (Character.isDigit(charSequence.charAt(n))) {
                        n4 = n3;
                        if (n3 >= 5) {
                            return true;
                        }
                    }
                    ++n;
                    n3 = n4;
                }
                return false;
            }
        };
        sPhoneNumberTransformFilter = new TransformFilter(){

            @Override
            public final String transformUrl(Matcher matcher, String string2) {
                return Patterns.digitsAndPlusOnly(matcher);
            }
        };
        DEFAULT_SPAN_FACTORY = _$$Lambda$Linkify$7J__cMhIF2bcttjkxA2jDFP8sKw.INSTANCE;
    }

    private static final void addLinkMovementMethod(TextView textView) {
        MovementMethod movementMethod = textView.getMovementMethod();
        if ((movementMethod == null || !(movementMethod instanceof LinkMovementMethod)) && textView.getLinksClickable()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static final void addLinks(TextView textView, Pattern pattern, String string2) {
        Linkify.addLinks(textView, pattern, string2, null, null, null);
    }

    public static final void addLinks(TextView textView, Pattern pattern, String string2, MatchFilter matchFilter, TransformFilter transformFilter) {
        Linkify.addLinks(textView, pattern, string2, null, matchFilter, transformFilter);
    }

    public static final void addLinks(TextView textView, Pattern pattern, String string2, String[] arrstring, MatchFilter matchFilter, TransformFilter transformFilter) {
        SpannableString spannableString = SpannableString.valueOf(textView.getText());
        if (Linkify.addLinks(spannableString, pattern, string2, arrstring, matchFilter, transformFilter)) {
            textView.setText(spannableString);
            Linkify.addLinkMovementMethod(textView);
        }
    }

    public static final boolean addLinks(Spannable spannable, int n) {
        return Linkify.addLinks(spannable, n, null, null);
    }

    private static boolean addLinks(Spannable spannable, int n, Context object, Function<String, URLSpan> function) {
        if (spannable != null && Linkify.containsUnsupportedCharacters(spannable.toString())) {
            EventLog.writeEvent(1397638484, "116321860", -1, "");
            return false;
        }
        if (n == 0) {
            return false;
        }
        URLSpan[] object22 = spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int i = object22.length - 1; i >= 0; --i) {
            spannable.removeSpan(object22[i]);
        }
        ArrayList<LinkSpec> arrayList = new ArrayList<LinkSpec>();
        if ((n & 1) != 0) {
            Pattern pattern = Patterns.AUTOLINK_WEB_URL;
            MatchFilter matchFilter = sUrlMatchFilter;
            Linkify.gatherLinks(arrayList, spannable, pattern, new String[]{"http://", "https://", "rtsp://"}, matchFilter, null);
        }
        if ((n & 2) != 0) {
            Linkify.gatherLinks(arrayList, spannable, Patterns.AUTOLINK_EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        }
        if ((n & 4) != 0) {
            Linkify.gatherTelLinks(arrayList, spannable, (Context)((Object)object));
        }
        if ((n & 8) != 0) {
            Linkify.gatherMapLinks(arrayList, spannable);
        }
        Linkify.pruneOverlaps(arrayList);
        if (arrayList.size() == 0) {
            return false;
        }
        for (LinkSpec linkSpec : arrayList) {
            Linkify.applyLink(linkSpec.url, linkSpec.start, linkSpec.end, spannable, function);
        }
        return true;
    }

    public static final boolean addLinks(Spannable spannable, int n, Function<String, URLSpan> function) {
        return Linkify.addLinks(spannable, n, null, function);
    }

    public static final boolean addLinks(Spannable spannable, Pattern pattern, String string2) {
        return Linkify.addLinks(spannable, pattern, string2, null, null, null);
    }

    public static final boolean addLinks(Spannable spannable, Pattern pattern, String string2, MatchFilter matchFilter, TransformFilter transformFilter) {
        return Linkify.addLinks(spannable, pattern, string2, null, matchFilter, transformFilter);
    }

    public static final boolean addLinks(Spannable spannable, Pattern pattern, String string2, String[] arrstring, MatchFilter matchFilter, TransformFilter transformFilter) {
        return Linkify.addLinks(spannable, pattern, string2, arrstring, matchFilter, transformFilter, null);
    }

    public static final boolean addLinks(Spannable spannable, Pattern object, String arrstring, String[] object2, MatchFilter matchFilter, TransformFilter transformFilter, Function<String, URLSpan> function) {
        int n;
        String[] arrstring2;
        block9 : {
            block8 : {
                if (spannable != null && Linkify.containsUnsupportedCharacters(spannable.toString())) {
                    EventLog.writeEvent(1397638484, "116321860", -1, "");
                    return false;
                }
                arrstring2 = arrstring;
                if (arrstring == null) {
                    arrstring2 = "";
                }
                if (object2 == null) break block8;
                arrstring = object2;
                if (((String[])object2).length >= 1) break block9;
            }
            arrstring = EmptyArray.STRING;
        }
        String[] arrstring3 = new String[arrstring.length + 1];
        arrstring3[0] = arrstring2.toLowerCase(Locale.ROOT);
        for (n = 0; n < arrstring.length; ++n) {
            object2 = arrstring[n];
            object2 = object2 == null ? "" : object2.toLowerCase(Locale.ROOT);
            arrstring3[n + 1] = object2;
        }
        boolean bl = false;
        object = ((Pattern)object).matcher(spannable);
        while (((Matcher)object).find()) {
            n = ((Matcher)object).start();
            int n2 = ((Matcher)object).end();
            boolean bl2 = true;
            if (matchFilter != null) {
                bl2 = matchFilter.acceptMatch(spannable, n, n2);
            }
            if (!bl2) continue;
            Linkify.applyLink(Linkify.makeUrl(((Matcher)object).group(0), arrstring3, (Matcher)object, transformFilter), n, n2, spannable, function);
            bl = true;
        }
        return bl;
    }

    public static final boolean addLinks(TextView textView, int n) {
        if (n == 0) {
            return false;
        }
        Context context = textView.getContext();
        CharSequence charSequence = textView.getText();
        if (charSequence instanceof Spannable) {
            if (Linkify.addLinks((Spannable)charSequence, n, context, null)) {
                Linkify.addLinkMovementMethod(textView);
                return true;
            }
            return false;
        }
        if (Linkify.addLinks((Spannable)(charSequence = SpannableString.valueOf(charSequence)), n, context, null)) {
            Linkify.addLinkMovementMethod(textView);
            textView.setText(charSequence);
            return true;
        }
        return false;
    }

    private static void applyLink(String string2, int n, int n2, Spannable spannable, Function<String, URLSpan> function) {
        Function<String, URLSpan> function2 = function;
        if (function == null) {
            function2 = DEFAULT_SPAN_FACTORY;
        }
        spannable.setSpan(function2.apply(string2), n, n2, 33);
    }

    public static boolean containsUnsupportedCharacters(String string2) {
        if (string2.contains("\u202c")) {
            Log.e(LOG_TAG, "Unsupported character for applying links: u202C");
            return true;
        }
        if (string2.contains("\u202d")) {
            Log.e(LOG_TAG, "Unsupported character for applying links: u202D");
            return true;
        }
        if (string2.contains("\u202e")) {
            Log.e(LOG_TAG, "Unsupported character for applying links: u202E");
            return true;
        }
        return false;
    }

    private static final void gatherLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern object, String[] arrstring, MatchFilter matchFilter, TransformFilter transformFilter) {
        object = ((Pattern)object).matcher(spannable);
        while (((Matcher)object).find()) {
            int n = ((Matcher)object).start();
            int n2 = ((Matcher)object).end();
            if (matchFilter != null && !matchFilter.acceptMatch(spannable, n, n2)) continue;
            LinkSpec linkSpec = new LinkSpec();
            linkSpec.url = Linkify.makeUrl(((Matcher)object).group(0), arrstring, (Matcher)object, transformFilter);
            linkSpec.start = n;
            linkSpec.end = n2;
            arrayList.add(linkSpec);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static final void gatherMapLinks(ArrayList<LinkSpec> arrayList, Spannable charSequence) {
        charSequence = ((Object)charSequence).toString();
        int n = 0;
        try {
            CharSequence charSequence2;
            while ((charSequence2 = WebView.findAddress((String)charSequence)) != null) {
                String string2;
                int n2 = ((String)charSequence).indexOf((String)charSequence2);
                if (n2 < 0) {
                    return;
                }
                LinkSpec linkSpec = new LinkSpec();
                int n3 = n2 + ((String)charSequence2).length();
                linkSpec.start = n + n2;
                linkSpec.end = n + n3;
                charSequence = ((String)charSequence).substring(n3);
                n += n3;
                try {
                    string2 = URLEncoder.encode((String)charSequence2, "UTF-8");
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("geo:0,0?q=");
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    continue;
                }
                ((StringBuilder)charSequence2).append(string2);
                linkSpec.url = ((StringBuilder)charSequence2).toString();
                arrayList.add(linkSpec);
            }
            return;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            return;
        }
    }

    @UnsupportedAppUsage
    private static void gatherTelLinks(ArrayList<LinkSpec> arrayList, Spannable charSequence, Context object) {
        PhoneNumberUtil phoneNumberUtil2 = PhoneNumberUtil.getInstance();
        object = object == null ? TelephonyManager.getDefault() : TelephonyManager.from((Context)object);
        for (PhoneNumberUtil phoneNumberUtil2 : phoneNumberUtil2.findNumbers((CharSequence)((Object)charSequence).toString(), ((TelephonyManager)object).getSimCountryIso().toUpperCase(Locale.US), PhoneNumberUtil.Leniency.POSSIBLE, Long.MAX_VALUE)) {
            LinkSpec linkSpec = new LinkSpec();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("tel:");
            ((StringBuilder)charSequence).append(PhoneNumberUtils.normalizeNumber(phoneNumberUtil2.rawString()));
            linkSpec.url = ((StringBuilder)charSequence).toString();
            linkSpec.start = phoneNumberUtil2.start();
            linkSpec.end = phoneNumberUtil2.end();
            arrayList.add(linkSpec);
        }
    }

    static /* synthetic */ URLSpan lambda$static$0(String string2) {
        return new URLSpan(string2);
    }

    private static final String makeUrl(String charSequence, String[] arrstring, Matcher object, TransformFilter transformFilter) {
        boolean bl;
        String string2 = charSequence;
        if (transformFilter != null) {
            string2 = transformFilter.transformUrl((Matcher)object, (String)charSequence);
        }
        boolean bl2 = false;
        int n = 0;
        do {
            bl = bl2;
            charSequence = string2;
            if (n >= arrstring.length) break;
            if (string2.regionMatches(true, 0, arrstring[n], 0, arrstring[n].length())) {
                bl = bl2 = true;
                charSequence = string2;
                if (string2.regionMatches(false, 0, arrstring[n], 0, arrstring[n].length())) break;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(arrstring[n]);
                ((StringBuilder)charSequence).append(string2.substring(arrstring[n].length()));
                charSequence = ((StringBuilder)charSequence).toString();
                bl = bl2;
                break;
            }
            ++n;
        } while (true);
        object = charSequence;
        if (!bl) {
            object = charSequence;
            if (arrstring.length > 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append(arrstring[0]);
                ((StringBuilder)object).append((String)charSequence);
                object = ((StringBuilder)object).toString();
            }
        }
        return object;
    }

    private static final void pruneOverlaps(ArrayList<LinkSpec> arrayList) {
        Collections.sort(arrayList, new Comparator<LinkSpec>(){

            @Override
            public final int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
                if (linkSpec.start < linkSpec2.start) {
                    return -1;
                }
                if (linkSpec.start > linkSpec2.start) {
                    return 1;
                }
                if (linkSpec.end < linkSpec2.end) {
                    return 1;
                }
                if (linkSpec.end > linkSpec2.end) {
                    return -1;
                }
                return 0;
            }
        });
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n - 1) {
            LinkSpec linkSpec = arrayList.get(n2);
            LinkSpec linkSpec2 = arrayList.get(n2 + 1);
            int n3 = -1;
            if (linkSpec.start <= linkSpec2.start && linkSpec.end > linkSpec2.start) {
                if (linkSpec2.end <= linkSpec.end) {
                    n3 = n2 + 1;
                } else if (linkSpec.end - linkSpec.start > linkSpec2.end - linkSpec2.start) {
                    n3 = n2 + 1;
                } else if (linkSpec.end - linkSpec.start < linkSpec2.end - linkSpec2.start) {
                    n3 = n2;
                }
                if (n3 != -1) {
                    arrayList.remove(n3);
                    --n;
                    continue;
                }
            }
            ++n2;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LinkifyMask {
    }

    public static interface MatchFilter {
        public boolean acceptMatch(CharSequence var1, int var2, int var3);
    }

    public static interface TransformFilter {
        public String transformUrl(Matcher var1, String var2);
    }

}

