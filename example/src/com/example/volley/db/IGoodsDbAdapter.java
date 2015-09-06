package com.example.volley.db;

import java.util.ArrayList;

import com.example.volley.model.DbGoods;

public interface IGoodsDbAdapter {
	void save(DbGoods item);

	void saveList(ArrayList<DbGoods> items);

	ArrayList<DbGoods> getAll();

	ArrayList<DbGoods> getByType(int type);

	void clearAll();
}
