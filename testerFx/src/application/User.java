package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Caissier;

public class User {
	public static Caissier c = new Caissier() ;
	public static ObservableList<String> buygetdata = FXCollections.observableArrayList();
	public static double totalAmount ;
	public static double change;
	public static double payamount;
	public static String slipno;
}
