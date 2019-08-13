/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.ims.ImsUtListener;
import com.android.ims.internal.IImsUt;
import com.android.ims.internal.IImsUtListener;

@SystemApi
public class ImsUtImplBase {
    private IImsUt.Stub mServiceImpl = new IImsUt.Stub(){

        @Override
        public void close() throws RemoteException {
            ImsUtImplBase.this.close();
        }

        @Override
        public int queryCLIP() throws RemoteException {
            return ImsUtImplBase.this.queryCLIP();
        }

        @Override
        public int queryCLIR() throws RemoteException {
            return ImsUtImplBase.this.queryCLIR();
        }

        @Override
        public int queryCOLP() throws RemoteException {
            return ImsUtImplBase.this.queryCOLP();
        }

        @Override
        public int queryCOLR() throws RemoteException {
            return ImsUtImplBase.this.queryCOLR();
        }

        @Override
        public int queryCallBarring(int n) throws RemoteException {
            return ImsUtImplBase.this.queryCallBarring(n);
        }

        @Override
        public int queryCallBarringForServiceClass(int n, int n2) throws RemoteException {
            return ImsUtImplBase.this.queryCallBarringForServiceClass(n, n2);
        }

        @Override
        public int queryCallForward(int n, String string2) throws RemoteException {
            return ImsUtImplBase.this.queryCallForward(n, string2);
        }

        @Override
        public int queryCallWaiting() throws RemoteException {
            return ImsUtImplBase.this.queryCallWaiting();
        }

        @Override
        public void setListener(IImsUtListener iImsUtListener) throws RemoteException {
            ImsUtImplBase.this.setListener(new ImsUtListener(iImsUtListener));
        }

        @Override
        public int transact(Bundle bundle) throws RemoteException {
            return ImsUtImplBase.this.transact(bundle);
        }

        @Override
        public int updateCLIP(boolean bl) throws RemoteException {
            return ImsUtImplBase.this.updateCLIP(bl);
        }

        @Override
        public int updateCLIR(int n) throws RemoteException {
            return ImsUtImplBase.this.updateCLIR(n);
        }

        @Override
        public int updateCOLP(boolean bl) throws RemoteException {
            return ImsUtImplBase.this.updateCOLP(bl);
        }

        @Override
        public int updateCOLR(int n) throws RemoteException {
            return ImsUtImplBase.this.updateCOLR(n);
        }

        @Override
        public int updateCallBarring(int n, int n2, String[] arrstring) throws RemoteException {
            return ImsUtImplBase.this.updateCallBarring(n, n2, arrstring);
        }

        @Override
        public int updateCallBarringForServiceClass(int n, int n2, String[] arrstring, int n3) throws RemoteException {
            return ImsUtImplBase.this.updateCallBarringForServiceClass(n, n2, arrstring, n3);
        }

        @Override
        public int updateCallForward(int n, int n2, String string2, int n3, int n4) throws RemoteException {
            return ImsUtImplBase.this.updateCallForward(n, n2, string2, n3, n4);
        }

        @Override
        public int updateCallWaiting(boolean bl, int n) throws RemoteException {
            return ImsUtImplBase.this.updateCallWaiting(bl, n);
        }
    };

    public void close() {
    }

    public IImsUt getInterface() {
        return this.mServiceImpl;
    }

    public int queryCLIP() {
        return this.queryClip();
    }

    public int queryCLIR() {
        return this.queryClir();
    }

    public int queryCOLP() {
        return this.queryColp();
    }

    public int queryCOLR() {
        return this.queryColr();
    }

    public int queryCallBarring(int n) {
        return -1;
    }

    public int queryCallBarringForServiceClass(int n, int n2) {
        return -1;
    }

    public int queryCallForward(int n, String string2) {
        return -1;
    }

    public int queryCallWaiting() {
        return -1;
    }

    public int queryClip() {
        return -1;
    }

    public int queryClir() {
        return -1;
    }

    public int queryColp() {
        return -1;
    }

    public int queryColr() {
        return -1;
    }

    public void setListener(ImsUtListener imsUtListener) {
    }

    public int transact(Bundle bundle) {
        return -1;
    }

    public int updateCLIP(boolean bl) {
        return this.updateClip(bl);
    }

    public int updateCLIR(int n) {
        return this.updateClir(n);
    }

    public int updateCOLP(boolean bl) {
        return this.updateColp(bl);
    }

    public int updateCOLR(int n) {
        return this.updateColr(n);
    }

    public int updateCallBarring(int n, int n2, String[] arrstring) {
        return -1;
    }

    public int updateCallBarringForServiceClass(int n, int n2, String[] arrstring, int n3) {
        return -1;
    }

    public int updateCallForward(int n, int n2, String string2, int n3, int n4) {
        return 0;
    }

    public int updateCallWaiting(boolean bl, int n) {
        return -1;
    }

    public int updateClip(boolean bl) {
        return -1;
    }

    public int updateClir(int n) {
        return -1;
    }

    public int updateColp(boolean bl) {
        return -1;
    }

    public int updateColr(int n) {
        return -1;
    }

}

