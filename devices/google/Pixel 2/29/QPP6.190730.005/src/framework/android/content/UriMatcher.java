/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class UriMatcher {
    private static final int EXACT = 0;
    public static final int NO_MATCH = -1;
    private static final int NUMBER = 1;
    private static final int TEXT = 2;
    @UnsupportedAppUsage
    private ArrayList<UriMatcher> mChildren;
    private int mCode;
    @UnsupportedAppUsage
    private final String mText;
    private final int mWhich;

    public UriMatcher(int n) {
        this.mCode = n;
        this.mWhich = -1;
        this.mChildren = new ArrayList();
        this.mText = null;
    }

    private UriMatcher(int n, String string2) {
        this.mCode = -1;
        this.mWhich = n;
        this.mChildren = new ArrayList();
        this.mText = string2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static UriMatcher createChild(String var0) {
        block3 : {
            block2 : {
                var1_1 = var0.hashCode();
                if (var1_1 == 35) break block2;
                if (var1_1 != 42 || !var0.equals("*")) ** GOTO lbl-1000
                var1_1 = 1;
                break block3;
            }
            if (var0.equals("#")) {
                var1_1 = 0;
            } else lbl-1000: // 2 sources:
            {
                var1_1 = -1;
            }
        }
        if (var1_1 == 0) return new UriMatcher(1, "#");
        if (var1_1 == 1) return new UriMatcher(2, "*");
        return new UriMatcher(0, var0);
    }

    public void addURI(String charSequence, String object, int n) {
        if (n >= 0) {
            Object object2;
            String[] arrstring = null;
            int n2 = 0;
            if (object != null) {
                object2 = arrstring = object;
                if (object.length() > 1) {
                    object2 = arrstring;
                    if (object.charAt(0) == '/') {
                        object2 = object.substring(1);
                    }
                }
                arrstring = object2.split("/");
            }
            if (arrstring != null) {
                n2 = arrstring.length;
            }
            object = this;
            for (int i = -1; i < n2; ++i) {
                CharSequence charSequence2 = i < 0 ? charSequence : arrstring[i];
                ArrayList<UriMatcher> arrayList = object.mChildren;
                int n3 = arrayList.size();
                int n4 = 0;
                do {
                    object2 = object;
                    if (n4 >= n3) break;
                    object2 = arrayList.get(n4);
                    if (((String)charSequence2).equals(object2.mText)) break;
                    ++n4;
                } while (true);
                object = object2;
                if (n4 != n3) continue;
                object = UriMatcher.createChild((String)charSequence2);
                object2.mChildren.add((UriMatcher)object);
            }
            object.mCode = n;
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("code ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" is invalid: it must be positive");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public int match(Uri uri) {
        List<String> list = uri.getPathSegments();
        int n = list.size();
        UriMatcher uriMatcher = this;
        if (n == 0 && uri.getAuthority() == null) {
            return this.mCode;
        }
        for (int i = -1; i < n; ++i) {
            String string2 = i < 0 ? uri.getAuthority() : list.get(i);
            ArrayList<UriMatcher> arrayList = uriMatcher.mChildren;
            if (arrayList == null) break;
            UriMatcher uriMatcher2 = null;
            int n2 = arrayList.size();
            int n3 = 0;
            do {
                block10 : {
                    uriMatcher = uriMatcher2;
                    if (n3 >= n2) break;
                    UriMatcher uriMatcher3 = arrayList.get(n3);
                    int n4 = uriMatcher3.mWhich;
                    if (n4 != 0) {
                        if (n4 != 1) {
                            uriMatcher = n4 != 2 ? uriMatcher2 : uriMatcher3;
                        } else {
                            int n5 = string2.length();
                            for (n4 = 0; n4 < n5; ++n4) {
                                char c = string2.charAt(n4);
                                uriMatcher = uriMatcher2;
                                if (c >= '0') {
                                    if (c <= '9') continue;
                                    uriMatcher = uriMatcher2;
                                }
                                break block10;
                            }
                            uriMatcher = uriMatcher3;
                        }
                    } else {
                        uriMatcher = uriMatcher2;
                        if (uriMatcher3.mText.equals(string2)) {
                            uriMatcher = uriMatcher3;
                        }
                    }
                }
                if (uriMatcher != null) break;
                ++n3;
                uriMatcher2 = uriMatcher;
            } while (true);
            if (uriMatcher != null) continue;
            return -1;
        }
        return uriMatcher.mCode;
    }
}

