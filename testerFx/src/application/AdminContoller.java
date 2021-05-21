package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AdminContoller {
	
	@FXML
	private Button btnProduit;
	@FXML
	private Button btnCatégorie;
	@FXML
	private Button btnCaissier;
	@FXML
	private Button btnFournisseur;
	@FXML
	private Button btnClient;
	
	@FXML
	private Button btnJournal;
	@FXML
	private Button btnStat;
	@FXML
	private Button btnExit;
	@FXML
	private AnchorPane common_pane;
	
	
	
	// for screen transaction from login to admin panel
		public class LoginPg extends Application {

			@Override
			public void start(Stage primaryStage) throws Exception {
				Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));

				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Ingenious Labs");
				//primaryStage.sizeToScene();
				primaryStage.setResizable(false);
				primaryStage.setMaximized(false);
				primaryStage.show();
			}
		}
	
	public void Exit (ActionEvent ev) throws IOException {
		
		// scene transaction
					try {
						new LoginPg().start((Stage) btnExit.getScene().getWindow());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
	}
	
	
	
	public void AllCategories (ActionEvent ev) {
		AnchorPane pane = null;
	try {
		pane = FXMLLoader.load(getClass().getResource("CategoryView.fxml"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	common_pane.getChildren().clear();
	common_pane.getChildren().add(pane);
	
	btnCatégorie.setDisable(true);
	btnProduit.setDisable(false);
	btnCaissier.setDisable(false);
	btnFournisseur.setDisable(false);
	//btnClient.setDisable(false);
	
	btnJournal.setDisable(false);
	btnStat.setDisable(false);
	
		
	}	
			
			
			
	public void AllProducts (ActionEvent ev) throws IOException {
		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource("ProductView.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);
		
		btnCatégorie.setDisable(false);
		btnProduit.setDisable(true);
		btnCaissier.setDisable(false);
		btnFournisseur.setDisable(false);
		//btnClient.setDisable(false);
		
		btnJournal.setDisable(false);
		btnStat.setDisable(false);
		
	  }
	
	public void AllCaissiers (ActionEvent ev) throws IOException {
		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource("Caissier.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);
		
		btnCatégorie.setDisable(false);
		btnProduit.setDisable(false);
		btnCaissier.setDisable(true);
		btnFournisseur.setDisable(false);
		//btnClient.setDisable(false);
		
		btnJournal.setDisable(false);
		btnStat.setDisable(false);
		
				
	  }
	
	public void AllFournisseurs (ActionEvent ev) throws IOException {
		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource("FournisseurView.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);
		
		btnCatégorie.setDisable(false);
		btnProduit.setDisable(false);
		btnCaissier.setDisable(false);
		btnFournisseur.setDisable(true);
		//btnClient.setDisable(false);
		
		btnJournal.setDisable(false);
		btnStat.setDisable(false);
		
				
	  }
	
	public void AllClients (ActionEvent ev) throws IOException {
		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource("ClientView.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);
		
		btnCatégorie.setDisable(false);
		btnProduit.setDisable(false);
		btnCaissier.setDisable(false);
		btnFournisseur.setDisable(false);
		btnClient.setDisable(true);
		
		btnJournal.setDisable(false);
		btnStat.setDisable(false);
				
	  }
	
	public void Statis (ActionEvent ev) throws IOException {
		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource("ViewChart.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);
		
		btnCatégorie.setDisable(false);
		btnProduit.setDisable(false);
		btnCaissier.setDisable(false);
		btnFournisseur.setDisable(false);
		//btnClient.setDisable(false);
		btnJournal.setDisable(false);
		btnStat.setDisable(true);
				
	  }
	
	public void Rapport (ActionEvent ev) {
		AnchorPane pane = null;
	try {
		pane = FXMLLoader.load(getClass().getResource("ReportView.fxml"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	common_pane.getChildren().clear();
	common_pane.getChildren().add(pane);
	
	btnCatégorie.setDisable(false);
	btnProduit.setDisable(false);
	btnCaissier.setDisable(false);
	btnFournisseur.setDisable(false);
	//btnClient.setDisable(false);
	
	btnJournal.setDisable(true);
	btnStat.setDisable(false);
	
		
	}	
	
			
	public void openModelWindow(String resource, String title) {
	try {
		Parent root = FXMLLoader.load(getClass().getResource(resource));
		Scene fxmlFile = new Scene(root);
		Stage window = new Stage();
		window.setScene(fxmlFile);
		window.initModality(Modality.NONE);
		window.setTitle(title);
		window.show();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
		
	}
	
	
	
	
	
}
	
	
	
	
	


