package net.ukr.bekit;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Александр on 03.05.2017.
 */

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cafe");
        EntityManager em = emf.createEntityManager();
        try {
            Dish dish;
            long gid1;
            Random rn = new Random();


            //#1 Create menu
            try {
                for (int i = 0; i < 10; i++) {
                    em.getTransaction().begin();
                    dish = new Dish("name" + i, rn.nextDouble() * 20, rn.nextDouble() * 1, "yes");
                    int temp = rn.nextInt(2);
                    if (temp >= 1) {
                        dish.setDiscount("yes");
                    } else {
                        dish.setDiscount("no");
                    }
                    em.persist(dish);
                    em.getTransaction().commit();
                    System.out.println(dish);
                }

            } catch (Exception ex) {
                em.getTransaction().rollback();
                return;
            }


            //#2 Instruction

            while (true) {
                System.out.println("Instruction:" + "\t\n" + "1. Type '1' to add new dish;"
                        + "\t\n" + "2. Type '2' to select dishes by price;" + "\t\n" + "3. Type '3' to select dishes by discount;"
                        + "\t\n" + "4. Type '4' to select dishes where whole weight will be less than 1 kg;");
                Scanner sc = new Scanner(System.in);
                String s = sc.nextLine();

                switch (s) {
                    case "1":

                        dish = newDish();

                        em.getTransaction().begin();
                        em.persist(dish);
                        em.getTransaction().commit();
                        System.out.println(dish);
                        break;
                    case "2":
                        selectByPrice(em);
                        break;
                    case "3":
                        selectByDiscount(em);
                        break;
                    case "4":
                        selectByWeight(em);
                        break;

                    default:
                        return;
                }
            }

        } finally {
            em.close();
            emf.close();
        }
    }

    public static Dish newDish() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type title of new dish");
        String title = sc.nextLine();
        System.out.println("Type price of new dish");
        Double price = sc.nextDouble();
        System.out.println("Type weight of new dish");
        Double weight = sc.nextDouble();
        System.out.println("Is there discount for new dish?(yes/no)");
        Scanner sc1 = new Scanner(System.in);
        String discount = sc1.nextLine();
        if (discount.equalsIgnoreCase("yes") || discount.equalsIgnoreCase("no")) {
            Dish dish = new Dish(title, price, weight, discount);

            return dish;
        } else {
            System.out.println("Wrong discount!");
            return null;
        }

    }

    public static void selectByPrice(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type min price of dish");
        Double priceMin = sc.nextDouble();
        System.out.println("Type max price of dish");
        Double priceMax = sc.nextDouble();
        try {
            Query query = em.createQuery("SELECT c FROM  Dish c WHERE c.price>=:min AND c.price<=:max", Dish.class);
            query.setParameter("min", priceMin);
            query.setParameter("max", priceMax);
            List<Dish> dishList = query.getResultList();
            for (Dish dish : dishList) {
                System.out.println(dish);
            }
        } catch (NoResultException e) {
            e.printStackTrace();
        }
    }

    public static void selectByDiscount(EntityManager em) {

        try {
            Query query = em.createQuery("SELECT c FROM  Dish c WHERE c.discount='yes'", Dish.class);
            List<Dish> dishList = query.getResultList();
            System.out.println("Dishes with discount:");
            for (Dish dish : dishList) {
                System.out.println(dish);
            }
        } catch (NoResultException e) {
            e.printStackTrace();
        }
    }

    public static void selectByWeight(EntityManager em) {

        try {

            Query query = em.createQuery("SELECT c FROM  Dish c", Dish.class);
            List<Dish> dishList = query.getResultList();
            Collections.shuffle(dishList);
            List<Dish> result = new ArrayList<Dish>();
            double weight = 0;
            z:
            for (int i = 0; i < dishList.size() && weight <= 1; i++) {
                int check = 0;
                Dish temp = dishList.get(i);
                double c = weight + temp.getWeight();
                if (c > 1) {

                    continue z;
                }
                result.add(temp);
                weight = weight + temp.getWeight();
            }
            for (Dish dish : result) {
                System.out.println(dish);
            }
        } catch (NoResultException e) {
            e.printStackTrace();
        }
    }
}
