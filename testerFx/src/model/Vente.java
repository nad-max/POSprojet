package model;

public class Vente {
	private String productID;
	private String productName;
	private Double sellingPrice;
	private int qte;
	private Double totalAmount;
	private int remise;
	
	
	
	public Vente() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Vente(String productID, String productName, Double sellingPrice, int qte, Double totalAmount, int remise) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.sellingPrice = sellingPrice;
		this.qte = qte;
		this.totalAmount = totalAmount;
		this.remise = remise;
	}



	public String getProductID() {
		return productID;
	}



	public void setProductID(String productID) {
		this.productID = productID;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public Double getSellingPrice() {
		return sellingPrice;
	}



	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}



	public int getQte() {
		return qte;
	}



	public void setQte(int qte) {
		this.qte = qte;
	}



	public Double getTotalAmount() {
		return totalAmount;
	}



	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}



	public int getRemise() {
		return remise;
	}



	public void setRemise(int remise) {
		this.remise = remise;
	}


	
	 

}
