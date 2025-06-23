package com.seth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate; 
    public void registerCustomer(CustomerRegistrationRequest request) {

        if (customerRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalStateException("Email already taken");
        }

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo: check if email valid
        //todo: check if email not taken
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}", FraudCheckResponse.class, customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster is not allowed");
        }


        //todo: send a notification
     }

    public void updateCustomer(String email, CustomerRegistrationRequest request) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new IllegalStateException("Customer with email " + email + " not found")
        );

        if (request.firstName() != null) customer.setFirstName(request.firstName());
        if (request.lastName() != null) customer.setLastName(request.lastName());
        if (request.email() != null) {
            customer.setEmail(request.email());
        }

        customerRepository.save(customer);
    }

    public void deleteCustomerByEmail(String email) {
        if (!customerRepository.existsByEmail(email)) {
            throw new IllegalStateException("Customer with email " + email + " not found");
        }
        customerRepository.deleteByEmail(email);
    }

    public Customer retrievingCustomerByEmail(String email) {
        if(!customerRepository.existsByEmail(email)) {
            throw new IllegalStateException("Customer with email " + email + " not found");
        }
        customerRepository.findByEmail(email);
        return null;
    }
}
