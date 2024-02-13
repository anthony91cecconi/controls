package com.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.gson.Gson;
import com.tests.cassandra.MyRequestBody;
import com.tests.cassandra.MyResponse;
import com.tests.fileconf.Session;
import com.tests.generic.store.FconfStore;
import com.tests.guidateste2e.Tests;
import com.tests.toml.Toml;
import com.tests.utility.Utility;
import com.tests.generic.store.FconfStore;

public class Main {
    public static FconfStore store;

    public static void main(String[] args) throws SQLException, IOException {

        Main.store = new FconfStore();
        Utility.scan = new Scanner(System.in);
        Toml.newToml();
        // menu();
        Utility.scan.close();

    }

    public static void menu() throws SQLException, IOException {

        System.out.println(
                "menu iniziale\n selezionare attività \n 1) contrtollo seriali \n 2) guida ai test (parziale in testing)");

        switch (Utility.getNumber(1, 2)) {
            case 1:
                Session session = new Session();
                session.menu();
                menu();
                break;
            case 2:
                Tests tests = new Tests();
                tests.session();
                menu();
                break;

            default:
                break;
        }

    }
    /*
     * public static void testHttp() throws IOException {
     * // Crea un'istanza di MyRequestBody
     * MyRequestBody myRequestBody = new MyRequestBody();
     * myRequestBody.setFrom(1);
     * myRequestBody.setSize(2);
     * myRequestBody.setQuery(new MyRequestBody.Query());
     * myRequestBody.getQuery().setBoolQuery(new MyRequestBody.Query.BoolQuery());
     * myRequestBody.getQuery().getBoolQuery().setMust(Arrays.asList(new
     * MyRequestBody.Query.BoolQuery.MustQuery()));
     * myRequestBody.getQuery().getBoolQuery().getMust().get(0)
     * .setTerms(new MyRequestBody.Query.BoolQuery.MustQuery.Terms());
     * myRequestBody.getQuery().getBoolQuery().getMust().get(0).getTerms().
     * setSensorIds(Arrays.asList(155261));
     * myRequestBody.setSort(new MyRequestBody.Sort());
     * myRequestBody.getSort().setDate("desc");
     * 
     * // Crea un'istanza di OkHttpClient
     * OkHttpClient client = new OkHttpClient();
     * 
     * // Crea un RequestBody dal MyRequestBody
     * Gson gson = new Gson();
     * String json = gson.toJson(myRequestBody);
     * RequestBody requestBody =
     * RequestBody.create(MediaType.parse("application/json"), json);
     * 
     * // Crea un Request con il metodo POST e l'URL desiderato
     * Request request = new Request.Builder()
     * .url("http://10.207.24.9:9200/timeseries/_search?pretty=true")
     * .post(requestBody)
     * .build();
     * 
     * // Esegui la chiamata e gestisci la risposta
     * try (Response response = client.newCall(request).execute()) {
     * MyResponse responseObject = gson.fromJson(responseBody, MyResponse.class); //
     * Sostituisci MyResponse con la
     * // tua classe concreta
     * 
     * if (responseObject.hits != null && responseObject.hits.total > 0) {
     * System.out.println(
     * "Chiamata a API completata con successo. Risultati trovati: " +
     * responseObject.hits.total);
     * } else {
     * System.out.println("Chiamata a API fallita. Nessun risultato trovato.");
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * 
     * try (Response response = client.newCall(request).execute()) {
     * if (response.isSuccessful()) {
     * String responseBody = response.body().string();
     * System.out.println(responseBody);
     * } else {
     * System.out.println("Errore durante la chiamata: " + response.code());
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     * [[DeviceList]]
     * Name = "ACC002_CAM01_Z"
     * ProfileName = "accelerometro"
     * Description = "0305682F"
     * Labels = ["Impalcato", "dx - bordo", "Spalla 1",
     * "PO_A90_11652_EBREI_CARR_INTERNA"]
     * [DeviceList.Protocols]
     * [DeviceList.Protocols.mqtt]
     * GatewayId = "0305682F"
     * AssetCode = "12001006930"
     * [DeviceList.Protocols.Layer]
     * "svg/PO_A90_11652_EBREI_CARR_INTERNA.svg" = "ALLARME_A-0305682F"
     */
}
