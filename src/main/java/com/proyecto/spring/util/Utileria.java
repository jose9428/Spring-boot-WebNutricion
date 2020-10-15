package com.proyecto.spring.util;

import com.proyecto.spring.models.entity.ErrorEntity;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    public static Set<ErrorEntity> getListError(BindingResult errores) {
        Set<ErrorEntity> lista = new HashSet<>();

        for (FieldError o : errores.getFieldErrors()) {
            ErrorEntity e = new ErrorEntity();
            e.setCampo(o.getField());
            e.setMensaje(o.getDefaultMessage());
            lista.add(e);
        }

        return lista;
    }

    public static String CodigoToken() {
        String cadena = "0123456789";
        String token = "";
        int max = cadena.length() - 1;

        for (int i = 0; i < 6; i++) {
            int caracter = (int) (Math.random() * (max));
            token += cadena.charAt(caracter);
        }
        return token;
    }

}
