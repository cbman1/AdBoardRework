package ru.kpfu.itis.adboardrework.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.*;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "char(60)", nullable = false)
    private String hashPassword;

    @Column(length = 15)
    private String phoneNumber;

    @Column(columnDefinition = "char(64)")
    private String hashForConfirm;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "advert_id"))
    @JsonIgnore
    @ToString.Exclude
    private List<Advert> favorites = new ArrayList<>();
    private String avatarPath;
}



