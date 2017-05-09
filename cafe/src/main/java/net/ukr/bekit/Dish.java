package net.ukr.bekit;

import javax.persistence.*;

/**
 * Created by Александр on 03.05.2017.
 */
@Entity
@Table(name="Menu")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name="title")
    private String title;

    @Column (name="price")
    private double price;

    @Column (name="weight")
    private double weight;

    @Column (name="discount")
    private String discount;

    public Dish() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Dish(String title, double price, double weight, String discount) {
        this.title = title;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + String.format("%(.2f",price) +
                ", weight=" + String.format("%(.2f",weight) +
                ", discount='" + discount + '\'' +
                '}';
    }
}
