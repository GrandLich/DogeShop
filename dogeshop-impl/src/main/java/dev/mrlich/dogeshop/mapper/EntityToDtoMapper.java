package dev.mrlich.dogeshop.mapper;

import dev.akkinoc.spring.boot.orika.OrikaMapperFactoryConfigurer;
import dev.mrlich.dogeshop.api.dto.AccountDto;
import dev.mrlich.dogeshop.api.dto.OrderDto;
import dev.mrlich.dogeshop.api.dto.SkinDto;
import dev.mrlich.dogeshop.entity.Account;
import dev.mrlich.dogeshop.entity.Order;
import dev.mrlich.dogeshop.entity.Skin;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class EntityToDtoMapper implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        mapperFactory.classMap(AccountDto.class, Account.class)
                .byDefault()
                .register();
        mapperFactory.classMap(SkinDto.class, Skin.class)
                .byDefault()
                .register();
        mapperFactory.classMap(OrderDto.class, Order.class)
                .byDefault()
                .register();
    }
}
