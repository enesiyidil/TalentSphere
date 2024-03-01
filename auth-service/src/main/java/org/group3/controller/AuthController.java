package org.group3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.group3.dto.request.*;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.dto.response.LoginResponseDto;
import org.group3.entity.Enums.EStatus;
import org.group3.exception.AuthManagerException;
import org.group3.exception.ErrorType;
import org.group3.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin("*")
@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<Boolean> register(@RequestBody @Valid RegisterRequestDto dto){
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
    public ResponseEntity<List<FindAllResponseDto>> findAll(@RequestParam(required = false) EStatus status){
        return ResponseEntity.ok(authService.findAll(status));
    }
    @GetMapping(FIND_BY_ID + "/{id}")
    public ResponseEntity<FindByIdResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(authService.findByIdDto(id));
    }
    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(authService.softDelete(id));
    }

    @PostMapping(PERSONAL_SAVE)
    public ResponseEntity<Long> personalSave(@RequestBody ManagerOrPersonalSaveRequestDto dto){
        return ResponseEntity.ok(authService.managerOrPersonalSave(dto));
    }
    @PostMapping(MANAGER_SAVE)
    public ResponseEntity<Long> managerSave(@RequestBody ManagerOrPersonalSaveRequestDto dto){
        return ResponseEntity.ok(authService.managerOrPersonalSave(dto));
    }

    @PostMapping(ADMIN_SAVE)
    public ResponseEntity<Long> adminSave(@RequestBody AdminSaveRequestDto dto){
        return ResponseEntity.ok(authService.adminSave(dto));
    }
    @GetMapping(ACTIVATE)
    public ResponseEntity<String> activate(@RequestParam String t){
        return ResponseEntity.ok(authService.activateCode(t));
    }

    @PostMapping(UPDATE_PASSWORD)
    public ResponseEntity<Boolean> updatePassword(@RequestBody UpdatePasswordRequestDto dto){
        return ResponseEntity.ok(authService.updatePassword(dto));
    }



}
