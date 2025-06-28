/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.SchnorrModel;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SchnorrController {
    private SchnorrModel model;
    private SecureRandom random = new SecureRandom();

    public SchnorrController(SchnorrModel model) {
        this.model = model;
        this.random = new SecureRandom();
    }

    public void generateParameters() {
        BigInteger q, p, g;
        int qBitLength = 160;
        int pBitLength = 1024;
        while (true) {
            q = BigInteger.probablePrime(qBitLength, random);

            int kBitLength = pBitLength - qBitLength;
            BigInteger k;
            do {
                k = new BigInteger(kBitLength, random);
            } while (k.compareTo(BigInteger.TWO) < 0); 

            p = q.multiply(k).add(BigInteger.ONE);

            if (p.bitLength() != pBitLength) continue;
            if (!p.isProbablePrime(100)) continue;

            BigInteger h;
            do {
                h = new BigInteger(pBitLength - 1, random);
                g = h.modPow(p.subtract(BigInteger.ONE).divide(q), p);
            } while (g.compareTo(BigInteger.ONE) <= 0);

            break;
        }

        model.setP(p);
        model.setQ(q);
        model.setG(g);
    }

    public void generateKeys() {
        BigInteger q = model.getQ();
        BigInteger p = model.getP();
        BigInteger g = model.getG();
        BigInteger x = new BigInteger(q.bitLength(), random).mod(q.subtract(BigInteger.ONE)).add(BigInteger.ONE);
        BigInteger y = g.modPow(x, p);
        model.setX(x);
        model.setY(y);
    }

    public BigInteger hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            return new BigInteger(1, hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public BigInteger[] sign(String message) {
        BigInteger p = model.getP();
        BigInteger q = model.getQ();
        BigInteger g = model.getG();
        BigInteger x = model.getX();
        BigInteger k;
        do {
            k = new BigInteger(q.bitLength(), random).mod(q.subtract(BigInteger.valueOf(2))).add(BigInteger.valueOf(2));
        } while (k.compareTo(BigInteger.ONE) <= 0 || k.compareTo(q.subtract(BigInteger.ONE)) >= 0);
        BigInteger r = g.modPow(k, p);
        BigInteger hM = hashSHA256(message).mod(q);

        BigInteger rInv = r.modInverse(q);
        BigInteger s = (k.subtract(x.multiply(rInv).multiply(hM))).mod(q);
        model.setK(k);
        model.setR(r);
        model.setE(hM);
        model.setS(s);
        model.setOriginalMessage(message);
        return new BigInteger[]{r, s};
    }

    public boolean verify(String message, BigInteger[] signature) {
        BigInteger p = model.getP();
        BigInteger q = model.getQ();
        BigInteger g = model.getG();
        BigInteger y = model.getY();
        BigInteger r = signature[0];
        BigInteger s = signature[1];
           StringBuilder error = new StringBuilder();

         if (!message.equals(model.getOriginalMessage())) {
        error.append("Thông điệp đã bị thay đổi! ");
        }
        if (!r.equals(model.getR()) || !s.equals(model.getS())) {
            error.append("Chữ ký giả mạo!");
        }
        if (error.length() > 0) {
        throw new IllegalArgumentException(error.toString().trim());
        }
        BigInteger hM = hashSHA256(message).mod(q);

        BigInteger rInv = r.modInverse(q);
        
        //BigInteger u1 = s.multiply(rInv).mod(q);
        
        BigInteger u2 = hM.multiply(rInv).mod(q);
        
        BigInteger v = g.modPow(s, p).multiply(y.modPow(u2, p)).mod(p);
        model.setV(v);
        
        return v.equals(r);
    }
}