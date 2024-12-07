package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.OperationType;
import org.example.exception.InsufficientFundsException;
import org.example.exception.WalletNotFoundException;
import org.example.model.Wallet;
import org.example.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public void processTransaction(UUID walletId, OperationType operationType, BigDecimal amount) throws InsufficientFundsException {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        if (operationType.equals(OperationType.DEPOSIT)) {
            wallet.setBalance(wallet.getBalance().add(amount));
        } else if (operationType.equals(OperationType.WITHDRAW)) {
            if (wallet.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Not enough funds");
            }
            wallet.setBalance(wallet.getBalance().subtract(amount));
        } else {
            throw new IllegalArgumentException("Invalid operation type");
        }
        walletRepository.save(wallet);
    }

    public Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
    }


    public Wallet createWallet(BigDecimal initialBalance) {
        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID()); // Генерация нового UUID для кошелька
        wallet.setBalance(initialBalance); // Установка начального баланса
        return walletRepository.save(wallet); // Сохраняем в базе данных
    }
}
