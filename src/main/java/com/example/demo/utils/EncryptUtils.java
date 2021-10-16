package com.example.demo.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncryptUtils {

    private static final byte[] des_key = "simpleDesKey".getBytes();

    private static final String private_key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKqGiWyiXuovhQoB9/Qa1PkmqlXOyV3e3wal791y8qC7TURBtbLyAMqgDbO8tTc6RtwVbohoNmxOLwmkU1CbLnE42iZiCW2aCT0jk7eOqAlWFaF4OxS0RwIZBZShpjb+sEHCLKgEKQmAYpC5v4qyPaW5r06mX6y2kc307NAPeKdBAgMBAAECgYBt8EbxLs2algBjr1k4m4PrDa6Lm8HfLy0CkEDQuKcMUTVDaEnIkJVKDV5IHsWGUaAVjAuzDdzofo982+CK+0E411NakNSA3ZX1/B29hdO34xHu0Il56kEwOj2Y1Jw/+Y4Q2EOMlWUJwFLEAU4RnZSH8vjTepS1pOPzrQcb4f/CMQJBAOOrQwvYF35VMC+3RCPn2YkIOkniklT/i+tW2FFlf+xl0eIatiaCiRUEGNS9NEWxYtKFps/coQ1IdEcl0OSP/r8CQQC/vuJXiARAjGdjFGaZWSB3nG4GVMMbYGjBYxoj3vg62URJIPc3VhfiHroU1me0wb1qqPlrGU31w6OwLxtrZtn/AkAYjpeLxoI6OTwaWX07Fj4LntySraK1El9oQhTRKs6SlCUAa3ssSjY5ExkOkV37CngrVwk4MIcapK8OIZEXpkSHAkA908MQDl+woJVFsXJTyBVb/CjOCc7PqQqeMOJg5qiL8Tq/ic2tHimdU+MUjP3rnv0Aw3OJQSOON4M0cDwbKZLpAkB0OPYM66yEnOgvW0KinG6x7N6rHAAC0uEd9Ns/0S4hRwLzLqTSmuSOrUpCviBt+ZonnEunhKHh9IkZPDED3wQ4";

    public static String comRsaDecrypt(String encryptStr) {
        RSA rsa = new RSA(private_key, null);
        return rsa.decryptStr(encryptStr, KeyType.PrivateKey);
    }

    public static String encryptToken(String rawToken) {
        return SecureUtil.des(des_key).encryptBase64(rawToken);
    }

    public static String decryptToken(String encryptToken) {
        String result = null;
        try {
            result = SecureUtil.des(des_key).decryptStr(encryptToken);
        } catch (Exception e) {
            log.error("token: " + encryptToken + " ,解密异常", e);
        }
        return result;
    }

}
