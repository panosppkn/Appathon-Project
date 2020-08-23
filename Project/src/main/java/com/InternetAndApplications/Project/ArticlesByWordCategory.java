package com.InternetAndApplications.Project;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "articlesbywordscategory")
public class ArticlesByWordCategory {
    @Id
    private String w_category;

    private Integer count;

    public String getW_category() {
        return w_category;
    }

    public void setW_category(String w_category) {
        this.w_category = w_category;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
