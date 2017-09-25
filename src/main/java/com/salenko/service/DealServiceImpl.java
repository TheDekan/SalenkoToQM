package com.salenko.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salenko.model.Checkout;
import com.salenko.model.Deal;

@Service
public class DealServiceImpl implements DealService {

    Checkout checkout = Checkout.getInstance();

    @Transactional
    @Override
    public Deal insert(Deal row) {
        checkout.deals.add(row);
        return row;
    }

    @Transactional
    @Override
    public List<Deal> findAll() {
        return checkout.deals;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        checkout.deals.remove((int) (0 + id));
    }

    @Transactional
    @Override
    public Deal findById(Long id) {
        int idd = (int) (0 + id);
        return checkout.deals.get(idd);
    }

    @Transactional
    @Override
    public Long getCount() {
        return (long) checkout.deals.size();
    }

    @Override
    public List<Deal> sortedFind(int startPosition, int maxResults) {
        List<Deal> seenList = new ArrayList<Deal>();
        if (checkout.deals.size() > 0)
            for (int i = startPosition; i < startPosition + maxResults && i < checkout.deals.size(); i++) {
                checkout.deals.get(i).setId((long) i);
                seenList.add(checkout.deals.get(i));
            }
        return seenList;
    }

    @Transactional
    @Override
    public String getCheck() {
        List<Deal> promList = new ArrayList<Deal>();
        for (int i = 0; i < checkout.deals.size(); i++) {
            promList.add(new Deal(checkout.deals.get(i).getProduct(), checkout.deals.get(i).getProductCount()));
        }
        List<Deal> aggregatedList = DealServiceImpl.getAggregatedList(promList);
        List<Deal> giftList = new ArrayList<Deal>();
        checkout.setCheck("");
        if (aggregatedList.size() > 0) {
            checkout.setCheck("Name.......Count.......Price" + "\n");
            double totalPrice = 0d;
            for (Deal deal : aggregatedList) {
                double dealPrice = new BigDecimal(DealServiceImpl.calculatePrice(giftList, deal)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                checkout.setCheck(checkout.getCheck() + "..." + deal.getProduct().getName() + ".............." + deal.getProductCount() + "..........." + dealPrice + "\n");
                totalPrice += dealPrice;
            }
            totalPrice = Math.ceil(totalPrice * 100) / 100;
            checkout.setCheck(checkout.getCheck() + "-------------------------------------" + "\n");
            checkout.setCheck(checkout.getCheck() + "Total Price : " + totalPrice + "\n");
            if (giftList.size() > 0) {
                checkout.setCheck(checkout.getCheck() + "-------------------------------------" + "\n");
                giftList = DealServiceImpl.getAggregatedList(giftList);
                checkout.setCheck(checkout.getCheck() + "Products you got free:" + "\n");
                for (Deal deal : giftList) {
                    checkout.setCheck(checkout.getCheck() + deal.getProduct().getGiftName() + ".............." + deal.getProductCount() + "\n");
                }
            }
        }
        return checkout.getCheck();
    }

    public static ArrayList<Deal> getAggregatedList(List<Deal> deals) {
        ArrayList<Deal> aggregatedList = new ArrayList<Deal>();
        for (Deal row : deals) {
            boolean added = false;
            String name = row.getProduct().getName();
            for (int i = 0; i < aggregatedList.size(); i++)
                if (aggregatedList.get(i).getProduct().getName().equals(name)) {
                    aggregatedList.get(i).setProductCount(aggregatedList.get(i).getProductCount() + row.getProductCount());
                    added = true;
                    break;
                }
            if (!added)
                aggregatedList.add(row);
        }
        return aggregatedList;
    }

    public static double calculatePrice(List<Deal> giftList, Deal deal) {
        double price = 0;
        // is there any action?
        if (deal.getProduct().getActionValid()) {
            // calculating units or kilograms?
            if (deal.getProduct().getCalculationType()) {
                // got some action?
                if (deal.getProductCount() >= deal.getProduct().getActionCount()) {
                    // for kilograms - actionCount lowers price for all weight
                    price = deal.getProduct().getPrice() * deal.getProductCount();
                    // got some gifts?
                    if (deal.getProduct().getGift()) {
                        // times you get a gift
                        double giftPartsByAction = Math.floor(deal.getProductCount() / deal.getProduct().getActionCount());
                        giftList.add(new Deal(deal.getProduct(), giftPartsByAction * deal.getProduct().getGiftCount()));
                    }
                } else {
                    price = deal.getProductCount() * deal.getProduct().getPrice();
                }
            } else {
                // got some action?
                if (deal.getProductCount() >= deal.getProduct().getActionCount()) {
                    // times you got an actionCount
                    double prodPartsByAction = Math.floor(deal.getProductCount() / deal.getProduct().getActionCount());
                    // products where you don't get actionCount
                    double prodsWithoutAction = deal.getProductCount() - prodPartsByAction * deal.getProduct().getActionCount();
                    price = prodPartsByAction * deal.getProduct().getActionPrice() + prodsWithoutAction * deal.getProduct().getPrice();
                    // got some gifts?
                    if (deal.getProduct().getGift()) {
                        giftList.add(new Deal(deal.getProduct(), prodPartsByAction * deal.getProduct().getGiftCount()));
                    }
                } else {
                    price = deal.getProductCount() * deal.getProduct().getPrice();
                }
            }
        } else {
            price = deal.getProductCount() * deal.getProduct().getPrice();
        }
        return price;
    }

}
