/*
 * Decompiled with CFR 0.145.
 */
package java.net;

class Parts {
    String path;
    String query;
    String ref;

    Parts(String charSequence, String string) {
        int n = ((String)charSequence).indexOf(35);
        String string2 = n < 0 ? null : ((String)charSequence).substring(n + 1);
        this.ref = string2;
        if (n >= 0) {
            charSequence = ((String)charSequence).substring(0, n);
        }
        n = ((String)charSequence).lastIndexOf(63);
        if (n != -1) {
            this.query = ((String)charSequence).substring(n + 1);
            this.path = ((String)charSequence).substring(0, n);
        } else {
            this.path = charSequence;
        }
        charSequence = this.path;
        if (charSequence != null && ((String)charSequence).length() > 0 && this.path.charAt(0) != '/' && string != null && !string.isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append('/');
            ((StringBuilder)charSequence).append(this.path);
            this.path = ((StringBuilder)charSequence).toString();
        }
    }

    String getPath() {
        return this.path;
    }

    String getQuery() {
        return this.query;
    }

    String getRef() {
        return this.ref;
    }
}

