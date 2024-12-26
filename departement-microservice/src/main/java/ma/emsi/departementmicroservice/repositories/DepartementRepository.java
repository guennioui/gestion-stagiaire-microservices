package ma.emsi.departementmicroservice.repositories;

import ma.emsi.departementmicroservice.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
    Optional<Departement> findByCode(String code);
}
