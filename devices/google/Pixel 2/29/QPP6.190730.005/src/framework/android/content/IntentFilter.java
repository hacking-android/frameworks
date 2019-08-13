/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.content;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.AndroidException;
import android.util.Log;
import android.util.Printer;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class IntentFilter
implements Parcelable {
    private static final String ACTION_STR = "action";
    private static final String AGLOB_STR = "aglob";
    private static final String AUTH_STR = "auth";
    private static final String AUTO_VERIFY_STR = "autoVerify";
    private static final String CAT_STR = "cat";
    public static final Parcelable.Creator<IntentFilter> CREATOR = new Parcelable.Creator<IntentFilter>(){

        @Override
        public IntentFilter createFromParcel(Parcel parcel) {
            return new IntentFilter(parcel);
        }

        public IntentFilter[] newArray(int n) {
            return new IntentFilter[n];
        }
    };
    private static final String HOST_STR = "host";
    private static final String LITERAL_STR = "literal";
    public static final int MATCH_ADJUSTMENT_MASK = 65535;
    public static final int MATCH_ADJUSTMENT_NORMAL = 32768;
    public static final int MATCH_CATEGORY_EMPTY = 1048576;
    public static final int MATCH_CATEGORY_HOST = 3145728;
    public static final int MATCH_CATEGORY_MASK = 268369920;
    public static final int MATCH_CATEGORY_PATH = 5242880;
    public static final int MATCH_CATEGORY_PORT = 4194304;
    public static final int MATCH_CATEGORY_SCHEME = 2097152;
    public static final int MATCH_CATEGORY_SCHEME_SPECIFIC_PART = 5767168;
    public static final int MATCH_CATEGORY_TYPE = 6291456;
    private static final String NAME_STR = "name";
    public static final int NO_MATCH_ACTION = -3;
    public static final int NO_MATCH_CATEGORY = -4;
    public static final int NO_MATCH_DATA = -2;
    public static final int NO_MATCH_TYPE = -1;
    private static final String PATH_STR = "path";
    private static final String PORT_STR = "port";
    private static final String PREFIX_STR = "prefix";
    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";
    private static final String SCHEME_STR = "scheme";
    private static final String SGLOB_STR = "sglob";
    private static final String SSP_STR = "ssp";
    private static final int STATE_NEED_VERIFY = 16;
    private static final int STATE_NEED_VERIFY_CHECKED = 256;
    private static final int STATE_VERIFIED = 4096;
    private static final int STATE_VERIFY_AUTO = 1;
    public static final int SYSTEM_HIGH_PRIORITY = 1000;
    public static final int SYSTEM_LOW_PRIORITY = -1000;
    private static final String TYPE_STR = "type";
    public static final int VISIBILITY_EXPLICIT = 1;
    public static final int VISIBILITY_IMPLICIT = 2;
    public static final int VISIBILITY_NONE = 0;
    @UnsupportedAppUsage
    private final ArrayList<String> mActions;
    private ArrayList<String> mCategories = null;
    private ArrayList<AuthorityEntry> mDataAuthorities = null;
    private ArrayList<PatternMatcher> mDataPaths = null;
    private ArrayList<PatternMatcher> mDataSchemeSpecificParts = null;
    private ArrayList<String> mDataSchemes = null;
    private ArrayList<String> mDataTypes = null;
    private boolean mHasPartialTypes;
    private int mInstantAppVisibility;
    @UnsupportedAppUsage
    private int mOrder;
    private int mPriority;
    private int mVerifyState;

    public IntentFilter() {
        this.mHasPartialTypes = false;
        this.mPriority = 0;
        this.mActions = new ArrayList();
    }

    public IntentFilter(IntentFilter intentFilter) {
        this.mHasPartialTypes = false;
        this.mPriority = intentFilter.mPriority;
        this.mOrder = intentFilter.mOrder;
        this.mActions = new ArrayList<String>(intentFilter.mActions);
        ArrayList<Object> arrayList = intentFilter.mCategories;
        if (arrayList != null) {
            this.mCategories = new ArrayList<String>(arrayList);
        }
        if ((arrayList = intentFilter.mDataTypes) != null) {
            this.mDataTypes = new ArrayList<String>(arrayList);
        }
        if ((arrayList = intentFilter.mDataSchemes) != null) {
            this.mDataSchemes = new ArrayList<String>(arrayList);
        }
        if ((arrayList = intentFilter.mDataSchemeSpecificParts) != null) {
            this.mDataSchemeSpecificParts = new ArrayList<String>(arrayList);
        }
        if ((arrayList = intentFilter.mDataAuthorities) != null) {
            this.mDataAuthorities = new ArrayList<String>(arrayList);
        }
        if ((arrayList = intentFilter.mDataPaths) != null) {
            this.mDataPaths = new ArrayList<String>(arrayList);
        }
        this.mHasPartialTypes = intentFilter.mHasPartialTypes;
        this.mVerifyState = intentFilter.mVerifyState;
        this.mInstantAppVisibility = intentFilter.mInstantAppVisibility;
    }

    public IntentFilter(Parcel parcel) {
        int n;
        int n2;
        boolean bl = false;
        this.mHasPartialTypes = false;
        this.mActions = new ArrayList();
        parcel.readStringList(this.mActions);
        if (parcel.readInt() != 0) {
            this.mCategories = new ArrayList();
            parcel.readStringList(this.mCategories);
        }
        if (parcel.readInt() != 0) {
            this.mDataSchemes = new ArrayList();
            parcel.readStringList(this.mDataSchemes);
        }
        if (parcel.readInt() != 0) {
            this.mDataTypes = new ArrayList();
            parcel.readStringList(this.mDataTypes);
        }
        if ((n = parcel.readInt()) > 0) {
            this.mDataSchemeSpecificParts = new ArrayList(n);
            for (n2 = 0; n2 < n; ++n2) {
                this.mDataSchemeSpecificParts.add(new PatternMatcher(parcel));
            }
        }
        if ((n = parcel.readInt()) > 0) {
            this.mDataAuthorities = new ArrayList(n);
            for (n2 = 0; n2 < n; ++n2) {
                this.mDataAuthorities.add(new AuthorityEntry(parcel));
            }
        }
        if ((n = parcel.readInt()) > 0) {
            this.mDataPaths = new ArrayList(n);
            for (n2 = 0; n2 < n; ++n2) {
                this.mDataPaths.add(new PatternMatcher(parcel));
            }
        }
        this.mPriority = parcel.readInt();
        boolean bl2 = parcel.readInt() > 0;
        this.mHasPartialTypes = bl2;
        bl2 = bl;
        if (parcel.readInt() > 0) {
            bl2 = true;
        }
        this.setAutoVerify(bl2);
        this.setVisibilityToInstantApp(parcel.readInt());
        this.mOrder = parcel.readInt();
    }

    public IntentFilter(String string2) {
        this.mHasPartialTypes = false;
        this.mPriority = 0;
        this.mActions = new ArrayList();
        this.addAction(string2);
    }

    public IntentFilter(String string2, String string3) throws MalformedMimeTypeException {
        this.mHasPartialTypes = false;
        this.mPriority = 0;
        this.mActions = new ArrayList();
        this.addAction(string2);
        this.addDataType(string3);
    }

    private static String[] addStringToSet(String[] arrstring, String string2, int[] arrn, int n) {
        if (IntentFilter.findStringInSet(arrstring, string2, arrn, n) >= 0) {
            return arrstring;
        }
        if (arrstring == null) {
            arrstring = new String[2];
            arrstring[0] = string2;
            arrn[n] = 1;
            return arrstring;
        }
        int n2 = arrn[n];
        if (n2 < arrstring.length) {
            arrstring[n2] = string2;
            arrn[n] = n2 + 1;
            return arrstring;
        }
        String[] arrstring2 = new String[n2 * 3 / 2 + 2];
        System.arraycopy(arrstring, 0, arrstring2, 0, n2);
        arrstring2[n2] = string2;
        arrn[n] = n2 + 1;
        return arrstring2;
    }

    public static IntentFilter create(String object, String string2) {
        try {
            object = new IntentFilter((String)object, string2);
            return object;
        }
        catch (MalformedMimeTypeException malformedMimeTypeException) {
            throw new RuntimeException("Bad MIME type", malformedMimeTypeException);
        }
    }

    private final boolean findMimeType(String string2) {
        ArrayList<String> arrayList = this.mDataTypes;
        if (string2 == null) {
            return false;
        }
        if (arrayList.contains(string2)) {
            return true;
        }
        int n = string2.length();
        if (n == 3 && string2.equals("*/*")) {
            return arrayList.isEmpty() ^ true;
        }
        if (this.mHasPartialTypes && arrayList.contains("*")) {
            return true;
        }
        int n2 = string2.indexOf(47);
        if (n2 > 0) {
            if (this.mHasPartialTypes && arrayList.contains(string2.substring(0, n2))) {
                return true;
            }
            if (n == n2 + 2 && string2.charAt(n2 + 1) == '*') {
                int n3 = arrayList.size();
                for (n = 0; n < n3; ++n) {
                    if (!string2.regionMatches(0, arrayList.get(n), 0, n2 + 1)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    private static int findStringInSet(String[] arrstring, String string2, int[] arrn, int n) {
        if (arrstring == null) {
            return -1;
        }
        int n2 = arrn[n];
        for (n = 0; n < n2; ++n) {
            if (!arrstring[n].equals(string2)) continue;
            return n;
        }
        return -1;
    }

    private static String[] removeStringFromSet(String[] arrstring, String arrstring2, int[] arrn, int n) {
        int n2 = IntentFilter.findStringInSet(arrstring, (String)arrstring2, arrn, n);
        if (n2 < 0) {
            return arrstring;
        }
        int n3 = arrn[n];
        if (n3 > arrstring.length / 4) {
            int n4 = n3 - (n2 + 1);
            if (n4 > 0) {
                System.arraycopy(arrstring, n2 + 1, arrstring, n2, n4);
            }
            arrstring[n3 - 1] = null;
            arrn[n] = n3 - 1;
            return arrstring;
        }
        arrstring2 = new String[arrstring.length / 3];
        if (n2 > 0) {
            System.arraycopy(arrstring, 0, arrstring2, 0, n2);
        }
        if (n2 + 1 < n3) {
            System.arraycopy(arrstring, n2 + 1, arrstring2, n2, n3 - (n2 + 1));
        }
        return arrstring2;
    }

    public final Iterator<String> actionsIterator() {
        Object object = this.mActions;
        object = object != null ? ((ArrayList)object).iterator() : null;
        return object;
    }

    public final void addAction(String string2) {
        if (!this.mActions.contains(string2)) {
            this.mActions.add(string2.intern());
        }
    }

    public final void addCategory(String string2) {
        if (this.mCategories == null) {
            this.mCategories = new ArrayList();
        }
        if (!this.mCategories.contains(string2)) {
            this.mCategories.add(string2.intern());
        }
    }

    public final void addDataAuthority(AuthorityEntry authorityEntry) {
        if (this.mDataAuthorities == null) {
            this.mDataAuthorities = new ArrayList();
        }
        this.mDataAuthorities.add(authorityEntry);
    }

    public final void addDataAuthority(String string2, String string3) {
        String string4 = string3;
        if (string3 != null) {
            string4 = string3.intern();
        }
        this.addDataAuthority(new AuthorityEntry(string2.intern(), string4));
    }

    public final void addDataPath(PatternMatcher patternMatcher) {
        if (this.mDataPaths == null) {
            this.mDataPaths = new ArrayList();
        }
        this.mDataPaths.add(patternMatcher);
    }

    public final void addDataPath(String string2, int n) {
        this.addDataPath(new PatternMatcher(string2.intern(), n));
    }

    public final void addDataScheme(String string2) {
        if (this.mDataSchemes == null) {
            this.mDataSchemes = new ArrayList();
        }
        if (!this.mDataSchemes.contains(string2)) {
            this.mDataSchemes.add(string2.intern());
        }
    }

    public final void addDataSchemeSpecificPart(PatternMatcher patternMatcher) {
        if (this.mDataSchemeSpecificParts == null) {
            this.mDataSchemeSpecificParts = new ArrayList();
        }
        this.mDataSchemeSpecificParts.add(patternMatcher);
    }

    public final void addDataSchemeSpecificPart(String string2, int n) {
        this.addDataSchemeSpecificPart(new PatternMatcher(string2, n));
    }

    public final void addDataType(String string2) throws MalformedMimeTypeException {
        int n = string2.indexOf(47);
        int n2 = string2.length();
        if (n > 0 && n2 >= n + 2) {
            if (this.mDataTypes == null) {
                this.mDataTypes = new ArrayList();
            }
            if (n2 == n + 2 && string2.charAt(n + 1) == '*') {
                if (!this.mDataTypes.contains(string2 = string2.substring(0, n))) {
                    this.mDataTypes.add(string2.intern());
                }
                this.mHasPartialTypes = true;
            } else if (!this.mDataTypes.contains(string2)) {
                this.mDataTypes.add(string2.intern());
            }
            return;
        }
        throw new MalformedMimeTypeException(string2);
    }

    public final Iterator<AuthorityEntry> authoritiesIterator() {
        Object object = this.mDataAuthorities;
        object = object != null ? ((ArrayList)object).iterator() : null;
        return object;
    }

    public final Iterator<String> categoriesIterator() {
        Object object = this.mCategories;
        object = object != null ? ((ArrayList)object).iterator() : null;
        return object;
    }

    public final int countActions() {
        return this.mActions.size();
    }

    public final int countCategories() {
        ArrayList<String> arrayList = this.mCategories;
        int n = arrayList != null ? arrayList.size() : 0;
        return n;
    }

    public final int countDataAuthorities() {
        ArrayList<AuthorityEntry> arrayList = this.mDataAuthorities;
        int n = arrayList != null ? arrayList.size() : 0;
        return n;
    }

    public final int countDataPaths() {
        ArrayList<PatternMatcher> arrayList = this.mDataPaths;
        int n = arrayList != null ? arrayList.size() : 0;
        return n;
    }

    public final int countDataSchemeSpecificParts() {
        ArrayList<PatternMatcher> arrayList = this.mDataSchemeSpecificParts;
        int n = arrayList != null ? arrayList.size() : 0;
        return n;
    }

    public final int countDataSchemes() {
        ArrayList<String> arrayList = this.mDataSchemes;
        int n = arrayList != null ? arrayList.size() : 0;
        return n;
    }

    public final int countDataTypes() {
        ArrayList<String> arrayList = this.mDataTypes;
        int n = arrayList != null ? arrayList.size() : 0;
        return n;
    }

    public boolean debugCheck() {
        return true;
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String string2) {
        Iterator<PatternMatcher> iterator;
        Object object;
        StringBuilder stringBuilder = new StringBuilder(256);
        if (this.mActions.size() > 0) {
            object = this.mActions.iterator();
            while (object.hasNext()) {
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("Action: \"");
                stringBuilder.append((String)object.next());
                stringBuilder.append("\"");
                printer.println(stringBuilder.toString());
            }
        }
        if ((object = this.mCategories) != null) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("Category: \"");
                stringBuilder.append((String)object.next());
                stringBuilder.append("\"");
                printer.println(stringBuilder.toString());
            }
        }
        if ((object = this.mDataSchemes) != null) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("Scheme: \"");
                stringBuilder.append((String)object.next());
                stringBuilder.append("\"");
                printer.println(stringBuilder.toString());
            }
        }
        if ((object = this.mDataSchemeSpecificParts) != null) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                iterator = (PatternMatcher)object.next();
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("Ssp: \"");
                stringBuilder.append(iterator);
                stringBuilder.append("\"");
                printer.println(stringBuilder.toString());
            }
        }
        if ((object = this.mDataAuthorities) != null) {
            iterator = ((ArrayList)object).iterator();
            while (iterator.hasNext()) {
                object = (AuthorityEntry)iterator.next();
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("Authority: \"");
                stringBuilder.append(((AuthorityEntry)object).mHost);
                stringBuilder.append("\": ");
                stringBuilder.append(((AuthorityEntry)object).mPort);
                if (((AuthorityEntry)object).mWild) {
                    stringBuilder.append(" WILD");
                }
                printer.println(stringBuilder.toString());
            }
        }
        if ((object = this.mDataPaths) != null) {
            iterator = ((ArrayList)object).iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("Path: \"");
                stringBuilder.append(object);
                stringBuilder.append("\"");
                printer.println(stringBuilder.toString());
            }
        }
        if ((object = this.mDataTypes) != null) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                stringBuilder.setLength(0);
                stringBuilder.append(string2);
                stringBuilder.append("Type: \"");
                stringBuilder.append((String)object.next());
                stringBuilder.append("\"");
                printer.println(stringBuilder.toString());
            }
        }
        if (this.mPriority != 0 || this.mOrder != 0 || this.mHasPartialTypes) {
            stringBuilder.setLength(0);
            stringBuilder.append(string2);
            stringBuilder.append("mPriority=");
            stringBuilder.append(this.mPriority);
            stringBuilder.append(", mOrder=");
            stringBuilder.append(this.mOrder);
            stringBuilder.append(", mHasPartialTypes=");
            stringBuilder.append(this.mHasPartialTypes);
            printer.println(stringBuilder.toString());
        }
        if (this.getAutoVerify()) {
            stringBuilder.setLength(0);
            stringBuilder.append(string2);
            stringBuilder.append("AutoVerify=");
            stringBuilder.append(this.getAutoVerify());
            printer.println(stringBuilder.toString());
        }
    }

    public final String getAction(int n) {
        return this.mActions.get(n);
    }

    public final boolean getAutoVerify() {
        int n = this.mVerifyState;
        boolean bl = true;
        if ((n & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    public final String getCategory(int n) {
        return this.mCategories.get(n);
    }

    public final AuthorityEntry getDataAuthority(int n) {
        return this.mDataAuthorities.get(n);
    }

    public final PatternMatcher getDataPath(int n) {
        return this.mDataPaths.get(n);
    }

    public final String getDataScheme(int n) {
        return this.mDataSchemes.get(n);
    }

    public final PatternMatcher getDataSchemeSpecificPart(int n) {
        return this.mDataSchemeSpecificParts.get(n);
    }

    public final String getDataType(int n) {
        return this.mDataTypes.get(n);
    }

    public String[] getHosts() {
        ArrayList<String> arrayList = this.getHostsList();
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public ArrayList<String> getHostsList() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<AuthorityEntry> iterator = this.authoritiesIterator();
        if (iterator != null) {
            while (iterator.hasNext()) {
                arrayList.add(iterator.next().getHost());
            }
        }
        return arrayList;
    }

    @SystemApi
    public final int getOrder() {
        return this.mOrder;
    }

    public final int getPriority() {
        return this.mPriority;
    }

    public int getVisibilityToInstantApp() {
        return this.mInstantAppVisibility;
    }

    public final boolean handleAllWebDataURI() {
        boolean bl;
        block3 : {
            block2 : {
                bl = this.hasCategory("android.intent.category.APP_BROWSER");
                boolean bl2 = false;
                if (bl) break block2;
                bl = bl2;
                if (!this.handlesWebUris(false)) break block3;
                bl = bl2;
                if (this.countDataAuthorities() != 0) break block3;
            }
            bl = true;
        }
        return bl;
    }

    public final boolean handlesWebUris(boolean bl) {
        Object object;
        if (this.hasAction("android.intent.action.VIEW") && this.hasCategory("android.intent.category.BROWSABLE") && (object = this.mDataSchemes) != null && ((ArrayList)object).size() != 0) {
            int n = this.mDataSchemes.size();
            for (int i = 0; i < n; ++i) {
                object = this.mDataSchemes.get(i);
                boolean bl2 = SCHEME_HTTP.equals(object) || SCHEME_HTTPS.equals(object);
                if (bl) {
                    if (bl2) continue;
                    return false;
                }
                if (!bl2) continue;
                return true;
            }
            return bl;
        }
        return false;
    }

    public final boolean hasAction(String string2) {
        boolean bl = string2 != null && this.mActions.contains(string2);
        return bl;
    }

    public final boolean hasCategory(String string2) {
        ArrayList<String> arrayList = this.mCategories;
        boolean bl = arrayList != null && arrayList.contains(string2);
        return bl;
    }

    @UnsupportedAppUsage
    public final boolean hasDataAuthority(AuthorityEntry authorityEntry) {
        ArrayList<AuthorityEntry> arrayList = this.mDataAuthorities;
        if (arrayList == null) {
            return false;
        }
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            if (!this.mDataAuthorities.get(i).match(authorityEntry)) continue;
            return true;
        }
        return false;
    }

    public final boolean hasDataAuthority(Uri uri) {
        boolean bl = this.matchDataAuthority(uri) >= 0;
        return bl;
    }

    @UnsupportedAppUsage
    public final boolean hasDataPath(PatternMatcher patternMatcher) {
        Object object = this.mDataPaths;
        if (object == null) {
            return false;
        }
        int n = ((ArrayList)object).size();
        for (int i = 0; i < n; ++i) {
            object = this.mDataPaths.get(i);
            if (((PatternMatcher)object).getType() != patternMatcher.getType() || !((PatternMatcher)object).getPath().equals(patternMatcher.getPath())) continue;
            return true;
        }
        return false;
    }

    public final boolean hasDataPath(String string2) {
        ArrayList<PatternMatcher> arrayList = this.mDataPaths;
        if (arrayList == null) {
            return false;
        }
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            if (!this.mDataPaths.get(i).match(string2)) continue;
            return true;
        }
        return false;
    }

    public final boolean hasDataScheme(String string2) {
        ArrayList<String> arrayList = this.mDataSchemes;
        boolean bl = arrayList != null && arrayList.contains(string2);
        return bl;
    }

    @UnsupportedAppUsage
    public final boolean hasDataSchemeSpecificPart(PatternMatcher patternMatcher) {
        Object object = this.mDataSchemeSpecificParts;
        if (object == null) {
            return false;
        }
        int n = ((ArrayList)object).size();
        for (int i = 0; i < n; ++i) {
            object = this.mDataSchemeSpecificParts.get(i);
            if (((PatternMatcher)object).getType() != patternMatcher.getType() || !((PatternMatcher)object).getPath().equals(patternMatcher.getPath())) continue;
            return true;
        }
        return false;
    }

    public final boolean hasDataSchemeSpecificPart(String string2) {
        ArrayList<PatternMatcher> arrayList = this.mDataSchemeSpecificParts;
        if (arrayList == null) {
            return false;
        }
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            if (!this.mDataSchemeSpecificParts.get(i).match(string2)) continue;
            return true;
        }
        return false;
    }

    public final boolean hasDataType(String string2) {
        boolean bl = this.mDataTypes != null && this.findMimeType(string2);
        return bl;
    }

    @UnsupportedAppUsage
    public final boolean hasExactDataType(String string2) {
        ArrayList<String> arrayList = this.mDataTypes;
        boolean bl = arrayList != null && arrayList.contains(string2);
        return bl;
    }

    public boolean isExplicitlyVisibleToInstantApp() {
        int n = this.mInstantAppVisibility;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isImplicitlyVisibleToInstantApp() {
        boolean bl = this.mInstantAppVisibility == 2;
        return bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public final boolean isVerified() {
        int n = this.mVerifyState;
        boolean bl = false;
        if ((n & 256) == 256) {
            if ((n & 16) == 16) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public boolean isVisibleToInstantApp() {
        boolean bl = this.mInstantAppVisibility != 0;
        return bl;
    }

    public final int match(ContentResolver object, Intent intent, boolean bl, String string2) {
        object = bl ? intent.resolveType((ContentResolver)object) : intent.getType();
        return this.match(intent.getAction(), (String)object, intent.getScheme(), intent.getData(), intent.getCategories(), string2);
    }

    public final int match(String string2, String string3, String string4, Uri uri, Set<String> set, String string5) {
        if (string2 != null && !this.matchAction(string2)) {
            return -3;
        }
        int n = this.matchData(string3, string4, uri);
        if (n < 0) {
            return n;
        }
        if (this.matchCategories(set) != null) {
            return -4;
        }
        return n;
    }

    public final boolean matchAction(String string2) {
        return this.hasAction(string2);
    }

    public final String matchCategories(Set<String> object) {
        Object var2_2 = null;
        if (object == null) {
            return null;
        }
        Iterator<String> iterator = object.iterator();
        if (this.mCategories == null) {
            object = var2_2;
            if (iterator.hasNext()) {
                object = iterator.next();
            }
            return object;
        }
        while (iterator.hasNext()) {
            object = iterator.next();
            if (this.mCategories.contains(object)) continue;
            return object;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int matchData(String string2, String string3, Uri uri) {
        ArrayList<String> arrayList = this.mDataTypes;
        ArrayList<String> arrayList2 = this.mDataSchemes;
        int n = 1048576;
        int n2 = -2;
        if (arrayList == null && arrayList2 == null) {
            if (string2 != null) return n2;
            if (uri != null) return n2;
            return 1081344;
        }
        String string4 = "";
        if (arrayList2 != null) {
            if (string3 != null) {
                string4 = string3;
            }
            if (!arrayList2.contains(string4)) return -2;
            n = n2 = 2097152;
            if (this.mDataSchemeSpecificParts != null) {
                n = n2;
                if (uri != null) {
                    n = this.hasDataSchemeSpecificPart(uri.getSchemeSpecificPart()) ? 5767168 : -2;
                }
            }
            n2 = n;
            if (n != 5767168) {
                n2 = n;
                if (this.mDataAuthorities != null) {
                    n2 = this.matchDataAuthority(uri);
                    if (n2 < 0) return -2;
                    if (this.mDataPaths != null) {
                        if (!this.hasDataPath(uri.getPath())) return -2;
                        n2 = 5242880;
                    }
                }
            }
            if (n2 == -2) {
                return -2;
            }
        } else {
            n2 = n;
            if (string3 != null) {
                n2 = n;
                if (!"".equals(string3)) {
                    n2 = n;
                    if (!"content".equals(string3)) {
                        n2 = n;
                        if (!"file".equals(string3)) {
                            return -2;
                        }
                    }
                }
            }
        }
        if (arrayList == null) {
            if (string2 == null) return 32768 + n2;
            return -1;
        }
        if (!this.findMimeType(string2)) return -1;
        n2 = 6291456;
        return 32768 + n2;
    }

    public final int matchDataAuthority(Uri uri) {
        ArrayList<AuthorityEntry> arrayList = this.mDataAuthorities;
        if (arrayList != null && uri != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                int n2 = this.mDataAuthorities.get(i).match(uri);
                if (n2 < 0) continue;
                return n2;
            }
            return -2;
        }
        return -2;
    }

    public final boolean needsVerification() {
        boolean bl = this.getAutoVerify();
        boolean bl2 = true;
        if (!bl || !this.handlesWebUris(true)) {
            bl2 = false;
        }
        return bl2;
    }

    public final Iterator<PatternMatcher> pathsIterator() {
        Object object = this.mDataPaths;
        object = object != null ? ((ArrayList)object).iterator() : null;
        return object;
    }

    public void readFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n;
        String string2 = xmlPullParser.getAttributeValue(null, AUTO_VERIFY_STR);
        boolean bl = TextUtils.isEmpty(string2) ? false : Boolean.getBoolean(string2);
        this.setAutoVerify(bl);
        int n2 = xmlPullParser.getDepth();
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
            CharSequence charSequence;
            if (n == 3 || n == 4) continue;
            string2 = xmlPullParser.getName();
            if (string2.equals(ACTION_STR)) {
                string2 = xmlPullParser.getAttributeValue(null, NAME_STR);
                if (string2 != null) {
                    this.addAction(string2);
                }
            } else if (string2.equals(CAT_STR)) {
                string2 = xmlPullParser.getAttributeValue(null, NAME_STR);
                if (string2 != null) {
                    this.addCategory(string2);
                }
            } else if (string2.equals(TYPE_STR)) {
                string2 = xmlPullParser.getAttributeValue(null, NAME_STR);
                if (string2 != null) {
                    try {
                        this.addDataType(string2);
                    }
                    catch (MalformedMimeTypeException malformedMimeTypeException) {}
                }
            } else if (string2.equals(SCHEME_STR)) {
                string2 = xmlPullParser.getAttributeValue(null, NAME_STR);
                if (string2 != null) {
                    this.addDataScheme(string2);
                }
            } else if (string2.equals(SSP_STR)) {
                string2 = xmlPullParser.getAttributeValue(null, LITERAL_STR);
                if (string2 != null) {
                    this.addDataSchemeSpecificPart(string2, 0);
                } else {
                    string2 = xmlPullParser.getAttributeValue(null, PREFIX_STR);
                    if (string2 != null) {
                        this.addDataSchemeSpecificPart(string2, 1);
                    } else {
                        string2 = xmlPullParser.getAttributeValue(null, SGLOB_STR);
                        if (string2 != null) {
                            this.addDataSchemeSpecificPart(string2, 2);
                        } else {
                            string2 = xmlPullParser.getAttributeValue(null, AGLOB_STR);
                            if (string2 != null) {
                                this.addDataSchemeSpecificPart(string2, 3);
                            }
                        }
                    }
                }
            } else if (string2.equals(AUTH_STR)) {
                string2 = xmlPullParser.getAttributeValue(null, HOST_STR);
                charSequence = xmlPullParser.getAttributeValue(null, PORT_STR);
                if (string2 != null) {
                    this.addDataAuthority(string2, (String)charSequence);
                }
            } else if (string2.equals(PATH_STR)) {
                string2 = xmlPullParser.getAttributeValue(null, LITERAL_STR);
                if (string2 != null) {
                    this.addDataPath(string2, 0);
                } else {
                    string2 = xmlPullParser.getAttributeValue(null, PREFIX_STR);
                    if (string2 != null) {
                        this.addDataPath(string2, 1);
                    } else {
                        string2 = xmlPullParser.getAttributeValue(null, SGLOB_STR);
                        if (string2 != null) {
                            this.addDataPath(string2, 2);
                        } else {
                            string2 = xmlPullParser.getAttributeValue(null, AGLOB_STR);
                            if (string2 != null) {
                                this.addDataPath(string2, 3);
                            }
                        }
                    }
                }
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unknown tag parsing IntentFilter: ");
                ((StringBuilder)charSequence).append(string2);
                Log.w("IntentFilter", ((StringBuilder)charSequence).toString());
            }
            XmlUtils.skipCurrentTag(xmlPullParser);
        }
    }

    public final Iterator<PatternMatcher> schemeSpecificPartsIterator() {
        Object object = this.mDataSchemeSpecificParts;
        object = object != null ? ((ArrayList)object).iterator() : null;
        return object;
    }

    public final Iterator<String> schemesIterator() {
        Object object = this.mDataSchemes;
        object = object != null ? ((ArrayList)object).iterator() : null;
        return object;
    }

    @UnsupportedAppUsage
    public final void setAutoVerify(boolean bl) {
        this.mVerifyState &= -2;
        if (bl) {
            this.mVerifyState |= 1;
        }
    }

    @SystemApi
    public final void setOrder(int n) {
        this.mOrder = n;
    }

    public final void setPriority(int n) {
        this.mPriority = n;
    }

    public void setVerified(boolean bl) {
        this.mVerifyState |= 256;
        this.mVerifyState &= -4097;
        if (bl) {
            this.mVerifyState |= 4096;
        }
    }

    public void setVisibilityToInstantApp(int n) {
        this.mInstantAppVisibility = n;
    }

    public final Iterator<String> typesIterator() {
        Object object = this.mDataTypes;
        object = object != null ? ((ArrayList)object).iterator() : null;
        return object;
    }

    @Override
    public final void writeToParcel(Parcel parcel, int n) {
        int n2;
        int n3;
        parcel.writeStringList(this.mActions);
        if (this.mCategories != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mCategories);
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataSchemes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mDataSchemes);
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataTypes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mDataTypes);
        } else {
            parcel.writeInt(0);
        }
        ArrayList<Object> arrayList = this.mDataSchemeSpecificParts;
        if (arrayList != null) {
            n3 = arrayList.size();
            parcel.writeInt(n3);
            for (n2 = 0; n2 < n3; ++n2) {
                this.mDataSchemeSpecificParts.get(n2).writeToParcel(parcel, n);
            }
        } else {
            parcel.writeInt(0);
        }
        arrayList = this.mDataAuthorities;
        if (arrayList != null) {
            n3 = arrayList.size();
            parcel.writeInt(n3);
            for (n2 = 0; n2 < n3; ++n2) {
                this.mDataAuthorities.get(n2).writeToParcel(parcel);
            }
        } else {
            parcel.writeInt(0);
        }
        arrayList = this.mDataPaths;
        if (arrayList != null) {
            n3 = arrayList.size();
            parcel.writeInt(n3);
            for (n2 = 0; n2 < n3; ++n2) {
                this.mDataPaths.get(n2).writeToParcel(parcel, n);
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mPriority);
        parcel.writeInt((int)this.mHasPartialTypes);
        parcel.writeInt((int)this.getAutoVerify());
        parcel.writeInt(this.mInstantAppVisibility);
        parcel.writeInt(this.mOrder);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        Iterator iterator;
        l = protoOutputStream.start(l);
        if (this.mActions.size() > 0) {
            iterator = this.mActions.iterator();
            while (iterator.hasNext()) {
                protoOutputStream.write(2237677961217L, (String)iterator.next());
            }
        }
        if ((iterator = this.mCategories) != null) {
            iterator = ((ArrayList)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                protoOutputStream.write(2237677961218L, (String)iterator.next());
            }
        }
        if ((iterator = this.mDataSchemes) != null) {
            iterator = ((ArrayList)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                protoOutputStream.write(2237677961219L, (String)iterator.next());
            }
        }
        if ((iterator = this.mDataSchemeSpecificParts) != null) {
            iterator = ((ArrayList)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                ((PatternMatcher)iterator.next()).writeToProto(protoOutputStream, 2246267895812L);
            }
        }
        if ((iterator = this.mDataAuthorities) != null) {
            iterator = ((ArrayList)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                ((AuthorityEntry)iterator.next()).writeToProto(protoOutputStream, 2246267895813L);
            }
        }
        if ((iterator = this.mDataPaths) != null) {
            iterator = ((ArrayList)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                ((PatternMatcher)iterator.next()).writeToProto(protoOutputStream, 2246267895814L);
            }
        }
        if ((iterator = this.mDataTypes) != null) {
            iterator = ((ArrayList)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                protoOutputStream.write(2237677961223L, (String)iterator.next());
            }
        }
        if (this.mPriority != 0 || this.mHasPartialTypes) {
            protoOutputStream.write(1120986464264L, this.mPriority);
            protoOutputStream.write(1133871366153L, this.mHasPartialTypes);
        }
        protoOutputStream.write(1133871366154L, this.getAutoVerify());
        protoOutputStream.end(l);
    }

    public void writeToXml(XmlSerializer xmlSerializer) throws IOException {
        int n;
        int n2;
        Object object;
        if (this.getAutoVerify()) {
            xmlSerializer.attribute(null, AUTO_VERIFY_STR, Boolean.toString(true));
        }
        int n3 = this.countActions();
        for (n = 0; n < n3; ++n) {
            xmlSerializer.startTag(null, ACTION_STR);
            xmlSerializer.attribute(null, NAME_STR, this.mActions.get(n));
            xmlSerializer.endTag(null, ACTION_STR);
        }
        n3 = this.countCategories();
        for (n = 0; n < n3; ++n) {
            xmlSerializer.startTag(null, CAT_STR);
            xmlSerializer.attribute(null, NAME_STR, this.mCategories.get(n));
            xmlSerializer.endTag(null, CAT_STR);
        }
        n3 = this.countDataTypes();
        for (n = 0; n < n3; ++n) {
            xmlSerializer.startTag(null, TYPE_STR);
            String string2 = this.mDataTypes.get(n);
            object = string2;
            if (string2.indexOf(47) < 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("/*");
                object = ((StringBuilder)object).toString();
            }
            xmlSerializer.attribute(null, NAME_STR, (String)object);
            xmlSerializer.endTag(null, TYPE_STR);
        }
        n3 = this.countDataSchemes();
        for (n = 0; n < n3; ++n) {
            xmlSerializer.startTag(null, SCHEME_STR);
            xmlSerializer.attribute(null, NAME_STR, this.mDataSchemes.get(n));
            xmlSerializer.endTag(null, SCHEME_STR);
        }
        n3 = this.countDataSchemeSpecificParts();
        for (n = 0; n < n3; ++n) {
            xmlSerializer.startTag(null, SSP_STR);
            object = this.mDataSchemeSpecificParts.get(n);
            n2 = ((PatternMatcher)object).getType();
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 == 3) {
                            xmlSerializer.attribute(null, AGLOB_STR, ((PatternMatcher)object).getPath());
                        }
                    } else {
                        xmlSerializer.attribute(null, SGLOB_STR, ((PatternMatcher)object).getPath());
                    }
                } else {
                    xmlSerializer.attribute(null, PREFIX_STR, ((PatternMatcher)object).getPath());
                }
            } else {
                xmlSerializer.attribute(null, LITERAL_STR, ((PatternMatcher)object).getPath());
            }
            xmlSerializer.endTag(null, SSP_STR);
        }
        n3 = this.countDataAuthorities();
        for (n = 0; n < n3; ++n) {
            xmlSerializer.startTag(null, AUTH_STR);
            object = this.mDataAuthorities.get(n);
            xmlSerializer.attribute(null, HOST_STR, ((AuthorityEntry)object).getHost());
            if (((AuthorityEntry)object).getPort() >= 0) {
                xmlSerializer.attribute(null, PORT_STR, Integer.toString(((AuthorityEntry)object).getPort()));
            }
            xmlSerializer.endTag(null, AUTH_STR);
        }
        n3 = this.countDataPaths();
        for (n = 0; n < n3; ++n) {
            xmlSerializer.startTag(null, PATH_STR);
            object = this.mDataPaths.get(n);
            n2 = ((PatternMatcher)object).getType();
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 == 3) {
                            xmlSerializer.attribute(null, AGLOB_STR, ((PatternMatcher)object).getPath());
                        }
                    } else {
                        xmlSerializer.attribute(null, SGLOB_STR, ((PatternMatcher)object).getPath());
                    }
                } else {
                    xmlSerializer.attribute(null, PREFIX_STR, ((PatternMatcher)object).getPath());
                }
            } else {
                xmlSerializer.attribute(null, LITERAL_STR, ((PatternMatcher)object).getPath());
            }
            xmlSerializer.endTag(null, PATH_STR);
        }
    }

    public static final class AuthorityEntry {
        private final String mHost;
        private final String mOrigHost;
        private final int mPort;
        private final boolean mWild;

        AuthorityEntry(Parcel parcel) {
            this.mOrigHost = parcel.readString();
            this.mHost = parcel.readString();
            boolean bl = parcel.readInt() != 0;
            this.mWild = bl;
            this.mPort = parcel.readInt();
        }

        public AuthorityEntry(String string2, String string3) {
            boolean bl;
            this.mOrigHost = string2;
            int n = string2.length();
            boolean bl2 = bl = false;
            if (n > 0) {
                bl2 = bl;
                if (string2.charAt(0) == '*') {
                    bl2 = true;
                }
            }
            this.mWild = bl2;
            if (this.mWild) {
                string2 = string2.substring(1).intern();
            }
            this.mHost = string2;
            n = string3 != null ? Integer.parseInt(string3) : -1;
            this.mPort = n;
        }

        public boolean equals(Object object) {
            if (object instanceof AuthorityEntry) {
                return this.match((AuthorityEntry)object);
            }
            return false;
        }

        public String getHost() {
            return this.mOrigHost;
        }

        public int getPort() {
            return this.mPort;
        }

        public int match(Uri uri) {
            String string2 = uri.getHost();
            if (string2 == null) {
                return -2;
            }
            String string3 = string2;
            if (this.mWild) {
                if (string2.length() < this.mHost.length()) {
                    return -2;
                }
                string3 = string2.substring(string2.length() - this.mHost.length());
            }
            if (string3.compareToIgnoreCase(this.mHost) != 0) {
                return -2;
            }
            int n = this.mPort;
            if (n >= 0) {
                if (n != uri.getPort()) {
                    return -2;
                }
                return 4194304;
            }
            return 3145728;
        }

        public boolean match(AuthorityEntry authorityEntry) {
            if (this.mWild != authorityEntry.mWild) {
                return false;
            }
            if (!this.mHost.equals(authorityEntry.mHost)) {
                return false;
            }
            return this.mPort == authorityEntry.mPort;
        }

        void writeToParcel(Parcel parcel) {
            parcel.writeString(this.mOrigHost);
            parcel.writeString(this.mHost);
            parcel.writeInt((int)this.mWild);
            parcel.writeInt(this.mPort);
        }

        void writeToProto(ProtoOutputStream protoOutputStream, long l) {
            l = protoOutputStream.start(l);
            protoOutputStream.write(1138166333441L, this.mHost);
            protoOutputStream.write(1133871366146L, this.mWild);
            protoOutputStream.write(1120986464259L, this.mPort);
            protoOutputStream.end(l);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InstantAppVisibility {
    }

    public static class MalformedMimeTypeException
    extends AndroidException {
        public MalformedMimeTypeException() {
        }

        public MalformedMimeTypeException(String string2) {
            super(string2);
        }
    }

}

