package t3h.vn.testonline.serviceApi;

import t3h.vn.testonline.dto.request.UserDto;
import t3h.vn.testonline.dto.response.PageResponse;
import t3h.vn.testonline.dto.response.UserDetailResponse;

public interface UserServiceApi {
    long addUser(UserDto userDto);

    void updateUser(long userId, UserDto userDto);

    void deleteUser(long userId);

    UserDetailResponse getUser(long userId);

    PageResponse<?> getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, String... sorts);
}
