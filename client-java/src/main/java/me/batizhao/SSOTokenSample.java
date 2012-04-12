package me.batizhao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.InetAddress;
import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenID;
import com.iplanet.sso.SSOTokenManager;

/**
 * @author: batizhao
 * @since: 12-4-11 PM3:55
 */
public class SSOTokenSample {
    private SSOTokenManager manager;
    private SSOToken token;

    private SSOTokenSample(String tokenID)
            throws SSOException {
        if (validateToken(tokenID)) {
            setGetProperties(token);
        }
    }

    private boolean validateToken(String tokenID)
            throws SSOException {
        boolean validated = false;
        manager = SSOTokenManager.getInstance();
        token = manager.createSSOToken(tokenID);

        // isValid method returns true for valid token.
        if (manager.isValidToken(token)) {
            // let us get all the values from the token
            String host = token.getHostName();
            java.security.Principal principal = token.getPrincipal();
            String authType = token.getAuthType();
            int level = token.getAuthLevel();
            InetAddress ipAddress = token.getIPAddress();
            long maxTime = token.getMaxSessionTime();
            long idleTime = token.getIdleTime();
            long maxIdleTime = token.getMaxIdleTime();

            System.out.println("SSOToken host name: " + host);
            System.out.println("SSOToken Principal name: " +
                    principal.getName());
            System.out.println("Authentication type used: " + authType);
            System.out.println("IPAddress of the host: " +
                    ipAddress.getHostAddress());
            validated = true;
        }

        return validated;
    }

    private void setGetProperties(SSOToken token)
            throws SSOException {
        /*
         * Validate the token again, with another method
         * if token is invalid, this method throws an exception
         */
        manager.validateToken(token);
        System.out.println("SSO Token validation test Succeeded.");

        // Get the SSOTokenID associated with the token and print it.
        SSOTokenID id = token.getTokenID();
        String tokenId = id.toString();
        System.out.println("Token ID: " + tokenId);

        // Set and get properties in the token.
        token.setProperty("TimeZone", "PST");
        token.setProperty("County", "SantaClara");
        String tZone = token.getProperty("TimeZone");
        String county = token.getProperty("County");

        System.out.println("Property: TimeZone: " + tZone);
        System.out.println("Property: County: " + county);
    }

    public static void main(String[] args) {
        try {
            System.out.print("Enter SSOToken ID: ");
            String ssoTokenID = (new BufferedReader(
                    new InputStreamReader(System.in))).readLine();
            new SSOTokenSample(ssoTokenID.trim());
        } catch (SSOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
