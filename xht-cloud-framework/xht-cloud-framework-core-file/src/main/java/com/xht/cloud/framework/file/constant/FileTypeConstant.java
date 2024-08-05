package com.xht.cloud.framework.file.constant;

import com.xht.cloud.framework.file.domain.FileType;

/**
 * 描述 ：文件类型常量
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unused")
public interface FileTypeConstant {
    FileType DEFAULT_FILETYPE = new FileType("", "application/octet-stream", "通用的 MIME 类型");

    FileType TXT = new FileType("txt", "text/plain", "文本文件");

    FileType DOC = new FileType("doc", "application/msword", "Microsoft Word文档（.doc）");

    FileType DOCX = new FileType("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Microsoft Word文档（.docx）");

    FileType PDF = new FileType("pdf", "application/pdf", "Adobe PDF文档");

    FileType XLS = new FileType("xls", "application/vnd.ms-excel", "Microsoft Excel工作簿（.xls）");

    FileType XLSX = new FileType("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Microsoft Excel工作簿（.xlsx）");

    FileType EXCEL = new FileType("xls", "application/vnd.ms-excel", "Microsoft Excel工作簿（非标准扩展名）");

    FileType MP3 = new FileType("mp3", "audio/mpeg", "MP3音频");

    FileType WAV = new FileType("wav", "audio/wav", "WAV音频");

    FileType MP4 = new FileType("mp4", "video/mp4", "MP4视频");

    FileType AVI = new FileType("avi", "video/x-msvideo", "AVI视频");

    FileType ZIP = new FileType("zip", "application/zip", "ZIP压缩文件");

    FileType CSV = new FileType("csv", "text/csv", "CSV数据文件");

    FileType XML = new FileType("xml", "application/xml", "XML数据文件");

    FileType JSON = new FileType("json", "application/json", "JSON数据文件");

    FileType HTML = new FileType("html", "text/html", "HTML网页文件");

    FileType CSS = new FileType("css", "text/css", "CSS样式表");

    FileType JS = new FileType("js", "application/javascript", "JavaScript脚本");

    FileType PY = new FileType("py", "text/x-python", "Python脚本");

    FileType JAVA = new FileType("java", "text/x-java-source", "Java源代码");

    FileType MD = new FileType("md", "text/markdown", "Markdown文档");

    FileType EPUB = new FileType("epub", "application/epub+zip", "EPUB电子书");

    FileType SVG = new FileType("svg", "image/svg+xml", "SVG矢量图像");

    FileType JPG = new FileType("jpg", "image/jpeg", "JPEG图像");

    FileType PNG = new FileType("png", "image/png", "PNG图像");

    FileType GIF = new FileType("gif", "image/gif", "GIF图像");

}
