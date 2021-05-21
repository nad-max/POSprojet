package application;

import java.util.UUID;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Categories;
import model.Fournisseur;
import model.Product;
import model.Promotion;

public class ProductController {
	@FXML
	private TableView<Product> productTab;
	@FXML
	private TableColumn<Product, String> colId;
	@FXML
	private TableColumn<Product, String> colNom;
	@FXML
	private TableColumn<Product, Double> colPrixAchat;
	@FXML
	private TableColumn<Product, Double> colPrixVente;
	@FXML
	private TableColumn<Product, String> colCat;
	@FXML
	private TableColumn<Product, String> colFournisseur;
	@FXML
	private TableColumn<Product, Integer> colQte;
	@FXML
	private TableColumn<Product, Integer> colRemise;
	
	
	// permet de la remplir depuis la base
	public void afficherProducts(ActionEvent me) {
	ObservableList<Product> list = DBConnection.getProductList();	
	colId.setCellValueFactory(new PropertyValueFactory<Product,String>("productID"));
	colNom.setCellValueFactory(new PropertyValueFactory<Product,String>("ProductName"));
	colPrixAchat.setCellValueFactory(new PropertyValueFactory<Product,Double>("BuyingPrice"));
	colPrixVente.setCellValueFactory(new PropertyValueFactory<Product,Double>("SellingPrice"));
	colCat.setCellValueFactory(new PropertyValueFactory<Product,String>("ProductCat"));
	colFournisseur.setCellValueFactory(new PropertyValueFactory<Product,String>("Fournisseur"));
	colQte.setCellValueFactory(new PropertyValueFactory<Product,Integer>("Qte"));
	colRemise.setCellValueFactory(new PropertyValueFactory<Product,Integer>("remise"));
	productTab.setItems(list);
	}
	
	// ajouter un produit
	public void Ajouter(ActionEvent e) {
		ajouterProduct(productTab);
	}
	
	private void ajouterProduct(TableView<Product> table) {
		// TODO Auto-generated method stub
		Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Ajouter");
        window.setMinWidth(250);
        VBox v = new VBox();
        v.getChildren().add(addTitle("Nouvel Article"));

        GridPane g = new GridPane();
        g.setPadding(new Insets(10, 10, 10, 10));
        g.setHgap(10);
        g.setVgap(8);
        // name label
        Label l1 = new Label("Nom");
        GridPane.setConstraints(l1, 0, 0);
        //name input
        TextField name = new TextField();
        GridPane.setConstraints(name, 1, 0);
        //buying price label
        Label l2 = new Label("Prix d'achat");
        GridPane.setConstraints(l2, 0, 1);
        //buying price input
        TextField buyingPrice = new TextField();
        GridPane.setConstraints(buyingPrice, 1, 1);
        // selling price  label
        Label l3 = new Label("Prix de vente");
        GridPane.setConstraints(l3, 0, 2);
        // selling price input
        TextField sellingPrice = new TextField();
        GridPane.setConstraints(sellingPrice, 1, 2);
        // category label
        Label l4 = new Label("Catégorie");
        GridPane.setConstraints(l4, 0, 3);
        //category input
        ChoiceBox<String> category =new ChoiceBox<>(DBConnection.categoryToString());
        GridPane.setConstraints(category, 1, 3);
        // fournisseur label
        Label l5 = new Label("Fournisseur");
        GridPane.setConstraints(l5, 0, 4);
        // fournisseur input
        ChoiceBox<String> fournisseur = new ChoiceBox<>(DBConnection.fournisseurToString());
        GridPane.setConstraints(fournisseur, 1, 4);
        // Qte label
        Label l6 = new Label("Quantité");
        GridPane.setConstraints(l6, 0, 5);
        // Qte input
        TextField qte = new TextField();
        GridPane.setConstraints(qte, 1, 5);
        // remise input
        Label l7 = new Label("Taux de Remise (%)");
        GridPane.setConstraints(l7, 0, 6);
     // Qte input
        TextField remise = new TextField();
        GridPane.setConstraints(remise, 1, 6);
     // add to gridpane
        g.getChildren().addAll(l1,name,l2,buyingPrice,l3,sellingPrice,l4,category,l5,fournisseur,l6,qte,l7,remise);
   //// add buttons
        Button leftb = new Button("Annuler");
        leftb.setOnAction(e -> window.close());
        Button rightb = new Button("Confirmer");
        rightb.setOnAction(e -> {
         
         Categories cat = DBConnection.getCategoryById(IDfromData(category.getValue()));
         Fournisseur f = DBConnection.getFournisseurById(IDfromData(fournisseur.getValue()));
         Product prod = new Product(newID(), name.getText(),Double.parseDouble(buyingPrice.getText()) , Double.parseDouble(sellingPrice.getText()),cat.getIdCat(),f.getID(),Integer.parseInt(qte.getText()),Integer.parseInt(remise.getText()));
         DBConnection.addProduct(prod);
         table.getItems().add(prod);
         window.close();
         //msg succès
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.setTitle("Message");
         alert.setHeaderText("Succès !");
         alert.setContentText("Ajout  fait avec succès !");
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
	
	//modifier produit
	public void modifier(ActionEvent e) {
		modifierProduct(productTab,productTab.getSelectionModel().getSelectedItem());
	}
	
	public void modifierProduct(TableView<Product> table, Product selectedProduct) {
		Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Modifier");
        //window.setMinWidth(250);
        VBox v = new VBox();
        v.getChildren().add(addTitle("Modifier Article"));

        GridPane g = new GridPane();
        g.setPadding(new Insets(10, 10, 10, 10));
        g.setHgap(10);
        g.setVgap(8);
        // id label
        Label l0 = new Label("Id");
        GridPane.setConstraints(l0, 0, 0);
        // id input
        TextField id = new TextField(selectedProduct.getProductID());
        id.setDisable(true);
        GridPane.setConstraints(id, 1, 0);
        // name label
        Label l1 = new Label("Nom");
        GridPane.setConstraints(l1, 0, 1);
        //name input
        TextField name = new TextField(selectedProduct.getProductName());
        GridPane.setConstraints(name, 1, 1);
        //buying price label
        Label l2 = new Label("Prix d'achat");
        GridPane.setConstraints(l2, 0, 2);
        // buying price input
        TextField buyingPrice = new TextField(String.valueOf(selectedProduct.getBuyingPrice()));
        GridPane.setConstraints(buyingPrice, 1,2);
        // selling price label
        Label l3 = new Label("Prix de vente");
        GridPane.setConstraints(l3, 0,3);
        // selling price input
        TextField sellingPrice = new TextField(String.valueOf(selectedProduct.getSellingPrice()));
        GridPane.setConstraints(sellingPrice, 1, 3);
        // category label
        Label l4 = new Label("Catégorie");
        GridPane.setConstraints(l4, 0, 4);
        // old category input
        TextField oldCat = new TextField(selectedProduct.getProductCat());
        oldCat.setDisable(true);
        GridPane.setConstraints(oldCat, 1, 4);
        // new category input
        ChoiceBox<String> category =new ChoiceBox<>(DBConnection.categoryToString());
        GridPane.setConstraints(category, 2, 4);
        // fournisseur label 
        Label l5 = new Label("Fournisseur");
        GridPane.setConstraints(l5, 0, 5);
        // old fournisseur input
        TextField oldF = new TextField(selectedProduct.getFournisseur());
        oldF.setDisable(true);
        GridPane.setConstraints(oldF, 1, 5);
        // fournisseur input
        ChoiceBox<String> fournisseur =new ChoiceBox<>(DBConnection.fournisseurToString());
        GridPane.setConstraints(fournisseur, 2, 5);
        // Qte label
        Label l6 = new Label("Quantité");
        GridPane.setConstraints(l6, 0, 6);
        // Qte input
        TextField qte = new TextField(String.valueOf(selectedProduct.getQte()));
        GridPane.setConstraints(qte, 1,6);
     // remise label
        Label l7 = new Label("Taux de Remise (%)");
        GridPane.setConstraints(l7, 0, 7);
     // remise input
        TextField remise = new TextField(String.valueOf(selectedProduct.getRemise()));
        GridPane.setConstraints(remise, 1,7);
        // add to gridpane
        g.getChildren().addAll(l0,id,l1,name,l2,buyingPrice,l3,sellingPrice,l4,oldCat,category,l5,oldF,fournisseur,l6,qte,l7,remise);
       //// add buttons
        Button leftb = new Button("Annuler");
        leftb.setOnAction(e -> window.close());
        Button rightb = new Button("Confirmer");
        rightb.setOnAction(e -> {
        	Categories cat = DBConnection.getCategoryById(IDfromData(category.getValue()));
        	Fournisseur f = DBConnection.getFournisseurById(IDfromData(fournisseur.getValue()));
        	Product prod = new Product(id.getText(),name.getText(),Double.parseDouble(buyingPrice.getText()), Double.parseDouble(sellingPrice.getText()), cat.getIdCat(),f.getID(),Integer.parseInt(qte.getText()),Integer.parseInt(remise.getText()));
            DBConnection.deleteProduct(selectedProduct);
            DBConnection.addProduct(prod);
            productTab.getItems().remove(selectedProduct);
            productTab.getItems().add(prod);
	        window.close();
	        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(" succès !");
            alert.setContentText("Modification faite avec succès !");
            alert.show();
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
	
	// supprimer produit
	public void delete(ActionEvent e) {
		delete1();
	}
	
	public void delete1() {
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
        Label l0 = new Label("Etes-vous sure de vouloir supprimer ce produit ?");
        GridPane.setConstraints(l0, 0, 0);
        g.getChildren().addAll(l0);
        
        Button leftb = new Button("Annuler");
        leftb.setOnAction(e -> window.close());
        Button rightb = new Button("Confirmer");
        rightb.setOnAction(e -> {
        System.out.println(productTab.getSelectionModel().getSelectedIndex());
        DBConnection.deleteProduct(productTab.getSelectionModel().getSelectedItem());
        productTab.getItems().remove(productTab.getSelectionModel().getSelectedItem());
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
	
	// chercher produit
	public void chercher(ActionEvent a) {
		chercherProduct();
	}
	
	private void chercherProduct() {
		// TODO Auto-generated method stub
		Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Chercher");
        window.setMinWidth(250);

        VBox v = new VBox(addTitle("Chercher Articles"));
        
        GridPane g = new GridPane();
        g.setPadding(new Insets(10, 10, 10, 10));
        g.setHgap(10);
        g.setVgap(8);
     // Name label
        Label l0 = new Label("Nom");
        GridPane.setConstraints(l0, 0, 0);
        // Name input
        TextField name = new TextField();
        GridPane.setConstraints(name, 1, 0);

        // minprice label
        Label l1 = new Label("Prix min");
        GridPane.setConstraints(l1, 0, 1);
        // minprice input
        TextField minprice = new TextField();
        minprice.setPromptText("10.0");
        GridPane.setConstraints(minprice, 1, 1);
     // maxprice label
        Label l2 = new Label("Prix Max");
        GridPane.setConstraints(l2, 0, 2);
        // maxprice input
        TextField Maxprice = new TextField();
        Maxprice.setPromptText("100.0");
        GridPane.setConstraints(Maxprice, 1, 2);

        // add to gridpane
        g.getChildren().addAll(l0, name, l1, minprice, l2, Maxprice);
        
        // making the table
        TableColumn<Product, String> idColumn = new TableColumn<>("ID");
        idColumn.setPrefWidth(75);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        
        TableColumn<Product, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setPrefWidth(75);
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
        
        TableColumn<Product, Integer> remiseColumn = new TableColumn<>("Remise");
        remiseColumn.setPrefWidth(117);
        remiseColumn.setCellValueFactory(new PropertyValueFactory<>("remise"));
        
        TableView<Product> tab = new TableView<>();
        tab.setItems(null);
        tab.getColumns().addAll(idColumn, nameColumn, bpriceColumn, spriceColumn, catColumn, fourniColumn, qteColumn,remiseColumn);
        // the buttons
        Button retour = new Button("Retour");
        retour.setOnAction(e -> window.close());
        Button chercher = new Button("Chercher");
        chercher.setOnAction(e -> {
        	Double minpriceprod;
            Double maxpriceprod;
            if (minprice.getText().isEmpty()) {
                minpriceprod = 0.0;
            } else {
                minpriceprod = Double.parseDouble(minprice.getText());
            }
            if (Maxprice.getText().isEmpty()) {
                maxpriceprod = 999999999.0;
            } else {
                maxpriceprod = Double.parseDouble(Maxprice.getText());
            }
            if (name.getText().isEmpty()) {
                tab.setItems(DBConnection.Filtrewithoutname(minpriceprod, maxpriceprod));
            } else {
                tab.setItems(DBConnection.Filtrewithname(name.getText(), minpriceprod, maxpriceprod));
            }
        });
        HBox b = new HBox(retour, chercher);
        b.setPadding(new Insets(10, 10, 10, 10));
        b.setSpacing(20);
        b.setAlignment(Pos.CENTER_RIGHT);
       
        v.setAlignment(Pos.CENTER);
        v.setPadding(new Insets(20, 10, 10, 10));
        v.setSpacing(15);
        v.getChildren().addAll(g, tab, b);
        Scene s = new Scene(v);
        window.setScene(s);
        window.show();
	}
	
	
	public Text addTitle(String title) {
        Text t = new Text(title);
        t.setFont(Font.font("Arial", 20));
        return t;
    }
	

    private String newID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, uuid.indexOf('-'));
    }
	
    public String IDfromData(String data) {
        return data.substring(0, data.indexOf(' '));
    }
    
    public int PourcentageFromData( String data) {
    		String s=data.substring(0,data.indexOf(' '));
    		
    		return Integer.parseInt(s) ;
    	
    }
	
}
