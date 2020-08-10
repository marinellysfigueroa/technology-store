package com.ceiba.tiendatecnologica.aplicacion.fabrica;

import com.ceiba.tiendatecnologica.aplicacion.comando.ComandoProducto;
import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FabricaGarantia {
    public GarantiaExtendida crearGarantia(Producto producto, Date fechaInicio,Date fechaFin, Double precioGarantia, String nombreCliente){
        return new GarantiaExtendida( producto, fechaInicio, fechaFin,precioGarantia, nombreCliente);
    }
}
