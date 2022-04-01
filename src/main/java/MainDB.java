import controller.BiletController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jdbc.BiletDBRepository;
import service.BiletService;
import view.BileteView;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainDB extends Application {
    //private static final Logger logger = LogManager.getLogger(MainDB.class);
//    public static void main(String[] args) {
//        //logger.debug("Some debug log");


//        BiletRepository biletRepo=new BiletDBRepository(props);
//        biletRepo.add(new Bilet(1,2,3,4));
//        biletRepo.add(new Bilet(2,2,3,4));
//        biletRepo.add(new Bilet(3,2,3,4));
//        biletRepo.delete(1);
//        biletRepo.update(new Bilet(3,20,20,40));
//        List<Bilet> bilete = biletRepo.getAll();
//        for (Bilet bilet:bilete) {
//            //logger.info("Bilet: " + bilet);
//            System.out.println(bilet.toString());
//        }
//
//        //logger.warn("Warning accrued at " + LocalDateTime.now());
//        //logger.error("Error accrued at " + LocalDateTime.now());
//
//        MeciRepository meciRepo = new MeciDBRepository(props);
//        meciRepo.add(new Meci(1,5,"U-BT","Buc","Finala"));
//        meciRepo.add(new Meci(2,50,"U-BT","TM","SemiFinala1"));
//        meciRepo.add(new Meci(3,15,"U-BT","SV","SemiFinala2"));
//
//        meciRepo.delete(1);
//        meciRepo.update(new Meci(3,0,"U-BT","Buc","Finala"));
//        List<Meci> meciuri = meciRepo.getAll();
//        for (Meci meci:meciuri) {
//            System.out.println(meci.toString());
//        }


    //   }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Basket Application");
        BorderPane pane = getView();
        Scene scene = new Scene(pane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static BorderPane getView() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        BiletDBRepository repo = new BiletDBRepository(props);

        BiletService service = new BiletService(repo);
        BiletController contr = new BiletController(service);
        BileteView view = new BileteView(contr);
        return view.getView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
