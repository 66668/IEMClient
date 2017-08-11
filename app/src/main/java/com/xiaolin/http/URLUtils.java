package com.xiaolin.http;

/**
 *  接口路径类
 */

public class URLUtils {
    //URL根目录
    //    public static final String API_BASE_URL = "http://192.168.1.245:1132/openapi/";//局域网测试
    //    public final static String API_BASE_URL = "http://101.201.72.112:7016/openapi/";//阿里云测试
    public final static String API_BASE_URL = "https://iemapi.yuevision.com/openapi/";//阿里云正式

    /**
     * 登录
     */
    public static final String LOGIN = "User/AppPersonDeviceLoginByPassword";

    /**
     * 访客
     */

    public static final String VISITOR = "Main/GetVisitorRecordsByPage";
    /**
     * 添加访客
     */
    public static final String ADD_VISITOR = "Main/AddOneVisitorRecord";

    /**
     * 添加访客
     */
    public static final String CHANGE_VISITOR_STATE = "Main/ConfirmVistorRecordTime";
    /**
     * 地图考勤
     */
    public static final String LOCATION = "Attend/AddOneMapAttendanceRecord";


    /**
     * 修改密码
     */
    public static final String CHANGEPS = "User/ChangePasswordN";

    /**
     * 退出
     */
    public static final String QUIT = "User/AppDeviceLogoutByPassword";

    /**
     * 获取月考勤状态接口
     */
    public static final String MONTHSTATUS = "Employee/GetAttendStatusByMonth";
    /**
     * 获取考勤月记录接口
     */
    public static final String MONTH_ALL = "Employee/GetAttendListByMonth";

    /**
     * 获取考勤月记录接口
     */
    public static final String MONTH_STATE_ALL = "Employee/GetAttendStatusByMonthC";
    /**
     * 获取考勤月记录接口 以此获取三个月的记录
     */
    public static final String MONTH_STATE_THREE = "Employee/GetAttendStatusByMonthCThree";

    /**
     * 获取考勤日记录接口
     */
    public static final String DAYOFMONTH = "Employee/GetAttendStatusByDayC";


    /**
     * 检查更新
     */
    public static final String CHECK_UPDATE = "Main/CheckVersionP/1001";//1001表示android
}
