import java.util.HashMap;

public class JSONParser {

    StringBuilder stringBuilder = new StringBuilder();


    public JSONParser(String string) {
        this.stringBuilder = new StringBuilder(string);
    }

    public JSONParser(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public HashMap<String, String> toObject() {
        HashMap<String, String> map = new HashMap<>();
        boolean isKey = false;
        boolean isValue = false;
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < this.stringBuilder.length(); i++) {
            char c = this.stringBuilder.charAt(i);
            if (c != '"') {
                if (isKey) {
                    if (c == ':') {
                        isKey = false;
                        isValue = true;
                    } else {
                        key.append(c);
                    }
                } else if (isValue) {
                    if (c == '}' || c == ',') {
                        map.put(key.toString(), value.toString());
                        isValue = false;
                        key = new StringBuilder();
                        value = new StringBuilder();

                        if (c == ',') {
                            isKey = true;
                        }
                    } else {
                        value.append(c);
                    }
                } else {
                    if (c == '{') {
                        isKey = true;
                    }
                }
            }
        }
        return map;
    }
}
