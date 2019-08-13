/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.net.UriCodec;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Set;

public abstract class Uri
implements Parcelable,
Comparable<Uri> {
    public static final Parcelable.Creator<Uri> CREATOR;
    private static final String DEFAULT_ENCODING = "UTF-8";
    public static final Uri EMPTY;
    private static final char[] HEX_DIGITS;
    private static final String LOG;
    private static final String NOT_CACHED;
    private static final int NOT_CALCULATED = -2;
    private static final int NOT_FOUND = -1;
    private static final String NOT_HIERARCHICAL = "This isn't a hierarchical URI.";
    private static final int NULL_TYPE_ID = 0;

    static {
        LOG = Uri.class.getSimpleName();
        NOT_CACHED = new String("NOT CACHED");
        EMPTY = new HierarchicalUri(null, Part.NULL, PathPart.EMPTY, Part.NULL, Part.NULL);
        CREATOR = new Parcelable.Creator<Uri>(){

            @Override
            public Uri createFromParcel(Parcel object) {
                int n = ((Parcel)object).readInt();
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            if (n == 3) {
                                return HierarchicalUri.readFrom((Parcel)object);
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unknown URI type: ");
                            ((StringBuilder)object).append(n);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        return OpaqueUri.readFrom((Parcel)object);
                    }
                    return StringUri.readFrom((Parcel)object);
                }
                return null;
            }

            public Uri[] newArray(int n) {
                return new Uri[n];
            }
        };
        HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    }

    @UnsupportedAppUsage
    private Uri() {
    }

    public static String decode(String string2) {
        if (string2 == null) {
            return null;
        }
        return UriCodec.decode(string2, false, StandardCharsets.UTF_8, false);
    }

    public static String encode(String string2) {
        return Uri.encode(string2, null);
    }

    public static String encode(String string2, String string3) {
        if (string2 == null) {
            return null;
        }
        CharSequence charSequence = null;
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int n4;
            for (n3 = n2; n3 < n && Uri.isAllowed(string2.charAt(n3), string3); ++n3) {
            }
            if (n3 == n) {
                if (n2 == 0) {
                    return string2;
                }
                ((StringBuilder)charSequence).append(string2, n2, n);
                return ((StringBuilder)charSequence).toString();
            }
            StringBuilder stringBuilder = charSequence;
            if (charSequence == null) {
                stringBuilder = new StringBuilder();
            }
            if (n3 > n2) {
                stringBuilder.append(string2, n2, n3);
            }
            for (n2 = n3 + 1; n2 < n && !Uri.isAllowed(string2.charAt(n2), string3); ++n2) {
            }
            charSequence = string2.substring(n3, n2);
            try {
                charSequence = ((String)charSequence).getBytes(DEFAULT_ENCODING);
                n4 = ((CharSequence)charSequence).length;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new AssertionError(unsupportedEncodingException);
            }
            for (n3 = 0; n3 < n4; ++n3) {
                stringBuilder.append('%');
                stringBuilder.append(HEX_DIGITS[(charSequence[n3] & 240) >> 4]);
                stringBuilder.append(HEX_DIGITS[charSequence[n3] & 15]);
                continue;
            }
            charSequence = stringBuilder;
        }
        if (charSequence != null) {
            string2 = ((StringBuilder)charSequence).toString();
        }
        return string2;
    }

    public static Uri fromFile(File object) {
        if (object != null) {
            object = PathPart.fromDecoded(((File)object).getAbsolutePath());
            return new HierarchicalUri("file", Part.EMPTY, (PathPart)object, Part.NULL, Part.NULL);
        }
        throw new NullPointerException("file");
    }

    public static Uri fromParts(String string2, String string3, String string4) {
        if (string2 != null) {
            if (string3 != null) {
                return new OpaqueUri(string2, Part.fromDecoded(string3), Part.fromDecoded(string4));
            }
            throw new NullPointerException("ssp");
        }
        throw new NullPointerException("scheme");
    }

    private static boolean isAllowed(char c, String string2) {
        boolean bl = c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || "_-!.~'()*".indexOf(c) != -1 || string2 != null && string2.indexOf(c) != -1;
        return bl;
    }

    public static Uri parse(String string2) {
        return new StringUri(string2);
    }

    public static Uri withAppendedPath(Uri uri, String string2) {
        return uri.buildUpon().appendEncodedPath(string2).build();
    }

    public static void writeToParcel(Parcel parcel, Uri uri) {
        if (uri == null) {
            parcel.writeInt(0);
        } else {
            uri.writeToParcel(parcel, 0);
        }
    }

    public abstract Builder buildUpon();

    public void checkContentUriWithoutPermission(String string2, int n) {
        if ("content".equals(this.getScheme()) && !Intent.isAccessUriMode(n)) {
            StrictMode.onContentUriWithoutPermission(this, string2);
        }
    }

    public void checkFileUriExposed(String string2) {
        if ("file".equals(this.getScheme()) && this.getPath() != null && !this.getPath().startsWith("/system/")) {
            StrictMode.onFileUriExposed(this, string2);
        }
    }

    @Override
    public int compareTo(Uri uri) {
        return this.toString().compareTo(uri.toString());
    }

    public boolean equals(Object object) {
        if (!(object instanceof Uri)) {
            return false;
        }
        object = (Uri)object;
        return this.toString().equals(((Uri)object).toString());
    }

    public abstract String getAuthority();

    public boolean getBooleanQueryParameter(String string2, boolean bl) {
        if ((string2 = this.getQueryParameter(string2)) == null) {
            return bl;
        }
        bl = !"false".equals(string2 = string2.toLowerCase(Locale.ROOT)) && !"0".equals(string2);
        return bl;
    }

    @UnsupportedAppUsage
    public Uri getCanonicalUri() {
        if ("file".equals(this.getScheme())) {
            Object object;
            String string2;
            try {
                object = new File(this.getPath());
                object = ((File)object).getCanonicalPath();
            }
            catch (IOException iOException) {
                return this;
            }
            if (Environment.isExternalStorageEmulated() && ((String)object).startsWith(string2 = Environment.getLegacyExternalStorageDirectory().toString())) {
                return Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString(), ((String)object).substring(string2.length() + 1)));
            }
            return Uri.fromFile(new File((String)object));
        }
        return this;
    }

    public abstract String getEncodedAuthority();

    public abstract String getEncodedFragment();

    public abstract String getEncodedPath();

    public abstract String getEncodedQuery();

    public abstract String getEncodedSchemeSpecificPart();

    public abstract String getEncodedUserInfo();

    public abstract String getFragment();

    public abstract String getHost();

    public abstract String getLastPathSegment();

    public abstract String getPath();

    public abstract List<String> getPathSegments();

    public abstract int getPort();

    public abstract String getQuery();

    public String getQueryParameter(String string2) {
        block7 : {
            block8 : {
                if (this.isOpaque()) break block7;
                if (string2 == null) break block8;
                String string3 = this.getEncodedQuery();
                if (string3 == null) {
                    return null;
                }
                string2 = Uri.encode(string2, null);
                int n = string3.length();
                int n2 = 0;
                do {
                    int n3;
                    int n4;
                    int n5;
                    block10 : {
                        block9 : {
                            n5 = (n4 = string3.indexOf(38, n2)) != -1 ? n4 : n;
                            int n6 = string3.indexOf(61, n2);
                            if (n6 > n5) break block9;
                            n3 = n6;
                            if (n6 != -1) break block10;
                        }
                        n3 = n5;
                    }
                    if (n3 - n2 == string2.length() && string3.regionMatches(n2, string2, 0, string2.length())) {
                        if (n3 == n5) {
                            return "";
                        }
                        return UriCodec.decode(string3.substring(n3 + 1, n5), true, StandardCharsets.UTF_8, false);
                    }
                    if (n4 == -1) break;
                    n2 = n4 + 1;
                } while (true);
                return null;
            }
            throw new NullPointerException("key");
        }
        throw new UnsupportedOperationException("This isn't a hierarchical URI.");
    }

    public Set<String> getQueryParameterNames() {
        block6 : {
            int n;
            if (this.isOpaque()) break block6;
            String string2 = this.getEncodedQuery();
            if (string2 == null) {
                return Collections.emptySet();
            }
            LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
            int n2 = 0;
            do {
                int n3;
                block8 : {
                    block7 : {
                        int n4;
                        if ((n = string2.indexOf(38, n2)) == -1) {
                            n = string2.length();
                        }
                        if ((n4 = string2.indexOf(61, n2)) > n) break block7;
                        n3 = n4;
                        if (n4 != -1) break block8;
                    }
                    n3 = n;
                }
                linkedHashSet.add(Uri.decode(string2.substring(n2, n3)));
                n2 = ++n;
            } while (n < string2.length());
            return Collections.unmodifiableSet(linkedHashSet);
        }
        throw new UnsupportedOperationException("This isn't a hierarchical URI.");
    }

    public List<String> getQueryParameters(String string2) {
        block10 : {
            block11 : {
                int n;
                ArrayList<String> arrayList;
                if (this.isOpaque()) break block10;
                if (string2 == null) break block11;
                String string3 = this.getEncodedQuery();
                if (string3 == null) {
                    return Collections.emptyList();
                }
                try {
                    string2 = URLEncoder.encode(string2, "UTF-8");
                    arrayList = new ArrayList<String>();
                    n = 0;
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    throw new AssertionError(unsupportedEncodingException);
                }
                do {
                    int n2;
                    int n3;
                    int n4;
                    block13 : {
                        block12 : {
                            n4 = (n2 = string3.indexOf(38, n)) != -1 ? n2 : string3.length();
                            int n5 = string3.indexOf(61, n);
                            if (n5 > n4) break block12;
                            n3 = n5;
                            if (n5 != -1) break block13;
                        }
                        n3 = n4;
                    }
                    if (n3 - n == string2.length() && string3.regionMatches(n, string2, 0, string2.length())) {
                        if (n3 == n4) {
                            arrayList.add("");
                        } else {
                            arrayList.add(Uri.decode(string3.substring(n3 + 1, n4)));
                        }
                    }
                    if (n2 == -1) break;
                    n = n2 + 1;
                } while (true);
                return Collections.unmodifiableList(arrayList);
            }
            throw new NullPointerException("key");
        }
        throw new UnsupportedOperationException("This isn't a hierarchical URI.");
    }

    public abstract String getScheme();

    public abstract String getSchemeSpecificPart();

    public abstract String getUserInfo();

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean isAbsolute() {
        return this.isRelative() ^ true;
    }

    public abstract boolean isHierarchical();

    public boolean isOpaque() {
        return this.isHierarchical() ^ true;
    }

    public boolean isPathPrefixMatch(Uri object) {
        if (!Objects.equals(this.getScheme(), ((Uri)object).getScheme())) {
            return false;
        }
        if (!Objects.equals(this.getAuthority(), ((Uri)object).getAuthority())) {
            return false;
        }
        List<String> list = this.getPathSegments();
        object = ((Uri)object).getPathSegments();
        int n = object.size();
        if (list.size() < n) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (Objects.equals(list.get(i), object.get(i))) continue;
            return false;
        }
        return true;
    }

    public abstract boolean isRelative();

    public Uri normalizeScheme() {
        String string2 = this.getScheme();
        if (string2 == null) {
            return this;
        }
        String string3 = string2.toLowerCase(Locale.ROOT);
        if (string2.equals(string3)) {
            return this;
        }
        return this.buildUpon().scheme(string3).build();
    }

    @SystemApi
    public String toSafeString() {
        String string2;
        CharSequence charSequence;
        CharSequence charSequence2;
        block8 : {
            block9 : {
                block10 : {
                    string2 = this.getScheme();
                    charSequence2 = charSequence = this.getSchemeSpecificPart();
                    if (string2 == null) break block8;
                    if (string2.equalsIgnoreCase("tel") || string2.equalsIgnoreCase("sip") || string2.equalsIgnoreCase("sms") || string2.equalsIgnoreCase("smsto") || string2.equalsIgnoreCase("mailto") || string2.equalsIgnoreCase("nfc")) break block9;
                    if (string2.equalsIgnoreCase("http") || string2.equalsIgnoreCase("https") || string2.equalsIgnoreCase("ftp")) break block10;
                    charSequence2 = charSequence;
                    if (!string2.equalsIgnoreCase("rtsp")) break block8;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("//");
                charSequence2 = this.getHost();
                charSequence = "";
                charSequence2 = charSequence2 != null ? this.getHost() : "";
                stringBuilder.append((String)charSequence2);
                charSequence2 = charSequence;
                if (this.getPort() != -1) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append(":");
                    ((StringBuilder)charSequence2).append(this.getPort());
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
                stringBuilder.append((String)charSequence2);
                stringBuilder.append("/...");
                charSequence2 = stringBuilder.toString();
                break block8;
            }
            charSequence2 = new StringBuilder(64);
            ((StringBuilder)charSequence2).append(string2);
            ((StringBuilder)charSequence2).append(':');
            if (charSequence != null) {
                for (int i = 0; i < ((String)charSequence).length(); ++i) {
                    char c = ((String)charSequence).charAt(i);
                    if (c != '-' && c != '@' && c != '.') {
                        ((StringBuilder)charSequence2).append('x');
                        continue;
                    }
                    ((StringBuilder)charSequence2).append(c);
                }
            }
            return ((StringBuilder)charSequence2).toString();
        }
        charSequence = new StringBuilder(64);
        if (string2 != null) {
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(':');
        }
        if (charSequence2 != null) {
            ((StringBuilder)charSequence).append((String)charSequence2);
        }
        return ((StringBuilder)charSequence).toString();
    }

    public abstract String toString();

    private static abstract class AbstractHierarchicalUri
    extends Uri {
        private volatile String host = Uri.access$300();
        private volatile int port = -2;
        private Part userInfo;

        private AbstractHierarchicalUri() {
        }

        private int findPortSeparator(String string2) {
            if (string2 == null) {
                return -1;
            }
            for (int i = string2.length() - 1; i >= 0; --i) {
                char c = string2.charAt(i);
                if (':' == c) {
                    return i;
                }
                if (c >= '0' && c <= '9') {
                    continue;
                }
                return -1;
            }
            return -1;
        }

        private Part getUserInfoPart() {
            Part part;
            block0 : {
                part = this.userInfo;
                if (part != null) break block0;
                this.userInfo = part = Part.fromEncoded(this.parseUserInfo());
            }
            return part;
        }

        private String parseHost() {
            String string2 = this.getEncodedAuthority();
            if (string2 == null) {
                return null;
            }
            int n = string2.lastIndexOf(64);
            int n2 = this.findPortSeparator(string2);
            string2 = n2 == -1 ? string2.substring(n + 1) : string2.substring(n + 1, n2);
            return AbstractHierarchicalUri.decode(string2);
        }

        private int parsePort() {
            String string2 = this.getEncodedAuthority();
            int n = this.findPortSeparator(string2);
            if (n == -1) {
                return -1;
            }
            string2 = AbstractHierarchicalUri.decode(string2.substring(n + 1));
            try {
                n = Integer.parseInt(string2);
                return n;
            }
            catch (NumberFormatException numberFormatException) {
                Log.w(LOG, "Error parsing port string.", numberFormatException);
                return -1;
            }
        }

        private String parseUserInfo() {
            String string2 = this.getEncodedAuthority();
            String string3 = null;
            if (string2 == null) {
                return null;
            }
            int n = string2.lastIndexOf(64);
            if (n != -1) {
                string3 = string2.substring(0, n);
            }
            return string3;
        }

        @Override
        public final String getEncodedUserInfo() {
            return this.getUserInfoPart().getEncoded();
        }

        @Override
        public String getHost() {
            String string2;
            boolean bl = this.host != NOT_CACHED;
            if (bl) {
                string2 = this.host;
            } else {
                this.host = string2 = this.parseHost();
            }
            return string2;
        }

        @Override
        public String getLastPathSegment() {
            List<String> list = this.getPathSegments();
            int n = list.size();
            if (n == 0) {
                return null;
            }
            return list.get(n - 1);
        }

        @Override
        public int getPort() {
            int n;
            if (this.port == -2) {
                this.port = n = this.parsePort();
            } else {
                n = this.port;
            }
            return n;
        }

        @Override
        public String getUserInfo() {
            return this.getUserInfoPart().getDecoded();
        }
    }

    static abstract class AbstractPart {
        volatile String decoded;
        volatile String encoded;

        AbstractPart(String string2, String string3) {
            this.encoded = string2;
            this.decoded = string3;
        }

        final String getDecoded() {
            String string2;
            boolean bl = this.decoded != NOT_CACHED;
            if (bl) {
                string2 = this.decoded;
            } else {
                this.decoded = string2 = Uri.decode(this.encoded);
            }
            return string2;
        }

        abstract String getEncoded();

        final void writeTo(Parcel parcel) {
            block5 : {
                block3 : {
                    boolean bl;
                    block4 : {
                        boolean bl2;
                        block2 : {
                            bl2 = this.encoded != NOT_CACHED;
                            bl = this.decoded != NOT_CACHED;
                            if (!bl2 || !bl) break block2;
                            parcel.writeInt(0);
                            parcel.writeString(this.encoded);
                            parcel.writeString(this.decoded);
                            break block3;
                        }
                        if (!bl2) break block4;
                        parcel.writeInt(1);
                        parcel.writeString(this.encoded);
                        break block3;
                    }
                    if (!bl) break block5;
                    parcel.writeInt(2);
                    parcel.writeString(this.decoded);
                }
                return;
            }
            throw new IllegalArgumentException("Neither encoded nor decoded");
        }

        static class Representation {
            static final int BOTH = 0;
            static final int DECODED = 2;
            static final int ENCODED = 1;

            Representation() {
            }
        }

    }

    public static final class Builder {
        private Part authority;
        private Part fragment;
        private Part opaquePart;
        private PathPart path;
        private Part query;
        private String scheme;

        private boolean hasSchemeOrAuthority() {
            Part part;
            boolean bl = this.scheme != null || (part = this.authority) != null && part != Part.NULL;
            return bl;
        }

        public Builder appendEncodedPath(String string2) {
            return this.path(PathPart.appendEncodedSegment(this.path, string2));
        }

        public Builder appendPath(String string2) {
            return this.path(PathPart.appendDecodedSegment(this.path, string2));
        }

        public Builder appendQueryParameter(String string2, String object) {
            this.opaquePart = null;
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(Uri.encode(string2, null));
            ((StringBuilder)charSequence).append("=");
            ((StringBuilder)charSequence).append(Uri.encode((String)object, null));
            string2 = ((StringBuilder)charSequence).toString();
            object = this.query;
            if (object == null) {
                this.query = Part.fromEncoded(string2);
                return this;
            }
            charSequence = ((Part)object).getEncoded();
            if (charSequence != null && ((String)charSequence).length() != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append("&");
                ((StringBuilder)object).append(string2);
                this.query = Part.fromEncoded(((StringBuilder)object).toString());
            } else {
                this.query = Part.fromEncoded(string2);
            }
            return this;
        }

        Builder authority(Part part) {
            this.opaquePart = null;
            this.authority = part;
            return this;
        }

        public Builder authority(String string2) {
            return this.authority(Part.fromDecoded(string2));
        }

        public Uri build() {
            AbstractPart abstractPart = this.opaquePart;
            if (abstractPart != null) {
                String string2 = this.scheme;
                if (string2 != null) {
                    return new OpaqueUri(string2, (Part)abstractPart, this.fragment);
                }
                throw new UnsupportedOperationException("An opaque URI must have a scheme.");
            }
            PathPart pathPart = this.path;
            if (pathPart != null && pathPart != PathPart.NULL) {
                abstractPart = pathPart;
                if (this.hasSchemeOrAuthority()) {
                    abstractPart = PathPart.makeAbsolute(pathPart);
                }
            } else {
                abstractPart = PathPart.EMPTY;
            }
            return new HierarchicalUri(this.scheme, this.authority, (PathPart)abstractPart, this.query, this.fragment);
        }

        public Builder clearQuery() {
            return this.query((Part)null);
        }

        public Builder encodedAuthority(String string2) {
            return this.authority(Part.fromEncoded(string2));
        }

        public Builder encodedFragment(String string2) {
            return this.fragment(Part.fromEncoded(string2));
        }

        public Builder encodedOpaquePart(String string2) {
            return this.opaquePart(Part.fromEncoded(string2));
        }

        public Builder encodedPath(String string2) {
            return this.path(PathPart.fromEncoded(string2));
        }

        public Builder encodedQuery(String string2) {
            return this.query(Part.fromEncoded(string2));
        }

        Builder fragment(Part part) {
            this.fragment = part;
            return this;
        }

        public Builder fragment(String string2) {
            return this.fragment(Part.fromDecoded(string2));
        }

        Builder opaquePart(Part part) {
            this.opaquePart = part;
            return this;
        }

        public Builder opaquePart(String string2) {
            return this.opaquePart(Part.fromDecoded(string2));
        }

        Builder path(PathPart pathPart) {
            this.opaquePart = null;
            this.path = pathPart;
            return this;
        }

        public Builder path(String string2) {
            return this.path(PathPart.fromDecoded(string2));
        }

        Builder query(Part part) {
            this.opaquePart = null;
            this.query = part;
            return this;
        }

        public Builder query(String string2) {
            return this.query(Part.fromDecoded(string2));
        }

        public Builder scheme(String string2) {
            this.scheme = string2;
            return this;
        }

        public String toString() {
            return this.build().toString();
        }
    }

    private static class HierarchicalUri
    extends AbstractHierarchicalUri {
        static final int TYPE_ID = 3;
        private final Part authority;
        private final Part fragment;
        private final PathPart path;
        private final Part query;
        private final String scheme;
        private Part ssp;
        private volatile String uriString = Uri.access$300();

        private HierarchicalUri(String object, Part part, PathPart pathPart, Part part2, Part part3) {
            this.scheme = object;
            this.authority = Part.nonNull(part);
            object = pathPart == null ? PathPart.NULL : pathPart;
            this.path = object;
            this.query = Part.nonNull(part2);
            this.fragment = Part.nonNull(part3);
        }

        private void appendSspTo(StringBuilder stringBuilder) {
            String string2 = this.authority.getEncoded();
            if (string2 != null) {
                stringBuilder.append("//");
                stringBuilder.append(string2);
            }
            if ((string2 = this.path.getEncoded()) != null) {
                stringBuilder.append(string2);
            }
            if (!this.query.isEmpty()) {
                stringBuilder.append('?');
                stringBuilder.append(this.query.getEncoded());
            }
        }

        private Part getSsp() {
            Part part;
            block0 : {
                part = this.ssp;
                if (part != null) break block0;
                this.ssp = part = Part.fromEncoded(this.makeSchemeSpecificPart());
            }
            return part;
        }

        private String makeSchemeSpecificPart() {
            StringBuilder stringBuilder = new StringBuilder();
            this.appendSspTo(stringBuilder);
            return stringBuilder.toString();
        }

        private String makeUriString() {
            StringBuilder stringBuilder = new StringBuilder();
            String string2 = this.scheme;
            if (string2 != null) {
                stringBuilder.append(string2);
                stringBuilder.append(':');
            }
            this.appendSspTo(stringBuilder);
            if (!this.fragment.isEmpty()) {
                stringBuilder.append('#');
                stringBuilder.append(this.fragment.getEncoded());
            }
            return stringBuilder.toString();
        }

        static Uri readFrom(Parcel parcel) {
            return new HierarchicalUri(parcel.readString(), Part.readFrom(parcel), PathPart.readFrom(parcel), Part.readFrom(parcel), Part.readFrom(parcel));
        }

        @Override
        public Builder buildUpon() {
            return new Builder().scheme(this.scheme).authority(this.authority).path(this.path).query(this.query).fragment(this.fragment);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public String getAuthority() {
            return this.authority.getDecoded();
        }

        @Override
        public String getEncodedAuthority() {
            return this.authority.getEncoded();
        }

        @Override
        public String getEncodedFragment() {
            return this.fragment.getEncoded();
        }

        @Override
        public String getEncodedPath() {
            return this.path.getEncoded();
        }

        @Override
        public String getEncodedQuery() {
            return this.query.getEncoded();
        }

        @Override
        public String getEncodedSchemeSpecificPart() {
            return this.getSsp().getEncoded();
        }

        @Override
        public String getFragment() {
            return this.fragment.getDecoded();
        }

        @Override
        public String getPath() {
            return this.path.getDecoded();
        }

        @Override
        public List<String> getPathSegments() {
            return this.path.getPathSegments();
        }

        @Override
        public String getQuery() {
            return this.query.getDecoded();
        }

        @Override
        public String getScheme() {
            return this.scheme;
        }

        @Override
        public String getSchemeSpecificPart() {
            return this.getSsp().getDecoded();
        }

        @Override
        public boolean isHierarchical() {
            return true;
        }

        @Override
        public boolean isRelative() {
            boolean bl = this.scheme == null;
            return bl;
        }

        @Override
        public String toString() {
            String string2;
            boolean bl = this.uriString != NOT_CACHED;
            if (bl) {
                string2 = this.uriString;
            } else {
                this.uriString = string2 = this.makeUriString();
            }
            return string2;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(3);
            parcel.writeString(this.scheme);
            this.authority.writeTo(parcel);
            this.path.writeTo(parcel);
            this.query.writeTo(parcel);
            this.fragment.writeTo(parcel);
        }
    }

    private static class OpaqueUri
    extends Uri {
        static final int TYPE_ID = 2;
        private volatile String cachedString = Uri.access$300();
        private final Part fragment;
        private final String scheme;
        private final Part ssp;

        private OpaqueUri(String string2, Part part, Part part2) {
            this.scheme = string2;
            this.ssp = part;
            if (part2 == null) {
                part2 = Part.NULL;
            }
            this.fragment = part2;
        }

        static Uri readFrom(Parcel parcel) {
            return new OpaqueUri(parcel.readString(), Part.readFrom(parcel), Part.readFrom(parcel));
        }

        @Override
        public Builder buildUpon() {
            return new Builder().scheme(this.scheme).opaquePart(this.ssp).fragment(this.fragment);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public String getAuthority() {
            return null;
        }

        @Override
        public String getEncodedAuthority() {
            return null;
        }

        @Override
        public String getEncodedFragment() {
            return this.fragment.getEncoded();
        }

        @Override
        public String getEncodedPath() {
            return null;
        }

        @Override
        public String getEncodedQuery() {
            return null;
        }

        @Override
        public String getEncodedSchemeSpecificPart() {
            return this.ssp.getEncoded();
        }

        @Override
        public String getEncodedUserInfo() {
            return null;
        }

        @Override
        public String getFragment() {
            return this.fragment.getDecoded();
        }

        @Override
        public String getHost() {
            return null;
        }

        @Override
        public String getLastPathSegment() {
            return null;
        }

        @Override
        public String getPath() {
            return null;
        }

        @Override
        public List<String> getPathSegments() {
            return Collections.emptyList();
        }

        @Override
        public int getPort() {
            return -1;
        }

        @Override
        public String getQuery() {
            return null;
        }

        @Override
        public String getScheme() {
            return this.scheme;
        }

        @Override
        public String getSchemeSpecificPart() {
            return this.ssp.getDecoded();
        }

        @Override
        public String getUserInfo() {
            return null;
        }

        @Override
        public boolean isHierarchical() {
            return false;
        }

        @Override
        public boolean isRelative() {
            boolean bl = this.scheme == null;
            return bl;
        }

        @Override
        public String toString() {
            boolean bl = this.cachedString != NOT_CACHED;
            if (bl) {
                return this.cachedString;
            }
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.scheme);
            ((StringBuilder)charSequence).append(':');
            ((StringBuilder)charSequence).append(this.getEncodedSchemeSpecificPart());
            if (!this.fragment.isEmpty()) {
                ((StringBuilder)charSequence).append('#');
                ((StringBuilder)charSequence).append(this.fragment.getEncoded());
            }
            charSequence = ((StringBuilder)charSequence).toString();
            this.cachedString = charSequence;
            return charSequence;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(2);
            parcel.writeString(this.scheme);
            this.ssp.writeTo(parcel);
            this.fragment.writeTo(parcel);
        }
    }

    static class Part
    extends AbstractPart {
        static final Part EMPTY;
        static final Part NULL;

        static {
            NULL = new EmptyPart(null);
            EMPTY = new EmptyPart("");
        }

        private Part(String string2, String string3) {
            super(string2, string3);
        }

        static Part from(String string2, String string3) {
            if (string2 == null) {
                return NULL;
            }
            if (string2.length() == 0) {
                return EMPTY;
            }
            if (string3 == null) {
                return NULL;
            }
            if (string3.length() == 0) {
                return EMPTY;
            }
            return new Part(string2, string3);
        }

        static Part fromDecoded(String string2) {
            return Part.from(NOT_CACHED, string2);
        }

        static Part fromEncoded(String string2) {
            return Part.from(string2, NOT_CACHED);
        }

        static Part nonNull(Part part) {
            block0 : {
                if (part != null) break block0;
                part = NULL;
            }
            return part;
        }

        static Part readFrom(Parcel object) {
            int n = ((Parcel)object).readInt();
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        return Part.fromDecoded(((Parcel)object).readString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown representation: ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                return Part.fromEncoded(((Parcel)object).readString());
            }
            return Part.from(((Parcel)object).readString(), ((Parcel)object).readString());
        }

        @Override
        String getEncoded() {
            String string2;
            boolean bl = this.encoded != NOT_CACHED;
            if (bl) {
                string2 = this.encoded;
            } else {
                this.encoded = string2 = Uri.encode(this.decoded);
            }
            return string2;
        }

        boolean isEmpty() {
            return false;
        }

        private static class EmptyPart
        extends Part {
            public EmptyPart(String string2) {
                super(string2, string2);
            }

            @Override
            boolean isEmpty() {
                return true;
            }
        }

    }

    static class PathPart
    extends AbstractPart {
        static final PathPart EMPTY;
        static final PathPart NULL;
        private PathSegments pathSegments;

        static {
            NULL = new PathPart(null, null);
            EMPTY = new PathPart("", "");
        }

        private PathPart(String string2, String string3) {
            super(string2, string3);
        }

        static PathPart appendDecodedSegment(PathPart pathPart, String string2) {
            return PathPart.appendEncodedSegment(pathPart, Uri.encode(string2));
        }

        static PathPart appendEncodedSegment(PathPart object, String string2) {
            int n;
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(string2);
                return PathPart.fromEncoded(((StringBuilder)object).toString());
            }
            CharSequence charSequence = ((PathPart)object).getEncoded();
            object = charSequence;
            if (charSequence == null) {
                object = "";
            }
            if ((n = ((String)object).length()) == 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(string2);
                object = ((StringBuilder)object).toString();
            } else if (((String)object).charAt(n - 1) == '/') {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append(string2);
                object = ((StringBuilder)charSequence).toString();
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append("/");
                ((StringBuilder)charSequence).append(string2);
                object = ((StringBuilder)charSequence).toString();
            }
            return PathPart.fromEncoded((String)object);
        }

        static PathPart from(String string2, String string3) {
            if (string2 == null) {
                return NULL;
            }
            if (string2.length() == 0) {
                return EMPTY;
            }
            return new PathPart(string2, string3);
        }

        static PathPart fromDecoded(String string2) {
            return PathPart.from(NOT_CACHED, string2);
        }

        static PathPart fromEncoded(String string2) {
            return PathPart.from(string2, NOT_CACHED);
        }

        static PathPart makeAbsolute(PathPart object) {
            CharSequence charSequence = ((PathPart)object).encoded;
            CharSequence charSequence2 = NOT_CACHED;
            boolean bl = true;
            boolean bl2 = charSequence != charSequence2;
            charSequence2 = bl2 ? ((PathPart)object).encoded : ((PathPart)object).decoded;
            if (charSequence2 != null && ((String)charSequence2).length() != 0 && !((String)charSequence2).startsWith("/")) {
                if (bl2) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("/");
                    ((StringBuilder)charSequence2).append(((PathPart)object).encoded);
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                } else {
                    charSequence2 = NOT_CACHED;
                }
                bl2 = ((PathPart)object).decoded != NOT_CACHED ? bl : false;
                if (bl2) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("/");
                    ((StringBuilder)charSequence).append(((PathPart)object).decoded);
                    object = ((StringBuilder)charSequence).toString();
                } else {
                    object = NOT_CACHED;
                }
                return new PathPart((String)charSequence2, (String)object);
            }
            return object;
        }

        static PathPart readFrom(Parcel object) {
            int n = ((Parcel)object).readInt();
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        return PathPart.fromDecoded(((Parcel)object).readString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Bad representation: ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                return PathPart.fromEncoded(((Parcel)object).readString());
            }
            return PathPart.from(((Parcel)object).readString(), ((Parcel)object).readString());
        }

        @Override
        String getEncoded() {
            String string2;
            boolean bl = this.encoded != NOT_CACHED;
            if (bl) {
                string2 = this.encoded;
            } else {
                this.encoded = string2 = Uri.encode(this.decoded, "/");
            }
            return string2;
        }

        PathSegments getPathSegments() {
            int n;
            Object object = this.pathSegments;
            if (object != null) {
                return object;
            }
            String string2 = this.getEncoded();
            if (string2 == null) {
                object = PathSegments.EMPTY;
                this.pathSegments = object;
                return object;
            }
            object = new PathSegmentsBuilder();
            int n2 = 0;
            while ((n = string2.indexOf(47, n2)) > -1) {
                if (n2 < n) {
                    ((PathSegmentsBuilder)object).add(Uri.decode(string2.substring(n2, n)));
                }
                n2 = n + 1;
            }
            if (n2 < string2.length()) {
                ((PathSegmentsBuilder)object).add(Uri.decode(string2.substring(n2)));
            }
            this.pathSegments = object = ((PathSegmentsBuilder)object).build();
            return object;
        }
    }

    static class PathSegments
    extends AbstractList<String>
    implements RandomAccess {
        static final PathSegments EMPTY = new PathSegments(null, 0);
        final String[] segments;
        final int size;

        PathSegments(String[] arrstring, int n) {
            this.segments = arrstring;
            this.size = n;
        }

        @Override
        public String get(int n) {
            if (n < this.size) {
                return this.segments[n];
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int size() {
            return this.size;
        }
    }

    static class PathSegmentsBuilder {
        String[] segments;
        int size = 0;

        PathSegmentsBuilder() {
        }

        void add(String string2) {
            String[] arrstring;
            String[] arrstring2 = this.segments;
            if (arrstring2 == null) {
                this.segments = new String[4];
            } else if (this.size + 1 == arrstring2.length) {
                arrstring = new String[arrstring2.length * 2];
                System.arraycopy(arrstring2, 0, arrstring, 0, arrstring2.length);
                this.segments = arrstring;
            }
            arrstring = this.segments;
            int n = this.size;
            this.size = n + 1;
            arrstring[n] = string2;
        }

        PathSegments build() {
            Object object = this.segments;
            if (object == null) {
                return PathSegments.EMPTY;
            }
            try {
                object = new PathSegments((String[])object, this.size);
                return object;
            }
            finally {
                this.segments = null;
            }
        }
    }

    private static class StringUri
    extends AbstractHierarchicalUri {
        static final int TYPE_ID = 1;
        private Part authority;
        private volatile int cachedFsi = -2;
        private volatile int cachedSsi = -2;
        private Part fragment;
        private PathPart path;
        private Part query;
        private volatile String scheme = Uri.access$300();
        private Part ssp;
        private final String uriString;

        private StringUri(String string2) {
            if (string2 != null) {
                this.uriString = string2;
                return;
            }
            throw new NullPointerException("uriString");
        }

        private int findFragmentSeparator() {
            int n;
            if (this.cachedFsi == -2) {
                this.cachedFsi = n = this.uriString.indexOf(35, this.findSchemeSeparator());
            } else {
                n = this.cachedFsi;
            }
            return n;
        }

        private int findSchemeSeparator() {
            int n;
            if (this.cachedSsi == -2) {
                this.cachedSsi = n = this.uriString.indexOf(58);
            } else {
                n = this.cachedSsi;
            }
            return n;
        }

        private Part getAuthorityPart() {
            Part part = this.authority;
            if (part == null) {
                this.authority = part = Part.fromEncoded(StringUri.parseAuthority(this.uriString, this.findSchemeSeparator()));
                return part;
            }
            return part;
        }

        private Part getFragmentPart() {
            Part part;
            block0 : {
                part = this.fragment;
                if (part != null) break block0;
                this.fragment = part = Part.fromEncoded(this.parseFragment());
            }
            return part;
        }

        private PathPart getPathPart() {
            PathPart pathPart;
            block0 : {
                pathPart = this.path;
                if (pathPart != null) break block0;
                this.path = pathPart = PathPart.fromEncoded(this.parsePath());
            }
            return pathPart;
        }

        private Part getQueryPart() {
            Part part;
            block0 : {
                part = this.query;
                if (part != null) break block0;
                this.query = part = Part.fromEncoded(this.parseQuery());
            }
            return part;
        }

        private Part getSsp() {
            Part part;
            Part part2 = part = this.ssp;
            if (part == null) {
                this.ssp = part2 = Part.fromEncoded(this.parseSsp());
            }
            return part2;
        }

        static String parseAuthority(String string2, int n) {
            int n2 = string2.length();
            if (n2 > n + 2 && string2.charAt(n + 1) == '/' && string2.charAt(n + 2) == '/') {
                char c;
                int n3;
                for (n3 = n + 3; n3 < n2 && (c = string2.charAt(n3)) != '#' && c != '/' && c != '?' && c != '\\'; ++n3) {
                }
                return string2.substring(n + 3, n3);
            }
            return null;
        }

        private String parseFragment() {
            int n = this.findFragmentSeparator();
            String string2 = n == -1 ? null : this.uriString.substring(n + 1);
            return string2;
        }

        private String parsePath() {
            String string2 = this.uriString;
            int n = this.findSchemeSeparator();
            if (n > -1) {
                boolean bl = n + 1 == string2.length();
                if (bl) {
                    return null;
                }
                if (string2.charAt(n + 1) != '/') {
                    return null;
                }
            }
            return StringUri.parsePath(string2, n);
        }

        static String parsePath(String string2, int n) {
            int n2;
            char c;
            int n3;
            block3 : {
                block5 : {
                    block4 : {
                        n2 = string2.length();
                        if (n2 <= n + 2 || string2.charAt(n + 1) != '/' || string2.charAt(n + 2) != '/') break block5;
                        n3 = n + 3;
                        do {
                            n = n3;
                            if (n3 >= n2) break block3;
                            n = string2.charAt(n3);
                            if (n == 35) break block4;
                            if (n == 47) break;
                            if (n != 63) {
                                if (n == 92) break;
                                ++n3;
                                continue;
                            }
                            break block4;
                            break;
                        } while (true);
                        n = n3;
                        break block3;
                    }
                    return "";
                }
                ++n;
            }
            for (n3 = n; n3 < n2 && (c = string2.charAt(n3)) != '#' && c != '?'; ++n3) {
            }
            return string2.substring(n, n3);
        }

        private String parseQuery() {
            int n = this.uriString.indexOf(63, this.findSchemeSeparator());
            if (n == -1) {
                return null;
            }
            int n2 = this.findFragmentSeparator();
            if (n2 == -1) {
                return this.uriString.substring(n + 1);
            }
            if (n2 < n) {
                return null;
            }
            return this.uriString.substring(n + 1, n2);
        }

        private String parseScheme() {
            int n = this.findSchemeSeparator();
            String string2 = n == -1 ? null : this.uriString.substring(0, n);
            return string2;
        }

        private String parseSsp() {
            int n = this.findSchemeSeparator();
            int n2 = this.findFragmentSeparator();
            String string2 = n2 == -1 ? this.uriString.substring(n + 1) : this.uriString.substring(n + 1, n2);
            return string2;
        }

        static Uri readFrom(Parcel parcel) {
            return new StringUri(parcel.readString());
        }

        @Override
        public Builder buildUpon() {
            if (this.isHierarchical()) {
                return new Builder().scheme(this.getScheme()).authority(this.getAuthorityPart()).path(this.getPathPart()).query(this.getQueryPart()).fragment(this.getFragmentPart());
            }
            return new Builder().scheme(this.getScheme()).opaquePart(this.getSsp()).fragment(this.getFragmentPart());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public String getAuthority() {
            return this.getAuthorityPart().getDecoded();
        }

        @Override
        public String getEncodedAuthority() {
            return this.getAuthorityPart().getEncoded();
        }

        @Override
        public String getEncodedFragment() {
            return this.getFragmentPart().getEncoded();
        }

        @Override
        public String getEncodedPath() {
            return this.getPathPart().getEncoded();
        }

        @Override
        public String getEncodedQuery() {
            return this.getQueryPart().getEncoded();
        }

        @Override
        public String getEncodedSchemeSpecificPart() {
            return this.getSsp().getEncoded();
        }

        @Override
        public String getFragment() {
            return this.getFragmentPart().getDecoded();
        }

        @Override
        public String getPath() {
            return this.getPathPart().getDecoded();
        }

        @Override
        public List<String> getPathSegments() {
            return this.getPathPart().getPathSegments();
        }

        @Override
        public String getQuery() {
            return this.getQueryPart().getDecoded();
        }

        @Override
        public String getScheme() {
            String string2;
            boolean bl = this.scheme != NOT_CACHED;
            if (bl) {
                string2 = this.scheme;
            } else {
                this.scheme = string2 = this.parseScheme();
            }
            return string2;
        }

        @Override
        public String getSchemeSpecificPart() {
            return this.getSsp().getDecoded();
        }

        @Override
        public boolean isHierarchical() {
            int n = this.findSchemeSeparator();
            boolean bl = true;
            if (n == -1) {
                return true;
            }
            if (this.uriString.length() == n + 1) {
                return false;
            }
            if (this.uriString.charAt(n + 1) != '/') {
                bl = false;
            }
            return bl;
        }

        @Override
        public boolean isRelative() {
            boolean bl = this.findSchemeSeparator() == -1;
            return bl;
        }

        @Override
        public String toString() {
            return this.uriString;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(1);
            parcel.writeString(this.uriString);
        }
    }

}

