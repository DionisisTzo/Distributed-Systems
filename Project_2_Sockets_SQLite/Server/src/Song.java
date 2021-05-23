
import java.io.Serializable;


public class Song implements Serializable {
    private String title,interpreter;
    private int duration;
    
    public Song(String title,String interpreter,int duration){
        this.title = title;
        this.interpreter = interpreter;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getInterpreter() {
        return interpreter;
    }

    public int getDuration() {
        return duration;
    }
}
