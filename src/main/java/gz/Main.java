package gz;

import javafx.scene.chart.PieChart;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        //Timestamp timestamp = Date()
//        Date date = new Date();
//        System.out.println("Date: " + date);

        //Timestamp timestamp =

        //test1();
        //test2();
        test3();



    }

    private static void test1() {
        Date date = new Date();

        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String formatted = formatDate.format(date);
        System.out.println("formatted: " + formatted);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Timestamp: " + timestamp);

        formatted = formatDate.format(timestamp.getTime());
        System.out.println("formatted2: " + formatted);

    }

    private static void test2() {

        //String sDate1 = "1977-06-30 21:00";
        String sDate = "1977-06-30";
        String sTime = "21:00";
        String sDate1 = sDate + " " + sTime;

        Date date1= null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(sDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(sDate1+"\t"+date1);

        Timestamp ts = new Timestamp(date1.getTime());
        System.out.println("Timestamp: " + ts);

    }

    private static void test3() {

        String sDate1 = "1977-06-30 21:00";
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(sDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp ts = new Timestamp(date1.getTime());
        System.out.println("Timestamp base: " + ts + "  " + ts.getTime());


        Timestamp timestamp_curr = new Timestamp(System.currentTimeMillis());
        System.out.println("Timestamp current: " + timestamp_curr + "   " + timestamp_curr.getTime());

//        System.out.println(timestamp_curr.getTime() - );

//        System.out.println("Diff: " + (timestamp_curr - ts));


    }

}
