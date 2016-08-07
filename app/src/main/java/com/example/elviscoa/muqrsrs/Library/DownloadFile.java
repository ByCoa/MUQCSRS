package com.example.elviscoa.muqrsrs.Library;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by soluciones on 8/2/2016.
 */
public class DownloadFile {
    private static final String URL= "http://i.imgur.com/HZ1hq.jpg";
    private static final String FILENAME = "lineadecodigo.jpg";

    public static File crearFichero() throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null){
            fichero = new File(ruta, FILENAME);
        }
        /**else{
         fichero = new File(actividad.getFilesDir(), NOMBRE_DOCUMENTO);
         }*/
        return fichero;
    }

    public static void Download(){
        try {
            File file = crearFichero();
            URLConnection conn = new URL(URL).openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            OutputStream out = new FileOutputStream(file);
            int b = 0;
            while (b != -1) {
                b = in.read();
                if (b != -1)
                    out.write(b);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static File getRuta() {

        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "PROCESSOR");

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {

                        return null;
                    }
                }
            }
        }
        return ruta;
    }
}
