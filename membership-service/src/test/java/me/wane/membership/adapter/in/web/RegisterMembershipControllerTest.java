package me.wane.membership.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.wane.membership.domain.Membership;
import me.wane.membership.domain.Membership.MembershipAddress;
import me.wane.membership.domain.Membership.MembershipEmail;
import me.wane.membership.domain.Membership.MembershipId;
import me.wane.membership.domain.Membership.MembershipIsCorp;
import me.wane.membership.domain.Membership.MembershipIsValid;
import me.wane.membership.domain.Membership.MembershipName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterMembershipControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Test
  void testRegisterMembership() throws Exception {
    RegisterMembershipRequest request = new RegisterMembershipRequest("name",
        "address", "email", true);

    Membership expect = Membership.generateMember(
        new MembershipId("1"),
        new MembershipName(request.getName()),
        new MembershipEmail(request.getEmail()),
        new MembershipAddress(request.getAddress()),
        new MembershipIsValid(true),
        new MembershipIsCorp(request.isCorp())
    );

    mockMvc.perform(
            MockMvcRequestBuilders.post("/membership/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(expect)));

  }

}