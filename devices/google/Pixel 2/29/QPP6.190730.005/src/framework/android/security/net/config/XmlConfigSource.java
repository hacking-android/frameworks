/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.security.net.config;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.security.net.config.CertificateSource;
import android.security.net.config.CertificatesEntryRef;
import android.security.net.config.ConfigSource;
import android.security.net.config.Domain;
import android.security.net.config.NetworkSecurityConfig;
import android.security.net.config.Pin;
import android.security.net.config.PinSet;
import android.security.net.config.ResourceCertificateSource;
import android.security.net.config.SystemCertificateSource;
import android.security.net.config.UserCertificateSource;
import android.security.net.config.WfaCertificateSource;
import android.util.ArraySet;
import android.util.Base64;
import android.util.Pair;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlConfigSource
implements ConfigSource {
    private static final int CONFIG_BASE = 0;
    private static final int CONFIG_DEBUG = 2;
    private static final int CONFIG_DOMAIN = 1;
    private final ApplicationInfo mApplicationInfo;
    private Context mContext;
    private final boolean mDebugBuild;
    private NetworkSecurityConfig mDefaultConfig;
    private Set<Pair<Domain, NetworkSecurityConfig>> mDomainMap;
    private boolean mInitialized;
    private final Object mLock = new Object();
    private final int mResourceId;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public XmlConfigSource(Context context, int n, ApplicationInfo applicationInfo) {
        this.mContext = context;
        this.mResourceId = n;
        this.mApplicationInfo = new ApplicationInfo(applicationInfo);
        boolean bl = (this.mApplicationInfo.flags & 2) != 0;
        this.mDebugBuild = bl;
    }

    private void addDebugAnchorsIfNeeded(NetworkSecurityConfig.Builder builder, NetworkSecurityConfig.Builder builder2) {
        if (builder != null && builder.hasCertificatesEntryRefs()) {
            if (!builder2.hasCertificatesEntryRefs()) {
                return;
            }
            builder2.addCertificatesEntryRefs(builder.getCertificatesEntryRefs());
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void ensureInitialized() {
        var1_1 = this.mLock;
        // MONITORENTER : var1_1
        if (this.mInitialized) {
            // MONITOREXIT : var1_1
            return;
        }
        var2_2 = this.mContext.getResources().getXml(this.mResourceId);
        try {
            this.parseNetworkSecurityConfig(var2_2);
            this.mContext = null;
            this.mInitialized = true;
            if (var2_2 == null) ** GOTO lbl31
        }
        catch (Throwable var3_4) {
            try {
                throw var3_4;
            }
            catch (Throwable var4_6) {
                if (var2_2 == null) throw var4_6;
                try {
                    XmlConfigSource.$closeResource(var3_4, var2_2);
                    throw var4_6;
                }
                catch (Resources.NotFoundException | ParserException | IOException | XmlPullParserException var2_3) {
                    var3_5 = new StringBuilder();
                    var3_5.append("Failed to parse XML configuration from ");
                    var3_5.append(this.mContext.getResources().getResourceEntryName(this.mResourceId));
                    var4_7 = new RuntimeException(var3_5.toString(), var2_3);
                    throw var4_7;
                }
            }
        }
        XmlConfigSource.$closeResource(null, var2_2);
lbl31: // 2 sources:
        // MONITOREXIT : var1_1
    }

    private static final String getConfigString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    return "debug-overrides";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown config type: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return "domain-config";
        }
        return "base-config";
    }

    private CertificatesEntryRef parseCertificatesEntry(XmlResourceParser xmlResourceParser, boolean bl) throws IOException, XmlPullParserException, ParserException {
        block2 : {
            block7 : {
                Object object;
                block4 : {
                    block6 : {
                        block5 : {
                            block3 : {
                                bl = xmlResourceParser.getAttributeBooleanValue(null, "overridePins", bl);
                                int n = xmlResourceParser.getAttributeResourceValue(null, "src", -1);
                                object = xmlResourceParser.getAttributeValue(null, "src");
                                if (object == null) break block2;
                                if (n == -1) break block3;
                                object = new ResourceCertificateSource(n, this.mContext);
                                break block4;
                            }
                            if (!"system".equals(object)) break block5;
                            object = SystemCertificateSource.getInstance();
                            break block4;
                        }
                        if (!"user".equals(object)) break block6;
                        object = UserCertificateSource.getInstance();
                        break block4;
                    }
                    if (!"wfa".equals(object)) break block7;
                    object = WfaCertificateSource.getInstance();
                }
                XmlUtils.skipCurrentTag(xmlResourceParser);
                return new CertificatesEntryRef((CertificateSource)object, bl);
            }
            throw new ParserException(xmlResourceParser, "Unknown certificates src. Should be one of system|user|@resourceVal");
        }
        throw new ParserException(xmlResourceParser, "certificates element missing src attribute");
    }

    private List<Pair<NetworkSecurityConfig.Builder, Set<Domain>>> parseConfigEntry(XmlResourceParser xmlResourceParser, Set<String> object, NetworkSecurityConfig.Builder object2, int n) throws IOException, XmlPullParserException, ParserException {
        boolean bl;
        boolean bl2;
        ArrayList<Pair<NetworkSecurityConfig.Builder, Set<Domain>>> arrayList = new ArrayList<Pair<NetworkSecurityConfig.Builder, Set<Domain>>>();
        NetworkSecurityConfig.Builder builder = new NetworkSecurityConfig.Builder();
        builder.setParent((NetworkSecurityConfig.Builder)object2);
        ArraySet<Domain> arraySet = new ArraySet<Domain>();
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = n == 2;
        xmlResourceParser.getName();
        int n2 = xmlResourceParser.getDepth();
        arrayList.add(new Pair(builder, arraySet));
        int n3 = 0;
        do {
            bl2 = bl3;
            bl = bl4;
            if (n3 >= xmlResourceParser.getAttributeCount()) break;
            object2 = xmlResourceParser.getAttributeName(n3);
            if ("hstsEnforced".equals(object2)) {
                builder.setHstsEnforced(xmlResourceParser.getAttributeBooleanValue(n3, false));
            } else if ("cleartextTrafficPermitted".equals(object2)) {
                builder.setCleartextTrafficPermitted(xmlResourceParser.getAttributeBooleanValue(n3, true));
            }
            ++n3;
        } while (true);
        do {
            object2 = this;
            if (!XmlUtils.nextElementWithin(xmlResourceParser, n2)) break;
            String string2 = xmlResourceParser.getName();
            if ("domain".equals(string2)) {
                if (n == 1) {
                    arraySet.add(this.parseDomain(xmlResourceParser, (Set<String>)object));
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("domain element not allowed in ");
                ((StringBuilder)object).append(XmlConfigSource.getConfigString(n));
                throw new ParserException(xmlResourceParser, ((StringBuilder)object).toString());
            }
            if ("trust-anchors".equals(string2)) {
                if (!bl) {
                    builder.addCertificatesEntryRefs(XmlConfigSource.super.parseTrustAnchors(xmlResourceParser, bl5));
                    bl = true;
                    continue;
                }
                throw new ParserException(xmlResourceParser, "Multiple trust-anchor elements not allowed");
            }
            if ("pin-set".equals(string2)) {
                if (n == 1) {
                    if (!bl2) {
                        builder.setPinSet(this.parsePinSet(xmlResourceParser));
                        bl2 = true;
                        continue;
                    }
                    throw new ParserException(xmlResourceParser, "Multiple pin-set elements not allowed");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("pin-set element not allowed in ");
                ((StringBuilder)object).append(XmlConfigSource.getConfigString(n));
                throw new ParserException(xmlResourceParser, ((StringBuilder)object).toString());
            }
            if ("domain-config".equals(string2)) {
                if (n == 1) {
                    arrayList.addAll(XmlConfigSource.super.parseConfigEntry(xmlResourceParser, (Set<String>)object, builder, n));
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Nested domain-config not allowed in ");
                ((StringBuilder)object).append(XmlConfigSource.getConfigString(n));
                throw new ParserException(xmlResourceParser, ((StringBuilder)object).toString());
            }
            XmlUtils.skipCurrentTag(xmlResourceParser);
        } while (true);
        if (n == 1 && arraySet.isEmpty()) {
            throw new ParserException(xmlResourceParser, "No domain elements in domain-config");
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private NetworkSecurityConfig.Builder parseDebugOverridesResource() throws IOException, XmlPullParserException, ParserException {
        Object object = this.mContext.getResources();
        String string2 = ((Resources)object).getResourcePackageName(this.mResourceId);
        String string3 = ((Resources)object).getResourceEntryName(this.mResourceId);
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append(string3);
        ((StringBuilder)object2).append("_debug");
        int n = ((Resources)object).getIdentifier(((StringBuilder)object2).toString(), "xml", string2);
        if (n == 0) {
            return null;
        }
        object2 = null;
        object = ((Resources)object).getXml(n);
        try {
            XmlUtils.beginDocument((XmlPullParser)object, "network-security-config");
            int n2 = object.getDepth();
            n = 0;
            while (XmlUtils.nextElementWithin((XmlPullParser)object, n2)) {
                if ("debug-overrides".equals(object.getName())) {
                    if (n != 0) {
                        object2 = new ParserException((XmlPullParser)object, "Only one debug-overrides allowed");
                        throw object2;
                    }
                    if (this.mDebugBuild) {
                        object2 = (NetworkSecurityConfig.Builder)this.parseConfigEntry((XmlResourceParser)object, null, null, (int)2).get((int)0).first;
                    } else {
                        XmlUtils.skipCurrentTag((XmlPullParser)object);
                    }
                    n = 1;
                    continue;
                }
                XmlUtils.skipCurrentTag((XmlPullParser)object);
            }
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (object != null) {
                    XmlConfigSource.$closeResource(throwable, (AutoCloseable)object);
                }
                throw throwable2;
            }
        }
        XmlConfigSource.$closeResource(null, (AutoCloseable)object);
        return object2;
    }

    private Domain parseDomain(XmlResourceParser xmlResourceParser, Set<String> object) throws IOException, XmlPullParserException, ParserException {
        boolean bl = xmlResourceParser.getAttributeBooleanValue(null, "includeSubdomains", false);
        if (xmlResourceParser.next() == 4) {
            String string2 = xmlResourceParser.getText().trim().toLowerCase(Locale.US);
            if (xmlResourceParser.next() == 3) {
                if (object.add((String)string2)) {
                    return new Domain(string2, bl);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" has already been specified");
                throw new ParserException(xmlResourceParser, ((StringBuilder)object).toString());
            }
            throw new ParserException(xmlResourceParser, "domain contains additional elements");
        }
        throw new ParserException(xmlResourceParser, "Domain name missing");
    }

    private void parseNetworkSecurityConfig(XmlResourceParser object) throws IOException, XmlPullParserException, ParserException {
        Object object2 = new ArraySet<String>();
        Object object3 = new ArrayList<Pair<NetworkSecurityConfig.Builder, Set<Domain>>>();
        NetworkSecurityConfig.Builder builder = null;
        Object object4 = null;
        boolean bl = false;
        boolean bl2 = false;
        XmlUtils.beginDocument((XmlPullParser)object, "network-security-config");
        int n = object.getDepth();
        while (XmlUtils.nextElementWithin((XmlPullParser)object, n)) {
            if ("base-config".equals(object.getName())) {
                if (!bl2) {
                    bl2 = true;
                    builder = (NetworkSecurityConfig.Builder)this.parseConfigEntry((XmlResourceParser)object, object2, null, (int)0).get((int)0).first;
                    continue;
                }
                throw new ParserException((XmlPullParser)object, "Only one base-config allowed");
            }
            if ("domain-config".equals(object.getName())) {
                object3.addAll(this.parseConfigEntry((XmlResourceParser)object, (Set<String>)object2, builder, 1));
                continue;
            }
            if ("debug-overrides".equals(object.getName())) {
                if (!bl) {
                    if (this.mDebugBuild) {
                        object4 = (NetworkSecurityConfig.Builder)this.parseConfigEntry((XmlResourceParser)object, null, null, (int)2).get((int)0).first;
                    } else {
                        XmlUtils.skipCurrentTag((XmlPullParser)object);
                    }
                    bl = true;
                    continue;
                }
                throw new ParserException((XmlPullParser)object, "Only one debug-overrides allowed");
            }
            XmlUtils.skipCurrentTag((XmlPullParser)object);
        }
        object = object4;
        if (this.mDebugBuild) {
            object = object4;
            if (object4 == null) {
                object = this.parseDebugOverridesResource();
            }
        }
        object4 = NetworkSecurityConfig.getDefaultBuilder(this.mApplicationInfo);
        this.addDebugAnchorsIfNeeded((NetworkSecurityConfig.Builder)object, (NetworkSecurityConfig.Builder)object4);
        if (builder != null) {
            builder.setParent((NetworkSecurityConfig.Builder)object4);
            this.addDebugAnchorsIfNeeded((NetworkSecurityConfig.Builder)object, builder);
        } else {
            builder = object4;
        }
        ArraySet<Pair<Domain, NetworkSecurityConfig>> arraySet = new ArraySet<Pair<Domain, NetworkSecurityConfig>>();
        object3 = object3.iterator();
        object4 = object2;
        while (object3.hasNext()) {
            Iterator iterator = (Pair)object3.next();
            object2 = (NetworkSecurityConfig.Builder)((Pair)iterator).first;
            iterator = (Set)((Pair)iterator).second;
            if (((NetworkSecurityConfig.Builder)object2).getParent() == null) {
                ((NetworkSecurityConfig.Builder)object2).setParent(builder);
            }
            this.addDebugAnchorsIfNeeded((NetworkSecurityConfig.Builder)object, (NetworkSecurityConfig.Builder)object2);
            object2 = ((NetworkSecurityConfig.Builder)object2).build();
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                arraySet.add(new Pair<Domain, Object>((Domain)iterator.next(), object2));
            }
        }
        this.mDefaultConfig = builder.build();
        this.mDomainMap = arraySet;
    }

    private Pin parsePin(XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException, ParserException {
        String string2 = xmlResourceParser.getAttributeValue(null, "digest");
        if (Pin.isSupportedDigestAlgorithm(string2)) {
            if (xmlResourceParser.next() == 4) {
                byte[] arrby;
                CharSequence charSequence = xmlResourceParser.getText().trim();
                try {
                    arrby = Base64.decode((String)charSequence, 0);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new ParserException(xmlResourceParser, "Invalid pin digest", illegalArgumentException);
                }
                int n = Pin.getDigestLength(string2);
                if (arrby.length == n) {
                    if (xmlResourceParser.next() == 3) {
                        return new Pin(string2, arrby);
                    }
                    throw new ParserException(xmlResourceParser, "pin contains additional elements");
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("digest length ");
                ((StringBuilder)charSequence).append(arrby.length);
                ((StringBuilder)charSequence).append(" does not match expected length for ");
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(" of ");
                ((StringBuilder)charSequence).append(n);
                throw new ParserException(xmlResourceParser, ((StringBuilder)charSequence).toString());
            }
            throw new ParserException(xmlResourceParser, "Missing pin digest");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported pin digest algorithm: ");
        stringBuilder.append(string2);
        throw new ParserException(xmlResourceParser, stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private PinSet parsePinSet(XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException, ParserException {
        Object object;
        String string2 = xmlResourceParser.getAttributeValue(null, "expiration");
        long l = Long.MAX_VALUE;
        if (string2 != null) {
            try {
                object = new SimpleDateFormat("yyyy-MM-dd");
                ((DateFormat)object).setLenient(false);
                object = ((DateFormat)object).parse(string2);
                if (object == null) {
                    object = new ParserException(xmlResourceParser, "Invalid expiration date in pin-set");
                    throw object;
                }
                l = ((Date)object).getTime();
            }
            catch (ParseException parseException) {
                throw new ParserException(xmlResourceParser, "Invalid expiration date in pin-set", parseException);
            }
        }
        int n = xmlResourceParser.getDepth();
        object = new ArraySet();
        while (XmlUtils.nextElementWithin(xmlResourceParser, n)) {
            if (xmlResourceParser.getName().equals("pin")) {
                object.add(this.parsePin(xmlResourceParser));
                continue;
            }
            XmlUtils.skipCurrentTag(xmlResourceParser);
        }
        return new PinSet((Set<Pin>)object, l);
    }

    private Collection<CertificatesEntryRef> parseTrustAnchors(XmlResourceParser xmlResourceParser, boolean bl) throws IOException, XmlPullParserException, ParserException {
        int n = xmlResourceParser.getDepth();
        ArrayList<CertificatesEntryRef> arrayList = new ArrayList<CertificatesEntryRef>();
        while (XmlUtils.nextElementWithin(xmlResourceParser, n)) {
            if (xmlResourceParser.getName().equals("certificates")) {
                arrayList.add(this.parseCertificatesEntry(xmlResourceParser, bl));
                continue;
            }
            XmlUtils.skipCurrentTag(xmlResourceParser);
        }
        return arrayList;
    }

    @Override
    public NetworkSecurityConfig getDefaultConfig() {
        this.ensureInitialized();
        return this.mDefaultConfig;
    }

    @Override
    public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
        this.ensureInitialized();
        return this.mDomainMap;
    }

    public static class ParserException
    extends Exception {
        public ParserException(XmlPullParser xmlPullParser, String string2) {
            this(xmlPullParser, string2, null);
        }

        public ParserException(XmlPullParser xmlPullParser, String string2, Throwable throwable) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" at: ");
            stringBuilder.append(xmlPullParser.getPositionDescription());
            super(stringBuilder.toString(), throwable);
        }
    }

}

