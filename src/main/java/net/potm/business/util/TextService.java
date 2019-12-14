/*
 *     (C) 2019 by Zoltan Bakcsa (zoltan@bakcsa.hu)
 *     This file is part of "putonthemap".
 *
 *     putonthemap is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     putonthemap is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with putonthemap.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.potm.business.util;

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
public class TextService {
    static Logger log=Logger.getLogger(TextService.class.getName());

    private static final String DEFAULT_LANGUAGE = "en";
    public static final String DEFAULT_STRING= "???/???";


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
