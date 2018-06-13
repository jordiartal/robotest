package utils;

public enum Utils {

	//related to url
    CAST_INFO_WEB("CAST-INFO-WEB"),
    WEB_TO_TEST("WEB_TO_TEST"),
	EMPRESA("/empresa/"),
	EMPLEO("/empleo/"),
	NOTICIAS("/noticias/"),
	CLIENTES("/clientes/"),
	
	//related to operations
	TEST_CREATE_COOKIE("testCreateCookie"),
	VALUE("value"),
	PRE_HOME_CFG("PRE_HOME_CFG"),
	POST_659("//*[@id=\"post-659\"]/div/div"),
	MESSAGE_ELEMENT("This element doesn't exist");
	
	private final String stringValue;

	private Utils(String stringValue) {
		this.stringValue = stringValue;
	}

	public String getStringValue() {
		return stringValue;
	}
	
	
}
