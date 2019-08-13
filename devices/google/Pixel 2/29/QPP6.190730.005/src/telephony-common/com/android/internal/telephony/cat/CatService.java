/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.ActivityManagerNative
 *  android.app.IActivityManager
 *  android.app.backup.BackupManager
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.LocaleList
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.backup.BackupManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.cat.AppInterface;
import com.android.internal.telephony.cat.BIPClientParams;
import com.android.internal.telephony.cat.CallSetupParams;
import com.android.internal.telephony.cat.CatCmdMessage;
import com.android.internal.telephony.cat.CatLog;
import com.android.internal.telephony.cat.CatResponseMessage;
import com.android.internal.telephony.cat.CommandDetails;
import com.android.internal.telephony.cat.CommandParams;
import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.cat.DTTZResponseData;
import com.android.internal.telephony.cat.DisplayTextParams;
import com.android.internal.telephony.cat.Duration;
import com.android.internal.telephony.cat.GetInkeyInputResponseData;
import com.android.internal.telephony.cat.Input;
import com.android.internal.telephony.cat.Item;
import com.android.internal.telephony.cat.LanguageParams;
import com.android.internal.telephony.cat.LanguageResponseData;
import com.android.internal.telephony.cat.LaunchBrowserParams;
import com.android.internal.telephony.cat.Menu;
import com.android.internal.telephony.cat.ResponseData;
import com.android.internal.telephony.cat.ResultCode;
import com.android.internal.telephony.cat.RilMessage;
import com.android.internal.telephony.cat.RilMessageDecoder;
import com.android.internal.telephony.cat.SelectItemResponseData;
import com.android.internal.telephony.cat.TextMessage;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccRefreshResponse;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.uicc.UiccProfile;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CatService
extends Handler
implements AppInterface {
    private static final boolean DBG = false;
    private static final int DEV_ID_DISPLAY = 2;
    private static final int DEV_ID_KEYPAD = 1;
    private static final int DEV_ID_NETWORK = 131;
    private static final int DEV_ID_TERMINAL = 130;
    private static final int DEV_ID_UICC = 129;
    protected static final int MSG_ID_ALPHA_NOTIFY = 9;
    protected static final int MSG_ID_CALL_SETUP = 4;
    protected static final int MSG_ID_EVENT_NOTIFY = 3;
    protected static final int MSG_ID_ICC_CHANGED = 8;
    private static final int MSG_ID_ICC_RECORDS_LOADED = 20;
    private static final int MSG_ID_ICC_REFRESH = 30;
    protected static final int MSG_ID_PROACTIVE_COMMAND = 2;
    static final int MSG_ID_REFRESH = 5;
    static final int MSG_ID_RESPONSE = 6;
    static final int MSG_ID_RIL_MSG_DECODED = 10;
    protected static final int MSG_ID_SESSION_END = 1;
    static final int MSG_ID_SIM_READY = 7;
    static final String STK_DEFAULT = "Default Message";
    private static IccRecords mIccRecords;
    private static UiccCardApplication mUiccApplication;
    @UnsupportedAppUsage
    private static CatService[] sInstance;
    @UnsupportedAppUsage
    private static final Object sInstanceLock;
    private IccCardStatus.CardState mCardState = IccCardStatus.CardState.CARDSTATE_ABSENT;
    @UnsupportedAppUsage
    private CommandsInterface mCmdIf;
    @UnsupportedAppUsage
    private Context mContext;
    @UnsupportedAppUsage
    private CatCmdMessage mCurrntCmd = null;
    @UnsupportedAppUsage
    private CatCmdMessage mMenuCmd = null;
    @UnsupportedAppUsage
    private RilMessageDecoder mMsgDecoder = null;
    @UnsupportedAppUsage
    private int mSlotId;
    @UnsupportedAppUsage
    private boolean mStkAppInstalled = false;
    @UnsupportedAppUsage
    private UiccController mUiccController;

    static {
        sInstanceLock = new Object();
        sInstance = null;
    }

    private CatService(CommandsInterface object, UiccCardApplication uiccCardApplication, IccRecords iccRecords, Context context, IccFileHandler iccFileHandler, UiccProfile uiccProfile, int n) {
        if (object != null && uiccCardApplication != null && iccRecords != null && context != null && iccFileHandler != null && uiccProfile != null) {
            this.mCmdIf = object;
            this.mContext = context;
            this.mSlotId = n;
            this.mMsgDecoder = RilMessageDecoder.getInstance(this, iccFileHandler, n);
            object = this.mMsgDecoder;
            if (object == null) {
                CatLog.d(this, "Null RilMessageDecoder instance");
                return;
            }
            object.start();
            this.mCmdIf.setOnCatSessionEnd(this, 1, null);
            this.mCmdIf.setOnCatProactiveCmd(this, 2, null);
            this.mCmdIf.setOnCatEvent(this, 3, null);
            this.mCmdIf.setOnCatCallSetUp(this, 4, null);
            this.mCmdIf.registerForIccRefresh(this, 30, null);
            this.mCmdIf.setOnCatCcAlphaNotify(this, 9, null);
            mIccRecords = iccRecords;
            mUiccApplication = uiccCardApplication;
            mIccRecords.registerForRecordsLoaded(this, 20, null);
            object = new StringBuilder();
            ((StringBuilder)object).append("registerForRecordsLoaded slotid=");
            ((StringBuilder)object).append(this.mSlotId);
            ((StringBuilder)object).append(" instance:");
            ((StringBuilder)object).append(this);
            CatLog.d(this, ((StringBuilder)object).toString());
            this.mUiccController = UiccController.getInstance();
            this.mUiccController.registerForIccChanged(this, 8, null);
            this.mStkAppInstalled = this.isStkAppInstalled();
            object = new StringBuilder();
            ((StringBuilder)object).append("Running CAT service on Slotid: ");
            ((StringBuilder)object).append(this.mSlotId);
            ((StringBuilder)object).append(". STK app installed:");
            ((StringBuilder)object).append(this.mStkAppInstalled);
            CatLog.d(this, ((StringBuilder)object).toString());
            return;
        }
        throw new NullPointerException("Service: Input parameters must not be null");
    }

    private void broadcastAlphaMessage(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Broadcasting CAT Alpha message from card: ");
        stringBuilder.append(string);
        CatLog.d(this, stringBuilder.toString());
        stringBuilder = new Intent("com.android.internal.stk.alpha_notify");
        stringBuilder.addFlags(268435456);
        stringBuilder.putExtra("alpha_string", string);
        stringBuilder.putExtra("SLOT_ID", this.mSlotId);
        stringBuilder.setComponent(AppInterface.getDefaultSTKApplication());
        this.mContext.sendBroadcast((Intent)stringBuilder, "android.permission.RECEIVE_STK_COMMANDS");
    }

    private void broadcastCardStateAndIccRefreshResp(IccCardStatus.CardState cardState, IccRefreshResponse object) {
        Intent intent = new Intent("com.android.internal.stk.icc_status_change");
        boolean bl = cardState == IccCardStatus.CardState.CARDSTATE_PRESENT;
        if (object != null) {
            intent.putExtra("refresh_result", ((IccRefreshResponse)object).refreshResult);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Sending IccResult with Result: ");
            stringBuilder.append(((IccRefreshResponse)object).refreshResult);
            CatLog.d(this, stringBuilder.toString());
        }
        intent.putExtra("card_status", bl);
        intent.setComponent(AppInterface.getDefaultSTKApplication());
        intent.putExtra("SLOT_ID", this.mSlotId);
        object = new StringBuilder();
        ((StringBuilder)object).append("Sending Card Status: ");
        ((StringBuilder)object).append((Object)cardState);
        ((StringBuilder)object).append(" cardPresent: ");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append("SLOT_ID: ");
        ((StringBuilder)object).append(this.mSlotId);
        CatLog.d(this, ((StringBuilder)object).toString());
        this.mContext.sendBroadcast(intent, "android.permission.RECEIVE_STK_COMMANDS");
    }

    private void broadcastCatCmdIntent(CatCmdMessage catCmdMessage) {
        Intent intent = new Intent("com.android.internal.stk.command");
        intent.putExtra("STK CMD", (Parcelable)catCmdMessage);
        intent.putExtra("SLOT_ID", this.mSlotId);
        intent.setComponent(AppInterface.getDefaultSTKApplication());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Sending CmdMsg: ");
        stringBuilder.append(catCmdMessage);
        stringBuilder.append(" on slotid:");
        stringBuilder.append(this.mSlotId);
        CatLog.d(this, stringBuilder.toString());
        this.mContext.sendBroadcast(intent, "android.permission.RECEIVE_STK_COMMANDS");
    }

    private void changeLanguage(String string) throws RemoteException {
        IActivityManager iActivityManager = ActivityManagerNative.getDefault();
        Configuration configuration = iActivityManager.getConfiguration();
        configuration.setLocales(new LocaleList(new Locale(string), LocaleList.getDefault()));
        configuration.userSetLocale = true;
        iActivityManager.updatePersistentConfiguration(configuration);
        BackupManager.dataChanged((String)"com.android.providers.settings");
    }

    private void encodeOptionalTags(CommandDetails commandDetails, ResultCode object, Input input, ByteArrayOutputStream byteArrayOutputStream) {
        AppInterface.CommandType commandType = AppInterface.CommandType.fromInt(commandDetails.typeOfCommand);
        if (commandType != null) {
            int n = 1.$SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType[commandType.ordinal()];
            if (n != 5) {
                if (n != 8 && n != 9) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("encodeOptionalTags() Unsupported Cmd details=");
                    ((StringBuilder)object).append(commandDetails);
                    CatLog.d(this, ((StringBuilder)object).toString());
                } else if (((ResultCode)((Object)object)).value() == ResultCode.NO_RESPONSE_FROM_USER.value() && input != null && input.duration != null) {
                    this.getInKeyResponse(byteArrayOutputStream, input);
                }
            } else if (commandDetails.commandQualifier == 4 && ((ResultCode)((Object)object)).value() == ResultCode.OK.value()) {
                this.getPliResponse(byteArrayOutputStream);
            }
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("encodeOptionalTags() bad Cmd details=");
            ((StringBuilder)object).append(commandDetails);
            CatLog.d(this, ((StringBuilder)object).toString());
        }
    }

    private void eventDownload(int n, int n2, int n3, byte[] object, boolean bl) {
        Object object2 = new ByteArrayOutputStream();
        ((ByteArrayOutputStream)object2).write(214);
        int n4 = 0;
        ((ByteArrayOutputStream)object2).write(0);
        ((ByteArrayOutputStream)object2).write(ComprehensionTlvTag.EVENT_LIST.value() | 128);
        ((ByteArrayOutputStream)object2).write(1);
        ((ByteArrayOutputStream)object2).write(n);
        ((ByteArrayOutputStream)object2).write(ComprehensionTlvTag.DEVICE_IDENTITIES.value() | 128);
        ((ByteArrayOutputStream)object2).write(2);
        ((ByteArrayOutputStream)object2).write(n2);
        ((ByteArrayOutputStream)object2).write(n3);
        if (n != 4) {
            if (n != 5) {
                if (n == 7) {
                    CatLog.d(sInstance, " Sending Language Selection event download to ICC");
                    ((ByteArrayOutputStream)object2).write(ComprehensionTlvTag.LANGUAGE.value() | 128);
                    ((ByteArrayOutputStream)object2).write(2);
                }
            } else {
                CatLog.d(sInstance, " Sending Idle Screen Available event download to ICC");
            }
        }
        if (object != null) {
            n2 = ((byte[])object).length;
            for (n = n4; n < n2; ++n) {
                ((ByteArrayOutputStream)object2).write(object[n]);
            }
        }
        object = ((ByteArrayOutputStream)object2).toByteArray();
        object[1] = (byte)(((byte[])object).length - 2);
        object = IccUtils.bytesToHexString((byte[])object);
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("ENVELOPE COMMAND: ");
        ((StringBuilder)object2).append((String)object);
        CatLog.d(this, ((StringBuilder)object2).toString());
        this.mCmdIf.sendEnvelope((String)object, null);
    }

    private void getInKeyResponse(ByteArrayOutputStream byteArrayOutputStream, Input input) {
        byteArrayOutputStream.write(ComprehensionTlvTag.DURATION.value());
        byteArrayOutputStream.write(2);
        Duration.TimeUnit timeUnit = input.duration.timeUnit;
        byteArrayOutputStream.write(Duration.TimeUnit.SECOND.value());
        byteArrayOutputStream.write(input.duration.timeInterval);
    }

    public static AppInterface getInstance() {
        int n = 0;
        SubscriptionController subscriptionController = SubscriptionController.getInstance();
        if (subscriptionController != null) {
            n = subscriptionController.getSlotIndex(subscriptionController.getDefaultSubId());
        }
        return CatService.getInstance(null, null, null, n);
    }

    public static AppInterface getInstance(int n) {
        return CatService.getInstance(null, null, null, n);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static CatService getInstance(CommandsInterface var0, Context var1_1, UiccProfile var2_2, int var3_3) {
        if (var2_2 != null) {
            var4_4 = var2_2.getApplicationIndex(0);
            if (var4_4 != null) {
                var5_5 = var4_4.getIccFileHandler();
                var6_6 = var4_4.getIccRecords();
            } else {
                var5_5 = null;
                var6_6 = null;
            }
        } else {
            var4_4 = null;
            var5_5 = null;
            var6_6 = null;
        }
        var7_7 = CatService.sInstanceLock;
        // MONITORENTER : var7_7
        if (CatService.sInstance == null) {
            var8_8 = TelephonyManager.getDefault().getSimCount();
            CatService.sInstance = new CatService[var8_8];
            for (var9_9 = 0; var9_9 < var8_8; ++var9_9) {
                CatService.sInstance[var9_9] = null;
            }
        }
        if (CatService.sInstance[var3_3] != null) ** GOTO lbl30
        if (var0 != null && var4_4 != null && var6_6 != null && var1_1 /* !! */  != null && var5_5 != null) {
            if (var2_2 == null) {
                return null;
            }
            var10_10 = CatService.sInstance;
            var10_10[var3_3] = var11_11 = new CatService((CommandsInterface)var0, var4_4, var6_6, var1_1 /* !! */ , var5_5, var2_2, var3_3);
        } else {
            // MONITOREXIT : var7_7
            return null;
lbl30: // 1 sources:
            if (var6_6 != null && CatService.mIccRecords != var6_6) {
                if (CatService.mIccRecords != null) {
                    CatService.mIccRecords.unregisterForRecordsLoaded(CatService.sInstance[var3_3]);
                }
                CatService.mIccRecords = var6_6;
                CatService.mUiccApplication = var4_4;
                CatService.mIccRecords.registerForRecordsLoaded(CatService.sInstance[var3_3], 20, null);
                var1_1 /* !! */  = CatService.sInstance[var3_3];
                var0 = new StringBuilder();
                var0.append("registerForRecordsLoaded slotid=");
                var0.append(var3_3);
                var0.append(" instance:");
                var0.append(CatService.sInstance[var3_3]);
                CatLog.d((Object)var1_1 /* !! */ , var0.toString());
            }
        }
        var0 = CatService.sInstance[var3_3];
        // MONITOREXIT : var7_7
        return var0;
    }

    private void getPliResponse(ByteArrayOutputStream byteArrayOutputStream) {
        String string = Locale.getDefault().getLanguage();
        if (string != null) {
            byteArrayOutputStream.write(ComprehensionTlvTag.LANGUAGE.value());
            ResponseData.writeLength(byteArrayOutputStream, string.length());
            byteArrayOutputStream.write(string.getBytes(), 0, string.length());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void handleCmdResponse(CatResponseMessage var1_1) {
        block23 : {
            if (!this.validateResponse(var1_1)) {
                return;
            }
            var2_2 = null;
            var3_9 = var1_1.getCmdDetails();
            var4_10 = AppInterface.CommandType.fromInt(var3_9.typeOfCommand);
            switch (1.$SwitchMap$com$android$internal$telephony$cat$ResultCode[var1_1.mResCode.ordinal()]) {
                default: {
                    return;
                }
                case 16: {
                    if (var4_10 == AppInterface.CommandType.SET_UP_CALL) {
                        this.mCmdIf.handleCallSetupRequestFromSim(false, null);
                        this.mCurrntCmd = null;
                        return;
                    }
                }
                case 17: {
                    var2_3 = null;
                    break;
                }
                case 14: 
                case 15: {
                    if (var4_10 != AppInterface.CommandType.SET_UP_CALL && var4_10 != AppInterface.CommandType.OPEN_CHANNEL) {
                        var2_4 = null;
                        break;
                    }
                    this.mCmdIf.handleCallSetupRequestFromSim(false, null);
                    this.mCurrntCmd = null;
                    return;
                }
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 13: {
                    var5_12 = false;
                    ** break;
                }
                case 1: {
                    var5_12 = true;
lbl29: // 2 sources:
                    var6_13 = 1.$SwitchMap$com$android$internal$telephony$cat$AppInterface$CommandType[var4_10.ordinal()];
                    var7_14 = true;
                    if (var6_13 == 1) break block23;
                    if (var6_13 != 2) {
                        if (var6_13 != 4) {
                            if (var6_13 != 17 && var6_13 != 19) {
                                switch (var6_13) {
                                    default: {
                                        ** break;
                                    }
                                    case 8: 
                                    case 9: {
                                        var4_11 = this.mCurrntCmd.geInput();
                                        if (!var4_11.yesNo) {
                                            if (var5_12) break;
                                            var2_5 = new GetInkeyInputResponseData(var1_1.mUsersInput, var4_11.ucs2, var4_11.packed);
                                            ** break;
                                        }
                                        var2_6 = new GetInkeyInputResponseData(var1_1.mUsersYesNoSelection);
                                        ** break;
                                    }
                                    case 7: {
                                        var2_7 = new SelectItemResponseData(var1_1.mUsersMenuSelection);
                                        ** break;
                                    }
                                    case 6: {
                                        if (var1_1.mResCode == ResultCode.LAUNCH_BROWSER_ERROR) {
                                            var1_1.setAdditionalInfo(4);
                                            ** break;
                                        }
                                        var1_1.mIncludeAdditionalInfo = false;
                                        var1_1.mAdditionalInfo = 0;
                                        ** break;
lbl56: // 6 sources:
                                        break;
                                    }
                                }
                                break;
                            }
                            this.mCmdIf.handleCallSetupRequestFromSim(var1_1.mUsersConfirm, null);
                            this.mCurrntCmd = null;
                            return;
                        }
                        if (5 == var1_1.mEventValue) {
                            this.eventDownload(var1_1.mEventValue, 2, 129, var1_1.mAddedInfo, false);
                            return;
                        }
                        this.eventDownload(var1_1.mEventValue, 130, 129, var1_1.mAddedInfo, false);
                        return;
                    }
                    if (var1_1.mResCode == ResultCode.TERMINAL_CRNTLY_UNABLE_TO_PROCESS) {
                        var1_1.setAdditionalInfo(1);
                        break;
                    }
                    var1_1.mIncludeAdditionalInfo = false;
                    var1_1.mAdditionalInfo = 0;
                }
            }
            this.sendTerminalResponse(var3_9, var1_1.mResCode, var1_1.mIncludeAdditionalInfo, var1_1.mAdditionalInfo, (ResponseData)var2_8);
            this.mCurrntCmd = null;
            return;
        }
        if (var1_1.mResCode != ResultCode.HELP_INFO_REQUIRED) {
            var7_14 = false;
        }
        this.sendMenuSelection(var1_1.mUsersMenuSelection, var7_14);
    }

    private void handleCommand(CommandParams commandParams, boolean bl) {
        Object object;
        Object object2;
        CatLog.d(this, commandParams.getCommandType().name());
        if (bl && (object2 = this.mUiccController) != null) {
            object = new StringBuilder();
            object.append("ProactiveCommand mSlotId=");
            object.append(this.mSlotId);
            object.append(" cmdParams=");
            object.append(commandParams);
            ((UiccController)((Object)object2)).addCardLog(object.toString());
        }
        object2 = new CatCmdMessage(commandParams);
        switch (commandParams.getCommandType()) {
            default: {
                CatLog.d(this, "Unsupported command");
                return;
            }
            case OPEN_CHANNEL: 
            case CLOSE_CHANNEL: 
            case RECEIVE_DATA: 
            case SEND_DATA: {
                boolean bl2;
                BIPClientParams bIPClientParams = (BIPClientParams)commandParams;
                try {
                    bl2 = this.mContext.getResources().getBoolean(17891526);
                }
                catch (Resources.NotFoundException notFoundException) {
                    bl2 = false;
                }
                if (bIPClientParams.mTextMsg.text == null && (bIPClientParams.mHasAlphaId || bl2)) {
                    object = new StringBuilder();
                    object.append("cmd ");
                    object.append((Object)commandParams.getCommandType());
                    object.append(" with null alpha id");
                    CatLog.d(this, object.toString());
                    if (bl) {
                        this.sendTerminalResponse(commandParams.mCmdDet, ResultCode.OK, false, 0, null);
                    } else if (commandParams.getCommandType() == AppInterface.CommandType.OPEN_CHANNEL) {
                        this.mCmdIf.handleCallSetupRequestFromSim(true, null);
                    }
                    return;
                }
                if (!this.mStkAppInstalled) {
                    CatLog.d(this, "No STK application found.");
                    if (bl) {
                        this.sendTerminalResponse(commandParams.mCmdDet, ResultCode.BEYOND_TERMINAL_CAPABILITY, false, 0, null);
                        return;
                    }
                }
                if (!bl || commandParams.getCommandType() != AppInterface.CommandType.CLOSE_CHANNEL && commandParams.getCommandType() != AppInterface.CommandType.RECEIVE_DATA && commandParams.getCommandType() != AppInterface.CommandType.SEND_DATA) break;
                this.sendTerminalResponse(commandParams.mCmdDet, ResultCode.OK, false, 0, null);
                break;
            }
            case LANGUAGE_NOTIFICATION: {
                object2 = ((LanguageParams)commandParams).mLanguage;
                object = ResultCode.OK;
                if (object2 != null && ((String)object2).length() > 0) {
                    try {
                        this.changeLanguage((String)object2);
                    }
                    catch (RemoteException remoteException) {
                        object = ResultCode.TERMINAL_CRNTLY_UNABLE_TO_PROCESS;
                    }
                }
                this.sendTerminalResponse(commandParams.mCmdDet, (ResultCode)((Object)object), false, 0, null);
                return;
            }
            case SET_UP_CALL: {
                if (((CallSetupParams)commandParams).mConfirmMsg.text == null || !((CallSetupParams)commandParams).mConfirmMsg.text.equals(STK_DEFAULT)) break;
                object = this.mContext.getText(17039428);
                ((CallSetupParams)commandParams).mConfirmMsg.text = object.toString();
                break;
            }
            case PLAY_TONE: {
                break;
            }
            case SEND_DTMF: 
            case SEND_SMS: 
            case SEND_SS: 
            case SEND_USSD: {
                if (((DisplayTextParams)commandParams).mTextMsg.text == null || !((DisplayTextParams)commandParams).mTextMsg.text.equals(STK_DEFAULT)) break;
                object = this.mContext.getText(17040987);
                ((DisplayTextParams)commandParams).mTextMsg.text = object.toString();
                break;
            }
            case REFRESH: 
            case RUN_AT: {
                if (!STK_DEFAULT.equals(((DisplayTextParams)commandParams).mTextMsg.text)) break;
                ((DisplayTextParams)commandParams).mTextMsg.text = null;
                break;
            }
            case SELECT_ITEM: 
            case GET_INPUT: 
            case GET_INKEY: {
                break;
            }
            case LAUNCH_BROWSER: {
                if (((LaunchBrowserParams)commandParams).mConfirmMsg.text == null || !((LaunchBrowserParams)commandParams).mConfirmMsg.text.equals(STK_DEFAULT)) break;
                object = this.mContext.getText(17040228);
                ((LaunchBrowserParams)commandParams).mConfirmMsg.text = object.toString();
                break;
            }
            case PROVIDE_LOCAL_INFORMATION: {
                int n = commandParams.mCmdDet.commandQualifier;
                if (n != 3) {
                    if (n != 4) {
                        this.sendTerminalResponse(commandParams.mCmdDet, ResultCode.OK, false, 0, null);
                    } else {
                        object = new LanguageResponseData(Locale.getDefault().getLanguage());
                        this.sendTerminalResponse(commandParams.mCmdDet, ResultCode.OK, false, 0, (ResponseData)object);
                    }
                } else {
                    object = new DTTZResponseData(null);
                    this.sendTerminalResponse(commandParams.mCmdDet, ResultCode.OK, false, 0, (ResponseData)object);
                }
                return;
            }
            case SET_UP_EVENT_LIST: {
                if (this.isSupportedSetupEventCommand((CatCmdMessage)object2)) {
                    this.sendTerminalResponse(commandParams.mCmdDet, ResultCode.OK, false, 0, null);
                    break;
                }
                this.sendTerminalResponse(commandParams.mCmdDet, ResultCode.BEYOND_TERMINAL_CAPABILITY, false, 0, null);
                break;
            }
            case SET_UP_IDLE_MODE_TEXT: {
                object = commandParams.mLoadIconFailed ? ResultCode.PRFRMD_ICON_NOT_DISPLAYED : ResultCode.OK;
                this.sendTerminalResponse(commandParams.mCmdDet, (ResultCode)((Object)object), false, 0, null);
                break;
            }
            case DISPLAY_TEXT: {
                break;
            }
            case SET_UP_MENU: {
                this.mMenuCmd = this.removeMenu(((CatCmdMessage)object2).getMenu()) ? null : object2;
                object = commandParams.mLoadIconFailed ? ResultCode.PRFRMD_ICON_NOT_DISPLAYED : ResultCode.OK;
                this.sendTerminalResponse(commandParams.mCmdDet, (ResultCode)((Object)object), false, 0, null);
            }
        }
        this.mCurrntCmd = object2;
        this.broadcastCatCmdIntent((CatCmdMessage)object2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void handleRilMsg(RilMessage object) {
        if (object == null) {
            return;
        }
        int n = ((RilMessage)object).mId;
        if (n != 1) {
            CommandParams commandParams;
            block7 : {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 5 || (object = (CommandParams)((RilMessage)object).mData) == null) return;
                        this.handleCommand((CommandParams)object, false);
                        return;
                    } else {
                        if (((RilMessage)object).mResCode != ResultCode.OK || (object = (CommandParams)((RilMessage)object).mData) == null) return;
                        this.handleCommand((CommandParams)object, false);
                    }
                    return;
                }
                try {
                    commandParams = (CommandParams)((RilMessage)object).mData;
                    if (commandParams == null) return;
                    if (((RilMessage)object).mResCode != ResultCode.OK) break block7;
                }
                catch (ClassCastException classCastException) {
                    CatLog.d(this, "Fail to parse proactive command");
                    CatCmdMessage catCmdMessage = this.mCurrntCmd;
                    if (catCmdMessage == null) return;
                    this.sendTerminalResponse(catCmdMessage.mCmdDet, ResultCode.CMD_DATA_NOT_UNDERSTOOD, false, 0, null);
                    return;
                }
                this.handleCommand(commandParams, true);
                return;
            }
            this.sendTerminalResponse(commandParams.mCmdDet, ((RilMessage)object).mResCode, false, 0, null);
            return;
        }
        this.handleSessionEnd();
    }

    private void handleSessionEnd() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SESSION END on ");
        stringBuilder.append(this.mSlotId);
        CatLog.d(this, stringBuilder.toString());
        this.mCurrntCmd = this.mMenuCmd;
        stringBuilder = new Intent("com.android.internal.stk.session_end");
        stringBuilder.putExtra("SLOT_ID", this.mSlotId);
        stringBuilder.setComponent(AppInterface.getDefaultSTKApplication());
        this.mContext.sendBroadcast((Intent)stringBuilder, "android.permission.RECEIVE_STK_COMMANDS");
    }

    @UnsupportedAppUsage
    private boolean isStkAppInstalled() {
        Intent intent = new Intent("com.android.internal.stk.command");
        Object object = this.mContext.getPackageManager();
        object = object.queryBroadcastReceivers(intent, 128);
        boolean bl = false;
        int n = object == null ? 0 : object.size();
        if (n > 0) {
            bl = true;
        }
        return bl;
    }

    private boolean isSupportedSetupEventCommand(CatCmdMessage arrn) {
        boolean bl = true;
        for (int n : arrn.getSetEventList().eventList) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Event: ");
            stringBuilder.append(n);
            CatLog.d(this, stringBuilder.toString());
            if (n == 4 || n == 5 || n == 7) continue;
            bl = false;
        }
        return bl;
    }

    private boolean removeMenu(Menu object) {
        try {
            return object.items.size() == 1 && (object = object.items.get(0)) == null;
        }
        catch (NullPointerException nullPointerException) {
            CatLog.d(this, "Unable to get Menu's items size");
            return true;
        }
    }

    private void sendMenuSelection(int n, boolean bl) {
        Object object = new ByteArrayOutputStream();
        object.write(211);
        object.write(0);
        object.write(ComprehensionTlvTag.DEVICE_IDENTITIES.value() | 128);
        object.write(2);
        object.write(1);
        object.write(129);
        object.write(ComprehensionTlvTag.ITEM_ID.value() | 128);
        object.write(1);
        object.write(n);
        if (bl) {
            object.write(ComprehensionTlvTag.HELP_REQUEST.value());
            object.write(0);
        }
        object = object.toByteArray();
        object[1] = (byte)(((byte[])object).length - 2);
        object = IccUtils.bytesToHexString((byte[])object);
        this.mCmdIf.sendEnvelope((String)object, null);
    }

    @UnsupportedAppUsage
    private void sendTerminalResponse(CommandDetails object, ResultCode resultCode, boolean bl, int n, ResponseData responseData) {
        int n2;
        int n3;
        if (object == null) {
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Input input = null;
        CatCmdMessage catCmdMessage = this.mCurrntCmd;
        if (catCmdMessage != null) {
            input = catCmdMessage.geInput();
        }
        int n4 = n2 = ComprehensionTlvTag.COMMAND_DETAILS.value();
        if (((CommandDetails)object).compRequired) {
            n4 = n2 | 128;
        }
        byteArrayOutputStream.write(n4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(((CommandDetails)object).commandNumber);
        byteArrayOutputStream.write(((CommandDetails)object).typeOfCommand);
        byteArrayOutputStream.write(((CommandDetails)object).commandQualifier);
        byteArrayOutputStream.write(ComprehensionTlvTag.DEVICE_IDENTITIES.value());
        n2 = 2;
        byteArrayOutputStream.write(2);
        byteArrayOutputStream.write(130);
        byteArrayOutputStream.write(129);
        n4 = n3 = ComprehensionTlvTag.RESULT.value();
        if (((CommandDetails)object).compRequired) {
            n4 = n3 | 128;
        }
        byteArrayOutputStream.write(n4);
        n4 = bl ? n2 : 1;
        byteArrayOutputStream.write(n4);
        byteArrayOutputStream.write(resultCode.value());
        if (bl) {
            byteArrayOutputStream.write(n);
        }
        if (responseData != null) {
            responseData.format(byteArrayOutputStream);
        } else {
            this.encodeOptionalTags((CommandDetails)object, resultCode, input, byteArrayOutputStream);
        }
        object = IccUtils.bytesToHexString((byte[])byteArrayOutputStream.toByteArray());
        this.mCmdIf.sendTerminalResponse((String)object, null);
    }

    private boolean validateResponse(CatResponseMessage object) {
        boolean bl = false;
        if (object.mCmdDet.typeOfCommand != AppInterface.CommandType.SET_UP_EVENT_LIST.value() && object.mCmdDet.typeOfCommand != AppInterface.CommandType.SET_UP_MENU.value()) {
            if (this.mCurrntCmd != null) {
                bl = ((CatResponseMessage)object).mCmdDet.compareTo(this.mCurrntCmd.mCmdDet);
                object = new StringBuilder();
                ((StringBuilder)object).append("isResponse for last valid cmd: ");
                ((StringBuilder)object).append(bl);
                CatLog.d(this, ((StringBuilder)object).toString());
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CmdType: ");
            stringBuilder.append(object.mCmdDet.typeOfCommand);
            CatLog.d(this, stringBuilder.toString());
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void dispose() {
        Object object = sInstanceLock;
        synchronized (object) {
            CatLog.d(this, "Disposing CatService object");
            mIccRecords.unregisterForRecordsLoaded(this);
            this.broadcastCardStateAndIccRefreshResp(IccCardStatus.CardState.CARDSTATE_ABSENT, null);
            this.mCmdIf.unSetOnCatSessionEnd(this);
            this.mCmdIf.unSetOnCatProactiveCmd(this);
            this.mCmdIf.unSetOnCatEvent(this);
            this.mCmdIf.unSetOnCatCallSetUp(this);
            this.mCmdIf.unSetOnCatCcAlphaNotify(this);
            this.mCmdIf.unregisterForIccRefresh(this);
            if (this.mUiccController != null) {
                this.mUiccController.unregisterForIccChanged(this);
                this.mUiccController = null;
            }
            this.mMsgDecoder.dispose();
            this.mMsgDecoder = null;
            this.removeCallbacksAndMessages(null);
            if (sInstance != null) {
                if (SubscriptionManager.isValidSlotIndex((int)this.mSlotId)) {
                    CatService.sInstance[this.mSlotId] = null;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("error: invaild slot id: ");
                    stringBuilder.append(this.mSlotId);
                    CatLog.d(this, stringBuilder.toString());
                }
            }
            return;
        }
    }

    protected void finalize() {
        CatLog.d(this, "Service finalized");
    }

    public void handleMessage(Message object) {
        block23 : {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("handleMessage[");
            charSequence.append(((Message)object).what);
            charSequence.append("]");
            CatLog.d(this, charSequence.toString());
            int n = ((Message)object).what;
            if (n == 20) break block23;
            if (n != 30) {
                block1 : switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                charSequence = new StringBuilder();
                                charSequence.append("Unrecognized CAT command: ");
                                charSequence.append(((Message)object).what);
                                throw new AssertionError((Object)charSequence.toString());
                            }
                            case 10: {
                                this.handleRilMsg((RilMessage)((Message)object).obj);
                                break block1;
                            }
                            case 9: {
                                CatLog.d(this, "Received CAT CC Alpha message from card");
                                if (((Message)object).obj != null) {
                                    object = (AsyncResult)((Message)object).obj;
                                    if (object != null && ((AsyncResult)object).result != null) {
                                        this.broadcastAlphaMessage((String)((AsyncResult)object).result);
                                        break block1;
                                    }
                                    CatLog.d(this, "CAT Alpha message: ar.result is null");
                                    break block1;
                                }
                                CatLog.d(this, "CAT Alpha message: msg.obj is null");
                                break block1;
                            }
                            case 8: 
                        }
                        CatLog.d(this, "MSG_ID_ICC_CHANGED");
                        this.updateIccAvailability();
                        break;
                    }
                    case 6: {
                        this.handleCmdResponse((CatResponseMessage)((Message)object).obj);
                        break;
                    }
                    case 4: {
                        this.mMsgDecoder.sendStartDecodingMessageParams(new RilMessage(((Message)object).what, null));
                        break;
                    }
                    case 1: 
                    case 2: 
                    case 3: 
                    case 5: {
                        charSequence = new StringBuilder();
                        charSequence.append("ril message arrived,slotid:");
                        charSequence.append(this.mSlotId);
                        CatLog.d(this, charSequence.toString());
                        Object var4_5 = null;
                        charSequence = var4_5;
                        if (((Message)object).obj != null) {
                            AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                            charSequence = var4_5;
                            if (asyncResult != null) {
                                charSequence = var4_5;
                                if (asyncResult.result != null) {
                                    try {
                                        charSequence = (String)asyncResult.result;
                                    }
                                    catch (ClassCastException classCastException) {
                                        break;
                                    }
                                }
                            }
                        }
                        this.mMsgDecoder.sendStartDecodingMessageParams(new RilMessage(((Message)object).what, (String)charSequence));
                        break;
                    }
                }
            } else if (((Message)object).obj != null) {
                charSequence = (AsyncResult)((Message)object).obj;
                if (charSequence != null && ((AsyncResult)charSequence).result != null) {
                    this.broadcastCardStateAndIccRefreshResp(IccCardStatus.CardState.CARDSTATE_PRESENT, (IccRefreshResponse)((AsyncResult)charSequence).result);
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Icc REFRESH with exception: ");
                    ((StringBuilder)object).append(((AsyncResult)charSequence).exception);
                    CatLog.d(this, ((StringBuilder)object).toString());
                }
            } else {
                CatLog.d(this, "IccRefresh Message is null");
            }
        }
    }

    @Override
    public void onCmdResponse(CatResponseMessage catResponseMessage) {
        synchronized (this) {
            if (catResponseMessage == null) {
                return;
            }
            this.obtainMessage(6, (Object)catResponseMessage).sendToTarget();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void update(CommandsInterface object, Context object2, UiccProfile object3) {
        object = null;
        Object var4_4 = null;
        object2 = var4_4;
        if (object3 != null) {
            object = object3 = ((UiccProfile)object3).getApplicationIndex(0);
            object2 = var4_4;
            if (object3 != null) {
                object2 = ((UiccCardApplication)object3).getIccRecords();
                object = object3;
            }
        }
        object3 = sInstanceLock;
        synchronized (object3) {
            if (object2 != null && mIccRecords != object2) {
                if (mIccRecords != null) {
                    mIccRecords.unregisterForRecordsLoaded(this);
                }
                CatLog.d(this, "Reinitialize the Service with SIMRecords and UiccCardApplication");
                mIccRecords = object2;
                mUiccApplication = object;
                mIccRecords.registerForRecordsLoaded(this, 20, null);
                object = new StringBuilder();
                ((StringBuilder)object).append("registerForRecordsLoaded slotid=");
                ((StringBuilder)object).append(this.mSlotId);
                ((StringBuilder)object).append(" instance:");
                ((StringBuilder)object).append(this);
                CatLog.d(this, ((StringBuilder)object).toString());
            }
            return;
        }
    }

    void updateIccAvailability() {
        if (this.mUiccController == null) {
            return;
        }
        IccCardStatus.CardState cardState = IccCardStatus.CardState.CARDSTATE_ABSENT;
        Object object = this.mUiccController.getUiccCard(this.mSlotId);
        if (object != null) {
            cardState = ((UiccCard)object).getCardState();
        }
        IccCardStatus.CardState cardState2 = this.mCardState;
        this.mCardState = cardState;
        object = new StringBuilder();
        ((StringBuilder)object).append("New Card State = ");
        ((StringBuilder)object).append((Object)cardState);
        ((StringBuilder)object).append(" Old Card State = ");
        ((StringBuilder)object).append((Object)cardState2);
        CatLog.d(this, ((StringBuilder)object).toString());
        if (cardState2 == IccCardStatus.CardState.CARDSTATE_PRESENT && cardState != IccCardStatus.CardState.CARDSTATE_PRESENT) {
            this.broadcastCardStateAndIccRefreshResp(cardState, null);
        } else if (cardState2 != IccCardStatus.CardState.CARDSTATE_PRESENT && cardState == IccCardStatus.CardState.CARDSTATE_PRESENT) {
            this.mCmdIf.reportStkServiceIsRunning(null);
        }
    }

}

