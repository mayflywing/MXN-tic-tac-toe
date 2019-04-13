import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HTTP {
    public String userId ;
    public String teamId ;
    public String gameId ;
    public String api_key ;
    public String opponent_teamId;

    public HTTP(){
        userId = "686";
        teamId = "1104";
        api_key = "09ed6fbadc39dc991e28";
    };

    public void setOpponent_teamId(String opponent_teamId){
    	//set opponent's teamId
        this.opponent_teamId = opponent_teamId;
        System.out.println("Opponent is:"+this.opponent_teamId);
    }

    public String getMember() {
        try {
            URL url = new URL("http://www.notexponential.com/aip2pgaming/api/index.php?type=team&teamId=" + teamId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-api-key", api_key);
            connection.setRequestProperty("userid", userId);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer result = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            in.close();
            connection.disconnect();
            System.out.println(result.toString());
            return result.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String createGame(String teamId1, String teamId2,int boardSize,int target){
        try {
            URL url = new URL("http://www.notexponential.com/aip2pgaming/api/index.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("x-api-key", api_key);
            connection.setRequestProperty("userid", userId);
            connection.setDoOutput(true);
            String parameters = "type=game&teamId1=" + teamId1 + "&teamId2=" + teamId2 +"&gameType=TTT"
                    +"&boardSize=" + boardSize + "&target=" +target;
            //System.out.println(parameters);
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            os.writeBytes(parameters);
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer result = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            in.close();
            connection.disconnect();
            System.out.println(result.toString());
            AutoSetGameId(result.toString());//create game then set gameId
            return result.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public void AutoSetGameId(String result) {
        String rgex = "\\{\"code\":\"OK\",\"gameId\":(.*?)\\}";
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(result);
        while(m.find()){
            this.gameId = m.group(1);
        }
        System.out.println("gameId: "+this.gameId);
        //this.gameId = gameId;
    }
    public void ManualSetGameId(String gameId){
        this.gameId = gameId;
        System.out.println("gameId: "+this.gameId);
    }

    public String getMoves(int count){
    	//get recent moves
        try {
            URL url = new URL("http://www.notexponential.com/aip2pgaming/api/index.php?type=moves&gameId="+gameId+"" +
                    "&count="+count);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-api-key", api_key);
            connection.setRequestProperty("userid",userId);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer result = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            in.close();
            connection.disconnect();
            //System.out.println(result.toString());
            return result.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

    }
    public Optional<Move> getOpponentLatestMove(String result){
        String tempMoves = "";
        String[] moves;
        String rgex = "\\{\"moves\":\\[(.*?)\\],\"code\":\"OK\"}";
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(result);
        while(m.find()){
            tempMoves = m.group(1);
        }
        moves = tempMoves.split("}");
        Move latestMove = new Move(moves[moves.length-1]);
        //System.out.println(latestMove.teamId);
        if(!latestMove.teamId.equals(this.teamId)){
        	//If the latest move is not down by our team, then return this move
            this.opponent_teamId=latestMove.teamId;//opponent
            System.out.println("team:"+latestMove.teamId+" move "+latestMove.move);
            return Optional.of(latestMove);
        }
        else {
            return Optional.empty();//Otherwise return empty
        }
    }

    public String makeMove(int x,int y){
        try{
        URL url = new URL("http://www.notexponential.com/aip2pgaming/api/index.php");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("x-api-key", api_key);
        connection.setRequestProperty("userid",userId);
        connection.setDoOutput(true);
        String parameters = "type=move&gameId="+gameId+"&teamId="+teamId+"&move="+x+","+y;
        //System.out.println(parameters);
        DataOutputStream os = new DataOutputStream(connection.getOutputStream());
        os.writeBytes(parameters);
        os.flush();
        os.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer result = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine);
        }
        in.close();
        connection.disconnect();
        System.out.println(result.toString());
        return result.toString();}
        catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }



    public static void main(String[] args) {
        HTTP http = new HTTP();
        //http.setOpponent_teamId("1105");
        String gameId="{\"code\":\"OK\",\"gameId\":1527}";
         http.AutoSetGameId(gameId);
         //System.out.println(http.gameId);
         //http.makeMove("1,1");

        String result = "{\"moves\":[{\"moveId\":\"9002\",\"gameId\":\"1527\",\"teamId\":\"1104\",\"move\":\"0,0\",\"symbol\":\"O\",\"moveX\":\"0\",\"moveY\":\"0\"}]," +
             "\"code\":\"OK\"}";
        http.getOpponentLatestMove(http.getMoves(1));
        //http.getMember();

    }
}

