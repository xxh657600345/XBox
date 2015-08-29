package com.idea.xbox.component.db.connection.controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.content.Context;

import com.idea.xbox.component.db.connection.ConnectionFactory;
import com.idea.xbox.component.db.connection.IConnection;
import com.idea.xbox.component.db.connection.IDatasource;

public class ConnectionPoolController implements IConnectionController {
    private BlockingQueue<IConnection> queues = null;

    public ConnectionPoolController(Context context, IDatasource datasource) throws Exception {
        String type = datasource.getDatabaseType();
        if ("sqlite".equalsIgnoreCase(type)) {
            throw new Exception("not support sqlite database");
        }
        int connectionSize = datasource.getConnectionSize();
        queues = new ArrayBlockingQueue<IConnection>(connectionSize);
        for (int i = 0; i < connectionSize; i++) {
            IConnection connection = ConnectionFactory.createConnection(context, datasource);
            queues.add(connection);
        }
    }

    public IConnection hold() {
        try {
            return queues.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void free(IConnection connection) {
        try {
            queues.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
