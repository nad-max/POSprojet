package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Caissier;
//import model.Administrateur;
import model.Categories;
import model.Client;
import model.Fournisseur;
import model.Product;
import model.Promotion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class DBConnection {

	 
	    public static Connection con;
	   
	    
	    public static Connection getConnexion(){  
	    	try {   
	    		Class.forName("com.mysql.cj.jdbc.Driver");   
	    		//Chargement de driver JDBC   
	    		 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projet", "root", "");  
	    		//System.out.println("Connection is established" );  
	    		   } 
	    	
	    	catch (Exception e) {   
	    		// TODO Auto-generated catch block  
	    		e.printStackTrace();  
	    		System.out.println("connection failed");  
	    		   }    
	    	
	    	return con;
	    	}
	    	
	 // récuperer la liste des catégories 
	 	public static ObservableList<Categories> getCategoryList() {
	 		
	 	ObservableList<Categories> listCategorie =FXCollections.observableArrayList();
	 	Connection conn= DBConnection.getConnexion();
	 	String req = "select * from category";
	 	PreparedStatement ps;
	 	ResultSet rs;
	 		try {
	 				ps=conn.prepareStatement(req);
	 		        rs=ps.executeQuery(req);
	 		       while (rs.next()) {
	 		           Categories c = new Categories(rs.getString(1), rs.getString(2));
	 		           listCategorie.add(c);
	 		            }
	 		           } catch (Exception e) {
	 		            e.printStackTrace();
	 		        }
	 		        return listCategorie;
	 			}	
	 	//liste promo
	 	/*public static ObservableList<Promotion> getPromoList() {
	 		
		 	ObservableList<Promotion> listPromotion =FXCollections.observableArrayList();
		 	Connection conn= DBConnection.getConnexion();
		 	String req = "select * from promotion";
		 	PreparedStatement ps;
		 	ResultSet rs;
		 		try {
		 				ps=conn.prepareStatement(req);
		 		        rs=ps.executeQuery(req);
		 		       while (rs.next()) {
		 		    	  Promotion p = new Promotion(rs.getString(1), rs.getInt(2));
		 		    			  
		 		           listPromotion.add(p);
		 		            }
		 		           } catch (Exception e) {
		 		            e.printStackTrace();
		 		        }
		 		        return listPromotion;
		 			}*/
	 	
	 	// récuperer la liste des fournisseurs
	 	public static ObservableList<Fournisseur> getFournisseurList() {
	 		
		 	ObservableList<Fournisseur> listFournisseur =FXCollections.observableArrayList();
		 	Connection conn= DBConnection.getConnexion();
		 	String req = "select * from fournisseur";
		 	PreparedStatement ps;
		 	ResultSet rs;
		 		try {
		 				ps=conn.prepareStatement(req);
		 		        rs=ps.executeQuery(req);
		 		       while (rs.next()) {
		 		    	  Fournisseur f = new Fournisseur(rs.getString(1), rs.getString(2), rs.getString(3),rs.getInt(4), rs.getString(5));
		 		          listFournisseur.add(f);
		 		            }
		 		           } catch (Exception e) {
		 		            e.printStackTrace();
		 		        }
		 		        return listFournisseur;
		 			}
	
	 	 // récuperer la liste des caissiers 
	 	public static ObservableList<Caissier> getCaissierList() {
	 		
	 	ObservableList<Caissier> listCaissier =FXCollections.observableArrayList();
	 	Connection conn= DBConnection.getConnexion();
	 	String req = "select * from caissier";
	 	PreparedStatement ps;
	 	ResultSet rs;
	 		try {
	 				ps=conn.prepareStatement(req);
	 		        rs=ps.executeQuery(req);
	 		       while (rs.next()) {
	 		           Caissier c = new Caissier(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getTimestamp(8));
	 		          listCaissier.add(c);
	 		          
	 		            }
	 		           } catch (Exception e) {
	 		            e.printStackTrace();
	 		        }
	 		        return listCaissier;
	 			
	 			
	 			}	
	 	
	 	
	 	//récuperer la liste des clients
	 	public static ObservableList<Client> getClientList(){
	 		ObservableList<Client> listClient =FXCollections.observableArrayList();
		 	Connection conn= DBConnection.getConnexion();
		 	String req = "select * from client";
		 	PreparedStatement ps;
		 	ResultSet rs;
		 	try {
		 		ps=conn.prepareStatement(req);
 		        rs=ps.executeQuery(req);
 		       while (rs.next()) {
 		    	 Client c = new Client(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getTimestamp(5)); 
 		    	listClient.add(c);
 		       }
		 		
		 	}catch(Exception e) {
		 		e.printStackTrace();
		 	}
	 		return listClient;
	 	}
	 	
	 	//récuperer la liste des produits
	 	public static ObservableList<Product> getProductList(){
	 		ObservableList<Product> listProduct =FXCollections.observableArrayList();
		 	Connection conn= DBConnection.getConnexion();
		 	String req = "select * from product";
		 	PreparedStatement ps;
		 	ResultSet rs;
		 	try {
		 		ps=conn.prepareStatement(req);
 		        rs=ps.executeQuery(req);
 		       while (rs.next()) {
 		    	  Product p = new Product(rs.getString(1),rs.getString(2), rs.getDouble(3), rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getInt(8));
 		    	listProduct.add(p);
 		       }
		 		
		 	}catch(Exception e) {
		 		e.printStackTrace();
		 	}
	 		return listProduct;
	 	}
	 	// chercher produit par nom 
	 	public static ObservableList<Product> ProductList(String nom){
	 		ObservableList<Product> listProduct =FXCollections.observableArrayList();
		 	Connection conn= DBConnection.getConnexion();
		 	String req = "select ProductID, ProductName, SellingPrice, Qte from product where ProductName LIKE '" +nom+ "%'";
		 	PreparedStatement ps;
		 	ResultSet rs;
		 	try {
		 		ps=conn.prepareStatement(req);
 		        rs=ps.executeQuery(req);
 		       while (rs.next()) {
 		    	  Product p = new Product();
 		    	p.setProductID(rs.getString(1));
 		    	p.setProductName(rs.getString(2));
 		    	//p.setProductCat(rs.getString(3));
 		    	p.setSellingPrice(rs.getDouble(3));
 		    	p.setQte(rs.getInt(4));
 		    	  listProduct.add(p);
 		       }
		 		
		 	}catch(Exception e) {
		 		e.printStackTrace();
		 	}
	 		return listProduct;
	 	}
	 	//chercher par nom
	 	public static ObservableList<Client> ClientList(String nom){
	 		ObservableList<Client> listClient =FXCollections.observableArrayList();
		 	Connection conn= DBConnection.getConnexion();
		 	String req = "select * from client where FirstName LIKE '" +nom+ "%'";
		 	PreparedStatement ps;
		 	ResultSet rs;
		 	try {
		 		ps=conn.prepareStatement(req);
 		        rs=ps.executeQuery(req);
 		       while (rs.next()) {
 		    	  //Client c = new Client();
 		    	  Client cl = new Client(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getTimestamp(5));
 		    	 listClient.add(cl);
 		       }
		 		
		 	}catch(Exception e) {
		 		e.printStackTrace();
		 	}
	 		return listClient;
	 	}
	 	//chercher cat par nom
	 	public static ObservableList<Categories> CatList(String nom){
	 		ObservableList<Categories> listCat =FXCollections.observableArrayList();
		 	Connection conn= DBConnection.getConnexion();
		 	String req = "select * from category where catName LIKE '" +nom+ "%'";
		 	PreparedStatement ps;
		 	ResultSet rs;
		 	try {
		 		ps=conn.prepareStatement(req);
 		        rs=ps.executeQuery(req);
 		       while (rs.next()) {
 		    	  Categories p = new Categories();
 		    	  p.setIdCat(rs.getString(1));
 		    	  p.setCatName(rs.getString(2));
 		    	
 		    	
 		    	 listCat.add(p);
 		       }
		 		
		 	}catch(Exception e) {
		 		e.printStackTrace();
		 	}
	 		return listCat;
	 	}
	 	
	 	
	 	
	  //AJOUTER une nv catégorie  
	public static void  addCategory(Categories cat) {
	   try {
	    Connection conn= DBConnection.getConnexion();
	    PreparedStatement ps = conn.prepareStatement("insert into category values (?,?)");
	    ps.setString(1, cat.getIdCat());
	    ps.setString(2, cat.getCatName());
	    ps.executeUpdate();
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	// nv promo
	public static void  addPromo(Promotion p) {
		try {
		    Connection conn= DBConnection.getConnexion();
		    PreparedStatement ps = conn.prepareStatement("insert into promotion values (?,?)");
		    ps.setString(1, p.getIdPromo());
		    ps.setInt(2, p.getPourcentage());
		    ps.executeUpdate();
		        } catch (SQLException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		
	}
	
	//ajouter un nouveau caissier
	public static void  addCassier(Caissier ca) {
		   try {
		    Connection conn= DBConnection.getConnexion();
		    PreparedStatement ps = conn.prepareStatement("insert into caissier values (?,?,?,?,?,?,?,?)");
		    ps.setString(1, ca.getID());
		    ps.setString(2, ca.getFirstName());
		    ps.setString(3, ca.getLastName());
		    ps.setInt(4, ca.getPhoneNbr());
		    ps.setString(5, ca.getUsername());
		    ps.setString(6, ca.getPassword());
		    ps.setDouble(7, ca.getSalary());
		    ps.setTimestamp(8, ca.getDateEmbauche());
		    
		    ps.executeUpdate();
		        } catch (SQLException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		    }
	
	//ajouter un nouveau client
		public static void  addClient(Client client) {
			try {
				Connection conn= DBConnection.getConnexion();
			    PreparedStatement ps = conn.prepareStatement("insert into client values (?,?,?,?,?)");
			    ps.setString(1, client.getID());
			    ps.setString(2, client.getFirstName());
			    ps.setString(3, client.getLastName());
			    ps.setInt(4, client.getPhoneNbr());
			    ps.setTimestamp(5,client.getDateInscrit());
			    ps.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		// ajouter fournisseur
		public static void  addFournisseur(Fournisseur fourni) {
			try {
				Connection conn= DBConnection.getConnexion();
			    PreparedStatement ps = conn.prepareStatement("insert into fournisseur values (?,?,?,?,?)");
			    ps.setString(1, fourni.getID());
			    ps.setString(2, fourni.getFirstName());
			    ps.setString(3, fourni.getLastName());
			    ps.setInt(4, fourni.getPhoneNbr());
			    ps.setString(5,fourni.getAdresse());
			    ps.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	//ajouter un nouveau produit
	public static void  addProduct(Product produit) {
	try {
	Connection conn= DBConnection.getConnexion();
	PreparedStatement ps = conn.prepareStatement("insert into product values (?,?,?,?,?,?,?,?,?)");
    ps.setString(1, produit.getProductID());
    ps.setString(2, produit.getProductName());
    ps.setDouble(3, produit.getBuyingPrice());
    ps.setDouble(4, produit.getSellingPrice());
    ps.setString(5,produit.getProductCat());
    ps.setString(6, produit.getFournisseur());
    ps.setInt(7, produit.getQte());
    ps.setInt(8, produit.getRemise());
    ps.setInt(9, produit.getCount());
    ps.executeUpdate();
	}catch(SQLException e) {
		e.printStackTrace();
	}
	}

	 public static void deleteCategory(Categories c) {
	        try {
	        	Connection conn= DBConnection.getConnexion();
	           PreparedStatement ps = conn.prepareStatement("Delete from category where idCat = ? ");
	           ps.setString(1, c.getIdCat());
	            ps.executeUpdate();
	        } catch (SQLException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
	 public static void deletePromo(Promotion p) {
	        try {
	        	Connection conn= DBConnection.getConnexion();
	           PreparedStatement ps = conn.prepareStatement("Delete from promotion where idPromo = ? ");
	           ps.setString(1, p.getIdPromo());
	            ps.executeUpdate();
	        } catch (SQLException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
	 public static void deleteProduct(Product p) {
	        try {
	        	Connection conn= DBConnection.getConnexion();
	           PreparedStatement ps = conn.prepareStatement("Delete from product where productID = ? ");
	           ps.setString(1, p.getProductID());
	            ps.executeUpdate();
	        } catch (SQLException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
	 
	 public static void deleteClient(Client c) {
		 try {
	        	Connection conn= DBConnection.getConnexion();
	           PreparedStatement ps = conn.prepareStatement("Delete from client where id= ? ");
	           ps.setString(1, c.getID());
	            ps.executeUpdate();
	        } catch (SQLException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	 }
	 
	 public static void deleteFournisseur(Fournisseur f) {
		 try {
	        	Connection conn= DBConnection.getConnexion();
	           PreparedStatement ps = conn.prepareStatement("Delete from fournisseur where id= ? ");
	           ps.setString(1, f.getID());
	            ps.executeUpdate();
	        } catch (SQLException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	 }
	 
	 
	 
	 public static void deleteCaissier(Caissier c) {
	        try {
	        	Connection conn= DBConnection.getConnexion();
	           PreparedStatement ps = conn.prepareStatement("Delete from caissier where id= ? ");
	           ps.setString(1, c.getID());
	            ps.executeUpdate();
	        } catch (SQLException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
	 //recupérer l'id et le nom des catégories en une liste de string
	 public static ObservableList<String> categoryToString(){
		 ObservableList<String> categorie = FXCollections.observableArrayList();
		 try {
			 Connection conn= DBConnection.getConnexion();
			 PreparedStatement ps = conn.prepareStatement("select idCat, catName from category ");
			 ResultSet rs;
			 rs=ps.executeQuery();
			 while(rs.next()) {
				 String cat= rs.getString(1) + " | " + rs.getString(2);
				 categorie.add(cat);
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 return categorie;
	 }
	 
	 public static ObservableList<Integer> promotionToString(){
		 ObservableList<Integer> promotion = FXCollections.observableArrayList();
		 try {
			 Connection conn= DBConnection.getConnexion();
			 PreparedStatement ps = conn.prepareStatement("select pourcentage from promotion ");
			 ResultSet rs;
			 rs=ps.executeQuery();
			 while(rs.next()) {
				 int p= rs.getInt(1);
				 promotion.add(p);
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 return promotion;
	 }
	 
	 
	 public static ObservableList<String> fournisseurToString(){
		ObservableList<String> fourni = FXCollections.observableArrayList();
		try {
			Connection conn= DBConnection.getConnexion();
			PreparedStatement ps = conn.prepareStatement("select iD, firstName, lastName from fournisseur");
			ResultSet rs;
			 rs=ps.executeQuery();
			 while(rs.next()) {
				 String fou= rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3);
				 fourni.add(fou);
			 }
			 
		}catch(Exception e) {
			 e.printStackTrace();
		 }
		return fourni;
	 }
	 
	  public static Categories getCategoryById(String id) {
		  ObservableList<Categories> categorie = FXCollections.observableArrayList();
		  try {
				 Connection conn= DBConnection.getConnexion();
				 PreparedStatement ps = conn.prepareStatement("select * from category where idCat = '"+id+"'");
				 ResultSet rs;
				 rs=ps.executeQuery();
				 while(rs.next()) {
					Categories cat = new Categories(rs.getString(1), rs.getString(2)); 
					categorie.add(cat);
				 }
				 
		  }
		  catch(Exception e) {
				 e.printStackTrace();
	  }
		  return categorie.get(0);
	  }
	  
	  public static Promotion getPromotionByPourcentage(int pourcentage) {
		  ObservableList<Promotion> promo = FXCollections.observableArrayList();
		  try {
				 Connection conn= DBConnection.getConnexion();
				 PreparedStatement ps = conn.prepareStatement("select pourcentage from promotion where pourcentage = '"+pourcentage+"'");
				 ResultSet rs;
				 rs=ps.executeQuery();
				 while(rs.next()) {
					 Promotion pp= new Promotion(rs.getInt(1));
				     promo.add(pp);
				 }
				 
		  }
		  catch(Exception e) {
				 e.printStackTrace();
	  }
		  return promo.get(0);
	  }
	  
	  
	  public static Fournisseur getFournisseurById(String id) {
		 ObservableList<Fournisseur> fourni = FXCollections.observableArrayList();
		 try {
			 Connection conn= DBConnection.getConnexion();
			 PreparedStatement ps = conn.prepareStatement("select * from fournisseur where iD = '"+id+"'");
			 ResultSet rs;
			 rs=ps.executeQuery();
			 while(rs.next()) {
				 Fournisseur f = new Fournisseur(rs.getString(1), rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5)); 
				 fourni.add(f);
				 }
			 
		 }catch(Exception e) {
			 e.printStackTrace();
  }
		 return fourni.get(0);
	  }
	  
	  // chercher product par nom et prix
	  public static ObservableList<Product> Filtrewithname(String nom, Double mn, Double Mx) {
	        ObservableList<Product> list_product = FXCollections.observableArrayList();
	        try {
	        	Connection conn= DBConnection.getConnexion();
	            
	        	PreparedStatement ps = conn.prepareStatement("select * from product where ProductName LIKE '" +nom+ "%' and SellingPrice between ? and ?");
	        	
	        	ps.setDouble(1, mn);
	        	ps.setDouble(2, Mx);
	        	ResultSet rs;
				 rs=ps.executeQuery();
	           
	            while (rs.next()) {
	                Product pr = new Product(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4),
	                		rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
	                list_product.add(pr);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return list_product;
	    }
	  
	  // recherche produit par prix
	  public static ObservableList<Product> Filtrewithoutname(Double mn, Double Mx) {
	        ObservableList<Product> list_product = FXCollections.observableArrayList();
	        try {
	        	Connection conn= DBConnection.getConnexion();
	        	PreparedStatement ps = conn.prepareStatement("select * from product where SellingPrice between ? and ?");
	        	ps.setDouble(1, mn);
	        	ps.setDouble(2, Mx);
	            ResultSet rs;
				 rs=ps.executeQuery();
	            
	            while (rs.next()) {
	                Product pr = new Product(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4),
	                		rs.getString(5), rs.getString(6), rs.getInt(7),rs.getInt(8));
	                list_product.add(pr);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list_product;
}
	  public static ObservableList<Product> getProductByCategory(String c) {
	        ObservableList<Product> list_product = FXCollections.observableArrayList();
	        try {
	        	Connection conn= DBConnection.getConnexion();
	        	PreparedStatement ps = conn.prepareStatement("select * from product where ProductCat = ?");
	        	ps.setString(1, c);
	        	ResultSet rs;
				 rs=ps.executeQuery();
	            while (rs.next()) {
	                Product pr = new Product(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4),
	                		rs.getString(5), rs.getString(6), rs.getInt(7));
	                list_product.add(pr);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list_product;
	    }
	  
	  public static void makeDiscount(String id, Double newvalue) {
	        try {
	        	Connection conn= DBConnection.getConnexion();
	        	PreparedStatement ps = conn.prepareStatement("update product set SellingPrice = ? where ProductID = ? ");
	        	ps.setDouble(1, newvalue);
	        	ps.setString(2, id);
	        	ps.executeUpdate();
	        	
	        }
	        catch (Exception e1) {

	            e1.printStackTrace();
	        }
	  }
	  
	  public static Product getProdByID(String id) {
	        String req = "select * from Product where ProductID = '" + id + "'";
	        ObservableList<Product> list_product = FXCollections.observableArrayList();
	        try {
	        	Connection conn= DBConnection.getConnexion();
	        	PreparedStatement ps = conn.prepareStatement(req);
	            ResultSet rs;
				rs=ps.executeQuery();
	            while (rs.next()) {
	                Product p = new Product(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4),
	                		rs.getString(5), rs.getString(6), rs.getInt(7));
	                list_product.add(p);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list_product.get(0);
	    }
	  
	  public static ObservableList<String> getCategoryName(){
		  ObservableList<String> listCat = FXCollections.observableArrayList(); 
		  try {
			  Connection conn= DBConnection.getConnexion();
			  PreparedStatement ps = conn.prepareStatement("select catName from category");
			  ResultSet rs;
				rs=ps.executeQuery();
				while(rs.next()) {
					String cat = rs.getString(1)+" ";
					listCat.add(cat);
				}
		  }
		  catch (Exception e) {
	            e.printStackTrace();
	        }
		  return listCat;
	  }
	   
}


