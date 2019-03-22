package cn.forever.blog.model;


/**
 * 这个model没有数据库对应的字段
 * 只是用来回显到页面的值的整合，需要就用这个比如
 * 主题的创建者姓名，主题的浏览量，主题所在的分类这些值
 * @author lwh
 */
public class PageValue {
	private Long id;
	private String valueA;
	private String valueB;
	private String valueC;
	private String valueD;
	private String valueE;
	private String valueF;
	private String valueG;
	private String valueH;
	private String valueI;
	private String valueJ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValueA() {
		return valueA;
	}
	public void setValueA(String valueA) {
		this.valueA = valueA;
	}
	public String getValueB() {
		return valueB;
	}
	public void setValueB(String valueB) {
		this.valueB = valueB;
	}
	public String getValueC() {
		return valueC;
	}
	public void setValueC(String valueC) {
		this.valueC = valueC;
	}
	public String getValueD() {
		return valueD;
	}
	public void setValueD(String valueD) {
		this.valueD = valueD;
	}
	public String getValueE() {
		return valueE;
	}
	public void setValueE(String valueE) {
		this.valueE = valueE;
	}
	public String getValueF() {
		return valueF;
	}
	public void setValueF(String valueF) {
		this.valueF = valueF;
	}
	public String getValueG() {
		return valueG;
	}
	public void setValueG(String valueG) {
		this.valueG = valueG;
	}
	public String getValueH() {
		return valueH;
	}
	public void setValueH(String valueH) {
		this.valueH = valueH;
	}
	public String getValueI() {
		return valueI;
	}
	public void setValueI(String valueI) {
		this.valueI = valueI;
	}
	public String getValueJ() {
		return valueJ;
	}
	public void setValueJ(String valueJ) {
		this.valueJ = valueJ;
	}
	@Override
	public String toString() {
		return "PageValue [id=" + id + ", valueA=" + valueA + ", valueB="
				+ valueB + ", valueC=" + valueC + ", valueD=" + valueD
				+ ", valueE=" + valueE + ", valueF=" + valueF + ", valueG="
				+ valueG + ", valueH=" + valueH + ", valueI=" + valueI
				+ ", valueJ=" + valueJ + "]";
	}
	 
}
