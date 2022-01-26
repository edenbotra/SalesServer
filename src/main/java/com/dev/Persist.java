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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


        ////////////////////////
        //  ORGS              //
        ///////////////////////

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

        org = new OrgObject();
        org.setName("Sharing is Caring");
        session.saveOrUpdate(org);

        org = new OrgObject();
        org.setName("Heart's Wish");
        session.saveOrUpdate(org);

        org = new OrgObject();
        org.setName("Feminism for Life");
        session.saveOrUpdate(org);

        org = new OrgObject();
        org.setName("One.co.il Fans");
        session.saveOrUpdate(org);


        ///////////////////
        //    SHOPS      //
        ///////////////////

        ShopObject shop = new ShopObject();
        shop.setName("Pull and Bear");
        shop.setImgSrc("http://is.am/4u34");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("Castro");
        shop.setImgSrc("http://is.am/4u35");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("Karbitz");
        shop.setImgSrc("http://is.am/4u36");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("Max Stock");
        shop.setImgSrc("http://is.am/4u37");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("IKEA");
        shop.setImgSrc("http://is.am/4u38");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("Fattal");
        shop.setImgSrc("http://is.am/4u39");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("Mega Sport");
        shop.setImgSrc("http://is.am/4u3a");
        session.saveOrUpdate(shop);

        shop = new ShopObject();
        shop.setName("GUCCI");
        shop.setImgSrc("http://is.am/4u3b");
        session.saveOrUpdate(shop);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.now().plusSeconds(40);

        /////////////////////////////////
        // for specific users sales    //
        /////////////////////////////////
        SaleObject sale = new SaleObject();
        sale.setForAllUsers(false);
        sale.setDescription("10% price drop for all jeans at Pull & Bear!");
        sale.setShop(session.load(ShopObject.class, 1));
        sale.addOrganization(session.load(OrgObject.class, 1));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(2).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);



        startTime = LocalDateTime.now().plusSeconds(50);

        sale = new SaleObject();
        sale.setForAllUsers(false);
        sale.setDescription("10% free item for hoodies at castro!");
        sale.setShop(session.load(ShopObject.class, 2));
        sale.addOrganization(session.load(OrgObject.class, 2));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(1).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);

        startTime = LocalDateTime.now().plusSeconds(45);


        sale = new SaleObject();
        sale.setForAllUsers(false);
        sale.setDescription("get 1 thousand free pencils here!");
        sale.setShop(session.load(ShopObject.class, 3));
        sale.addOrganization(session.load(OrgObject.class, 3));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(2).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);


        startTime = LocalDateTime.now().plusSeconds(60);
        sale = new SaleObject();
        sale.setForAllUsers(false);
        sale.setDescription("buy 3 plates get 1 chair for free");
        sale.setShop(session.load(ShopObject.class, 4));
        sale.addOrganization(session.load(OrgObject.class, 4));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(1).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);

        startTime = LocalDateTime.now().plusSeconds(60);

        sale = new SaleObject();
        sale.setForAllUsers(false);
        sale.setDescription("Buy 1 table get 100 meat balls for free!");
        sale.setShop(session.load(ShopObject.class, 5));
        sale.addOrganization(session.load(OrgObject.class, 5));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(1).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);

        startTime = LocalDateTime.now().plusSeconds(70);

        sale = new SaleObject();
        sale.setForAllUsers(false);
        sale.setDescription("Book a weekend and get free month stay at our hotel");
        sale.setShop(session.load(ShopObject.class, 6));
        sale.addOrganization(session.load(OrgObject.class, 6));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(1).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);

        startTime = LocalDateTime.now().plusSeconds(40);

        sale = new SaleObject();
        sale.setForAllUsers(false);
        sale.setDescription("Buy 1 soccer ball and your favorite team will win! [we use black magic]");
        sale.setShop(session.load(ShopObject.class, 7));
        sale.addOrganization(session.load(OrgObject.class, 7));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(1).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);

        startTime = LocalDateTime.now().plusSeconds(55);

        sale = new SaleObject();
        sale.setForAllUsers(false);
        sale.setDescription("no sales here - sales for poor people!  [GUCCI]");
        sale.setShop(session.load(ShopObject.class, 8));
        sale.addOrganization(session.load(OrgObject.class, 8));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(2).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);


        ////////////////////////////
        // for all users sales    //
        ////////////////////////////

        startTime = LocalDateTime.now().plusSeconds(50);

        sale = new SaleObject();
        sale.setForAllUsers(true);
        sale.setDescription("SALE SALE SALE 50% offer at Pull & Bear!");
        sale.setShop(session.load(ShopObject.class, 1));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(2).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);

        startTime = LocalDateTime.now().plusSeconds(80);

        sale = new SaleObject();
        sale.setForAllUsers(true);
        sale.setDescription("25% free for pants at castro!");
        sale.setShop(session.load(ShopObject.class, 2));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(1).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);

        startTime = LocalDateTime.now().plusSeconds(110);

        sale = new SaleObject();
        sale.setForAllUsers(true);
        sale.setDescription("get 1 thousand free pencils here!");
        sale.setShop(session.load(ShopObject.class, 3));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(2).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
        session.saveOrUpdate(sale);

        startTime = LocalDateTime.now().plusSeconds(140);
        sale = new SaleObject();
        sale.setForAllUsers(true);
        sale.setDescription("buy 3 plates get 1 chair for free");
        sale.setShop(session.load(ShopObject.class, 4));
        sale.setSaleStart(startTime.format(formatter));
        sale.setSaleEnd(startTime.plusMinutes(1).format(formatter));
        sale.setNotifiedStart(false);
        sale.setNotifiedEnd(false);
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

        Integer id =  getUserIdByToken(token);
        if (id != null) {
            UserObject userObject = session.load(UserObject.class, getUserIdByToken(token));
            if (userObject != null) {
                flag = userObject.isFirstLogin();
                userObject.setFirstLogin(false);
                session.update(userObject);
                transaction.commit();
            }
        }

        session.close();
        return flag;
    }

    public void updateNotified(Integer saleId, Integer type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        SaleObject sale = session.load(SaleObject.class, saleId);

        // 0 for start time
        // 1 for end time
        if (type == 0) {
            sale.setNotifiedStart(true);
        }
        else {
            sale.setNotifiedEnd(true);

        }
        session.update(sale);
        transaction.commit();
        session.close();
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

    public String getShopName(Integer id) {
        Session session = sessionFactory.openSession();
        ShopObject shop = (ShopObject) session.createQuery("from ShopObject where id =: id")
                .setParameter("id", id)
                .uniqueResult();
        session.close();
        return shop.getName();
    }

    public String getShopNameFromSale(Integer id) {
        Session session = sessionFactory.openSession();
        SaleObject sale = (SaleObject) session.createQuery("from SaleObject where id =: id")
                .setParameter("id", id)
                .uniqueResult();

        session.close();
        return sale.getShop().getName();
    }

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


    public List<UserObject> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<UserObject> users = session.createQuery("SELECT u FROM UserObject u",UserObject.class).getResultList();
        session.close();
        return users;
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
        List<SaleObject> sales = session.createQuery("select s from SaleObject s", SaleObject.class).getResultList();
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