package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Controller.MainAdmin;
import application.Controller.MainCashier;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MyController {
	
	@FXML 
	private TextField txtLogin;
	@FXML 
	private PasswordField txtPw;
	@FXML 
	private RadioButton btnAdmin;
	@FXML 
	private RadioButton btnCaissier;
	@FXML 
	private Button btnLogin;
	private String realId ;
    private String realPw ;
	//clic sur le bouton quitter
		public void Exit (ActionEvent ev) {
			Platform.exit();	
		}
		
		//clic sur le bouton se connecter
		public void Enter (ActionEvent e)throws IOException, SQLException {
			String id = txtLogin.getText().toString();
			String pw = txtPw.getText().toString();
			
			if(btnAdmin.isSelected()) {
				System.out.println("Admin is seleted");
				realId = "pos-2021-ad"; 
		    	realPw = "Admin@2021";
		    	if(id.equals(realId) && pw.equals(realPw)) {
		    		System.out.println("Success!");
		    		//aller à l'interface admin 
	    			 try {
	 					new MainAdmin().start((Stage)btnLogin.getScene().getWindow());
	 				} catch (Exception ex) {
	 					// TODO Auto-generated catch block
	 					ex.printStackTrace();
	 				}
		    		
		    	}else{
		    		Alert alert = new Alert(AlertType.ERROR, " Echec !");
					alert.showAndWait();
					txtLogin.clear();
					txtPw.clear();

					System.out.println("login fail Error showed");
		    	}
				
			}//end admin selected
			else {
				System.out.println("Cashier is seleted");
				
					String query ="select username, password, firstName, lastName, iD from caissier where username = '"+id+"'";
					Connection con = DBConnection.getConnexion();
					PreparedStatement ps= con.prepareStatement(query);
					ResultSet rs;
					rs=ps.executeQuery();
					if(rs.next()) {
						realId=rs.getString("username");
						realPw=rs.getString("password");
						String prenom =rs.getString("firstName");
						String nom = rs.getString("lastName");
						String ID = rs.getString("iD");
						
						System.out.println("Id is "+id);
				    	System.out.println("Password is "+pw);
				    	System.out.println("nom is "+nom);
				    	System.out.println("prenom is "+prenom);
				    	if(id.equals(realId) && pw.equals(realPw)) {
				    		User.c.setFirstName(prenom);
				    		User.c.setLastName(nom);
				    		User.c.setID(ID);
				    		System.out.println("prenom caissier "+User.c.getFirstName());
				    		System.out.println("nom caissier "+User.c.getLastName());
				    		System.out.println("id caissier "+User.c.getID());
				    		try {
			  					new MainCashier().start((Stage)btnLogin.getScene().getWindow());
			  				} catch (Exception ex) {
			  					// TODO Auto-generated catch block
			  					ex.printStackTrace();
			  				}
				    		
				    	}else {
				    		Alert alert = new Alert(AlertType.ERROR, "Login fail! Incorrect Password");
							alert.showAndWait();
							txtLogin.clear();
							txtPw.clear();

							System.out.println("login fail Error showed");
				    	}
						
					}//end rs next
					else {
			    		Alert alert = new Alert(AlertType.ERROR, "No such user");
						alert.showAndWait();
						txtLogin.clear();
						txtPw.clear();

						System.out.println("no user Error showed");
			    		//System.out.println("No such user!!");
			    	}
			}
		}
		
		
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
		public static boolean isInteger(String s) {
		    try { 
		        Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
		    // only got here if we didn't return false
		    return true;
		}

}
