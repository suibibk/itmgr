package cn.forever.test;

public class TestStringFromSplit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a = "aaa#bbb#ccc#";
		String b = "aaa##ccc#";
		String c = "##ccc#";
		String d = "aaa###";
		String e = "###";
		
		System.out.println(a.split("#").length);
		for (int i = 0; i < a.split("#").length; i++) {
			System.out.println(a.split("#")[i]);
		}
		System.out.println("--------------------");
		System.out.println(b.split("#").length);
		for (int i = 0; i < b.split("#").length; i++) {
			System.out.println(b.split("#")[i]);
		}
		System.out.println("--------------------");
		System.out.println(c.split("#").length);
		for (int i = 0; i < c.split("#").length; i++) {
			System.out.println(c.split("#")[i].equals(""));
			
		}
		System.out.println("--------------------");
		System.out.println(d.split("#").length);
		for (int i = 0; i < d.split("#").length; i++) {
			System.out.println(d.split("#")[i]);
		}
		System.out.println("--------------------");
		System.out.println(e.split("#").length);
		for (int i = 0; i < e.split("#").length; i++) {
			System.out.println(e.split("#")[i]);
		}
		System.out.println("a".split("#"));
		String[] aaa = "".split("#");
		System.out.println(aaa.length);
		for (int i = 0; i < aaa.length; i++) {
			System.out.println(aaa[i]);
		}
		String fileFileName = "shfgewjfkewhfjk.jpg";
		System.out.println(fileFileName.substring(0,fileFileName.lastIndexOf(".")));
	}

}
