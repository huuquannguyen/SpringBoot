import java.util.ArrayList;
import java.util.List;

public class App {
  public static void main(String[] args) {
    /*DemoStream.map01();
    DemoStream.map02();
    DemoStream.map03();*/
    //DemoStream.filter01();
    //DemoStream.maxBy();
    // DemoStream.groupBy01();
    // DemoStream.distinct();

    //Lọc người từ độ tuổi 20 -> 30
    DemoStream.filterAge();

    //Tuổi trung bình của tất cả mọi người
    DemoStream.avgAge();

    //Tuổi trung bình theo từng quốc tịch
    DemoStream.avgAgeOfCountry();
    
  }
}