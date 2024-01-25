package guru.maze.avatar.model.inter;

/**
 * Created by xiaok on 2018/11/6.
 * 用于网络请求结束之后数据的回调
 */

public class EventResponse {

    public String msg;
    public int code;

    public EventResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
