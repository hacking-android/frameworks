/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.aidl;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.ims.RcsEventQueryParams;
import android.telephony.ims.RcsEventQueryResultDescriptor;
import android.telephony.ims.RcsFileTransferCreationParams;
import android.telephony.ims.RcsIncomingMessageCreationParams;
import android.telephony.ims.RcsMessageQueryParams;
import android.telephony.ims.RcsMessageQueryResultParcelable;
import android.telephony.ims.RcsMessageSnippet;
import android.telephony.ims.RcsOutgoingMessageCreationParams;
import android.telephony.ims.RcsParticipantQueryParams;
import android.telephony.ims.RcsParticipantQueryResultParcelable;
import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims.RcsThreadQueryParams;
import android.telephony.ims.RcsThreadQueryResultParcelable;

public interface IRcs
extends IInterface {
    public int addIncomingMessage(int var1, RcsIncomingMessageCreationParams var2, String var3) throws RemoteException;

    public int addOutgoingMessage(int var1, RcsOutgoingMessageCreationParams var2, String var3) throws RemoteException;

    public void addParticipantToGroupThread(int var1, int var2, String var3) throws RemoteException;

    public int createGroupThread(int[] var1, String var2, Uri var3, String var4) throws RemoteException;

    public int createGroupThreadIconChangedEvent(long var1, int var3, int var4, Uri var5, String var6) throws RemoteException;

    public int createGroupThreadNameChangedEvent(long var1, int var3, int var4, String var5, String var6) throws RemoteException;

    public int createGroupThreadParticipantJoinedEvent(long var1, int var3, int var4, int var5, String var6) throws RemoteException;

    public int createGroupThreadParticipantLeftEvent(long var1, int var3, int var4, int var5, String var6) throws RemoteException;

    public int createParticipantAliasChangedEvent(long var1, int var3, String var4, String var5) throws RemoteException;

    public int createRcs1To1Thread(int var1, String var2) throws RemoteException;

    public int createRcsParticipant(String var1, String var2, String var3) throws RemoteException;

    public void deleteFileTransfer(int var1, String var2) throws RemoteException;

    public void deleteMessage(int var1, boolean var2, int var3, boolean var4, String var5) throws RemoteException;

    public boolean deleteThread(int var1, int var2, String var3) throws RemoteException;

    public long get1To1ThreadFallbackThreadId(int var1, String var2) throws RemoteException;

    public int get1To1ThreadOtherParticipantId(int var1, String var2) throws RemoteException;

    public RcsEventQueryResultDescriptor getEvents(RcsEventQueryParams var1, String var2) throws RemoteException;

    public RcsEventQueryResultDescriptor getEventsWithToken(RcsQueryContinuationToken var1, String var2) throws RemoteException;

    public String getFileTransferContentType(int var1, String var2) throws RemoteException;

    public Uri getFileTransferContentUri(int var1, String var2) throws RemoteException;

    public long getFileTransferFileSize(int var1, String var2) throws RemoteException;

    public int getFileTransferHeight(int var1, String var2) throws RemoteException;

    public long getFileTransferLength(int var1, String var2) throws RemoteException;

    public String getFileTransferPreviewType(int var1, String var2) throws RemoteException;

    public Uri getFileTransferPreviewUri(int var1, String var2) throws RemoteException;

    public String getFileTransferSessionId(int var1, String var2) throws RemoteException;

    public int getFileTransferStatus(int var1, String var2) throws RemoteException;

    public long getFileTransferTransferOffset(int var1, String var2) throws RemoteException;

    public int getFileTransferWidth(int var1, String var2) throws RemoteException;

    public int[] getFileTransfersAttachedToMessage(int var1, boolean var2, String var3) throws RemoteException;

    public String getGlobalMessageIdForMessage(int var1, boolean var2, String var3) throws RemoteException;

    public Uri getGroupThreadConferenceUri(int var1, String var2) throws RemoteException;

    public Uri getGroupThreadIcon(int var1, String var2) throws RemoteException;

    public String getGroupThreadName(int var1, String var2) throws RemoteException;

    public int getGroupThreadOwner(int var1, String var2) throws RemoteException;

    public double getLatitudeForMessage(int var1, boolean var2, String var3) throws RemoteException;

    public double getLongitudeForMessage(int var1, boolean var2, String var3) throws RemoteException;

    public long getMessageArrivalTimestamp(int var1, boolean var2, String var3) throws RemoteException;

    public long getMessageOriginationTimestamp(int var1, boolean var2, String var3) throws RemoteException;

    public int[] getMessageRecipients(int var1, String var2) throws RemoteException;

    public long getMessageSeenTimestamp(int var1, boolean var2, String var3) throws RemoteException;

    public RcsMessageSnippet getMessageSnippet(int var1, String var2) throws RemoteException;

    public int getMessageStatus(int var1, boolean var2, String var3) throws RemoteException;

    public int getMessageSubId(int var1, boolean var2, String var3) throws RemoteException;

    public RcsMessageQueryResultParcelable getMessages(RcsMessageQueryParams var1, String var2) throws RemoteException;

    public RcsMessageQueryResultParcelable getMessagesWithToken(RcsQueryContinuationToken var1, String var2) throws RemoteException;

    public long getOutgoingDeliveryDeliveredTimestamp(int var1, int var2, String var3) throws RemoteException;

    public long getOutgoingDeliverySeenTimestamp(int var1, int var2, String var3) throws RemoteException;

    public int getOutgoingDeliveryStatus(int var1, int var2, String var3) throws RemoteException;

    public RcsParticipantQueryResultParcelable getParticipants(RcsParticipantQueryParams var1, String var2) throws RemoteException;

    public RcsParticipantQueryResultParcelable getParticipantsWithToken(RcsQueryContinuationToken var1, String var2) throws RemoteException;

    public String getRcsParticipantAlias(int var1, String var2) throws RemoteException;

    public String getRcsParticipantCanonicalAddress(int var1, String var2) throws RemoteException;

    public String getRcsParticipantContactId(int var1, String var2) throws RemoteException;

    public RcsThreadQueryResultParcelable getRcsThreads(RcsThreadQueryParams var1, String var2) throws RemoteException;

    public RcsThreadQueryResultParcelable getRcsThreadsWithToken(RcsQueryContinuationToken var1, String var2) throws RemoteException;

    public int getSenderParticipant(int var1, String var2) throws RemoteException;

    public String getTextForMessage(int var1, boolean var2, String var3) throws RemoteException;

    public void removeParticipantFromGroupThread(int var1, int var2, String var3) throws RemoteException;

    public void set1To1ThreadFallbackThreadId(int var1, long var2, String var4) throws RemoteException;

    public void setFileTransferContentType(int var1, String var2, String var3) throws RemoteException;

    public void setFileTransferContentUri(int var1, Uri var2, String var3) throws RemoteException;

    public void setFileTransferFileSize(int var1, long var2, String var4) throws RemoteException;

    public void setFileTransferHeight(int var1, int var2, String var3) throws RemoteException;

    public void setFileTransferLength(int var1, long var2, String var4) throws RemoteException;

    public void setFileTransferPreviewType(int var1, String var2, String var3) throws RemoteException;

    public void setFileTransferPreviewUri(int var1, Uri var2, String var3) throws RemoteException;

    public void setFileTransferSessionId(int var1, String var2, String var3) throws RemoteException;

    public void setFileTransferStatus(int var1, int var2, String var3) throws RemoteException;

    public void setFileTransferTransferOffset(int var1, long var2, String var4) throws RemoteException;

    public void setFileTransferWidth(int var1, int var2, String var3) throws RemoteException;

    public void setGlobalMessageIdForMessage(int var1, boolean var2, String var3, String var4) throws RemoteException;

    public void setGroupThreadConferenceUri(int var1, Uri var2, String var3) throws RemoteException;

    public void setGroupThreadIcon(int var1, Uri var2, String var3) throws RemoteException;

    public void setGroupThreadName(int var1, String var2, String var3) throws RemoteException;

    public void setGroupThreadOwner(int var1, int var2, String var3) throws RemoteException;

    public void setLatitudeForMessage(int var1, boolean var2, double var3, String var5) throws RemoteException;

    public void setLongitudeForMessage(int var1, boolean var2, double var3, String var5) throws RemoteException;

    public void setMessageArrivalTimestamp(int var1, boolean var2, long var3, String var5) throws RemoteException;

    public void setMessageOriginationTimestamp(int var1, boolean var2, long var3, String var5) throws RemoteException;

    public void setMessageSeenTimestamp(int var1, boolean var2, long var3, String var5) throws RemoteException;

    public void setMessageStatus(int var1, boolean var2, int var3, String var4) throws RemoteException;

    public void setMessageSubId(int var1, boolean var2, int var3, String var4) throws RemoteException;

    public void setOutgoingDeliveryDeliveredTimestamp(int var1, int var2, long var3, String var5) throws RemoteException;

    public void setOutgoingDeliverySeenTimestamp(int var1, int var2, long var3, String var5) throws RemoteException;

    public void setOutgoingDeliveryStatus(int var1, int var2, int var3, String var4) throws RemoteException;

    public void setRcsParticipantAlias(int var1, String var2, String var3) throws RemoteException;

    public void setRcsParticipantContactId(int var1, String var2, String var3) throws RemoteException;

    public void setTextForMessage(int var1, boolean var2, String var3, String var4) throws RemoteException;

    public int storeFileTransfer(int var1, boolean var2, RcsFileTransferCreationParams var3, String var4) throws RemoteException;

    public static class Default
    implements IRcs {
        @Override
        public int addIncomingMessage(int n, RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int addOutgoingMessage(int n, RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void addParticipantToGroupThread(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int createGroupThread(int[] arrn, String string2, Uri uri, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public int createGroupThreadIconChangedEvent(long l, int n, int n2, Uri uri, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int createGroupThreadNameChangedEvent(long l, int n, int n2, String string2, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public int createGroupThreadParticipantJoinedEvent(long l, int n, int n2, int n3, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int createGroupThreadParticipantLeftEvent(long l, int n, int n2, int n3, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int createParticipantAliasChangedEvent(long l, int n, String string2, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public int createRcs1To1Thread(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int createRcsParticipant(String string2, String string3, String string4) throws RemoteException {
            return 0;
        }

        @Override
        public void deleteFileTransfer(int n, String string2) throws RemoteException {
        }

        @Override
        public void deleteMessage(int n, boolean bl, int n2, boolean bl2, String string2) throws RemoteException {
        }

        @Override
        public boolean deleteThread(int n, int n2, String string2) throws RemoteException {
            return false;
        }

        @Override
        public long get1To1ThreadFallbackThreadId(int n, String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public int get1To1ThreadOtherParticipantId(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public RcsEventQueryResultDescriptor getEvents(RcsEventQueryParams rcsEventQueryParams, String string2) throws RemoteException {
            return null;
        }

        @Override
        public RcsEventQueryResultDescriptor getEventsWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getFileTransferContentType(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public Uri getFileTransferContentUri(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public long getFileTransferFileSize(int n, String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public int getFileTransferHeight(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public long getFileTransferLength(int n, String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public String getFileTransferPreviewType(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public Uri getFileTransferPreviewUri(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getFileTransferSessionId(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getFileTransferStatus(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public long getFileTransferTransferOffset(int n, String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public int getFileTransferWidth(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int[] getFileTransfersAttachedToMessage(int n, boolean bl, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getGlobalMessageIdForMessage(int n, boolean bl, String string2) throws RemoteException {
            return null;
        }

        @Override
        public Uri getGroupThreadConferenceUri(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public Uri getGroupThreadIcon(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getGroupThreadName(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getGroupThreadOwner(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public double getLatitudeForMessage(int n, boolean bl, String string2) throws RemoteException {
            return 0.0;
        }

        @Override
        public double getLongitudeForMessage(int n, boolean bl, String string2) throws RemoteException {
            return 0.0;
        }

        @Override
        public long getMessageArrivalTimestamp(int n, boolean bl, String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public long getMessageOriginationTimestamp(int n, boolean bl, String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public int[] getMessageRecipients(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public long getMessageSeenTimestamp(int n, boolean bl, String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public RcsMessageSnippet getMessageSnippet(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getMessageStatus(int n, boolean bl, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getMessageSubId(int n, boolean bl, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public RcsMessageQueryResultParcelable getMessages(RcsMessageQueryParams rcsMessageQueryParams, String string2) throws RemoteException {
            return null;
        }

        @Override
        public RcsMessageQueryResultParcelable getMessagesWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String string2) throws RemoteException {
            return null;
        }

        @Override
        public long getOutgoingDeliveryDeliveredTimestamp(int n, int n2, String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public long getOutgoingDeliverySeenTimestamp(int n, int n2, String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public int getOutgoingDeliveryStatus(int n, int n2, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public RcsParticipantQueryResultParcelable getParticipants(RcsParticipantQueryParams rcsParticipantQueryParams, String string2) throws RemoteException {
            return null;
        }

        @Override
        public RcsParticipantQueryResultParcelable getParticipantsWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getRcsParticipantAlias(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getRcsParticipantCanonicalAddress(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getRcsParticipantContactId(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public RcsThreadQueryResultParcelable getRcsThreads(RcsThreadQueryParams rcsThreadQueryParams, String string2) throws RemoteException {
            return null;
        }

        @Override
        public RcsThreadQueryResultParcelable getRcsThreadsWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getSenderParticipant(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public String getTextForMessage(int n, boolean bl, String string2) throws RemoteException {
            return null;
        }

        @Override
        public void removeParticipantFromGroupThread(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void set1To1ThreadFallbackThreadId(int n, long l, String string2) throws RemoteException {
        }

        @Override
        public void setFileTransferContentType(int n, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setFileTransferContentUri(int n, Uri uri, String string2) throws RemoteException {
        }

        @Override
        public void setFileTransferFileSize(int n, long l, String string2) throws RemoteException {
        }

        @Override
        public void setFileTransferHeight(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void setFileTransferLength(int n, long l, String string2) throws RemoteException {
        }

        @Override
        public void setFileTransferPreviewType(int n, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setFileTransferPreviewUri(int n, Uri uri, String string2) throws RemoteException {
        }

        @Override
        public void setFileTransferSessionId(int n, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setFileTransferStatus(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void setFileTransferTransferOffset(int n, long l, String string2) throws RemoteException {
        }

        @Override
        public void setFileTransferWidth(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void setGlobalMessageIdForMessage(int n, boolean bl, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setGroupThreadConferenceUri(int n, Uri uri, String string2) throws RemoteException {
        }

        @Override
        public void setGroupThreadIcon(int n, Uri uri, String string2) throws RemoteException {
        }

        @Override
        public void setGroupThreadName(int n, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setGroupThreadOwner(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void setLatitudeForMessage(int n, boolean bl, double d, String string2) throws RemoteException {
        }

        @Override
        public void setLongitudeForMessage(int n, boolean bl, double d, String string2) throws RemoteException {
        }

        @Override
        public void setMessageArrivalTimestamp(int n, boolean bl, long l, String string2) throws RemoteException {
        }

        @Override
        public void setMessageOriginationTimestamp(int n, boolean bl, long l, String string2) throws RemoteException {
        }

        @Override
        public void setMessageSeenTimestamp(int n, boolean bl, long l, String string2) throws RemoteException {
        }

        @Override
        public void setMessageStatus(int n, boolean bl, int n2, String string2) throws RemoteException {
        }

        @Override
        public void setMessageSubId(int n, boolean bl, int n2, String string2) throws RemoteException {
        }

        @Override
        public void setOutgoingDeliveryDeliveredTimestamp(int n, int n2, long l, String string2) throws RemoteException {
        }

        @Override
        public void setOutgoingDeliverySeenTimestamp(int n, int n2, long l, String string2) throws RemoteException {
        }

        @Override
        public void setOutgoingDeliveryStatus(int n, int n2, int n3, String string2) throws RemoteException {
        }

        @Override
        public void setRcsParticipantAlias(int n, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setRcsParticipantContactId(int n, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setTextForMessage(int n, boolean bl, String string2, String string3) throws RemoteException {
        }

        @Override
        public int storeFileTransfer(int n, boolean bl, RcsFileTransferCreationParams rcsFileTransferCreationParams, String string2) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRcs {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IRcs";
        static final int TRANSACTION_addIncomingMessage = 12;
        static final int TRANSACTION_addOutgoingMessage = 13;
        static final int TRANSACTION_addParticipantToGroupThread = 27;
        static final int TRANSACTION_createGroupThread = 11;
        static final int TRANSACTION_createGroupThreadIconChangedEvent = 87;
        static final int TRANSACTION_createGroupThreadNameChangedEvent = 86;
        static final int TRANSACTION_createGroupThreadParticipantJoinedEvent = 88;
        static final int TRANSACTION_createGroupThreadParticipantLeftEvent = 89;
        static final int TRANSACTION_createParticipantAliasChangedEvent = 90;
        static final int TRANSACTION_createRcs1To1Thread = 10;
        static final int TRANSACTION_createRcsParticipant = 29;
        static final int TRANSACTION_deleteFileTransfer = 63;
        static final int TRANSACTION_deleteMessage = 14;
        static final int TRANSACTION_deleteThread = 9;
        static final int TRANSACTION_get1To1ThreadFallbackThreadId = 17;
        static final int TRANSACTION_get1To1ThreadOtherParticipantId = 18;
        static final int TRANSACTION_getEvents = 7;
        static final int TRANSACTION_getEventsWithToken = 8;
        static final int TRANSACTION_getFileTransferContentType = 69;
        static final int TRANSACTION_getFileTransferContentUri = 67;
        static final int TRANSACTION_getFileTransferFileSize = 71;
        static final int TRANSACTION_getFileTransferHeight = 79;
        static final int TRANSACTION_getFileTransferLength = 81;
        static final int TRANSACTION_getFileTransferPreviewType = 85;
        static final int TRANSACTION_getFileTransferPreviewUri = 83;
        static final int TRANSACTION_getFileTransferSessionId = 65;
        static final int TRANSACTION_getFileTransferStatus = 75;
        static final int TRANSACTION_getFileTransferTransferOffset = 73;
        static final int TRANSACTION_getFileTransferWidth = 77;
        static final int TRANSACTION_getFileTransfersAttachedToMessage = 53;
        static final int TRANSACTION_getGlobalMessageIdForMessage = 42;
        static final int TRANSACTION_getGroupThreadConferenceUri = 26;
        static final int TRANSACTION_getGroupThreadIcon = 22;
        static final int TRANSACTION_getGroupThreadName = 20;
        static final int TRANSACTION_getGroupThreadOwner = 24;
        static final int TRANSACTION_getLatitudeForMessage = 50;
        static final int TRANSACTION_getLongitudeForMessage = 52;
        static final int TRANSACTION_getMessageArrivalTimestamp = 44;
        static final int TRANSACTION_getMessageOriginationTimestamp = 40;
        static final int TRANSACTION_getMessageRecipients = 55;
        static final int TRANSACTION_getMessageSeenTimestamp = 46;
        static final int TRANSACTION_getMessageSnippet = 15;
        static final int TRANSACTION_getMessageStatus = 38;
        static final int TRANSACTION_getMessageSubId = 36;
        static final int TRANSACTION_getMessages = 5;
        static final int TRANSACTION_getMessagesWithToken = 6;
        static final int TRANSACTION_getOutgoingDeliveryDeliveredTimestamp = 56;
        static final int TRANSACTION_getOutgoingDeliverySeenTimestamp = 58;
        static final int TRANSACTION_getOutgoingDeliveryStatus = 60;
        static final int TRANSACTION_getParticipants = 3;
        static final int TRANSACTION_getParticipantsWithToken = 4;
        static final int TRANSACTION_getRcsParticipantAlias = 31;
        static final int TRANSACTION_getRcsParticipantCanonicalAddress = 30;
        static final int TRANSACTION_getRcsParticipantContactId = 33;
        static final int TRANSACTION_getRcsThreads = 1;
        static final int TRANSACTION_getRcsThreadsWithToken = 2;
        static final int TRANSACTION_getSenderParticipant = 54;
        static final int TRANSACTION_getTextForMessage = 48;
        static final int TRANSACTION_removeParticipantFromGroupThread = 28;
        static final int TRANSACTION_set1To1ThreadFallbackThreadId = 16;
        static final int TRANSACTION_setFileTransferContentType = 68;
        static final int TRANSACTION_setFileTransferContentUri = 66;
        static final int TRANSACTION_setFileTransferFileSize = 70;
        static final int TRANSACTION_setFileTransferHeight = 78;
        static final int TRANSACTION_setFileTransferLength = 80;
        static final int TRANSACTION_setFileTransferPreviewType = 84;
        static final int TRANSACTION_setFileTransferPreviewUri = 82;
        static final int TRANSACTION_setFileTransferSessionId = 64;
        static final int TRANSACTION_setFileTransferStatus = 74;
        static final int TRANSACTION_setFileTransferTransferOffset = 72;
        static final int TRANSACTION_setFileTransferWidth = 76;
        static final int TRANSACTION_setGlobalMessageIdForMessage = 41;
        static final int TRANSACTION_setGroupThreadConferenceUri = 25;
        static final int TRANSACTION_setGroupThreadIcon = 21;
        static final int TRANSACTION_setGroupThreadName = 19;
        static final int TRANSACTION_setGroupThreadOwner = 23;
        static final int TRANSACTION_setLatitudeForMessage = 49;
        static final int TRANSACTION_setLongitudeForMessage = 51;
        static final int TRANSACTION_setMessageArrivalTimestamp = 43;
        static final int TRANSACTION_setMessageOriginationTimestamp = 39;
        static final int TRANSACTION_setMessageSeenTimestamp = 45;
        static final int TRANSACTION_setMessageStatus = 37;
        static final int TRANSACTION_setMessageSubId = 35;
        static final int TRANSACTION_setOutgoingDeliveryDeliveredTimestamp = 57;
        static final int TRANSACTION_setOutgoingDeliverySeenTimestamp = 59;
        static final int TRANSACTION_setOutgoingDeliveryStatus = 61;
        static final int TRANSACTION_setRcsParticipantAlias = 32;
        static final int TRANSACTION_setRcsParticipantContactId = 34;
        static final int TRANSACTION_setTextForMessage = 47;
        static final int TRANSACTION_storeFileTransfer = 62;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRcs asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRcs) {
                return (IRcs)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRcs getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 90: {
                    return "createParticipantAliasChangedEvent";
                }
                case 89: {
                    return "createGroupThreadParticipantLeftEvent";
                }
                case 88: {
                    return "createGroupThreadParticipantJoinedEvent";
                }
                case 87: {
                    return "createGroupThreadIconChangedEvent";
                }
                case 86: {
                    return "createGroupThreadNameChangedEvent";
                }
                case 85: {
                    return "getFileTransferPreviewType";
                }
                case 84: {
                    return "setFileTransferPreviewType";
                }
                case 83: {
                    return "getFileTransferPreviewUri";
                }
                case 82: {
                    return "setFileTransferPreviewUri";
                }
                case 81: {
                    return "getFileTransferLength";
                }
                case 80: {
                    return "setFileTransferLength";
                }
                case 79: {
                    return "getFileTransferHeight";
                }
                case 78: {
                    return "setFileTransferHeight";
                }
                case 77: {
                    return "getFileTransferWidth";
                }
                case 76: {
                    return "setFileTransferWidth";
                }
                case 75: {
                    return "getFileTransferStatus";
                }
                case 74: {
                    return "setFileTransferStatus";
                }
                case 73: {
                    return "getFileTransferTransferOffset";
                }
                case 72: {
                    return "setFileTransferTransferOffset";
                }
                case 71: {
                    return "getFileTransferFileSize";
                }
                case 70: {
                    return "setFileTransferFileSize";
                }
                case 69: {
                    return "getFileTransferContentType";
                }
                case 68: {
                    return "setFileTransferContentType";
                }
                case 67: {
                    return "getFileTransferContentUri";
                }
                case 66: {
                    return "setFileTransferContentUri";
                }
                case 65: {
                    return "getFileTransferSessionId";
                }
                case 64: {
                    return "setFileTransferSessionId";
                }
                case 63: {
                    return "deleteFileTransfer";
                }
                case 62: {
                    return "storeFileTransfer";
                }
                case 61: {
                    return "setOutgoingDeliveryStatus";
                }
                case 60: {
                    return "getOutgoingDeliveryStatus";
                }
                case 59: {
                    return "setOutgoingDeliverySeenTimestamp";
                }
                case 58: {
                    return "getOutgoingDeliverySeenTimestamp";
                }
                case 57: {
                    return "setOutgoingDeliveryDeliveredTimestamp";
                }
                case 56: {
                    return "getOutgoingDeliveryDeliveredTimestamp";
                }
                case 55: {
                    return "getMessageRecipients";
                }
                case 54: {
                    return "getSenderParticipant";
                }
                case 53: {
                    return "getFileTransfersAttachedToMessage";
                }
                case 52: {
                    return "getLongitudeForMessage";
                }
                case 51: {
                    return "setLongitudeForMessage";
                }
                case 50: {
                    return "getLatitudeForMessage";
                }
                case 49: {
                    return "setLatitudeForMessage";
                }
                case 48: {
                    return "getTextForMessage";
                }
                case 47: {
                    return "setTextForMessage";
                }
                case 46: {
                    return "getMessageSeenTimestamp";
                }
                case 45: {
                    return "setMessageSeenTimestamp";
                }
                case 44: {
                    return "getMessageArrivalTimestamp";
                }
                case 43: {
                    return "setMessageArrivalTimestamp";
                }
                case 42: {
                    return "getGlobalMessageIdForMessage";
                }
                case 41: {
                    return "setGlobalMessageIdForMessage";
                }
                case 40: {
                    return "getMessageOriginationTimestamp";
                }
                case 39: {
                    return "setMessageOriginationTimestamp";
                }
                case 38: {
                    return "getMessageStatus";
                }
                case 37: {
                    return "setMessageStatus";
                }
                case 36: {
                    return "getMessageSubId";
                }
                case 35: {
                    return "setMessageSubId";
                }
                case 34: {
                    return "setRcsParticipantContactId";
                }
                case 33: {
                    return "getRcsParticipantContactId";
                }
                case 32: {
                    return "setRcsParticipantAlias";
                }
                case 31: {
                    return "getRcsParticipantAlias";
                }
                case 30: {
                    return "getRcsParticipantCanonicalAddress";
                }
                case 29: {
                    return "createRcsParticipant";
                }
                case 28: {
                    return "removeParticipantFromGroupThread";
                }
                case 27: {
                    return "addParticipantToGroupThread";
                }
                case 26: {
                    return "getGroupThreadConferenceUri";
                }
                case 25: {
                    return "setGroupThreadConferenceUri";
                }
                case 24: {
                    return "getGroupThreadOwner";
                }
                case 23: {
                    return "setGroupThreadOwner";
                }
                case 22: {
                    return "getGroupThreadIcon";
                }
                case 21: {
                    return "setGroupThreadIcon";
                }
                case 20: {
                    return "getGroupThreadName";
                }
                case 19: {
                    return "setGroupThreadName";
                }
                case 18: {
                    return "get1To1ThreadOtherParticipantId";
                }
                case 17: {
                    return "get1To1ThreadFallbackThreadId";
                }
                case 16: {
                    return "set1To1ThreadFallbackThreadId";
                }
                case 15: {
                    return "getMessageSnippet";
                }
                case 14: {
                    return "deleteMessage";
                }
                case 13: {
                    return "addOutgoingMessage";
                }
                case 12: {
                    return "addIncomingMessage";
                }
                case 11: {
                    return "createGroupThread";
                }
                case 10: {
                    return "createRcs1To1Thread";
                }
                case 9: {
                    return "deleteThread";
                }
                case 8: {
                    return "getEventsWithToken";
                }
                case 7: {
                    return "getEvents";
                }
                case 6: {
                    return "getMessagesWithToken";
                }
                case 5: {
                    return "getMessages";
                }
                case 4: {
                    return "getParticipantsWithToken";
                }
                case 3: {
                    return "getParticipants";
                }
                case 2: {
                    return "getRcsThreadsWithToken";
                }
                case 1: 
            }
            return "getRcsThreads";
        }

        public static boolean setDefaultImpl(IRcs iRcs) {
            if (Proxy.sDefaultImpl == null && iRcs != null) {
                Proxy.sDefaultImpl = iRcs;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                boolean bl7 = false;
                boolean bl8 = false;
                boolean bl9 = false;
                boolean bl10 = false;
                boolean bl11 = false;
                boolean bl12 = false;
                boolean bl13 = false;
                boolean bl14 = false;
                boolean bl15 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 90: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.createParticipantAliasChangedEvent(((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.createGroupThreadParticipantLeftEvent(((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.createGroupThreadParticipantJoinedEvent(((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = ((Parcel)object).readLong();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.createGroupThreadIconChangedEvent(l, n2, n, uri, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.createGroupThreadNameChangedEvent(((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getFileTransferPreviewType(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFileTransferPreviewType(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getFileTransferPreviewUri(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setFileTransferPreviewUri(n, uri, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getFileTransferLength(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFileTransferLength(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getFileTransferHeight(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFileTransferHeight(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getFileTransferWidth(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFileTransferWidth(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getFileTransferStatus(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFileTransferStatus(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getFileTransferTransferOffset(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFileTransferTransferOffset(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getFileTransferFileSize(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFileTransferFileSize(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getFileTransferContentType(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFileTransferContentType(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getFileTransferContentUri(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setFileTransferContentUri(n, uri, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getFileTransferSessionId(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFileTransferSessionId(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteFileTransfer(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        RcsFileTransferCreationParams rcsFileTransferCreationParams = ((Parcel)object).readInt() != 0 ? RcsFileTransferCreationParams.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.storeFileTransfer(n, bl15, rcsFileTransferCreationParams, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setOutgoingDeliveryStatus(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getOutgoingDeliveryStatus(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setOutgoingDeliverySeenTimestamp(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getOutgoingDeliverySeenTimestamp(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setOutgoingDeliveryDeliveredTimestamp(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getOutgoingDeliveryDeliveredTimestamp(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMessageRecipients(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getSenderParticipant(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        object = this.getFileTransfersAttachedToMessage(n, bl15, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        double d = this.getLongitudeForMessage(n, bl15, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeDouble(d);
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = ((Parcel)object).readInt() != 0;
                        this.setLongitudeForMessage(n, bl15, ((Parcel)object).readDouble(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        double d = this.getLatitudeForMessage(n, bl15, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeDouble(d);
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = ((Parcel)object).readInt() != 0;
                        this.setLatitudeForMessage(n, bl15, ((Parcel)object).readDouble(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        object = this.getTextForMessage(n, bl15, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        this.setTextForMessage(n, bl15, ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        long l = this.getMessageSeenTimestamp(n, bl15, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = ((Parcel)object).readInt() != 0;
                        this.setMessageSeenTimestamp(n, bl15, ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        long l = this.getMessageArrivalTimestamp(n, bl15, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = ((Parcel)object).readInt() != 0;
                        this.setMessageArrivalTimestamp(n, bl15, ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        object = this.getGlobalMessageIdForMessage(n, bl15, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl9;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        this.setGlobalMessageIdForMessage(n, bl15, ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl10;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        long l = this.getMessageOriginationTimestamp(n, bl15, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = ((Parcel)object).readInt() != 0;
                        this.setMessageOriginationTimestamp(n, bl15, ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl11;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        n = this.getMessageStatus(n, bl15, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl12;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        this.setMessageStatus(n, bl15, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl13;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        n = this.getMessageSubId(n, bl15, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = bl14;
                        if (((Parcel)object).readInt() != 0) {
                            bl15 = true;
                        }
                        this.setMessageSubId(n, bl15, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRcsParticipantContactId(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRcsParticipantContactId(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRcsParticipantAlias(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRcsParticipantAlias(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRcsParticipantCanonicalAddress(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.createRcsParticipant(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeParticipantFromGroupThread(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addParticipantToGroupThread(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGroupThreadConferenceUri(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setGroupThreadConferenceUri(n, uri, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getGroupThreadOwner(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setGroupThreadOwner(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGroupThreadIcon(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setGroupThreadIcon(n, uri, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGroupThreadName(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setGroupThreadName(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.get1To1ThreadOtherParticipantId(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.get1To1ThreadFallbackThreadId(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.set1To1ThreadFallbackThreadId(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMessageSnippet(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RcsMessageSnippet)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl15 = ((Parcel)object).readInt() != 0;
                        n2 = ((Parcel)object).readInt();
                        bl = ((Parcel)object).readInt() != 0;
                        this.deleteMessage(n, bl15, n2, bl, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams = ((Parcel)object).readInt() != 0 ? RcsOutgoingMessageCreationParams.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addOutgoingMessage(n, rcsOutgoingMessageCreationParams, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams = ((Parcel)object).readInt() != 0 ? RcsIncomingMessageCreationParams.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addIncomingMessage(n, rcsIncomingMessageCreationParams, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        int[] arrn = ((Parcel)object).createIntArray();
                        String string2 = ((Parcel)object).readString();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.createGroupThread(arrn, string2, uri, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.createRcs1To1Thread(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.deleteThread(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        RcsQueryContinuationToken rcsQueryContinuationToken = ((Parcel)object).readInt() != 0 ? RcsQueryContinuationToken.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getEventsWithToken(rcsQueryContinuationToken, ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RcsEventQueryResultDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        RcsEventQueryParams rcsEventQueryParams = ((Parcel)object).readInt() != 0 ? RcsEventQueryParams.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getEvents(rcsEventQueryParams, ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RcsEventQueryResultDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        RcsQueryContinuationToken rcsQueryContinuationToken = ((Parcel)object).readInt() != 0 ? RcsQueryContinuationToken.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getMessagesWithToken(rcsQueryContinuationToken, ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RcsMessageQueryResultParcelable)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        RcsMessageQueryParams rcsMessageQueryParams = ((Parcel)object).readInt() != 0 ? RcsMessageQueryParams.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getMessages(rcsMessageQueryParams, ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RcsMessageQueryResultParcelable)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        RcsQueryContinuationToken rcsQueryContinuationToken = ((Parcel)object).readInt() != 0 ? RcsQueryContinuationToken.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getParticipantsWithToken(rcsQueryContinuationToken, ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RcsParticipantQueryResultParcelable)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        RcsParticipantQueryParams rcsParticipantQueryParams = ((Parcel)object).readInt() != 0 ? RcsParticipantQueryParams.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getParticipants(rcsParticipantQueryParams, ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RcsParticipantQueryResultParcelable)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        RcsQueryContinuationToken rcsQueryContinuationToken = ((Parcel)object).readInt() != 0 ? RcsQueryContinuationToken.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getRcsThreadsWithToken(rcsQueryContinuationToken, ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RcsThreadQueryResultParcelable)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                RcsThreadQueryParams rcsThreadQueryParams = ((Parcel)object).readInt() != 0 ? RcsThreadQueryParams.CREATOR.createFromParcel((Parcel)object) : null;
                object = this.getRcsThreads(rcsThreadQueryParams, ((Parcel)object).readString());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((RcsThreadQueryResultParcelable)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IRcs {
            public static IRcs sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public int addIncomingMessage(int n, RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (rcsIncomingMessageCreationParams != null) {
                        parcel.writeInt(1);
                        rcsIncomingMessageCreationParams.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().addIncomingMessage(n, rcsIncomingMessageCreationParams, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int addOutgoingMessage(int n, RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (rcsOutgoingMessageCreationParams != null) {
                        parcel.writeInt(1);
                        rcsOutgoingMessageCreationParams.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().addOutgoingMessage(n, rcsOutgoingMessageCreationParams, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void addParticipantToGroupThread(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addParticipantToGroupThread(n, n2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public int createGroupThread(int[] arrn, String string2, Uri uri, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    parcel.writeString(string2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().createGroupThread(arrn, string2, uri, string3);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int createGroupThreadIconChangedEvent(long l, int n, int n2, Uri uri, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var5_10;
                block14 : {
                    block13 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeLong(l);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel2.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel2.writeInt(n2);
                            if (uri != null) {
                                parcel2.writeInt(1);
                                uri.writeToParcel(parcel2, 0);
                                break block13;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeString(string2);
                        if (!this.mRemote.transact(87, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().createGroupThreadIconChangedEvent(l, n, n2, uri, string2);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var5_10;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int createGroupThreadNameChangedEvent(long l, int n, int n2, String string2, String string3) throws RemoteException {
                Parcel parcel;
                void var5_11;
                Parcel parcel2;
                block14 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeString(string3);
                        if (!this.mRemote.transact(86, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().createGroupThreadNameChangedEvent(l, n, n2, string2, string3);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var5_11;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int createGroupThreadParticipantJoinedEvent(long l, int n, int n2, int n3, String string2) throws RemoteException {
                Parcel parcel;
                void var6_12;
                Parcel parcel2;
                block14 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeString(string2);
                        if (!this.mRemote.transact(88, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().createGroupThreadParticipantJoinedEvent(l, n, n2, n3, string2);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var6_12;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int createGroupThreadParticipantLeftEvent(long l, int n, int n2, int n3, String string2) throws RemoteException {
                Parcel parcel;
                void var6_12;
                Parcel parcel2;
                block14 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeString(string2);
                        if (!this.mRemote.transact(89, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().createGroupThreadParticipantLeftEvent(l, n, n2, n3, string2);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var6_12;
            }

            @Override
            public int createParticipantAliasChangedEvent(long l, int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(90, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().createParticipantAliasChangedEvent(l, n, string2, string3);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int createRcs1To1Thread(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().createRcs1To1Thread(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int createRcsParticipant(String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().createRcsParticipant(string2, string3, string4);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void deleteFileTransfer(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteFileTransfer(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void deleteMessage(int n, boolean bl, int n2, boolean bl2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n3 = 1;
                int n4 = bl ? 1 : 0;
                parcel.writeInt(n4);
                parcel.writeInt(n2);
                n4 = bl2 ? n3 : 0;
                try {
                    parcel.writeInt(n4);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteMessage(n, bl, n2, bl2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean deleteThread(int n, int n2, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(9, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().deleteThread(n, n2, string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public long get1To1ThreadFallbackThreadId(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().get1To1ThreadFallbackThreadId(n, string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int get1To1ThreadOtherParticipantId(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().get1To1ThreadOtherParticipantId(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public RcsEventQueryResultDescriptor getEvents(RcsEventQueryParams parcelable, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((RcsEventQueryParams)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        RcsEventQueryResultDescriptor rcsEventQueryResultDescriptor = Stub.getDefaultImpl().getEvents((RcsEventQueryParams)parcelable, (String)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return rcsEventQueryResultDescriptor;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        RcsEventQueryResultDescriptor rcsEventQueryResultDescriptor = RcsEventQueryResultDescriptor.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public RcsEventQueryResultDescriptor getEventsWithToken(RcsQueryContinuationToken parcelable, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((RcsQueryContinuationToken)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        RcsEventQueryResultDescriptor rcsEventQueryResultDescriptor = Stub.getDefaultImpl().getEventsWithToken((RcsQueryContinuationToken)parcelable, (String)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return rcsEventQueryResultDescriptor;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        RcsEventQueryResultDescriptor rcsEventQueryResultDescriptor = RcsEventQueryResultDescriptor.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public String getFileTransferContentType(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getFileTransferContentType(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Uri getFileTransferContentUri(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(67, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getFileTransferContentUri(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public long getFileTransferFileSize(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getFileTransferFileSize(n, string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getFileTransferHeight(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(79, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getFileTransferHeight(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getFileTransferLength(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(81, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getFileTransferLength(n, string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getFileTransferPreviewType(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(85, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getFileTransferPreviewType(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Uri getFileTransferPreviewUri(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(83, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getFileTransferPreviewUri(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public String getFileTransferSessionId(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getFileTransferSessionId(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getFileTransferStatus(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(75, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getFileTransferStatus(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getFileTransferTransferOffset(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getFileTransferTransferOffset(n, string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getFileTransferWidth(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(77, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getFileTransferWidth(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getFileTransfersAttachedToMessage(int n, boolean bl, String arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString((String)arrn);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrn = Stub.getDefaultImpl().getFileTransfersAttachedToMessage(n, bl, (String)arrn);
                        return arrn;
                    }
                    parcel2.readException();
                    arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getGlobalMessageIdForMessage(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getGlobalMessageIdForMessage(n, bl, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Uri getGroupThreadConferenceUri(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(26, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getGroupThreadConferenceUri(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public Uri getGroupThreadIcon(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(22, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getGroupThreadIcon(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public String getGroupThreadName(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getGroupThreadName(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getGroupThreadOwner(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getGroupThreadOwner(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public double getLatitudeForMessage(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        double d = Stub.getDefaultImpl().getLatitudeForMessage(n, bl, string2);
                        return d;
                    }
                    parcel2.readException();
                    double d = parcel2.readDouble();
                    return d;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public double getLongitudeForMessage(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        double d = Stub.getDefaultImpl().getLongitudeForMessage(n, bl, string2);
                        return d;
                    }
                    parcel2.readException();
                    double d = parcel2.readDouble();
                    return d;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getMessageArrivalTimestamp(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getMessageArrivalTimestamp(n, bl, string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getMessageOriginationTimestamp(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getMessageOriginationTimestamp(n, bl, string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getMessageRecipients(int n, String arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)arrn);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrn = Stub.getDefaultImpl().getMessageRecipients(n, (String)arrn);
                        return arrn;
                    }
                    parcel2.readException();
                    arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getMessageSeenTimestamp(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getMessageSeenTimestamp(n, bl, string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public RcsMessageSnippet getMessageSnippet(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(15, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getMessageSnippet(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? RcsMessageSnippet.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public int getMessageStatus(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getMessageStatus(n, bl, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getMessageSubId(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getMessageSubId(n, bl, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public RcsMessageQueryResultParcelable getMessages(RcsMessageQueryParams parcelable, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((RcsMessageQueryParams)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        RcsMessageQueryResultParcelable rcsMessageQueryResultParcelable = Stub.getDefaultImpl().getMessages((RcsMessageQueryParams)parcelable, (String)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return rcsMessageQueryResultParcelable;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        RcsMessageQueryResultParcelable rcsMessageQueryResultParcelable = RcsMessageQueryResultParcelable.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public RcsMessageQueryResultParcelable getMessagesWithToken(RcsQueryContinuationToken parcelable, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((RcsQueryContinuationToken)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        RcsMessageQueryResultParcelable rcsMessageQueryResultParcelable = Stub.getDefaultImpl().getMessagesWithToken((RcsQueryContinuationToken)parcelable, (String)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return rcsMessageQueryResultParcelable;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        RcsMessageQueryResultParcelable rcsMessageQueryResultParcelable = RcsMessageQueryResultParcelable.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public long getOutgoingDeliveryDeliveredTimestamp(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getOutgoingDeliveryDeliveredTimestamp(n, n2, string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getOutgoingDeliverySeenTimestamp(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getOutgoingDeliverySeenTimestamp(n, n2, string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getOutgoingDeliveryStatus(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getOutgoingDeliveryStatus(n, n2, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public RcsParticipantQueryResultParcelable getParticipants(RcsParticipantQueryParams parcelable, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((RcsParticipantQueryParams)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        RcsParticipantQueryResultParcelable rcsParticipantQueryResultParcelable = Stub.getDefaultImpl().getParticipants((RcsParticipantQueryParams)parcelable, (String)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return rcsParticipantQueryResultParcelable;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        RcsParticipantQueryResultParcelable rcsParticipantQueryResultParcelable = RcsParticipantQueryResultParcelable.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public RcsParticipantQueryResultParcelable getParticipantsWithToken(RcsQueryContinuationToken parcelable, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((RcsQueryContinuationToken)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        RcsParticipantQueryResultParcelable rcsParticipantQueryResultParcelable = Stub.getDefaultImpl().getParticipantsWithToken((RcsQueryContinuationToken)parcelable, (String)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return rcsParticipantQueryResultParcelable;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        RcsParticipantQueryResultParcelable rcsParticipantQueryResultParcelable = RcsParticipantQueryResultParcelable.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public String getRcsParticipantAlias(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getRcsParticipantAlias(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getRcsParticipantCanonicalAddress(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getRcsParticipantCanonicalAddress(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getRcsParticipantContactId(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getRcsParticipantContactId(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public RcsThreadQueryResultParcelable getRcsThreads(RcsThreadQueryParams parcelable, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((RcsThreadQueryParams)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        RcsThreadQueryResultParcelable rcsThreadQueryResultParcelable = Stub.getDefaultImpl().getRcsThreads((RcsThreadQueryParams)parcelable, (String)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return rcsThreadQueryResultParcelable;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        RcsThreadQueryResultParcelable rcsThreadQueryResultParcelable = RcsThreadQueryResultParcelable.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public RcsThreadQueryResultParcelable getRcsThreadsWithToken(RcsQueryContinuationToken parcelable, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((RcsQueryContinuationToken)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        RcsThreadQueryResultParcelable rcsThreadQueryResultParcelable = Stub.getDefaultImpl().getRcsThreadsWithToken((RcsQueryContinuationToken)parcelable, (String)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return rcsThreadQueryResultParcelable;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        RcsThreadQueryResultParcelable rcsThreadQueryResultParcelable = RcsThreadQueryResultParcelable.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public int getSenderParticipant(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getSenderParticipant(n, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getTextForMessage(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getTextForMessage(n, bl, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void removeParticipantFromGroupThread(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeParticipantFromGroupThread(n, n2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void set1To1ThreadFallbackThreadId(int n, long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().set1To1ThreadFallbackThreadId(n, l, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferContentType(int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferContentType(n, string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferContentUri(int n, Uri uri, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferContentUri(n, uri, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferFileSize(int n, long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(70, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferFileSize(n, l, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferHeight(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferHeight(n, n2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferLength(int n, long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(80, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferLength(n, l, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferPreviewType(int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(84, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferPreviewType(n, string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferPreviewUri(int n, Uri uri, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(82, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferPreviewUri(n, uri, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferSessionId(int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferSessionId(n, string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferStatus(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(74, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferStatus(n, n2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferTransferOffset(int n, long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferTransferOffset(n, l, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setFileTransferWidth(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(76, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFileTransferWidth(n, n2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setGlobalMessageIdForMessage(int n, boolean bl, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGlobalMessageIdForMessage(n, bl, string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setGroupThreadConferenceUri(int n, Uri uri, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGroupThreadConferenceUri(n, uri, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setGroupThreadIcon(int n, Uri uri, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGroupThreadIcon(n, uri, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setGroupThreadName(int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGroupThreadName(n, string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setGroupThreadOwner(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGroupThreadOwner(n, n2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setLatitudeForMessage(int n, boolean bl, double d, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeDouble(d);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLatitudeForMessage(n, bl, d, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setLongitudeForMessage(int n, boolean bl, double d, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeDouble(d);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLongitudeForMessage(n, bl, d, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setMessageArrivalTimestamp(int n, boolean bl, long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMessageArrivalTimestamp(n, bl, l, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setMessageOriginationTimestamp(int n, boolean bl, long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMessageOriginationTimestamp(n, bl, l, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setMessageSeenTimestamp(int n, boolean bl, long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMessageSeenTimestamp(n, bl, l, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setMessageStatus(int n, boolean bl, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMessageStatus(n, bl, n2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setMessageSubId(int n, boolean bl, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMessageSubId(n, bl, n2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setOutgoingDeliveryDeliveredTimestamp(int n, int n2, long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOutgoingDeliveryDeliveredTimestamp(n, n2, l, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setOutgoingDeliverySeenTimestamp(int n, int n2, long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOutgoingDeliverySeenTimestamp(n, n2, l, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setOutgoingDeliveryStatus(int n, int n2, int n3, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOutgoingDeliveryStatus(n, n2, n3, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setRcsParticipantAlias(int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRcsParticipantAlias(n, string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setRcsParticipantContactId(int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRcsParticipantContactId(n, string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setTextForMessage(int n, boolean bl, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTextForMessage(n, bl, string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int storeFileTransfer(int n, boolean bl, RcsFileTransferCreationParams rcsFileTransferCreationParams, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (rcsFileTransferCreationParams != null) {
                        parcel.writeInt(1);
                        rcsFileTransferCreationParams.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(62, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().storeFileTransfer(n, bl, rcsFileTransferCreationParams, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

