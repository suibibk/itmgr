package cn.forever.blog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "topic")
public class Topic implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;//自增的ID
	private String title;//标题
	private String content;//内容
	private String imgUrl;//主题首图路径
	private String create_datetime;//发布时间
	private String update_datetime;//跟新时间
	private Long menuId;//主题所在菜单
	private String userId ;//主题的创建者
	private Long hot ;//热度
	private String visible ;//是否有效0无效，1有效
	private String value ;//预留
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public Long getHot() {
		return hot;
	}
	public void setHot(Long hot) {
		this.hot = hot;
	}
	@Override
	public String toString() {
		return "Topic [content=" + content + ", create_datetime="
				+ create_datetime + ", hot=" + hot + ", id=" + id + ", imgUrl="
				+ imgUrl + ", menuId=" + menuId + ", title=" + title
				+ ", update_datetime=" + update_datetime + ", userId=" + userId
				+ ", value=" + value + ", visible=" + visible + "]";
	}
	
}
