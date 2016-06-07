package com.wom.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WOMDBPR.tblproductimage")
public class ProductImage implements Serializable{

	public ProductImage(){}
	public ProductImage (String productcode, String filename, String filetype, String side, 
			String filesize, String active, String exist, String series){
		this.productCode = productcode;
		this.fileName = filename;
		this.fileType = filetype;
		this.side = side;
		this.fileSize = filesize;
		this.active = active;
		this.exist = exist;
		this.series = series;
		
	}
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long id;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "FileName")
	private String fileName;
	
	@Column(name = "FileType")
	private String fileType;
	
	@Column(name = "FileSize")
	private String fileSize;
	
	@Column(name = "Side")
	private String side;
	
	@Column(name = "Active")
	private String active;
	
	@Column(name = "Exist")
	private String exist;
	
	@Column(name = "Series")
	private String series;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public String getExist() {
		return exist;
	}
	
	public void setExist(String exist) {
		this.exist = exist;
	}
	
	public String getSeries() {
		return series;
	}
	
	public void setSeries(String series) {
		this.series = series;
	}

	

}
