package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ControllerPrivado {
	public static final int PORT=6013;
	public static final String IP="localhost";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEnviarDatos;

    @FXML
    private TextArea txtDatos;

    @FXML
    private TextArea txtDatosEnviar;

   
    private String resive;
    private String envia;
    private Socket client;
    ObjectOutputStream Odato=null;
    ObjectInputStream	Idato=null;
    public void setData(String resive,String envia){
    	
    	this.envia=envia;
    	this.resive=resive;
    	try {
			init();
		
    	initThreadMSG();
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void closeSo(){
    	try {
			
			Odato.close();
			Idato.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
    }
    
    public void init() throws IOException{
    	client =new Socket(IP,PORT);
		ObjectOutputStream ob=new ObjectOutputStream(client.getOutputStream());		
		ob.writeObject(new DatosUsuario(DatosUsuario.type.chatPrivate,envia,""));
    }
    public void initThreadMSG() throws IOException{
    	
    	if(Odato==null){
			Odato = new  ObjectOutputStream( client.getOutputStream());
		}
		Odato.writeObject(new DataChat("",""));
		txtDatos.clear();
		txtDatos.setText("");
		Odato.flush();
    	Thread init =new Thread(new Runnable(){

			public void run() {
				try {
					Idato = new ObjectInputStream(client.getInputStream());
				while(true){
					DataChat da=(DataChat) Idato.readObject();
					if(da.getEnvia()!=null&&da.getResive()!=null){
					if((da.getEnvia().equals(resive)&&da.getResive().equals(envia))||(da.getEnvia().equals(envia)&&da.getResive().equals(resive))){
					txtDatos.appendText(da.getPrivateMsg().trim()+System.lineSeparator());	
					}
				}
				}
				} catch (IOException | ClassNotFoundException e) {
					
					e.printStackTrace();
				}
			}	
    	});
    	init.start();
    }
    @FXML
    void enviar(ActionEvent event) throws IOException {
    	if(Odato==null){
			Odato = new  ObjectOutputStream( client.getOutputStream());
		}
    	if(!txtDatosEnviar.getText().trim().isEmpty()){ 

	        DataChat dat=new DataChat("OpenedWindows","");
	        dat.setEnvia(envia);
	       	dat.setResive(resive);
	    	Odato.writeObject(dat);   		
	   		Odato.flush();
	    	DataChat dat2=new DataChat("",envia+": "+txtDatosEnviar.getText());
	    	dat2.setEnvia(envia);
	    	dat2.setResive(resive);
			Odato.writeObject(dat2);
			txtDatosEnviar.setText("");
			Odato.flush();
	    }
    }
   
    @FXML
    void initialize() {
    
    }
   
}
