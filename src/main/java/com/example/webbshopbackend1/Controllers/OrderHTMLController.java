package com.example.webbshopbackend1.Controllers;

import com.example.webbshopbackend1.Models.Customer;
import com.example.webbshopbackend1.Models.Item;
import com.example.webbshopbackend1.Models.Orders;
import com.example.webbshopbackend1.Repos.CustomerRepo;
import com.example.webbshopbackend1.Repos.ItemRepo;
import com.example.webbshopbackend1.Repos.OrderRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orderHTML")
public class OrderHTMLController {

    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final ItemRepo itemRepo;

    OrderHTMLController(OrderRepo orderRepo, CustomerRepo customerRepo, ItemRepo itemRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.itemRepo = itemRepo;
    }

    @RequestMapping("getAll")
    public String getAllOrders(Model model){
        List<Orders> orders = orderRepo.findAll();
        model.addAttribute("allOrders", orders);
        model.addAttribute("date", "Date");
        model.addAttribute("customerName", "Customer name");
        model.addAttribute("customerSsn", "Customer ssn");
        model.addAttribute("date", "Date");
        model.addAttribute("itemName", "Item name");
        model.addAttribute("itemPrice", "Price");

        model.addAttribute("itemInfo", "Item");
        model.addAttribute("customerInfo", "Customer info");

        return "orders";

    }


    @RequestMapping("/save")
    public String addOrder(@RequestParam Long customerId, @ModelAttribute("itemIds") List<Long> itemIds, Model model){
       /* Item item = itemRepo.findById(itemId).get();
        Customer customer = customerRepo.findById(customerId).get();
        orderRepo.save(new Orders(LocalDate.now(),customer, List.of(item)));
        return getAllOrders(model);

        */
        List<Item> items = new ArrayList<>();
        for (Long itemId :itemIds) {
            Item item = itemRepo.findById(itemId).orElse(null);
            if (item != null) {
                items.add(item);
            }
            else {
                return "Order failed";
            }
        }
        Customer customer = customerRepo.findById(customerId).orElse(null);
        if (items != null && customer != null) {
            orderRepo.save(new Orders(LocalDate.now(), customer, items));
            return getAllOrders(model);
        } else {
            return "Order failed";
        }

    }



    /*@RequestMapping("/getByCustomerId/{id}")
    public String getOrdersByCustomerId(@PathVariable Long id, Model model){

        return "";

    }
     */

}
