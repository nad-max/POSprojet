package application;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.sf.jasperreports.engine.JRException;

public class ReportController {

	@FXML
    private Button btnDaily;
	
	@FXML
    private Button btnMonthly;
	@FXML
    private Button btnPopulaire;
	
	public void onDailyAction(ActionEvent a) throws SQLException, JRException {
		new GeneratRepport().generateDailyReport();
		
	}
	public void onMonthlyAction(ActionEvent a) throws SQLException, JRException {
		
		new GeneratRepport().generateMonthlyReport();
		
	}
	public void onPopulrItemAction(ActionEvent a) throws SQLException, JRException {
		
		new GeneratRepport().generatePopularItems();
	}
}
