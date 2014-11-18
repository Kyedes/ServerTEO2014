

import java.security.MessageDigest;

/**
 * Created by jesperbruun on 23/09/14.
 */

public class EncryptUserID {

    /**
     * Constant cipher seed - DO NOT CHANGE.
     * http://www.miraclesalad.com/webtools/md5.php - Du kan her saette userid foerst og derefter hashkey for at teste
     */
    private final String HASHKEY = "v.eRyzeKretW0r_t";
    private String key;
    private static MessageDigest digester;

    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Enkryptere en tekst streng som bliver parset til funktionen
    public String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Error");
        }

        digester.update(str.getBytes());
        byte[] hash = digester.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
    }

    //Quick example of how to get the hash.
    public static void main(String[] args) {
    	EncryptUserID cryptU = new EncryptUserID(); 
    	String userId = "esky13ab";
        System.out.print("Secret key: " + cryptU.crypt(userId + cryptU.getHASHKEY()));
//        String key = crypt(userId+ HASHKEY);

    }

	public String getKey() {
		return key;
	}

	public String getHASHKEY() {
		return HASHKEY;
	}
    
}

