
import java.io.Serializable;
import java.util.Random;


public class Album implements Serializable{
    private String description,kind;
    private int year,totalSongs;
    
    public Album(String description,String kind,int year, int totalSongs){
        this.description = description;
        this.kind = kind;
        this.year = year;
        this.totalSongs = totalSongs;
    }
    
    public String getDescription() {
        return description;
    }

    public String getKind() {
        return kind;
    }

    public int getYear() {
        return year;
    }

    public int getTotalSongs() {
        return totalSongs;
    }
    
}
