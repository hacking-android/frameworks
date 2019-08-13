/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.inputmethod;

import java.util.StringJoiner;

public final class InputMethodDebug {
    private InputMethodDebug() {
    }

    public static String softInputModeToString(int n) {
        StringBuilder stringBuilder;
        StringJoiner stringJoiner = new StringJoiner("|");
        int n2 = n & 15;
        int n3 = n & 240;
        n = (n & 256) != 0 ? 1 : 0;
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 != 4) {
                            if (n2 != 5) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("STATE_UNKNOWN(");
                                stringBuilder.append(n2);
                                stringBuilder.append(")");
                                stringJoiner.add(stringBuilder.toString());
                            } else {
                                stringJoiner.add("STATE_ALWAYS_VISIBLE");
                            }
                        } else {
                            stringJoiner.add("STATE_VISIBLE");
                        }
                    } else {
                        stringJoiner.add("STATE_ALWAYS_HIDDEN");
                    }
                } else {
                    stringJoiner.add("STATE_HIDDEN");
                }
            } else {
                stringJoiner.add("STATE_UNCHANGED");
            }
        } else {
            stringJoiner.add("STATE_UNSPECIFIED");
        }
        if (n3 != 0) {
            if (n3 != 16) {
                if (n3 != 32) {
                    if (n3 != 48) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("ADJUST_UNKNOWN(");
                        stringBuilder.append(n3);
                        stringBuilder.append(")");
                        stringJoiner.add(stringBuilder.toString());
                    } else {
                        stringJoiner.add("ADJUST_NOTHING");
                    }
                } else {
                    stringJoiner.add("ADJUST_PAN");
                }
            } else {
                stringJoiner.add("ADJUST_RESIZE");
            }
        } else {
            stringJoiner.add("ADJUST_UNSPECIFIED");
        }
        if (n != 0) {
            stringJoiner.add("IS_FORWARD_NAVIGATION");
        }
        return stringJoiner.setEmptyValue("(none)").toString();
    }

    public static String startInputFlagsToString(int n) {
        StringJoiner stringJoiner = new StringJoiner("|");
        if ((n & 1) != 0) {
            stringJoiner.add("VIEW_HAS_FOCUS");
        }
        if ((n & 2) != 0) {
            stringJoiner.add("IS_TEXT_EDITOR");
        }
        if ((n & 4) != 0) {
            stringJoiner.add("FIRST_WINDOW_FOCUS_GAIN");
        }
        if ((n & 8) != 0) {
            stringJoiner.add("INITIAL_CONNECTION");
        }
        return stringJoiner.setEmptyValue("(none)").toString();
    }

    public static String startInputReasonToString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown=");
                stringBuilder.append(n);
                return stringBuilder.toString();
            }
            case 9: {
                return "SESSION_CREATED_BY_IME";
            }
            case 8: {
                return "DEACTIVATED_BY_IMMS";
            }
            case 7: {
                return "ACTIVATED_BY_IMMS";
            }
            case 6: {
                return "UNBOUND_FROM_IMMS";
            }
            case 5: {
                return "BOUND_TO_IMMS";
            }
            case 4: {
                return "CHECK_FOCUS";
            }
            case 3: {
                return "APP_CALLED_RESTART_INPUT_API";
            }
            case 2: {
                return "WINDOW_FOCUS_GAIN_REPORT_ONLY";
            }
            case 1: {
                return "WINDOW_FOCUS_GAIN";
            }
            case 0: 
        }
        return "UNSPECIFIED";
    }

    public static String unbindReasonToString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown=");
                stringBuilder.append(n);
                return stringBuilder.toString();
            }
            case 6: {
                return "SWITCH_USER";
            }
            case 5: {
                return "SWITCH_IME_FAILED";
            }
            case 4: {
                return "NO_IME";
            }
            case 3: {
                return "DISCONNECT_IME";
            }
            case 2: {
                return "SWITCH_IME";
            }
            case 1: {
                return "SWITCH_CLIENT";
            }
            case 0: 
        }
        return "UNSPECIFIED";
    }
}

