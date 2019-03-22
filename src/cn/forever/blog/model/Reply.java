package cn.forever.blog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reply")
public class Reply implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;//自增的ID
	private String content;//回复的内容
	private String userId;//回复者id
	private String create_datetime;//回复时间
	private String update_datetime;//更新时间
	private String visible ;//是否显示0不显示，1显示
	private Long topicId;//回复的主题ID
	private Long toUserId;//回复谁有就显示，没有就不显示
	private Long hot;//热度
	private String value;//预留值
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public Long getToUserId() {
		return toUserId;
	}
	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
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
		return "Reply [content=" + content + ", create_datetime="
				+ create_datetime + ", hot=" + hot + ", id=" + id
				+ ", toUserId=" + toUserId + ", topicId=" + topicId
				+ ", update_datetime=" + update_datetime + ", userId=" + userId
				+ ", value=" + value + ", visible=" + visible + "]";
	}
	
	
}
