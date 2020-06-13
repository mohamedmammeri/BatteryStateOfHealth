import java.util.Date;

public class InfoModele {

int temperature;
Double sunHour;
String location;
Date date;
public InfoModele(String location,Double sunHour2, int temp,Date date) {
	this.sunHour=sunHour2;
	this.temperature=temp;
	this.location=location;
	this.date=date;
	
	// TODO Auto-generated constructor stub
}
public void setsunHour(Double sunHour) {
	this.sunHour=sunHour;
}
public void setTemp(int temp) {
	this.temperature=temp;
}
public void setLocation(String location) {
	this.location=location;
}
public void setDate(Date date) {
	this.date=date;
}
public String getLocation() {
	return location;
}
public Double getSunHour() {
	return sunHour;
}
public int getTemperature() {
	return temperature;
}
public Date getDate() {
	return date;
}
}
