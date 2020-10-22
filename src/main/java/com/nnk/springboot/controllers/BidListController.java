package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.Timestamp;

/**
 * This controller class expose data related methods to front for the BidList
 * object
 *
 * @author DP
 *
 */

@Controller
public class BidListController {

    private static final Logger logger = LogManager.getLogger("BidListController");

    @Autowired
    BidListRepository bidListRepository;

    /**
     * Endpoint to show the list of bid
     *
     * @param model
     * @return the bidList list
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists",bidListRepository.findAll());
        logger.info("bidList/list : OK");
        return "bidList/list";
    }

    /**
     * Endpoint to display bidlist adding form
     *
     * @param bid the bidlist to be added
     * @return
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.info("bidList/add : start");
        return "bidList/add";
    }

    /**
     * Endpoint to validate the info of bidlist
     *
     * @param bidlist, bidlist to be added
     * @param result   technical result
     * @param model    public interface model, model can be accessed and attributes
     *                 can be added
     * @return
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {

        logger.info("bidList/validate : start");
        if (!result.hasErrors()){

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            bid.setCreationDate(timestamp);
            bid.setRevisionDate(timestamp);
            bidListRepository.save(bid);
            model.addAttribute("bidLists",bidListRepository.findAll());

            logger.info("bidList/validate : ended for bid : "+bid.toString());
            return "redirect:/bidList/list";
        }
        logger.info("bidList/validate : error for bid : "+bid.toString());
        return "bidList/add";
    }

    /**
     * Endpoint to display updating form
     *
     * @param id    the bidlist id
     * @param model public interface model, model can be accessed and attributes can
     *              be added
     * @return bidlist/update if OK
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        logger.info("bidList/update : start for id :" + id);
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        model.addAttribute("bidlist",bidList);
        return "bidList/update";
    }

    /**
     * Endpoint to validate the bidlist updating form
     *
     * @param id
     * @param bidlist the bidlist id
     * @param result  technical result
     * @param model   public interface model, model can be accessed and attributes
     *                can be added
     * @return bidlist/list if ok or bidlist/update if ko
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid : KO
        if (result.hasErrors()){
            logger.info("bidList/update : error for id : "+ id);
            return "bidList/update";
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        bidList.setRevisionDate(timestamp);
        bidList.setBidListId(id);
        bidListRepository.save(bidList);
        model.addAttribute("bidLists",bidListRepository.findAll());
        logger.info("bidList/update : ended for bid :" + bidList.toString());
        return "redirect:/bidList/list";
    }

    /**
     * Endpoint to delete a bidlist
     *
     * @param id    the bidlist id to delete
     * @param model public interface model, model can be accessed and attributes can
     *              be added
     * @return bidlist/list if ok
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {


        logger.info("bidList/delete : start for id :" + id);
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        bidListRepository.delete(bidList);
        model.addAttribute("bidLists",bidListRepository.findAll());
        logger.info("bidList/delete : ended for bid :" + bidList.toString());
        return "redirect:/bidList/list";
    }
}
