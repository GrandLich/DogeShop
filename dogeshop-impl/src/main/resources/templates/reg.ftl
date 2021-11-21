<#assign title = "Регистрация">
<#assign authed = false>

<#include "blocks/header.ftl">

<div class="p-2 row">
    <div class="col-md-6 offset-md-3">
        <form action="/api/account/register" method="post">
            <div class="mb-3">
                <label for="username" class="col-form-label">Имя пользователя: <span class="text-danger">*</span></label>
                <input type="username" class="form-control" id="username" name="username" required>
            </div>
            <div class="mb-3">
                <label for="password" class="col-form-label">Пароль: <span class="text-danger">*</span></label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="col-12 text-center">
                <button class="btn btn-light border rounded" type="submit">Зарегистрироваться</button>
            </div>
        </form>
    </div>
</div>

<#include "blocks/footer.ftl">