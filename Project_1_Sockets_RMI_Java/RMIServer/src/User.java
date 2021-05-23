
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name,surname,phone,mail,loginName,password;
    private boolean isAdmin;

    //=============================CONSTRUCTORS================================================================
    // User Constructor
    public User(String UserName, String UserSurname, String UserPhone, String UserMail, String UserLoginName, String UserPassword){
        this.name = UserName;
        this.surname = UserSurname;
        this.phone = UserPhone;
        this.mail = UserMail;
        this.loginName = UserLoginName;
        this.password = UserPassword;
        
        this.isAdmin = false;
    }
    
    // Admin Constructor
    public User(String adminName, String adminPassword){
        this.loginName = adminName;
        this.password = adminPassword;
        this.isAdmin = true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.loginName);
        hash = 89 * hash + Objects.hashCode(this.password);
        hash = 89 * hash + (this.isAdmin ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final User other = (User) obj;
        if (this.isAdmin != other.isAdmin) {return false;}
        if (!Objects.equals(this.loginName, other.loginName)) {return false;}
        if (!Objects.equals(this.password, other.password)) {return false;}
        
        return true;
    }
    

    //=============================GETTERS================================================================
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getPhone() { return phone; }
    public String getMail() { return mail; }
    public String getLoginName() { return loginName; }
    public String getPassword() { return password; }
    public boolean getIsAdmin() { return isAdmin; }

    //=============================toSTRING================================================================
//    @Override
//    public String toString(){
//        StringBuilder displayUser = new StringBuilder("");
//        displayUser.append("Name: ");
//        displayUser.append(name);
//        displayUser.append("/");
//        displayUser.append("Surname: ");
//        displayUser.append(surname);
//        displayUser.append("/");
//        displayUser.append("Phone: ");
//        displayUser.append(phone);
//        displayUser.append("/");
//        displayUser.append("E-Mail: ");
//        displayUser.append(mail);
//        displayUser.append("/");
//        displayUser.append("Login Name: ");
//        displayUser.append(loginName);
//        displayUser.append("/");
//        displayUser.append("Password: ");
//        displayUser.append(password);
//
//        return displayUser.toString();
//    }

}
