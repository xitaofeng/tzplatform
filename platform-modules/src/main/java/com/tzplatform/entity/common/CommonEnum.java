package com.tzplatform.entity.common;

public class CommonEnum {

    public enum SEX {
        // 性别枚举类 male男 female女
        男("0"), 女("1");
        private String value;

        public String getValue() {
            return value;
        }

        SEX(String value) {
            this.value = value;
        }
    }

    public enum USER_TYPE {
        系统用户("0"), 厂商用户("1");
        private String value;

        public String getValue() {
            return value;
        }

        USER_TYPE(String value) {
            this.value = value;
        }
    }

    public enum HEAD_PARAM {
        TOKEN("TZToken"), ACCOUNT("TZ-Account"), REQUESTSOURCE("RequestSource");
        private String value;

        public String getValue() {
            return value;
        }

        HEAD_PARAM(String value) {
            this.value = value;
        }
    }

    public enum USER_STATUS {
        已注册未激活("0"), 已激活未审核("1"), 启用("2"), 停用("3");
        private String value;

        public String getValue() {
            return value;
        }

        USER_STATUS(String value) {
            this.value = value;
        }
    }

    public enum FREE_STATUS {
        收费("Y"), 免费("N");
        private String value;

        public String getValue() {
            return value;
        }

        FREE_STATUS(String value) {
            this.value = value;
        }
    }

    public enum APP_STATUS {
        初审("1"), 已审核("2"), 已下架("3");
        private String value;

        public String getValue() {
            return value;
        }

        APP_STATUS(String value) {
            this.value = value;
        }
    }

    public enum APP_SCOPE {
        区级("1"), 校级("2");
        private String value;

        public String getValue() {
            return value;
        }

        APP_SCOPE(String value) {
            this.value = value;
        }
    }

    public enum APP_DEFAULT {
        默认应用("1"), 非默认应用("0");
        private String value;

        public String getValue() {
            return value;
        }

        APP_DEFAULT(String value) {
            this.value = value;
        }
    }

    public enum FREE_USE {
        收费可适用("Y"), 收费不可适用("N");
        private String value;

        public String getValue() {
            return value;
        }

        FREE_USE(String value) {
            this.value = value;
        }
    }

    public enum APP_COMMENT {
        好评("1"), 中评("2"), 差评("3");
        private String value;

        public String getValue() {
            return value;
        }

        APP_COMMENT(String value) {
            this.value = value;
        }
    }

    public enum APP_ACCESSTOKEN {
        TOKEN("appAccessToken");
        private String value;

        public String getValue() {
            return value;
        }

        APP_ACCESSTOKEN(String value) {
            this.value = value;
        }
    }

    public enum APP_USECOUNT {
        应用名称("appid");
        private String value;

        public String getValue() {
            return value;
        }

        APP_USECOUNT(String value) {
            this.value = value;
        }
    }

    public enum ROLE_CODE {
        超级管理员("sadmin"), 区管理员("qdmin"), 校管理员("xgly"), 学生("stu");
        private String value;

        public String getValue() {
            return value;
        }

        ROLE_CODE(String value) {
            this.value = value;
        }
    }

    public enum IMAGT_TYPE {
        通知公告("notice"), 失物招领("lostfound"), 校风光("campus"), 海报("poster"), 意见反馈("feedback");

        private String value;

        public String getValue() {
            return value;
        }

        IMAGT_TYPE(String value) {
            this.value = value;
        }
    }

    public enum APPROVAL_STATUS {
        未审核("1"), 审核通过("2"), 拒绝("3");
        private String value;

        public String getValue() {
            return value;
        }

        APPROVAL_STATUS(String value) {
            this.value = value;
        }
    }

    public enum MOBILE_STATUS {
        未上架("1"), 上架("2");
        private String value;

        public String getValue() {
            return value;
        }

        MOBILE_STATUS(String value) {
            this.value = value;
        }
    }

    public enum HANDLE_REUSLT {
        // 用户操作结果
        success("0"), failed("1");
        private String value;

        public String getValue() {
            return value;
        }

        HANDLE_REUSLT(String value) {
            this.value = value;
        }
    }

    public enum REQUEST_ACCOUNT {
        用户账号("account");
        private String value;

        public String getValue() {
            return value;
        }

        REQUEST_ACCOUNT(String value) {
            this.value = value;
        }
    }

}
