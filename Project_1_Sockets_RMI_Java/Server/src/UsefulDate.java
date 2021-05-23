
import java.io.*;
import java.time.*;

/**
 * My own personal implementation of a Date class
 * since java's default classes are too hard to
 * make a sense of
 */
public final class UsefulDate implements Serializable{
    private int year, month, day;
    private static final long serialVersionUID = 1L;
    
    public UsefulDate(){
        this.day=LocalDate.now().getDayOfMonth();
        this.month=LocalDate.now().getMonthValue();
        this.year=LocalDate.now().getYear();
    }

    public UsefulDate(String date) throws NumberFormatException{
        String[] fields=date.split("/");
        if(fields.length!=3){
            throw new NumberFormatException();
        }else{
            int day, month, year;
            day=Integer.parseInt(fields[0]);
            month= Integer.parseInt(fields[1]);
            year= Integer.parseInt(fields[2]);

            if(month>0 && month<13){
                this.month=month;
            }else{//weird month number
                throw new NumberFormatException();
            }

            this.day=day;
            this.month=month;
            this.year=year;
        }
    }
    
    public UsefulDate(int day, int month, int year) throws NumberFormatException{
        
        if(year>LocalDate.now().getYear()){//year is in the future
            throw new NumberFormatException();
        }else{
            this.year=year;
        }
        
        if(month>0 && month<13){
            this.month=month;
        }else{//weird month number
            throw new NumberFormatException();
        }
        
        if(day<=getMaxDays(this.month) && day>0){
            this.day=day;
        }else{//weird day number
            throw new NumberFormatException();
        }
        
    }
        
        private boolean isLeapYear(int year){
            
            if(year % 400 == 0){
                return true;
            }
            
            if(year % 100 == 0){
                return false;
            }
            
            if(year % 4 == 0){
                return true;
            }
            
            return false;
        }
        
        private int getMaxDays(int month){
            
            if((month%2==1 && month<8) || (month%2==0 && month>7)){
                return 31;
            }else if(month==2){
                if(isLeapYear(year)){
                    return 29;
                }else{
                    return 28;
                }
            }else{
                return 30;
            }
            
        }
        
        //returns true if date entered is before this date
        public boolean before(UsefulDate date){
            if(year>date.year){
                return true;
            }else if(year==date.year && month>date.month){
                return true;
            }else if(year==date.year && month==date.month && day>=date.day){
                return true;
            }
            return false;
        }
        
        //returns true if date entered is after this date
        public boolean after(UsefulDate date){
            return !before(date);
        }

        public String toString(){

            StringBuilder sb = new StringBuilder("");
            sb.append(day);
            sb.append("/");
            sb.append(month);
            sb.append("/");
            sb.append(year);

            return sb.toString();
        }
        
}