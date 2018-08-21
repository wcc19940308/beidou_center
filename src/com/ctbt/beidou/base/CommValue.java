package com.ctbt.beidou.base;

public class CommValue {

	/**数据发送的时间间隔 毫秒**/
	public static int SEND_INTERVAL = 1000;
	
	/**中心平台的卡号**/
	public static int CENTER_CARD = 999999;
	
	/**数据接收的时间间隔 毫秒**/
	public static int RECV_INTERVAL = 1000;
	
	/**中心平台编号**/
	public static String CenterNo = "";

	/**聊天信息，信息类型，文本**/
	public static String BdMsgChat_MsgType_TEXT = "1";
	/**聊天信息，信息类型，图片**/
	public static String BdMsgChat_MsgType_IMG = "2";
	/**聊天信息，信息类型，音频**/
	public static String BdMsgChat_MsgType_AUDIO = "3";
	/**聊天信息，信息类型，视频**/
	public static String BdMsgChat_MsgType_VIDEO = "4";
	

	/**session中存放用户信息的key**/
	public static final String SESSION_USER = "loginUser";

	/**session中存放图片验证码的key**/
	public static final String SESSION_RANDOM_CODE_MAP_KEY = "sRandomCodeMapKey";

	/**session中存放临时操作码的key**/
	public static final String SESSION_TEMP_OP_CODE_MAP_KEY = "sTempOpCodeMapKey";

	public static final String CHARSET = "utf-8";
	/** 文件上传等操作时，操作的临时码 提交时的参数名 **/
	public static final String TEMP_OP_CODE = "TempOpCode";
	/** YES/NO **/
	public static final String YES = "1";

	public static final String NO = "0";

	/**默认的每页记录数**/
	public static int DEFAULT_PAGE_SIZE = 10;
}
