package com.nnk.springboot.UT;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListRepository bidListRepository;

    // Données de test
    String account = "AccountTest";
    String type = "TypeTest";
    Double bidQuantity = 500d;

    /* Show the list of BidList */
    @Test
    void listBidList() throws Exception{

        List<BidList> bidLists = new ArrayList<>();

        //GIVEN
        BidList bidListTest = new BidList(account,type,bidQuantity);
        bidLists.add(bidListTest);
        Mockito.when(bidListRepository.findAll()).thenReturn(bidLists);

        //WHEN //THEN return the list page
        mockMvc.perform(get("/bidList/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }

    /* Display bidList adding form*/
    @Test
    void addBidList() throws Exception{

        //GIVEN

        //WHEN //THEN return the add page
        mockMvc.perform(get("/bidList/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    /* Validate correct bidList */
    @Test
    void validateBidList_CorrectBidList() throws Exception{

        //GIVEN : Give a new bidList
        BidList bidListTest = new BidList(account,type,bidQuantity);
        Mockito.when(bidListRepository.save(any(BidList.class))).thenReturn(bidListTest);
        Mockito.when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidListTest));

        //WHEN //THEN return the list page
        mockMvc.perform(post("/bidList/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("account", account)
                .param("type", type)
                .param("bidQuantity", String.valueOf(bidQuantity)))
                .andDo(print())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection());
    }

    /* Display bidList updating form */
    @Test
    void updateBidList_BidListIsReturn() throws Exception{

        //GIVEN : Give an exiting bidList
        BidList bidListTest = new BidList(account,type,bidQuantity);
        Mockito.when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidListTest));

        //WHEN //THEN return the update page
        mockMvc.perform(get("/bidList/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    /* Display delete a bidList */
    @Test
    void DeleteBidList_BidListListIsReturn() throws Exception{

        //GIVEN : Give an exiting Person
        BidList bidListTest = new BidList(account,type,bidQuantity);
        Mockito.when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidListTest));

        //WHEN //THEN return the list page
        mockMvc.perform(get("/bidList/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));

        Mockito.verify(bidListRepository,Mockito.times(1)).delete(any(BidList.class));
    }
}