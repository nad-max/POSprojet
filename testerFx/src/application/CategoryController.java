package application;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.controlsfx.control.textfield.AutoCompletionBinding;

import model.Categories;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Product;



public class CategoryController {
	
	@FXML
	private TableView<Categories> tabCategorie;
	@FXML
	private TableColumn<Categories, String> colId;
	@FXML
	private TableColumn<Categories, String> colName;
	@FXML
	private Button btnAjouter;
	@FXML
	private TextField txtNomCat;
	@FXML
	private TableView<Categories> tabCatChercher;
	@FXML
	private TableColumn<Categories, String> colidCat;
	@FXML
	private TableColumn<Categories, String> colNomCat;
	
	
	
	
	public void Ajouter(ActionEvent e) {
		ajouterCat(tabCategorie);
	}
	
	// créer un  stage pour ajouter une nv catégorie
	public void ajouterCat(TableView<Categories> table) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Ajouter");
        window.setMinWidth(250);
        
        VBox v = new VBox();
        v.getChildren().add(addTitle("Nouvelle Categorie"));

        GridPane g = new GridPane();
        g.setPadding(new Insets(10, 10, 10, 10));
        g.setHgap(10);
        g.setVgap(8);

        // Name label
        Label l1 = new Label("Nom");
        GridPane.setConstraints(l1, 0, 0);
        // Name input
        TextField name = new TextField();
        
        GridPane.setConstraints(name, 1, 0);

        // add to gridpane
        g.getChildren().addAll(l1, name);

        //// add buttons
        Button leftb = new Button("Annuler");
        leftb.setOnAction(e -> window.close());
        Button rightb = new Button("Confirmer");
        rightb.setOnAction(e -> {

            String a1 = name.getText();
             Categories c = new Categories(newID(),a1);
            DBConnection.addCategory(c);
            table.getItems().add(c);
            window.close();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(" succès !");
            alert.setContentText("Ajout fait avec succès !");
            alert.show();
        });

        HBox b = new HBox(leftb, rightb);
        b.setPadding(new Insets(10, 10, 10, 10));
        b.setSpacing(20);
        b.setAlignment(Pos.CENTER_RIGHT);

        v.setAlignment(Pos.CENTER);
        v.setPadding(new Insets(20, 10, 10, 10));
        v.setSpacing(15);
        v.getChildren().addAll(g, b);

        Scene s = new Scene(v);
        window.setScene(s);
        window.show();
    }
	
	// permet de la remplir depuis la base
	public void afficherCategories(ActionEvent me) {
	ObservableList<Categories> list = DBConnection.getCategoryList();	
	colId.setCellValueFactory(new PropertyValueFactory<Categories,String>("idCat"));
	colName.setCellValueFactory(new PropertyValueFactory<Categories,String>("catName"));
	tabCategorie.setItems(list);
	}
	
	
	public void delete(ActionEvent e) {
		delete1(tabCategorie,tabCategorie.getSelectionModel().getSelectedItem());
	}
	
	public void delete1(TableView<Categories> table, Categories catSelected) {
		Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Attention");
        //window.setMinWidth(250);
        VBox v = new VBox();
        v.getChildren().add(addTitle("Attention"));

        GridPane g = new GridPane();
        g.setPadding(new Insets(10, 10, 10, 10));
        g.setHgap(10);
        g.setVgap(8);
        //label
        Label l0 = new Label("Etes-vous sure de vouloir supprimer cette catégorie ?");
        GridPane.setConstraints(l0, 0, 0);
        g.getChildren().addAll(l0);
        
        Button leftb = new Button("Annuler");
        leftb.setOnAction(e -> window.close());
        Button rightb = new Button("Confirmer");
        rightb.setOnAction(e -> {
        	System.out.println(tabCategorie.getSelectionModel().getSelectedIndex());
            DBConnection.deleteCategory(tabCategorie.getSelectionModel().getSelectedItem());
            tabCategorie.getItems().remove(tabCategorie.getSelectionModel().getSelectedItem());
        window.close();
        });
        HBox hb = new HBox(leftb, rightb);
        hb.setPadding(new Insets(10, 10, 10, 10));
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER_RIGHT);

        v.setAlignment(Pos.CENTER);
        v.setPadding(new Insets(20, 10, 10, 10));
        v.setSpacing(15);
        v.getChildren().addAll(g, hb);

        Scene sc = new Scene(v);
        window.setScene(sc);
        window.show();
	}
	public void modifier1(ActionEvent e) {
		modifierCat(tabCategorie,tabCategorie.getSelectionModel().getSelectedItem());
	}
	
	public void modifierCat(TableView<Categories> table, Categories catSelected) {
		Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Modifier");
        window.setMinWidth(250);
        VBox v = new VBox(addTitle("Modifier Catégorie"));
        
        GridPane g = new GridPane();
        g.setPadding(new Insets(10, 10, 10, 10));
        g.setHgap(10);
        g.setVgap(8);
     // id label
        Label l0 = new Label("ID");
        GridPane.setConstraints(l0, 0, 0);
        // id input
        TextField id = new TextField(catSelected.getIdCat());
        id.setDisable(true);
        GridPane.setConstraints(id, 1, 0);
     // name label
        Label l1 = new Label("Nom");
        GridPane.setConstraints(l1, 0, 1);
        // name input
        TextField name = new TextField(catSelected.getCatName());
        GridPane.setConstraints(name, 1, 1);
     // add to gridpane
        g.getChildren().addAll(l0, id, l1, name);
        
        Button annuler = new Button("Annuler");
        annuler.setOnAction(e -> window.close());
        Button modifier = new Button("Modifier");
        modifier.setOnAction(e -> {
        	Categories categories = new Categories(id.getText(), name.getText());
        	DBConnection.deleteCategory(catSelected);
        	DBConnection.addCategory(categories);
        	table.getItems().remove(catSelected);
        	table.getItems().add(categories);
        	window.close();
        	HBox b = new HBox(annuler, modifier);
            b.setPadding(new Insets(10, 10, 10, 10));
            b.setSpacing(20);
            b.setAlignment(Pos.CENTER_RIGHT);

            v.getChildren().addAll(g, b);
            v.setAlignment(Pos.CENTER);
            v.setPadding(new Insets(20, 10, 10, 10));
            v.setSpacing(15);
            Scene s = new Scene(v);
            window.setScene(s);
            window.show();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(" succès !");
            alert.setContentText("Modification faite avec succès !");
            alert.show();
        });
	
	}
	
	// détails 
	public void details(ActionEvent a) {
		detail(tabCategorie);
	}
	public void detail(TableView<Categories> table) {
		Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Détails");
        window.setMinWidth(75);

        VBox v = new VBox(addTitle("Les produits de cette catégorie"));
     // making the table
        TableColumn<Product, String> idColumn = new TableColumn<>("ID");
        idColumn.setPrefWidth(75);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ProductID"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setPrefWidth(77);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("ProductName"));

        TableColumn<Product, String> bpriceColumn = new TableColumn<>("Prix d'Achat");
        bpriceColumn.setPrefWidth(77);
        bpriceColumn.setCellValueFactory(new PropertyValueFactory<>("BuyingPrice"));

        TableColumn<Product, String> spriceColumn = new TableColumn<>("Prix de Vente");
        spriceColumn.setPrefWidth(93);
        spriceColumn.setCellValueFactory(new PropertyValueFactory<>("SellingPrice"));

        TableColumn<Product, String> catColumn = new TableColumn<>("Categorie");
        catColumn.setPrefWidth(93);
        catColumn.setCellValueFactory(new PropertyValueFactory<>("ProductCat"));

        TableColumn<Product, String> fourniColumn = new TableColumn<>("Fournisseur");
        fourniColumn.setPrefWidth(88);
        fourniColumn.setCellValueFactory(new PropertyValueFactory<>("Fournisseur"));

        TableColumn<Product, String> qteColumn = new TableColumn<>("Quantité");
        qteColumn.setPrefWidth(117);
        qteColumn.setCellValueFactory(new PropertyValueFactory<>("Qte"));

        TableView<Product> tab = new TableView<>();
        tab.setItems(DBConnection.getProductByCategory(table.getSelectionModel().getSelectedItem().getIdCat()));
        tab.getColumns().addAll(idColumn, nameColumn, bpriceColumn, spriceColumn, catColumn, fourniColumn, qteColumn);
        
        Button ok = new Button("Ok");
        ok.setOnAction(e -> window.close());
        HBox b = new HBox(ok);
        b.setPadding(new Insets(10, 10, 10, 10));
        b.setSpacing(20);
        b.setAlignment(Pos.CENTER_RIGHT);

        v.getChildren().addAll(tab, b);
        v.setAlignment(Pos.CENTER);
        v.setPadding(new Insets(20, 10, 10, 10));
        v.setSpacing(15);
        Scene s = new Scene(v);
        window.setScene(s);
        window.show();
	}
	
	//chercher
	public void chercher(ActionEvent a) throws IOException {
		
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("ChercherCatView.fxml"));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chercher Catégories");
		primaryStage.setResizable(false);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();
	
	}
	
	
	public void NameSearchAction(KeyEvent k) {
		String name = txtNomCat.getText();
		ObservableList<Categories> list = DBConnection.CatList(name);
		colidCat.setCellValueFactory(new PropertyValueFactory<Categories,String>("idCat"));
		colNomCat.setCellValueFactory(new PropertyValueFactory<Categories,String>("catName"));
		tabCatChercher.setItems(list);
	}
	
	
	public Text addTitle(String title) {
        Text t = new Text(title);
        t.setFont(Font.font("Arial", 18));
        return t;
    }

    private String newID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, uuid.indexOf('-'));
    }

	
}

