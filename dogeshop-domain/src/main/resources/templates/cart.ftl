<#assign title = "DogeShop | Cart">

<#include "blocks/header.ftl">

<div>
    <div class="row">
        <div class="col-md-6 d-flex">
            <span class="my-auto"><@spring.message code='cart.balance'/> ${balance} <@spring.message code='cart.currency'/></span>
        </div>
        <div class="col-md-6 d-flex flex-row-reverse">
            <a href="/payment" class="btn btn-light border" style="max-width: 200px;" href=""><@spring.message code='cart.deposit'/></a>
        </div>
    </div>
    <div class="row mt-4">
        <div class="text-center col-12">
            <h2><@spring.message code='cart.title'/></h2>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th><@spring.message code='cart.skin'/></th>
                    <th><@spring.message code='cart.price'/></th>
                </tr>
                <tr>
                    <th>
                        <select class="form-control" name="cur" id="cur">
                            <option value="RUB" selected>Рубль</option>
                            <option value="UAH">Хрыфни</option>
                            <option value="USD">Доллары</option>
                            <option value="EUR">Евро</option>
                            <option value="JPY">Анимешные монетки</option>
                        </select>
                    </th>
                </tr>
            </thead>
            <tbody>
                <#list cartSkins as skin>
                <tr class="product">
                                    <td>${skin.name}</td>
                                    <td><span class="product_price" data-price="${skin.price?string.computer}">${skin.price}</span> <span class="product_cur">руб.</span></td>
                                </tr>
                </#list>
                <tr class="border-top">
                                                    <td class="text-end pt-5 fw-bold"><@spring.message code='cart.total'/></td>
                                                    <td class="pt-5"><span class="total_price" data-price="80">80</span> <span class="total_cur">руб.</span></td>
                                                </tr>
            </tbody>
        </table>
    </div>
    <div class="d-flex flex-row justify-content-around mt-3">
        <button id="btn_buy" data-bs-toggle="modal" data-bs-target="#successModal" class="btn btn-light rounded border"><@spring.message code='cart.buy'/></button>
        <button class="btn btn-light rounded border"><@spring.message code='cart.clear'/></button>
    </div>
</div>

<script>
    $(document).ready(function(){
    $('#cur').on("change", function () {
        let cur = $(this).val();
        $.ajax({
            url:'https://www.cbr-xml-daily.ru/latest.js',
            type:'get',
            success:function(response){
                let currencies;
                let c_n = {
                    'UAH': 'Хрывень',
                    'EUR': 'Евро',
                    'JPY': 'Анимешных монеток',
                    'USD': 'Долларов',
                    'RUB': 'Рублей'
                }
                currencies = JSON.parse(response).rates;
                let rate = 1;
                if (cur !== "RUB") { rate = currencies[cur]; }
                $('.product').each(function () {
                    let p_price = $(this).find('.product_price');
					
                    p_price.text(Math.floor((Number(p_price.attr('data-price')) * rate) * 100) / 100);
                    $(this).find('.product_cur').text(c_n[cur])
                });
                let t_price = $('.total_price');
                t_price.text(Math.floor((Number(t_price.attr('data-price')) * rate) * 100) / 100);
                $('.total_cur').text(c_n[cur]);

            },
            error: function (err) {
                alert('Произошла ошибка :(');
            }
        });

    });

        $("#btn_clear").on('click', function(e){
    		e.preventDefault();
                $.ajax({
                    url:'/api/cart/clear',
                    type:'post',
                    success:function(response) {
    					window.location.reload();
    					console.log(response)
                    },
                    error: function (err) {
                        console.log(err)
                    }
                });
        });

        $("#btn_buy").on('click', function(e){
            		e.preventDefault();
                        $.ajax({
                            url:'/api/cart/buy',
                            type:'post',
                            success:function(response) {
            					console.log(response)
                            },
                            error: function (err) {
                                console.log(err)
                            }
                        });
                });
        $("#modal_close").on('click', function(e) {
            e.preventDefault();
            window.location.reload();
        });
    });
</script>

<div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content" style="background-color: #59E12F;">
            <div class="modal-header">
                <h5 class="modal-title" id="successModalLabel"><@spring.message code='cart.notification.title'/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="modal_close"></button>
            </div>
            <div class="modal-body text-center">
                <h2><@spring.message code='cart.notification.success.text'/></h2>
            </div>
        </div>
    </div>
</div>

<#include "blocks/footer.ftl">