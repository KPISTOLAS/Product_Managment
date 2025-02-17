package com.project1.Product.Managment.Models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.aspectj.bridge.IMessage;
import org.springframework.web.multipart.MultipartFile;

public class ProdDto {
    @NotEmpty(message = "This name is required")
    private String name;
    @NotEmpty(message = "This brand is required")
    private String brand;
    @NotEmpty(message = "This category is required")
    private String category;
    @Min(0)
    private double price;

    @Size(min = 10, message = "The description must be at least 10 characters")
    @Size(max = 1000, message = "The description cannot exceed 1000 characters")
    private String description;

    private MultipartFile image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
