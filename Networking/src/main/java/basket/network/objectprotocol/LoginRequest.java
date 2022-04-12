package basket.network.objectprotocol;

import basket.network.dto.UserDTO;

public class LoginRequest implements Request {
    private UserDTO user;

    public LoginRequest(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}
