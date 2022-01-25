package com.dev;

import com.dev.objects.OrgObject;
import com.dev.objects.SaleObject;
import com.dev.objects.ShopObject;
import com.dev.objects.UserObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Persist {
    private final SessionFactory sessionFactory;

    @Autowired
    public Persist (SessionFactory sf) {
        this.sessionFactory = sf;
    }


    @PostConstruct
    public void init () {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        OrgObject org = new OrgObject();
        org.setName("Food for Africa Org");
        session.saveOrUpdate(org);

        org = new OrgObject();
        org.setName("Breast Cancer Awareness Org");
        session.saveOrUpdate(org);

        org = new OrgObject();
        org.setName("GreenPeace");
        session.saveOrUpdate(org);

        org = new OrgObject();
        org.setName("Doctors without borders");
        session.saveOrUpdate(org);

        ShopObject shop = new ShopObject();
        shop.setName("Pull and Bear");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("Castro");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("Karbitz");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("Max Stock");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("IKEA");
        session.saveOrUpdate(shop);

        SaleObject sale = new SaleObject();
        sale.setForAllUsers(true);
        sale.setDescription("10% price drop at pull and bear!");
        sale.setStore(session.load(ShopObject.class, 1));

        sale.addOrganization((OrgObject) session.createQuery("from OrgObject where id =: id")
                .setParameter("id", 1).uniqueResult());
        session.saveOrUpdate(sale);

        transaction.commit();
        session.close();
    }


    public String getTokenByUsernameAndPassword(String username, String password) {
        String token = null;
        Session session = sessionFactory.openSession();
        UserObject userObject = (UserObject) session.createQuery("FROM UserObject WHERE username = :username AND password = :password")
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
        session.close();
        if (userObject != null) {
            token = userObject.getToken();
        }
        return token;
    }

    public boolean addAccount (UserObject userObject) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(userObject);
        transaction.commit();
        session.close();
        if (userObject.getId() > 0) {
            success = true;
        }
        return success;
    }

    public Integer getUserIdByToken (String token) {
        Integer id = null;
        Session session = sessionFactory.openSession();
        UserObject userObject = (UserObject) session.createQuery("FROM UserObject WHERE token = :token").setParameter("token", token).uniqueResult();
        session.close();
        if (userObject != null) {
            id = userObject.getId();
        }
        return id;
    }

    public String getUsernameByToken(String token) {
        String username = null;
        Session session = sessionFactory.openSession();
        UserObject userObject = (UserObject) session.createQuery("FROM UserObject WHERE token = :token").setParameter("token", token).uniqueResult();
        session.close();
        if (userObject != null) {
            username = userObject.getUsername();
        }
        return username;
    }

    public boolean LoginAttempt(String username, String password) {//return true if username and password matches
        boolean passwordIsValid = false;
        Session session = sessionFactory.openSession();
        UserObject userObject = (UserObject) session.createQuery("FROM UserObject WHERE username = :username").setParameter("username", username).uniqueResult();

        if (userObject != null) {
            if (userObject.getPassword().equals(password)) {
                passwordIsValid = true;
            }
        }
        session.close();
        return passwordIsValid;
    }

    public boolean userNameValidation(String username) {
        boolean usernameUsed = false;

        Session session = sessionFactory.openSession();
        UserObject userObject = (UserObject) session.createQuery("FROM UserObject WHERE username = :username").setParameter("username", username).uniqueResult();

        if (userObject != null) {
            usernameUsed = true;
        }
        return usernameUsed;
    }

    public boolean checkFirstLogin(String token) {

        boolean flag = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        UserObject userObject = session.load(UserObject.class, getUserIdByToken(token));

        if (userObject != null) {
            flag = userObject.isFirstLogin();
            userObject.setFirstLogin(false);
            session.update(userObject);

        }
        transaction.commit();
        session.close();
        return flag;
    }

    public List<OrgObject> getAllOrgs() {
        Session session = sessionFactory.openSession();
        List<OrgObject> orgs = session.createQuery("SELECT o FROM OrgObject o",OrgObject.class).getResultList();
        session.close();
        return orgs;
    }

    public List<ShopObject> getShops() {
        Session session = sessionFactory.openSession();
        List<ShopObject> stores = session.createQuery("SELECT s from ShopObject s", ShopObject.class).getResultList();
        session.close();
        return stores;
    }

//    public String getShopName(Integer id) {
//        Session session = sessionFactory.openSession();
//        ShopObject store = (ShopObject) session.createQuery("from ShopObject a where id =: id")
//                .setParameter("id", id)
//                .uniqueResult();
//        session.close();
//        return store.getName();
//    }

    public List<SaleObject> getShopSales(Integer id) {
        Session session = sessionFactory.openSession();
        List<SaleObject> sales =  session.createQuery("from SaleObject where shop.id =: id").setParameter("id", id).getResultList();
        session.close();
        return sales;
    }

    public List<SaleObject> getSearchResults(String text) {
        Session session = sessionFactory.openSession();
        List<SaleObject> sales =  session.createQuery("from SaleObject where description like :text").setParameter("text", "%" + text + "%").getResultList();
        session.close();
        return sales;
    }

    public List<SaleObject> getMySales(String token) {
        Session session = sessionFactory.openSession();
        List<SaleObject> sales = session.createQuery("select s from SaleObject s", SaleObject.class).getResultList();
        List<SaleObject> openSales = new ArrayList<>();
        for (SaleObject sale : sales) {

            if (isSaleOpen(token, sale.getId())) {
                openSales.add(sale);
            }
        }

        session.close();
        return openSales;
    }


    public boolean isSaleOpen(String token, Integer saleId) {

        Session session = sessionFactory.openSession();
        SaleObject sale = session.load(SaleObject.class, saleId);
        if (sale.isForAllUsers()) {
            return true;
        }
        List userOrgs =  session.createQuery("select o.id from UserObject u join u.organizations o where u.token =: token").setParameter("token", token).getResultList();
        List saleOrgs =  session.createQuery("select o.id from SaleObject s join s.organizations o where s.id =:id").setParameter("id", saleId).getResultList();
        session.close();
        for (Object i : userOrgs) {
            for (Object j : saleOrgs) {
                if (i == j) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<SaleObject> getAllSales() {
        Session session = sessionFactory.openSession();
        List<SaleObject> sales = session.createQuery("select a from SaleObject a", SaleObject.class).getResultList();
        session.close();
        return sales;
    }

    public List<Integer> getMyOrgs (String token) {
        Session session = sessionFactory.openSession();
        List<Integer> ids = session.createQuery("select o.id from UserObject u join u.organizations o where u.token =: token")
                .setParameter("token", token).getResultList();
        session.close();
        return ids;
    }

    public void updateOrg(String token, Integer orgId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserObject userObject = session.load(UserObject.class, getUserIdByToken(token));
        OrgObject org = session.load(OrgObject.class, orgId);

        if (userObject.getOrganizations().contains(org)) {
            userObject.removeOrganization(org);
        }
        else {
            userObject.addOrganization(org);
        }
        session.saveOrUpdate(userObject);
        transaction.commit();
        session.close();
    }
}