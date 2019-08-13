/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.graphics.Bitmap
 *  android.os.Handler
 *  android.os.Message
 *  android.text.TextUtils
 *  com.android.internal.telephony.GsmAlphabet
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.cat.AppInterface;
import com.android.internal.telephony.cat.BIPClientParams;
import com.android.internal.telephony.cat.BerTlv;
import com.android.internal.telephony.cat.CallSetupParams;
import com.android.internal.telephony.cat.CatLog;
import com.android.internal.telephony.cat.CommandDetails;
import com.android.internal.telephony.cat.CommandParams;
import com.android.internal.telephony.cat.ComprehensionTlv;
import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.cat.DisplayTextParams;
import com.android.internal.telephony.cat.Duration;
import com.android.internal.telephony.cat.GetInputParams;
import com.android.internal.telephony.cat.IconId;
import com.android.internal.telephony.cat.IconLoader;
import com.android.internal.telephony.cat.Input;
import com.android.internal.telephony.cat.Item;
import com.android.internal.telephony.cat.ItemsIconId;
import com.android.internal.telephony.cat.LanguageParams;
import com.android.internal.telephony.cat.LaunchBrowserMode;
import com.android.internal.telephony.cat.LaunchBrowserParams;
import com.android.internal.telephony.cat.Menu;
import com.android.internal.telephony.cat.PlayToneParams;
import com.android.internal.telephony.cat.PresentationType;
import com.android.internal.telephony.cat.ResultCode;
import com.android.internal.telephony.cat.ResultException;
import com.android.internal.telephony.cat.RilMessageDecoder;
import com.android.internal.telephony.cat.SelectItemParams;
import com.android.internal.telephony.cat.SetEventListParams;
import com.android.internal.telephony.cat.TextMessage;
import com.android.internal.telephony.cat.Tone;
import com.android.internal.telephony.cat.ValueParser;
import com.android.internal.telephony.uicc.IccFileHandler;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

class CommandParamsFactory
extends Handler {
    static final int DTTZ_SETTING = 3;
    static final int LANGUAGE_SETTING = 4;
    static final int LOAD_MULTI_ICONS = 2;
    static final int LOAD_NO_ICON = 0;
    static final int LOAD_SINGLE_ICON = 1;
    private static final int MAX_GSM7_DEFAULT_CHARS = 239;
    private static final int MAX_UCS2_CHARS = 118;
    static final int MSG_ID_LOAD_ICON_DONE = 1;
    static final int NON_SPECIFIC_LANGUAGE = 0;
    static final int SPECIFIC_LANGUAGE = 1;
    private static CommandParamsFactory sInstance = null;
    private RilMessageDecoder mCaller = null;
    private CommandParams mCmdParams = null;
    private int mIconLoadState = 0;
    @UnsupportedAppUsage
    private IconLoader mIconLoader;
    private String mRequestedLanguage;
    private String mSavedLanguage;
    private boolean mloadIcon = false;

    private CommandParamsFactory(RilMessageDecoder rilMessageDecoder, IccFileHandler iccFileHandler) {
        this.mCaller = rilMessageDecoder;
        this.mIconLoader = IconLoader.getInstance(this, iccFileHandler);
    }

    static CommandParamsFactory getInstance(RilMessageDecoder object, IccFileHandler iccFileHandler) {
        synchronized (CommandParamsFactory.class) {
            block6 : {
                if (sInstance == null) break block6;
                object = sInstance;
                return object;
            }
            if (iccFileHandler != null) {
                object = new CommandParamsFactory((RilMessageDecoder)((Object)object), iccFileHandler);
                return object;
            }
            return null;
            finally {
            }
        }
    }

    private boolean processBIPClient(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        StringBuilder stringBuilder;
        Object object2 = AppInterface.CommandType.fromInt(commandDetails.typeOfCommand);
        if (object2 != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("process ");
            stringBuilder.append(object2.name());
            CatLog.d((Object)this, stringBuilder.toString());
        }
        object2 = new TextMessage();
        stringBuilder = null;
        boolean bl = false;
        Object object3 = this.searchForTag(ComprehensionTlvTag.ALPHA_ID, (List<ComprehensionTlv>)object);
        if (object3 != null) {
            ((TextMessage)object2).text = ValueParser.retrieveAlphaId((ComprehensionTlv)object3);
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("alpha TLV text=");
            ((StringBuilder)object3).append(((TextMessage)object2).text);
            CatLog.d((Object)this, ((StringBuilder)object3).toString());
            bl = true;
        }
        object3 = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object);
        object = stringBuilder;
        if (object3 != null) {
            object = ValueParser.retrieveIconId((ComprehensionTlv)object3);
            ((TextMessage)object2).iconSelfExplanatory = ((IconId)object).selfExplanatory;
        }
        ((TextMessage)object2).responseNeeded = false;
        this.mCmdParams = new BIPClientParams(commandDetails, (TextMessage)object2, bl);
        if (object != null) {
            this.mIconLoadState = 1;
            this.mIconLoader.loadIcon(((IconId)object).recordNumber, this.obtainMessage(1));
            return true;
        }
        return false;
    }

    private CommandDetails processCommandDetails(List<ComprehensionTlv> object) {
        CommandDetails commandDetails;
        CommandDetails commandDetails2 = commandDetails = null;
        if (object != null) {
            object = this.searchForTag(ComprehensionTlvTag.COMMAND_DETAILS, (List<ComprehensionTlv>)object);
            commandDetails2 = commandDetails;
            if (object != null) {
                try {
                    commandDetails2 = ValueParser.retrieveCommandDetails((ComprehensionTlv)object);
                }
                catch (ResultException resultException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("processCommandDetails: Failed to procees command details e=");
                    ((StringBuilder)object).append((Object)resultException);
                    CatLog.d((Object)this, ((StringBuilder)object).toString());
                    commandDetails2 = commandDetails;
                }
            }
        }
        return commandDetails2;
    }

    private boolean processDisplayText(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        CatLog.d((Object)this, "process DisplayText");
        TextMessage textMessage = new TextMessage();
        IconId iconId = null;
        ComprehensionTlv comprehensionTlv = this.searchForTag(ComprehensionTlvTag.TEXT_STRING, (List<ComprehensionTlv>)object);
        if (comprehensionTlv != null) {
            textMessage.text = ValueParser.retrieveTextString(comprehensionTlv);
        }
        if (textMessage.text != null) {
            if (this.searchForTag(ComprehensionTlvTag.IMMEDIATE_RESPONSE, (List<ComprehensionTlv>)object) != null) {
                textMessage.responseNeeded = false;
            }
            if ((comprehensionTlv = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object)) != null) {
                iconId = ValueParser.retrieveIconId(comprehensionTlv);
                textMessage.iconSelfExplanatory = iconId.selfExplanatory;
            }
            if ((object = this.searchForTag(ComprehensionTlvTag.DURATION, (List<ComprehensionTlv>)object)) != null) {
                textMessage.duration = ValueParser.retrieveDuration((ComprehensionTlv)object);
            }
            boolean bl = (commandDetails.commandQualifier & 1) != 0;
            textMessage.isHighPriority = bl;
            bl = (commandDetails.commandQualifier & 128) != 0;
            textMessage.userClear = bl;
            this.mCmdParams = new DisplayTextParams(commandDetails, textMessage);
            if (iconId != null) {
                this.mloadIcon = true;
                this.mIconLoadState = 1;
                this.mIconLoader.loadIcon(iconId.recordNumber, this.obtainMessage(1));
                return true;
            }
            return false;
        }
        throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
    }

    private boolean processEventNotify(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        CatLog.d((Object)this, "process EventNotify");
        TextMessage textMessage = new TextMessage();
        Object var4_4 = null;
        textMessage.text = ValueParser.retrieveAlphaId(this.searchForTag(ComprehensionTlvTag.ALPHA_ID, (List<ComprehensionTlv>)object));
        ComprehensionTlv comprehensionTlv = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object);
        object = var4_4;
        if (comprehensionTlv != null) {
            object = ValueParser.retrieveIconId(comprehensionTlv);
            textMessage.iconSelfExplanatory = ((IconId)object).selfExplanatory;
        }
        textMessage.responseNeeded = false;
        this.mCmdParams = new DisplayTextParams(commandDetails, textMessage);
        if (object != null) {
            this.mloadIcon = true;
            this.mIconLoadState = 1;
            this.mIconLoader.loadIcon(((IconId)object).recordNumber, this.obtainMessage(1));
            return true;
        }
        return false;
    }

    private boolean processGetInkey(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        CatLog.d((Object)this, "process GetInkey");
        Input input = new Input();
        IconId iconId = null;
        ComprehensionTlv comprehensionTlv = this.searchForTag(ComprehensionTlvTag.TEXT_STRING, (List<ComprehensionTlv>)object);
        if (comprehensionTlv != null) {
            input.text = ValueParser.retrieveTextString(comprehensionTlv);
            comprehensionTlv = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object);
            if (comprehensionTlv != null) {
                iconId = ValueParser.retrieveIconId(comprehensionTlv);
                input.iconSelfExplanatory = iconId.selfExplanatory;
            }
            if ((object = this.searchForTag(ComprehensionTlvTag.DURATION, (List<ComprehensionTlv>)object)) != null) {
                input.duration = ValueParser.retrieveDuration((ComprehensionTlv)object);
            }
            input.minLen = 1;
            input.maxLen = 1;
            boolean bl = (commandDetails.commandQualifier & 1) == 0;
            input.digitOnly = bl;
            bl = (commandDetails.commandQualifier & 2) != 0;
            input.ucs2 = bl;
            bl = (commandDetails.commandQualifier & 4) != 0;
            input.yesNo = bl;
            bl = (commandDetails.commandQualifier & 128) != 0;
            input.helpAvailable = bl;
            input.echo = true;
            this.mCmdParams = new GetInputParams(commandDetails, input);
            if (iconId != null) {
                this.mloadIcon = true;
                this.mIconLoadState = 1;
                this.mIconLoader.loadIcon(iconId.recordNumber, this.obtainMessage(1));
                return true;
            }
            return false;
        }
        throw new ResultException(ResultCode.REQUIRED_VALUES_MISSING);
    }

    private boolean processGetInput(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        CatLog.d((Object)this, "process GetInput");
        Input input = new Input();
        IconId iconId = null;
        ComprehensionTlv comprehensionTlv = this.searchForTag(ComprehensionTlvTag.TEXT_STRING, (List<ComprehensionTlv>)object);
        if (comprehensionTlv != null) {
            input.text = ValueParser.retrieveTextString(comprehensionTlv);
            comprehensionTlv = this.searchForTag(ComprehensionTlvTag.RESPONSE_LENGTH, (List<ComprehensionTlv>)object);
            if (comprehensionTlv != null) {
                try {
                    byte[] arrby = comprehensionTlv.getRawValue();
                    int n = comprehensionTlv.getValueIndex();
                    input.minLen = arrby[n] & 255;
                    input.maxLen = arrby[n + 1] & 255;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
                }
                comprehensionTlv = this.searchForTag(ComprehensionTlvTag.DEFAULT_TEXT, (List<ComprehensionTlv>)object);
                if (comprehensionTlv != null) {
                    input.defaultText = ValueParser.retrieveTextString(comprehensionTlv);
                }
                if ((comprehensionTlv = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object)) != null) {
                    iconId = ValueParser.retrieveIconId(comprehensionTlv);
                    input.iconSelfExplanatory = iconId.selfExplanatory;
                }
                if ((object = this.searchForTag(ComprehensionTlvTag.DURATION, (List<ComprehensionTlv>)object)) != null) {
                    input.duration = ValueParser.retrieveDuration((ComprehensionTlv)object);
                }
                boolean bl = (commandDetails.commandQualifier & 1) == 0;
                input.digitOnly = bl;
                bl = (commandDetails.commandQualifier & 2) != 0;
                input.ucs2 = bl;
                bl = (commandDetails.commandQualifier & 4) == 0;
                input.echo = bl;
                bl = (commandDetails.commandQualifier & 8) != 0;
                input.packed = bl;
                bl = (commandDetails.commandQualifier & 128) != 0;
                input.helpAvailable = bl;
                if (input.ucs2 && input.maxLen > 118) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("UCS2: received maxLen = ");
                    ((StringBuilder)object).append(input.maxLen);
                    ((StringBuilder)object).append(", truncating to ");
                    ((StringBuilder)object).append(118);
                    CatLog.d((Object)this, ((StringBuilder)object).toString());
                    input.maxLen = 118;
                } else if (!input.packed && input.maxLen > 239) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("GSM 7Bit Default: received maxLen = ");
                    ((StringBuilder)object).append(input.maxLen);
                    ((StringBuilder)object).append(", truncating to ");
                    ((StringBuilder)object).append(239);
                    CatLog.d((Object)this, ((StringBuilder)object).toString());
                    input.maxLen = 239;
                }
                this.mCmdParams = new GetInputParams(commandDetails, input);
                if (iconId != null) {
                    this.mloadIcon = true;
                    this.mIconLoadState = 1;
                    this.mIconLoader.loadIcon(iconId.recordNumber, this.obtainMessage(1));
                    return true;
                }
                return false;
            }
            throw new ResultException(ResultCode.REQUIRED_VALUES_MISSING);
        }
        throw new ResultException(ResultCode.REQUIRED_VALUES_MISSING);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean processLanguageNotification(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        CatLog.d((Object)this, "process Language Notification");
        StringBuilder stringBuilder = null;
        ComprehensionTlv comprehensionTlv = null;
        String string = Locale.getDefault().getLanguage();
        int n = commandDetails.commandQualifier;
        if (n != 0) {
            if (n != 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("LN[");
                ((StringBuilder)object).append(commandDetails.commandQualifier);
                ((StringBuilder)object).append("] Command Not Supported");
                CatLog.d((Object)this, ((StringBuilder)object).toString());
                object = stringBuilder;
            } else {
                comprehensionTlv = this.searchForTag(ComprehensionTlvTag.LANGUAGE, (List<ComprehensionTlv>)object);
                object = stringBuilder;
                if (comprehensionTlv != null) {
                    if (comprehensionTlv.getLength() != 2) throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
                    object = GsmAlphabet.gsm8BitUnpackedToString((byte[])comprehensionTlv.getRawValue(), (int)comprehensionTlv.getValueIndex(), (int)2);
                    if (TextUtils.isEmpty((CharSequence)this.mSavedLanguage) || !TextUtils.isEmpty((CharSequence)this.mRequestedLanguage) && !this.mRequestedLanguage.equals(string)) {
                        this.mSavedLanguage = string;
                    }
                    this.mRequestedLanguage = object;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Specific language notification changes the language setting to ");
                    stringBuilder.append(this.mRequestedLanguage);
                    CatLog.d((Object)this, stringBuilder.toString());
                }
            }
        } else {
            object = comprehensionTlv;
            if (!TextUtils.isEmpty((CharSequence)this.mSavedLanguage)) {
                object = comprehensionTlv;
                if (!TextUtils.isEmpty((CharSequence)this.mRequestedLanguage)) {
                    object = comprehensionTlv;
                    if (this.mRequestedLanguage.equals(string)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Non-specific language notification changes the language setting back to ");
                        ((StringBuilder)object).append(this.mSavedLanguage);
                        CatLog.d((Object)this, ((StringBuilder)object).toString());
                        object = this.mSavedLanguage;
                    }
                }
            }
            this.mSavedLanguage = null;
            this.mRequestedLanguage = null;
        }
        this.mCmdParams = new LanguageParams(commandDetails, (String)object);
        return false;
    }

    private boolean processLaunchBrowser(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        int n;
        CatLog.d((Object)this, "process LaunchBrowser");
        TextMessage textMessage = new TextMessage();
        IconId iconId = null;
        Object object2 = null;
        ComprehensionTlv comprehensionTlv = this.searchForTag(ComprehensionTlvTag.URL, (List<ComprehensionTlv>)object);
        if (comprehensionTlv != null) {
            block6 : {
                int n2;
                try {
                    object2 = comprehensionTlv.getRawValue();
                    n2 = comprehensionTlv.getValueIndex();
                    n = comprehensionTlv.getLength();
                    if (n <= 0) break block6;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
                }
                object2 = GsmAlphabet.gsm8BitUnpackedToString((byte[])object2, (int)n2, (int)n);
            }
            object2 = null;
        }
        textMessage.text = ValueParser.retrieveAlphaId(this.searchForTag(ComprehensionTlvTag.ALPHA_ID, (List<ComprehensionTlv>)object));
        if ((object = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object)) != null) {
            iconId = ValueParser.retrieveIconId((ComprehensionTlv)object);
            textMessage.iconSelfExplanatory = iconId.selfExplanatory;
        }
        object = (n = commandDetails.commandQualifier) != 2 ? (n != 3 ? LaunchBrowserMode.LAUNCH_IF_NOT_ALREADY_LAUNCHED : LaunchBrowserMode.LAUNCH_NEW_BROWSER) : LaunchBrowserMode.USE_EXISTING_BROWSER;
        this.mCmdParams = new LaunchBrowserParams(commandDetails, textMessage, (String)object2, (LaunchBrowserMode)((Object)object));
        if (iconId != null) {
            this.mIconLoadState = 1;
            this.mIconLoader.loadIcon(iconId.recordNumber, this.obtainMessage(1));
            return true;
        }
        return false;
    }

    private boolean processPlayTone(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        CatLog.d((Object)this, "process PlayTone");
        Object object2 = null;
        TextMessage textMessage = new TextMessage();
        ComprehensionTlv comprehensionTlv = this.searchForTag(ComprehensionTlvTag.TONE, (List<ComprehensionTlv>)object);
        Object object3 = object2;
        if (comprehensionTlv != null) {
            object3 = object2;
            if (comprehensionTlv.getLength() > 0) {
                try {
                    object3 = Tone.fromInt(comprehensionTlv.getRawValue()[comprehensionTlv.getValueIndex()]);
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
                }
            }
        }
        if ((object2 = this.searchForTag(ComprehensionTlvTag.ALPHA_ID, (List<ComprehensionTlv>)object)) != null) {
            textMessage.text = ValueParser.retrieveAlphaId((ComprehensionTlv)object2);
            if (textMessage.text == null) {
                textMessage.text = "";
            }
        }
        object2 = (object2 = this.searchForTag(ComprehensionTlvTag.DURATION, (List<ComprehensionTlv>)object)) != null ? ValueParser.retrieveDuration((ComprehensionTlv)object2) : null;
        if ((object = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object)) != null) {
            object = ValueParser.retrieveIconId((ComprehensionTlv)object);
            textMessage.iconSelfExplanatory = ((IconId)object).selfExplanatory;
        } else {
            object = null;
        }
        boolean bl = (commandDetails.commandQualifier & 1) != 0;
        textMessage.responseNeeded = false;
        this.mCmdParams = new PlayToneParams(commandDetails, textMessage, (Tone)((Object)object3), (Duration)object2, bl);
        if (object != null) {
            this.mIconLoadState = 1;
            this.mIconLoader.loadIcon(((IconId)object).recordNumber, this.obtainMessage(1));
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean processProvideLocalInfo(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        CatLog.d((Object)this, "process ProvideLocalInfo");
        int n = commandDetails.commandQualifier;
        if (n == 3) {
            CatLog.d((Object)this, "PLI [DTTZ_SETTING]");
            this.mCmdParams = new CommandParams(commandDetails);
            return false;
        }
        if (n == 4) {
            CatLog.d((Object)this, "PLI [LANGUAGE_SETTING]");
            this.mCmdParams = new CommandParams(commandDetails);
            return false;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("PLI[");
        ((StringBuilder)object).append(commandDetails.commandQualifier);
        ((StringBuilder)object).append("] Command Not Supported");
        CatLog.d((Object)this, ((StringBuilder)object).toString());
        this.mCmdParams = new CommandParams(commandDetails);
        throw new ResultException(ResultCode.BEYOND_TERMINAL_CAPABILITY);
    }

    private boolean processSelectItem(CommandDetails arrn, List<ComprehensionTlv> object) throws ResultException {
        block16 : {
            Object var5_5;
            IconId iconId;
            ComprehensionTlv comprehensionTlv;
            Menu menu;
            Object object2;
            block15 : {
                AppInterface.CommandType commandType;
                block14 : {
                    CatLog.d((Object)this, "process SelectItem");
                    menu = new Menu();
                    iconId = null;
                    var5_5 = null;
                    object2 = object.iterator();
                    commandType = AppInterface.CommandType.fromInt(arrn.typeOfCommand);
                    comprehensionTlv = this.searchForTag(ComprehensionTlvTag.ALPHA_ID, (List<ComprehensionTlv>)object);
                    if (comprehensionTlv == null) break block14;
                    menu.title = ValueParser.retrieveAlphaId(comprehensionTlv);
                    break block15;
                }
                if (commandType == AppInterface.CommandType.SET_UP_MENU) break block16;
            }
            while ((comprehensionTlv = this.searchForNextTag(ComprehensionTlvTag.ITEM, (Iterator<ComprehensionTlv>)object2)) != null) {
                menu.items.add(ValueParser.retrieveItem(comprehensionTlv));
            }
            if (menu.items.size() != 0) {
                int n;
                object2 = this.searchForTag(ComprehensionTlvTag.ITEM_ID, (List<ComprehensionTlv>)object);
                if (object2 != null) {
                    menu.defaultItem = ValueParser.retrieveItemId((ComprehensionTlv)object2) - 1;
                }
                if ((object2 = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object)) != null) {
                    this.mIconLoadState = 1;
                    iconId = ValueParser.retrieveIconId((ComprehensionTlv)object2);
                    menu.titleIconSelfExplanatory = iconId.selfExplanatory;
                }
                object2 = this.searchForTag(ComprehensionTlvTag.ITEM_ICON_ID_LIST, (List<ComprehensionTlv>)object);
                object = var5_5;
                if (object2 != null) {
                    this.mIconLoadState = 2;
                    object = ValueParser.retrieveItemsIconId((ComprehensionTlv)object2);
                    menu.itemsIconSelfExplanatory = ((ItemsIconId)object).selfExplanatory;
                }
                if ((n = (arrn.commandQualifier & 1) != 0 ? 1 : 0) != 0) {
                    menu.presentationType = (arrn.commandQualifier & 2) == 0 ? PresentationType.DATA_VALUES : PresentationType.NAVIGATION_OPTIONS;
                }
                boolean bl = (arrn.commandQualifier & 4) != 0;
                menu.softKeyPreferred = bl;
                bl = (arrn.commandQualifier & 128) != 0;
                menu.helpAvailable = bl;
                bl = iconId != null;
                this.mCmdParams = new SelectItemParams((CommandDetails)arrn, menu, bl);
                n = this.mIconLoadState;
                if (n != 0) {
                    if (n != 1) {
                        if (n == 2) {
                            arrn = ((ItemsIconId)object).recordNumbers;
                            if (iconId != null) {
                                arrn = new int[((ItemsIconId)object).recordNumbers.length + 1];
                                arrn[0] = iconId.recordNumber;
                                System.arraycopy(((ItemsIconId)object).recordNumbers, 0, arrn, 1, ((ItemsIconId)object).recordNumbers.length);
                            }
                            this.mloadIcon = true;
                            this.mIconLoader.loadIcons(arrn, this.obtainMessage(1));
                        }
                    } else {
                        this.mloadIcon = true;
                        this.mIconLoader.loadIcon(iconId.recordNumber, this.obtainMessage(1));
                    }
                    return true;
                }
                return false;
            }
            throw new ResultException(ResultCode.REQUIRED_VALUES_MISSING);
        }
        throw new ResultException(ResultCode.REQUIRED_VALUES_MISSING);
    }

    private boolean processSetUpEventList(CommandDetails commandDetails, List<ComprehensionTlv> object) {
        CatLog.d((Object)this, "process SetUpEventList");
        int[] arrn = this.searchForTag(ComprehensionTlvTag.EVENT_LIST, (List<ComprehensionTlv>)object);
        if (arrn != null) {
            object = arrn.getRawValue();
            int n = arrn.getValueIndex();
            int n2 = arrn.getLength();
            arrn = new int[n2];
            int n3 = 0;
            while (n2 > 0) {
                int n4 = object[n] & 255;
                ++n;
                --n2;
                if (n4 != 4 && n4 != 5 && n4 != 7 && n4 != 8 && n4 != 15) continue;
                arrn[n3] = n4;
                ++n3;
            }
            try {
                this.mCmdParams = object = new SetEventListParams(commandDetails, arrn);
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                CatLog.e((Object)this, " IndexOutofBoundException in processSetUpEventList");
            }
        }
        return false;
    }

    private boolean processSetUpIdleModeText(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        CatLog.d((Object)this, "process SetUpIdleModeText");
        TextMessage textMessage = new TextMessage();
        Object var4_4 = null;
        ComprehensionTlv comprehensionTlv = this.searchForTag(ComprehensionTlvTag.TEXT_STRING, (List<ComprehensionTlv>)object);
        if (comprehensionTlv != null) {
            textMessage.text = ValueParser.retrieveTextString(comprehensionTlv);
        }
        comprehensionTlv = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object);
        object = var4_4;
        if (comprehensionTlv != null) {
            object = ValueParser.retrieveIconId(comprehensionTlv);
            textMessage.iconSelfExplanatory = ((IconId)object).selfExplanatory;
        }
        if (textMessage.text == null && object != null && !textMessage.iconSelfExplanatory) {
            throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
        }
        this.mCmdParams = new DisplayTextParams(commandDetails, textMessage);
        if (object != null) {
            this.mloadIcon = true;
            this.mIconLoadState = 1;
            this.mIconLoader.loadIcon(((IconId)object).recordNumber, this.obtainMessage(1));
            return true;
        }
        return false;
    }

    private boolean processSetupCall(CommandDetails commandDetails, List<ComprehensionTlv> object) throws ResultException {
        CatLog.d((Object)this, "process SetupCall");
        Object object2 = object.iterator();
        TextMessage textMessage = new TextMessage();
        TextMessage textMessage2 = new TextMessage();
        IconId iconId = null;
        Object var7_7 = null;
        textMessage.text = ValueParser.retrieveAlphaId(this.searchForNextTag(ComprehensionTlvTag.ALPHA_ID, (Iterator<ComprehensionTlv>)object2));
        ComprehensionTlv comprehensionTlv = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object);
        if (comprehensionTlv != null) {
            iconId = ValueParser.retrieveIconId(comprehensionTlv);
            textMessage.iconSelfExplanatory = iconId.selfExplanatory;
        }
        if ((object2 = this.searchForNextTag(ComprehensionTlvTag.ALPHA_ID, (Iterator<ComprehensionTlv>)object2)) != null) {
            textMessage2.text = ValueParser.retrieveAlphaId((ComprehensionTlv)object2);
        }
        object2 = this.searchForTag(ComprehensionTlvTag.ICON_ID, (List<ComprehensionTlv>)object);
        object = var7_7;
        if (object2 != null) {
            object = ValueParser.retrieveIconId((ComprehensionTlv)object2);
            textMessage2.iconSelfExplanatory = ((IconId)object).selfExplanatory;
        }
        this.mCmdParams = new CallSetupParams(commandDetails, textMessage, textMessage2);
        if (iconId == null && object == null) {
            return false;
        }
        this.mIconLoadState = 2;
        int n = -1;
        int n2 = iconId != null ? iconId.recordNumber : -1;
        if (object != null) {
            n = ((IconId)object).recordNumber;
        }
        object = this.mIconLoader;
        commandDetails = this.obtainMessage(1);
        ((IconLoader)((Object)object)).loadIcons(new int[]{n2, n}, (Message)commandDetails);
        return true;
    }

    @UnsupportedAppUsage
    private ComprehensionTlv searchForNextTag(ComprehensionTlvTag object, Iterator<ComprehensionTlv> iterator) {
        int n = ((ComprehensionTlvTag)((Object)object)).value();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (((ComprehensionTlv)object).getTag() != n) continue;
            return object;
        }
        return null;
    }

    @UnsupportedAppUsage
    private ComprehensionTlv searchForTag(ComprehensionTlvTag comprehensionTlvTag, List<ComprehensionTlv> list) {
        return this.searchForNextTag(comprehensionTlvTag, list.iterator());
    }

    private void sendCmdParams(ResultCode resultCode) {
        this.mCaller.sendMsgParamsDecoded(resultCode, this.mCmdParams);
    }

    private ResultCode setIcons(Object arrbitmap) {
        if (arrbitmap == null) {
            CatLog.d((Object)this, "Optional Icon data is NULL");
            this.mCmdParams.mLoadIconFailed = true;
            this.mloadIcon = false;
            return ResultCode.OK;
        }
        int n = this.mIconLoadState;
        if (n != 1) {
            if (n == 2) {
                for (Bitmap bitmap : (Bitmap[])arrbitmap) {
                    this.mCmdParams.setIcon(bitmap);
                    if (bitmap != null || !this.mloadIcon) continue;
                    CatLog.d((Object)this, "Optional Icon data is NULL while loading multi icons");
                    this.mCmdParams.mLoadIconFailed = true;
                }
            }
        } else {
            this.mCmdParams.setIcon((Bitmap)arrbitmap);
        }
        return ResultCode.OK;
    }

    @UnsupportedAppUsage
    public void dispose() {
        this.mIconLoader.dispose();
        this.mIconLoader = null;
        this.mCmdParams = null;
        this.mCaller = null;
        sInstance = null;
    }

    public void handleMessage(Message message) {
        if (message.what == 1 && this.mIconLoader != null) {
            this.sendCmdParams(this.setIcons(message.obj));
        }
    }

    void make(BerTlv object) {
        CommandDetails commandDetails;
        block25 : {
            if (object == null) {
                return;
            }
            this.mCmdParams = null;
            this.mIconLoadState = 0;
            if (((BerTlv)object).getTag() != 208) {
                this.sendCmdParams(ResultCode.CMD_TYPE_NOT_UNDERSTOOD);
                return;
            }
            List<ComprehensionTlv> list = ((BerTlv)object).getComprehensionTlvs();
            commandDetails = this.processCommandDetails(list);
            if (commandDetails == null) {
                this.sendCmdParams(ResultCode.CMD_TYPE_NOT_UNDERSTOOD);
                return;
            }
            Object object2 = AppInterface.CommandType.fromInt(commandDetails.typeOfCommand);
            if (object2 == null) {
                this.mCmdParams = new CommandParams(commandDetails);
                this.sendCmdParams(ResultCode.BEYOND_TERMINAL_CAPABILITY);
                return;
            }
            if (!((BerTlv)object).isLengthValid()) {
                this.mCmdParams = new CommandParams(commandDetails);
                this.sendCmdParams(ResultCode.CMD_DATA_NOT_UNDERSTOOD);
                return;
            }
            try {
                boolean bl;
                switch (object2) {
                    default: {
                        break block25;
                    }
                    case OPEN_CHANNEL: 
                    case CLOSE_CHANNEL: 
                    case RECEIVE_DATA: 
                    case SEND_DATA: {
                        bl = this.processBIPClient(commandDetails, list);
                        break;
                    }
                    case LANGUAGE_NOTIFICATION: {
                        bl = this.processLanguageNotification(commandDetails, list);
                        break;
                    }
                    case PROVIDE_LOCAL_INFORMATION: {
                        bl = this.processProvideLocalInfo(commandDetails, list);
                        break;
                    }
                    case SET_UP_EVENT_LIST: {
                        bl = this.processSetUpEventList(commandDetails, list);
                        break;
                    }
                    case PLAY_TONE: {
                        bl = this.processPlayTone(commandDetails, list);
                        break;
                    }
                    case LAUNCH_BROWSER: {
                        bl = this.processLaunchBrowser(commandDetails, list);
                        break;
                    }
                    case GET_CHANNEL_STATUS: 
                    case SET_UP_CALL: {
                        bl = this.processSetupCall(commandDetails, list);
                        break;
                    }
                    case SEND_DTMF: 
                    case SEND_SMS: 
                    case REFRESH: 
                    case RUN_AT: 
                    case SEND_SS: 
                    case SEND_USSD: {
                        bl = this.processEventNotify(commandDetails, list);
                        break;
                    }
                    case GET_INPUT: {
                        bl = this.processGetInput(commandDetails, list);
                        break;
                    }
                    case GET_INKEY: {
                        bl = this.processGetInkey(commandDetails, list);
                        break;
                    }
                    case SET_UP_IDLE_MODE_TEXT: {
                        bl = this.processSetUpIdleModeText(commandDetails, list);
                        break;
                    }
                    case DISPLAY_TEXT: {
                        bl = this.processDisplayText(commandDetails, list);
                        break;
                    }
                    case SELECT_ITEM: {
                        bl = this.processSelectItem(commandDetails, list);
                        break;
                    }
                    case SET_UP_MENU: {
                        bl = this.processSelectItem(commandDetails, list);
                    }
                }
                if (!bl) {
                    this.sendCmdParams(ResultCode.OK);
                }
                return;
            }
            catch (ResultException resultException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("make: caught ResultException e=");
                ((StringBuilder)object2).append((Object)resultException);
                CatLog.d((Object)this, ((StringBuilder)object2).toString());
                this.mCmdParams = new CommandParams(commandDetails);
                this.sendCmdParams(resultException.result());
                return;
            }
        }
        this.mCmdParams = object = new CommandParams(commandDetails);
        this.sendCmdParams(ResultCode.BEYOND_TERMINAL_CAPABILITY);
    }

}

