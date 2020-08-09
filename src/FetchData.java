
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JProgressBar;

import org.json.*;

public class FetchData {
    String URL, startDate, endDate;
    ArrayList<InfoModele> mList;
    SimpleDateFormat dateFormat;

    public FetchData() {
    }

    public ArrayList<InfoModele> startFetching(String mUrl, String place, JProgressBar progressBar) throws IOException {
       
        StateOfHealthSimulation soh = new StateOfHealthSimulation();
        //// date
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        startDate = "2018-01-01";

        endDate = "2018-01-31";

        /////////
        URL = mUrl + place + "&format=json&" + "date=" + startDate + "&enddate=" + endDate;

        //////////
        ArrayList<InfoModele> modeleArrayList = new ArrayList<>();

        HttpURLConnection conn = null;
        InputStream is = null;


        for (int j = 0; j < 12; j++) {

            int progress = (j * 100) / 12;
            progressBar.setValue(progress);
            URL url = null;
            try {
                url = new URL(URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();

            }


            try {
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setConnectTimeout(6 * 10 * 1000);
                conn.setReadTimeout(6 * 10 * 1000);
                conn.connect();

                is = conn.getInputStream();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            String jsonReponse = "";
            StringBuilder builder = new StringBuilder();
            if (is != null) {
                InputStreamReader reader = new InputStreamReader(is, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
            }
            jsonReponse = builder.toString();

            Double sunHour = 0.0;
            int temp = 0;
            String location = "";
            String sDate = "";
            Date date = null;

            try {
                JSONObject root = new JSONObject(jsonReponse);

                JSONObject data = root.getJSONObject("data");

                JSONArray request = data.getJSONArray("request");

                JSONArray weather = data.getJSONArray("weather");


                for (int i = 0; i < weather.length(); i++) {
                    JSONObject inObject = weather.getJSONObject(i);
                    JSONObject reObject = request.getJSONObject(0);
                    location = reObject.getString("query");
                    sunHour = inObject.getDouble("sunHour");
                    temp = inObject.getInt("avgtempC");
                    sDate = inObject.getString("date");

                    try {
                        date = dateFormat.parse(sDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    modeleArrayList.add(new InfoModele(location, sunHour, temp, date));


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // new start date
            Date mStartDate = null;
            try {
                mStartDate = dateFormat.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mStartDate);
            calendar.add(Calendar.MONTH, +1);
            Date newSatrtDate = calendar.getTime();
            startDate = dateFormat.format(newSatrtDate);


            // new end date
            Date mEndDate = null;
            try {
                mEndDate = dateFormat.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(mEndDate);
            calendar2.set(Calendar.DATE, calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date newEndDate = calendar2.getTime();
            endDate = dateFormat.format(newEndDate);
            /////////
            URL = mUrl + place + "&format=json&" + "date=" + startDate + "&enddate=" + endDate;

        }
        conn.disconnect();
        progressBar.setValue(1);
        return modeleArrayList;
    }
}

