package org.zpp.gateway.component;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zpp.common.properties.ValidateCodeProperties;
import org.zpp.common.validate.code.ValidateCodeGenerator;
import org.zpp.common.validate.code.image.ImageCode;

import java.awt.image.BufferedImage;

/**
 * @author zpp
 * @date 2019/9/10 14:02
 */
@Slf4j
@Component("imageValidateCodeGenerator")
public class MyImageCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private DefaultKaptcha producer;

    @Autowired
    private ValidateCodeProperties validateCodeProperties;

    @Override
    public ImageCode generate(int width, int height) {
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);

        log.info("[覆盖默认的imageCode] - [{}]",text);
        ImageCode imageCode = new ImageCode(image,text,
                validateCodeProperties.getImage().getExpireIn());
        return imageCode;
    }
}
