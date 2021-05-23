
import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String title,kind,userName;
    private UsefulDate date;
    private int ticketsNum;

    public Order(String userName, String title, String kind, UsefulDate date, int ticketsNum) {
        this.userName = userName;
        this.title = title;
        this.kind = kind;
        this.date = date;
        this.ticketsNum = ticketsNum;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.title);
        hash = 43 * hash + Objects.hashCode(this.kind);
        hash = 43 * hash + Objects.hashCode(this.userName);
        hash = 43 * hash + Objects.hashCode(this.date);
        hash = 43 * hash + this.ticketsNum;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        
        final Order other = (Order) obj;
        if (this.ticketsNum != other.ticketsNum) {return false;}
        if (!Objects.equals(this.title, other.title)) {return false;}
        if (!Objects.equals(this.kind, other.kind)) {return false;}
        if (!Objects.equals(this.userName, other.userName)) {return false;}
        if (!Objects.equals(this.date, other.date)) {return false;}
        
        return true;
    }
    
    
    //======================GETTERS=================================================
    
    public String getUserName() {return userName;}
    public String getTitle() {return title;}
    public String getKind() {return kind;}
    public UsefulDate getDate() {return date;}
    public int getTicketsNum() {return ticketsNum;}

}
