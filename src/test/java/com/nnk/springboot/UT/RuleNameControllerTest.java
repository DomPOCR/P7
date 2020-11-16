package com.nnk.springboot.UT;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameRepository ruleNameRepository;

    // Données de test
    String name = "NameTest";
    String description = "DescriptionTest";
    String json = "JsonTest";
    String template = "TemplateTest";
    String sqlStr = "SqlStr";
    String sqlPart = "SqlPart";

    /* Show the list of ruleName */
    @Test
    void listRuleName() throws Exception{

        List<RuleName> ruleNameList = new ArrayList<>();

        //GIVEN
        RuleName ruleNameTest = new RuleName(name,description,json,template,sqlStr,sqlPart);
        ruleNameList.add(ruleNameTest);
        Mockito.when(ruleNameRepository.findAll()).thenReturn(ruleNameList);

        //WHEN //THEN return the list page
        mockMvc.perform(get("/ruleName/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
    }

    /* Display ruleName adding form*/
    @Test
    void addRuleName() throws Exception{

        //GIVEN

        //WHEN //THEN return the add page
        mockMvc.perform(get("/ruleName/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    /* Validate correct ruleName */
    @Test
    void validateRuleName_CorrectRuleName() throws Exception{

        //GIVEN : Give a new ruleName
        RuleName ruleNameTest = new RuleName(name,description,json,template,sqlStr,sqlPart);
        Mockito.when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleNameTest);
        Mockito.when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleNameTest));

        //WHEN //THEN return the list page
        mockMvc.perform(post("/ruleName/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("name", name)
                .param("description", description)
                .param("json", json)
                .param("template", template)
                .param("sqlStr", sqlStr)
                .param("sqlPart",sqlPart))
                .andDo(print())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(status().is3xxRedirection());
    }

    /* Display ruleName updating form */
    @Test
    void updateRuleName_RuleNameIsReturn() throws Exception{

        //GIVEN : Give an exiting ruleName
        RuleName ruleNameTest = new RuleName(name,description,json,template,sqlStr,sqlPart);
        Mockito.when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleNameTest));

        //WHEN //THEN return the update page
        mockMvc.perform(get("/ruleName/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    /* Display delete a ruleName */
    @Test
    void DeleteRuleName_RuleNameListIsReturn() throws Exception{

        //GIVEN : Give an exiting Person
        RuleName ruleNameTest = new RuleName(name,description,json,template,sqlStr,sqlPart);
        Mockito.when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleNameTest));

        //WHEN //THEN return the list page
        mockMvc.perform(get("/ruleName/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));

        Mockito.verify(ruleNameRepository,Mockito.times(1)).delete(any(RuleName.class));
    }
}