/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.util;

import android.annotation.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractMessageParser {
    public static final String musicNote = "\u266b ";
    private HashMap<Character, Format> formatStart;
    private int nextChar;
    private int nextClass;
    private boolean parseAcronyms;
    private boolean parseFormatting;
    private boolean parseMeText;
    private boolean parseMusic;
    private boolean parseSmilies;
    private boolean parseUrls;
    private ArrayList<Part> parts;
    private String text;
    private ArrayList<Token> tokens;

    public AbstractMessageParser(String string2) {
        this(string2, true, true, true, true, true, true);
    }

    public AbstractMessageParser(String string2, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        this.text = string2;
        this.nextChar = 0;
        this.nextClass = 10;
        this.parts = new ArrayList();
        this.tokens = new ArrayList();
        this.formatStart = new HashMap();
        this.parseSmilies = bl;
        this.parseAcronyms = bl2;
        this.parseFormatting = bl3;
        this.parseUrls = bl4;
        this.parseMusic = bl5;
        this.parseMeText = bl6;
    }

    private void addToken(Token token) {
        this.tokens.add(token);
    }

    private void addURLToken(String string2, String string3) {
        this.addToken(AbstractMessageParser.tokenForUrl(string2, string3));
    }

    private void buildParts(String string2) {
        for (int i = 0; i < this.tokens.size(); ++i) {
            Token token = this.tokens.get(i);
            if (token.isMedia() || this.parts.size() == 0 || this.lastPart().isMedia()) {
                this.parts.add(new Part());
            }
            this.lastPart().add(token);
        }
        if (this.parts.size() > 0) {
            this.parts.get(0).setMeText(string2);
        }
    }

    private int getCharClass(int n) {
        if (n >= 0 && this.text.length() > n) {
            char c = this.text.charAt(n);
            if (Character.isWhitespace(c)) {
                return 1;
            }
            if (Character.isLetter(c)) {
                return 2;
            }
            if (Character.isDigit(c)) {
                return 3;
            }
            if (AbstractMessageParser.isPunctuation(c)) {
                this.nextClass = n = this.nextClass + 1;
                return n;
            }
            return 4;
        }
        return 0;
    }

    private boolean isDomainChar(char c) {
        boolean bl = c == '-' || Character.isLetter(c) || Character.isDigit(c);
        return bl;
    }

    private static boolean isFormatChar(char c) {
        return c == '*' || c == '^' || c == '_';
    }

    private static boolean isPunctuation(char c) {
        return c == '!' || c == '\"' || c == '(' || c == ')' || c == ',' || c == '.' || c == '?' || c == ':' || c == ';';
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isSmileyBreak(char c, char c2) {
        if (c != '$' && c != '&' && c != '-' && c != '/' && c != '@' && c != '*' && c != '+') {
            switch (c) {
                default: {
                    switch (c) {
                        default: {
                            switch (c) {
                                default: {
                                    return false;
                                }
                                case '|': 
                                case '}': 
                                case '~': 
                            }
                        }
                        case '[': 
                        case '\\': 
                        case ']': 
                        case '^': 
                    }
                }
                case '<': 
                case '=': 
                case '>': 
            }
        }
        if (c2 == '*') return true;
        if (c2 == '/') return true;
        if (c2 == '@') return true;
        if (c2 == '^') return true;
        if (c2 == '~') return true;
        if (c2 == '[') return true;
        if (c2 == '\\') return true;
        switch (c2) {
            default: {
                switch (c2) {
                    default: {
                        return false;
                    }
                    case '<': 
                    case '=': 
                    case '>': 
                }
            }
            case '#': 
            case '$': 
            case '%': 
        }
        return true;
    }

    private boolean isSmileyBreak(int n) {
        return n > 0 && n < this.text.length() && AbstractMessageParser.isSmileyBreak(this.text.charAt(n - 1), this.text.charAt(n));
    }

    private boolean isURLBreak(int n) {
        return (n = this.getCharClass(n - 1)) != 2 && n != 3 && n != 4;
    }

    private boolean isValidDomain(String string2) {
        return AbstractMessageParser.matches(this.getResources().getDomainSuffixes(), AbstractMessageParser.reverse(string2));
    }

    private boolean isWordBreak(int n) {
        boolean bl = this.getCharClass(n - 1) != this.getCharClass(n);
        return bl;
    }

    private Part lastPart() {
        ArrayList<Part> arrayList = this.parts;
        return arrayList.get(arrayList.size() - 1);
    }

    private static TrieNode longestMatch(TrieNode trieNode, AbstractMessageParser abstractMessageParser, int n) {
        return AbstractMessageParser.longestMatch(trieNode, abstractMessageParser, n, false);
    }

    private static TrieNode longestMatch(TrieNode trieNode, AbstractMessageParser abstractMessageParser, int n, boolean bl) {
        TrieNode trieNode2 = null;
        while (n < abstractMessageParser.getRawText().length()) {
            String string2 = abstractMessageParser.getRawText();
            int n2 = n + 1;
            if ((trieNode = trieNode.getChild(string2.charAt(n))) == null) break;
            if (trieNode.exists()) {
                if (abstractMessageParser.isWordBreak(n2)) {
                    trieNode2 = trieNode;
                    n = n2;
                    continue;
                }
                if (bl && abstractMessageParser.isSmileyBreak(n2)) {
                    trieNode2 = trieNode;
                    n = n2;
                    continue;
                }
            }
            n = n2;
        }
        return trieNode2;
    }

    private static boolean matches(TrieNode trieNode, String string2) {
        for (int i = 0; i < string2.length() && (trieNode = trieNode.getChild(string2.charAt(i))) != null; ++i) {
            if (!trieNode.exists()) continue;
            return true;
        }
        return false;
    }

    private boolean parseAcronym() {
        if (!this.parseAcronyms) {
            return false;
        }
        TrieNode trieNode = AbstractMessageParser.longestMatch(this.getResources().getAcronyms(), this, this.nextChar);
        if (trieNode == null) {
            return false;
        }
        this.addToken(new Acronym(trieNode.getText(), trieNode.getValue()));
        this.nextChar += trieNode.getText().length();
        return true;
    }

    private boolean parseFormatting() {
        int n;
        if (!this.parseFormatting) {
            return false;
        }
        for (n = this.nextChar; n < this.text.length() && AbstractMessageParser.isFormatChar(this.text.charAt(n)); ++n) {
        }
        if (n != this.nextChar && this.isWordBreak(n)) {
            LinkedHashMap<Object, Boolean> linkedHashMap = new LinkedHashMap<Object, Boolean>();
            for (int i = this.nextChar; i < n; ++i) {
                char c = this.text.charAt(i);
                Character c2 = Character.valueOf(c);
                if (linkedHashMap.containsKey(c2)) {
                    this.addToken(new Format(c, false));
                    continue;
                }
                Object object = this.formatStart.get(c2);
                if (object != null) {
                    ((Format)object).setMatched(true);
                    this.formatStart.remove(c2);
                    linkedHashMap.put(c2, Boolean.TRUE);
                    continue;
                }
                object = new Format(c, true);
                this.formatStart.put(c2, (Format)object);
                this.addToken((Token)object);
                linkedHashMap.put(c2, Boolean.FALSE);
            }
            for (Object object : linkedHashMap.keySet()) {
                if (linkedHashMap.get(object) != Boolean.TRUE) continue;
                object = new Format(((Character)object).charValue(), false);
                ((Format)object).setMatched(true);
                this.addToken((Token)object);
            }
            this.nextChar = n;
            return true;
        }
        return false;
    }

    private boolean parseMusicTrack() {
        if (this.parseMusic && this.text.startsWith(musicNote)) {
            this.addToken(new MusicTrack(this.text.substring(musicNote.length())));
            this.nextChar = this.text.length();
            return true;
        }
        return false;
    }

    private boolean parseSmiley() {
        if (!this.parseSmilies) {
            return false;
        }
        TrieNode trieNode = AbstractMessageParser.longestMatch(this.getResources().getSmileys(), this, this.nextChar, true);
        if (trieNode == null) {
            return false;
        }
        int n = this.getCharClass(this.nextChar - 1);
        int n2 = this.getCharClass(this.nextChar + trieNode.getText().length());
        if (n != 2 && n != 3 || n2 != 2 && n2 != 3) {
            this.addToken(new Smiley(trieNode.getText()));
            this.nextChar += trieNode.getText().length();
            return true;
        }
        return false;
    }

    private void parseText() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.nextChar;
        do {
            String string2 = this.text;
            int n2 = this.nextChar;
            this.nextChar = n2 + 1;
            char c = string2.charAt(n2);
            if (c != '\n') {
                if (c != '\"') {
                    if (c != '<') {
                        if (c != '>') {
                            if (c != '&') {
                                if (c != '\'') {
                                    stringBuilder.append(c);
                                    continue;
                                }
                                stringBuilder.append("&apos;");
                                continue;
                            }
                            stringBuilder.append("&amp;");
                            continue;
                        }
                        stringBuilder.append("&gt;");
                        continue;
                    }
                    stringBuilder.append("&lt;");
                    continue;
                }
                stringBuilder.append("&quot;");
                continue;
            }
            stringBuilder.append("<br>");
        } while (!this.isWordBreak(this.nextChar));
        this.addToken(new Html(this.text.substring(n, this.nextChar), stringBuilder.toString()));
    }

    private boolean parseURL() {
        block20 : {
            block23 : {
                String string2;
                int n;
                CharSequence charSequence;
                int n2;
                int n3;
                int n4;
                block22 : {
                    block25 : {
                        char c;
                        block26 : {
                            block28 : {
                                int n5;
                                block27 : {
                                    block24 : {
                                        block21 : {
                                            if (!this.parseUrls || !this.isURLBreak(this.nextChar)) break block20;
                                            for (n = n4 = this.nextChar; n < this.text.length() && this.isDomainChar(this.text.charAt(n)); ++n) {
                                            }
                                            string2 = "";
                                            n3 = 0;
                                            n2 = 0;
                                            n5 = 0;
                                            if (n == this.text.length()) {
                                                return false;
                                            }
                                            if (this.text.charAt(n) != ':') break block21;
                                            charSequence = this.text.substring(this.nextChar, n);
                                            if (!this.getResources().getSchemes().contains(charSequence)) {
                                                return false;
                                            }
                                            n3 = n2;
                                            break block22;
                                        }
                                        if (this.text.charAt(n) != '.') break block23;
                                        while (n < this.text.length() && ((c = this.text.charAt(n)) == '.' || this.isDomainChar(c))) {
                                            ++n;
                                        }
                                        if (!this.isValidDomain(this.text.substring(this.nextChar, n))) {
                                            return false;
                                        }
                                        n2 = n;
                                        if (n + 1 < this.text.length()) {
                                            n2 = n;
                                            if (this.text.charAt(n) == ':') {
                                                n2 = n;
                                                if (Character.isDigit(this.text.charAt(n + 1))) {
                                                    ++n;
                                                    do {
                                                        n2 = n;
                                                        if (n >= this.text.length()) break;
                                                        n2 = n;
                                                        if (!Character.isDigit(this.text.charAt(n))) break;
                                                        ++n;
                                                    } while (true);
                                                }
                                            }
                                        }
                                        if (n2 != this.text.length()) break block24;
                                        n = 1;
                                        break block25;
                                    }
                                    c = this.text.charAt(n2);
                                    if (c != '?') break block26;
                                    if (n2 + 1 != this.text.length()) break block27;
                                    n = 1;
                                    break block25;
                                }
                                c = this.text.charAt(n2 + 1);
                                if (Character.isWhitespace(c)) break block28;
                                n = n5;
                                if (!AbstractMessageParser.isPunctuation(c)) break block25;
                            }
                            n = 1;
                            break block25;
                        }
                        if (AbstractMessageParser.isPunctuation(c)) {
                            n = 1;
                        } else if (Character.isWhitespace(c)) {
                            n = 1;
                        } else {
                            n = n3;
                            if (c != '/') {
                                if (c == '#') {
                                    n = n3;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                    string2 = "http://";
                    n3 = n;
                    n = n2;
                }
                n2 = n;
                if (n3 == 0) {
                    do {
                        n2 = n;
                        if (n >= this.text.length()) break;
                        n2 = n;
                        if (Character.isWhitespace(this.text.charAt(n))) break;
                        ++n;
                    } while (true);
                }
                String string3 = this.text.substring(n4, n2);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(string3);
                this.addURLToken(((StringBuilder)charSequence).toString(), string3);
                this.nextChar = n2;
                return true;
            }
            return false;
        }
        return false;
    }

    protected static String reverse(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = string2.length() - 1; i >= 0; --i) {
            stringBuilder.append(string2.charAt(i));
        }
        return stringBuilder.toString();
    }

    public static Token tokenForUrl(String string2, String string3) {
        if (string2 == null) {
            return null;
        }
        Token token = Video.matchURL(string2, string3);
        if (token != null) {
            return token;
        }
        token = YouTubeVideo.matchURL(string2, string3);
        if (token != null) {
            return token;
        }
        token = Photo.matchURL(string2, string3);
        if (token != null) {
            return token;
        }
        token = FlickrPhoto.matchURL(string2, string3);
        if (token != null) {
            return token;
        }
        return new Link(string2, string3);
    }

    public final Part getPart(int n) {
        return this.parts.get(n);
    }

    public final int getPartCount() {
        return this.parts.size();
    }

    public final List<Part> getParts() {
        return this.parts;
    }

    public final String getRawText() {
        return this.text;
    }

    protected abstract Resources getResources();

    public void parse() {
        String string2;
        int n;
        if (this.parseMusicTrack()) {
            this.buildParts(null);
            return;
        }
        String string3 = string2 = null;
        if (this.parseMeText) {
            string3 = string2;
            if (this.text.startsWith("/me")) {
                string3 = string2;
                if (this.text.length() > 3) {
                    string3 = string2;
                    if (Character.isWhitespace(this.text.charAt(3))) {
                        string3 = this.text.substring(0, 4);
                        this.text = this.text.substring(4);
                    }
                }
            }
        }
        int n2 = 0;
        while (this.nextChar < this.text.length()) {
            if (!(this.isWordBreak(this.nextChar) || n2 != 0 && this.isSmileyBreak(this.nextChar))) {
                throw new AssertionError((Object)"last chunk did not end at word break");
            }
            if (this.parseSmiley()) {
                n2 = 1;
                continue;
            }
            n2 = n = 0;
            if (this.parseAcronym()) continue;
            n2 = n;
            if (this.parseURL()) continue;
            n2 = n;
            if (this.parseFormatting()) continue;
            this.parseText();
            n2 = n;
        }
        for (n2 = 0; n2 < this.tokens.size(); ++n2) {
            if (!this.tokens.get(n2).isMedia()) continue;
            if (n2 > 0 && this.tokens.get(n2 - 1) instanceof Html) {
                ((Html)this.tokens.get(n2 - 1)).trimLeadingWhitespace();
            }
            if (n2 + 1 >= this.tokens.size() || !(this.tokens.get(n2 + 1) instanceof Html)) continue;
            ((Html)this.tokens.get(n2 + 1)).trimTrailingWhitespace();
        }
        n2 = 0;
        while (n2 < this.tokens.size()) {
            n = n2;
            if (this.tokens.get(n2).isHtml()) {
                n = n2;
                if (this.tokens.get(n2).toHtml(true).length() == 0) {
                    this.tokens.remove(n2);
                    n = n2 - 1;
                }
            }
            n2 = n + 1;
        }
        this.buildParts(string3);
    }

    public String toHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Part part : this.parts) {
            boolean bl = false;
            stringBuilder.append("<p>");
            for (Token token : part.getTokens()) {
                if (token.isHtml()) {
                    stringBuilder.append(token.toHtml(bl));
                } else {
                    switch (token.getType()) {
                        default: {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("unknown token type: ");
                            stringBuilder.append((Object)((Object)token.getType()));
                            throw new AssertionError((Object)stringBuilder.toString());
                        }
                        case FLICKR: {
                            Token token2 = (Photo)token;
                            stringBuilder.append("<a href=\"");
                            stringBuilder.append(((FlickrPhoto)token).getUrl());
                            stringBuilder.append("\">");
                            stringBuilder.append(token.getRawText());
                            stringBuilder.append("</a>");
                            break;
                        }
                        case PHOTO: {
                            stringBuilder.append("<a href=\"");
                            stringBuilder.append(Photo.getAlbumURL(((Photo)token).getUser(), ((Photo)token).getAlbum()));
                            stringBuilder.append("\">");
                            stringBuilder.append(token.getRawText());
                            stringBuilder.append("</a>");
                            break;
                        }
                        case YOUTUBE_VIDEO: {
                            stringBuilder.append("<a href=\"");
                            Token token2 = (YouTubeVideo)token;
                            stringBuilder.append(YouTubeVideo.getURL(((YouTubeVideo)token).getDocID()));
                            stringBuilder.append("\">");
                            stringBuilder.append(token.getRawText());
                            stringBuilder.append("</a>");
                            break;
                        }
                        case GOOGLE_VIDEO: {
                            stringBuilder.append("<a href=\"");
                            Token token2 = (Video)token;
                            stringBuilder.append(Video.getURL(((Video)token).getDocID()));
                            stringBuilder.append("\">");
                            stringBuilder.append(token.getRawText());
                            stringBuilder.append("</a>");
                            break;
                        }
                        case MUSIC: {
                            stringBuilder.append(((MusicTrack)token).getTrack());
                            break;
                        }
                        case ACRONYM: {
                            stringBuilder.append(token.getRawText());
                            break;
                        }
                        case SMILEY: {
                            stringBuilder.append(token.getRawText());
                            break;
                        }
                        case LINK: {
                            stringBuilder.append("<a href=\"");
                            stringBuilder.append(((Link)token).getURL());
                            stringBuilder.append("\">");
                            stringBuilder.append(token.getRawText());
                            stringBuilder.append("</a>");
                        }
                    }
                }
                if (!token.controlCaps()) continue;
                bl = token.setCaps();
            }
            stringBuilder.append("</p>\n");
        }
        return stringBuilder.toString();
    }

    public static class Acronym
    extends Token {
        private String value;

        public Acronym(String string2, String string3) {
            super(Token.Type.ACRONYM, string2);
            this.value = string3;
        }

        @Override
        public List<String> getInfo() {
            List<String> list = super.getInfo();
            list.add(this.getRawText());
            list.add(this.getValue());
            return list;
        }

        public String getValue() {
            return this.value;
        }

        @Override
        public boolean isHtml() {
            return false;
        }
    }

    public static class FlickrPhoto
    extends Token {
        private static final Pattern GROUPING_PATTERN;
        private static final String SETS = "sets";
        private static final String TAGS = "tags";
        private static final Pattern URL_PATTERN;
        private String grouping;
        private String groupingId;
        private String photo;
        private String user;

        static {
            URL_PATTERN = Pattern.compile("http://(?:www.)?flickr.com/photos/([^/?#&]+)/?([^/?#&]+)?/?.*");
            GROUPING_PATTERN = Pattern.compile("http://(?:www.)?flickr.com/photos/([^/?#&]+)/(tags|sets)/([^/?#&]+)/?");
        }

        public FlickrPhoto(String string2, String string3, String string4, String string5, String string6) {
            super(Token.Type.FLICKR, string6);
            boolean bl = TAGS.equals(string2);
            string6 = null;
            if (!bl) {
                this.user = string2;
                string2 = string6;
                if (!"show".equals(string3)) {
                    string2 = string3;
                }
                this.photo = string2;
                this.grouping = string4;
                this.groupingId = string5;
            } else {
                this.user = null;
                this.photo = null;
                this.grouping = TAGS;
                this.groupingId = string3;
            }
        }

        public static String getPhotoURL(String string2, String string3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://flickr.com/photos/");
            stringBuilder.append(string2);
            stringBuilder.append("/");
            stringBuilder.append(string3);
            return stringBuilder.toString();
        }

        public static String getRssUrl(String string2) {
            return null;
        }

        public static String getTagsURL(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://flickr.com/photos/tags/");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }

        public static String getUserSetsURL(String string2, String string3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://flickr.com/photos/");
            stringBuilder.append(string2);
            stringBuilder.append("/sets/");
            stringBuilder.append(string3);
            return stringBuilder.toString();
        }

        public static String getUserTagsURL(String string2, String string3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://flickr.com/photos/");
            stringBuilder.append(string2);
            stringBuilder.append("/tags/");
            stringBuilder.append(string3);
            return stringBuilder.toString();
        }

        public static String getUserURL(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://flickr.com/photos/");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }

        public static FlickrPhoto matchURL(String object, String string2) {
            Matcher matcher = GROUPING_PATTERN.matcher((CharSequence)object);
            if (matcher.matches()) {
                return new FlickrPhoto(matcher.group(1), null, matcher.group(2), matcher.group(3), string2);
            }
            if (((Matcher)(object = URL_PATTERN.matcher((CharSequence)object))).matches()) {
                return new FlickrPhoto(((Matcher)object).group(1), ((Matcher)object).group(2), null, null, string2);
            }
            return null;
        }

        public String getGrouping() {
            return this.grouping;
        }

        public String getGroupingId() {
            return this.groupingId;
        }

        @Override
        public List<String> getInfo() {
            List<String> list = super.getInfo();
            list.add(this.getUrl());
            String string2 = this.getUser();
            String string3 = "";
            string2 = string2 != null ? this.getUser() : "";
            list.add(string2);
            string2 = this.getPhoto() != null ? this.getPhoto() : "";
            list.add(string2);
            string2 = this.getGrouping() != null ? this.getGrouping() : "";
            list.add(string2);
            string2 = string3;
            if (this.getGroupingId() != null) {
                string2 = this.getGroupingId();
            }
            list.add(string2);
            return list;
        }

        public String getPhoto() {
            return this.photo;
        }

        public String getUrl() {
            if (SETS.equals(this.grouping)) {
                return FlickrPhoto.getUserSetsURL(this.user, this.groupingId);
            }
            if (TAGS.equals(this.grouping)) {
                String string2 = this.user;
                if (string2 != null) {
                    return FlickrPhoto.getUserTagsURL(string2, this.groupingId);
                }
                return FlickrPhoto.getTagsURL(this.groupingId);
            }
            String string3 = this.photo;
            if (string3 != null) {
                return FlickrPhoto.getPhotoURL(this.user, string3);
            }
            return FlickrPhoto.getUserURL(this.user);
        }

        public String getUser() {
            return this.user;
        }

        @Override
        public boolean isHtml() {
            return false;
        }

        @Override
        public boolean isMedia() {
            return true;
        }
    }

    public static class Format
    extends Token {
        private char ch;
        private boolean matched;
        private boolean start;

        public Format(char c, boolean bl) {
            super(Token.Type.FORMAT, String.valueOf(c));
            this.ch = c;
            this.start = bl;
        }

        private String getFormatEnd(char c) {
            if (c != '\"') {
                if (c != '*') {
                    if (c != '^') {
                        if (c == '_') {
                            return "</i>";
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unknown format '");
                        stringBuilder.append(c);
                        stringBuilder.append("'");
                        throw new AssertionError((Object)stringBuilder.toString());
                    }
                    return "</font></b>";
                }
                return "</b>";
            }
            return "\u201d</font>";
        }

        private String getFormatStart(char c) {
            if (c != '\"') {
                if (c != '*') {
                    if (c != '^') {
                        if (c == '_') {
                            return "<i>";
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unknown format '");
                        stringBuilder.append(c);
                        stringBuilder.append("'");
                        throw new AssertionError((Object)stringBuilder.toString());
                    }
                    return "<b><font color=\"#005FFF\">";
                }
                return "<b>";
            }
            return "<font color=\"#999999\">\u201c";
        }

        @Override
        public boolean controlCaps() {
            boolean bl = this.ch == '^';
            return bl;
        }

        @Override
        public List<String> getInfo() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isHtml() {
            return true;
        }

        @Override
        public boolean setCaps() {
            return this.start;
        }

        public void setMatched(boolean bl) {
            this.matched = bl;
        }

        @Override
        public String toHtml(boolean bl) {
            if (this.matched) {
                String string2 = this.start ? this.getFormatStart(this.ch) : this.getFormatEnd(this.ch);
                return string2;
            }
            char c = this.ch;
            String string3 = c == '\"' ? "&quot;" : String.valueOf(c);
            return string3;
        }
    }

    public static class Html
    extends Token {
        private String html;

        public Html(String string2, String string3) {
            super(Token.Type.HTML, string2);
            this.html = string3;
        }

        private static String trimLeadingWhitespace(String string2) {
            int n;
            for (n = 0; n < string2.length() && Character.isWhitespace(string2.charAt(n)); ++n) {
            }
            return string2.substring(n);
        }

        public static String trimTrailingWhitespace(String string2) {
            int n;
            for (n = string2.length(); n > 0 && Character.isWhitespace(string2.charAt(n - 1)); --n) {
            }
            return string2.substring(0, n);
        }

        @Override
        public List<String> getInfo() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isHtml() {
            return true;
        }

        @Override
        public String toHtml(boolean bl) {
            String string2;
            String string3 = string2 = this.html;
            if (bl) {
                string3 = string2.toUpperCase();
            }
            return string3;
        }

        public void trimLeadingWhitespace() {
            this.text = Html.trimLeadingWhitespace(this.text);
            this.html = Html.trimLeadingWhitespace(this.html);
        }

        public void trimTrailingWhitespace() {
            this.text = Html.trimTrailingWhitespace(this.text);
            this.html = Html.trimTrailingWhitespace(this.html);
        }
    }

    public static class Link
    extends Token {
        private String url;

        public Link(String string2, String string3) {
            super(Token.Type.LINK, string3);
            this.url = string2;
        }

        @Override
        public List<String> getInfo() {
            List<String> list = super.getInfo();
            list.add(this.getURL());
            list.add(this.getRawText());
            return list;
        }

        public String getURL() {
            return this.url;
        }

        @Override
        public boolean isHtml() {
            return false;
        }
    }

    public static class MusicTrack
    extends Token {
        private String track;

        public MusicTrack(String string2) {
            super(Token.Type.MUSIC, string2);
            this.track = string2;
        }

        @Override
        public List<String> getInfo() {
            List<String> list = super.getInfo();
            list.add(this.getTrack());
            return list;
        }

        public String getTrack() {
            return this.track;
        }

        @Override
        public boolean isHtml() {
            return false;
        }
    }

    public static class Part {
        private String meText;
        private ArrayList<Token> tokens = new ArrayList();

        private String getPartType() {
            if (this.isMedia()) {
                return "d";
            }
            if (this.meText != null) {
                return "m";
            }
            return "";
        }

        public void add(Token token) {
            if (!this.isMedia()) {
                this.tokens.add(token);
                return;
            }
            throw new AssertionError((Object)"media ");
        }

        public Token getMediaToken() {
            if (this.isMedia()) {
                return this.tokens.get(0);
            }
            return null;
        }

        public String getRawText() {
            StringBuilder stringBuilder = new StringBuilder();
            String string2 = this.meText;
            if (string2 != null) {
                stringBuilder.append(string2);
            }
            for (int i = 0; i < this.tokens.size(); ++i) {
                stringBuilder.append(this.tokens.get(i).getRawText());
            }
            return stringBuilder.toString();
        }

        public ArrayList<Token> getTokens() {
            return this.tokens;
        }

        public String getType(boolean bl) {
            StringBuilder stringBuilder = new StringBuilder();
            String string2 = bl ? "s" : "r";
            stringBuilder.append(string2);
            stringBuilder.append(this.getPartType());
            return stringBuilder.toString();
        }

        public boolean isMedia() {
            boolean bl;
            int n = this.tokens.size();
            boolean bl2 = bl = false;
            if (n == 1) {
                bl2 = bl;
                if (this.tokens.get(0).isMedia()) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        public void setMeText(String string2) {
            this.meText = string2;
        }
    }

    public static class Photo
    extends Token {
        private static final Pattern URL_PATTERN = Pattern.compile("http://picasaweb.google.com/([^/?#&]+)/+((?!searchbrowse)[^/?#&]+)(?:/|/photo)?(?:\\?[^#]*)?(?:#(.*))?");
        private String album;
        private String photo;
        private String user;

        public Photo(String string2, String string3, String string4, String string5) {
            super(Token.Type.PHOTO, string5);
            this.user = string2;
            this.album = string3;
            this.photo = string4;
        }

        public static String getAlbumURL(String string2, String string3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://picasaweb.google.com/");
            stringBuilder.append(string2);
            stringBuilder.append("/");
            stringBuilder.append(string3);
            return stringBuilder.toString();
        }

        public static String getPhotoURL(String string2, String string3, String string4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://picasaweb.google.com/");
            stringBuilder.append(string2);
            stringBuilder.append("/");
            stringBuilder.append(string3);
            stringBuilder.append("/photo#");
            stringBuilder.append(string4);
            return stringBuilder.toString();
        }

        public static String getRssUrl(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://picasaweb.google.com/data/feed/api/user/");
            stringBuilder.append(string2);
            stringBuilder.append("?category=album&alt=rss");
            return stringBuilder.toString();
        }

        public static Photo matchURL(String object, String string2) {
            if (((Matcher)(object = URL_PATTERN.matcher((CharSequence)object))).matches()) {
                return new Photo(((Matcher)object).group(1), ((Matcher)object).group(2), ((Matcher)object).group(3), string2);
            }
            return null;
        }

        public String getAlbum() {
            return this.album;
        }

        @Override
        public List<String> getInfo() {
            List<String> list = super.getInfo();
            list.add(Photo.getRssUrl(this.getUser()));
            list.add(Photo.getAlbumURL(this.getUser(), this.getAlbum()));
            if (this.getPhoto() != null) {
                list.add(Photo.getPhotoURL(this.getUser(), this.getAlbum(), this.getPhoto()));
            } else {
                list.add(null);
            }
            return list;
        }

        public String getPhoto() {
            return this.photo;
        }

        public String getUser() {
            return this.user;
        }

        @Override
        public boolean isHtml() {
            return false;
        }

        @Override
        public boolean isMedia() {
            return true;
        }
    }

    public static interface Resources {
        public TrieNode getAcronyms();

        public TrieNode getDomainSuffixes();

        public Set<String> getSchemes();

        public TrieNode getSmileys();
    }

    public static class Smiley
    extends Token {
        public Smiley(String string2) {
            super(Token.Type.SMILEY, string2);
        }

        @Override
        public List<String> getInfo() {
            List<String> list = super.getInfo();
            list.add(this.getRawText());
            return list;
        }

        @Override
        public boolean isHtml() {
            return false;
        }
    }

    public static abstract class Token {
        protected String text;
        protected Type type;

        protected Token(Type type, String string2) {
            this.type = type;
            this.text = string2;
        }

        public boolean controlCaps() {
            return false;
        }

        public List<String> getInfo() {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(this.getType().toString());
            return arrayList;
        }

        public String getRawText() {
            return this.text;
        }

        public Type getType() {
            return this.type;
        }

        public boolean isArray() {
            return this.isHtml() ^ true;
        }

        public abstract boolean isHtml();

        public boolean isMedia() {
            return false;
        }

        public boolean setCaps() {
            return false;
        }

        public String toHtml(boolean bl) {
            throw new AssertionError((Object)"not html");
        }

        public static enum Type {
            HTML("html"),
            FORMAT("format"),
            LINK("l"),
            SMILEY("e"),
            ACRONYM("a"),
            MUSIC("m"),
            GOOGLE_VIDEO("v"),
            YOUTUBE_VIDEO("yt"),
            PHOTO("p"),
            FLICKR("f");
            
            private String stringRep;

            private Type(String string3) {
                this.stringRep = string3;
            }

            public String toString() {
                return this.stringRep;
            }
        }

    }

    public static class TrieNode {
        private final HashMap<Character, TrieNode> children = new HashMap();
        private String text;
        private String value;

        public TrieNode() {
            this("");
        }

        public TrieNode(String string2) {
            this.text = string2;
        }

        public static void addToTrie(TrieNode trieNode, String string2, String string3) {
            for (int i = 0; i < string2.length(); ++i) {
                trieNode = trieNode.getOrCreateChild(string2.charAt(i));
            }
            trieNode.setValue(string3);
        }

        public final boolean exists() {
            boolean bl = this.value != null;
            return bl;
        }

        public TrieNode getChild(char c) {
            return this.children.get(Character.valueOf(c));
        }

        public TrieNode getOrCreateChild(char c) {
            Character c2 = Character.valueOf(c);
            TrieNode trieNode = this.children.get(c2);
            Object object = trieNode;
            if (trieNode == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append(this.text);
                ((StringBuilder)object).append(String.valueOf(c));
                object = new TrieNode(((StringBuilder)object).toString());
                this.children.put(c2, (TrieNode)object);
            }
            return object;
        }

        public final String getText() {
            return this.text;
        }

        public final String getValue() {
            return this.value;
        }

        public void setValue(String string2) {
            this.value = string2;
        }
    }

    public static class Video
    extends Token {
        private static final Pattern URL_PATTERN = Pattern.compile("(?i)http://video\\.google\\.[a-z0-9]+(?:\\.[a-z0-9]+)?/videoplay\\?.*?\\bdocid=(-?\\d+).*");
        private String docid;

        public Video(String string2, String string3) {
            super(Token.Type.GOOGLE_VIDEO, string3);
            this.docid = string2;
        }

        public static String getRssUrl(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://video.google.com/videofeed?type=docid&output=rss&sourceid=gtalk&docid=");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }

        public static String getURL(String string2) {
            return Video.getURL(string2, null);
        }

        public static String getURL(String string2, String charSequence) {
            CharSequence charSequence2;
            if (charSequence == null) {
                charSequence2 = "";
            } else {
                charSequence2 = charSequence;
                if (((String)charSequence).length() > 0) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append("&");
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("http://video.google.com/videoplay?");
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("docid=");
            ((StringBuilder)charSequence).append(string2);
            return ((StringBuilder)charSequence).toString();
        }

        public static Video matchURL(String object, String string2) {
            if (((Matcher)(object = URL_PATTERN.matcher((CharSequence)object))).matches()) {
                return new Video(((Matcher)object).group(1), string2);
            }
            return null;
        }

        public String getDocID() {
            return this.docid;
        }

        @Override
        public List<String> getInfo() {
            List<String> list = super.getInfo();
            list.add(Video.getRssUrl(this.docid));
            list.add(Video.getURL(this.docid));
            return list;
        }

        @Override
        public boolean isHtml() {
            return false;
        }

        @Override
        public boolean isMedia() {
            return true;
        }
    }

    public static class YouTubeVideo
    extends Token {
        private static final Pattern URL_PATTERN = Pattern.compile("(?i)http://(?:[a-z0-9]+\\.)?youtube\\.[a-z0-9]+(?:\\.[a-z0-9]+)?/watch\\?.*\\bv=([-_a-zA-Z0-9=]+).*");
        private String docid;

        public YouTubeVideo(String string2, String string3) {
            super(Token.Type.YOUTUBE_VIDEO, string3);
            this.docid = string2;
        }

        public static String getPrefixedURL(boolean bl, String charSequence, String string2, String charSequence2) {
            String string3 = "";
            if (bl) {
                string3 = "http://";
            }
            String string4 = charSequence;
            if (charSequence == null) {
                string4 = "";
            }
            if (charSequence2 == null) {
                charSequence = "";
            } else {
                charSequence = charSequence2;
                if (((String)charSequence2).length() > 0) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append("&");
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append(string3);
            ((StringBuilder)charSequence2).append(string4);
            ((StringBuilder)charSequence2).append("youtube.com/watch?");
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("v=");
            ((StringBuilder)charSequence2).append(string2);
            return ((StringBuilder)charSequence2).toString();
        }

        public static String getRssUrl(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://youtube.com/watch?v=");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }

        public static String getURL(String string2) {
            return YouTubeVideo.getURL(string2, null);
        }

        public static String getURL(String string2, String charSequence) {
            CharSequence charSequence2;
            if (charSequence == null) {
                charSequence2 = "";
            } else {
                charSequence2 = charSequence;
                if (((String)charSequence).length() > 0) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append("&");
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("http://youtube.com/watch?");
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("v=");
            ((StringBuilder)charSequence).append(string2);
            return ((StringBuilder)charSequence).toString();
        }

        public static YouTubeVideo matchURL(String object, String string2) {
            if (((Matcher)(object = URL_PATTERN.matcher((CharSequence)object))).matches()) {
                return new YouTubeVideo(((Matcher)object).group(1), string2);
            }
            return null;
        }

        public String getDocID() {
            return this.docid;
        }

        @Override
        public List<String> getInfo() {
            List<String> list = super.getInfo();
            list.add(YouTubeVideo.getRssUrl(this.docid));
            list.add(YouTubeVideo.getURL(this.docid));
            return list;
        }

        @Override
        public boolean isHtml() {
            return false;
        }

        @Override
        public boolean isMedia() {
            return true;
        }
    }

}

