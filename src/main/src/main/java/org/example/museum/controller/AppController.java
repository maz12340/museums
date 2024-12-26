package org.example.museum.controller;

import org.example.museum.model.User;
import org.example.museum.service.CategoryService;
import org.example.museum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Контроллер, обрабатывающий запросы, связанные с отображением страниц и операциями с пользователями и категориями.
 * <p>
 * Этот контроллер обрабатывает все основные страницы приложения, включая главную, страницы для редактирования продуктов,
 * регистрации и авторизации, а также страницу для управления пользователями (доступно только администратору).
 */
@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Обрабатывает запрос на главную страницу.
     * @param model модель для передачи данных в представление
     * @return имя шаблона для отображения главной страницы
     */
    @GetMapping
    public String viewHomePage(Model model) {
        return "index";
    }

    /**
     * Отображает страницу для редактирования товара.
     * @param id идентификатор продукта, который необходимо отредактировать
     * @return имя шаблона для редактирования продукта
     */
    @GetMapping("/edit-product/{id}")
    public String editProduct(@PathVariable Long id) {
        return "edit_product";
    }

    /**
     * Отображает страницу для добавления нового продукта.
     * @return имя шаблона для добавления нового продукта
     */
    @GetMapping("/new-product")
    public String newProduct() {
        return "new_product";
    }

    /**
     * Отображает страницу со списком всех категорий.
     * @param model модель для передачи списка категорий в представление
     * @return имя шаблона для отображения категорий
     */
    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    /**
     * Отображает страницу с гистограммой.
     * @param model модель для передачи данных в представление
     * @return имя шаблона для отображения гистограммы
     */
    @GetMapping("/histogram")
    public String showHistogram(Model model) {
        return "histogram";
    }

    /**
     * Отображает страницу "Об авторе".
     * @return имя шаблона для отображения информации об авторе
     */
    @GetMapping("/author")
    public String aboutPage() {
        return "author";
    }

    /**
     * Отображает форму регистрации нового пользователя.
     * @param model модель для передачи объекта пользователя в форму
     * @return имя шаблона для страницы регистрации
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }
}
