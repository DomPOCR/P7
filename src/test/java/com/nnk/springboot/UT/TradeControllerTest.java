package com.nnk.springboot.UT;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
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
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeRepository tradeRepository;

    // Données de test
    Timestamp creationDate = new Timestamp(System.currentTimeMillis());
    String account = "AccountTest";
    String type = "TypeTest";
    Double buyQuantity = 500d;
    Double sellQuantity = 200d;
    Double incorrectSellQuantity = 0d;

    /* Show the list of trade */
    @Test
    void listTrade() throws Exception{

        List<Trade> tradeList = new ArrayList<>();

        //GIVEN
        Trade tradeTest = new Trade(account,type,buyQuantity,sellQuantity);
        tradeTest.setCreationDate(creationDate);
        
        tradeList.add(tradeTest);
        Mockito.when(tradeRepository.findAll()).thenReturn(tradeList);

        //WHEN //THEN return the list page
        mockMvc.perform(get("/trade/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    /* Display trade adding form*/
    @Test
    void addTrade() throws Exception{

        //GIVEN

        //WHEN //THEN return the add page
        mockMvc.perform(get("/trade/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }
    
    /* Validate correct trade */
    @Test
    void validateTrade_CorrectTrade() throws Exception{

        //GIVEN : Give a new trade
        Trade tradeTest = new Trade(account,type,buyQuantity,sellQuantity);
        tradeTest.setCreationDate(creationDate);

        Mockito.when(tradeRepository.save(any(Trade.class))).thenReturn(tradeTest);
        Mockito.when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(tradeTest));

        //WHEN //THEN return the list page
        mockMvc.perform(post("/trade/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("account", account)
                .param("type", type)
                .param("buyQuantity", String.valueOf(buyQuantity))
                .param("sellQuantity", String.valueOf(sellQuantity)))
                .andDo(print())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection());
    }

    /* Display trade updating form */
    @Test
    void updateTrade_ShowUpdateForm() throws Exception{

        //GIVEN : Give an exiting trade
        Trade tradeTest = new Trade(account,type,buyQuantity,sellQuantity);

        Mockito.when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(tradeTest));

        //WHEN //THEN return the update page
        mockMvc.perform(get("/trade/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    /* Update an existing trade and return to the list    */
    @Test
    public void updateTrade_CorrectTrade() throws Exception {

        //GIVEN : Give an exiting trade
        Trade tradeTest = new Trade(account,type,buyQuantity,sellQuantity);
        Mockito.when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(tradeTest));

        //WHEN //THEN return the list page
        mockMvc.perform(post("/trade/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("account", account)
                .param("type", type)
                .param("buyQuantity", String.valueOf(buyQuantity))
                .param("sellQuantity", String.valueOf(sellQuantity)))
                .andDo(print())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection());
    }

    /* Update an incorrect trade and stay to update    */
    @Test
    public void updateTrade_IncorrectTrade() throws Exception {

        //GIVEN : Give an exiting trade
        Trade tradeTest = new Trade(account,type,buyQuantity,sellQuantity);
        Mockito.when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(tradeTest));

        //WHEN //THEN return the update page
        mockMvc.perform(post("/trade/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("account", account)
                .param("type", type)
                .param("buyQuantity", String.valueOf(buyQuantity))
                .param("sellQuantity", String.valueOf(incorrectSellQuantity)))
                .andDo(print())
                .andExpect(view().name("trade/update"))
                .andExpect(status().isOk());
    }

    /* Display delete a trade */
    @Test
    void deleteTrade_TradeListIsReturn() throws Exception{

        //GIVEN : Give an exiting Person
        Trade tradeTest = new Trade(account,type,buyQuantity,sellQuantity);
        Mockito.when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(tradeTest));

        //WHEN //THEN return the list page
        mockMvc.perform(get("/trade/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));

        Mockito.verify(tradeRepository,Mockito.times(1)).delete(any(Trade.class));
    }
}