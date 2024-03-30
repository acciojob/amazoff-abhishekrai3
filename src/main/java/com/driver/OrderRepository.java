package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;



    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
       orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        partnerMap.put(deliveryPartner.getId(),deliveryPartner);
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            partnerToOrderMap.computeIfAbsent(partnerId, k -> new HashSet<>()).add(orderId);
            DeliveryPartner partner = partnerMap.get(partnerId);
            int ordervalue= partner.getNumberOfOrders();
            partner.setNumberOfOrders(ordervalue+1);
            orderToPartnerMap.put(orderId, partnerId);
        }
    }

    public Order findOrderById(String orderId){
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        return partnerToOrderMap.get(partnerId).size();

    }

    public List<String> findOrdersByPartnerId(String partnerId){
        return new ArrayList<>(partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()));
    }

    public List<String> findAllOrders(){
        return new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId){
        partnerMap.remove(partnerId);
        partnerToOrderMap.remove(partnerId);
        orderToPartnerMap.entrySet().removeIf(entry -> entry.getValue().equals(partnerId));
    }

    public void deleteOrder(String orderId){
        orderMap.remove(orderId);
        orderToPartnerMap.remove(orderId);
        for (HashSet<String> orderSet : partnerToOrderMap.values()) {
            orderSet.remove(orderId);
        }
    }

    public Integer findCountOfUnassignedOrders(){
        return orderMap.size() - orderToPartnerMap.size();
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        OrderService orderService= new OrderService();
        return orderService.getOrdersLeftAfterGivenTimeByPartnerId(timeString, partnerId);

    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        OrderService orderService= new OrderService();
        return orderService.getLastDeliveryTimeByPartnerId(partnerId);

    }
}
