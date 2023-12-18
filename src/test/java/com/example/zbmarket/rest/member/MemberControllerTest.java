package com.example.zbmarket.rest.member;

import com.example.zbmarket.exception.ValidateErrorCode;
import com.example.zbmarket.rest.member.model.MemberCreateRequestDto;
import com.example.zbmarket.service.member.AuthMemberService;
import com.example.zbmarket.service.member.model.DefaultToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(value={})
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthMemberService authMemberService;

    @Test
    @DisplayName("회원가입 성공")
    public void createMemberTest() throws Exception {
        MemberCreateRequestDto request = MemberCreateRequestDto.builder()
                .email("test@example.com").password("a@C45678#").build();

        DefaultToken token = new DefaultToken("access", "refresh");
        when(authMemberService.createMember(request.getEmail(), request.getPassword()))
                .thenReturn(token);
        mockMvc.perform(post("/api/v1/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 이메일 검증 실패")
    public void invalidEmail() throws Exception {
        MemberCreateRequestDto invalidRequest = MemberCreateRequestDto.builder()
                .email("invalid-email").password("password").build();

        mockMvc.perform(post("/api/v1/join") // MemberController의 join 경로에 맞게 수정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists()) // 오류 응답 구조에 따라 수정 가능
                .andExpect(jsonPath("$.errors[0].errorCode").value(ValidateErrorCode.INVALID_EMAIL_FORMAT.name())) // 필드 이름 확인
                .andExpect(jsonPath("$.errors[0].errorMessage").exists());
    }

    @Test
    @DisplayName("회원가입 비밀번호 검증 실패 - 소문자만")
    public void invalidLowCasePassword() throws Exception {
        MemberCreateRequestDto invalidRequest = MemberCreateRequestDto.builder()
                .email("test@example.com").password("passwordefg").build();

        mockMvc.perform(post("/api/v1/join") // MemberController의 join 경로에 맞게 수정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists()) // 오류 응답 구조에 따라 수정 가능
                .andExpect(jsonPath("$.errors[0].errorCode").value(ValidateErrorCode.PATTERN_MISMATCH_ERROR.name())) // 필드 이름 확인
                .andExpect(jsonPath("$.errors[0].errorMessage").exists());
    }

    @Test
    @DisplayName("회원가입 비밀번호 검증 실패 - 대문자만")
    public void invalidUpperCasePassword() throws Exception {
        MemberCreateRequestDto invalidRequest = MemberCreateRequestDto.builder()
                .email("test@example.com").password("PASSWORDDA").build();

        mockMvc.perform(post("/api/v1/join") // MemberController의 join 경로에 맞게 수정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists()) // 오류 응답 구조에 따라 수정 가능
                .andExpect(jsonPath("$.errors[0].errorCode").value(ValidateErrorCode.PATTERN_MISMATCH_ERROR.name())) // 필드 이름 확인
                .andExpect(jsonPath("$.errors[0].errorMessage").exists());
    }

    @Test
    @DisplayName("회원가입 비밀번호 검증 실패 - 특수문자만")
    public void invalidSpecialPassword() throws Exception {
        MemberCreateRequestDto invalidRequest = MemberCreateRequestDto.builder()
                .email("test@example.com").password("!@#$%^&*(").build();

        mockMvc.perform(post("/api/v1/join") // MemberController의 join 경로에 맞게 수정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists()) // 오류 응답 구조에 따라 수정 가능
                .andExpect(jsonPath("$.errors[0].errorCode").value(ValidateErrorCode.PATTERN_MISMATCH_ERROR.name())) // 필드 이름 확인
                .andExpect(jsonPath("$.errors[0].errorMessage").exists());
    }

    @Test
    @DisplayName("회원가입 비밀번호 검증 실패 - 8자이하")
    public void invalidLessThenPassword() throws Exception {
        MemberCreateRequestDto invalidRequest = MemberCreateRequestDto.builder()
                .email("test@example.com").password("aB#12de").build();

        mockMvc.perform(post("/api/v1/join") // MemberController의 join 경로에 맞게 수정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists()) // 오류 응답 구조에 따라 수정 가능
                .andExpect(jsonPath("$.errors[0].errorCode").value(ValidateErrorCode.PATTERN_MISMATCH_ERROR.name())) // 필드 이름 확인
                .andExpect(jsonPath("$.errors[0].errorMessage").exists());
    }

    @Test
    @DisplayName("회원가입 비밀번호 검증 실패 - 16자초과")
    public void invalidGreaterThanPassword() throws Exception {
        MemberCreateRequestDto invalidRequest = MemberCreateRequestDto.builder()
                .email("test@example.com").password("a@#D5678901234567").build();

        mockMvc.perform(post("/api/v1/join") // MemberController의 join 경로에 맞게 수정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists()) // 오류 응답 구조에 따라 수정 가능
                .andExpect(jsonPath("$.errors[0].errorCode").value(ValidateErrorCode.PATTERN_MISMATCH_ERROR.name())) // 필드 이름 확인
                .andExpect(jsonPath("$.errors[0].errorMessage").exists());
    }
}