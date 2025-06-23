package com.seth;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

//    @PostMapping
//    public void registerCustomer(@RequestBody @Valid CustomerRegistrationRequest customerRegistrationRequest) {
//
//        log.info("New Customer Registration {}", customerRegistrationRequest);
//
//        customerService.registerCustomer(customerRegistrationRequest);
//    }

//    @PutMapping("/{id}")
//    public void updateCustomer(@PathVariable Integer id, @RequestBody @Valid CustomerRegistrationRequest request) {
//        log.info("Updating Customer ID {} with {}", id, request);
//        customerService.updateCustomer(id, request);
//    }

//    @DeleteMapping
//    public void deleteCustomerByEmail(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//        log.info("Deleting Customer Email {}", email);
//        customerService.deleteCustomerByEmail(email);
//    }

    @PostMapping
    public ResponseEntity<String> registerCustomer(@RequestBody @Valid CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("New Customer Registration {}", customerRegistrationRequest);
        customerService.registerCustomer(customerRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateCustomer(@RequestBody @Valid CustomerRegistrationRequest request) {
        log.info("Updating Customer with {}", request);
        customerService.updateCustomer(request.email(), request);
        return ResponseEntity.ok("Customer updated successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCustomerByEmail(@RequestParam String email) {
        log.info("Deleting Customer Email {}", email);
        customerService.deleteCustomerByEmail(email);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @GetMapping("/by-email")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam(name = "email") String email) {
        log.info("Getting customer by email: {}", email);
        Customer customer = customerService.retrievingCustomerByEmail(email);
        return ResponseEntity.ok(customer);
    }


}
 