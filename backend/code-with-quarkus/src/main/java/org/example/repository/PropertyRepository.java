package org.example.repository;

import java.util.List;

import org.example.entity.Property;
import org.example.entity.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PropertyRepository implements PanacheRepository<Property> {

    // Get all properties of a specific user
    public List<Property> findByOwner(User owner) {
        return list("owner", owner);
    }

    // Search by location
    public List<Property> findByLocation(String location) {
        return list("LOWER(location) LIKE LOWER(?1)", "%" + location + "%");
    }

    // Filter by price range
    public List<Property> findByPriceRange(double min, double max) {
        return list("price BETWEEN ?1 AND ?2", min, max);
    }
}