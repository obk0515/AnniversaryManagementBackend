package com.fzu.utils;

import java.util.HashMap;
import java.util.Map;

public class MsgCodeUtils {
    public static final int MSG_CODE_SUCCESS = 0;
    public static final int MSG_CODE_UNKNOWN = -10000;
    public static final int MSG_CODE_FORBIDDEN_LOGIN = -10001;
    public static final int MSG_CODE_JWT_TOKEN_ISNULL = -10002;
    public static final int MSG_CODE_JWT_SIGNATURE_EXCEPTION = -10003;
    public static final int MSG_CODE_JWT_MALFORMED = -10004;
    public static final int MSG_CODE_JWT_TOKEN_EXPIRED = -10005;
    public static final int MSG_CODE_JWT_TOKEN_UNSUPPORTED = -10006;
    public static final int MSG_CODE_JWT_ILLEGAL_ARGUMENT = -10007;
    public static final int MSG_CODE_USERNAME_OR_PASSWORD_INCORRECT = -10008;
    public static final int MSG_CODE_JWT_TOKEN_TYPE_MISMATCH = -10009;
    public static final int MSG_CODE_SYSTEM_ROLE_NAME_EXIST = -10010;
    public static final int MSG_CODE_SYSTEM_ROLE_ENNAME_EXIST = -10011;
    public static final int MSG_CODE_SYSTEM_NOT_SUPER_ADMIN_PERMISSION = -10012;
    public static final int MSG_CODE_SYSTEM_DATABASE_KEY_DUPLICATE = -10013;
    public static final int MSG_CODE_JWT_REFRESH_TOKEN_EXPIRED = -10014;
    public static final int MSG_CODE_REFRESH_TOKEN_FREQUENT = -10015;
    public static final int MSG_CODE_ILLEGAL_ARGUMENT = -10016;
    public static final int MSG_CODE_PARAMETER_IS_NULL = -10017;
    public static final int MSG_CODE_ID_IS_NULL = -10018;
    public static final int MSG_CODE_PARENT_NODE_NOT_EXIST = -10019;
    public static final int MSG_CODE_NODE_NOT_EXIST = -10020;
    public static final int MSG_CODE_NOT_PERMISSION = -10021;
    public static final int MSG_CODE_SYSTEM_USER_LOGIN_NAME_EXIST = -10022;
    public static final int MSG_CODE_SYSTEM_OFFICE_NOT_EXIST = -10023;
    public static final int MSG_CODE_DATA_NOT_EXIST = -10024;
    public static final int MSG_CODE_DATA_EXIST = -10025;
    public static final int MSG_CODE_PARENT_NODE_NOT_HIMSELF = -10026;
    public static final int MSG_CODE_FLOWINFO_STATE = -10027;
    public static final int MSG_CODE_FLOWINFO_NOT_FOUNT = -10028;
    public static final int MSC_DATA_DRODATA_ERROR = -10029;
    public static final int MSC_DATA_ADDDATA_ERROR = -10030;
    public static final int MSC_DATA_UPDATADATA_ERROR = -10031;
    public static final int MSG_DATABASE_CONNECT_ERROR = -10032;
    public static final int MSG_TIMING_SET_ERROR = -10033;
    public static final int MSG_TIMING_change_ERROR = -10034;
    public static final int MSG_SAME_NAME_ERROR = -10035;
    public static final int MSG_API_OCCUPY_ERROR = 100;
    public static final int MSG_CODE_TODAY_DAILY_HEALTH_EXIST = -10036;
    public static final int MSG_CODE_SIMPLE_TOKEN_EXPIRED = -10037;
    public static final int MSG_CODE_RUN_FAIL = -10041;
    public static final int MSG_CODE_ACCOUNT_ABANDON = -10038;
    public static final int MSG_CODE_ACCOUNT_ERROR = -10039;
    public static final int MSG_CODE_UNIT_EMPLOYEE = -10040;
    public static final int MSG_CODE_METHOD_IS_NOT_FOUND = -10042;
    public static final int MSG_CODE_ILLEGAL_ACCESS_EXCEPTION = -10043;
    public static final int MSG_CODE_INVACATION_TARGET_EXCEPTION = -10044;
    public static final int MSG_CODE_INSTANTIATION_EXCEPTION = -10045;
    public static final int MSG_CODE_DATA_ERROR = -10047;
    public static final int MSG_DATA_NOT_ALLOW_DELETE = -11048;
    public static final int MSG_DATA_NOT_ALLOW_OPERAION = -11049;
    public static final int MSG_FILE_INPUT_FAILUER = -11050;
    public static final int MSG_FILE_NOT_EXIST = -11051;
    public static final int MSG_FILE_DELETE_FAILURE = -11052;
    private static Map<Integer, String> map = new HashMap();

    public MsgCodeUtils() {
    }

    public static String getErrMsg(int msgCode) {
        return (String) map.get(msgCode);
    }

    static {
        map.put(0, "请求成功.");
        map.put(-10000, "未知错误.");
        map.put(-10001, "该帐号禁止登录.");
        map.put(-10002, "access token为空.");
        map.put(-10003, "token签名异常或者token过期.");
        map.put(-10004, "token格式错误.");
        map.put(-10005, "token过期.");
        map.put(-10006, "不支持该token.");
        map.put(-10007, "token参数错误异常.");
        map.put(-10008, "账号或者密码错误.");
        map.put(-10009, "token类型错误.");
        map.put(-10010, "角色名称已经存在.");
        map.put(-10011, "角色英文名称已经存在.");
        map.put(-10012, "越权操作，需超级管理员权限.");
        map.put(-10013, "数据库主键重复.");
        map.put(-10014, "refresh token过期.");
        map.put(-10015, "refresh token刷新频率太高，请稍后再试.");
        map.put(-10016, "非法参数异常:");
        map.put(-10017, "参数为空.");
        map.put(-10018, "参数ID为空.");
        map.put(-10019, "父级节点不存在.");
        map.put(-10020, "节点不存在.");
        map.put(-10021, "无权限.");
        map.put(-10022, "登录名已经存在.");
        map.put(-10023, "机构不存在.");
        map.put(-10024, "数据不存在：");
        map.put(-10025, "数据存在：");
        map.put(-10026, "父级节点不能是自己");
        map.put(-10029, "删除失败");
        map.put(-10030, "新增失败");
        map.put(-10031, "修改失败");
        map.put(-10032, "数据库连接失败");
        map.put(-10033, "时间调度设置失败");
        map.put(-10034, "该表非事务表，不允许更新数据");
        map.put(-10035, "名称重复，请检查参数是否设置合理");
        map.put(-10027, "流程状态不存在");
        map.put(-10028, "未找到当前运行流程信息");
        map.put(100, "当前API被占用，暂时无法进行编辑");
        map.put(-10036, "今日健康已经提交.");
        map.put(-10037, "基础token过期.");
        map.put(-10038, "该账号被禁止入园，请联系公司管理员");
        map.put(-10039, "该账户存在异常，请联系公司管理员");
        map.put(-10040, "该单位下存在员工，禁止删除");
        map.put(-10043, "非法访问异常");
        map.put(-10044, "调用目标异常");
        map.put(-10045, "实例化异常");
        map.put(-10042, "方法不存在");
        map.put(-11048, "数据不允许删除");
        map.put(-11049, "数据禁止操作");
        map.put(-11050, "文件上传失败");
        map.put(-11051, "文件不存在");
        map.put(-11052, "文件删除失败");
    }
}
