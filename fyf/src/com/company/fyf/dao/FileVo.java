package com.company.fyf.dao;

public class FileVo {
	
	/*
	 * onSuccess : jsonStr = {"result":{"filelist":[{"aid":21,"filename":"1447856210460.jpg","filesize":9369,"filepath":"http:\/\/api.zzpinna.com\/uploadfile\/2015\/1118\/20151118101336932.jpg"}],"upload_error":""},"success":1}
	 */
	private long aid;
	private String filename;
	private long filesize;
	private String filepath;
	public long getAid() {
		return aid;
	}
	public void setAid(long aid) {
		this.aid = aid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	

}
