package com.supermax.base.common.http;

import android.support.annotation.NonNull;

import com.supermax.base.common.exception.QsException;
import com.supermax.base.common.exception.QsExceptionType;
import com.supermax.base.common.utils.StreamCloseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Author yinzh
 * @Date 2019/1/26 09:46
 * @Description
 */
public class HttpResponse {
    Response response;
    HttpBuilder httpBuilder;
    private DecryptionProvider decryptionProvider;

    public HttpBuilder getBuilder() {
        return httpBuilder;
    }

    public Response getResponse() {
        return response;
    }

    public void registerDecryptionProvider(DecryptionProvider provider) {
        this.decryptionProvider = provider;
    }

    String getJsonString() {
        if (decryptionProvider != null) {
            try {
                ResponseBody body = response.body();
                if (body != null) {
                    byte[] descryptionBytes = decryptionProvider.decryption(body.bytes());
                    return new String(descryptionBytes, getChcarset(body));
                } else {
                    return null;
                }
            } catch (IOException e) {
                throw new QsException(QsExceptionType.HTTP_ERROR, httpBuilder.getRequestTag(), e.getMessage());
            } catch (Exception e) {
                throw new QsException(QsExceptionType.HTTP_ERROR, httpBuilder.getRequestTag(), e.getMessage());
            }
        } else {
            return null;
        }
    }


    private String getJsonFromBody(ResponseBody body, Object requestTag) {
        Charset chcarset = getChcarset(body);
        InputStream inputStream = body.byteStream();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, chcarset);
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                return result.toString();
            } catch (IOException e) {
                throw new QsException(QsExceptionType.UNEXPECTED, requestTag, e.getMessage());
            } catch (Exception e) {
                throw new QsException(QsExceptionType.UNEXPECTED, requestTag, e.getMessage());
            } finally {
                StreamCloseUtils.close(inputStreamReader, inputStream, bufferedReader);
            }
        }
        return getJsonFromBody(response.body(), httpBuilder.getRequestTag());
    }


    @NonNull
    private Charset getChcarset(ResponseBody body) {
        Charset charset = Charset.forName("UTF-8");
        MediaType mediaType = body.contentType();
        if (mediaType != null) {
            Charset cs = mediaType.charset(charset);
            if (cs != null) charset = cs;
        }
        return charset;
    }

    /**
     * 解密提供者
     */
    public interface DecryptionProvider {
        byte[] decryption(byte[] secretBytes);
    }
}
