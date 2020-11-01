package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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

/**
 * This controller class expose data related methods to front for the rating
 * object
 *
 * @author DP
 *
 */
@Controller
public class RatingController {

    private static final Logger logger = LogManager.getLogger("RatingController");

    @Autowired
    private RatingRepository ratingRepository;

    /**
    * Endpoint to show the list of rating
    *
    * @param model
    * @return the rating list
    */
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingRepository.findAll());
        logger.info("rating/list : OK");
        return "rating/list";
    }

    /**
     * Endpoint to display rating adding form
     *
     * @param rating the rating to be added
     * @return rating/add
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.info("rating/add : start");
        return "rating/add";
    }

    /**
     * Endpoint to validate the info of rating
     *
     * @param rating, rating to be added
     * @param result  technical result
     * @param model   public interface model, model can be accessed and attributes
     *                can be added
     * @return rating/list if OK
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {

        logger.info("rating/validate : start");
        if (!result.hasErrors()) {

            ratingRepository.save(rating);
            model.addAttribute("rating", ratingRepository.findAll());
            logger.info("rating/validate : ended for rating : "+ rating.toString());
            return "redirect:/rating/list";
        }
        logger.info("rating/validate : error for rating : "+ rating.toString());
        return "rating/add";
    }

    /**
     * Endpoint to display updating form
     *
     * @param id    the rating id
     * @param model public interface model, model can be accessed and attributes can
     *              be added
     * @return rating/update if OK
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        logger.info("showUpdateForm ended for id " + id);
        return "rating/update";
    }

    /**
     * Endpoint to validate the rating updating form
     *
     * @param id
     * @param rating the rating id
     * @param result     technical result
     * @param model      public interface model, model can be accessed and
     *                   attributes can be added
     * @return rating/list if ok or rating/update if ko
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {

        rating.setId(id);

        if (result.hasErrors()) {
            String error = result.getFieldErrors().get(0).getDefaultMessage();
            String field = result.getFieldErrors().get(0).getField();
            logger.info("trade/update : error for rating : "+ rating.toString() + " : " + field + " " + error);
            return "rating/update";
        }

        ratingRepository.save(rating);
        model.addAttribute("rating", ratingRepository.findAll());
        logger.info("rating/update : ended for rating :" + rating.toString());
        return "redirect:/rating/list";
    }
    
    /**
     * Endpoint to delete a rating
     *
     * @param id    the rating id to delete
     * @param model public interface model, model can be accessed and attributes can
     *              be added
     * @return rating/list if ok
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {

        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingRepository.delete(rating);
        model.addAttribute("ratings", ratingRepository.findAll());
        logger.info("rating/delete : ended for rating :" + rating.toString());
        return "redirect:/rating/list";
    }
}
