package com.nnk.springboot.IT;

import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) /*désactive tous les filtres dans la configuration SpringSecurity */
class BidListControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;


}