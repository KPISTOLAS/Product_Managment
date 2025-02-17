package com.project1.Product.Managment.Controller;


import com.project1.Product.Managment.Models.ProdDto;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.project1.Product.Managment.Models.Product;
import com.project1.Product.Managment.Service.ProdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/products")  // Changed to /products
public class ProdController {
    private final ProdRepository repo;

    public ProdController(ProdRepository repo) {
        this.repo = repo;
    }

    @GetMapping({"", "/"})
    public String showProdList(Model model) {
        List<Product> products = repo.findAll();  // This should fetch from the products table
        model.addAttribute("products", products);
        return "products/index";  // Ensure this is the correct template name
    }
    @GetMapping("/create")
    public String ShowcreateProd(Model model) {
        ProdDto prodDto = new ProdDto();
        model.addAttribute("prodDto", prodDto);
        return "products/create";
    }
    @PostMapping("/create")
    public String createProd(@Valid @ModelAttribute ProdDto prodDto, BindingResult res) {
        if (prodDto.getImage().isEmpty()) {
            res.addError(new FieldError("prodDto", "image", "Image is required"));
        }
        if (res.hasErrors()) {
            return "products/create";
        }

        MultipartFile img = prodDto.getImage();
        String storageFile = System.currentTimeMillis() + "_" + img.getOriginalFilename();
        String uploadDir = "public/images/";

        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            // Save the image
            try (InputStream is = img.getInputStream()) {
                Files.copy(is, path.resolve(storageFile), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
            res.addError(new FieldError("prodDto", "image", "Failed to save image"));
            return "products/create";
        }

        // Create and save the product
        Product product = new Product();
        product.setName(prodDto.getName());
        product.setBrand(prodDto.getBrand());
        product.setCategory(prodDto.getCategory());
        product.setPrice(prodDto.getPrice());
        product.setDescription(prodDto.getDescription());
        product.setDate(new Date());
        product.setImage(storageFile);

        try {
            repo.save(product);
        } catch (Exception e) {
            System.out.println("Error saving product: " + e.getMessage());
            return "products/create";
        }

        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public String showEditProd(@PathVariable int id, Model model) {
        return repo.findById(id)
                .map(product -> {
                    model.addAttribute("product", product); // Add the product object to the model
                    ProdDto prodDto = new ProdDto();
                    prodDto.setName(product.getName());
                    prodDto.setBrand(product.getBrand());
                    prodDto.setCategory(product.getCategory());
                    prodDto.setPrice(product.getPrice());
                    prodDto.setDescription(product.getDescription());
                    model.addAttribute("prodDto", prodDto); // Add the ProdDto object to the model
                    return "products/EditProduct"; // Return the template name
                })
                .orElseGet(() -> {
                    System.out.println("Product not found");
                    return "redirect:/products"; // Redirect if product not found
                });
    }
    @PostMapping("/edit/{id}")
    public String updateProd(@PathVariable int id, @Valid @ModelAttribute("prodDto") ProdDto prodDto, BindingResult res, Model model) {
        if (res.hasErrors()) {
            model.addAttribute("product", repo.findById(id).orElse(null)); // Add the product object to the model
            return "products/EditProduct"; // Return to edit page if there are validation errors
        }

        try {
            Product product = repo.findById(id).orElseThrow(() -> new Exception("Product not found"));

            product.setName(prodDto.getName());
            product.setBrand(prodDto.getBrand());
            product.setCategory(prodDto.getCategory());
            product.setPrice(prodDto.getPrice());
            product.setDescription(prodDto.getDescription());

            if (!prodDto.getImage().isEmpty()) {
                deleteOldImage(product.getImage());
                String storageFile = saveImage(prodDto.getImage());
                product.setImage(storageFile);
            }

            repo.save(product);
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
            return "products/EditProduct"; // Return to edit page on error
        }

        return "redirect:/products";
    }

    // Helper method to delete the old image
    private void deleteOldImage(String imageName) {
        try {
            Path imagePath = Paths.get("public/images/" + imageName);
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            System.out.println("Error deleting old image: " + e.getMessage());
        }
    }

    // Helper method to save the new image
    private String saveImage(MultipartFile img) throws IOException {
        String storageFile = System.currentTimeMillis() + "_" + img.getOriginalFilename();
        String uploadDir = "public/images/";
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        try (InputStream is = img.getInputStream()) {
            Files.copy(is, path.resolve(storageFile), StandardCopyOption.REPLACE_EXISTING);
        }
        return storageFile;
    }


    @GetMapping("/delete")
    public String deleteProd(@RequestParam int id) {
        try{
            Product product = repo.findById(id).get();
            Path imagepath = Paths.get(product.getImage());
            try{
                Files.delete(imagepath);
            }catch (Exception e){
                System.out.println("Error deleting old image");
            }
            repo.delete(product);
        }catch (Exception e){
            System.out.println("Error deleting product");
        }

        return "redirect:/products";
    }
    @PostMapping("/delete")
    public String deleteProdpost(@RequestParam int id) {
        try {
            // Find the product by ID
            Product product = repo.findById(id).orElseThrow(() -> new Exception("Product not found"));

            // Delete the associated image file if it exists
            Path imagePath = Paths.get("public/images/" + product.getImage());
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                System.out.println("Error deleting old image: " + e.getMessage());
            }

            // Delete the product from the repository
            repo.delete(product);
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }

        // Redirect to the products list after deletion
        return "redirect:/products";
    }
}
