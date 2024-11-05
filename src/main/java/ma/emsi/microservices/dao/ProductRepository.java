package ma.emsi.microservices.dao;

import ma.emsi.microservices.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<String, Product> {
}
