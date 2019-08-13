/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.TelephonyManager
 *  com.android.internal.telephony.uicc.IccUtils
 *  com.android.internal.util.IState
 *  com.android.internal.util.State
 *  com.android.internal.util.StateMachine
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.cat.BerTlv;
import com.android.internal.telephony.cat.CatLog;
import com.android.internal.telephony.cat.CommandParams;
import com.android.internal.telephony.cat.CommandParamsFactory;
import com.android.internal.telephony.cat.ResultCode;
import com.android.internal.telephony.cat.ResultException;
import com.android.internal.telephony.cat.RilMessage;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.util.IState;
import com.android.internal.util.State;
import com.android.internal.util.StateMachine;

class RilMessageDecoder
extends StateMachine {
    private static final int CMD_PARAMS_READY = 2;
    private static final int CMD_START = 1;
    @UnsupportedAppUsage
    private static RilMessageDecoder[] mInstance;
    private static int mSimCount;
    private Handler mCaller = null;
    @UnsupportedAppUsage
    private CommandParamsFactory mCmdParamsFactory = null;
    private RilMessage mCurrentRilMessage = null;
    private StateCmdParamsReady mStateCmdParamsReady = new StateCmdParamsReady();
    @UnsupportedAppUsage
    private StateStart mStateStart = new StateStart();

    static {
        mSimCount = 0;
        mInstance = null;
    }

    private RilMessageDecoder() {
        super("RilMessageDecoder");
    }

    private RilMessageDecoder(Handler handler, IccFileHandler iccFileHandler) {
        super("RilMessageDecoder");
        this.addState((State)this.mStateStart);
        this.addState((State)this.mStateCmdParamsReady);
        this.setInitialState((State)this.mStateStart);
        this.mCaller = handler;
        this.mCmdParamsFactory = CommandParamsFactory.getInstance(this, iccFileHandler);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean decodeMessageParams(RilMessage var1_1) {
        this.mCurrentRilMessage = var1_1;
        var2_4 = var1_1.mId;
        if (var2_4 != 1) {
            if (var2_4 != 2 && var2_4 != 3) {
                if (var2_4 != 4) {
                    if (var2_4 != 5) {
                        return false;
                    } else {
                        ** GOTO lbl-1000
                    }
                }
            } else lbl-1000: // 3 sources:
            {
                try {
                    var1_1 = IccUtils.hexStringToBytes((String)((String)var1_1.mData));
                }
                catch (Exception var1_3) {
                    CatLog.d((Object)this, "decodeMessageParams dropping zombie messages");
                    return false;
                }
                try {
                    this.mCmdParamsFactory.make(BerTlv.decode(var1_1));
                    return true;
                }
                catch (ResultException var1_2) {
                    var4_6 = new StringBuilder();
                    var4_6.append("decodeMessageParams: caught ResultException e=");
                    var4_6.append((Object)var1_2);
                    CatLog.d((Object)this, var4_6.toString());
                    this.mCurrentRilMessage.mResCode = var1_2.result();
                    this.sendCmdForExecution(this.mCurrentRilMessage);
                    return false;
                }
            }
        }
        this.mCurrentRilMessage.mResCode = ResultCode.OK;
        this.sendCmdForExecution(this.mCurrentRilMessage);
        return false;
    }

    @UnsupportedAppUsage
    public static RilMessageDecoder getInstance(Handler object, IccFileHandler iccFileHandler, int n) {
        synchronized (RilMessageDecoder.class) {
            block11 : {
                block10 : {
                    if (mInstance != null) break block10;
                    mSimCount = TelephonyManager.getDefault().getSimCount();
                    mInstance = new RilMessageDecoder[mSimCount];
                    int n2 = 0;
                    do {
                        if (n2 >= mSimCount) break;
                        RilMessageDecoder.mInstance[n2] = null;
                        ++n2;
                    } while (true);
                }
                if (n != -1) {
                    block12 : {
                        RilMessageDecoder rilMessageDecoder;
                        if (n >= mSimCount) break block11;
                        if (mInstance[n] != null) break block12;
                        RilMessageDecoder[] arrrilMessageDecoder = mInstance;
                        arrrilMessageDecoder[n] = rilMessageDecoder = new RilMessageDecoder((Handler)object, iccFileHandler);
                    }
                    object = mInstance[n];
                    return object;
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("invaild slot id: ");
            ((StringBuilder)object).append(n);
            CatLog.d("RilMessageDecoder", ((StringBuilder)object).toString());
            return null;
            finally {
            }
        }
    }

    private void sendCmdForExecution(RilMessage rilMessage) {
        this.mCaller.obtainMessage(10, (Object)new RilMessage(rilMessage)).sendToTarget();
    }

    public void dispose() {
        this.quitNow();
        this.mStateStart = null;
        this.mStateCmdParamsReady = null;
        this.mCmdParamsFactory.dispose();
        this.mCmdParamsFactory = null;
        this.mCurrentRilMessage = null;
        this.mCaller = null;
        mInstance = null;
    }

    public void sendMsgParamsDecoded(ResultCode resultCode, CommandParams commandParams) {
        Message message = this.obtainMessage(2);
        message.arg1 = resultCode.value();
        message.obj = commandParams;
        this.sendMessage(message);
    }

    public void sendStartDecodingMessageParams(RilMessage rilMessage) {
        Message message = this.obtainMessage(1);
        message.obj = rilMessage;
        this.sendMessage(message);
    }

    private class StateCmdParamsReady
    extends State {
        private StateCmdParamsReady() {
        }

        public boolean processMessage(Message object) {
            if (object.what == 2) {
                RilMessageDecoder.access$400((RilMessageDecoder)RilMessageDecoder.this).mResCode = ResultCode.fromInt(object.arg1);
                RilMessageDecoder.access$400((RilMessageDecoder)RilMessageDecoder.this).mData = object.obj;
                object = RilMessageDecoder.this;
                ((RilMessageDecoder)((Object)object)).sendCmdForExecution(((RilMessageDecoder)((Object)object)).mCurrentRilMessage);
                object = RilMessageDecoder.this;
                object.transitionTo((IState)((RilMessageDecoder)((Object)object)).mStateStart);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("StateCmdParamsReady expecting CMD_PARAMS_READY=2 got ");
                stringBuilder.append(object.what);
                CatLog.d((Object)this, stringBuilder.toString());
                RilMessageDecoder.this.deferMessage(object);
            }
            return true;
        }
    }

    private class StateStart
    extends State {
        private StateStart() {
        }

        public boolean processMessage(Message object) {
            if (object.what == 1) {
                if (RilMessageDecoder.this.decodeMessageParams((RilMessage)object.obj)) {
                    object = RilMessageDecoder.this;
                    object.transitionTo((IState)((RilMessageDecoder)((Object)object)).mStateCmdParamsReady);
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("StateStart unexpected expecting START=1 got ");
                stringBuilder.append(object.what);
                CatLog.d((Object)this, stringBuilder.toString());
            }
            return true;
        }
    }

}

