package com.nnk.springboot.UT;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointRepository curvePointRepository;

    // Données de test
    Timestamp creationDate = new Timestamp(System.currentTimeMillis());
    Integer curveId = 1;
    Double term = 20d;
    Double value = 30d;

    /* Show the list of curvePoint */
    @Test
    void listCurvePoint() throws Exception{

        List<CurvePoint> curvePointList = new ArrayList<>();

        //GIVEN
        CurvePoint curvePointTest = new CurvePoint(curveId,term,value);
        curvePointTest.setCreationDate(creationDate);

        curvePointList.add(curvePointTest);
        Mockito.when(curvePointRepository.findAll()).thenReturn(curvePointList);

        //WHEN //THEN return the list page
        mockMvc.perform(get("/curvePoint/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
    }

    /* Display curvePoint adding form*/
    @Test
    void addCurvePoint() throws Exception{

        //GIVEN

        CurvePoint curvePointTest = new CurvePoint(curveId,term,value);
        curvePointTest.setCreationDate(creationDate);

        //WHEN //THEN return the add page
        mockMvc.perform(get("/curvePoint/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    /* Validate correct curvePoint */
    @Test
    void validateCurvePoint_CorrectCurvePoint() throws Exception{

        //GIVEN : Give a new curvePoint
        CurvePoint curvePointTest = new CurvePoint(curveId,term,value);
        Mockito.when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePointTest);
        Mockito.when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePointTest));

        //WHEN //THEN return the list page
        mockMvc.perform(post("/curvePoint/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("curveId", String.valueOf(curveId))
                .param("term", String.valueOf(term))
                .param("value", String.valueOf(value)))
                .andDo(print())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(status().is3xxRedirection());
    }

    /* Display curvePoint updating form */
    @Test
    void updateCurvePoint_curvePointIsReturn() throws Exception{

        //GIVEN : Give an exiting curvePoint
        CurvePoint curvePointTest = new CurvePoint(curveId,term,value);
        Mockito.when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePointTest));

        //WHEN //THEN return the update page
        mockMvc.perform(get("/curvePoint/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    /* Display delete a curvePoint */
    @Test
    void DeleteCurvePoint_CurvePointListIsReturn() throws Exception{

        //GIVEN : Give an exiting Person
        CurvePoint curvePointTest = new CurvePoint(curveId,term,value);
        Mockito.when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePointTest));

        //WHEN //THEN return the list page
        mockMvc.perform(get("/curvePoint/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));

        Mockito.verify(curvePointRepository,Mockito.times(1)).delete(any(CurvePoint.class));
    }
}