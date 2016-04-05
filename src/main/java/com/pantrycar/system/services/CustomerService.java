package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.Customer;
import com.pantrycar.system.core.FacebookLogin;
import com.pantrycar.system.core.GoogleLogin;
import com.pantrycar.system.core.Password;
import com.pantrycar.system.dao.CustomerDAO;
import com.pantrycar.system.representations.customer.CustomerDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final Provider<CustomerDAO> daoProvider;
    private final Provider<PasswordService> passwordServiceProvider;

    @Inject
    public CustomerService(Provider<CustomerDAO> daoProvider, Provider<PasswordService> passwordServiceProvider) {
        this.daoProvider = daoProvider;
        this.passwordServiceProvider = passwordServiceProvider;
    }

    public Customer getCustomerByEmailId(String emailId) {
        return getUniqueCustomerByColumn("emailId", emailId);
    }

    public List<Customer> getCustomerByEmailId(List emailIds) {
        return daoProvider.get().findByColumn("emailId", emailIds);
    }

    public Customer getUniqueCustomerByColumn(String column, Object value) {
        return daoProvider.get().findUniqueByColumn(column, value);
    }

    public CustomerDetail getCustomerDetailsResponse(Customer customer) {
        CustomerDetail customerDetail = CustomerDetail.builder()
                .contactNo(customer.getContactNo())
                .emailId(customer.getEmailId())
                .name(customer.getName())
                .verified(customer.isVerified()).build();
        return customerDetail;
    }

    public CustomerDetail register(CustomerDetail customerDetail) {
        Customer customer = Customer.builder()
                .contactNo(customerDetail.getContactNo())
                .emailId(customerDetail.getEmailId())
                .name(customerDetail.getEmailId())
                .build();
        boolean isValid = validateAndAddAuthToCustomer(customer, customerDetail);
        if (isValid == false)
            return new CustomerDetail().error(400, "No login pass present during registration");
        customer = daoProvider.get().persist(customer);
        return getCustomerDetailsResponse(customer);
    }

    private boolean validateAndAddAuthToCustomer(Customer customer, CustomerDetail customerDetail) {
        if (customerDetail.getLoginPass() != null)
            customer.setPassword(new Password(customerDetail.getLoginPass()).updateTimeStamps());
        else if (customerDetail.getGoogleLogin() != null)
            customer.setGoogleLogin(GoogleLogin.builder()
                    .bio(customerDetail.getGoogleLogin().getBio())
                    .token(customerDetail.getGoogleLogin().getToken())
                    .customer(customer)
                    .build().updateTimeStamps());
        else if (customerDetail.getFacebookLogin() != null)
            customer.setFacebookLogin(FacebookLogin.builder()
                    .bio(customerDetail.getFacebookLogin().getBio())
                    .token(customerDetail.getFacebookLogin().getToken())
                    .customer(customer)
                    .build().updateTimeStamps());
        else
            return false;
        return true;
    }

    public CustomerDetail updateRememberToken(Customer customer, String token) {
        customer.setRememberToken(token);
        return getCustomerDetailsResponse(daoProvider.get().persist(customer));
    }

    public CustomerDetail updateVerificationToken(Customer customer, String token) {
        customer.setVerificationToken(token);
        return getCustomerDetailsResponse(daoProvider.get().persist(customer));
    }

    public CustomerDetail updatePasswordResetToken(Customer customer, String token) {
        customer.setPassResetToken(token);
        return getCustomerDetailsResponse(daoProvider.get().persist(customer));
    }

    public CustomerDetail resetPassword(Customer customer, String loginPass) {
        customer.setPassResetToken(null);
        daoProvider.get().persist(customer);
        Password password = customer.getPassword();
        passwordServiceProvider.get().resetPassword(password, loginPass);
        return getCustomerDetailsResponse(customer);
    }

    public CustomerDetail markCustomerVerified(Customer customer) {
        customer.setVerificationToken(null);
        customer.setVerified(true);
        return getCustomerDetailsResponse(daoProvider.get().persist(customer));
    }

    public CustomerDetail validateLogin(Customer customer, CustomerDetail customerDetail) {
        boolean isValidLogin;
        if (customer.getPassword() != null && customerDetail.getLoginPass() != null)
            isValidLogin = customer.getPassword().getPassword().equals(customerDetail.getLoginPass());
        else if (customer.getGoogleLogin() != null && customerDetail.getGoogleLogin() != null)
            isValidLogin = customer.getGoogleLogin().getToken().equals(customerDetail.getGoogleLogin().getToken());
        else if (customer.getFacebookLogin() != null && customerDetail.getFacebookLogin() != null)
            isValidLogin = customer.getFacebookLogin().getToken().equals(customerDetail.getFacebookLogin().getToken());
        else
            isValidLogin = false;

        if (isValidLogin == false) {
            return new CustomerDetail().error(400, "Login failed or No login passed during login");
        }
        return getCustomerDetailsResponse(customer);
    }
}
