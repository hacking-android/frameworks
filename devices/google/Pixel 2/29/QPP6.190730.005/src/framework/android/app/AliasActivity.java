/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AliasActivity
extends Activity {
    public final String ALIAS_META_DATA;

    public AliasActivity() {
        this.ALIAS_META_DATA = "android.app.alias";
    }

    private Intent parseAlias(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n;
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
        CharSequence charSequence = null;
        while ((n = xmlPullParser.next()) != 1 && n != 2) {
        }
        Object object = xmlPullParser.getName();
        if ("alias".equals(object)) {
            int n2;
            n = xmlPullParser.getDepth();
            while ((n2 = xmlPullParser.next()) != 1 && (n2 != 3 || xmlPullParser.getDepth() > n)) {
                if (n2 == 3 || n2 == 4) continue;
                if ("intent".equals(xmlPullParser.getName())) {
                    Intent intent = Intent.parseIntent(this.getResources(), xmlPullParser, attributeSet);
                    object = charSequence;
                    if (charSequence == null) {
                        object = intent;
                    }
                    charSequence = object;
                    continue;
                }
                XmlUtils.skipCurrentTag(xmlPullParser);
            }
            return charSequence;
        }
        charSequence = new StringBuilder();
        charSequence.append("Alias meta-data must start with <alias> tag; found");
        charSequence.append((String)object);
        charSequence.append(" at ");
        charSequence.append(xmlPullParser.getPositionDescription());
        throw new RuntimeException(charSequence.toString());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void onCreate(Bundle var1_1) {
        super.onCreate((Bundle)var1_1);
        var2_2 = null;
        var3_5 = null;
        var4_6 = null;
        var1_1 = null;
        try {
            var5_7 = this.getPackageManager().getActivityInfo(this.getComponentName(), 128).loadXmlMetaData(this.getPackageManager(), "android.app.alias");
            if (var5_7 == null) ** GOTO lbl42
            var1_1 = var5_7;
            var2_2 = var5_7;
            var3_5 = var5_7;
            var4_6 = var5_7;
            var6_10 = this.parseAlias((XmlPullParser)var5_7);
            if (var6_10 != null) {
                var1_1 = var5_7;
                var2_2 = var5_7;
                var3_5 = var5_7;
                var4_6 = var5_7;
                this.startActivity((Intent)var6_10);
                var1_1 = var5_7;
                var2_2 = var5_7;
                var3_5 = var5_7;
                var4_6 = var5_7;
                this.finish();
                var5_7.close();
                return;
            }
            var1_1 = var5_7;
            var2_2 = var5_7;
            var3_5 = var5_7;
            var4_6 = var5_7;
            var1_1 = var5_7;
            var2_2 = var5_7;
            var3_5 = var5_7;
            var4_6 = var5_7;
            var6_10 = new RuntimeException("No <intent> tag found in alias description");
            var1_1 = var5_7;
            var2_2 = var5_7;
            var3_5 = var5_7;
            var4_6 = var5_7;
            throw var6_10;
lbl42: // 1 sources:
            var1_1 = var5_7;
            var2_2 = var5_7;
            var3_5 = var5_7;
            var4_6 = var5_7;
            var1_1 = var5_7;
            var2_2 = var5_7;
            var3_5 = var5_7;
            var4_6 = var5_7;
            var6_11 = new RuntimeException("Alias requires a meta-data field android.app.alias");
            var1_1 = var5_7;
            var2_2 = var5_7;
            var3_5 = var5_7;
            var4_6 = var5_7;
            throw var6_11;
        }
        catch (Throwable var5_8) {
        }
        catch (IOException var5_9) {
            var1_1 = var2_2;
            var1_1 = var2_2;
            var3_5 = new RuntimeException("Error parsing alias", var5_9);
            var1_1 = var2_2;
            throw var3_5;
        }
        catch (XmlPullParserException var2_3) {
            var1_1 = var3_5;
            var1_1 = var3_5;
            var5_7 = new RuntimeException("Error parsing alias", var2_3);
            var1_1 = var3_5;
            throw var5_7;
        }
        catch (PackageManager.NameNotFoundException var2_4) {
            var1_1 = var4_6;
            var1_1 = var4_6;
            var5_7 = new RuntimeException("Error parsing alias", var2_4);
            var1_1 = var4_6;
            throw var5_7;
        }
        if (var1_1 == null) throw var5_8;
        var1_1.close();
        throw var5_8;
    }
}

