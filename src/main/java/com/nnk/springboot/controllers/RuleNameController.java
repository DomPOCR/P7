package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
 * This controller class expose data related methods to front for the ruleName
 * object
 *
 * @author DP
 *
 */
@Controller
public class RuleNameController {

    private static final Logger logger = LogManager.getLogger(RuleNameController.class);

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Endpoint to show the list of ruleName
     *
     * @param model
     * @return the ruleName list
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", ruleNameRepository.findAll());
        
        logger.info("ruleName/list : OK");
        return "ruleName/list";
    }

    /**
     * Endpoint to display ruleName adding form
     *
     * @param ruleName the ruleName to be added
     * @return ruleName/add
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        
        logger.info("ruleName/add : start");
        return "ruleName/add";
    }

    /**
     * Endpoint to validate the info of ruleName
     *
     * @param ruleName, ruleName to be added
     * @param result   technical result
     * @param model    public interface model, model can be accessed and attributes
     *                 can be added
     * @return
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {

        logger.info("ruleName/validate : start");
        
        if (!result.hasErrors()) {
            ruleNameRepository.save(ruleName);
            model.addAttribute("ruleName", ruleNameRepository.findAll());
            logger.info("ruleName/validate : ended for ruleName : "+ ruleName.toString());
            return "redirect:/ruleName/list";
        }
        
        logger.info("ruleName/validate : error for ruleName : "+ ruleName.toString());
        return "ruleName/add";
    }

    /**
     * Endpoint to display updating form
     *
     * @param id    the ruleName id
     * @param model public interface model, model can be accessed and attributes can
     *              be added
     * @return ruleName/update if OK
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        logger.info("showUpdateForm start for id " + id);
        
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        model.addAttribute("ruleName", ruleName);
        
        logger.info("showUpdateForm ended for id " + id);
        return "ruleName/update";
    }

    /**
     * Endpoint to validate the ruleName updating form
     *
     * @param id
     * @param ruleName the ruleName id
     * @param result  technical result
     * @param model   public interface model, model can be accessed and attributes
     *                can be added
     * @return ruleName/list if OK or ruleName/update if KO
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {

        logger.info("updateRating start for id " + id);

        ruleName.setId(id);

        if (result.hasErrors()) {
            String error = result.getFieldErrors().get(0).getDefaultMessage();
            String field = result.getFieldErrors().get(0).getField();
            logger.info("curvePoint/update error for rule name : "+ ruleName.toString() + " : " + field + " " + error);
            logger.info("ruleName/update : error for id : "+ id);
            return "ruleName/update";
        }
        ruleNameRepository.save(ruleName);
        model.addAttribute("ruleNames", ruleNameRepository.findAll());

        logger.info("ruleName/update : ended for rule name :" + ruleName.toString());
        return "redirect:/ruleName/list";
    }

    /**
     * Endpoint to delete a ruleName
     *
     * @param id    the ruleName id to delete
     * @param model public interface model, model can be accessed and attributes can
     *              be added
     * @return ruleName/list if ok
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

        logger.info("ruleName/delete : start for id :" + id);
        
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameRepository.delete(ruleName);
        model.addAttribute("ruleNames", ruleNameRepository.findAll());

        logger.info("ruleName/delete : ended for rule name :" + ruleName.toString());
        return "redirect:/ruleName/list";
    }
}
