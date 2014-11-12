package com.emodoki.rest.api;

public interface IConstants {
	public static final String TARGET_NAMESPACE = "http://www.emodoki.nl/api/";
	public static final String DEFAULT_LOCALE = "en_US";
	public static final String DATE_FORMAT = "yyyy/MM/dd";
	public static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
	
	public interface Status {
		public static final String OK = "200";
		public static final String ERR_500 = "500"; //Internal Server
		public static final String ERR_404 = "404"; //Not Found
		public static final String ERR_403 = "403"; //Forbidden
		public static final String ERR_204 = "204"; //No Content
	}
	
	public interface ResetPassword {
		public static final int STATUS_PENDING = 1;
		public static final int STATUS_DONE = 2;
		
	}
	
}
