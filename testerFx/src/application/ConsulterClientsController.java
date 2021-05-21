package application;

import java.sql.Timestamp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Client;

public class ConsulterClientsController {
	@FXML
	private TableView<Client> clientTable;
	@FXML
	private TableColumn<Client, String> colid;
	@FXML
	private TableColumn<Client, String> colNom;
	@FXML
	private TableColumn<Client, String> colPrenom;
	@FXML
	private TableColumn<Client, Integer> colTel;
	@FXML
	private TableColumn<Client, Timestamp> colDate;
	@FXML
	private TextField txtNomClient;
	
	
	// permet de la remplir depuis la base
		public void afficherClient(KeyEvent k) {
			String name = txtNomClient.getText();
			ObservableList<Client> list = DBConnection.ClientList(name);	
			colid.setCellValueFactory(new PropertyValueFactory<Client,String>("iD"));
			colNom.setCellValueFactory(new PropertyValueFactory<Client,String>("firstName"));
			colPrenom.setCellValueFactory(new PropertyValueFactory<Client,String>("lastName"));
			colTel.setCellValueFactory(new PropertyValueFactory<Client,Integer>("phoneNbr"));	
			colDate.setCellValueFactory(new PropertyValueFactory<Client,Timestamp>("dateInscrit"));
			clientTable.setItems(list);
		}

}
