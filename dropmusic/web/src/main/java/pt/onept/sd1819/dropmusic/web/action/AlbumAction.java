package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.interceptor.SessionAware;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Album;

import java.util.Map;

public class AlbumAction extends ActionSupport implements SessionAware, ModelDriven<Album> {
	private Album album = new Album();
	private Map<String, Object> session;

	public String create() throws Exception {
		return Action.SUCCESS;
	}

	public String read() throws Exception {
		return Action.SUCCESS;
	}

	public String update() throws Exception {
		return Action.SUCCESS;
	}

	public String delete() throws Exception {
		return Action.SUCCESS;
	}

	@Override
	public Album getModel() {
		return this.album;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}
}
