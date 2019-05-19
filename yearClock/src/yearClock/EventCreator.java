package yearClock;
import java.awt.MouseInfo;
import java.util.Date;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.time.LocalDate;

public class EventCreator implements Runnable {

	public boolean mouseWaiting;
	Canvas canvas;
	Canvas canvasSave;
	GraphicsContext gc;
	GraphicsContext gs;
	public EventCreator(Canvas canvas, Canvas canvasSave) {
		this.canvas = canvas;
		this.canvasSave = canvasSave;
	}
	
	@Override
	public void run() {
		gc = canvas.getGraphicsContext2D();
		gs = canvasSave.getGraphicsContext2D();
		
		loop(gc);
		gs.setFill(Color.CRIMSON);
		gs.fillOval(circlePosX-25, circlePosY-25, 50, 50);
		gs.fillPolygon(triangleX,  triangleY, 3);
			
	}//run
	
	
	
	
	
	
	int day;
	double[] triangleX = {0,0,0};
	double[] triangleY = {0,0,0};
	double circlePosX = 0;
	double circlePosY = 0;
	void loop(GraphicsContext gc) {
		
		gc.setFill(Color.CRIMSON);
		double mouseX = 0;
		double mouseY = 0;
		double circleX = 0;
		double circleY = 0;
		double dayAngle = 0;
		double angle = 0;
		double triVOffset = 0.0465;
		
		do{
			if(mouseWaiting) {
			//AVOIDS CREATING A BLACK HOLE IN YOUR CPU
			try {
					Thread.sleep(20);
				} catch (Exception e) {
			}//trycatch
				
			//mouse coordinates on canvas
			mouseX = MouseInfo.getPointerInfo().getLocation().x-canvas.localToScreen(0, 0).getX();
			mouseY = MouseInfo.getPointerInfo().getLocation().y-canvas.localToScreen(0, 0).getY();
			}
			//mouse coordinates on circle
			circleX = mouseX-500;
			circleY = mouseY-500;
			
			dayAngle = Math.toRadians(ClockAni.degOfOneDay);
			//mouse pos angle to circle, adding 2   (math magic)
			angle = Math.atan2(circleY, circleX) + Math.PI/2;
			if(angle != Math.abs(angle)) {
				angle = 2*Math.PI-Math.abs(angle);
			}

			//rounding angle to dayAngle this and adding halv to step though days
			angle = angle - (angle % dayAngle) + dayAngle/2;
			
			
			//event triangle drawer
			//triVOffset = 0.0465;
			triangleX[0] = Math.cos(angle-Math.PI/2)*420+500;
			triangleX[1] = Math.cos(angle-triVOffset-Math.PI/2)*446+500;
			triangleX[2]= Math.cos(angle+triVOffset-Math.PI/2)*446+500;
			triangleY[0] = Math.sin(angle-Math.PI/2)*420+500;
			triangleY[1] = Math.sin(angle-triVOffset-Math.PI/2)*446+500;
			triangleY[2] = Math.sin(angle+triVOffset-Math.PI/2)*446+500;
			
			
			//final circle position (except subtracting circle W/H in draw function)
			circlePosX = Math.cos(angle-Math.PI/2)*460+500;
			circlePosY = Math.sin(angle-Math.PI/2)*460+500;
			
			day = (int)Math.round(angle/dayAngle + 0.5);
			
			
			//REDRAW
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			
			//DRAW EVENT CIRCLE
			gc.fillOval(circlePosX-25, circlePosY-25, 50, 50);
			
			//DRAW EVENT TRIANGLE
			gc.fillPolygon(triangleX,  triangleY, 3);
			
			
			
				
		}while (mouseWaiting); //while
		
	}
	
	
}//MyRunnable
