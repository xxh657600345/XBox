package com.idea.xbox.component.db.connection.sqlite;

import android.database.sqlite.SQLiteStatement;

import com.idea.xbox.component.db.connection.IPrepareStatement;

public class SQLitePrepareStatement implements IPrepareStatement {
    private SQLiteStatement sqliteStatement;

    public SQLitePrepareStatement(SQLiteStatement sqliteStatement) {
        this.sqliteStatement = sqliteStatement;
    }

    public long executeInsert() {
        return sqliteStatement.executeInsert();
    }

    public void executeUpdate() {
        sqliteStatement.execute();
    }

    public void setLong(int index, long value) {
        sqliteStatement.bindLong(index, value);
    }

    public void setBlob(int index, byte[] value) {
        sqliteStatement.bindBlob(index, value);
    }

    public void setDouble(int index, double value) {
        sqliteStatement.bindDouble(index, value);
    }

    public void setNull(int index) {
        sqliteStatement.bindNull(index);
    }

    public void setString(int index, String value) {
        sqliteStatement.bindString(index, value);
    }

}
