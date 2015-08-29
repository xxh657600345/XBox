package com.idea.xbox.component.db.connection.controller;

import com.idea.xbox.component.db.connection.IConnection;

public interface IConnectionController {
    public IConnection hold();

    public void free(IConnection connection);
}
