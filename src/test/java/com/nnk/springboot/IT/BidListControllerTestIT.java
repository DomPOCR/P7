package com.nnk.springboot.IT;

import com.nnk.springboot.domain.bidList;
import com.nnk.springboot.repositories.BidListRepository;
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
class BidListControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    /*------------------------------ Post ------------------------------*/

    /* Add validate bidList */
    @Test
    void addBidList_ValidateBidList() throws Exception{

        List<bidList> bidListsBeforeAdd;
        bidListsBeforeAdd = bidListRepository.findAll();

        //GIVEN
        String account = "AccountTest";
        String type = "TypeTest";
        Double bidQuantity = 500d;

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("account", account)
                .param("type", type)
                .param("bidQuantity", String.valueOf(bidQuantity)))
                .andDo(print())
                .andExpect(view().name("redirect:/bidList/list"));

        List<bidList> bidListsAfterAdd;
        bidListsAfterAdd = bidListRepository.findAll();

        assertEquals(bidListsAfterAdd.size(),bidListsBeforeAdd.size()+1);
    }

    /* Validate non compliant bidQuantity */
    @Test
    void validateBidList_NonCompliant_bidQuantity() throws Exception{

        List<bidList> bidListsBeforeAdd;
        bidListsBeforeAdd = bidListRepository.findAll();

        Integer bidListId = 1;
        String account = "AccountTest";
        String type = "TypeTest";
        Double bidQuantity = 0d;    /* Must be >=1 */

        //GIVEN
        bidList bidListTest = new bidList(bidListId,account,type,bidQuantity);

        //WHEN //THEN stay to add page
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("account", account)
                    .param("type", type)
                    .param("bidQuantity", String.valueOf(bidQuantity)))
                    .andDo(print())
                    .andExpect(view().name("bidList/add"));
            bidListRepository.save(bidListTest);
        }
        catch (Exception e){
             assertTrue(e.getMessage().contains("must be greater than or equal to 1"));
        }
        List<bidList> bidListsAfterAdd;
        bidListsAfterAdd = bidListRepository.findAll();

        assertEquals(bidListsAfterAdd.size(),bidListsBeforeAdd.size());
    }

    /*------------------------------ Get ------------------------------*/

    @Test
    void deleteBidList_ExistingBidList() throws Exception{

        Integer bidListId = 1;
        String account = "AccountTest";
        String type = "TypeTest";
        Double bidQuantity = 1d;

        //GIVEN
        bidList bidListTest = new bidList(bidListId,account,type,bidQuantity);
        bidListRepository.save(bidListTest);

        List<bidList> bidListsBeforeDelete;
        bidListsBeforeDelete = bidListRepository.findAll();

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));

        List<bidList> bidListsAfterDelete;
        bidListsAfterDelete = bidListRepository.findAll();

        assertEquals(bidListsAfterDelete.size(),bidListsBeforeDelete.size()-1);
    }

    @Test
    void deleteBidList_Non_ExistingBidList() throws Exception{


        Integer bidListId = 1;
        String account = "AccountTest";
        String type = "TypeTest";
        Double bidQuantity = 1d;

        //GIVEN
        bidList bidListTest = new bidList(bidListId,account,type,bidQuantity);
        bidListRepository.save(bidListTest);

        List<bidList> bidListsBeforeDelete;
        bidListsBeforeDelete = bidListRepository.findAll();

        // WHEN
        // THEN
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/bidList/list"));
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid bid Id:999"));
        }
        List<bidList> bidListsAfterDelete;
        bidListsAfterDelete = bidListRepository.findAll();

        assertEquals(bidListsAfterDelete.size(),bidListsBeforeDelete.size());
    }
}