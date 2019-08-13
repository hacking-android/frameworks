/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Message
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.telephony.Rlog
 *  com.android.internal.util.IState
 *  com.android.internal.util.State
 *  com.android.internal.util.StateMachine
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.Rlog;
import com.android.internal.telephony.Phone;
import com.android.internal.util.IState;
import com.android.internal.util.State;
import com.android.internal.util.StateMachine;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class WakeLockStateMachine
extends StateMachine {
    protected static final boolean DBG = true;
    protected static final int EVENT_BROADCAST_COMPLETE = 2;
    public static final int EVENT_NEW_SMS_MESSAGE = 1;
    static final int EVENT_RELEASE_WAKE_LOCK = 3;
    private static final int WAKE_LOCK_TIMEOUT = 3000;
    @UnsupportedAppUsage
    protected Context mContext;
    private final DefaultState mDefaultState = new DefaultState();
    @UnsupportedAppUsage
    private final IdleState mIdleState = new IdleState();
    @UnsupportedAppUsage
    protected Phone mPhone;
    protected final BroadcastReceiver mReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if (WakeLockStateMachine.this.mReceiverCount.decrementAndGet() == 0) {
                WakeLockStateMachine.this.sendMessage(2);
            }
        }
    };
    protected AtomicInteger mReceiverCount = new AtomicInteger(0);
    private final WaitingState mWaitingState = new WaitingState();
    private final PowerManager.WakeLock mWakeLock;

    protected WakeLockStateMachine(String string, Context context, Phone phone) {
        super(string);
        this.mContext = context;
        this.mPhone = phone;
        this.mWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, string);
        this.mWakeLock.acquire();
        this.addState((State)this.mDefaultState);
        this.addState((State)this.mIdleState, (State)this.mDefaultState);
        this.addState((State)this.mWaitingState, (State)this.mDefaultState);
        this.setInitialState((State)this.mIdleState);
    }

    public final void dispatchSmsMessage(Object object) {
        this.sendMessage(1, object);
    }

    public final void dispose() {
        this.quit();
    }

    protected abstract boolean handleSmsMessage(Message var1);

    @UnsupportedAppUsage
    protected void log(String string) {
        Rlog.d((String)this.getName(), (String)string);
    }

    protected void loge(String string) {
        Rlog.e((String)this.getName(), (String)string);
    }

    protected void loge(String string, Throwable throwable) {
        Rlog.e((String)this.getName(), (String)string, (Throwable)throwable);
    }

    protected void onQuitting() {
        while (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
    }

    class DefaultState
    extends State {
        DefaultState() {
        }

        public boolean processMessage(Message object) {
            int n = object.what;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("processMessage: unhandled message type ");
            stringBuilder.append(object.what);
            object = stringBuilder.toString();
            if (!Build.IS_DEBUGGABLE) {
                WakeLockStateMachine.this.loge((String)object);
                return true;
            }
            throw new RuntimeException((String)object);
        }
    }

    class IdleState
    extends State {
        IdleState() {
        }

        public void enter() {
            WakeLockStateMachine.this.sendMessageDelayed(3, 3000L);
        }

        public void exit() {
            WakeLockStateMachine.this.mWakeLock.acquire();
            WakeLockStateMachine.this.log("acquired wakelock, leaving Idle state");
        }

        public boolean processMessage(Message object) {
            int n = object.what;
            if (n != 1) {
                if (n != 3) {
                    return false;
                }
                WakeLockStateMachine.this.mWakeLock.release();
                if (WakeLockStateMachine.this.mWakeLock.isHeld()) {
                    WakeLockStateMachine.this.log("mWakeLock is still held after release");
                } else {
                    WakeLockStateMachine.this.log("mWakeLock released");
                }
                return true;
            }
            if (WakeLockStateMachine.this.handleSmsMessage((Message)object)) {
                object = WakeLockStateMachine.this;
                object.transitionTo((IState)((WakeLockStateMachine)((Object)object)).mWaitingState);
            }
            return true;
        }
    }

    class WaitingState
    extends State {
        WaitingState() {
        }

        public boolean processMessage(Message object) {
            int n = object.what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return false;
                    }
                    WakeLockStateMachine.this.mWakeLock.release();
                    if (!WakeLockStateMachine.this.mWakeLock.isHeld()) {
                        WakeLockStateMachine.this.loge("mWakeLock released while still in WaitingState!");
                    }
                    return true;
                }
                WakeLockStateMachine.this.log("broadcast complete, returning to idle");
                object = WakeLockStateMachine.this;
                object.transitionTo((IState)((WakeLockStateMachine)((Object)object)).mIdleState);
                return true;
            }
            WakeLockStateMachine.this.log("deferring message until return to idle");
            WakeLockStateMachine.this.deferMessage(object);
            return true;
        }
    }

}

