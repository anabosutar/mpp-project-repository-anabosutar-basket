import model.Bilet;
import repositories.BiletDBRepository;
import repositories.BiletRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class MainDB {
    public static void main(String[] args) {

        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        BiletRepository biletRepo=new BiletDBRepository(props);
        biletRepo.add(new Bilet(1,2,3,4));
        biletRepo.add(new Bilet(2,2,3,4));
        biletRepo.add(new Bilet(3,2,3,4));
        biletRepo.delete(1);
        biletRepo.update(new Bilet(3,20,20,40));
        List<Bilet> bilete = biletRepo.getAll();
        for (Bilet bilet:bilete) {
            System.out.println(bilet.toString());
        }




    }
}
