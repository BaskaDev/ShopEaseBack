package co.edu.unbosque.shopease_app.service;

import co.edu.unbosque.shopease_app.model.CategoriaModel;
import co.edu.unbosque.shopease_app.model.ProductoModel;
import co.edu.unbosque.shopease_app.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final Logger logger = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;


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
        if (productoRepository.existsById(id)) { // Verifica si la producto existe
            productoRepository.deleteById(id); // Elimina directamente por id
            return true;
        } else {
            throw new EntityNotFoundException("El producto con ID " + id + " no existe.");
        }
    }

    public List<ProductoModel> findAll(){
        return productoRepository.findAll();
    }
}