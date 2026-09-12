package org.see;



import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Miscelaneous {
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static DateTimeFormatter formatterComTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static String formatDate(LocalDate date){
        return dtf.format(date);
    }
    public static String formatDateTime(LocalDateTime dateTime){
        return formatterComTime.format(dateTime);
    }

    public static String[] simpleSubstringSearch(String[]arr, String subs) {
        ArrayList<String> c = new ArrayList<String>();
        for(int i =0; i<arr.length; i++){
            if(arr[i].toLowerCase().contains(subs.toLowerCase())){
                c.add(arr[i]);
            }
        }
        String[] retorno = new String[c.size()];
        for(int i = 0; i<retorno.length; i++){
            retorno[i] = c.get(i);
        }
        return retorno;
    }


    public static ArrayList<String> returnListOfMonthsBasedInDay(int day){
        ArrayList<String> months = new ArrayList<String>();
        months.add("Janeiro");
        months.add("Fevereiro");
        months.add("Março");
        months.add("Abril");
        months.add("Maio");
        months.add("Junho");
        months.add("Julho");
        months.add("Agosto");
        months.add("Setembro");
        months.add("Outubro");
        months.add("Novembro");
        months.add("Dezembro");

        if(day>29) {
            months.remove("Fevereiro");
        }
        if(day == 31) {
            months.remove("Abril");
            months.remove("Junho");
            months.remove("Setembro");
            months.remove("Novembro");
        }
        return months;
    }

    public static ArrayList<String> returnYearList(boolean feb29selected, int currentYear, int fixedStartPoint){
        int useInLoop;
        if(fixedStartPoint != -1) {
            useInLoop = fixedStartPoint;
            if(feb29selected) {
                while(useInLoop % 4 != 0) {
                    useInLoop++;
                }
            }
        }else if(feb29selected){
            useInLoop = currentYear - 28;
        }else{
            useInLoop = currentYear - 30;
        }
        ArrayList<String> years = new ArrayList<String>();
        if (feb29selected){
            while(currentYear % 4 != 0) {
                currentYear++;
            }
            for(int i = useInLoop; i<=currentYear; i += 4) {
                if(!(currentYear % 100 == 0 && currentYear % 400 != 0)) {
                    years.add(String.valueOf(i));
                }

            }
        }else {
            for(int i = useInLoop; i<=currentYear; i++) {
                years.add(String.valueOf(i));
            }
        }
        return years;
    }


    public static boolean checkIfLeapYear(int year){
        //nao e bissexto
        if(year % 4 != 0 || year % 100 == 0 && year % 400 != 0) {
            return false;

        }
        return true;
    }

    public static ArrayList<String> returnDays(){
        ArrayList<String> days = new ArrayList<String>();
        for(int i = 1; i<32; i++) {
            days.add(String.valueOf(i));
        }
        return days;
    }

    public static String getMonthName(byte month) {
        return switch (month) {
            case 1 -> "Janeiro";
            case 2 -> "Fevereiro";
            case 3 -> "Março";
            case 4 -> "Abril";
            case 5 -> "Maio";
            case 6 -> "Junho";
            case 7 -> "Julho";
            case 8 -> "Agosto";
            case 9 -> "Setembro";
            case 10 -> "Outubro";
            case 11 -> "Novembro";
            case 12 -> "Dezembro";
            default -> throw new InvalidParameterException("Mês inexistente.");
        };
    }



    public static int returnMonthNumberByName(String nome) {
        return switch (nome.toLowerCase()) {
            case "janeiro" -> 1;
            case "fevereiro" -> 2;
            case "março" -> 3;
            case "abril" -> 4;
            case "maio" -> 5;
            case "junho" -> 6;
            case "julho" -> 7;
            case "agosto" -> 8;
            case "setembro" -> 9;
            case "outubro" -> 10;
            case "novembro" -> 11;
            case "dezembro" -> 12;
            default -> throw new InvalidParameterException("Mês inexistente");
        };

    }


    public static int[] getSystemDate() {
        int[] date = new int[3];
        LocalDateTime now = LocalDateTime.now();

        date[0] = Integer.parseInt(DateTimeFormatter.ofPattern("dd").format(now));
        date[1] = Integer.parseInt(DateTimeFormatter.ofPattern("MM").format(now));
        date[2] = Year.now().getValue();

        return date;
    }

    public static int[] getSystemTime() {
        int[] hour = new int[3];
        LocalDateTime now = LocalDateTime.now();

        hour[0] = Integer.parseInt(DateTimeFormatter.ofPattern("HH").format(now));
        hour[1] = Integer.parseInt(DateTimeFormatter.ofPattern("mm").format(now));

        return hour;
    }

    public static String formatPhoneNumber(long phoneNumber) {
        String formatted = String.format("%011d", phoneNumber).replaceFirst("(\\d{2})(\\d)(\\d{4})(\\d{4})", "($1) $2 $3-$4");
        return formatted;
    }
}

