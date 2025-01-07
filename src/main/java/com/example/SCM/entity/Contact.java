package com.example.SCM.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contact {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private String picture;
    private String description;
    private boolean favourite = false;
    private String webSiteLink;
    private String linkedinLink;
    private String cloudinaryImagePublicId;

    @Column(name = "added_date")
    private LocalDateTime addedDate;


    @ManyToOne
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL ,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLinks> socialLinks = new ArrayList<>();

    public Contact (){

    }

    public Contact(String address, String description, String email, boolean favourite, String id, String name, String password, String picture, List<SocialLinks> socialLinks, User user, String webSiteLink , String linkedinLink ,String phoneNumber , String cloudinaryImagePublicId , LocalDateTime addedDate) {
        this.address = address;
        this.description = description;
        this.email = email;
        this.favourite = favourite;
        this.id = id;
        this.name = name;
        this.password = password;
        this.picture = picture;
        this.socialLinks = socialLinks;
        this.user = user;
        this.webSiteLink = webSiteLink;
        this.linkedinLink = linkedinLink;
        this.phoneNumber = phoneNumber;
        this.cloudinaryImagePublicId = cloudinaryImagePublicId;
        this.addedDate = addedDate;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWebSiteLink() {
        return webSiteLink;
    }

    public void setWebSiteLink(String webSiteLink) {
        this.webSiteLink = webSiteLink;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }

    public void setLinkedinLink(String linkedinLink) {
        this.linkedinLink = linkedinLink;
    }

    public List<SocialLinks> getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(List<SocialLinks> socialLinks) {
        this.socialLinks = socialLinks;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCloudinaryImagePublicId() {
        return cloudinaryImagePublicId;
    }

    public void setCloudinaryImagePublicId(String cloudinaryImagePublicId) {
        this.cloudinaryImagePublicId = cloudinaryImagePublicId;
    }


    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", favourite=" + favourite +
                ", webSiteLink='" + webSiteLink + '\'' +
                ", linkedinLink='" + linkedinLink + '\'' +
                ", cloudinaryImagePublicId='" + cloudinaryImagePublicId + '\'' +
                ", addedDate=" + addedDate +
                ", user=" + user +
                ", socialLinks=" + socialLinks +
                '}';
    }
}