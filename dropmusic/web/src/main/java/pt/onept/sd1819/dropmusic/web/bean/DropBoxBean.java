package pt.onept.sd1819.dropmusic.web.bean;

import pt.onept.sd1819.rest.dropbox.DropBoxRestService;

public class DropBoxBean {
	private DropBoxRestService dropBoxRestService;
	String callBack = "https://onept.pt:8443/dropmusic/CallBackServlet";
	String userAccessToken = "aU9jFrT5TPcAAAAAAAAIfU16hAcUqC5StEbGvaKeM2mzJ_K090B1_lAqpwFl6F3g";
	String userDbId = "dbid:AACtXPVN9YbNLwgtB3yIfd61H9O1NVvAjIw";

	public DropBoxBean() {
		this.dropBoxRestService = new DropBoxRestService(callBack);
	}

	public String getAuthUrl() {
		return this.dropBoxRestService.getAuthorizationUrl();
	}

	public DropBoxRestService getDropBoxRestService() {
		return dropBoxRestService;
	}
}
