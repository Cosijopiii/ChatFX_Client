package application;

import java.io.Serializable;
import java.util.ArrayList;

public class DataChat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5196371672419125258L;
	
	private String msg;
	private String privateMsg;
	private ArrayList<String> observableList=new ArrayList<>();
	private String envia;
	private String resive;
	
	

	/**
	 * @param msg
	 * @param privateMsg
	 */
	public DataChat(String msg, String privateMsg) {
		super();
		this.msg = msg;
		this.privateMsg = privateMsg;
	}

	/**
	 * @param msg
	 * @param privateMsg
	 * @param observableList
	 */
	public DataChat(String msg, String privateMsg, ArrayList<String> observableList) {
		super();
		this.msg = msg;
		this.privateMsg = privateMsg;
		this.observableList = observableList;
	}

	/**
	 * @param observableList
	 */
	public DataChat(ArrayList<String> observableList) {
		super();
		this.observableList = observableList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPrivateMsg() {
		return privateMsg;
	}

	public void setPrivateMsg(String privateMsg) {
		this.privateMsg = privateMsg;
	}

	public ArrayList<String> getObservableList() {
		return observableList;
	}

	public void setObservableList(ArrayList<String> observableList) {
		this.observableList = observableList;
	}

	public String getEnvia() {
		return envia;
	}

	public void setEnvia(String envia) {
		this.envia = envia;
	}

	public String getResive() {
		return resive;
	}

	public void setResive(String resive) {
		this.resive = resive;
	}
	
	
	
}
