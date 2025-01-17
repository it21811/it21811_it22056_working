package gr.hua.dit.ds.ds_lab_2024.repositories;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
    @Query("SELECT p FROM Property p WHERE " +
            "(:address IS NULL OR p.address LIKE %:address%) AND " +
            "(:municipality IS NULL OR p.municipality LIKE %:municipality%) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:verified IS NULL OR p.verified = :verified)")
    List<Property> filterProperties(
            @Param("address") String address,
            @Param("municipality") String municipality,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("verified") Integer verified);

    @Query("SELECT DISTINCT p.municipality FROM Property p")
    List<String> findDistinctMunicipalities();
}
