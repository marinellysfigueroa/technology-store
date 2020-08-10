package com.ceiba.tiendatecnologica.dominio.unitaria;


import com.ceiba.tiendatecnologica.aplicacion.util.CriteriosGarantia;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.servicio.vendedor.ServicioVendedor;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioGarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;
import com.ceiba.tiendatecnologica.testdatabuilder.ProductoTestDataBuilder;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioVendedorTest {

	public static final double PRECIO = 200000.0;
	public static final double PRECIO_ESPERADO = 20000.0;
	public static final String CODIGO_VOCALES = "AEI1234";
	public static final String FECHA_INICIO_NO_DOM = "2018-08-16";
	public static final String FECHA_FIN_NO_DOM = "2019-04-06";
	public static final String PATTERN = "yyyy-MM-dd";
	public static final String FECHA_INICIO_DOM = "2018-08-17";
	public static final String FECHA_FIN_DOM = "2019-04-09";
	public static final String FECHA_INICIO = "2020-04-08";
	public static final String FFECHA_FIN = "2020-07-17";

	@Test
	public void productoYaTieneGarantiaTest() {
		
		// arrange
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		
		Producto producto = productoTestDataBuilder.build();
		
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(producto);
		
		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);
		
		// act 
		boolean existeProducto = servicioVendedor.tieneGarantia(producto.getCodigo());
		
		//assert
		assertTrue(existeProducto);
	}
	
	@Test
	public void productoNoTieneGarantiaTest() {
		
		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		
		Producto producto = productoestDataBuilder.build(); 
		
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(null);
		
		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);
		
		// act 
		boolean existeProducto =  servicioVendedor.tieneGarantia(producto.getCodigo());
		
		//assert
		assertFalse(existeProducto);
	}

	@Test
	public void productoCuentaConGarantia()
	{
		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.build();

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);

		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		// act
		boolean cuentaConGarantia = servicioVendedor.cuentaConGarantia(producto.getCodigo());

		//assert
		assertTrue(cuentaConGarantia);
	}

	@Test
	public void productoNoCuentaConGarantia()
	{
		// arrange
		String codigo= CODIGO_VOCALES;

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);

		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		// act
		boolean cuentaConGarantia = servicioVendedor.cuentaConGarantia(codigo);

		//assert
		assertFalse(cuentaConGarantia);
	}

	@Test
	public void calcularPrecioProductoMayor500()
	{
		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.build();

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);

		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		//assert
		assertEquals(java.util.Optional.of(130000.0), java.util.Optional.ofNullable(servicioVendedor.calcularPrecio(producto.getPrecio())));
	}

	@Test
	public void calcularFechaProductoMayor500NoDomingo()
	{
		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.build();

		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
		String fechaInicio = FECHA_INICIO_NO_DOM;
		String fechaFin = FECHA_FIN_NO_DOM;
		Date fechaSolicitud = null;
		Date fechaEsperada = null;
		try {
			fechaSolicitud = sdf.parse(fechaInicio);
			fechaEsperada= sdf.parse(fechaFin);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);

		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		//assert
		assertEquals(java.util.Optional.of(fechaEsperada), java.util.Optional.ofNullable(servicioVendedor.calcularFechaFin(fechaSolicitud,producto.getPrecio())));
	}

	@Test
	public void calcularFechaProductoMayor500Domingo()
	{
		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.build();

		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
		String fechaInicio = FECHA_INICIO_DOM;
		String fechaFin = FECHA_FIN_DOM;
		Date fechaSolicitud = null;
		Date fechaEsperada = null;
		try {
			fechaSolicitud = sdf.parse(fechaInicio);
			fechaEsperada= sdf.parse(fechaFin);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);

		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		//assert
		assertEquals(java.util.Optional.of(fechaEsperada), java.util.Optional.ofNullable(servicioVendedor.calcularFechaFin(fechaSolicitud,producto.getPrecio())));
	}

	@Test
	public void calcularPrecioProductoMenor500()
	{
		// arrange
		Double precio= PRECIO;

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);

		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		//assert
		assertEquals(java.util.Optional.of(PRECIO_ESPERADO), java.util.Optional.ofNullable(servicioVendedor.calcularPrecio(precio)));
	}

	@Test
	public void calcularFechaProductoMenor500()
	{
		// arrange
		Double precio=PRECIO;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaInicio = FECHA_INICIO;
		String fechaFin = FECHA_FIN;
		Date fechaSolicitud = null;
		Date fechaEsperada = null;
		try {
			fechaSolicitud = sdf.parse(fechaInicio);
			fechaEsperada= sdf.parse(fechaFin);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);

		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		//assert
		assertEquals(java.util.Optional.of(fechaEsperada), java.util.Optional.ofNullable(servicioVendedor.calcularFechaFin(fechaSolicitud,precio)));
	}


}
