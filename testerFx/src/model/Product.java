package model;

public class Product {
	
	private String productID;
    private String productName;
    private Double buyingPrice;
    private Double sellingPrice;
    private String productCat;
    private String fournisseur;
    private int qte;
    private int remise;
    private int count;
    
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Product(String productID, String productName, Double buyingPrice, Double sellingPrice, String productCat,
			String fournisseur, int qte) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.buyingPrice = buyingPrice;
		this.sellingPrice = sellingPrice;
		this.productCat = productCat;
		this.fournisseur = fournisseur;
		this.qte = qte;
	}


	public Product(String productID, String productName, Double buyingPrice, Double sellingPrice, String productCat,
			String fournisseur, int qte, int remise) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.buyingPrice = buyingPrice;
		this.sellingPrice = sellingPrice;
		this.productCat = productCat;
		this.fournisseur = fournisseur;
		this.qte = qte;
		this.remise = remise;
	}

	public Product(String productID, String productName, Double buyingPrice, Double sellingPrice, String productCat,
			String fournisseur, int qte, int remise, int count) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.buyingPrice = buyingPrice;
		this.sellingPrice = sellingPrice;
		this.productCat = productCat;
		this.fournisseur = fournisseur;
		this.qte = qte;
		this.remise = remise;
		this.count = count;
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

	public Double getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(Double buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getProductCat() {
		return productCat;
	}

	public void setProductCat(String productCat) {
		this.productCat = productCat;
	}

	public String getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public int getRemise() {
		return remise;
	}

	public void setRemise(int remise) {
		this.remise = remise;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
   
    
    
	
	
}
