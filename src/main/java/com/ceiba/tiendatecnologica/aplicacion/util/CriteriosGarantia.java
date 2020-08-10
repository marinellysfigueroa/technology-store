package com.ceiba.tiendatecnologica.aplicacion.util;


import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Date;


@Component
public class CriteriosGarantia {

    public static final int PRECIO_BASE = 500000;
    public static final int PORCENTAJE_MAX = 20;
    public static final int PORCENTAJE_MIN = 10;
    public static final int MIN_DIAS = 100;
    public static final int MAX_DIAS = 200;

    public CriteriosGarantia() {
    }

    public boolean cuentaConGarantia(String codigo)
    {
        boolean conGarantia=false;
        int cantidadVocales=this.contarVocales(codigo);
        if(cantidadVocales!=3)
        {
            conGarantia=true;
        }
        return  conGarantia;
    }
    public Date calcularFechaFin(Date fechaInicio,double precio)
    {
        Date dateCalculada=new Date();
        if(precio>PRECIO_BASE)
        {
            dateCalculada=this.calculaDias(fechaInicio, MAX_DIAS);
        }else
        {
            dateCalculada=this.sumarDias(fechaInicio, MIN_DIAS);
        }

        return dateCalculada;
    }

    private Date calculaDias(Date fecha, int maxDias) {
        Date fechaCalculada=this.calcularFechaFin(fecha,maxDias);
        return fechaCalculada;
    }

    private Date calcularFechaFin(Date fechaInicio, int maxDias) {

        int contador=1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);

        while (contador<=maxDias)
        {
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                contador++;
            }
            calendar.add(Calendar.DATE, 1);
        }

        //validar si es domingo
        if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
        {
            calendar.add(Calendar.DATE, 2);//se considera martes como día hábil
        }

        return calendar.getTime();
    }

    private Date sumarDias(Date fecha, int minDias) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR,minDias);
        return  calendar.getTime();
    }

    public Double calcularPrecio(double precio)
    {
        double precioCalculado=0.0;
        if(precio> PRECIO_BASE)
        {
            precioCalculado=precio* PORCENTAJE_MAX /100;
        }else {
            precioCalculado=precio* PORCENTAJE_MIN /100;
        }
        return  precioCalculado;
    }

    private int contarVocales(String codigo) {
        int contador=0;
        codigo=codigo.toLowerCase();
        for(int x=0;x<codigo.length();x++) {
            if ((codigo.charAt(x)=='a') || (codigo.charAt(x)=='e') || (codigo.charAt(x)=='i') || (codigo.charAt(x)=='o') || (codigo.charAt(x)=='u')){
                contador++;
            }
        }
        return contador;
    }

}
