package com.idea.xbox.component.db;

import com.idea.xbox.component.db.connection.IConnection;

public class Session implements ISession {
	private IConnection connection = null;

	public Session(IConnection connection) {
		this.connection = connection;
	}

	public IConnection getConnection() {
		return connection;
	}

	public void close() {
		if (connection != null) {
			connection.close();
		}
	}

}
