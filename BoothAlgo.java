package com.codewithharry;

import java.util.Scanner;

class Main {
        static String M,Q;
        static String Q1="0";
        static String Q0;
        static String ac= "0";
        static int cycles=0;
        static String neg_M;
        static boolean ism1Negative = false;
        static boolean ism2Negative = false;
        static String result;

    static void matchBits()
    {
        while(M.length() - Q.length() > 0)
        {
            Q = "0" + Q;
        }
        while(M.length() - Q.length() < 0)
        {
            M = "0" + M;
        }
        M = "0" + M;
        Q = "0" + Q;
    }

    static void matchAc()
    {
        while(ac.length()-Q.length()<0)
        {
            ac = "0" + ac;
        }

    }
    static String Complement(String k)
    {
        //ones
      StringBuilder temp= new StringBuilder();
      StringBuilder temp2= new StringBuilder();
      for(int i = 0;i<k.length();i++)
      {
          if(k.charAt(i)=='0')
          {              temp.append("1");
          }
          else if(k.charAt(i)=='1')
          {              temp.append("0");
          }
      }
      //twos
      for(int i = k.length()-1;i>=0;i--)
      {
          if(temp.charAt(i)=='0')
          {              temp.replace(i,i+1,"1");
              break;
          }          else if(temp.charAt(i)=='1')
          {              temp.replace(i,i+1,"0");
          }
      }
    return temp.toString();
    }

    static String addBinary(String a, String b) {
        // Initialize result
        String result = "";
        // Initialize digit sum
        int s = 0;
        int i = a.length() - 1, j = b.length() - 1;
        while (i >= 0 || j >= 0 || s == 1) {

            // Compute sum of last
            // digits and carry
            s += ((i >= 0) ? a.charAt(i) - '0' : 0);
            s += ((j >= 0) ? b.charAt(j) - '0' : 0);

            result = (char) (s % 2 + '0') + result;

            // Compute carry
            s /= 2;
            // Move to next digits
            i--;
            j--;
        }
        if(result.length()!=a.length())
        {            result = result.substring(1);
        }
        return result;
    }

        static void rightShift()
        {

            Q1 = Q0;
            Q = String.valueOf(ac.charAt(ac.length()-1)) + Q.substring(0,Q.length()-1);
            ac = ac.charAt(0) + ac.substring(0,ac.length()-1);
            Q0 = String.valueOf(Q.charAt(Q.length()-1));
        }

        static String booths()
        {

            matchAc();
            cycles = ac.length();

            for(int i = 0;i<cycles;i++)
            {
                // Case 1 = When both the given numbers are positive
                if(!ism1Negative && !ism2Negative)
                {

                    if ((Q0.equals("0") && Q1.equals("0")) || (Q0.equals("1") && Q1.equals("1"))) {
                        rightShift();
                    }
                    else if (Q0.equals("1") && Q1.equals("0")) {
                        ac = addBinary(ac, neg_M);
                        rightShift();
                    }
                    else if (Q0.equals("0") && Q1.equals("1")) {
                        ac = addBinary(ac, M);
                        rightShift();
                    }
                }

                // Case 2 = When one of the given numbers if negative
                else if((ism1Negative && !ism2Negative) || (!ism1Negative && ism2Negative))
                {
                    if ((Q0.equals("0") && Q1.equals("0")) || (Q0.equals("1") && Q1.equals("1"))) {
                        rightShift();
                    }
                    else if (Q0.equals("1") && Q1.equals("0")) {
                        ac = addBinary(ac, neg_M);
                        rightShift();
                    }
                    else if (Q0.equals("0") && Q1.equals("1")) {
                        ac = addBinary(ac, M);
                        rightShift();
                    }


                }

                // Case 3 = When both the numbers are negative:-
                else if(ism1Negative && ism2Negative)
                {
                    if ((Q0.equals("0") && Q1.equals("0")) || (Q0.equals("1") && Q1.equals("1"))) {
                        rightShift();
                    }
                    else if (Q0.equals("1") && Q1.equals("0")) {
                        ac = addBinary(ac, neg_M);
                        rightShift();
                    }
                    else if (Q0.equals("0") && Q1.equals("1")) {
                        ac = addBinary(ac, M);
                        rightShift();
                    }
                }
                System.out.println("AC = " + ac + "     Q = " + Q  + "     Q1 = " + Q1);
            }
            result = ac + Q;
            if((ism1Negative && !ism2Negative) || (!ism1Negative && ism2Negative))
            {
                result = Complement(result);
            }
            System.out.println("The Binary Result = " + result);
            return result;
        }

        public static void main(String args[] ) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the 1st decimal number :");
        int m1 = scan.nextInt();
        System.out.println("Enter the 2nd decimal number :");
        int m2 = scan.nextInt();


        if(m1<0)
        {
            ism1Negative = true; // means m1 is negative so -M will be abs m1 and M = Complement(abs(m1))

            M = Integer.toBinaryString(Math.abs(m1));

        }
        else
        {
            M = Integer.toBinaryString(m1);
        }

        if(m2<0)
        {

            Q = Integer.toBinaryString(Math.abs(m2));
            ism2Negative = true; // means that Q is negative so it will be stored in 2s complement form

        }
        else
        {
            Q = Integer.toBinaryString(m2);

        }
            matchBits(); // to match the bits of the multiplier(Q) and multiplicand(M)

            neg_M = Complement(M);

            if(ism1Negative)
            {
                neg_M = M;
                M = Complement(M);
            }
            if(ism2Negative)
            {
                Q = Complement(Q);
            }
            char c = Q.charAt(Q.length()-1);
            Q0 = String.valueOf(c);


            System.out.println("The Given Multiplicand = "+M);
            System.out.println("The Given Multiplier = " + Q);
            System.out.println();
            System.out.println("-------------------Initializing Booth's Algorithm-------------------");
            String res = booths();
            long result = Long.parseLong(res,2);
            if (!ism1Negative && !ism2Negative)
            {
                System.out.println("The Final Integer Result = " + result);
            }
            else if (ism1Negative && ism2Negative)
            {
                System.out.println("The Final Integer Result = " + result);
            }
            else if((ism1Negative && !ism2Negative) || (!ism1Negative && ism2Negative))
            {
                System.out.println("The Final Integer Result = "+"-" + result);
            }


    }
}