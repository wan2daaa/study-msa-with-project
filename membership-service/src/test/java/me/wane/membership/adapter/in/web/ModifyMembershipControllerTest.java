package me.wane.membership.adapter.in.web;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.wane.membership.adapter.out.persistence.MembershipJpaEntity;
import me.wane.membership.adapter.out.persistence.SpringDataMembershipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class ModifyMembershipControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private SpringDataMembershipRepository membershipRepository;

  @BeforeEach
  void setUp() {
    MembershipJpaEntity savedEntity = new MembershipJpaEntity(
        "name",
        "address",
        "email",
        false,
        false
    );
    membershipRepository.save(savedEntity);
  }

  @Test
  void modifyMembershipTest() throws Exception {


    ModifyMembershipRequest request = new ModifyMembershipRequest(
        "1",
        "changedName",
        "changedAddress",
        "changedEmail",
        true,
        true
    );

    mockMvc.perform(
            MockMvcRequestBuilders.post("/membership/modify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(request)));
  }


}