/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.messages.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

public interface SystemMessageProto {

    public static final class SystemMessage
    extends MessageNano {
        public static final int NOTE_ACCOUNT_CREDENTIAL_PERMISSION = 38;
        public static final int NOTE_ACCOUNT_REQUIRE_SIGNIN = 37;
        public static final int NOTE_ADB_ACTIVE = 26;
        public static final int NOTE_AUTO_SAVER_SUGGESTION = 49;
        public static final int NOTE_BAD_CHARGER = 2;
        public static final int NOTE_BATTERY_SAVER_WARNING = 1200;
        public static final int NOTE_CARRIER_NETWORK_AVAILABLE = 46;
        public static final int NOTE_CAR_MODE_DISABLE = 10;
        public static final int NOTE_DUMP_HEAP_NOTIFICATION = 12;
        public static final int NOTE_FBE_ENCRYPTED_NOTIFICATION = 9;
        public static final int NOTE_FOREGROUND_SERVICES = 40;
        public static final int NOTE_GLOBAL_SCREENSHOT = 1;
        public static final int NOTE_HEAVY_WEIGHT_NOTIFICATION = 11;
        public static final int NOTE_HIDDEN_NOTIFICATIONS = 5;
        public static final int NOTE_HIGH_TEMP = 4;
        public static final int NOTE_INSTANT_APPS = 7;
        public static final int NOTE_LOGOUT_USER = 1011;
        public static final int NOTE_LOW_STORAGE = 23;
        public static final int NOTE_NETWORK_AVAILABLE = 17303299;
        public static final int NOTE_NETWORK_LOGGED_IN = 744;
        public static final int NOTE_NETWORK_LOGGING = 1002;
        public static final int NOTE_NETWORK_LOST_INTERNET = 742;
        public static final int NOTE_NETWORK_NO_INTERNET = 741;
        public static final int NOTE_NETWORK_PARTIAL_CONNECTIVITY = 745;
        public static final int NOTE_NETWORK_SIGN_IN = 740;
        public static final int NOTE_NETWORK_SUGGESTION_AVAILABLE = 51;
        public static final int NOTE_NETWORK_SWITCH = 743;
        public static final int NOTE_NET_LIMIT = 35;
        public static final int NOTE_NET_LIMIT_SNOOZED = 36;
        public static final int NOTE_NET_RAPID = 45;
        public static final int NOTE_NET_WARNING = 34;
        public static final int NOTE_PACKAGE_STATE = 21;
        public static final int NOTE_PLUGIN = 6;
        public static final int NOTE_POWER_LOW = 3;
        public static final int NOTE_PROFILE_WIPED = 1001;
        public static final int NOTE_REMOTE_BUGREPORT = 678432343;
        public static final int NOTE_REMOVE_GUEST = 1010;
        public static final int NOTE_RETAIL_RESET = 24;
        public static final int NOTE_SELECT_INPUT_METHOD = 8;
        public static final int NOTE_SELECT_KEYBOARD_LAYOUT = 19;
        public static final int NOTE_SOFTAP_CONFIG_CHANGED = 50;
        public static final int NOTE_SSL_CERT_INFO = 33;
        public static final int NOTE_STORAGE_DISK = 1396986699;
        public static final int NOTE_STORAGE_MOVE = 1397575510;
        public static final int NOTE_STORAGE_PRIVATE = 1397772886;
        public static final int NOTE_STORAGE_PUBLIC = 1397773634;
        public static final int NOTE_SYNC_ERROR = 18;
        public static final int NOTE_SYSTEM_UPGRADING = 13;
        public static final int NOTE_TEST_HARNESS_MODE_ENABLED = 54;
        public static final int NOTE_TETHER_BLUETOOTH = 16;
        public static final int NOTE_TETHER_GENERAL = 14;
        public static final int NOTE_TETHER_USB = 15;
        public static final int NOTE_THERMAL_SHUTDOWN = 39;
        public static final int NOTE_TV_PIP = 1100;
        public static final int NOTE_UNKNOWN = 0;
        public static final int NOTE_USB_ACCESSORY = 30;
        public static final int NOTE_USB_AUDIO_ACCESSORY_NOT_SUPPORTED = 41;
        public static final int NOTE_USB_CHARGING = 32;
        public static final int NOTE_USB_CONTAMINANT_DETECTED = 52;
        public static final int NOTE_USB_CONTAMINANT_NOT_DETECTED = 53;
        public static final int NOTE_USB_MIDI = 29;
        public static final int NOTE_USB_MTP = 27;
        public static final int NOTE_USB_MTP_TAP = 25;
        public static final int NOTE_USB_PTP = 28;
        public static final int NOTE_USB_SUPPLYING = 31;
        public static final int NOTE_USB_TETHER = 47;
        public static final int NOTE_VPN_DISCONNECTED = 17;
        public static final int NOTE_VPN_STATUS = 20;
        public static final int NOTE_WIFI_WAKE_ONBOARD = 43;
        public static final int NOTE_WIFI_WAKE_TURNED_BACK_ON = 44;
        public static final int NOTE_WIFI_WRONG_PASSWORD = 42;
        public static final int NOTE_ZEN_UPGRADE = 48;
        private static volatile SystemMessage[] _emptyArray;

        public SystemMessage() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static SystemMessage[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new SystemMessage[0];
                return _emptyArray;
            }
        }

        public static SystemMessage parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new SystemMessage().mergeFrom(codedInputByteBufferNano);
        }

        public static SystemMessage parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new SystemMessage(), arrby);
        }

        public SystemMessage clear() {
            this.cachedSize = -1;
            return this;
        }

        @Override
        public SystemMessage mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                return this;
            }
            return this;
        }
    }

}

