package com.xiangyue.image;

import java.io.Serializable;

/**
 * 一个图片对象
 * 
 * @author Administrator
 * 
 */
public class ImageItem implements Serializable {

	private static final long serialVersionUID = -7188270558443739436L;
	public String imageId;
	public String thumbnailPath;
	public String sourcePath;
	public boolean isSelected = false;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
