package com.example.volley.db.impl;

import java.util.ArrayList;

import com.example.volley.db.IGoodsDbAdapter;
import com.example.volley.db.base.BaseDBAdapterImpl;
import com.example.volley.db.base.DBConfig;
import com.example.volley.model.DbGoods;
import com.idea.xbox.component.logger.Logger;

public class GoodsDbAdapterImpl extends BaseDBAdapterImpl implements
		IGoodsDbAdapter {
	private static final String TAG = "GoodsDbAdapterImpl";

	@Override
	public void save(DbGoods item) {
		try {
			super.save(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveList(ArrayList<DbGoods> items) {
		try {
			for (DbGoods item : items) {
				super.save(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<DbGoods> getAll() {
		ArrayList<DbGoods> goods = new ArrayList<DbGoods>();
		try {
			goods = (ArrayList<DbGoods>) super.findAll(DbGoods.class);
		} catch (Exception e) {
			Logger.e(TAG, e.getMessage());
		}
		return goods;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<DbGoods> getByType(int type) {
		ArrayList<DbGoods> goods = new ArrayList<DbGoods>();
		try {
			String sql = "select * from " + DBConfig.TABLE_GOODS
					+ " where cat_id = " + type;
			goods = (ArrayList<DbGoods>) super.findResultList(sql,
					new Object[] {}, DbGoods.class);
		} catch (Exception e) {
			Logger.e(TAG, e.getMessage());
		}
		return goods;
	}

	@Override
	public void clearAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from " + DBConfig.TABLE_GOODS + " where 1=1");
		try {
			super.execute(sql.toString(), new Object[] {});
		} catch (Exception e) {
			Logger.e(TAG, e.getMessage());
		}
	}

}
