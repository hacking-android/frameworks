/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.cat.AppInterface;
import com.android.internal.telephony.cat.BIPClientParams;
import com.android.internal.telephony.cat.CallSetupParams;
import com.android.internal.telephony.cat.CommandDetails;
import com.android.internal.telephony.cat.CommandParams;
import com.android.internal.telephony.cat.DisplayTextParams;
import com.android.internal.telephony.cat.GetInputParams;
import com.android.internal.telephony.cat.Input;
import com.android.internal.telephony.cat.LaunchBrowserMode;
import com.android.internal.telephony.cat.LaunchBrowserParams;
import com.android.internal.telephony.cat.Menu;
import com.android.internal.telephony.cat.PlayToneParams;
import com.android.internal.telephony.cat.SelectItemParams;
import com.android.internal.telephony.cat.SetEventListParams;
import com.android.internal.telephony.cat.TextMessage;
import com.android.internal.telephony.cat.ToneSettings;

public class CatCmdMessage
implements Parcelable {
    public static final Parcelable.Creator<CatCmdMessage> CREATOR = new Parcelable.Creator<CatCmdMessage>(){

        public CatCmdMessage createFromParcel(Parcel parcel) {
            return new CatCmdMessage(parcel);
        }

        public CatCmdMessage[] newArray(int n) {
            return new CatCmdMessage[n];
        }
    };
    private BrowserSettings mBrowserSettings = null;
    @UnsupportedAppUsage
    private CallSettings mCallSettings = null;
    @UnsupportedAppUsage
    CommandDetails mCmdDet;
    @UnsupportedAppUsage
    private Input mInput;
    private boolean mLoadIconFailed;
    @UnsupportedAppUsage
    private Menu mMenu;
    private SetupEventListSettings mSetupEventListSettings = null;
    @UnsupportedAppUsage
    private TextMessage mTextMsg;
    private ToneSettings mToneSettings = null;

    public CatCmdMessage(Parcel parcel) {
        boolean bl = false;
        this.mLoadIconFailed = false;
        this.mCmdDet = (CommandDetails)parcel.readParcelable(null);
        this.mTextMsg = (TextMessage)parcel.readParcelable(null);
        this.mMenu = (Menu)parcel.readParcelable(null);
        this.mInput = (Input)parcel.readParcelable(null);
        if (parcel.readByte() == 1) {
            bl = true;
        }
        this.mLoadIconFailed = bl;
        int n = 2.$SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType[this.getCmdType().ordinal()];
        if (n != 13) {
            if (n != 14) {
                if (n != 16) {
                    if (n == 21) {
                        this.mSetupEventListSettings = new SetupEventListSettings();
                        int n2 = parcel.readInt();
                        this.mSetupEventListSettings.eventList = new int[n2];
                        for (n = 0; n < n2; ++n) {
                            this.mSetupEventListSettings.eventList[n] = parcel.readInt();
                        }
                    }
                } else {
                    this.mCallSettings = new CallSettings();
                    this.mCallSettings.confirmMsg = (TextMessage)parcel.readParcelable(null);
                    this.mCallSettings.callMsg = (TextMessage)parcel.readParcelable(null);
                }
            } else {
                this.mToneSettings = (ToneSettings)parcel.readParcelable(null);
            }
        } else {
            this.mBrowserSettings = new BrowserSettings();
            this.mBrowserSettings.url = parcel.readString();
            this.mBrowserSettings.mode = LaunchBrowserMode.values()[parcel.readInt()];
        }
    }

    CatCmdMessage(CommandParams commandParams) {
        this.mLoadIconFailed = false;
        this.mCmdDet = commandParams.mCmdDet;
        this.mLoadIconFailed = commandParams.mLoadIconFailed;
        switch (this.getCmdType()) {
            default: {
                break;
            }
            case SET_UP_EVENT_LIST: {
                this.mSetupEventListSettings = new SetupEventListSettings();
                this.mSetupEventListSettings.eventList = ((SetEventListParams)commandParams).mEventInfo;
                break;
            }
            case OPEN_CHANNEL: 
            case CLOSE_CHANNEL: 
            case RECEIVE_DATA: 
            case SEND_DATA: {
                this.mTextMsg = ((BIPClientParams)commandParams).mTextMsg;
                break;
            }
            case SET_UP_CALL: {
                this.mCallSettings = new CallSettings();
                this.mCallSettings.confirmMsg = ((CallSetupParams)commandParams).mConfirmMsg;
                this.mCallSettings.callMsg = ((CallSetupParams)commandParams).mCallMsg;
                break;
            }
            case GET_CHANNEL_STATUS: {
                this.mTextMsg = ((CallSetupParams)commandParams).mConfirmMsg;
                break;
            }
            case PLAY_TONE: {
                commandParams = (PlayToneParams)commandParams;
                this.mToneSettings = ((PlayToneParams)commandParams).mSettings;
                this.mTextMsg = ((PlayToneParams)commandParams).mTextMsg;
                break;
            }
            case LAUNCH_BROWSER: {
                this.mTextMsg = ((LaunchBrowserParams)commandParams).mConfirmMsg;
                this.mBrowserSettings = new BrowserSettings();
                this.mBrowserSettings.url = ((LaunchBrowserParams)commandParams).mUrl;
                this.mBrowserSettings.mode = ((LaunchBrowserParams)commandParams).mMode;
                break;
            }
            case GET_INPUT: 
            case GET_INKEY: {
                this.mInput = ((GetInputParams)commandParams).mInput;
                break;
            }
            case DISPLAY_TEXT: 
            case SET_UP_IDLE_MODE_TEXT: 
            case SEND_DTMF: 
            case SEND_SMS: 
            case REFRESH: 
            case RUN_AT: 
            case SEND_SS: 
            case SEND_USSD: {
                this.mTextMsg = ((DisplayTextParams)commandParams).mTextMsg;
                break;
            }
            case SET_UP_MENU: 
            case SELECT_ITEM: {
                this.mMenu = ((SelectItemParams)commandParams).mMenu;
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public Input geInput() {
        return this.mInput;
    }

    @UnsupportedAppUsage
    public TextMessage geTextMessage() {
        return this.mTextMsg;
    }

    public BrowserSettings getBrowserSettings() {
        return this.mBrowserSettings;
    }

    @UnsupportedAppUsage
    public CallSettings getCallSettings() {
        return this.mCallSettings;
    }

    @UnsupportedAppUsage
    public AppInterface.CommandType getCmdType() {
        return AppInterface.CommandType.fromInt(this.mCmdDet.typeOfCommand);
    }

    public Menu getMenu() {
        return this.mMenu;
    }

    @UnsupportedAppUsage
    public SetupEventListSettings getSetEventList() {
        return this.mSetupEventListSettings;
    }

    public ToneSettings getToneSettings() {
        return this.mToneSettings;
    }

    @UnsupportedAppUsage
    public boolean hasIconLoadFailed() {
        return this.mLoadIconFailed;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable((Parcelable)this.mCmdDet, 0);
        parcel.writeParcelable((Parcelable)this.mTextMsg, 0);
        parcel.writeParcelable((Parcelable)this.mMenu, 0);
        parcel.writeParcelable((Parcelable)this.mInput, 0);
        parcel.writeByte((byte)(this.mLoadIconFailed ? 1 : 0));
        n = 2.$SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType[this.getCmdType().ordinal()];
        if (n != 13) {
            if (n != 14) {
                if (n != 16) {
                    if (n == 21) {
                        parcel.writeIntArray(this.mSetupEventListSettings.eventList);
                    }
                } else {
                    parcel.writeParcelable((Parcelable)this.mCallSettings.confirmMsg, 0);
                    parcel.writeParcelable((Parcelable)this.mCallSettings.callMsg, 0);
                }
            } else {
                parcel.writeParcelable((Parcelable)this.mToneSettings, 0);
            }
        } else {
            parcel.writeString(this.mBrowserSettings.url);
            parcel.writeInt(this.mBrowserSettings.mode.ordinal());
        }
    }

    public class BrowserSettings {
        public LaunchBrowserMode mode;
        public String url;
    }

    public final class BrowserTerminationCauses {
        public static final int ERROR_TERMINATION = 1;
        public static final int USER_TERMINATION = 0;
    }

    public class CallSettings {
        @UnsupportedAppUsage
        public TextMessage callMsg;
        @UnsupportedAppUsage
        public TextMessage confirmMsg;
    }

    public final class SetupEventListConstants {
        public static final int BROWSER_TERMINATION_EVENT = 8;
        public static final int BROWSING_STATUS_EVENT = 15;
        public static final int IDLE_SCREEN_AVAILABLE_EVENT = 5;
        public static final int LANGUAGE_SELECTION_EVENT = 7;
        public static final int USER_ACTIVITY_EVENT = 4;
    }

    public class SetupEventListSettings {
        @UnsupportedAppUsage
        public int[] eventList;
    }

}

