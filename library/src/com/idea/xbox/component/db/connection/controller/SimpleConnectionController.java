package com.idea.xbox.component.db.connection.controller;

import android.content.Context;

import com.idea.xbox.component.db.connection.ConnectionFactory;
import com.idea.xbox.component.db.connection.IConnection;
import com.idea.xbox.component.db.connection.IDatasource;

public class SimpleConnectionController implements IConnectionController {
    private IDatasource datasource = null;

    private Context context = null;

    public SimpleConnectionController(Context context, IDatasource datasource) {
        this.datasource = datasource;
        this.context = context;
    }

    public IConnection hold() {
        return ConnectionFactory.createConnection(context, datasource);
    }

    public void free(IConnection connection) {
        connection.close();
    }

}
