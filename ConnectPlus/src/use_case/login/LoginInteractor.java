package use_case.login;

import Entities.LoggedInUser;
import Entities.User;
import com.sun.net.httpserver.*;
import org.apache.juli.logging.Log;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.security.KeyStore;

import static Entities.LoggedInUser.getLoggedInUser;


public class LoginInteractor implements LoginInputBoundary{
    final LoginUserDataAccessInterface userDataAccessObject;
    final LoginOutputBoundary loginPresenter;
    private final String appSecret = "0e34d02764d3c9a90dffa20e67a2322a";
    String redirectUri = "https://localhost:8080/callback";
    String appId = "820899762858583";


    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary){
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;

    }


    public void execute() {
        try {
            WebDriver driver = initualizeServer();
            String auth = getToken(driver);
            User loggedUser = loggedUser(getAccessToken(auth)); //get the attempting user
            if (userDataAccessObject.existsByName(loggedUser.getUsername())){ //if the user exists in the database
                loggedUser = userDataAccessObject.getUser(loggedUser.getUsername());
            }
            else{
                userDataAccessObject.saveUser(loggedUser);
            }
            if (getLoggedInUser() == null) {
                loginPresenter.prepareSuccessView(new LoginOutputData(loggedUser.getName(), loggedUser.getWins(),
                        loggedUser.getLosses(), loggedUser.getEloRating()));
                LoggedInUser.logIn(loggedUser);
            }
            else {
                loginPresenter.prepareFailView("Someone is already logged in. Realistically, this should never happen.");
                System.out.println("Someone is already logged in. Realistically, this should never happen.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public WebDriver initualizeServer() throws Exception{
        char[] keystorePassword = "password".toCharArray();
        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(new FileInputStream("mykeystore.jks"), keystorePassword);

        // Set up the key manager factory
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keystore, keystorePassword);

        // Set up the trust manager factory
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(keystore);

        // Create and initialize SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        // Create and configure HTTPS server
        HttpsServer server = HttpsServer.create(new InetSocketAddress(8080), 0);
        server.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
            public void configure(HttpsParameters params) {
                SSLContext context = getSSLContext();
                params.setSSLParameters(context.getDefaultSSLParameters());
            }
        });
        // Define an endpoint and its handler
        server.createContext("/callback", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                String query = httpExchange.getRequestURI().getQuery(); // Get the query string from URI
                String response = "You can now close this window.";
                if (query != null && query.contains("code=")) {
                    System.out.println(query);
                }

                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        server.start();
        System.out.println("Server started on port 8000");
        System.setProperty("webdriver.chrome.driver", "ConnectPlus/chromedriver.exe"); //https://googlechromelabs.github.io/chrome-for-testing/#stable
        return new ChromeDriver();

    }

    public String getToken(WebDriver driver) throws Exception {


        driver.get(UriComponentsBuilder.fromHttpUrl("https://www.facebook.com/v18.0/dialog/oauth")
                .queryParam("client_id", appId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "email, gaming_profile")
                .build().toUriString());
        // if url contains error, return error, if url contains code, return code
        while (true){
            String url = driver.getCurrentUrl();
            if (url.contains(redirectUri)){
                if (url.contains("error")){
                    driver.quit();
                    return "error";
                }
                else if (url.contains("code")){
                    driver.quit();
                    System.out.println(url.substring(url.indexOf("code=")+5));
                    return url.substring(url.indexOf("code=")+5);
                }
            }
            Thread.sleep(1000);
        }
    }

    public String getAccessToken(String code) throws Exception{
        String url = UriComponentsBuilder.fromHttpUrl("https://graph.facebook.com/v18.0/oauth/access_token?")
                .queryParam("client_id", appId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("client_secret", appSecret)
                .queryParam("code", code)
                .build().toUriString();
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Print result
        System.out.println(response.toString());

        // Parse JSON response
        JSONObject myResponse = new JSONObject(response.toString());
        return myResponse.getString("access_token");

    }

    public User loggedUser(String accTok) throws Exception {
        String url = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=" + accTok;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        JSONObject myResponse = new JSONObject(response.toString());
        String id = myResponse.getString("id");
        String name = myResponse.getString("name");
        String email = myResponse.getString("email");
//        String picture = myResponse.getString("picture");
        User user = new User(id);
        user.setEmail(email);
        user.setName(name);
        //user.setPicture(picture);
        return user;

    }



}
