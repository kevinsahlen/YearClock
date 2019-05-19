package yearClock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ClockAni extends Canvas{

	//private Image clockbg;                                    TODO


	static int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	static int yearDays = Year.of(currentYear).length();
	static double degOfOneDay = Math.toDegrees(Math.PI * 2 / yearDays);
	//variables for clockGraphics()
	private Color cSeason[] =
		{
			Color.rgb( 32,  32, 255).darker(),	//JAN
			Color.rgb( 32,  32, 255),			//FEB
			Color.rgb( 32, 255,  32),			//MAR
			Color.rgb( 32, 255,  32).darker(),	//APR
			Color.rgb( 32, 255,  32),			//MAJ
			Color.rgb(240, 240,  32),			//JUN
			Color.rgb(240, 240,  32).darker(),	//JUL
			Color.rgb(240, 240,  32),			//AGU
			Color.rgb(255, 128,  32),			//SEP
			Color.rgb(255, 128,  32).darker(),	//OKT
			Color.rgb(255, 128,  32),			//NOV
			Color.rgb( 32,  32, 255)			//DEC
		};
	private Color cMonth[] = {
			Color.rgb(200, 200, 200),
			Color.rgb(255, 255, 255)
	};
	private String sMonth[] =
		{
				"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"
		};
	
	
	
	
	ClockAni() {
		//clockbg = new Image("res/clock.png");                 TODO
		setWidth(1000);
		setHeight(1000);
		clockGraphics();
	}// constructor

	private void clockGraphics() {

		GraphicsContext gc = getGraphicsContext2D();

		gc.clearRect(0, 0, getWidth(), getHeight());
		gc.setFill(Color.rgb(20,20,20));
		gc.fillRect(0, 0, getWidth(), getHeight());
		gc.setLineCap(StrokeLineCap.BUTT);
		
//SET FONT AND DRAW YEAR NUMBER
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font("Impact Regular", 32));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFill(Color.WHITE);
		gc.setLineWidth(2);
		
		gc.fillText(currentYear+"",500, 500);
		
		
		
//LOOP MONTHS
		int dayOfYear = 0;
		for (int i = 0; i <= 11; i++) {
			
			
			gc.setStroke(cSeason[i]);
			gc.setLineWidth(60);
			//gc.strokeArc(170, 170, 660, 660, 90-degOfOneDay*dayOfYear, -degOfOneDay*monthLength(i)-0.1, ArcType.OPEN);
//DRAW SEASON ARC
			arcDrawer(gc, 330, 90-degOfOneDay*dayOfYear, -degOfOneDay*monthLength(i));
			
			double txtrad = monthLength(i)/2*Math.toRadians(degOfOneDay);
			if (i%2 == 0) {
				gc.setStroke(cMonth[1]);
				gc.setFill(cMonth[1]);
			}
			else {
				gc.setFill(cMonth[0]);
				gc.setStroke(cMonth[0]);
			}
			gc.setLineWidth(2);
//DRAW MONTH TEXT
			gc.fillText(sMonth[i], Math.cos(Math.toRadians(degOfOneDay)*dayOfYear-Math.PI/2+txtrad)*260+500, Math.sin(Math.toRadians(degOfOneDay)*dayOfYear-Math.PI/2+txtrad)*260+500);
			
			
			gc.setLineWidth(25);
			//gc.strokeArc(130, 130, 740, 740, 89.9-degOfOneDay*dayOfYear, -degOfOneDay*monthLength(i)-0.1, ArcType.OPEN);
//DRAW MONTH ARC
			arcDrawer(gc, 370, 89.9-degOfOneDay*dayOfYear, -degOfOneDay*monthLength(i));
			
			
			
//DAY LOOP
			for (int j = 1; j <= YearMonth.of(currentYear, i + 1).lengthOfMonth(); j++) {
				
				
//ALTERNATE MONTH / SEASON
				if (dayOfYear%2 == 0) {
					gc.setStroke(cSeason[i]);
				}
				else {
//ALTERNATE MONTH WHITE / GRAY
					if (i%2 == 0) {
						gc.setStroke(cMonth[1]);
					}
					else {
						gc.setStroke(cMonth[0]);
					}
				}
				//gc.strokeArc(150, 150, 700, 700, 90-degOfOneDay*dayOfYear, -degOfOneDay-0.1, ArcType.OPEN);
//DRAW DAY ARCS
				arcDrawer(gc, 430, 90-degOfOneDay*dayOfYear, -degOfOneDay);
				
//ALTERNATE WEEKNUMBER
				String pmon;
				if (i>8) {
					pmon = ""+(i+1);
				} else {
					pmon = "0"+(i+1);
				}
				String pday;
				if (j>9) {
					pday = ""+j;
				} else {
					pday = "0"+j;
				}
				String input = currentYear+""+pmon+""+pday;
				String format = "yyyyMMdd";

				SimpleDateFormat df = new SimpleDateFormat(format);
				Date date;
				try {
					date = df.parse(input);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int week = cal.get(Calendar.WEEK_OF_YEAR);

					
					if (week%2 == 0) {
						gc.setStroke(Color.rgb(40, 45, 50));
						gc.setFill(Color.rgb(60, 75, 100));
						
					} else {
						gc.setStroke(Color.rgb(60, 75, 100));
						gc.setFill(Color.rgb(40, 45, 50));
					}
					gc.setLineWidth(40);
					//gc.strokeArc(100, 100, 800, 800, 90-degOfOneDay*dayOfYear, -degOfOneDay-0.3, ArcType.OPEN);
//DRAW WEEK ARCS
					arcDrawer(gc, 400, 90-degOfOneDay*dayOfYear, -degOfOneDay);
					gc.setLineWidth(25);
					
//WEEK TEXT
					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
						gc.setLineWidth(2);
//DRAW WEEK TEXT
						gc.fillText(week+"", Math.cos(Math.toRadians(degOfOneDay)*dayOfYear-Math.toRadians(degOfOneDay)*2.5-Math.PI/2)*400+500,
											Math.sin(Math.toRadians(degOfOneDay)*dayOfYear-Math.toRadians(degOfOneDay)*2.5-Math.PI/2)*400+500);
						gc.setLineWidth(25);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}//try
				
				
				dayOfYear++;
			} // for j
		} // for i
		
		//DRAW CLOCK INDICATOR
		gc.setStroke(Color.DARKRED);
		gc.setLineWidth(2);
		gc.strokeLine(500, 500, Math.cos(Math.PI*2/yearDays*Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - Math.PI/2 - Math.PI*2/yearDays/2) * 350 + 500,
								Math.sin(Math.PI*2/yearDays*Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - Math.PI/2 - Math.PI*2/yearDays/2) * 350 + 500);
		
		
	}// clockGraphics()
	
	void arcDrawer(GraphicsContext gc, double radie, double startAngle, double arcExtent) {
		gc.strokeArc(500-radie, 500-radie, radie*2, radie*2, startAngle, arcExtent, ArcType.OPEN);
	}
	
	int monthLength(int month) {
		
		month = YearMonth.of(Calendar.getInstance().get(Calendar.YEAR), month + 1).lengthOfMonth();
		
		return month;
	}
}// ClockAni
