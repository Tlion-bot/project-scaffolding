package com.base.test.java;

import com.base.test.java.killport.KillPortUtils;

import java.io.IOException;

public class Test {


        public static void main(String[] args) throws IOException {
            KillPortUtils.start(8329);

        }
    }
