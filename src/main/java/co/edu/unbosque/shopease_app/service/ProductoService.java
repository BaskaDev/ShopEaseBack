package co.edu.unbosque.shopease_app.service;

import co.edu.unbosque.shopease_app.model.CategoriaModel;
import co.edu.unbosque.shopease_app.model.ProductoModel;
import co.edu.unbosque.shopease_app.repository.CategoriaRepository;
import co.edu.unbosque.shopease_app.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ProductoService {
	private final Logger logger = LoggerFactory.getLogger(ProductoService.class);

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired 
	private CategoriaService categoriaService;


	public ProductoModel saveProducto(ProductoModel producto) {

		ProductoModel productoModel = productoRepository.save(producto);
		return productoModel;
	}

	public ProductoModel updateProducto(int id, ProductoModel producto) {
		if (productoRepository.existsById(id)) {
			producto.setId_producto(id);
			return productoRepository.save(producto);
		} else {
			return null;
		}

	}

	public Boolean deleteProducto(int id) {
		if (productoRepository.existsById(id)) {
			productoRepository.deleteById(id); 
			return true;
		} else {
			throw new EntityNotFoundException("El producto con ID " + id + " no existe.");
		}
	}

	public List<ProductoModel> findAll(){
		return productoRepository.findAll();
	}


	public Map<Object, List<ProductoModel>> obtenerProductosOrdenadosPorNombreCategoria() {

		// Obtener todas las categorías
		List<CategoriaModel> categorias = categoriaService.findAll();

		// Crear un mapa para las categorías por ID para búsqueda rápida
		Map<Integer, String> idCategoriaANombre = categorias.stream()
				.collect(Collectors.toMap(CategoriaModel::getId_categoria, CategoriaModel::getNombre));


		// Obtener todos los productos
		List<ProductoModel> productos = findAll();

		// Agrupar productos por nombre de categoría
		Map<Object, List<ProductoModel>> productosOrdenadosPorCategoria = productos.stream()
				.filter(producto -> idCategoriaANombre.containsKey(producto.getId_categoria()))
				.collect(Collectors.groupingBy(producto -> idCategoriaANombre.get(producto.getId_categoria()), TreeMap::new, Collectors.toList()));

		return productosOrdenadosPorCategoria;
	}

}
