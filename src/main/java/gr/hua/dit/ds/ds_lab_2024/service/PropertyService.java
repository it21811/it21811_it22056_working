package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.repositories.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getProperties() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Integer id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isEmpty()) {
            throw new RuntimeException("Property not found with id: " + id);
        }
        return property.get();
    }

    public void saveProperty(Property property) {
        propertyRepository.save(property);
    }

    public void updateProperty(Property property) {
        propertyRepository.save(property);
    }

    public void deleteProperty(Integer id) {
        propertyRepository.deleteById(id);
    }
    public List<Property> filterProperties(String address, String municipality, Integer minPrice, Integer maxPrice, Integer verified) {
        return propertyRepository.filterProperties(address, municipality, minPrice, maxPrice, verified);
    }
    public List<String> getDistinctMunicipalities() {
        return propertyRepository.findDistinctMunicipalities();
    }
}
