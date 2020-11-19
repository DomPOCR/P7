package com.nnk.springboot.IT;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) /*désactive tous les filtres dans la configuration SpringSecurity */
class TradeControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    /*------------------------------ Post ------------------------------*/

    /* Add validate trade */
    @Test
    void addTrade_ValidateTrade() throws Exception{

        List<Trade> tradesBeforeAdd;
        tradesBeforeAdd = tradeRepository.findAll();

        //GIVEN
        String account = "AccountTest";
        String type = "TypeTest";
        Double buyQuantity = 500d;
        Double sellQuantity = 200d;

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("account", account)
                .param("type", type)
                .param("buyQuantity", String.valueOf(buyQuantity))
                .param("sellQuantity", String.valueOf(sellQuantity)))
                .andDo(print())
                .andExpect(view().name("redirect:/trade/list"));

        List<Trade> tradesAfterAdd;
        tradesAfterAdd = tradeRepository.findAll();

        assertEquals(tradesAfterAdd.size(),tradesBeforeAdd.size()+1);
    }

    /* Validate non compliant buyQuantity */
    @Test
    void validateTrade_NonCompliant_buyQuantity() throws Exception{

        List<Trade> tradesBeforeAdd;
        tradesBeforeAdd = tradeRepository.findAll();

        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        String account = "AccountTest";
        String type = "TypeTest";
        Double buyQuantity = 0d;    /* Must be >=1 */
        Double sellQuantity = 200d;

        //GIVEN
        Trade tradeTest = new Trade(account,type,buyQuantity,sellQuantity);
        tradeTest.setCreationDate(creationDate);

        //WHEN //THEN stay to add page
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("account", account)
                    .param("type", type)
                    .param("buyQuantity", String.valueOf(buyQuantity))
                    .param("sellQuantity", String.valueOf(sellQuantity)))
                    .andDo(print())
                    .andExpect(view().name("trade/add"));
            tradeRepository.save(tradeTest);
        }
        catch (Exception e){
            assertTrue(e.getMessage().contains("must be greater than or equal to 1"));
        }
        List<Trade> tradesAfterAdd;
        tradesAfterAdd = tradeRepository.findAll();

        assertEquals(tradesAfterAdd.size(),tradesBeforeAdd.size());
    }

    /*------------------------------ Get ------------------------------*/

    @Test
    void deleteTrade_ExistingTrade() throws Exception{

        String account = "AccountTest";
        String type = "TypeTest";
        Double buyQuantity = 500d;    
        Double sellQuantity = 200d;

        //GIVEN
        Trade tradeTest = new Trade(account,type,buyQuantity,sellQuantity);
        tradeRepository.save(tradeTest);

        List<Trade> tradesBeforeDelete;
        tradesBeforeDelete = tradeRepository.findAll();

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));

        List<Trade> tradesAfterDelete;
        tradesAfterDelete = tradeRepository.findAll();

        assertEquals(tradesAfterDelete.size(),tradesBeforeDelete.size()-1);
    }

    @Test
    void deleteTrade_Non_ExistingTrade() throws Exception{

        String account = "AccountTest";
        String type = "TypeTest";
        Double buyQuantity = 500d;
        Double sellQuantity = 200d;

        //GIVEN
        Trade tradeTest = new Trade(account,type,buyQuantity,sellQuantity);
        tradeRepository.save(tradeTest);

        List<Trade> tradesBeforeDelete;
        tradesBeforeDelete = tradeRepository.findAll();

        // WHEN
        // THEN
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/trade/list"));
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid trade Id:999"));
        }
        List<Trade> tradesAfterDelete;
        tradesAfterDelete = tradeRepository.findAll();

        assertEquals(tradesAfterDelete.size(),tradesBeforeDelete.size());
    }
}