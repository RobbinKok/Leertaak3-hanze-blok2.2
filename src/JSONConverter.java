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

        for (int i = 0; i < arrayList.size(); i++)
        {
            stringBuilder.append(arrayList.get(i));
            stringBuilder.append(i < arrayList.size() - 1 ? "," : "]");
        }

        return stringBuilder.toString();
    }
}
