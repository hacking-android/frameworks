/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.telephony.Rlog;
import android.text.TextUtils;
import android.util.SparseIntArray;
import com.android.internal.telephony.EncodeException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

public class GsmAlphabet {
    public static final byte GSM_EXTENDED_ESCAPE = 27;
    private static final String TAG = "GSM";
    public static final int UDH_SEPTET_COST_CONCATENATED_MESSAGE = 6;
    public static final int UDH_SEPTET_COST_LENGTH = 1;
    public static final int UDH_SEPTET_COST_ONE_SHIFT_TABLE = 4;
    public static final int UDH_SEPTET_COST_TWO_SHIFT_TABLES = 7;
    @UnsupportedAppUsage
    private static final SparseIntArray[] sCharsToGsmTables;
    @UnsupportedAppUsage
    private static final SparseIntArray[] sCharsToShiftTables;
    private static boolean sDisableCountryEncodingCheck;
    @UnsupportedAppUsage
    private static int[] sEnabledLockingShiftTables;
    @UnsupportedAppUsage
    private static int[] sEnabledSingleShiftTables;
    @UnsupportedAppUsage
    private static int sHighestEnabledSingleShiftCode;
    @UnsupportedAppUsage
    private static final String[] sLanguageShiftTables;
    @UnsupportedAppUsage
    private static final String[] sLanguageTables;

    static {
        CharSequence charSequence;
        int n;
        int n2;
        Object object;
        int n3;
        sDisableCountryEncodingCheck = false;
        sLanguageTables = new String[]{"@\u00a3$\u00a5\u00e8\u00e9\u00f9\u00ec\u00f2\u00c7\n\u00d8\u00f8\r\u00c5\u00e5\u0394_\u03a6\u0393\u039b\u03a9\u03a0\u03a8\u03a3\u0398\u039e\uffff\u00c6\u00e6\u00df\u00c9 !\"#\u00a4%&'()*+,-./0123456789:;<=>?\u00a1ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00c4\u00d6\u00d1\u00dc\u00a7\u00bfabcdefghijklmnopqrstuvwxyz\u00e4\u00f6\u00f1\u00fc\u00e0", "@\u00a3$\u00a5\u20ac\u00e9\u00f9\u0131\u00f2\u00c7\n\u011e\u011f\r\u00c5\u00e5\u0394_\u03a6\u0393\u039b\u03a9\u03a0\u03a8\u03a3\u0398\u039e\uffff\u015e\u015f\u00df\u00c9 !\"#\u00a4%&'()*+,-./0123456789:;<=>?\u0130ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00c4\u00d6\u00d1\u00dc\u00a7\u00e7abcdefghijklmnopqrstuvwxyz\u00e4\u00f6\u00f1\u00fc\u00e0", "", "@\u00a3$\u00a5\u00ea\u00e9\u00fa\u00ed\u00f3\u00e7\n\u00d4\u00f4\r\u00c1\u00e1\u0394_\u00aa\u00c7\u00c0\u221e^\\\u20ac\u00d3|\uffff\u00c2\u00e2\u00ca\u00c9 !\"#\u00ba%&'()*+,-./0123456789:;<=>?\u00cdABCDEFGHIJKLMNOPQRSTUVWXYZ\u00c3\u00d5\u00da\u00dc\u00a7~abcdefghijklmnopqrstuvwxyz\u00e3\u00f5`\u00fc\u00e0", "\u0981\u0982\u0983\u0985\u0986\u0987\u0988\u0989\u098a\u098b\n\u098c \r \u098f\u0990  \u0993\u0994\u0995\u0996\u0997\u0998\u0999\u099a\uffff\u099b\u099c\u099d\u099e !\u099f\u09a0\u09a1\u09a2\u09a3\u09a4)(\u09a5\u09a6,\u09a7.\u09a80123456789:; \u09aa\u09ab?\u09ac\u09ad\u09ae\u09af\u09b0 \u09b2   \u09b6\u09b7\u09b8\u09b9\u09bc\u09bd\u09be\u09bf\u09c0\u09c1\u09c2\u09c3\u09c4  \u09c7\u09c8  \u09cb\u09cc\u09cd\u09ceabcdefghijklmnopqrstuvwxyz\u09d7\u09dc\u09dd\u09f0\u09f1", "\u0a81\u0a82\u0a83\u0a85\u0a86\u0a87\u0a88\u0a89\u0a8a\u0a8b\n\u0a8c\u0a8d\r \u0a8f\u0a90\u0a91 \u0a93\u0a94\u0a95\u0a96\u0a97\u0a98\u0a99\u0a9a\uffff\u0a9b\u0a9c\u0a9d\u0a9e !\u0a9f\u0aa0\u0aa1\u0aa2\u0aa3\u0aa4)(\u0aa5\u0aa6,\u0aa7.\u0aa80123456789:; \u0aaa\u0aab?\u0aac\u0aad\u0aae\u0aaf\u0ab0 \u0ab2\u0ab3 \u0ab5\u0ab6\u0ab7\u0ab8\u0ab9\u0abc\u0abd\u0abe\u0abf\u0ac0\u0ac1\u0ac2\u0ac3\u0ac4\u0ac5 \u0ac7\u0ac8\u0ac9 \u0acb\u0acc\u0acd\u0ad0abcdefghijklmnopqrstuvwxyz\u0ae0\u0ae1\u0ae2\u0ae3\u0af1", "\u0901\u0902\u0903\u0905\u0906\u0907\u0908\u0909\u090a\u090b\n\u090c\u090d\r\u090e\u090f\u0910\u0911\u0912\u0913\u0914\u0915\u0916\u0917\u0918\u0919\u091a\uffff\u091b\u091c\u091d\u091e !\u091f\u0920\u0921\u0922\u0923\u0924)(\u0925\u0926,\u0927.\u09280123456789:;\u0929\u092a\u092b?\u092c\u092d\u092e\u092f\u0930\u0931\u0932\u0933\u0934\u0935\u0936\u0937\u0938\u0939\u093c\u093d\u093e\u093f\u0940\u0941\u0942\u0943\u0944\u0945\u0946\u0947\u0948\u0949\u094a\u094b\u094c\u094d\u0950abcdefghijklmnopqrstuvwxyz\u0972\u097b\u097c\u097e\u097f", " \u0c82\u0c83\u0c85\u0c86\u0c87\u0c88\u0c89\u0c8a\u0c8b\n\u0c8c \r\u0c8e\u0c8f\u0c90 \u0c92\u0c93\u0c94\u0c95\u0c96\u0c97\u0c98\u0c99\u0c9a\uffff\u0c9b\u0c9c\u0c9d\u0c9e !\u0c9f\u0ca0\u0ca1\u0ca2\u0ca3\u0ca4)(\u0ca5\u0ca6,\u0ca7.\u0ca80123456789:; \u0caa\u0cab?\u0cac\u0cad\u0cae\u0caf\u0cb0\u0cb1\u0cb2\u0cb3 \u0cb5\u0cb6\u0cb7\u0cb8\u0cb9\u0cbc\u0cbd\u0cbe\u0cbf\u0cc0\u0cc1\u0cc2\u0cc3\u0cc4 \u0cc6\u0cc7\u0cc8 \u0cca\u0ccb\u0ccc\u0ccd\u0cd5abcdefghijklmnopqrstuvwxyz\u0cd6\u0ce0\u0ce1\u0ce2\u0ce3", " \u0d02\u0d03\u0d05\u0d06\u0d07\u0d08\u0d09\u0d0a\u0d0b\n\u0d0c \r\u0d0e\u0d0f\u0d10 \u0d12\u0d13\u0d14\u0d15\u0d16\u0d17\u0d18\u0d19\u0d1a\uffff\u0d1b\u0d1c\u0d1d\u0d1e !\u0d1f\u0d20\u0d21\u0d22\u0d23\u0d24)(\u0d25\u0d26,\u0d27.\u0d280123456789:; \u0d2a\u0d2b?\u0d2c\u0d2d\u0d2e\u0d2f\u0d30\u0d31\u0d32\u0d33\u0d34\u0d35\u0d36\u0d37\u0d38\u0d39 \u0d3d\u0d3e\u0d3f\u0d40\u0d41\u0d42\u0d43\u0d44 \u0d46\u0d47\u0d48 \u0d4a\u0d4b\u0d4c\u0d4d\u0d57abcdefghijklmnopqrstuvwxyz\u0d60\u0d61\u0d62\u0d63\u0d79", "\u0b01\u0b02\u0b03\u0b05\u0b06\u0b07\u0b08\u0b09\u0b0a\u0b0b\n\u0b0c \r \u0b0f\u0b10  \u0b13\u0b14\u0b15\u0b16\u0b17\u0b18\u0b19\u0b1a\uffff\u0b1b\u0b1c\u0b1d\u0b1e !\u0b1f\u0b20\u0b21\u0b22\u0b23\u0b24)(\u0b25\u0b26,\u0b27.\u0b280123456789:; \u0b2a\u0b2b?\u0b2c\u0b2d\u0b2e\u0b2f\u0b30 \u0b32\u0b33 \u0b35\u0b36\u0b37\u0b38\u0b39\u0b3c\u0b3d\u0b3e\u0b3f\u0b40\u0b41\u0b42\u0b43\u0b44  \u0b47\u0b48  \u0b4b\u0b4c\u0b4d\u0b56abcdefghijklmnopqrstuvwxyz\u0b57\u0b60\u0b61\u0b62\u0b63", "\u0a01\u0a02\u0a03\u0a05\u0a06\u0a07\u0a08\u0a09\u0a0a \n  \r \u0a0f\u0a10  \u0a13\u0a14\u0a15\u0a16\u0a17\u0a18\u0a19\u0a1a\uffff\u0a1b\u0a1c\u0a1d\u0a1e !\u0a1f\u0a20\u0a21\u0a22\u0a23\u0a24)(\u0a25\u0a26,\u0a27.\u0a280123456789:; \u0a2a\u0a2b?\u0a2c\u0a2d\u0a2e\u0a2f\u0a30 \u0a32\u0a33 \u0a35\u0a36 \u0a38\u0a39\u0a3c \u0a3e\u0a3f\u0a40\u0a41\u0a42    \u0a47\u0a48  \u0a4b\u0a4c\u0a4d\u0a51abcdefghijklmnopqrstuvwxyz\u0a70\u0a71\u0a72\u0a73\u0a74", " \u0b82\u0b83\u0b85\u0b86\u0b87\u0b88\u0b89\u0b8a \n  \r\u0b8e\u0b8f\u0b90 \u0b92\u0b93\u0b94\u0b95   \u0b99\u0b9a\uffff \u0b9c \u0b9e !\u0b9f   \u0ba3\u0ba4)(  , .\u0ba80123456789:;\u0ba9\u0baa ?  \u0bae\u0baf\u0bb0\u0bb1\u0bb2\u0bb3\u0bb4\u0bb5\u0bb6\u0bb7\u0bb8\u0bb9  \u0bbe\u0bbf\u0bc0\u0bc1\u0bc2   \u0bc6\u0bc7\u0bc8 \u0bca\u0bcb\u0bcc\u0bcd\u0bd0abcdefghijklmnopqrstuvwxyz\u0bd7\u0bf0\u0bf1\u0bf2\u0bf9", "\u0c01\u0c02\u0c03\u0c05\u0c06\u0c07\u0c08\u0c09\u0c0a\u0c0b\n\u0c0c \r\u0c0e\u0c0f\u0c10 \u0c12\u0c13\u0c14\u0c15\u0c16\u0c17\u0c18\u0c19\u0c1a\uffff\u0c1b\u0c1c\u0c1d\u0c1e !\u0c1f\u0c20\u0c21\u0c22\u0c23\u0c24)(\u0c25\u0c26,\u0c27.\u0c280123456789:; \u0c2a\u0c2b?\u0c2c\u0c2d\u0c2e\u0c2f\u0c30\u0c31\u0c32\u0c33 \u0c35\u0c36\u0c37\u0c38\u0c39 \u0c3d\u0c3e\u0c3f\u0c40\u0c41\u0c42\u0c43\u0c44 \u0c46\u0c47\u0c48 \u0c4a\u0c4b\u0c4c\u0c4d\u0c55abcdefghijklmnopqrstuvwxyz\u0c56\u0c60\u0c61\u0c62\u0c63", "\u0627\u0622\u0628\u067b\u0680\u067e\u06a6\u062a\u06c2\u067f\n\u0679\u067d\r\u067a\u067c\u062b\u062c\u0681\u0684\u0683\u0685\u0686\u0687\u062d\u062e\u062f\uffff\u068c\u0688\u0689\u068a !\u068f\u068d\u0630\u0631\u0691\u0693)(\u0699\u0632,\u0696.\u06980123456789:;\u069a\u0633\u0634?\u0635\u0636\u0637\u0638\u0639\u0641\u0642\u06a9\u06aa\u06ab\u06af\u06b3\u06b1\u0644\u0645\u0646\u06ba\u06bb\u06bc\u0648\u06c4\u06d5\u06c1\u06be\u0621\u06cc\u06d0\u06d2\u064d\u0650\u064f\u0657\u0654abcdefghijklmnopqrstuvwxyz\u0655\u0651\u0653\u0656\u0670"};
        sLanguageShiftTables = new String[]{"          \f         ^                   {}     \\            [~] |                                    \u20ac                          ", "          \f         ^                   {}     \\            [~] |      \u011e \u0130         \u015e               \u00e7 \u20ac \u011f \u0131         \u015f            ", "         \u00e7\f         ^                   {}     \\            [~] |\u00c1       \u00cd     \u00d3     \u00da           \u00e1   \u20ac   \u00ed     \u00f3     \u00fa          ", "     \u00ea   \u00e7\f\u00d4\u00f4 \u00c1\u00e1  \u03a6\u0393^\u03a9\u03a0\u03a8\u03a3\u0398     \u00ca        {}     \\            [~] |\u00c0       \u00cd     \u00d3     \u00da     \u00c3\u00d5    \u00c2   \u20ac   \u00ed     \u00f3     \u00fa     \u00e3\u00f5  \u00e2", "@\u00a3$\u00a5\u00bf\"\u00a4%&'\f*+ -/<=>\u00a1^\u00a1_#*\u09e6\u09e7 \u09e8\u09e9\u09ea\u09eb\u09ec\u09ed\u09ee\u09ef\u09df\u09e0\u09e1\u09e2{}\u09e3\u09f2\u09f3\u09f4\u09f5\\\u09f6\u09f7\u09f8\u09f9\u09fa       [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          \u20ac                          ", "@\u00a3$\u00a5\u00bf\"\u00a4%&'\f*+ -/<=>\u00a1^\u00a1_#*\u0964\u0965 \u0ae6\u0ae7\u0ae8\u0ae9\u0aea\u0aeb\u0aec\u0aed\u0aee\u0aef  {}     \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          \u20ac                          ", "@\u00a3$\u00a5\u00bf\"\u00a4%&'\f*+ -/<=>\u00a1^\u00a1_#*\u0964\u0965 \u0966\u0967\u0968\u0969\u096a\u096b\u096c\u096d\u096e\u096f\u0951\u0952{}\u0953\u0954\u0958\u0959\u095a\\\u095b\u095c\u095d\u095e\u095f\u0960\u0961\u0962\u0963\u0970\u0971 [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          \u20ac                          ", "@\u00a3$\u00a5\u00bf\"\u00a4%&'\f*+ -/<=>\u00a1^\u00a1_#*\u0964\u0965 \u0ce6\u0ce7\u0ce8\u0ce9\u0cea\u0ceb\u0cec\u0ced\u0cee\u0cef\u0cde\u0cf1{}\u0cf2    \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          \u20ac                          ", "@\u00a3$\u00a5\u00bf\"\u00a4%&'\f*+ -/<=>\u00a1^\u00a1_#*\u0964\u0965 \u0d66\u0d67\u0d68\u0d69\u0d6a\u0d6b\u0d6c\u0d6d\u0d6e\u0d6f\u0d70\u0d71{}\u0d72\u0d73\u0d74\u0d75\u0d7a\\\u0d7b\u0d7c\u0d7d\u0d7e\u0d7f       [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          \u20ac                          ", "@\u00a3$\u00a5\u00bf\"\u00a4%&'\f*+ -/<=>\u00a1^\u00a1_#*\u0964\u0965 \u0b66\u0b67\u0b68\u0b69\u0b6a\u0b6b\u0b6c\u0b6d\u0b6e\u0b6f\u0b5c\u0b5d{}\u0b5f\u0b70\u0b71  \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          \u20ac                          ", "@\u00a3$\u00a5\u00bf\"\u00a4%&'\f*+ -/<=>\u00a1^\u00a1_#*\u0964\u0965 \u0a66\u0a67\u0a68\u0a69\u0a6a\u0a6b\u0a6c\u0a6d\u0a6e\u0a6f\u0a59\u0a5a{}\u0a5b\u0a5c\u0a5e\u0a75 \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          \u20ac                          ", "@\u00a3$\u00a5\u00bf\"\u00a4%&'\f*+ -/<=>\u00a1^\u00a1_#*\u0964\u0965 \u0be6\u0be7\u0be8\u0be9\u0bea\u0beb\u0bec\u0bed\u0bee\u0bef\u0bf3\u0bf4{}\u0bf5\u0bf6\u0bf7\u0bf8\u0bfa\\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          \u20ac                          ", "@\u00a3$\u00a5\u00bf\"\u00a4%&'\f*+ -/<=>\u00a1^\u00a1_#*   \u0c66\u0c67\u0c68\u0c69\u0c6a\u0c6b\u0c6c\u0c6d\u0c6e\u0c6f\u0c58\u0c59{}\u0c78\u0c79\u0c7a\u0c7b\u0c7c\\\u0c7d\u0c7e\u0c7f         [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          \u20ac                          ", "@\u00a3$\u00a5\u00bf\"\u00a4%&'\f*+ -/<=>\u00a1^\u00a1_#*\u0600\u0601 \u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9\u060c\u060d{}\u060e\u060f\u0610\u0611\u0612\\\u0613\u0614\u061b\u061f\u0640\u0652\u0658\u066b\u066c\u0672\u0673\u06cd[~]\u06d4|ABCDEFGHIJKLMNOPQRSTUVWXYZ          \u20ac                          "};
        GsmAlphabet.enableCountrySpecificEncodings();
        int n4 = sLanguageTables.length;
        int n5 = sLanguageShiftTables.length;
        if (n4 != n5) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Error: language tables array length ");
            ((StringBuilder)charSequence).append(n4);
            ((StringBuilder)charSequence).append(" != shift tables array length ");
            ((StringBuilder)charSequence).append(n5);
            Rlog.e(TAG, ((StringBuilder)charSequence).toString());
        }
        sCharsToGsmTables = new SparseIntArray[n4];
        for (n = 0; n < n4; ++n) {
            charSequence = sLanguageTables[n];
            n3 = ((String)charSequence).length();
            if (n3 != 0 && n3 != 128) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error: language tables index ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" length ");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(" (expected 128 or 0)");
                Rlog.e(TAG, ((StringBuilder)object).toString());
            }
            GsmAlphabet.sCharsToGsmTables[n] = object = new SparseIntArray(n3);
            for (n2 = 0; n2 < n3; ++n2) {
                ((SparseIntArray)object).put(((String)charSequence).charAt(n2), n2);
            }
        }
        sCharsToShiftTables = new SparseIntArray[n4];
        for (n = 0; n < n5; ++n) {
            charSequence = sLanguageShiftTables[n];
            n4 = ((String)charSequence).length();
            if (n4 != 0 && n4 != 128) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error: language shift tables index ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" length ");
                ((StringBuilder)object).append(n4);
                ((StringBuilder)object).append(" (expected 128 or 0)");
                Rlog.e(TAG, ((StringBuilder)object).toString());
            }
            GsmAlphabet.sCharsToShiftTables[n] = object = new SparseIntArray(n4);
            for (n2 = 0; n2 < n4; ++n2) {
                n3 = ((String)charSequence).charAt(n2);
                if (n3 == 32) continue;
                ((SparseIntArray)object).put(n3, n2);
            }
        }
    }

    private GsmAlphabet() {
    }

    @UnsupportedAppUsage
    public static int charToGsm(char c) {
        try {
            int n = GsmAlphabet.charToGsm(c, false);
            return n;
        }
        catch (EncodeException encodeException) {
            return sCharsToGsmTables[0].get(32, 32);
        }
    }

    @UnsupportedAppUsage
    public static int charToGsm(char c, boolean bl) throws EncodeException {
        int n = sCharsToGsmTables[0].get(c, -1);
        if (n == -1) {
            if (sCharsToShiftTables[0].get(c, -1) == -1) {
                if (!bl) {
                    return sCharsToGsmTables[0].get(32, 32);
                }
                throw new EncodeException(c);
            }
            return 27;
        }
        return n;
    }

    public static int charToGsmExtended(char c) {
        if ((c = (char)sCharsToShiftTables[0].get(c, -1)) == '\uffffffff') {
            return sCharsToGsmTables[0].get(32, 32);
        }
        return c;
    }

    public static int countGsmSeptets(char c) {
        try {
            int n = GsmAlphabet.countGsmSeptets(c, false);
            return n;
        }
        catch (EncodeException encodeException) {
            return 0;
        }
    }

    @UnsupportedAppUsage
    public static int countGsmSeptets(char c, boolean bl) throws EncodeException {
        if (sCharsToGsmTables[0].get(c, -1) != -1) {
            return 1;
        }
        if (sCharsToShiftTables[0].get(c, -1) != -1) {
            return 2;
        }
        if (!bl) {
            return 1;
        }
        throw new EncodeException(c);
    }

    public static TextEncodingDetails countGsmSeptets(CharSequence object, boolean bl) {
        int n;
        int n2;
        if (!sDisableCountryEncodingCheck) {
            GsmAlphabet.enableCountrySpecificEncodings();
        }
        int n3 = sEnabledSingleShiftTables.length;
        Object object2 = sEnabledLockingShiftTables;
        int n4 = ((int[])object2).length;
        if (n3 + n4 == 0) {
            object2 = new TextEncodingDetails();
            n2 = GsmAlphabet.countGsmSeptetsUsingTables((CharSequence)object, bl, 0, 0);
            if (n2 == -1) {
                return null;
            }
            ((TextEncodingDetails)object2).codeUnitSize = 1;
            ((TextEncodingDetails)object2).codeUnitCount = n2;
            if (n2 > 160) {
                ((TextEncodingDetails)object2).msgCount = (n2 + 152) / 153;
                ((TextEncodingDetails)object2).codeUnitsRemaining = ((TextEncodingDetails)object2).msgCount * 153 - n2;
            } else {
                ((TextEncodingDetails)object2).msgCount = 1;
                ((TextEncodingDetails)object2).codeUnitsRemaining = 160 - n2;
            }
            return object2;
        }
        int n5 = sHighestEnabledSingleShiftCode;
        object2 = new ArrayList(((int[])object2).length + 1);
        object2.add(new LanguagePairCount(0));
        Object object3 = sEnabledLockingShiftTables;
        n4 = ((int[])object3).length;
        for (n2 = 0; n2 < n4; ++n2) {
            n3 = object3[n2];
            if (n3 == 0 || sLanguageTables[n3].isEmpty()) continue;
            object2.add(new LanguagePairCount(n3));
        }
        n3 = object.length();
        for (n2 = 0; n2 < n3 && !object2.isEmpty(); ++n2) {
            n = object.charAt(n2);
            if (n == 27) {
                Rlog.w(TAG, "countGsmSeptets() string contains Escape character, ignoring!");
                continue;
            }
            object3 = object2.iterator();
            while (object3.hasNext()) {
                int[] arrn;
                LanguagePairCount languagePairCount = (LanguagePairCount)object3.next();
                if (sCharsToGsmTables[languagePairCount.languageCode].get(n, -1) == -1) {
                    for (n4 = 0; n4 <= n5; ++n4) {
                        if (languagePairCount.septetCounts[n4] == -1) continue;
                        if (sCharsToShiftTables[n4].get(n, -1) == -1) {
                            if (bl) {
                                arrn = languagePairCount.septetCounts;
                                arrn[n4] = arrn[n4] + 1;
                                arrn = languagePairCount.unencodableCounts;
                                arrn[n4] = arrn[n4] + 1;
                                continue;
                            }
                            languagePairCount.septetCounts[n4] = -1;
                            continue;
                        }
                        arrn = languagePairCount.septetCounts;
                        arrn[n4] = arrn[n4] + 2;
                    }
                    continue;
                }
                for (n4 = 0; n4 <= n5; ++n4) {
                    if (languagePairCount.septetCounts[n4] == -1) continue;
                    arrn = languagePairCount.septetCounts;
                    arrn[n4] = arrn[n4] + 1;
                }
            }
        }
        object = new TextEncodingDetails();
        ((TextEncodingDetails)object).msgCount = Integer.MAX_VALUE;
        ((TextEncodingDetails)object).codeUnitSize = 1;
        n3 = Integer.MAX_VALUE;
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object3 = (LanguagePairCount)object2.next();
            for (n4 = 0; n4 <= n5; ++n4) {
                int n6;
                block25 : {
                    int n7;
                    int n8;
                    block27 : {
                        block26 : {
                            block24 : {
                                n8 = ((LanguagePairCount)object3).septetCounts[n4];
                                if (n8 != -1) break block24;
                                n6 = n3;
                                break block25;
                            }
                            n2 = ((LanguagePairCount)object3).languageCode != 0 && n4 != 0 ? 8 : (((LanguagePairCount)object3).languageCode == 0 && n4 == 0 ? 0 : 5);
                            if (n8 + n2 > 160) {
                                n = n2;
                                if (n2 == 0) {
                                    n = 1;
                                }
                                n6 = 160 - (n + 6);
                                n2 = n = (n8 + n6 - 1) / n6;
                                n = n * n6 - n8;
                            } else {
                                n6 = 1;
                                n = 160 - n2 - n8;
                                n2 = n6;
                            }
                            n7 = ((LanguagePairCount)object3).unencodableCounts[n4];
                            if (!bl || n7 <= n3) break block26;
                            n6 = n3;
                            break block25;
                        }
                        if (bl && n7 < n3 || n2 < ((TextEncodingDetails)object).msgCount) break block27;
                        n6 = n3;
                        if (n2 != ((TextEncodingDetails)object).msgCount) break block25;
                        n6 = n3;
                        if (n <= ((TextEncodingDetails)object).codeUnitsRemaining) break block25;
                    }
                    n6 = n7;
                    ((TextEncodingDetails)object).msgCount = n2;
                    ((TextEncodingDetails)object).codeUnitCount = n8;
                    ((TextEncodingDetails)object).codeUnitsRemaining = n;
                    ((TextEncodingDetails)object).languageTable = ((LanguagePairCount)object3).languageCode;
                    ((TextEncodingDetails)object).languageShiftTable = n4;
                }
                n3 = n6;
            }
        }
        if (((TextEncodingDetails)object).msgCount == Integer.MAX_VALUE) {
            return null;
        }
        return object;
    }

    public static int countGsmSeptetsUsingTables(CharSequence charSequence, boolean bl, int n, int n2) {
        int n3 = 0;
        int n4 = charSequence.length();
        SparseIntArray sparseIntArray = sCharsToGsmTables[n];
        SparseIntArray sparseIntArray2 = sCharsToShiftTables[n2];
        n = n3;
        for (n2 = 0; n2 < n4; ++n2) {
            n3 = charSequence.charAt(n2);
            if (n3 == 27) {
                Rlog.w(TAG, "countGsmSeptets() string contains Escape character, skipping.");
                continue;
            }
            if (sparseIntArray.get(n3, -1) != -1) {
                ++n;
                continue;
            }
            if (sparseIntArray2.get(n3, -1) != -1) {
                n += 2;
                continue;
            }
            if (bl) {
                ++n;
                continue;
            }
            return -1;
        }
        return n;
    }

    private static void enableCountrySpecificEncodings() {
        int[] arrn = Resources.getSystem();
        sEnabledSingleShiftTables = arrn.getIntArray(17236061);
        sEnabledLockingShiftTables = arrn.getIntArray(17236060);
        arrn = sEnabledSingleShiftTables;
        sHighestEnabledSingleShiftCode = arrn.length > 0 ? arrn[arrn.length - 1] : 0;
    }

    @UnsupportedAppUsage
    public static int findGsmSeptetLimitIndex(String string2, int n, int n2, int n3, int n4) {
        int n5 = 0;
        int n6 = string2.length();
        SparseIntArray sparseIntArray = sCharsToGsmTables[n3];
        SparseIntArray sparseIntArray2 = sCharsToShiftTables[n4];
        n3 = n;
        n = n5;
        while (n3 < n6) {
            n = sparseIntArray.get(string2.charAt(n3), -1) == -1 ? (sparseIntArray2.get(string2.charAt(n3), -1) == -1 ? ++n : (n += 2)) : ++n;
            if (n > n2) {
                return n3;
            }
            ++n3;
        }
        return n6;
    }

    public static int[] getEnabledLockingShiftTables() {
        synchronized (GsmAlphabet.class) {
            int[] arrn = sEnabledLockingShiftTables;
            return arrn;
        }
    }

    public static int[] getEnabledSingleShiftTables() {
        synchronized (GsmAlphabet.class) {
            int[] arrn = sEnabledSingleShiftTables;
            return arrn;
        }
    }

    @UnsupportedAppUsage
    public static String gsm7BitPackedToString(byte[] arrby, int n, int n2) {
        return GsmAlphabet.gsm7BitPackedToString(arrby, n, n2, 0, 0, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public static String gsm7BitPackedToString(byte[] var0, int var1_2, int var2_3, int var3_4, int var4_5, int var5_6) {
        block17 : {
            block16 : {
                var6_7 = new StringBuilder(var2_3);
                if (var4_5 < 0 || var4_5 > GsmAlphabet.sLanguageTables.length) {
                    var7_8 = new StringBuilder();
                    var7_8.append("unknown language table ");
                    var7_8.append(var4_5);
                    var7_8.append(", using default");
                    Rlog.w("GSM", var7_8.toString());
                    var4_5 = 0;
                }
                if (var5_6 < 0) break block16;
                var8_9 = var5_6;
                if (var5_6 <= GsmAlphabet.sLanguageShiftTables.length) break block17;
            }
            var7_8 = new StringBuilder();
            var7_8.append("unknown single shift table ");
            var7_8.append(var5_6);
            var7_8.append(", using default");
            Rlog.w("GSM", var7_8.toString());
            var8_9 = 0;
        }
        var9_10 = 0;
        try {
            var10_11 = GsmAlphabet.sLanguageTables[var4_5];
            var11_12 = GsmAlphabet.sLanguageShiftTables[var8_9];
            var7_8 = var10_11;
            if (var10_11.isEmpty()) {
                var7_8 = new StringBuilder();
                var7_8.append("no language table for code ");
                var7_8.append(var4_5);
                var7_8.append(", using default");
                Rlog.w("GSM", var7_8.toString());
                var7_8 = GsmAlphabet.sLanguageTables[0];
            }
            var10_11 = var11_12;
            if (var11_12.isEmpty()) {
                var10_11 = new StringBuilder();
                var10_11.append("no single shift table for code ");
                var10_11.append(var8_9);
                var10_11.append(", using default");
                Rlog.w("GSM", var10_11.toString());
                var10_11 = GsmAlphabet.sLanguageShiftTables[0];
            }
            var5_6 = 0;
            var4_5 = var9_10;
        }
        catch (RuntimeException var0_1) {
            Rlog.e("GSM", "Error GSM 7 bit packed: ", var0_1);
            return null;
        }
        while (var5_6 < var2_3) {
            block19 : {
                block18 : {
                    var8_9 = var5_6 * 7 + var3_4;
                    var12_13 = var8_9 / 8;
                    var13_14 = var8_9 % 8;
                    var8_9 = var9_10 = var0[var1_2 + var12_13] >> var13_14 & 127;
                    if (var13_14 > 1) {
                        var8_9 = var9_10 & 127 >> var13_14 - 1 | 127 & var0[var1_2 + var12_13 + 1] << 8 - var13_14;
                    }
                    if (var4_5 == 0) ** GOTO lbl82
                    if (var8_9 != 27) ** GOTO lbl72
                    var6_7.append(' ');
                    break block18;
lbl72: // 1 sources:
                    var14_15 = var10_11.charAt(var8_9);
                    if (var14_15 == ' ') {
                        var6_7.append(var7_8.charAt(var8_9));
                    } else {
                        var6_7.append(var14_15);
                    }
                }
                var4_5 = 0;
                break block19;
lbl82: // 1 sources:
                if (var8_9 == 27) {
                    var4_5 = 1;
                } else {
                    var6_7.append(var7_8.charAt(var8_9));
                }
            }
            ++var5_6;
        }
        return var6_7.toString();
    }

    @UnsupportedAppUsage
    public static String gsm8BitUnpackedToString(byte[] arrby, int n, int n2) {
        return GsmAlphabet.gsm8BitUnpackedToString(arrby, n, n2, "");
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static String gsm8BitUnpackedToString(byte[] arrby, int n, int n2, String string2) {
        int n3;
        Object object;
        int n4;
        Object object2;
        int n5 = 0;
        StringBuilder stringBuilder = null;
        String string3 = null;
        if (!TextUtils.isEmpty(string2)) {
            n4 = n5;
            object2 = stringBuilder;
            object = string3;
            if (!string2.equalsIgnoreCase("us-ascii")) {
                n4 = n5;
                object2 = stringBuilder;
                object = string3;
                if (Charset.isSupported(string2)) {
                    n4 = 1;
                    object2 = Charset.forName(string2);
                    object = ByteBuffer.allocate(2);
                }
            }
        } else {
            object = string3;
            object2 = stringBuilder;
            n4 = n5;
        }
        string3 = sLanguageTables[0];
        string2 = sLanguageShiftTables[0];
        stringBuilder = new StringBuilder(n2);
        char c = '\u0000';
        for (n5 = n; n5 < n + n2 && (n3 = arrby[n5] & 255) != 255; ++n5) {
            if (n3 == 27) {
                if (c != '\u0000') {
                    stringBuilder.append(' ');
                    c = '\u0000';
                    continue;
                }
                c = '\u0001';
                continue;
            }
            if (c != '\u0000') {
                char c2 = n3 < string2.length() ? (c = (char)string2.charAt(n3)) : (c = ' ');
                if (c2 == ' ') {
                    if (n3 < string3.length()) {
                        stringBuilder.append(string3.charAt(n3));
                    } else {
                        stringBuilder.append(' ');
                    }
                } else {
                    stringBuilder.append(c2);
                }
            } else if (n4 != 0 && n3 >= 128 && n5 + 1 < n + n2) {
                ((Buffer)object).clear();
                ((ByteBuffer)object).put(arrby, n5, 2);
                ((Buffer)object).flip();
                stringBuilder.append(((Charset)object2).decode((ByteBuffer)object).toString());
                ++n5;
            } else if (n3 < string3.length()) {
                stringBuilder.append(string3.charAt(n3));
            } else {
                stringBuilder.append(' ');
            }
            c = '\u0000';
        }
        return stringBuilder.toString();
    }

    public static char gsmExtendedToChar(int n) {
        if (n == 27) {
            return ' ';
        }
        if (n >= 0 && n < 128) {
            char c = sLanguageShiftTables[0].charAt(n);
            if (c == ' ') {
                return sLanguageTables[0].charAt(n);
            }
            return c;
        }
        return ' ';
    }

    @UnsupportedAppUsage
    public static char gsmToChar(int n) {
        if (n >= 0 && n < 128) {
            return sLanguageTables[0].charAt(n);
        }
        return ' ';
    }

    public static boolean isGsmSeptets(char c) {
        if (sCharsToGsmTables[0].get(c, -1) != -1) {
            return true;
        }
        return sCharsToShiftTables[0].get(c, -1) != -1;
    }

    @UnsupportedAppUsage
    private static void packSmsChar(byte[] arrby, int n, int n2) {
        int n3 = n / 8;
        arrby[++n3] = (byte)(arrby[n3] | n2 << (n %= 8));
        if (n > 1) {
            arrby[n3 + 1] = (byte)(n2 >> 8 - n);
        }
    }

    public static void setEnabledLockingShiftTables(int[] arrn) {
        synchronized (GsmAlphabet.class) {
            sEnabledLockingShiftTables = arrn;
            sDisableCountryEncodingCheck = true;
            return;
        }
    }

    public static void setEnabledSingleShiftTables(int[] arrn) {
        synchronized (GsmAlphabet.class) {
            sEnabledSingleShiftTables = arrn;
            sDisableCountryEncodingCheck = true;
            sHighestEnabledSingleShiftCode = arrn.length > 0 ? arrn[arrn.length - 1] : 0;
            return;
        }
    }

    @UnsupportedAppUsage
    public static byte[] stringToGsm7BitPacked(String string2) throws EncodeException {
        return GsmAlphabet.stringToGsm7BitPacked(string2, 0, true, 0, 0);
    }

    public static byte[] stringToGsm7BitPacked(String string2, int n, int n2) throws EncodeException {
        return GsmAlphabet.stringToGsm7BitPacked(string2, 0, true, n, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public static byte[] stringToGsm7BitPacked(String string2, int n, boolean bl, int n2, int n3) throws EncodeException {
        int n4 = string2.length();
        int n5 = GsmAlphabet.countGsmSeptetsUsingTables(string2, bl ^ true, n2, n3);
        if (n5 == -1) throw new EncodeException("countGsmSeptetsUsingTables(): unencodable char");
        int n6 = n5 + n;
        if (n6 > 255) throw new EncodeException("Payload cannot exceed 255 septets", 1);
        byte[] arrby = new byte[(n6 * 7 + 7) / 8 + 1];
        SparseIntArray sparseIntArray = sCharsToGsmTables[n2];
        SparseIntArray sparseIntArray2 = sCharsToShiftTables[n3];
        n3 = n;
        n2 = n * 7;
        n = n3;
        for (n5 = 0; n5 < n4 && n < n6; ++n5) {
            char c = string2.charAt(n5);
            int n7 = sparseIntArray.get(c, -1);
            int n8 = n;
            int n9 = n2;
            n3 = n7;
            if (n7 == -1) {
                n3 = sparseIntArray2.get(c, -1);
                if (n3 == -1) {
                    if (bl) throw new EncodeException("stringToGsm7BitPacked(): unencodable char");
                    n3 = sparseIntArray.get(32, 32);
                    n8 = n;
                    n9 = n2;
                } else {
                    GsmAlphabet.packSmsChar(arrby, n2, 27);
                    n9 = n2 + 7;
                    n8 = n + 1;
                }
            }
            GsmAlphabet.packSmsChar(arrby, n9, n3);
            n = n8 + 1;
            n2 = n9 + 7;
        }
        arrby[0] = (byte)n6;
        return arrby;
    }

    public static byte[] stringToGsm7BitPackedWithHeader(String string2, byte[] arrby) throws EncodeException {
        return GsmAlphabet.stringToGsm7BitPackedWithHeader(string2, arrby, 0, 0);
    }

    @UnsupportedAppUsage
    public static byte[] stringToGsm7BitPackedWithHeader(String arrby, byte[] arrby2, int n, int n2) throws EncodeException {
        if (arrby2 != null && arrby2.length != 0) {
            arrby = GsmAlphabet.stringToGsm7BitPacked((String)arrby, ((arrby2.length + 1) * 8 + 6) / 7, true, n, n2);
            arrby[1] = (byte)arrby2.length;
            System.arraycopy(arrby2, 0, arrby, 2, arrby2.length);
            return arrby;
        }
        return GsmAlphabet.stringToGsm7BitPacked((String)arrby, n, n2);
    }

    @UnsupportedAppUsage
    public static byte[] stringToGsm8BitPacked(String string2) {
        byte[] arrby = new byte[GsmAlphabet.countGsmSeptetsUsingTables(string2, true, 0, 0)];
        GsmAlphabet.stringToGsm8BitUnpackedField(string2, arrby, 0, arrby.length);
        return arrby;
    }

    public static void stringToGsm8BitUnpackedField(String string2, byte[] arrby, int n, int n2) {
        int n3;
        int n4 = n;
        SparseIntArray sparseIntArray = sCharsToGsmTables[0];
        SparseIntArray sparseIntArray2 = sCharsToShiftTables[0];
        int n5 = 0;
        int n6 = string2.length();
        do {
            n3 = n4;
            if (n5 >= n6) break;
            n3 = n4;
            if (n4 - n >= n2) break;
            char c = string2.charAt(n5);
            int n7 = sparseIntArray.get(c, -1);
            int n8 = n4;
            n3 = n7;
            if (n7 == -1) {
                n3 = sparseIntArray2.get(c, -1);
                if (n3 == -1) {
                    n3 = sparseIntArray.get(32, 32);
                    n8 = n4;
                } else {
                    if (n4 + 1 - n >= n2) {
                        n3 = n4;
                        break;
                    }
                    arrby[n4] = (byte)27;
                    n8 = n4 + 1;
                }
            }
            arrby[n8] = (byte)n3;
            ++n5;
            n4 = n8 + 1;
        } while (true);
        while (n3 - n < n2) {
            arrby[n3] = (byte)-1;
            ++n3;
        }
    }

    private static class LanguagePairCount {
        @UnsupportedAppUsage
        final int languageCode;
        @UnsupportedAppUsage
        final int[] septetCounts;
        @UnsupportedAppUsage
        final int[] unencodableCounts;

        @UnsupportedAppUsage
        LanguagePairCount(int n) {
            this.languageCode = n;
            int n2 = sHighestEnabledSingleShiftCode;
            this.septetCounts = new int[n2 + 1];
            this.unencodableCounts = new int[n2 + 1];
            int n3 = 0;
            for (int i = 1; i <= n2; ++i) {
                if (sEnabledSingleShiftTables[n3] == i) {
                    ++n3;
                    continue;
                }
                this.septetCounts[i] = -1;
            }
            if (n == 1 && n2 >= 1) {
                this.septetCounts[1] = -1;
            } else if (n == 3 && n2 >= 2) {
                this.septetCounts[2] = -1;
            }
        }
    }

    public static class TextEncodingDetails {
        @UnsupportedAppUsage
        public int codeUnitCount;
        @UnsupportedAppUsage
        public int codeUnitSize;
        @UnsupportedAppUsage
        public int codeUnitsRemaining;
        @UnsupportedAppUsage
        public int languageShiftTable;
        @UnsupportedAppUsage
        public int languageTable;
        @UnsupportedAppUsage
        public int msgCount;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TextEncodingDetails { msgCount=");
            stringBuilder.append(this.msgCount);
            stringBuilder.append(", codeUnitCount=");
            stringBuilder.append(this.codeUnitCount);
            stringBuilder.append(", codeUnitsRemaining=");
            stringBuilder.append(this.codeUnitsRemaining);
            stringBuilder.append(", codeUnitSize=");
            stringBuilder.append(this.codeUnitSize);
            stringBuilder.append(", languageTable=");
            stringBuilder.append(this.languageTable);
            stringBuilder.append(", languageShiftTable=");
            stringBuilder.append(this.languageShiftTable);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

}

