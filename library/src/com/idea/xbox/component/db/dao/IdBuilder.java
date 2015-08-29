package com.idea.xbox.component.db.dao;

public class IdBuilder {
	private long idValue;

	public IdBuilder(long startValue) {
		idValue = startValue;
	}

	public long nextValue() {
		return ++idValue;
	}

}
