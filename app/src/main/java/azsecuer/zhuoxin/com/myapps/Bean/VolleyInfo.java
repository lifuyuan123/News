package azsecuer.zhuoxin.com.myapps.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class VolleyInfo {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-09-08 10:56","title":"四川患者抗生素过敏死亡 病历中现伪造签名","description":"网易社会","picUrl":"http://s.cimg.163.com/catchpic/8/8D/8D8876FA4AF9B1534CE2273BD49296EE.jpg.119x83.jpg","url":"http://news.163.com/16/0908/10/C0EG06CM00011229.html#f=slist"}]
     */

    private int code;
    private String msg;
    /**
     * ctime : 2016-09-08 10:56
     * title : 四川患者抗生素过敏死亡 病历中现伪造签名
     * description : 网易社会
     * picUrl : http://s.cimg.163.com/catchpic/8/8D/8D8876FA4AF9B1534CE2273BD49296EE.jpg.119x83.jpg
     * url : http://news.163.com/16/0908/10/C0EG06CM00011229.html#f=slist
     */

    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "NewslistBean{" +
                    "ctime='" + ctime + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VolleyInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", newslist=" + newslist +
                '}';
    }
}
