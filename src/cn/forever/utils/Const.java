package cn.forever.utils;
/**
 * 常量Memory中valueA是功能锁的类型 valueB是是否开始0未开始1开始
 * 下面的常量都是valueA,key_name:
 * FUNCTION_LOCK:是功能锁
 * @author forever
 *
 */
public class Const {
	public static final String REDIS_STATUS = "00";//REDIS的开启状态 01开启
	public static final String PAGE_NUM = "10";//获取的主题数目
	public static final String HOT_NUM = "10";//默认获取的热门主题数
	public static final String MENUS_KEY="MENUS";//menus在redis中的key
	public static final String QUICK_WEB="QUICK_WEB";//跨速通道在redis中的key
	public static final String DAN_HUANG="/uploadFile/dhp/";//蛋黄盘的访问路径
	
	/****************sftp传输基本信息********************/
	public static final String SFTP_USERNAME="file";//用户名
	public static final String SFTP_PASSWORD="LWHMHM@1208";//用户密码
	public static final String SFTP_BASEPATH="/home/file";//根目录
	public static final String SFTP_IP="120.78.199.154";//ip
	public static final Integer SFTP_PORT=22;//端口
	
	/******************蛋黄盘的一些标数定义***********************/
	public static final String DHP_TYPE_WJJ="1";//文件夹
	public static final String DHP_TYPE_WJ="2";//文件
	public static final String DHP_SUBTYPE_PRIVATE="0";//私密文件夹，这个暂时用不到
	
	public static final String DHP_SUBTYPE_WJJ="1";//普通文件夹
	public static final String DHP_SUBTYPE_TXT="2";//TXT文本
	public static final String DHP_SUBTYPE_IMG="3";//图片
	public static final String DHP_SUBTYPE_VIDEO="4";//视频
	public static final String DHP_SUBTYPE_MUSIC="5";//音乐
	public static final String DHP_SUBTYPE_WORD="6";//word
	public static final String DHP_SUBTYPE_PDF="7";//pdf
	public static final String DHP_SUBTYPE_CODE="8";//代码，js jsp ,java
	public static final String DHP_SUBTYPE_HTML="9";//html
	public static final String DHP_SUBTYPE_ZIP="10";//压缩包
	public static final String DHP_SUBTYPE_EXE="11";//exe可执行文件
	public static final String DHP_SUBTYPE_OTHER="0";//其他都用这个
	
	public static final String DHP_OPERATION_DOWN="0";//下载 2,3,4,5,6,7,8,9,10,11,0
	public static final String DHP_OPERATION_DELETE="1";//删除1,2,3,4,5,6,7,8,9,10,11,0
	public static final String DHP_OPERATION_CHECK="2";//查看 2,3,6,7,8,9
	public static final String DHP_OPERATION_VIDEO="3";//观看4
	public static final String DHP_OPERATION_LISTEN="4";//听 5
}
