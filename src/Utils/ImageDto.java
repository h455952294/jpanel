package Utils;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class ImageDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer width;
    private Integer height;
    private List<File> listFile;
    public ImageDto() {
    }
    public ImageDto(Integer width, Integer height, List<File> listFile) {
        this.width = width;
        this.height = height;
        this.listFile = listFile;
    }
    public Integer getWidth() {
        return width;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }
    public Integer getHeight() {
        return height;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public List<File> getListFile() {
        return listFile;
    }
    public void setListFile(List<File> listFile) {
        this.listFile = listFile;
    }
}  