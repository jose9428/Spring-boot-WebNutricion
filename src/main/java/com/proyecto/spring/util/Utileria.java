package com.proyecto.spring.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

public class Utileria {

    public static Date ConvertirFecha(String cadena) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;

        try {
            fecha = df.parse(cadena);
        } catch (Exception ex) {
        }

        return fecha;
    }

    public static byte[] ConvertirImagen(MultipartFile imagen) {
        byte[] byteObjects = null;
        
        if (!imagen.isEmpty()) {
            try {
                byteObjects = new byte[imagen.getBytes().length];

                int i = 0;

                for (byte b : imagen.getBytes()) {
                    byteObjects[i++] = b;
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return byteObjects;
    }
}
