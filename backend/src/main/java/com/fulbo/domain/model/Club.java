package com.fulbo.domain.model;

public class Club {

    private Long id;
    private String name;
    private String shortName;
    private String logoUrl;
    private String stadiumName;
    private String city;
    private Integer foundedYear;

    public Club() {}

    public Club(Long id, String name, String shortName, String logoUrl,
                String stadiumName, String city, Integer foundedYear) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.logoUrl = logoUrl;
        this.stadiumName = stadiumName;
        this.city = city;
        this.foundedYear = foundedYear;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getShortName() { return shortName; }
    public void setShortName(String shortName) { this.shortName = shortName; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public String getStadiumName() { return stadiumName; }
    public void setStadiumName(String stadiumName) { this.stadiumName = stadiumName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public Integer getFoundedYear() { return foundedYear; }
    public void setFoundedYear(Integer foundedYear) { this.foundedYear = foundedYear; }
}
