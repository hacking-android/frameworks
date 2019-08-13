/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.CancellationSignal
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.provider.Telephony
 *  android.provider.Telephony$RcsColumns
 *  android.provider.Telephony$RcsColumns$Rcs1To1ThreadColumns
 *  android.provider.Telephony$RcsColumns$RcsCanonicalAddressHelper
 *  android.provider.Telephony$RcsColumns$RcsFileTransferColumns
 *  android.provider.Telephony$RcsColumns$RcsGroupThreadColumns
 *  android.provider.Telephony$RcsColumns$RcsIncomingMessageColumns
 *  android.provider.Telephony$RcsColumns$RcsParticipantColumns
 *  android.telephony.Rlog
 *  android.telephony.ims.RcsEventQueryParams
 *  android.telephony.ims.RcsEventQueryResultDescriptor
 *  android.telephony.ims.RcsFileTransferCreationParams
 *  android.telephony.ims.RcsIncomingMessageCreationParams
 *  android.telephony.ims.RcsMessageCreationParams
 *  android.telephony.ims.RcsMessageQueryParams
 *  android.telephony.ims.RcsMessageQueryResultParcelable
 *  android.telephony.ims.RcsMessageSnippet
 *  android.telephony.ims.RcsOutgoingMessageCreationParams
 *  android.telephony.ims.RcsParticipantQueryParams
 *  android.telephony.ims.RcsParticipantQueryResultParcelable
 *  android.telephony.ims.RcsQueryContinuationToken
 *  android.telephony.ims.RcsThreadQueryParams
 *  android.telephony.ims.RcsThreadQueryResultParcelable
 *  android.telephony.ims.aidl.IRcs
 *  android.telephony.ims.aidl.IRcs$Stub
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.ims.-$
 *  com.android.internal.telephony.ims.-$$Lambda
 *  com.android.internal.telephony.ims.-$$Lambda$RcsMessageStoreController
 *  com.android.internal.telephony.ims.-$$Lambda$RcsMessageStoreController$75youkeK6UPD-R54BvRLBXlMeuw
 *  com.android.internal.telephony.ims.-$$Lambda$RcsMessageStoreController$Abaq2KJr5w02nAfSHDpHL8cbGCM
 *  com.android.internal.telephony.ims.-$$Lambda$RcsMessageStoreController$dK7yaLArRjD5DmHyJNMMqtB22C4
 *  com.android.internal.telephony.ims.-$$Lambda$RcsMessageStoreController$n7YWjkBre8yAm3X4Ma8Y6IJulUU
 *  com.android.internal.telephony.ims.-$$Lambda$RcsMessageStoreController$ql9qCCnj1UThMFRJGcj36kFApKA
 *  com.android.internal.telephony.ims.-$$Lambda$RcsMessageStoreController$yW1mU-OjjSZ_aoCdgW7hX38_lT8
 */
package com.android.internal.telephony.ims;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Telephony;
import android.telephony.Rlog;
import android.telephony.ims.RcsEventQueryParams;
import android.telephony.ims.RcsEventQueryResultDescriptor;
import android.telephony.ims.RcsFileTransferCreationParams;
import android.telephony.ims.RcsIncomingMessageCreationParams;
import android.telephony.ims.RcsMessageCreationParams;
import android.telephony.ims.RcsMessageQueryParams;
import android.telephony.ims.RcsMessageQueryResultParcelable;
import android.telephony.ims.RcsMessageSnippet;
import android.telephony.ims.RcsOutgoingMessageCreationParams;
import android.telephony.ims.RcsParticipantQueryParams;
import android.telephony.ims.RcsParticipantQueryResultParcelable;
import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims.RcsThreadQueryParams;
import android.telephony.ims.RcsThreadQueryResultParcelable;
import android.telephony.ims.aidl.IRcs;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.ims.-$;
import com.android.internal.telephony.ims.RcsEventQueryHelper;
import com.android.internal.telephony.ims.RcsMessageQueryHelper;
import com.android.internal.telephony.ims.RcsMessageStoreUtil;
import com.android.internal.telephony.ims.RcsParticipantQueryHelper;
import com.android.internal.telephony.ims.RcsPermissions;
import com.android.internal.telephony.ims.RcsThreadQueryHelper;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$1jwBeSBvZDdyDt_jPAHrOI2_Kp8;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$2vqOScXnTngkQ9yzhAYC8e3AHUU;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$40atfWQEcRbpUIloB6mwL9gyuIc;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$4U3TsrRCd3QMjXYC5EsUpGmVMTw;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$59OQ763FYDG6pocPJGJOTyticw0;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$75youkeK6UPD_R54BvRLBXlMeuw;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$8kZ0Whs2V3H2_A7PZtgThC9cHrM;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$9wFprf97OtAZJet5n97zbx_SmAw;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$Abaq2KJr5w02nAfSHDpHL8cbGCM;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$Aj3odzXvQEQBCSmamvCh9PVCEoo;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$C_S6XDOl4j0OCArwRwMKCC1AaRc;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$ClTeG1a5315E4yM3I5FjYPv_aqU;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$CrJMOfHeqLMbEHM26Tzgd2h0xwc;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$DJ1U8cPQGcg90QJamjBaum8g4gc;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$DenzMTC9mTdhbmGgI_nCtmmHclY;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$DjIzQFPqrkv3Uo1dzNDROD0ozLs;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$EUwM1EwT7Lz6sUEnYRpBVA8Q4Yo;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$FZtgkcQORLtmljGumeKt4RYKcDI;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$GMu9xFuH6Pwj30sjJjU3zueFCNY;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$H8kZkoDGWxnaPfrHVAB6QaOOEK4;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$HSCl_3_xd_STs1XaojnDci914C0;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$I4OWMlhoGkKEhCcow2wPN5FG9VY;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$I8TYhXVjUPl2Qbe8Oh1Ed3yhWNw;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$IU_phvRVIxp54_IApD01IQZxvMU;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$JHzHPFCAseKapBbeL98bJFNAzig;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$K1FMaXVKrG26Oq2VVJlnciT_hdc;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$Kv0WzjSrvy9VSan2OBtU0Bx6ETs;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$LKp6ysypBzOb0aWBWdCnqPDcLHA;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$LMhPtb7AbEObwiwot1ObQconTrY;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$LbCZDtv0n6njHJFbdiZw0tky6vs;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$NyzhqlBzK5HgvBBj0y_IUKT7n6c;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$QuCMf_ZjaL38JKfdVdaXlhUciYg;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$RAd62JxQGCnFeaVRbYvfRMq2nMQ;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$TpIpy59FjUIVg96luEg12VkT5aw;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$VySBVSH4PKKZDjHYzvMaQ6sXAh4;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$Ydc70CwcfJDOBqlqBcqxTt38Uzo;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$YipgJUqRvwIQNiAwv0iAJe9j_Dg;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$ZbAi2Z4Zs7PCzpYK2ddr149tEyQ;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$_Cb4PItW7V1g69LCBQGhBiRRtz4;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$_MbBnVvQk8PdmMcn1WGDQaVhTok;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$_QH7KyUipgV0PmMsbFbK5_YvipI;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$aLn59mZ4fSyftj8qGtwnDws4upk;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$aTBxVCDp4645Mr87rYLwljQQ11U;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$cI3HfQAJmekiQsJsCcTNShYosxw;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$cN0QvwaxRss2b1Nol38Q6BWlJb8;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$cOS8pT4JicoW_zRFZJ1cYJTDjoE;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$d0MdM4t_qzzQuPmkNRmZ3KJdjlE;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$d1hmXh2zI4_jeU_X_WBp_HcCjlE;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$d2kuYkT0m7BtnEDDoFJO8Ff4aik;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$dK7yaLArRjD5DmHyJNMMqtB22C4;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$dtjug27mcIESiWPv1SG5VdrJVNk;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$emhqhI_Zb6_HnA9QWgvIl6aqRig;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$fbjeAKvq29PTxOBowvC2GclUNIA;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$fgKkPLRhFZnK35RO00UEoYUhO3I;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$fyr6oqGnSJUp4Gt59YfyGGlG77Q;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$g2STMzf2abyGQ3T02uUs_sGDUYU;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$gpg3zST_PdicDEJNClha72fjiHY;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$gwncfskzeEFKLz89VwsUiuq7rr4;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$hOq33zEjGZu3QxvphbbEytozff8;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$i5gf8gYSpfipcECfLUsMExN6FGE;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$jHeKn_absIeh0lBHfJyzVOO62F0;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$jdfxgyc8mRmghuhEspSRnxyRt4Y;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$jiseLvnaiI_ZGA6BgQU2fHwubyw;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$kKfCdoAe6weg7LBn30W5ytjoBmg;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$kwvr7eXsIkjI91KRQHY7Ht_2JB4;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$lpHB7xyEmqwVVQoFOttd3ccPVsk;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$mQeh9a23oigBT3qH7by7Bi8LlEU;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$n7YWjkBre8yAm3X4Ma8Y6IJulUU;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$nCbhDPjKFA0Jm53EFmbQexceNLQ;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$nHeOZX6Yex65rTb0Sr3YN_hUFNI;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$oYdMpGEerSj76ai9qOJ48sYoY14;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$obN_p4lwM_T0StmSSFcBaCx4A1s;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$ql9qCCnj1UThMFRJGcj36kFApKA;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$rOTFWScZeGqI_FfpdXKvaj_6TaY;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$rUl7GR0q_Zo6dP_OpjyrnxyNLc4;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$sAlZvDr2whzxoiLJXOIiyp_xEVM;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$tdK9Zp0b0YckcWYCGTylP_1Sa4g;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$th762XOIu1OKBMzBiiE5dtAuBnE;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$uHotLs093tvDOCQG2PWoegXouxA;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$uY3LpbCjF8RJpCw0F59FfBzKsJo;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$vA5r4Lfp_qX3gFPu_m4wW3apM7Q;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$wo_oijl9hUm68TegPWxMOAOSGs8;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$xC3HWhR80aJOt1X6r83yAZM3tUQ;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$yDvbl7WU_iKHa8m_gKb4dC0kTmU;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$yS7DrFPmo_7WnhiPUTQ_g6NDQjw;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$yW1mU_OjjSZ_aoCdgW7hX38_lT8;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$ysPz1siZPIda_DS_nk6hkNkODHo;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$yv74ikmFfA_7ogwNEv9RwRPIh3w;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$zWLrx_FcTUxnS1nsFt35WphvPII;
import com.android.internal.telephony.ims._$$Lambda$RcsMessageStoreController$zi0MeLHsTlKqbg6lktWyXcOuEr4;

public class RcsMessageStoreController
extends IRcs.Stub {
    private static final String RCS_SERVICE_NAME = "ircs";
    static final String TAG = "RcsMsgStoreController";
    private static RcsMessageStoreController sInstance;
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private final RcsEventQueryHelper mEventQueryHelper;
    private final RcsMessageQueryHelper mMessageQueryHelper;
    private final RcsMessageStoreUtil mMessageStoreUtil;
    private final RcsParticipantQueryHelper mParticipantQueryHelper;
    private final RcsThreadQueryHelper mThreadQueryHelper;

    @VisibleForTesting
    public RcsMessageStoreController(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mParticipantQueryHelper = new RcsParticipantQueryHelper(this.mContentResolver);
        this.mMessageQueryHelper = new RcsMessageQueryHelper(this.mContentResolver);
        this.mThreadQueryHelper = new RcsThreadQueryHelper(this.mContentResolver, this.mParticipantQueryHelper);
        this.mEventQueryHelper = new RcsEventQueryHelper(this.mContentResolver);
        this.mMessageStoreUtil = new RcsMessageStoreUtil(this.mContentResolver);
    }

    private int addMessage(int n, boolean bl, ContentValues object) throws RemoteException {
        object = this.mContentResolver.insert(this.mMessageQueryHelper.getMessageInsertionUri(bl), (ContentValues)object);
        if (object != null) {
            return Integer.parseInt(object.getLastPathSegment());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not create message on thread, threadId: ");
        ((StringBuilder)object).append(n);
        throw new RemoteException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static RcsMessageStoreController init(Context object) {
        synchronized (RcsMessageStoreController.class) {
            if (sInstance == null) {
                RcsMessageStoreController rcsMessageStoreController;
                sInstance = rcsMessageStoreController = new RcsMessageStoreController((Context)object);
                if (ServiceManager.getService((String)RCS_SERVICE_NAME) == null) {
                    ServiceManager.addService((String)RCS_SERVICE_NAME, (IBinder)sInstance);
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("init() called multiple times! sInstance = ");
                ((StringBuilder)object).append((Object)sInstance);
                Rlog.e((String)TAG, (String)((StringBuilder)object).toString());
            }
            return sInstance;
        }
    }

    static /* synthetic */ int[] lambda$getFileTransfersAttachedToMessage$59() throws RemoteException {
        return new int[0];
    }

    static /* synthetic */ RcsMessageSnippet lambda$getMessageSnippet$1() throws RemoteException {
        return null;
    }

    static /* synthetic */ Integer lambda$getOutgoingDeliveryStatus$51() throws RemoteException {
        return 0;
    }

    static /* synthetic */ String lambda$getRcsParticipantContactId$16() throws RemoteException {
        return null;
    }

    static /* synthetic */ void lambda$setOutgoingDeliveryStatus$52() throws RemoteException {
    }

    static /* synthetic */ void lambda$setRcsParticipantContactId$17() throws RemoteException {
    }

    private <T> T performCreateOperation(String string, ThrowingSupplier<T> throwingSupplier) {
        RcsPermissions.checkWritePermissions(this.mContext, string);
        try {
            string = throwingSupplier.get();
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
        return (T)string;
    }

    private <T> T performReadOperation(String string, ThrowingSupplier<T> throwingSupplier) {
        RcsPermissions.checkReadPermissions(this.mContext, string);
        try {
            string = throwingSupplier.get();
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
        return (T)string;
    }

    private void performWriteOperation(String string, ThrowingRunnable throwingRunnable) {
        RcsPermissions.checkWritePermissions(this.mContext, string);
        try {
            throwingRunnable.run();
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public int addIncomingMessage(int n, RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams, String string) {
        return (Integer)this.performCreateOperation(string, new _$$Lambda$RcsMessageStoreController$jiseLvnaiI_ZGA6BgQU2fHwubyw(this, rcsIncomingMessageCreationParams, n));
    }

    public int addOutgoingMessage(int n, RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams, String string) {
        return (Integer)this.performCreateOperation(string, new _$$Lambda$RcsMessageStoreController$40atfWQEcRbpUIloB6mwL9gyuIc(this, n, rcsOutgoingMessageCreationParams));
    }

    public void addParticipantToGroupThread(int n, int n2, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$aLn59mZ4fSyftj8qGtwnDws4upk(this, n, n2));
    }

    public int createGroupThread(int[] arrn, String string, Uri uri, String string2) {
        return (Integer)this.performCreateOperation(string2, new _$$Lambda$RcsMessageStoreController$wo_oijl9hUm68TegPWxMOAOSGs8(this, string, uri, arrn, string2));
    }

    public int createGroupThreadIconChangedEvent(long l, int n, int n2, Uri uri, String string) {
        return (Integer)this.performCreateOperation(string, new _$$Lambda$RcsMessageStoreController$4U3TsrRCd3QMjXYC5EsUpGmVMTw(this, uri, l, n, n2));
    }

    public int createGroupThreadNameChangedEvent(long l, int n, int n2, String string, String string2) {
        return (Integer)this.performCreateOperation(string2, new _$$Lambda$RcsMessageStoreController$mQeh9a23oigBT3qH7by7Bi8LlEU(this, string, l, n, n2));
    }

    public int createGroupThreadParticipantJoinedEvent(long l, int n, int n2, int n3, String string) {
        return (Integer)this.performCreateOperation(string, new _$$Lambda$RcsMessageStoreController$gwncfskzeEFKLz89VwsUiuq7rr4(this, n3, l, n, n2));
    }

    public int createGroupThreadParticipantLeftEvent(long l, int n, int n2, int n3, String string) {
        return (Integer)this.performCreateOperation(string, new _$$Lambda$RcsMessageStoreController$sAlZvDr2whzxoiLJXOIiyp_xEVM(this, n3, l, n, n2));
    }

    public int createParticipantAliasChangedEvent(long l, int n, String string, String string2) {
        return (Integer)this.performCreateOperation(string2, new _$$Lambda$RcsMessageStoreController$d1hmXh2zI4_jeU_X_WBp_HcCjlE(this, l, n, string));
    }

    public int createRcs1To1Thread(int n, String string) {
        return (Integer)this.performCreateOperation(string, new _$$Lambda$RcsMessageStoreController$gpg3zST_PdicDEJNClha72fjiHY(this, n));
    }

    public int createRcsParticipant(String string, String string2, String string3) {
        return (Integer)this.performCreateOperation(string3, new _$$Lambda$RcsMessageStoreController$H8kZkoDGWxnaPfrHVAB6QaOOEK4(this, string, string2));
    }

    public void deleteFileTransfer(int n, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$cI3HfQAJmekiQsJsCcTNShYosxw(this, n));
    }

    public void deleteMessage(int n, boolean bl, int n2, boolean bl2, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$obN_p4lwM_T0StmSSFcBaCx4A1s(this, n, bl, n2, bl2));
    }

    public boolean deleteThread(int n, int n2, String string) {
        return (Boolean)this.performCreateOperation(string, new _$$Lambda$RcsMessageStoreController$vA5r4Lfp_qX3gFPu_m4wW3apM7Q(this, n2, n));
    }

    public long get1To1ThreadFallbackThreadId(int n, String string) {
        return (Long)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$Kv0WzjSrvy9VSan2OBtU0Bx6ETs(this, n));
    }

    public int get1To1ThreadOtherParticipantId(int n, String string) {
        return (Integer)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$C_S6XDOl4j0OCArwRwMKCC1AaRc(this, n));
    }

    public RcsEventQueryResultDescriptor getEvents(RcsEventQueryParams rcsEventQueryParams, String string) {
        return (RcsEventQueryResultDescriptor)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$LbCZDtv0n6njHJFbdiZw0tky6vs(this, rcsEventQueryParams));
    }

    public RcsEventQueryResultDescriptor getEventsWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String string) {
        return (RcsEventQueryResultDescriptor)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$uY3LpbCjF8RJpCw0F59FfBzKsJo(this, rcsQueryContinuationToken));
    }

    public String getFileTransferContentType(int n, String string) {
        return (String)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$HSCl_3_xd_STs1XaojnDci914C0(this, n));
    }

    public Uri getFileTransferContentUri(int n, String string) {
        return (Uri)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$kwvr7eXsIkjI91KRQHY7Ht_2JB4(this, n));
    }

    public long getFileTransferFileSize(int n, String string) {
        return (Long)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$tdK9Zp0b0YckcWYCGTylP_1Sa4g(this, n));
    }

    public int getFileTransferHeight(int n, String string) {
        return (Integer)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$yS7DrFPmo_7WnhiPUTQ_g6NDQjw(this, n));
    }

    public long getFileTransferLength(int n, String string) {
        return (Long)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$zi0MeLHsTlKqbg6lktWyXcOuEr4(this, n));
    }

    public String getFileTransferPreviewType(int n, String string) {
        return (String)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$2vqOScXnTngkQ9yzhAYC8e3AHUU(this, n));
    }

    public Uri getFileTransferPreviewUri(int n, String string) {
        return (Uri)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$fbjeAKvq29PTxOBowvC2GclUNIA(this, n));
    }

    public String getFileTransferSessionId(int n, String string) {
        return (String)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$IU_phvRVIxp54_IApD01IQZxvMU(this, n));
    }

    public int getFileTransferStatus(int n, String string) {
        return (Integer)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$aTBxVCDp4645Mr87rYLwljQQ11U(this, n));
    }

    public long getFileTransferTransferOffset(int n, String string) {
        return (Long)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$yv74ikmFfA_7ogwNEv9RwRPIh3w(this, n));
    }

    public int getFileTransferWidth(int n, String string) {
        return (Integer)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$DjIzQFPqrkv3Uo1dzNDROD0ozLs(this, n));
    }

    public int[] getFileTransfersAttachedToMessage(int n, boolean bl, String string) {
        return (int[])this.performReadOperation(string, (ThrowingSupplier<T>)_$$Lambda$RcsMessageStoreController$dK7yaLArRjD5DmHyJNMMqtB22C4.INSTANCE);
    }

    public String getGlobalMessageIdForMessage(int n, boolean bl, String string) {
        return (String)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$g2STMzf2abyGQ3T02uUs_sGDUYU(this, bl, n));
    }

    public Uri getGroupThreadConferenceUri(int n, String string) {
        return (Uri)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$I4OWMlhoGkKEhCcow2wPN5FG9VY(this, n));
    }

    public Uri getGroupThreadIcon(int n, String string) {
        return (Uri)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$i5gf8gYSpfipcECfLUsMExN6FGE(this, n));
    }

    public String getGroupThreadName(int n, String string) {
        return (String)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$Aj3odzXvQEQBCSmamvCh9PVCEoo(this, n));
    }

    public int getGroupThreadOwner(int n, String string) {
        return (Integer)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$FZtgkcQORLtmljGumeKt4RYKcDI(this, n));
    }

    public double getLatitudeForMessage(int n, boolean bl, String string) {
        return (Double)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$_Cb4PItW7V1g69LCBQGhBiRRtz4(this, bl, n));
    }

    public double getLongitudeForMessage(int n, boolean bl, String string) {
        return (Double)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$DJ1U8cPQGcg90QJamjBaum8g4gc(this, bl, n));
    }

    public long getMessageArrivalTimestamp(int n, boolean bl, String string) {
        return (Long)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$rOTFWScZeGqI_FfpdXKvaj_6TaY(this, bl, n));
    }

    public long getMessageOriginationTimestamp(int n, boolean bl, String string) {
        return (Long)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$nHeOZX6Yex65rTb0Sr3YN_hUFNI(this, bl, n));
    }

    public int[] getMessageRecipients(int n, String string) {
        return (int[])this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$dtjug27mcIESiWPv1SG5VdrJVNk(this, n));
    }

    public long getMessageSeenTimestamp(int n, boolean bl, String string) {
        return (Long)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$YipgJUqRvwIQNiAwv0iAJe9j_Dg(this, bl, n));
    }

    public RcsMessageSnippet getMessageSnippet(int n, String string) {
        return (RcsMessageSnippet)this.performReadOperation(string, (ThrowingSupplier<T>)_$$Lambda$RcsMessageStoreController$yW1mU_OjjSZ_aoCdgW7hX38_lT8.INSTANCE);
    }

    public int getMessageStatus(int n, boolean bl, String string) {
        return (Integer)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$DenzMTC9mTdhbmGgI_nCtmmHclY(this, bl, n));
    }

    public int getMessageSubId(int n, boolean bl, String string) {
        return (Integer)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$jdfxgyc8mRmghuhEspSRnxyRt4Y(this, bl, n));
    }

    public RcsMessageQueryResultParcelable getMessages(RcsMessageQueryParams rcsMessageQueryParams, String string) {
        return (RcsMessageQueryResultParcelable)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$8kZ0Whs2V3H2_A7PZtgThC9cHrM(this, rcsMessageQueryParams));
    }

    public RcsMessageQueryResultParcelable getMessagesWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String string) {
        return (RcsMessageQueryResultParcelable)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$yDvbl7WU_iKHa8m_gKb4dC0kTmU(this, rcsQueryContinuationToken));
    }

    public long getOutgoingDeliveryDeliveredTimestamp(int n, int n2, String string) {
        return (Long)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$cN0QvwaxRss2b1Nol38Q6BWlJb8(this, n, n2));
    }

    public long getOutgoingDeliverySeenTimestamp(int n, int n2, String string) {
        return (Long)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$hOq33zEjGZu3QxvphbbEytozff8(this, n, n2));
    }

    public int getOutgoingDeliveryStatus(int n, int n2, String string) {
        return (Integer)this.performReadOperation(string, (ThrowingSupplier<T>)_$$Lambda$RcsMessageStoreController$ql9qCCnj1UThMFRJGcj36kFApKA.INSTANCE);
    }

    public RcsParticipantQueryResultParcelable getParticipants(RcsParticipantQueryParams rcsParticipantQueryParams, String string) {
        return (RcsParticipantQueryResultParcelable)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$ysPz1siZPIda_DS_nk6hkNkODHo(this, rcsParticipantQueryParams));
    }

    public RcsParticipantQueryResultParcelable getParticipantsWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String string) {
        return (RcsParticipantQueryResultParcelable)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$th762XOIu1OKBMzBiiE5dtAuBnE(this, rcsQueryContinuationToken));
    }

    public String getRcsParticipantAlias(int n, String string) {
        return (String)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$I8TYhXVjUPl2Qbe8Oh1Ed3yhWNw(this, n));
    }

    public String getRcsParticipantCanonicalAddress(int n, String string) {
        return (String)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$d2kuYkT0m7BtnEDDoFJO8Ff4aik(this, n));
    }

    public String getRcsParticipantContactId(int n, String string) {
        return (String)this.performReadOperation(string, (ThrowingSupplier<T>)_$$Lambda$RcsMessageStoreController$75youkeK6UPD_R54BvRLBXlMeuw.INSTANCE);
    }

    public RcsThreadQueryResultParcelable getRcsThreads(RcsThreadQueryParams rcsThreadQueryParams, String string) {
        return (RcsThreadQueryResultParcelable)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$ClTeG1a5315E4yM3I5FjYPv_aqU(this, rcsThreadQueryParams));
    }

    public RcsThreadQueryResultParcelable getRcsThreadsWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String string) {
        return (RcsThreadQueryResultParcelable)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$59OQ763FYDG6pocPJGJOTyticw0(this, rcsQueryContinuationToken));
    }

    public int getSenderParticipant(int n, String string) {
        return (Integer)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$LMhPtb7AbEObwiwot1ObQconTrY(this, n));
    }

    public String getTextForMessage(int n, boolean bl, String string) {
        return (String)this.performReadOperation(string, new _$$Lambda$RcsMessageStoreController$nCbhDPjKFA0Jm53EFmbQexceNLQ(this, bl, n));
    }

    public /* synthetic */ Integer lambda$addIncomingMessage$31$RcsMessageStoreController(RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams, int n) throws RemoteException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("arrival_timestamp", Long.valueOf(rcsIncomingMessageCreationParams.getArrivalTimestamp()));
        contentValues.put("seen_timestamp", Long.valueOf(rcsIncomingMessageCreationParams.getSeenTimestamp()));
        contentValues.put("sender_participant", Integer.valueOf(rcsIncomingMessageCreationParams.getSenderParticipantId()));
        this.mMessageQueryHelper.createContentValuesForGenericMessage(contentValues, n, (RcsMessageCreationParams)rcsIncomingMessageCreationParams);
        return this.addMessage(n, true, contentValues);
    }

    public /* synthetic */ Integer lambda$addOutgoingMessage$32$RcsMessageStoreController(int n, RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams) throws RemoteException {
        ContentValues contentValues = new ContentValues();
        this.mMessageQueryHelper.createContentValuesForGenericMessage(contentValues, n, (RcsMessageCreationParams)rcsOutgoingMessageCreationParams);
        return this.addMessage(n, false, contentValues);
    }

    public /* synthetic */ void lambda$addParticipantToGroupThread$29$RcsMessageStoreController(int n, int n2) throws RemoteException {
        ContentValues contentValues = new ContentValues(2);
        contentValues.put("rcs_thread_id", Integer.valueOf(n));
        contentValues.put("rcs_participant_id", Integer.valueOf(n2));
        this.mContentResolver.insert(RcsThreadQueryHelper.getAllParticipantsInThreadUri(n), contentValues);
    }

    public /* synthetic */ Integer lambda$createGroupThread$11$RcsMessageStoreController(String string, Uri uri, int[] arrn, String string2) throws RemoteException {
        int n = this.mThreadQueryHelper.createGroupThread(string, uri);
        if (n > 0) {
            if (arrn != null) {
                int n2 = arrn.length;
                for (int i = 0; i < n2; ++i) {
                    this.addParticipantToGroupThread(n, arrn[i], string2);
                }
            }
            return n;
        }
        throw new RemoteException("Could not create RcsGroupThread.");
    }

    public /* synthetic */ Integer lambda$createGroupThreadIconChangedEvent$86$RcsMessageStoreController(Uri object, long l, int n, int n2) throws RemoteException {
        ContentValues contentValues = new ContentValues();
        object = object == null ? null : object.toString();
        contentValues.put("new_icon_uri", (String)object);
        return this.mEventQueryHelper.createGroupThreadEvent(8, l, n, n2, contentValues);
    }

    public /* synthetic */ Integer lambda$createGroupThreadNameChangedEvent$85$RcsMessageStoreController(String string, long l, int n, int n2) throws RemoteException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("new_name", string);
        return this.mEventQueryHelper.createGroupThreadEvent(16, l, n, n2, contentValues);
    }

    public /* synthetic */ Integer lambda$createGroupThreadParticipantJoinedEvent$87$RcsMessageStoreController(int n, long l, int n2, int n3) throws RemoteException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("destination_participant", Integer.valueOf(n));
        return this.mEventQueryHelper.createGroupThreadEvent(2, l, n2, n3, contentValues);
    }

    public /* synthetic */ Integer lambda$createGroupThreadParticipantLeftEvent$88$RcsMessageStoreController(int n, long l, int n2, int n3) throws RemoteException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("destination_participant", Integer.valueOf(n));
        return this.mEventQueryHelper.createGroupThreadEvent(4, l, n2, n3, contentValues);
    }

    public /* synthetic */ Integer lambda$createParticipantAliasChangedEvent$89$RcsMessageStoreController(long l, int n, String charSequence) throws RemoteException {
        ContentValues contentValues = new ContentValues(4);
        contentValues.put("origination_timestamp", Long.valueOf(l));
        contentValues.put("source_participant", Integer.valueOf(n));
        contentValues.put("new_alias", (String)charSequence);
        charSequence = this.mContentResolver.insert(this.mEventQueryHelper.getParticipantEventInsertionUri(n), contentValues);
        if (charSequence != null) {
            return Integer.parseInt(charSequence.getLastPathSegment());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Could not create RcsParticipantAliasChangedEvent with participant id: ");
        ((StringBuilder)charSequence).append(n);
        throw new RemoteException(((StringBuilder)charSequence).toString());
    }

    public /* synthetic */ Integer lambda$createRcs1To1Thread$10$RcsMessageStoreController(int n) throws RemoteException {
        return this.mThreadQueryHelper.create1To1Thread(n);
    }

    public /* synthetic */ Integer lambda$createRcsParticipant$12$RcsMessageStoreController(String string, String string2) throws RemoteException {
        ContentValues contentValues = new ContentValues();
        long l = Telephony.RcsColumns.RcsCanonicalAddressHelper.getOrCreateCanonicalAddressId((ContentResolver)this.mContentResolver, (String)string);
        if (l != Integer.MIN_VALUE) {
            contentValues.put("canonical_address_id", Long.valueOf(l));
            contentValues.put("rcs_alias", string2);
            string = this.mContentResolver.insert(Telephony.RcsColumns.RcsParticipantColumns.RCS_PARTICIPANT_URI, contentValues);
            if (string != null) {
                int n;
                try {
                    n = Integer.parseInt(string.getLastPathSegment());
                }
                catch (NumberFormatException numberFormatException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Uri returned after creating a participant is malformed: ");
                    stringBuilder.append((Object)string);
                    throw new RemoteException(stringBuilder.toString());
                }
                return n;
            }
            throw new RemoteException("Error inserting new participant into RcsProvider");
        }
        throw new RemoteException("Could not create or make canonical address entry");
    }

    public /* synthetic */ void lambda$deleteFileTransfer$61$RcsMessageStoreController(int n) throws RemoteException {
        this.mContentResolver.delete(this.mMessageQueryHelper.getFileTransferUpdateUri(n), null, null);
    }

    public /* synthetic */ void lambda$deleteMessage$33$RcsMessageStoreController(int n, boolean bl, int n2, boolean bl2) throws RemoteException {
        this.mContentResolver.delete(this.mMessageQueryHelper.getMessageDeletionUri(n, bl, n2, bl2), null, null);
    }

    public /* synthetic */ Boolean lambda$deleteThread$0$RcsMessageStoreController(int n, int n2) throws RemoteException {
        ContentResolver contentResolver = this.mContentResolver;
        boolean bl = true;
        Uri uri = n == 1 ? Telephony.RcsColumns.RcsGroupThreadColumns.RCS_GROUP_THREAD_URI : Telephony.RcsColumns.Rcs1To1ThreadColumns.RCS_1_TO_1_THREAD_URI;
        if (contentResolver.delete(uri, "rcs_thread_id=?", new String[]{Integer.toString(n2)}) <= 0) {
            bl = false;
        }
        return bl;
    }

    public /* synthetic */ Long lambda$get1To1ThreadFallbackThreadId$19$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getLongValueFromTableRow(Telephony.RcsColumns.Rcs1To1ThreadColumns.RCS_1_TO_1_THREAD_URI, "rcs_fallback_thread_id", "rcs_thread_id", n);
    }

    public /* synthetic */ Integer lambda$get1To1ThreadOtherParticipantId$20$RcsMessageStoreController(int n) throws RemoteException {
        Uri uri;
        block9 : {
            uri = RcsThreadQueryHelper.get1To1ThreadUri(n);
            if ((uri = this.mContentResolver.query(uri, new String[]{"rcs_participant_id"}, null, null)) != null) {
                if (uri.getCount() != 1) break block9;
                uri.moveToNext();
                n = uri.getInt(uri.getColumnIndex("rcs_participant_id"));
                uri.close();
                return n;
            }
        }
        try {
            RemoteException remoteException = new RemoteException("Could not get the thread recipient");
            throw remoteException;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (uri != null) {
                    try {
                        uri.close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                }
                throw throwable2;
            }
        }
    }

    public /* synthetic */ RcsEventQueryResultDescriptor lambda$getEvents$8$RcsMessageStoreController(RcsEventQueryParams rcsEventQueryParams) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putParcelable("event_query_parameters", (Parcelable)rcsEventQueryParams);
        return this.mEventQueryHelper.performEventQuery(bundle);
    }

    public /* synthetic */ RcsEventQueryResultDescriptor lambda$getEventsWithToken$9$RcsMessageStoreController(RcsQueryContinuationToken rcsQueryContinuationToken) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putParcelable("query_continuation_token", (Parcelable)rcsQueryContinuationToken);
        return this.mEventQueryHelper.performEventQuery(bundle);
    }

    public /* synthetic */ String lambda$getFileTransferContentType$68$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getStringValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "content_type", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ Uri lambda$getFileTransferContentUri$66$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getUriValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "content_uri", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ Long lambda$getFileTransferFileSize$70$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getLongValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "file_size", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ Integer lambda$getFileTransferHeight$78$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getIntValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "height", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ Long lambda$getFileTransferLength$80$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getLongValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "duration", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ String lambda$getFileTransferPreviewType$84$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getStringValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "preview_type", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ Uri lambda$getFileTransferPreviewUri$82$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getUriValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "duration", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ String lambda$getFileTransferSessionId$64$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getStringValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "session_id", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ Integer lambda$getFileTransferStatus$74$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getIntValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "status", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ Long lambda$getFileTransferTransferOffset$72$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getLongValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "transfer_offset", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ Integer lambda$getFileTransferWidth$76$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getIntValueFromTableRow(Telephony.RcsColumns.RcsFileTransferColumns.FILE_TRANSFER_URI, "width", "rcs_file_transfer_id", n);
    }

    public /* synthetic */ String lambda$getGlobalMessageIdForMessage$41$RcsMessageStoreController(boolean bl, int n) throws RemoteException {
        return this.mMessageStoreUtil.getStringValueFromTableRow(RcsMessageStoreUtil.getMessageTableUri(bl), "rcs_message_global_id", "rcs_message_row_id", n);
    }

    public /* synthetic */ Uri lambda$getGroupThreadConferenceUri$28$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getUriValueFromTableRow(Telephony.RcsColumns.RcsGroupThreadColumns.RCS_GROUP_THREAD_URI, "conference_uri", "rcs_thread_id", n);
    }

    public /* synthetic */ Uri lambda$getGroupThreadIcon$24$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getUriValueFromTableRow(Telephony.RcsColumns.RcsGroupThreadColumns.RCS_GROUP_THREAD_URI, "group_icon", "rcs_thread_id", n);
    }

    public /* synthetic */ String lambda$getGroupThreadName$22$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getStringValueFromTableRow(Telephony.RcsColumns.RcsGroupThreadColumns.RCS_GROUP_THREAD_URI, "group_name", "rcs_thread_id", n);
    }

    public /* synthetic */ Integer lambda$getGroupThreadOwner$26$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getIntValueFromTableRow(Telephony.RcsColumns.RcsGroupThreadColumns.RCS_GROUP_THREAD_URI, "owner_participant", "rcs_thread_id", n);
    }

    public /* synthetic */ Double lambda$getLatitudeForMessage$56$RcsMessageStoreController(boolean bl, int n) throws RemoteException {
        return this.mMessageStoreUtil.getDoubleValueFromTableRow(RcsMessageStoreUtil.getMessageTableUri(bl), "latitude", "rcs_message_row_id", n);
    }

    public /* synthetic */ Double lambda$getLongitudeForMessage$58$RcsMessageStoreController(boolean bl, int n) throws RemoteException {
        return this.mMessageStoreUtil.getDoubleValueFromTableRow(RcsMessageStoreUtil.getMessageTableUri(bl), "longitude", "rcs_message_row_id", n);
    }

    public /* synthetic */ Long lambda$getMessageArrivalTimestamp$43$RcsMessageStoreController(boolean bl, int n) throws RemoteException {
        return this.mMessageStoreUtil.getLongValueFromTableRow(RcsMessageStoreUtil.getMessageTableUri(bl), "arrival_timestamp", "rcs_message_row_id", n);
    }

    public /* synthetic */ Long lambda$getMessageOriginationTimestamp$39$RcsMessageStoreController(boolean bl, int n) throws RemoteException {
        return this.mMessageStoreUtil.getLongValueFromTableRow(RcsMessageStoreUtil.getMessageTableUri(bl), "origination_timestamp", "rcs_message_row_id", n);
    }

    public /* synthetic */ int[] lambda$getMessageRecipients$46$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageQueryHelper.getDeliveryParticipantsForMessage(n);
    }

    public /* synthetic */ Long lambda$getMessageSeenTimestamp$45$RcsMessageStoreController(boolean bl, int n) throws RemoteException {
        return this.mMessageStoreUtil.getLongValueFromTableRow(RcsMessageStoreUtil.getMessageTableUri(bl), "seen_timestamp", "rcs_message_row_id", n);
    }

    public /* synthetic */ Integer lambda$getMessageStatus$37$RcsMessageStoreController(boolean bl, int n) throws RemoteException {
        return this.mMessageStoreUtil.getIntValueFromTableRow(RcsMessageStoreUtil.getMessageTableUri(bl), "status", "rcs_message_row_id", n);
    }

    public /* synthetic */ Integer lambda$getMessageSubId$35$RcsMessageStoreController(boolean bl, int n) throws RemoteException {
        return this.mMessageStoreUtil.getIntValueFromTableRow(RcsMessageStoreUtil.getMessageTableUri(bl), "sub_id", "rcs_message_row_id", n);
    }

    public /* synthetic */ RcsMessageQueryResultParcelable lambda$getMessages$6$RcsMessageStoreController(RcsMessageQueryParams rcsMessageQueryParams) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putParcelable("message_query_parameters", (Parcelable)rcsMessageQueryParams);
        return this.mMessageQueryHelper.performMessageQuery(bundle);
    }

    public /* synthetic */ RcsMessageQueryResultParcelable lambda$getMessagesWithToken$7$RcsMessageStoreController(RcsQueryContinuationToken rcsQueryContinuationToken) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putParcelable("query_continuation_token", (Parcelable)rcsQueryContinuationToken);
        return this.mMessageQueryHelper.performMessageQuery(bundle);
    }

    public /* synthetic */ Long lambda$getOutgoingDeliveryDeliveredTimestamp$47$RcsMessageStoreController(int n, int n2) throws RemoteException {
        return this.mMessageQueryHelper.getLongValueFromDelivery(n, n2, "delivered_timestamp");
    }

    public /* synthetic */ Long lambda$getOutgoingDeliverySeenTimestamp$49$RcsMessageStoreController(int n, int n2) throws RemoteException {
        return this.mMessageQueryHelper.getLongValueFromDelivery(n, n2, "seen_timestamp");
    }

    public /* synthetic */ RcsParticipantQueryResultParcelable lambda$getParticipants$4$RcsMessageStoreController(RcsParticipantQueryParams rcsParticipantQueryParams) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putParcelable("participant_query_parameters", (Parcelable)rcsParticipantQueryParams);
        return this.mParticipantQueryHelper.performParticipantQuery(bundle);
    }

    public /* synthetic */ RcsParticipantQueryResultParcelable lambda$getParticipantsWithToken$5$RcsMessageStoreController(RcsQueryContinuationToken rcsQueryContinuationToken) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putParcelable("query_continuation_token", (Parcelable)rcsQueryContinuationToken);
        return this.mParticipantQueryHelper.performParticipantQuery(bundle);
    }

    public /* synthetic */ String lambda$getRcsParticipantAlias$14$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getStringValueFromTableRow(Telephony.RcsColumns.RcsParticipantColumns.RCS_PARTICIPANT_URI, "rcs_alias", "rcs_participant_id", n);
    }

    public /* synthetic */ String lambda$getRcsParticipantCanonicalAddress$13$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getStringValueFromTableRow(Telephony.RcsColumns.RcsParticipantColumns.RCS_PARTICIPANT_URI, "address", "rcs_participant_id", n);
    }

    public /* synthetic */ RcsThreadQueryResultParcelable lambda$getRcsThreads$2$RcsMessageStoreController(RcsThreadQueryParams rcsThreadQueryParams) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putParcelable("thread_query_parameters", (Parcelable)rcsThreadQueryParams);
        return this.mThreadQueryHelper.performThreadQuery(bundle);
    }

    public /* synthetic */ RcsThreadQueryResultParcelable lambda$getRcsThreadsWithToken$3$RcsMessageStoreController(RcsQueryContinuationToken rcsQueryContinuationToken) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putParcelable("query_continuation_token", (Parcelable)rcsQueryContinuationToken);
        return this.mThreadQueryHelper.performThreadQuery(bundle);
    }

    public /* synthetic */ Integer lambda$getSenderParticipant$60$RcsMessageStoreController(int n) throws RemoteException {
        return this.mMessageStoreUtil.getIntValueFromTableRow(Telephony.RcsColumns.RcsIncomingMessageColumns.INCOMING_MESSAGE_URI, "sender_participant", "rcs_message_row_id", n);
    }

    public /* synthetic */ String lambda$getTextForMessage$54$RcsMessageStoreController(boolean bl, int n) throws RemoteException {
        return this.mMessageStoreUtil.getStringValueFromTableRow(RcsMessageStoreUtil.getMessageTableUri(bl), "rcs_text", "rcs_message_row_id", n);
    }

    public /* synthetic */ void lambda$removeParticipantFromGroupThread$30$RcsMessageStoreController(int n, int n2) throws RemoteException {
        this.mContentResolver.delete(RcsThreadQueryHelper.getParticipantInThreadUri(n, n2), null, null);
    }

    public /* synthetic */ void lambda$set1To1ThreadFallbackThreadId$18$RcsMessageStoreController(int n, long l) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(RcsThreadQueryHelper.get1To1ThreadUri(n), "rcs_fallback_thread_id", l, "Could not set fallback thread ID");
    }

    public /* synthetic */ void lambda$setFileTransferContentType$67$RcsMessageStoreController(int n, String string) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "content_type", string, "Could not set content type for file transfer");
    }

    public /* synthetic */ void lambda$setFileTransferContentUri$65$RcsMessageStoreController(int n, Uri uri) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "content_uri", uri, "Could not set content URI for file transfer");
    }

    public /* synthetic */ void lambda$setFileTransferFileSize$69$RcsMessageStoreController(int n, long l) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "file_size", l, "Could not set file size for file transfer");
    }

    public /* synthetic */ void lambda$setFileTransferHeight$77$RcsMessageStoreController(int n, int n2) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "height", n2, "Could not set height of file transfer");
    }

    public /* synthetic */ void lambda$setFileTransferLength$79$RcsMessageStoreController(int n, long l) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "duration", l, "Could not set length of file transfer");
    }

    public /* synthetic */ void lambda$setFileTransferPreviewType$83$RcsMessageStoreController(int n, String string) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "preview_type", string, "Could not set preview type of file transfer");
    }

    public /* synthetic */ void lambda$setFileTransferPreviewUri$81$RcsMessageStoreController(int n, Uri uri) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "preview_uri", uri, "Could not set preview URI of file transfer");
    }

    public /* synthetic */ void lambda$setFileTransferSessionId$63$RcsMessageStoreController(int n, String string) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "session_id", string, "Could not set session ID for file transfer");
    }

    public /* synthetic */ void lambda$setFileTransferStatus$73$RcsMessageStoreController(int n, int n2) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "transfer_status", n2, "Could not set transfer status for file transfer");
    }

    public /* synthetic */ void lambda$setFileTransferTransferOffset$71$RcsMessageStoreController(int n, long l) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "transfer_offset", l, "Could not set transfer offset for file transfer");
    }

    public /* synthetic */ void lambda$setFileTransferWidth$75$RcsMessageStoreController(int n, int n2) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getFileTransferUpdateUri(n), "width", n2, "Could not set width of file transfer");
    }

    public /* synthetic */ void lambda$setGlobalMessageIdForMessage$40$RcsMessageStoreController(int n, boolean bl, String string) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageUpdateUri(n, bl), "rcs_message_global_id", string, "Could not set the global ID for message");
    }

    public /* synthetic */ void lambda$setGroupThreadConferenceUri$27$RcsMessageStoreController(int n, Uri uri) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(RcsThreadQueryHelper.getGroupThreadUri(n), "conference_uri", uri, "Could not set the conference URI for group");
    }

    public /* synthetic */ void lambda$setGroupThreadIcon$23$RcsMessageStoreController(int n, Uri uri) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(RcsThreadQueryHelper.getGroupThreadUri(n), "group_icon", uri, "Could not update group icon");
    }

    public /* synthetic */ void lambda$setGroupThreadName$21$RcsMessageStoreController(int n, String string) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(RcsThreadQueryHelper.getGroupThreadUri(n), "group_name", string, "Could not update group name");
    }

    public /* synthetic */ void lambda$setGroupThreadOwner$25$RcsMessageStoreController(int n, int n2) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(RcsThreadQueryHelper.getGroupThreadUri(n), "owner_participant", n2, "Could not set the group owner");
    }

    public /* synthetic */ void lambda$setLatitudeForMessage$55$RcsMessageStoreController(int n, boolean bl, double d) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageUpdateUri(n, bl), "latitude", d, "Could not update latitude for message");
    }

    public /* synthetic */ void lambda$setLongitudeForMessage$57$RcsMessageStoreController(int n, boolean bl, double d) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageUpdateUri(n, bl), "longitude", d, "Could not set longitude for message");
    }

    public /* synthetic */ void lambda$setMessageArrivalTimestamp$42$RcsMessageStoreController(int n, boolean bl, long l) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageUpdateUri(n, bl), "arrival_timestamp", l, "Could not update the arrival timestamp for message");
    }

    public /* synthetic */ void lambda$setMessageOriginationTimestamp$38$RcsMessageStoreController(int n, boolean bl, long l) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageUpdateUri(n, bl), "origination_timestamp", l, "Could not set the origination timestamp for message");
    }

    public /* synthetic */ void lambda$setMessageSeenTimestamp$44$RcsMessageStoreController(int n, boolean bl, long l) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageUpdateUri(n, bl), "seen_timestamp", l, "Could not set the notified timestamp for message");
    }

    public /* synthetic */ void lambda$setMessageStatus$36$RcsMessageStoreController(int n, boolean bl, int n2) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageUpdateUri(n, bl), "status", n2, "Could not set the status for message");
    }

    public /* synthetic */ void lambda$setMessageSubId$34$RcsMessageStoreController(int n, boolean bl, int n2) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageUpdateUri(n, bl), "sub_id", n2, "Could not set subscription ID for message");
    }

    public /* synthetic */ void lambda$setOutgoingDeliveryDeliveredTimestamp$48$RcsMessageStoreController(int n, int n2, long l) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageDeliveryUri(n, n2), "delivered_timestamp", l, "Could not update the delivered timestamp for outgoing delivery");
    }

    public /* synthetic */ void lambda$setOutgoingDeliverySeenTimestamp$50$RcsMessageStoreController(int n, int n2, long l) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageDeliveryUri(n, n2), "seen_timestamp", l, "Could not update the seen timestamp for outgoing delivery");
    }

    public /* synthetic */ void lambda$setRcsParticipantAlias$15$RcsMessageStoreController(int n, String string) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(RcsParticipantQueryHelper.getUriForParticipant(n), "rcs_alias", string, "Could not update RCS participant alias");
    }

    public /* synthetic */ void lambda$setTextForMessage$53$RcsMessageStoreController(int n, boolean bl, String string) throws RemoteException {
        this.mMessageStoreUtil.updateValueOfProviderUri(this.mMessageQueryHelper.getMessageUpdateUri(n, bl), "rcs_text", string, "Could not set the text for message");
    }

    public /* synthetic */ Integer lambda$storeFileTransfer$62$RcsMessageStoreController(RcsFileTransferCreationParams rcsFileTransferCreationParams, int n) throws RemoteException {
        rcsFileTransferCreationParams = this.mMessageQueryHelper.getContentValuesForFileTransfer(rcsFileTransferCreationParams);
        rcsFileTransferCreationParams = this.mContentResolver.insert(this.mMessageQueryHelper.getFileTransferInsertionUri(n), (ContentValues)rcsFileTransferCreationParams);
        if (rcsFileTransferCreationParams != null) {
            return Integer.parseInt(rcsFileTransferCreationParams.getLastPathSegment());
        }
        return Integer.MIN_VALUE;
    }

    public void removeParticipantFromGroupThread(int n, int n2, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$JHzHPFCAseKapBbeL98bJFNAzig(this, n, n2));
    }

    public void set1To1ThreadFallbackThreadId(int n, long l, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$RAd62JxQGCnFeaVRbYvfRMq2nMQ(this, n, l));
    }

    public void setFileTransferContentType(int n, String string, String string2) {
        this.performWriteOperation(string2, new _$$Lambda$RcsMessageStoreController$QuCMf_ZjaL38JKfdVdaXlhUciYg(this, n, string));
    }

    public void setFileTransferContentUri(int n, Uri uri, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$VySBVSH4PKKZDjHYzvMaQ6sXAh4(this, n, uri));
    }

    public void setFileTransferFileSize(int n, long l, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$NyzhqlBzK5HgvBBj0y_IUKT7n6c(this, n, l));
    }

    public void setFileTransferHeight(int n, int n2, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$cOS8pT4JicoW_zRFZJ1cYJTDjoE(this, n, n2));
    }

    public void setFileTransferLength(int n, long l, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$_MbBnVvQk8PdmMcn1WGDQaVhTok(this, n, l));
    }

    public void setFileTransferPreviewType(int n, String string, String string2) {
        this.performWriteOperation(string2, new _$$Lambda$RcsMessageStoreController$zWLrx_FcTUxnS1nsFt35WphvPII(this, n, string));
    }

    public void setFileTransferPreviewUri(int n, Uri uri, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$d0MdM4t_qzzQuPmkNRmZ3KJdjlE(this, n, uri));
    }

    public void setFileTransferSessionId(int n, String string, String string2) {
        this.performWriteOperation(string2, new _$$Lambda$RcsMessageStoreController$CrJMOfHeqLMbEHM26Tzgd2h0xwc(this, n, string));
    }

    public void setFileTransferStatus(int n, int n2, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$fyr6oqGnSJUp4Gt59YfyGGlG77Q(this, n, n2));
    }

    public void setFileTransferTransferOffset(int n, long l, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$_QH7KyUipgV0PmMsbFbK5_YvipI(this, n, l));
    }

    public void setFileTransferWidth(int n, int n2, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$lpHB7xyEmqwVVQoFOttd3ccPVsk(this, n, n2));
    }

    public void setGlobalMessageIdForMessage(int n, boolean bl, String string, String string2) {
        this.performWriteOperation(string2, new _$$Lambda$RcsMessageStoreController$emhqhI_Zb6_HnA9QWgvIl6aqRig(this, n, bl, string));
    }

    public void setGroupThreadConferenceUri(int n, Uri uri, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$uHotLs093tvDOCQG2PWoegXouxA(this, n, uri));
    }

    public void setGroupThreadIcon(int n, Uri uri, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$9wFprf97OtAZJet5n97zbx_SmAw(this, n, uri));
    }

    public void setGroupThreadName(int n, String string, String string2) {
        this.performWriteOperation(string2, new _$$Lambda$RcsMessageStoreController$TpIpy59FjUIVg96luEg12VkT5aw(this, n, string));
    }

    public void setGroupThreadOwner(int n, int n2, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$LKp6ysypBzOb0aWBWdCnqPDcLHA(this, n, n2));
    }

    public void setLatitudeForMessage(int n, boolean bl, double d, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$xC3HWhR80aJOt1X6r83yAZM3tUQ(this, n, bl, d));
    }

    public void setLongitudeForMessage(int n, boolean bl, double d, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$rUl7GR0q_Zo6dP_OpjyrnxyNLc4(this, n, bl, d));
    }

    public void setMessageArrivalTimestamp(int n, boolean bl, long l, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$kKfCdoAe6weg7LBn30W5ytjoBmg(this, n, bl, l));
    }

    public void setMessageOriginationTimestamp(int n, boolean bl, long l, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$EUwM1EwT7Lz6sUEnYRpBVA8Q4Yo(this, n, bl, l));
    }

    public void setMessageSeenTimestamp(int n, boolean bl, long l, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$ZbAi2Z4Zs7PCzpYK2ddr149tEyQ(this, n, bl, l));
    }

    public void setMessageStatus(int n, boolean bl, int n2, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$oYdMpGEerSj76ai9qOJ48sYoY14(this, n, bl, n2));
    }

    public void setMessageSubId(int n, boolean bl, int n2, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$Ydc70CwcfJDOBqlqBcqxTt38Uzo(this, n, bl, n2));
    }

    public void setOutgoingDeliveryDeliveredTimestamp(int n, int n2, long l, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$K1FMaXVKrG26Oq2VVJlnciT_hdc(this, n, n2, l));
    }

    public void setOutgoingDeliverySeenTimestamp(int n, int n2, long l, String string) {
        this.performWriteOperation(string, new _$$Lambda$RcsMessageStoreController$jHeKn_absIeh0lBHfJyzVOO62F0(this, n, n2, l));
    }

    public void setOutgoingDeliveryStatus(int n, int n2, int n3, String string) {
        this.performWriteOperation(string, (ThrowingRunnable)_$$Lambda$RcsMessageStoreController$Abaq2KJr5w02nAfSHDpHL8cbGCM.INSTANCE);
    }

    public void setRcsParticipantAlias(int n, String string, String string2) {
        this.performWriteOperation(string2, new _$$Lambda$RcsMessageStoreController$GMu9xFuH6Pwj30sjJjU3zueFCNY(this, n, string));
    }

    public void setRcsParticipantContactId(int n, String string, String string2) {
        this.performWriteOperation(string2, (ThrowingRunnable)_$$Lambda$RcsMessageStoreController$n7YWjkBre8yAm3X4Ma8Y6IJulUU.INSTANCE);
    }

    public void setTextForMessage(int n, boolean bl, String string, String string2) {
        this.performWriteOperation(string2, new _$$Lambda$RcsMessageStoreController$fgKkPLRhFZnK35RO00UEoYUhO3I(this, n, bl, string));
    }

    public int storeFileTransfer(int n, boolean bl, RcsFileTransferCreationParams rcsFileTransferCreationParams, String string) {
        return (Integer)this.performCreateOperation(string, new _$$Lambda$RcsMessageStoreController$1jwBeSBvZDdyDt_jPAHrOI2_Kp8(this, rcsFileTransferCreationParams, n));
    }

    static interface ThrowingRunnable {
        public void run() throws RemoteException;
    }

    static interface ThrowingSupplier<T> {
        public T get() throws RemoteException;
    }

}

