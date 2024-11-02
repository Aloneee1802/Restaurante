package utils;

public class ApiResponse {

    private String msg;
    private Object data;

    public ApiResponse() {
        this.msg = "OK";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
