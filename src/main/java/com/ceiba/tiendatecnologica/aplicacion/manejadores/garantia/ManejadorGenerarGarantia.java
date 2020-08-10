package com.ceiba.tiendatecnologica.aplicacion.manejadores.garantia;

import com.ceiba.tiendatecnologica.aplicacion.fabrica.FabricaGarantia;
import com.ceiba.tiendatecnologica.aplicacion.util.CriteriosGarantia;
import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.excepcion.GarantiaExtendidaException;
import com.ceiba.tiendatecnologica.dominio.servicio.producto.ServicioObtenerProducto;
import com.ceiba.tiendatecnologica.dominio.servicio.vendedor.ServicioVendedor;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ManejadorGenerarGarantia {

	public static final String GARANTIA_GENERADA_DE_MANERA_EXITOSA = "Garantía generada de manera exitosa";
	public static final String TIENE_GARANTIA_EXTENDIDA = "Ya cuenta con garantía extendida";
	public static final String PRODUCTO_NO_EXISTE = "No se puede generar la garantía, producto no existe";
	public static final String NO_CUENTA_CON_GARANTIA_EXTENDIDA = "Este producto no cuenta con garantía extendida";

	private final ServicioVendedor servicioVendedor;
	private final ServicioObtenerProducto servicioObtenerProducto;
	private final FabricaGarantia fabricaGarantia;


	public ManejadorGenerarGarantia( ServicioVendedor servicioVendedor, ServicioObtenerProducto servicioObtenerProducto,FabricaGarantia fabricaGarantia, CriteriosGarantia criteriosGarantia) {
		this.servicioVendedor = servicioVendedor;
		this.servicioObtenerProducto = servicioObtenerProducto;
		this.fabricaGarantia = fabricaGarantia;

	}
	
	@Transactional
	public void ejecutar(String codigoProducto,String nombreCliente)  {
		String resultado="";
		//valida si el código de producto cuenta con garantía extendida, para ello se debe invocar a un metodo de utilidad que haga la validacíon.
		if (this.servicioVendedor.cuentaConGarantia(codigoProducto))
		{
			Producto producto = this.servicioObtenerProducto.ejecutar(codigoProducto);
			if(producto!=null)
			{
				//verifica si tiene una garantia
				if(!this.servicioVendedor.tieneGarantia(codigoProducto))
				{
					//fecha de solicitud se asume que es la fecha actual, pero se coloca como variable, por si se decide que cambie a futuro
					Date fechaSolicitud=new Date();//lo que va
					//Calcular los fecha fin de la garantia
					Date fechaFin=this.servicioVendedor.calcularFechaFin(fechaSolicitud,producto.getPrecio());
					//calcular el precio
					Double precio=this.servicioVendedor.calcularPrecio(producto.getPrecio());
					//crear garantía mediante fabrica
					GarantiaExtendida garantiaExtendida=this.fabricaGarantia.crearGarantia( producto,fechaSolicitud,fechaFin,precio,nombreCliente);
					this.servicioVendedor.generarGarantia(garantiaExtendida);
					resultado= GARANTIA_GENERADA_DE_MANERA_EXITOSA;

				}else
				{
					resultado= TIENE_GARANTIA_EXTENDIDA;
					throw new GarantiaExtendidaException(resultado);
				}
			}else {
				resultado= PRODUCTO_NO_EXISTE;
				throw new GarantiaExtendidaException(resultado);
			}
		}else {
			resultado=NO_CUENTA_CON_GARANTIA_EXTENDIDA;
			throw new GarantiaExtendidaException(resultado);
		}


	}


}
