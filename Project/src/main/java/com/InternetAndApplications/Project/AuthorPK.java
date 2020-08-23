package com.InternetAndApplications.Project;

import java.io.Serializable;

public class AuthorPK implements Serializable {
    private String json_id;

    private Integer author_id;

    public String getJson_id() {
        return json_id;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public AuthorPK() {
    }

    public AuthorPK(String json_id, Integer author_id) {
        this.json_id = json_id;
        this.author_id = author_id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((json_id == null) ? 0 : json_id.hashCode());
        result = prime * result + author_id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AuthorPK other = (AuthorPK) obj;
        if (json_id == null) {
            if (other.json_id != null)
                return false;
        } else if (!json_id.equals(other.json_id))
            return false;
        if (author_id != other.author_id)
            return false;
        return true;
    }
}
