package br.com.first.stock.repository;

import org.springframework.data.repository.CrudRepository;
import br.com.first.stock.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
