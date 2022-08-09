package com.springboot.security.securityinmemorykotlin

import com.springboot.security.securityinmemorykotlin.controller.WebController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(WebController::class)
class WebControllerTest(
  @Autowired
  val mockMvc: MockMvc
) {
  @WithMockUser(username = "admin", password = "admin", roles = ["USER", "ADMIN"])
  @Test
  fun givenAuthRequestOnPrivateService_shouldSucceedWith200(){
    mockMvc.perform(get("/index")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
  }
}