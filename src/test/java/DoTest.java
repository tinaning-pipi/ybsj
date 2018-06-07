import com.hill.ybsj.api.maputil.MapUtil;

public class DoTest {

    public static void main(String[] arg0){
        double[] locaion = MapUtil.GetAround(39.50229483160951, 116.34032657879418, 1000);
        System.out.println("距离范围，最小经度：" + locaion[1] + "，最大经度：" + locaion[3] + "，最小为纬度：" + locaion[0] + "，最大纬度：" + locaion[2]);    }
}
