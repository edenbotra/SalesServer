package com.dev.objects;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "sales")
public class SaleObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String saleStart;

    @Column
    private String saleEnd;


    @Column
    private boolean forAllUsers;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn (name = "shopId")
    private ShopObject shop;

    @ManyToMany
    private Set<OrgObject> organizations = new HashSet<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getSaleStart() {
        return saleStart;
    }

    public void setSaleStart(String saleStart) {
        this.saleStart = saleStart;
    }

    public String getSaleEnd() {
        return saleEnd;
    }

    public void setSaleEnd(String saleEnd) {
        this.saleEnd = saleEnd;
    }

    public boolean isForAllUsers() {
        return forAllUsers;
    }

    public void setForAllUsers(boolean forAllUsers) {
        this.forAllUsers = forAllUsers;
    }

    public Set<OrgObject> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<OrgObject> organizations) {
        this.organizations = organizations;
    }

    public void addOrganization(OrgObject org) {
        organizations.add(org);
    }

    public void removeOrganization(OrgObject org) {
        organizations.remove(org);
    }

    public ShopObject getStore() {
        return shop;
    }

    public void setStore(ShopObject shop) {
        this.shop = shop;
    }

    public ShopObject getShop() {
        return shop;
    }

    public void setShop(ShopObject shop) {
        this.shop = shop;
    }
}