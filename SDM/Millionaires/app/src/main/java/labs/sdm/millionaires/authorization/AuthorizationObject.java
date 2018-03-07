package labs.sdm.millionaires.authorization;

/**
 * Created by Rafal on 2018-02-13.
 */

public class AuthorizationObject {

    private String login;
    private String password;

    public AuthorizationObject(String login, String password) {
        setLogin(login);
        setPassword(password);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean check() {
        if(login.length()!=0)
            return true;
        else
            return false;
    }



}
