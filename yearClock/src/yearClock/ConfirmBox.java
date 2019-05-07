package yearClock;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox
{
	public static boolean confirm;
	
	public static boolean display(String title, String label) {
		Stage window = new Stage();
		GridPane layout = new GridPane();
		Scene scene = new Scene(layout);
		Label lbl = new Label(label);
		Button btnYes = new Button("Yes");
		Button btnNo = new Button("No");
		
		
		btnYes.setOnAction(e ->{
			confirm = true;
			window.close();
		});
		btnNo.setOnAction(e ->{
			confirm = false;
			window.close();
		});
		
		btnYes.setMinWidth(50);
		btnNo.setMinWidth(50);
		GridPane.setHalignment(lbl, HPos.CENTER);
		layout.setHgap(20);
		layout.setVgap(20);
		layout.setPadding(new Insets(20, 80, 20, 80));
		layout.setAlignment(Pos.CENTER);
		layout.add(lbl, 0, 0, 2, 1);
		layout.add(btnYes, 0, 1);
		layout.add(btnNo, 1, 1);
		
		
		
		window.setResizable(false);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setScene(scene);
		window.setTitle(title);
		
		window.showAndWait();
		
		return confirm;
	}
}
