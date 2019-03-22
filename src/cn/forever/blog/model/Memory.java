package cn.forever.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 这个表示用来放刷新到缓存的一些数据
 * 根据名称刷新到内存
 * @author lwh
 *
 */
@Entity
@Table(name = "memory")
public class Memory {
	private Long id;//ID
	private String key_name;//键
	private String sort;//排序1开始
	private String create_datetime;//创建时间
	private String update_datetime;//更新时间
	private String visible;//是否有效0无效 1有效
	private String remark;//说明备注
	private String valueA;
	private String valueB;
	private String valueC;
	private String valueD;
	private String valueE;
	private String valueF;
	private String valueG;
	private String valueH;
	private String valueI;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKey_name() {
		return key_name;
	}
	public void setKey_name(String keyName) {
		key_name = keyName;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getCreate_datetime() {
		return create_datetime;
	}
	public void setCreate_datetime(String createDatetime) {
		create_datetime = createDatetime;
	}
	public String getUpdate_datetime() {
		return update_datetime;
	}
	public void setUpdate_datetime(String updateDatetime) {
		update_datetime = updateDatetime;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Memory [create_datetime=" + create_datetime + ", id=" + id
				+ ", key_name=" + key_name + ", remark=" + remark + ", sort="
				+ sort + ", update_datetime=" + update_datetime + ", valueA="
				+ valueA + ", valueB=" + valueB + ", valueC=" + valueC
				+ ", valueD=" + valueD + ", valueE=" + valueE + ", valueF="
				+ valueF + ", valueG=" + valueG + ", valueH=" + valueH
				+ ", valueI=" + valueI + ", visible=" + visible + "]";
	}
	
}
