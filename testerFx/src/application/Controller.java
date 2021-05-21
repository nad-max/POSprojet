package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Administrateur;
import model.Caissier;
import application.DBConnection;


public class Controller {
	
	@FXML 
	private Label lab;
	@FXML 
	private TextField txtLogin;
	@FXML 
	private PasswordField txtPw;
	@FXML 
	private RadioButton btnAdmin;
	@FXML 
	private RadioButton btnCaissier;
	@FXML 
	private Label label;
	@FXML 
	private Button btnLogin;
	
	
	
	
	
	//pour tester la cnx avec la base
	public void DBStatus(ActionEvent e)throws IOException, SQLException {
		if (! DBConnection.getConnexion().isClosed()) {
			label.setText("Connected");
			
		}
		else {
			label.setText(" Not Connected");
		}
	}
	
	//clic sur le bouton quitter
	public void Exit (ActionEvent ev) {
		Platform.exit();
		
	}
	//clic sur le bouton se connecter
	public void Enter (ActionEvent e)throws IOException, SQLException {
		
	
     Administrateur adm = new Administrateur(txtLogin.getText(), txtPw.getText());
     //Caissier c = new Caissier(txtLogin.getText(), txtPw.getText());
     
     UsersC user= new UsersC();
     
     Administrateur admn = user.getAdmin(adm); 
     //Caissier cai = user.getCaissier(txtLogin.getText(), txtPw.getText());
     
     if(btnAdmin.isSelected()) {
    	 if(admn!=null) {
    		 if((! txtLogin.getText().isEmpty()) && (! txtPw.getText().isEmpty())) {
    			 DBConnection.getConnexion();
    			 
         	    //aller à l'interface admin 
    			 try {
 					new MainAdmin().start((Stage)btnLogin.getScene().getWindow());
 				} catch (Exception ex) {
 					// TODO Auto-generated catch block
 					ex.printStackTrace();
 				}

     			
    		 }
    		 else {
    			 Alert alert = new Alert(AlertType.WARNING);
                 alert.setTitle("Alerte");
                 alert.setHeaderText("Erreur de connection");
                 alert.setContentText("Veuillez remplir les champs !");
                 alert.show();
    			 
    		 }
    		 
    	 }
    	 else {
    		 Alert alert = new Alert(AlertType.WARNING);
    	        alert.setTitle("Alerte");
    	        alert.setHeaderText("Erreur de connection");
    	        alert.setContentText("user n'existe pas");
    	        alert.show();
    			System.out.println("user n'existe pas !");
    		 
    	 }
    	 
     }
     else if (btnCaissier.isSelected()) {
    	 
    		 if((! txtLogin.getText().isEmpty()) && (! txtPw.getText().isEmpty())) {
    			 DBConnection.getConnexion();
         	    //aller à l'interface caissier 
    			 Caissier cai = user.getCaissier(txtLogin.getText(), txtPw.getText());
    			 try {
  					new MainCashier().start((Stage)btnLogin.getScene().getWindow());
  				} catch (Exception ex) {
  					// TODO Auto-generated catch block
  					ex.printStackTrace();
  				}
     			 
    		 }
    		 else {
    			 Alert alert = new Alert(AlertType.WARNING);
                 alert.setTitle("Alerte");
                 alert.setHeaderText("Erreur de connection");
                 alert.setContentText("Veuillez remplir les champs !");
                 alert.show();
    			 
    		 }
    		 
    	 
    	 /*else {
    		 Alert alert = new Alert(AlertType.WARNING);
    	        alert.setTitle("Alerte");
    	        alert.setHeaderText("Erreur de connection");
    	        alert.setContentText("user n'existe pas");
    	        alert.show();
    			System.out.println("user n'existe pas");
    		 
    	 }*/
    	 
     }
     else {
    	 Alert alert = new Alert(AlertType.WARNING);
         alert.setTitle("Alerte");
         alert.setHeaderText("Erreur de connection");
         alert.setContentText("Veuillez cocher un bouton !");
         alert.show();
     }
    	 

}
	//for screen transaction from login to admin panel
			public class MainAdmin extends Application{

			    @Override
			    public void start(Stage primaryStage) throws Exception {
			        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));

			        
			        Scene scene = new Scene(root,1320,696);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Admin View");
					//primaryStage.sizeToScene();
					primaryStage.setResizable(false);
					//primaryStage.getIcons().add(new Image("graphic/poslogorect.png"));
					primaryStage.setMaximized(false);
					primaryStage.show();
			    }
			}
			//for screen transaction from login to cashier panel
			public class MainCashier extends Application{

			    @Override
			    public void start(Stage primaryStage) throws Exception {
			        Parent root = FXMLLoader.load(getClass().getResource("CaissierView.fxml"));

		
			    
			        
			        Scene scene = new Scene(root, 1320, 696);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Caissier view");
					//primaryStage.sizeToScene();
					primaryStage.setResizable(false);
					
					primaryStage.setMaximized(false);
					primaryStage.show();
			    }
			}
	public void openModelWindow(String resource, String title) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(resource));
			Scene fxmlFile = new Scene(root);
			Stage window = new Stage();
			window.setScene(fxmlFile);
			window.initModality(Modality.NONE);
			//window.setAlwaysOnTop(true);
			//window.setIconified(false);
			window.setTitle(title);
			window.showAndWait();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
}
}
	
	


