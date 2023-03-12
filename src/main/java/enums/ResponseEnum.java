package enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    SUCCESS(200, "成功"),

    FAIL(1, "失败"),

    ILLEGAL_PARAM(101, "参数错误"),
    TEL_OR_PWD_ERROR(102, "手机号或密码错误"),
    VERIFY_MSG_CODE_INVALID(103, "验证码已失效，请重试"),
    VERIFY_MSG_CODE_VALID(104,"验证码已发送，请查收"),
    USER_NOT_FOUND(105, "未找到对应的用户"),
    USERINFO_NOT_FOUND(106,"未找到对应的用户信息"),
    OBJECT_PUBLISHER_NOT_FOUND(107,"未找到该用户发布的相应信息"),
    OBJECT_RELATIVE_TEXT_NOT_FOUND(108,"未找到相关内容的信息"),
    OBJECT_IN_ONE_WEEK_NOT_FOUND(109,"未找到近一周的信息"),
    SWIPER_NEWS_NOT_FOUND(110,"未找到该新闻对应的轮播图信息"),
    SWIPER_PRIORITY_NOT_FOUND(111,"未找到该优先级对应的轮播图信息"),
    ACTIVITY_NEWS_ACTIVITY_NOT_FOUND(112,"未找到该活动对应的活动新闻信息"),
    COMMENT_RESPONSE_COMMENT_NOT_FOUND(113,"未找到该普通评论对应的评论回复信息"),
    COMMENT_RESPONSE_VIDEO_COMMENT_NOT_FOUND(114,"未找到该视频评论对应的评论回复信息"),
    OPERATE_DATABASE_FAIL(115,"数据库操作失败"),
    TEL_HAS_BEEN_USED(116,"该手机号已注册"),
    VERIFY_MSG_CODE_ERROR(117,"验证码错误"),
    USER_ERROR_FREQUENCY_LIMIT(118,"用户尝试次数已达上限，请5分钟后再试"),
    UPLOAD_OSS_FAILURE(119,"文件上传OSS失败"),
    SIGNED_ACTIVITY_NOT_FOUND(120,"未找到已报名活动"),
    NO_ACTIVITY_FOCUS(121,"无关注活动"),
    INSUFFICIENT_CREDIT(122,"积分不足"),
    NUM_NOT_ENOUGH(123,"库存不足"),
    OBJECT_NOT_FOUND(124,"商品不存在"),

    PURCHASE_ORDER_NOT_FOUND(301, "未找到对应的采购计划"),

    SERVER_ERROR(500, "服务器内部错误")
    ;

    private int code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
