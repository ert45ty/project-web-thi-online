package t3h.vn.testonline.service;

import t3h.vn.testonline.dto.request.CustomerDto;

public interface CustomerService {

    public String registerCustomer(CustomerDto customerDto);

    public Boolean reActivateAccount(CustomerDto customerDto);

    public Boolean forgotPassword(CustomerDto customerDto);
}
