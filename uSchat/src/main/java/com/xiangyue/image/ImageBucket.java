package com.xiangyue.image;

import java.util.List;

/**
 * 相册对象
 * 
 */
public class ImageBucket implements Comparable<ImageBucket> {
	public int count = 0;
	public String bucketName;
	public List<ImageItem> imageList;
	public boolean selected = false;

	public ImageBucket() {
	}

	@Override
	public int compareTo(ImageBucket another) {
		if (this.count > another.count) {
			return -1;
		} else if (this.count == another.count) {
			return 0;
		} else {
			return 1;
		}
	}
}
