package com.tzplatform.entity.common;

import java.io.Serializable;

/**
 * 返回信息码状态集合
 *
 * @author leijie
 */
public class ResultMessage implements Serializable {

    private static final long serialVersionUID = -1589770643922776949L;

    public static String SUCCESS_CODE = "1";

    public static String FAILED_CODE = "-1";

    public static String FAILED_VERIFY_TOKEN = "-2";

    public static String SUCCESS_MESSAGE = "成功";

    public static String FAILED_MESSAGE = "失败";

    public static String SUCCESS_UPDATE_MESSAGE = "更新成功";

    public static String SUCCESS_INSERT_MESSAGE = "添加成功";

    public static String SUCCESS_DELETE_MESSAGE = "删除成功";

    public static String SUCCESS_LOGIN_MESSAGE = "登录成功";

    public static String SUCCESS_TOKEN_VERIFY_MESSAGE = "TOKEN校验成功";

    public static String SUCCESS_SYNC_MESSAGE = "同步成功";

    public static String FAILED_UPDATE_MESSAGE = "更新失败";

    public static String FAILED_INSERT_MESSAGE = "添加失败";

    public static String FAILED_DELETE_MESSAGE = "删除失败";

    public static String FAILED_UPLOAD_MESSAGE = "导入失败";

    public static String FAILED_EMAIL_NOT_EMPTY = "注册邮箱不能为空";

    public static String FAILED_LOGIN_NULL_MESSAGE = "用户名和密码不能为空";

    public static String FAILED_LOGIN_NOTUSER_MESSAGE = "用户不存在";

    public static String FAILED_LOGIN_PWDERROR_MESSAGE = "密码错误";

    public static String FAILED_LOGIN_PWDUSERERROR_MESSAGE = "用户名或密码有误";

    public static String FAILED_LOGIN_TOKENERROR_MESSAGE = "网络异常";

    public static String FAILED_TOKEN_VERIFY_MESSAGE = "TOKEN校验失败,account或token为空";

    public static String FAILED_TOKEN_EXPIRE_MESSAGE = "TOKEN已失效";

    public static String FAILED_TICKET_EXPIRE_MESSAGE = "ticket已失效";

    public static String FAILED_TOKEN_NOTMATCH_MESSAGE = "TOKEN匹配失败";

    public static String FAILED_USERNOT_ROLE_MESSAGE ="用户未授权,禁止登录";

    public static String FAILED_USER_OLDPASSWORD_MESSAGE="失败,原密码输入有误";

    public static String FAILED_APP_ACCESSTOKENIP="ip不在范围内";

    public static String FAILED_APP_NOACCESSTOKEN="应用请求无token";

    public static String FAILED_APP_ERRORACCESSTOKEN="应用请求token无效";

    public static String FAILED_REGISTER_MESSAGE = "该邮箱已经注册过";

    public static String FAILED_NO_REGISTER_MESSAGE = "该邮箱还未进行注册";

    public static String FAILED_USER_COLLECT_MESSAGE = "该用户已经添加/收藏过该应用";

    public static String FAILED_VALIDATECODE_NOTUSER_MESSAGE = "验证码已失效";

    public static String FAILED_REGISTER_AREADY_MESSAGE = "该邮箱已经激活过";

    public static String FAILED_REGISTER_EXPIRETIME_MESSAGE = "激活邮件已经超时";

    public static String FAILED_WEBAPPKEY_NULL_MESSAGE = "key值不能为空";

    public static String FAILED_WEBAPPSECRET_NULL_MESSAGE = "找不到对应的secret";

    public static String FAILED_WEBAPPIP_NULL_MESSAGE = "找不到应用ip";

    public static String CHECKED_CODE = "3";

    public static String CHECKED_CODE_MESSAGE= "编码重复";

    public static String FAILED_PARAMS_MESSAGE= "参数不正确";

    public static String FAILED_THIRD_LOGIN_WEIXIN_NOTEXIST_MESSAGE = "微信帐号参数不能为空";

    public static String FAILED_THIRD_LOGIN_ACCOUNTNOTEXIST_MESSAGE = "账户不存在";

    public static String FAILED_THIRD_LOGIN_QQ_NOTEXIST_MESSAGE = "QQ帐号参数不能为空";

    public static String FAILED_THIRD_LOGIN_WEIBO_NOTEXIST_MESSAGE = "微博帐号参数不能为空";

    public static String FAILED_EDITPWD_NOTNULL_MESSAGE = "修改密码参数不能为空";

    public static String FAILED_EDITPWD_OLDPWDERROR_MESSAGE = "原密码不正确";
}
