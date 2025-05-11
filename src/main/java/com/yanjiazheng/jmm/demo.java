package com.yanjiazheng.jmm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class demo {
    public static void main(String[] args) throws IOException {
        // 高效读取
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine()); // 查询次数

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            String s = br.readLine().trim();
            int[] a = new int[n];

            for (int i = 0; i < n; i++) {
                a[i] = s.charAt(i) - '0';
            }

            // 原始陡峭值
            long sumOriginal = 0;
            for (int i = 0; i < n - 1; i++) {
                sumOriginal += Math.abs(a[i + 1] - a[i]);
            }

            // delta_left[i] = 加 1 到 a[i] 导致 a[i] - a[i-1] 变化的代价
            int[] deltaLeft = new int[n];
            for (int i = 1; i < n; i++) {
                int d = a[i] - a[i - 1];
                deltaLeft[i] = Math.abs(d + 1) - Math.abs(d);
            }

            // delta_r[r] = 加 1 到 a[r] 导致 a[r+1] - a[r] 变化的代价
            int[] deltaRight = new int[n];
            for (int r = 0; r < n - 1; r++) {
                int d = a[r + 1] - a[r];
                deltaRight[r] = Math.abs(d - 1) - Math.abs(d);
            }

            // 计算 min_left_prefix[i]：表示 [0..i] 中 deltaLeft 最小值
            int[] minLeftPrefix = new int[n];
            int currentMin = deltaLeft[0];
            minLeftPrefix[0] = currentMin;
            for (int i = 1; i < n; i++) {
                if (deltaLeft[i] < currentMin) {
                    currentMin = deltaLeft[i];
                }
                minLeftPrefix[i] = currentMin;
            }

            // 计算最小的 deltaLeft + deltaRight 组合
            int minCandidate = Integer.MAX_VALUE;
            for (int r = 0; r < n; r++) {
                int candidate = minLeftPrefix[r] + deltaRight[r];
                if (candidate < minCandidate) {
                    minCandidate = candidate;
                }
            }

            // 如果可以减少，就应用最小变化，否则保持原陡峭值
            long finalSum = (minCandidate < 0) ? sumOriginal + minCandidate : sumOriginal;
            System.out.println(finalSum);
        }
    }
}

