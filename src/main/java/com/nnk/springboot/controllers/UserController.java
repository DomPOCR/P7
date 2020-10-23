package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 *
 * This controller class expose data related methods to front for the User object
 * @author DP
 *
 */
@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger("CurveController");

    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint to show the list of user
     * @param model
     * @return the user list
     */
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        logger.info("user/list : OK");
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    /**
     * Endpoint to display user adding form
     * @param user the user to be added
     * @return
     */
    @GetMapping("/user/add")
    public String addUser(User user) {

        logger.info("user/add : start");
        return "user/add";
    }

    /**
     * Endpoint to validate the info of user
     * @param user, user to be added
     * @param result technical result
     * @param model public interface model, model can be accessed and attributes can be added
     * @return
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {

        logger.info("user/validate : start");
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());
            logger.info("user/validate : ended for user : " + user.toString());
            return "redirect:/user/list";
        }
        logger.info("user/validate : error for user : " + user.toString());
        return "user/add";
    }

    /**
     * Endpoint to display user updating form
     * @param id the user id
     * @param model public interface model, model can be accessed and attributes can be added
     * @return user/update if OK
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        logger.info("showUpdateForm start for id " + id);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        logger.info("showUpdateForm ended for id " + id);
        return "user/update";
    }

    /**
     * Endpoint to validate the user updating form
     * @param id
     * @param user the user id
     * @param result technical result
     * @param model public interface model, model can be accessed and attributes can be added
     * @return user/list if ok or user/update if ko
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            logger.info("user/update error for id : "+ id);
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        logger.info("user//update : ended for curve point :" + user.toString());
        return "redirect:/user/list";
    }

    /**
     * Endpoint to delete a user
     * @param id the user id to delete
     * @param model public interface model, model can be accessed and attributes can be added
     * @return user/list if ok
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {

        logger.info("deleteuser start for id " + id);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        logger.info("user/delete ended for : " + user.toString());
        return "redirect:/user/list";
    }
}
