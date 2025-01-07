package com.example.SCM.entity;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity

public class User implements UserDetails {

    @Id
    private String userId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String about;
    private String profilePic;
    private String phoneNumber;
    private String theme;

    //Verification
    private boolean enabled = false;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;


    //provider
    @Enumerated(value = EnumType.STRING)
    private Provider provider = Provider.SELF;
    private String providerId;

    //contact
    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL , fetch = FetchType.LAZY , orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ContactUsForm> contactForms = new ArrayList<>();

    private String emailToken;



    public User(){

    }

    public User(String about, List<ContactUsForm> contactForms, List<Contact> contacts, String email, boolean emailVerified, boolean enabled, String name, String password, String phoneNumber, boolean phoneVerified, String profilePic, Provider provider, String providerId, String userId,String theme,String emailToken) {
        this.about = about;
        this.contactForms = contactForms;
        this.contacts = contacts;
        this.email = email;
        this.emailVerified = emailVerified;
        this.enabled = enabled;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.phoneVerified = phoneVerified;
        this.profilePic = profilePic;
        this.provider = provider;
        this.providerId = providerId;
        this.userId = userId;
        this.theme = theme;
        this.emailToken = emailToken;
    }


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // list of roles[USER,ADMIN]
        // Collection of SimpleGrantedAuthority[roles{ADMIN,USER}]
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return roles;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    // for this project:
    // email id hai wahi hamare username

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return  this.enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ContactUsForm> getContactForms() {
        return contactForms;
    }

    public void setContactForms(List<ContactUsForm> contactForms) {
        this.contactForms = contactForms;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    //    @Override
//    public String toString() {
//        return "User{" +
//                "about='" + about + '\'' +
//                ", userId='" + userId + '\'' +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", profilePic='" + profilePic + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", enabled=" + enabled +
//                ", emailVerified=" + emailVerified +
//                ", phoneVerified=" + phoneVerified +
//                ", provider=" + provider +
//                ", providerId='" + providerId + '\'' +
//                ", contacts=" + contacts +
//                ", contactForms=" + contactForms +
//                ", roleList=" + roleList +
//                '}';
//    }
}
