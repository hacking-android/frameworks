/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SystemApi
public class WebAddress {
    static final int MATCH_GROUP_AUTHORITY = 2;
    static final int MATCH_GROUP_HOST = 3;
    static final int MATCH_GROUP_PATH = 5;
    static final int MATCH_GROUP_PORT = 4;
    static final int MATCH_GROUP_SCHEME = 1;
    static Pattern sAddressPattern = Pattern.compile("(?:(http|https|file)\\:\\/\\/)?(?:([-A-Za-z0-9$_.+!*'(),;?&=]+(?:\\:[-A-Za-z0-9$_.+!*'(),;?&=]+)?)@)?([a-zA-Z0-9\u00a0-\ud7ff\uf900-\ufdcf\ufdf0-\uffef%_-][a-zA-Z0-9\u00a0-\ud7ff\uf900-\ufdcf\ufdf0-\uffef%_\\.-]*|\\[[0-9a-fA-F:\\.]+\\])?(?:\\:([0-9]*))?(\\/?[^#]*)?.*", 2);
    private String mAuthInfo;
    @UnsupportedAppUsage
    private String mHost;
    @UnsupportedAppUsage
    private String mPath;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mPort;
    @UnsupportedAppUsage
    private String mScheme;

    public WebAddress(String object) throws ParseException {
        if (object != null) {
            this.mScheme = "";
            this.mHost = "";
            this.mPort = -1;
            this.mPath = "/";
            this.mAuthInfo = "";
            if (((Matcher)(object = sAddressPattern.matcher((CharSequence)object))).matches()) {
                CharSequence charSequence = ((Matcher)object).group(1);
                if (charSequence != null) {
                    this.mScheme = ((String)charSequence).toLowerCase(Locale.ROOT);
                }
                if ((charSequence = ((Matcher)object).group(2)) != null) {
                    this.mAuthInfo = charSequence;
                }
                if ((charSequence = ((Matcher)object).group(3)) != null) {
                    this.mHost = charSequence;
                }
                if ((charSequence = ((Matcher)object).group(4)) != null && ((String)charSequence).length() > 0) {
                    try {
                        this.mPort = Integer.parseInt((String)charSequence);
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw new ParseException("Bad port");
                    }
                }
                if ((object = ((Matcher)object).group(5)) != null && ((String)object).length() > 0) {
                    if (((String)object).charAt(0) == '/') {
                        this.mPath = object;
                    } else {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("/");
                        ((StringBuilder)charSequence).append((String)object);
                        this.mPath = ((StringBuilder)charSequence).toString();
                    }
                }
                if (this.mPort == 443 && this.mScheme.equals("")) {
                    this.mScheme = "https";
                } else if (this.mPort == -1) {
                    this.mPort = this.mScheme.equals("https") ? 443 : 80;
                }
                if (this.mScheme.equals("")) {
                    this.mScheme = "http";
                }
                return;
            }
            throw new ParseException("Bad address");
        }
        throw new NullPointerException();
    }

    @UnsupportedAppUsage
    public String getAuthInfo() {
        return this.mAuthInfo;
    }

    @UnsupportedAppUsage
    public String getHost() {
        return this.mHost;
    }

    @UnsupportedAppUsage
    public String getPath() {
        return this.mPath;
    }

    @UnsupportedAppUsage
    public int getPort() {
        return this.mPort;
    }

    @UnsupportedAppUsage
    public String getScheme() {
        return this.mScheme;
    }

    public void setAuthInfo(String string2) {
        this.mAuthInfo = string2;
    }

    @UnsupportedAppUsage
    public void setHost(String string2) {
        this.mHost = string2;
    }

    @UnsupportedAppUsage
    public void setPath(String string2) {
        this.mPath = string2;
    }

    public void setPort(int n) {
        this.mPort = n;
    }

    public void setScheme(String string2) {
        this.mScheme = string2;
    }

    public String toString() {
        CharSequence charSequence;
        CharSequence charSequence2;
        block5 : {
            block4 : {
                charSequence = "";
                if (this.mPort != 443 && this.mScheme.equals("https")) break block4;
                charSequence2 = charSequence;
                if (this.mPort == 80) break block5;
                charSequence2 = charSequence;
                if (!this.mScheme.equals("http")) break block5;
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append(":");
            ((StringBuilder)charSequence2).append(Integer.toString(this.mPort));
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = "";
        if (this.mAuthInfo.length() > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.mAuthInfo);
            ((StringBuilder)charSequence).append("@");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mScheme);
        stringBuilder.append("://");
        stringBuilder.append((String)charSequence);
        stringBuilder.append(this.mHost);
        stringBuilder.append((String)charSequence2);
        stringBuilder.append(this.mPath);
        return stringBuilder.toString();
    }
}

