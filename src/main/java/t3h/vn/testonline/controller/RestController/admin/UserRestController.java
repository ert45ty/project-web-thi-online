package t3h.vn.testonline.controller.RestController.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import t3h.vn.testonline.dto.request.UserDto;
import t3h.vn.testonline.dto.response.ResponseData;
import t3h.vn.testonline.dto.response.ResponseError;
import t3h.vn.testonline.exception.ResourceNotFoundException;
import t3h.vn.testonline.serviceApi.UserServiceApi;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceApi userServiceApi;

    @Operation(summary = "Get user detail", description = "API get user detail by userId")
    @GetMapping("/{userId}")
    public ResponseData<?> getUserById(@PathVariable Long userId){
        log.info("Request get user with userId={}", userId);
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "User detail", userServiceApi.getUser(userId));
        }catch (ResourceNotFoundException e){
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get user detail fail");
        }
    }

    @Operation(summary = "Create user", description = "API create user")
    @PostMapping("/create")
    public ResponseData<Long> createUser(@Valid @RequestBody UserDto userDto){
        log.info("Request create user");
        try {
            Long userId = userServiceApi.addUser(userDto);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Create user successfully", userId);
        }catch (ResourceNotFoundException e){
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create user fail");
        }
    }

    @Operation(summary = "Update user", description = "API update user")
    @PutMapping("/update/{userId}")
    public ResponseData<?> updateUser(@Valid @RequestBody UserDto userDto,
                                      @PathVariable Long userId){
        log.info("Request update user with userId={}", userId);
        try {
            userServiceApi.updateUser(userId, userDto);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User update successfully");
        }catch (ResourceNotFoundException e){
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update user fail");
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseData<?> deleteUser(@PathVariable Long userId){
        log.info("Request delete user with userId={}", userId);
        try {
            userServiceApi.deleteUser(userId);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete user successfully");
        }catch (ResourceNotFoundException e){
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete user fail");
        }
    }
}
