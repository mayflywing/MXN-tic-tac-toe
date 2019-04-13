import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Move {
    public String moveId,gameId,teamId,moveX,moveY,move,symbol;
    public Move(String move){
        String rgex = "\"moveId\":\"(.*?)\",";
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(move);
        while(m.find()){
            this.moveId = m.group(1);
        }
        rgex = "\"gameId\":\"(.*?)\",";
        pattern = Pattern.compile(rgex);
        m = pattern.matcher(move);
        while(m.find()){
            this.gameId =m.group(1);
        }

        rgex = "\"teamId\":\"(.*?)\",";
        pattern = Pattern.compile(rgex);
        m = pattern.matcher(move);
        while(m.find()){
            this.teamId = m.group(1);
        }

        rgex = "\"move\":\"(.*?)\",";
        pattern = Pattern.compile(rgex);
        m = pattern.matcher(move);
        while(m.find()){
            this.move = m.group(1);
        }

        rgex = "\"symbol\":\"(.*?)\",";
        pattern = Pattern.compile(rgex);
        m = pattern.matcher(move);
        while(m.find()){
            this.symbol = m.group(1);
        }

        rgex = "\"moveX\":\"(.*?)\",";
        pattern = Pattern.compile(rgex);
        m = pattern.matcher(move);
        while(m.find()){
            this.moveX = m.group(1);
        }

        rgex = "\"moveY\":\"(.*?)\"";
        pattern = Pattern.compile(rgex);
        m = pattern.matcher(move);
        while(m.find()){
            this.moveY = m.group(1);
        }
    }
}
