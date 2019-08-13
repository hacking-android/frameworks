/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.MimeUtils
 */
package com.android.internal.util;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.android.internal.annotations.GuardedBy;
import java.util.Locale;
import java.util.Objects;
import libcore.net.MimeUtils;

public class MimeIconUtils {
    @GuardedBy(value={"sCache"})
    private static final ArrayMap<String, ContentResolver.MimeTypeInfo> sCache = new ArrayMap();

    private static ContentResolver.MimeTypeInfo buildGenericTypeInfo(String string2) {
        if (string2.startsWith("audio/")) {
            return MimeIconUtils.buildTypeInfo(string2, 17302381, 17040414, 17040415);
        }
        if (string2.startsWith("video/")) {
            return MimeIconUtils.buildTypeInfo(string2, 17302398, 17040429, 17040430);
        }
        if (string2.startsWith("image/")) {
            return MimeIconUtils.buildTypeInfo(string2, 17302392, 17040423, 17040424);
        }
        if (string2.startsWith("text/")) {
            return MimeIconUtils.buildTypeInfo(string2, 17302397, 17040418, 17040419);
        }
        String string3 = MimeUtils.guessMimeTypeFromExtension((String)MimeUtils.guessExtensionFromMimeType((String)string2));
        if (string3 != null && !Objects.equals(string2, string3)) {
            return MimeIconUtils.buildTypeInfo(string3);
        }
        return MimeIconUtils.buildTypeInfo(string2, 17302391, 17040421, 17040422);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static ContentResolver.MimeTypeInfo buildTypeInfo(String var0) {
        block133 : {
            switch (var0.hashCode()) {
                case 2132236175: {
                    if (!var0.equals("text/javascript")) break;
                    var1_1 = 39;
                    break block133;
                }
                case 2062095256: {
                    if (!var0.equals("text/x-c++src")) break;
                    var1_1 = 21;
                    break block133;
                }
                case 2062084266: {
                    if (!var0.equals("text/x-c++hdr")) break;
                    var1_1 = 20;
                    break block133;
                }
                case 2041423923: {
                    if (!var0.equals("application/x-x509-user-cert")) break;
                    var1_1 = 9;
                    break block133;
                }
                case 1993842850: {
                    if (!var0.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) break;
                    var1_1 = 106;
                    break block133;
                }
                case 1969663169: {
                    if (!var0.equals("application/rdf+xml")) break;
                    var1_1 = 13;
                    break block133;
                }
                case 1948418893: {
                    if (!var0.equals("application/mac-binhex40")) break;
                    var1_1 = 41;
                    break block133;
                }
                case 1868769095: {
                    if (!var0.equals("application/x-quicktimeplayer")) break;
                    var1_1 = 100;
                    break block133;
                }
                case 1851895234: {
                    if (!var0.equals("application/x-webarchive")) break;
                    var1_1 = 53;
                    break block133;
                }
                case 1709755138: {
                    if (!var0.equals("application/x-font-woff")) break;
                    var1_1 = 65;
                    break block133;
                }
                case 1673742401: {
                    if (!var0.equals("application/vnd.stardivision.writer")) break;
                    var1_1 = 92;
                    break block133;
                }
                case 1643664935: {
                    if (!var0.equals("application/vnd.oasis.opendocument.spreadsheet")) break;
                    var1_1 = 81;
                    break block133;
                }
                case 1637222218: {
                    if (!var0.equals("application/x-kspread")) break;
                    var1_1 = 86;
                    break block133;
                }
                case 1577426419: {
                    if (!var0.equals("application/vnd.openxmlformats-officedocument.presentationml.slideshow")) break;
                    var1_1 = 111;
                    break block133;
                }
                case 1573656544: {
                    if (!var0.equals("application/x-pkcs12")) break;
                    var1_1 = 5;
                    break block133;
                }
                case 1536912403: {
                    if (!var0.equals("application/x-object")) break;
                    var1_1 = 15;
                    break block133;
                }
                case 1502452311: {
                    if (!var0.equals("application/font-woff")) break;
                    var1_1 = 64;
                    break block133;
                }
                case 1461751133: {
                    if (!var0.equals("application/vnd.oasis.opendocument.text-master")) break;
                    var1_1 = 89;
                    break block133;
                }
                case 1454024983: {
                    if (!var0.equals("application/x-7z-compressed")) break;
                    var1_1 = 56;
                    break block133;
                }
                case 1440428940: {
                    if (!var0.equals("application/javascript")) break;
                    var1_1 = 37;
                    break block133;
                }
                case 1436962847: {
                    if (!var0.equals("application/vnd.oasis.opendocument.presentation")) break;
                    var1_1 = 79;
                    break block133;
                }
                case 1432260414: {
                    if (!var0.equals("application/x-latex")) break;
                    var1_1 = 32;
                    break block133;
                }
                case 1431987873: {
                    if (!var0.equals("application/x-kword")) break;
                    var1_1 = 98;
                    break block133;
                }
                case 1383977381: {
                    if (!var0.equals("application/vnd.sun.xml.impress")) break;
                    var1_1 = 76;
                    break block133;
                }
                case 1377360791: {
                    if (!var0.equals("application/vnd.oasis.opendocument.graphics-template")) break;
                    var1_1 = 68;
                    break block133;
                }
                case 1305955842: {
                    if (!var0.equals("application/x-debian-package")) break;
                    var1_1 = 45;
                    break block133;
                }
                case 1283455191: {
                    if (!var0.equals("application/x-apple-diskimage")) break;
                    var1_1 = 44;
                    break block133;
                }
                case 1255211837: {
                    if (!var0.equals("text/x-haskell")) break;
                    var1_1 = 26;
                    break block133;
                }
                case 1239557416: {
                    if (!var0.equals("application/x-pkcs7-crl")) break;
                    var1_1 = 7;
                    break block133;
                }
                case 1154449330: {
                    if (!var0.equals("application/x-gtar")) break;
                    var1_1 = 46;
                    break block133;
                }
                case 1154415139: {
                    if (!var0.equals("application/x-font")) break;
                    var1_1 = 63;
                    break block133;
                }
                case 1060806194: {
                    if (!var0.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.template")) break;
                    var1_1 = 104;
                    break block133;
                }
                case 1043583697: {
                    if (!var0.equals("application/x-pkcs7-certificates")) break;
                    var1_1 = 10;
                    break block133;
                }
                case 904647503: {
                    if (!var0.equals("application/msword")) break;
                    var1_1 = 102;
                    break block133;
                }
                case 859118878: {
                    if (!var0.equals("application/x-abiword")) break;
                    var1_1 = 97;
                    break block133;
                }
                case 822865392: {
                    if (!var0.equals("text/x-tex")) break;
                    var1_1 = 31;
                    break block133;
                }
                case 822865318: {
                    if (!var0.equals("text/x-tcl")) break;
                    var1_1 = 30;
                    break block133;
                }
                case 822849473: {
                    if (!var0.equals("text/x-csh")) break;
                    var1_1 = 25;
                    break block133;
                }
                case 822609188: {
                    if (!var0.equals("text/vcard")) break;
                    var1_1 = 60;
                    break block133;
                }
                case 717553764: {
                    if (!var0.equals("application/vnd.google-apps.document")) break;
                    var1_1 = 99;
                    break block133;
                }
                case 694663701: {
                    if (!var0.equals("application/vnd.openxmlformats-officedocument.presentationml.template")) break;
                    var1_1 = 110;
                    break block133;
                }
                case 669516689: {
                    if (!var0.equals("application/vnd.stardivision.impress")) break;
                    var1_1 = 75;
                    break block133;
                }
                case 641141505: {
                    if (!var0.equals("application/x-texinfo")) break;
                    var1_1 = 33;
                    break block133;
                }
                case 603849904: {
                    if (!var0.equals("application/xhtml+xml")) break;
                    var1_1 = 16;
                    break block133;
                }
                case 571050671: {
                    if (!var0.equals("application/vnd.stardivision.writer-global")) break;
                    var1_1 = 93;
                    break block133;
                }
                case 501428239: {
                    if (!var0.equals("text/x-vcard")) break;
                    var1_1 = 59;
                    break block133;
                }
                case 428819984: {
                    if (!var0.equals("application/vnd.oasis.opendocument.graphics")) break;
                    var1_1 = 67;
                    break block133;
                }
                case 394650567: {
                    if (!var0.equals("application/pgp-keys")) break;
                    var1_1 = 3;
                    break block133;
                }
                case 363965503: {
                    if (!var0.equals("application/x-rar-compressed")) break;
                    var1_1 = 58;
                    break block133;
                }
                case 302663708: {
                    if (!var0.equals("application/ecmascript")) break;
                    var1_1 = 35;
                    break block133;
                }
                case 302189274: {
                    if (!var0.equals("vnd.android.document/directory")) break;
                    var1_1 = 1;
                    break block133;
                }
                case 262346941: {
                    if (!var0.equals("text/x-vcalendar")) break;
                    var1_1 = 62;
                    break block133;
                }
                case 245790645: {
                    if (!var0.equals("application/vnd.google-apps.drawing")) break;
                    var1_1 = 73;
                    break block133;
                }
                case 180207563: {
                    if (!var0.equals("application/x-stuffit")) break;
                    var1_1 = 51;
                    break block133;
                }
                case 163679941: {
                    if (!var0.equals("application/pgp-signature")) break;
                    var1_1 = 4;
                    break block133;
                }
                case 81142075: {
                    if (!var0.equals("application/vnd.android.package-archive")) break;
                    var1_1 = 2;
                    break block133;
                }
                case 26919318: {
                    if (!var0.equals("application/x-iso9660-image")) break;
                    var1_1 = 47;
                    break block133;
                }
                case -43840953: {
                    if (!var0.equals("application/json")) break;
                    var1_1 = 36;
                    break block133;
                }
                case -43923783: {
                    if (!var0.equals("application/gzip")) break;
                    var1_1 = 55;
                    break block133;
                }
                case -109382304: {
                    if (!var0.equals("application/vnd.oasis.opendocument.spreadsheet-template")) break;
                    var1_1 = 82;
                    break block133;
                }
                case -221944004: {
                    if (!var0.equals("application/x-font-ttf")) break;
                    var1_1 = 66;
                    break block133;
                }
                case -228136375: {
                    if (!var0.equals("application/x-pkcs7-mime")) break;
                    var1_1 = 11;
                    break block133;
                }
                case -261278343: {
                    if (!var0.equals("text/x-java")) break;
                    var1_1 = 27;
                    break block133;
                }
                case -261439913: {
                    if (!var0.equals("text/x-dsrc")) break;
                    var1_1 = 24;
                    break block133;
                }
                case -261469704: {
                    if (!var0.equals("text/x-csrc")) break;
                    var1_1 = 23;
                    break block133;
                }
                case -261480694: {
                    if (!var0.equals("text/x-chdr")) break;
                    var1_1 = 22;
                    break block133;
                }
                case -366307023: {
                    if (!var0.equals("application/vnd.ms-excel")) break;
                    var1_1 = 105;
                    break block133;
                }
                case -396757341: {
                    if (!var0.equals("application/vnd.sun.xml.impress.template")) break;
                    var1_1 = 77;
                    break block133;
                }
                case -427343476: {
                    if (!var0.equals("application/x-webarchive-xml")) break;
                    var1_1 = 54;
                    break block133;
                }
                case -479218428: {
                    if (!var0.equals("application/vnd.sun.xml.writer.global")) break;
                    var1_1 = 95;
                    break block133;
                }
                case -676675015: {
                    if (!var0.equals("application/vnd.oasis.opendocument.text-web")) break;
                    var1_1 = 91;
                    break block133;
                }
                case -723118015: {
                    if (!var0.equals("application/x-javascript")) break;
                    var1_1 = 40;
                    break block133;
                }
                case -779913474: {
                    if (!var0.equals("application/vnd.sun.xml.draw")) break;
                    var1_1 = 71;
                    break block133;
                }
                case -779959281: {
                    if (!var0.equals("application/vnd.sun.xml.calc")) break;
                    var1_1 = 84;
                    break block133;
                }
                case -951557661: {
                    if (!var0.equals("application/vnd.google-apps.presentation")) break;
                    var1_1 = 80;
                    break block133;
                }
                case -958424608: {
                    if (!var0.equals("text/calendar")) break;
                    var1_1 = 61;
                    break block133;
                }
                case -1004727243: {
                    if (!var0.equals("text/xml")) break;
                    var1_1 = 19;
                    break block133;
                }
                case -1004747231: {
                    if (!var0.equals("text/css")) break;
                    var1_1 = 17;
                    break block133;
                }
                case -1033484950: {
                    if (!var0.equals("application/vnd.sun.xml.draw.template")) break;
                    var1_1 = 72;
                    break block133;
                }
                case -1050893613: {
                    if (!var0.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) break;
                    var1_1 = 103;
                    break block133;
                }
                case -1071817359: {
                    if (!var0.equals("application/vnd.ms-powerpoint")) break;
                    var1_1 = 108;
                    break block133;
                }
                case -1073633483: {
                    if (!var0.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) break;
                    var1_1 = 109;
                    break block133;
                }
                case -1082243251: {
                    if (!var0.equals("text/html")) break;
                    var1_1 = 18;
                    break block133;
                }
                case -1143717099: {
                    if (!var0.equals("application/x-pkcs7-certreqresp")) break;
                    var1_1 = 6;
                    break block133;
                }
                case -1190438973: {
                    if (!var0.equals("application/x-pkcs7-signature")) break;
                    var1_1 = 12;
                    break block133;
                }
                case -1248325150: {
                    if (!var0.equals("application/zip")) break;
                    var1_1 = 43;
                    break block133;
                }
                case -1248326952: {
                    if (!var0.equals("application/xml")) break;
                    var1_1 = 38;
                    break block133;
                }
                case -1248333084: {
                    if (!var0.equals("application/rar")) break;
                    var1_1 = 42;
                    break block133;
                }
                case -1248334925: {
                    if (!var0.equals("application/pdf")) break;
                    var1_1 = 74;
                    break block133;
                }
                case -1294595255: {
                    if (!var0.equals("inode/directory")) break;
                    var1_1 = 0;
                    break block133;
                }
                case -1296467268: {
                    if (!var0.equals("application/atom+xml")) break;
                    var1_1 = 34;
                    break block133;
                }
                case -1316922187: {
                    if (!var0.equals("application/vnd.oasis.opendocument.text-template")) break;
                    var1_1 = 90;
                    break block133;
                }
                case -1326989846: {
                    if (!var0.equals("application/x-shockwave-flash")) break;
                    var1_1 = 101;
                    break block133;
                }
                case -1348221103: {
                    if (!var0.equals("application/x-tar")) break;
                    var1_1 = 52;
                    break block133;
                }
                case -1348228010: {
                    if (!var0.equals("application/x-lzx")) break;
                    var1_1 = 50;
                    break block133;
                }
                case -1348228026: {
                    if (!var0.equals("application/x-lzh")) break;
                    var1_1 = 49;
                    break block133;
                }
                case -1348228591: {
                    if (!var0.equals("application/x-lha")) break;
                    var1_1 = 48;
                    break block133;
                }
                case -1348236371: {
                    if (!var0.equals("application/x-deb")) break;
                    var1_1 = 57;
                    break block133;
                }
                case -1386165903: {
                    if (!var0.equals("application/x-kpresenter")) break;
                    var1_1 = 78;
                    break block133;
                }
                case -1506009513: {
                    if (!var0.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.template")) break;
                    var1_1 = 107;
                    break block133;
                }
                case -1590813831: {
                    if (!var0.equals("application/vnd.sun.xml.calc.template")) break;
                    var1_1 = 85;
                    break block133;
                }
                case -1628346451: {
                    if (!var0.equals("application/vnd.sun.xml.writer")) break;
                    var1_1 = 94;
                    break block133;
                }
                case -1719571662: {
                    if (!var0.equals("application/vnd.oasis.opendocument.text")) break;
                    var1_1 = 88;
                    break block133;
                }
                case -1747277413: {
                    if (!var0.equals("application/vnd.sun.xml.writer.template")) break;
                    var1_1 = 96;
                    break block133;
                }
                case -1777056778: {
                    if (!var0.equals("application/vnd.oasis.opendocument.image")) break;
                    var1_1 = 69;
                    break block133;
                }
                case -1808693885: {
                    if (!var0.equals("text/x-pascal")) break;
                    var1_1 = 29;
                    break block133;
                }
                case -1883861089: {
                    if (!var0.equals("application/rss+xml")) break;
                    var1_1 = 14;
                    break block133;
                }
                case -1917350260: {
                    if (!var0.equals("text/x-literate-haskell")) break;
                    var1_1 = 28;
                    break block133;
                }
                case -1988437312: {
                    if (!var0.equals("application/x-x509-ca-cert")) break;
                    var1_1 = 8;
                    break block133;
                }
                case -2035614749: {
                    if (!var0.equals("application/vnd.google-apps.spreadsheet")) break;
                    var1_1 = 87;
                    break block133;
                }
                case -2135135086: {
                    if (!var0.equals("application/vnd.stardivision.draw")) break;
                    var1_1 = 70;
                    break block133;
                }
                case -2135180893: {
                    if (!var0.equals("application/vnd.stardivision.calc")) break;
                    var1_1 = 83;
                    break block133;
                }
            }
            ** break;
lbl451: // 1 sources:
            var1_1 = -1;
        }
        switch (var1_1) {
            default: {
                return MimeIconUtils.buildGenericTypeInfo(var0);
            }
            case 108: 
            case 109: 
            case 110: 
            case 111: {
                return MimeIconUtils.buildTypeInfo(var0, 17302394, 17040425, 17040426);
            }
            case 105: 
            case 106: 
            case 107: {
                return MimeIconUtils.buildTypeInfo(var0, 17302388, 17040427, 17040428);
            }
            case 102: 
            case 103: 
            case 104: {
                return MimeIconUtils.buildTypeInfo(var0, 17302399, 17040418, 17040419);
            }
            case 100: 
            case 101: {
                return MimeIconUtils.buildTypeInfo(var0, 17302398, 17040429, 17040430);
            }
            case 88: 
            case 89: 
            case 90: 
            case 91: 
            case 92: 
            case 93: 
            case 94: 
            case 95: 
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                return MimeIconUtils.buildTypeInfo(var0, 17302386, 17040418, 17040419);
            }
            case 81: 
            case 82: 
            case 83: 
            case 84: 
            case 85: 
            case 86: 
            case 87: {
                return MimeIconUtils.buildTypeInfo(var0, 17302396, 17040427, 17040428);
            }
            case 75: 
            case 76: 
            case 77: 
            case 78: 
            case 79: 
            case 80: {
                return MimeIconUtils.buildTypeInfo(var0, 17302395, 17040425, 17040426);
            }
            case 74: {
                return MimeIconUtils.buildTypeInfo(var0, 17302393, 17040418, 17040419);
            }
            case 67: 
            case 68: 
            case 69: 
            case 70: 
            case 71: 
            case 72: 
            case 73: {
                return MimeIconUtils.buildTypeInfo(var0, 17302392, 17040423, 17040424);
            }
            case 63: 
            case 64: 
            case 65: 
            case 66: {
                return MimeIconUtils.buildTypeInfo(var0, 17302390, 17040421, 17040422);
            }
            case 61: 
            case 62: {
                return MimeIconUtils.buildTypeInfo(var0, 17302387, 17040421, 17040422);
            }
            case 59: 
            case 60: {
                return MimeIconUtils.buildTypeInfo(var0, 17302385, 17040421, 17040422);
            }
            case 41: 
            case 42: 
            case 43: 
            case 44: 
            case 45: 
            case 46: 
            case 47: 
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: 
            case 58: {
                return MimeIconUtils.buildTypeInfo(var0, 17302384, 17040416, 17040417);
            }
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 38: 
            case 39: 
            case 40: {
                return MimeIconUtils.buildTypeInfo(var0, 17302383, 17040418, 17040419);
            }
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                return MimeIconUtils.buildTypeInfo(var0, 17302382, 17040421, 17040422);
            }
            case 2: {
                return MimeIconUtils.buildTypeInfo(var0, 17302380, 17040413, -1);
            }
            case 0: 
            case 1: 
        }
        return MimeIconUtils.buildTypeInfo(var0, 17302389, 17040420, -1);
    }

    private static ContentResolver.MimeTypeInfo buildTypeInfo(String string2, int n, int n2, int n3) {
        Resources resources = Resources.getSystem();
        string2 = !TextUtils.isEmpty(string2 = MimeUtils.guessExtensionFromMimeType((String)string2)) && n3 != -1 ? resources.getString(n3, string2.toUpperCase(Locale.US)) : resources.getString(n2);
        return new ContentResolver.MimeTypeInfo(Icon.createWithResource(resources, n), string2, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ContentResolver.MimeTypeInfo getTypeInfo(String object) {
        String string2 = ((String)object).toLowerCase(Locale.US);
        ArrayMap<String, ContentResolver.MimeTypeInfo> arrayMap = sCache;
        synchronized (arrayMap) {
            ContentResolver.MimeTypeInfo mimeTypeInfo = sCache.get(string2);
            object = mimeTypeInfo;
            if (mimeTypeInfo == null) {
                object = MimeIconUtils.buildTypeInfo(string2);
                sCache.put(string2, (ContentResolver.MimeTypeInfo)object);
            }
            return object;
        }
    }
}

