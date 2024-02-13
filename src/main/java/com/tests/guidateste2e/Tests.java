package com.tests.guidateste2e;

import java.io.IOException;
import java.sql.SQLException;

import com.tests.fileconf.Session;
import com.tests.utility.Utility;

public class Tests {
        String asset;

        public void session() throws IOException, SQLException {

                t("questo è un tutorial passo passo che ti guiderà nei test E2E di un edge e scrivera l'excel per te");
                t("per ora non stampa il file excell che deve essere compilato quindi a mano");
                t("0.1,0.2,1.1,1.2 questi punti sono semi automatici verrà prodotto un file txt con le informazioni necessarie che dovrai riportare sul excell");
                Session fconfTests = new Session();
                fconfTests.session();
                question(
                                "1.3\tCadenza Invio File Dinamici (1 ogni 4 ore)\r\n" + //
                                                "1.4\tCadenza Invio File Statici (1 ogni ora)",
                                "entrare nel EDGE, nel percorso D:/anas/backup puoi verificare se ci siano csv gia ingestionati e guardando l'ora nel nome del file puoi capire la cadenza.");
                question(
                                "1.5\tI dati dei sensori statici sono caricati nella cartella corretta\r\n" + //
                                                "1.6\tI dati dei sensori dinamici sono caricati nella cartella corretta\r\n"
                                                + //
                                                "1.7\tI dati dei sensori statici sono processati dalla componente edge\r\n"
                                                + //
                                                "1.8\tI dati dei sensori dinamici sono processati dalla componente edge",
                                "se i csv raggiungono correttamente  D:/anas/backup significa che questi punti sono corretti \n se si riscontrano problemi andare nella cartella D:\\shm\\sem-anas-gics-be-edge-shm-dfm\\logs aprire i log e cercare il problema");
                question(
                                "1.9\tI dati statici salgono in piattaforma\r\n" + //
                                                "1.10\tI dati dinamici salgono in piattaforma",
                                "entrare su moova e verificare che i sensori mostrino i grafici dei dati");
                question(
                                "2.1.1\tPer i dispositivi censiti è possibile visualizzare lo stato dei sensori (on/off): acce\r\n"
                                                + //
                                                "2.1.2\tPer i dispositivi censiti è possibile visualizzare lo stato dei sensori (on/off): temp\r\n"
                                                + //
                                                "2.1.3\tPer i dispositivi censiti è possibile visualizzare lo stato dei sensori (on/off): UCL",
                                "dal EDGE, aprire un browser alla pagina \"localhost:4000\" nel menu di sinistra andare su dispositivi e verificare che siano tutti \"attivi\"");
                question(
                                "2.2.1\tAccensione sensore acc\r\n" + //
                                                "2.2.2\tSpegnimento sensore acc\r\n" + //
                                                "2.2.3\tAccensione sensore temp\r\n" + //
                                                "2.2.4\tSpegnimento sensore temp",
                                "dal localhost:4000 del EDGE, entrare su un singolo dispositivo, accedere al menu Lista Parametri/comandi, testare lo spengimento e verificare su info se è stato eseguito, stessa cosa per restart.");
                /*
                 * question(
                 * "2.2.5\tAggiornamento firmware sensori\r\n" + //
                 * "2.2.6\tAggiornamento firmware UCL", "");
                 */
                question("2.2.7\tCambio frequenza su tutto l'impianto (per ora solo dewnsoft)",
                                "accedere al UCL, al menu Lista Parametri/comandi provare a cambiare la frequenza e verificare che ci sia una risposta positiva, poi ricaricare la pagina");
                question(
                                "2.3.1\tSi riceve l'allarme di disconnessione/malfunzionamento del sensore/linea di sensori\r\n"
                                                + //
                                                "2.3.2\tRientro allarme di un sensore (solo dewensoft)",
                                "accedere al seguente url <<ip_del_EDGE_+2(alcuni+3)>>:8888/alarm/<<gatewai_del_sensore>>/<<stato>> (on per attivare allarme off per spengere l'allarme) \n esempio url \n 10.238.1.13:8888/alarm/TEST4G/on");

        }

        public void t(String t) {
                System.out.println(t);
        }

        public void question(String task, String tutorial) {
                t(task);
                if (Utility.yesOrNo("servono istruzioni ?")) {
                        t(tutorial);
                }

        }

}
