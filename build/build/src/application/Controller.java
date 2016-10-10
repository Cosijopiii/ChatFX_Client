package application;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Controller {
	public static final int PORT=6013;
	public static final String IP="localhost";
    @FXML
    private ListView<String> listUsuarios;
    @FXML
    private MenuItem closeSesion;
    @FXML
    private TextField txtusuarioRegistrar;
    @FXML
    private AnchorPane Login;
    @FXML
    private AnchorPane chat;
    @FXML
    private TextArea txtDatosEnviar;

    @FXML
    private TextField txtUsuarioSesion;

    @FXML
    private PasswordField txtPasswordRegistrar;

    @FXML
    private Button btnguardar;

    @FXML
    private Button btnLogin;

    @FXML
    private MenuItem mntCerrar;

    @FXML
    private Button btnEnviarDatos;

    @FXML
    private TextArea txtDatos;
    
    @FXML
    private PasswordField txtpasswordSesion;

    @FXML
    private Button btnRegistrar;

    @FXML
    private VBox vboxChat;
    private Socket client=null;
    private Alert alert=null;
    private DatosUsuario d=null;
    private ObjectOutputStream Odato=null;
    ObjectInputStream	Idato=null;
    private ObservableList<String>  observableList;
	ArrayList<String> chil=new ArrayList<>();
    public Controller(){
    	alert = new Alert(AlertType.INFORMATION);
    	alert.initStyle(StageStyle.UTILITY);
    	alert.setHeaderText(null);
    	alert.setContentText("Introdusca los datos correspondientes en las cajas de texto ");	
    	observableList = FXCollections.observableArrayList(); 	
    }
    
    public void eventocell(String datos){
    	if(datos==null){
        if(!chil.contains(listUsuarios.getSelectionModel().getSelectedItem())){
        if(listUsuarios.getSelectionModel().getSelectedItem()!=null){
        	try {
        	FXMLLoader  root = new FXMLLoader(getClass().getResource("VistaChatPrivado.fxml"));
        	Stage dialog = new Stage();
            Scene scene = new Scene(root.load());
            dialog.setTitle(d.getUsuario()+" - "+listUsuarios.getSelectionModel().getSelectedItem());
            dialog.setScene(scene);
            dialog.show();
            chil.add(listUsuarios.getSelectionModel().getSelectedItem());
            dialog.setResizable(false);
           
        	dialog.setOnCloseRequest(new EventHandler<WindowEvent>(){
        		@Override
				public void handle(WindowEvent event) {
					root.<ControllerPrivado>getController().closeSo();
					dialog.close();
					String d=listUsuarios.getSelectionModel().getSelectedItem();
					int x=chil.indexOf(d);
					if(x!=-1)
					chil.remove(x);
				}
        		
        	});
            root.<ControllerPrivado>getController().setData(listUsuarios.getSelectionModel().getSelectedItem(),d.getUsuario());

        	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        }
       }
      }else{
    	  
    	  if(!chil.contains(datos)){
    		
    	        	
    	        	Platform.runLater(new Runnable(){

						@Override
						public void run() {
							try {
							FXMLLoader  root = new FXMLLoader(getClass().getResource("VistaChatPrivado.fxml"));
		    	        	Stage dialog = new Stage();
		    	        	 Scene scene;
							
								scene = new Scene(root.load());
							
		     	            dialog.setTitle(d.getUsuario()+" - "+datos);
		     	            dialog.setScene(scene);
		     	            dialog.show();
		     	            chil.add(datos);
		     	            dialog.setResizable(false);
		     	           
		     	        	dialog.setOnCloseRequest(new EventHandler<WindowEvent>(){
		     	        		@Override
		     					public void handle(WindowEvent event) {
		     						root.<ControllerPrivado>getController().closeSo();
		     						dialog.close();
		     						String d=datos;
		     						int x=chil.indexOf(d);
		     						if(x!=-1)
		     						chil.remove(x);
		     					}
		     	        		
		     	        	});
		     	            root.<ControllerPrivado>getController().setData(datos,d.getUsuario());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
							
						});
    	        	   
    	       } 
    	  
      }
    	
    
}
    
    @FXML
    void initialize() {   	
    	listUsuarios.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	 eventocell(null);   
            }
        });
    }
    
    @FXML
    void EnviardatosUsuario(ActionEvent event) {
    	/*
    	if(IP.getText().trim().isEmpty() && usuario.getText().trim().isEmpty()){
    		alert.show();
    	}else{
    		try {
				client=new Socket(IP.getText(),PORT);
				btnguardar.setDisable(true);
				IP.setEditable(false);
				usuario.setEditable(false);
				initResivirMsg();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	*/
    }

    @FXML
    void RegistrarUsuario(ActionEvent event) {
    	if(txtusuarioRegistrar.getText().trim().isEmpty()&& txtPasswordRegistrar.getText().trim().isEmpty()){
    		alert.setContentText("Introdusca los datos correspondientes en las cajas de texto ");	
    		alert.show();
    	}else{
    		 d=new DatosUsuario(DatosUsuario.type.register,txtusuarioRegistrar.getText(),txtPasswordRegistrar.getText());
    		try {
				client =new Socket(IP,PORT);
				ObjectOutputStream ob=new ObjectOutputStream(client.getOutputStream());
				ob.writeObject(d);
				DataInputStream ib= new DataInputStream(client.getInputStream());
				
				Boolean b=ib.readBoolean();
				if(b){
					Login.setVisible(false);
					initResivirData();
		    		if(Odato==null){
		    			Odato = new  ObjectOutputStream( client.getOutputStream());
		    		}
		    		Odato.writeObject(new DataChat("",""));
		    		txtDatos.clear();
		    		txtDatos.setText("");
		    		Odato.flush();
		    		listUsuarios.setItems(observableList);
				}else{
					alert.setContentText("Usuario ya existe por favor utilise otro ");
					alert.show();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	
    }
 
    @FXML
    void IniciarSesion(ActionEvent event) {
    	
    	if(txtUsuarioSesion.getText().trim().isEmpty()&& txtpasswordSesion.getText().trim().isEmpty()){
    		alert.setContentText("Introdusca los datos correspondientes en las cajas de texto ");	
    		alert.show();
    	}else{
    		 d=new DatosUsuario(DatosUsuario.type.sessionstart,txtUsuarioSesion.getText(),txtpasswordSesion.getText());
    		try {
				client =new Socket(IP,PORT);
				ObjectOutputStream ob=new ObjectOutputStream(client.getOutputStream());
				ob.writeObject(d);
				DataInputStream ib= new DataInputStream(client.getInputStream());
				
				Boolean b=ib.readBoolean();
				if(b){
					Login.setVisible(false);
					initResivirData();
					if(Odato==null){
						Odato = new  ObjectOutputStream( client.getOutputStream());
		    		}
					
		    		Odato.writeObject(new DataChat("",""));
		    		txtDatos.clear();
		    		txtDatos.setText("");
		    		Odato.flush();
		    		listUsuarios.setItems(observableList);
				}else{
					alert.setContentText("Usuario o contraseña incorrecto verifique sus datos");
					alert.show();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }
    @FXML
    void cerrar(ActionEvent event) {
    System.exit(0);
    }

    @FXML
    void enviar(ActionEvent event)  throws IOException {
    		if(Odato==null){
    			Odato = new  ObjectOutputStream( client.getOutputStream());
    		}if(!txtDatosEnviar.getText().trim().isEmpty()){
    		Odato.writeObject(new DataChat(d.getUsuario()+": "+txtDatosEnviar.getText(),""));
    		txtDatosEnviar.setText("");
    		Odato.flush();
    		}
    }
    @FXML
    void enviarDatosEnter(KeyEvent event) throws IOException {
    	if(event.getCode()==KeyCode.ENTER){
    		if(Odato==null){
    			Odato = new  ObjectOutputStream( client.getOutputStream());
    		}
    		Odato.writeObject(new DataChat(d.getUsuario()+": "+txtDatosEnviar.getText(),""));
    		txtDatosEnviar.setText("");
    		Odato.flush();
    	}
    }	
    @FXML
    void CerrarSesion(ActionEvent event) {
    	try {
			client.close();
			Login.setVisible(true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void SelectItem(MouseEvent  event) {
    	
    	
    }	
    
    void initResivirData(){
    	Thread init =new Thread(new Runnable(){

			public void run() {
				try {
					Idato = new ObjectInputStream(client.getInputStream());
				
				while(true){
					
					DataChat da=(DataChat) Idato.readObject();
					
					int i=da.getObservableList().indexOf(d.getUsuario());
					if(i!=-1){
						da.getObservableList().remove(i);
					}
					
					observableList.setAll(da.getObservableList());
					if(!da.getMsg().isEmpty()){
						
						if(da.getMsg().equals("OpenedWindows")){
							if(!da.getEnvia().equals(d.getUsuario())){
								eventocell(da.getEnvia());
							}
						}else{
							txtDatos.appendText(da.getMsg().trim()+System.lineSeparator());	
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

}
