package com.cis4150.ABS;

import org.apache.commons.io.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Nicole Hurley
 */
public class Restore {
    /**
     * Copies the given file (source) to the given destination (dest)
     * @param source the file being backed up
     * @param dest the location of the new backed up file
     * @throws IOException
     */
    public static void restore (File source, File dest) throws IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
       File decryptedFile = new File(source.getName().replace(".enc", ""));
        if(source.getName().contains(".enc")){
            Encrypt.decryptFile("1234567890123456", source, decryptedFile);

            FileUtils.copyFileToDirectory(decryptedFile, dest);
    } else {
            FileUtils.copyFileToDirectory(source, dest);
        }
    }
}
