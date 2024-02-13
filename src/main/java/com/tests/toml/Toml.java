package com.tests.toml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.tests.fileconf.Session;
import com.tests.generic.entitis.FileConfEntity;
import com.tests.utility.Utility;

public class Toml {
    public static void newToml() throws IOException {
        File file = new File("text.toml");
        FileWriter writer = new FileWriter(file);

        Session se = new Session();
        se.setConf(se.newFConf());

        List<FileConfEntity> sensors = se.getConf().getSensors();
        String asset = se.getConf().getAsset();

        for (int i = 0; i < sensors.size(); i++) {
            FileConfEntity el = sensors.get(i);
            // TODO: implementare il profileName , labels
            writer.write(
                    "[[DeviceList]]\r\n" + //
                            "     Name = \"" + el.getName() + "\"\t\r\n" + //
                            "     Description = \"" + el.getNumeroSeriale() + "\"\t\r\n" + //
                            "     ProfileName = \"temperatura\"\r\n" + //
                            "     Labels = [\"Pila\",\"centrale\",\"PoD\",\"CASALDELMARMO\"]\r\n" + //
                            "     [DeviceList.Protocols]\r\n" + //
                            "          [DeviceList.Protocols.mqtt]\r\n" + //
                            "               GatewayId = \"" + el.getNumeroSeriale() + "\"\r\n" + //
                            "               AssetCode =\"" + asset + "\"  \r\n" + //
                            "          [DeviceList.Protocols.Layer]   \r\n" + //
                            "               \"svg/CASALDELMARMO2.svg\" = \"ALLARME_T-2301691F\"\r\n" + //
                            "               \"svg/CASALDELMARMO.svg\" = \"ALLARME_121801\"" +
                            "\n");
        }

        writer.close();
        Utility.logInfo("writer chiuso");

    }

}
