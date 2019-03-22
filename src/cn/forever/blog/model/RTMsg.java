package cn.forever.blog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rtmsg")
public class RTMsg implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;//自增的ID
	private String type1;//该记录是浏览量还是热度，1是热度，2是浏览量
	private String type2;//是主题的还是回复的，1是主题，2是回复
	private String userId;//操作ID，可有可无，只有热度会有
	private Long msgId;//对应的主题或者回复ID
	private String create_datetime;//创建时间
	private String update_datetime;//更新时间
	private String value;//预留
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "RTMsg [create_datetime=" + create_datetime + ", id=" + id
				+ ", msgId=" + msgId + ", type1=" + type1 + ", type2=" + type2
				+ ", update_datetime=" + update_datetime + ", userId=" + userId
				+ ", value=" + value + "]";
	}
	
}
