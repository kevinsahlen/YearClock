package yearClock;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.event.MouseInputListener;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
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
	static EventCreator t;

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
		Label lbl = new Label("Set date for you event");
		Label yearlbl = new Label(ClockAni.currentYear+"");
		Button add = new Button("+");
		Button sub = new Button("-");
		Button btnCreate = new Button("Create Event");
		Button btnCancel = new Button("Cancel");
		TextField txt = new TextField(t.ld.getDayOfMonth() + "");
		ComboBox<String> cb = new ComboBox<String>();
		cb.getItems().addAll(drawBG.sMonth);
		
		
		
		cb.setEditable(true);
		
		cb.valueProperty().addListener(new ChangeListener<String>() {
			int cbday = YearClock.t.ld.getDayOfMonth();
      @Override public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
      	
      	int month;
      	switch (t1)
      	{
      		case "JAN":
      			month = 1;
      			break;

      		case "FEB":
      			month = 2;
      			break;

      		case "MAR":
      			month = 3;
      			break;

      		case "APR":
      			month = 4;
      			break;

      		case "MAY":
      			month = 5;
      			break;

      		case "JUN":
      			month = 6;
      			break;

      		case "JUL":
      			month = 7;
      			break;

      		case "AUG":
      			month = 8;
      			break;

      		case "SEP":
      			month = 9;
      			break;

      		case "OCT":
      			month = 10;
      			break;

      		case "NOV":
      			month = 11;
      			break;

      		case "DEC":
      			month = 12;
      			break;

      		default:
      			month = 1;
      			System.out.println("error: no month picked");
      			break;
      	}
      	YearClock.t.ld = LocalDate.of(ClockAni.currentYear, month, cbday);
      	YearClock.t.update();
      	
      } 
  });
		
		cb.setOnAction((event) -> {
			int month;
    	switch (cb.getSelectionModel().getSelectedItem())
    	{
    		case "JAN":
    			month = 1;
    			break;

    		case "FEB":
    			month = 2;
    			break;

    		case "MAR":
    			month = 3;
    			break;

    		case "APR":
    			month = 4;
    			break;

    		case "MAY":
    			month = 5;
    			break;

    		case "JUN":
    			month = 6;
    			break;

    		case "JUL":
    			month = 7;
    			break;

    		case "AUG":
    			month = 8;
    			break;

    		case "SEP":
    			month = 9;
    			break;

    		case "OCT":
    			month = 10;
    			break;

    		case "NOV":
    			month = 11;
    			break;

    		case "DEC":
    			month = 12;
    			break;

    		default:
    			month = 1;
    			System.out.println("error: no month picked");
    			break;
    	}
    	System.out.println(month);
    	System.out.println(t.ld.getDayOfMonth());
    	t.ld = LocalDate.of(ClockAni.currentYear, month, t.ld.getDayOfMonth());
    	t.day = t.ld.getDayOfYear();
    	t.update();
	});
		
		
		add.setOnAction(e -> {
			t.day++;
			if (t.day > 365)
			{
				t.day = 1;
			}
			
			t.update();
			txt.setText(t.ld.getDayOfMonth() + "");
		});
		sub.setOnAction(e -> {
			t.day--;
			if (t.day < 1)
			{
				t.day = 365;
			}
			t.update();
			txt.setText(t.ld.getDayOfMonth() + "");
		});
		
		
		
		
		txt.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent event) {
          if (event.getCode() == KeyCode.ENTER) {
              int dateStr = Integer.parseInt(txt.getText());
              
              System.out.println(dateStr);
              t.ld = LocalDate.of(ClockAni.currentYear, t.ld.getMonthValue(), dateStr);
              t.day = t.ld.getDayOfYear();
              t.update();
          }
      }
  });
		
		
		
		
		btnCreate.setOnAction(e -> {
			t.createEvent();
			window.close();
		});
		btnCancel.setOnAction(e ->{
			t.clearUpper();
			window.close();
		});
		
		
//		add
//		sub
//		btnCreate
//		btnCancle
//		txt
//		cb
		
		cb.setValue("JAN");
		lbl.setTextAlignment(TextAlignment.CENTER);
		lbl.setAlignment(Pos.CENTER);
		lbl.setMinWidth(200);
		txt.setPrefWidth(30);
		GridPane gpane = new GridPane();
		gpane.setVgap(10);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(50);
    ColumnConstraints column3 = new ColumnConstraints();
		column3.setPercentWidth(50);
    ColumnConstraints column4 = new ColumnConstraints();
    column4.setPercentWidth(50);
    
    gpane.getColumnConstraints().addAll(column1, column2, column3, column4);
		
		gpane.setGridLinesVisible(true);
		gpane.setAlignment(Pos.CENTER);
		gpane.add(lbl, 0, 0, 4, 1);
		gpane.add(add, 0, 1);
		gpane.add(yearlbl, 1, 1);
		gpane.add(cb, 2, 1);
		gpane.add(txt, 3, 1);
		gpane.add(sub, 4, 1);
		gpane.add(btnCreate, 1, 2);
		gpane.add(btnCancel, 2, 2);

		
		
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Event Creator");
		window.setResizable(false);
		window.setScene(new Scene(gpane));
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









/* 

int day = t.ld.getDayOfMonth();
int month;
switch (arg2)
{
	case "JAN":
		month = 1;
		break;

	case "FEB":
		month = 2;
		break;

	case "MAR":
		month = 3;
		break;

	case "APR":
		month = 4;
		break;

	case "MAY":
		month = 5;
		break;

	case "JUN":
		month = 6;
		break;

	case "JUL":
		month = 7;
		break;

	case "AUG":
		month = 8;
		break;

	case "SEP":
		month = 9;
		break;

	case "OCT":
		month = 10;
		break;

	case "NOV":
		month = 11;
		break;

	case "DEC":
		month = 12;
		break;

	default:
		month = 1;
		System.out.println("error: no month picked");
		break;
}
t.ld = LocalDate.of(ClockAni.currentYear, month, day);
t.update();

*/