package com.example.volley.ui;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.example.volley.R;
import com.example.volley.config.XApplication;
import com.example.volley.http.base.RequestManager;
import com.example.volley.model.GoodsVo;

@SuppressLint({ "NewApi", "InflateParams" })
public class GoodsListAdapter extends BaseAdapter {

	private Context mContext;
	private List<GoodsVo> mList;
	private ImageLoader mImageLoader;

	public GoodsListAdapter(Context c, List<GoodsVo> list) {
		mContext = c;
		mImageLoader = new ImageLoader(RequestManager.getRequestQueue(),
				XApplication.getApp().getVolleyImageCache());
		mList = list;
	}

	public class BitmapCache implements ImageCache {

		private LruCache<String, Bitmap> mCache;

		public BitmapCache() {
			int maxSize = 10 * 1024 * 1024;
			mCache = new LruCache<String, Bitmap>(maxSize) {
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					return bitmap.getRowBytes() * bitmap.getHeight();
				}
			};
		}

		@Override
		public Bitmap getBitmap(String url) {
			return mCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			mCache.put(url, bitmap);
		}

	}

	public void setList(List<GoodsVo> list) {
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_goods_list, null);
			holder.iv = (NetworkImageView) convertView
					.findViewById(R.id.item_goods_list_img);
			int size = XApplication.getApp().getWindowWidth() / 2;
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					size, size);
			holder.iv.setLayoutParams(params);

			holder.title = (TextView) convertView
					.findViewById(R.id.item_goods_list_title);
			holder.package_num = (TextView) convertView
					.findViewById(R.id.item_goods_list_package_num);
			holder.sell_num = (TextView) convertView
					.findViewById(R.id.item_goods_list_sell_num);
			holder.app_price = (TextView) convertView
					.findViewById(R.id.item_goods_list_app_price);
			holder.cost_price = (TextView) convertView
					.findViewById(R.id.item_goods_list_cost_price);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv.setDefaultImageResId(R.drawable.bg_img_goods_list);
		holder.iv.setErrorImageResId(R.drawable.bg_img_goods_list);
		holder.iv.setImageUrl(mList.get(position).getGoods_img().trim(),
				mImageLoader);
		holder.title.setText(mList.get(position).getGoods_name());
		holder.sell_num.setText("已销售" + mList.get(position).getSales_count()
				+ "个");
		holder.app_price.setText(mList.get(position).getShop_price() + "");
		holder.cost_price.setText(mList.get(position).getMarket_price() + "");
		holder.package_num.setText(mList.get(position).getGoods_weight() + "g");

		return convertView;
	}

	private class ViewHolder {
		NetworkImageView iv;
		TextView title;
		TextView package_num;
		TextView sell_num;
		TextView app_price;
		TextView cost_price;
	}
}
