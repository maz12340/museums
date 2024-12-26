package org.example.museum.controller;

import org.example.museum.model.Category;
import org.example.museum.model.Product;
import org.example.museum.repository.ProductRepository;
import org.example.museum.service.CategoryService;
import org.example.museum.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для работы с продуктами в системе.
 * <p>
 * Этот контроллер предоставляет RESTful API для управления продуктами, включая операции добавления,
 * обновления, удаления, получения данных о продукте, а также получения гистограммы по датам поставок.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Получение списка продуктов с возможностью фильтрации по ключевому слову.
     * <p>
     * Если параметр "keyword" не передан или пуст, возвращаются все продукты.
     * В противном случае выполняется поиск по ключевому слову.
     *
     * @param keyword ключевое слово для фильтрации продуктов (необязательный параметр)
     * @return ResponseEntity, содержащий список продуктов
     */
    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Product> products;
        if (keyword == null || keyword.isEmpty()) {
            products = productRepository.findAll();
        } else {
            products = productRepository.search(keyword);
        }
        return ResponseEntity.ok(products);
    }

    /**
     * Получение данных о продукте по его идентификатору.
     * <p>
     * Если продукт с указанным идентификатором найден, возвращается его информация.
     * Если продукт не найден, возвращается ошибка 404 (Not Found).
     *
     * @param id идентификатор продукта
     * @return ResponseEntity, содержащий данные продукта или статус 404, если продукт не найден
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.get(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    /**
     * Добавление нового продукта в систему.
     * <p>
     * Если у продукта указана новая категория (без идентификатора), она сохраняется в базе данных,
     * а затем продукт сохраняется с привязанной категорией.
     *
     * @param product объект продукта, который нужно добавить
     * @return ResponseEntity, содержащий добавленный продукт и статус 201 (Created), или ошибку 500 при проблемах
     */
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Category category = product.getCategory();
            if (category != null && category.getId() == null) {
                category = categoryService.save(category);
                product.setCategory(category);
            }
            Product savedProduct = productService.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
