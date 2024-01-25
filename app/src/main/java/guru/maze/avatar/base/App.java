package guru.maze.avatar.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.blankj.utilcode.util.LanguageUtils;
import com.blankj.utilcode.util.SPUtils;

import java.util.Locale;

import androidx.appcompat.app.AppCompatDelegate;
import guru.maze.avatar.greendao.db.DaoMaster;
import guru.maze.avatar.greendao.db.DaoSession;
import guru.maze.avatar.model.db.GreenDaoDBUpdateOpenHelper;
import guru.maze.avatar.model.db.MigrationHelper;
import guru.maze.avatar.utils.Constant;

/**
 * @author by xiaok
 * @date 2022/10/31
 */
public class App extends Application {
    private static App ins;
    public int messageStartTime = 0;
    private DaoSession mDaoSession;
    //慎用 防止内存泄漏

    public static App getIns() {
        if (ins == null) {
            return new App();
        }
        return ins;
    }

    public App() {
        ins = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLanguage();


        initThemeMode();
        initGreenDao();


    }

    public void initLanguage() {


        String language = SPUtils.getInstance().getString(Constant.LOCAL_LANGUAGE, "");
//
//        Log.i(Constant.XIAOK, "当前语言环境：" +LanguageUtils.getAppContextLanguage().getDisplayName());
//        Log.i(Constant.XIAOK, "当前语言环境：" +LanguageUtils.getAppContextLanguage().getDisplayCountry());
        //CN //TW


        if (TextUtils.isEmpty(language)) {
            LanguageUtils.applySystemLanguage();
        } else {
            if (language.equalsIgnoreCase("TW")) {
                LanguageUtils.applyLanguage(Locale.TRADITIONAL_CHINESE);
            } else if (language.equalsIgnoreCase("CN")) {
                LanguageUtils.applyLanguage(Locale.SIMPLIFIED_CHINESE);
            } else {
                Locale locale = new Locale(language);
                LanguageUtils.applyLanguage(locale);
            }

        }


    }


    /**
     * 日夜间主题切换
     */
    private void initThemeMode() {
        boolean darkMode = SPUtils.getInstance().getBoolean(Constant.USER_DARK_MODE);
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    private void initGreenDao() {
        MigrationHelper.DEBUG = true; //如果你想查看日志信息，请将DEBUG设置为true
        GreenDaoDBUpdateOpenHelper helper = new GreenDaoDBUpdateOpenHelper(this, "user.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


}
