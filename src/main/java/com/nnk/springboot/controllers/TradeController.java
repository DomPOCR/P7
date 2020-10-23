package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

/**
 * This controller class expose data related methods to front for the trade
 * object
 *
 * @author DP
 *
 */
@Controller
public class TradeController {

    private static final Logger logger = LogManager.getLogger("TradeController");

    @Autowired
    private TradeRepository tradeRepository;
    private List<FieldError> list;

    /**
     * End to show the list of Trade
     *
     * @param model
     * @return the trade list
     */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", tradeRepository.findAll());
        logger.info("trade/list : OK");
        return "trade/list";
    }

    /**
     * Endpoint to display trade adding form
     *
     * @param trade the trade to be added
     * @return
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {

        logger.info("trade/add : start");
        return "trade/add";
    }

    /**
     * Endpoint to validate the info of trade
     *
     * @param trade, trade to be added
     * @param result   technical result
     * @param model    public interface model, model can be accessed and attributes
     *                 can be added
     * @return
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {

        if (!result.hasErrors()) {

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            trade.setRevisionDate(timestamp);
            trade.setCreationDate(timestamp);
            trade.setTradeDate(timestamp);
            tradeRepository.save(trade);
            model.addAttribute("trade", tradeRepository.findAll());

            logger.info("trade/validate : ended for trade : "+ trade.toString());
            return "redirect:/trade/list";
        }
        logger.info("trade/validate : error for trade : "+ trade.toString());
        return "trade/add";
    }

    /**
     * Endpoint to display updating form
     *
     * @param id    the trade id
     * @param model public interface model, model can be accessed and attributes can
     *              be added
     * @return trade/update if OK
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        logger.info("showUpdateForm start for id " + id);
        
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        
        return "trade/update";
    }

    /**
     * Endpoint to validate the trade updating form
     *
     * @param id
     * @param trade the trade id
     * @param result  technical result
     * @param model   public interface model, model can be accessed and attributes
     *                can be added
     * @return trade/list if ok or trade/update if ko
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            String error = result.getFieldErrors().get(0).getDefaultMessage();
            String field = result.getFieldErrors().get(0).getField();
            logger.info("trade/update : error for id : "+ id + " : " + field + " " + error);
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeRepository.save(trade);
        model.addAttribute("trade", tradeRepository.findAll());

        logger.info("trade/update : ended for trade :" + trade.toString());
        return "redirect:/trade/list";
    }

    /**
     * Endpoint to delete a trade
     *
     * @param id    the trade id to delete
     * @param model public interface model, model can be accessed and attributes can
     *              be added
     * @return trade/list if ok
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeRepository.delete(trade);
        model.addAttribute("trades", tradeRepository.findAll());

        logger.info("trade/delete : ended for bid :" + trade.toString());
        return "redirect:/trade/list";
    }
}
