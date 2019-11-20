package com.app.crawler.video.sexforum;

import com.app.crawler.pojo.CNode;
import com.app.crawler.request.RestRequest;
import com.app.crawler.video.StartDown;
import com.bobo.base.BaseClass;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @date 2019年6月29日 下午10:59:24
 * @ClassName: Forum
 */
public class Forum extends BaseClass {

    RestRequest restRequest = new RestRequest();

    public void parseAllTable(String url) {

        String content = restRequest.doGet(url);

        Document document = Jsoup.parse(content);

        Elements ts = document.select("table.fl_tb dl");
        List<CNode> list = new ArrayList<>();
        for (Element element : ts) {

            Elements as1 = element.select("a");

            if (as1 != null && as1.size() > 0) {
                Element a = as1.first();
                String href = a.attr("href");
                String text = a.text();
                CNode node = new CNode();
                node.setUrl(String.format(MainParse.DOMIAN, trimSplit(href)));
                node.setName(text);
                list.add(node);
            }

        }
        doGetSimpleUrl(list);
    }


    private void doGetSimpleUrl(List<CNode> list) {
        StringBuilder builder = new StringBuilder();
        for (CNode node : list) {
            ChineseTables hand = null;
            switch (node.getName()) {

                case "高薪 | 全職招聘區 | Full-Time Jobs":
                    break;
                case "管理招聘区 | Applicant-Initiated Recruitment":
                    break;
                case "职位竞技场 | Arena Of Managers":
                    break;
                case "炎狼门派 | Groups":
                    break;
                case "影院热播免费看 | Cinema hits free to watch":
                    break;
                case "DZ论坛BUG跟进反馈区":
                    break;
                case "VIP会员客服专区 | VIP Customers Service":
                    break;
                case "论坛公告区 | Forum":
                    break;
                case "广告洽谈区 | ADs Service":
                    break;
                case "杏吧聊天室 | Chatting Room":
                    break;
                case "勋章申领区 | Medals":
                    break;
                case "任务活动区 | Activities":
                    break;
                case "求助专区 | Helping Center":
                    break;
                case "建议专区 | Suggestion":
                    break;
                case "宣传交流区 | Experience Of Extend":
                    break;
                case "投诉专区 | Surveillance":
                    break;
                case "悔过专区 | Repentance":
                    break;
                case "新手签到区 | Novice Report":
                    break;
                case "原创视频区|Original video":
                    hand = new ChineseOnline();
                    hand.setPath("Original-video.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "原创图片区|original picture":
                    hand = new ChineseOnline();
                    hand.setPath("original-picture.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "原创录音区|Original recording":
                    hand = new ChineseOnline();
                    hand.setPath("original-recording.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "影视体验区 | Movies Preview":
                    hand = new ChineseOnline();
                    hand.setPath("original-Preview.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "VR影视体验区 | VRs Preview":
                    hand = new ChineseOnline();
                    hand.setPath("VRs-Preview.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "精美图片体验区 | Pics Preview":
                    hand = new ChineseOnline();
                    hand.setPath("Pics-Preview.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "杏吧VR虚拟现实影片区 | Virtual Reality Porn Movies":
                    hand = new ChineseOnline();
                    hand.setPath("Reality-Porn.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "破处|主播|偷拍|野战|车震区 | Specific Movies":
                    hand = new ChineseOnline();
                    hand.setPath("Specific-Movies.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "亚洲无码区 | Asian Non-mosaic Movies":
                    hand = new ChineseOnline();
                    hand.setPath("mosaic-Movies.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "亚洲有码区 | Asian Mosaic Movies":
                    hand = new ChineseOnline();
                    hand.setPath("Mosaic-Movies.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "欧美性爱区 | Western Sex Movies":
                    hand = new ChineseOnline();
                    hand.setPath("Sex-Movies.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "华人精品原创|Chinese Homemade Videos":
                    hand = new ChineseOnline();
                    hand.setPath("Homemade-Videos.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "全坛图片精品荟萃 | Globe Selfie Photos":
                    hand = new ChineseOnline();
                    hand.setPath("Globe-Photos.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "套图下载区 | Photos Download":
                    hand = new ChineseOnline();
                    hand.setPath("Photos-Download.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "VIP超级资源点播区 | VIP Bespoke Websites":
                    break;
                case "完美丽人 | The Beauties":
                    break;
                case "VIP密码点播区 | VIP Password":
                    break;
                case "破解网站专区 | Private Website":
                    break;
                case "综合讨论厅 | Discussing":
                    break;
                case "杏博足球区 | xingboFootball zone":
                    break;
                case "体育竞技区 |Sports":
                    break;
                case "杏博彩票区 | LotteryArea":
                    break;
                case "真人视讯区 | Live Video":
                    break;
                case "老虎机区 | Jackpot":
                    break;
                case "杏博挖矿 | MiNing":
                    break;
                case "白菜专区 | Cabbagezone":
                    break;
                case "S8拓展团团部 | Gamble Club":
                    break;
                case "投诉建议区 | Gamble Service":
                    break;
                case "戒赌中心 | Gambling Center":
                    break;
                case "杏吧官方主播秀场 | 大秀直播开车 | Live Cam Show":
                    break;
                case "论坛官方杂志下载 | E-magazine Download":
                    break;
                case "杂志资源发布 | Resource For Magazine":
                    break;
                case "杂志制作交流 | Fans Of Magazine":
                    break;
                case "电台声优体验区 | Seiyuu Preview":
                    break;
                case "网电互动交流区 | Fans Of Radio":
                    break;
                case "苮儿专区 | Xianer Zone":
                    break;
                case "芯嫒专区 | Xinai Zone":
                    break;
                case "小咪专区 | Xiaomi Zone":
                    break;
                case "小窈专区 | Xiaoyao Zone":
                    break;
                case "曼曼＆浅浅专区 | Qianqian Zone":
                    break;
                case "其他主播 | ALL Seiyuu Zone":
                    break;
                case "真实性爱录音区 | Erotic Recording":
                    break;
                case "有声小说在线听 | 免费":
                    break;
                case "精品下载 | Download Boutique":
                    hand = new ChineseOnline();
                    hand.setPath("Download-Boutique.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "视频连载| Serializing Videos":
                    hand = new ChineseOnline();
                    hand.setPath("Serializing-Videos.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "私人定制区 | Custom-tailor Voice":
                    break;
                case "无损音乐 | Music":
                    break;
                case "有声小说 | Audiobooks":
                    break;
                case "书友互动区 | Communication":
                    break;
                case "写手初练区 | Latest Novels":
                    break;
                case "转帖试阅区丨Probation Novels":
                    break;
                case "女优专区 | AV Idol":
                    break;
                case "小说改编悬赏区":
                    break;
                case "原创精品区 | Original Novels":
                    break;
                case "中长篇连载 | Serial Novels":
                    break;
                case "短篇故事会 | Short Novels":
                    break;
                case "网络流行小说 | Popular Novels":
                    break;
                case "18禁动漫书 | Erotic Anime":
                    break;
                case "图吧聊天活动室 | Communication":
                    break;
                case "另类同人 | Special And Homosex":
                    break;
                case "图片合成区 | Compose":
                    break;
                case "亚洲性爱 | Asian Sex":
                    hand = new ChineseOnline();
                    hand.setPath("Asian-Sex.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "欧美性爱 | Western Sex":
                    hand = new ChineseOnline();
                    hand.setPath("Western-Sex.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "亚洲裸女 | Asian Naked":
                    hand = new ChineseOnline();
                    hand.setPath("Asian-Naked.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "欧美裸女 | Western Naked":
                    hand = new ChineseOnline();
                    hand.setPath("Western-Naked.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "裸模艺术 | Inland Model":
                    hand = new ChineseOnline();
                    hand.setPath("Inland-Model.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "亚欧套图 | Pics Sets":
                    hand = new ChineseOnline();
                    hand.setPath("Pics-Sets.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "国模套图 | Model Sets":
                    hand = new ChineseOnline();
                    hand.setPath("Model-Sets.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "华人生活自拍 | Selfie Pics For Daily Life":
                    hand = new ChineseOnline();
                    hand.setPath("Daily-Life.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "华人性爱自拍 | Selfie Pics For Sex":
                    hand = new ChineseOnline();
                    hand.setPath("Pics-ForSex.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "华人街拍区 | Street Snap":
                    hand = new ChineseOnline();
                    hand.setPath("Street-Snap.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "极致诱惑 | Uniform And Breast":
                    hand = new ChineseOnline();
                    hand.setPath("Uniform-Breast.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "明星专区 | Popular Stars":
                    hand = new ChineseOnline();
                    hand.setPath("Popular-Stars.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "影吧聊天活动室 | Communication":
                    break;
                case "华人网友自拍区 | Self-Shooting Video":
                    hand = new ChineseOnline();
                    hand.setUrl("Self-Shooting.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "华人AV女优馆 | Chinese AV Actress":
                    hand = new ChineseOnline();
                    hand.setPath("Chinese-Actress.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "另类视频区 | Special Video":
                    hand = new ChineseOnline();
                    hand.setPath("Special-Video.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "亚洲无码区 | Asian Non-mosaic Video":
                    hand = new ChineseOnline();
                    hand.setPath("Asian-mosaic.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "亚洲有码区 | Asian Mosaic Video":
                    hand = new ChineseOnline();
                    hand.setPath("Asian-MosaicVideos.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "欧美性爱区 | Western Video":
                    hand = new ChineseOnline();
                    hand.setPath("Western-Video.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "卡通动漫区 | Cartoon Video":
                    hand = new ChineseOnline();
                    hand.setPath("Cartoon-Video.txt");
                    tacticsPaserDom(node, hand);
                    break;
                case "华人精品原创下载分享区 | Chinese Original Classic Videos":

                    break;
                case "无码BT原创下载分享区 | Asian Non-mosaic Original Movies":
                    break;
                case "有码BT原创下载分享区| Asian mosaic Original Movies":
                    break;
                case "欧美BT原创下载分享区 | Western Original BT":
                    break;
                case "特邀嘉宾新片区 | Honored Guest Renew":
                    break;
                case "华人性爱下载区 | Chinese sex BT":
                    break;
                case "亚洲无码下载 | Asian Non-mosaic BT":
                    break;
                case "亚洲有码下载 | Asian mosaic BT":
                    break;
                case "欧美下载 | Western BT":
                    break;
                case "动漫影视下载 | Animation BT":
                    break;
                case "高清新片影视下载 | HD New Film Download":
                    break;
                case "中文字幕转帖区 | ReprintedChinese Caption":
                    break;
                case "网盘下载区 | Disk Download":
                    hand = new DropBoxDown();
                    tacticsPaserDom(node, hand);
                    break;
                case "杏吧网盘专区 | XingYu Disk":
                    break;
                case "情爱三级在线区 | Online Erotic Vide":
                    break;
                case "情爱三级下载区 | Erotic Video Download":
                    break;
                case "寻欢分享 | Share Info":
                    break;
                case "性爱宝典 | Knowledge Of Sex":
                    break;
                case "不良曝光台 | Bad Info Reporting":
                    break;
                case "夫妻交友申请区 | Couples Entertainment":
                    break;
                case "真实认证夫妻交友信息库 | Couples Info":
                    break;
                case "真人验证 | Confirmed Info In Person":
                    break;
                case "北京市 | BeiJing":
                    break;
                case "天津市 | TianJin":
                    break;
                case "石家庄 | ShiJiaZhuang":
                    break;
                case "郑州 | ZhengZhou":
                    break;
                case "青岛 | QingDao":
                    break;
                case "济南 | JiNan":
                    break;
                case "哈尔滨 | HaErBin":
                    break;
                case "沈阳 | ShenYang":
                    break;
                case "大连 | DaLian":
                    break;
                case "长春 | ChangChun":
                    break;
                case "兰州 | LanZhou":
                    break;
                case "西安 | XiAn":
                    break;
                case "上海市 | ShangHai":
                    break;
                case "广州 | GuangZhou":
                    break;
                case "深圳 | ShenZhen":
                    break;
                case "杭州 | HangZhou":
                    break;
                case "南京 | NanJing":
                    break;
                case "合肥 | HeFei":
                    break;
                case "武汉 | WuHan":
                    break;
                case "南宁 | NanNing":
                    break;
                case "长沙 | ChangSha":
                    break;
                case "宁波 | NingBo":
                    break;
                case "厦门 | XiaMen":
                    break;
                case "苏州 | SuZhou":
                    break;
                case "福州 | FuZhou":
                    break;
                case "重庆 | ChongQing":
                    break;
                case "成都 | ChengDu":
                    break;
                case "贵阳 | GuiYang":
                    break;
                case "昆明 | KunMing":
                    break;
                case "北方省份 | North Province Of Chinese Mainland":
                    break;
                case "南方省份 | South Province Of Chinese Mainland":
                    break;
                case "西北-西南 | Western Province Of Chinese Mainland":
                    break;
                case "新闻频道 | News":
                    break;
                case "内涵段子 | Conno Tation Tribe":
                    break;
                case "游戏空间 | Game Zone":
                    break;
                case "AV档案馆 | AV Archives":
                    break;
                case "杏吧辩论室 | Debate":
                    break;
                case "资源交流区 | Resource Exchange":
                    break;
                case "电脑专区 | Computer Related":
                    break;
                case "离职管理聊天室 | Dimission Chatroom":
                    break;
                default:
                    break;

            }

        }

    }

    private void tacticsPaserDom(CNode node, StartDown hand) {
        String content = restRequest.doGet(node.getUrl());
        Document document = Jsoup.parse(content);
        Elements ts = document.select("ul.ttp.bm.cl li");

        for (Element element : ts) {
            try {
                String text = element.text();
                Element e = element.select("a").first();
                String url = e.attr("href");
                hand.setUrl(String.format(MainParse.DOMIAN, trimSplit(url)));
                hand.startMode();

            } catch (Exception e) {
                LOGGER.error("不是指定格式解析");
            }
        }
    }

}
