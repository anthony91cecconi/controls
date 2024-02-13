package com.tests.generic.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tests.generic.entitis.PysicalDbEntity;
import com.tests.utility.Utility;

public class ConnectorDbMysql {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private Connection connection;

    public ConnectorDbMysql() throws SQLException {
        System.out.println("quale ambiente ? \n1 cert \n2 demo \n3prod");

        switch (Utility.getNumber(1, 3)) {
            case 1:
                this.host = "10.207.24.68";
                this.port = 3306;
                this.database = "nrg_sem_station";
                this.username = "remoteop";
                this.password = "urewt__kb4333";
                break;
            case 2:
                this.host = "10.207.19.144";
                this.port = 3306;
                this.database = "nrg_sem_station";
                this.username = "hipuser";
                this.password = "g10TT0!123stella";
                break;
            case 3:
                this.host = "10.207.36.68";
                this.port = 3306;
                this.database = "nrg_sem_station";
                this.username = "remoteop";
                this.password = "u834vj$h43uiji";
                break;
            default:
                break;
        }

        this.connection = DriverManager.getConnection(
                "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                this.username,
                this.password);

    }

    public List<PysicalDbEntity> getAllPhysical_id(String asset) throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement
                .executeQuery("SELECT physical_id FROM gics_sensors WHERE gics_object_id IN (\t\r\n" + //
                        "\tSELECT id FROM gics_object WHERE parent_id IN (\t\r\n" + //
                        "\t\tSELECT id FROM gics_object WHERE parent_id IN (\t\r\n" + //
                        "\t\t\tSELECT id FROM gics_object WHERE parent_id =\r\n" + //
                        "\t\t\t(\r\n" + //
                        "\t\t\t\tSELECT ID_SEDE FROM dm_d_sedi WHERE codice_stazione LIKE \""
                        + asset + "\"\r\n" + //
                        "\t\t\t)\r\n" + //
                        "\t\t)\r\n" + //
                        "\t)\r\n" + //
                        ")AND (PHYSICAL_ID LIKE '%_z'\r\n" + //
                        "OR PHYSICAL_ID LIKE '%_x'\r\n" + //
                        "OR PHYSICAL_ID LIKE '%_Y'\r\n" + //
                        "OR PHYSICAL_ID LIKE '%_t')\r\n" + //
                        "AND NOT (PHYSICAL_ID LIKE '%_al_anomaly');");

        List<PysicalDbEntity> physicals = new ArrayList<PysicalDbEntity>();
        while (resultSet.next()) {
            String s = resultSet.getString("physical_id");
            PysicalDbEntity p = new PysicalDbEntity(s);
            physicals.add(p);
        }
        return physicals;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

}
