package o2o.shop.com.testjsoup;

import java.io.Serializable;

/**
 * Created by 6005001713 on 2018/1/4.
 */

public class TestBean implements Serializable
{
    private String title;

    public TestBean(String title, String photourl, String detail)
    {
        this.title = title;
        this.photourl = photourl;
        this.detail = detail;
    }

    public TestBean()
    {
    }

    private String photourl;
    private String detail;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPhotourl()
    {
        return photourl;
    }

    public void setPhotourl(String photourl)
    {
        this.photourl = photourl;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }
}
