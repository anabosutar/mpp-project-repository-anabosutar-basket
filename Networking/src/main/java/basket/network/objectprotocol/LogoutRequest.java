package basket.network.objectprotocol;

import basket.network.dto.UserDTO;

public class LogoutRequest implements Request {
    private UserDTO user;

    public LogoutRequest(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}
