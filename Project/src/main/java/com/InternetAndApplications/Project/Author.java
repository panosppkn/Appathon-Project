package com.InternetAndApplications.Project;

import javax.persistence.*;

@Entity
@IdClass(AuthorPK.class)
public class Author {

    @Id
    private String json_id;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer author_id;

    private String name;

    private String institution;

    private String settlement;

    private String country;

    public String getJson_id() {
        return json_id;
    }

    public void setJson_id(String json_id) {
        this.json_id = json_id;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

