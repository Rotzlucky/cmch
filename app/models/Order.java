package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import models.enums.OrderType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * Created by marcelsteffen on 15.08.16.
 */
@Entity
@Table(name = "orders", uniqueConstraints = {@UniqueConstraint(columnNames = {"order_type", "order_number"})})
public class Order extends Model{

    @Id
    public Long id;
    public int orderNumber;
    @Enumerated(EnumType.STRING)
    public OrderType orderType;
    @ManyToOne
    public Issue issue;
    public Date createdAt;
    public Date modifiedAt;

    public static Finder<Long, Order> find = new Finder<>(Order.class);

    public Order(int orderNumber, OrderType orderType, Issue issue) {
        this.orderNumber = orderNumber;
        this.orderType = orderType;
        this.issue = issue;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    public static Order create(int orderNumber, String orderType, Long issueId) {
        Issue issue = Issue.find.ref(issueId);

        Order order = new Order(orderNumber, OrderType.valueOf(orderType), issue);
        order.save();

        issue.orders.add(order);
        Ebean.saveAssociation(issue, "orders");

        return order;
    }
}
