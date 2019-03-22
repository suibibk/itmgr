package cn.forever.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestCalendar {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		//输出星期六
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
		System.out.println(df.format(calendar1.getTime()));
		//输出星期天
		Calendar calendar2 = Calendar.getInstance();
		//calendar2.add(Calendar.DATE, -7);上一个星期天
		calendar2.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		System.out.println(df.format(calendar2.getTime()));
		//时间的比较 
		Date date = new Date();
		String dateStr = "20170607080808";
		Date strDate = df.parse(dateStr);
		System.out.println(strDate);
		System.out.println(date.before(strDate));
		 SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		  SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
		//输出星期六
		System.out.println(format1.format(format2.parse(dateStr)));
		
		System.out.println("---------------------------------------");
		//测试天数一天天增加
		Calendar calendar3 = Calendar.getInstance();
		//for (int i = 0; i < 30; i++) {
			//calendar3.add(Calendar.DAY_OF_YEAR, +1);  	
			//System.out.println(format1.format(calendar3.getTime()));
		//}
		System.out.println("-----------------------------------------");
		String aa = "1996-08-09 09:89:99";
		String aaa = aa.substring(0,10);
		System.out.println(aaa);
		String aaaa = aaa+" 00:00:00";
		System.out.println(aaaa);
		Date date3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(aaaa);
		System.out.println(date3);
		for (int i = 0; i < 30; i++) {
			calendar3.setTime(date3);
			calendar3.add(Calendar.DAY_OF_YEAR, +1);  	
			System.out.println(format1.format(calendar3.getTime()));
		}
		
	}

}
