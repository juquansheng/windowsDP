package sample.utils;

import com.csvreader.CsvReader;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;

public class CsvToNumUtil {

    // CSV文件编码
    public static final String ENCODE = "UTF-8";

    public static List<Double> getresult(String file) throws IOException {
        try {

            List<Double> result = Lists.newArrayList();
            // 创建CSV读对象
            FileInputStream fr = new FileInputStream(file);
            //CSVReader reader = new CSVReader(new InputStreamReader(fr, "utf-8"));
            CsvReader csvReader = new CsvReader(fr, Charset.defaultCharset());

            List<Long> B = Lists.newArrayListWithCapacity(8192);
            List<Long> C = Lists.newArrayListWithCapacity(8192);
            List<Long> D = Lists.newArrayListWithCapacity(8192);
            List<Long> E = Lists.newArrayListWithCapacity(8192);
            // 读表头( 跳过表头 如果需要表头的话，这句可以忽略  )
            //csvReader.readHeaders();

            while (csvReader.readRecord()){
                if (csvReader.get(0).equals("")){
                    break;
                }
                B.add(stringToLong(csvReader.get(1)));
                C.add(stringToLong(csvReader.get(2)));
                D.add(stringToLong(csvReader.get(3)));
                E.add(stringToLong(csvReader.get(4)));
            }
            result.add(getRMS(B));
            result.add(getRMS(C));
            result.add(getRMS(D));
            result.add(getRMS(E));

            return result;

        } catch (IOException e) {
            throw e;
        }
    }

    public static long stringToLong(String source){
        BigDecimal bd = new BigDecimal(source);
        /*String es = source.split("E")[0];
        String s = es.replace(".", "");*/
        return Long.valueOf(bd.toPlainString());
    }

    public static double getRMS(List<Long> longList){
        Long count = 0L;
        for (Long num:longList){
            count = count + num * num;
        }

        long l = count / 8192;
        return Math.sqrt(l);
    }
}
