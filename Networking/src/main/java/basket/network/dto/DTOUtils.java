package basket.network.dto;

import model.Bilet;
import model.User;

public class DTOUtils {
    public static User getFromDTO(UserDTO usdto){
        String id=usdto.getusername();
        String pass=usdto.getPasswd();
        return new User(id, pass);

    }
    public static UserDTO getDTO(User user){
        String username=user.getUsername();
        String pass=user.getPassword();
        return new UserDTO(username, pass);
    }
    public static BiletDTO getDTO(Bilet bilet){
        int id = bilet.getId();
        int loc = bilet.getNr_loc();
        int rand = bilet.getNr_rand();
        int pret = bilet.getPret();
        return new BiletDTO(id,loc,rand,pret);
    }



    public static UserDTO[] getDTO(User[] users){
        UserDTO[] frDTO=new UserDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }
    public static BiletDTO[] getDTO(Bilet[] bilete){
        BiletDTO[] frDTO=new BiletDTO[bilete.length];
        for(int i=0;i<bilete.length;i++)
            frDTO[i]=getDTO(bilete[i]);
        return frDTO;
    }

    public static User[] getFromDTO(UserDTO[] users){
        User[] friends=new User[users.length];
        for(int i=0;i<users.length;i++){
            friends[i]=getFromDTO(users[i]);
        }
        return friends;
    }

//    public static Bilet[] getFromDTO(BiletDTO[] bilete){
//        Bilet[] availabletickets=new Bilet[bilete.length];
//        for(int i=0;i<bilete.length;i++){
//            availabletickets[i]=getFromDTO(bilete[i]);
//        }
//        return availabletickets;
//    }
}

