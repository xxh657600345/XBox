package com.idea.xbox.component.db.connection.controller;

import android.content.Context;

import com.idea.xbox.component.db.connection.IDatasource;

public class ConnectionControllerFactory {

    public static IConnectionController getConnectionController(Context context,
            IDatasource datasource) throws Exception {
        IConnectionController connectionController = null;
        int connectionSize = datasource.getConnectionSize();
        switch (connectionSize) {
            case 0:
                connectionController = new SimpleConnectionController(context, datasource);
                break;
            case 1:
                connectionController = new SingletonConnectionController(context, datasource);
                break;
            default:
                if (connectionSize > 1) {
                    connectionController = new ConnectionPoolController(context, datasource);
                    break;
                }
        }
        return connectionController;
    }

}
