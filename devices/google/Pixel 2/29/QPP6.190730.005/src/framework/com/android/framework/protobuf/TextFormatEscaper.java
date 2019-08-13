/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;

final class TextFormatEscaper {
    private TextFormatEscaper() {
    }

    static String escapeBytes(ByteString byteString) {
        return TextFormatEscaper.escapeBytes(new ByteSequence(){

            @Override
            public byte byteAt(int n) {
                return ByteString.this.byteAt(n);
            }

            @Override
            public int size() {
                return ByteString.this.size();
            }
        });
    }

    static String escapeBytes(ByteSequence byteSequence) {
        StringBuilder stringBuilder = new StringBuilder(byteSequence.size());
        for (int i = 0; i < byteSequence.size(); ++i) {
            byte by = byteSequence.byteAt(i);
            if (by != 34) {
                if (by != 39) {
                    if (by != 92) {
                        switch (by) {
                            default: {
                                if (by >= 32 && by <= 126) {
                                    stringBuilder.append((char)by);
                                    break;
                                }
                                stringBuilder.append('\\');
                                stringBuilder.append((char)((by >>> 6 & 3) + 48));
                                stringBuilder.append((char)((by >>> 3 & 7) + 48));
                                stringBuilder.append((char)((by & 7) + 48));
                                break;
                            }
                            case 13: {
                                stringBuilder.append("\\r");
                                break;
                            }
                            case 12: {
                                stringBuilder.append("\\f");
                                break;
                            }
                            case 11: {
                                stringBuilder.append("\\v");
                                break;
                            }
                            case 10: {
                                stringBuilder.append("\\n");
                                break;
                            }
                            case 9: {
                                stringBuilder.append("\\t");
                                break;
                            }
                            case 8: {
                                stringBuilder.append("\\b");
                                break;
                            }
                            case 7: {
                                stringBuilder.append("\\a");
                                break;
                            }
                        }
                        continue;
                    }
                    stringBuilder.append("\\\\");
                    continue;
                }
                stringBuilder.append("\\'");
                continue;
            }
            stringBuilder.append("\\\"");
        }
        return stringBuilder.toString();
    }

    static String escapeBytes(byte[] arrby) {
        return TextFormatEscaper.escapeBytes(new ByteSequence(){

            @Override
            public byte byteAt(int n) {
                return val$input[n];
            }

            @Override
            public int size() {
                return val$input.length;
            }
        });
    }

    static String escapeDoubleQuotesAndBackslashes(String string2) {
        return string2.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    static String escapeText(String string2) {
        return TextFormatEscaper.escapeBytes(ByteString.copyFromUtf8(string2));
    }

    private static interface ByteSequence {
        public byte byteAt(int var1);

        public int size();
    }

}

