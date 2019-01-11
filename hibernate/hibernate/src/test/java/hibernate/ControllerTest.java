package hibernate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.objectfrontier.training.hibernate.controller.PersonController;

@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PersonController personController;

    @Before
    public void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void readTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/person")).andExpect(MockMvcResultMatchers.content().string("Hi"));
    }

}
