package model;

public class DailyRepport {
	private String categoryname;
	private Integer salecount;
	private Double categorytotalamount;
	public DailyRepport() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DailyRepport(String categoryname, Integer salecount, Double categorytotalamount) {
		super();
		this.categoryname = categoryname;
		this.salecount = salecount;
		this.categorytotalamount = categorytotalamount;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public Integer getSalecount() {
		return salecount;
	}
	public void setSalecount(Integer salecount) {
		this.salecount = salecount;
	}
	public Double getCategorytotalamount() {
		return categorytotalamount;
	}
	public void setCategorytotalamount(Double categorytotalamount) {
		this.categorytotalamount = categorytotalamount;
	}
	
	
}
