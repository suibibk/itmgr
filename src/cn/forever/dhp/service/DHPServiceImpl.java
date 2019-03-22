package cn.forever.dhp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.forever.dao.ObjectDao;
import cn.forever.dhp.model.DHP;

@Component("dhpService")
@Transactional
public class DHPServiceImpl implements DHPService {
	@Resource(name="objectDao")
	private ObjectDao objectDao;
	@Override
	public List<DHP> getDHPs(String supId, String userId) {
		String hql = "from DHP d where d.supId=:supId and d.userId=:userId and d.visible='1' order by d.type+0,d.create_datetime";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("supId", supId);
		@SuppressWarnings("unchecked")
		List<DHP> dhps = objectDao.findByHqlAndMap(hql,map);
		return dhps;
	}
	
	@Override
	public DHP getDHP(String dhpId, String userId) {
		String hql = "from DHP d where d.dhpId=:dhpId and d.userId=:userId and d.visible='1'";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("dhpId", dhpId);
		DHP dhp = (DHP) objectDao.findObjectByHqlAndMap(hql,map);
		return dhp;
	}
	
}
