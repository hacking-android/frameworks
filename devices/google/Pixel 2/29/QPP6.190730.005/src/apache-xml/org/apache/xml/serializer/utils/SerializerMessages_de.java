/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.util.ListResourceBundle;

public class SerializerMessages_de
extends ListResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"BAD_MSGFORMAT", "Das Format der Nachricht ''{0}'' in der Nachrichtenklasse ''{1}'' ist fehlgeschlagen."};
        Object[] arrobject2 = new Object[]{"ER_RESOURCE_COULD_NOT_FIND", "Die Ressource [ {0} ] konnte nicht gefunden werden.\n {1}"};
        Object[] arrobject3 = new Object[]{"ER_BUFFER_SIZE_LESSTHAN_ZERO", "Puffergr\u00f6\u00dfe <=0"};
        Object[] arrobject4 = new Object[]{"ER_INVALID_UTF16_SURROGATE", "Ung\u00fcltige UTF-16-Ersetzung festgestellt: {0} ?"};
        Object[] arrobject5 = new Object[]{"ER_OIERROR", "E/A-Fehler"};
        Object[] arrobject6 = new Object[]{"ER_ILLEGAL_ATTRIBUTE_POSITION", "Attribut {0} kann nicht nach Kindknoten oder vor dem Erstellen eines Elements hinzugef\u00fcgt werden.  Das Attribut wird ignoriert."};
        Object[] arrobject7 = new Object[]{"ER_STRAY_NAMESPACE", "Namensbereichdeklaration ''{0}''=''{1}'' befindet sich nicht in einem Element."};
        Object[] arrobject8 = new Object[]{"ER_COULD_NOT_LOAD_RESOURCE", "''{0}'' konnte nicht geladen werden (CLASSPATH pr\u00fcfen). Es werden die Standardwerte verwendet."};
        Object[] arrobject9 = new Object[]{"ER_INVALID_PORT", "Ung\u00fcltige Portnummer"};
        Object[] arrobject10 = new Object[]{"ER_HOST_ADDRESS_NOT_WELLFORMED", "Der Host ist keine syntaktisch korrekte Adresse."};
        Object[] arrobject11 = new Object[]{"ER_SCHEME_NOT_CONFORMANT", "Das Schema ist nicht angepasst."};
        Object[] arrobject12 = new Object[]{"ER_SCHEME_FROM_NULL_STRING", "Schema kann nicht von Nullzeichenfolge festgelegt werden."};
        Object[] arrobject13 = new Object[]{"ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Der Pfad enth\u00e4lt eine ung\u00fcltige Escapezeichenfolge."};
        Object[] arrobject14 = new Object[]{"ER_PATH_INVALID_CHAR", "Pfad enth\u00e4lt ung\u00fcltiges Zeichen: {0}."};
        Object[] arrobject15 = new Object[]{"ER_FRAG_INVALID_CHAR", "Fragment enth\u00e4lt ein ung\u00fcltiges Zeichen."};
        Object[] arrobject16 = new Object[]{"ER_FRAG_WHEN_PATH_NULL", "Fragment kann nicht festgelegt werden, wenn der Pfad gleich Null ist."};
        Object[] arrobject17 = new Object[]{"ER_FRAG_FOR_GENERIC_URI", "Fragment kann nur f\u00fcr eine generische URI (Uniform Resource Identifier) festgelegt werden."};
        Object[] arrobject18 = new Object[]{"ER_NO_QUERY_STRING_IN_PATH", "Abfragezeichenfolge kann nicht im Pfad und in der Abfragezeichenfolge angegeben werden."};
        Object[] arrobject19 = new Object[]{"ER_NO_USERINFO_IF_NO_HOST", "Benutzerinformationen k\u00f6nnen nicht angegeben werden, wenn der Host nicht angegeben wurde."};
        Object[] arrobject20 = new Object[]{"ER_ENCODING_NOT_SUPPORTED", "Warnung:  Die Codierung ''{0}'' wird von Java Runtime nicht unterst\u00fctzt."};
        Object[] arrobject21 = new Object[]{"FEATURE_NOT_SUPPORTED", "Der Parameter ''{0}'' wird erkannt, der angeforderte Wert kann jedoch nicht festgelegt werden."};
        Object[] arrobject22 = new Object[]{"unsupported-encoding", "Eine nicht unterst\u00fctzte Codierung wurde festgestellt."};
        Object[] arrobject23 = new Object[]{"ER_WARNING_WF_NOT_CHECKED", "Eine Instanz des Pr\u00fcfprogramms f\u00fcr korrekte Formatierung konnte nicht erstellt werden.  F\u00fcr den korrekt formatierten Parameter wurde der Wert 'True' festgelegt, die Pr\u00fcfung auf korrekte Formatierung kann jedoch nicht durchgef\u00fchrt werden."};
        Object[] arrobject24 = new Object[]{"wf-invalid-character", "Der Knoten ''{0}'' enth\u00e4lt ung\u00fcltige XML-Zeichen."};
        Object[] arrobject25 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_PI", "In der Verarbeitungsanweisung wurde ein ung\u00fcltiges XML-Zeichen (Unicode: 0x{0}) gefunden."};
        Object[] arrobject26 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_TEXT", "Ein ung\u00fcltiges XML-Zeichen  (Unicode: 0x{0}) wurde im Inhalt der Zeichendaten des Knotens gefunden."};
        Object[] arrobject27 = new Object[]{"ER_WF_DASH_IN_COMMENT", "Die Zeichenfolge \"--\" ist innerhalb von Kommentaren nicht zul\u00e4ssig."};
        Object[] arrobject28 = new Object[]{"ER_WF_LT_IN_ATTVAL", "Der Wert des Attributs \"{1}\" mit einem Elementtyp \"{0}\" darf nicht das Zeichen ''<'' enthalten."};
        Object[] arrobject29 = new Object[]{"ER_WF_REF_TO_UNPARSED_ENT", "Der syntaktisch nicht analysierte Entit\u00e4tenverweis \"&{0};\" ist nicht zul\u00e4ssig."};
        Object[] arrobject30 = new Object[]{"ER_WF_REF_TO_EXTERNAL_ENT", "Der externe Entit\u00e4tenverweis \"&{0};\" ist in einem Attributwert nicht zul\u00e4ssig."};
        Object[] arrobject31 = new Object[]{"ER_NS_PREFIX_CANNOT_BE_BOUND", "Das Pr\u00e4fix \"{0}\" kann nicht an den Namensbereich \"{1}\" gebunden werden."};
        Object[] arrobject32 = new Object[]{"ER_NULL_LOCAL_ATTR_NAME", "Der lokale Name des Attributs \"{0}\" ist nicht angegeben."};
        Object[] arrobject33 = new Object[]{"unbound-prefix-in-entity-reference", "Der Ersatztext des Entit\u00e4tenknotens \"{0}\" enth\u00e4lt einen Attributknoten \"{1}\" mit einem nicht gebundenen Pr\u00e4fix \"{2}\"."};
        return new Object[][]{{"BAD_MSGKEY", "Der Nachrichtenschl\u00fcssel ''{0}'' ist nicht in der Nachrichtenklasse ''{1}'' enthalten."}, arrobject, {"ER_SERIALIZER_NOT_CONTENTHANDLER", "Die Parallel-Seriell-Umsetzerklasse ''{0}'' implementiert org.xml.sax.ContentHandler nicht."}, arrobject2, {"ER_RESOURCE_COULD_NOT_LOAD", "Die Ressource [ {0} ] konnte nicht geladen werden: {1} \n {2} \t {3}"}, arrobject3, arrobject4, arrobject5, arrobject6, {"ER_NAMESPACE_PREFIX", "Der Namensbereich f\u00fcr Pr\u00e4fix ''{0}'' wurde nicht deklariert."}, {"ER_STRAY_ATTRIBUTE", "Attribut ''{0}'' befindet sich nicht in einem Element."}, arrobject7, arrobject8, {"ER_ILLEGAL_CHARACTER", "Es wurde versucht, ein Zeichen des Integralwerts {0} auszugeben, der nicht in der angegebenen Ausgabeverschl\u00fcsselung von {1} dargestellt ist."}, {"ER_COULD_NOT_LOAD_METHOD_PROPERTY", "Die Merkmaldatei ''{0}'' konnte f\u00fcr die Ausgabemethode ''{1}'' nicht geladen werden (CLASSPATH pr\u00fcfen)"}, arrobject9, {"ER_PORT_WHEN_HOST_NULL", "Der Port kann nicht festgelegt werden, wenn der Host gleich Null ist."}, arrobject10, arrobject11, arrobject12, arrobject13, arrobject14, arrobject15, arrobject16, arrobject17, {"ER_NO_SCHEME_IN_URI", "Kein Schema gefunden in URI"}, {"ER_CANNOT_INIT_URI_EMPTY_PARMS", "URI (Uniform Resource Identifier) kann nicht mit leeren Parametern initialisiert werden."}, {"ER_NO_FRAGMENT_STRING_IN_PATH", "Fragment kann nicht im Pfad und im Fragment angegeben werden."}, arrobject18, {"ER_NO_PORT_IF_NO_HOST", "Der Port kann nicht angegeben werden, wenn der Host nicht angegeben wurde."}, arrobject19, {"ER_XML_VERSION_NOT_SUPPORTED", "Warnung: Die Version des Ausgabedokuments muss ''{0}'' lauten.  Diese XML-Version wird nicht unterst\u00fctzt.  Die Version des Ausgabedokuments ist ''1.0''."}, {"ER_SCHEME_REQUIRED", "Schema ist erforderlich!"}, {"ER_FACTORY_PROPERTY_MISSING", "Das an SerializerFactory \u00fcbermittelte Merkmalobjekt weist kein Merkmal ''{0}'' auf."}, arrobject20, {"FEATURE_NOT_FOUND", "Der Parameter ''{0}'' wird nicht erkannt."}, arrobject21, {"DOMSTRING_SIZE_ERR", "Die Ergebniszeichenfolge ist zu lang f\u00fcr eine DOM-Zeichenfolge: ''{0}''."}, {"TYPE_MISMATCH_ERR", "Der Werttyp f\u00fcr diesen Parameternamen ist nicht kompatibel mit dem erwarteten Werttyp."}, {"no-output-specified", "Das Ausgabeziel f\u00fcr die zu schreibenden Daten war leer."}, arrobject22, {"ER_UNABLE_TO_SERIALIZE_NODE", "Der Knoten konnte nicht serialisiert werden."}, {"cdata-sections-splitted", "Der Abschnitt CDATA enth\u00e4lt mindestens eine Beendigungsmarkierung ']]>'."}, arrobject23, arrobject24, {"ER_WF_INVALID_CHARACTER_IN_COMMENT", "Im Kommentar wurde ein ung\u00fcltiges XML-Zeichen (Unicode: 0x{0}) gefunden."}, arrobject25, {"ER_WF_INVALID_CHARACTER_IN_CDATA", "Im Inhalt von CDATASection wurde ein ung\u00fcltiges XML-Zeichen (Unicode: 0x{0}) gefunden."}, arrobject26, {"wf-invalid-character-in-node-name", "Ung\u00fcltige XML-Zeichen wurden gefunden in {0} im Knoten ''{1}''."}, arrobject27, arrobject28, arrobject29, arrobject30, arrobject31, {"ER_NULL_LOCAL_ELEMENT_NAME", "Der lokale Name von Element \"{0}\" ist nicht angegeben."}, arrobject32, {"unbound-prefix-in-entity-reference", "Der Ersatztext des Entit\u00e4tenknotens \"{0}\" enth\u00e4lt einen Elementknoten \"{1}\" mit einem nicht gebundenen Pr\u00e4fix \"{2}\"."}, arrobject33};
    }
}

