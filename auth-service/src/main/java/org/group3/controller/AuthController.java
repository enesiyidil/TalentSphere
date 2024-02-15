package org.group3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group3.dto.request.*;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdRespoonseDto;
import org.group3.dto.response.LoginResponseDto;
import org.group3.dto.response.RegisterResponseDto;
import org.group3.entity.Enums.EStatus;
import org.group3.exception.AuthManagerException;
import org.group3.exception.ErrorType;
import org.group3.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        if (!dto.getPassword().equals(dto.getRePassword())){
            throw new AuthManagerException(ErrorType.REGISTER_PASSWORD_MISMATCH);
        }
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<FindAllResponseDto>> findAll(String token, @RequestParam(required = false) EStatus status){
        return ResponseEntity.ok(authService.findAll(token, status));
    }
    @GetMapping(FIND_BY_ID + "/{id}")
    public ResponseEntity<FindByIdRespoonseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(authService.findByIdDto(id));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(authService.softDelete(id));
    }

    @PostMapping(PERSONAL_SAVE)
    public ResponseEntity<Long> personalSave(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.personalSave(dto));
    }
    @GetMapping(ACTIVATE)
    public ResponseEntity<String> activate(@RequestParam String t){
        return ResponseEntity.ok(authService.activateCode(t));
    }

    @PostMapping(UPDATE_PASSWORD)
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequestDto dto){
        return ResponseEntity.ok(authService.updatePassword(dto));
    }



}
