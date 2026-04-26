package org.example.service;

import java.util.List;

import org.example.entity.Property;
import org.example.entity.User;
import org.example.repository.PropertyRepository;
import org.example.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PropertyService {

    @Inject
    PropertyRepository propertyRepository;

    @Inject
    UserRepository userRepository;

    // Add property
    public Property addProperty(Property property, String username) {

        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        property.setOwner(owner);
        propertyRepository.persist(property);

        return property;
    }

    // Get all properties
    public List<Property> getAllProperties() {
        return propertyRepository.listAll();
    }

    // Get property by ID
    public Property getPropertyById(Long id) {
        return propertyRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    }

    // Get properties by owner
    public List<Property> getByOwner(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return propertyRepository.findByOwner(user);
    }

    // Search by location
    public List<Property> searchByLocation(String location) {
        return propertyRepository.findByLocation(location);
    }

    // Filter by price
    public List<Property> filterByPrice(double min, double max) {
        return propertyRepository.findByPriceRange(min, max);
    }
}