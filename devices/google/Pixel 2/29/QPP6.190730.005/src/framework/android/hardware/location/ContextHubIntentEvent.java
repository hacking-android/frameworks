/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.content.Intent;
import android.hardware.location.ContextHubInfo;
import android.hardware.location.NanoAppMessage;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

@SystemApi
public class ContextHubIntentEvent {
    private final ContextHubInfo mContextHubInfo;
    private final int mEventType;
    private final int mNanoAppAbortCode;
    private final long mNanoAppId;
    private final NanoAppMessage mNanoAppMessage;

    private ContextHubIntentEvent(ContextHubInfo contextHubInfo, int n) {
        this(contextHubInfo, n, -1L, null, -1);
    }

    private ContextHubIntentEvent(ContextHubInfo contextHubInfo, int n, long l) {
        this(contextHubInfo, n, l, null, -1);
    }

    private ContextHubIntentEvent(ContextHubInfo contextHubInfo, int n, long l, int n2) {
        this(contextHubInfo, n, l, null, n2);
    }

    private ContextHubIntentEvent(ContextHubInfo contextHubInfo, int n, long l, NanoAppMessage nanoAppMessage) {
        this(contextHubInfo, n, l, nanoAppMessage, -1);
    }

    private ContextHubIntentEvent(ContextHubInfo contextHubInfo, int n, long l, NanoAppMessage nanoAppMessage, int n2) {
        this.mContextHubInfo = contextHubInfo;
        this.mEventType = n;
        this.mNanoAppId = l;
        this.mNanoAppMessage = nanoAppMessage;
        this.mNanoAppAbortCode = n2;
    }

    public static ContextHubIntentEvent fromIntent(Intent object) {
        Preconditions.checkNotNull(object, "Intent cannot be null");
        ContextHubIntentEvent.hasExtraOrThrow((Intent)object, "android.hardware.location.extra.CONTEXT_HUB_INFO");
        ContextHubInfo contextHubInfo = (ContextHubInfo)((Intent)object).getParcelableExtra("android.hardware.location.extra.CONTEXT_HUB_INFO");
        if (contextHubInfo != null) {
            int n = ContextHubIntentEvent.getIntExtraOrThrow((Intent)object, "android.hardware.location.extra.EVENT_TYPE");
            switch (n) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown intent event type ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                case 6: {
                    object = new ContextHubIntentEvent(contextHubInfo, n);
                    break;
                }
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    long l = ContextHubIntentEvent.getLongExtraOrThrow((Intent)object, "android.hardware.location.extra.NANOAPP_ID");
                    if (n == 5) {
                        ContextHubIntentEvent.hasExtraOrThrow((Intent)object, "android.hardware.location.extra.MESSAGE");
                        object = (NanoAppMessage)((Intent)object).getParcelableExtra("android.hardware.location.extra.MESSAGE");
                        if (object != null) {
                            object = new ContextHubIntentEvent(contextHubInfo, n, l, (NanoAppMessage)object);
                            break;
                        }
                        throw new IllegalArgumentException("NanoAppMessage extra was null");
                    }
                    object = n == 4 ? new ContextHubIntentEvent(contextHubInfo, n, l, ContextHubIntentEvent.getIntExtraOrThrow((Intent)object, "android.hardware.location.extra.NANOAPP_ABORT_CODE")) : new ContextHubIntentEvent(contextHubInfo, n, l);
                }
            }
            return object;
        }
        throw new IllegalArgumentException("ContextHubInfo extra was null");
    }

    private static int getIntExtraOrThrow(Intent intent, String string2) {
        ContextHubIntentEvent.hasExtraOrThrow(intent, string2);
        return intent.getIntExtra(string2, -1);
    }

    private static long getLongExtraOrThrow(Intent intent, String string2) {
        ContextHubIntentEvent.hasExtraOrThrow(intent, string2);
        return intent.getLongExtra(string2, -1L);
    }

    private static void hasExtraOrThrow(Intent object, String string2) {
        if (((Intent)object).hasExtra(string2)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Intent did not have extra: ");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = true;
        if (object == this) {
            return true;
        }
        boolean bl3 = bl = false;
        if (object instanceof ContextHubIntentEvent) {
            object = (ContextHubIntentEvent)object;
            bl3 = bl;
            if (((ContextHubIntentEvent)object).getEventType() == this.mEventType) {
                bl3 = bl;
                if (((ContextHubIntentEvent)object).getContextHubInfo().equals(this.mContextHubInfo)) {
                    boolean bl4;
                    bl = true;
                    if (this.mEventType != 6) {
                        bl4 = ((ContextHubIntentEvent)object).getNanoAppId() == this.mNanoAppId;
                        bl = true & bl4;
                    }
                    bl3 = bl;
                    if (this.mEventType == 4) {
                        bl4 = ((ContextHubIntentEvent)object).getNanoAppAbortCode() == this.mNanoAppAbortCode ? bl2 : false;
                        bl3 = bl4 & bl;
                    }
                    bl = bl3;
                    try {
                        if (this.mEventType == 5) {
                            bl = ((ContextHubIntentEvent)object).getNanoAppMessage().equals(this.mNanoAppMessage);
                            bl &= bl3;
                        }
                        bl3 = bl;
                    }
                    catch (UnsupportedOperationException unsupportedOperationException) {
                        bl3 = false;
                    }
                }
            }
        }
        return bl3;
    }

    public ContextHubInfo getContextHubInfo() {
        return this.mContextHubInfo;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public int getNanoAppAbortCode() {
        if (this.mEventType == 4) {
            return this.mNanoAppAbortCode;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot invoke getNanoAppAbortCode() on non-abort event: ");
        stringBuilder.append(this.mEventType);
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public long getNanoAppId() {
        if (this.mEventType != 6) {
            return this.mNanoAppId;
        }
        throw new UnsupportedOperationException("Cannot invoke getNanoAppId() on Context Hub reset event");
    }

    public NanoAppMessage getNanoAppMessage() {
        if (this.mEventType == 5) {
            return this.mNanoAppMessage;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot invoke getNanoAppMessage() on non-message event: ");
        stringBuilder.append(this.mEventType);
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("ContextHubIntentEvent[eventType = ");
        ((StringBuilder)charSequence).append(this.mEventType);
        ((StringBuilder)charSequence).append(", contextHubId = ");
        ((StringBuilder)charSequence).append(this.mContextHubInfo.getId());
        CharSequence charSequence2 = charSequence = ((StringBuilder)charSequence).toString();
        if (this.mEventType != 6) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(", nanoAppId = 0x");
            ((StringBuilder)charSequence2).append(Long.toHexString(this.mNanoAppId));
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (this.mEventType == 4) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(", nanoAppAbortCode = ");
            ((StringBuilder)charSequence).append(this.mNanoAppAbortCode);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (this.mEventType == 5) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(", nanoAppMessage = ");
            ((StringBuilder)charSequence2).append(this.mNanoAppMessage);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("]");
        return ((StringBuilder)charSequence).toString();
    }
}

