package com.kb.member.controller;

import com.kb.common.util.UploadFiles;
import com.kb.member.dto.ChangePasswordDTO;
import com.kb.member.dto.Member;
import com.kb.member.dto.MemberDTO;
import com.kb.member.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Api(value = "MemberController", tags = "멤버 정보")
@PropertySource({"classpath:/application.properties"})
public class MemberController {

    @Value("#{'${os_type}' == 'win' ? '${file_save_location_win}':'${file_save_location_other}'}")
    public String LOCATION;

    private final MemberService service;

    @GetMapping("/checkid/{id}")
    public ResponseEntity<Boolean> checkDuplicate(@PathVariable String id) {
        return ResponseEntity.ok().body(service.checkDuplicate(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getMember(id));
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


    @PostMapping("")
    public ResponseEntity<Member> join(MemberDTO memberDTO,
                                       @RequestParam(name = "avatar", required = false) MultipartFile avatar) throws IllegalAccessException {
        Member member = memberDTO.toMember();
        return ResponseEntity.ok(service.join(member, avatar));
    }

    @PutMapping("/{id}/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePassword) {
        service.changePassword(changePassword);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> changeProfile(MemberDTO memberDTO,
                @RequestParam(name = "avatar", required = false) MultipartFile avatar) throws IllegalAccessException {
        Member member = memberDTO.toMember();
        return ResponseEntity.ok(service.update(member, avatar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Member> delete(@PathVariable String id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
