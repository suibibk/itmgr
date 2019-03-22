package cn.forever.dhp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dhp")
public class DHP  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;//自增的ID
	private String dhpId;// '原件的ID',
	private String type;// '文件类型1-文件夹 2-文件',
	private String subType;// '子类型0-私密夹 1-wjj 2-txt...',
	private String name;// '文件名称',
	private String operation;//'可操作',	
	private String path;//'文件保存的路径'
	private String supId;//'父级dhpId,第一级就叫做dhp',
	private String size;//'文件大小',
	private String userId;//'创建者ID',
	private String create_datetime;//  '创建时间',
	private String update_datetime;// '修改时间',
	private String visible;// '是否有效0无效，1有效',
	private String value;//'预留字段'
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getDhpId() {
		return dhpId;
	}

	public void setDhpId(String dhpId) {
		this.dhpId = dhpId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSupId() {
		return supId;
	}

	public void setSupId(String supId) {
		this.supId = supId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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

	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}

	public String getUpdate_datetime() {
		return update_datetime;
	}

	public void setUpdate_datetime(String update_datetime) {
		this.update_datetime = update_datetime;
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
		return "DHP [id=" + id + ", dhpId=" + dhpId + ", type=" + type
				+ ", subType=" + subType + ", name=" + name + ", operation="
				+ operation + ", path=" + path + ", supId=" + supId + ", size="
				+ size + ", userId=" + userId + ", create_datetime="
				+ create_datetime + ", update_datetime=" + update_datetime
				+ ", visible=" + visible + ", value=" + value + "]";
	}
	
}
