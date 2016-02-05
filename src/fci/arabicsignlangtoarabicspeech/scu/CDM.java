package fci.arabicsignlangtoarabicspeech.scu;

public class CDM {
	public static class Ocr {
		public static final String APPLICATION_ID = "OCR for deaf and dumb";
		public static final String PASSWORD = "BXWwg5JrU3+GAKweN3FD7lnU";
	}

	public static class Connection {
		public static String HOST_ADDRESS = "192.168.43.155";
		public static final String HOST_PORT = ":7101";
	}

	public static class URLs {
		public static final String LOGIN = "http://"
				+ Connection.HOST_ADDRESS
				+ ":7101/GraduationProjectServer-Project1-context-root/resources/user/login";
		public static final String REGISTER = "http://"
				+ Connection.HOST_ADDRESS
				+ ":7101/GraduationProjectServer-Project1-context-root/resources/user/register";

		public static final String MERGE = "http://{Connection.HOST_ADDRESS}:7101/GraduationProjectServer-Project1-context-root/resources/merge/video?text=";
	}

	public static class KEYs {
		public static final String USER_NAME = "userName";
		public static final String PASSWORD = "password";
		public static final String CITY = "city";
		public static final String COUNTRY = "country";
		public static final String DATE_OF_BIRTH = "dateOfBirth";
		public static final String EMAIL = "email";
		public static final String FIRST_NAME = "firstName";
		public static final String LAST_NAME = "lastName";
	}
}
