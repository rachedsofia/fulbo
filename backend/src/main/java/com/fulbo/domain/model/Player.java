package com.fulbo.domain.model;

public class Player {

    private Long id;
    private Long clubId;
    private String firstName;
    private String lastName;
    private String position;
    private String nationality;
    private Integer shirtNumber;
    private String photoUrl;
    private Double marketValue;

    public Player() {}

    public Player(Long id, Long clubId, String firstName, String lastName, String position,
                  String nationality, Integer shirtNumber, String photoUrl, Double marketValue) {
        this.id = id;
        this.clubId = clubId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.nationality = nationality;
        this.shirtNumber = shirtNumber;
        this.photoUrl = photoUrl;
        this.marketValue = marketValue;
    }

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
