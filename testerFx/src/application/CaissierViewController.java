package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Product;
import model.Vente;
import net.sf.jasperreports.engine.JRException;


public class CaissierViewController {
	@FXML
	private Button btnExit;
	@FXML
	private TextField txtTotal;
	@FXML
	private TableView<Product> tabProd;
	@FXML
	private TableColumn<Product, String> colCode;
	@FXML
	private TableColumn<Product, String> colNom;
	@FXML
	private TableColumn<Product, String> colCat;
	@FXML
	private TableColumn<Product, Double> colPrix;
	@FXML
	private TableColumn<Product, Integer> colQte;
	@FXML
	private TextField txtName;
	@FXML
	private Label txtFactId;
	@FXML
	private Label caissierName;
	
	
	// VENTE TABLE 
	@FXML
	private TableView<Vente> tabVente;
	@FXML
	private TableColumn<Vente, String> colCodeP;
	@FXML
	private TableColumn<Vente, String> colNomP;
	@FXML
	private TableColumn<Vente, Double> colPu;
	@FXML
	private TableColumn<Vente, Integer> colQteP;
	@FXML
	private TableColumn<Vente, Double> colMT;
	@FXML
	private TableColumn<Vente, Integer> colremise;
	@FXML
	private TextField txtEspece;
	@FXML
	private TextField txtRendu;
	
	
	private static ObservableList<Vente> purchasedata = FXCollections.observableArrayList();
	
	
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
		
		public void consulterClients(ActionEvent ev) {
			try {
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("consulterClients.fxml"));
				Scene scene = new Scene(root);
				//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Clients");
				primaryStage.setResizable(false);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public void NameSearchAction(KeyEvent k) {
			String name = txtName.getText();
			ObservableList<Product> list = DBConnection.ProductList(name);
			colCode.setCellValueFactory(new PropertyValueFactory<Product,String>("productID"));
			colNom.setCellValueFactory(new PropertyValueFactory<Product,String>("ProductName"));
			//colCat.setCellValueFactory(new PropertyValueFactory<Product,String>("ProductCat"));
			colPrix.setCellValueFactory(new PropertyValueFactory<Product,Double>("SellingPrice"));
			colQte.setCellValueFactory(new PropertyValueFactory<Product,Integer>("Qte"));
			tabProd.setItems(list);
		}
		
	// btn +	
	public void clique(ActionEvent a) throws SQLException {
		initialize(tabProd,tabProd.getSelectionModel().getSelectedItem());
	}
		
		public void initialize(TableView<Product> table, Product selectedProduct) throws SQLException {
			Callback<TableColumn<Vente, Integer>, TableCell<Vente, Integer>> cellFactory = (
			TableColumn<Vente, Integer> param) -> new EditingCell();
			colCodeP.setCellValueFactory(new PropertyValueFactory<Vente,String>("productID"));
			colNomP.setCellValueFactory(new PropertyValueFactory<Vente,String>("productName"));
			colPu.setCellValueFactory(new PropertyValueFactory<Vente,Double>("sellingPrice"));
			colQteP.setCellValueFactory(new PropertyValueFactory<Vente,Integer>("qte"));
			colremise.setCellValueFactory(new PropertyValueFactory<Vente,Integer>("remise"));
			colMT.setCellValueFactory(new PropertyValueFactory<Vente,Double>("totalAmount"));
			
			
			colQteP.setCellFactory(cellFactory);
			colQteP.setOnEditCommit(new EventHandler<CellEditEvent<Vente, Integer>>() {
				
				@Override
				public void handle(CellEditEvent<Vente, Integer> t) {
					// TODO Auto-generated method stub
					((Vente) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQte((t.getNewValue()));
					System.out.println("Qty edit Working");
					t.getRowValue().setQte(t.getNewValue());
					
					double qty = ((Vente) t.getTableView().getItems().get(t.getTablePosition().getRow())).getQte();
					String itemmId = ((Vente) t.getTableView().getItems().get(t.getTablePosition().getRow())).getProductID();
					double unitprice = t.getRowValue().getSellingPrice();
					//int discount = t.getRowValue().getRemise();
					int discount = ((Vente) t.getTableView().getItems().get(t.getTablePosition().getRow())).getRemise();
					double total1 = unitprice * qty;
					
					
					if (discount != 0) {
						
						double tominuspromotion = total1 * (discount * 0.01);
						double total = total1 - tominuspromotion;
						t.getRowValue().setTotalAmount(total);
						

					} else {
						t.getRowValue().setTotalAmount(total1);
					}
					//System.out.println(total);
					//t.getRowValue().setTotalAmount(total1);
					tabVente.refresh();
					tabVente.getColumns().get(0).setVisible(false);
					tabVente.getColumns().get(0).setVisible(true);
				//total achat
					int totalall = 0;
					for (Vente i : tabVente.getItems()) {
						totalall += i.getTotalAmount();
					}
					txtTotal.setText("" + totalall);
					
				
				}
		});
			tabVente.setItems(purchasedata);
			tabVente.setEditable(true);
			tabVente.refresh();
    String query= " select remise from product where ProductID = ?";
    Connection con = DBConnection.getConnexion();
    PreparedStatement ps = con.prepareStatement(query);
    ps.setString(1, tabProd.getSelectionModel().getSelectedItem().getProductID());
    ResultSet rs;
	 rs=ps.executeQuery();
	 int re=0;
	 while (rs.next()) {
		  re = rs.getInt(1);
	 }
	 
			
    selectedProduct = tabProd.getSelectionModel().getSelectedItem();
	System.out.println(" click is: " + selectedProduct.getProductName());
	//String qq="insert into Vente(ProductID,ProductName,SellingPrice,Qte,TotalAmount,remise) values()";
	User.buygetdata.add(selectedProduct.getProductID());
	System.out.println("buy get data "+User.buygetdata);
	Vente v =  new Vente();
	v.setProductID(selectedProduct.getProductID());
	v.setProductName(selectedProduct.getProductName());
	v.setSellingPrice(selectedProduct.getSellingPrice());
	v.setQte(1);
	v.setRemise(re);
	purchasedata.add(v);
	tabVente.refresh();
	Double totalall = 0.0;
	for (Vente i : tabVente.getItems()) {
		totalall += i.getTotalAmount();
	}
	txtTotal.setText("" + totalall);
	User.totalAmount= Double.parseDouble(txtTotal.getText());
								
}
		
		
		public void onEspecesEnter(MouseEvent k) {
			if (txtEspece.getText().equals("")) {
				Alert al = new Alert(AlertType.ERROR, "No Input!");
				al.showAndWait();
			} else if (txtEspece.getText().matches(".*[a-zA-Z]+.*")) {
				Alert al = new Alert(AlertType.ERROR, "S'il vous plait entrez un numéro !");
				al.showAndWait();
			} else if (Double.parseDouble(txtEspece.getText()) < Double.parseDouble(txtTotal.getText())) {
				// do nothing
				Alert al = new Alert(AlertType.ERROR, "Montant invalide!");
				al.showAndWait();
				txtEspece.clear();
			}else {
				Double total = Double.parseDouble(txtTotal.getText());
				Double es = Double.parseDouble(txtEspece.getText());
				Double change = es - total;
				txtRendu.setText(""+change);
				
				User.payamount = es;
				User.change = change;
				System.out.println("change"+User.change);
				//txtRendu.setText("" + User.change);
				
			}
			
		}
  
		// btn nv
		public void onBtnNewAction(ActionEvent event) throws SQLException{
			User.buygetdata.clear();
			
			// set name caissier
						caissierName.setText(User.c.getFirstName()+" " + User.c.getLastName());
			Connection conn= DBConnection.getConnexion();
				String ancienid = " SELECT `id` FROM `facture` ORDER BY `id` DESC LIMIT 1 " ;
				PreparedStatement ps= conn.prepareStatement(ancienid);
				ResultSet rs = ps.executeQuery();
				int previousid=0;
				while (rs.next()) {
					previousid = rs.getInt("id");
				}
				int newid = previousid + 1;
				txtFactId.setText("" + newid );
				User.slipno=""+newid;
				purchasedata.clear();
				tabVente.refresh();
				User.totalAmount=0;
				txtTotal.clear();
				txtEspece.clear();
				txtRendu.clear();
				
			
		}
		
		
		public void onPrintOnAction(ActionEvent event) throws JRException {
			if (txtRendu.getText().equals("") || txtEspece.getText().equals("")) {
				// do nothing
				txtRendu.clear();
				txtEspece.clear();
				Alert al = new Alert(AlertType.ERROR, "S'il vous plait entrez un numéro !");
				al.showAndWait();
			}else {
				ObservableList<Vente> ventedata = FXCollections.observableArrayList();
				ventedata = tabVente.getItems();
				StringBuilder productid = new StringBuilder();
				StringBuilder qty = new StringBuilder();
				for (int i = 0; i < ventedata.size(); i++) {
					productid.append(ventedata.get(i).getProductID());
					productid.append(",");

					qty.append(ventedata.get(i).getQte());
					qty.append(",");
                 }
				
				String iid = productid.toString();
				String qte = qty.toString();
				try {
					// id facture
					Connection conn= DBConnection.getConnexion();
					String ancienId = " SELECT `id` FROM `facture` ORDER BY `id` DESC LIMIT 1 " ;
					PreparedStatement ps= conn.prepareStatement(ancienId);
					ResultSet rs = ps.executeQuery(ancienId);
					int previousId=0;
					while (rs.next()) {
						previousId = rs.getInt("id");
					}
					int newId = previousId + 1;
					
					//total amount
					//Double total = Double.parseDouble(txtTotal.getText());
					User.totalAmount = Double.parseDouble(txtTotal.getText());
			
					// db
					 conn= DBConnection.getConnexion();
					 String newFacture = "insert into facture(id,ProductId,totalAmount,Qte,idCaisier) values(?,?,?,?,?)";
					ps=conn.prepareStatement(newFacture);
					ps.setInt(1, newId);
					ps.setString(2, iid);
					ps.setDouble(3, User.totalAmount);
					ps.setString(4, qte);
					ps.setString(5, User.c.getID());
					ps.executeUpdate();
					
					//update stock
					// update count
					String[] FactureProductId = productid.toString().split(",");
					String[] FactureProductQty = qty.toString().split(",");
					
					for (int i = 0; i < FactureProductId.length; i++) {
						String getstockquery = "SELECT `Qte` FROM `product` WHERE product.ProductID = '"
								+ FactureProductId[i] + "';";
						ps=conn.prepareStatement(getstockquery);
					    rs = ps.executeQuery();
					    int oldQt = 0;
					    while(rs.next()) {
					    	 oldQt = rs.getInt(1);
					    }
					    int newQt = oldQt - Integer.parseInt(FactureProductQty[i]);
					    String updatestockquery = "UPDATE `product` SET `Qte`='" + newQt
								+ "' WHERE product.ProductID = '" + FactureProductId[i] + "'";
					    ps.executeUpdate(updatestockquery);
					    
					    int oldcount = 0;
						int newcount = 0;
						// get old count
						String getoldcount = " select count from product where ProductID = '"+FactureProductId[i]+"'" ;
						ps=conn.prepareStatement(getoldcount);
						rs = ps.executeQuery();
						while(rs.next()) {
							oldcount=rs.getInt(1);
						}
						System.out.println("old count is : " + oldcount);
						newcount = oldcount + Integer.parseInt(FactureProductQty[i]);
						System.out.println("new count is " + newcount + "purchase qty" + FactureProductQty[i]);
						String updateCount = "update product set count ='" +newcount+"' where ProductID ='"+ FactureProductId[i] + "'";
						ps.executeUpdate(updateCount);
					}//end for
					
					//User.totalAmount= Double.parseDouble(txtTotal.getText().toString());
					User.payamount=Double.parseDouble(txtEspece.getText().toString());
					User.change=Double.parseDouble(txtRendu.getText().toString());
					
					txtTotal.clear();
					txtEspece.clear();
					txtRendu.clear();
					
					
					new GeneratRepport().generateFacture(ventedata);
					
					// clear sale data
					purchasedata.clear();
					tabVente.refresh();
					User.totalAmount=0;
					
					//set num facture
					//conn= DBConnection.getConnexion();
					 conn= DBConnection.getConnexion();
					String ancienid = " SELECT `id` FROM `facture` ORDER BY `id` DESC LIMIT 1 " ;
					 ps= conn.prepareStatement(ancienid);
					 rs = ps.executeQuery(ancienid);
					int previousid=0;
					while (rs.next()) {
						previousid = rs.getInt("id");
					}
					int newid = previousid + 1;
					txtFactId.setText("" + newid );
					
					User.buygetdata.clear();
					User.totalAmount=0;
					User.change=0;
					User.payamount=0;
					
					
					
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
				
				
			}
			
		} 
		// btn annuler
		public void cancel(ActionEvent a) {
			cancelItem(tabVente,tabVente.getSelectionModel().getSelectedItem());
		}
		public void cancelItem(TableView<Vente> table, Vente selectedItem) {
			 selectedItem = table.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Etes vous sure de supprimer " + selectedItem.getProductName() + " ?",
					ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			
			if(alert.getResult() == ButtonType.YES) {
				//Vente vente = purchasedata.get(table.getSelectionModel().getSelectedItem());
				String tt = txtTotal.getText();
				Double t = Double.parseDouble(tt);
				t = t - selectedItem.getTotalAmount();
				txtTotal.setText(""+ t);
				txtEspece.clear();
				txtRendu.clear();
				table.getItems().remove(selectedItem);
				table.refresh();
				
			}
			
			
		}
		
		
		
		
		
		
}
