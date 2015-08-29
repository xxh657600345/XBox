package com.idea.xbox.component.db.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

import com.idea.xbox.common.definition.TYPE;
import com.idea.xbox.component.db.ISession;
import com.idea.xbox.component.db.connection.IConnection;
import com.idea.xbox.component.db.connection.IDatasource;
import com.idea.xbox.component.db.dao.mapping.ColumnMapping;
import com.idea.xbox.component.db.dao.mapping.TableMapping;
import com.idea.xbox.component.db.dao.sqlite.SQLiteStandardConverter;
import com.idea.xbox.component.logger.Logger;

public class DataAccessSupport implements IDataAccessSupport {
    final private static String TAG = "DataAccessSupport";

    private IConverter converter;

    private IDatasource datasource = null;

    private Map<String, IdBuilder> idBuilders = new HashMap<String, IdBuilder>();

    public IDatasource getDatasource() {
        return datasource;
    }

    public void setDatasource(IDatasource datasource) {
        this.datasource = datasource;
    }

    public IConverter getConverter() {
        return converter;
    }

    public void setConverter(IConverter converter) {
        this.converter = converter;
    }

    public void onCreated(Context context) {
        if (converter == null) {
            converter = new SQLiteStandardConverter();
        }
        converter.init(datasource);
        IConnection connection = datasource.getConnectionController().hold();
        int oldVersion = connection.getVersion();
        int newVersion = datasource.getDatabaseVersion() == 0 ? 1 : datasource.getDatabaseVersion();
        for (String className : converter.getClassNames()) {
            converter.getLoadSql(className);
            converter.getInsertSql(className);
            converter.getUpdateSql(className);
            converter.getDeleteSql(className);
            converter.getFindAllSql(className);
            if (oldVersion == 0) {
                onCreate(className, connection, newVersion);
            } else if (oldVersion < newVersion) {
                onUpgrade(className, connection, oldVersion, newVersion);
            }
            if (IConverter.GENERATOR.INCREMENT.equalsIgnoreCase(converter
                    .getGeneratorByClassName(className))) {
                createIdBuilder(className, connection);
            }
        }
        datasource.getConnectionController().free(connection);
    }

    private void createIdBuilder(String className, IConnection connection) {
        long startValue = getMaxIdValue(className, connection);
        IdBuilder idBuilder = new IdBuilder(startValue);
        idBuilders.put(className, idBuilder);
    }

    protected void onCreate(String className, IConnection connection, int newVersion) {
        String sql = converter.getCreateTableSql(className);
        connection.beginTransaction();
        connection.execute(sql);
        connection.commit();
        connection.setVersion(newVersion);
        Logger.d(TAG, "create persistence object(" + className + ")'s table by sql(" + sql + ")");
    }

    protected void onUpgrade(String className, IConnection connection, int newVersion,
            int oldVersion) {

        Logger.d(TAG, "onUpgrade");
        try {


            connection.beginTransaction();

            ArrayList<String> updateTableSqls = new ArrayList<String>();
            TableMapping tm = converter.getTableMappings().get(className);
            String tableName = tm.getTableName();

            // Create table if not exist
            StringBuffer createBuffer = new StringBuffer();
            for (ColumnMapping column : tm.getColumnMappings()) {
                createBuffer.append(",").append(column.getColumnName()).append(" ")
                        .append(column.getColumnType()).append(" ").append(column.getColumnLimit());
                if (column.isPrimaryKey()) {
                    createBuffer.append(" PRIMARY KEY");
                }
            }
            updateTableSqls.add("CREATE TABLE IF NOT EXISTS " + tableName + " ("
                    + createBuffer.toString().substring(1) + ");");
            connection.execute("CREATE TABLE IF NOT EXISTS " + tableName + " ("
                    + createBuffer.toString().substring(1) + ");");

            // Rename table.

            String tempTableName = tableName + "_temp";
            updateTableSqls.add("ALTER TABLE " + tableName + " RENAME TO " + tempTableName + ";");
            connection.execute("ALTER TABLE " + tableName + " RENAME TO " + tempTableName + ";");

            // Create table.
            updateTableSqls.add("CREATE TABLE IF NOT EXISTS " + tableName + " ("
                    + createBuffer.toString().substring(1) + ");");
            connection.execute("CREATE TABLE IF NOT EXISTS " + tableName + " ("
                    + createBuffer.toString().substring(1) + ");");

            // Load data
            Logger.d(TAG, "Load data");
            String[] oldColumns = null;
            String[] newColumns = null;
            StringBuffer columnNamesBuffer = new StringBuffer();
            Cursor c = null;
            try {
                Logger.d(TAG, "Cursor");
                c = connection.rawQuery("PRAGMA table_info(" + tempTableName + ")", null);
                if (null != c) {
                    Logger.d(TAG, "Cursor != null");
                    Logger.d(TAG, "Cursor = " + c.getCount());
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        Logger.d(TAG, "getColumnName " + c.getColumnName(i));
                    }
                    int columnIndex = c.getColumnIndex("name");
                    if (-1 == columnIndex) {
                        Logger.d(TAG, "Cursor = return");
                        return;
                    }
                    int index = 0;
                    oldColumns = new String[c.getCount()];
                    for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                        oldColumns[index] = c.getString(columnIndex);
                        index++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                c.close();
            }

            int newIndex = 0;
            newColumns = new String[tm.getColumnMappings().size()];
            for (ColumnMapping column : tm.getColumnMappings()) {
                newColumns[newIndex] = column.getColumnName();
                newIndex++;
            }

            for (int i = 0; i < oldColumns.length; i++) {
                for (int j = 0; j < newColumns.length; j++) {
                    if (oldColumns[i].equals(newColumns[j])) {
                        columnNamesBuffer.append(",").append(oldColumns[i]).append(" ");
                        break;
                    }
                }
            }
            if (columnNamesBuffer.length() == 0) {
                return;
            }
            String columns = columnNamesBuffer.toString().substring(1);
            Logger.d(TAG, " = INSERT");
            updateTableSqls.add("INSERT INTO " + tableName + " (" + columns + ") " + " SELECT "
                    + columns + " FROM " + tempTableName + ";");
            connection.execute("INSERT INTO " + tableName + " (" + columns + ") " + " SELECT "
                    + columns + " FROM " + tempTableName + ";");

            // Drop the temporary table.
            updateTableSqls.add("DROP TABLE IF EXISTS " + tempTableName + ";");
            connection.execute("DROP TABLE IF EXISTS " + tempTableName + ";");

            // commit
            connection.commit();
            connection.setVersion(newVersion);
            Logger.d(TAG, "update persistence object(" + className + ")'s table by sql("
                    + updateTableSqls + ")");
        } catch (Exception e) {
            Logger.e(TAG, e.getMessage(), e);
        }
    }

    public Object load(Class<?> clazz, Serializable id, ISession session) throws Exception {
        String className = clazz.getName();
        String sql = converter.getLoadSql(className);
        Cursor cursor = session.getConnection().query(sql, new String[] {id.toString()});
        Object object = converter.readObject(cursor, className);
        cursor.close();
        Logger.d(TAG, "load persistence object(" + className + ")'s key value = " + id + " by sql("
                + sql + ")");
        return object;
    }

    public void save(Object instance, ISession session) throws Exception {
        String className = instance.getClass().getName();
        String sql = converter.getInsertSql(className);
        IdBuilder idBuilder = idBuilders.get(className);
        Serializable id = converter.setIdValue(instance, idBuilder);
        Object[] values = converter.getInsertValues(instance);
        Logger.d(TAG, "save persistence object(" + className + ")'s key value = " + id + " by sql("
                + sql + ")");
        session.getConnection().execute(sql, values);
    }

    public void update(Object instance, ISession session) throws Exception {
        String sql = converter.getUpdateSql(instance.getClass().getName());
        Object[] values = converter.getUpdateValues(instance);
        session.getConnection().execute(sql, values);
        Logger.d(TAG, "update persistence object(" + instance.getClass().getName() + ")'s by sql("
                + sql + ")");
    }

    public void delete(Class<?> clazz, Serializable id, ISession session) {
        String className = clazz.getName();
        String sql = converter.getDeleteSql(className);
        session.getConnection().execute(sql, new Object[] {id});
        Logger.d(TAG, "delete persistence object(" + clazz.getName() + ")'s key value=" + id);
    }

    public void execute(String sql, Object[] args, ISession session) {
        session.getConnection().execute(sql, args);
        Logger.d(TAG, "execute sql(" + sql + ")");
    }

    public Object findOneResultBySQL(String sql, Object[] args, Class<?> clazz, ISession session)
            throws Exception {
        Cursor cursor = session.getConnection().query(sql, args);
        Object object = converter.readObject(cursor, clazz.getName());
        cursor.close();
        Logger.d(TAG,
                "find one result by sql(" + sql + ") for persistence object(" + clazz.getName()
                        + ")");
        return object;
    }

    public Object[] findOneResultBySQL(String sql, Object[] args, String[] labels, TYPE[] types,
            ISession session) throws Exception {
        Cursor cursor = session.getConnection().query(sql, args);
        Object[] object = converter.readOneResult(cursor, types, labels);
        cursor.close();
        Logger.d(TAG, "find one result by sql(" + sql + ") for labels");
        return object;
    }

    public List<Object[]> findResultList(String sql, Object[] args, String[] labels, TYPE[] types,
            ISession session) throws Exception {
        session.getConnection().createStatement(sql);
        Cursor cursor = session.getConnection().query(sql, args);
        List<Object[]> list = converter.readResultList(cursor, types, labels);
        cursor.close();
        Logger.d(TAG, "find result list by sql(" + sql + ") for labels");
        return list;
    }

    public List<Object[]> findResultList(String sql, int startRow, int rowNum, Object[] args,
            String[] labels, TYPE[] types, ISession session) throws Exception {
        if (rowNum > 0 && startRow >= 0) {
            sql = converter.getSubsectionQuerySQL(sql, startRow, rowNum);
        }
        Cursor cursor = session.getConnection().query(sql, args);
        List<Object[]> list = converter.readResultList(cursor, types, labels);
        cursor.close();
        Logger.d(TAG, "find result list by row and sql(" + sql + ") for labels");
        return list;
    }

    public List<Object> findResultList(String sql, int startRow, int rowNum, Object[] args,
            Class<?> clazz, ISession session) throws Exception {
        if (rowNum > 0 && startRow >= 0) {
            sql = converter.getSubsectionQuerySQL(sql, startRow, rowNum);
        }
        String className = clazz.getName();
        Cursor cursor = session.getConnection().query(sql, args);
        List<Object> list = converter.readResultList(cursor, className);
        cursor.close();
        Logger.d(TAG, "find result list by row and sql(" + sql + ") for persistence object("
                + clazz.getName() + ")");
        return list;
    }

    public List<Object> findAll(Class<?> clazz, ISession session) throws Exception {
        String className = clazz.getName();
        String sql = converter.getFindAllSql(className);
        Cursor cursor = session.getConnection().query(sql);
        List<Object> list = converter.readResultList(cursor, className);
        cursor.close();
        Logger.d(TAG, "find all by sql(" + sql + ") for persistence object(" + clazz.getName()
                + ")");
        return list;
    }

    public long getMaxIdValue(Class<?> clazz, ISession session) {
        return getMaxIdValue(clazz.getName(), session.getConnection());
    }

    private long getMaxIdValue(String className, IConnection connection) {
        String tableName = converter.getTableNameByClassName(className);
        String columnName = converter.getPrimaryKeyByClassName(className);
        StringBuilder buffer = new StringBuilder();
        buffer.append("select max(").append(columnName).append(") from ").append(tableName);
        Cursor cursor = connection.query(buffer.toString());
        long idValue = -1L;
        if (cursor.moveToFirst()) {
            idValue = cursor.getLong(0);
        }
        cursor.close();
        return idValue;
    }
}
