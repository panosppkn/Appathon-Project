package com.InternetAndApplications.Project;

import javax.persistence.*;

@Entity
public class Content {

    @Id
    private String json_id;

    @Column(name = "body_text")
    private String bodyText;

    public String getJson_id() {
        return json_id;
    }

    public void setJson_id(String json_id) {
        this.json_id = json_id;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }
}
