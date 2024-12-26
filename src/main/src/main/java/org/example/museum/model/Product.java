package org.example.museum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Модель продукта.
 * <p>
 * Этот класс представляет сущность "Product", которая используется для хранения информации о продукте в системе.
 */
@Entity
public class Product {

    private Long id; // ID 
    private String name; // Название 
    private String artist; // Художник (ссылка на объект категории)
    private LocalDate creationDate; // Дата создания

    /**
     * Конструктор по умолчанию.
     * <p>
     * Создает новый объект продукта без значений для полей.
     */
    public Product() {
    }

    /**
     * Получение идентификатора продукта.
     *
     * @return Идентификатор продукта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Генерация идентификатора через автоинкремент
    public Long getId() {
        return id;
    }

    /**
     * Установка идентификатора .
     *
     * @param id Идентификатор 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Получение названия.
     *
     * @return Название 
     */
    @Column(nullable = false, length = 255) // Указываем обязательность и максимальную длину
    public String getName() {
        return name;
    }

    /**
     * Установка имени Художника.
     *
     * @param name художника
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получение категории.
     *
     * @return Категория 
     */
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // Связь с категорией, обязательность
    public Category getCategory() {
        return category;
    }

    /**
     * Установка категории продукта.
     *
     * @param category Категория продукта
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Получение производителя продукта.
     *
     * @return Производитель продукта
     */
    @Column(nullable = false, length = 255) // Указываем обязательность и максимальную длину
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Установка художника.
     *
     * @param manufacturer Художник экспоната
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Получение даты поставки продукта.
     *
     * @return Дата поставки
     */
    @Column(nullable = false) // Указываем обязательность
    public LocalDate creationDate() {
        return creationDate;
    }

    /**
     * Установка даты написания.
     *
     * @param deliveryDate Дата написани
     */
    public void creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}


