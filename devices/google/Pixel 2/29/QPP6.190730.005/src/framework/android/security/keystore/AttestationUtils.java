/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.security.KeyStore;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.DeviceIdAttestationException;
import android.security.keystore.KeyAttestationException;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArraySet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SystemApi
public abstract class AttestationUtils {
    public static final int ID_TYPE_IMEI = 2;
    public static final int ID_TYPE_MEID = 3;
    public static final int ID_TYPE_SERIAL = 1;

    private AttestationUtils() {
    }

    public static X509Certificate[] attestDeviceIds(Context arrx509Certificate, int[] object, byte[] arrby) throws DeviceIdAttestationException {
        arrx509Certificate = AttestationUtils.prepareAttestationArgumentsForDeviceId((Context)arrx509Certificate, object, arrby);
        object = new KeymasterCertificateChain();
        int n = KeyStore.getInstance().attestDeviceIds((KeymasterArguments)arrx509Certificate, (KeymasterCertificateChain)object);
        if (n == 1) {
            try {
                arrx509Certificate = AttestationUtils.parseCertificateChain((KeymasterCertificateChain)object);
                return arrx509Certificate;
            }
            catch (KeyAttestationException keyAttestationException) {
                throw new DeviceIdAttestationException(keyAttestationException.getMessage(), keyAttestationException);
            }
        }
        throw new DeviceIdAttestationException("Unable to perform attestation", KeyStore.getKeyStoreException(n));
    }

    public static boolean isChainValid(KeymasterCertificateChain keymasterCertificateChain) {
        boolean bl = keymasterCertificateChain != null && keymasterCertificateChain.getCertificates().size() >= 2;
        return bl;
    }

    private static boolean isPotentiallyMisprovisionedDevice(Context object) {
        object = ((Context)object).getResources().getString(17039753);
        return Build.MODEL.equals(object);
    }

    public static X509Certificate[] parseCertificateChain(KeymasterCertificateChain arrx509Certificate) throws KeyAttestationException {
        List<byte[]> list = arrx509Certificate.getCertificates();
        if (list.size() >= 2) {
            arrx509Certificate = new ByteArrayOutputStream();
            try {
                list = list.iterator();
                while (list.hasNext()) {
                    arrx509Certificate.write((byte[])list.next());
                }
                list = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrx509Certificate.toByteArray());
                arrx509Certificate = ((CertificateFactory)((Object)list)).generateCertificates(byteArrayInputStream).toArray(new X509Certificate[0]);
                return arrx509Certificate;
            }
            catch (Exception exception) {
                throw new KeyAttestationException("Unable to construct certificate chain", exception);
            }
        }
        arrx509Certificate = new StringBuilder();
        arrx509Certificate.append("Attestation certificate chain contained ");
        arrx509Certificate.append(list.size());
        arrx509Certificate.append(" entries. At least two are required.");
        throw new KeyAttestationException(arrx509Certificate.toString());
    }

    public static KeymasterArguments prepareAttestationArguments(Context context, int[] arrn, byte[] arrby) throws DeviceIdAttestationException {
        return AttestationUtils.prepareAttestationArguments(context, arrn, arrby, Build.BRAND);
    }

    private static KeymasterArguments prepareAttestationArguments(Context object, int[] object2, byte[] object3, String string2) throws DeviceIdAttestationException {
        if (object3 != null) {
            int n;
            KeymasterArguments keymasterArguments = new KeymasterArguments();
            keymasterArguments.addBytes(-1879047484, (byte[])object3);
            if (object2 == null) {
                return keymasterArguments;
            }
            object3 = new ArraySet(((int[])object2).length);
            int n2 = ((int[])object2).length;
            for (n = 0; n < n2; ++n) {
                object3.add(object2[n]);
            }
            object2 = null;
            if (!object3.contains(2) && !object3.contains(3) || (object2 = (TelephonyManager)((Context)object).getSystemService("phone")) != null) {
                object = object3.iterator();
                while (object.hasNext()) {
                    object3 = (Integer)object.next();
                    n = (Integer)object3;
                    if (n != 1) {
                        if (n != 2) {
                            if (n == 3) {
                                object3 = ((TelephonyManager)object2).getMeid(0);
                                if (object3 != null) {
                                    keymasterArguments.addBytes(-1879047477, ((String)object3).getBytes(StandardCharsets.UTF_8));
                                    continue;
                                }
                                throw new DeviceIdAttestationException("Unable to retrieve MEID");
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unknown device ID type ");
                            ((StringBuilder)object).append(object3);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        object3 = ((TelephonyManager)object2).getImei(0);
                        if (object3 != null) {
                            keymasterArguments.addBytes(-1879047478, ((String)object3).getBytes(StandardCharsets.UTF_8));
                            continue;
                        }
                        throw new DeviceIdAttestationException("Unable to retrieve IMEI");
                    }
                    keymasterArguments.addBytes(-1879047479, Build.getSerial().getBytes(StandardCharsets.UTF_8));
                }
                keymasterArguments.addBytes(-1879047482, string2.getBytes(StandardCharsets.UTF_8));
                keymasterArguments.addBytes(-1879047481, Build.DEVICE.getBytes(StandardCharsets.UTF_8));
                keymasterArguments.addBytes(-1879047480, Build.PRODUCT.getBytes(StandardCharsets.UTF_8));
                keymasterArguments.addBytes(-1879047476, Build.MANUFACTURER.getBytes(StandardCharsets.UTF_8));
                keymasterArguments.addBytes(-1879047475, Build.MODEL.getBytes(StandardCharsets.UTF_8));
                return keymasterArguments;
            }
            throw new DeviceIdAttestationException("Unable to access telephony service");
        }
        throw new NullPointerException("Missing attestation challenge");
    }

    private static KeymasterArguments prepareAttestationArgumentsForDeviceId(Context context, int[] arrn, byte[] arrby) throws DeviceIdAttestationException {
        if (arrn != null) {
            return AttestationUtils.prepareAttestationArguments(context, arrn, arrby);
        }
        throw new NullPointerException("Missing id types");
    }

    public static KeymasterArguments prepareAttestationArgumentsIfMisprovisioned(Context context, int[] arrn, byte[] arrby) throws DeviceIdAttestationException {
        String string2 = context.getResources().getString(17039752);
        if (!TextUtils.isEmpty(string2) && !AttestationUtils.isPotentiallyMisprovisionedDevice(context)) {
            return null;
        }
        return AttestationUtils.prepareAttestationArguments(context, arrn, arrby, string2);
    }
}

