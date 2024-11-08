package co.edu.unbosque.shopease_app.controller;


import co.edu.unbosque.shopease_app.model.CategoriaModel;
import co.edu.unbosque.shopease_app.model.ProductoModel;
import co.edu.unbosque.shopease_app.service.CategoriaService;
import co.edu.unbosque.shopease_app.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Transactional
@CrossOrigin(origins = { "http://localhost:8090", "http://localhost:8080", "*" })
@RestController
@RequestMapping("/producto")
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@Autowired
	private CategoriaService categoriaService;

	@PostMapping("/crear")
	@Operation(summary = "Crear Producto", description = "Crea un producto de acuerdo a un cuerpo JSON.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ProductoModel.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	public ResponseEntity<String> crearProducto(@RequestBody ProductoModel producto) {
		try {

			ProductoModel nuevoProducto = productoService.saveProducto(producto);
			return ResponseEntity.ok("Se insertó el producto");
		} catch (Exception e) {
			e.printStackTrace(); // Considera usar un logger en lugar de imprimir la traza
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al guardar el producto: " + e.getMessage());
		}
	}
	@GetMapping("/listar")
	@Operation(summary = "Obtener lista de productos ", description = "Obtener lista de productos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Productos encontradas"),
			@ApiResponse(responseCode = "404", description = "Productos no encontradas")
	})
	public ResponseEntity<List<ProductoModel>> listarTodosProductos() {
		List <ProductoModel> productos = productoService.findAll();
		if (productos != null) {
			return ResponseEntity.ok(productos);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@PutMapping("/actualizar/{id}")
	@Operation(summary = "Actualizar un producto", description = "Actualiza un producto existente.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente", content = @Content(schema = @Schema(implementation = ProductoModel.class))),
			@ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
	})
	public ResponseEntity<ProductoModel> actualizaProductos(@PathVariable int id, @RequestBody ProductoModel productoModel) {

		ProductoModel actualizarProducto= productoService.updateProducto(id,productoModel);
		if (actualizarProducto != null) {
			return ResponseEntity.ok(actualizarProducto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	@DeleteMapping("/eliminar/{id}")
	@Operation(summary = "Eliminar Producto", description = "Elimina una Producto por su ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto eliminada exitosamente"),
			@ApiResponse(responseCode = "404", description = "Producto no encontrada"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	public ResponseEntity<String> eliminarProducto(@PathVariable int id) {
		try {
			boolean isRemoved = productoService.deleteProducto(id);
			if (isRemoved) {
				return ResponseEntity.ok("Producto eliminada exitosamente.");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrada.");
			}
		} catch (Exception e) {
			e.printStackTrace(); // Considera usar un logger
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al eliminar la producto: " + e.getMessage());
		}
	}


	@GetMapping("/productos-ordenados-por-categoria")
	@Operation(summary = "Obtener productos ordenados por categoría", description = "Devuelve una lista de productos agrupados y ordenados por el nombre de la categoría en formato JSON")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Productos encontrados y ordenados"),
			@ApiResponse(responseCode = "404", description = "Productos o categorías no encontrados")
	})
	public ResponseEntity<Map<Object, List<ProductoModel>>> obtenerProductosOrdenadosPorNombreCategoria() {
		// Obtener el mapa de productos ordenados por nombre de categoría desde el servicio
		Map<Object, List<ProductoModel>> productosOrdenadosPorCategoria = productoService.obtenerProductosOrdenadosPorNombreCategoria();

		if (productosOrdenadosPorCategoria.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(productosOrdenadosPorCategoria);
	}

}
