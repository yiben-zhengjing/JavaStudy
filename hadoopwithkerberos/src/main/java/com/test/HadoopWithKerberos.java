package com.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.net.URI;

public class HadoopWithKerberos {
    private static Configuration conf = new Configuration();

    public static void main(String[] args) {
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        System.setProperty("java.security.krb5.conf", "C:\\JAVA8\\jdk1.8.0_144\\jre\\lib\\security\\krb5.conf");
        conf.set("hadoop.security.authentication", "kerberos");
        UserGroupInformation.setConfiguration(conf);
        FileSystem fs = null;
        try {
            UserGroupInformation.loginUserFromKeytab("ngap.sandbox.user@LOCAL.COM", "C:\\etc\\security\\keytabs\\ngap.sandbox.user.keytab");
            fs = FileSystem.get(new URI("hdfs://10.85.64.159:1543"), conf);
            FileStatus[] iter = fs.listStatus(new Path("/tmp/"));
            for (FileStatus fileStatus : iter) {
                System.out.println(fileStatus.getPath());
            }

        } catch (Exception e) {
            System.out.println("身份认证异常： " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
