package yearClock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.Calendar;

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
	public String sMonth[] =
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

		GraphicsContext gxbg = getGraphicsContext2D();

		gxbg.clearRect(0, 0, getWidth(), getHeight());
		gxbg.setFill(Color.rgb(20,20,20));
		gxbg.fillRect(0, 0, getWidth(), getHeight());
		gxbg.setLineCap(StrokeLineCap.BUTT);
		
//SET FONT AND DRAW YEAR NUMBER
		gxbg.setTextBaseline(VPos.CENTER);
		gxbg.setFont(new Font("Impact Regular", 32));
		gxbg.setTextAlign(TextAlignment.CENTER);
		gxbg.setFill(Color.WHITE);
		gxbg.setLineWidth(2);
		
		gxbg.fillText(currentYear+"",500, 500);
		
		
		
//LOOP MONTHS
		int dayOfYear = 0;
		for (int i = 0; i <= 11; i++) {
			
			
			gxbg.setStroke(cSeason[i]);
			gxbg.setLineWidth(60);
//DRAW SEASON ARC
			arcDrawer(gxbg, 330, 90-degOfOneDay*dayOfYear, -degOfOneDay*monthLength(i));
			
			double txtrad = monthLength(i)/2*Math.toRadians(degOfOneDay);
			if (i%2 == 0) {
				gxbg.setStroke(cMonth[1]);
				gxbg.setFill(cMonth[1]);
			}
			else {
				gxbg.setFill(cMonth[0]);
				gxbg.setStroke(cMonth[0]);
			}
			gxbg.setLineWidth(2);
//DRAW MONTH TEXT
			gxbg.fillText(sMonth[i], Math.cos(Math.toRadians(degOfOneDay)*dayOfYear-Math.PI/2+txtrad)*260+500, Math.sin(Math.toRadians(degOfOneDay)*dayOfYear-Math.PI/2+txtrad)*260+500);
			
			
			gxbg.setLineWidth(25);
//DRAW MONTH ARC
			arcDrawer(gxbg, 370, 90-degOfOneDay*dayOfYear, -degOfOneDay*monthLength(i));
			
			
			
//DAY LOOP
			for (int j = 1; j <= YearMonth.of(currentYear, i + 1).lengthOfMonth(); j++) {
				
				
//ALTERNATE MONTH / SEASON
				if (dayOfYear%2 == 0) {
					gxbg.setStroke(cSeason[i]);
				}
				else {
//ALTERNATE MONTH WHITE / GRAY
					if (i%2 == 0) {
						gxbg.setStroke(cMonth[1]);
					}
					else {
						gxbg.setStroke(cMonth[0]);
					}
				}
//DRAW DAY ARCS
				arcDrawer(gxbg, 430, 90-degOfOneDay*dayOfYear, -degOfOneDay);
				
//ALTERNATE WEEKNUMBER
				LocalDate loopday = LocalDate.ofYearDay(currentYear, dayOfYear+1);

					int week = loopday.get(WeekFields.ISO.weekOfWeekBasedYear());
					
					if (week%2 == 0) {
						gxbg.setStroke(Color.rgb(40, 45, 50));
						gxbg.setFill(Color.rgb(60, 75, 100));
						
					} else {
						gxbg.setStroke(Color.rgb(60, 75, 100));
						gxbg.setFill(Color.rgb(40, 45, 50));
					}
					gxbg.setLineWidth(40);
//DRAW WEEK ARCS
					arcDrawer(gxbg, 400, 90-degOfOneDay*dayOfYear, -degOfOneDay);
					gxbg.setLineWidth(25);
					
//WEEK TEXT
					if (loopday.getDayOfWeek() == DayOfWeek.SUNDAY) {
						gxbg.setLineWidth(2);
//DRAW WEEK TEXT
						gxbg.fillText(week+"", Math.cos(Math.toRadians(degOfOneDay)*dayOfYear-Math.toRadians(degOfOneDay)*2.5-Math.PI/2)*400+500,
											Math.sin(Math.toRadians(degOfOneDay)*dayOfYear-Math.toRadians(degOfOneDay)*2.5-Math.PI/2)*400+500);
						gxbg.setLineWidth(25);
					}

				
				dayOfYear++;
			} // for j
		} // for i
		
		//DRAW CLOCK INDICATOR
		gxbg.setStroke(Color.DARKRED);
		gxbg.setLineWidth(2);
		gxbg.strokeLine(500, 500, Math.cos(Math.PI*2/yearDays*Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - Math.PI/2 - Math.PI*2/yearDays/2) * 440 + 500,
								Math.sin(Math.PI*2/yearDays*Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - Math.PI/2 - Math.PI*2/yearDays/2) * 440 + 500);
		
		
	}// clockGraphics()
	
	void arcDrawer(GraphicsContext gc, double radie, double startAngle, double arcExtent) {
		gc.strokeArc(500-radie, 500-radie, radie*2, radie*2, startAngle, arcExtent, ArcType.OPEN);
	}
	
	int monthLength(int month) {
		
		month = YearMonth.of(Calendar.getInstance().get(Calendar.YEAR), month + 1).lengthOfMonth();
		
		return month;
	}
}// ClockAni
