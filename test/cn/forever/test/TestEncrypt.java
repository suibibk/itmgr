package cn.forever.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class TestEncrypt {
	public static String encrypt(String msg){
		try {
			byte[] srcBytes1=msg.trim().getBytes();
			String base641 = new BASE64Encoder().encode(srcBytes1);
			System.out.println("base641:"+base641);
			String encoder1 = URLEncoder.encode(base641, "UTF-8");
			System.out.println("encoder1:"+encoder1);
			String md51 = md5(encoder1);
			System.out.println("md51:"+md51);
			String encoder2 = URLEncoder.encode(md51, "UTF-8");
			System.out.println("encoder2:"+encoder2);
			byte[] srcBytes2=encoder2.getBytes();
			String base642 = new BASE64Encoder().encode(srcBytes2);
			System.out.println("base642:"+base642);
			String md52 = md5(base642);
			System.out.println("password:"+md52);
			return md52;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	private static String  md5(String msg){
		try {
			//根据MD5算法生成MessageDigest对象
			MessageDigest sha=MessageDigest.getInstance("MD5");
			byte[] srcBytes=msg.trim().getBytes();
			//使用srcBytes更新摘要
			sha.update(srcBytes);
			//完成hash计算，得到result
			byte[] resultBytes=sha.digest();
			return new String(resultBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
