package com.project1.Product.Managment.Service;

import com.project1.Product.Managment.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdRepository extends JpaRepository<Product, Integer> {

}
