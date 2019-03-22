package cn.forever.blog.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.forever.dao.ObjectDao;

@Component("blogManageService")
public class BlogManageServiceImpl implements BlogManageService {
	@Resource(name="objectDao")
	private ObjectDao objectDao;
	
}
