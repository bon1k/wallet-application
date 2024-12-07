package org.example.controller;

import org.example.exception.WalletNotFoundException;
import org.example.model.Wallet;
import org.example.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

//    @PostMapping
//    public ResponseEntity<?> createWallet(@RequestBody WalletRequest request) {
//        try {
//            walletService.processTransaction(request.getWalletId(), request.getOperationType(),
//                    request.getAmount());
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    @GetMapping("/{walletId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable UUID walletId) {
        try {
            Wallet wallet = walletService.getWallet(walletId);
            return ResponseEntity.ok(wallet);
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Map<String, Object> requestBody) {
        BigDecimal initialBalance = new BigDecimal(requestBody.get("initialBalance").toString());
        Wallet wallet = walletService.createWallet(initialBalance);
        return ResponseEntity.ok(wallet);
    }
}
