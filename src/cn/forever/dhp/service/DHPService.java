package cn.forever.dhp.service;

import java.util.List;

import cn.forever.dhp.model.DHP;

public interface DHPService {
	/**
	 * 根据上级ID 和用户ID获取蛋黄盘的内容
	 * @param supId 上级ID
	 * @param userId 用户ID
	 * @return
	 */
	public List<DHP> getDHPs(String supId,String userId);
	
	/**
	 * 根据级别ID和用户ID去获取某一原件的信息
	 * @param dhpId
	 * @param userId
	 * @return
	 */
	public DHP getDHP(String dhpId,String userId);
}
