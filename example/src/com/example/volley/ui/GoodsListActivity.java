package com.example.volley.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;

import com.example.volley.R;
import com.example.volley.config.XMessageType;
import com.example.volley.logic.IGoodsLogic;
import com.example.volley.model.GoodsVo;
import com.example.volley.utils.ToastUtil;
import com.example.volley.utils.XListView;
import com.idea.xbox.framework.core.ui.BaseFragmentActivity;

public class GoodsListActivity extends BaseFragmentActivity implements
		XListView.IXListViewListener {
	private static final int PAGE_TYPE_CATEGORY = 7;

	private int mCategoryId = PAGE_TYPE_CATEGORY;
	private int mNowPageNum = 1;

	private String mOrderKey = "";
	private String mOrderValue = "";

	private IGoodsLogic goodsLogic;

	private XListView mListView;

	private List<GoodsVo> mList;
	private GoodsListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
		initData();
	}

	private void initView() {
		setContentView(R.layout.activity_goods_list);

		mListView = (XListView) findViewById(R.id.goods_list_lv);
	}

	private void initData() {
		mCategoryId = PAGE_TYPE_CATEGORY;

		mList = new ArrayList<GoodsVo>();
		mAdapter = new GoodsListAdapter(getBaseContext(), mList);

		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(false);
		mListView.setFooterDividersEnabled(false);
		mListView.setAdapter(mAdapter);

		refreshList();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void refreshList() {
		goodsLogic.getGoodsByCategory(mCategoryId, mNowPageNum, mOrderKey,
				mOrderValue);

	}

	private void refreshAdapters() {
		mAdapter.setList(mList);
		mAdapter.notifyDataSetChanged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleStateMessage(Message msg) {
		switch (msg.what) {
		case XMessageType.GoodsMessage.GET_GOODS_LIST_SUCCESS:
			mListView.stopRefresh(true);
			mListView.stopLoadMore();
			if (mNowPageNum == 1) {
				mList.clear();
			}
			List<GoodsVo> list = (List<GoodsVo>) msg.obj;
			if (list != null && list.size() > 0) {
				if (list.size() < 10) {
					mListView.setPullLoadEnable(false);
				} else {
					mListView.setPullLoadEnable(true);
				}
				mList.addAll(list);
				refreshAdapters();
			} else {
				mListView.setPullLoadEnable(false);
			}
			break;
		case XMessageType.GoodsMessage.GET_GOODS_LIST_FAIL:
			ToastUtil.showMessage((String) msg.obj);
			mListView.stopRefresh(false);
			mListView.stopLoadMore();
			if (mNowPageNum > 1) {
				mNowPageNum--;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh() {
		mNowPageNum = 1;
		mListView.setPullLoadEnable(true);
		refreshList();
	}

	@Override
	public void onLoadMore() {
		mNowPageNum++;
		refreshList();
	}

	public IGoodsLogic getGoodsLogic() {
		return goodsLogic;
	}

	public void setGoodsLogic(IGoodsLogic goodsLogic) {
		this.goodsLogic = goodsLogic;
	}
}
