/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.mms.pdu;

import android.util.Log;
import com.google.android.mms.InvalidHeaderValueException;
import com.google.android.mms.pdu.EncodedStringValue;
import com.google.android.mms.pdu.MultimediaMessagePdu;
import com.google.android.mms.pdu.PduBody;
import com.google.android.mms.pdu.PduHeaders;

public class SendReq
extends MultimediaMessagePdu {
    private static final String TAG = "SendReq";

    public SendReq() {
        try {
            this.setMessageType(128);
            this.setMmsVersion(18);
            this.setContentType("application/vnd.wap.multipart.related".getBytes());
            EncodedStringValue encodedStringValue = new EncodedStringValue("insert-address-token".getBytes());
            this.setFrom(encodedStringValue);
            this.setTransactionId(this.generateTransactionId());
            return;
        }
        catch (InvalidHeaderValueException invalidHeaderValueException) {
            Log.e((String)TAG, (String)"Unexpected InvalidHeaderValueException.", (Throwable)invalidHeaderValueException);
            throw new RuntimeException(invalidHeaderValueException);
        }
    }

    SendReq(PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    SendReq(PduHeaders pduHeaders, PduBody pduBody) {
        super(pduHeaders, pduBody);
    }

    public SendReq(byte[] arrby, EncodedStringValue encodedStringValue, int n, byte[] arrby2) throws InvalidHeaderValueException {
        this.setMessageType(128);
        this.setContentType(arrby);
        this.setFrom(encodedStringValue);
        this.setMmsVersion(n);
        this.setTransactionId(arrby2);
    }

    private byte[] generateTransactionId() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("T");
        stringBuilder.append(Long.toHexString(System.currentTimeMillis()));
        return stringBuilder.toString().getBytes();
    }

    public void addBcc(EncodedStringValue encodedStringValue) {
        this.mPduHeaders.appendEncodedStringValue(encodedStringValue, 129);
    }

    public void addCc(EncodedStringValue encodedStringValue) {
        this.mPduHeaders.appendEncodedStringValue(encodedStringValue, 130);
    }

    public EncodedStringValue[] getBcc() {
        return this.mPduHeaders.getEncodedStringValues(129);
    }

    public EncodedStringValue[] getCc() {
        return this.mPduHeaders.getEncodedStringValues(130);
    }

    public byte[] getContentType() {
        return this.mPduHeaders.getTextString(132);
    }

    public int getDeliveryReport() {
        return this.mPduHeaders.getOctet(134);
    }

    public long getExpiry() {
        return this.mPduHeaders.getLongInteger(136);
    }

    public byte[] getMessageClass() {
        return this.mPduHeaders.getTextString(138);
    }

    public long getMessageSize() {
        return this.mPduHeaders.getLongInteger(142);
    }

    public int getReadReport() {
        return this.mPduHeaders.getOctet(144);
    }

    public byte[] getTransactionId() {
        return this.mPduHeaders.getTextString(152);
    }

    public void setBcc(EncodedStringValue[] arrencodedStringValue) {
        this.mPduHeaders.setEncodedStringValues(arrencodedStringValue, 129);
    }

    public void setCc(EncodedStringValue[] arrencodedStringValue) {
        this.mPduHeaders.setEncodedStringValues(arrencodedStringValue, 130);
    }

    public void setContentType(byte[] arrby) {
        this.mPduHeaders.setTextString(arrby, 132);
    }

    public void setDeliveryReport(int n) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(n, 134);
    }

    public void setExpiry(long l) {
        this.mPduHeaders.setLongInteger(l, 136);
    }

    public void setMessageClass(byte[] arrby) {
        this.mPduHeaders.setTextString(arrby, 138);
    }

    public void setMessageSize(long l) {
        this.mPduHeaders.setLongInteger(l, 142);
    }

    public void setReadReport(int n) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(n, 144);
    }

    public void setTo(EncodedStringValue[] arrencodedStringValue) {
        this.mPduHeaders.setEncodedStringValues(arrencodedStringValue, 151);
    }

    public void setTransactionId(byte[] arrby) {
        this.mPduHeaders.setTextString(arrby, 152);
    }
}

