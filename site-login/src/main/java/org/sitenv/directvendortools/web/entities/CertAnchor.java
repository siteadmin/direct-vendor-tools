package org.sitenv.directvendortools.web.entities;


public class CertAnchor {
	
	private String certFile;
	private String uploadedTimeStamp;
	private String absolutePath;
	
	public String getCertFile() {
		return certFile;
	}
	public void setCertFile(String certFile) {
		this.certFile = certFile;
	}
	public String getUploadedTimeStamp() {
		return uploadedTimeStamp;
	}
	public void setUploadedTimeStamp(String uploadedTimeStamp) {
		this.uploadedTimeStamp = uploadedTimeStamp;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
}
