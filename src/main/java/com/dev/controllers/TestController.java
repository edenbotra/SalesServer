package com.dev.controllers;

import com.dev.Persist;
import com.dev.objects.OrgObject;
import com.dev.objects.SaleObject;
import com.dev.objects.ShopObject;
import com.dev.objects.UserObject;
import com.dev.utils.MessagesHandler;
import com.dev.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@RestController
public class TestController {

    @Autowired
    private MessagesHandler msgHandler;
    @Autowired
    private Persist persist;


    @PostConstruct
    public void init () {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // create thread here
        List<UserObject> users = this.persist.getAllUsers();


        new Thread(() -> {
            while (true) {

                try {

                    Thread.sleep(30000);

                    List<SaleObject> sales = this.persist.getAllSales();

                    for (SaleObject sale : sales) {

                        // parsing the date string from sale
                        String startTime = sale.getSaleStart();
                        LocalDateTime startTimeParsed = LocalDateTime.parse(startTime, formatter);

                        if (LocalDateTime.now().isAfter(startTimeParsed) && !sale.isNotifiedStart()) {

                            // mark sale as notified!
                            this.persist.updateNotified(sale.getId(), 0);

                            // create the notification text and title
                            JSONObject notificationStart = new JSONObject();
                            notificationStart.put(sale.getShop().getName(), sale.getDescription());

                            if (sale.isForAllUsers()) {
                                // now send to all users the notification
                                msgHandler.sendNotificationToAll(notificationStart);
                            }
                            else {
                                // searching for the right users
                                List<String> usersTokens = new ArrayList<>();
                                for (UserObject user : users) {
                                    if (this.persist.isSaleOpen(user.getToken(), sale.getId())) {
                                        usersTokens.add(user.getToken());
                                    }
                                }
                                // now send to specific users the notification
                                msgHandler.sendNotification(notificationStart, usersTokens);
                            }


                        }

                        String endTime = sale.getSaleEnd();
                        LocalDateTime endTimeParsed = LocalDateTime.parse(endTime, formatter);

                        if (LocalDateTime.now().isAfter(endTimeParsed) && !sale.isNotifiedEnd()) {

                            // mark sale as notified!
                            this.persist.updateNotified(sale.getId(), 1);

                            // create the notification text and title
                            JSONObject notificationEnd = new JSONObject();
                            notificationEnd.put(sale.getShop().getName(), " This sale: " + sale.getDescription() + " is OVER NOW");

                            if (sale.isForAllUsers()) {
                                // now send to all users the notification
                                msgHandler.sendNotificationToAll(notificationEnd);
                            } else {
                                // searching for the right users
                                List<String> usersTokens = new ArrayList<>();
                                for (UserObject user : users) {
                                    if (this.persist.isSaleOpen(user.getToken(), sale.getId())) {
                                        usersTokens.add(user.getToken());
                                    }
                                }
                                // now send to specific users the notification
                                msgHandler.sendNotification(notificationEnd, usersTokens);
                            }
                        }
                    }
                    System.out.println("Executed Thread");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @RequestMapping(value = "sign-in", method = RequestMethod.GET )
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

    @RequestMapping(value = "create-account", method = RequestMethod.POST)
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

    @RequestMapping(value = "/get-username-by-token",  method = RequestMethod.GET)
    public String getUsernameByToken(String token){

        return persist.getUsernameByToken(token);
    }


    @RequestMapping(value = "get-id-by-token", method = RequestMethod.GET)
    public Integer getId(String token) {
        return persist.getUserIdByToken(token);
    }

    @RequestMapping(value = "first-login", method = RequestMethod.GET)
    public boolean firstLogin(String token) {
        return persist.checkFirstLogin(token);
    }

    @RequestMapping(value = "get-shop-name", method = RequestMethod.GET)
    public String getShopName(Integer id) {
        return this.persist.getShopName(id);
    }

    @RequestMapping(value = "get-shop-name-from-sale", method = RequestMethod.GET)
    public String getShopNameFromSale(Integer id) {
        return this.persist.getShopNameFromSale(id);
    }


    @RequestMapping(value = "get-all-orgs", method = RequestMethod.GET)
    public List<OrgObject> getAllOrgs() {
        return this.persist.getAllOrgs();
    }

    @RequestMapping(value = "get-shops", method = RequestMethod.GET)
    public List<ShopObject> getShops() {
        return this.persist.getShops();
    }


    @RequestMapping(value = "get-search-results", method = RequestMethod.GET)
    public List<SaleObject> getSearchResults(String text) {
        return this.persist.getSearchResults(text);
    }

    @RequestMapping(value = "get-shop-sales", method = RequestMethod.GET)
    public List<SaleObject> getShopSales(Integer id) {
        return this.persist.getShopSales(id);
    }

    @RequestMapping(value = "get-my-sales", method = RequestMethod.GET)
    public List<SaleObject> getMySales(String token) {
        return this.persist.getMySales(token);
    }

    @RequestMapping(value = "is-sale-open", method = RequestMethod.GET)
    public boolean isSaleOpen(String token, Integer saleId) {
        return this.persist.isSaleOpen(token, saleId);
    }

    @RequestMapping(value = "get-my-orgs", method = RequestMethod.GET)
    public List<Integer> getMyOrgs(String token) {
        return this.persist.getMyOrgs(token);
    }

    @RequestMapping(value = "update-org", method = RequestMethod.POST)
    public void updateOrgs(String token, Integer orgId) {
        this.persist.updateOrg(token, orgId);
    }
}