package yearClock;
import javafx.scene.Scene;
import javafx.scene.control.Button; //   <--- import the right Button!!!!
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class EventCreatorWindow
{
	
	
	
	//variables
	public static String eventString;
	
	
	public static String display() {
		Stage window = new Stage();
		Button add = new Button("+");
		TextField txt = new TextField();
		Button sub = new Button("-");
		Button btnCreate = new Button("Create Event");
		Button btnCancel = new Button("Cancel");
		BorderPane layout = new BorderPane();
		HBox hbox = new HBox();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Event Creator");
		window.setResizable(false);
		
		
		
		btnCreate.setOnAction(e -> {
			eventString = "Event Created!";
			window.close();
		});
		btnCancel.setOnAction(e ->{
			eventString = "Event Canceled";
			window.close();
		});
		layout.setLeft(add);
		layout.setCenter(txt);
		layout.setRight(sub);
		layout.setBottom(hbox);
		hbox.getChildren().addAll(btnCreate, btnCancel);
		window.setScene(new Scene(layout));
		window.showAndWait();
		
		return eventString;
	}
}//class EventCreator