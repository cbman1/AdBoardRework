package ru.kpfu.itis.adboardrework.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {
    private String region;
    private String city;
    private String street;
    private String house;
}
