package com.nnk.springboot.IT;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
class RatingControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    /*------------------------------ Post ------------------------------*/

    /* Add validate rating */
    @Test
    void addRating_ValidateRating() throws Exception{

        List<Rating> ratingsBeforeAdd;
        ratingsBeforeAdd = ratingRepository.findAll();

        //GIVEN
        String moodysRating = "MoodysRatingTest";
        String sandPRating = "SandPRatingTest";
        String fitchRating = "FitchRatingTest";
        Integer orderNumber = 1;

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("moodysRating", moodysRating)
                .param("sandPRating", sandPRating)
                .param("fitchPRating", fitchRating)
                .param("orderNumber", String.valueOf(orderNumber)))
                .andDo(print())
                .andExpect(view().name("redirect:/rating/list"));

        List<Rating> ratingsAfterAdd;
        ratingsAfterAdd = ratingRepository.findAll();

        assertEquals(ratingsAfterAdd.size(),ratingsBeforeAdd.size()+1);
    }

    /*------------------------------ Get ------------------------------*/

    @Test
    void deleteRating_ExistingRating() throws Exception{

        Integer id = 1;
        String moodysRating = "MoodysRatingTest";
        String sandPRating = "SandPRatingTest";
        String fitchRating = "FitchRatingTest";
        Integer orderNumber = 1;

        //GIVEN
        Rating ratingTest = new Rating(id,moodysRating,sandPRating,fitchRating,orderNumber);
        ratingRepository.save(ratingTest);

        List<Rating> ratingsBeforeDelete;
        ratingsBeforeDelete = ratingRepository.findAll();

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));

        List<Rating> ratingsAfterDelete;
        ratingsAfterDelete = ratingRepository.findAll();

        assertEquals(ratingsAfterDelete.size(),ratingsBeforeDelete.size()-1);
    }

    @Test
    void deleteRating_Non_ExistingRating() throws Exception{

        Integer id = 1;
        String moodysRating = "MoodysRatingTest";
        String sandPRating = "SandPRatingTest";
        String fitchRating = "FitchRatingTest";
        Integer orderNumber = 1;

        //GIVEN
        Rating ratingTest = new Rating(id,moodysRating,sandPRating,fitchRating,orderNumber);
        ratingRepository.save(ratingTest);

        List<Rating> ratingsBeforeDelete;
        ratingsBeforeDelete = ratingRepository.findAll();

        // WHEN
        // THEN
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/rating/list"));
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid rating Id:999"));
        }
        List<Rating> ratingsAfterDelete;
        ratingsAfterDelete = ratingRepository.findAll();

        assertEquals(ratingsAfterDelete.size(),ratingsBeforeDelete.size());
    }
}