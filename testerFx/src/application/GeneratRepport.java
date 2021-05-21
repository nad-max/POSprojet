package application;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DailyRepport;
import model.DailyyTable;
import model.DayItem;
import model.Facture;
import model.Product;
import model.Vente;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;



public class GeneratRepport {
	
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	public void generateDailyReport() throws SQLException, JRException {
		String pattern = "yyyy-MM-dd";
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("Today is .......  " + today);
		// get today total sale amount
		String totalSale = "select totalAmount from facture where date LIKE '"+today+"%'";
		 con = DBConnection.getConnexion();
		 ps = con.prepareStatement(totalSale);
		 rs=ps.executeQuery();
		double totalAmount = 0;
		while(rs.next()) {
			totalAmount= totalAmount + rs.getDouble(1);
		}
		//get today total sale count
		String countQuery="select Qte from facture where date LIKE '"+today+"%'";
		con = DBConnection.getConnexion();
		ps = con.prepareStatement(countQuery);
		rs=ps.executeQuery();
		int itemCount = 0;
		while(rs.next()) {
			String tempCount = rs.getString(1);
			String[] tempAry = tempCount.split(",");
			for( int i = 0 ; i < tempAry.length  ; i++) {
				System.out.println("count is : "+tempAry[i]);
				itemCount = itemCount + Integer.parseInt(tempAry[i]);
			}
		}
		System.out.println("Total sale item count : "+itemCount);
		//get category from db
		ObservableList<String> categoryList = FXCollections.observableArrayList();
		String getCagetoryListQuery = "select catName from category";
		con = DBConnection.getConnexion();
		ps = con.prepareStatement(getCagetoryListQuery);
		rs=ps.executeQuery();
		while(rs.next()) {
			categoryList.add(rs.getString(1));
		}
		//create array for category name, count , total sale amount 
		String [] categoryNameAry = new String[categoryList.size()];
		int [] categoryCountAry = new int [categoryList.size()];
		Double [] categoryTotalAmountAry = new Double[categoryList.size()];
		
		//add item to category name array
		for(int a = 0 ; a < categoryNameAry.length; a++) {
			categoryNameAry[a] = categoryList.get(a);
			categoryCountAry[a] = 0;
			categoryTotalAmountAry[a] = 0.0 ;
		}
		
		ObservableList<String> barcodeAryList = FXCollections.observableArrayList();
		ObservableList<String> qtyAryList = FXCollections.observableArrayList();
		
		//get barcode and find category type
		String getFactureBarcodeQuery="select ProductID, Qte from facture where date LIKE '"+today+"%'";
		con = DBConnection.getConnexion();
		ps = con.prepareStatement(getFactureBarcodeQuery);
		rs=ps.executeQuery();
		while(rs.next()) {
			String barcode = rs.getString(1);
			String qty =rs.getString(2);
			String[] barcodeAry = barcode.split(",");
			String[] qtyAry = qty.split(",");
			for(int e = 0 ; e < barcodeAry.length; e++) {
				barcodeAryList.add(barcodeAry[e]);
				qtyAryList.add(qtyAry[e]);
			}
		}//end while
		
		for(int i = 0 ; i < barcodeAryList.size(); i++) {
			String getCategoryQuery =" select category.catName, product.SellingPrice from category, product where product.ProductID ='"+barcodeAryList.get(i)+"' and product.ProductCat=category.idCat";
			con = DBConnection.getConnexion();
			ps = con.prepareStatement(getCategoryQuery);
			rs=ps.executeQuery();
			while(rs.next()) {
				String categoryName = rs.getString(1);
				Double price = rs.getDouble(2);
				for(int k = 0 ; k < categoryNameAry.length; k++) {
					if(categoryNameAry[k].equals(categoryName)) {
						categoryCountAry[k] = categoryCountAry[k] + Integer.parseInt(qtyAryList.get(i));
						categoryTotalAmountAry[k] = categoryTotalAmountAry[k] + price*Integer.parseInt(qtyAryList.get(i));
					}//end if
					
				}//end for k
			}//end while
		}//end for i
		
		double initialsale = 0;
		double promotion = 0 ;
		/* List to hold Items */
		List<DailyRepport> listItems = new ArrayList<DailyRepport>();
		for(int f = 0 ; f < categoryNameAry.length; f++) {
			DailyRepport dr = new DailyRepport();
			dr.setCategoryname(categoryNameAry[f]);
			dr.setSalecount(categoryCountAry[f]);
			dr.setCategorytotalamount(categoryTotalAmountAry[f]);
			listItems.add(dr);
			initialsale = initialsale + categoryTotalAmountAry[f]  ;
		}
        promotion = initialsale - totalAmount;
		
		////////////
		//for daily item sale table 
		ObservableList<String> barcodeList = FXCollections.observableArrayList();
		String getProduct ="select ProductID from product";
		con = DBConnection.getConnexion();
		ps = con.prepareStatement(getProduct);
		rs=ps.executeQuery();
		while(rs.next()) {
			barcodeList.add(rs.getString(1));
		}
		
		String[] barcodeAry = new String[barcodeList.size()];
		String[] barcodeCountAry = new String[barcodeList.size()];
		String[] barcodePriceAry = new String[barcodeList.size()];
		String[] barcodeCategoryAry = new String[barcodeList.size()];
		String[] barcodeNameAry = new String[barcodeList.size()];
		
		//add data to count 
		for( int b = 0 ; b < barcodeCountAry.length; b++) {
			barcodeCountAry[b] = "0";
			barcodeAry[b] = barcodeList.get(b);
		}
		
		//barcodeArrayList & qty array list
		ObservableList<String> barcodeArrayList = FXCollections.observableArrayList();
		ObservableList<String> qtyArrayList = FXCollections.observableArrayList();
		
		String qq="select ProductID, Qte from facture where date LIKE '"+today+"%'";
		con = DBConnection.getConnexion();
		ps = con.prepareStatement(qq);
		rs=ps.executeQuery();
		while(rs.next()) {
			String barcodeTemp = (rs.getString(1));
			String qtyTodayTemp = rs.getString(2);
			
			String[] barcodeTempAry = barcodeTemp.split(",");
			String[] qtyTodayTempAry = qtyTodayTemp.split(",");
			for(int w = 0 ; w < barcodeTempAry.length; w++) {
				barcodeArrayList.add(barcodeTempAry[w]);
				qtyArrayList.add(qtyTodayTempAry[w]);
			}//end for
			
		}//end while
		for(int m =0 ; m < barcodeAryList.size(); m++) {
			String bCode = barcodeAryList.get(m);
			
			for(int l = 0; l < barcodeAry.length; l++) {
				if(bCode.equals(barcodeAry[l])){
					barcodeCountAry[l] = "" + (Integer.parseInt(barcodeCountAry[l]) + Integer.parseInt(qtyArrayList.get(m)) );
				}//end of if
			}//end of inner for
		}//end of for
		
		//get category name and price in ordering
		for(int g = 0 ; g < barcodeAry.length; g ++) {
			String gett="select category.catName, product.SellingPrice, product.ProductName from category, product where product.ProductID= '"+barcodeAry[g]+"' and product.ProductCat=category.idCat";
			con = DBConnection.getConnexion();
			ps = con.prepareStatement(gett);
			rs=ps.executeQuery();
			while(rs.next()) {
				String cName = rs.getString(1);
				Double cPrice = rs.getDouble(2);
				String pName = rs.getString(3);
				barcodeCategoryAry[g] = cName;
				barcodePriceAry[g] = cPrice.toString();
				barcodeNameAry[g] = pName;
			}
		}//end for
		
		List<DayItem> listItems1 = new ArrayList<DayItem>();
		for(int v = 0 ; v < barcodeAry.length; v++) {
			DayItem dit = new DayItem();
			dit.setBarcode(barcodeAry[v]);
			dit.setCategory(barcodeCategoryAry[v]);
			dit.setName(barcodeNameAry[v]);
			dit.setSalecount(barcodeCountAry[v]);
			Double amount = Integer.parseInt(barcodeCountAry[v]+"") * Double.parseDouble(barcodePriceAry[v]); 
			dit.setSaleamount(amount+"");
			
			listItems1.add(dit);
		}
		
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		/* Convert List to JRBeanCollectionDataSource */
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
		JRBeanCollectionDataSource itemsJRBean1 = new JRBeanCollectionDataSource(listItems1);
		
		param.put("CategoryDataset", "HELLO");
		param.put("ItemDataset", "HELLO");
		param.put("DS", itemsJRBean);
		param.put("DSItem", itemsJRBean1);
		param.put("totalsaleamount", Double.parseDouble(""+totalAmount));
		param.put("totalsaleitemcount", Integer.parseInt(""+itemCount));
		param.put("initialsale", ""+initialsale);
		param.put("netsale", ""+totalAmount);
		param.put("promotion",""+promotion );
		
		File outDir = new File("E:\\mes cours 3éme année\\PFE");
		outDir.mkdirs();
		
		JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\NadaMedini\\git\\projet\\testerFx\\src\\application\\DailyReport.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		//JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File("").getAbsolutePath() +"/src/application/DailyReport.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
				exporter.setExporterInput(exporterInput);
				// ExporterOutput
				OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
						"E:\\mes cours 3éme année\\PFE\\DailyReport.pdf");
				// Output
				exporter.setExporterOutput(exporterOutput);
				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				exporter.setConfiguration(configuration);
				exporter.exportReport();

				JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
				jasperViewer.setVisible(true);
				jasperViewer.setFitPageZoomRatio();
				jasperViewer.setTitle("INGENIOUS LABS POS System: Printing service");
	}//end fnt
	
	public void generateMonthlyReport() throws SQLException, JRException{
		
		String pattern = "yyyy-MM-dd";
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("Today is .......  " + today);
		
		//get month
		String [] todayAry = today.split("-");
		String currentMonth = todayAry[1];
		String totalSale = "select totalAmount from facture where date LIKE '%"+currentMonth+"%'";
		 con = DBConnection.getConnexion();
		 ps = con.prepareStatement(totalSale);
		 rs=ps.executeQuery();
		double totalAmount = 0;
		while(rs.next()) {
			totalAmount= totalAmount + rs.getDouble(1);
		}
		
		
		String countQuery="select Qte from facture where date LIKE '%"+currentMonth+"%'";
		con = DBConnection.getConnexion();
		 PreparedStatement pss = con.prepareStatement(countQuery);
		 ResultSet rss;
		rss=pss.executeQuery();
		int itemCount = 0;
		while(rss.next()) {
			String tempCount = rss.getString(1);
			String[] tempAry = tempCount.split(",");
			for( int i = 0 ; i < tempAry.length  ; i++) {
				System.out.println("count is : "+tempAry[i]);
				itemCount = itemCount + Integer.parseInt(tempAry[i]);
			}
		}
		
		//get category from db
		ObservableList<String> categoryList = FXCollections.observableArrayList();
		String getCagetoryListQuery = "select catName from category";
		con = DBConnection.getConnexion();
		ps = con.prepareStatement(getCagetoryListQuery);
		rs=ps.executeQuery();
		while(rs.next()) {
			categoryList.add(rs.getString(1));
		}
		//create array for category name, count , total sale amount 
				String [] categoryNameAry = new String[categoryList.size()];
				int [] categoryCountAry = new int [categoryList.size()];
				Double [] categoryTotalAmountAry = new Double[categoryList.size()];
				
				//add item to category name array
				for(int a = 0 ; a < categoryNameAry.length; a++) {
					categoryNameAry[a] = categoryList.get(a);
					categoryCountAry[a] = 0;
					categoryTotalAmountAry[a] = 0.0 ;
				}
		
				ObservableList<String> barcodeAryList = FXCollections.observableArrayList();
				ObservableList<String> qtyAryList = FXCollections.observableArrayList();
				
				//get barcode and find category type
				String getFactureBarcodeQuery="select ProductID, Qte from facture where date LIKE '%"+currentMonth+"%'";
				con = DBConnection.getConnexion();
				ps = con.prepareStatement(getFactureBarcodeQuery);
				rs=ps.executeQuery();
				while(rs.next()) {
					String barcode = rs.getString(1);
					String qty =rs.getString(2);
					String[] barcodeAry = barcode.split(",");
					String[] qtyAry = qty.split(",");
					for(int e = 0 ; e < barcodeAry.length; e++) {
						barcodeAryList.add(barcodeAry[e]);
						qtyAryList.add(qtyAry[e]);
					}
				}//end while
		
				for(int i = 0 ; i < barcodeAryList.size(); i++) {
					String getCategoryQuery =" select category.catName, product.SellingPrice from category, product where product.ProductID ='"+barcodeAryList.get(i)+"' and product.ProductCat=category.idCat";
					con = DBConnection.getConnexion();
					ps = con.prepareStatement(getCategoryQuery);
					rs=ps.executeQuery();
					while(rs.next()) {
						String categoryName = rs.getString(1);
						Double price = rs.getDouble(2);
						for(int k = 0 ; k < categoryNameAry.length; k++) {
							if(categoryNameAry[k].equals(categoryName)) {
								categoryCountAry[k] = categoryCountAry[k] + Integer.parseInt(qtyAryList.get(i));
								categoryTotalAmountAry[k] = categoryTotalAmountAry[k] + price*Integer.parseInt(qtyAryList.get(i));
							}//end if
							
						}//end for k
					}//end while
				}//end for i
				
				double initialsale = 0;
				double promotion = 0 ;
				/* List to hold Items */
				List<DailyRepport> listItems = new ArrayList<DailyRepport>();
				for(int f = 0 ; f < categoryNameAry.length; f++) {
					DailyRepport dr = new DailyRepport();
					dr.setCategoryname(categoryNameAry[f]);
					dr.setSalecount(categoryCountAry[f]);
					dr.setCategorytotalamount(categoryTotalAmountAry[f]);
					listItems.add(dr);
					initialsale = initialsale + categoryTotalAmountAry[f]  ;
				}
		        promotion = initialsale - totalAmount;
				
				////////////
				//for daily item sale table 
				ObservableList<String> barcodeList = FXCollections.observableArrayList();
				String getProduct ="select ProductID from product";
				con = DBConnection.getConnexion();
				ps = con.prepareStatement(getProduct);
				rs=ps.executeQuery();
				while(rs.next()) {
					barcodeList.add(rs.getString(1));
				}
				
				String[] barcodeAry = new String[barcodeList.size()];
				String[] barcodeCountAry = new String[barcodeList.size()];
				String[] barcodePriceAry = new String[barcodeList.size()];
				String[] barcodeCategoryAry = new String[barcodeList.size()];
				String[] barcodeNameAry = new String[barcodeList.size()];
				
				//add data to count 
				for( int b = 0 ; b < barcodeCountAry.length; b++) {
					barcodeCountAry[b] = "0";
					barcodeAry[b] = barcodeList.get(b);
				}
				
				//barcodeArrayList & qty array list
				ObservableList<String> barcodeArrayList = FXCollections.observableArrayList();
				ObservableList<String> qtyArrayList = FXCollections.observableArrayList();
				
				String qq="select ProductID, Qte from facture where date LIKE '%"+currentMonth+"%'";
				con = DBConnection.getConnexion();
				ps = con.prepareStatement(qq);
				rs=ps.executeQuery();
				while(rs.next()) {
					String barcodeTemp = (rs.getString(1));
					String qtyTodayTemp = rs.getString(2);
					
					String[] barcodeTempAry = barcodeTemp.split(",");
					String[] qtyTodayTempAry = qtyTodayTemp.split(",");
					for(int w = 0 ; w < barcodeTempAry.length; w++) {
						barcodeArrayList.add(barcodeTempAry[w]);
						qtyArrayList.add(qtyTodayTempAry[w]);
					}//end for
					
				}//end while
				for(int m =0 ; m < barcodeAryList.size(); m++) {
					String bCode = barcodeAryList.get(m);
					
					for(int l = 0; l < barcodeAry.length; l++) {
						if(bCode.equals(barcodeAry[l])){
							barcodeCountAry[l] = "" + (Integer.parseInt(barcodeCountAry[l]) + Integer.parseInt(qtyArrayList.get(m)) );
						}//end of if
					}//end of inner for
				}//end of for
		
				//get category name and price in ordering
				for(int g = 0 ; g < barcodeAry.length; g ++) {
					String gett="select category.catName, product.SellingPrice, product.ProductName from category, product where product.ProductID= '"+barcodeAry[g]+"' and product.ProductCat=category.idCat";
					con = DBConnection.getConnexion();
					ps = con.prepareStatement(gett);
					rs=ps.executeQuery();
					while(rs.next()) {
						String cName = rs.getString(1);
						Double cPrice = rs.getDouble(2);
						String pName = rs.getString(3);
						barcodeCategoryAry[g] = cName;
						barcodePriceAry[g] = cPrice.toString();
						barcodeNameAry[g] = pName;
					}
				}//end for
				
				List<DayItem> listItems1 = new ArrayList<DayItem>();
				for(int v = 0 ; v < barcodeAry.length; v++) {
					DayItem dit = new DayItem();
					dit.setBarcode(barcodeAry[v]);
					dit.setCategory(barcodeCategoryAry[v]);
					dit.setName(barcodeNameAry[v]);
					dit.setSalecount(barcodeCountAry[v]);
					Double amount = Integer.parseInt(barcodeCountAry[v]+"") * Double.parseDouble(barcodePriceAry[v]); 
					dit.setSaleamount(amount+"");
					
					listItems1.add(dit);
				}
				///////////
				
				//for daily sale items
				//days array
				String[] daysAry = new String[31];
				String[] daysAmountAry = new String[31];
				String[] daysCountAry = new String[31];
				//set data to  arrays
				for(int z =0 ; z < daysAry.length; z++) {
					daysAry[z] = String.format("%02d", (z+1));
					daysAmountAry[z] = 0+""; 
					daysCountAry[z] = "0";
				}
				
				//get daily purchase in this month
				String getDailyPurchaseQuery="select date, totalAmount from facture where date LIKE '%"+currentMonth+"%'";
				con = DBConnection.getConnexion();
				ps = con.prepareStatement(getDailyPurchaseQuery);
				rs=ps.executeQuery();
				while(rs.next()) {
					Timestamp dateTemp =rs.getTimestamp(1);
					Double totalAmountTemp = rs.getDouble(2);
					
					String daate = dateTemp.toString();
					String[] dateTempAry = daate.split("-");
					
					for(int r =0 ; r < daysAry.length; r++) {
						if(dateTempAry[0].equals(daysAry[r])) {
							daysAmountAry[r] = "" + (Double.parseDouble(daysAmountAry[r]) + totalAmountTemp);
						}//end if
					}//end for
					
				}//end while
				
				//get the total count , initial sale and discount
				for(int s =0 ; s < daysAry.length; s++) {
					String getTotalCountQueryy= "select Qte from facture where date LIKE '%"+currentMonth+"-"+daysAry[s]+"%'";
					// con = DBConnection.getConnexion();
					 PreparedStatement pps = con.prepareStatement(getTotalCountQueryy);
					 ResultSet rrs;
					rrs=pps.executeQuery();
					while(rrs.next()) {
						String coulntTemppp = rrs.getString(1);
						String[] countTempAryyy = coulntTemppp.split(",");
						for(int c =0 ; c < countTempAryyy.length; c++) {
							daysCountAry[s] = "" + ( Integer.parseInt(daysCountAry[s]) + Integer.parseInt(countTempAryyy[c]));
						}//end of inner for
					}
				}//end for
				
				List<DailyyTable> listItems2 = new ArrayList<DailyyTable>();
				
				//adding data object
				for(int q =0; q < daysAry.length; q ++) {
					DailyyTable dat = new DailyyTable();
					dat.setDate(daysAry[q]+"/"+currentMonth);
					dat.setItemcount(daysCountAry[q]);
					dat.setNetsale(daysAmountAry[q]);
					
					listItems2.add(dat);
				}
				
				
				////////////
				
				HashMap<String, Object> param = new HashMap<String, Object>();
				/* Convert List to JRBeanCollectionDataSource */
				JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
				JRBeanCollectionDataSource itemsJRBean1 = new JRBeanCollectionDataSource(listItems1);
				JRBeanCollectionDataSource itemsJRBean2 = new JRBeanCollectionDataSource(listItems2);
				
				param.put("CategoryDataset", "HELLO");
				param.put("ItemDataset", "HELLO");
				param.put("DS", itemsJRBean);
				param.put("DSItem", itemsJRBean1);
				param.put("DS1", itemsJRBean2);
				param.put("totalsaleamount", Double.parseDouble(""+totalAmount));
				param.put("totalsaleitemcount", Integer.parseInt(""+itemCount));
				param.put("initialsale", ""+initialsale);
				param.put("netsale", ""+totalAmount);
				param.put("promotion",""+promotion );
				param.put("categorysale","Item Sale" );
				
				File outDir = new File("E:\\mes cours 3éme année\\PFE");
				outDir.mkdirs();
				JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\NadaMedini\\git\\projet\\testerFx\\src\\application\\MonthlyReport.jrxml");
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				//JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File("").getAbsolutePath() +"/src/application/DailyReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
				JRPdfExporter exporter = new JRPdfExporter();

				ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
				// ExporterInput
						exporter.setExporterInput(exporterInput);
						// ExporterOutput
						OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
								"E:\\mes cours 3éme année\\PFE\\MonthlyReport.pdf");
						// Output
						exporter.setExporterOutput(exporterOutput);
						SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
						exporter.setConfiguration(configuration);
						exporter.exportReport();

						JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
						jasperViewer.setVisible(true);
						jasperViewer.setFitPageZoomRatio();
						jasperViewer.setTitle("INGENIOUS LABS POS System: Printing service");
		
		
		
		
		
		
	}//end fnt
	
	public void generateFacture(ObservableList<Vente> sale) throws JRException {
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		List<Vente> listItems = new ArrayList<Vente>();
		for (int i = 0; i < sale.size(); i++) {
			Vente sa = new Vente();
			sa.setProductName(sale.get(i).getProductName());
			sa.setSellingPrice(sale.get(i).getSellingPrice());
			sa.setQte(sale.get(i).getQte());
			sa.setRemise(sale.get(i).getRemise());
			sa.setTotalAmount(sale.get(i).getTotalAmount());

			listItems.add(sa);
		}
		
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
		String pattern = "yyyy-MM-dd hh:mm:ss";
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("purhcase time is : " + today);
		
		
		param.put("ItemDataSource", "HELLO");
		param.put("DS1", itemsJRBean);
		param.put("cashiername", User.c.getFirstName());
		param.put("total", User.totalAmount);
		param.put("pay", User.payamount);
		param.put("change", User.change);
		param.put("date", today);
		param.put("slipno", User.slipno);
		
		File outDir = new File("E:\\mes cours 3éme année\\PFE");
		outDir.mkdirs();
		
		JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\NadaMedini\\git\\projet\\testerFx\\src\\application\\Facture.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		//JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File("").getAbsolutePath() +"/src/application/DailyReport.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
				exporter.setExporterInput(exporterInput);
				// ExporterOutput
				OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
						"E:\\mes cours 3éme année\\PFE\\Facturet.pdf");
				// Output
				exporter.setExporterOutput(exporterOutput);
				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				exporter.setConfiguration(configuration);
				exporter.exportReport();

				JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
				jasperViewer.setVisible(true);
				jasperViewer.setFitPageZoomRatio();
				jasperViewer.setTitle("INGENIOUS LABS POS System: Printing service");
		
				User.buygetdata.clear();
				User.totalAmount = 0;
				User.change = 0;
				User.payamount = 0;
		
	}//end fnt
	
	
	public void generatePopularItems() throws SQLException, JRException {
		
		ObservableList<Product> popularData = FXCollections.observableArrayList();
		String query="select product.ProductID, product.ProductName, product.SellingPrice, product.Qte, product.count from product ORDER BY product.count DESC LIMIT 10";
		con = DBConnection.getConnexion();
		PreparedStatement pps=con.prepareStatement(query);
		ResultSet result;
		result=pps.executeQuery();
		while(result.next()) {
			Product product = new Product();
			product.setProductID(result.getString(1));
			product.setProductName(result.getString(2));
			//product.setProductCat(result.getString(3));
			product.setSellingPrice(result.getDouble(3));
			product.setQte(result.getInt(4));
			product.setCount(result.getInt(5));
			popularData.add(product);
		}
		
		HashMap<String, Object> param = new HashMap<String, Object>();
    JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(popularData);

		
		param.put("PopularDataset", "HELLO");
		param.put("DS1", itemsJRBean);
		
		File outDir = new File("E:\\mes cours 3éme année\\PFE");
		outDir.mkdirs();
		
		JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\NadaMedini\\git\\projet\\testerFx\\src\\application\\PopularItem.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		//JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File("").getAbsolutePath() +"/src/application/DailyReport.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
				exporter.setExporterInput(exporterInput);
				// ExporterOutput
				OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
						"E:\\mes cours 3éme année\\PFE\\PopularItem.pdf");
				// Output
				exporter.setExporterOutput(exporterOutput);
				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				exporter.setConfiguration(configuration);
				exporter.exportReport();

				JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
				jasperViewer.setVisible(true);
				jasperViewer.setFitPageZoomRatio();
				jasperViewer.setTitle("INGENIOUS LABS POS System: Printing service");
	
	
	
	}//end fnt
	
	
	
}
