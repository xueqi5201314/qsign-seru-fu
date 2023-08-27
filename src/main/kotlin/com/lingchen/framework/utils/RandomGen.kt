// -*- coding: utf-8 -*-
package com.lingchen.framework.utils

import com.lingchen.framework.crypto.MD5.encrypt
import com.lingchen.models.AndroidSysVersion
import com.lingchen.models.MobileBrandInfo
import java.text.SimpleDateFormat
import java.util.*

object RandomGen {
    private val FEMALE_FIRST_NAMES = arrayOf(
        "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer", "Maria", "Susan", "Margaret", "Dorothy",
        "Lisa", "Nancy", "Karen", "Betty", "Helen", "Sandra", "Donna", "Carol", "Ruth", "Sharon",
        "Michelle", "Laura", "Sarah", "Kimberly", "Deborah", "Jessica", "Shirley", "Cynthia", "Angela", "Melissa",
        "Brenda", "Amy", "Anna", "Rebecca", "Virginia", "Kathleen", "Pamela", "Martha", "Debra", "Amanda",
        "Stephanie", "Carolyn", "Christine", "Marie", "Janet", "Catherine", "Frances", "Ann", "Joyce", "Diane",
        "Alice", "Julie", "Heather", "Teresa", "Doris", "Gloria", "Evelyn", "Jean", "Cheryl", "Mildred",
        "Katherine", "Joan", "Ashley", "Judith", "Rose", "Janice", "Kelly", "Nicole", "Judy", "Christina",
        "Kathy", "Theresa", "Beverly", "Denise", "Tammy", "Irene", "Jane", "Lori", "Rachel", "Marilyn",
        "Andrea", "Kathryn", "Louise", "Sara", "Anne", "Jacqueline", "Wanda", "Bonnie", "Julia", "Ruby",
        "Lois", "Tina", "Phyllis", "Norma", "Paula", "Diana", "Annie", "Lillian", "Emily", "Robin",
        "Peggy", "Crystal", "Gladys", "Rita", "Dawn", "Connie", "Florence", "Tracy", "Edna", "Tiffany",
        "Carmen", "Rosa", "Cindy", "Grace", "Wendy", "Victoria", "Edith", "Kim", "Sherry", "Sylvia",
        "Josephine", "Thelma", "Shannon", "Sheila", "Ethel", "Ellen", "Elaine", "Marjorie", "Carrie", "Charlotte",
        "Monica", "Esther", "Pauline", "Emma", "Juanita", "Anita", "Rhonda", "Hazel", "Amber", "Eva",
        "Debbie", "April", "Leslie", "Clara", "Lucille", "Jamie", "Joanne", "Eleanor", "Valerie", "Danielle",
        "Megan", "Alicia", "Suzanne", "Michele", "Gail", "Bertha", "Darlene", "Veronica", "Jill", "Erin",
        "Geraldine", "Lauren", "Cathy", "Joann", "Lorraine", "Lynn", "Sally", "Regina", "Erica", "Beatrice",
        "Dolores", "Bernice", "Audrey", "Yvonne", "Annette", "June", "Samantha", "Marion", "Dana", "Stacy",
        "Ana", "Renee", "Ida", "Vivian", "Roberta", "Holly", "Brittany", "Melanie", "Loretta", "Yolanda",
        "Jeanette", "Laurie", "Katie", "Kristen", "Vanessa", "Alma", "Sue", "Elsie", "Beth", "Jeanne"
    )

    //private static final String[] telStarts = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153,180,181,182,183,185,186,176,187,188,189,177,178".split(",");
    private val LAST_NAMES = arrayOf(
        "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
        "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson",
        "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King",
        "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter",
        "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins",
        "Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey",
        "Rivera", "Cooper", "Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez",
        "James", "Watson", "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross",
        "Henderson", "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington",
        "Butler", "Simmons", "Foster", "Gonzales", "Bryant", "Alexander", "Russell", "Griffin", "Diaz", "Hayes",
        "Myers", "Ford", "Hamilton", "Graham", "Sullivan", "Wallace", "Woods", "Cole", "West", "Jordan",
        "Owens", "Reynolds", "Fisher", "Ellis", "Harrison", "Gibson", "Mcdonald", "Cruz", "Marshall", "Ortiz",
        "Gomez", "Murray", "Freeman", "Wells", "Webb", "Simpson", "Stevens", "Tucker", "Porter", "Hunter",
        "Hicks", "Crawford", "Henry", "Boyd", "Mason", "Morales", "Kennedy", "Warren", "Dixon", "Ramos",
        "Reyes", "Burns", "Gordon", "Shaw", "Holmes", "Rice", "Robertson", "Hunt", "Black", "Daniels",
        "Palmer", "Mills", "Nichols", "Grant", "Knight", "Ferguson", "Rose", "Stone", "Hawkins", "Dunn",
        "Perkins", "Hudson", "Spencer", "Gardner", "Stephens", "Payne", "Pierce", "Berry", "Matthews", "Arnold",
        "Wagner", "Willis", "Ray", "Watkins", "Olson", "Carroll", "Duncan", "Snyder", "Hart", "Cunningham",
        "Bradley", "Lane", "Andrews", "Ruiz", "Harper", "Fox", "Riley", "Armstrong", "Carpenter", "Weaver",
        "Greene", "Lawrence", "Elliott", "Chavez", "Sims", "Austin", "Peters", "Kelley", "Franklin", "Lawson"
    )
    private val EMAIL_SUFFIX = arrayOf(
        "qq.com",
        "126.com",
        "163.com",
        "gmail.com",
        "163.net",
        "msn.com",
        "hotmail.com",
        "yahoo.com.cn",
        "sina.com",
        "@mail.com",
        "263.net",
        "sohu.com",
        "21cn.com",
        "sogou.com"
    )
    private val BrandList = arrayOf(
        "HTC/HTC/HTC/Desire HD",
        "samsung/samsung/msm8996/hero2qltechn",
        "HUAWEI/honor/FRD/HWFRD",
        "VIVO/VIVO/VIVO/VIVO",
        "OPPO/OPPO/OPPO/OPPO",
        "LG/LG/LG/LG",
        "GIONEE/GIONEE/GIONEE/GIONEE",
        "Nokia/Nokia/Nokia/Nokia",
        "Leshi/Leshi/LS/Leshi",
        "XIAOMI/MI/HM/hongmi"
    )
    private val DeviceNameList = arrayOf(
        "A9191,Desire HD G11,Aria,Buzz,Desire Z,EVO 4G,A510c,A6390,Chacha,Desire S,Doubleshot,Evo Design 4G,M7,New One,Butterfly,Desire C,One S SE,C620t,Edge,Golf,T329d,X920e,T528t,X920e,DROID DNA",
        "SM_G350,SM_G9350,SM_G9250,SM_G9150,SM_G9050,GALAXY S4,GALAXY S3,GALAXY S2,GALAXY S1,GALAXY Note8,GALAXY Note7,GALAXY Note6,GALAXY Note6,GALAXY Note5,GALAXY Note4,GALAXY Note3,GALAXY Note2,GALAXY Note 1,GALAXY A4,GALAXY Note 4",
        "FRD-AL00,FRD-AL10,FRD-DL00,FIG-AL00,JDN-W09,BKL-AL00,HDN-W09,BLA-AL09,BND_AL10,ALP_AL00,RNE-AL00,JMM-AL00,JMM-AL10,MOVA 2S,STF_AL10,MYA-AL10,BAH-W09,Pic_AL00,KOB_W09,BAC_AL00",
        "X9,Xplay6,X9Plus,,X8,X7,X6,Y51A,Y55,X6S,X9i,V3Max,X6Plus,X5Max,V3,X5M,X6,Y31A,Y35L,Y53,Xshot,Y35A,Y927,Y923",
        "R9s,R9,R11,R9s Plus,A57,A59s,A37,A59,A33,A53,N3,Find 9,R9 Plus,F3 Plus,A53,",
        "G6,V20,G5,G4,V10,Nexus 5X,G5 SE,Stylus 2 plus,K10,X Screen,K8,G5,G6,Stylo3,Flex 3",
        "M2017,M6 Plus,S9,F5,F103,Jingang,TJW909,S5,TJW900S,F103B,M5Plus,S6,F100,S6,S8,S7,GN152,M5,GN715",
        "Nokia6 2018,TA-1054,TA-1041,Nokia7,Nokia8,Nokia6,M6,Vertu,RM-1110,X2DS,N1,XL_4G,C1,RM-1089,RM-1090,RM-1053,RM-1042,RM-986,Lumia735,RM-1087,XL-LTE,RM-1091",
        "LePro3,Cool 1C,Cool1 dual,2,Cool S1",
        "MDE1,MEE7,MCE16,hongmi5,MCE8,MCE3B,MDE6,Note2,5S,Note 4X,Max2,hongmi5"
    )
    private val _random = Random()
    @JvmStatic
    fun GenWifiName(seed: Long): String {
        val str2 = "ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray()
        val random = Random(seed)
        val bit = random.nextInt(3) + 2
        val sb = StringBuilder()
        for (i in 0 until bit) {
            sb.append(str2[random.nextInt(str2.size)])
        }
        return "TP-Link_$sb"
    }

    fun GenComputeName(seed: Long): String {
        return Base64.getEncoder().encodeToString(Util.hexString2Buf(encrypt(seed.toString() + "ComputeName")))
            .substring(0, 8)
    }

    fun GenDiskDriveName(seed: Long): String {
        val brands = arrayOf("SEAGATE", "WESTERN DIGITAL", "sumsung", "HITACHI", "TOSHIBA", "kingston")
        return brands[Random(
            seed.toInt().toLong()
        ).nextInt(brands.size)] + GenComputeName(seed) + "128GVN8 M.2 2280 128GB"
    }

    fun GenSimSerialNumber(mobile: Long): String {
        var mobile = mobile
        val sMobile = mobile.toString()
        if (mobile <= 0 || mobile.toString().length < 11) {
            mobile = GenMobile(mobile)
        }
        val dic = HashMap<Int, String>()
        val sb = StringBuilder()
        sb.append(89)
        sb.append(86)
        val sims = arrayOf("00", "01", "03")
        val simType = sims[Random(mobile.toInt().toLong()).nextInt(3)]
        sb.append(simType)
        val random = Random(mobile.toInt().toLong())
        when (simType) {
            "00" -> {
                dic[159] = "0"
                dic[158] = "1"
                dic[150] = "2"
                dic[151] = "3"
                dic[134] = "4"
                dic[135] = "5"
                dic[136] = "6"
                dic[137] = "7"
                dic[138] = "8"
                dic[139] = "9"
                dic[157] = "A"
                dic[188] = "B"
                dic[152] = "C"
                dic[147] = "D"
                dic[187] = "E"
                val key = sMobile.substring(0, 3).toInt()
                sb.append(dic.getOrDefault(key, "0"))
                sb.append(sMobile, 3, 1)
                sb.append(Util.padLeft(random.nextInt(31).toString(), 2, '0'))
                sb.append(Util.padLeft(random.nextInt(18).toString(), 2, '0'))
                sb.append(Util.padLeft(random.nextInt(9).toString(), 2, '0'))
                sb.append(sMobile, 4, 6)
                sb.append(random.nextInt(9))
            }

            "01" -> {
                sb.append(Util.padLeft(random.nextInt(18).toString(), 2, '0'))
                sb.append(8)
                val provinces = intArrayOf(
                    10, 11, 13, 17, 18, 19, 30, 31, 34, 36,
                    38, 50, 51, 59, 70, 71, 34, 36, 38, 50,
                    51, 59, 70, 71, 74, 75, 76, 79, 81, 83,
                    84, 85, 86, 87, 88, 89, 90, 91, 97
                )
                sb.append(provinces[random.nextInt(provinces.size)])
                sb.append(sMobile, 2, 8)
                sb.append(random.nextInt(9))
            }

            "02" -> {
                sb.append(Util.padLeft(random.nextInt(18).toString(), 2, '0'))
                sb.append(random.nextInt(2))
                sb.append("0")
                sb.append(Util.padLeft(random.nextInt(790).toString(), 3, '0'))
                sb.append(sMobile, 3, 7)
            }

            else -> {
                sb.append(sMobile, 0, 11).append("0000000")
            }
        }
        return sb.toString()
    }

    @JvmStatic
    fun GenMobile(seed: Long): Long {
        val mobile: String
        val sSeed = seed.toString()
        val aaa = arrayOf("159", "158", "150", "151", "134", "135", "136", "137", "138", "139")
        val head = aaa[Random(seed).nextInt(aaa.size)]
        mobile = if (sSeed.length <= 8) {
            head + Util.padLeft(sSeed, 8, '5')
        } else {
            head + sSeed.substring(0, 8)
        }
        return mobile.toLong()
    }

    @JvmStatic
    fun GenAndroidSysVersion(seed: Long): AndroidSysVersion {
        val AndroidSysVersion = AndroidSysVersion()
        val androids = HashMap<Int?, String>()
        androids[21] = "5.0"
        androids[22] = "5.1"
        androids[23] = "6.0.1"
        androids[24] = "7.0"
        androids[25] = "7.1.1"
        androids[26] = "8.0"
        androids[27] = "8.1"
        androids[28] = "9.0"
        androids[29] = "10.0"
        androids[30] = "11.0"
        androids[31] = "12.0"
        androids[32] = "12.1"
        androids[33] = "13.0"
        androids[34] = "14.0"
        val index = Random(seed.toInt().toLong()).nextInt(androids.size)
        val list: ArrayList<*> = ArrayList<Any?>(androids.keys)
        AndroidSysVersion.AndroidLevel = list[index] as Int
        AndroidSysVersion.AndroidVersion = androids[AndroidSysVersion.AndroidLevel]
        return AndroidSysVersion
    }

    @JvmStatic
    fun GenImsi(seed: Long): String {
        return "4600" + Util.padLeft(seed.toString(), 11, '8')
    }

    @JvmStatic
    fun GenICCID(mobile: Long): String {
        var mobile = mobile
        var sMobile = mobile.toString()
        if (sMobile.length < 11) {
            mobile = GenMobile(mobile)
        }
        sMobile = mobile.toString()
        val sb = StringBuilder()
        sb.append("8986")
        sb.append("00")
        val pre3 = sMobile.substring(0, 3)
        var numberArea: String? = null
        val numberAreas = arrayOf(
            "159", "158", "150", "151", "134", "135", "136", "137", "138", "139"
        )
        for (i in numberAreas.indices) {
            val ad = numberAreas[i]
            if (ad == pre3) {
                numberArea = i.toString()
                break
            }
        }
        sb.append(numberArea)
        val simpleDateFormat = SimpleDateFormat("yyyy")
        val time = simpleDateFormat.format(Date())
        sb.append("01")
        sb.append(time, 2, 4)
        sb.append("0")
        sb.append(sMobile, 4, 10)
        val chkSum = LuhnSum(sb.toString())
        sb.append(chkSum)
        return sb.toString()
    }

    private fun LuhnSum(InVal: String): Int {
        var IsOdd = true
        var evenSum = 0
        var oddSum = 0
        val strLen = InVal.length
        for (i in strLen downTo 1) {
            var digit = InVal.substring(i - 1, i).toInt()
            if (IsOdd) {
                oddSum += digit
                IsOdd = false
            } else {
                digit *= 2
                if (digit > 9) {
                    digit -= 9
                }
                evenSum += digit
                IsOdd = true
            }
        }
        return oddSum + evenSum
    }

    @JvmStatic
    fun GenMobileBrand(seed: Long): MobileBrandInfo {
        val mobileBrandInfo = MobileBrandInfo()
        mobileBrandInfo.manufacturer = "HUAWEI"
        mobileBrandInfo.model = "FRD-AL00"
        mobileBrandInfo.board = "FRD"
        val s1 = seed.toString()
        val index = s1.substring(s1.length - 1).toInt()
        val str = BrandList[index]
        val ss3 = str.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        mobileBrandInfo.manufacturer = ss3[0]
        mobileBrandInfo.brand = ss3[1]
        mobileBrandInfo.board = ss3[2]
        mobileBrandInfo.deviceName = ss3[3]
        val ss = DeviceNameList[index].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (seed % ss.size <= ss.size) {
            mobileBrandInfo.model = ss[(seed % ss.size).toInt()]
        }
        return mobileBrandInfo
    }

    @JvmStatic
    fun GenImei(seed: Long): String {
        val imei = StringBuilder()
        try {
            imei.append("86") //
            imei.append(seed.toString(), 0, 4)
            imei.append("02")
            val ad = (Random(seed.toInt().toLong()).nextInt(899999) + 100000).toString()
            imei.append(ad)
            val deviceIdPre = imei.toString()
            val imeiChar = deviceIdPre.toCharArray()
            var checksum = 0
            var i: Int
            i = 0
            while (i < imeiChar.size) {
                val a = Character.toString(imeiChar[i]).toInt()
                i++
                val temp = Character.toString(imeiChar[i]).toInt() * 2
                val b = if (temp < 10) temp else temp - 9
                checksum += a + b
                i++
            }
            checksum %= 10
            checksum = if (checksum != 0) 10 - checksum else 0
            imei.append(checksum)
        } catch (ex: Exception) {
            //LogHelper.WriteInTxt("错误日志", ex.toString());
        }
        return imei.toString()
    }

    private fun NextValue(
        array: Array<Any>
    ): Any {
        return array[_random.nextInt(array.size)]
    }

    fun GenEnglishName(seed: Long): String {
        return GenEnglishFirstName(seed) + " " + GenEnglishLastName(seed)
    }

    fun GenEnglishFirstName(seed: Long): String {
        return FEMALE_FIRST_NAMES[Random(
            seed.toInt().toLong()
        ).nextInt(FEMALE_FIRST_NAMES.size)]
    }

    fun GenEnglishLastName(seed: Long): String {
        return LAST_NAMES[Random(seed.toInt().toLong()).nextInt(LAST_NAMES.size)]
    }

    fun GenEmailAddress(seed: Long): String {
        return GenEnglishFirstName(seed) + GenEnglishLastName(seed) + "@" + NextValue(arrayOf(EMAIL_SUFFIX))
    }

    @JvmStatic
    fun GenAndroidId(seed: Long): String {
        if (seed > 0) {
            val id = encrypt(seed.toString())
            return "a" + id.substring(17).lowercase(Locale.getDefault())
        }
        val _8Bytes = ByteArray(8)
        _random.nextBytes(_8Bytes)
        return Util.buf2hexString(_8Bytes).lowercase(Locale.getDefault())
    }

    fun GenerateChineseWords(count: Int): String {
        val str = StringBuilder()
        try {
            //Encoding encoding = Encoding.GetEncoding("gb2312");
            for (i in 0 until count) {
                val num2 = _random.nextInt(40) + 16
                val num3 = if (num2 != 55) _random.nextInt(94) + 1 else _random.nextInt(89) + 1
                val num4 = num2 + 160
                val num5 = num3 + 160
                val bytes = byteArrayOf(num4.toByte(), num5.toByte())
                str.append(String(bytes))
            }
        } catch (exception: Exception) {
        }
        return str.toString()
    }

    fun GenerateLevel1ChineseWords(count: Int): String {
        val str =
            "的一是了我不人在他有这个上们来到时大地为子中你说生国年着就那和要她出也得里后自以会家可下而过天去能对小多然于心学么之都好看起发当没成只如事把还用第样道想作种开见明问力理尔点文几定本公特做外孩相西果走将月十实向声车全信重三机工物气每并别真打太新比才便夫再书部水像眼等体却加电主界门利海受听表德少克代员许稜先口由死安写性马光白或住难望教命花结乐色更拉东神记处让母父应直字场平报友关放至张认接告入笑内英军候民岁往何度山觉路带万男边风解叫任金快原吃妈变通师立象数四失满战远格士音轻目条呢病始达深完今提求清王化空业思切怎非找片罗钱紶吗语元喜曾离飞科言干流欢约各即指合反题必该论交终林请医晚制球决窢传画保读运及则房早院量苦火布品近坐产答星精视五连司巴奇管类未朋且婚台夜青北队久乎越观落尽形影红爸百令周吧识步希亚术留市半热送兴造谈容极随演收首根讲整式取照办强石古华諣拿计您装似足双妻尼转诉米称丽客南领节衣站黑刻统断福城故历惊脸选包紧争另建维绝树系伤示愿持千史谁准联妇纪基买志静阿诗独复痛消社算算义竟确酒需单治卡幸兰念举仅钟怕共毛句息功官待究跟穿室易游程号居考突皮哪费倒价图具刚脑永歌响商礼细专黄块脚味灵改据般破引食仍存众注笔甚某沉血备习校默务土微娘须试怀料调广蜖苏显赛查密议底列富梦错座参八除跑亮假印设线温虽掉京初养香停际致阳纸李纳验助激够严证帝饭忘趣支春集丈木研班普导顿睡展跳获艺六波察群皇段急庭创区奥器谢弟店否害草排背止组州朝封睛板角况曲馆育忙质河续哥呼若推境遇雨标姐充围案伦护冷警贝著雪索剧啊船险烟依斗值帮汉慢佛肯闻唱沙局伯族低玩资屋击速顾泪洲团圣旁堂兵七露园牛哭旅街劳型烈姑陈莫鱼异抱宝权鲁简态级票怪寻杀律胜份汽右洋范床舞秘午登楼贵吸责例追较职属渐左录丝牙党继托赶章智冲叶胡吉卖坚喝肉遗救修松临藏担戏善卫药悲敢靠伊村戴词森耳差短祖云规窗散迷油旧适乡架恩投弹铁博雷府压超负勒杂醒洗采毫嘴毕九冰既状乱景席珍童顶派素脱农疑练野按犯拍征坏骨余承置臓彩灯巨琴免环姆暗换技翻束增忍餐洛塞缺忆判欧层付阵玛批岛项狗休懂武革良恶恋委拥娜妙探呀营退摇弄桌熟诺宣银势奖宫忽套康供优课鸟喊降夏困刘罪亡鞋健模败伴守挥鲜财孤枪禁恐伙杰迹妹藸遍盖副坦牌江顺秋萨菜划授归浪听凡预奶雄升碃编典袋莱含盛济蒙棋端腿招释介烧误".toCharArray()
        val str2 = StringBuilder()
        for (i in 0 until count) {
            str2.append(str[_random.nextInt(str.size - 1 - _random.nextInt(str.size / 2) + _random.nextInt(str.size / 2))])
        }
        return str2.toString()
    }

    fun GetRandomChar(): Char {
        val random = Random()
        var num = random.nextInt(122)
        while (num < 48 || num > 57 && num < 65 || num > 90 && num < 97) {
            num = random.nextInt(122)
        }
        return num.toChar()
    }

    fun GetRandomChineseName(pvNameLength: Int): String {
        return try {
            val str2 = StringBuilder()
            val surnames =
                "王陈张李刘周吴杨黄徐胡朱沈潘程郑方孙章马汪叶金何林姚赵蒋丁俞许高董谢施曹蔡余邱江姜戴洪郭汤袁卢宋罗钱傅肖顾彭倪唐雷梁盛龚童杜邵范吕严陆夏梅曾储陶熊韩鲍万邓郎诸楼毛华项应葛卞冯阮费崔裘邹喻涂任凌康魏于孔苏季祝薛石田单孟尚钟柳宣胥娄翁游虞饶邬甘向查莫贾殷谈管尉秦郝温舒裴韦史伍庄竺易段笪骆鲁詹褚赖黎臧廖麻缪操谭卜兰巴尹开水平厉求成包安白乔有来谷阎欧柯柏桂颜戚沃蓝霍刁卫干丰艾车牛文官练乐池宇申邢匡牟毕茅束闵卓宓密冒芮劳阙沙迟印明郏相柴钮耿宿符郦琚简路鄂阚滕钦时濮窦於席屠聂闻过穆".toCharArray()
            val names =
                "华红琴芳明国丽萍海文平晓月伟春建金荣美爱云志霞燕玉玲林新秀英凤亚小良军飞忠永成雪卫珍强梅芬民兰根龙峰敏德旭勇生学水娟莲兴青慧富世斌艳庆贵安刚祥胜菊清义东宏莉彩宝利锋桂江群福喜银秋立婷佳珠友香炳星兵丹贤娣娥法有仁洪双惠家元花中和冬正波来光亮继培长昌吉顺蓉晶彬鸣一虹益君鑫辉淑礼叶武开天宇雅乐方勤山康苗琳仙涛盛连高松才孝锡娜阳杰向少先振传泽芸加定泉为超丰莹智巧琼欢坤如娇瑛发汉维迎代芝静意锦鹏显珊素炎能远".toCharArray()
            val random = Random(UUID.randomUUID().hashCode().toLong())
            val surName = surnames[random.nextInt(surnames.size - 1) + 1].toString()
            for (i in 0 until pvNameLength - surName.length) {
                var j = names[random.nextInt(names.size - 1) + 1]
                if (surName.contains(j.toString())) {
                    j = names[random.nextInt(names.size - 1) + 1]
                }
                str2.append(j)
            }
            surName + str2
        } catch (exception: Exception) {
            ""
        }
    }

    fun GetRandomInt(pvMinValue: Int, pvMaxValue: Int): String {
        return (_random.nextInt(pvMaxValue - pvMinValue) + pvMinValue).toString()
    }

    fun GenNumber(bit: Int): String {
        val sb = StringBuilder()
        for (i in 0 until bit) {
            val a = _random.nextInt(9)
            sb.append(a)
        }
        return sb.toString()
    }

    fun GetRandomMobile(): String {
        val strArray = "137;138;139;136;135;133;131;132;130;151;152;153;156;158;159;188;187;189".split(";".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val length = strArray.size
        val index = _random.nextInt(length - 1) + 1
        val str2 = strArray[index]
        val str3 = StringBuilder(_random.nextInt(9).toString())
        var num3 = 8
        val num4 = 1
        while (num4 < num3) {
            str3.append(_random.nextInt(9))
            num3--
        }
        return str2 + str3
    }

    fun GetRandomString(length: Int): String {
        return GetRandomWord(length)
    }

    fun GetRandomString(
        seed: Long, length: Int, useNum: Boolean, useLow: Boolean,
        useUpp: Boolean, useSpe: Boolean, custom: String
    ): String {
        val random = Random(seed)
        val str: StringBuilder? = null
        var str2 = custom
        if (useNum) {
            str2 += "0123456789"
        }
        if (useLow) {
            str2 += "abcdefghijkmnpqrstuvwxyz"
        }
        if (useUpp) {
            str2 += "ABCDEFGHIJKLMNPQRSTUVWXYZ"
        }
        if (useSpe) {
            str2 += "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"
        }
        for (i in 0 until length) {
            val startIndex = random.nextInt(str2.length - 1)
            str!!.append(str2[startIndex])
        }
        return str.toString()
    }

    fun GetRandomWord(randCharCount: Int): String {
        var randCharCount = randCharCount
        val str2 = "abcdefghijklmnopqrstuvwxyz".toCharArray()
        val random = Random(UUID.randomUUID().hashCode().toLong())
        val str = StringBuilder(str2[random.nextInt(str2.size - 1) + 1].toString())
        val num = 1
        while (num < randCharCount) {
            str.append(str2[random.nextInt(str2.size - 1) + 1])
            randCharCount--
        }
        return str.toString()
    }

    @JvmStatic
    fun GenBssid(seed: Long): String {
        val da = ByteArray(6)
        Random(seed.toInt().toLong()).nextBytes(da)
        da[5]++
        val das = arrayOfNulls<String>(da.size)
        for (i in da.indices) {
            das[i] = String.format("%02x", da[i])
        }
        return java.lang.String.join(":", *das)
    }

    @JvmStatic
    fun GenMacAddress(seed: Long): String {
        val da: ByteArray?
        da = Util.hexString2Buf(encrypt(seed.toString()).substring(0, 12))
        val das = arrayOfNulls<String>(da.size)
        for (i in da.indices) {
            das[i] = String.format("%02x", da[i])
        }
        return java.lang.String.join(":", *das)
    }

    private fun GetGb2312String(): String {
        val list = ArrayList<Byte>()
        for (i in 16..55) {
            val j = if (i == 55) 89 else 94
            for (k in 1..j) {
                list.add((i + 160).toByte())
                list.add((k + 160).toByte())
            }
        }
        return Arrays.toString(list.toTypedArray())
    }
}