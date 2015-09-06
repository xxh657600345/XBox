package com.example.volley.db.base;

import com.idea.xbox.component.db.adapter.DbAdapter;

public class BaseDBAdapterImpl extends DbAdapter {
	//
	// protected void saveUserBaseInfo(UserInfo userInfo) {
	// StringBuilder sql = new StringBuilder();
	// sql.append("select * from " + DBConfig.UserInfoTableName +
	// " where user_id = ?");
	// DBUserInfo saved = null;
	//
	// try {
	// saved =
	// (DBUserInfo) findOneResultBySQL(sql.toString(),
	// new Object[] {userInfo.getUserId()}, DBUserInfo.class);
	// } catch (Exception e) {
	// Logger.e(TAG, e.getMessage());
	// }
	// try {
	// if (saved == null) {
	// DBUserInfo dbUserInfo = new DBUserInfo(userInfo);
	// save(dbUserInfo);
	// } else {
	// StringBuilder updateSql = new StringBuilder();
	// updateSql.append("update " + DBConfig.UserInfoTableName
	// + " SET nick_name = ?,icon_url = ? where user_id = ?");
	// super.execute(
	// updateSql.toString(),
	// new Object[] {userInfo.getNickName(), userInfo.getIconUrl(),
	// saved.get_user_id()});
	// }
	// } catch (Exception e) {
	// Logger.e(TAG, e.getMessage());
	// }
	// }
	//
	// protected UserInfo getUserInfoWithOwner(long userId, long ownerId) {
	// UserInfo userInfo = null;
	// StringBuilder sql = new StringBuilder();
	// sql.append("select * from " + DBConfig.UserInfoTableName +
	// " where user_id = ?");
	// DBUserInfo saved = null;
	// try {
	// saved =
	// (DBUserInfo) findOneResultBySQL(sql.toString(), new Object[] {userId},
	// DBUserInfo.class);
	// } catch (Exception e) {
	// Logger.e(TAG, e.getMessage());
	// }
	//
	// if (saved != null) {
	// userInfo = new UserInfo(saved);
	// StringBuilder friendSql = new StringBuilder();
	// friendSql.append("select * from " + DBConfig.FriendShipTableName
	// + " where friend_user_id = ? and owner_id = ?");
	// DBFriendShip friendShip = null;
	// try {
	// friendShip =
	// (DBFriendShip) findOneResultBySQL(friendSql.toString(), new Object[] {
	// userId, ownerId}, DBFriendShip.class);
	// } catch (Exception e) {
	// Logger.e(TAG, e.getMessage());
	// }
	// if (friendShip != null) {
	// userInfo.setFriendId(friendShip.get_friend_id());
	// userInfo.setFriendStatus(friendShip.get_status());
	// userInfo.setAlias(friendShip.get_alias());
	// }
	// }
	// return userInfo;
	// }
}
