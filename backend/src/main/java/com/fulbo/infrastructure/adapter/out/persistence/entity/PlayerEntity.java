package com.fulbo.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clubId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String position;
    private String nationality;
    private Integer shirtNumber;
    private String photoUrl;
    private Double marketValue;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClubId() { return clubId; }
    public void setClubId(Long clubId) { this.clubId = clubId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public Integer getShirtNumber() { return shirtNumber; }
    public void setShirtNumber(Integer shirtNumber) { this.shirtNumber = shirtNumber; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public Double getMarketValue() { return marketValue; }
    public void setMarketValue(Double marketValue) { this.marketValue = marketValue; }
}
