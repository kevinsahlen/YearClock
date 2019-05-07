package yearClock;

import javafx.scene.Scene;
import javafx.scene.control.Button; //   <--- import the right Button!!!!

import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventCreator
{
	
	
	
	//variables
	public static String eventString;
	
	
	
	public static String display() {
		Stage window = new Stage();
		Button btnCreate = new Button("Create Event");
		Button btnCancel = new Button("Cancel");
		HBox layout = new HBox(10); // spacing between buttons = 10
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Event Creator");
		window.setResizable(false);
		
		
		
		btnCreate.setOnAction(e -> {
			eventString = "Event Created!!";
			window.close();
		});
		btnCancel.setOnAction(e ->{
			eventString = "Event Canceled";
			window.close();
		});
		
		
		
		layout.getChildren().addAll(btnCreate, btnCancel);
		window.setScene(new Scene(layout));
		window.showAndWait();
		
		return eventString;
	}
}//class EventCreator
