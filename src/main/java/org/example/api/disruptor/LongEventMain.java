package org.example.api.disruptor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LongEventMain {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //调用Class.forName()方法加载驱动程序
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.80:3306/raven?useSSL=false", "root", "root"); //创建连接
            Statement stmt = conn.createStatement(); //创建Statement对象

            String sql = "select * from sys_app";    //要执行的SQL
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            System.out.println("编号" + "\t" + "姓名" + "\t" + "年龄");
            while (rs.next()) {
                System.out.print(rs.getInt(1) + "\t");
                System.out.print(rs.getString(2) + "\t");
                System.out.print(rs.getString(3) + "\t");
                System.out.print(rs.getString(4) + "\t");
                System.out.println("\t");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
        }
    }
   /* public static void main(String[] args) throws Exception {
        String binaryStr = "1010"; // 二进制字符串
        String hexStr = "1A"; // 十六进制字符串
        String octalStr = "17"; // 八进制字符串

        try {
            int decimalFromBinary = Integer.parseInt(binaryStr, 2);
            System.out.println("二进制 " + binaryStr + " 转换为十进制: " + decimalFromBinary);

            int decimalFromHex = Integer.parseInt(hexStr, 16);
            System.out.println("十六进制 " + hexStr + " 转换为十进制: " + decimalFromHex);

            int decimalFromOctal = Integer.parseInt(octalStr, 8);
            System.out.println("八进制 " + octalStr + " 转换为十进制: " + decimalFromOctal);
        } catch (NumberFormatException e) {
            System.out.println("解析失败: " + e.getMessage());
        }
        int bufferSize = 2;
        Disruptor<LongEvent> disruptor =
                new Disruptor<>(
                        new LongEventFactory(),
                        bufferSize,
                        DaemonThreadFactory.INSTANCE,
                        ProducerType.SINGLE,
                        new BlockingWaitStrategy());
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
            Thread.sleep(1000);
        }
    }*/
}
