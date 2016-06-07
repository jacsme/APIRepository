package com.wom.api.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.QueriesString;
import com.wom.api.constant.StatusCode;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.ProductSupplier;
import com.wom.api.model.Supplier;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class SupplierDaoImpl implements SupplierDao{

	FactoryEntityService<Supplier> factoryentityService = new FactoryEntityServiceImpl<Supplier>();
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(SupplierDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getSupplierList(String productcode) throws Exception {
		JSONArray supplierlistarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> supplierlist = session.createSQLQuery(QueriesString.supplierListQuery 
					+ QueriesString.supplierListWhere + QueriesString.supplierlistGroupby)
					.setString("productcode", HelperUtil.checkNullString(productcode))
					.list();
			if(supplierlist.size() != 0){
				supplierlistarray = ResultGeneratorUtil.populateresults(supplierlist, MainEnum.SUPPLIERLIST);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				supplierlistarray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			supplierlistarray.put(exceptionobj);
		}finally{
			HibernateUtil.callClose(session);
		}
		return supplierlistarray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getSupplierListAll() throws Exception {
		JSONArray supplierlistarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> supplierlist = session.createSQLQuery(QueriesString.searchsupplierQuery 
					+ QueriesString.searchsupplierGroupby)
					.list();
			if(supplierlist.size() != 0){
				supplierlistarray = ResultGeneratorUtil.populateresults(supplierlist, MainEnum.SUPPLIERLIST);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				supplierlistarray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			supplierlistarray.put(exceptionobj);
		}finally{
			HibernateUtil.callClose(session);
		}
		return supplierlistarray;
	}
	
	@Override
	public JSONArray submitSupplier(String suppliername, String address, String phone, String fax, String website, 
			String email, String contactperson, String gstid, String contactpersonphone, String productcode)
			throws Exception {
		
		JSONArray supplierArray = new JSONArray();
		JSONObject supplierObj = new JSONObject();
		Map<String, String> mapsupplierList = new HashMap<String, String>();
		List<ProductSupplier> productsupplierlist = new ArrayList<ProductSupplier>();
		
		String straddress = null;
		String strphone = null;
		String strfax = null;
		String strwebsite = null;
		 
		String stremail = null;
		String strcontactperson = null;
		String strgstid = null;
		String strcontactpersonphone = null;
		String suppliercode = null;
		Session session = sessionFactory.openSession();
		try {
			Supplier supplier = factoryentityService.getEntity(MainEnum.SUPPLIER, suppliername, address, 
					gstid, contactpersonphone, session);
			if(supplier!=null){
				if(!address.equalsIgnoreCase(supplier.getAddress())){straddress = address;}else{straddress = supplier.getAddress();}	
				if(!phone.equalsIgnoreCase(supplier.getPhone())){strphone = phone;}else{strphone = supplier.getPhone();}
				if(!fax.equalsIgnoreCase(supplier.getFax())){strfax = fax;}else{strfax = supplier.getFax();}
				if(!website.equalsIgnoreCase(supplier.getWebsite())){strwebsite = website;}else{strwebsite = supplier.getWebsite();}
				if(!email.equalsIgnoreCase(supplier.getEmail())){stremail = email;}else{stremail = supplier.getEmail();}
				if(!gstid.equalsIgnoreCase(supplier.getgSTId())){strgstid = gstid;}else{strgstid = supplier.getgSTId();}
				if(!contactperson.equalsIgnoreCase(supplier.getContactPerson())){strcontactperson = contactperson;}else{strcontactperson = supplier.getContactPerson();}
				if(!contactpersonphone.equalsIgnoreCase(supplier.getContactPersonPhone())){strcontactpersonphone = contactpersonphone;}else{strcontactpersonphone = supplier.getContactPersonPhone();}
				
				suppliercode = supplier.getSupplierCode();
				
				mapsupplierList.put("SupplierCode", supplier.getSupplierCode());
				mapsupplierList.put("SupplierName", supplier.getSupplierName());
				mapsupplierList.put("Address", straddress);
				mapsupplierList.put("Phone", strphone);
				mapsupplierList.put("Fax", strfax);
				mapsupplierList.put("Website", strwebsite);
				mapsupplierList.put("ContactPerson", strcontactperson);
				mapsupplierList.put("GSTId", strgstid);
				mapsupplierList.put("Email", stremail);
				mapsupplierList.put("ContactPersonPhone", strcontactpersonphone);
				mapsupplierList.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				//mapsupplierList.put("SupplierCode", suppliercode);
				
				supplier.setSupplierCode(suppliercode);
				supplier.setSupplierName(suppliername);
				supplier.setAddress(straddress);
				supplier.setPhone(phone);
				supplier.setFax(strfax);
				supplier.setWebsite(strwebsite);
				supplier.setContactPerson(strcontactperson);
				supplier.setgSTId(strgstid);
				supplier.setEmail(stremail);
				supplier.setContactPersonPhone(strcontactpersonphone);
				
				if(!productcode.equalsIgnoreCase("0")){
					ProductSupplier productsupplier = new ProductSupplier();
					productsupplier.setProductCode(productcode);
					productsupplier.setSupplierCode(suppliercode);
					productsupplierlist.add(productsupplier);
					
					supplier.setProductSupplier(productsupplierlist);
				}
				session.update(supplier);
				supplierArray.put(mapsupplierList);
			}else{
				suppliercode = ResultGeneratorUtil.codeGenerator("","sq_supplier_code", "SU22", session);
				BigInteger supplierid = ResultGeneratorUtil.idGenerator("", "sq_supplier_id", session);
				supplier  = new Supplier(supplierid, suppliercode, suppliername, address, phone, fax, website, 
						email, contactperson, gstid, contactpersonphone);
				
				if(!productcode.equalsIgnoreCase("0")){
					ProductSupplier productsupplier = new ProductSupplier();
					productsupplier.setSupplierCode(suppliercode);
					productsupplier.setProductCode(productcode);
					productsupplierlist.add(productsupplier);
					
					supplier.setProductSupplier(productsupplierlist);
				}
				session.save(supplier);
				supplierObj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				supplierObj.put("SupplierCode", suppliercode);
				
				supplierArray.put(supplierObj);
			}	
			
			HibernateUtil.callCommitClose(session);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			supplierArray.put(exceptionobj);
		}
		return supplierArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray searchSupplier(String keyword) throws Exception {
		JSONArray searchSupplierListArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> searchSupplierList = session.createSQLQuery(QueriesString.searchsupplierQuery 
					+ QueriesString.searchsupplierWhere)
					.setString("keysuppliername", HelperUtil.checkNullString(keyword))
					.list();
			if (searchSupplierList.size() != 0){
				searchSupplierListArray = ResultGeneratorUtil.populateresults(searchSupplierList, MainEnum.SUPPLIERLIST);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				searchSupplierListArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			searchSupplierListArray.put(exceptionobj);
		}finally{
			HibernateUtil.callClose(session);
		}
		return searchSupplierListArray;
	}
}
