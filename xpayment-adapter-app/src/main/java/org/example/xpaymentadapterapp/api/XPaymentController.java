package org.example.xpaymentadapterapp.api;

import org.example.xpaymentadapterapp.api.generated.DefaultApi;
import org.example.xpaymentadapterapp.api.generated.model.ChargeResponse;
import org.example.xpaymentadapterapp.api.generated.model.CreateChargeRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.xpaymentadapterapp.api.XPaymentAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class XPaymentController implements DefaultApi {

    XPaymentAdapter adapter;

    @Override
    public ResponseEntity<ChargeResponse> createCharge(CreateChargeRequest createChargeRequest) {
        var newCharge = adapter.create(createChargeRequest);

        var uriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        uriBuilder.scheme("http");
        uriBuilder.pathSegment(newCharge.getId().toString());

        return ResponseEntity.created(uriBuilder.build().toUri()).body(newCharge);
    }

    @Override
    public ResponseEntity<ChargeResponse> retrieveCharge(UUID id) {
        return ResponseEntity.ok(adapter.retrieve(id));
    }
}
