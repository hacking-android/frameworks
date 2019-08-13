/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

public class PSource {
    private String pSrcName;

    protected PSource(String string) {
        if (string != null) {
            this.pSrcName = string;
            return;
        }
        throw new NullPointerException("pSource algorithm is null");
    }

    public String getAlgorithm() {
        return this.pSrcName;
    }

    public static final class PSpecified
    extends PSource {
        public static final PSpecified DEFAULT = new PSpecified(new byte[0]);
        private byte[] p = new byte[0];

        public PSpecified(byte[] arrby) {
            super("PSpecified");
            this.p = (byte[])arrby.clone();
        }

        public byte[] getValue() {
            byte[] arrby = this.p;
            if (arrby.length != 0) {
                arrby = (byte[])arrby.clone();
            }
            return arrby;
        }
    }

}

