package ru.kpfu.itis.adboardrework.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/images/uploads/**")
                .addResourceLocations("file:C:\\Users\\79174\\IdeaProjects\\AdBoardRework\\src\\main\\resources\\static\\images\\uploads\\advert\\")
                .addResourceLocations("file:C:\\Users\\79174\\IdeaProjects\\AdBoardRework\\src\\main\\resources\\static\\images\\uploads\\avatar\\")
                .setCachePeriod(0);
    }
}
