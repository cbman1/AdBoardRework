package ru.kpfu.itis.adboardrework.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private String coordinates;
    private String salesStartDate;
    private String dateOfSale;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private State state;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name="favorites",
            joinColumns=@JoinColumn (name="advert_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    @JsonIgnore
    @ToString.Exclude
    private List<User> users = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Advert other = (Advert) obj;
        return Objects.equals(this.name, other.name);
    }
}
