<#assign title = "Регистрация">
<#assign authed = false>

<#include "blocks/header.ftl">

<div class="p-2 row">
    <div class="col-md-6 offset-md-3">
        <form action="" method="post">
            <div class="mb-3">
                <label for="email" class="col-form-label">Электронная почта: <span class="text-danger">*</span></label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="mb-3">
                <label for="pass" class="col-form-label">Пароль: <span class="text-danger">*</span></label>
                <input type="password" class="form-control" id="pass" name="pass" required>
            </div>
            <div class="mb-3">
                <label for="pass2" class="col-form-label">Пароль: <span class="text-danger">*</span></label>
                <input type="password" class="form-control" id="pass2" name="pass2" required>
            </div>
            <div class="col-12 text-center">
                <button class="btn btn-light border rounded" type="submit">Зарегистрироваться</button>
            </div>
        </form>
    </div>
</div>

<#include "blocks/footer.ftl">