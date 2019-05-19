package yearClock;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class YearClock extends Application implements MouseInputListener
{

	Scene clockScene;
	BorderPane clockPane;
	Pane pane;
	Button btnEvent;
	Label lbl;
	ClockAni drawBG;
	Canvas drawLoop;
	EventCreator t;

	public static void main(String[] args)
	{
		launch();
	}// main

	@Override
	public void start(Stage window)
	{
		drawBG = new ClockAni();
		drawLoop = new Canvas(1000, 1000);
		clockPane = new BorderPane();
		clockScene = new Scene(clockPane);
		btnEvent = new Button("Create Event");
		lbl = new Label();
		
		t = new EventCreator(drawLoop, drawBG);

		pane = new Pane(drawBG, drawLoop);
		lbl.setPrefSize(200, 200);
		lbl.setStyle("-fx-background-color: #888888;");
		btnEvent.setOnAction(e -> {
			
			t.mouseWaiting = true;
			new Thread(t).start();
			drawLoop.setOnMouseClicked(f -> {
				t.mouseWaiting = false;
				display();
			});
			
			
		});
		
		btnEvent.setAlignment(Pos.CENTER);
		clockPane.setRight(btnEvent);
		clockPane.setLeft(lbl);
		clockPane.setCenter(pane);

		// window.setMaximized(true);
		// window.setFullScreen(true);
		window.setOnCloseRequest(e ->
		{
			e.consume();
			closewindow();
		});
		window.setTitle("YearClock");
		window.setScene(clockScene);
		window.show();
		
	}// start
	
private void display() {
		
		Stage window = new Stage();
		Button add = new Button("+");
		TextField txt = new TextField(t.day + "");
		Button sub = new Button("-");
		Button btnCreate = new Button("Create Event");
		Button btnCancel = new Button("Cancel");
		BorderPane layout = new BorderPane();
		HBox hbox = new HBox();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Event Creator");
		window.setResizable(false);
		add.setOnAction(e -> {
			t.day++;
			t.loop(t.gc);
			txt.setText(t.day + "");
		});
		sub.setOnAction(e -> {
			t.day--;
			txt.setText(t.day + "");
		});
		
		
		btnCreate.setOnAction(e -> {
			window.close();
		});
		btnCancel.setOnAction(e ->{
			window.close();
		});
		layout.setLeft(add);
		layout.setCenter(txt);
		layout.setRight(sub);
		layout.setBottom(hbox);
		hbox.getChildren().addAll(btnCreate, btnCancel);
		window.setScene(new Scene(layout));
		window.showAndWait();
		
	}

	private void closewindow()
	{
		ConfirmBox.display("Closing", "Are you Sure?");
		if (ConfirmBox.confirm)
		{
			Platform.exit();
		}//if
	}//closewindow

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}// class YearClock