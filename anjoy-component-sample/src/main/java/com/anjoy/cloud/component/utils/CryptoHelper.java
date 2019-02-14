package com.anjoy.cloud.component.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {

    /*
     * 注意，如果前端是js，不建议采用aes加密，因为aes是对等加密，前后端需要有同样的key和 iv
     * 但是由于前端js基本上是所有人可见的，key和iv容易泄密，会造成数据安全性问题
     * 建议这种情况使用md5
     *
     * */


    //aes加密使用key和iv，禁止直接在前端js写入这个值，如果需要则采用请求-传值的方式
    //后续这2个值会直接维护到数据库中，这样一旦key和iv泄漏只需要在数据库中改变这2个值就可以
    //也可以通过改变这2个值达到注销所有的令牌的目的
    private static String key = "814EEv2a14g8BLol";
    private static String iv = "ad8dqJ8ReF1a10lL";
    private static String seperateChar = "~";


    public static void main(String args[]) throws Exception {
        String source = "wzwzwzwz`2016-09-09 09:09:09`2016-09-01 09:33:30`WEB`127.0.0.1`ANY";
        String[] sourcestr = source.split("`");
        System.out.println(sourcestr[0]);

        System.out.println(aesMd5Encrypt(source));
        System.out.println(aesMd5Decrypt(aesMd5Encrypt(source)));

        System.out.println(aesMd5Decrypt("eHveJoVvNySBgthK0jVZ3QUq8gaY1rCwbACJjOpjKBn8VqNc33dcsLo5orgU+FfsU/SW1tHHzNDPwGyPkrAUTtt2RYgYBe/3G40M0lC1STksk4AxMJfOwhLWbj73o5GTLYXhAVVnswYz8VlI4t4mEw=="));
        System.out.println(encryptMD5("123456"));
    }


	/*  使用本util时，前端js采用如下方式可以与后端互通
	 *
     <script src="js/crypto-js/crypto-js.js"></script>

    function encrypt(){
	var data = "test";
		var key  = CryptoJS.enc.Latin1.parse('Yunping,27346283');
		var iv   = CryptoJS.enc.Latin1.parse('ImDevilAndIWantU');
    //加密
    var encrypted = CryptoJS.AES.encrypt(
      data,
      key,
      {iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding
    });
    alert(encrypted);
    //解密
    var decrypted = CryptoJS.AES.decrypt(encrypted,key,{iv:iv,padding:CryptoJS.pad.ZeroPadding});
	alert(decrypted.toString(CryptoJS.enc.Utf8));

	}

	function encryptMD5(){
	var code = CryptoJS.MD5("098f6bcd4621d373cade4e832627b4f6").toString(CryptoJS.enc.Hex);
                alert(code);

	}
    */


    /*
     * 通用aes256加密方法
     *
     * */
    public static String encrypt(String data) throws Exception {
        try {
            long tockenLifeTime = (((((long) 90 * 24) * 60) * 60) * 1000);
            System.out.println(tockenLifeTime);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 通用aes256解密方法
     *
     * */
    public static String decrypt(String data) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 通用 MD5 加密方法
     *
     * */
    public static String encryptMD5(String data) throws Exception {
        String dataMD5 = Md5Util.MD5Encode(data.trim(), null).trim();
        return dataMD5;
    }

    /*
     *
     * 通用aes+md5加密方法，利用aes做加密，md5做hash验证
     * 注意传入的串两边不能带空格
     *
     * */
    public static String aesMd5Encrypt(String data) throws Exception {
        return encrypt(encryptMD5(data.trim())+seperateChar+data.trim());
    }


    /*
     *
     * 通用aes+md5解密方法，先解aes，再hash验证md5值
     * */
    public static String aesMd5Decrypt(String data) throws Exception {
        try {
            String[] s = decrypt(data).trim().split(seperateChar);
            if (encryptMD5(s[1].trim()).equals(s[0].trim())){
                return s[1].trim();
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
