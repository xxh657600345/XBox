package com.idea.xbox.component.db;

import com.idea.xbox.component.db.connection.IConnection;

public interface ISession {
	public IConnection getConnection();

	public void close();
}
