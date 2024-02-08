package com.tests.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Utility {

    public static Scanner scan;

    public Utility() {
    }

    /*
     * permette di prendere un intero dal utente
     * 
     * @min imposta il valore minimo selezionabile
     * 
     * @max imposta il valore massimo selezionabile
     * 
     * @scanner attualmente l'applicazione non gestisce uno scanner in modo globale
     * deve essere portato nei metodi
     * 
     * TODO: sostituire Sacanner con un sistema globale
     * TODO: sostituire i println con sistema di log globale
     */

    public static int getNumber(int min, int max) {
        System.out.println("inserire valore tra " + min + " e " + max);

        try {
            int i = Utility.scan.nextInt();
            Utility.scan.nextLine();
            if (i >= min && i <= max) {
                return i;
            }
            return getNumber(min, max);
        } catch (Exception e) {
            System.out.println("valore non accettato");
            return getNumber(min, max);
        }

    }

    /*
     * permette di prendere una stringa inserita dal utente
     * 
     * @tipo riporta parte della stringa rendendo riutilizzabile il metodo
     * 
     * TODO: sostituire Sacanner con un sistema globale
     * TODO: sostituire i println con sistema di log globale
     */

    public static String getString(String tipo) {
        System.out.println("inserire " + tipo);
        try {
            return Utility.scan.nextLine();
        } catch (Exception e) {
            System.out.println("valore non accettato");
            return getString(tipo);
        }
    }

    /*
     * questo metodo prende il si e il no e lo muta in booleano
     */

    public static boolean yesOrNo(String text) {
        System.out.println(text);
        try {
            String r = Utility.scan.nextLine().toLowerCase();
            if (r.equals("yes".toLowerCase()) || r.equals("y".toLowerCase()) || r.equals("s".toLowerCase())
                    || r.equals("si".toLowerCase()) || r.equals("ok".toLowerCase())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("valore non valido");
            return yesOrNo(text);
        }
    }

    public static void logInfo(String message) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
        String ora = sdf.format(date);
        System.out.println("log-info " + ora + " - " + message);
    }
}
