//package interface_adapter;
//
//import interface_adapter.login.LoginController;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import use_case.login.LoginInputBoundary;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//public class LoginControllerTest {
//
//    @Mock
//    private LoginInputBoundary loginInteractor;
//
//    @InjectMocks
//    private LoginController loginController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
//    }
//
//    @Test
//    public void testLoginRedirect() throws Exception {
//        when(loginInteractor.getLoginUrl()).thenReturn("http://mocked-url-for-facebook.com");
//        mockMvc.perform(get("/facebookLogin"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("http://mocked-url-for-facebook.com"));
//    }
//
//    // ... other tests
//}
//
