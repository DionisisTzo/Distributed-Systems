

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class Event implements Serializable{
    
    private static final long serialVersionUID = 1L;
        
    private String title,kind;
    private UsefulDate date;
    private LocalTime timeEvent;
    private int cost,tickets;

    public Event(String title, String kind, UsefulDate date, LocalTime timeEvent ,int tickets, int cost) {
        this.title = title;
        this.kind = kind;
        this.cost = cost;
        this.date = date; 
        this.timeEvent = timeEvent;
        this.tickets = tickets;
    }
    
    //=============================OBJECTS_EQUALS================================================================
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.title);
        hash = 37 * hash + Objects.hashCode(this.kind);
        hash = 37 * hash + this.cost;
        hash = 37 * hash + Objects.hashCode(this.date);
        hash = 37 * hash + Objects.hashCode(this.timeEvent);
        hash = 37 * hash + Objects.hashCode(this.tickets);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        
        final Event other = (Event) obj;
        
        if (this.cost != other.cost) {return false;}
        if (!Objects.equals(this.title, other.title)) {return false;}
        if (!Objects.equals(this.kind, other.kind)) {return false;}
        if (!Objects.equals(this.date, other.date)) {return false;}
        if (!Objects.equals(this.timeEvent, other.timeEvent)) {return false;}
        
        return true;
    }

    
    //=============================GETTERS================================================================

    public String getTitle() {return title;}
    public String getKind() {return kind;}
    public UsefulDate getDate() {return date;}
    public LocalTime getTimeEvent() {return timeEvent;}
    public int getCost() {return cost;}
    public int getTickets() {return tickets;}


}
