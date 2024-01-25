package guru.maze.avatar.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.LanguageUtils;
import com.blankj.utilcode.util.SPUtils;

/**
 * @author by xiaok
 * @date 2023/10/27
 */
public class LocalLanguageUtils {

//    LanguageUtils.getAppContextLanguage().getLanguage()

    public static String getServerLanguage(){
        String language = SPUtils.getInstance().getString(Constant.LOCAL_LANGUAGE, "");
//


        if (TextUtils.isEmpty(language)) {
           return LanguageUtils.getAppContextLanguage().getLanguage();
        } else {
            if (language.equalsIgnoreCase("TW")){
               return "zh-Hant";
            }else if (language.equalsIgnoreCase("CN")){
                return "zh-Hans";
            }else {
              return LanguageUtils.getAppContextLanguage().getLanguage();
            }
        }
    }
}
