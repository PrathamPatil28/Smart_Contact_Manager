package com.example.SCM.entity;

import jakarta.persistence.*;

@Entity
public class SocialLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String link;
    private String title;

    @ManyToOne
   private  Contact contact;

    public SocialLinks(){

    }

    public SocialLinks(Long id, String link, String title , Contact contact) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
