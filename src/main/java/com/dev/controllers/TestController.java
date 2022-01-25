package com.dev.controllers;

import com.dev.Persist;
import com.dev.objects.OrgObject;
import com.dev.objects.SaleObject;
import com.dev.objects.ShopObject;
import com.dev.objects.UserObject;
import com.dev.utils.MessagesHandler;
import com.dev.utils.Utils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;


@RestController
public class TestController {


    @Autowired
    private Persist persist;


    @Autowired
    private MessagesHandler msgHandler;



    @PostConstruct
    public void init () {




    }

    @RequestMapping("sign-in")
    public String signIn (String username, String password)  {
        String token=null;
        if(!persist.userNameValidation(username)){
            token="userNotExist";
        }
        else {
            if (!persist.LoginAttempt(username, password)) {
                token = "invalidPassword";
            }

            if(persist.LoginAttempt(username,password)){
                token = persist.getTokenByUsernameAndPassword(username,password);
            }
        }
        return token;
    }

    @RequestMapping("create-account")
    public boolean createAccount(String username, String password) {
        boolean success = false;
        boolean alreadyExists = persist.getTokenByUsernameAndPassword(username, password) != null;
        if (!alreadyExists) {
            UserObject userObject = new UserObject();
            userObject.setUsername(username);
            userObject.setPassword(password);
            String hash = Utils.createHash(username, password);
            userObject.setToken(hash);
            userObject.setFirstLogin(true);
            success = persist.addAccount(userObject);
        }
        return success;
    }

    @RequestMapping("/get-username-by-token")
    public String getUsernameByToken(String token){

        return persist.getUsernameByToken(token);
    }


    @RequestMapping("get-id-by-token")
    public Integer getId(String token) {
        return persist.getUserIdByToken(token);
    }

    @RequestMapping("first-login")
    public boolean firstLogin(String token) {
        return persist.checkFirstLogin(token);
    }


    @RequestMapping("get-all-orgs")
    public List<OrgObject> getAllOrgs() {
        return this.persist.getAllOrgs();
    }

    @RequestMapping("get-shops")
    public List<ShopObject> getShops() {
        return this.persist.getShops();
    }

//    @RequestMapping("get-shop-name")
//    public String getShopName(Integer id) {
//        return this.persist.getShopName(id);
//    }

    @RequestMapping("get-search-results")
    public List<SaleObject> getSearchResults(String text) {
        return this.persist.getSearchResults(text);
    }

    @RequestMapping("get-shop-sales")
    public List<SaleObject> getShopSales(Integer id) {
        return this.persist.getShopSales(id);
    }

    @RequestMapping("get-my-sales")
    public List<SaleObject> getMySales(String token) {
        return this.persist.getMySales(token);
    }

    @RequestMapping("is-sale-open")
    public boolean isSaleOpen(String token, Integer saleId) {
        return this.persist.isSaleOpen(token, saleId);
    }

    @RequestMapping("get-my-orgs")
    public List<Integer> getMyOrgs(String token) {
        return this.persist.getMyOrgs(token);
    }

    @RequestMapping("update-org")
    public void updateOrgs(String token, Integer orgId) {
        this.persist.updateOrg(token, orgId);
    }

}