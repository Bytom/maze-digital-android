package guru.maze.avatar.base;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author xiaok
 * @date 2018/11/16
 */

public class BaseResponse<T>  {

    /**
     * code : 200
     * msg :
     * data : ["a8d38d07-098a-49c4-b192-1f2949da01be","e5b4e998-8593-4f5f-b03a-532291a0c96c","c530579b-c9b3-4fc6-9b4b-b1e962846bf6","fc41ff9c-24d5-4bf2-b95b-9ce87161aa2f","8951cd2b-0c62-4289-b9dd-564402b34503","eec01ae4-b4ed-4b83-ad68-c244ee02fd3c","72f0edc9-141a-4e93-941c-95b384fc6611","24a1bb33-0dfe-4410-a3e8-e23c4c8c8e21","547dc12c-cfb2-4106-a72b-021b5f5b6aa9","5ab3a6c9-75fc-4444-ab18-d768286a06fb"]
     * start : 0
     * limit : 10
     * _links : {"next":"/api/v1/btm/account/list-guids?limit=10&start=10"}
     */

    private int code;

    @SerializedName(value = "msg",alternate = "err")
    private String msg;
    @SerializedName(value = "result",alternate = "data")
    private T result;

    @SerializedName("pagination")
    private PaginationBean pagination;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }

    public static class PaginationBean{
        @SerializedName("total")
        public int total;
    }

}
