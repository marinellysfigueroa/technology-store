package com.ceiba.tiendatecnologica.dominio.servicio.vendedor;

import com.ceiba.tiendatecnologica.aplicacion.util.CriteriosGarantia;
import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioGarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;

import java.util.Date;

public class ServicioVendedor {

	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantía extendida";

	private RepositorioProducto repositorioProducto;
	private RepositorioGarantiaExtendida repositorioGarantia;
	private CriteriosGarantia criteriosGarantia;

	public ServicioVendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioGarantia = repositorioGarantia;
		this.criteriosGarantia = new CriteriosGarantia();

	}



	public void generarGarantia(GarantiaExtendida garantia) {
		this.repositorioGarantia.agregar(garantia);
	}

	public boolean tieneGarantia(String codigo) {

		if(this.repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo)!=null)
		{
			return true;
		}else {
			return false;
		}
	}

	public boolean cuentaConGarantia(String codigo)
	{
		return this.criteriosGarantia.cuentaConGarantia(codigo);
	}

	public Date calcularFechaFin(Date fechaSolicitud,Double precio)
	{
		return this.criteriosGarantia.calcularFechaFin(fechaSolicitud,precio);
	}

	public Double calcularPrecio(Double precio)
	{
		return this.criteriosGarantia.calcularPrecio(precio);
	}
}
