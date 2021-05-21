package model;

import java.sql.Timestamp;

public class Facture {
	private int id;
	private String ProductID;
	private String idCaisier;
	private Double totalAmount;
	private Timestamp date;
	private String Qte;
	private String nomClient;
	public Facture() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Facture(int id, String productID, String idCaisier, Double totalAmount, Timestamp date, String qte,
			String nomClient) {
		super();
		this.id = id;
		ProductID = productID;
		this.idCaisier = idCaisier;
		this.totalAmount = totalAmount;
		this.date = date;
		Qte = qte;
		this.nomClient = nomClient;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getIdCaisier() {
		return idCaisier;
	}
	public void setIdCaisier(String idCaisier) {
		this.idCaisier = idCaisier;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getQte() {
		return Qte;
	}
	public void setQte(String qte) {
		Qte = qte;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	
	
	

}
