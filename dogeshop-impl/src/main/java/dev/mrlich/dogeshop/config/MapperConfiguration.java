package dev.mrlich.dogeshop.config;

import dev.mrlich.dogeshop.api.model.Account;
import dev.mrlich.dogeshop.api.model.Order;
import dev.mrlich.dogeshop.api.model.Skin;
import dev.mrlich.dogeshop.entity.AccountEntity;
import dev.mrlich.dogeshop.entity.OrderEntity;
import dev.mrlich.dogeshop.entity.SkinEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public MapperFactory mapperFactory() {
        return new DefaultMapperFactory.Builder().build();
    }

    @Bean
    public MapperFacade mapperFacade(MapperFactory factory) {
        factory.classMap(Account.class, AccountEntity.class)
                .byDefault();
        factory.classMap(Skin.class, SkinEntity.class)
                .byDefault();
        factory.classMap(Order.class, OrderEntity.class)
                .byDefault();
        return factory.getMapperFacade();
    }

}
