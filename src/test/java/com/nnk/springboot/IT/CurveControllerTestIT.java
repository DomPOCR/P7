package com.nnk.springboot.IT;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) /*désactive tous les filtres dans la configuration SpringSecurity */
class CurveControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    /*------------------------------ Post ------------------------------*/

    /* Add validate curvePoint */
    @Test
    void addCurvePoint_ValidateCurvePoint() throws Exception{

        List<CurvePoint> curvePointsBeforeAdd;
        curvePointsBeforeAdd = curvePointRepository.findAll();

        //GIVEN
        Integer curveId = 1;
        Double term = 20d;
        Double value = 30d;

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("curveId", String.valueOf(curveId))
                .param("term", String.valueOf(term))
                .param("value", String.valueOf(value)))
                .andDo(print())
                .andExpect(view().name("redirect:/curvePoint/list"));

        List<CurvePoint> curvePointsAfterAdd;
        curvePointsAfterAdd = curvePointRepository.findAll();

        assertEquals(curvePointsAfterAdd.size(),curvePointsBeforeAdd.size()+1);
    }

    /* Validate non compliant value */
    @Test
    void validateCurvePoint_NonCompliant_Value() throws Exception{

        List<CurvePoint> curvePointsBeforeAdd;
        curvePointsBeforeAdd = curvePointRepository.findAll();

        //GIVEN

        Integer curveId = 1;
        Double term = 20d;
        Double value = 0d;    /* Must be >=1 */

       //WHEN //THEN stay to add page

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("curveId", String.valueOf(curveId))
                    .param("term", String.valueOf(term))
                    .param("value", String.valueOf(value)))
                    .andDo(print())
                    .andExpect(view().name("curvePoint/add"));

        MvcResult mvcResult = result.andExpect(status().isOk()).andReturn();
        String htmlResponse = mvcResult.getResponse().getContentAsString();
        assertTrue(htmlResponse.contains("must be greater than or equal to 1"));

        List<CurvePoint> curvePointsAfterAdd;
        curvePointsAfterAdd = curvePointRepository.findAll();

        assertEquals(curvePointsAfterAdd.size(),curvePointsBeforeAdd.size());
    }

    /*------------------------------ Get ------------------------------*/

    @Test
    void deleteCurvePoint_ExistingCurvePoint() throws Exception{

        Integer id = 1;
        Integer curveId = 1;
        Double term = 20d;
        Double value = 30d;

        //GIVEN
        CurvePoint curvePointTest = new CurvePoint(id,curveId,term,value);
        curvePointRepository.save(curvePointTest);

        List<CurvePoint> curvePointsBeforeDelete;
        curvePointsBeforeDelete = curvePointRepository.findAll();

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));

        List<CurvePoint> curvePointsAfterDelete;
        curvePointsAfterDelete = curvePointRepository.findAll();

        assertEquals(curvePointsAfterDelete.size(),curvePointsBeforeDelete.size()-1);
    }

    @Test
    void deleteCurvePoint_Non_ExistingCurvePoint() throws Exception{

        Integer id = 1;
        Integer curveId = 1;
        Double term = 20d;
        Double value = 30d;

        //GIVEN
        CurvePoint curvePointTest = new CurvePoint(id,curveId,term,value);
        curvePointRepository.save(curvePointTest);

        List<CurvePoint> curvePointsBeforeDelete;
        curvePointsBeforeDelete = curvePointRepository.findAll();

        // WHEN
        // THEN
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/curvePoint/list"));
        }catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid curvePoint Id:999"));
        }
        List<CurvePoint> curvePointsAfterDelete;
        curvePointsAfterDelete = curvePointRepository.findAll();

        assertEquals(curvePointsAfterDelete.size(),curvePointsBeforeDelete.size());
    }
}