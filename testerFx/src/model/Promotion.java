package model;

public class Promotion {

	private String idPromo;
	private int pourcentage;
	private String prodName;
	private String prodId;
	public Promotion() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Promotion(String idPromo, int pourcentage, String prodName, String prodId) {
		super();
		this.idPromo = idPromo;
		this.pourcentage = pourcentage;
		this.prodName = prodName;
		this.prodId = prodId;
	}
	public Promotion(int pourcentage) {
		// TODO Auto-generated constructor stub
		this.pourcentage = pourcentage;
	}
	public String getIdPromo() {
		return idPromo;
	}
	public void setIdPromo(String idPromo) {
		this.idPromo = idPromo;
	}
	public int getPourcentage() {
		return pourcentage;
	}
	public void setPourcentage(int pourcentage) {
		this.pourcentage = pourcentage;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

}
