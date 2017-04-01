package com.xiangyue.type;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Info extends BmobObject {
	private BmobFile headImage;

	public BmobFile getHeadImage() {
		return headImage;
	}

	public void setHeadImage(BmobFile headImage) {
		this.headImage = headImage;
	}

}
