package com.nnk.springboot.IT;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) /*désactive tous les filtres dans la configuration SpringSecurity */
class RuleNameControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /*------------------------------ Post ------------------------------*/

    /* Add validate ruleName */
    @Test
    void addRuleName_ValidateRuleName() throws Exception{

        List<RuleName> ruleNamesBeforeAdd;
        ruleNamesBeforeAdd = ruleNameRepository.findAll();

        //GIVEN
        String name = "NameTest";
        String description = "DescriptionTest";
        String json = "JsonTest";
        String template = "String template";
        String sqlStr = "SqlStr";
        String sqlPart = "SqlPart";

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("name", name)
                .param("description", description)
                .param("json", json)
                .param("template", template)
                .param("sqlStr", sqlStr)
                .param("sqlPart",sqlPart))
                .andDo(print())
                .andExpect(view().name("redirect:/ruleName/list"));

        List<RuleName> ruleNamesAfterAdd;
        ruleNamesAfterAdd = ruleNameRepository.findAll();

        assertEquals(ruleNamesAfterAdd.size(),ruleNamesBeforeAdd.size()+1);
    }

    /* Validate non compliant RuleName */
    @Test
    void validateRuleName_NonCompliant_RuleName() throws Exception{

        List<RuleName> ruleNamesBeforeAdd;
        ruleNamesBeforeAdd = ruleNameRepository.findAll();

        //GIVEN
        Integer id = 1;
        String name = "";
        String description = "DescriptionTest";
        String json = "JsonTest";
        String template = "String template";
        String sqlStr = "SqlStr";
        String sqlPart = "SqlPart";

        RuleName ruleNameTest = new RuleName(id,name,description,json,template,sqlStr,sqlPart);

        //WHEN //THEN stay to add page
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("name", name)
                    .param("description", description)
                    .param("json", json)
                    .param("template", template)
                    .param("sqlStr", sqlStr)
                    .param("sqlPart",sqlPart))
                    .andDo(print())
                    .andExpect(view().name("ruleName/add"));
            ruleNameRepository.save(ruleNameTest);
        }
        catch (Exception e){
            assertTrue(e.getMessage().contains("Name is mandatory"));
        }
        List<RuleName> ruleNamesAfterAdd;
        ruleNamesAfterAdd = ruleNameRepository.findAll();

        assertEquals(ruleNamesAfterAdd.size(),ruleNamesBeforeAdd.size());
    }

    @Test
    void deleteRuleName_ExistingRuleName() throws Exception{

        Integer id = 1;
        String name = "NameTest";
        String description = "DescriptionTest";
        String json = "JsonTest";
        String template = "String template";
        String sqlStr = "SqlStr";
        String sqlPart = "SqlPart";

        //GIVEN
        RuleName ruleNameTest = new RuleName(id,name,description,json,template,sqlStr,sqlPart);
        ruleNameRepository.save(ruleNameTest);

        List<RuleName> ruleNamesBeforeDelete;
        ruleNamesBeforeDelete = ruleNameRepository.findAll();

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));

        List<RuleName> ruleNamesAfterDelete;
        ruleNamesAfterDelete = ruleNameRepository.findAll();

        assertEquals(ruleNamesAfterDelete.size(),ruleNamesBeforeDelete.size()-1);
    }

    /*------------------------------ Post ------------------------------*/

    @Test
    void deleteRuleName_Non_ExistingRuleName() throws Exception{

        Integer id = 1;
        String name = "NameTest";
        String description = "DescriptionTest";
        String json = "JsonTest";
        String template = "String template";
        String sqlStr = "SqlStr";
        String sqlPart = "SqlPart";

        //GIVEN
        RuleName ruleNameTest = new RuleName(id,name,description,json,template,sqlStr,sqlPart);
        ruleNameRepository.save(ruleNameTest);

        List<RuleName> ruleNamesBeforeDelete;
        ruleNamesBeforeDelete = ruleNameRepository.findAll();

        // WHEN
        // THEN
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/ruleName/list"));
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid ruleName Id:999"));
        }
        List<RuleName> ruleNamesAfterDelete;
        ruleNamesAfterDelete = ruleNameRepository.findAll();

        assertEquals(ruleNamesAfterDelete.size(),ruleNamesBeforeDelete.size());
    }
}