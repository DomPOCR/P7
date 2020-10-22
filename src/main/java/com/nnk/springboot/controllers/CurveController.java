package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
 * This controller class expose data related methods to front for the CurvePoint
 * object
 *
 * @author DP
 *
 */
@Controller
public class CurveController {

    private static final Logger logger = LogManager.getLogger("CurveController");

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Endpoint to show the list of CurvePoint
     *
     * @param model
     * @return the curvePoint list
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointRepository.findAll());
        logger.info("curvePoint/list : OK");
        return "curvePoint/list";
    }

    /**
     * Endpoint to display curvePoint adding form
     *
     * @param curvePoint the curvePoint to be added
     * @return curvePoint/add
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        logger.info("curvePoint/add : start");
        return "curvePoint/add";
    }

    /**
     * Endpoint to validate the info of curve
     *
     * @param curvePoint, curvePoint to be added
     * @param result   technical result
     * @param model    public interface model, model can be accessed and attributes
     *                 can be added
     * @return curvePoint/add
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {

        logger.info("curvePoint/validate : start");
        if (!result.hasErrors()) {

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            curvePoint.setAsOfDate(timestamp);
            curvePoint.setCreationDate(timestamp);
            curvePointRepository.save(curvePoint);
            model.addAttribute("curvePoint", curvePointRepository.findAll());

            logger.info("curvePoint/validate : ended for curvePoint : " + curvePoint.toString());
            return "redirect:/curvePoint/list";
        }
        logger.info("curvePoint/validate : error for curvePoint : " + curvePoint.toString());
        return "curvePoint/add";
    }

    /**
     * Endpoint to display updating form
     *
     * @param id    the curvePoint id
     * @param model public interface model, model can be accessed and attributes can
     *              be added
     * @return curvePoint/update if OK
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        logger.info("showUpdateForm start for id " + id);
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        model.addAttribute("curvePoint", curvePoint);
        logger.info("showUpdateForm ended for id " + id);
        return "curvePoint/update";
    }

    /**
     * Update Curvepoint
     * @param id Curvepoint ID to updtate
     * @param curvePoint new Curvepoint datas
     * @param result input information
     * @param model CurvePoint list
     * @return new page to display
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            logger.info("curvePoint/update error for id : "+ id);
            return "curvePoint/update";
        }
        curvePoint.setCurveId(id);
        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePoint", curvePointRepository.findAll());
        logger.info("curvePoint//update : ended for curve point :" + curvePoint.toString());
        return "redirect:/curvePoint/list";
    }

    /**
     * Endpoint to delete a curvepoint
     *
     * @param id CurvePoint ID to delete
     * @param model CurvePoint list
     * @return new page to display
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        logger.info("deleteCurvePoint start for id " + id);
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curvePointRepository.delete(curvePoint);
        model.addAttribute("curvePoints", curvePointRepository.findAll());
        logger.info("curvePoint/delete ended for : " + curvePoint.toString());
        return "redirect:/curvePoint/list";
    }
}
