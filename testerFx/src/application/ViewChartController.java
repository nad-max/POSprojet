package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

public class ViewChartController {
	
	@FXML 
	private AnchorPane ch_dailySale;
	@FXML 
	private AnchorPane ch_category;
	@FXML 
	private AnchorPane caissierChart;
	
	
	
	
	public void dailyChart(ActionEvent a) throws SQLException {
		showingDailyChart();
		showingCategoryChart();
		showingCaissierChart();
	}
	
	private void showingDailyChart() throws SQLException {
		String pattern = "yyyy-MM-dd";
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("Today is .......  " + today);

		// current month
		String[] tempAry = today.split("-");
		String currentmonth = tempAry[0]+ "-" + tempAry[1] ;
		//+ "/" + tempAry[2];
		System.out.println("current month is .......  " + currentmonth);
		
		String date = "select DISTINCT date from facture where date like '" + currentmonth + "%'";
		Connection con = DBConnection.getConnexion();
		PreparedStatement ps = con.prepareStatement(date);
		ResultSet rs;
		 rs=ps.executeQuery();
		 ObservableList<Timestamp> dateAry = FXCollections.observableArrayList();
		 while (rs.next()) {

				dateAry.add(rs.getTimestamp(1));
			}
		 ObservableList<Timestamp> finalDateAry = FXCollections.observableArrayList();

			for (int i = 0; i < dateAry.size(); i++) {
				finalDateAry.addAll(dateAry.get(i));
			}
			
			ObservableList<Integer> finalAmountAry = FXCollections.observableArrayList();
			con = DBConnection.getConnexion();
			for (int j = 0; j < dateAry.size(); j++) {
				double totalAmount = 0;
			    String totalquery = "select totalAmount from facture where date = '"+ dateAry.get(j)+ "'";	
			    System.out.println("total amount1 is  ......  " + totalAmount);
			    ps=con.prepareStatement(totalquery);
			    rs= ps.executeQuery();
			    while(rs.next()) {
			    	totalAmount = totalAmount + rs.getDouble(1); 
			    	System.out.println("total amount2 is  ......  " + totalAmount);
			    }
			    finalAmountAry.add((int) totalAmount);
				System.out.println("total amount3 is  ......  " + totalAmount);
			}//end for
			
			
			// Defining the x axis
			NumberAxis xAxis = new NumberAxis(1, 31, 2);
			xAxis.setLabel("Jours");
			// Defining the y axis
			NumberAxis yAxis = new NumberAxis(5000, 100000, 10000);
			
			yAxis.setLabel("Montant de vente");
			// Creating the line chart
			LineChart linechart = new LineChart(xAxis, yAxis);
			linechart.setTitle("Vente quotidienne");
			// Prepare XYChart.Series objects by setting data
			XYChart.Series series1 = new XYChart.Series();
			series1.setName("Progression quotidienne des ventes au cours de ce mois");
			for (int k = 0; k < dateAry.size(); k++) {
				System.out.println(" datee "+finalDateAry.get(k));
				String datee = finalDateAry.get(k).toString();
				int newdatee = Integer.parseInt(datee.substring(8, 10));
				System.out.println(" newdatee "+ newdatee);
				series1.getData().add(new XYChart.Data(newdatee, finalAmountAry.get(k)));
			}
			linechart.getData().add(series1);
			linechart.setAnimated(true);
			linechart.animatedProperty();

			linechart.setPrefHeight(515);
			linechart.setMinHeight(515);
			linechart.setMaxHeight(515);

			linechart.setPrefWidth(400);
			linechart.setMinWidth(400);
			linechart.setMaxWidth(400);

			linechart.setPrefSize(510, 300);
			linechart.setMinSize(510, 300);
			linechart.setMaxSize(510, 300);

			ch_dailySale.getChildren().add(linechart);

			System.out.println("Daily Sale chart is generated...");
			
			
	}
	
	private void showingMonthlyChart() throws SQLException {
		String pattern = "yyyy-MM-dd";
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("Today is .......  " + today);
		
		String[] tempAry = today.split("-");
		String currentYear = tempAry[0];
		System.out.println("current year is .......  " + currentYear);
		// month list
	    ObservableList<String> monthList = FXCollections.observableArrayList();
		ObservableList<String> monthTempList = FXCollections.observableArrayList();
		// get data from database
		String getMonth= "select DISTINCT date from facture where date like '%- "+currentYear+ "'";
		Connection con = DBConnection.getConnexion();
		PreparedStatement ps = con.prepareStatement(getMonth);
		ResultSet rs;
		 rs=ps.executeQuery();
		 while(rs.next()) {
			 
		 }
	}
	
	private void showingCategoryChart() throws SQLException{
		
		ObservableList<String> categoryNameList = FXCollections.observableArrayList();
		ObservableList<String> categoryIDList = FXCollections.observableArrayList();
        
		// get category from database
		String catQuery = "select idCat, catName from category";
		Connection con = DBConnection.getConnexion();
		PreparedStatement ps = con.prepareStatement(catQuery);
		ResultSet rs;
		rs=ps.executeQuery();
		while (rs.next()) {
			categoryIDList.add(rs.getString(1));
			categoryNameList.add(rs.getString(2));
		}
		
		// category count list
				ObservableList<Integer> categoryCount = FXCollections.observableArrayList();
				for (int i = 0; i < categoryIDList.size(); i++) {
					String query =" select count from product where product.ProductCat = '"+ categoryIDList.get(i) + "'";
					con = DBConnection.getConnexion();
					int cCount = 0;
					ps = con.prepareStatement(query);
					rs=ps.executeQuery();
					while(rs.next()) {
						cCount = cCount + rs.getInt(1);
					}
					categoryCount.add(cCount);
				}
				
				ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
				// add pie chart data
				for (int j = 0; j < categoryNameList.size(); j++) {
					pieChartData.add(new PieChart.Data(categoryNameList.get(j), categoryCount.get(j)));
				}
				// Creating a Pie chart
				PieChart pieChart = new PieChart(pieChartData);
				// Setting the title of the Pie chart
				pieChart.setTitle("Category Sales");

				pieChart.setPrefHeight(500);
				pieChart.setMinHeight(400);
				pieChart.setMaxHeight(400);

				pieChart.setPrefWidth(400);
				pieChart.setMinWidth(300);
				pieChart.setMaxWidth(300);

				pieChart.setPrefSize(420, 300);
				pieChart.setMinSize(420, 300);
				pieChart.setMaxSize(420, 300);

				// setting the direction to arrange the data
				pieChart.setClockwise(true);

				// Setting the length of the label line
				pieChart.setLabelLineLength(50);

				// Setting the labels of the pie chart visible
				pieChart.setLabelsVisible(true);

				// Setting the start angle of the pie chart
				pieChart.setStartAngle(180);
				pieChart.setAnimated(true);
				pieChart.animatedProperty();

				ch_category.getChildren().add(pieChart);

				System.out.println("Category chart is generated... ");
		
	}
	private void showingCaissierChart() throws SQLException{
		
		String pattern = "yyyy-MM-dd";
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("Today is .......  " + today);

		// current month
		String[] tempAry = today.split("-");
		String currentmonth = tempAry[0]+ "-" + tempAry[1] ;
		//+ "/" + tempAry[2];
		System.out.println("current month is .......  " + currentmonth);
		
		String date = "select DISTINCT date from facture where date like '" + currentmonth + "%'";
		Connection con = DBConnection.getConnexion();
		PreparedStatement ps = con.prepareStatement(date);
		ResultSet rs;
		 rs=ps.executeQuery();
		 ObservableList<Timestamp> dateAry = FXCollections.observableArrayList();
		 while (rs.next()) {

				dateAry.add(rs.getTimestamp(1));
			}
		 ObservableList<Timestamp> finalDateAry = FXCollections.observableArrayList();

			for (int i = 0; i < dateAry.size(); i++) {
				finalDateAry.addAll(dateAry.get(i));
			}
			
			ObservableList<Integer> CaissierN = FXCollections.observableArrayList();
			ObservableList<Integer> CaissierM = FXCollections.observableArrayList();
			//ObservableList<Integer> CaissierCount = FXCollections.observableArrayList();
			for (int j = 0; j < dateAry.size(); j++) {
			String query="select COUNT(*) from facture  where date = '"+ dateAry.get(j)+ "' and idCaisier LIKE 'd60de1cf' ";
			ps = con.prepareStatement(query);
			 rs=ps.executeQuery();
			 while(rs.next()) {
				 CaissierN.add(rs.getInt(1));
			 }
			 String query1="select COUNT(*) from facture where date = '"+ dateAry.get(j)+ "' and idCaisier LIKE'3566c88b'";
			 PreparedStatement pss = con.prepareStatement(query1);
			 ResultSet rss;
			 rss=pss.executeQuery();
			 while(rss.next()) {
				 CaissierM.add(rss.getInt(1));
			 }
			
			}//end for
			System.out.println("nbr vente pour N "+CaissierN.size());
			System.out.println("nbr vente pour M "+CaissierM.size());
			
			int ii=CaissierN.size() - CaissierM.size();
			System.out.println("diff "+ii);
			
			final NumberAxis xAxis = new NumberAxis(1, 31, 1);
			xAxis.setLabel("Jours");
			final NumberAxis yAxis = new NumberAxis(0,100,10);
			yAxis.setLabel("Nombre de vente");
			final StackedAreaChart<Number, Number> sac = new StackedAreaChart<Number, Number>(xAxis, yAxis);
			sac.setTitle("Caissier DashBord ");
			XYChart.Series<Number, Number> seriesN = new XYChart.Series<Number, Number>();
			seriesN.setName("Nouha Medini");
			for(int k = 0; k < dateAry.size(); k++) {
				String datee = finalDateAry.get(k).toString();
				int newdatee = Integer.parseInt(datee.substring(8, 10));
				
				seriesN.getData().add(new XYChart.Data(newdatee,CaissierN.get(k)));
			}
			XYChart.Series<Number, Number> seriesM = new XYChart.Series<Number, Number>();
			seriesM.setName("Malek Mansouri");
			for(int t = 0; t < dateAry.size(); t++) {
				String datee = finalDateAry.get(t).toString();
				int newdatee = Integer.parseInt(datee.substring(8, 10));
				
				seriesM.getData().add(new XYChart.Data(newdatee,CaissierM.get(t)));
			}
			sac.getData().addAll(seriesN, seriesM);
			sac.setAnimated(true);
			sac.animatedProperty();

			sac.setPrefHeight(555);
			sac.setMinHeight(555);
			sac.setMaxHeight(555);

			sac.setPrefWidth(400);
			sac.setMinWidth(400);
			sac.setMaxWidth(400);

			sac.setPrefSize(555, 280);
			sac.setMinSize(555, 280);
			sac.setMaxSize(555, 280);
			
			caissierChart.getChildren().add(sac);
			System.out.println("Caissier chart is generated ...........");
	}

}
