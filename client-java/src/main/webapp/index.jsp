<%@ page import="com.iplanet.sso.SSOTokenManager" %>
<%@ page import="com.iplanet.sso.SSOToken" %>
<%@ page import="com.sun.identity.idm.AMIdentity" %>
<%@ page import="com.sun.identity.idm.IdUtils" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.iplanet.sso.SSOTokenID" %>
<%@ page import="java.net.InetAddress" %>

<h1>This is OpenAM Client for Java.</h1><br/>

<%
    SSOTokenManager manager = SSOTokenManager.getInstance();
    SSOToken token = manager.createSSOToken(request);

    if (manager.isValidToken(token)) {
        //print some of the values from the token.
        String host = token.getHostName();
        java.security.Principal principal = token.getPrincipal();
        String authType = token.getAuthType();
        int level = token.getAuthLevel();
        InetAddress ipAddress = token.getIPAddress();

        out.println("SSOToken host name: " + host);
        out.println("<br />");
        out.println("SSOToken Principal name: " +
                principal.getName());
        out.println("<br />");
        out.println("Authentication type used: " + authType);
        out.println("<br />");
        out.println("IPAddress of the host: " +
                ipAddress.getHostAddress());
        out.println("<br />");
    }

    /* Validate the token again, with another method.
    * if token is invalid, this method throws exception
    */
    manager.validateToken(token);
    out.println("SSO Token validation test succeeded");
    out.println("<br />");

    // Get the SSOTokenID associated with the token and print it.
    SSOTokenID tokenId = token.getTokenID();
    out.println("The token id is " + tokenId.toString());
    out.println("<br />");

    // Set and get some properties in the token.
    token.setProperty("Company", "Sun Microsystems");
    token.setProperty("Country", "USA");
    String name = token.getProperty("Company");
    String country = token.getProperty("Country");
    out.println("Property: Company: " + name);
    out.println("<br />");
    out.println("Property: Country: " + country);
    out.println("<br />");

    // Retrieve user profile and print them
    AMIdentity userIdentity = IdUtils.getIdentity(token);
    Map attrs = userIdentity.getAttributes();
    out.println("User Attributes: " + attrs);

%>
