package com.wechat.pay.contrib.apache.httpclient.auth;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * @author xy-peng
 */
public class PrivateKeySigner implements Signer {

  protected final String certificateSerialNumber;
  protected final PrivateKey privateKey;

  public PrivateKeySigner(String serialNumber, PrivateKey privateKey) {
    this.certificateSerialNumber = serialNumber;
    this.privateKey = privateKey;
  }

  @Override
  public SignatureResult sign(byte[] message) {
    try {
      Signature sign = Signature.getInstance("SHA256withRSA");
      sign.initSign(privateKey);
      sign.update(message);
      return new SignatureResult(
          Base64.encodeToString(sign.sign(), Base64.NO_WRAP), certificateSerialNumber);

    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
    } catch (SignatureException e) {
      throw new RuntimeException("签名计算失败", e);
    } catch (InvalidKeyException e) {
      throw new RuntimeException("无效的私钥", e);
    }
  }
}
