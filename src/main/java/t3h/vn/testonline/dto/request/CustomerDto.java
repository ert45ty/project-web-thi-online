package t3h.vn.testonline.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import t3h.vn.testonline.dto.groupValidation.ChangePassword;
import t3h.vn.testonline.dto.groupValidation.EmailActiveAccount;

@Getter
@Setter
public class CustomerDto {
    @NotBlank(message = "Tên tài khoản không được để trống")
    @Size(min = 5, max = 20, message = "Tên tài khoản phải nằm trong khoảng từ 5-20 kí tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được phép để trống")
    @Size(min = 6, max = 24, message = "Mật khẩu phải nằm trong khoảng 6-24 kí tự", groups = ChangePassword.class)
    private String password;

    @NotBlank(message = "Email không được phép để trống")
    @Email(message = "Email không đúng định dạng", groups = EmailActiveAccount.class)
    private String email;

    @NotBlank(message = "Tên của bạn không được phép để trống")
    @Size(min = 2, max = 30, message = "Tên của bạn phải nằm trong khoảng từ 2-30 kí tự")
    private String fullname;

    private String role;

}
