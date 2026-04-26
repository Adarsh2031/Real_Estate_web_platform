package org.example.controller;

import java.util.List;

import org.example.entity.Property;
import org.example.service.PropertyService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/properties")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PropertyController {

    @Inject
    PropertyService propertyService;

    // Add property
    @POST
    public Response addProperty(Property property, @HeaderParam("username") String username) {
        try {
            Property saved = propertyService.addProperty(property, username);
            return Response.ok(saved).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    // Get all properties
    @GET
    public List<Property> getAll() {
        return propertyService.getAllProperties();
    }

    // Get property by ID
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            return Response.ok(propertyService.getPropertyById(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    // Get properties by owner
    @GET
    @Path("/owner/{username}")
    public List<Property> getByOwner(@PathParam("username") String username) {
        return propertyService.getByOwner(username);
    }

    // Search by location
    @GET
    @Path("/search")
    public List<Property> search(@QueryParam("location") String location) {
        return propertyService.searchByLocation(location);
    }

    // Filter by price
    @GET
    @Path("/filter")
    public List<Property> filter(@QueryParam("min") double min,
                                @QueryParam("max") double max) {
        return propertyService.filterByPrice(min, max);
    }
}