package enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    SUCCESS(200, "成功"),

    FAIL(1, "失败");

    private int code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
