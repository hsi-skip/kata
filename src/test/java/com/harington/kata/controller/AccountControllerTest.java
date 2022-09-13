package com.harington.kata.controller;

import com.harington.kata.entity.Account;
import com.harington.kata.entity.Customer;
import com.harington.kata.repository.AccountRepository;
import com.harington.kata.service.AccountService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class AccountControllerTest {

    @MockBean
    AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc restServerMockMvc;
    private EntityManager em;

    @MockBean
    AccountService accountService;

    private Account account;

    @Before
    public void setUp() {
        restServerMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public static Account createEntity(EntityManager em) {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Nono");
        customer.setLastName("Berber");
        customer.setEmail("hsaaan@mail.com");
        customer.setBirthday(LocalDate.now());

        Account account = new Account();
        account.setId(44L);
        account.setAmount(100);
        account.setIban("IBAN");
        account.setRib("RIB");
        account.setSwift("SWIFT");
        account.setCustomer(customer);

        return account;
    }

    @Spy
    @InjectMocks
    private AccountController controller = new AccountController(accountService);

    @BeforeEach
    public void initTest() {
        account = createEntity(em);
    }

    @Test
    void testGetAllAccounts() throws Exception {

    }

}
