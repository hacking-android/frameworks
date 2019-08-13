/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.ProviderException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Iterator;
import javax.crypto.JceSecurity;
import javax.crypto.MacSpi;
import javax.crypto.ShortBufferException;
import sun.security.jca.GetInstance;
import sun.security.jca.Providers;

public class Mac
implements Cloneable {
    private static int warnCount = 10;
    private final String algorithm;
    private boolean initialized = false;
    private final Object lock;
    private Provider provider;
    private MacSpi spi;

    private Mac(String string) {
        this.algorithm = string;
        this.lock = new Object();
    }

    protected Mac(MacSpi macSpi, Provider provider, String string) {
        this.spi = macSpi;
        this.provider = provider;
        this.algorithm = string;
        this.lock = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void chooseProvider(Key object, AlgorithmParameterSpec object2) throws InvalidKeyException, InvalidAlgorithmParameterException {
        Object object3 = this.lock;
        synchronized (object3) {
            Object object4;
            if (this.spi != null && (object == null || this.lock == null)) {
                this.spi.engineInit((Key)object, (AlgorithmParameterSpec)object2);
                return;
            }
            Object object5 = null;
            for (Provider.Service service : GetInstance.getServices("Mac", this.algorithm)) {
                boolean bl;
                if (!service.supportsParameter(object) || !(bl = JceSecurity.canUseProvider(service.getProvider()))) continue;
                try {
                    object4 = (MacSpi)service.newInstance(null);
                    ((MacSpi)object4).engineInit((Key)object, (AlgorithmParameterSpec)object2);
                    this.provider = service.getProvider();
                    this.spi = object4;
                    return;
                }
                catch (Exception exception) {
                    object4 = object5;
                    if (object5 == null) {
                        object4 = exception;
                    }
                    object5 = object4;
                }
            }
            if (object5 instanceof InvalidKeyException) {
                throw (InvalidKeyException)object5;
            }
            if (object5 instanceof InvalidAlgorithmParameterException) {
                throw (InvalidAlgorithmParameterException)object5;
            }
            if (object5 instanceof RuntimeException) {
                throw (RuntimeException)object5;
            }
            object = object != null ? object.getClass().getName() : "(null)";
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("No installed provider supports this key: ");
            ((StringBuilder)object2).append((String)object);
            object4 = new InvalidKeyException(((StringBuilder)object2).toString(), (Throwable)object5);
            throw object4;
        }
    }

    public static final Mac getInstance(String string) throws NoSuchAlgorithmException {
        Object object = GetInstance.getServices("Mac", string).iterator();
        while (object.hasNext()) {
            if (!JceSecurity.canUseProvider(object.next().getProvider())) continue;
            return new Mac(string);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Algorithm ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" not available");
        throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
    }

    public static final Mac getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        Providers.checkBouncyCastleDeprecation((String)object, "Mac", string);
        object = JceSecurity.getInstance("Mac", MacSpi.class, string, (String)object);
        return new Mac((MacSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static final Mac getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        Providers.checkBouncyCastleDeprecation((Provider)object, "Mac", string);
        object = JceSecurity.getInstance("Mac", MacSpi.class, string, (Provider)object);
        return new Mac((MacSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void chooseFirstProvider() {
        if (this.spi != null) return;
        Object object = this.lock;
        if (object == null) {
            return;
        }
        synchronized (object) {
            if (this.spi != null) {
                return;
            }
            Throwable throwable = null;
            Object object2 = GetInstance.getServices("Mac", this.algorithm).iterator();
            do {
                if (!object2.hasNext()) {
                    object2 = new ProviderException("Could not construct MacSpi instance");
                    if (throwable == null) throw object2;
                    ((Throwable)object2).initCause(throwable);
                    throw object2;
                }
                Provider.Service service = object2.next();
                boolean bl = JceSecurity.canUseProvider(service.getProvider());
                if (!bl) continue;
                try {
                    Object object3 = service.newInstance(null);
                    if (!(object3 instanceof MacSpi)) continue;
                    this.spi = (MacSpi)object3;
                    this.provider = service.getProvider();
                    return;
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    continue;
                }
                break;
            } while (true);
        }
    }

    public final Object clone() throws CloneNotSupportedException {
        this.chooseFirstProvider();
        Mac mac = (Mac)super.clone();
        mac.spi = (MacSpi)this.spi.clone();
        return mac;
    }

    public final void doFinal(byte[] arrby, int n) throws ShortBufferException, IllegalStateException {
        this.chooseFirstProvider();
        if (this.initialized) {
            int n2 = this.getMacLength();
            if (arrby != null && arrby.length - n >= n2) {
                System.arraycopy(this.doFinal(), 0, arrby, n, n2);
                return;
            }
            throw new ShortBufferException("Cannot store MAC in output buffer");
        }
        throw new IllegalStateException("MAC not initialized");
    }

    public final byte[] doFinal() throws IllegalStateException {
        this.chooseFirstProvider();
        if (this.initialized) {
            byte[] arrby = this.spi.engineDoFinal();
            this.spi.engineReset();
            return arrby;
        }
        throw new IllegalStateException("MAC not initialized");
    }

    public final byte[] doFinal(byte[] arrby) throws IllegalStateException {
        this.chooseFirstProvider();
        if (this.initialized) {
            this.update(arrby);
            return this.doFinal();
        }
        throw new IllegalStateException("MAC not initialized");
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public MacSpi getCurrentSpi() {
        return this.spi;
    }

    public final int getMacLength() {
        this.chooseFirstProvider();
        return this.spi.engineGetMacLength();
    }

    public final Provider getProvider() {
        this.chooseFirstProvider();
        return this.provider;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void init(Key key) throws InvalidKeyException {
        try {
            if (this.spi != null && (key == null || this.lock == null)) {
                this.spi.engineInit(key, null);
            } else {
                this.chooseProvider(key, null);
            }
            this.initialized = true;
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidKeyException("init() failed", invalidAlgorithmParameterException);
        }
    }

    public final void init(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.spi != null && (key == null || this.lock == null)) {
            this.spi.engineInit(key, algorithmParameterSpec);
        } else {
            this.chooseProvider(key, algorithmParameterSpec);
        }
        this.initialized = true;
    }

    public final void reset() {
        this.chooseFirstProvider();
        this.spi.engineReset();
    }

    public final void update(byte by) throws IllegalStateException {
        this.chooseFirstProvider();
        if (this.initialized) {
            this.spi.engineUpdate(by);
            return;
        }
        throw new IllegalStateException("MAC not initialized");
    }

    public final void update(ByteBuffer byteBuffer) {
        this.chooseFirstProvider();
        if (this.initialized) {
            if (byteBuffer != null) {
                this.spi.engineUpdate(byteBuffer);
                return;
            }
            throw new IllegalArgumentException("Buffer must not be null");
        }
        throw new IllegalStateException("MAC not initialized");
    }

    public final void update(byte[] arrby) throws IllegalStateException {
        this.chooseFirstProvider();
        if (this.initialized) {
            if (arrby != null) {
                this.spi.engineUpdate(arrby, 0, arrby.length);
            }
            return;
        }
        throw new IllegalStateException("MAC not initialized");
    }

    public final void update(byte[] arrby, int n, int n2) throws IllegalStateException {
        this.chooseFirstProvider();
        if (this.initialized) {
            if (arrby != null) {
                if (n >= 0 && n2 <= arrby.length - n && n2 >= 0) {
                    this.spi.engineUpdate(arrby, n, n2);
                } else {
                    throw new IllegalArgumentException("Bad arguments");
                }
            }
            return;
        }
        throw new IllegalStateException("MAC not initialized");
    }
}

