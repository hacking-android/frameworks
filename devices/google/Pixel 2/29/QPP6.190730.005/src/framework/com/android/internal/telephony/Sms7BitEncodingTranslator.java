/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.telephony.Rlog;
import android.util.SparseIntArray;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.cdma.sms.UserData;
import com.android.internal.util.XmlUtils;

public class Sms7BitEncodingTranslator {
    private static final boolean DBG = Build.IS_DEBUGGABLE;
    private static final String TAG = "Sms7BitEncodingTranslator";
    private static final String XML_CHARACTOR_TAG = "Character";
    private static final String XML_FROM_TAG = "from";
    private static final String XML_START_TAG = "SmsEnforce7BitTranslationTable";
    private static final String XML_TO_TAG = "to";
    private static final String XML_TRANSLATION_TYPE_TAG = "TranslationType";
    private static boolean mIs7BitTranslationTableLoaded = false;
    private static SparseIntArray mTranslationTable = null;
    private static SparseIntArray mTranslationTableCDMA;
    private static SparseIntArray mTranslationTableCommon;
    private static SparseIntArray mTranslationTableGSM;

    static {
        mTranslationTableCommon = null;
        mTranslationTableGSM = null;
        mTranslationTableCDMA = null;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void load7BitTranslationTableFromXml() {
        Throwable throwable2222;
        XmlResourceParser xmlResourceParser;
        block16 : {
            block17 : {
                block15 : {
                    xmlResourceParser = null;
                    Object object = Resources.getSystem();
                    if (!false) {
                        if (DBG) {
                            Rlog.d(TAG, "load7BitTranslationTableFromXml: open normal file");
                        }
                        xmlResourceParser = ((Resources)object).getXml(18284563);
                    }
                    XmlUtils.beginDocument(xmlResourceParser, XML_START_TAG);
                    do {
                        XmlUtils.nextElement(xmlResourceParser);
                        CharSequence charSequence = xmlResourceParser.getName();
                        if (DBG) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("tag: ");
                            ((StringBuilder)object).append((String)charSequence);
                            Rlog.d(TAG, ((StringBuilder)object).toString());
                        }
                        if (XML_TRANSLATION_TYPE_TAG.equals(charSequence)) {
                            object = xmlResourceParser.getAttributeValue(null, "Type");
                            if (DBG) {
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append("type: ");
                                ((StringBuilder)charSequence).append((String)object);
                                Rlog.d(TAG, ((StringBuilder)charSequence).toString());
                            }
                            if (((String)object).equals("common")) {
                                mTranslationTable = mTranslationTableCommon;
                                continue;
                            }
                            if (((String)object).equals("gsm")) {
                                mTranslationTable = mTranslationTableGSM;
                                continue;
                            }
                            if (((String)object).equals("cdma")) {
                                mTranslationTable = mTranslationTableCDMA;
                                continue;
                            }
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("Error Parsing 7BitTranslationTable: found incorrect type");
                            ((StringBuilder)charSequence).append((String)object);
                            Rlog.e(TAG, ((StringBuilder)charSequence).toString());
                            continue;
                        }
                        if (!XML_CHARACTOR_TAG.equals(charSequence) || mTranslationTable == null) break;
                        int n = xmlResourceParser.getAttributeUnsignedIntValue(null, XML_FROM_TAG, -1);
                        int n2 = xmlResourceParser.getAttributeUnsignedIntValue(null, XML_TO_TAG, -1);
                        if (n != -1 && n2 != -1) {
                            if (DBG) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Loading mapping ");
                                ((StringBuilder)object).append(Integer.toHexString(n).toUpperCase());
                                ((StringBuilder)object).append(" -> ");
                                ((StringBuilder)object).append(Integer.toHexString(n2).toUpperCase());
                                Rlog.d(TAG, ((StringBuilder)object).toString());
                            }
                            mTranslationTable.put(n, n2);
                            continue;
                        }
                        Rlog.d(TAG, "Invalid translation table file format");
                    } while (true);
                    if (!DBG) break block15;
                    Rlog.d(TAG, "load7BitTranslationTableFromXml: parsing successful, file loaded");
                }
                if (!(xmlResourceParser instanceof XmlResourceParser)) return;
                break block17;
                {
                    catch (Throwable throwable2222) {
                        break block16;
                    }
                    catch (Exception exception) {}
                    {
                        Rlog.e(TAG, "Got exception while loading 7BitTranslationTable file.", exception);
                    }
                    if (!(xmlResourceParser instanceof XmlResourceParser)) return;
                }
            }
            xmlResourceParser.close();
            return;
        }
        if (!(xmlResourceParser instanceof XmlResourceParser)) throw throwable2222;
        xmlResourceParser.close();
        throw throwable2222;
    }

    private static boolean noTranslationNeeded(char c, boolean bl) {
        if (bl) {
            bl = GsmAlphabet.isGsmSeptets(c) && UserData.charToAscii.get(c, -1) != -1;
            return bl;
        }
        return GsmAlphabet.isGsmSeptets(c);
    }

    public static String translate(CharSequence charSequence, boolean bl) {
        Object object;
        if (charSequence == null) {
            Rlog.w(TAG, "Null message can not be translated");
            return null;
        }
        int n = charSequence.length();
        if (n <= 0) {
            return "";
        }
        if (!mIs7BitTranslationTableLoaded) {
            mTranslationTableCommon = new SparseIntArray();
            mTranslationTableGSM = new SparseIntArray();
            mTranslationTableCDMA = new SparseIntArray();
            Sms7BitEncodingTranslator.load7BitTranslationTableFromXml();
            mIs7BitTranslationTableLoaded = true;
        }
        if ((object = mTranslationTableCommon) != null && ((SparseIntArray)object).size() > 0 || (object = mTranslationTableGSM) != null && ((SparseIntArray)object).size() > 0 || (object = mTranslationTableCDMA) != null && ((SparseIntArray)object).size() > 0) {
            object = new char[n];
            for (int i = 0; i < n; ++i) {
                object[i] = Sms7BitEncodingTranslator.translateIfNeeded(charSequence.charAt(i), bl);
            }
            return String.valueOf((char[])object);
        }
        return null;
    }

    private static char translateIfNeeded(char c, boolean bl) {
        if (Sms7BitEncodingTranslator.noTranslationNeeded(c, bl)) {
            if (DBG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("No translation needed for ");
                stringBuilder.append(Integer.toHexString(c));
                Rlog.v(TAG, stringBuilder.toString());
            }
            return c;
        }
        int n = -1;
        Object object = mTranslationTableCommon;
        if (object != null) {
            n = ((SparseIntArray)object).get(c, -1);
        }
        int n2 = n;
        if (n == -1) {
            if (bl) {
                object = mTranslationTableCDMA;
                n2 = n;
                if (object != null) {
                    n2 = ((SparseIntArray)object).get(c, -1);
                }
            } else {
                object = mTranslationTableGSM;
                n2 = n;
                if (object != null) {
                    n2 = ((SparseIntArray)object).get(c, -1);
                }
            }
        }
        if (n2 != -1) {
            if (DBG) {
                object = new StringBuilder();
                ((StringBuilder)object).append(Integer.toHexString(c));
                ((StringBuilder)object).append(" (");
                ((StringBuilder)object).append(c);
                ((StringBuilder)object).append(") translated to ");
                ((StringBuilder)object).append(Integer.toHexString(n2));
                ((StringBuilder)object).append(" (");
                ((StringBuilder)object).append((char)n2);
                ((StringBuilder)object).append(")");
                Rlog.v(TAG, ((StringBuilder)object).toString());
            }
            return (char)n2;
        }
        if (DBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No translation found for ");
            ((StringBuilder)object).append(Integer.toHexString(c));
            ((StringBuilder)object).append("! Replacing for empty space");
            Rlog.w(TAG, ((StringBuilder)object).toString());
        }
        return ' ';
    }
}

