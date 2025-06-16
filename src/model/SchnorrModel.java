/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigInteger;

public class SchnorrModel {
    private BigInteger p, q, g, x, y, k, r, e, s, v;
    private String originalMessage;

    public SchnorrModel() {
        // Giá trị mặc định (sẽ được khởi tạo ngẫu nhiên trong controller)
        p = BigInteger.ZERO;
        q = BigInteger.ZERO;
        g = BigInteger.ZERO;
        x = BigInteger.ZERO;
        y = BigInteger.ZERO;
        k = BigInteger.ZERO;
        r = BigInteger.ZERO;
        e = BigInteger.ZERO;
        s = BigInteger.ZERO;
        v = BigInteger.ZERO;
        originalMessage = "";
    }
    public String getOriginalMessage() { return originalMessage; }
    public void setOriginalMessage(String originalMessage) { this.originalMessage = originalMessage; }
    // Getters và Setters
    public BigInteger getP() { return p; }
    public void setP(BigInteger p) { this.p = p; }
    public BigInteger getQ() { return q; }
    public void setQ(BigInteger q) { this.q = q; }
    public BigInteger getG() { return g; }
    public void setG(BigInteger g) { this.g = g; }
    public BigInteger getX() { return x; }
    public void setX(BigInteger x) { this.x = x; }
    public BigInteger getY() { return y; }
    public void setY(BigInteger y) { this.y = y; }
    public BigInteger getK() { return k; }
    public void setK(BigInteger k) { this.k = k; }
    public BigInteger getR() { return r; }
    public void setR(BigInteger r) { this.r = r; }
    public BigInteger getE() { return e; }
    public void setE(BigInteger e) { this.e = e; }
    public BigInteger getS() { return s; }
    public void setS(BigInteger s) { this.s = s; }
    public BigInteger getV() { return v; }
    public void setV(BigInteger v) { this.v = v; }
}
