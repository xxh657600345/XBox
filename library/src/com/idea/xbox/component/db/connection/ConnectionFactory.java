package com.idea.xbox.component.db.connection;

import android.content.Context;

import com.idea.xbox.component.db.connection.sqlite.SQLiteConnection;

public class ConnectionFactory {
    public static synchronized IConnection createConnection(Context context, IDatasource datasource) {
        String type = datasource.getDatabaseType();
        if (IDatasource.SQLITE_DB.equalsIgnoreCase(type)) {
            return new SQLiteConnection(context, datasource);
        }
        return null;
    }
}
