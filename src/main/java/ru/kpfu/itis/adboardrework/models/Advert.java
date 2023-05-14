package ru.kpfu.itis.adboardrework.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User authorId;
    private String category;
    @Column(columnDefinition = "json")
    private String locations;
    private String salesStartDate;
    private String dateOfSale;
    @Enumerated(EnumType.STRING)
    private State state;
    @ManyToMany(mappedBy = "favorites")
    private List<User> users = new ArrayList<>();


    public List<Location> getLocations() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(locations, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void setLocations(List<Location> locations) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.locations = objectMapper.writeValueAsString(locations);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            this.locations = null;
        }
    }
}
