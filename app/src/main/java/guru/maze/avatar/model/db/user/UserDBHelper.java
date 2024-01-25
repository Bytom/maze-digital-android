package guru.maze.avatar.model.db.user;

import com.blankj.utilcode.util.SPUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import guru.maze.avatar.base.App;
import guru.maze.avatar.greendao.db.UserDBManagerDao;
import guru.maze.avatar.utils.Constant;

/**
 * @author by xiaok
 * @date 2022/11/11
 */
public class UserDBHelper {


    private static class Holder {
        private static UserDBHelper INSTANCE = new UserDBHelper();
    }

    public static UserDBHelper getIns() {
        return Holder.INSTANCE;
    }

    public void addOrUpdateAsset(UserDBManager userDBManager) {
        UserDBManagerDao dao = App.getIns().getDaoSession().getUserDBManagerDao();
        QueryBuilder<UserDBManager> qb = dao.queryBuilder();

        UserDBManager unique = qb.where(UserDBManagerDao.Properties.AccessToken.eq(userDBManager.getAccessToken())).unique();
        if (unique == null) {
            userDBManager.setId(0L);
            dao.insert(userDBManager);
        } else {
            dao.deleteAll();
            userDBManager.setId(0L);
            dao.insert(userDBManager);
        }
    }

    //TODO想办法设置之举只有一条
    public UserDBManager queryUserData() {
        UserDBManagerDao userDBManagerDao = App.getIns().getDaoSession().getUserDBManagerDao();
        if (userDBManagerDao==null)return null;//我登录
        return userDBManagerDao.load(0L);
    }


    //TODO想办法设置之举只有一条
    public int queryUserID() {

        if (queryUserData()==null)return 0;
        return queryUserData().getUserID();
    }

    public void loginOutAccount(){
        SPUtils.getInstance().put(Constant.ACCESS_TOKEN,"");
        UserDBManagerDao dao = App.getIns().getDaoSession().getUserDBManagerDao();
        dao.deleteAll();
    }

}
