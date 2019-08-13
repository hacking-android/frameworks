/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.sql.Wrapper;

public interface DatabaseMetaData
extends Wrapper {
    public static final short attributeNoNulls = 0;
    public static final short attributeNullable = 1;
    public static final short attributeNullableUnknown = 2;
    public static final int bestRowNotPseudo = 1;
    public static final int bestRowPseudo = 2;
    public static final int bestRowSession = 2;
    public static final int bestRowTemporary = 0;
    public static final int bestRowTransaction = 1;
    public static final int bestRowUnknown = 0;
    public static final int columnNoNulls = 0;
    public static final int columnNullable = 1;
    public static final int columnNullableUnknown = 2;
    public static final int functionColumnIn = 1;
    public static final int functionColumnInOut = 2;
    public static final int functionColumnOut = 3;
    public static final int functionColumnResult = 5;
    public static final int functionColumnUnknown = 0;
    public static final int functionNoNulls = 0;
    public static final int functionNoTable = 1;
    public static final int functionNullable = 1;
    public static final int functionNullableUnknown = 2;
    public static final int functionResultUnknown = 0;
    public static final int functionReturn = 4;
    public static final int functionReturnsTable = 2;
    public static final int importedKeyCascade = 0;
    public static final int importedKeyInitiallyDeferred = 5;
    public static final int importedKeyInitiallyImmediate = 6;
    public static final int importedKeyNoAction = 3;
    public static final int importedKeyNotDeferrable = 7;
    public static final int importedKeyRestrict = 1;
    public static final int importedKeySetDefault = 4;
    public static final int importedKeySetNull = 2;
    public static final int procedureColumnIn = 1;
    public static final int procedureColumnInOut = 2;
    public static final int procedureColumnOut = 4;
    public static final int procedureColumnResult = 3;
    public static final int procedureColumnReturn = 5;
    public static final int procedureColumnUnknown = 0;
    public static final int procedureNoNulls = 0;
    public static final int procedureNoResult = 1;
    public static final int procedureNullable = 1;
    public static final int procedureNullableUnknown = 2;
    public static final int procedureResultUnknown = 0;
    public static final int procedureReturnsResult = 2;
    public static final int sqlStateSQL = 2;
    public static final int sqlStateSQL99 = 2;
    public static final int sqlStateXOpen = 1;
    public static final short tableIndexClustered = 1;
    public static final short tableIndexHashed = 2;
    public static final short tableIndexOther = 3;
    public static final short tableIndexStatistic = 0;
    public static final int typeNoNulls = 0;
    public static final int typeNullable = 1;
    public static final int typeNullableUnknown = 2;
    public static final int typePredBasic = 2;
    public static final int typePredChar = 1;
    public static final int typePredNone = 0;
    public static final int typeSearchable = 3;
    public static final int versionColumnNotPseudo = 1;
    public static final int versionColumnPseudo = 2;
    public static final int versionColumnUnknown = 0;

    public boolean allProceduresAreCallable() throws SQLException;

    public boolean allTablesAreSelectable() throws SQLException;

    public boolean autoCommitFailureClosesAllResultSets() throws SQLException;

    public boolean dataDefinitionCausesTransactionCommit() throws SQLException;

    public boolean dataDefinitionIgnoredInTransactions() throws SQLException;

    public boolean deletesAreDetected(int var1) throws SQLException;

    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException;

    public ResultSet getAttributes(String var1, String var2, String var3, String var4) throws SQLException;

    public ResultSet getBestRowIdentifier(String var1, String var2, String var3, int var4, boolean var5) throws SQLException;

    public String getCatalogSeparator() throws SQLException;

    public String getCatalogTerm() throws SQLException;

    public ResultSet getCatalogs() throws SQLException;

    public ResultSet getClientInfoProperties() throws SQLException;

    public ResultSet getColumnPrivileges(String var1, String var2, String var3, String var4) throws SQLException;

    public ResultSet getColumns(String var1, String var2, String var3, String var4) throws SQLException;

    public Connection getConnection() throws SQLException;

    public ResultSet getCrossReference(String var1, String var2, String var3, String var4, String var5, String var6) throws SQLException;

    public int getDatabaseMajorVersion() throws SQLException;

    public int getDatabaseMinorVersion() throws SQLException;

    public String getDatabaseProductName() throws SQLException;

    public String getDatabaseProductVersion() throws SQLException;

    public int getDefaultTransactionIsolation() throws SQLException;

    public int getDriverMajorVersion();

    public int getDriverMinorVersion();

    public String getDriverName() throws SQLException;

    public String getDriverVersion() throws SQLException;

    public ResultSet getExportedKeys(String var1, String var2, String var3) throws SQLException;

    public String getExtraNameCharacters() throws SQLException;

    public ResultSet getFunctionColumns(String var1, String var2, String var3, String var4) throws SQLException;

    public ResultSet getFunctions(String var1, String var2, String var3) throws SQLException;

    public String getIdentifierQuoteString() throws SQLException;

    public ResultSet getImportedKeys(String var1, String var2, String var3) throws SQLException;

    public ResultSet getIndexInfo(String var1, String var2, String var3, boolean var4, boolean var5) throws SQLException;

    public int getJDBCMajorVersion() throws SQLException;

    public int getJDBCMinorVersion() throws SQLException;

    public int getMaxBinaryLiteralLength() throws SQLException;

    public int getMaxCatalogNameLength() throws SQLException;

    public int getMaxCharLiteralLength() throws SQLException;

    public int getMaxColumnNameLength() throws SQLException;

    public int getMaxColumnsInGroupBy() throws SQLException;

    public int getMaxColumnsInIndex() throws SQLException;

    public int getMaxColumnsInOrderBy() throws SQLException;

    public int getMaxColumnsInSelect() throws SQLException;

    public int getMaxColumnsInTable() throws SQLException;

    public int getMaxConnections() throws SQLException;

    public int getMaxCursorNameLength() throws SQLException;

    public int getMaxIndexLength() throws SQLException;

    public int getMaxProcedureNameLength() throws SQLException;

    public int getMaxRowSize() throws SQLException;

    public int getMaxSchemaNameLength() throws SQLException;

    public int getMaxStatementLength() throws SQLException;

    public int getMaxStatements() throws SQLException;

    public int getMaxTableNameLength() throws SQLException;

    public int getMaxTablesInSelect() throws SQLException;

    public int getMaxUserNameLength() throws SQLException;

    public String getNumericFunctions() throws SQLException;

    public ResultSet getPrimaryKeys(String var1, String var2, String var3) throws SQLException;

    public ResultSet getProcedureColumns(String var1, String var2, String var3, String var4) throws SQLException;

    public String getProcedureTerm() throws SQLException;

    public ResultSet getProcedures(String var1, String var2, String var3) throws SQLException;

    public int getResultSetHoldability() throws SQLException;

    public RowIdLifetime getRowIdLifetime() throws SQLException;

    public String getSQLKeywords() throws SQLException;

    public int getSQLStateType() throws SQLException;

    public String getSchemaTerm() throws SQLException;

    public ResultSet getSchemas() throws SQLException;

    public ResultSet getSchemas(String var1, String var2) throws SQLException;

    public String getSearchStringEscape() throws SQLException;

    public String getStringFunctions() throws SQLException;

    public ResultSet getSuperTables(String var1, String var2, String var3) throws SQLException;

    public ResultSet getSuperTypes(String var1, String var2, String var3) throws SQLException;

    public String getSystemFunctions() throws SQLException;

    public ResultSet getTablePrivileges(String var1, String var2, String var3) throws SQLException;

    public ResultSet getTableTypes() throws SQLException;

    public ResultSet getTables(String var1, String var2, String var3, String[] var4) throws SQLException;

    public String getTimeDateFunctions() throws SQLException;

    public ResultSet getTypeInfo() throws SQLException;

    public ResultSet getUDTs(String var1, String var2, String var3, int[] var4) throws SQLException;

    public String getURL() throws SQLException;

    public String getUserName() throws SQLException;

    public ResultSet getVersionColumns(String var1, String var2, String var3) throws SQLException;

    public boolean insertsAreDetected(int var1) throws SQLException;

    public boolean isCatalogAtStart() throws SQLException;

    public boolean isReadOnly() throws SQLException;

    public boolean locatorsUpdateCopy() throws SQLException;

    public boolean nullPlusNonNullIsNull() throws SQLException;

    public boolean nullsAreSortedAtEnd() throws SQLException;

    public boolean nullsAreSortedAtStart() throws SQLException;

    public boolean nullsAreSortedHigh() throws SQLException;

    public boolean nullsAreSortedLow() throws SQLException;

    public boolean othersDeletesAreVisible(int var1) throws SQLException;

    public boolean othersInsertsAreVisible(int var1) throws SQLException;

    public boolean othersUpdatesAreVisible(int var1) throws SQLException;

    public boolean ownDeletesAreVisible(int var1) throws SQLException;

    public boolean ownInsertsAreVisible(int var1) throws SQLException;

    public boolean ownUpdatesAreVisible(int var1) throws SQLException;

    public boolean storesLowerCaseIdentifiers() throws SQLException;

    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException;

    public boolean storesMixedCaseIdentifiers() throws SQLException;

    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException;

    public boolean storesUpperCaseIdentifiers() throws SQLException;

    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException;

    public boolean supportsANSI92EntryLevelSQL() throws SQLException;

    public boolean supportsANSI92FullSQL() throws SQLException;

    public boolean supportsANSI92IntermediateSQL() throws SQLException;

    public boolean supportsAlterTableWithAddColumn() throws SQLException;

    public boolean supportsAlterTableWithDropColumn() throws SQLException;

    public boolean supportsBatchUpdates() throws SQLException;

    public boolean supportsCatalogsInDataManipulation() throws SQLException;

    public boolean supportsCatalogsInIndexDefinitions() throws SQLException;

    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException;

    public boolean supportsCatalogsInProcedureCalls() throws SQLException;

    public boolean supportsCatalogsInTableDefinitions() throws SQLException;

    public boolean supportsColumnAliasing() throws SQLException;

    public boolean supportsConvert() throws SQLException;

    public boolean supportsConvert(int var1, int var2) throws SQLException;

    public boolean supportsCoreSQLGrammar() throws SQLException;

    public boolean supportsCorrelatedSubqueries() throws SQLException;

    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException;

    public boolean supportsDataManipulationTransactionsOnly() throws SQLException;

    public boolean supportsDifferentTableCorrelationNames() throws SQLException;

    public boolean supportsExpressionsInOrderBy() throws SQLException;

    public boolean supportsExtendedSQLGrammar() throws SQLException;

    public boolean supportsFullOuterJoins() throws SQLException;

    public boolean supportsGetGeneratedKeys() throws SQLException;

    public boolean supportsGroupBy() throws SQLException;

    public boolean supportsGroupByBeyondSelect() throws SQLException;

    public boolean supportsGroupByUnrelated() throws SQLException;

    public boolean supportsIntegrityEnhancementFacility() throws SQLException;

    public boolean supportsLikeEscapeClause() throws SQLException;

    public boolean supportsLimitedOuterJoins() throws SQLException;

    public boolean supportsMinimumSQLGrammar() throws SQLException;

    public boolean supportsMixedCaseIdentifiers() throws SQLException;

    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException;

    public boolean supportsMultipleOpenResults() throws SQLException;

    public boolean supportsMultipleResultSets() throws SQLException;

    public boolean supportsMultipleTransactions() throws SQLException;

    public boolean supportsNamedParameters() throws SQLException;

    public boolean supportsNonNullableColumns() throws SQLException;

    public boolean supportsOpenCursorsAcrossCommit() throws SQLException;

    public boolean supportsOpenCursorsAcrossRollback() throws SQLException;

    public boolean supportsOpenStatementsAcrossCommit() throws SQLException;

    public boolean supportsOpenStatementsAcrossRollback() throws SQLException;

    public boolean supportsOrderByUnrelated() throws SQLException;

    public boolean supportsOuterJoins() throws SQLException;

    public boolean supportsPositionedDelete() throws SQLException;

    public boolean supportsPositionedUpdate() throws SQLException;

    public boolean supportsResultSetConcurrency(int var1, int var2) throws SQLException;

    public boolean supportsResultSetHoldability(int var1) throws SQLException;

    public boolean supportsResultSetType(int var1) throws SQLException;

    public boolean supportsSavepoints() throws SQLException;

    public boolean supportsSchemasInDataManipulation() throws SQLException;

    public boolean supportsSchemasInIndexDefinitions() throws SQLException;

    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException;

    public boolean supportsSchemasInProcedureCalls() throws SQLException;

    public boolean supportsSchemasInTableDefinitions() throws SQLException;

    public boolean supportsSelectForUpdate() throws SQLException;

    public boolean supportsStatementPooling() throws SQLException;

    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException;

    public boolean supportsStoredProcedures() throws SQLException;

    public boolean supportsSubqueriesInComparisons() throws SQLException;

    public boolean supportsSubqueriesInExists() throws SQLException;

    public boolean supportsSubqueriesInIns() throws SQLException;

    public boolean supportsSubqueriesInQuantifieds() throws SQLException;

    public boolean supportsTableCorrelationNames() throws SQLException;

    public boolean supportsTransactionIsolationLevel(int var1) throws SQLException;

    public boolean supportsTransactions() throws SQLException;

    public boolean supportsUnion() throws SQLException;

    public boolean supportsUnionAll() throws SQLException;

    public boolean updatesAreDetected(int var1) throws SQLException;

    public boolean usesLocalFilePerTable() throws SQLException;

    public boolean usesLocalFiles() throws SQLException;
}

