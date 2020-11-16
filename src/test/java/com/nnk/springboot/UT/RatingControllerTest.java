package com.nnk.springboot.UT;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingRepository ratingRepository;

    // Données de test
    String moodysRating = "MoodysRatingTest";
    String sandPRating = "SandPRatingTest";
    String fitchRating = "FitchRatingTest";
    Integer orderNumber = 1;

    /* Show the list of Rating */
    @Test
    void listRating() throws Exception{

        List<Rating> ratingList = new ArrayList<>();

        //GIVEN
        Rating RatingTest = new Rating(moodysRating,sandPRating,fitchRating,orderNumber);
        ratingList.add(RatingTest);
        Mockito.when(ratingRepository.findAll()).thenReturn(ratingList);

        //WHEN //THEN return the list page
        mockMvc.perform(get("/rating/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    /* Display rating adding form*/
    @Test
    void addRating() throws Exception{

        //GIVEN

        //WHEN //THEN return the add page
        mockMvc.perform(get("/rating/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    /* Validate correct rating */
    @Test
    void validateRating_CorrectRating() throws Exception{

        //GIVEN : Give a new rating
        Rating ratingTest = new Rating(moodysRating,sandPRating,fitchRating,orderNumber);

        Mockito.when(ratingRepository.save(any(Rating.class))).thenReturn(ratingTest);
        Mockito.when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(ratingTest));

        //WHEN //THEN return the list page
        mockMvc.perform(post("/rating/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("moodysRating",moodysRating)
                .param("sandPRating", sandPRating)
                .param("fitchRating", fitchRating)
                .param("orderNumber", String.valueOf(orderNumber)))
                .andDo(print())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(status().is3xxRedirection());
    }

    /* Display rating updating form */
    @Test
    void updateRating_RatingIsReturn() throws Exception{

        //GIVEN : Give an exiting rating
        Rating ratingTest = new Rating(moodysRating,sandPRating,fitchRating,orderNumber);
        Mockito.when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(ratingTest));

        //WHEN //THEN return the update page
        mockMvc.perform(get("/rating/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    /* Display delete a rating */
    @Test
    void DeleteRating_RatingListIsReturn() throws Exception{

        //GIVEN : Give an exiting Person
        Rating ratingTest = new Rating(moodysRating,sandPRating,fitchRating,orderNumber);
        Mockito.when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(ratingTest));

        //WHEN //THEN return the list page
        mockMvc.perform(get("/rating/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));

        Mockito.verify(ratingRepository,Mockito.times(1)).delete(any(Rating.class));
    }

}