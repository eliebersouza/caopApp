package br.mp.mpro.caopapp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("oAuth2Authentication", jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.Form.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.Form.class.getName() + ".servicoPublicos", jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.Form.class.getName() + ".espacoLazers", jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.RendaFamiliar.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.FaixaEtaria.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.FaixaEtariaForm.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.CicloEscolar.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.CicloEscolarForm.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.ResidenteForaDaEscola.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.MotivoEvasao.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.ResponsavelLar.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.Religiao.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.ServicoPublico.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.EspacoLazer.class.getName(), jcacheConfiguration);
            cm.createCache(br.mp.mpro.caopapp.domain.Periodicidade.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
