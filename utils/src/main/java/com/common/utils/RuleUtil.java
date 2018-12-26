package com.common.utils;

import android.text.TextUtils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuyq on 14-8-5.
 */
public class RuleUtil {
    public static final String rule_id_card_no = "identity_id";
    public static final String rule_phone = "verify_phone";
    public static final String rule_string = "string";
    public static final String rule_num = "verify_num";
    public static final String rule_decimal = "decimal";
    public static final String rule_email = "email";
    public static final String rule_3_digits = "verify_3digits";
    public static final String rule_4_digits = "verify_4digits";

    private static Pattern p_phone_number = Pattern.compile("^1([0123456789])\\d{9}$");
    private static Pattern p_6_num = Pattern.compile("[0-9]{6}");
    private static Pattern p_decimal = Pattern.compile("[0-9]*\\.?[0-9]{0,2}");
    private static Pattern p_we_chat = Pattern.compile("[\\w-]+");
    private static Pattern p_email = Pattern.compile("^\\S+@\\S+\\.\\S+$");
    private static Pattern p_3_digits = Pattern.compile("[0-9]{3}");
    private static Pattern p_4_digits = Pattern.compile("[0-9]{4}");
    private static Pattern p_fixed_line = Pattern.compile("^0[0-9]{2,3}-[0-9]\\d{5,7}$");//固话
    private static Pattern p_bank_card = Pattern.compile("[0-9]{4}");

//    private static Pattern p_name = Pattern.compile("[/\\:*?<>|\"\n\t]");//验证字符，删除标点符号，目前中文的标点符号匹配不了
    private static Pattern p_name = Pattern.compile("[\u4E00-\u9FA5]{1,13}(?:·[\u4E00-\u9FA5]{1,13})*");

    private static String str_relation = "爷奶爸妈姐弟舅嫂叔父母侄姨婶孙甥亲婆伯姑妯娌姊妹妻娘爹啊儿女";

    public static boolean verifyBankCardNum(String num){
        return !(num == null || num.trim().length() < 16);
    }
    public static boolean isIpv4(String ipAddress) {

        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();

    }
    /**
     * 校验姓名中是否有诸如亲属关系的字段
     * 有则验证不通过，返回false
     * 没有则通过，返回true
     * @param name
     * @return
     */
    public static boolean verifyRelation(String name){
        if(name == null || name.trim().length() == 0){
            return false;
        }
        for(int i=0; i<str_relation.length(); i++){
            if(name.contains(str_relation.subSequence(i, i+1))){
                return false;
            }
        }
        return true;
    }

    public static boolean verifyName(String name, int limitLength) {
        if(name == null){
            return false;
        }

        String temp = name.replace(" ", "");
        if( temp.length() == 0 || temp.length() < limitLength){
            return false;
        }

        Matcher m = p_name.matcher(temp);
        if(!m.matches()){
            return false;
        }

//        char[] namechar = temp.toCharArray();
//        for (int i = 0; i < namechar.length; i++) {
//            if (!isChinese(namechar[i])) {
//                return false;
//            }
//        }

        return true;
    }
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }
    /**
     * 验证手机号码
     * @param phone
     * @return
     */
    public static boolean verifyPhone(String phone) {
        if (phone == null || phone.trim().length() == 0)
            return false;
        //测试号段
        Matcher m = p_phone_number.matcher(phone.replace(" ", ""));
        return m.matches();
    }

    //校验固定电话号码
    public static boolean verigyFixedLine(String number){
        if (number == null || number.trim().length() == 0) return false;
        Matcher matcher = p_fixed_line.matcher(number);
        return matcher.matches();
    }

    public static boolean verifyNum(String num) {
        Matcher matcher = p_6_num.matcher(num);
        return matcher.matches();
    }

    public static boolean verifyDecimal(String decimal) {
        if (decimal == null || decimal.trim().length() == 0) return false;
        Matcher matcher = p_decimal.matcher(decimal);
        return matcher.matches();
    }

    public static boolean verifyWeChat(String weChat) {
        if (weChat == null || weChat.trim().length() == 0) return false;
        Matcher matcher = p_we_chat.matcher(weChat);
        return matcher.matches();
    }

    public static boolean verifyEmail(String email) {
        if (email == null || email.trim().length() == 0) return false;
        Matcher matcher = p_email.matcher(email);
        return matcher.matches();
    }

    public static boolean verify3Digits(String num) {
        Matcher matcher = p_3_digits.matcher(num);
        return matcher.matches();
    }

    public static boolean verify4Digits(String num) {
        Matcher matcher = p_4_digits.matcher(num);
        return matcher.matches();
    }

    /**
     * ddddddyyyymmddxxsp共18位，其中：
     * yyyy为4位的年份代码，是身份证持有人的出身年份。
     * mm为2位的月份代码，是身份证持有人的出身月份。
     * dd为2位的日期代码，是身份证持有人的出身日。
     * 这8位在一起组成了身份证持有人的出生日期。
     * xx为2位的顺序码，这个是随机数。
     * s为1位的性别代码，奇数代表男性，偶数代表女性。
     * 最后一位为校验位。
     * 校验规则是：
     * （1）十七位数字本体码加权求和公式
     * S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
     * Ai:表示第i位置上的身份证号码数字值
     * Wi:表示第i位置上的加权因子
     * Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * （2）计算模
     * Y = mod(S, 11)
     * （3）通过模得到对应的校验码
     * Y: 0 1 2 3 4 5 6 7 8 9 10
     * 校验码: 1 0 X 9 8 7 6 5 4 3 2
     * 也就是说，如果得到余数为1则最后的校验位p应该为对应的0.如果校验位不是，则该身份证号码不正确。
     *
     * @param identity
     * @return
     */
    public static boolean verifyIDCardNo(String identity) {
        if (identity == null || identity.trim().length() == 0) {
            return false;
        }
        if (identity.length() != 18) {
            return false;
        }
        //地区号，不做具体检查，只检查是数字
        String dddddd = identity.substring(0, 6);
        if (!verifyNum(dddddd)) {
            return false;
        }
        //生日日期部分，检查是否是有效的日期，
        String birthday = identity.substring(6, 14);
        if (!verifyDecimal(birthday)) {
            return false;
        }
        if (!verifyDate(birthday)) {
            return false;
        }
        //xx两位顺序码，随机，不验证
        //s为性别，不验证
        //p, 校验码
        char p = identity.charAt(17);
        p = Character.toUpperCase(p);
        char q = expectVerifyCode(identity);
        return p == q;
    }

    private static final int[] verify_weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final char[] verify_code = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    private static char expectVerifyCode(String identity) {
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (identity.charAt(i) - '0') * verify_weight[i];
        }
        return verify_code[sum % 11];
    }

    /**
     * 验证是否是有效的日期
     *
     * @param date
     * @return
     */
    private static boolean verifyDate(String date) {
        int year = Integer.valueOf(date.substring(0, 4));
        int month = Integer.valueOf(date.substring(4, 6));
        if (month <= 0 || month > 13) {
            return false;
        }
        int day = Integer.valueOf(date.substring(6));
        if (day <= 0 || day >= 32) {
            return false;
        }
        //所在月的1号
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        //获取这个月的第一天和最后一天，确定日期是否有效
        int max = calendar.getActualMaximum(Calendar.DATE);
        return day <= max;
    }

    public static boolean verify(String rule, String str) {
        if (rule_string.equals(rule)) {
            return !TextUtils.isEmpty(str);
        }
        if (rule_num.equals(rule)) {
            return verifyNum(str);
        }
        if (rule_email.equals(rule)) {
            return verifyEmail(str);
        }
        if (rule_decimal.equals(rule)) {
            return verifyDecimal(str);
        }
        if (rule_phone.equals(rule)) {
            str = str.replaceAll(" ", "");
            return verifyPhone(str);
        }
        if (rule_id_card_no.equals(rule)) {
            return verifyIDCardNo(str);
        }
        if (rule_3_digits.equals(rule)) {
            return verify3Digits(str);
        }
        if (rule_4_digits.equals(rule)) {
            return verify4Digits(str);
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        String test = "610302199601260021";
        System.out.println(expectVerifyCode(test));
    }



}