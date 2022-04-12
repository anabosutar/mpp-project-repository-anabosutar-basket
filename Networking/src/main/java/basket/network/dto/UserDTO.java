package basket.network.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String username;
    private String passwd;

    public UserDTO(String username) {
        this(username,"");
    }

    public UserDTO(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    @Override
    public String toString(){
        return "UserDTO["+username+' '+passwd+"]";
    }
}
