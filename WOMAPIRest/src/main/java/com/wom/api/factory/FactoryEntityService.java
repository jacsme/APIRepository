package com.wom.api.factory;

import java.util.List;

import org.hibernate.Session;

import com.wom.api.constant.MainEnum;

public interface FactoryEntityService<H> {
	public H getEntity(MainEnum mainenum, String parameter, Session session) throws Exception;
	public H getEntity(MainEnum mainenum, String parameter1, String parameter2, Session session) throws Exception;
	public List<H> getEntityList(MainEnum mainenum, String parameter, Session session) throws Exception;
	public List<H> getEntityList(MainEnum mainenum, String parameter1, String parameter2, Session session) throws Exception;
	public H getEntity(MainEnum mainenum, String parameter1, String parameter2, String parameter3, String parameter4, Session session)
			throws Exception;
	public H getEntity(MainEnum mainenum, String parameter1, String parameter2, String parameter3, Session session) throws Exception;
	public List<H> getEntityList(MainEnum mainenum, String parameter1, String parameter2, String parameter3, Session session)
			throws Exception;
}
