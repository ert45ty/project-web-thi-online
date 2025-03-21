package t3h.vn.testonline.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import t3h.vn.testonline.dto.groupValidation.UpdateAccountInformation;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "Tên tài khoản không được để trống")
    @Size(min = 5, max = 20, message = "Tên tài khoản phải nằm trong khoảng từ 5-20 kí tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được phép để trống")
    @Size(min = 6, max = 24, message = "Mật khẩu phải nằm trong khoảng 6-24 kí tự")
    private String password;

    @NotBlank(message = "Email không được phép để trống")
    @Email(message = "Email không đúng định dạng", groups = UpdateAccountInformation.class)
    private String email;

    @NotBlank(message = "Tên của bạn không được phép để trống")
    @Size(min = 2, max = 30, message = "Tên của bạn phải nằm trong khoảng từ 2-30 kí tự", groups = UpdateAccountInformation.class)
    private String fullname;

    private String role;

    private String image_name;

    private LocalDateTime created_at;

    MultipartFile fileImage;

}
