package yearClock;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class YearClock extends Application
{

	Scene clockScene;
	BorderPane clockPane;
	Button btnEvent;
	Label lbl;
	BufferedImage clockbg;

	public static void main(String[] args)
	{
		launch();
	}// main

	@Override
	public void start(Stage window) throws Exception
	{
		clockbg = ImageIO.read(new File("res/ clock.png"));
		clockPane = new BorderPane();
		clockScene = new Scene(clockPane);
		btnEvent = new Button("Create Event");
		lbl = new Label();
		lbl.setPrefSize(200, 200);
		lbl.setStyle("-fx-background-color: #888888;");
		btnEvent.setOnAction(e -> lbl.setText(EventCreator.display()));
		btnEvent.setAlignment(Pos.CENTER);
		clockPane.setRight(btnEvent);
		clockPane.setLeft(lbl);

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

	private void closewindow()
	{
		ConfirmBox.display("Closing", "Are you Sure?");
		if (ConfirmBox.confirm)
		{
			Platform.exit();
		}//if
	}//closewindow
	
	
	
	private Canvas clockGraphics() {
		
		Canvas canvas = new Canvas();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.drawImage(clockbg, 0, 0);
		
		return canvas;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}// class YearClock