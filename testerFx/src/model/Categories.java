package model;

public class Categories {
private String idCat;
private String catName;




public Categories() {
	super();
	// TODO Auto-generated constructor stub
}





public Categories(String idCat, String catName) {
	super();
	this.idCat = idCat;
	this.catName = catName;
}





public String getIdCat() {
	return idCat;
}


public void setIdCat(String idCat) {
	this.idCat = idCat;
}


public String getCatName() {
	return catName;
}


public void setCatName(String catName) {
	this.catName = catName;
}


}
