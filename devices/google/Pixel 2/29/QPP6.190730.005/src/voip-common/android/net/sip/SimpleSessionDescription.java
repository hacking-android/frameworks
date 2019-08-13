/*
 * Decompiled with CFR 0.145.
 */
package android.net.sip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class SimpleSessionDescription {
    private final Fields mFields = new Fields("voscbtka");
    private final ArrayList<Media> mMedia = new ArrayList();

    public SimpleSessionDescription(long l, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        Object object = string.indexOf(58) < 0 ? "IN IP4 " : "IN IP6 ";
        stringBuilder.append((String)object);
        stringBuilder.append(string);
        string = stringBuilder.toString();
        this.mFields.parse("v=0");
        this.mFields.parse(String.format(Locale.US, "o=- %d %d %s", l, System.currentTimeMillis(), string));
        this.mFields.parse("s=-");
        this.mFields.parse("t=0 0");
        object = this.mFields;
        stringBuilder = new StringBuilder();
        stringBuilder.append("c=");
        stringBuilder.append(string);
        ((Fields)object).parse(stringBuilder.toString());
    }

    public SimpleSessionDescription(String object) {
        String[] arrstring = ((String)object).trim().replaceAll(" +", " ").split("[\r\n]+");
        object = this.mFields;
        for (String string : arrstring) {
            block9 : {
                block10 : {
                    int n = 1;
                    if (string.charAt(1) != '=') break block9;
                    if (string.charAt(0) != 'm') break block10;
                    String[] arrstring2 = string.substring(2).split(" ", 4);
                    object = arrstring2[1].split("/", 2);
                    String string2 = arrstring2[0];
                    int n2 = Integer.parseInt(object[0]);
                    if (((String[])object).length >= 2) {
                        n = Integer.parseInt(object[1]);
                    }
                    object = this.newMedia(string2, n2, n, arrstring2[2]);
                    arrstring2 = arrstring2[3].split(" ");
                    n2 = arrstring2.length;
                    for (n = 0; n < n2; ++n) {
                        ((Media)object).setFormat(arrstring2[n], null);
                    }
                    continue;
                }
                try {
                    ((Fields)object).parse(string);
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid SDP: ");
                    stringBuilder.append(string);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                continue;
            }
            object = new IllegalArgumentException();
            throw object;
        }
    }

    public String encode() {
        StringBuilder stringBuilder = new StringBuilder();
        this.mFields.write(stringBuilder);
        Iterator<Media> iterator = this.mMedia.iterator();
        while (iterator.hasNext()) {
            iterator.next().write(stringBuilder);
        }
        return stringBuilder.toString();
    }

    public String getAddress() {
        return this.mFields.getAddress();
    }

    public String getAttribute(String string) {
        return this.mFields.getAttribute(string);
    }

    public String[] getAttributeNames() {
        return this.mFields.getAttributeNames();
    }

    public int getBandwidth(String string) {
        return this.mFields.getBandwidth(string);
    }

    public String[] getBandwidthTypes() {
        return this.mFields.getBandwidthTypes();
    }

    public String getEncryptionKey() {
        return this.mFields.getEncryptionKey();
    }

    public String getEncryptionMethod() {
        return this.mFields.getEncryptionMethod();
    }

    public Media[] getMedia() {
        ArrayList<Media> arrayList = this.mMedia;
        return arrayList.toArray(new Media[arrayList.size()]);
    }

    public Media newMedia(String object, int n, int n2, String string) {
        object = new Media((String)object, n, n2, string);
        this.mMedia.add((Media)object);
        return object;
    }

    public void setAddress(String string) {
        this.mFields.setAddress(string);
    }

    public void setAttribute(String string, String string2) {
        this.mFields.setAttribute(string, string2);
    }

    public void setBandwidth(String string, int n) {
        this.mFields.setBandwidth(string, n);
    }

    public void setEncryption(String string, String string2) {
        this.mFields.setEncryption(string, string2);
    }

    private static class Fields {
        private final ArrayList<String> mLines = new ArrayList();
        private final String mOrder;

        Fields(String string) {
            this.mOrder = string;
        }

        private String[] cut(String string, char c) {
            String[] arrstring = new String[this.mLines.size()];
            int n = 0;
            for (String string2 : this.mLines) {
                int n2 = n;
                if (string2.startsWith(string)) {
                    int n3;
                    n2 = n3 = string2.indexOf(c);
                    if (n3 == -1) {
                        n2 = string2.length();
                    }
                    arrstring[n] = string2.substring(string.length(), n2);
                    n2 = n + 1;
                }
                n = n2;
            }
            return Arrays.copyOf(arrstring, n);
        }

        private int find(String string, char c) {
            int n = string.length();
            for (int i = this.mLines.size() - 1; i >= 0; --i) {
                String string2 = this.mLines.get(i);
                if (!string2.startsWith(string) || string2.length() != n && string2.charAt(n) != c) continue;
                return i;
            }
            return -1;
        }

        private String get(String string, char c) {
            int n = this.find(string, c);
            if (n == -1) {
                return null;
            }
            String string2 = this.mLines.get(n);
            n = string.length();
            string = string2.length() == n ? "" : string2.substring(n + 1);
            return string;
        }

        private void parse(String string) {
            char c;
            char c2;
            block7 : {
                block5 : {
                    block6 : {
                        char c3 = string.charAt(0);
                        if (this.mOrder.indexOf(c3) == -1) {
                            return;
                        }
                        c = '=';
                        if (string.startsWith("a=rtpmap:") || string.startsWith("a=fmtp:")) break block5;
                        if (c3 == 'b') break block6;
                        c2 = c;
                        if (c3 != 'a') break block7;
                    }
                    c2 = c = ':';
                    break block7;
                }
                c2 = c = ' ';
            }
            if ((c = string.indexOf(c2)) == '\uffffffff') {
                this.set(string, c2, "");
            } else {
                this.set(string.substring(0, c), c2, string.substring(c + '\u0001'));
            }
        }

        private void set(String string, char c, String string2) {
            int n = this.find(string, c);
            if (string2 != null) {
                CharSequence charSequence = string;
                if (string2.length() != 0) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append(c);
                    ((StringBuilder)charSequence).append(string2);
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                if (n == -1) {
                    this.mLines.add((String)charSequence);
                } else {
                    this.mLines.set(n, (String)charSequence);
                }
            } else if (n != -1) {
                this.mLines.remove(n);
            }
        }

        private void write(StringBuilder stringBuilder) {
            for (int i = 0; i < this.mOrder.length(); ++i) {
                char c = this.mOrder.charAt(i);
                for (String string : this.mLines) {
                    if (string.charAt(0) != c) continue;
                    stringBuilder.append(string);
                    stringBuilder.append("\r\n");
                }
            }
        }

        public String getAddress() {
            Object object = this.get("c", '=');
            if (object == null) {
                return null;
            }
            if (((String[])(object = object.split(" "))).length != 3) {
                return null;
            }
            int n = object[2].indexOf(47);
            object = n < 0 ? object[2] : object[2].substring(0, n);
            return object;
        }

        public String getAttribute(String string) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("a=");
            stringBuilder.append(string);
            return this.get(stringBuilder.toString(), ':');
        }

        public String[] getAttributeNames() {
            return this.cut("a=", ':');
        }

        public int getBandwidth(String string) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("b=");
            charSequence.append(string);
            charSequence = this.get(charSequence.toString(), ':');
            if (charSequence != null) {
                try {
                    int n = Integer.parseInt((String)charSequence);
                    return n;
                }
                catch (NumberFormatException numberFormatException) {
                    this.setBandwidth(string, -1);
                }
            }
            return -1;
        }

        public String[] getBandwidthTypes() {
            return this.cut("b=", ':');
        }

        public String getEncryptionKey() {
            String string = this.get("k", '=');
            String string2 = null;
            if (string == null) {
                return null;
            }
            int n = string.indexOf(58);
            if (n != -1) {
                string2 = string.substring(0, n + 1);
            }
            return string2;
        }

        public String getEncryptionMethod() {
            String string = this.get("k", '=');
            if (string == null) {
                return null;
            }
            int n = string.indexOf(58);
            if (n != -1) {
                string = string.substring(0, n);
            }
            return string;
        }

        public void setAddress(String string) {
            String string2 = string;
            if (string != null) {
                StringBuilder stringBuilder = new StringBuilder();
                string2 = string.indexOf(58) < 0 ? "IN IP4 " : "IN IP6 ";
                stringBuilder.append(string2);
                stringBuilder.append(string);
                string2 = stringBuilder.toString();
            }
            this.set("c", '=', string2);
        }

        public void setAttribute(String string, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("a=");
            stringBuilder.append(string);
            this.set(stringBuilder.toString(), ':', string2);
        }

        public void setBandwidth(String string, int n) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("b=");
            charSequence.append(string);
            charSequence = charSequence.toString();
            string = n < 0 ? null : String.valueOf(n);
            this.set((String)charSequence, ':', string);
        }

        public void setEncryption(String string, String string2) {
            if (string != null && string2 != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(':');
                stringBuilder.append(string2);
                string = stringBuilder.toString();
            }
            this.set("k", '=', string);
        }
    }

    public static class Media
    extends Fields {
        private ArrayList<String> mFormats = new ArrayList();
        private final int mPort;
        private final int mPortCount;
        private final String mProtocol;
        private final String mType;

        private Media(String string, int n, int n2, String string2) {
            super("icbka");
            this.mType = string;
            this.mPort = n;
            this.mPortCount = n2;
            this.mProtocol = string2;
        }

        private void write(StringBuilder stringBuilder) {
            stringBuilder.append("m=");
            stringBuilder.append(this.mType);
            stringBuilder.append(' ');
            stringBuilder.append(this.mPort);
            if (this.mPortCount != 1) {
                stringBuilder.append('/');
                stringBuilder.append(this.mPortCount);
            }
            stringBuilder.append(' ');
            stringBuilder.append(this.mProtocol);
            for (String string : this.mFormats) {
                stringBuilder.append(' ');
                stringBuilder.append(string);
            }
            stringBuilder.append("\r\n");
            this.write(stringBuilder);
        }

        public String getFmtp(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("a=fmtp:");
            stringBuilder.append(n);
            return this.get(stringBuilder.toString(), ' ');
        }

        public String getFmtp(String string) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("a=fmtp:");
            stringBuilder.append(string);
            return this.get(stringBuilder.toString(), ' ');
        }

        public String[] getFormats() {
            ArrayList<String> arrayList = this.mFormats;
            return arrayList.toArray(new String[arrayList.size()]);
        }

        public int getPort() {
            return this.mPort;
        }

        public int getPortCount() {
            return this.mPortCount;
        }

        public String getProtocol() {
            return this.mProtocol;
        }

        public int[] getRtpPayloadTypes() {
            int[] arrn = new int[this.mFormats.size()];
            int n = 0;
            for (String string : this.mFormats) {
                try {
                    arrn[n] = Integer.parseInt(string);
                    ++n;
                }
                catch (NumberFormatException numberFormatException) {}
            }
            return Arrays.copyOf(arrn, n);
        }

        public String getRtpmap(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("a=rtpmap:");
            stringBuilder.append(n);
            return this.get(stringBuilder.toString(), ' ');
        }

        public String getType() {
            return this.mType;
        }

        public void removeFormat(String string) {
            this.mFormats.remove(string);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("a=rtpmap:");
            stringBuilder.append(string);
            this.set(stringBuilder.toString(), ' ', null);
            stringBuilder = new StringBuilder();
            stringBuilder.append("a=fmtp:");
            stringBuilder.append(string);
            this.set(stringBuilder.toString(), ' ', null);
        }

        public void removeRtpPayload(int n) {
            this.removeFormat(String.valueOf(n));
        }

        public void setFormat(String string, String string2) {
            this.mFormats.remove(string);
            this.mFormats.add(string);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("a=rtpmap:");
            stringBuilder.append(string);
            this.set(stringBuilder.toString(), ' ', null);
            stringBuilder = new StringBuilder();
            stringBuilder.append("a=fmtp:");
            stringBuilder.append(string);
            this.set(stringBuilder.toString(), ' ', string2);
        }

        public void setRtpPayload(int n, String charSequence, String string) {
            String string2 = String.valueOf(n);
            this.mFormats.remove(string2);
            this.mFormats.add(string2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("a=rtpmap:");
            stringBuilder.append(string2);
            this.set(stringBuilder.toString(), ' ', (String)charSequence);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("a=fmtp:");
            ((StringBuilder)charSequence).append(string2);
            this.set(((StringBuilder)charSequence).toString(), ' ', string);
        }
    }

}

