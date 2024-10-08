package com.xht.cloud.framework.mongodb.utils;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.InputStream;

/**
 * 描述 ：MongoDB GridFS操作的实用类
 *
 * @author 小糊涂
 **/
@Slf4j
@RequiredArgsConstructor
public class GridFSUtils {
    /**
     * GridFS桶对象
     */
    private final GridFSBucket gridFSBucket;


    /**
     * 上传文件至MongoDB
     *
     * @param fileInputStream 文件输入流对象
     * @param filename        上传后的文件名（存到MongoDB后的文件名，不带扩展名）
     * @param type            文件原本的扩展名，不带.
     * @return 上传文件后的文件id
     */
    public ObjectId uploadFile(InputStream fileInputStream, String filename, String type) throws Exception {
        GridFSUploadOptions options = new GridFSUploadOptions();
        options.metadata(new Document("type", type));
        ObjectId getId = gridFSBucket.uploadFromStream(filename, fileInputStream, options);
        // 上传完成后关闭文件流
        fileInputStream.close();
        return getId;
    }

    /**
     * 删除文件
     *
     * @param filename 要删除的文件名（不带扩展名）
     */
    public void deleteFile(String filename) {
        GridFSFile file = findFileByMainName(filename);
        if (file == null) {
            return;
        }
        gridFSBucket.delete(file.getObjectId());
    }

    /**
     * 根据文件名查询文件对象（不带扩展名）
     *
     * @param filename 文件名（不带扩展名）
     * @return 文件对象，若文件不存在则为null
     */
    public GridFSFile findFileByMainName(String filename) {
        GridFSFile result;
        Bson query = Filters.eq("filename", filename);
        try (MongoCursor<GridFSFile> cursor = gridFSBucket.find(query).cursor()) {
            if (!cursor.hasNext()) {
                return null;
            }
            result = cursor.next();
        }
        return result;
    }

    /**
     * 根据完整文件名查询文件对象
     *
     * @param fullName 完整文件名（需要带扩展名）
     * @return 文件对象，若文件不存在则为null
     */
    public GridFSFile findFileByFullName(String fullName) {
        String mainName = FileNameUtil.mainName(fullName);
        String formatName = FileNameUtil.extName(fullName);
        if (StrUtil.isEmpty(formatName)) {
            return findFileByMainName(mainName);
        }
        GridFSFile result;
        Bson query = Filters.and(Filters.eq("filename", mainName), Filters.eq("metadata.type", formatName));
        try (MongoCursor<GridFSFile> cursor = gridFSBucket.find(query).cursor()) {
            if (!cursor.hasNext()) {
                return null;
            }
            result = cursor.next();
        }
        return result;
    }

    /**
     * 检测文件是否存在于MongoDB中
     *
     * @param filename 文件名（不带扩展名）
     * @return 文件是否存在
     */
    public boolean fileExists(String filename) {
        return findFileByMainName(filename) != null;
    }
}
