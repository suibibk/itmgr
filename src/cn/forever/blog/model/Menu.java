package cn.forever.blog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;//自增的ID
	private String name;//菜单名称
	private String remark;//菜单备注
	private String create_datetime;//创建时间
	private String update_datetime;//修改时间
	private String userId;//创建者ID
	private String imgUrl;//菜单图片
	private String sort;//菜单排序，从1开始
	private String type;//菜单类别1是以及菜单，2是二级菜单
	private Long menuId;//上一级菜单
	private String visible;//是否有效0无效，1有效
	private String value;//预留字段
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Menu [create_datetime=" + create_datetime + ", id=" + id
				+ ", imgUrl=" + imgUrl + ", menuId=" + menuId + ", name="
				+ name + ", remark=" + remark + ", sort=" + sort + ", type="
				+ type + ", update_datetime=" + update_datetime + ", userId="
				+ userId + ", value=" + value + ", visible=" + visible + "]";
	}
	
}
