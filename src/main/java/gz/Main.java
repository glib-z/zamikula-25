package gz;

import javafx.scene.chart.PieChart;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        //Timestamp timestamp = Date()
        Date date = new Date();
        System.out.println("Date: " + date);

        //Timestamp timestamp =

        test1();



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
}
