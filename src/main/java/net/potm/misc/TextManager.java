package net.potm.misc;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


@ApplicationScoped
public class TextManager {

    private static final String DEFAULT_LANGUAGE = "en";
    public static final String DEFAULT_STRING="???/???";
    @Inject
    Logger log;

    @Inject
    net.potm.persistence.service.TextService ts;

    private LoadingCache<CacheKey, String> cache;

    @PostConstruct
    public void init() {
        CacheLoader<CacheKey, String> loader;
        loader = new CacheLoader<>() {
            @Override
            public String load(CacheKey key) {
                try {
                    return ts.getTextByKey(key.getKey(), key.getLanguage()).getText();
                } catch (Exception e) {
                    log.log(Level.WARNING, String.format("Could not fetch text. Key: %s; Language: %s", key.getKey(), key.getLanguage()),e);
                    return DEFAULT_STRING;
                }
            }
        };

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(15, TimeUnit.MINUTES)
                .build(loader);

        log.info("Language service init done");
    }


    public String getText(String key, String language) {
    	String text=null;
    	try{
			text = cache.get(new CacheKey(key, language));
			if (text.equals(DEFAULT_STRING)) {
				text = cache.get(new CacheKey(key, DEFAULT_LANGUAGE));
			}
		}catch (ExecutionException e){
			log.log(Level.SEVERE,"Could not fetch text",e);
		}

        return text != null ? text : DEFAULT_STRING;
    }

    private static class CacheKey {
        private String key;
        private String language;

        public CacheKey(String key, String language) {
            this.key = key;
            this.language = language;
        }

        public String getKey() {
            return key;
        }

        public String getLanguage() {
            return language;
        }

        public String toString(){
        	return String.format("Key: %s Language: %s",key,language);
		}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return key.equals(cacheKey.key) &&
                    language.equals(cacheKey.language);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, language);
        }
    }
}
