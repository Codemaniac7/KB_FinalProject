package com.kb.member.controller;

import com.kb.common.util.UploadFiles;
import com.kb.kakao.KaKaoLoginService;
import com.kb.member.dto.ChangePasswordDTO;
import com.kb.member.dto.Member;
import com.kb.member.dto.MemberDTO;
import com.kb.member.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
@Api(value = "MemberController", tags = "멤버 정보")
@PropertySource({"classpath:/application.properties"})
public class MemberController {

    @Value("#{'${os_type}' == 'win' ? '${file_save_location_win}':'${file_save_location_other}'}")
    public String LOCATION;

    private final MemberService service;

    private final KaKaoLoginService kaKaoLoginService;


    @GetMapping("/checkid/{id}")
    public ResponseEntity<Boolean> checkDuplicate(@PathVariable String id) {
        return ResponseEntity.ok().body(service.checkDuplicate(id));
    }

    @GetMapping("/checkkakao/{kakaoId}")
    public ResponseEntity<Boolean> checkDuplicateKakao(@PathVariable String kakaoId) {
        return ResponseEntity.ok().body(service.checkKakaoDuplicate(kakaoId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Member> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getMember(id));
    }


    @GetMapping("/kakaoInfo/{code}")
    public ResponseEntity<Map<String,Object>> getKakaoInfo(@PathVariable String code) throws IOException {
        String enrollUrl = "http://localhost:8081/auth/kakaojoin";
        String token = kaKaoLoginService.getToken(code, enrollUrl);
        Map<String, Object> map = kaKaoLoginService.getUserInfo(token);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}/avatar")
    public void getAvatar(@PathVariable String id, HttpServletResponse response) {
        String avatarPath =  LOCATION + "/avatar/" + id + ".png";
        File file = new File(avatarPath);
        if (!file.exists()) {
            file = new File( LOCATION + "/avatar/unknown.png");
        }
        UploadFiles.downloadImage(response, file);
    }

    @GetMapping("/mno")
    public ResponseEntity<Integer> getMemberAutoIncrement(){
        return ResponseEntity.ok(service.getMemberAutoIncrement());
    }


    @PostMapping("")
    public ResponseEntity<Member> join(MemberDTO memberDTO,
                                       @RequestParam(name = "avatar", required = false)
                                       MultipartFile avatar) throws IllegalAccessException {
        Member member = memberDTO.toMember();
        System.out.println("@@@" + member);
        return ResponseEntity.ok(service.join(member, avatar));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Integer> checkPassword(@PathVariable String id, @RequestBody ChangePasswordDTO changePassword) {

        return ResponseEntity.ok( service.checkPassword(id, changePassword));
    }

    @PutMapping("/{id}/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePassword) {
        service.changePassword(changePassword);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/kakaoInfo/{code}")
    public ResponseEntity<Member> changeProfile(MemberDTO memberDTO,
                                                @RequestParam(name = "avatar", required = false) MultipartFile avatar) throws IllegalAccessException {
        Member member = memberDTO.toMember();
        return ResponseEntity.ok(service.update(member, avatar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Member> delete(@PathVariable String id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PostMapping(value = "/saveInvestType", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> saveInvestType(@RequestBody MemberDTO memberDTO) {
        log.info("Received request to save investType: {}", memberDTO);

        if (memberDTO.getId() == null || memberDTO.getInvestType() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }

        boolean isSaved = service.saveInvestType(memberDTO.getId(), memberDTO.getInvestType());

        if (isSaved) {
            return ResponseEntity.ok("투자 성향이 성공적으로 저장되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("투자 성향 저장 실패");
        }
    }

    @PutMapping(value = "/updateInvestType", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> updateInvestType(@RequestBody MemberDTO memberDTO) {
        log.info("Received request to update investType: {}", memberDTO);

        if (memberDTO.getId() == null || memberDTO.getInvestType() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }

        boolean isUpdated = service.updateInvestType(memberDTO.getId(), memberDTO.getInvestType());

        if (isUpdated) {
            return ResponseEntity.ok("투자 성향이 성공적으로 업데이트되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("투자 성향 업데이트 실패");
        }
    }


}
