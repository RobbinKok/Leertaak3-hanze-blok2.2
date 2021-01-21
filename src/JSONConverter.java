import java.util.ArrayList;

public class JSONConverter {
    ArrayList<?> arrayList;

    public JSONConverter(ArrayList<?> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        int counter = 1;
        for (Object item : arrayList) {
            stringBuilder.append(item.toString());
            if (counter < arrayList.size()) {
                stringBuilder.append(",");
            }
            counter++;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
