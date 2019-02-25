package Frontend;

import Backend.data.LoginDetails;
import Backend.data.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class Requests {
    private URL url;

    public URL getUrl(){ return this.url; }
    public Requests(){ }
    public void setUrl(URL url){ this.url = url;}

    public String sendRequest(int type, LoginDetails loginDetails, User user){
        try{
            URL url;
            if(type == 1) {
                url = new URL("http://localhost:8080/login");
                this.setUrl(url);
            }
            else {
                url = new URL("http://localhost:8080/signup");
                this.setUrl(url);
            }
//            System.out.println(this.getUrl().toString());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");

            ObjectMapper mapper = new ObjectMapper();
            String json;

            if(type == 1)
                json = mapper.writeValueAsString(loginDetails);
            else
                json = mapper.writeValueAsString(user);

            con.getOutputStream().write(json.getBytes(Charset.forName("UTF-8")));
            con.getOutputStream().flush();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println(response);
            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
