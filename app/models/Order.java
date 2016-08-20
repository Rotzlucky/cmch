package models;

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

}
